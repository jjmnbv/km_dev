package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.user.remote.service.TrationListRemoteService;
import com.pltfm.app.dao.InvoiceDAO;
import com.pltfm.app.dao.InvoiceItemDAO;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.dao.OrderOperateStatementDAO;
import com.pltfm.app.dao.OrderPayStatementDAO;
import com.pltfm.app.dao.OrderPreferentialDAO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.InvoiceItem;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderItemExample;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderOperateStatement;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.entities.OrderPayStatementExample;
import com.pltfm.app.entities.OrderPreferential;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.app.service.OrderPayStatementService;
import com.pltfm.app.service.RiskManagementService;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.OrderInterFaceOperateTypeEnum;
import com.pltfm.app.util.SettlementAndPayUtil;
import com.pltfm.app.util.SysConstants;
import com.pltfm.app.vobject.PaymentVO;
import com.pltfm.sys.util.ErrorCode;

@Service("orderPayStatementService")
@Scope("singleton")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderPayStatementServiceImpl extends BaseService implements OrderPayStatementService {

    private Logger log = Logger.getLogger(OrderPayStatementServiceImpl.class);

    @Resource
    OrderPayStatementDAO orderPayStatementDAO;
    @Resource
    OrderOperateStatementDAO orderOperateStatementDAO;
    @Resource
    OrderMainDAO orderMainDAO;
    @Resource
    OrderItemQryService orderItemQryService;

    /*
     * 删除微商业务 @Resource private SpreadEffectDAO spreadEffectDAO;
     */
    @Resource
    InvoiceDAO invoiceDAO;
    @Resource
    private InvoiceItemDAO invoiceItemDAO;
    @Resource
    private OrderPreferentialDAO orderPreferentialDAO;
    // 客户系统的修改账户余额的接口服务类
    @Autowired
    private TrationListRemoteService trantionListRemoteService;
    // 产品系统的优惠接口服务类
    @Autowired
    private CouponRemoteService couponRemoteService;
    @Resource
    private RiskManagementService riskManagementService;
    @Autowired
    private SettlementAndPayUtil settlementAndPayUtil;

    @SuppressWarnings("unused")
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int pay(String operator, Long paymentWay, String account, String orderCode,
            BigDecimal orderMoney, String outsidePayStatementNo, Long flag,
            BigDecimal preferentialNo, BigDecimal preferentialGrantId, Long status, Long state,
            String platFormCode, String platFormName) throws ServiceException {
        int result = -1;
        OrderMain om = null;
        try {
            om = orderMainDAO.selectByOrderCode(orderCode);
        } catch (Exception e1) {
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, "订单不存在");
        }
        if (null == om) {
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, "订单不存在");
        }
        om.setOrderStatus(status);
        // 根据orderCode查询出所有的订单明细的列表
        List<OrderItem> orderItemList = orderItemQryService.listOrderItems(orderCode);
        String SKUStr = null;
        BigDecimal wareHouseId = null;
        Long wareHouseIdLongValue = null;
        Long count = null;
        // 遍历orderItemMap中的仓库id，组装锁库存用的map，形如Map<SKUCode,Map<WareHouseId,Count>>
        Map<String, Map<Long, Long>> // lockStockMap = new HashMap<String,
        // Map<Long, Long>>();
        lockStockMap = SettlementAndPayUtil.builderStockMap(orderItemList);
        Date now = new Date(System.currentTimeMillis() + 10000);// 操作时间
        Date endDate = null;// 支付完成时间
        String comment = null;
        if (flag.intValue() == OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()) {// 退款
            // 退款条件限制
            if (om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Not_Pay.getKey()
                    || om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Pay_Done
                            .getKey()
                    || om.getOrderStatus()
                            .intValue() == OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()
                    || om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Stock_Lock
                            .getKey()
                    || om.getOrderStatus()
                            .intValue() == OrderDictionaryEnum.Order_Status.Risk_Appraise.getKey()
                    || om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Risk_Pass
                            .getKey()
                    || om.getOrderStatus()
                            .intValue() == OrderDictionaryEnum.Order_Status.Nopay_FinalMoney
                                    .getKey()) {// 前置条件：已完成

                if (paymentWay.intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.Balance
                        .getKey()
                        || paymentWay
                                .intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.AlipayToBalance
                                        .getKey()) {
                    // 余额退回
                    try {
                        // 记录支付流水
                        save(paymentWay, state, account, orderCode, orderMoney.negate(), now, now,
                                outsidePayStatementNo, flag, preferentialNo, null, null, null,
                                null);
                        Long tempFlag = flag;
                        if (om.getOrderStatus()
                                .intValue() == OrderDictionaryEnum.Order_Status.Not_Pay.getKey()
                                || om.getOrderStatus()
                                        .intValue() == OrderDictionaryEnum.Order_Status.Pay_Done
                                                .getKey()) {
                            tempFlag = 0l;// 取消订单
                        }
                        Integer re = trantionListRemoteService.orderTrationAccout(account,
                                (orderMoney.doubleValue()), orderCode + "", "支付内容:退款到余额账户",
                                tempFlag.intValue());
                        if (1 != re) {
                            throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_PAY_ERROR,
                                    "余额退回失败,返回re=" + re);
                        }
                        result = 1;
                    } catch (Exception e) {
                        log.error("余额退回异常", e);
                        throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR,
                                "余额退回失败：" + e.getMessage());
                    }
                } else if (paymentWay
                        .intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.Coupon.getKey()) {
                    // 优惠券退回
                    try {
                        UserInfoDO userInfo = new UserInfoDO();
                        userInfo.setLoginId(om.getCustomerId().intValue());
                        // 优惠券接口
                        couponRemoteService.changeCustomGrantToGive(userInfo,
                                preferentialNo.longValue(), om.getCustomerId().longValue(),
                                SysConstants.COUPON_UNFREEZE, om.getOrderCode(),
                                preferentialGrantId.longValue());
                        // 记录支付流水
                        save(paymentWay, state, account, orderCode, orderMoney.negate(), now, now,
                                outsidePayStatementNo, flag, preferentialNo, null, null, null,
                                null);
                    } catch (Exception e) {
                        log.error("记录支付流水异常", e);
                    }
                    result = 1;
                } else if (paymentWay
                        .intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.Platform
                                .getKey()) {

                    save(paymentWay, state, account, orderCode, orderMoney.negate(), now, now,
                            outsidePayStatementNo, flag, preferentialNo, null, platFormName, null,
                            platFormCode);
                    result = 1;
                } else {
                    throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, "退款方式不支持！");
                }
            } else {
                throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, "订单此状态下不能退款！");
            }

        } else {
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, "未知请求！");
        }
        return result;
    }

    // 预售添加
    @SuppressWarnings("unused")
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int pay(String operator, Long paymentWay, String account, String orderCode,
            BigDecimal orderMoney, String outsidePayStatementNo, Long flag,
            BigDecimal preferentialNo, BigDecimal preferentialGrantId, Long status, Long state,
            String platFormCode, String platFormName, String ysFlage) throws ServiceException {
        int result = -1;
        OrderMain om = null;
        try {
            om = orderMainDAO.selectByOrderCode(orderCode);
        } catch (Exception e1) {
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, "订单不存在");
        }
        if (null == om) {
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, "订单不存在");
        }
        om.setOrderStatus(status);
        // 根据orderCode查询出所有的订单明细的列表
        List<OrderItem> orderItemList = orderItemQryService.listOrderItems(orderCode);
        String SKUStr = null;
        BigDecimal wareHouseId = null;
        Long wareHouseIdLongValue = null;
        Long count = null;
        // 遍历orderItemMap中的仓库id，组装锁库存用的map，形如Map<SKUCode,Map<WareHouseId,Count>>
        Map<String, Map<Long, Long>> // lockStockMap = new HashMap<String,
        // Map<Long, Long>>();
        lockStockMap = SettlementAndPayUtil.builderStockMap(orderItemList);
        Date now = new Date(System.currentTimeMillis() + 10000);// 操作时间
        Date endDate = null;// 支付完成时间
        String comment = null;
        if (flag.intValue() == OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()) {// 退款
            // 退款条件限制
            if (om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Not_Pay.getKey()
                    || om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Pay_Done
                            .getKey()
                    || om.getOrderStatus()
                            .intValue() == OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()
                    || om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Stock_Lock
                            .getKey()
                    || om.getOrderStatus()
                            .intValue() == OrderDictionaryEnum.Order_Status.Risk_Appraise.getKey()
                    || om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Risk_Pass
                            .getKey()
                    || om.getOrderStatus()
                            .intValue() == OrderDictionaryEnum.Order_Status.Nopay_FinalMoney
                                    .getKey()) {// 前置条件：已完成
                if (paymentWay.intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.Balance
                        .getKey()
                        || paymentWay
                                .intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.AlipayToBalance
                                        .getKey()) {
                    // 余额退回
                    try {
                        // 记录支付流水
                        save(paymentWay, state, account, orderCode, orderMoney.negate(), now, now,
                                outsidePayStatementNo, flag, preferentialNo, null, null, null,
                                null);
                        Long tempFlag = flag;
                        if (om.getOrderStatus()
                                .intValue() == OrderDictionaryEnum.Order_Status.Not_Pay.getKey()
                                || om.getOrderStatus()
                                        .intValue() == OrderDictionaryEnum.Order_Status.Pay_Done
                                                .getKey()) {
                            tempFlag = 0l;// 取消订单
                        }
                        if (1 != trantionListRemoteService.orderTrationAccout(account,
                                (orderMoney.doubleValue()), orderCode + "", "支付内容:退款到余额账户",
                                tempFlag.intValue())) {
                            throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_PAY_ERROR,
                                    "余额退回失败");
                        }
                        result = 1;
                    } catch (Exception e) {
                        log.error("余额退回异常", e);
                        throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR,
                                "余额退回失败：" + e.getMessage());
                    }
                } else if (paymentWay
                        .intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.Coupon.getKey()) {
                    // 优惠券退回
                    try {
                        UserInfoDO userInfo = new UserInfoDO();
                        userInfo.setLoginId(om.getCustomerId().intValue());
                        // 优惠券接口
                        couponRemoteService.changeCustomGrantToGive(userInfo,
                                preferentialNo.longValue(), om.getCustomerId().longValue(),
                                SysConstants.COUPON_UNFREEZE, om.getOrderCode(),
                                preferentialGrantId.longValue());
                        // 记录支付流水
                        save(paymentWay, state, account, orderCode, orderMoney.negate(), now, now,
                                outsidePayStatementNo, flag, preferentialNo, null, null, null,
                                null);
                    } catch (Exception e) {
                        log.error("记录支付流水异常", e);
                    }
                    result = 1;
                } else if (paymentWay
                        .intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.Platform
                                .getKey()) {
                    // 支付成功
                    save(paymentWay, state, account, orderCode, orderMoney.negate(), now, now,
                            outsidePayStatementNo, flag, preferentialNo, null, platFormName, null,
                            platFormCode, ysFlage);
                    result = 1;
                } else {
                    throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, "退款方式不支持！");
                }
            } else {
                throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, "订单此状态下不能退款！");
            }

        } else {
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, "未知请求！");
        }
        return result;
    }

    /**
     * 检查支付是否完成 orderMoney 已支付金额（去除优惠券冻结的金额）
     * 
     * @throws ServiceException
     * @throws SQLException
     */
    @Override
    public Boolean checkFinish(String orderCode, BigDecimal orderMoney, BigDecimal amountPayable,
            Long flag) throws ServiceException {
        Boolean result = false;
        // 订单当次支付金额
        BigDecimal orderPayMoney = BigDecimal.ZERO;
        // 订单已付金额+订单当次支付金额
        BigDecimal orderPayMoneyTemp = BigDecimal.ZERO;
        // 是否为支付动作的判断标识位
        Boolean flagCompareResult = false;
        // 订单应付金额与(订单已付金额+订单当次支付金额)的比较结果
        int payCompareIntResult = 0;
        try {
            Map map = new HashMap();
            map.put("orderCode", orderCode);
            // 根据订单号查询出已支付金额 -- 已冻结优惠券金额和已支付成功的其他支付金额
            orderPayMoney = getOrderPay(map);
            // 打印日志
            log.info("订单号：" + orderCode + "检查是否支付完成数据：当次支付金额为：" + orderMoney + "；订单应付金额为："
                    + amountPayable + "；已支付金额为：" + orderPayMoney);
            // 如果不为空则加上当次支付金额
            if (orderMoney == null) {
                orderPayMoneyTemp = orderPayMoney;
            } else {
                orderPayMoneyTemp = orderMoney.add(orderPayMoney);
            }
            // 得到订单应付金额与(订单已付金额+订单当次支付金额)的比较结果的赋值
            if (amountPayable != null) {
                payCompareIntResult = amountPayable.compareTo(orderPayMoneyTemp);
            }
            // 得到是否为支付动作的标识位的赋值
            flagCompareResult =
                    flag.intValue() == OrderDictionaryEnum.OrderPayFlag.Payment.getKey();

            if (flagCompareResult) {
                result = payCompareIntResult <= 0;
            } else {
                result = payCompareIntResult >= 0;
            }

        } catch (Exception e) {
            log.error("订单号：" + orderCode + "检查是否支付完成时异常：当次支付金额为" + orderMoney + "；订单应付金额为"
                    + amountPayable, e);
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                    "检查订单" + orderCode + "支付是否完成：" + e.getMessage());
        }
        return result;
    }

    /**
     * 记录订单支付流水
     * 
     * @param paymentWay 支付方式
     * @param state 支付状态
     * @param account 客户账号
     * @param orderCode 订单号
     * @param orderMoney 付款金额
     * @param createDate 生成日期
     * @param endDate 支付完成日期
     * @param outsidePayStatementNo 第三方支付流水号
     * @param flag 付款/退款标识
     * @param preferentialNo 优惠券号
     * @param bankName 银行名称
     * @param platFormName 支付平台名称
     * @param bankCode 银行代码
     * @param platFormCode 支付平台代码
     * @throws ServiceException
     */
    @Override
    public void save(Long paymentWay, Long state, String account, String orderCode,
            BigDecimal orderMoney, Date createDate, Date endDate, String outsidePayStatementNo,
            Long flag, BigDecimal preferentialNo, String bankName, String platFormName,
            String bankCode, String platFormCode) throws ServiceException {
        try {
            if (state == OrderDictionaryEnum.OrderPayState.Ready.getKey()
                    && flag == OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()) {
                OrderPayStatementExample example = new OrderPayStatementExample();
                OrderPayStatementExample.Criteria criteria = example.createCriteria();
                criteria.andOrderCodeEqualTo(orderCode);
                criteria.andStateEqualTo(state);
                criteria.andFlagEqualTo(flag);
                List list = orderPayStatementDAO.selectByExample(example);
                if (null == list || 0 == list.size()) {
                    orderPayStatementDAO.insert(newOrderPay(paymentWay, state, account, orderCode,
                            orderMoney, createDate, endDate, outsidePayStatementNo, flag,
                            preferentialNo, bankName, platFormName, bankCode, platFormCode));
                }
            } else {
                orderPayStatementDAO.insert(newOrderPay(paymentWay, state, account, orderCode,
                        orderMoney, createDate, endDate, outsidePayStatementNo, flag,
                        preferentialNo, bankName, platFormName, bankCode, platFormCode));
            }
        } catch (SQLException e) {
            log.error("保存订单支付流水发生异常", e);
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                    "订单号：" + orderCode + " 保存订单支付流水失败" + e.getMessage());
        }
    }


    /**
     * 记录订单支付流水---预售取消订单退款添加
     * 
     * @param paymentWay 支付方式
     * @param state 支付状态
     * @param account 客户账号
     * @param orderCode 订单号
     * @param orderMoney 付款金额
     * @param createDate 生成日期
     * @param endDate 支付完成日期
     * @param outsidePayStatementNo 第三方支付流水号
     * @param flag 付款/退款标识
     * @param preferentialNo 优惠券号
     * @param bankName 银行名称
     * @param platFormName 支付平台名称
     * @param bankCode 银行代码
     * @param platFormCode 支付平台代码
     * @throws ServiceException
     */
    public void save(Long paymentWay, Long state, String account, String orderCode,
            BigDecimal orderMoney, Date createDate, Date endDate, String outsidePayStatementNo,
            Long flag, BigDecimal preferentialNo, String bankName, String platFormName,
            String bankCode, String platFormCode, String ysFlage) throws ServiceException {
        try {
            if (state == OrderDictionaryEnum.OrderPayState.Ready.getKey()
                    && flag == OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()) {
                OrderPayStatementExample example = new OrderPayStatementExample();
                OrderPayStatementExample.Criteria criteria = example.createCriteria();
                criteria.andOrderCodeEqualTo(orderCode);
                criteria.andStateEqualTo(state);
                criteria.andFlagEqualTo(flag);
                criteria.addYsFlageEqualTo(ysFlage);
                List list = orderPayStatementDAO.selectByExample(example);
                if (null == list || 0 == list.size()) {
                    orderPayStatementDAO.insert(newOrderPay(paymentWay, state, account, orderCode,
                            orderMoney, createDate, endDate, outsidePayStatementNo, flag,
                            preferentialNo, bankName, platFormName, bankCode, platFormCode,
                            ysFlage));
                }
            } else {
                orderPayStatementDAO.insert(newOrderPay(paymentWay, state, account, orderCode,
                        orderMoney, createDate, endDate, outsidePayStatementNo, flag,
                        preferentialNo, bankName, platFormName, bankCode, platFormCode, ysFlage));
            }
        } catch (SQLException e) {
            log.error("保存订单支付流水发生异常", e);
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                    "订单号：" + orderCode + " 保存订单支付流水失败" + e.getMessage());
        }
    }

    // 取消订单--预售添加
    private OrderPayStatement newOrderPay(Long paymentWay, Long state, String account,
            String orderCode, BigDecimal orderMoney, Date createDate, Date endDate,
            String outsidePayStatementNo, Long flag, BigDecimal preferentialNo, String bankName,
            String platFormName, String bankCode, String platFormCode, String ysFlage) {
        OrderPayStatement newRecord = new OrderPayStatement();
        newRecord.setAccount(account);
        newRecord.setCreateDate(createDate);
        newRecord.setEndDate(endDate);
        newRecord.setFlag(flag);
        newRecord.setOrderCode(orderCode);
        newRecord.setOrderMoney(orderMoney);
        newRecord.setOutsidePayStatementNo(outsidePayStatementNo);
        newRecord.setPaymentWay(paymentWay);
        newRecord.setPreferentialNo(preferentialNo);
        newRecord.setState(state);
        newRecord.setBankName(bankName);
        newRecord.setBankCode(bankCode);
        newRecord.setPlatFormCode(platFormCode);
        newRecord.setPlatFormName(platFormName);
        newRecord.setYsFlage(ysFlage);
        return newRecord;
    }

    private OrderPayStatement newOrderPay(Long paymentWay, Long state, String account,
            String orderCode, BigDecimal orderMoney, Date createDate, Date endDate,
            String outsidePayStatementNo, Long flag, BigDecimal preferentialNo, String bankName,
            String platFormName, String bankCode, String platFormCode) {
        OrderPayStatement newRecord = new OrderPayStatement();
        newRecord.setAccount(account);
        newRecord.setCreateDate(createDate);
        newRecord.setEndDate(endDate);
        newRecord.setFlag(flag);
        newRecord.setOrderCode(orderCode);
        newRecord.setOrderMoney(orderMoney);
        newRecord.setOutsidePayStatementNo(outsidePayStatementNo);
        newRecord.setPaymentWay(paymentWay);
        newRecord.setPreferentialNo(preferentialNo);
        newRecord.setState(state);
        newRecord.setBankName(bankName);
        newRecord.setBankCode(bankCode);
        newRecord.setPlatFormCode(platFormCode);
        newRecord.setPlatFormName(platFormName);
        return newRecord;
    }

    @Override
    public BigDecimal getOrderPay(Map map) throws ServiceException {
        try {
            return orderPayStatementDAO.getOrderPay(map);
        } catch (SQLException e) {
            log.error("获取支付总额发生错误", e);
            throw new ServiceException(ErrorCode.INNER_PAYMENT_CALC_ERROR,
                    "获取支付总额发生错误：" + e.getMessage());
        }
    }

    @Override
    public BigDecimal getOrderRealPay(Map map) throws ServiceException {
        try {
            return orderPayStatementDAO.getOrderPay(map);
        } catch (SQLException e) {
            log.error("获取订单实付金额发生错误", e);
            throw new ServiceException(ErrorCode.INNER_PAYMENT_CALC_ERROR,
                    "获取订单实付金额发生错误：" + e.getMessage());
        }
    }

    @Override
    public BigDecimal getNotPay(String orderCode) throws ServiceException {
        try {
            Map map = new HashMap();
            map.put("orderCode", orderCode);
            return orderMainDAO.selectByOrderCode(orderCode).getAmountPayable()
                    .subtract(orderPayStatementDAO.getOrderPay(map));
        } catch (SQLException e) {
            log.error("获取未付款金额发生错误", e);
            throw new ServiceException(ErrorCode.INNER_PAYMENT_CALC_ERROR,
                    "获取未付款金额发生错误：" + e.getMessage());
        }
    }

    /**
     * 
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int batchPay(PaymentVO paymentVO) throws ServiceException {

        // 初始化返回结果
        int result = 0;
        // 初始化支付核心处理方法的返回结果
        int coreHandleResult = 0;
        // 获取支付对象列表
        List<OrderPayStatement> orderPayStatementList = paymentVO.getOrderPayStatementList();
        // 获取付款/退款标识
        Long payOrRefundFlag = paymentVO.getFlag();
        // 获取支付方式
        Long paymentWay = paymentVO.getPaymentWay();
        // 获取客户账户
        String account = paymentVO.getAccount();
        // 支付时间完成时间
        Date endDate = new Date();
        // 获取订单编码
        String orderCode = paymentVO.getOrderCode();
        // 获取登录账户的id
        Long loginId = paymentVO.getLogInId();
        // 初始化订单对象
        OrderMain om = null;
        // 初始化订单金额
        BigDecimal orderMoney = null;
        // 初始化余额支付的金额,paymentVO的orderMoney只存放余额支付的金额（如果有余额支付的话）
        BigDecimal accountBalanceMoney = null;
        // 初始化订单金额列表
        List<Double> orderMoneyList = new ArrayList<Double>();
        // 订单金额总额
        BigDecimal orderMoneyAll = BigDecimal.ZERO;
        // 初始化校验订单支付是否完成的返回结果
        Boolean checkFinishResult = false;
        // 实例化当前时间
        Date now = new Date();
        // 操作信息用于记录操作流水
        String comment = null;
        // 初始化当次操作流水的操作类型
        Long nowOperateType = new Long(OrderDictionaryEnum.OrderOperateType.Pay.getKey());
        // 初始化当次操作流水的订单状态
        Long nowOrderStatus = null;
        // 未支付的订单状态
        Long notPayOrderStatus = new Long(OrderDictionaryEnum.Order_Status.Not_Pay.getKey());
        // 已支付的订单状态
        Long payDoneOrderStatus = new Long(OrderDictionaryEnum.Order_Status.Pay_Done.getKey());
        // 临时支付流水列表对象
        List<OrderPayStatement> orderPayStatementTempList = new ArrayList<OrderPayStatement>();
        // 支付流水临时对象
        OrderPayStatement orderPayStatementTemp = null;
        // 余额支付相关接口返回结果
        @SuppressWarnings("unused")
        int trantionRemoteResult = 0;
        // 初始化是否含有余额支付的标志位
        Boolean balancePayFlag = false;
        // 初始化是否含有优惠券支付的标志位
        Boolean couponPayFlag = false;
        // 初始化是否含有预备金支付的标志位
        /* Boolean reservePayFlag = false; */
        // 优惠券编码
        Long preferentialNo = null;
        // 优惠券发放id
        Long preferentialGrantId = null;
        // 用户信息对象
        UserInfoDO userInfo = null;
        try {
            if (orderPayStatementList != null && orderPayStatementList.size() > 0) {
                // 判断优惠券是否已支付成功
                boolean couponstate = false;
                for (OrderPayStatement orderPayStatement : orderPayStatementList) {
                    paymentWay = orderPayStatement.getPaymentWay();
                    if (OrderDictionaryEnum.OrderPayMethod.Coupon.getKey() == paymentWay.intValue()
                            && orderPayStatement.getState().equals(Long.parseLong("1"))) {
                        couponstate = true;
                    }

                }
                for (OrderPayStatement orderPayStatement : orderPayStatementList) {

                    orderPayStatement.setCreateDate(now);

                    payOrRefundFlag = orderPayStatement.getFlag();
                    paymentWay = orderPayStatement.getPaymentWay();
                    // 如果是余额支付，则赋值accountBalanceMoney，用于后续修改订单时setAccountBalance
                    if (OrderDictionaryEnum.OrderPayMethod.Balance.getKey() == paymentWay
                            .intValue()) {
                        accountBalanceMoney = paymentVO.getOrderMoney();
                        // 如果是余额支付，在下单的时候已经做了真正的支付动作，其金额已经算入历史支付金额，在此不应累加记入当次支付总金额
                    }

                    if ((OrderDictionaryEnum.OrderPayMethod.Balance.getKey() != paymentWay
                            .intValue())
                            /*
                             * 删除预备金 && (OrderDictionaryEnum.OrderPayMethod.Reserve.getKey() !=
                             * paymentWay.intValue())
                             */
                            && !couponstate) {// 新增：优惠券如果已支付成功，则不再添加支付流水
                        // 赋值支付流水临时对象
                        orderPayStatementTemp = new OrderPayStatement();
                        BeanUtils.copyProperties(orderPayStatement, orderPayStatementTemp);
                        orderPayStatementTemp.setEndDate(now);
                        if (OrderDictionaryEnum.OrderPayMethod.Coupon.getKey() == paymentWay
                                .intValue()) {// 修改优惠券支付流水为1
                            orderPayStatementTemp.setState(1L);
                        }
                    }

                    // 如果是非余额支付或是预备金支付，则需要构建新的支付流水，在后面进行插入数据的操作
                    if ((OrderDictionaryEnum.OrderPayMethod.Balance.getKey() != paymentWay
                            .intValue())
                            /*
                             * 删除预备金 && (OrderDictionaryEnum.OrderPayMethod.Reserve.getKey() !=
                             * paymentWay.intValue())
                             */
                            && (OrderDictionaryEnum.OrderPayMethod.Coupon.getKey() != paymentWay
                                    .intValue())) {// 除去使用优惠券金额,否者后面方法checkFinish（）中计算orderPayMoneyTemp时会重复计算优惠券金额

                        orderMoney = orderPayStatement.getOrderMoney();
                        if (orderMoney != null) {
                            // 累加每次支付的金额
                            orderMoneyAll = orderMoneyAll.add(orderMoney);
                            orderMoneyList.add(orderMoney.doubleValue());
                        }
                    }
                    if (accountBalanceMoney != null) {
                        orderMoneyAll = orderMoneyAll.add(accountBalanceMoney);
                        orderMoneyList.add(accountBalanceMoney.doubleValue());
                    }
                    // 如果是优惠券支付，则赋值preferentialNo，用于后续修改订单操作
                    if (OrderDictionaryEnum.OrderPayMethod.Coupon.getKey() == paymentWay
                            .intValue()) {
                        preferentialNo = orderPayStatement.getPreferentialNo().longValue();
                        if (!couponstate) {// 优惠券没有支付成功才设置支付状态为成功状态
                            orderPayStatementTemp.setState(
                                    new Long(OrderDictionaryEnum.OrderPayState.Success.getKey()));
                        }
                        preferentialGrantId = null != orderPayStatement.getPreferentialGrantId()
                                ? orderPayStatement.getPreferentialGrantId().longValue() : 0l;
                    }
                    // 如果是非余额支付，在前面的操作中会实例化新的支付流水对象，故此时加入临时支付流水对象列表，用于后续的插入支付流水
                    if (orderPayStatementTemp != null) {
                        orderPayStatementTempList.add(orderPayStatementTemp);
                    }

                    coreHandleResult = this.corePay(orderPayStatement, loginId, couponstate);
                    // 根据支付标志位不同，来给comment赋值，是付款还是退款
                    if (OrderDictionaryEnum.OrderPayFlag.Payment.getKey() == payOrRefundFlag
                            .intValue()) {// 付款
                        comment = SysConstants.COMMENT_PAY;
                    } else if (OrderDictionaryEnum.OrderPayFlag.Refundment
                            .getKey() == payOrRefundFlag.intValue()) {
                        comment = SysConstants.COMMENT_REFUND;
                    }

                    if (coreHandleResult == -1) {
                        throw new ServiceException(ErrorCode.INTER_PAYMENT_CALC_ERROR,
                                "订单：" + orderPayStatement.getOrderCode() + " 进行订单支付核心处理时发生异常！");
                    } else if (coreHandleResult == 1) {
                        result = 1;
                    }
                }
            } else {// 此分支下说明下单时余额支付已支付完所有金额，这种情况下传来的支付关键信息只有余额支付的金额OrderMoney
                coreHandleResult = 1;
                orderMoneyAll = paymentVO.getOrderMoney();
            }
            // 核心处理结果为1才做后续的处理
            if (coreHandleResult == 1) {
                // 首先通过orderCode查询得到订单对象
                om = orderMainDAO.selectByOrderCode(orderCode);
                // 根据orderCode查询出所有的订单明细的列表
                List<OrderItem> orderItemList = orderItemQryService.listOrderItems(orderCode);

                nowOrderStatus = notPayOrderStatus;
                // 如果批量操作库存成功才修改订单状态，且订单应付金额等于支付列表中累加金额
                checkFinishResult = this.checkFinish(orderCode, orderMoneyAll,
                        om.getAmountPayable(), payOrRefundFlag);

                /*
                 * 删除微商业务 String saleRebateAmount = "";// 销售返佣 String distriRebackAmount = "";//
                 * 分销返佣 SpreadEffect spereadEffect = new SpreadEffect(); List<SpreadEffect>
                 * spereadEffectList = null;
                 */
                // 如果调试打开，则校验订单金额是否全部支付完的判断失效
                if (checkFinishResult) {
                    // if(SysConstants.DEBUG_MODE||checkFinishResult){
                    // 此时订单已支付完成，其订单状态应为已支付
                    nowOrderStatus = payDoneOrderStatus;
                    if (om.getOrderType() == 7) {
                        comment = "您的订单" + orderCode + "已经成功支付尾款,正在安排配送。";
                        orderMoneyAll = om.getAmountPayable();
                    } else {
                        comment = "您的订单" + orderCode + "已经支付成功";
                    }
                    om.setAccountBalance(orderMoney);// 余额支付量
                    om.setPayMethod(new Long(OrderDictionaryEnum.OrderPayMethod.OnLine.getKey()));// 支付方式，统一改为在线支付
                    om.setPayDate(endDate);// 支付完成时间
                    om.setOrderStatus(nowOrderStatus);// 订单状态改为已支付

                    // 生成发票
                    Invoice invoice = SettlementAndPayUtil.order2invoice(om, now);
                    Long invoiceId = invoiceDAO.insertSelective(invoice);
                    // 生成发票明细
                    OrderItemExample oi = new OrderItemExample();
                    OrderItemExample.Criteria criteria = oi.createCriteria();
                    criteria.andOrderCodeEqualTo(om.getOrderCode());
                    // List<OrderItem> orderItemList =
                    // orderItemQryService.listOrderItems(om.getOrderCode());
                    InvoiceItem invoiceItem = null;
                    for (Object oit : orderItemList) {
                        invoiceItem = SettlementAndPayUtil.orderItem2invoiceItem((OrderItem) oit,
                                invoiceId);
                        invoiceItemDAO.insertSelective(invoiceItem);
                    }
                    om.setInvoiceId(invoiceId);// 生成发票信息
                    orderMainDAO.updateByOrderCode(om);
                }
                if (orderPayStatementList != null && orderPayStatementList.size() > 0) {
                    orderPayStatementDAO.insertList(orderPayStatementTempList);
                }
                // paymentVO.getOrderMoney()改为orderMoneyAll
                this.orderOperateStatement(paymentVO, nowOperateType, nowOrderStatus, orderMoneyAll,
                        comment);

                // 订单支付成功同步订单信息到总部系统
                /*
                 * 删除同步总部会员功能 try { List<String> lstOrderCode = new ArrayList<String>();
                 * lstOrderCode.add(paymentVO.getOrderCode());
                 * orderQryService.syncOrderInfo2Base(lstOrderCode); } catch (Exception e) {
                 * log.error("订单支付成功同步订单信息到总部系统异常!"); }
                 */
                result = 1;
                // }
            }
        } catch (Exception e) {
            log.error(e);
            log.error("发生错误", e);
            try {
                om = orderMainDAO.selectByOrderCode(orderCode);
            } catch (Exception e1) {
                throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, "订单不存在");
            }
            /**
             * 发生异常之后所有和接口相关的操作在此处回滚
             */
            // 从OrderPayStatementList中判断是否含有余额支付、优惠券支付、预备金支付
            if (orderPayStatementList != null && orderPayStatementList.size() > 0) {
                Long paymentWayForException = null;
                for (OrderPayStatement orderPayStatementForException : orderPayStatementList) {
                    paymentWayForException = orderPayStatementForException.getPaymentWay();
                    if (OrderDictionaryEnum.OrderPayMethod.Balance
                            .getKey() == paymentWayForException.intValue()) {
                        balancePayFlag = true;
                    }
                    if (OrderDictionaryEnum.OrderPayMethod.Coupon.getKey() == paymentWayForException
                            .intValue()) {
                        couponPayFlag = true;
                    }
                    /*
                     * 删除预备金 if (OrderDictionaryEnum.OrderPayMethod.Reserve.getKey() ==
                     * paymentWayForException .intValue()) { reservePayFlag = true; }
                     */
                }
            }
            // 余额返还
            if (balancePayFlag) {
                try {
                    if (trantionListRemoteService != null) {
                        trantionRemoteResult =
                                trantionListRemoteService.orderTrationAccout(account,
                                        accountBalanceMoney == null ? 0
                                                : accountBalanceMoney.doubleValue(),
                                        orderCode, "交易内容",
                                        OrderDictionaryEnum.OrderPayFlag.Payment.getKey());
                    }
                } catch (MalformedURLException me) {
                    log.error("余额返还异常", me);
                    throw new ServiceException(ErrorCode.INNER_SINGLE_MONEY_ERROR,
                            "订单 " + orderCode + " 下单接口异常时，余额返还异常：" + me.getMessage());
                } catch (ClassNotFoundException ce) {
                    log.error("余额返还异常", ce);
                    throw new ServiceException(ErrorCode.INNER_SINGLE_MONEY_ERROR,
                            "订单 " + orderCode + " 下单接口异常时，余额返还异常：" + ce.getMessage());
                } catch (Exception ee) {
                    log.error("余额返还异常", ee);
                    throw new ServiceException(ErrorCode.INNER_SINGLE_MONEY_ERROR,
                            "订单 " + orderCode + " 下单接口异常时，余额返还异常：" + ee.getMessage());
                }
            }
            // 优惠券解冻
            if (couponPayFlag) {
                try {
                    if (couponRemoteService != null) {
                        userInfo = new UserInfoDO();
                        userInfo.setLoginId(om.getCustomerId().intValue());
                        couponRemoteService.changeCustomGrantToGive(userInfo, preferentialNo,
                                om.getCustomerId().longValue(), SysConstants.COUPON_UNFREEZE,
                                orderCode, preferentialGrantId);
                    } else {
                        throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                "订单 " + orderCode + " 获取产品系统优惠券相关接口失败！");
                    }

                } catch (MalformedURLException me) {
                    log.error("优惠券解冻异常", me);
                    throw new ServiceException(ErrorCode.INNER_SINGLE_COUPON_ERROR,
                            "订单 " + orderCode + " 下单接口异常时，优惠券解冻异常" + me.getMessage());
                } catch (ClassNotFoundException ce) {
                    log.error("优惠券解冻异常", ce);
                    throw new ServiceException(ErrorCode.INNER_SINGLE_COUPON_ERROR,
                            "订单 " + orderCode + " 下单接口异常时，优惠券解冻异常" + ce.getMessage());
                } catch (NumberFormatException ne) {
                    log.error("优惠券解冻异常", ne);
                    throw new ServiceException(ErrorCode.INNER_SINGLE_COUPON_ERROR,
                            "订单 " + orderCode + " 下单接口异常时，优惠券解冻异常" + ne.getMessage());
                } catch (Exception ee) {
                    log.error("优惠券解冻异常", ee);
                    throw new ServiceException(ErrorCode.INNER_SINGLE_COUPON_ERROR,
                            "订单 " + orderCode + " 下单接口异常时，优惠券解冻异常" + ee.getMessage());
                }

            }
            // 预备金返还
            /*
             * 删除预备金 if (reservePayFlag) { try { if (trantionListRemoteService != null) {
             * trantionRemoteResult = trantionListRemoteService.updateReserver(account,
             * accountBalanceMoney.doubleValue(), orderCode, "交易内容", 13, 1); } } catch
             * (MalformedURLException me) { log.error("预备金返还异常", me); throw new
             * ServiceException(ErrorCode.INNER_SINGLE_MONEY_ERROR, "订单 " + orderCode +
             * " 下单接口异常时，预备金返还异常：" + me.getMessage()); } catch (ClassNotFoundException ce) {
             * log.error("预备金返还异常", ce); throw new
             * ServiceException(ErrorCode.INNER_SINGLE_MONEY_ERROR, "订单 " + orderCode +
             * " 下单接口异常时，预备金返还异常：" + ce.getMessage()); } catch (Exception ee) { log.error("预备金返还异常",
             * ee); throw new ServiceException(ErrorCode.INNER_SINGLE_MONEY_ERROR, "订单 " + orderCode
             * + " 下单接口异常时，预备金返还异常：" + ee.getMessage()); }
             * 
             * }
             */
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                    "订单 " + orderCode + " 进行订单批量支付处理时发生异常！" + e.getMessage());

        }
        return result;
    }

    @SuppressWarnings("unused")
    private int corePay(OrderPayStatement orderPayStatement, Long loginId, Boolean couponstate)
            throws ServiceException {
        int result = 0;
        String comment = null;
        int trantionListRemoteResult = 0;
        // Long state =
        new Long(OrderDictionaryEnum.OrderPayState.Success.getKey());
        Date endDate = null;
        // Date now = new Date();
        int growScoreResult = 0;
        int growLevelResult = 0;
        String stockRemoteResult = null;
        UserInfoDO userInfo = null;

        // String operator = paymentVO.get;
        Long paymentWay = orderPayStatement.getPaymentWay();
        String account = orderPayStatement.getAccount();
        String orderCode = orderPayStatement.getOrderCode();
        BigDecimal orderMoney = orderPayStatement.getOrderMoney();
        String outsidePayStatementNo = orderPayStatement.getOutsidePayStatementNo();
        Long flag = orderPayStatement.getFlag();
        BigDecimal preferentialNo = orderPayStatement.getPreferentialNo();
        BigDecimal preferentialGrantId = orderPayStatement.getPreferentialGrantId();
        // String bankName =
        orderPayStatement.getBankName();
        // String platFormName =
        orderPayStatement.getPlatFormName();
        // String bankCode =
        orderPayStatement.getBankCode();
        // String platFormCode =
        orderPayStatement.getPlatFormCode();
        try {

            OrderMain om = orderMainDAO.selectByOrderCode(orderCode);
            if (null == om) {
                throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                        "订单号为" + orderCode + "的订单不存在！");
            }

            if (flag.intValue() == OrderDictionaryEnum.OrderPayFlag.Payment.getKey()) {// 付款
                if (om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Not_Pay
                        .getKey()) {// 前置条件：未付款
                    if (paymentWay.intValue() == OrderDictionaryEnum.OrderPayMethod.Balance
                            .getKey()) {
                        // 余额支付
                        if (trantionListRemoteService != null) {
                            trantionListRemoteResult = trantionListRemoteService.orderTrationAccout(
                                    account, (orderMoney.doubleValue()), orderCode, "交易内容",
                                    flag.intValue());
                        }
                        switch (trantionListRemoteResult) {
                            case 0:
                                throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                        "订单 " + orderCode + " 获取客户系统余额相关接口失败！");
                            case 2:
                                throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                        "订单 " + orderCode + " 账户号不存在！");
                            case 3:
                                throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                        "订单 " + orderCode + " 账户金额小于订单交易金额！");
                            case 4:
                                throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                        "订单 " + orderCode + " 订单号不能为空！");
                            case 5:
                                throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                        "订单 " + orderCode + " 交易内容不能为空！");
                        }
                        result = 1;
                        om.setAccountBalance(orderMoney);// 余额支付量
                    } /*
                       * 删除预备金 else if (paymentWay.intValue() ==
                       * OrderDictionaryEnum.OrderPayMethod.Reserve.getKey()) { // 预备金支付
                       * trantionListRemoteService.updateReserver(account, (Double)
                       * (orderMoney.doubleValue()), orderCode, "交易内容", 11, 1); }
                       */ else if (paymentWay.intValue() == OrderDictionaryEnum.OrderPayMethod.Coupon
                            .getKey() && !couponstate) {// 优惠券未支付成功
                        // couponGrant = new CouponGrant();
                        // 优惠券支付在下单时候就会进行冻结，在这需要对优惠券进行使用
                        if (couponRemoteService != null) {
                            userInfo = new UserInfoDO();
                            userInfo.setLoginId(loginId.intValue());
                            couponRemoteService.changeCustomGrantToGive(userInfo,
                                    preferentialNo.longValue(), om.getCustomerId().longValue(),
                                    SysConstants.COUPON_USED, orderCode,
                                    preferentialGrantId.longValue());
                            // couponRemoteService.couponUseing(preferentialNo.longValue(),
                            // om.getCustomerId()+"",
                            // SysConstants.HAVEUSE_COUPONSTATUS);
                        } else {
                            throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                    "订单 " + orderCode + " 获取产品系统优惠券相关接口失败！");
                        }
                        result = 1;
                    } else if (paymentWay.intValue() == OrderDictionaryEnum.OrderPayMethod.Bank
                            .getKey()) {
                        // 网银/信用卡支付
                        if (null == outsidePayStatementNo) {
                            throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                    "订单 " + orderCode + " 网银/信用卡支付失败！");
                        } else {
                            // 记录支付流水
                            // orderPayStatementDAO.insert(orderPayStatement);
                        }
                        result = 1;
                    } else if (paymentWay.intValue() == OrderDictionaryEnum.OrderPayMethod.Platform
                            .getKey()) {
                        // 第三方支付移
                        if (null == outsidePayStatementNo) {// 支付失败标志，否则为流水号
                            throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                    "订单 " + orderCode + " 第三方支付失败！");
                        } else {
                            // 记录支付流水
                            // orderPayStatementDAO.insert(orderPayStatement);
                        }
                        result = 1;
                    } else if (paymentWay.intValue() == OrderDictionaryEnum.OrderPayMethod.OnLine
                            .getKey()) {
                        // 在线支付移至前端B2B系统
                        if (null == outsidePayStatementNo) {// 支付失败标志，否则为流水号
                            throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                    "订单 " + orderCode + " 在线支付失败！");
                        }
                        result = 1;
                    } else {
                        throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                "订单 " + orderCode + " 支付方式不支持！");
                    }
                } else if (om.getOrderStatus()
                        .intValue() == OrderDictionaryEnum.Order_Status.Pay_Done.getKey()) {
                    result = 1;
                } else if (om.getOrderStatus()
                        .intValue() == OrderDictionaryEnum.Order_Status.Nopay_FinalMoney.getKey()) {
                    result = 1;
                } else {
                    throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                            "订单 " + orderCode + " 已支付或已撤单！");
                }
                comment = SysConstants.COMMENT_PAY;
            } else if (flag.intValue() == OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()) {// 退款
                // 退款条件限制
                if (om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Pay_Done
                        .getKey()) {// 前置条件：已完成
                    if (paymentWay.intValue() == OrderDictionaryEnum.OrderPayMethod.Balance
                            .getKey()) {
                        // 余额退回
                        try {
                            if (1 != trantionListRemoteService.orderTrationAccout(account,
                                    (orderMoney.doubleValue()), orderCode + "", "支付内容:退款到余额账户",
                                    flag.intValue())) {
                                throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                        "订单 " + orderCode + " 余额退回失败！");
                            }
                            result = 1;
                        } catch (Exception e) {
                            log.error("余额退回发生异常", e);
                            throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                    "订单 " + orderCode + " 余额退回失败！" + e.getMessage());
                        }
                        om.setAccountBalance(om.getAccountBalance().subtract(orderMoney));// 余额支付量
                    } else if (paymentWay.intValue() == OrderDictionaryEnum.OrderPayMethod.Coupon
                            .getKey()) {
                        // 优惠券退回
                        // if(1!=ip.payByPreferential(account, null,
                        // OrderDictionaryEnum.OrderPayFlag.Payment.getKey())){
                        // throw new ServiceException(0,"优惠券退回失败！");
                        // }
                        result = 1;
                    } else if (paymentWay.intValue() == OrderDictionaryEnum.OrderPayMethod.Platform
                            .getKey()) {
                        // 第三方退回
                        if (null == outsidePayStatementNo) {
                            throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                    "订单 " + orderCode + " 第三方退回失败！");
                        }
                        result = 1;
                    } else {
                        throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                                "订单 " + orderCode + " 退款方式不支持！");
                    }
                } else {
                    throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                            "订单 " + orderCode + " 订单此状态下不能退款！");
                }
            } else {
                throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                        "订单 " + orderCode + " 未知请求！");
            }
        } catch (Exception e) {
            log.error("支付核心处理异常", e);
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                    "订单 " + orderCode + " 订单支付核心处理异常");
        }
        return result;
    }

    @SuppressWarnings("unused")
    private int extOperationAfterPay(String orderCode, int flag, List<Double> orderMoneyList,
            String account, OrderMain om) {
        int result = 0;
        for (Double orderMoney : orderMoneyList) {
            try {
                result = this.updateUserExtInfo(flag, orderMoney, account, om);
            } catch (Exception e) {
                log.error(e);
                log.error("修改会员积分和等级失败", e);
            }

        }
        return result;
    }

    /*
     * private int batchHandleStock(String orderCode, int flag) throws ServiceException { int result
     * = 0; String operateStr = null; String stockRemoteResult = null; // 根据orderCode查询出所有的订单明细的列表
     * List<OrderItem> orderItemList = orderItemQryService.listOrderItems(orderCode); String SKUStr
     * = null; BigDecimal wareHouseId = null; Long wareHouseIdLongValue = null; Long count = null;
     * Map<String, Object> resultMap = null; //
     * 遍历orderItemMap中的仓库id，组装锁库存用的map，形如Map<SKUCode,Map<WareHouseId,Count>> Map<String, Map<Long,
     * Long>> lockStockMap = new HashMap<String, Map<Long, Long>>(); lockStockMap =
     * SettlementAndPayUtil.builderStockMap(orderItemList);
     * 
     * Map<Long,Long> wareHouse2CountMap = null;//new HashMap<Long,Long>(); //遍历前述操作得到的订单明细列表
     * for(OrderItem tempItem:orderItemList){ //取得SKU编码 SKUStr = tempItem.getCommoditySku();
     * //取得仓库id wareHouseId = tempItem.getWarehouseId(); wareHouseIdLongValue =
     * wareHouseId.longValue(); //取得商品数量 count = tempItem.getCommodityNumber(); //首先判断查询Map
     * lockStockMap中是否包含该SKU编码的键值对 if(lockStockMap.containsKey(SKUStr)){//如果存在该SKU编码的键值对
     * ，则要进一步判断仓库id到商品数量的Map数据 wareHouse2CountMap = lockStockMap.get(SKUStr);
     * //如果仓库id到商品数量的Map中不存在该仓库id，则需要加入列表，并从lockStockMap去掉该键值对重新载入新的
     * if(!wareHouse2CountMap.containsKey(wareHouseIdLongValue)){
     * wareHouse2CountMap.put(wareHouseIdLongValue,count); lockStockMap.remove(SKUStr);
     * lockStockMap.put(SKUStr, wareHouse2CountMap); }else{//如果存在，需要更新？？
     * 
     * } }else{//如果不存在该SKU编码的键值对，实例化仓库id到商品数量的Map并加入该仓库id //实例化某个SKU编码下的仓库id到商品数量的Map
     * wareHouse2CountMap = new HashMap<Long,Long>();
     * wareHouse2CountMap.put(wareHouseIdLongValue,count); lockStockMap.put(SKUStr,
     * wareHouse2CountMap); } }
     * 
     * try { stockRemoteService = (StockRemoteService)
     * RemoteTool.getRemote(SysConstants.PRODUCT_SYSCODE, SysConstants.STOCK_SERVICE); // 1是锁库存 if
     * (flag == 1) { operateStr = "锁库存"; resultMap =
     * stockRemoteService.batchLockStock(lockStockMap,orderCode); // 2是解锁 } else if (flag == 2) {
     * operateStr = "解锁库存"; resultMap = stockRemoteService.batchUnLockStock(lockStockMap); } } catch
     * (Exception e) { log.error(operateStr+"-"+"调用产品系统的库存接口"+"-"+operateStr+"-"+"时发生异常",e); throw
     * new ServiceException(ErrorCode.INNER_PAYMENT_ERROR, "订单 " + orderCode + operateStr+"-"+
     * " 调用产品系统的库存接口时发生异常！" + e.getMessage()); } Object object = null; List<ProductStock>
     * cannotLockList = null; if (resultMap != null && resultMap.size() > 0) { for (String key :
     * resultMap.keySet()) { if ("success".equalsIgnoreCase(key)) { stockRemoteResult = "success"; }
     * if ("fail".equalsIgnoreCase(key)) { stockRemoteResult = "fail"; object = resultMap.get(key);
     * if (object instanceof java.util.List) { cannotLockList = (List<ProductStock>)
     * resultMap.get(key); for(ProductStock proStock : cannotLockList){
     * log.info(operateStr+"-"+"调用产品系统的库存接口返回结果集合打印开始：");
     * log.info("仓库id："+proStock.getWarehouseId()); log.info("sku编码："+proStock.getSkuAttValue());
     * log.info(operateStr+"-"+"调用产品系统的库存接口返回结果集合打印结束："); } } if(object instanceof
     * java.lang.String){ log.info(operateStr+"-"+"调用产品系统的库存接口返回结果字符串为："+object); } } } if
     * ("success".equalsIgnoreCase(stockRemoteResult)) { log.info("############# 订单号：" + orderCode +
     * " 通过产品系统库存接口批量"+"-"+operateStr+"-"+"成功"); result = 1; } else { log.info("############# 订单号："
     * + orderCode + " 通过产品系统接口接口批量"+"-"+operateStr+"-"+"失败"); result = -1; } } return result; }
     */

    @SuppressWarnings("unused")
    private int updateUserExtInfo(int flag, Double orderMoney, String account, OrderMain om)
            throws ServiceException {
        int result = 0;
        int growScoreResult = 0;
        int growLevelResult = 0;
        Map<String, String> map = new HashMap<String, String>();
        map.put(SysConstants.PAYABLE_AMOUNT_SYMBOL, String.valueOf(om.getCommoditySum()));
        try {
            switch (flag) {
                // 1为付款后积分和等级的增加
                case 1:

                    // START 更新用户积分
                    /*
                     * 在同步时代订单时才赠送积分try{ String loginAccout = om.getCustomerAccount(); Integer type
                     * = orderMainDAO.selectLoginType(loginAccout);//获取用户类型 BigDecimal pv = new
                     * BigDecimal(om.getOrderPv()); Integer i =
                     * userGrowingService.updateScoreInfoForTimeMember(Integer.parseInt(SysConstants
                     * .COMMON_ADD_TYPE), loginAccout, pv); if(i != 1){ log.error("修改用户pv积分出错！i ="
                     * +i); } }catch(Exception e){ log.error("订单:"+om.getOrderCode()+"更新用户pv积分失败!");
                     * }
                     */
                    // END 更新用户积分
                    // 0 减 1加
                    /*
                     * growScoreResult =
                     * userGrowingService.updateUserScoreInfo(SysConstants.RULE_AFTER_PAY,
                     * Integer.parseInt(SysConstants.COMMON_ADD_TYPE), account, map);
                     */
                    // 0 减 1加
                    /*
                     * 删除客户等级 growLevelResult =
                     * userGrowingService.byFeeUpdateUserLevel(orderMoney.doubleValue(), account,
                     * Integer.parseInt(SysConstants.COMMON_ADD_TYPE));
                     */
                    break;
                // 2为退款后积分和等级的减少
                case 2:
                    // START 更新用户积分
                    /*
                     * 在同步时代订单时才赠送积分 try{ String loginAccout = om.getCustomerAccount(); Integer type
                     * = orderMainDAO.selectLoginType(loginAccout);//获取用户类型 BigDecimal pv = new
                     * BigDecimal(om.getOrderPv()); Integer i =
                     * userGrowingService.updateScoreInfoForTimeMember(Integer.parseInt(SysConstants
                     * .COMMON_MINUS_TYPE), loginAccout, pv); if(i != 1){ log.error("修改用户pv积分出错！i ="
                     * +i); } }catch(Exception e){ log.error("订单:"+om.getOrderCode()+"更新用户pv积分失败!");
                     * }
                     */
                    // END 更新用户积分
                    // 0 减 1加
                    /*
                     * growScoreResult =
                     * userGrowingService.updateUserScoreInfo(SysConstants.RULE_AFTER_PAY,
                     * Integer.parseInt(SysConstants.COMMON_MINUS_TYPE), account, map);
                     */
                    // 0 减 1加
                    /*
                     * 删除客户等级 growLevelResult =
                     * userGrowingService.byFeeUpdateUserLevel(orderMoney.doubleValue(), account,
                     * Integer.parseInt(SysConstants.COMMON_MINUS_TYPE));
                     */
                    break;
                default:
                    break;
            }
            result = 1;
        } catch (Exception e) {
            log.error(e);
            log.error("修改客户积分时发生异常", e);
            throw new ServiceException(0, "修改客户积分发生异常", e);
        }
        return result;
    }

    @Override
    public int savePayStatement(List<OrderPayStatement> orderPayStatementList)
            throws ServiceException {
        int result = 0;
        String orderCode = orderPayStatementList.get(0).getOrderCode();
        try {
            orderPayStatementDAO.insertList(orderPayStatementList);
            result = 1;

        } catch (Exception e) {
            log.error("批量保存支付流水记", e);
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR,
                    "订单号：" + orderCode + "批量保存支付流水记" + e.getMessage());
        }
        return result;
    }

    @SuppressWarnings("unused")
    private void update() throws ServiceException {
        OrderPayStatement record = null;
        OrderPayStatementExample example = new OrderPayStatementExample();
        // Criteria criteria =
        example.createCriteria();
        try {
            orderPayStatementDAO.updateByExample(record, example);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR,
                    "修改支付流水失败" + e.getMessage());
        }

    }

    @SuppressWarnings("unused")
    private OrderPayStatement transVO2Entity(PaymentVO vo) {
        OrderPayStatement result = null;
        BeanUtils.copyProperties(vo, result);
        return result;
    }

    @Override
    public BigDecimal getPayPreferentialNoByOrderCode(String orderCode) throws ServiceException {
        try {
            return orderPayStatementDAO.getPayPreferentialNoByOrderCode(orderCode);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_ORDER_INVOICE_DETAIL_ERROR,
                    "查询订单优惠券编号异常：" + e.getMessage());
        }
    }

    @Override
    public List<OrderPreferential> getPreferentialByOrderCode(String orderCode, Long type)
            throws ServiceException {
        try {
            OrderPreferential op = new OrderPreferential();
            op.setOrderCode(orderCode);
            op.setOrderPreferentialType(type);
            return orderPreferentialDAO.getPreferentialNoByOrderCode(op);
        } catch (Exception e) {
            log.error("查询订单优惠券编号异常", e);
            throw new ServiceException(0, "查询订单优惠券编号异常");
        }
    }

    @Override
    public List<OrderPayStatement> getPayPreferentialByOrderCode(String orderCode)
            throws ServiceException {
        try {
            return orderPayStatementDAO.getPayPreferentialByOrderCode(orderCode);
        } catch (Exception e) {
            log.error("查询订单优惠券编号异常", e);
            throw new ServiceException(0, "查询订单优惠券编号异常");
        }
    }

    @Override
    public Integer querySaleReportCount(Map<String, String> map) throws ServiceException {
        try {
            return orderPayStatementDAO.querySaleReportCount(map);
        } catch (SQLException e) {
            log.error("查询销售统计表数据总行数异常", e);
            throw new ServiceException(0, "查询销售统计表数据总行数异常");
        }
    }

    @Override
    public List<Map<String, Object>> querySaleReport(Map<String, String> map)
            throws ServiceException {
        try {
            return orderPayStatementDAO.querySaleReport(map);
        } catch (SQLException e) {
            log.error("查询销售统计表数据异常", e);
            throw new ServiceException(0, "查询销售统计表数据异常");
        }
    }

    @Override
    public String saleReportExportExcel(Map<String, String> map, Integer userId)
            throws ServiceException {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            result = orderPayStatementDAO.saleReportExportExcel(map);
        } catch (SQLException e) {
            log.error("导出销售统计表数据异常", e);
            throw new ServiceException(0, "导出销售统计表数据异常");
        }
        List<String> lable = new ArrayList<String>();
        lable.add("订单号");
        /* lable.add("渠道"); */
        lable.add("客户账号");
        lable.add("订单状态");
        lable.add("订单金额");
        lable.add("实付金额");
        lable.add("交易方式");
        lable.add("交易平台");
        lable.add("交易金额");
        lable.add("支付日期");
        List<String> key = new ArrayList<String>();
        key.add("ORDER_CODE");
        /* key.add("ORDER_CHANNEL"); */
        key.add("LOGIN_ACCOUNT");
        key.add("FLAG");
        key.add("AMOUNT_PAYABLE");
        key.add("ORDER_MONEY");
        key.add("ORDER_DICTIONARY_VALUE");
        key.add("PLATFORM_NAME");
        key.add("ACT_MONEY");
        key.add("CREATE_DATE");
        return exportFile(userId.intValue(), "销售统计表", lable, key, result);
    }

    @Override
    public Long queryCouponGrantByCounponGrantId(Long couponGrantId) throws ServiceException {
        try {
            return orderPreferentialDAO.queryCouponGrantByCounponGrantId(couponGrantId);
        } catch (SQLException e) {
            throw new ServiceException(0, "查询优惠券状态异常!");
        }
    };

    /**
     * 获取订单支付信息
     * 
     * @param orderCode
     * @return
     * @throws ServiceException
     */
    @Override
    public Map<String, BigDecimal> getOrderPayInfo(String orderCode) throws ServiceException {
        try {
            return orderPayStatementDAO.getOrderPayInfo(orderCode);
        } catch (SQLException e) {
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR,
                    "修改支付流水失败" + e.getMessage());
        }
    }

    @Override
    public BigDecimal getOrderPayByOrderCode(String orderCode) throws ServiceException {
        try {
            return orderPayStatementDAO.getOrderPayByOrderCode(orderCode);
        } catch (SQLException e) {
            log.error("获取支付总额发生错误", e);
            throw new ServiceException(ErrorCode.INNER_PAYMENT_CALC_ERROR,
                    "获取支付总额发生错误：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int lockStock(PaymentVO paymentVO) throws ServiceException {
        // 初始化返回结果
        int result = 0;
        // 初始化操作库存方法的返回结果
        int batchHandleStockResult = 0;
        String orderCode = paymentVO.getOrderCode();

        // 已锁库存
        Long stockLockOrderStatus = new Long(OrderDictionaryEnum.Order_Status.Stock_Lock.getKey());

        // 首先通过orderCode查询得到订单对象
        OrderMain om = null;
        try {
            om = orderMainDAO.selectByOrderCode(orderCode);
        } catch (Exception e1) {
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, "订单不存在");
        }
        if (null == om) {
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, orderCode + "订单不存在");
        }
        if (om.getOrderStatus() != 2) {
            throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR,
                    orderCode + "订单状态是" + om.getOrderStatus() + "不能锁库存");
        }
        // 根据orderCode查询出所有的订单明细的列表
        List<OrderItem> orderItemList = orderItemQryService.listOrderItems(orderCode);
        // 库存接口,锁操作类型
        int lockFlag = OrderInterFaceOperateTypeEnum.StockHandleType.batchLock.getKey();
        // 库存接口,解锁操作类型
        int unLockFlag = OrderInterFaceOperateTypeEnum.StockHandleType.batchUnLock.getKey();

        try {
            batchHandleStockResult = settlementAndPayUtil.batchHandleStock(orderCode, orderItemList,
                    lockFlag, om.getOrderChannel());
            if (batchHandleStockResult == -1) {
                throw new ServiceException(ErrorCode.INTER_PAYMENT_CALC_ERROR,
                        "订单 " + orderCode + " 调用库存接口成功，但返回结果为失败");
            }
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_PAYMENT_CALC_ERROR,
                    "订单 " + orderCode + " 调用库存接口失败：" + e.getMessage());
        }
        try {
            if (batchHandleStockResult == 1) {
                OrderMain omLock = new OrderMain();
                String comment = "您的订单" + orderCode + "已经锁库存";
                omLock.setOrderCode(orderCode);
                omLock.setOrderStatus(stockLockOrderStatus);
                orderMainDAO.updateByOrderCode(omLock);
                // 记录订单操作流水
                this.orderOperateStatement(paymentVO,
                        new Long(OrderDictionaryEnum.OrderOperateType.LockStock.getKey()),
                        stockLockOrderStatus, null, comment);
                result = 1;
                riskManagementService.addRiskJudgeTask(om.getCommerceId(), om.getCommerceName(),
                        orderCode);
            }
        } catch (Exception e) {
            // 如果前面锁库存已经成功，则需要将其解锁
            if (batchHandleStockResult == 1) {
                try {
                    settlementAndPayUtil.batchHandleStock(orderCode, orderItemList, unLockFlag,
                            om.getOrderChannel());
                } catch (ServiceException se) {
                    log.error("需要解锁库存时发生异常", e);
                    throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                            "订单 " + orderCode + " 支付异常需要解锁库存时发生异常：" + se.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * 生成订单操作流水
     * 
     * @param paymentVO
     * @param nowOperateType
     * @param nowOrderStatus
     * @param orderMoneyAll
     * @param comment
     * @param orderPayStatementTempList
     */
    private void orderOperateStatement(PaymentVO paymentVO, Long nowOperateType,
            Long nowOrderStatus, BigDecimal orderMoneyAll, String comment) throws ServiceException {
        // 获取支付对象列表
        List<OrderPayStatement> orderPayStatementList = paymentVO.getOrderPayStatementList();
        // 获取客户账户
        String account = paymentVO.getAccount();
        // 获取订单编码
        String orderCode = paymentVO.getOrderCode();
        Date nowt = new Date();// 操作时间
        OrderOperateStatement oosPrevious = new OrderOperateStatement();
        try {
            if (orderPayStatementList != null && orderPayStatementList.size() > 0) {
                // 找到该订单号最近的记录操作流水
                oosPrevious = orderOperateStatementDAO.selectNewest(orderCode);
                if (null == orderMoneyAll) { // 记录锁库存时操作流水时，orderMoneyAll传入为空
                    orderMoneyAll = oosPrevious.getNowOrderSum();
                }
                OrderOperateStatement oosNow = new OrderOperateStatement();
                // 如果最近一次操作是支付操作，则只需要修改当前操作时间即可
                if (oosPrevious != null) {
                    // comment = "您的订单"+orderCode+"已经支付成功，正在安排配送；";
                    if (OrderDictionaryEnum.OrderOperateType.Pay.getKey() == oosPrevious
                            .getNowOperateType().intValue()) {
                        BeanUtils.copyProperties(oosPrevious, oosNow);
                        oosNow.setNowOperateDate(new Date());
                    } else {
                        oosNow.setNowOperateDate(nowt);
                        oosNow.setNowOperateType(nowOperateType);
                        oosNow.setNowOperator(account);
                        oosNow.setNowOrderStatus(nowOrderStatus);
                        oosNow.setNowOrderSum(orderMoneyAll);
                        oosNow.setOrderCode(orderCode);
                        oosNow.setPreviousOperateDate(oosPrevious.getNowOperateDate());
                        oosNow.setPreviousOperateType(oosPrevious.getNowOperateType());
                        oosNow.setPreviousOperator(oosPrevious.getNowOperator());
                        oosNow.setPreviousOrderStatus(oosPrevious.getNowOrderStatus());
                        oosNow.setPreviousOrderSum(oosPrevious.getNowOrderSum());
                    }
                }
                oosNow.setOperateInfo(comment);
                oosNow.setNowOrderStatus(nowOrderStatus);
                orderOperateStatementDAO.insert(oosNow);
            } else {
                // 初始化最近的操作流水记录sdsdfsdfsdf

                // 找到该订单号最近的记录操作流水
                oosPrevious = orderOperateStatementDAO.selectNewest(orderCode);
                // comment = "您的订单"+orderCode+"已经支付成功，正在安排配送；";
                oosPrevious.setNowOperateDate(nowt);
                oosPrevious.setOperateInfo(comment);
                oosPrevious.setNowOrderStatus(nowOrderStatus);
                orderOperateStatementDAO.updateByPrimaryKey(oosPrevious);
            }
        } catch (Exception e) {
            throw new ServiceException(0, "记录订单操作信息失败!" + e);
        }
    }

    /**
     * 是否允许补单
     * 
     * @param map
     * @return
     * @throws ServiceException
     */
    @Override
    public Boolean checkIsAdditional(Map map) throws ServiceException {
        try {
            return orderPayStatementDAO.checkIsAdditional(map);
        } catch (SQLException e) {
            throw new ServiceException(0, "是否允许补单发生异常!" + e);
        }
    }
}
