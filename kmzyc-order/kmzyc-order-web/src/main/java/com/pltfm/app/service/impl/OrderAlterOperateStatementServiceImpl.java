package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.km.framework.common.util.RemoteTool;
import com.kmzyc.b2b.model.QueryResult;
import com.kmzyc.b2b.service.QryOrderOnLineService;
import com.kmzyc.b2b.service.RefundOrderService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.remote.ExpressSubscriptionRemoteService;
import com.kmzyc.framework.constants.RefundResultCode;
import com.kmzyc.product.remote.service.StockRemoteService;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.user.remote.service.UserGrowingService;
import com.pltfm.app.dao.CouponStatementDAO;
import com.pltfm.app.dao.OrderAlterDAO;
import com.pltfm.app.dao.OrderAlterDetailDAO;
import com.pltfm.app.dao.OrderAlterOperateStatementDAO;
import com.pltfm.app.dao.OrderAlterPayStatementDAO;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.dao.OrderPayStatementDAO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.entities.CouponStatement;
import com.pltfm.app.entities.CouponStatementExample;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.entities.OrderAlterDetail;
import com.pltfm.app.entities.OrderAlterDetailExample;
import com.pltfm.app.entities.OrderAlterOperateStatement;
import com.pltfm.app.entities.OrderAlterOperateStatementExample;
import com.pltfm.app.entities.OrderAlterPayStatement;
import com.pltfm.app.entities.OrderAlterPayStatementExample;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.entities.OrderPayStatementExample;
import com.pltfm.app.entities.OrderPreferential;
import com.pltfm.app.service.OrderAlterOperateStatementService;
import com.pltfm.app.service.OrderAlterPayStatementService;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.app.service.OrderPayStatementService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.service.OrderReturnService;
import com.pltfm.app.service.RefundRequestService;
import com.pltfm.app.util.Constants;
import com.pltfm.app.util.OrderAlterCodeUtil;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.SysConstants;
import com.pltfm.app.util.pay.OrderAlterPayBackEnum;
import com.pltfm.app.vobject.Address;
import com.pltfm.app.vobject.AftersaleReturnOrder;
import com.pltfm.app.vobject.OrderAlterOperateStatementSaveVo;
import com.pltfm.app.vobject.OrderPayStatementVo;
import com.pltfm.app.vobject.RefundRequest;
import com.pltfm.sys.util.ErrorCode;

