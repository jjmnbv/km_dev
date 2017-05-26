package com.pltfm.app.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.km.framework.mq.Sender;
import com.km.framework.mq.bean.KmMsg;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.MessageConstants;
import com.kmzyc.promotion.app.vobject.OrderProduct;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.dao.CouponStatementDAO;
import com.pltfm.app.dao.OrderAlterDAO;
import com.pltfm.app.dao.OrderAlterOperateStatementDAO;
import com.pltfm.app.dao.OrderAlterPayStatementDAO;
import com.pltfm.app.dao.OrderAlterPhotoDAO;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.entities.CouponStatement;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.entities.OrderAlterOperateStatementExample;
import com.pltfm.app.entities.OrderAlterPayStatementExample;
import com.pltfm.app.entities.OrderAlterPhoto;
import com.pltfm.app.entities.OrderAlterPhotoExample;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderOperateStatement;
import com.pltfm.app.entities.OrderPreferential;
import com.pltfm.app.service.FareService;
import com.pltfm.app.service.OrderAlterOperateStatementService;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.service.OrderPayStatementService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.service.OrderReturnService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.SysConstants;
import com.pltfm.app.util.export.SellerAlterOrderExportService;
import com.pltfm.app.vobject.OrderAlterOperateStatementSaveVo;
import com.pltfm.app.vobject.OrderAlterVo;
import com.pltfm.sys.util.ErrorCode;

/* import com.kmzyc.mailmobile.vo.EmailReturnGoodsVO; */
/* 删除总部会员对接 import com.pltfm.remote.service.Sync2BaseRemoteService; */

