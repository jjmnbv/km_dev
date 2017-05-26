package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.promotion.remote.service.PresellInfoRemoteService;
import com.kmzyc.user.remote.service.TrationListRemoteService;
import com.pltfm.app.dao.OrderItemDAO;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.dao.OrderOperateStatementDAO;
import com.pltfm.app.dao.OrderPayStatementDAO;
import com.pltfm.app.dao.OrderPreferentialDAO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderOperateStatement;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.entities.OrderPreferential;
import com.pltfm.app.service.AgencyCreateOrderService;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.service.RiskManagementService;
import com.pltfm.app.util.OrderCodeUtil;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.OrderInterFaceOperateTypeEnum;
import com.pltfm.app.util.SettlementAndPayUtil;
import com.pltfm.app.util.SysConstants;
import com.pltfm.app.vobject.OrderPreferentialVO;
import com.pltfm.sys.util.ErrorCode;

/**
 * 订单生成接口实现类
 * 
 * @author lvxingxing
 * @version 1.0
 * @since 2013-7-26
 */
@SuppressWarnings("unchecked")
@Service("orderService")
public class AgencyCreateOrderServiceImpl implements AgencyCreateOrderService {
    @Resource
    private OrderMainDAO orderMainDAO;
    @Resource
    private OrderItemDAO orderItemDAO;
    @Resource
    private OrderPreferentialDAO orderPreferentialDAO;
    @Resource
    private OrderPayStatementDAO orderPayStatementDAO;
    @Resource
    private OrderOperateStatementService orderOperateStatementService;
    @Resource
    private OrderOperateStatementDAO orderOperateStatementDAO;

    // 客户系统的修改账户余额的接口服务类
    @Resource
    private TrationListRemoteService trantionListRemoteService;
    // 产品系统的优惠接口服务类
    @Autowired
    private CouponRemoteService couponRemoteService;
    @Resource
    private PresellInfoRemoteService presellInfoRemoteService;
    @Autowired
    private SettlementAndPayUtil settlementAndPayUtil;
    // log4j实例化
    private static final Logger log = Logger.getLogger(AgencyCreateOrderServiceImpl.class);
    // 订单风控
    @Resource
    private RiskManagementService riskManagementService;
    // 订单同步到总部系统
    @Resource
    private OrderQryService orderQryService;

    // 优惠券接口调用参数，对应类型
    // private static final String INTERFACE_PARAMETER_TYPE = "1";

