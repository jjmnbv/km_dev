package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.b2b.service.RefundOrderService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.user.remote.service.TrationListRemoteService;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.dao.OrderOperateStatementDAO;
import com.pltfm.app.dao.OrderPayStatementDAO;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.service.OrderPayStatementService;
import com.pltfm.app.service.RefundRequestService;
import com.pltfm.app.service.ReturnFundForKJService;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.SysConstants;
import com.pltfm.app.vobject.RefundRequest;
import com.pltfm.sys.util.ErrorCode;

@Service("returnFundForKJService")
@Scope("singleton")

public class ReturnFundForKJServiceImpl extends BaseService implements ReturnFundForKJService {

    private final static Logger logger = Logger.getLogger(ReturnFundForKJServiceImpl.class);

    @Resource
    OrderMainDAO orderMainDAO;
    @Resource
    OrderPayStatementService orderPayStatementService;
    @Resource
    OrderPayStatementDAO orderPayStatementDAO;
    @Resource
    RefundRequestService refundRequestService;
    @Resource
    OrderOperateStatementDAO orderOperateStatementDAO;
    // 客户系统的修改账户余额的接口服务类
    @Resource
    private TrationListRemoteService trantionListRemoteService;
    @Resource
    private RefundOrderService refundOrderService;


    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public String returnFund(String orderCode) throws ServiceException {
        String TrxId = "";
        String platFormCode = null;
        String platFormName = null;
        int payWay = 0;
        String comment;
        Date now = new Date();
        OrderPayStatement platformop = null;
        boolean isZFB = false;// 是否支付宝
        BigDecimal payMoney = BigDecimal.ZERO;
        OrderMain om = null;

        try {
            om = orderMainDAO.getOrderMainInfoForKJ(orderCode);
            if (null == om) {
                logger.error("表BARGAIN_ORDER_MAIN无orderCode=" + orderCode + "记录");
                throw new ServiceException(ErrorCode.INNER_PAYMENT_CALC_ERROR,
                        "表BARGAIN_ORDER_MAIN无orderCode=" + orderCode + "记录");
            }
        } catch (Exception e) {
            logger.error("查询订单发生异常", e);
            throw new ServiceException(ErrorCode.INNER_PAYMENT_CALC_ERROR,
                    "查询订单发生异常,orderCode=" + orderCode);
        }

        // query OrderPayStatement by orderCode
        List<OrderPayStatement> orderPayList = new ArrayList<OrderPayStatement>();
        try {
            orderPayList = orderPayStatementDAO.getPayInfoForKJ(orderCode);
        } catch (SQLException e) {
            logger.error("通过订单号" + orderCode + "获取砍价端支付信息失败", e);
            throw new ServiceException(ErrorCode.INNER_PAYMENT_CALC_ERROR,
                    "获取支付总额发生错误：" + e.getMessage());
        }
        try {
            OrderPayStatement payObject = new OrderPayStatement();
            if (null != orderPayList && orderPayList.size() > 0) {
                for (int i = 0; i < orderPayList.size(); i++) {
                    payObject = orderPayList.get(i);
                    payMoney = payObject.getOrderMoney();
                    platFormCode = payObject.getPlatFormCode();
                    platFormName = payObject.getPlatFormName();
                    payWay = null != payObject.getPaymentWay()
                            ? payObject.getPaymentWay().intValue() : 0;

                    isZFB = (OrderDictionaryEnum.PlatformCode.alipay.getKey() + "")
                            .equals(platFormCode);

                    // 记录操作流水-退款
                    comment = "您的订单单中已支付部分金额已返还到您的余额及银行账户。";
                    TrxId = paidMoneyReturn(SysConstants.SYS, om, orderCode, platFormCode,
                            platFormName, payWay, comment, now, payObject, payMoney, isZFB);
                    payObject = null;

                }
            } else {
                logger.error("订单" + orderCode + "无支付成功信息");
                return "FAIL";
            }
        } catch (Exception e) {
            logger.error("order" + orderCode + "退款失败", e);
            throw new ServiceException(ErrorCode.INNER_PAYMENT_CALC_ERROR,
                    "orderCode" + orderCode + "退款失败！" + e.getMessage());
        }

        return TrxId;

    }

