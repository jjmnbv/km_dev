package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.kmzyc.b2b.model.QueryResult;
import com.kmzyc.b2b.service.CloseKMTPayService;
import com.kmzyc.b2b.service.QryOrderOnLineService;
import com.kmzyc.b2b.service.RefundOrderService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.framework.constants.RefundResultCode;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.promotion.remote.service.PresellInfoRemoteService;
import com.kmzyc.user.remote.service.UserGrowingService;
import com.pltfm.app.dao.OrderHandleDAO;
import com.pltfm.app.dao.OrderItemDAO;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.dao.OrderOperateStatementDAO;
import com.pltfm.app.dao.OrderPayStatementDAO;
import com.pltfm.app.dao.OrderPreferentialDAO;
import com.pltfm.app.dao.OrderRiskRosterDAO;
import com.pltfm.app.dao.OrderSyncDAO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.entities.OrderHandle;
import com.pltfm.app.entities.OrderHandleExample;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderMainExt;
import com.pltfm.app.entities.OrderOperateStatement;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.entities.OrderPayStatementExample;
import com.pltfm.app.entities.OrderPreferential;
import com.pltfm.app.entities.OrderRiskBackList;
import com.pltfm.app.entities.OrderRiskCondition;
import com.pltfm.app.entities.OrderSync;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.service.OrderPayStatementService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.service.OrderRiskRosterService;
import com.pltfm.app.service.OrderRiskSettingService;
import com.pltfm.app.service.RefundRequestService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.OrderInterFaceOperateTypeEnum;
import com.pltfm.app.util.OrderRiskKey;
import com.pltfm.app.util.SerializeUtil;
import com.pltfm.app.util.SettlementAndPayUtil;
import com.pltfm.app.util.SysConstants;
import com.pltfm.app.vobject.OrderMainVo;
import com.pltfm.app.vobject.PaymentVO;
import com.pltfm.app.vobject.RefundRequest;
import com.pltfm.sys.util.ErrorCode;

import net.sf.json.JSONObject;
import redis.clients.jedis.JedisCluster;