    /**
     * 下单接口 入参：订单对象order、订单明细列表orderItemList、 订单优惠明细对象列表orderPreferentialVOList（经过2次封装包含sku信息）、
     * 订单支付对象列表orderPayStatementList（主要是针对优惠券而产生的支付信息） 返回结果：如果正常，则返回订单编码、 如果调用产品系统的优惠券接口异常，则返回-1、
     * 如果订单系统内部异常，则返回0
     * 
     * */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public String agencyCreateOrder(OrderMain order, List<OrderItem> orderItemList,
            List<OrderPreferentialVO> orderPreferentialVOList,
            List<OrderPayStatement> orderPayStatementList) throws ServiceException {
        // 返回结果初始化
        String result = "0";
        // 初始化生成的订单id
        Long orderId = null;
        // 初始化生成的订单对象
        OrderMain newOrder = null;
        // 下单备注信息初始化
        String orderInfo = null;
        // 优惠券编号初始化
        Long preferentialNo = null;
        // 优惠券发放编号初始化
        Long preferentialGrantId = null;
        // 客户id初始化
        String customerId = null;
        if (order.getCustomerId() != null) {
            customerId = order.getCustomerId().toString();
        }
        // 设置订单类型为普通商品订单
        if (null == order.getOrderType()) {
            order.setOrderType( Long.valueOf(OrderDictionaryEnum.Order_Type.Normal.getKey()));
        }
        // 优惠券调用返回结果初始化
        int couponRemoteResult = 0;
        // 生成对应操作时间
        Date date = new Date();
        // 支付类型
        Long payMethod = null;
        // 客户账号
        String account = order.getCustomerAccount() == null ? "游客":order.getCustomerAccount() ;
        /* if (order.getOrderPurchaserType().intValue() == OrderDictionaryEnum.OrderPurchaserType.Register.getKey() ) {
            if (order.getCustomerAccount() != null) {
                account = order.getCustomerAccount();
            }
        } else if (order.getOrderPurchaserType().intValue() == OrderDictionaryEnum.OrderPurchaserType.UnRegister
                .getKey()) {
            account = "游客";
        }*/
        // 订单金额
        Double orderMoney = null;
        // 调用客户系统账号余额相关接口的返回值
        Integer trantionRemoteResult = null;
        // 设置订单状态为未支付
        order.setOrderStatus((long) OrderDictionaryEnum.Order_Status.Not_Pay.getKey());
        order.setDisabled((long) OrderDictionaryEnum.OrderDisabled.Display.getKey());
        // 获取orderCode
        String orderCode = OrderCodeUtil.generateOrderCode();
        order.setOrderCode(orderCode);
        // 设置订单生成时间
        order.setCreateDate(date);
        // 操作类型用于操作流水
        Long operateType = new Long(OrderDictionaryEnum.OrderOperateType.Create.getKey());
        // 未支付的订单状态
        Long notPayOrderStatus = new Long(OrderDictionaryEnum.Order_Status.Not_Pay.getKey());
        // 初始化客户信息对象
        UserInfoDO userInfo = null;
        userInfo = new UserInfoDO();
        if(null != customerId){
        userInfo.setLoginId(new Integer(customerId));
        }
        // 初始化支付流水对象，用于循环遍历支付流水列表
        OrderPayStatement orderPayStatement = null;
        // 初始化订单生成的操作流水记录
        OrderOperateStatement createOperateRecord = null;
        // 初始化订单支付的操作流水记录
        OrderOperateStatement payOperateRecord = null;
        // 初始化订单操作流水记录列表
        List<OrderOperateStatement> orderOperateRecordList;
        // 初始化订单支付流水对象列表
        List<OrderPayStatement> insertPayStatementList = null;
        // 初始化当次所有支付的总金额
        BigDecimal orderMoneyAll = BigDecimal.ZERO;
        // 初始化是否含有余额支付的标志位
        Boolean balancePayFlag = false;
        // 初始化是否含有优惠券支付的标志位
        Boolean couponPayFlag = false;
        // 优惠券授权对象
        // CouponGrant couponGrant = null;
        // 余额支付的金额
        BigDecimal accountBalanceMoney = null;
        // 获取订单明细数量
        int itemsTotalNum = 0;
        // 订单明细
        OrderItem orderItem = null;

        try {
            // 生成订单
            // 计算订单总pv值
            float orderPv = 0;
            for (OrderItem oi : orderItemList) {
                if (null != oi.getCommodityPv() && oi.getCommodityPv() > 0) {
                    orderPv = orderPv + oi.getCommodityPv() * oi.getCommodityNumber();
                }
            }
            if (orderPv > 0) {
                order.setOrderPv(orderPv);
            }
            // 调用订单生成DAO得到订单的物理主键
            orderId = orderMainDAO.insertSelective(order);
            Map<String, Long> orderItemIdMap = new HashMap<String, Long>();
            if (orderId > 0) {
                // 写操作日志，订单明细表
                // 带有明细ID的orderItem集合
                List<OrderItem> itemInsertList = new ArrayList<OrderItem>();
                // 组装订单明细实例
                for (int i = 0; i < orderItemList.size(); i++) {
                    orderItem = orderItemList.get(i);
                    itemsTotalNum += orderItem.getCommodityNumber();
                    orderItem.setOrderCode(orderCode);
                    Long resultItemID = orderItemDAO.insertSelective(orderItem);
                    if (1l == orderItem.getCommodityType()) {
                        orderItemIdMap.put(orderItem.getCommoditySku(), resultItemID);
                    }
                    orderItem.setOrderItemId(resultItemID);
                    itemInsertList.add(orderItem);
                    // 生成推广效果
                    // 推广者不能为空，推广规则不能为空
                    /*删除微商业务  if (null != orderItem.getOrderItemExt()
                            && null != orderItem.getOrderItemExt().getSpreadId()
                            && null != orderItem.getOrderItemExt().getCpId()) {
                        SpreadEffect spereadEffect = new SpreadEffect();
                        spereadEffect.setSpreadId(BigDecimal.valueOf(orderItem.getOrderItemExt()
                                .getSpreadId().longValue())); // 推广者ID
                        spereadEffect.setConsumerId(order.getCustomerId());// 消费者id;
                        try {
                            Map<String, Long> tempMap = new HashMap<String, Long>(1);
                            tempMap.put("srid", orderItem.getOrderItemExt().getSpreadId());// 推广者ID
                            SpreadEffect spereadInfo =
                                    spreadEffectDAO.getSpreandRecommenders(tempMap); // 通过推广者ID
                            // 查询引荐人ID
                            if (spereadInfo != null && spereadInfo.getRecommenders() != null) {
                                spereadEffect.setRecommenders(spereadInfo.getRecommenders());// 微商引荐人ID
                            }
                        } catch (Exception ex) {
                            log.error("查询微商引荐人ID数据异常", ex);
                        }

                        spereadEffect.setOrderCode(orderCode); // 订单号
                        spereadEffect.setOrderItemId(resultItemID); // 订单明细ID
                        spereadEffect.setCommoditySku(orderItem.getCommoditySku());// 商品SKU编号
                        if (orderItem.getSuitId() != null) {
                            spereadEffect.setSuitId(BigDecimal.valueOf(orderItem.getSuitId())); // 套餐ID
                        }
                        if (null != orderItem.getCommodityNumber()) {
                            spereadEffect.setCommodityNumber(orderItem.getCommodityNumber());// 购买数量
                        }
                        if (orderItem.getCommodityUnitIncoming() != null) {
                            spereadEffect.setCommodityUnitIncoming(orderItem
                                    .getCommodityUnitIncoming());// 单品实收
                        }
                        // 获取返佣率数据
                        Map<String, Long> mapTemp = new HashMap<String, Long>(1);
                        if (orderItem.getCommoditySku() != null) {
                            // mapTemp.put("productId", orderItem.getCommoditySku());
                            mapTemp.put("cpId", orderItem.getOrderItemExt().getCpId());
                            try {
                                SpreadEffect spereadEffectTemp =
                                        spreadEffectDAO.selectSpeadEffect(mapTemp);
                                if (spereadEffectTemp != null) {
                                    // 可获得销售返佣率
                                    if (spereadEffectTemp.getSaleRebateAmount() != null) {
                                        spereadEffect.setSaleRebateRate(spereadEffectTemp
                                                .getSaleRebateAmount());
                                    }
                                    // 分销返佣率
                                    if (spereadEffectTemp.getDistriRebackAmount() != null) {
                                        // 当引荐人为空值时，分销返佣率设为0，否则设为云产品定义的分销返佣率
                                        if (spereadEffect.getRecommenders() == null) {
                                            spereadEffect.setDistriRebackRate(new BigDecimal(0));
                                        } else {
                                            spereadEffect.setDistriRebackRate(spereadEffectTemp
                                                    .getDistriRebackAmount());
                                        }
                                    }
                                    // 消费返利率
                                    if (spereadEffectTemp.getConsumptionRate() != null) {
                                        spereadEffect.setConsumptionRate(spereadEffectTemp
                                                .getConsumptionRate());
                                    }
                                    // 对应推广规则ID(改为云产品cpId)
                                    spereadEffect.setSrid(BigDecimal.valueOf(orderItem
                                            .getOrderItemExt().getCpId().longValue()));
                                    spereadEffect.setStatus(order.getOrderStatus().shortValue());
                                    spereadEffect.setRemark(order.getOrderDescription());
                                    spreadEffectDAO.insert(spereadEffect);
                                }
                            } catch (Exception ex) {
                                log.error("获取微商返佣率异常", ex);
                            }
                        }
                    }*/
                }

                // 组装优惠明细
                List<OrderPreferential> orderPreferentialList = new ArrayList<OrderPreferential>();
                OrderPreferentialVO orderPreferentialVO = null;
                OrderPreferential orderPreferential = null;
                if (orderPreferentialVOList != null) {
                    for (int i = 0; i < orderPreferentialVOList.size(); i++) {
                        orderPreferentialVO = orderPreferentialVOList.get(i);
                        orderPreferential = getOrderPreferential(orderPreferentialVO);
                        orderPreferential.setOrderCode(orderCode);
                        if (orderPreferentialVO.getPreferentialSKU() != null
                                && orderItemIdMap.containsKey(orderPreferentialVO
                                        .getPreferentialSKU())) {
                            // 判断某个优惠是否与商品明细相关
                            orderPreferential.setOrderItemId(orderItemIdMap.get(orderPreferentialVO
                                    .getPreferentialSKU()));
                        }
                        orderPreferentialList.add(orderPreferential);
                    }
                    // 如果优惠明细对象列表不为空，则插入优惠明细
                    if (orderPreferentialList.size() > 0) {
                        orderPreferentialDAO.insertList(orderPreferentialList);
                    }
                }

                // --------------------------------------记录下单操作的操作流水 begin
                orderOperateRecordList = new ArrayList<OrderOperateStatement>();
                // 取得订单最近的操作流水记录
                OrderOperateStatement oldRecord = orderOperateStatementDAO.selectNewest(orderCode);
                // 如果没历史操作流水
                if (oldRecord != null) {
                    createOperateRecord =
                            orderOperateStatementService.newOrderOperate(oldRecord, orderCode,
                                    null, notPayOrderStatus, account, date, operateType,
                                    order.getAmountPayable(), orderInfo);
                } else {
                    createOperateRecord = new OrderOperateStatement();
                    createOperateRecord.setNowOperateDate(date);
                    createOperateRecord
                            .setNowOperateType((long) OrderDictionaryEnum.OrderOperateType.Create
                                    .getKey());
                    createOperateRecord.setNowOperator(account);
                    createOperateRecord.setNowOrderStatus(notPayOrderStatus);
                    createOperateRecord.setNowOrderSum(order.getAmountPayable());
                    createOperateRecord.setOrderCode(orderCode);
                    orderInfo = "您已经提交了订单，编号为" + orderCode + "，订单将在支付成功后进入处理流程。";
                    createOperateRecord.setOperateInfo(orderInfo);
                }
                orderOperateRecordList.add(createOperateRecord);

                // 判断是否是预售订单,如果是预售订单需要提交另外一条操作流水 "请您支付定金"
                if (OrderDictionaryEnum.Order_Type.YsOrder.getKey() == order.getOrderType()
                        .intValue()) {
                    orderInfo = "请您支付定金。";
                    createOperateRecord =
                            orderOperateStatementService.newOrderOperate(createOperateRecord,
                                    orderCode, null, notPayOrderStatus, account,
                                    new Date(date.getTime() + 5 * 1000), operateType,
                                    order.getAmountPayable(), orderInfo);
                    orderOperateRecordList.add(createOperateRecord);
                }

                orderOperateStatementDAO.insertList(orderOperateRecordList);
                // ----------------------- 记录支付流水（包括余额和优惠券）和支付操作（有余额支付的前提下）的操作流水
                newOrder = orderMainDAO.selectByPrimaryKey(orderId);
                if (newOrder != null) {
                    OrderPayStatement orderPo = null;
                    // 有支付记录说明选择了余额支付或是优惠券支付、亦或预备金支付，或是3者皆选
                    if (orderPayStatementList != null && orderPayStatementList.size() > 0) {
                        long tempPreferentialNo = -1l;
                        insertPayStatementList = new ArrayList<OrderPayStatement>();
                        Long payState = null;
                        for (int i = 0; i < orderPayStatementList.size(); i++) {
                            orderPo = orderPayStatementList.get(i);
                            payMethod = orderPo.getPaymentWay();
                            if (orderPo.getOrderMoney() != null) {
                                orderMoney = orderPo.getOrderMoney().doubleValue();
                            }
                            // 如果支付方式为余额支付，则直接调客户系统接口来消费余额
                            if (OrderDictionaryEnum.OrderPayMethod.Balance.getKey() == payMethod
                                    .intValue()) {
                                trantionRemoteResult =
                                        trantionListRemoteService.orderTrationAccout(account,
                                                orderMoney, orderCode, "交易内容",
                                                OrderDictionaryEnum.OrderPayFlag.Payment.getKey());
                                balancePayFlag =
                                        null != trantionRemoteResult && 1 == trantionRemoteResult;
                            }
                            if ((null == trantionRemoteResult || 1 != trantionRemoteResult)
                                    && OrderDictionaryEnum.OrderPayMethod.Balance.getKey() == payMethod
                                            .intValue()) {
                                log.error("订单"+orderCode+"余额支付失败，返回trantionRemoteResult="+trantionRemoteResult);
                                // 余额支付失败
                                continue;
                            }
                            // 如果支付方式为预备金支付，则直接调客户系统接口来消费预备金
                       /*删除预备金     if (OrderDictionaryEnum.OrderPayMethod.Reserve.getKey() == payMethod
                                    .intValue()) {
                                trantionListRemoteService.updateReserver(account, orderMoney,
                                        orderCode, "交易内容", 11, 1);
                            }*/
                            if (orderPo.getPreferentialNo() != null) {
                                preferentialNo = orderPo.getPreferentialNo().longValue();
                            }
                            if (orderPo.getPreferentialGrantId() != null) {
                                preferentialGrantId = orderPo.getPreferentialGrantId().longValue();
                            }
                            // 调用优惠券接口，这里要冻结优惠券
                            if (preferentialNo != null
                                    && tempPreferentialNo != preferentialNo.longValue()) {
                                couponPayFlag =
                                        couponRemoteService.changeCustomGrantToGive(userInfo,
                                                preferentialNo, order.getCustomerId().longValue(),
                                                SysConstants.COUPON_FREEZE, orderCode,
                                                preferentialGrantId) > 0;
                                tempPreferentialNo = preferentialNo;
                            }
                            if (couponRemoteResult == 2) {
                                log.error("客户 " + customerId + " 冻结优惠券失败，其优惠券编号为：" + preferentialNo);
                                return "-1";
                            }
                            orderPayStatement = orderPayStatementList.get(i);
                            payMethod = orderPayStatement.getPaymentWay();
                            orderPayStatement.setCreateDate(date);
                            orderPayStatement.setEndDate(new Date());
                            orderPayStatement.setOrderCode(orderCode);
                            orderPayStatement
                                    .setFlag((long) OrderDictionaryEnum.OrderPayFlag.Payment
                                            .getKey());
                            orderPayStatement.setAccount(order.getCustomerAccount());
                            /*
                             * 如果是余额支付或是预备金支付，则需要记录支付成功流水，
                             * 优惠券因为此时是做冻结，故做冻结的支付流水且冻结的优惠券金额不记入当次支付的总金额中
                             */
                            if (OrderDictionaryEnum.OrderPayMethod.Balance.getKey() == payMethod
                                    .intValue()
                                    /*删除|| OrderDictionaryEnum.OrderPayMethod.Reserve.getKey() == payMethod
                                            .intValue()*/) {
                                payState =
                                        new Long(OrderDictionaryEnum.OrderPayState.Success.getKey());
                                accountBalanceMoney = orderPayStatement.getOrderMoney();
                                orderMoneyAll = orderMoneyAll.add(accountBalanceMoney);
                                orderInfo = OrderDictionaryEnum.OrderOperateType.Pay.getValue();
                                payOperateRecord = new OrderOperateStatement();
                                payOperateRecord.setNowOperateDate(new Date());
                                payOperateRecord
                                        .setNowOperateType((long) OrderDictionaryEnum.OrderOperateType.Pay
                                                .getKey());
                                payOperateRecord.setNowOperator(account);
                                payOperateRecord.setNowOrderStatus(notPayOrderStatus);
                                payOperateRecord.setNowOrderSum(order.getAmountPayable());
                                payOperateRecord.setOrderCode(orderCode);
                                payOperateRecord.setOperateInfo(orderInfo);

                                payOperateRecord.setPreviousOperator(account);
                                payOperateRecord.setPreviousOperateDate(date);
                                payOperateRecord.setPreviousOrderSum(order.getAmountPayable());
                                payOperateRecord.setPreviousOrderStatus(notPayOrderStatus);
                                payOperateRecord
                                        .setPreviousOperateType((long) OrderDictionaryEnum.OrderOperateType.Create
                                                .getKey());
                                orderOperateRecordList.add(payOperateRecord);
                            }
                            if (OrderDictionaryEnum.OrderPayMethod.Coupon.getKey() == payMethod
                                    .intValue()) {
                                payState =
                                        new Long(OrderDictionaryEnum.OrderPayState.Freeze.getKey());
                            }
                            orderPayStatement.setState(payState);
                            insertPayStatementList.add(orderPayStatement);
                        }
                        orderPayStatementDAO.insertList(insertPayStatementList);
                    }
                    result = newOrder.getOrderCode();
                }
            }
        } catch (Exception ex) {
            log.error("下单发生异常", ex);
            // 余额返还
            try {
                if (balancePayFlag) {
                    trantionRemoteResult =
                            trantionListRemoteService.orderTrationAccout(
                                    account,
                                    accountBalanceMoney == null ? 0 : accountBalanceMoney
                                            .doubleValue(), orderCode, "交易内容",
                                    OrderDictionaryEnum.OrderPayFlag.Refundment.getKey());
                }
            } catch (Exception e) {
                log.error("下单接口异常时，余额返还异常", e);
                throw new ServiceException(ErrorCode.INNER_SINGLE_MONEY_ERROR, "下单接口异常时，余额返还异常："
                        + e.getMessage());
            }
            // 优惠券解冻
            if (couponPayFlag && null != couponRemoteService) {
                try {
                    couponRemoteService.changeCustomGrantToGive(userInfo, preferentialNo, order
                            .getCustomerId().longValue(), SysConstants.COUPON_UNFREEZE, orderCode,
                            preferentialGrantId);
                } catch (Exception e) {
                    log.error("下单接口异常时，优惠券解冻异常", e);
                    throw new ServiceException(ErrorCode.INNER_SINGLE_COUPON_ERROR,
                            "下单接口异常时，优惠券解冻异常：" + e.getMessage());
                }
            }
            throw new ServiceException(ErrorCode.INNER_SINGLE_MONEY_ERROR, ex.getMessage());
        }
        // 订单生成成功
        if (OrderDictionaryEnum.Order_Type.YsOrder.getKey() == order.getOrderType().intValue()) {
            try {
                presellInfoRemoteService.updatePresellStock(orderItem.getPresellId(),
                        orderItem.getSkuId(), itemsTotalNum);
            } catch (Exception e) {
                log.error("预售订单出错,扣减活动库存发生异常报错", e);
                throw new ServiceException(ErrorCode.INNER_SINGLE_ERROR, e.getMessage());
            }
        }
        return result;
    }

