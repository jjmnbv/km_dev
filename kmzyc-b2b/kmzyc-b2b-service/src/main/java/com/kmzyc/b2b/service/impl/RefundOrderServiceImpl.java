package com.kmzyc.b2b.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.model.OrderPayStatement;
import com.kmzyc.b2b.model.RefundParamInfo;
import com.kmzyc.b2b.model.RefundResult;
import com.kmzyc.b2b.service.OrderPayStatementService;
import com.kmzyc.b2b.service.RefundOrderService;
import com.kmzyc.b2b.util.XMLHelper;
import com.kmzyc.b2b.util.hessian.HessianContext;
import com.kmzyc.b2b.util.httpClient.WebUtils;
import com.kmzyc.b2b.util.pay.BasePayPlugIn;
import com.kmzyc.b2b.util.pay.BasePayPlugInFactory;
import com.kmzyc.b2b.util.pay.BuildParam;
import com.kmzyc.b2b.util.pay.PayPlugInManger;
import com.kmzyc.b2b.util.pay.util.PaymentUtil;
import com.kmzyc.b2b.util.wxpay.CommonUtil;
import com.kmzyc.b2b.util.wxpay.client.TenpayHttpClient;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.constants.RefundResultCode;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.zkconfig.ConfigurationUtil;

import net.sf.json.JSONObject;

@Service("refundOrderService")
@SuppressWarnings("unchecked")
public class RefundOrderServiceImpl implements RefundOrderService {

    @Resource(name = "orderPayStatementServiceImpl")
    private OrderPayStatementService orderPayStatementService;
    /**
     * 精度
     */
    private static final int MONEY_SCALE = 2;
    // private static Logger log = Logger.getLogger(RefundOrderServiceImpl.class);
    private static Logger log = LoggerFactory.getLogger(RefundOrderServiceImpl.class);
    private static final Random random = new Random();