@Service("orderOperateStatementService")
@SuppressWarnings({"unchecked", "unused"})
public class OrderOperateStatementServiceImpl extends BaseService
        implements OrderOperateStatementService {
    private final static Logger logger = Logger.getLogger(OrderOperateStatementServiceImpl.class);

    @Resource
    OrderMainDAO orderMainDAO;
    @Resource
    OrderOperateStatementDAO orderOperateStatementDAO;
    @Resource
    OrderHandleDAO orderHandleDAO;
    @Resource
    OrderQryService orderQryService;
    @Resource
    OrderPayStatementService orderPayStatementService;
    @Resource
    OrderItemQryService orderItemQryService;
    @Resource
    RefundRequestService refundRequestService;
    // 客户系统的积分和等级的服务类
    @Resource
    private UserGrowingService userGrowingService;
    @Resource
    OrderPreferentialDAO orderPreferentialDAO;
    @Resource
    OrderPayStatementDAO orderPayStatementDAO;
    @Resource
    private OrderSyncDAO orderSyncDAO;
    @Resource
    private OrderItemDAO orderItemDAO;

    @Resource
    private OrderRiskSettingService orderRiskSettingService;

    @Resource
    private OrderRiskRosterDAO OrderRiskRosterDAO;
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;
    @Resource
    private JmsTemplate jmsTemplate;
    // @Resource(name = "orderUserSource")
    // private Destination destination;

    @Resource
    private OrderRiskRosterService orderRiskRosterService;

    @Resource
    private QryOrderOnLineService qryOrderOnLineService;

    @Resource
    private RefundOrderService refundOrderService;

    // 产品系统的优惠接口服务类
    @Resource
    private CouponRemoteService couponRemoteService;

    @Autowired
    private SettlementAndPayUtil settlementAndPayUtil;

    // 取消康美通为支付的订单
    @Autowired
    private CloseKMTPayService closeKMTPayService;
    @Resource
    private PresellInfoRemoteService presellInfoRemoteService;

    /**
     * 取消前补单
     * 
     * @return
     */
    @Override
    public boolean additionalBeforeCancle(String orderCode, Long status, QueryResult qr)
            throws ServiceException {
        boolean result = false;
        try {
            OrderMainVo om;
            OrderPayStatement ready;
            if (status.intValue() == OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()
                    && null != (om = orderMainDAO.selectByOrderCode(orderCode))
                    && om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Not_Pay
                            .getKey()
                    && null != (ready =
                            orderPayStatementDAO.queryOrderTHDReadyPayStatement(orderCode))) {

                if (null != qr && "SUCCESS".equals(qr.getRb_PayStatus())) {
                    OrderPayStatement ops = (OrderPayStatement) ready.clone();
                    Date date = new Date();
                    ops.setCreateDate(date);
                    ops.setEndDate(date);
                    ops.setState((long) OrderDictionaryEnum.OrderPayState.Success.getKey());
                    ops.setOutsidePayStatementNo(qr.getR2_TrxId());
                    if (orderPayStatementDAO.insert(ops) > 0) {
                        try {
                            OrderOperateStatement operate = new OrderOperateStatement();
                            operate.setNowOperateDate(date);
                            operate.setNowOperateType(
                                    (long) OrderDictionaryEnum.OrderOperateType.Pay.getKey());
                            operate.setNowOperator(SysConstants.SYS);
                            operate.setNowOrderStatus(
                                    (long) OrderDictionaryEnum.Order_Status.Pay_Done.getKey());
                            operate.setNowOrderSum(ops.getOrderMoney());
                            operate.setOrderCode(ops.getOrderCode());
                            operate.setOperateInfo("您的订单" + ops.getOrderCode() + "已经支付成功，正在安排配送；");
                            orderOperateStatementDAO.insert(operate);
                        } catch (Exception e) {
                            logger.error(e);
                        }
                    }
                    result = true;
                }
            }
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int updateOrderMain(OrderMain om) throws ServiceException {
        try {
            orderMainDAO.updateByPrimaryKey(om);
            return 1;
        } catch (SQLException e) {
            logger.error("更新订单发生异常", e);
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                    "更新订单失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int updateOrderMainSelective(OrderMain om) throws ServiceException {
        try {
            orderMainDAO.updateByPrimaryKeySelective(om);
            return 1;
        } catch (SQLException e) {
            logger.error("更新订单发生异常", e);
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                    "更新订单失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int save(String orderCode, Long orderItemId, Long status, String operator, Date date,
            Long type, BigDecimal orderSum, String info) throws ServiceException {
        try {
            OrderOperateStatement oldRecord = orderOperateStatementDAO.selectNewest(orderCode);
            orderOperateStatementDAO.insertSelective(newOrderOperate(oldRecord, orderCode,
                    orderItemId, status, operator, date, type, orderSum, info));
            return 1;
        } catch (SQLException e) {
            logger.error("记录订单流水时发生异常", e);
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                    "记录订单流水时发生异常：" + e.getMessage());
        }
    }

    @Override
    public OrderOperateStatement newOrderOperate(OrderOperateStatement oldRecord, String orderCode,
            Long orderItemId, Long status, String operator, Date date, Long type,
            BigDecimal orderSum, String info) {
        OrderOperateStatement newRecord = new OrderOperateStatement();
        if (null != oldRecord) {
            newRecord.setPreviousOperateDate(oldRecord.getNowOperateDate());
            newRecord.setPreviousOperateType(oldRecord.getNowOperateType());
            newRecord.setPreviousOperator(oldRecord.getNowOperator());
            newRecord.setPreviousOrderStatus(oldRecord.getNowOrderStatus());
            newRecord.setPreviousOrderSum(oldRecord.getNowOrderSum());
        }
        newRecord.setNowOperateDate(new Date());
        newRecord.setNowOperateType(type);
        newRecord.setNowOperator(operator);
        newRecord.setNowOrderStatus(status);
        newRecord.setNowOrderSum(orderSum);
        newRecord.setOperateInfo(info);
        newRecord.setOrderCode(orderCode);
        newRecord.setOrderItemId(orderItemId);
        return newRecord;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public Boolean InsertOperate(OrderOperateStatement oost) throws ServiceException {
        try {
            // 根据oost实体中的orderId，先查找下是否存在记录，如有记录，则取最近一条记录
            OrderOperateStatement oosBef =
                    orderOperateStatementDAO.selectNewest(oost.getOrderCode());
            // 组装OrderOperateStatement操作流水
            oost.setPreviousOperateDate(oosBef.getNowOperateDate());
            oost.setPreviousOperator(oosBef.getNowOperator());
            oost.setPreviousOperateType(oosBef.getNowOperateType());
            oost.setPreviousOrderStatus(oosBef.getNowOrderStatus());
            oost.setPreviousOrderSum(oosBef.getNowOrderSum());
            oost.setNowOrderSum(oosBef.getNowOrderSum());
            Long result = orderOperateStatementDAO.insert(oost);
            return result > 0;
        } catch (Exception e) {
            logger.error("记录订单流水时发生异常", e);
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                    "记录订单流水时发生异常：" + e.getMessage());
        }
    }

    /**
     * 批量处理取消支付宝订单
     * 
     * @param rrList 回调实体集合
     * @param refundNo 退款批次号
     * @return
     * @throws ServiceException
     */
    @Override
    public void batchRefundZFB(List<RefundRequest> rrList, String refundNo)
            throws ServiceException {
        if (rrList != null && 0 != rrList.size()) {
            for (RefundRequest rr : rrList) {
                try {
                    alterRefund(rr, refundNo);

                } catch (Exception e) {
                    logger.error("取消订单修改回调状态发生异常,退款请求ID=" + rr.getRrid(), e);
                }
            }
        }
    }

    /**
     * 修改回调状态
     * 
     * @param rr 回调实体
     * @param refundNo 退款批次号
     * @return
     * @throws ServiceException
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    private void alterRefund(RefundRequest rr, String refundNo) throws ServiceException {
        rr.setRefundNo(refundNo);
        refundRequestService.updateFinishRefundRequest(rr);
        Date d = new Date();
        try {
            // 记录支付流水
            orderPayStatementService.save(
                    (long) OrderDictionaryEnum.OrderPayMethod.Platform.getKey(),
                    (long) OrderDictionaryEnum.OrderPayState.Success.getKey(), rr.getMenberName(),
                    rr.getOrderCode(), rr.getRefurnMoney().negate(), d, d, rr.getOutBatchNo(),
                    (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null, null,
                    OrderDictionaryEnum.PlatformCode.alipay.getValue(), null,
                    String.valueOf(OrderDictionaryEnum.PlatformCode.alipay.getKey()));
        } catch (Exception e) {
            logger.error("记录支付流水异常", e);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int changeOrderStatus(String operator, String orderCode, Long status, BigDecimal no)
            throws ServiceException {
        String platFormCode = null;
        String platFormName = null;
        String comment;
        Long operateType;
        Date now = new Date();
        OrderMain om;
        try {
            om = orderMainDAO.selectByOrderCode(orderCode);
            if (null == om) {
                return -5;
            }
        } catch (Exception e) {
            logger.error("查询订单发生异常", e);
            return -5;
        }
        Long oldStatus = om.getOrderStatus();
        if (status.intValue() == OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()) {// 取消订单
            return this.cancelOrder(om, oldStatus, operator);


        } else if (status.intValue() == OrderDictionaryEnum.Order_Status.Stock_Done.getKey()) {// 出库
            if (om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Settle_Done
                    .getKey()) {
                operateType = (long) OrderDictionaryEnum.OrderOperateType.Stock_Out.getKey();
                comment = "您的订单中的商品已经出库,请等待发货!";
                om.setStockOutId(no);// 出库单号
                om.setOrderStatus(status);
                try {
                    updateOrderMain(om);
                    // 记录操作流水
                    save(orderCode, null, om.getOrderStatus(), operator, now, operateType,
                            om.getAmountPayable(), comment);
                } catch (Exception e) {
                    logger.error("修改订单" + orderCode + "状态为出库状态失败!");
                    return -2;
                }
                return 1;
            } else {
                return -2;
            }
        } else if (status.intValue() == OrderDictionaryEnum.Order_Status.Ship_Done.getKey()) {// 配送
            if (om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Stock_Done
                    .getKey()) {
                operateType = (long) OrderDictionaryEnum.OrderOperateType.Ship.getKey();
                comment = "您的订单中的商品已经发货,请在收到商品后确认收货!";
                try {
                    om.setDistributionId(no);// 配送单号
                    om.setOrderStatus(status);
                    updateOrderMain(om);
                    // 记录操作流水
                    save(orderCode, null, om.getOrderStatus(), operator, now, operateType,
                            om.getAmountPayable(), comment);
                    return 1;
                } catch (Exception e) {
                    logger.error("修改订单" + orderCode + "状态为配送状态失败!");
                    return -2;
                }
            } else {
                return -2;
            }

        } else if (status.intValue() == OrderDictionaryEnum.Order_Status.Order_Done.getKey()
                && om.getOrderStatus() != OrderDictionaryEnum.Order_Status.Order_Done.getKey()) {// 订单完成后调用接口修改积分、消费额

            return this.finshOrder(om, operator);


        } else {
            // 未知订单状态
            return -1;
        }

    }

    // 取消订单退款
    private void paidMoneyReturns(String operator, String orderCode, String comment, Date now,
            OrderMain om, Long oldStatus, BigDecimal totalMoneyPaid, BigDecimal couponMoneyPaid,
            List<OrderPayStatement> preferentialList, BigDecimal balanceMoneyPaid,
            List<OrderPayStatement> thirdPaymentList/* 删除预备金, BigDecimal reserveFundPaid */)
            throws ServiceException {

        save(orderCode, null, om.getOrderStatus(), operator, new Date(now.getTime() + 10000),
                (long) OrderDictionaryEnum.OrderOperateType.Pay.getKey(), totalMoneyPaid, comment);
        // 退回第三方准备
        if (thirdPaymentList != null && thirdPaymentList.size() > 0) {
            for (OrderPayStatement payStatement : thirdPaymentList) {
                tirdpaidMoneyReturn(operator, orderCode, comment, now, om, payStatement, oldStatus);
            }
        }

        // 退回余额账户
        if (0 != balanceMoneyPaid.compareTo(BigDecimal.ZERO)) {
            int payResult = orderPayStatementService.pay(operator,
                    (long) OrderAlterDictionaryEnum.OrderReturnMethod.Balance.getKey(),
                    om.getCustomerAccount(), orderCode, balanceMoneyPaid, null,
                    (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null, null,
                    oldStatus, (long) OrderDictionaryEnum.OrderPayState.Success.getKey(), null,
                    null);
            if (1 != payResult) {
                throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR, "订单退还失败");
            }
        }

        // }
        /* 优惠券退回 */
        if (0 != couponMoneyPaid.compareTo(BigDecimal.ZERO)) {
            try {
                for (OrderPayStatement op : preferentialList) {
                    orderPayStatementService.pay(operator,
                            (long) OrderAlterDictionaryEnum.OrderReturnMethod.Coupon.getKey(),
                            om.getCustomerAccount(), orderCode, couponMoneyPaid, null,
                            (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(),
                            op.getPreferentialNo(), op.getPreferentialGrantId(), oldStatus,
                            (long) OrderDictionaryEnum.OrderPayState.Success.getKey(), null, null);
                }
            } catch (Exception e1) {
                logger.error("优惠券退回发生异常", e1);
            }
        }


    }

    // 第三方支付退款
    private void tirdpaidMoneyReturn(String operator, String orderCode, String comment, Date now,
            OrderMain om, OrderPayStatement platformop, Long oldStatus) throws ServiceException {
        // 退回第三方准备

        if (0 != platformop.getOrderMoney().compareTo(BigDecimal.ZERO)) {
            // 如果是支付宝支付，则要退回到余额
            if ((OrderDictionaryEnum.PlatformCode.alipay.getKey() + "")
                    .equals(platformop.getPlatFormCode())) {
                try {
                    // 记录一条支付宝退款流水，当流水已存在则更新金额
                    RefundRequest rr = new RefundRequest();
                    rr.setOrderCode(orderCode);
                    rr.setChannel(om.getOrderChannel());
                    rr.setSellerType(null == om.getCommerceId() ? 1 : 2);
                    rr.setSellerShop(om.getCommerceName());
                    rr.setPlatformCode(platformop.getPlatFormCode());
                    rr.setOutBatchNo(platformop.getOutsidePayStatementNo());
                    rr.setMenberName(om.getCustomerAccount());
                    rr.setRefurnMoney(platformop.getOrderMoney());
                    rr.setStatus(0);
                    rr.setRefundType(1);
                    refundRequestService.addRefundRequest(rr);
                } catch (Exception e1) {
                    logger.error("添加支付宝退款流水发生异常", e1);
                }
            } else {
                // 第三方退回，实际的退款动作通过调用B2B的相关接口完成，暂不支持支付宝的原路返回
                Integer rechargeOrOrderFlag = 2;

                if (!StringUtils.isEmpty(platformop.getYsFlage())) {
                    // 预售
                    // 第三方准备，没有实际退还动作，只记录相关的操作流水和支付流水
                    int rs = orderPayStatementService.pay(operator,
                            (long) OrderAlterDictionaryEnum.OrderReturnMethod.Platform.getKey(),
                            om.getCustomerAccount(), orderCode, platformop.getOrderMoney(), null,
                            (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null, null,
                            oldStatus, (long) OrderDictionaryEnum.OrderPayState.Ready.getKey(),
                            platformop.getPlatFormCode(), platformop.getPlatFormName(),
                            platformop.getYsFlage());
                    if (1 != rs) {
                        throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                                "订单退还失败");
                    }
                } else {
                    // 第三方准备，没有实际退还动作，只记录相关的操作流水和支付流水
                    int rs = orderPayStatementService.pay(operator,
                            (long) OrderAlterDictionaryEnum.OrderReturnMethod.Platform.getKey(),
                            om.getCustomerAccount(), orderCode, platformop.getOrderMoney(), null,
                            (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null, null,
                            oldStatus, (long) OrderDictionaryEnum.OrderPayState.Ready.getKey(),
                            platformop.getPlatFormCode(), platformop.getPlatFormName());
                    if (1 != rs) {
                        throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                                "订单退还失败");
                    }
                }

                ReturnResult returnResult;
                try {
                    // 预售退款
                    if (!StringUtils.isEmpty(platformop.getYsFlage())) {
                        returnResult = refundOrderService.refundOrderForYs(om.getOrderCode(),
                                rechargeOrOrderFlag, platformop.getOrderMoney().abs(),
                                om.getOrderCode(), platformop.getYsFlage());
                    } else {
                        // 非预售退款
                        returnResult = refundOrderService.refundOrder(om.getOrderCode(),
                                rechargeOrOrderFlag, platformop.getOrderMoney().abs(),
                                om.getOrderCode());
                    }

                    logger.info("订单" + om.getOrderCode() + "退款结果returnResult="
                            + returnResult.getMessage());

                    if (RefundResultCode.UNSUPORT_BANK_REFUND_EXCEPTION
                            .equals(returnResult.getCode())) {
                        orderPayStatementService.pay(operator,
                                (long) OrderDictionaryEnum.OrderPayMethod.Platform.getKey(),
                                om.getCustomerAccount(), orderCode, platformop.getOrderMoney(),
                                (String) returnResult.getReturnObject(),
                                (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null,
                                null, oldStatus,
                                (long) OrderDictionaryEnum.OrderPayState.Fail.getKey(),
                                platformop.getPlatFormCode(), platformop.getPlatFormName());
                    }
                    if (RefundResultCode.SUCCESS.equals(returnResult.getCode())) {
                        orderPayStatementService.pay(operator,
                                (long) OrderAlterDictionaryEnum.OrderReturnMethod.Platform.getKey(),
                                om.getCustomerAccount(), orderCode, platformop.getOrderMoney(),
                                (String) returnResult.getReturnObject(),
                                (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null,
                                null, oldStatus,
                                (long) OrderDictionaryEnum.OrderPayState.Success.getKey(),
                                platformop.getPlatFormCode(), platformop.getPlatFormName());
                    }
                } catch (Exception e1) {
                    logger.error("订单号:" + om.getOrderCode() + "第三方退款发生异常" + e1.getMessage());
                }


            }
        }
    }

    // 退回第三方支付金额
    private void paidMoneyReturn(String operator, String orderCode, String platFormCode,
            String platFormName, String comment, Date now, OrderMain om,
            OrderPayStatement platformop, Long oldStatus, BigDecimal totalMoneyPaid,
            BigDecimal couponMoneyPaid, List<OrderPayStatement> preferentialList, boolean isZFB,
            BigDecimal balanceMoneyPaid, BigDecimal thirdPaidMoney, BigDecimal reserveFundPaid)
            throws ServiceException {
        save(orderCode, null, om.getOrderStatus(), operator, new Date(now.getTime() + 10000),
                (long) OrderDictionaryEnum.OrderOperateType.Pay.getKey(), totalMoneyPaid, comment);

        // 退回第三方准备
        if (0 != thirdPaidMoney.compareTo(BigDecimal.ZERO)) {
            // 如果是支付宝支付，则要退回到余额
            if (isZFB) {
                try {
                    // 记录一条支付宝退款流水，当流水已存在则更新金额
                    RefundRequest rr = new RefundRequest();
                    rr.setOrderCode(orderCode);
                    rr.setChannel(om.getOrderChannel());
                    rr.setSellerType(null == om.getCommerceId() ? 1 : 2);
                    rr.setSellerShop(om.getCommerceName());
                    rr.setPlatformCode(platFormCode);
                    rr.setOutBatchNo(platformop.getOutsidePayStatementNo());
                    rr.setMenberName(om.getCustomerAccount());
                    rr.setRefurnMoney(thirdPaidMoney);
                    rr.setStatus(0);
                    rr.setRefundType(1);
                    refundRequestService.addRefundRequest(rr);
                } catch (Exception e1) {
                    logger.error("添加支付宝退款流水发生异常", e1);
                }
            } else {
                // 第三方退回，实际的退款动作通过调用B2B的相关接口完成，暂不支持支付宝的原路返回
                Integer rechargeOrOrderFlag = 2;

                try {
                    // 第三方准备流水，没有实际退还动作，只记录相关的操作流水和支付流水
                    int rs = orderPayStatementService.pay(operator,
                            (long) OrderAlterDictionaryEnum.OrderReturnMethod.Platform.getKey(),
                            om.getCustomerAccount(), orderCode, thirdPaidMoney, null,
                            (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null, null,
                            oldStatus, (long) OrderDictionaryEnum.OrderPayState.Ready.getKey(),
                            platFormCode, platFormName);

                    // 第三方退款
                    ReturnResult returnResult = refundOrderService.refundOrder(om.getOrderCode(),
                            rechargeOrOrderFlag, thirdPaidMoney, om.getOrderCode());
                    logger.info("订单" + om.getOrderCode() + "退款结果returnResult="
                            + returnResult.getMessage());

                    if (RefundResultCode.UNSUPORT_BANK_REFUND_EXCEPTION
                            .equals(returnResult.getCode())) {// 不支持退款
                        orderPayStatementService.pay(operator,
                                (long) OrderDictionaryEnum.OrderPayMethod.Platform.getKey(),
                                om.getCustomerAccount(), orderCode, thirdPaidMoney,
                                (String) returnResult.getReturnObject(),
                                (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null,
                                null, oldStatus,
                                (long) OrderDictionaryEnum.OrderPayState.Fail.getKey(),
                                platFormCode, platFormName);
                        /*
                         * 删除退款余额orderPayStatementService.pay(operator, (long)
                         * OrderAlterDictionaryEnum.OrderReturnMethod.Balance.getKey(),
                         * om.getCustomerAccount(), orderCode, thirdPaidMoney, null, (long)
                         * OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null, null,
                         * oldStatus, (long) OrderDictionaryEnum.OrderPayState.Success.getKey(),
                         * platFormCode, platFormName);
                         */
                    }
                    if (RefundResultCode.SUCCESS.equals(returnResult.getCode())) {
                        orderPayStatementService
                                .pay(operator,
                                        (long) OrderAlterDictionaryEnum.OrderReturnMethod.Platform
                                                .getKey(),
                                        om.getCustomerAccount(), orderCode, thirdPaidMoney,
                                        (String) returnResult.getReturnObject(),
                                        (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(),
                                        null, null, oldStatus,
                                        (long) OrderDictionaryEnum.OrderPayState.Success.getKey(),
                                        platFormCode, platFormName);
                    }
                } catch (Exception e1) {
                    logger.error("订单" + om.getOrderCode() + "第三方退回发生异常" + e1.getMessage());
                }


            }
        }

        // 退回余额账户
        if (0 != balanceMoneyPaid.compareTo(BigDecimal.ZERO)) {
            int payResult = orderPayStatementService.pay(operator,
                    (long) OrderAlterDictionaryEnum.OrderReturnMethod.Balance.getKey(),
                    om.getCustomerAccount(), orderCode, balanceMoneyPaid, null,
                    (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null, null,
                    oldStatus, (long) OrderDictionaryEnum.OrderPayState.Success.getKey(), null,
                    null);
            if (1 != payResult) {
                throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                        "订单" + orderCode + "余额退还失败");
            }
        }

        // }
        /* 优惠券退回 */
        if (0 != couponMoneyPaid.compareTo(BigDecimal.ZERO)) {
            try {
                for (OrderPayStatement op : preferentialList) {
                    orderPayStatementService.pay(operator,
                            (long) OrderAlterDictionaryEnum.OrderReturnMethod.Coupon.getKey(),
                            om.getCustomerAccount(), orderCode, couponMoneyPaid, null,
                            (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(),
                            op.getPreferentialNo(), op.getPreferentialGrantId(), oldStatus,
                            (long) OrderDictionaryEnum.OrderPayState.Success.getKey(), null, null);
                }
            } catch (Exception e1) {
                logger.error("优惠券退回发生异常", e1);
            }
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int changeOrderStatus(Map map) throws ServiceException {
        try {
            orderMainDAO.changeOrderStatus(map);
            return 1;
        } catch (SQLException e) {
            logger.error("更新订单状态发生异常", e);
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                    "更新订单状态发生异常失败");
        }
    }

    /**
     * 批量插入操作流水
     */
    @Override
    public void insertByList(List<OrderMain> list, String operator, String info, Long orderStatus,
            Long operatType) throws ServiceException {
        // 获取订单的上次操作流水记录
        List<String> listCode = new ArrayList<>();
        List<OrderOperateStatement> oostList = new ArrayList<>(), temp;

        try {
            for (OrderMain aList : list) {
                listCode.add(aList.getOrderCode());
                if (listCode.size() == 999) {
                    temp = orderOperateStatementDAO.selectByOrderCodeList(listCode);
                    if (null != temp && !temp.isEmpty()) {
                        oostList.addAll(temp);
                    }
                    listCode.clear();
                }
            }
            if (!listCode.isEmpty()) {
                temp = orderOperateStatementDAO.selectByOrderCodeList(listCode);
                if (null != temp && !temp.isEmpty()) {
                    oostList.addAll(temp);
                }
            }
            // 组装操作流水
            List<OrderOperateStatement> ossListInsert = new ArrayList<>();
            Date now = new Date();
            for (OrderOperateStatement oo : oostList) {
                OrderOperateStatement ooInsert = new OrderOperateStatement();
                ooInsert.setOrderCode(oo.getOrderCode());
                ooInsert.setOrderItemId(oo.getOrderItemId());
                ooInsert.setNowOperateDate(now);
                ooInsert.setNowOperateType(operatType);// 结转操作
                ooInsert.setNowOperator(operator);
                ooInsert.setNowOrderStatus(orderStatus);// 订单结转
                ooInsert.setNowOrderSum(oo.getNowOrderSum());
                ooInsert.setOperateInfo(info);
                ooInsert.setPreviousOperateDate(oo.getNowOperateDate());
                ooInsert.setPreviousOperateType(oo.getNowOperateType());
                ooInsert.setPreviousOperator(oo.getNowOperator());
                ooInsert.setPreviousOrderStatus(oo.getNowOrderStatus());
                ooInsert.setPreviousOrderSum(oo.getNowOrderSum());
                ossListInsert.add(ooInsert);
                if (ossListInsert.size() == 999) {
                    orderOperateStatementDAO.insertList(ossListInsert);
                    ossListInsert.clear();
                }
            }
            if (!ossListInsert.isEmpty()) {
                orderOperateStatementDAO.insertList(ossListInsert);
            }
        } catch (Exception e) {
            logger.error("批量插入操作流水发生异常", e);
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                    "批量插入操作流水发生异常" + e.getMessage());
        }

    }

    @Override
    public int changeOrderDisabled(String operator, String orderCode, Long disabled)
            throws ServiceException {
        try {
            String comment;
            Long operateType;
            Date now = new Date();
            OrderMain om = orderQryService.getOrderByCode(orderCode);
            if (disabled.intValue() == OrderDictionaryEnum.OrderDisabled.Delete.getKey()) {
                if (om.getDisabled().intValue() == OrderDictionaryEnum.OrderDisabled.Display
                        .getKey()
                        && (om.getOrderStatus()
                                .intValue() == OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()
                                || om.getOrderStatus()
                                        .intValue() == OrderDictionaryEnum.Order_Status.Order_Done
                                                .getKey())) {
                    operateType = (long) OrderDictionaryEnum.OrderOperateType.Delete.getKey();
                    comment = "订单已被删除,您可以在订单回收站中查询并还原.";
                } else {
                    return -2;
                }
            } else if (disabled.intValue() == OrderDictionaryEnum.OrderDisabled.Display.getKey()) {
                if (om.getDisabled().intValue() == OrderDictionaryEnum.OrderDisabled.Delete
                        .getKey()) {
                    operateType = (long) OrderDictionaryEnum.OrderOperateType.Recovery.getKey();
                    comment = "订单已被恢复!";
                } else {
                    return -2;
                }
            } else if (disabled.intValue() == OrderDictionaryEnum.OrderDisabled.Drop.getKey()) {
                if (om.getDisabled().intValue() == OrderDictionaryEnum.OrderDisabled.Delete
                        .getKey()) {
                    operateType = (long) OrderDictionaryEnum.OrderOperateType.Drop.getKey();
                    comment = "订单已被永久删除!";
                } else {
                    return -2;
                }
            } else {
                // 未知请求
                return -1;
            }
            om.setDisabled(disabled);
            updateOrderMain(om);
            // 记录操作流水
            save(orderCode, null, om.getOrderStatus(), operator, now, operateType,
                    om.getAmountPayable(), comment);
            return 1;
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                    (0 == disabled.compareTo(1L) ? "恢复" : "删除") + "订单" + orderCode + "发生错误："
                            + e.getMessage());
        }
    }

    @Override
    public int changeOrderAssessStatus(String orderCode, Long assessStatus)
            throws ServiceException {
        try {
            String comment;
            Long operateType;
            OrderMain om;
            try {
                om = orderMainDAO.selectByOrderCode(orderCode);
                if (null == om) {
                    return -5;
                }
            } catch (Exception e) {
                logger.error("修改订单评价发生异常", e);
                return -5;
            }
            if (assessStatus.intValue() == OrderDictionaryEnum.Assess_Status.Assess.getKey()) {
                operateType = (long) OrderDictionaryEnum.OrderOperateType.Assess.getKey();
                comment = "订单已评价!";
            } else if (assessStatus.intValue() == OrderDictionaryEnum.Assess_Status.Additional
                    .getKey()) {
                operateType = (long) OrderDictionaryEnum.OrderOperateType.Additional.getKey();
                comment = "订单已追加评价!";
            } else {
                return -1;
            }
            om.setAssessStatus(assessStatus);
            updateOrderMain(om);
            save(orderCode, null, om.getOrderStatus(), om.getCustomerAccount(), new Date(),
                    operateType, om.getAmountPayable(), comment);
            return 1;
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_EVALUATE_ERROR,
                    "修改订单评价" + orderCode + "发生错误：" + e.getMessage());
        }
    }

    @Override
    public int additional(String orderCode) throws ServiceException {
        int result = 0;
        List<OrderPayStatement> orderPaystatementList;
        try {
            String interfaceSysCode = "";
            OrderMain om;
            Boolean isAdditional;
            Map admap = new HashMap();
            admap.put("orderCode", orderCode);
            try {
                om = orderMainDAO.selectByOrderCode(orderCode);
                if (null == om) {
                    return -5;
                }
                if (om.getOrderStatus().intValue() != OrderDictionaryEnum.Order_Status.Not_Pay
                        .getKey()
                        && om.getOrderStatus()
                                .intValue() != OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()
                        && om.getOrderStatus()
                                .intValue() != OrderDictionaryEnum.Order_Status.Pay_Done.getKey()
                        && om.getOrderStatus()
                                .intValue() != OrderDictionaryEnum.Order_Status.Nopay_FinalMoney
                                        .getKey()) {// 增加待付尾款状态
                    return -2;
                }
                admap.put("flag",
                        om.getOrderStatus()
                                .intValue() == OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()
                                        ? OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()
                                        : OrderDictionaryEnum.OrderPayFlag.Payment.getKey());
                if (om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Not_Pay
                        .getKey()
                        || om.getOrderStatus()
                                .intValue() == OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()
                        || om.getOrderStatus()
                                .intValue() == OrderDictionaryEnum.Order_Status.Nopay_FinalMoney
                                        .getKey()) {
                    orderPaystatementList = orderPayStatementDAO.selectAdditionalList(admap);
                } else { // 已付款
                    orderPaystatementList = null;
                }
            } catch (Exception e) {
                logger.error("发生异常", e);
                return -5;
            }
            try {
                //
                // 预售补单
                int orderStatus = om.getOrderStatus().intValue(); // 订单状态
                if (null != orderPaystatementList
                        && orderStatus == OrderDictionaryEnum.Order_Status.Nopay_FinalMoney
                                .getKey()) {// 尾款支付补单
                    isAdditional = true;
                    orderCode = orderCode + "a"; // 尾款实际支付的订单号
                    for (OrderPayStatement ps : orderPaystatementList) {
                        if (null != ps.getYsFlage() && "2".equals(ps.getYsFlage())
                                && 1 == ps.getState().intValue()) {
                            isAdditional = false;
                        }
                    }
                } else {
                    isAdditional = orderPayStatementDAO.checkIsAdditional(admap);
                }
                if (!isAdditional) {
                    return -2;
                }
            } catch (SQLException e1) {
                return -2;
            }
            QueryResult qr = null;
            if (om.getOrderStatus().intValue() != OrderDictionaryEnum.Order_Status.Pay_Done
                    .getKey()) {
                try {

                    qr = qryOrderOnLineService.queryByOrder(orderCode);

                } catch (Exception e) {
                    logger.error("发生异常", e);
                    return -4;
                }
            }

            if ((om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Not_Pay.getKey()
                    && om.getOrderType() != 7)
                    || om.getOrderStatus()
                            .intValue() == OrderDictionaryEnum.Order_Status.Nopay_FinalMoney
                                    .getKey()) {// 付款补单||尾款补单
                PaymentVO paymentVO = new PaymentVO();
                paymentVO.setOrderCode((String) admap.get("orderCode"));// 从map里去orderCode，去除尾款支付订单号后面的a
                paymentVO.setFlag((long) OrderDictionaryEnum.OrderPayFlag.Payment.getKey());
                paymentVO.setAccount(om.getCustomerAccount());
                if (om.getOrderStatus()
                        .intValue() == OrderDictionaryEnum.Order_Status.Nopay_FinalMoney.getKey()) {
                    admap.put("ysflage", "2");
                    orderPaystatementList = orderPayStatementDAO.selectAdditionalListForYs(admap);
                }
                if (null != orderPaystatementList && orderPaystatementList.size() > 0) {
                    for (OrderPayStatement statement : orderPaystatementList) {
                        if (om.getOrderStatus()
                                .intValue() == OrderDictionaryEnum.Order_Status.Nopay_FinalMoney
                                        .getKey()) {
                            statement.setPayInfo("支付尾款成功！支付号:" + orderCode);
                        }
                        if (null != qr && "SUCCESS".equals(qr.getRb_PayStatus())
                                && statement.getPaymentWay()
                                        .intValue() != OrderDictionaryEnum.OrderPayMethod.Coupon
                                                .getKey()) {// 优惠券支付流水时，不改变状态，在batch中改变为1
                            statement.setState(
                                    (long) OrderDictionaryEnum.OrderPayState.Success.getKey());
                            if (statement.getPaymentWay()
                                    .intValue() == OrderDictionaryEnum.OrderPayMethod.Platform
                                            .getKey()) {
                                statement.setOutsidePayStatementNo(qr.getR2_TrxId());
                                if (null != qr.getR3_Amt()) {
                                    statement.setOrderMoney(new BigDecimal(qr.getR3_Amt()));
                                } else {
                                    statement.setOrderMoney(BigDecimal.ZERO);
                                }
                            }
                            // statement.setPlatFormCode(qr.getR8_MP());
                            // statement.setPlatFormName(OrderDictionaryEnum.PlatformCode.getValueByKey(new
                            // Integer(qr.getR8_MP())));
                        } else {
                            statement.setState(
                                    (long) OrderDictionaryEnum.OrderPayState.Fail.getKey());
                        }
                    }
                }
                paymentVO.setOrderPayStatementList(orderPaystatementList);
                paymentVO.setLogInId(om.getCustomerId().longValue());

                result = orderPayStatementService.batchPay(paymentVO);// 支付
                if (result != 0) {
                    result = orderPayStatementService.lockStock(paymentVO);// 锁库存
                }

            } else if (om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Not_Pay
                    .getKey() && om.getOrderType() == 7) {// 支付定金补单
                admap.put("ysflage", "1");
                orderPaystatementList = orderPayStatementDAO.selectAdditionalListForYs(admap);

                if (null != orderPaystatementList && orderPaystatementList.size() > 0) {
                    for (OrderPayStatement statement : orderPaystatementList) {
                        if (null != qr && "SUCCESS".equals(qr.getRb_PayStatus())
                                && statement.getPaymentWay()
                                        .intValue() != OrderDictionaryEnum.OrderPayMethod.Coupon
                                                .getKey()) {// 优惠券支付流水时，不改变状态，在batch中改变为1
                            statement.setState(
                                    (long) OrderDictionaryEnum.OrderPayState.Success.getKey());
                            if (statement.getPaymentWay()
                                    .intValue() == OrderDictionaryEnum.OrderPayMethod.Platform
                                            .getKey()) {
                                statement.setOutsidePayStatementNo(qr.getR2_TrxId());
                                if (null != qr.getR3_Amt()) {
                                    statement.setOrderMoney(new BigDecimal(qr.getR3_Amt()));
                                } else {
                                    statement.setOrderMoney(BigDecimal.ZERO);
                                }
                            }
                            statement.setPayInfo("支付定金成功！支付号:" + orderCode);
                            // statement.setPlatFormCode(qr.getR8_MP());
                            // statement.setPlatFormName(OrderDictionaryEnum.PlatformCode.getValueByKey(new
                            // Integer(qr.getR8_MP())));
                        } else {
                            statement.setState(
                                    (long) OrderDictionaryEnum.OrderPayState.Fail.getKey());
                        }
                    }
                } else {
                    throw new ServiceException("预售订单 " + orderCode + "的预售补单支付流水为空");
                }
                /** 1.添加支付流水 **/
                if (null == orderPaystatementList.get(0).getOutsidePayStatementNo()) {// 检查尾款是否支付完成
                    throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR,
                            "补单查询订单 " + orderCode + "定金|尾款支付失败！");
                }
                orderPayStatementDAO.insertList(orderPaystatementList);
                /** 2.修改订单状态为待付尾款 **/
                long status = OrderDictionaryEnum.Order_Status.Nopay_FinalMoney.getKey();// 待支付尾款
                om.setOrderStatus(status);
                orderMainDAO.updateByOrderCode(om);

                /** 3添加操作流水 **/
                OrderOperateStatement oosPrevious;
                // 找到该订单号最近的记录操作流水
                oosPrevious = orderOperateStatementDAO.selectNewest(orderCode);
                OrderOperateStatement oosNow = new OrderOperateStatement();
                Long notPayOrderStatus;
                // 如果是预售订单，把订单状态改为代付尾款
                if (om.getOrderType() == 7) {
                    notPayOrderStatus =
                            (long) OrderDictionaryEnum.Order_Status.Nopay_FinalMoney.getKey();
                } else {
                    notPayOrderStatus = (long) OrderDictionaryEnum.Order_Status.Not_Pay.getKey();
                }

                // 如果最近一次操作是支付操作，则只需要修改当前操作时间即可
                if (oosPrevious != null) {
                    // comment = "您的订单"+orderCode+"已经支付成功，正在安排配送；";
                    if (OrderDictionaryEnum.OrderOperateType.Pay.getKey() == oosPrevious
                            .getNowOperateType().intValue()) {
                        BeanUtils.copyProperties(oosPrevious, oosNow);
                        oosNow.setNowOperateDate(new Date());
                    } else {
                        oosNow.setNowOperateDate(new Date());
                        oosNow.setNowOperateType(2L);// 支付
                        oosNow.setNowOperator(om.getCustomerAccount());
                        // 未支付的订单状态

                        oosNow.setNowOrderStatus(notPayOrderStatus);
                        oosNow.setNowOrderSum(om.getAmountPayable());
                        oosNow.setOrderCode(orderCode);
                        oosNow.setPreviousOperateDate(oosPrevious.getNowOperateDate());
                        oosNow.setPreviousOperateType(oosPrevious.getNowOperateType());
                        oosNow.setPreviousOperator(oosPrevious.getNowOperator());
                        oosNow.setPreviousOrderStatus(oosPrevious.getNowOrderStatus());
                        oosNow.setPreviousOrderSum(oosPrevious.getNowOrderSum());
                    }
                }
                String comment = "您的订单" + orderCode + "已经成功支付定金";
                oosNow.setOperateInfo(comment);
                oosNow.setNowOrderStatus(notPayOrderStatus);
                orderOperateStatementDAO.insert(oosNow);

                result = 1;

            } else if (om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Pay_Done
                    .getKey()) {
                PaymentVO paymentVO = new PaymentVO();
                paymentVO.setOrderCode(orderCode);
                paymentVO.setFlag((long) OrderDictionaryEnum.OrderPayFlag.Payment.getKey());
                paymentVO.setAccount(om.getCustomerAccount());
                paymentVO.setLogInId(om.getCustomerId().longValue());
                result = orderPayStatementService.lockStock(paymentVO);// 锁库存
                pushOrderUserSource(orderCode);// 推送
            } else if (om.getOrderStatus()
                    .intValue() == OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()) { // 取消订单退款补单
                // 进行退款操作
                ReturnResult returnResult;
                Integer rechargeOrOrderFlag = 2;
                for (OrderPayStatement platformop : orderPaystatementList) {
                    try {
                        // 预售退款
                        if (!StringUtils.isEmpty(platformop.getYsFlage())) {
                            returnResult = refundOrderService.refundOrderForYs(om.getOrderCode(),
                                    rechargeOrOrderFlag, platformop.getOrderMoney().abs(),
                                    om.getOrderCode(), platformop.getYsFlage());
                        } else {
                            // 非预售退款
                            returnResult = refundOrderService.refundOrder(om.getOrderCode(),
                                    rechargeOrOrderFlag, platformop.getOrderMoney().abs(),
                                    om.getOrderCode());
                        }
                        logger.info("订单" + om.getOrderCode() + "退款结果returnResult="
                                + returnResult.getMessage());

                        if (RefundResultCode.SUCCESS.equals(returnResult.getCode())) {
                            result = 1;
                            orderPayStatementService.pay(SysConstants.SYS,
                                    (long) OrderAlterDictionaryEnum.OrderReturnMethod.Platform
                                            .getKey(),
                                    om.getCustomerAccount(), orderCode,
                                    platformop.getOrderMoney().abs(),
                                    (String) returnResult.getReturnObject(),
                                    (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(),
                                    null, null, om.getOrderStatus(),
                                    (long) OrderDictionaryEnum.OrderPayState.Success.getKey(),
                                    platformop.getPlatFormCode(), platformop.getPlatFormName());
                        }
                    } catch (Exception e) {
                        logger.error("订单" + om.getOrderCode() + "第三方退回发生异常" + e.getMessage());
                        throw new ServiceException(
                                "订单" + om.getOrderCode() + "第三方退回发生异常" + e.getMessage());
                    }

                }

                /*
                 * 删除 }else { throw new
                 * ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_EVALUATE_ERROR, "补单" +
                 * orderCode + "发生错误："); }
                 */

            }
        } catch (Exception e) {
            logger.error("订单" + orderCode + "补单发生异常" + e.getMessage());
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_EVALUATE_ERROR,
                    "补单" + orderCode + "发生错误：" + e.getMessage());
        }
        return result;
    }

    @Override
    public int handle(String orderCode, Short state, String account, Date date)
            throws ServiceException {
        try {
            OrderHandle record = new OrderHandle();
            record.setHandleAccount(account);
            record.setHandleState(state);
            record.setHandleTime(date);
            record.setOrderCode(orderCode);
            OrderHandleExample example = new OrderHandleExample();
            OrderHandleExample.Criteria criteria = example.createCriteria();
            criteria.andOrderCodeEqualTo(orderCode);
            List list = orderHandleDAO.selectByExample(example);
            if (!ListUtils.isNotEmpty(list)) {
                if (state == 2) {
                    orderHandleDAO.insert(record);
                }
            } else {
                record.setHandleNo(((OrderHandle) list.get(0)).getHandleNo());
                orderHandleDAO.updateByPrimaryKey(record);
            }
        } catch (SQLException e) {
            logger.error("记录订单处理标记出错", e);
            throw new ServiceException(0, "记录订单处理标记出错！");
        }
        return 1;
    }

    @Override
    public int changeOrderInfo(Map<String, String> map) throws ServiceException {
        try {
            // orderMainDAO.updateByOrderCode(record);
            orderMainDAO.changeOrderInfo(map);
        } catch (SQLException e) {
            logger.error("修改订单信息出错", e);
            throw new ServiceException(0, "修改订单信息出错！");
        }
        return 1;
    }

    @Override
    public int medicCheck(String orderCode, Long checkFlag, String account, Date date)
            throws ServiceException {
        try {
            Map map = new HashMap<String, Object>();
            map.put("orderCode", orderCode);
            map.put("checkFlag", checkFlag);
            orderMainDAO.updateCheckFLagByOrderCode(map);
        } catch (SQLException e) {
            logger.error("修改订单审核标志位出错", e);
            throw new ServiceException(0, "修改订单审核标志位出错！");
        }
        return 1;
    }

    /**
     * 修改运费
     * 
     * @param orderCode 订单
     * @param account 操作人
     * @param newFare 邮费
     * @return
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public boolean updateFare(String orderCode, String account, BigDecimal newFare)
            throws ServiceException {
        try {
            OrderMain om = orderMainDAO.selectByOrderCode(orderCode);
            if (null != om && OrderDictionaryEnum.Order_Status.Not_Pay.getKey() == om
                    .getOrderStatus().intValue()) {
                BigDecimal oldFare = om.getFare();
                BigDecimal amountPayable = om.getAmountPayable();
                if (null == oldFare) {
                    oldFare = BigDecimal.ZERO;
                }
                BigDecimal diff = newFare.subtract(oldFare);
                if (BigDecimal.ZERO.compareTo(diff) == 0) {
                    return true;
                } else {
                    amountPayable = amountPayable.add(diff);
                }
                Map map = new HashMap();
                map.put("orderCode", orderCode);
                map.put("fare", newFare);
                map.put("amountPayable", String.valueOf(amountPayable));
                orderMainDAO.changeOrderFee(map);
                String comment = "订单运费由" + oldFare + "元改为" + newFare + "元";
                save(orderCode, null, om.getOrderStatus(), account, new Date(),
                        (long) OrderDictionaryEnum.OrderOperateType.ChangeFee.getKey(),
                        amountPayable, comment);
                return true;
            }
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR, "修改运费出错！", e);
        }
        return false;
    }

    /**
     * 查询待确认收货的订单编号
     * 
     * @return
     * @throws ServiceException
     */
    @Override
    public List<String> queryUnconfirmReceiptOrderCode() throws ServiceException {
        try {
            return orderOperateStatementDAO.queryUnconfirmReceiptOrderCode();
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ERROR, e.getMessage());
        }
    }

    /**
     * 订单自动确认
     * 
     * @param orderCode
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean OrderAutoSure(String orderCode) throws ServiceException {
        logger.info("订单号：" + orderCode + "调用同步方法");
        int rs = changeOrderStatus(SysConstants.SYS_KEY, orderCode,
                (long) (OrderDictionaryEnum.Order_Status.Order_Done.getKey()), null);
        if (1 == rs) {
            logger.info("订单号：" + orderCode + "同步进行中");
            OrderSync orderSync = new OrderSync();
            orderSync.setOrderCode(orderCode);
            orderSync.setSyncFlag(OrderDictionaryEnum.OrderSyncFlag.unSync.getKey());
            try {
                orderSyncDAO.insertForAuto(orderSync);
                logger.info("订单号：" + orderCode + "同步成功");
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return 1 == rs;
    }

    /**
     * 延迟收货
     * 
     * @param operator
     * @param ome
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean delayReceipt(String operator, OrderMainExt ome) throws ServiceException {
        try {
            orderMainDAO.UpdateOrderMainExt(ome);
            return true;
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ERROR, e.getMessage());
        }
    }


    /**
     * @param orderCode SKU编码
     *
     */
    @Override
    public void pushOrderUserSource(String orderCode) {
        try {
            byte[] bt = jedisCluster.get(("orderItem_" + orderCode).getBytes());
            if (null != bt && bt.length > 0) {
                List<com.pltfm.app.entities.OrderItem> oList =
                        (List<com.pltfm.app.entities.OrderItem>) SerializeUtil.unserialize(
                                jedisCluster.get(("orderItem_" + orderCode).getBytes()));
                JSONObject jsonMain = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                jsonMain.put("orderCode", orderCode);// 订单编号
                for (com.pltfm.app.entities.OrderItem oi : oList) {
                    JSONObject jsonItem = new JSONObject();
                    jsonItem.put("skuId", oi.getSkuId());// SkuID
                    jsonItem.put("commodityNumber", oi.getCommodityNumber());// 商品数量
                    jsonArray.add(jsonItem);
                }
                jsonMain.put("oList", jsonArray);
                jsonMain.put("userSource", jedisCluster.get("orderUserSource_" + orderCode));// 来源
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                jsonMain.put("payTime", sdf.format(new Date()));// 支付时间
                logger.info("推送信息为：" + jsonMain.toString());
                // send(jsonMain.toString(), destination, jmsTemplate);
            }
        } catch (Exception e) {
            logger.error("订单用户来源推送出错：" + e.getMessage());
        }
    }

    // public static void send(final String value, Destination destination, JmsTemplate jmsTemplate)
    // {
    // jmsTemplate.send(destination, session -> {
    // ObjectMessage objectMessage = null;
    // try {
    // objectMessage = session.createObjectMessage(value);
    // } catch (Exception e) {
    // logger.error("发送MQ消息发生异常" + e.getMessage());
    // throw new JMSException("发送MQ消息发生异常," + e);
    // }
    // return objectMessage;
    // });
    // }

    @Override
    public int renewExceptionOrder(String operator, String orderCode) throws ServiceException {
        int result = -1;
        try {
            // 根据订单编码查询订单
            OrderMain orderMain = orderQryService.getOrderByCode(orderCode);
            // 获取最近一次订单设置为异常订单操作流水
            OrderOperateStatement latestOperate = orderOperateStatementDAO.selectNewestWithStatus(
                    orderCode, OrderDictionaryEnum.Order_Status.Exception_Order.getKey());
            if (latestOperate != null && orderMain != null) {
                // 更新订单状态,如果最近一次异常状态之前状态为待风险评估，则更新状态为风控通过，如果之前状态为风控通过，则修改状态为已锁库存
                if (OrderDictionaryEnum.Order_Status.Risk_Appraise.getKey() == latestOperate
                        .getPreviousOrderStatus()) {
                    orderMain.setOrderStatus(
                            (long) OrderDictionaryEnum.Order_Status.Risk_Pass.getKey());
                } else {
                    orderMain.setOrderStatus(latestOperate.getPreviousOrderStatus());
                }
                orderMainDAO.updateByPrimaryKeySelective(orderMain);
                // 新增订单恢复操作流水，流水类型为【解除异常】，并设置操作流水内容为 【订单被解除异常标记，恢复为正常订单】
                orderOperateStatementDAO
                        .insertSelective(newOrderOperate(latestOperate, orderCode, null,
                                orderMain.getOrderStatus(), operator, new Date(),
                                (long) OrderDictionaryEnum.OrderOperateType.ReleaseException
                                        .getKey(),
                                orderMain.getAmountPayable(), "订单被解除异常标记，恢复为正常订单"));
                result = 1;
            }
        } catch (Exception e) {
            logger.error("恢复异常订单时失败！orderCode=" + orderCode + "," + e.getMessage());
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ERROR, e.getMessage());
        }
        return result;
    }

    @Override
    public int saveExceptionOrder(String operator, OrderMain orderMain) throws ServiceException {
        int result;
        try {
            // 更新订单
            orderMainDAO.updateByPrimaryKeySelective(orderMain);
            // 新增订单操作流水，流水类型为【标记异常】，并设置操作流水内容为 【订单被标记为异常订单】
            OrderOperateStatement oldRecord =
                    orderOperateStatementDAO.selectNewest(orderMain.getOrderCode());
            orderOperateStatementDAO.insertSelective(newOrderOperate(oldRecord,
                    orderMain.getOrderCode(), null, orderMain.getOrderStatus(), operator,
                    new Date(), (long) OrderDictionaryEnum.OrderOperateType.MarkException.getKey(),
                    orderMain.getAmountPayable(), "订单被标记为异常订单"));
            // 添加黑名单
            addBlackList(orderMain.getOrderCode(), operator);
            result = 1;
        } catch (Exception e) {
            logger.error("恢复异常订单时失败！orderCode=" + orderMain.getOrderCode() + "," + e.getMessage());
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ERROR, e.getMessage());
        }
        return result;
    }



    /**
     * 修改风控状态
     * 
     * @param orderCode
     * @param ops
     * @return
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public boolean orderRiskCheckUpdateOrder(String orderCode, Long status, String estimateContent,
            OrderOperateStatement ops) throws ServiceException {
        try {
            if (orderMainDAO.orderRiskCheckUpdateOrder(orderCode, status, estimateContent)
                    && orderOperateStatementDAO.insert(ops) > 0) {
                if (OrderDictionaryEnum.Order_Status.Exception_Order.getKey() == status) {
                    // 增加黑名单
                    addBlackList(orderCode, ops.getPreviousOperator());
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ERROR, e.getMessage());
        }
    }

    @Override
    public void addBlackList(String orderCode, String operator) throws Exception {
        try {
            OrderRiskCondition orc = orderRiskSettingService
                    .queryOrderRisk(OrderRiskKey.ORDER_RISK_AUTO_ADD_BLACK_LIST);
            List<OrderRiskBackList> list = new ArrayList<>();
            if (null != orc && (orc.getCondition().intValue() == 1 || 1 == orc.getScore())) {
                OrderMain om = orderMainDAO.selectByOrderCode(orderCode);
                OrderRiskBackList orbl;
                if (orc.getCondition().intValue() == 1 && null != om.getCustomerAccount()) {
                    int type = OrderRiskKey.OrderRiskRosterTypeEnum.ROSTER_TYPE_CUSTOMER_ACCOUNT
                            .getCode();
                    String content = om.getCustomerAccount();
                    if (orderRiskRosterService.isExistBlack(type, content)) {
                        logger.info("type:" + type + ",content:" + content + "黑名单已存在");
                    } else if (orderRiskRosterService.isExistWhite(type, content)) {
                        logger.info("type:" + type + ",content:" + content + "白名单已存在");
                    } else {
                        orbl = new OrderRiskBackList();
                        orbl.setType(type);
                        orbl.setContent(content);
                        orbl.setValid(OrderRiskKey.ORDER_RISK_VALID_NORMAL);
                        orbl.setOperator(operator);
                        list.add(orbl);
                    }
                }
                if (1 == orc.getScore() && null != om.getConsigneeMobile()) {
                    int type = OrderRiskKey.OrderRiskRosterTypeEnum.ROSTER_TYPE_CONSIGNEE_MOBILE
                            .getCode();
                    String content = om.getConsigneeMobile();
                    if (orderRiskRosterService.isExistBlack(type, content)) {
                        logger.info("type:" + type + ",content:" + content + "黑名单已存在");
                    } else if (orderRiskRosterService.isExistWhite(type, content)) {
                        logger.info("type:" + type + ",content:" + content + "白名单已存在");
                    } else {
                        orbl = new OrderRiskBackList();
                        orbl.setType(type);
                        orbl.setContent(content);
                        orbl.setValid(OrderRiskKey.ORDER_RISK_VALID_NORMAL);
                        orbl.setOperator(operator);
                        list.add(orbl);

                    }
                }
                OrderRiskRosterDAO.saveBlackList(list);
            }
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ERROR, e.getMessage());
        }
    }

    /**
     * 取消订单
     * 
     * @param om
     * @param oldStatus 修改前的订单状态
     * @param operator
     * @return
     * @throws ServiceException
     */
    public int cancelOrder(OrderMain om, Long oldStatus, String operator) throws ServiceException {
        if (om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Not_Pay.getKey()
                || om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Pay_Done
                        .getKey()
                || om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Stock_Lock
                        .getKey()
                || om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Risk_Appraise
                        .getKey()
                || om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Risk_Pass
                        .getKey()
                || om.getOrderStatus()
                        .intValue() == OrderDictionaryEnum.Order_Status.Nopay_FinalMoney.getKey()) {
            Long operateType = (long) OrderDictionaryEnum.OrderOperateType.Cancel.getKey();
            String comment = "您的订单已经被取消。";
            BigDecimal totalMoneyPaid;// 已支付总额
            BigDecimal couponMoneyPaid;// 原优惠券总额
            List<OrderPayStatement> preferentialList;// 优惠券支付列表
            List<OrderPayStatement> platformops;// 第三方支付流水
            BigDecimal balanceMoneyPaid;// 余额支付金额
            BigDecimal thirdPaidMoney = BigDecimal.ZERO;// 第三方支付金额

            String orderCode = om.getOrderCode();
            Date now = new Date();
            int unLockFlag = OrderInterFaceOperateTypeEnum.StockHandleType.batchUnLock.getKey();

            try {
                Map map = new HashMap();
                map.put("orderCode", om.getOrderCode());
                totalMoneyPaid = orderPayStatementService.getOrderPay(map);

                map.put("payway", OrderDictionaryEnum.OrderPayMethod.Coupon.getKey());
                couponMoneyPaid = orderPayStatementService.getOrderPay(map);

                map.put("payway", OrderDictionaryEnum.OrderPayMethod.Balance.getKey());
                balanceMoneyPaid = orderPayStatementService.getOrderPay(map);

                preferentialList =
                        orderPayStatementService.getPayPreferentialByOrderCode(om.getOrderCode());

                OrderPayStatementExample example = new OrderPayStatementExample();
                OrderPayStatementExample.Criteria criteria = example.createCriteria();
                criteria.andOrderCodeEqualTo(om.getOrderCode());
                criteria.andPaymentWayEqualTo(
                        (long) OrderDictionaryEnum.OrderPayMethod.Platform.getKey());
                if (om.getOrderStatus().intValue() != OrderDictionaryEnum.Order_Status.Not_Pay
                        .getKey()) {
                    criteria.andStateEqualTo(
                            (long) OrderDictionaryEnum.OrderPayState.Success.getKey());
                }
                platformops = orderPayStatementDAO.selectByExample(example);

            } catch (Exception e1) {
                logger.error("取消订单" + orderCode + "操作查询支付金额，支付流水失败!" + e1.getMessage());
                return -4;
            }

            om.setOrderStatus(new Long(OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()));
            try {
                if (1 != orderMainDAO.cancelOrder(orderCode)) {
                    logger.error("取消订单:修改订单" + orderCode + "的订单状态不成功");
                    return -4;
                }
            } catch (Exception e) {
                logger.error("修改订单" + orderCode + "的订单状态发生异常!" + e.getMessage());
                return -4;
            }
            // 记录操作流水-取消
            if (BigDecimal.ZERO.compareTo(totalMoneyPaid) != 0) {
                comment = "您的订单已经被取消，已支付金额将会原路返还。根据您的支付渠道具体政策不同，将在一到三个工作日内到账，以实际入账时间为准。";
            }
            save(om.getOrderCode(), null, om.getOrderStatus(), operator, now, operateType,
                    totalMoneyPaid, comment);
            if ((om.getOrderType() != 3 || om.getOrderType() != 4 || om.getOrderType() != 5)) {// 非时代订单
                // 记录操作流水-退款
                comment = "您的订单单中已支付部分金额已返还到您的余额及银行账户。";
                paidMoneyReturns(operator, orderCode, comment, now, om, oldStatus, totalMoneyPaid,
                        couponMoneyPaid, preferentialList, balanceMoneyPaid, platformops);
            }
            if (oldStatus.intValue() == OrderDictionaryEnum.Order_Status.Stock_Lock.getKey()
                    || oldStatus.intValue() == OrderDictionaryEnum.Order_Status.Risk_Appraise
                            .getKey()
                    || oldStatus.intValue() == OrderDictionaryEnum.Order_Status.Risk_Pass
                            .getKey()) {
                try {
                    List<OrderItem> orderItemList = orderItemQryService.listOrderItems(orderCode);
                    int re = settlementAndPayUtil.batchHandleStock(orderItemList, unLockFlag,
                            om.getOrderChannel());
                } catch (Exception e1) {
                    logger.error("取消订单" + orderCode + "时解锁库存发生异常", e1);
                    return -4;
                }
            }
            if (oldStatus.intValue() == OrderDictionaryEnum.Order_Status.Not_Pay.getKey()
                    && (null != platformops && platformops.size() > 0
                            && "7".equals(platformops.get(0).getPlatFormCode()))) { // 未付款且是康美通支付是才取消订单
                try {
                    closeKMTPayService.closeKMTPay(null, orderCode);
                } catch (Exception e1) {
                    logger.error("取消订单" + orderCode + "关闭康美通未支付订单", e1);
                    return -4;
                }
            }
            // 取消预售订单 减少预售库存
            if (OrderDictionaryEnum.Order_Type.YsOrder.getKey() == om.getOrderType().intValue()) {
                try {
                    List<OrderItem> orderItemList =
                            orderItemDAO.listByOrderCopdeForPresell(orderCode);
                    for (OrderItem orderItem : orderItemList) {
                        presellInfoRemoteService.updatePresellStock(orderItem.getPresellId(),
                                orderItem.getSkuId(),
                                orderItem.getCommodityNumber().intValue() * (-1));
                    }
                } catch (Exception e) {
                    logger.error("取消预售订单" + orderCode + "出错,扣减活动库存发生异常报错", e);
                    return -4;
                }
            }
            return 1;
        } else {
            return -2;
        }
    }


    public int finshOrder(OrderMain om, String operator) {
        if (om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Ship_Done.getKey()
                || operator.equals(SysConstants.SYS_KEY)) {
            if (operator.equals(SysConstants.SYS_KEY)) {
                operator = SysConstants.SYS;
            }
            long operateType = OrderDictionaryEnum.OrderOperateType.Finish.getKey();
            String comment = "买家已确认收货!";
            Date now = new Date();
            String orderCode = om.getOrderCode();


            om.setFinishDate(now);
            om.setOrderStatus(new Long(OrderDictionaryEnum.Order_Status.Order_Done.getKey()));

            try {
                updateOrderMain(om);
                // 记录操作流水
                save(orderCode, null, om.getOrderStatus(), operator, now, operateType,
                        om.getAmountPayable(), comment);

                OrderMain rom = orderQryService.getRootOrderByCode(orderCode);
                Map<String, String> nmap = new HashMap<String, String>();
                nmap.put("parentOrderCode", rom.getOrderCode());
                nmap.put("orderCode", om.getOrderCode());
                Boolean isLastOrder = orderMainDAO.isLastOrder(nmap);// 是否最后确认收货的子订单
                if (isLastOrder) {
                    // 更新父订单完成时间
                    rom.setFinishDate(new Date());
                    orderMainDAO.updateByPrimaryKeySelective(rom);

                    // 满送优惠券发放
                    List<OrderPreferential> plist = orderItemQryService.listOrderPreferentials(
                            rom.getOrderCode(), SysConstants.PROMOTION_TYPE_GIFT);
                    UserInfoDO userInfo = new UserInfoDO();
                    userInfo.setLoginId(om.getCustomerId().intValue());

                    List<OrderPreferential> opList = new ArrayList<>();
                    for (OrderPreferential orderPreferential : plist) {
                        if (orderPreferential.getOrderPreferentialType()
                                .intValue() == SysConstants.PROMOTION_TYPE_GIFT.intValue()) {
                            opList.add(orderPreferential);
                        }
                    }
                    Map<Long, OrderPreferential> couponMap = couponRemoteService
                            .orderGivenCoupon(om.getCustomerId().intValue(), opList);
                    if (null != couponMap && !couponMap.isEmpty()) {
                        for (OrderPreferential op : couponMap.values()) {
                            OrderPreferential upOp = new OrderPreferential();
                            upOp.setOrderCode(op.getOrderCode());
                            upOp.setCouponId(op.getCouponId());
                            upOp.setGrantId(op.getGrantId());
                            upOp.setOrder_preferential_id(op.getOrder_preferential_id());
                            orderPreferentialDAO.updateByPrimaryKeySelective(upOp);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("完成订单" + orderCode + "失败！" + "  " + e);
                return -2;
            }
            return 1;
        } else {
            return -2;
        }
    }


    @Override
    public boolean noPayOrderCheck(String orderCode) throws ServiceException {
        boolean result = false;
        try {
            OrderMainVo om;
            OrderPayStatement ready;
            if (null != (om = orderMainDAO.selectByOrderCode(orderCode))
                    && om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Not_Pay
                            .getKey()
                    && null != (ready =
                            orderPayStatementDAO.queryOrderTHDReadyPayStatement(orderCode))) {
                QueryResult qr;
                qr = qryOrderOnLineService.queryByOrder(orderCode);

                if (null != qr && "SUCCESS".equals(qr.getRb_PayStatus())) {
                    OrderPayStatement ops = (OrderPayStatement) ready.clone();
                    Date date = new Date();
                    ops.setCreateDate(date);
                    ops.setEndDate(date);
                    ops.setState((long) OrderDictionaryEnum.OrderPayState.Success.getKey());
                    ops.setOutsidePayStatementNo(qr.getR2_TrxId());
                    if (orderPayStatementDAO.insert(ops) > 0) {
                        try {
                            OrderOperateStatement operate = new OrderOperateStatement();
                            operate.setNowOperateDate(date);
                            operate.setNowOperateType(
                                    (long) OrderDictionaryEnum.OrderOperateType.Pay.getKey());
                            operate.setNowOperator(SysConstants.SYS);
                            operate.setNowOrderStatus(
                                    (long) OrderDictionaryEnum.Order_Status.Pay_Done.getKey());
                            operate.setNowOrderSum(ops.getOrderMoney());
                            operate.setOrderCode(ops.getOrderCode());
                            operate.setOperateInfo("您的订单" + ops.getOrderCode() + "已经支付成功，正在安排配送；");
                            orderOperateStatementDAO.insert(operate);

                            om.setOrderStatus(
                                    (long) OrderDictionaryEnum.Order_Status.Pay_Done.getKey());
                            orderMainDAO.updateByPrimaryKey(om);
                        } catch (Exception e) {
                            logger.error(e);
                        }
                    }
                    result = true;
                }
            }
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
        return result;
    }


}