@Service("orderAlterOperateStatementService")
@Scope("singleton")
@SuppressWarnings({"unchecked", "unused"})
public class OrderAlterOperateStatementServiceImpl extends BaseService
        implements OrderAlterOperateStatementService {
    private static Logger logger = Logger.getLogger(OrderAlterOperateStatementServiceImpl.class);
    @Resource
    private OrderMainDAO orderMainDAO;
    @Resource
    private OrderAlterDAO orderAlterDAO;
    @Resource
    private CouponStatementDAO couponStatementDAO;
    @Resource
    private OrderAlterDetailDAO orderAlterDetailDAO;
    @Resource
    private OrderAlterOperateStatementDAO orderAlterOperateStatementDAO;
    @Resource
    private OrderAlterPayStatementDAO orderAlterPayStatementDAO;
    @Resource
    private OrderQryService orderQryService;
    @Resource
    private OrderItemQryService orderItemQryService;
    @Resource
    private OrderAlterPayStatementService orderAlterPayStatementService;
    @Resource
    private OrderPayStatementService orderPayStatementService;
    @Resource
    OrderPayStatementDAO orderPayStatementDAO;
    @Resource
    private RefundRequestService refundRequestService;

    @Resource
    private ExpressSubscriptionRemoteService expressSubscriptionRemoteService;

    // 产品系统的库存接口
    @Resource
    private StockRemoteService stockRemoteService;

    @Resource
    private QryOrderOnLineService qryOrderOnLineService;


    @Resource
    private RefundOrderService refundOrderService;
    // 产品系统的优惠接口服务类
    @Resource
    private CouponRemoteService couponRemoteService;
    // 客户系统的积分和等级的服务类
    @Resource
    private UserGrowingService userGrowingService;

    @Resource
    private OrderReturnService orderReturnService;



    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public void save(String orderAlterCode, Long orderItemId, Long status, String operator,
            Date date, Long type, BigDecimal orderSum, String info) throws ServiceException {
        try {
            OrderAlterOperateStatement oldRecord =
                    orderAlterOperateStatementDAO.selectNewest(orderAlterCode);
            orderAlterOperateStatementDAO.insertSelective(newOrderOperate(oldRecord, orderAlterCode,
                    orderItemId, status, operator, date, type, orderSum, info));
        } catch (SQLException e) {
            logger.error("保存退换货单流水时发生异常", e);
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_RETURNS_ERROR,
                    "保存退换货单流水时发生异常：" + e.getMessage());
        }
    }

    private OrderAlterOperateStatement newOrderOperate(OrderAlterOperateStatement oldRecord,
            String alterCode, Long orderItemId, Long status, String operator, Date date, Long type,
            BigDecimal orderSum, String info) {
        OrderAlterOperateStatement newRecord = new OrderAlterOperateStatement();
        if (null != oldRecord) {
            newRecord.setPreviousOperateDate(oldRecord.getNowOperateDate());
            newRecord.setPreviousOperateType(oldRecord.getNowOperateType());
            newRecord.setPreviousOperator(oldRecord.getNowOperator());
            newRecord.setPreviousAlterStatus(oldRecord.getNowAlterStatus());
            newRecord.setPreviousReturnSum(oldRecord.getNowReturnSum());
        }
        newRecord.setNowOperateDate(new Date());
        newRecord.setNowOperateType(type);
        newRecord.setNowOperator(operator);
        newRecord.setNowAlterStatus(status);
        newRecord.setNowReturnSum(orderSum);
        newRecord.setOperateInfo(info);
        newRecord.setOrderAlterCode(alterCode);
        return newRecord;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public Boolean InsertOperate(OrderAlterOperateStatement oost) throws ServiceException {
        boolean rs = false;
        try {
            // 根据oost实体中的orderId，先查找下是否存在记录，如有记录，则取最近一条记录
            OrderAlterOperateStatement oosBef =
                    orderAlterOperateStatementDAO.selectNewest(oost.getOrderAlterCode());
            // 组装OrderAlterOperateStatement操作流水
            oost.setPreviousOperateDate(oosBef.getNowOperateDate());
            oost.setPreviousOperator(oosBef.getNowOperator());
            oost.setPreviousOperateType(oosBef.getNowOperateType());
            oost.setPreviousAlterStatus(oosBef.getNowAlterStatus());
            oost.setNowReturnSum(oosBef.getNowReturnSum());
            Long result = orderAlterOperateStatementDAO.insert(oost);
            rs = result > 0;
        } catch (Exception e) {
            logger.error("保存退换货单流水时发生异常", e);
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_RETURNS_ERROR,
                    "保存退换货单流水时发生异常：" + e.getMessage());
        }
        return rs;
    }


    /**
     * 超时未发货赔付 或超时退换货----确认退款
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public Map<String, String> changeAlterStatusYS(String operator, OrderAlter newOrderAlter)
            throws ServiceException {

        String comment;
        Long operateType = null;
        OrderAlter oldOrderAlter = null;
        List<OrderPayStatement> platformops = null;// 第三方支付流水
        OrderPayStatement platformop = null;
        String platFormCode = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            // 根据退换货单号查询得到退换货单的数据
            oldOrderAlter = orderAlterDAO.selectByAlterCode(newOrderAlter.getOrderAlterCode());
        } catch (SQLException e) {
            logger.error("查询退换货单发生异常", e);
            map.put("-4", "查询退换货支付流水发生异常");
            return map;
        }
        // 获取订单主表
        OrderMain omain = orderQryService.getOrderByCode(oldOrderAlter.getOrderCode());
        String account = omain.getCustomerAccount();

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderCode", omain.getOrderCode());
            params.put("paymentWay", OrderDictionaryEnum.OrderPayMethod.Platform.getKey());// 预售都是第三方，没有余额或优惠劵支付
            params.put("status", OrderDictionaryEnum.OrderPayState.Success.getKey());
            params.put("flag", OrderDictionaryEnum.OrderPayFlag.Payment.getKey());// 已付款
            platformops = orderPayStatementDAO.queryOrderPayStatement(params);
        } catch (SQLException e2) {
            logger.error("根据订单号、状态查询支付流水发生异常", e2);
            map.put("-4", "根据订单号、状态查询支付流水发生异常");
            return map;
        }

        // 得到退换货单的当前状态
        Short oldStatus = oldOrderAlter.getProposeStatus();
        // 得到操作完成后的退换货单的状态
        Short newStatus = newOrderAlter.getProposeStatus();
        // 将完成后的退换货单的状态更新到退换货数据
        oldOrderAlter.setProposeStatus(newStatus);

        if (newOrderAlter.getProposeStatus()
                .intValue() == OrderAlterDictionaryEnum.Propose_Status.Backpay.getKey()) {
            // 前台标识为，已退款待确认

            if (oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.F_Backpay
                    .getKey()) {
                operateType =
                        (long) OrderAlterDictionaryEnum.OrderAlterOperateType.PayBack.getKey();
                comment = "商家退款";
            } else {
                map.put("-2", "");
                return map;
            }

            boolean depositeRefu = false;
            boolean finalMoneyRefu = false;

            if (null != platformops && 0 != platformops.size()) {
                System.out.println("--------platformops.size():" + platformops.size());
                for (int p = 0; p < platformops.size(); p++) {

                    platformop = platformops.get(p);
                    platFormCode = platformop.getPlatFormCode(); // 平台代码
                    String ysFlag = platformop.getYsFlage(); // 预售类型
                    System.out.println("ysFlag--1为定金---2为尾款---：" + ysFlag);
                    boolean needRefund = true; // 是否需要退款，因定金退成功，但尾款不一定退成功,未退的则进入该循环
                    try {
                        // 是否需要查一次银行支付状态是否已成功
                        // 应该查退换货的支付流水表,关于paymentWay,预售都是第三方，没有余额或优惠劵支付,目前只有9支付宝、0默认康美通,预售用12处理
                        Map<String, Object> queryMap = new HashMap<String, Object>();
                        queryMap.put("orderCode", omain.getOrderCode());
                        queryMap.put("orderAlterCode", oldOrderAlter.getOrderAlterCode());
                        queryMap.put("state", OrderDictionaryEnum.OrderPayState.Success.getKey());
                        queryMap.put("flag", OrderDictionaryEnum.OrderPayFlag.Refundment.getKey());// 已退款
                        queryMap.put("ysFlag", ysFlag);// 定金还是尾款

                        List<OrderAlterPayStatement> ps =
                                orderAlterPayStatementDAO.queryOrderAlterPayStatementYS(queryMap);
                        if (ps.size() > 0) {
                            needRefund = false;
                        } else if ((OrderDictionaryEnum.PlatformCode.alipay.getKey() + "")
                                .equals(platformop.getPlatFormCode())) {
                            // 如果是支付宝支付的，则需查退款请求表
                            Map<String, Object> queryMap2 = new HashMap<String, Object>();
                            queryMap2.put("orderCode", omain.getOrderCode());
                            queryMap2.put("status", "0");
                            queryMap2.put("refundType",
                                    OrderDictionaryEnum.OrderPayFlag.Refundment.getKey());// 已退款
                            queryMap2.put("ysFlag", ysFlag);// 定金还是尾款

                            int count = refundRequestService.queryRefundRequestCountYS(queryMap2);
                            if (count > 0)
                                needRefund = false;
                        }
                    } catch (SQLException e2) {
                        logger.error("查询退换货支付流水发生异常", e2);
                        map.put("-4", "查询退换货支付流水发生异常");
                        return map;
                    }
                    if (needRefund) {

                        boolean isAliPay = false; // 是否支付宝支付
                        // boolean zeroFlag = false;

                        isAliPay = (OrderDictionaryEnum.PlatformCode.alipay.getKey() + "")
                                .equals(platformops.get(p).getPlatFormCode());

                        Long refundFlag =
                                (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(); // 退款

                        // *************************************************** 退款 第三方支付处理模块
                        // begin ***************************************************
                        // 第三方支付，根据退款是否可以“原路返回”分为两种情况：一是支付宝支付；二是非支付宝支付（暂时只有康美通）
                        boolean refundResult = false;// 退款成功标志
                        if (isAliPay) {

                            BigDecimal refundMoney = (ysFlag.equals("1")
                                    ? oldOrderAlter.getDeposit() : oldOrderAlter.getFinalmoney());

                            // 记录一条支付宝退款流水
                            if (refundMoney.compareTo(BigDecimal.ZERO) != 0) {
                                RefundRequest rr = new RefundRequest();
                                rr.setOrderCode(omain.getOrderCode());
                                rr.setChannel(omain.getOrderChannel());
                                rr.setSellerType(null == omain.getCommerceId() ? 1 : 2);
                                rr.setSellerShop(omain.getCommerceName());
                                rr.setPlatformCode(platFormCode);
                                System.out.println("platformop.getOutsidePayStatementNo():"
                                        + platformop.getOutsidePayStatementNo() + "-------------"
                                        + platformop.getOutsidePayStatementNo() == null);
                                rr.setOutBatchNo(platformop.getOutsidePayStatementNo() == null
                                        ? "数据有错，不为空" : platformop.getOutsidePayStatementNo());
                                rr.setMenberName(omain.getCustomerAccount());
                                rr.setRefurnMoney(refundMoney);
                                rr.setOrderAlterCode(newOrderAlter.getOrderAlterCode());
                                rr.setStatus(0); // 状态，0待处理，1已完成
                                rr.setRefundType(2); // 退款类型
                                rr.setYsflag(ysFlag);
                                // refundRequestService.addRefundRequest(rr);
                                refundRequestService.addRefundRequestForYS(rr);
                                refundResult = true;
                            } else if (refundMoney.compareTo(BigDecimal.ZERO) == 0) {
                                refundResult = true;
                                // zeroFlag = true;
                            }

                        } else {// 处理第三方支付中的非支付宝部分的逻辑

                            long platFormNameKey = 0;
                            platFormNameKey = Long.valueOf(platformop.getPlatFormCode() == null
                                    || platformop.getPlatFormCode().trim().equals("") ? "0"
                                            : platformop.getPlatFormCode());
                            if (platFormNameKey == 7)
                                platFormNameKey = 12; // 预售康美通用12处理

                            // platFormNameKey = platformop.getPaymentWay();
                            // 退第三方原路返回
                            BigDecimal platFormRefundNow = (ysFlag.equals("1")
                                    ? oldOrderAlter.getDeposit() : oldOrderAlter.getFinalmoney());// 当次退第三方的金额

                            // 退第三方准备，在OrderAlterPayStatement中插入一条准备中的支付流水
                            if (0.0 != platFormRefundNow.doubleValue()) {
                                int rs = orderAlterPayStatementService.pay(operator,
                                        platFormNameKey, account, newOrderAlter.getOrderAlterCode(),
                                        platFormRefundNow, null, refundFlag, null, oldStatus,
                                        (long) OrderDictionaryEnum.OrderPayState.Ready.getKey(),
                                        ysFlag);

                                if (1 != rs) {
                                    throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR,
                                            "退换货，往退换货支付流水表插入准备中的流水数据发生异常");
                                }
                            }

                            // 第三方退回
                            if (0.0 != platFormRefundNow.doubleValue()) {
                                try {
                                    Integer rechargeOrOrderFlag = 2;
                                    ReturnResult returnResult = null;
                                    returnResult = refundOrderService
                                            .refundOrderForYs(omain.getOrderCode(),
                                                    rechargeOrOrderFlag, platFormRefundNow,
                                                    String.valueOf(
                                                            oldOrderAlter.getOrderAlterCode()),
                                                    ysFlag);
                                    logger.info("退换货单" + oldOrderAlter.getOrderAlterCode()
                                            + "退款结果returnResult=" + returnResult.getMessage());

                                    // 不支持退款转到余额
                                    if (RefundResultCode.UNSUPORT_BANK_REFUND_EXCEPTION
                                            .equals(returnResult.getCode())) {
                                        refundResult = false;
                                        // 记录退货单支付流水
                                        orderAlterPayStatementService.pay(operator, platFormNameKey,
                                                account, newOrderAlter.getOrderAlterCode(),
                                                platFormRefundNow,
                                                (String) returnResult.getReturnObject(), refundFlag,
                                                null, oldStatus,
                                                (long) OrderDictionaryEnum.OrderPayState.Fail
                                                        .getKey(),
                                                ysFlag);
                                        logger.error("退换货单号：" + oldOrderAlter.getOrderAlterCode()
                                                + "订单号：" + omain.getOrderCode()
                                                + ",预售--退款到第三方失败,code:" + returnResult.getCode()
                                                + ",message:" + returnResult.getMessage());
                                    }

                                    // 成功退款到第三方，不成功的都当作失败处理
                                    if (RefundResultCode.SUCCESS.equals(returnResult.getCode())) {
                                        orderAlterPayStatementService.pay(operator, platFormNameKey,
                                                account, newOrderAlter.getOrderAlterCode(),
                                                platFormRefundNow,
                                                (String) returnResult.getReturnObject(), refundFlag,
                                                null, oldStatus,
                                                (long) OrderDictionaryEnum.OrderPayState.Success
                                                        .getKey(),
                                                ysFlag);
                                        refundResult = true;

                                    }
                                    /*
                                     * else if
                                     * (RefundResultCode.FAILED.equals(returnResult.getCode())) { //
                                     * 退款到第三方 refundResult = false;
                                     * orderAlterPayStatementService.pay(operator, (long)
                                     * platFormNameKey, account, newOrderAlter.getOrderAlterCode(),
                                     * platFormRefundNow, (String) returnResult.getReturnObject(),
                                     * refundFlag, null, oldStatus, (long)
                                     * OrderDictionaryEnum.OrderPayState.Fail.getKey(),ysFlag); }
                                     */

                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                    logger.error("退换货单" + oldOrderAlter.getOrderAlterCode() + "退款失败"
                                            + e1.getMessage());
                                    refundResult = false;
                                }
                            } else if (0.0 == platFormRefundNow.doubleValue()) {
                                refundResult = true;
                                // zeroFlag = true;
                            }


                            // *************************************************** 退款

                            // 第三方支付处理模块

                        }

                        if (ysFlag.equals("1"))
                            depositeRefu = refundResult;
                        else if (ysFlag.equals("2"))
                            finalMoneyRefu = refundResult;

                        // 退款成功，则更新退换货状态，添加成功退款操作流水(为零也更新操作记录表)
                        if (refundResult) {

                            // 更新退换货单
                            // updateOrderAlter(oldOrderAlter);
                            // 记录退换货操作流水表
                            saveOperateStatement(newOrderAlter, oldOrderAlter, operator, ysFlag);
                        }

                        // end ***************************************************
                    } else {
                        if (ysFlag.equals("1"))
                            depositeRefu = true;
                        else if (ysFlag.equals("2"))
                            finalMoneyRefu = true;
                    }


                }

            }

            boolean compensateRefu = false;
            boolean exc = false;

            // 如果是超时赔付，则需要退定金补偿到余额
            if (oldOrderAlter.getAlterType().intValue() == 4) {
                boolean needCompensateRefu = true;

                // 还需要加上判断退货单支付流水是否有成功记录，若成功就不做操作
                try {
                    // 是否需要查一次银行支付状态是否已成功
                    // 应该查退换货的支付流水表
                    Map<String, Object> queryMap = new HashMap<String, Object>();
                    queryMap.put("orderCode", omain.getOrderCode());
                    queryMap.put("orderAlterCode", oldOrderAlter.getOrderAlterCode());
                    queryMap.put("paymentWay", OrderDictionaryEnum.OrderPayMethod.Balance.getKey());// 余额
                    queryMap.put("state", OrderDictionaryEnum.OrderPayState.Success.getKey());
                    queryMap.put("flag", OrderDictionaryEnum.OrderPayFlag.Refundment.getKey());// 已退款
                    queryMap.put("yfFlag", "3");// 定金赔偿

                    List<OrderAlterPayStatement> ps =
                            orderAlterPayStatementDAO.queryOrderAlterPayStatement(queryMap);
                    if (ps.size() > 0) {
                        needCompensateRefu = false;
                        compensateRefu = true;
                    }
                } catch (SQLException e2) {
                    logger.error("根据订单号、状态查询支付流水发生异常", e2);
                    map.put("-4", "根据订单号、状态查询支付流水发生异常");
                    return map;
                }

                if (oldOrderAlter.getCompensate().doubleValue() != 0.0
                        && needCompensateRefu == true) {
                    int rs = orderAlterPayStatementService
                            .pay(operator,
                                    (long) OrderAlterDictionaryEnum.OrderReturnMethod.Balance
                                            .getKey(),
                                    account, newOrderAlter.getOrderAlterCode(),
                                    oldOrderAlter.getCompensate(), null,
                                    (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(),
                                    null, oldStatus,
                                    (long) OrderDictionaryEnum.OrderPayState.Success.getKey(), "3");
                    if (1 != rs) {
                        logger.error("退换货单号：" + oldOrderAlter.getOrderAlterCode() + "订单号："
                                + omain.getOrderCode() + ",退换货余额退还时发生异常,:" + rs);
                        // throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR,
                        // "退换货余额退还时发生异常");
                        exc = true;
                    }
                    System.out.println("exc----------------------" + exc);
                    if (exc == false && needCompensateRefu == true) {
                        saveOperateStatement(newOrderAlter, oldOrderAlter, operator, "3");
                        compensateRefu = true;
                    }
                } else if (oldOrderAlter.getCompensate().compareTo(BigDecimal.ZERO) == 0.0
                        && needCompensateRefu == true) {
                    // 为零也要添加操作记录
                    saveOperateStatement(newOrderAlter, oldOrderAlter, operator, "3");
                    compensateRefu = true;
                }

            }

            if (oldOrderAlter.getAlterType().intValue() != 4) {
                if (depositeRefu == true && finalMoneyRefu == true) {
                    // 更新退换货单表
                    updateOrderAlter(oldOrderAlter);
                    map.put("1", "退定金、尾款成功");

                } else if (depositeRefu == false && finalMoneyRefu == false) {
                    map.put("0", "退定金、尾款失败");
                } else
                    map.put("1", "退定金" + (depositeRefu == true ? "成功" : "失败") + ",退尾款"
                            + (finalMoneyRefu == true ? "成功" : "失败"));

            } else if (oldOrderAlter.getAlterType().intValue() == 4) {
                if (compensateRefu == true && depositeRefu == true && finalMoneyRefu == true) {
                    updateOrderAlter(oldOrderAlter);
                    map.put("1", "退定金、尾款、定金补偿成功");
                } else if (compensateRefu == false && depositeRefu == false
                        && finalMoneyRefu == false) {
                    map.put("0", "退定金、尾款、定金补偿失败");
                } else
                    map.put("1",
                            "退定金" + (depositeRefu == true ? "成功" : "失败") + ",退尾款"
                                    + (finalMoneyRefu == true ? "成功" : "失败") + ",退定金补偿"
                                    + (compensateRefu == true ? "成功" : "失败"));
            }

        } else {

            // 错误的前台传值
            map.put("-1", "错误的前台传值");
            return map;
        }

        return map;
    }



    /**
     * 更改退换货单的状态 所有对退换货单的操作最终的统一入口
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int changeAlterStatus(String operator, OrderAlter newOrderAlter)
            throws ServiceException {
        Boolean notAlready = Boolean.TRUE;
        String comment = null;
        Long operateType = null;
        Date now = new Date();
        OrderAlter oldOrderAlter = null;
        List<OrderPayStatement> platformops = null;// 第三方支付流水
        OrderPayStatement platformop = null;
        String platFormCode = null;
        try {
            // 根据退换货单号查询得到退换货单的数据
            oldOrderAlter = orderAlterDAO.selectByAlterCode(newOrderAlter.getOrderAlterCode());
        } catch (SQLException e) {
            logger.error("更改状态发生异常", e);
            return -4;
        }
        OrderMain omain = orderQryService.getOrderByCode(oldOrderAlter.getOrderCode());

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderCode", omain.getOrderCode());
            params.put("paymentWay", OrderDictionaryEnum.OrderPayMethod.Platform.getKey());
            params.put("status", OrderDictionaryEnum.OrderPayState.Success.getKey());
            platformops = orderPayStatementDAO.queryOrderPayStatement(params); // 父订单支付流水
        } catch (SQLException e2) {
            logger.error("根据订单号、状态查询支付流水发生异常", e2);
        }

        if (null != platformops && 0 != platformops.size()) {
            platformop = platformops.get(0);
            platFormCode = platformop.getPlatFormCode();
        }

        // 得到退换货单的当前状态
        Short oldStatus = oldOrderAlter.getProposeStatus();
        // 得到操作完成后的退换货单的状态
        Short newStatus = newOrderAlter.getProposeStatus();
        // 将完成后的退换货单的状态更新到退换货数据
        oldOrderAlter.setProposeStatus(newStatus);
        if (newOrderAlter.getProposeStatus()
                .intValue() == OrderAlterDictionaryEnum.Propose_Status.Cancel.getKey()) {// 取消退换货
            // 操作备注信息
            comment = "客户取消申请";
            // 设置退换货单的完成时间
            oldOrderAlter.setFinishDate(now);
            // 只有在退换货单的当前状态为“已提交待审核”或者“已通过待退货”，才能进行退换货取消操作
            if (oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.Audit.getKey()
                    || oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.Pass
                            .getKey()) {
                // 设置操作类型为“取消”
                operateType = (long) OrderAlterDictionaryEnum.OrderAlterOperateType.Cancel.getKey();
                notAlready = Boolean.FALSE;
                // 更新退换货单数据
                updateOrderAlter(oldOrderAlter);
                // 记录退换货单操作流水
                saveCancelOperateStatement(operator, newOrderAlter, oldOrderAlter);

                // 如果操作完成后的退换货单的状态为“已通过待退换货”，则需要解冻在“已提交待审核”时冻结的优惠券,********需要此操作？？？
                if (oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.Pass.getKey()) {
                    // ****************调用用户系统的接口，解冻订单所送优惠券；并记录优惠券操作流水(CouponStatement)begin*****************
                    List<CouponStatement> csList;

                    CouponStatementExample example = new CouponStatementExample();
                    CouponStatementExample.Criteria criteria = example.createCriteria();
                    criteria.andOrderAlterCodeEqualTo(oldOrderAlter.getOrderAlterCode());
                    OrderMain om = orderQryService.getRootOrderByCode(oldOrderAlter.getOrderCode());
                    try {
                        csList = couponStatementDAO.selectByExample(example);
                        UserInfoDO userInfo = new UserInfoDO();
                        userInfo.setLoginId(om.getCustomerId().intValue());
                        for (CouponStatement cs : csList) {
                            CouponStatementExample example2 = new CouponStatementExample();
                            criteria.andStatementNoNotEqualTo(cs.getStatementNo());
                            criteria.andGrantIdEqualTo(cs.getGrantId());
                            criteria.andStatusNotEqualTo((short) 1);
                            boolean flag = couponStatementDAO.countByExample(example2) == 0;
                            cs.setStatus(SysConstants.COUPON_UNFREEZE.shortValue());
                            if (flag) {
                                // 调用用户系统的优惠券接口
                                couponRemoteService.changeCustomGrantToGive(userInfo,
                                        Long.valueOf(cs.getCouponId()),
                                        om.getCustomerId().longValue(),
                                        SysConstants.COUPON_UNFREEZE, om.getOrderCode(),
                                        cs.getGrantId().longValue());
                            }
                            // couponStatementDAO.updateByPrimaryKey(cs);
                        }
                        couponStatementDAO.batchUpdateByPrimaryKey(csList);
                    } catch (Exception e) {
                        logger.error("取消退换货操作时，解冻订单所送优惠券，发生异常", e);
                        throw new ServiceException(ErrorCode.INNER_RETURNS_COUPON_ERROR,
                                "取消退换货操作时，解冻订单所送优惠券，发生异常：" + e.getMessage());
                    }
                    // }
                }
                // *****************调用用户系统的接口，解冻订单所送优惠券；并记录优惠券操作流水(CouponStatement)end*************************
            } else {
                // 此状态下不能进行此操作
                return -2;
            }
        } else if (newOrderAlter.getProposeStatus()
                .intValue() == OrderAlterDictionaryEnum.Propose_Status.Returning.getKey()) {// 开始进行“已退货待取件”操作
            // 当前退换货单的状态为“已通过待退货”时，才能进行实际的“已退货待取件”操作，此时退换货数据将要流入产品系统以作为后续质检等操作的基础
            if (oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.Pass.getKey()) {
                // 操作流水中的操作类型为“已退货待取件”
                operateType =
                        (long) OrderAlterDictionaryEnum.OrderAlterOperateType.Returning.getKey();
                // 操作流水中的操作备注为“客户退货”
                comment = "客户退货";
                try {
                    ExpressSubscription es = new ExpressSubscription();
                    es.setLogisticsCode(newOrderAlter.getCustomerLogisticsCode());
                    es.setLogisticsNo(newOrderAlter.getCustomerLogisticsNo());
                    es.setOrderCode(oldOrderAlter.getOrderCode());
                    es.setChannel(SysConstants.B2B_CHANNEL);
                    expressSubscriptionRemoteService.sucribeOrderExpressInfo(es);
                } catch (Exception e) {
                    logger.error(e);
                }
                oldOrderAlter.setCustomerLogisticsCode(newOrderAlter.getCustomerLogisticsCode());// 客户寄回商品的物流公司代码
                oldOrderAlter.setCustomerLogisticsNo(newOrderAlter.getCustomerLogisticsNo());// 客户寄回商品的物流单号
                oldOrderAlter.setCustomerLogisticsName(newOrderAlter.getCustomerLogisticsName());// 客户寄回商品的物流公司名称
                // 如果退换货单的类型为“换货”，则还需要记录客户相关的联系信息，用于寄回商品给客户
                if (oldOrderAlter.getAlterType() == OrderAlterDictionaryEnum.AlterTypes.EXchange
                        .getKey()) {
                    oldOrderAlter.setAddressId(newOrderAlter.getAddressId());
                    oldOrderAlter.setAddress(newOrderAlter.getAddress());
                    oldOrderAlter.setName(newOrderAlter.getName());
                    oldOrderAlter.setPhone(newOrderAlter.getPhone());
                    oldOrderAlter.setProvince(newOrderAlter.getProvince());
                    oldOrderAlter.setCity(newOrderAlter.getCity());
                    oldOrderAlter.setArea(newOrderAlter.getArea());
                    oldOrderAlter.setZipcode(newOrderAlter.getZipcode());
                }
                notAlready = Boolean.FALSE;
                updateOrderAlter(oldOrderAlter);
                // 记录操作流水
                saveCustomerAlterOrderStatement(operator, newOrderAlter, oldOrderAlter);
                // 查询所关联的订单数据
                OrderMain om = orderQryService.getOrderByCode(oldOrderAlter.getOrderCode());
                // 查询所关联的订单明细数据
                OrderItem oi = orderItemQryService.getOrderItemById(oldOrderAlter.getOrderItemId());
                // 得到订单明细中的商品实际交易价格
                BigDecimal unitPrice = oi.getCommodityUnitPrice();
                BigDecimal totalPrice = null;
                // 再根据退换货单中的退换货数量，相乘得到退换货单的商品实际交易总价
                if (unitPrice != null && oi.getCommodityNumber() != null) {
                    totalPrice = unitPrice.multiply(new BigDecimal(oldOrderAlter.getAlterNum()));
                }
                try {
                    // ************************构造产品系统中的退换货单数据begin**************************
                    String ShipAddres = oldOrderAlter.getProvince() + oldOrderAlter.getCity()
                            + oldOrderAlter.getArea() + oldOrderAlter.getAddress();
                    AftersaleReturnOrder returnOrder = new AftersaleReturnOrder();
                    returnOrder.setUnitPrice(unitPrice);
                    returnOrder.setTotalPrice(totalPrice);
                    returnOrder.setCustId(om.getCustomerId().intValue());
                    returnOrder.setOrderCode(oldOrderAlter.getOrderAlterCode());
                    returnOrder.setOrderdetailId(oi.getOrderItemId());
                    returnOrder.setOrderType(oldOrderAlter.getAlterType());
                    returnOrder.setShipAddress(ShipAddres);// 地址
                    returnOrder.setLinkPhone(oldOrderAlter.getPhone());
                    returnOrder.setCustName(oldOrderAlter.getName());
                    returnOrder.setProductNo(oi.getCommodityCode());// 产品NO
                    String commodityName = oi.getCommodityName();
                    if (null == commodityName || "".equals(commodityName)) {
                        commodityName = oi.getCommodityTitle();
                    }
                    returnOrder.setProductName(commodityName);
                    returnOrder.setProductSku(oi.getCommoditySku());
                    returnOrder.setProductCounts(oldOrderAlter.getAlterNum());// 数量
                    returnOrder.setProductDesc(oldOrderAlter.getAlterComment());
                    returnOrder.setWarehouseId(oi.getWarehouseId().longValue());
                    returnOrder.setUnitPrice(oi.getCommodityUnitPrice());// 单价
                    // ********************构造产品系统中的退换货单数据end************************
                    stockRemoteService.insertReturnOrder(returnOrder);
                } catch (Exception e) {
                    logger.error("更改退换货单时发生异常", e);
                    throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR,
                            "更改退换货单时发生异常：" + e.getMessage());
                }
            } else {
                // 此状态下不能进行此操作
                return -2;
            }
        } else if (newOrderAlter.getProposeStatus()
                .intValue() == OrderAlterDictionaryEnum.Propose_Status.Pickup.getKey()) {// 已取件待质检
            if (oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.Returning
                    .getKey()) {
                operateType = (long) OrderAlterDictionaryEnum.OrderAlterOperateType.Pickup.getKey();
                comment = "商家取件";
            } else {
                return -2;
            }
        } else if (newOrderAlter.getProposeStatus()
                .intValue() == OrderAlterDictionaryEnum.Propose_Status.F_Backpay.getKey()) {// 已通过待退款
            oldOrderAlter.setQualityComment(newOrderAlter.getQualityComment());
            if (oldOrderAlter.getAlterType() == OrderAlterDictionaryEnum.AlterTypes.Return.getKey()
                    && oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.Pickup
                            .getKey()) {
                operateType =
                        (long) OrderAlterDictionaryEnum.OrderAlterOperateType.Quality.getKey();
                comment = "您申请的商品已通过质检，等待退款.";
            } else {
                return -2;
            }
        } else if (newOrderAlter.getProposeStatus()
                .intValue() == OrderAlterDictionaryEnum.Propose_Status.F_Stockout.getKey()) {// 已通过待发货
            oldOrderAlter.setQualityComment(newOrderAlter.getQualityComment());
            if (oldOrderAlter.getAlterType() == OrderAlterDictionaryEnum.AlterTypes.EXchange
                    .getKey()
                    && oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.Pickup
                            .getKey()) {
                operateType =
                        (long) OrderAlterDictionaryEnum.OrderAlterOperateType.Quality.getKey();
                comment = "您申请的商品已通过质检，等待重新发货.";
            } else {
                return -2;
            }
        } else if (newOrderAlter.getProposeStatus()
                .intValue() == OrderAlterDictionaryEnum.Propose_Status.F_BackShop.getKey()) {// 已驳回待原件返回
            oldOrderAlter.setQualityComment(newOrderAlter.getQualityComment());
            if (oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.Pickup.getKey()) {
                operateType =
                        (long) OrderAlterDictionaryEnum.OrderAlterOperateType.Quality.getKey();
                comment = "您申请的商品不符合退换货规则，等待退回原件.";
                notAlready = Boolean.FALSE;
                updateOrderAlter(oldOrderAlter);
                // 记录操作流水
                saveOperateStatement(operator, newOrderAlter, oldOrderAlter);
                if (oldOrderAlter.getAlterType()
                        .intValue() == OrderAlterDictionaryEnum.AlterTypes.Return.getKey()) {
                    // 解冻订单所送优惠券
                    OrderMain om = orderQryService.getRootOrderByCode(oldOrderAlter.getOrderCode());
                    List<OrderPreferential> preferentialList =
                            orderPayStatementService.getPreferentialByOrderCode(om.getOrderCode(),
                                    SysConstants.PROMOTION_TYPE_GIFT);
                    if (null != preferentialList && 0 != preferentialList.size()) {
                        try {
                            UserInfoDO userInfo = new UserInfoDO();
                            userInfo.setLoginId(om.getCustomerId().intValue());
                            for (OrderPreferential preferential : preferentialList) {
                                // 优惠券接口
                                couponRemoteService.changeCustomGrantToGive(userInfo,
                                        Long.valueOf(preferential.getCouponId()),
                                        om.getCustomerId().longValue(),
                                        SysConstants.COUPON_UNFREEZE, om.getOrderCode(),
                                        preferential.getGrantId().longValue());
                            }
                        } catch (Exception e) {
                            logger.error("解冻订单所送优惠券异常时发生异常", e);
                            throw new ServiceException(ErrorCode.INNER_RETURNS_COUPON_ERROR,
                                    "解冻订单所送优惠券异常时发生异常：" + e.getMessage());
                        }
                    }
                }
            } else {
                return -2;
            }
        } else if (newOrderAlter.getProposeStatus()
                .intValue() == OrderAlterDictionaryEnum.Propose_Status.ExchangeToReturn.getKey()) {// 换货转退货
            oldOrderAlter.setQualityComment(newOrderAlter.getQualityComment());
            if (oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.Pickup.getKey()) {
                operateType = (long) OrderAlterDictionaryEnum.OrderAlterOperateType.ExchangeToReturn
                        .getKey();
                oldOrderAlter
                        .setAlterType((short) OrderAlterDictionaryEnum.AlterTypes.Return.getKey());
                // 如果是预售订单,换货转退货，需要计算金额
                orderReturnService.compute(omain, oldOrderAlter);
                oldOrderAlter.setFareSubsidy(null);// 清空补贴运费
                comment = "您申请的商品由于缺货，经协商准备转换为退货.";
            } else {
                return -2;
            }
        } else if (newOrderAlter.getProposeStatus()
                .intValue() == OrderAlterDictionaryEnum.Propose_Status.Backpay.getKey()) {// --------------------------------------已退款待确认
            notAlready = false;
            this.backPay(operator, newOrderAlter, oldOrderAlter, oldStatus, omain, platformops);

        } else if (newOrderAlter.getProposeStatus()
                .intValue() == OrderAlterDictionaryEnum.Propose_Status.Stockout.getKey()) {// 已发货待签收
            if (oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.F_Stockout
                    .getKey()) {
                operateType =
                        (long) OrderAlterDictionaryEnum.OrderAlterOperateType.Stockout.getKey();
                comment = "商家发货";
                oldOrderAlter.setLogisticsCode(newOrderAlter.getLogisticsCode());
                oldOrderAlter.setLogisticsOrderNo(newOrderAlter.getLogisticsOrderNo());// 物流单号
                oldOrderAlter.setLogisticsName(newOrderAlter.getLogisticsName());
            } else {
                return -2;
            }
        } else if (newOrderAlter.getProposeStatus()
                .intValue() == OrderAlterDictionaryEnum.Propose_Status.BackShop.getKey()) {// 已返回原件待签收
            if (oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.F_BackShop
                    .getKey()) {
                // 判断是否是自营的，如果是则需要订阅物流信息,供应商的订阅已经在供应商系统处理
                if (StringUtils.isEmpty(omain.getCommerceId())) {
                    executeSubscribe(oldOrderAlter.getOrderCode(), newOrderAlter.getLogisticsCode(),
                            newOrderAlter.getLogisticsOrderNo());
                }

                operateType =
                        (long) OrderAlterDictionaryEnum.OrderAlterOperateType.BackShop.getKey();
                comment = "商家返回原件";
                oldOrderAlter.setLogisticsCode(newOrderAlter.getLogisticsCode());
                oldOrderAlter.setLogisticsOrderNo(newOrderAlter.getLogisticsOrderNo());// 物流单号
                oldOrderAlter.setLogisticsName(newOrderAlter.getLogisticsName());
            } else {
                return -2;
            }
        } else if (newOrderAlter.getProposeStatus()
                .intValue() == OrderAlterDictionaryEnum.Propose_Status.Returns_Done.getKey()) {// 完成
            if (oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.Backpay.getKey()
                    || oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.Stockout
                            .getKey()
                    || oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.BackShop
                            .getKey()) {
                operateType = (long) OrderAlterDictionaryEnum.OrderAlterOperateType.Finish.getKey();
                comment = "客户确认完成";
                oldOrderAlter.setFinishDate(now);// 完成时间
                notAlready = Boolean.FALSE;
                updateOrderAlter(oldOrderAlter);

                save(newOrderAlter.getOrderAlterCode(), null,
                        (long) oldOrderAlter.getProposeStatus(), operator, now, operateType,
                        oldOrderAlter.getRuturnSum(), comment);
                // 换货单完成后补偿运费
                if (null != oldOrderAlter.getRuturnSum()
                        && 0.0 != oldOrderAlter.getRuturnSum().doubleValue()) {
                    if (oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.Stockout
                            .getKey()) {
                        OrderMain om =
                                orderQryService.getRootOrderByCode(oldOrderAlter.getOrderCode());
                        String account = om.getCustomerAccount();
                        int rs = orderAlterPayStatementService.pay(operator,
                                (long) OrderAlterDictionaryEnum.OrderReturnMethod.Balance.getKey(),
                                account, newOrderAlter.getOrderAlterCode(),
                                oldOrderAlter.getRuturnSum(), null,
                                (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null,
                                oldStatus,
                                (long) OrderDictionaryEnum.OrderPayState.Success.getKey());// 余额退还
                        if (1 != rs) {
                            throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR,
                                    "退换货余额退还时发生异常");
                        }
                    }
                }
            } else {
                return -2;
            }
        } else {
            // 未知退换货单状态
            return -1;
        }
        if (notAlready) {
            updateOrderAlter(oldOrderAlter);
            save(newOrderAlter.getOrderAlterCode(), null, (long) oldOrderAlter.getProposeStatus(),
                    operator, now, operateType, oldOrderAlter.getRuturnSum(), comment);
        }
        return 1;
    }

    // 执行快递100的物流订阅
    private void executeSubscribe(String orderCode, String logisticsCode, String logisticsNo) {

        String result = null;
        try {
            ExpressSubscription expressSubscription = new ExpressSubscription();
            expressSubscription.setOrderCode(orderCode);
            expressSubscription.setLogisticsCode(logisticsCode);
            expressSubscription.setLogisticsNo(logisticsNo);
            expressSubscription.setChannel(Constants.EXPRESS_B2B_CHANNEL);
            // 进行快递订阅
            result = expressSubscriptionRemoteService.sucribeOrderExpressInfo(expressSubscription);
            logger.info("result:" + result + ",logisticsCode:" + logisticsCode + ",logisticsNo:"
                    + logisticsNo);
        } catch (Exception ex) {
            logger.error("执行快递订阅异常:" + "result:" + result + ",logisticsCode:" + logisticsCode
                    + ",logisticsNo:" + logisticsNo + "," + ex.getMessage(), ex);
        }
    }



    private int savePayStatement(String operator, OrderAlter newOrderAlter, Short oldStatus,
            String account, Long refundFlag, BigDecimal balanceAndReserveRefundNow)
            throws ServiceException {
        OrderPayStatementVo vo = new OrderPayStatementVo();
        vo.setOperate(operator);
        vo.setPaymentWay((long) OrderAlterDictionaryEnum.OrderReturnMethod.Balance.getKey());
        vo.setAccount(account);
        vo.setAlterCode(newOrderAlter.getOrderAlterCode());
        vo.setOrderMoney(balanceAndReserveRefundNow);
        vo.setFlag(refundFlag);
        vo.setStatus(oldStatus);
        vo.setState((long) OrderDictionaryEnum.OrderPayState.Success.getKey());
        int rs = orderAlterPayStatementService.pay(vo);
        return rs;
    }

    // 预售
    private void saveOperateStatement(OrderAlter newOrderAlter, OrderAlter oldOrderAlter,
            String operator, String yfFlag) throws ServiceException {
        OrderAlterOperateStatementSaveVo vo = new OrderAlterOperateStatementSaveVo();
        vo.setOrderAlterCode(newOrderAlter.getOrderAlterCode());
        vo.setStatus((long) oldOrderAlter.getProposeStatus());
        vo.setOperator(operator);
        vo.setDate(new Date());
        vo.setType((long) OrderAlterDictionaryEnum.OrderAlterOperateType.PayBack.getKey());
        if (yfFlag.equals("1")) {
            vo.setOrderSum(oldOrderAlter.getDeposit());
            vo.setInfo("商家退款--定金");
        } else if (yfFlag.equals("2")) {
            vo.setOrderSum(oldOrderAlter.getFinalmoney());
            vo.setInfo("商家退款--尾款");
        } else {
            vo.setOrderSum(oldOrderAlter.getCompensate());
            vo.setInfo("商家退款--定金补偿");
        }
        save(vo);
    }

    private void saveOperateStatement(OrderAlter newOrderAlter, OrderAlter oldOrderAlter,
            String operator) throws ServiceException {
        OrderAlterOperateStatementSaveVo vo = new OrderAlterOperateStatementSaveVo();
        vo.setOrderAlterCode(newOrderAlter.getOrderAlterCode());
        vo.setStatus((long) oldOrderAlter.getProposeStatus());
        vo.setOperator(operator);
        vo.setDate(new Date());
        vo.setType((long) OrderAlterDictionaryEnum.OrderAlterOperateType.PayBack.getKey());
        vo.setOrderSum(oldOrderAlter.getRuturnSum());
        vo.setInfo("商家退款");
        save(vo);
    }



    private void saveOperateStatement(String operator, OrderAlter newOrderAlter,
            OrderAlter oldOrderAlter) throws ServiceException {
        OrderAlterOperateStatementSaveVo vo = new OrderAlterOperateStatementSaveVo();
        vo.setOrderAlterCode(newOrderAlter.getOrderAlterCode());
        vo.setStatus((long) oldOrderAlter.getProposeStatus());
        vo.setOperator(operator);
        vo.setDate(new Date());
        vo.setType((long) OrderAlterDictionaryEnum.OrderAlterOperateType.Quality.getKey());
        vo.setOrderSum(oldOrderAlter.getRuturnSum());
        vo.setInfo("您申请的商品不符合退换货规则，等待退回原件.");
        save(vo);
    }

    private void saveCustomerAlterOrderStatement(String operator, OrderAlter newOrderAlter,
            OrderAlter oldOrderAlter) throws ServiceException {
        OrderAlterOperateStatementSaveVo vo = new OrderAlterOperateStatementSaveVo();
        vo.setOrderAlterCode(newOrderAlter.getOrderAlterCode());
        vo.setStatus((long) oldOrderAlter.getProposeStatus());
        vo.setOperator(operator);
        vo.setDate(new Date());
        vo.setType((long) OrderAlterDictionaryEnum.OrderAlterOperateType.Returning.getKey());
        vo.setOrderSum(oldOrderAlter.getRuturnSum());
        vo.setInfo("客户退货");
        save(vo);
    }

    private void saveCancelOperateStatement(String operator, OrderAlter newOrderAlter,
            OrderAlter oldOrderAlter) throws ServiceException {
        OrderAlterOperateStatementSaveVo vo = new OrderAlterOperateStatementSaveVo();
        vo.setOrderAlterCode(newOrderAlter.getOrderAlterCode());
        vo.setStatus((long) oldOrderAlter.getProposeStatus());
        vo.setOperator(operator);
        vo.setDate(new Date());
        vo.setType((long) OrderAlterDictionaryEnum.OrderAlterOperateType.Cancel.getKey());
        vo.setOrderSum(oldOrderAlter.getRuturnSum());
        vo.setInfo("客户取消申请");
        save(vo);
    }

    @Override
    public int changeAlterStatusForProduct(String operator, String orderAlterCode, Long status,
            String comment) throws ServiceException {
        int result = 0;
        try {
            OrderAlter oa = new OrderAlter();
            oa.setOrderAlterCode(orderAlterCode);
            oa.setProposeStatus(status.shortValue());
            oa.setQualityComment(comment);
            result = changeAlterStatus(operator, oa);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_RETURNS_UPDATE_ERROR,
                    "退换货更新产品信息时发生异常：" + e.getMessage());
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public List getOrderAlterDetailsByAlterCode(String orderAlterCode) throws ServiceException {
        try {
            OrderAlterDetailExample example = new OrderAlterDetailExample();
            OrderAlterDetailExample.Criteria criteria = example.createCriteria();
            criteria.andOrderAlterCodeEqualTo(orderAlterCode);
            return orderAlterDetailDAO.selectByExample(example);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR,
                    "根据退换货批次号获取退换货详情时发生异常：" + e.getMessage());
        }
    }

    @Override
    public void updateOrderAlter(OrderAlter oa) throws ServiceException {
        try {
            if (null == oa.getOrderAlterId()) {
                throw new ServiceException(ErrorCode.INNER_RETURNS_UPDATE_ERROR,
                        "退换货编号" + oa.getOrderAlterCode()
                                + "中oa.getOrderAlterId为空，拒绝通过OrderAlterId更新updateOrderAlter(oa)");
            }
            orderAlterDAO.updateByPrimaryKey(oa);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_RETURNS_UPDATE_ERROR,
                    "更新退换货单失败时发生异常：" + e.getMessage());
        }
    }

    @Override
    public String saveOrderAlter(OrderAlter record, BigDecimal customerId) throws ServiceException {
        try {
            String code = OrderAlterCodeUtil.generateOrderAlterCode(customerId);
            record.setOrderAlterCode(code);

            Map<String, Object> querymap = new HashMap<String, Object>();
            querymap.put("alterNum", record.getAlterNum());
            querymap.put("orderItemId", record.getOrderItemId());
            querymap.put("orderAlterCode", code);
            if (orderAlterDAO.checkDate(querymap)) {
                orderAlterDAO.insert(record);
            } else {
                code = null;
            }
            return code;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_RETURNS_ERROR,
                    "保存退换货单失败时发生异常：" + e.getMessage());
        }
    }

    @Override
    public OrderAlter getOrderAlterByCode(String orderAlterCode) throws ServiceException {
        try {
            return orderAlterDAO.selectByAlterCode(orderAlterCode);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR,
                    "查询退换货单时发生异常：" + e.getMessage());
        }
    }

    @Override
    public void saveOrderAlterDetail(OrderAlterDetail oad) throws ServiceException {
        try {
            orderAlterDetailDAO.insert(oad);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR,
                    "保存退换货单明细时发生异常：" + e.getMessage());
        }
    }

    @Override
    public List listOrderAlterOperates(String alterCode) throws ServiceException {
        try {
            OrderAlterOperateStatementExample example = new OrderAlterOperateStatementExample();
            example.setOrderByClause("STATEMENT_Id ASC");
            OrderAlterOperateStatementExample.Criteria criteria = example.createCriteria();
            criteria.andOrderAlterCodeEqualTo(alterCode);
            return orderAlterOperateStatementDAO.selectByExample(example);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR,
                    "查询退换货单操作明细时发生异常：" + e.getMessage());
        }
    }

    @Override
    public List listOrderAlterPays(String alterCode) throws ServiceException {
        try {
            OrderAlterPayStatementExample example = new OrderAlterPayStatementExample();
            example.setOrderByClause("PAY_STATEMENT_NO ASC");
            OrderAlterPayStatementExample.Criteria criteria = example.createCriteria();
            criteria.andOrderAlterCodeEqualTo(alterCode);
            return orderAlterPayStatementDAO.selectByExample(example);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR,
                    "查询退换货单支付明细时发生异常：" + e.getMessage());
        }
    }

    @Override
    public Address getAddressById(BigDecimal addressId) throws ServiceException {
        try {
            return orderAlterOperateStatementDAO.selectAddressById(addressId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR,
                    "获取收货地址时发生异常：" + e.getMessage());
        }
    }

    @Override
    public Integer selectFareAdditional(String orderAlterCode) throws ServiceException {
        try {
            return orderAlterDAO.selectFareAdditional(orderAlterCode);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR,
                    "获取运费时发生异常：" + e.getMessage());
        }
    }

    @Override
    public int additional(String alterCode) throws ServiceException {
        try {
            String interfaceSysCode = "";
            OrderAlter oa = orderAlterDAO.selectByAlterCode(alterCode);
            OrderMain om = orderQryService.getRootOrderByCode(oa.getOrderCode());
            OrderAlterPayStatementExample example = new OrderAlterPayStatementExample();
            OrderAlterPayStatementExample.Criteria criteria = example.createCriteria();
            criteria.andOrderAlterCodeEqualTo(alterCode);
            List<Integer> pw = new ArrayList<Integer>();
            pw.add(OrderAlterDictionaryEnum.OrderReturnMethod.Platform.getKey());
            pw.add(OrderAlterDictionaryEnum.OrderReturnMethod.AliPay.getKey());
            pw.add(OrderAlterDictionaryEnum.OrderReturnMethod.TenPay.getKey());
            pw.add(OrderAlterDictionaryEnum.OrderReturnMethod.WeiXin.getKey());
            criteria.andPaymentWayIn(pw);
            criteria.andStateEqualTo((long) OrderDictionaryEnum.OrderPayState.Ready.getKey());
            criteria.andFlagEqualTo((long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey());
            OrderAlterPayStatement oas = (OrderAlterPayStatement) orderAlterPayStatementDAO
                    .selectByExample(example).get(0);
            Map map = new HashMap();
            map.put("orderAlterId", alterCode);
            map.put("payWay", OrderAlterDictionaryEnum.OrderReturnMethod.Platform.getKey());
            map.put("state", OrderDictionaryEnum.OrderPayState.Success.getKey());
            map.put("flag", OrderDictionaryEnum.OrderPayFlag.Refundment.getKey());
            BigDecimal rop = orderAlterPayStatementDAO.getOrderAlterPay(map);// 订单已退记录
            interfaceSysCode = getInterfaceSysCode(om.getOrderChannel());
            QueryResult qr = null;
            qr = qryOrderOnLineService.queryByOrder(om.getOrderCode());
            Integer rechargeOrOrderFlag = 2;
            BigDecimal rd = new BigDecimal(qr.getRd_RefundAmt());// 银行已退记录
            if (rd.compareTo(rop.add(oas.getOrderMoney()).abs()) >= 0) {// 已退补记录
                return orderAlterPayStatementService.pay(SysConstants.SYS,
                        (long) OrderAlterDictionaryEnum.OrderReturnMethod.Platform.getKey(),
                        om.getCustomerAccount(), oa.getOrderAlterCode(), oas.getOrderMoney().abs(),
                        qr.getR2_TrxId(),
                        (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null,
                        oa.getProposeStatus(),
                        (long) OrderDictionaryEnum.OrderPayState.Success.getKey());
            } else {// 没退重新退
                ReturnResult returnResult = null;
                try {
                    RefundOrderService refundOrderService = (RefundOrderService) RemoteTool
                            .getRemote(interfaceSysCode, SysConstants.REFUND_ORDER_SERVICE);
                    returnResult = refundOrderService.refundOrder(om.getOrderCode(),
                            rechargeOrOrderFlag, oas.getOrderMoney().abs(), oa.getOrderAlterCode());
                    logger.info("退换货单" + oa.getOrderAlterCode() + "退款结果returnResult="
                            + returnResult.getMessage());
                } catch (Exception e) {
                    throw new ServiceException(
                            "退换货单" + oa.getOrderAlterCode() + "退款失败" + e.getMessage());
                }

                if (RefundResultCode.UNSUPORT_BANK_REFUND_EXCEPTION
                        .equals(returnResult.getCode())) {// 不支持退款转到余额
                    orderAlterPayStatementService.pay(SysConstants.SYS,
                            (long) OrderAlterDictionaryEnum.OrderReturnMethod.Platform.getKey(),
                            om.getCustomerAccount(), oa.getOrderAlterCode(),
                            oas.getOrderMoney().abs(), (String) returnResult.getReturnObject(),
                            (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null,
                            oa.getProposeStatus(),
                            (long) OrderDictionaryEnum.OrderPayState.Fail.getKey());
                    /*
                     * 删除第三方退到余额orderAlterPayStatementService.pay(SysConstants.SYS, (long)
                     * OrderAlterDictionaryEnum.OrderReturnMethod.Balance.getKey(), om
                     * .getCustomerAccount(), oa.getOrderAlterCode(), oas.getOrderMoney().abs(),
                     * null, (long) OrderDictionaryEnum.OrderPayState.Success.getKey(), null, oa
                     * .getProposeStatus(), (long)
                     * OrderDictionaryEnum.OrderPayState.Success.getKey());
                     */
                }
                if (!RefundResultCode.SUCCESS.equals(returnResult.getCode())) {
                    throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_EVALUATE_ERROR,
                            "补单" + alterCode + "发生错误：");
                }
                return orderAlterPayStatementService.pay(SysConstants.SYS,
                        (long) OrderAlterDictionaryEnum.OrderReturnMethod.Platform.getKey(),
                        om.getCustomerAccount(), oa.getOrderAlterCode(), oas.getOrderMoney().abs(),
                        (String) returnResult.getReturnObject(),
                        (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(), null,
                        oa.getProposeStatus(),
                        (long) OrderDictionaryEnum.OrderPayState.Success.getKey());
            }
        } catch (Exception e1) {
            logger.error("补单" + alterCode + "发生错误：" + e1.getMessage());
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_EVALUATE_ERROR,
                    "补单" + alterCode + "发生错误：" + e1.getMessage());
        }
    }

    /**
     * 批量处理退换货
     * 
     * @param rrList 回调实体集合
     * @param refundNo 退款批次号
     * @throws ServiceException
     */
    @Override
    public void batchAlterRefund(List<RefundRequest> rrList, String refundNo)
            throws ServiceException {
        if (rrList != null && 0 != rrList.size()) {
            for (RefundRequest rr : rrList) {
                try {
                    alterRefund(rr, refundNo);
                } catch (Exception e) {
                    logger.error("退换货修改回调状态发生异常,退款请求ID=" + rr.getRrid(), e);
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
        Date date = new Date();
        try {
            // 记录退款流水
            String alterCodeStr = rr.getOrderAlterCode();
            String refundDetail = rr.getRefundDetail();
            if (null == alterCodeStr || alterCodeStr.length() == 0) {
                return;
            }
            if (null == refundDetail) {
                refundDetail = "";
            }
            String[] alterCodes = alterCodeStr.split(",");
            String[] refundMoneys = refundDetail.split(",");
            String alterCode = null, refundMoney = null;
            int mLen = refundMoneys.length;
            for (int i = 0, len = alterCodes.length; i < len; i++) {
                alterCode = alterCodes[i];
                if (mLen > i) {
                    refundMoney = refundMoneys[i];
                } else {
                    refundMoney = "0";
                }
                orderAlterPayStatementService.save(
                        (long) OrderAlterDictionaryEnum.OrderReturnMethod.AliPay.getKey(), 1l,
                        rr.getMenberName(), alterCode, new BigDecimal(refundMoney).negate(), date,
                        date, refundNo, (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey(),
                        null);
            }
        } catch (Exception e) {
            logger.error("记录支付流水异常", e);
        }
    }

    private OrderAlterOperateStatement newOrderOperation(OrderAlterOperateStatement oldRecord,
            OrderAlterOperateStatementSaveVo vo) {
        OrderAlterOperateStatement newRecord = new OrderAlterOperateStatement();
        if (null != oldRecord) {
            newRecord.setPreviousOperateDate(oldRecord.getNowOperateDate());
            newRecord.setPreviousOperateType(oldRecord.getNowOperateType());
            newRecord.setPreviousOperator(oldRecord.getNowOperator());
            newRecord.setPreviousAlterStatus(oldRecord.getNowAlterStatus());
            newRecord.setPreviousReturnSum(oldRecord.getNowReturnSum());
        }
        newRecord.setNowOperateDate(new Date());
        newRecord.setNowOperateType(vo.getType());
        newRecord.setNowOperator(vo.getOperator());
        newRecord.setNowAlterStatus(vo.getStatus());
        newRecord.setNowReturnSum(vo.getOrderSum());
        newRecord.setOperateInfo(vo.getInfo());
        newRecord.setOrderAlterCode(vo.getOrderAlterCode());
        return newRecord;
    }

    /**
     * 调用dao层代码保存操作流水
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public void save(OrderAlterOperateStatementSaveVo vo) throws ServiceException {
        try {
            OrderAlterOperateStatement oldRecord =
                    orderAlterOperateStatementDAO.selectNewest(vo.getOrderAlterCode());
            orderAlterOperateStatementDAO.insertSelective(newOrderOperation(oldRecord, vo));
        } catch (SQLException e) {
            logger.error("保存退换货单流水时发生异常", e);
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_RETURNS_ERROR,
                    "保存退换货单流水时发生异常：" + e.getMessage());
        }
    }

    /**
     * 查询待确认的退换货编号
     * 
     * @return
     * @throws ServiceException
     */
    @Override
    public List<String> queryUnconfirmOrderAlterCode() throws ServiceException {
        try {
            return orderAlterOperateStatementDAO.queryUnconfirmOrderAlterCode();
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_RETURNS_ERROR,
                    "查询待确认的退换货编号发生异常：" + e.getMessage());
        }
    }

    /**
     * 自动确认退换货
     * 
     * @param orderAlterCode
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean OrderAlterAutoSure(String orderAlterCode) throws ServiceException {
        OrderAlter oa = new OrderAlter();
        oa.setOrderAlterCode(orderAlterCode);
        oa.setProposeStatus((short) OrderAlterDictionaryEnum.Propose_Status.Returns_Done.getKey());
        return 1 == changeAlterStatus(SysConstants.SYS, oa);
    }

    @Override
    public BigDecimal selectReturnFare(String orderCode) throws ServiceException {
        try {
            BigDecimal sumReturnFare = null;
            sumReturnFare = orderAlterDAO.selectReturnFare(orderCode);
            return sumReturnFare;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR,
                    "查询订单" + orderCode + "累计退货返运费出错" + e.getMessage());
        }
    }

    @Override
    public void updateOrderMainStatus(String orderCode) throws ServiceException {
        // TODO Auto-generated method stub
        try {
            int result = orderMainDAO.updateOrderMainStatus(orderCode);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_RETURNS_ERROR,
                    "超时审核更改订单状态出错" + orderCode + e.getMessage());
        }
    }


    /**
     * 退款
     * 
     * @param operator 操作者
     * @param newOrderAlter
     * @param oldOrderAlter
     * @param oldStatus
     * @param omain
     * @param platformops
     * @return
     */
    private int backPay(String operator, OrderAlter newOrderAlter, OrderAlter oldOrderAlter,
            Short oldStatus, OrderMain omain, List<OrderPayStatement> platformops) {

        // --------------------------------------已退款待确认
        if (oldStatus.intValue() == OrderAlterDictionaryEnum.Propose_Status.F_Backpay.getKey()) {
            Long operateType =
                    (long) OrderAlterDictionaryEnum.OrderAlterOperateType.PayBack.getKey();
            String comment = "商家退款";
        } else {
            return -2;
        }

        boolean isAliPay = false;// 是否支付宝支付
        // 初始化，用于查询订单支付流水
        OrderMain om = null;
        String platFormCode = null; // 支付平台代码
        String outBatchNo = null; // 第三方支付流水号
        try {
            // 通过退换货单中的相关订单号查询得到父订单数据
            om = orderQryService.getRootOrderByCode(oldOrderAlter.getOrderCode());
            // 初始化订单支付流水查询器
            OrderPayStatementExample orderPayStatementExample = new OrderPayStatementExample();
            // 初始化订单支付流水查询条件
            OrderPayStatementExample.Criteria criteria = orderPayStatementExample.createCriteria();
            // // ***********************构造订单支付流水查询条件begin****************************
            // criteria.andOrderCodeEqualTo(om.getOrderCode());
            // criteria.andPaymentWayEqualTo((long)
            // OrderDictionaryEnum.OrderPayMethod.Platform.getKey());
            // criteria.andStateEqualTo((long) OrderDictionaryEnum.OrderPayState.Success.getKey());
            // // **********************构造订单支付流水查询条件end*****************************
            //
            // // 查询该订单的父订单支付方式为“第三方支付平台支付”所有支付成功的支付流水
            // List<OrderPayStatement> platformops =
            // orderPayStatementDAO.selectByExample(orderPayStatementExample);

            if (null != platformops && 0 != platformops.size()) {
                // 由于“第三方支付平台支付”的排他性，所以只会存在一种支付平台的支付（要不是易宝要不就是支付宝）
                outBatchNo = platformops.get(0).getOutsidePayStatementNo();
                platFormCode = platformops.get(0).getPlatFormCode();
                isAliPay = (OrderDictionaryEnum.PlatformCode.alipay.getKey() + "")
                        .equals(platFormCode);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            return -4;
        }
        String account = om.getCustomerAccount();

        BigDecimal allReadyRefundWithOutFare = BigDecimal.ZERO;// 历史已退总额（不含运费补偿）
        try {
            Map<String, String> nmap = new HashMap<String, String>();
            nmap.put("orderCode", om.getOrderCode());
            allReadyRefundWithOutFare = orderAlterDAO.getReturnMoneySum(nmap).abs();
        } catch (SQLException e) {
            logger.error(e);
            throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR, "退换货余额退款时发生异常");
        }

        Long refundFlag = (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey();

        // ********************************如果是“退货”，作废订单所送优惠券，修改用户积分和等级 begin
        // *********************************************
        if (oldOrderAlter.getAlterType().intValue() == OrderAlterDictionaryEnum.AlterTypes.Return
                .getKey()) {

            List<CouponStatement> couponStatementList = null;
            CouponStatementExample example = new CouponStatementExample();
            CouponStatementExample.Criteria criteria = example.createCriteria();
            criteria.andOrderAlterCodeEqualTo(oldOrderAlter.getOrderAlterCode());

            try {
                couponStatementList = couponStatementDAO.selectByExample(example);
                UserInfoDO userInfo = new UserInfoDO();
                userInfo.setLoginId(om.getCustomerId().intValue());
                // 初始化产品系统的优惠券接口
                for (CouponStatement cs : couponStatementList) {
                    cs.setStatus(SysConstants.COUPON_RETURN_ORDER.shortValue());
                    // 调用产品系统的优惠券接口，作废发放的优惠券
                    couponRemoteService.changeCustomGrantToGive(userInfo,
                            Long.valueOf(cs.getCouponId()), om.getCustomerId().longValue(),
                            SysConstants.COUPON_RETURN_ORDER, om.getOrderCode(),
                            cs.getGrantId().longValue());
                    couponStatementDAO.updateByPrimaryKey(cs);
                }
            } catch (Exception e) {
                logger.error("退货时作废订单所送优惠券异常", e);

            }

        }
        // ********************************如果是“退货”，作废订单所送优惠券，修改用户积分和等级 end
        // *********************************************
        // *************************************************** 退款 第三方支付处理模块
        // begin ***************************************************
        // 第三方支付，根据退款是否可以“原路返回”分为两种情况：一是支付宝支付；二是非支付宝支付
        boolean refundResult = true;// 退款成功标志
        if (isAliPay) {// 支付宝退款

            String newstAccount = om.getCustomerAccount();

            BigDecimal aliPayNeedToRefund = null;
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderCode", oldOrderAlter.getOrderCode());
            paramMap.put("paymentWay", OrderDictionaryEnum.OrderPayMethod.Platform.getKey());
            paramMap.put("platFormCode", OrderDictionaryEnum.PlatformCode.alipay.getKey());
            paramMap.put("refundWay",
                    OrderAlterDictionaryEnum.OrderReturnMethod.AlipayToBalance.getKey());
            aliPayNeedToRefund = orderAlterPayStatementService.needToRefund(paramMap);// 支付宝未退款总金额 =
                                                                                      // 支付宝支付总金额 -
            // 实际支付流水只会有3种：1、余额支付；2、支付宝支付；3、易宝支付，其中2和3互斥，isAliPay为true时所有支付金额退回到余额
            // 如果支付宝只是做部分支付，则优先把余额退还
            if (oldOrderAlter.getRuturnSum().compareTo(aliPayNeedToRefund) > 0) {
                // 记录一条支付宝退款流水，当流水已存在则更新金额
                RefundRequest rr = new RefundRequest();
                rr.setOrderCode(omain.getOrderCode());
                rr.setChannel(omain.getOrderChannel());
                rr.setSellerType(null == omain.getCommerceId() ? 1 : 2);
                rr.setSellerShop(omain.getCommerceName());
                rr.setPlatformCode(platFormCode);
                rr.setOutBatchNo(outBatchNo);
                rr.setMenberName(omain.getCustomerAccount());
                rr.setRefurnMoney(aliPayNeedToRefund);
                rr.setOrderAlterCode(newOrderAlter.getOrderAlterCode());
                rr.setStatus(0);
                rr.setRefundType(2);
                refundRequestService.addRefundRequest(rr);

                int rs = orderAlterPayStatementService.pay(operator,
                        (long) OrderAlterDictionaryEnum.OrderReturnMethod.Balance.getKey(),
                        newstAccount, newOrderAlter.getOrderAlterCode(),
                        oldOrderAlter.getRuturnSum().subtract(aliPayNeedToRefund), null, refundFlag,
                        null, oldStatus, (long) OrderDictionaryEnum.OrderPayState.Success.getKey());
                if (1 != rs) {
                    throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR,
                            "存在支付宝支付，B2B订单退换货余额退还时发生异常");
                }
            } else {// 支付宝全额支付情况
                // 记录一条支付宝退款流水，当流水已存在则更新金额
                if (0 != oldOrderAlter.getRuturnSum().compareTo(BigDecimal.ZERO)) {
                    RefundRequest rr = new RefundRequest();
                    rr.setOrderCode(omain.getOrderCode());
                    rr.setChannel(omain.getOrderChannel());
                    rr.setSellerType(null == omain.getCommerceId() ? 1 : 2);
                    rr.setSellerShop(omain.getCommerceName());
                    rr.setPlatformCode(platFormCode);
                    rr.setOutBatchNo(outBatchNo);
                    rr.setMenberName(omain.getCustomerAccount());
                    rr.setRefurnMoney(oldOrderAlter.getRuturnSum());
                    rr.setOrderAlterCode(newOrderAlter.getOrderAlterCode());
                    rr.setStatus(0);
                    rr.setRefundType(2);
                    refundRequestService.addRefundRequest(rr);
                }

            }


        } else {// 处理第三方支付中的非支付宝部分的逻辑
            String interfaceSysCode = SysConstants.B2B_SYSCODE;// 暂时只通过B2B的查询接口查询已退款信息
            Map map = new HashMap();
            map.put("orderCode", om.getOrderCode());
            map.put("payway", OrderDictionaryEnum.OrderPayMethod.Balance.getKey());
            BigDecimal balanceMoneyNeedToRefund = orderPayStatementService.getOrderPay(map);// 余额需要退款金额

            BigDecimal balanceAndReserveNeedToRefund = balanceMoneyNeedToRefund;// 余额+预备金需要退款金额之和

            map.put("payway", OrderDictionaryEnum.OrderPayMethod.Platform.getKey());
            BigDecimal platFormNeedToRefund = orderPayStatementService.getOrderPay(map);// 第三方支付平台需要退款的金额

            // 退余额
            BigDecimal platFormAlreadyRefund = BigDecimal.ZERO;
            int platFormNameKey = 0;
            // 如果第三方支付平台需要退款的金额不为0，则需要通过调用接口查询已退款信息
            if (0.0 != platFormNeedToRefund.doubleValue()) {
                try {
                    QueryResult qr = null;

                    qr = qryOrderOnLineService.queryByOrder(om.getParentOrderCode() == null
                            ? om.getOrderCode() : om.getParentOrderCode());


                    if (null != qr) {
                        platFormAlreadyRefund = new BigDecimal(qr.getRd_RefundAmt());// 第三方历史已退
                    }
                    // 康美通返回来的为0，所以当为0时，需要统计表order_alter_pay_statement中的order_moeny统计历史已退
                    if (BigDecimal.ZERO.equals(platFormAlreadyRefund)) {
                        Map<String, String> queryC = new HashMap<String, String>();
                        platFormAlreadyRefund =
                                orderPayStatementService.getOrderPayByOrderCode(om.getOrderCode());
                    }
                    if (qr != null) {
                        String platFormKey =
                                OrderAlterPayBackEnum.getValueByKey(Integer.valueOf(qr.getR8_MP()));
                        platFormNameKey = OrderAlterDictionaryEnum.OrderReturnMethod
                                .getKeyByValue(platFormKey);
                    }
                    // OrderPayMethod.getValueByKey(key)
                } catch (Exception e) {
                    logger.error("退换货时查询第三方已退款信息时发生异常", e);
                    throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR,
                            "退换货时查询第三方已退款信息时发生异常");
                }
            }
            BigDecimal balanceAndReserveAlreadyRefund =
                    allReadyRefundWithOutFare.subtract(platFormAlreadyRefund);// 余额+预备金历史已退
            BigDecimal balanceAndReserveMaxRefund =
                    balanceAndReserveNeedToRefund.subtract(balanceAndReserveAlreadyRefund);// 余额+预备金最大可退
            BigDecimal platFormMaxRefund = platFormNeedToRefund.subtract(platFormAlreadyRefund);// 第三方最大可退
            // 整单最大可退-余额+预备金最大可退>0，则当次退余额+预备金总金额=余额+预备金最大可退；否则当次退余额+预备金总金额=整单最大可退+运费补偿
            BigDecimal balanceAndReserveRefundNow =
                    (oldOrderAlter.getRuturnMoney().add(oldOrderAlter.getReturnFare())
                            .subtract(balanceAndReserveMaxRefund).doubleValue() > 0.0
                                    ? balanceAndReserveMaxRefund
                                    : oldOrderAlter.getRuturnMoney()
                                            .add(oldOrderAlter.getReturnFare()))
                                                    .add(oldOrderAlter.getFareSubsidy());
            // 退第三方原路返回
            BigDecimal platFormRefundNow;// 当次退第三方的金额
            // 如果整单最大可退的金额大于第三方支付最大可退，则根据退款的优先级，当次退第三方的金额为第三方支付最大可退
            if (oldOrderAlter.getRuturnSum().subtract(platFormMaxRefund).doubleValue() >= 0.0) {
                if (oldOrderAlter.getFareSubsidy() != null
                        && oldOrderAlter.getFareSubsidy().doubleValue() != 0.0) {
                    if (oldOrderAlter.getRuturnMoney() != null
                            && oldOrderAlter.getRuturnMoney().doubleValue() != 0.0) {
                        if (oldOrderAlter.getRuturnMoney().add(oldOrderAlter.getReturnFare())
                                .subtract(platFormMaxRefund).doubleValue() >= 0.0) {
                            platFormRefundNow = platFormMaxRefund;
                            balanceAndReserveRefundNow = oldOrderAlter.getFareSubsidy()
                                    .add(oldOrderAlter.getRuturnMoney()
                                            .add(oldOrderAlter.getReturnFare())
                                            .subtract(platFormRefundNow));
                        } else {
                            platFormRefundNow = oldOrderAlter.getRuturnMoney();
                            balanceAndReserveRefundNow = oldOrderAlter.getFareSubsidy();
                        }
                    } else {
                        platFormRefundNow = new BigDecimal(0.0);
                        balanceAndReserveRefundNow = oldOrderAlter.getFareSubsidy();
                    }
                } else {
                    if (oldOrderAlter.getRuturnMoney().add(oldOrderAlter.getReturnFare())
                            .subtract(platFormMaxRefund).doubleValue() >= 0.0) {
                        platFormRefundNow = platFormMaxRefund;
                        balanceAndReserveRefundNow = oldOrderAlter.getRuturnMoney()
                                .add(oldOrderAlter.getReturnFare()).subtract(platFormRefundNow);
                    } else {
                        platFormRefundNow =
                                oldOrderAlter.getRuturnMoney().add(oldOrderAlter.getReturnFare());
                        balanceAndReserveRefundNow = oldOrderAlter.getFareSubsidy();
                    }

                }
            } else {// 补偿运费退余额，其他全部退第三方
                if (oldOrderAlter.getFareSubsidy() != null
                        && oldOrderAlter.getFareSubsidy().doubleValue() != 0.0) {
                    // platFormRefundNow = new BigDecimal(0.0);
                    platFormRefundNow =
                            oldOrderAlter.getRuturnSum().subtract(oldOrderAlter.getFareSubsidy());
                    // balanceAndReserveRefundNow = oldOrderAlter.getRuturnSum();
                    balanceAndReserveRefundNow = oldOrderAlter.getFareSubsidy();
                } else {
                    platFormRefundNow = oldOrderAlter.getRuturnSum();
                    balanceAndReserveRefundNow = new BigDecimal(0.0);
                }
            }
            // 退第三方准备，在OrderAlterPayStatement中插入一条准备中的支付流水
            if (0.0 != platFormRefundNow.doubleValue()) {
                int rs = orderAlterPayStatementService.pay(operator, (long) platFormNameKey,
                        account, newOrderAlter.getOrderAlterCode(), platFormRefundNow, null,
                        refundFlag, null, oldStatus,
                        (long) OrderDictionaryEnum.OrderPayState.Ready.getKey());
                if (1 != rs) {
                    throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR,
                            "退换货，往退换货支付流水表插入准备中的流水数据发生异常");
                }
            }
            // 第三方退回
            if (0.0 != platFormRefundNow.doubleValue()) {
                try {
                    Integer rechargeOrOrderFlag = 2;
                    ReturnResult returnResult = null;

                    returnResult = refundOrderService.refundOrder(om.getOrderCode(),
                            rechargeOrOrderFlag, platFormRefundNow,
                            String.valueOf(oldOrderAlter.getOrderAlterCode()));

                    logger.info("退换货单" + oldOrderAlter.getOrderAlterCode() + "退款结果returnResult="
                            + returnResult.getMessage());

                    if (RefundResultCode.UNSUPORT_BANK_REFUND_EXCEPTION
                            .equals(returnResult.getCode())) {// 不支持退款转到余额
                        orderAlterPayStatementService.pay(operator, (long) platFormNameKey, account,
                                newOrderAlter.getOrderAlterCode(), platFormRefundNow,
                                (String) returnResult.getReturnObject(), refundFlag, null,
                                oldStatus, (long) OrderDictionaryEnum.OrderPayState.Fail.getKey());
                        logger.error("退换货单号" + newOrderAlter.getOrderAlterCode() + "第三方退款不成功"
                                + returnResult.toString());

                    }
                    if (RefundResultCode.SUCCESS.equals(returnResult.getCode())) {
                        orderAlterPayStatementService.pay(operator, (long) platFormNameKey, account,
                                newOrderAlter.getOrderAlterCode(), platFormRefundNow,
                                (String) returnResult.getReturnObject(), refundFlag, null,
                                oldStatus,
                                (long) OrderDictionaryEnum.OrderPayState.Success.getKey());
                    } else if (RefundResultCode.FAILED.equals(returnResult.getCode())) {
                        refundResult = false;
                        orderAlterPayStatementService.pay(operator, (long) platFormNameKey, account,
                                newOrderAlter.getOrderAlterCode(), platFormRefundNow,
                                (String) returnResult.getReturnObject(), refundFlag, null,
                                oldStatus, (long) OrderDictionaryEnum.OrderPayState.Fail.getKey());
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                    logger.error("退换货单号" + newOrderAlter.getOrderAlterCode() + "第三方退款不成功"
                            + e1.getMessage());
                }
            }
            // *************************************************** 退款
            // 第三方支付处理模块
            // 余额+预备金退还
            if (0.0 != balanceAndReserveRefundNow.doubleValue()) {


                try {
                    if (!orderMainDAO.checkAccount(account)) {
                        account = orderMainDAO.getAccount(
                                null != om.getCustomerId() ? om.getCustomerId().longValue() : 0l);
                    }
                } catch (Exception e) {

                    logger.error("查询账户信息表异常", e);
                    throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                            "查询账户信息表异常" + e.getMessage());
                }

                if (null == account && "".equals(account)) {
                    logger.error("账户account为空");
                    throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                            "账户account为空");
                }
                int rs = savePayStatement(operator, newOrderAlter, oldStatus, account, refundFlag,
                        balanceAndReserveRefundNow);
                if (1 != rs) {
                    throw new ServiceException(ErrorCode.INNER_RETURNS_REFUND_ERROR,
                            "订单退换货余额退还时发生异常!返回结果rs=" + rs);
                }

            }
        }
        if (refundResult) { // 退款成功，则更新退换货状态，添加成功退款操作流水表
            // 为何还要在这更新退换货单和记录操作流水
            updateOrderAlter(oldOrderAlter);
            // 记录操作流水
            saveOperateStatement(newOrderAlter, oldOrderAlter, operator);
        }
        return 1;
        // end ***************************************************

    }
}