    private String paidMoneyReturn(String operator, OrderMain om, String orderCode,
            String platFormCode, String platFormName, int payWay, String comment, Date now,
            OrderPayStatement platformop, BigDecimal payMoney, boolean isZFB)
            throws ServiceException {
        String TrxId = "SUCCESS";
        // 退回预备金
        /*
         * 删除预备金 if (OrderDictionaryEnum.OrderPayMethod.Reserve.getKey() == payWay) { int payResult
         * = this.pay(om,(long) OrderAlterDictionaryEnum.OrderReturnMethod.Reserve.getKey(),
         * om.getCustomerAccount(), orderCode, payMoney, (long)
         * OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()); if (1 != payResult) { throw new
         * ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR, "订单退还失败"); } } else
         */ if (OrderAlterDictionaryEnum.OrderReturnMethod.Balance.getKey() == payWay) {// 退回余额账户
            int payResult =
                    this.pay(om, (long) OrderAlterDictionaryEnum.OrderReturnMethod.Balance.getKey(),
                            om.getCustomerAccount(), orderCode, payMoney,
                            (long) OrderDictionaryEnum.OrderPayFlag.Refundment.getKey());
            if (1 != payResult) {
                throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR, "订单退还失败");
            }
        } else {// 退回第三方准备
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
                    rr.setRefurnMoney(payMoney);
                    rr.setStatus(0);
                    rr.setRefundType(1);
                    refundRequestService.addRefundRequest(rr);
                } catch (Exception e1) {
                    logger.error("添加支付宝退款流水发生异常", e1);
                }
            } else {
                // 第三方退回，实际的退款动作通过调用B2B的相关接口完成，暂不支持支付宝的原路返回
                Integer rechargeOrOrderFlag = 2;
                ReturnResult returnResult = null;
                try {
                    returnResult = refundOrderService.refundOrder(om.getOrderCode(),
                            rechargeOrOrderFlag, payMoney, om.getOrderCode());

                    logger.info("订单" + om.getOrderCode() + "退款结果returnResult="
                            + returnResult.getMessage());
                } catch (Exception e1) {
                    logger.error("订单" + om.getOrderCode() + "第三方退回发生异常,返回结果："
                            + (null != returnResult ? returnResult.toString() : "null")
                            + e1.getMessage());
                }

            }


        }
        return TrxId;
    }



    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int pay(OrderMain om, Long paymentWay, String account, String orderCode,
            BigDecimal orderMoney, Long flag) throws ServiceException {
        int result = -1;

        if (flag.intValue() == OrderDictionaryEnum.OrderPayFlag.Refundment.getKey()) {// 退款
            // 退款条件限制
            if (om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Not_Pay.getKey()
                    || om.getOrderStatus().intValue() == OrderDictionaryEnum.Order_Status.Pay_Done
                            .getKey()
                    || om.getOrderStatus()
                            .intValue() == OrderDictionaryEnum.Order_Status.Cancel_Done.getKey()) {
                // 前置条件：已完成
                /*
                 * 删除预备金 if (paymentWay.intValue() ==
                 * OrderAlterDictionaryEnum.OrderReturnMethod.Reserve.getKey()) { // 预备金退回 try { if
                 * (1 != trantionListRemoteService.updateReserver(account, orderMoney.doubleValue(),
                 * orderCode, "支付内容:退款到预备金账户", 14, 1)) { throw new
                 * ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_PAY_ERROR, "预备金退回失败"); } result
                 * = 1; } catch (Exception e) { logger.error("预备金退回异常", e); throw new
                 * ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR, "预备金退回失败：" +
                 * e.getMessage()); } } else
                 */ if (paymentWay.intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.Balance
                        .getKey()
                        || paymentWay
                                .intValue() == OrderAlterDictionaryEnum.OrderReturnMethod.AlipayToBalance
                                        .getKey()) {
                    // 余额退回
                    try {
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
                        logger.error("余额退回异常", e);
                        throw new ServiceException(ErrorCode.INNER_PAYMENT_ASSEMBLY_ERROR,
                                "余额退回失败：" + e.getMessage());
                    }
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


}