@Service("orderReturnService")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class OrderReturnServiceImpl extends BaseService implements OrderReturnService {
    private static Logger logger = Logger.getLogger(OrderReturnServiceImpl.class);
    @Resource
    private OrderMainDAO orderMainDAO;
    @Resource
    private OrderAlterDAO orderAlterDAO;
    @Resource
    private CouponStatementDAO couponStatementDAO;
    @Resource
    private OrderAlterOperateStatementDAO orderAlterOperateStatementDAO;
    @Resource
    private OrderAlterPayStatementDAO orderAlterPayStatementDAO;
    @Resource
    private OrderAlterPhotoDAO orderAlterPhotoDAO;
    @Resource
    private OrderQryService orderQryService;
    @Resource
    private OrderItemQryService orderItemQryService;
    @Resource
    private OrderAlterOperateStatementService orderAlterOperateStatementService;
    @Resource
    private OrderPayStatementService orderPayStatementService;
    @Resource
    FareService fareService;
    @Resource
    private JmsTemplate jmsTemplate;
    @Resource
    private javax.jms.Destination destination;
    @Resource
    private OrderReturnService orderReturnService;
    /*
     * 删除邮件业务 @Resource private EmailSubscriptionRemoteService emailSubscriptionRemoteService;
     */
    // 产品系统的优惠接口服务类
    @Resource
    private CouponRemoteService couponRemoteService;

    /*
     * 删除总部会员对接 @Resource private Sync2BaseRemoteService sync2BaseRemoteService;
     */
    @Resource
    private OrderOperateStatementService orderOperateStatementService;


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final DecimalFormat amountformat = new DecimalFormat("#####0.00");

    @Override
    public Map compute(String orderCode, Long orderItemId, Long alterNum) throws ServiceException {
        try {
            Map map = new HashMap();
            if (orderItemQryService.isSuit(orderCode)) {
                map.put("msg", "isSuit");
                return map;
            }
            OrderMain om = orderQryService.getRootOrderByCode(orderCode);
            OrderAlter oa = new OrderAlter();
            oa.setOrderCode(orderCode);
            oa.setOrderItemId(orderItemId);
            oa.setAlterNum(alterNum);
            compute(om, oa);
            map.put("total", oa.getFareSubsidy().add(oa.getRuturnSum()));
            map.put("fareAdditional", oa.getFareSubsidy());
            map.put("returnSum", oa.getRuturnSum().compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO
                    : oa.getRuturnSum());
            return map;
        } catch (Exception e) {
            logger.error("退换货计算发生错误", e);
            // log.error(e);
            throw new ServiceException(ErrorCode.INNER_RETURNS_ERROR,
                    "退换货计算发生错误：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int alter(OrderAlter oa) throws ServiceException {
        OrderMain om = orderQryService.getRootOrderByCode(oa.getOrderCode());
        try {
            if (checkIsReturning(oa.getOrderItemId())) {// 是否退货中
                logger.error("退换货编号：" + oa.getOrderAlterCode() + "正在退货中");
                throw new Exception();
            }
            oa.setProposeStatus((short) OrderAlterDictionaryEnum.Propose_Status.Audit.getKey());
            oa.setCreateDate(new Date());
            if (oa.getAlterType() == OrderAlterDictionaryEnum.AlterTypes.Return.getKey()
                    || oa.getAlterType() == OrderAlterDictionaryEnum.AlterTypes.Refund.getKey()
                    || oa.getAlterType() == OrderAlterDictionaryEnum.AlterTypes.Indemnity
                            .getKey()) {// 退货
                // TO-DO 计算退款额
                compute(om, oa);
                oa.setFareSubsidy(null);// 清空补贴运费
            }
            String oaCode =
                    orderAlterOperateStatementService.saveOrderAlter(oa, om.getCustomerId());
            if (null != oaCode) {
                // 记录退换货单操作流水
                saveCreateOperateStatement(oa, oaCode);

                return 1;
            }
            return 0;
        } catch (Exception e) {
            logger.error("退换货申请发生错误", e);
            throw new ServiceException(ErrorCode.INTER_RETURNS_ERROR,
                    "退换货申请发生错误：" + e.getMessage());
        }
    }

    private void saveCreateOperateStatement(OrderAlter oa, String oaCode) throws ServiceException {
        OrderAlterOperateStatementSaveVo vo = new OrderAlterOperateStatementSaveVo();
        vo.setOrderAlterCode(oaCode);
        vo.setStatus((long) oa.getProposeStatus());
        vo.setOperator(oa.getProposer());
        vo.setDate(new Date());
        vo.setType((long) OrderAlterDictionaryEnum.OrderAlterOperateType.Create.getKey());
        vo.setOrderSum(oa.getRuturnSum());
        if (oa.getAlterType() == OrderAlterDictionaryEnum.AlterTypes.Indemnity.getKey()) {
            vo.setInfo("超时未发货赔付申请");
        } else {
            vo.setInfo("退换货申请");
        }
        orderAlterOperateStatementService.save(vo);
    }

    @Override
    public Map compute(OrderMain om, OrderAlter oa) throws ServiceException {
        Map map = new HashMap();
        // 预售申请赔付 开始
        if (oa.getAlterType() != null && oa.getAlterType()
                .intValue() == OrderAlterDictionaryEnum.AlterTypes.Indemnity.getKey()) {
            oa.setFareSubsidy(BigDecimal.ZERO);
            oa.setRuturnMoney(om.getAmountPayable());
            oa.setDeposit(om.getDepositSum());
            oa.setFinalmoney(om.getAmountPayable().subtract(om.getDepositSum()));
            oa.setCompensate(om.getDepositSum());
            oa.setRuturnSum(om.getAmountPayable().add(om.getDepositSum()));
            return map;
        } else {
            if (orderItemQryService.isSuit(om.getOrderCode())) {
                oa.setFareSubsidy(BigDecimal.ZERO);
                oa.setRuturnMoney(BigDecimal.ZERO);
                oa.setRuturnSum(BigDecimal.ZERO);
                return map;
            }
            BigDecimal fare = om.getFare();// 原邮费
            OrderItem oi = orderItemQryService.getOrderItemById(oa.getOrderItemId());

            BigDecimal returnMoney = BigDecimal.ZERO;
            if (null != oi) {
                BigDecimal unitIncoming = oi.getCommodityUnitIncoming() == null ? BigDecimal.ZERO
                        : oi.getCommodityUnitIncoming();
                long alterNum = oa.getAlterNum() == null ? 0L : oa.getAlterNum();
                returnMoney = unitIncoming.multiply(new BigDecimal(alterNum));
            }
            List<OrderItem> oilist = orderItemQryService.listOrderItems(om.getOrderCode());
            List<OrderAlterVo> oalist;// 历史退货记录
            try {
                oalist = orderAlterDAO.selectHistory(om.getOrderCode());
            } catch (SQLException e1) {
                logger.error("查询历史退货记录发生错误", e1);
                throw new ServiceException(0, "");
            }
            BigDecimal a = om.getCommoditySum();// 原总额
            BigDecimal c = (om.getAmountPayable()).subtract(fare);// 原除邮费应付
            BigDecimal d;// 原优惠券总额
            map.put("orderCode", om.getOrderCode());
            map.put("payway", OrderDictionaryEnum.OrderPayMethod.Coupon.getKey());
            try {
                d = orderPayStatementService.getOrderPay(map);
            } catch (Exception e) {
                logger.error("退换货计算发生错误", e);
                throw new ServiceException(ErrorCode.INNER_RETURNS_ERROR,
                        "退换货计算发生错误：" + e.getMessage());
            }
            BigDecimal na;// 现总额
            BigDecimal nb;// 现优惠额
            BigDecimal oc = BigDecimal.ZERO;// 历史已退金额
            BigDecimal nc;// 现应付
            BigDecimal nd;// 现优惠券总额
            try {
                List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
                OrderProduct op;
                Map<Long, Long> tmap = new HashMap<Long, Long>();
                for (OrderItem toi : oilist) {
                    for (OrderAlterVo toa : oalist) {
                        if (toa.getSkuCode().equals(toi.getCommoditySku())) {
                            toi.setCommodityNumber(toi.getCommodityNumber().longValue()
                                    - toa.getAlterNum().longValue());
                            oc = oc.add(toa.getRuturnMoney());
                            break;
                        }
                    }
                    if (oi.getCommoditySku().equals(toi.getCommoditySku())) {
                        toi.setCommodityNumber(toi.getCommodityNumber().longValue()
                                - oa.getAlterNum().longValue());
                    }
                    if (toi.getCommodityNumber() != 0) {
                        op = new OrderProduct();
                        if (null != toi.getSuitId()) {// 套餐
                            if (null == tmap.get(toi.getOrderItemId())) {
                                op.setSuitId(toi.getSuitId());
                                // 原始价格 没有活动时的价格
                                op.setCostPrice(toi.getCommodityCalledPrice());
                                op.setAmount(toi.getCommodityNumber().intValue());
                                orderProductList.add(op);
                                tmap.put(toi.getOrderItemId(), toi.getSuitId());
                            }
                        } else {// 普通产品
                            op.setProductSkuCode(toi.getCommoditySku());
                            // 原始价格 没有活动时的价格
                            op.setCostPrice(toi.getCommodityCalledPrice());
                            op.setAmount(toi.getCommodityNumber().intValue());
                            orderProductList.add(op);
                        }
                    }
                }
                if (0 == orderProductList.size()) {
                    na = BigDecimal.ZERO;// 现总金额
                    nb = BigDecimal.ZERO;// 减免金额
                    nc = BigDecimal.ZERO;// 现应付
                } else {
                    // PromotionInfoRemoteService prs =
                    // (PromotionInfoRemoteService)
                    // RemoteTool.getRemote(SysConstants.PROMOTION_SYSCODE,
                    // "promotionInfoRemoteService");
                    // ProductPromotionInfo pp =
                    // prs.selectChannelPromotionByOrder(orderProductList, om.getCreateDate(),
                    // om.getOrderChannel());
                    map.put("pList", null);
                    // na = pp.getAllMoney();// 现总金额
                    // nb = pp.getReductionMoney();// 减免金额
                    // nc = pp.getAllMoney().subtract(pp.getReductionMoney());// 现应付
                    na = oi.getCommodityUnitPrice() != null ? oi.getCommodityUnitPrice()
                            : new BigDecimal(0);
                    nc = oi.getCommodityUnitIncoming() != null ? oi.getCommodityUnitIncoming()
                            : new BigDecimal(0);
                    nb = na.subtract(nc);
                }
            } catch (Exception e) {
                logger.error("退换货计算发生错误", e);
                throw new ServiceException(ErrorCode.INNER_RETURNS_ERROR,
                        "退换货计算发生错误：" + e.getMessage());
            }
            nd = d.multiply(na).divide(a, SysConstants.DEE_DIV_SCALE_INT, BigDecimal.ROUND_HALF_UP);// 现优惠券支付额
            oa.setPreferentialAmount(d.subtract(nd));// 优惠券退回
            oa.setDiscountAmount(nb);
            BigDecimal weight = null == om.getWeight() ? BigDecimal.ONE
                    : om.getWeight().divide(BigDecimal.valueOf(1000L));
            BigDecimal nfare = fareService.getFare(1l, nc, weight,
                    null == om.getProvince() ? Boolean.FALSE : (om.getProvince().indexOf("广东") > 0),
                    om.getOrderChannel());// 现邮费
            Integer num = orderAlterOperateStatementService.selectFareAdditional(om.getOrderCode());
            if (0.00 == fare.doubleValue()) {// 是否免运费
                if (num > 0) {// 以前是否补回免运费
                    oa.setFareSubsidy(BigDecimal.ZERO);
                    // 本次是否补回免运费
                    oa.setFareAdditional((short) OrderAlterDictionaryEnum.Whether.YES.getKey());
                } else {
                    oa.setFareSubsidy(nfare);
                    // 本次是否补回免运费
                    oa.setFareAdditional((short) (0.00 == nfare.doubleValue()
                            ? OrderAlterDictionaryEnum.Whether.NO.getKey()
                            : OrderAlterDictionaryEnum.Whether.YES.getKey()));
                }
                // oa.setRuturnMoney(c.subtract(oc).subtract(nc).subtract(nfare).subtract(d).add(nd));//
                // 商品应退金额
                // oa.setRuturnMoney(c.subtract(oc).subtract(nc).subtract(nfare).subtract(d));//
                // 商品应退金额
                oa.setRuturnMoney(returnMoney);
            } else {
                oa.setFareSubsidy(BigDecimal.ZERO);
                oa.setFareAdditional((short) OrderAlterDictionaryEnum.Whether.YES.getKey());
                // oa.setRuturnMoney(c.subtract(oc).subtract(nc).subtract(d).add(nd));//
                // 商品应退金额
                // oa.setRuturnMoney(c.subtract(oc).subtract(nc).subtract(d));// 商品应退金额
                oa.setRuturnMoney(returnMoney);
            }
            oa.setRuturnSum(oa.getRuturnMoney());// 总金额
            if (oa.getRuturnMoney().compareTo(BigDecimal.ZERO) < 0) {
                oa.setRuturnMoney(BigDecimal.ZERO);
                oa.setFareSubsidy(BigDecimal.ZERO);
                oa.setRuturnSum(BigDecimal.ZERO);
            }
            // 如果为预售商品 ，设置定金和尾款字段
            if (OrderDictionaryEnum.Order_Type.YsOrder.getKey() == om.getOrderType().intValue()) {
                if (oi.getDeposit() != null && oi.getCommodityUnitIncoming() != null
                        && oa.getAlterNum() != null) {
                    oa.setDeposit(oi.getDeposit().multiply(new BigDecimal(oa.getAlterNum())));
                    oa.setFinalmoney((oi.getCommodityUnitIncoming().subtract(oi.getDeposit()))
                            .multiply(new BigDecimal(oa.getAlterNum())));
                }
            }
        }
        return map;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public void alterCK(String operator, String alterCode, Short type, BigDecimal fareSubsidy,
            BigDecimal returnMoney, BigDecimal returnFare, BigDecimal returnSum,
            BigDecimal preferentialAmount, String auditComment) throws ServiceException {
        OrderAlter oa = orderAlterOperateStatementService.getOrderAlterByCode(alterCode);
        Date date = new Date();
        // 订单号
        String orderCode = oa.getOrderCode();

        if (oa.getProposeStatus().intValue() != OrderAlterDictionaryEnum.Propose_Status.Audit
                .getKey()
                && oa.getProposeStatus()
                        .intValue() != OrderAlterDictionaryEnum.Propose_Status.ExchangeToReturn
                                .getKey()) {
            throw new ServiceException(ErrorCode.INNER_RETURNS_ERROR, "状态不是待审核");
        }
        oa.setCheckDate(date);
        oa.setChecker(operator);
        oa.setAuditComment(auditComment);// 审核说明
        if (type.intValue() == OrderAlterDictionaryEnum.Propose_Status.Pass.getKey()) {// 通过
            oa.setFareSubsidy(null == fareSubsidy ? BigDecimal.ZERO : fareSubsidy);// 运费补贴
            oa.setRuturnMoney(null == returnMoney ? BigDecimal.ZERO : returnMoney);// 商品应退金额
            oa.setReturnFare(null == returnFare ? BigDecimal.ZERO : returnFare);// 退货返运费
            // oa.setRuturnSum(oa.getFareSubsidy().add(oa.getRuturnMoney()));// 总金额
            oa.setRuturnSum(null == returnSum ? BigDecimal.ZERO : returnSum);// 总金额
            oa.setPreferentialAmount(
                    null == preferentialAmount ? BigDecimal.ZERO : preferentialAmount);// 返回优惠券面值
            if (oa.getProposeStatus()
                    .intValue() == OrderAlterDictionaryEnum.Propose_Status.ExchangeToReturn.getKey()
                    || oa.getAlterType().intValue() == OrderAlterDictionaryEnum.AlterTypes.Refund
                            .getKey()) {
                // 换货转退货或不退货退款
                oa.setProposeStatus(
                        (short) OrderAlterDictionaryEnum.Propose_Status.F_Backpay.getKey());// 已通过待退款
            } else {
                oa.setProposeStatus((short) OrderAlterDictionaryEnum.Propose_Status.Pass.getKey());// 已通过待退货
                try {
                    OrderMain om = orderQryService.getOrderByCode(oa.getOrderCode());
                    UserInfoDO userInfoDO =
                            orderMainDAO.queryUserInfo(om.getCustomerId().longValue());
                    Map<String, String> CommerceInfo = null;
                    String concact = null;
                    if (null != om.getCommerceId()) {
                        CommerceInfo = orderQryService.queryCommerceInfo(om.getCommerceId());
                        if (null != CommerceInfo) {
                            String mobile = CommerceInfo.get("MOBILE");
                            String telphone = CommerceInfo.get("PHONE");
                            concact = (null == mobile ? "" : mobile)
                                    + ((null != mobile && mobile.length() > 0 && null != telphone
                                            && telphone.length() > 0) ? "/" : "")
                                    + (null == telphone ? "" : telphone);
                        }
                    }

                    List<Long> uidList = new ArrayList<Long>();
                    uidList.add(Long.valueOf(userInfoDO.getLoginId()));
                    List<String> moblieList = new ArrayList();
                    moblieList.add(userInfoDO.getMobile());
                    KmMsg kmMsg = new KmMsg("6000", false);
                    kmMsg.getMsgData().put("kmMsgType", MessageConstants.KMMSG_MOBIL);
                    kmMsg.getMsgData().put("smsmailType", EmailSendType.MSGRETURNGOODS.getStatus());
                    kmMsg.getMsgData().put("systemType", MessageConstants.KMMSG_SYSTEM_TYPE_B2B);
                    kmMsg.getMsgData().put("mobilePhones", moblieList);
                    kmMsg.getMsgData().put("msgSendType", MessageConstants.EM_SEND_TYPE_IMM);
                    kmMsg.getMsgData().put("uidList", uidList);
                    kmMsg.getMsgData().put("returnOrderCode", oa.getOrderAlterCode());
                    if (null != om.getCommerceId()) {
                        kmMsg.getMsgData().put("businessPhone", concact);// 电话号
                        kmMsg.getMsgData().put("businessType", null == concact ? 3 : 2);// 类型1:自营2:商家3:商家没有手机号
                    } else {
                        kmMsg.getMsgData().put("businessPhone", SysConstants.MARKET_CONCACTS);// 电话号
                        kmMsg.getMsgData().put("businessType", 1);// 类型1:自营2:商家3:商家没有手机号
                    }
                    Sender.send(kmMsg, destination, jmsTemplate);
                    logger.info("退换货审核发送短信结果：成功" + "手机号码：" + userInfoDO.getMobile());
                } catch (Exception e) {
                    logger.error("退换货发送短信邮件发生错误", e);
                }
            }
            orderAlterOperateStatementService.updateOrderAlter(oa);
            // 记录退换货单操作流水
            saveAuditOperateStatement(operator, alterCode, oa);

            if (oa.getAlterType().intValue() == OrderAlterDictionaryEnum.AlterTypes.Return.getKey()
                    || oa.getAlterType().intValue() == OrderAlterDictionaryEnum.AlterTypes.Refund
                            .getKey()) {
                // 退货、不退货退款
                // 冻结订单所送优惠券
                OrderMain om = orderQryService.getRootOrderByCode(oa.getOrderCode());
                List<OrderPreferential> preferentialList =
                        orderPayStatementService.getPreferentialByOrderCode(om.getOrderCode(),
                                SysConstants.PROMOTION_TYPE_GIFT);
                if (ListUtils.isNotEmpty(preferentialList)) {
                    try {
                        UserInfoDO userInfo = new UserInfoDO();
                        userInfo.setLoginId(om.getCustomerId().intValue());
                        CouponStatement cs = new CouponStatement();
                        cs.setOrderCode(om.getOrderCode());
                        cs.setOrderAlterCode(oa.getOrderAlterCode());
                        // 常量
                        cs.setStatus(SysConstants.COUPON_FREEZE.shortValue());
                        boolean flag = true;
                        for (OrderPreferential preferential : preferentialList) {
                            flag = true;
                            if (preferential
                                    .getOrderPreferentialType() == SysConstants.PROMOTION_TYPE_GIFT
                                            .intValue()) {
                                flag = false;
                            }
                            if (flag) {
                                Long couponState =
                                        orderPayStatementService.queryCouponGrantByCounponGrantId(
                                                preferential.getGrantId().longValue());
                                if (couponState != null && !couponState.equals(6L)) {// 优惠券的状态为6已作废，则不冻结订单
                                    // 优惠券接口
                                    couponRemoteService.changeCustomGrantToGive(userInfo,
                                            Long.valueOf(preferential.getCouponId()),
                                            om.getCustomerId().longValue(),
                                            SysConstants.COUPON_FREEZE, om.getOrderCode(),
                                            preferential.getGrantId().longValue());
                                    // 记录冻结
                                    cs.setCouponId(preferential.getCouponId());
                                    cs.setGrantId(preferential.getGrantId());
                                    couponStatementDAO.insert(cs);
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error("冻结订单所送优惠券异常", e);
                        throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR,
                                "冻结订单所送优惠券异常：" + e.getMessage());
                    }
                } else {

                }
            }

            // 退换货MQ推送
            /*
             * 删除同步总部会员功能 try { List<String> lstOrderAlterCode = new ArrayList<String>();
             * lstOrderAlterCode.add(alterCode);
             * orderReturnService.syncAlterOrderInfo2Base(lstOrderAlterCode); } catch (Exception e)
             * { logger.error("退换货同步订单信息到总部系统异常!");
             * 
             * throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_RETURNS_ERROR,
             * "退换货同步订单信息到总部系统异常：" + e.getMessage()); }
             */
        } else if (type.intValue() == OrderAlterDictionaryEnum.Propose_Status.Veto.getKey()) {// 驳回
            oa.setProposeStatus((short) OrderAlterDictionaryEnum.Propose_Status.Veto.getKey());
            oa.setFinishDate(date);
            orderAlterOperateStatementService.updateOrderAlter(oa);
            saveAuditOperateStatement(operator, alterCode, oa);
        } else {
            throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR, "退换货审核失败");
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int alterCKYS(String operator, String alterCode, Short type, String auditComment)
            throws ServiceException {
        // 超时未发货赔付审核

        OrderAlter oa = orderAlterOperateStatementService.getOrderAlterByCode(alterCode);
        Date date = new Date();

        if (oa.getProposeStatus().intValue() != OrderAlterDictionaryEnum.Propose_Status.Audit
                .getKey()) {
            throw new ServiceException(ErrorCode.INNER_RETURNS_ERROR, "状态不是待审核");
        }
        oa.setCheckDate(date);
        oa.setChecker(operator);
        oa.setAuditComment(auditComment);// 审核说明
        if (type.intValue() == OrderAlterDictionaryEnum.Propose_Status.Pass.getKey()) {
            // 通过 增加验证判断是否已配送
            OrderMain omain;
            try {
                omain = orderMainDAO.selectByOrderCode(oa.getOrderCode());
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR,
                        "超时未发货赔付审核---查询订单状态异常" + e);
            }
            // 等于已配送不能审核通过
            if (omain.getOrderStatus() == OrderDictionaryEnum.Order_Status.Ship_Done.getKey()) {
                return -1;
            }
            oa.setProposeStatus((short) OrderAlterDictionaryEnum.Propose_Status.F_Backpay.getKey());// 已通过待退款
            orderAlterOperateStatementService.updateOrderAlter(oa);
            // 更改订单状态为已完成
            orderAlterOperateStatementService.updateOrderMainStatus(oa.getOrderCode());
            // 记录超时未发货赔付操作流水
            saveAuditOperateStatement(operator, alterCode, oa);

            // 插入一条订单操作流水
            OrderOperateStatement oost = new OrderOperateStatement();
            oost.setNowOperator(operator);
            oost.setNowOperateType((long) 9);
            oost.setNowOperateDate(new Date());
            oost.setNowOrderStatus((long) OrderDictionaryEnum.Order_Status.Order_Done.getKey());
            oost.setOperateInfo("买家已确认收货!");
            oost.setOrderCode(oa.getOrderCode());
            orderOperateStatementService.InsertOperate(oost);

            return 1;

            // 退换货MQ推送,超时审核暂时不推
            /*
             * try { List<String> lstOrderAlterCode = new ArrayList<String>();
             * lstOrderAlterCode.add(alterCode);
             * orderReturnService.syncAlterOrderInfo2Base(lstOrderAlterCode); } catch (Exception e)
             * { logger.error("退换货同步订单信息到总部系统异常!");
             * 
             * throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_RETURNS_ERROR,
             * "退换货同步订单信息到总部系统异常：" + e.getMessage()); }
             */
        } else if (type.intValue() == OrderAlterDictionaryEnum.Propose_Status.Veto.getKey()) {
            // 驳回
            oa.setProposeStatus((short) OrderAlterDictionaryEnum.Propose_Status.Veto.getKey());
            oa.setFinishDate(date);
            orderAlterOperateStatementService.updateOrderAlter(oa);
            saveAuditOperateStatement(operator, alterCode, oa);

            return 1;
        } else {
            throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR, "超时未发货赔付审核失败");
        }
    }

    private void saveAuditOperateStatement(String operator, String alterCode, OrderAlter oa)
            throws ServiceException {
        OrderAlterOperateStatementSaveVo vo = new OrderAlterOperateStatementSaveVo();
        vo.setOrderAlterCode(alterCode);
        vo.setStatus((long) oa.getProposeStatus());
        vo.setOperator(operator);
        vo.setDate(new Date());
        vo.setType((long) OrderAlterDictionaryEnum.OrderAlterOperateType.Audit.getKey());
        vo.setOrderSum(oa.getRuturnSum());
        if (oa.getAlterType().intValue() == 4)
            // 待改了枚举类的后可用枚举类代替
            vo.setInfo("超时未发货审核");
        else
            vo.setInfo("退换货审核");
        orderAlterOperateStatementService.save(vo);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public Integer listCount(Map<String, Object> map) throws ServiceException {
        try {
            return orderAlterDAO.countByMap(map);
        } catch (Exception e) {
            logger.error("获取退换货列表计数异常", e);
            throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR,
                    "获取退换货列表计数异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public List listAlter(Map<String, Object> map) throws ServiceException {
        try {
            return orderAlterDAO.selectByMap(map);
        } catch (Exception e) {
            logger.error("获取退换货列表异常", e);
            throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR,
                    "获取退换货列表异常：" + e.getMessage());
        }
    }

    // 是否退换货中
    private Boolean checkIsReturning(Long orderItemId) throws ServiceException {
        try {
            return orderAlterDAO.checkIsReturning(orderItemId);
        } catch (SQLException e) {
            logger.error("检查是否退换货中异常", e);
            throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR,
                    "检查是否退换货中异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public List listOrderAlterOperates(String alterCode) throws ServiceException {
        try {
            OrderAlterOperateStatementExample example = new OrderAlterOperateStatementExample();
            example.setOrderByClause("STATEMENT_Id ASC");
            OrderAlterOperateStatementExample.Criteria criteria = example.createCriteria();
            criteria.andOrderAlterCodeEqualTo(alterCode);
            return orderAlterOperateStatementDAO.selectByExample(example);
        } catch (Exception e) {
            logger.error("查询退换货单操作异常", e);
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_RETURNS_ERROR,
                    "查询退换货单操作异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public List listOrderAlterPays(String OrderAlterCode) throws ServiceException {
        try {
            OrderAlterPayStatementExample example = new OrderAlterPayStatementExample();
            example.setOrderByClause("PAY_STATEMENT_NO ASC");
            OrderAlterPayStatementExample.Criteria criteria = example.createCriteria();
            criteria.andOrderAlterCodeEqualTo(OrderAlterCode);
            return orderAlterPayStatementDAO.selectByExample(example);
        } catch (Exception e) {
            logger.error("查询退换货单支付操作异常", e);
            throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR,
                    "查询退换货单支付操作异常：" + e.getMessage());
        }
    }

    @Override
    public int savaPhoto(OrderAlterPhoto photo) throws ServiceException {
        try {
            orderAlterPhotoDAO.insert(photo);
            return 1;
        } catch (SQLException e) {
            throw new ServiceException(ErrorCode.INNER_RETURNS_ERROR,
                    "退换货保存图片发生错误：" + e.getMessage());
        }
    }

    @Override
    public List getPhotoByBatchNo(String photoBatchNo) throws ServiceException {
        if (null == photoBatchNo || "".equals(photoBatchNo)) {
            logger.error("方法getPhotoByBatchNo传入的photoBatchNo为空");
            return null;
        }
        OrderAlterPhotoExample example = new OrderAlterPhotoExample();
        OrderAlterPhotoExample.Criteria criteria = example.createCriteria();
        criteria.andBatchNoEqualTo(photoBatchNo);
        try {
            return orderAlterPhotoDAO.selectByExample(example);
        } catch (Exception e) {
            logger.error("获取退换货图片发生错误", e);
            throw new ServiceException(ErrorCode.INNER_RETURNS_ERROR,
                    "获取退换货图片发生错误：" + e.getMessage());
        }
    }

    @Override
    public String exportSellerAlterOrders(Map<String, Object> params) throws ServiceException {
        List alterOrderList = null;
        StringBuffer filePath = new StringBuffer(100);
        String strEndDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            alterOrderList = orderAlterDAO.querySellerAlterOrderExportData(params);
            if (alterOrderList == null || alterOrderList.isEmpty()) {
                return "NO_DATA";
            }

            filePath.append("/report/").append("退换货订单_");

            // 获取订单完成结束时间，如果为空字符串，则取完成时间的结束时间
            strEndDate =
                    params.containsKey("createEnd") ? String.valueOf(params.get("createEnd")) : "";

            if (StringUtils.isNotBlank(strEndDate)) {
                Calendar endCal = Calendar.getInstance();
                endCal.setTime(dateFormat.parse(strEndDate));
                filePath.append(endCal.get(Calendar.YEAR)).append("年");
                filePath.append(endCal.get(Calendar.MONTH) + 1).append("月");
                filePath.append(endCal.get(Calendar.DAY_OF_MONTH)).append("日");
                filePath.append("_");
            }

            // 添加唯一随机数
            filePath.append(UUID.randomUUID()).append(".xls");

            String excelPath = ConfigurationUtil.getString("path");
            String visitPath = ConfigurationUtil.getString("visitPath");

            File file = new File(excelPath.concat(filePath.toString()));
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            SellerAlterOrderExportService exportService = new SellerAlterOrderExportService();
            exportService.exportExcel(excelPath.concat(filePath.toString()), "退换货订单导出",
                    alterOrderList, params);
            return visitPath.concat(filePath.toString());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR,
                    "导出供应商退换货订单错误".concat(ex.getMessage()));
        }
    }

    /*
     * 删除总部会员对接 @Override public int syncAlterOrderInfo2Base(List<String> lstAlterCode) throws
     * ServiceException { int result = 0; JSONObject json = null;
     * 
     * try {
     * 
     * // 查询需要同步的退换货订单信息 List<OrderAlter> lstInfos =
     * this.orderAlterDAO.queryAlterOrderInfoSync2Base(lstAlterCode); if (lstInfos != null &&
     * lstInfos.size() > 0) { for (OrderAlter temp : lstInfos) { json = new JSONObject();
     * json.put("cardNum", StringUtils.defaultIfBlank(temp.getCardNum(), "")); json.put("orderCode",
     * StringUtils.defaultIfBlank(temp.getOrderCode(), "")); json.put("orderAlterCode",
     * StringUtils.defaultIfBlank(temp.getOrderAlterCode(), "")); json.put("alterType",
     * StringUtils.defaultIfBlank(String.valueOf(temp.getAlterType()), ""));
     * json.put("commoditySKU", StringUtils.defaultIfBlank(temp.getCommoditySKU(), ""));
     * json.put("commodityTitle", StringUtils.defaultIfBlank(temp.getCommodityTitle(), ""));
     * json.put("alterComment", StringUtils.defaultIfBlank(temp.getAlterComment(), ""));
     * json.put("auditComment", StringUtils.defaultIfBlank(temp.getAuditComment(), ""));
     * json.put("ruturnMoney", temp.getRuturnMoney() == null ? "" :
     * amountformat.format(temp.getRuturnMoney())); json.put("returnfare", temp.getReturnFare() ==
     * null ? "" : amountformat.format(temp.getReturnFare())); json.put("subsidy",
     * temp.getFareSubsidy() == null ? "" : amountformat.format(temp.getFareSubsidy()));
     * json.put("createDate", temp.getCreateDate() == null ? "" :
     * dateFormat.format(temp.getCreateDate())); json.put("checkDate", temp.getCheckDate() == null ?
     * "" : dateFormat.format(temp.getCheckDate())); // 超时审核添加
     * 
     * json.put("deposit", temp.getDeposit() == null ? "" : amountformat.format(temp
     * .getDeposit())); json.put("finalmoney", temp.getFinalmoney() == null ? "" :
     * amountformat.format(temp .getFinalmoney())); json.put("compensate", temp.getCompensate() ==
     * null ? "" : amountformat.format(temp .getCompensate()));
     * 
     * 
     * // 同步 sync2BaseRemoteService.pushAlterOrderBaseDockData(json.toJSONString()); } } result = 1;
     * } catch (Exception e) { logger.error("同步退换货订单发生异常错误：" + e.getMessage()); throw new
     * ServiceException(ErrorCode.SYNC_ORDER_ALTER_2_BASE_ERROR, "同步退换货订单发生异常错误：" + e.getMessage());
     * } return result; }
     */
}