    /**
     * 修改订单状态
     */
    @Override
    public Boolean updateOrder(OrderMain order) throws SQLException {
        int result = orderMainDAO.updateByPrimaryKey(order);
        if (result > 0) {
            return true;
        }
        return false;
    }

    /**
     * 优惠明细VO转换到优惠明细中去
     * 
     * @param vo
     * @return
     */
    private OrderPreferential getOrderPreferential(OrderPreferentialVO vo) {
        OrderPreferential orderPreferential = new OrderPreferential();
        /*
         * if(vo.getPreferentialSKU()!=null){ orderPreferential.setOrderPreferentialCode
         * (vo.getPreferentialSKU().toString()); }else if(vo.getClientNO()!=null){
         * orderPreferential.setOrderPreferentialCode (vo.getClientNO().toString()); }
         */
        orderPreferential.setOrderPreferentialCode(vo.getOrderPreferentialCode());
        orderPreferential.setOrderPreferentialSource(vo.getOrderPreferentialSource());
        orderPreferential.setOrderPreferentialSum(vo.getOrderPreferentialSum());
        orderPreferential.setOrderPreferentialType(vo.getOrderPreferentialType());
        orderPreferential.setCouponId(vo.getCouponId());
        return orderPreferential;
    }

    @Override
    public void updateList(List<OrderMain> list) throws SQLException {
        // Auto-generated method stub

    }