    /**
     * 支付退款
     *
     * @param orderCode           订单编号
     * @param rechargeOrOrderFlag 1:用户充值 2:用户订单支付
     * @param amount              退款金额 遵循"四舍六入，逢五奇进偶舍" 这里"四"是小于五的意思,"六"是大于五的意思,"五"是舍入位之后的尾数逢五的话看前一位，奇进偶不进，
     *                            就像1.25，因为2是偶数，所以是1.2。又像1.35，因为3是奇数，所以是1.4
     * @return ReturnResult
     */
    @Override
    public ReturnResult refundOrder(String orderCode, int rechargeOrOrderFlag, BigDecimal amount,
                                    String batchNo) {
        ReturnResult returnResult = null;
        // 用户充值
        if (rechargeOrOrderFlag == 1) {
            returnResult = new ReturnResult<>(RefundResultCode.UNSUPORT_DRAWALS_EXCEPTION,
                    "暂时不支持用户提现功能！", null);
            return returnResult;
        }
        // 用户订单支付
        if (rechargeOrOrderFlag == 2) {
            // 查询订单支付表 order_pay_statement
            OrderPayStatement orderPayStatement;
            try {
                orderPayStatement = orderPayStatementService.findrefundOrderPayStatement(orderCode);
                if (orderPayStatement == null) {
                    returnResult = new ReturnResult<>(RefundResultCode.NO_PAY_STATEMENT_EXCEPTION,
                            "此订单没有银行支付流水。", null);
                    return returnResult;
                }
            } catch (ServiceException e1) {
                log.error("", e1);
                returnResult = new ReturnResult<>(RefundResultCode.EXCEPTION, e1.getErrorString(),
                        null);
                return returnResult;
            }
            // 预售订单尾款退款处理
            // String ysFlage = orderPayStatement.getYsFlage();
            // if(null != ysFlage && "2".equals(ysFlage)){ //"2"代表尾款支付
            // batchNo = batchNo+"a";//尾款支付的实际batchNo = orderCode+"a"
            // }
            if (amount == null || amount.compareTo(orderPayStatement.getOrderMoney()) > 0) {
                returnResult = new ReturnResult<>(RefundResultCode.ERROR_REFUND_MONEY_EXCEPTION,
                        "退款金额为空或退款金额不能大于订单金额.", null);
                return returnResult;
            }
            BigDecimal amountRS = amount.setScale(MONEY_SCALE, BigDecimal.ROUND_HALF_EVEN);// 实退金额
            // 构造refundParamInfo
            RefundParamInfo refundParamInfo = new RefundParamInfo();
            refundParamInfo.setPb_TrxId(orderPayStatement.getOutsidePayStatementNo());
            refundParamInfo.setP3_Amt(amountRS.toString());
            refundParamInfo.setP5_Desc("order refund");
            refundParamInfo.setBatchNo(batchNo);
            if (ConfigurationUtil.getString("ali_pay_code")
                    .equals(orderPayStatement.getPlatformCode())) {
                // 支付宝退款
                try {
                    aliRefund(refundParamInfo);
                } catch (ServiceException e) {
                    returnResult = new ReturnResult<>(InterfaceResultCode.FAILED, e.getMessage(),
                            null);
                    return returnResult;
                }
            } else if (ConfigurationUtil.getString("ten_pay_code")
                    .equals(orderPayStatement.getPlatformCode()) ||
                    ConfigurationUtil.getString("wx_pay_code")
                            .equals(orderPayStatement.getPlatformCode()) ||
                    ConfigurationUtil.getString("kmt_pay_code")
                            .equals(orderPayStatement.getPlatformCode())) {
                // 财付通、微信退款
                RefundResult rr = null;
                boolean refundCk = false;   //判断是否退款成功
                for (int req = 1; req <= 3 && (!refundCk); req++) {

                    try {
                        refundParamInfo.setP1_MerId(orderPayStatement.getPlatformCode());
                        if (ConfigurationUtil.getString("ten_pay_code")
                                .equals(orderPayStatement.getPlatformCode())) {
                            rr = tenPayRefund(refundParamInfo, orderPayStatement);
                        } else if (ConfigurationUtil.getString("wx_pay_code")
                                .equals(orderPayStatement.getPlatformCode())) {
                            rr = wxPayRefund(refundParamInfo, orderPayStatement);
                        } else if (ConfigurationUtil.getString("kmt_pay_code")
                                .equals(orderPayStatement.getPlatformCode())) {
                            rr = KMTPayRefund(refundParamInfo, orderPayStatement);
                        }
                        if (null != rr && ("SUCCESS".equals(rr.getR1_Code()) ||
                                "0".equals(rr.getR1_Code()))) {
                            returnResult = new ReturnResult<Object>(RefundResultCode.SUCCESS,
                                    "退款成功。", rr.getR2_TrxId());
                            log.info("订单号：" + orderCode + "，退款轮询，于第" + req + "次退款成功,并退出轮询");
                            refundCk = true;
                            // return returnResult;
                        } else {
                            log.error("订单号：" + orderCode + "，退款轮询，第" + req + "次退款失败。refundResult:" +
                                    rr);
                            returnResult = new ReturnResult<>(RefundResultCode.FAILED,
                                    "退款失败，请联系客服支持。", null);
                            // return returnResult;
                        }
                    } catch (ServiceException e) {
                        log.error("订单号：" + orderCode + "，退款轮询，第" + req + "次退款失败", e);
                        returnResult = new ReturnResult<>(RefundResultCode.FAILED, "退款失败，请联系客服支持。",
                                null);
                        // return returnResult;
                    }

                }
                return returnResult;
            }         }
        return null;
    }

    /**
     * 支付宝退款
     */
    private void aliRefund(RefundParamInfo rp) throws ServiceException {
        // 根据返回参数构造不同支付组件工厂类,由支付组件工厂类创建支付组件
        BuildParam bd = new BuildParam();
        bd.setFactoryType(PayPlugInManger.PLATFORM_ALI_PAY);
        bd.setPlugInType(BasePayPlugInFactory.ACCESSTYPE_WEB);
        BasePayPlugInFactory factory = PayPlugInManger.createFactory(bd);
        BasePayPlugIn plugIn;
        try {
            plugIn = factory.createPayPlugIn();
            plugIn.setRefundInfo(rp);
            String result = plugIn.onPay();
            ServletResponse response = HessianContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            PrintWriter out;
            try {
                out = response.getWriter();
                out.write(result);
                out.flush();
                out.close();
            } catch (IOException e) {
                log.error("", e);
                throw new ServiceException(1, "response 向页面写入信息错误:" + e.getMessage(), e);
            }
        } catch (Exception e) {
            log.error("", e);
            throw new ServiceException(1, "构建支付宝退款工厂出错:" + e.getMessage(), e);
        }
    }