    @Override
    @SuppressWarnings("unused")
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public String agencyCreateOrderForPrize(OrderMain order, List<OrderItem> orderItemList,
            List<OrderPreferentialVO> orderPreferentialList,
            List<OrderPayStatement> orderPayStatementList) throws ServiceException {

        // 返回结果初始化
        String result = "0";
        // 初始化生成的订单id
        Long orderId = null;
        // 初始化生成的订单对象
        OrderMain newOrder = null;
        // 下单备注信息初始化
        String orderInfo = null;
        // 优惠券编号初始化
        Long preferentialNo = null;
        // 优惠券发放编号初始化
        Long preferentialGrantId = null;
        // 客户id初始化
        String customerId = null;
        // 站点代码信息初始化
        String webSite = null;

        if (order.getCustomerId() != null) {
            customerId = order.getCustomerId().toString();
        }
        if (order.getOrderChannel() != null) {
            webSite = order.getOrderChannel();
        }
        // 设置订单类型为抽奖商品订单
        order.setOrderType(new Long(OrderDictionaryEnum.Order_Type.Prize.getKey()));
        // 优惠券调用返回结果初始化
        int couponRemoteResult = 0;
        // 生成对应操作时间
        Date date = new Date();
        // 支付类型
        Long payMethod = null;
        // 客户账号
        String account = order.getCustomerAccount() == null ? "游客" : order.getCustomerAccount();
        /*if (order.getOrderPurchaserType().intValue() == OrderDictionaryEnum.OrderPurchaserType.Register
                .getKey()) {
            if (order.getCustomerAccount() != null) {
                account = order.getCustomerAccount();
            }
        } else if (order.getOrderPurchaserType().intValue() == OrderDictionaryEnum.OrderPurchaserType.UnRegister
                .getKey()) {
            account = "游客";
        }*/
        // 订单金额
        Double orderMoney = null;
        // 调用客户系统账号余额相关接口的返回值

        Integer trantionRemoteResult = null;

        // 设置订单状态为已锁库存
        order.setOrderStatus((long) OrderDictionaryEnum.Order_Status.Stock_Lock.getKey());
        // 设置支付时间
        order.setPayDate(new Date());
        order.setDisabled((long) OrderDictionaryEnum.OrderDisabled.Display.getKey());
        // 获取orderCode
        String orderCode = OrderCodeUtil.generateOrderCode();
        order.setOrderCode(orderCode);
        // 设置订单生成时间
        order.setCreateDate(date);
        // 操作类型用于操作流水
        Long operateType = new Long(OrderDictionaryEnum.OrderOperateType.Create.getKey());
        // 未支付的订单状态
        Long notPayOrderStatus = new Long(OrderDictionaryEnum.Order_Status.Not_Pay.getKey());
        // 已支付的订单状态
        Long payOrderStatus = new Long(OrderDictionaryEnum.Order_Status.Pay_Done.getKey());
        // 初始化客户信息对象
        UserInfoDO userInfo = null;
        userInfo = new UserInfoDO();
        if(null != customerId){
            userInfo.setLoginId(new Integer(customerId));
        }
        // 初始化支付流水对象，用于循环遍历支付流水列表
        OrderPayStatement orderPayStatement = null;
        // 初始化订单生成的操作流水记录
        OrderOperateStatement createOperateRecord = null;
        // 初始化订单支付的操作流水记录
        OrderOperateStatement payOperateRecord = null;
        // 初始化订单操作流水记录列表
        List<OrderOperateStatement> orderOperateRecordList;
        // 初始化订单支付流水对象列表
        List<OrderPayStatement> insertPayStatementList = null;
        // 初始化当次所有支付的总金额
        // BigDecimal orderMoneyAll = BigDecimal.ZERO;
        // 初始化是否含有余额支付的标志位
        // Boolean balancePayFlag = false;
        // 初始化是否含有优惠券支付的标志位
        // Boolean couponPayFlag = false;
        // 优惠券授权对象
        // CouponGrant couponGrant = null;
        // 余额支付的金额
        BigDecimal accountBalanceMoney = null;
        // 批量锁库存操作结果
        int batchHandleStockResult = -1;
        // 锁/解锁库存的标志位:1是锁库存；2是解锁库存
        int lockFlag = OrderInterFaceOperateTypeEnum.StockHandleType.batchLock.getKey();

        try {

            // 调用订单生成DAO得到订单的物理主键
            orderId = orderMainDAO.insertSelective(order);

            String commerceId = order.getCommerceId();

            if (orderId > 0) {
                // 带有明细ID的orderItem集合
                List<OrderItem> itemInsertList = new ArrayList<OrderItem>();
                // 组装订单明细实例
                for (int i = 0; i < orderItemList.size(); i++) {
                    OrderItem orderItem = orderItemList.get(i);
                    orderItem.setOrderCode(orderCode);
                    if (null != commerceId && !"".equals(commerceId)) { // 入驻
                        orderItem.setSupplierType(2L);
                    } else { // 自营
                        orderItem.setSupplierType(1L);
                    }
                    Long resultItemID = orderItemDAO.insertSelective(orderItem);
                    orderItem.setOrderItemId(resultItemID);
                    itemInsertList.add(orderItem);
                }
                // --------------------------------------记录下单操作的操作流水 begin
                // ------------------------------------------
                orderOperateRecordList = new ArrayList<OrderOperateStatement>();
                // 取得订单最近的操作流水记录
                OrderOperateStatement oldRecord = orderOperateStatementDAO.selectNewest(orderCode);
                // 如果没历史操作流水
                if (oldRecord != null) {
                    createOperateRecord =
                            orderOperateStatementService.newOrderOperate(oldRecord, orderCode,
                                    null, notPayOrderStatus, account, date, operateType,
                                    order.getAmountPayable(), orderInfo);
                } else {
                    createOperateRecord = new OrderOperateStatement();
                    createOperateRecord.setNowOperateDate(date);
                    createOperateRecord
                            .setNowOperateType((long) OrderDictionaryEnum.OrderOperateType.Create
                                    .getKey());
                    createOperateRecord.setNowOperator(account);
                    createOperateRecord.setNowOrderStatus(payOrderStatus);
                    createOperateRecord.setNowOrderSum(order.getAmountPayable());
                    createOperateRecord.setOrderCode(orderCode);
                    orderInfo = "您的奖品已经成功生成了订单，编号为" + orderCode + "，订单即将进入配送流程。";
                    createOperateRecord.setOperateInfo(orderInfo);

                    orderOperateRecordList.add(createOperateRecord);
                }
                orderOperateStatementDAO.insertSelective(createOperateRecord);
                // --------------------------------------记录下单操作的操作流水 end
                // ------------------------------------------

                // --------------------------------------记录下单操作的支付流水 begin
                // ------------------------------------------
                orderPayStatement = new OrderPayStatement();
                payMethod = new Long(OrderDictionaryEnum.OrderPayMethod.Prize.getKey());
                orderPayStatement.setCreateDate(date);
                orderPayStatement.setEndDate(new Date());
                orderPayStatement.setOrderCode(orderCode);
                orderPayStatement.setFlag((long) OrderDictionaryEnum.OrderPayFlag.Payment.getKey());
                orderPayStatement.setAccount(order.getCustomerAccount());
                orderPayStatement.setOrderMoney(BigDecimal.ZERO);
                orderPayStatement.setPaymentWay(payMethod);
                orderPayStatement.setState(new Long(OrderDictionaryEnum.OrderPayState.Success
                        .getKey()));

                orderPayStatementDAO.insert(orderPayStatement);
                // --------------------------------------记录下单操作的支付流水 end
                // ------------------------------------------

                // --------------------------------------调产品接口锁库存 begin
                // ------------------------------------------
                batchHandleStockResult =
                        settlementAndPayUtil.batchHandleStock(orderItemList, lockFlag, webSite);
                if (batchHandleStockResult == -1) {
                    throw new ServiceException(ErrorCode.INNER_SINGLE_ERROR, "奖品下单接口调用时锁库存异常，订单号："
                            + orderCode);
                }
                // --------------------------------------调产品接口锁库存 end
                // ------------------------------------------

                newOrder = orderMainDAO.selectByPrimaryKey(orderId);
                if(null == newOrder){
                    log.error("agencyCreateOrderForPrize 无orderId = "+orderId+"的订单数据");
                    throw new ServiceException("agencyCreateOrderForPrize 无orderId = "+orderId+"的订单数据");
                }
                if (newOrder != null && batchHandleStockResult > -1) {
                    result = newOrder.getOrderCode();
                }
                // 风控
                riskManagementService.addRiskJudgeTask(newOrder.getCommerceId(),
                        newOrder.getCommerceName(), newOrder.getOrderCode());

                // 抽奖订单同步订单信息到总部系统
              /*删除同步到总部会员功能  try {
                    List<String> lstOrderCode = new ArrayList<String>();
                    lstOrderCode.add(newOrder.getOrderCode());
                    orderQryService.syncOrderInfo2Base(lstOrderCode);
                } catch (Exception e) {
                    throw new ServiceException(ErrorCode.INNER_SINGLE_ERROR, "领奖订单同步订单信息到总部系统异常!");
                }*/
            }
        } catch (Exception ex) {
            log.error("奖品下单接口调用异常", ex);
            throw new ServiceException(ErrorCode.INNER_SINGLE_ERROR, "奖品下单接口调用异常："
                    + ex.getMessage());
        }

        return result;

    }
}