    /**
     * 财付通退款
     */
    private RefundResult tenPayRefund(RefundParamInfo rp, OrderPayStatement ops) throws
            ServiceException {
        log.info("--------订单号:" + ops.getOrderCode() + "，进入tenPayRefund方法--------");
        RefundResult rr = null;
        String charset = ConfigurationUtil.getString("ten_pay_input_charset");
        SortedMap<String, String> reParams = new TreeMap<>();
        reParams.put("sign_type", ConfigurationUtil.getString("ten_pay_sign_type"));// 签名方式
        reParams.put("input_charset", charset);// 字符集
        reParams.put("sign_key_index",
                ConfigurationUtil.getString("ten_pay_sign_key_index"));// 密钥序号
        reParams.put("service_version",
                ConfigurationUtil.getString("ten_pay_refund_req_service_version"));// 接口版本
        reParams.put("partner", rp.getP1_MerId());// 商户号
        reParams.put("out_trade_no", ops.getOrderCode());// 商户订单号
        reParams.put("transaction_id", rp.getPb_TrxId());// 财付通交易号
        String batchNo = rp.getBatchNo();
        if (null == batchNo || "".equals(batchNo)) {
            batchNo = ops.getOrderCode();// 取消为订单号
        }
        reParams.put("out_refund_no", batchNo);// 退款单号
        reParams.put("total_fee",
                ops.getOrderMoney().multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).toBigInteger()
                        .toString());// 总金额
        reParams.put("refund_fee",
                new BigDecimal(rp.getP3_Amt()).multiply(BigDecimal.TEN).multiply(BigDecimal.TEN)
                        .toBigInteger().toString());// 退款金额 分
        reParams.put("op_user_id", ConfigurationUtil.getString("ten_pay_operater"));// 操作员
        reParams.put("op_user_passwd",
                ConfigurationUtil.getString("ten_pay_operater_password"));// 操作员密码
        try {
            TenpayHttpClient thc = new TenpayHttpClient(1);
            thc.setReqContent(PaymentUtil
                    .getTenPayRequestURL(ConfigurationUtil.getString("ten_pay_refund_req_url"),
                            reParams));
            String responseInfo = null;
            if (thc.call()) {
                responseInfo = thc.getResContent();
            }
            log.info("订单号:" + batchNo + "，微信退款返回responseInfo：" +
                    (responseInfo == null ? "null" : responseInfo));
            if (null != responseInfo) {
                Map<String, String> rsMap = XMLHelper.singleLevelAnalyze(responseInfo);
                if ("0".equals(rsMap.get("retcode")) && PaymentUtil
                        .isTenpaySign(rsMap, ConfigurationUtil.getString("ten_pay_private_key"))) {
                    rr = new RefundResult();
                    rr.setR1_Code(rsMap.get("retcode"));
                    rr.setR2_TrxId(rsMap.get("refund_id"));
                }
            }
        } catch (Exception e) {
            log.error("订单" + batchNo + "退款失败!" + e.getMessage());
            throw new ServiceException(0, e.getMessage());
        }
        return rr;
    }

    /**
     * 微信退款
     */
    private RefundResult wxPayRefund(RefundParamInfo rp, OrderPayStatement ops) throws
            ServiceException {
        log.info("--------订单号:" + ops.getOrderCode() + "，进入wxPayRefund方法--------");
        RefundResult rr = null;
        SortedMap<String, String> reParams = new TreeMap<>();

        String batchNo = rp.getBatchNo();
        if (null == batchNo || "".equals(batchNo)) {
            batchNo = ops.getOrderCode();// 取消为订单号
        }

        reParams.put("appid", ConfigurationUtil.getString("wx_pay_appid"));// 公众账号
        // ID
        reParams.put("mch_id", ConfigurationUtil.getString("wx_pay_partner"));// 商户号
        reParams.put("nonce_str", CommonUtil.CreateNoncestr());// 随机字符串
        reParams.put("out_trade_no", ops.getOrderCode());// 商户订单号
        reParams.put("out_refund_no", batchNo);// 退款单号
        reParams.put("total_fee",
                ops.getOrderMoney().multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).toBigInteger()
                        .toString());// 总金额
        reParams.put("refund_fee",
                new BigDecimal(rp.getP3_Amt()).multiply(BigDecimal.TEN).multiply(BigDecimal.TEN)
                        .toBigInteger().toString());// 退款金额 分
        reParams.put("op_user_id", ConfigurationUtil.getString("wx_pay_partner"));// 操作员

        try {

            TenpayHttpClient thc = new TenpayHttpClient(2);
            // HttpUtils.URLPost(ConfigurationUtil.getString("wx_pay_refund_req_url"),
            // PaymentUtil.getWXPayMap(reParams), charset);
            thc.setReqContent(PaymentUtil
                    .getWXPayRequestURL(ConfigurationUtil.getString("wx_pay_refund_req_url"),
                            reParams));

            thc.setReParams(PaymentUtil.getWXPayMap(reParams));

            String responseInfo = null;
            if (thc.call()) {
                responseInfo = thc.getResContent();
            }
            log.info("订单号:" + batchNo + "，微信退款返回responseInfo：" + (responseInfo == null ? "null" :
                    responseInfo));
            if (null != responseInfo) {
                Map<String, String> rsMap = XMLHelper.singleLevelAnalyze(responseInfo);
                if ("SUCCESS".equals(rsMap.get("return_code")) && PaymentUtil
                        .isTenpaySign(rsMap, ConfigurationUtil.getString("wx_pay_private_key"))) {
                    rr = new RefundResult();
                    rr.setR1_Code(rsMap.get("return_code"));
                    rr.setR2_TrxId(rsMap.get("refund_id"));
                }
            }
        } catch (Exception e) {
            log.error("订单" + batchNo + "退款失败!" + e.getMessage());
            throw new ServiceException(0, e.getMessage());
        }
        return rr;
    }

    /**
     * 康美通退款
     */
    private RefundResult KMTPayRefund(RefundParamInfo rp, OrderPayStatement ops) throws
            ServiceException {
        log.info("--------订单号:" + ops.getOrderCode() + "，进入KMTPayRefund方法--------");
        String batchNo = rp.getBatchNo();
        if (null == batchNo || "".equals(batchNo)) {
            batchNo = ops.getOrderCode();// 取消为订单号
        }
        RefundResult rr = null;
        String timestamp = String.valueOf(System.currentTimeMillis());
        String charset = ConfigurationUtil.getString("ten_pay_input_charset");
        SortedMap<String, String> reParams = new TreeMap<>();
        reParams.put("method", "kmpay.refund.req");
        reParams.put("partner", ConfigurationUtil.getString("kmt_pay_partner"));
        reParams.put("inputCharset", charset);
        reParams.put("v", "2.0");
        reParams.put("format", "json");
        reParams.put("clientIp", ConfigurationUtil.getString("kmt_request_ip"));
        reParams.put("timestamp", timestamp);
        reParams.put("sellerEmail", ConfigurationUtil.getString("kmt_seller_email"));
        reParams.put("refundDate", timestamp);
        reParams.put("batchNo", timestamp + random.nextInt(10000));
        reParams.put("batchNum", "1");
        reParams.put("detailData", ops.getOutsidePayStatementNo() + '^' +
                new BigDecimal(rp.getP3_Amt()).multiply(BigDecimal.TEN).multiply(BigDecimal.TEN)
                        .toBigInteger().toString() + "^refund");
        try {
            String responseStr = WebUtils.doPost(ConfigurationUtil.getString("kmt_refund_req_url"),
                    PaymentUtil.buildRequestParaForKMT(reParams,
                            ConfigurationUtil.getString("kmt_sign_type")), 20000, 20000, true);
            JSONObject result;
            log.info("订单号:" + batchNo + "，康美通退款返回responseStr：" + (responseStr == null ? "null" :
                    responseStr));
            if (null != responseStr && !responseStr.isEmpty() &&
                    null != (result = JSONObject.fromObject(responseStr)) &&
                    result.containsKey("data")) {
                Map<String, String> resMap = new HashMap<>();
                String value;
                JSONObject json = result.getJSONObject("data");
                for (Object key : json.keySet()) {
                    value = json.getString(key.toString());
                    if (null != value && value.length() > 0 && !"null".equals(value)) {
                        resMap.put(key.toString(), value);
                    }
                }
                if ("1".equals(json.getString("successNum")) &&
                        PaymentUtil.verifyIsKMTPay(resMap)) {
                    rr = new RefundResult();
                    rr.setR1_Code("0");
                    rr.setR2_TrxId(resMap.get("batchNo"));
                }
            }
        } catch (Exception e) {
            log.error("订单" + batchNo + "退款失败!" + e.getMessage());
            throw new ServiceException(0, e.getMessage());
        }
        return rr;
    }

    @Override
    public ReturnResult refundOrderForYs(String orderCode, int rechargeOrOrderFlag,
                                         BigDecimal amount, String batchNo, String ysFlage) {

        ReturnResult returnResult = null;
        // 用户订单支付
        if (rechargeOrOrderFlag == 2) {
            // 查询订单支付表 order_pay_statement
            OrderPayStatement orderPayStatement;
            try {
                Map<String, String> conditionMap = new HashMap<>();
                conditionMap.put("orderCode", orderCode);
                conditionMap.put("ysFlage", ysFlage);
                orderPayStatement = orderPayStatementService
                        .findrefundOrderPayStatementForYs(conditionMap);
                if (orderPayStatement == null) {
                    returnResult = new ReturnResult<>(RefundResultCode.NO_PAY_STATEMENT_EXCEPTION,
                            "此订单没有银行支付流水。", null);
                    return returnResult;
                }
            } catch (ServiceException e1) {
                log.error("", e1);
                returnResult = new ReturnResult<>(RefundResultCode.EXCEPTION, e1.getErrorString(),
                        null);
                return returnResult;
            }
            // 预售订单尾款退款处理
            String ysFlageCheck = orderPayStatement.getYsFlage();
            if (null == ysFlageCheck || !ysFlage.equals(ysFlageCheck)) {
                returnResult = new ReturnResult<>(RefundResultCode.FAILED,
                        "订单" + orderCode + "退款标识ysFlage=" + ysFlage + "!=订单支付流水表中ysFlage" +
                                ysFlageCheck, null);
                return returnResult;
            }
            if ( "2".equals(ysFlageCheck)) { // "2"代表尾款支付
                batchNo = batchNo + "a";// 尾款支付的实际batchNo = orderCode+"a"
            }
            if (amount == null || amount.compareTo(orderPayStatement.getOrderMoney()) > 0) {
                returnResult = new ReturnResult<>(RefundResultCode.ERROR_REFUND_MONEY_EXCEPTION,
                        "退款金额为空或退款金额不能大于订单金额.", null);
                return returnResult;
            }
            BigDecimal amountRS = amount.setScale(MONEY_SCALE, BigDecimal.ROUND_HALF_EVEN);// 实退金额
            // 构造refundParamInfo
            RefundParamInfo refundParamInfo = new RefundParamInfo();
            refundParamInfo.setPb_TrxId(orderPayStatement.getOutsidePayStatementNo());
            refundParamInfo.setP3_Amt(amountRS.toString());
            refundParamInfo.setP5_Desc("order refund");
            refundParamInfo.setBatchNo(batchNo);
            if (ConfigurationUtil.getString("ali_pay_code")
                    .equals(orderPayStatement.getPlatformCode())) {
                // 支付宝退款
                try {
                    aliRefund(refundParamInfo);
                } catch (ServiceException e) {
                    returnResult = new ReturnResult<>(InterfaceResultCode.FAILED, e.getMessage(),
                            null);
                    return returnResult;
                }
            } else if (ConfigurationUtil.getString("ten_pay_code")
                    .equals(orderPayStatement.getPlatformCode()) ||
                    ConfigurationUtil.getString("wx_pay_code")
                            .equals(orderPayStatement.getPlatformCode()) ||
                    ConfigurationUtil.getString("kmt_pay_code")
                            .equals(orderPayStatement.getPlatformCode())) {
                // 财付通、微信退款
                RefundResult rr = null;
                boolean refundCk = false;   //判断是否退款成功
                for (int req = 1; req <= 3 && (!refundCk); req++) {

                    try {
                        refundParamInfo.setP1_MerId(orderPayStatement.getPlatformCode());
                        if (ConfigurationUtil.getString("ten_pay_code")
                                .equals(orderPayStatement.getPlatformCode())) {
                            rr = tenPayRefund(refundParamInfo, orderPayStatement);
                        } else if (ConfigurationUtil.getString("wx_pay_code")
                                .equals(orderPayStatement.getPlatformCode())) {
                            rr = wxPayRefund(refundParamInfo, orderPayStatement);
                        } else if (ConfigurationUtil.getString("kmt_pay_code")
                                .equals(orderPayStatement.getPlatformCode())) {
                            rr = KMTPayRefund(refundParamInfo, orderPayStatement);
                        }
                        if (null != rr && ("SUCCESS".equals(rr.getR1_Code()) ||
                                "0".equals(rr.getR1_Code()))) {
                            returnResult = new ReturnResult<Object>(RefundResultCode.SUCCESS,
                                    "退款成功。", rr.getR2_TrxId());
                            log.info("订单号：" + orderCode + "退款轮询，于第" + req + "次退款成功,并退出轮询");
                            refundCk = true;
                            // return returnResult;
                        } else {

                            log.error("订单号：" + orderCode + "，退款轮询，第" + req + "次退款失败。refundResult:" +
                                    rr);
                            returnResult = new ReturnResult<>(RefundResultCode.FAILED,
                                    "退款失败，请联系客服支持。", null);
                            // return returnResult;
                        }
                    } catch (ServiceException e) {
                        log.error("订单号：" + orderCode + "，退款轮询，第" + req + "次退款失败。", e);
                        log.error("订单号：" + orderCode + "退款失败。refundResult:" + rr);
                        returnResult = new ReturnResult<>(RefundResultCode.FAILED, "退款失败，请联系客服支持。",
                                null);
                        // return returnResult;
                    }


                }
                return returnResult;
            }         }
        return null;

    }
}
