package com.kmzyc.b2b.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alipay.util.httpClient.HttpProtocolHandler;
import com.alipay.util.httpClient.HttpRequest;
import com.alipay.util.httpClient.HttpResponse;
import com.alipay.util.httpClient.HttpResultType;
import com.kmzyc.b2b.dao.OrderPayStatementDAO;
import com.kmzyc.b2b.model.QueryResult;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.QryOrderOnLineService;
import com.kmzyc.b2b.util.HttpUtils;
import com.kmzyc.b2b.util.XMLHelper;
import com.kmzyc.b2b.util.httpClient.WebUtils;
import com.kmzyc.b2b.util.pay.util.DigestUtil;
import com.kmzyc.b2b.util.pay.util.PaymentUtil;
import com.kmzyc.b2b.util.wxpay.CommonUtil;
import com.kmzyc.order.remote.OrderQryRemoteService;
import com.kmzyc.util.StringUtil;
// import com.km.framework.common.util.RemoteTool;
import com.kmzyc.zkconfig.ConfigurationUtil;

import net.sf.json.JSONObject;

@Service("qryOrderOnLineService")
@SuppressWarnings({"unchecked", "unused", "BigDecimalMethodWithoutRoundingCalled"})
public class QryOrderOnLineServiceImpl implements QryOrderOnLineService {
    // private static Logger log = LoggerFactory.getLogger(QryOrderOnLineServiceImpl.class);
    private static Logger log = LoggerFactory.getLogger(QryOrderOnLineServiceImpl.class);

    // private static String p1_MerId = ConfigurationUtil.getString("p1_MerId"); // 商家ID
    // private static String queryReqURL = ConfigurationUtil.getString("queryReqURL"); // 请求地址
    // private static String keyValue = ConfigurationUtil.getString("keyValue"); // 商家密钥
    private static String query_Cmd = "QueryOrdDetail"; // 订单查询请求，固定值”

    @Resource(name = "orderPayStatementDAOImpl")
    private OrderPayStatementDAO orderPayStatementDAO;
    @Resource(name = "loginServiceImp")
    private LoginService loginService;
    @Resource
    private OrderQryRemoteService orderQryRemoteService;
    /**
     * 支付宝提供给商户的服务接入网关URL(查询用)
     */
    private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";

    // private static final String TEN_PAY_ORDER_QUERY_URL = ConfigurationUtil
    // .getString("ten_pay_order_query_url");
    // private static final String TEN_PAY_REFUND_QUERY_URL = ConfigurationUtil
    // .getString("ten_pay_refund_query_url");

    // private static final String WX_PAY_ORDER_QUERY_URL = ConfigurationUtil
    // .getString("wx_pay_order_query_url");
    // private static final String KMT_TRADE_QUERY_URL = ConfigurationUtil
    // .getString("kmt_trade_query_url");

    /**
     * 微信退款查询
     */
    private static final String WX_PAY_REFUND_QUERY_URL = ConfigurationUtil
            .getString("wx_pay_refund_query_url");

    private static final int SIZE = 1024 * 1024;
    private static String encodingCharset = "UTF-8";

    /**
     * 订单查询请求参数 易宝/支付宝
     *
     * @param orderCode 订单号
     * @return queryResult
     */
    @Override
    public QueryResult queryByOrder(String orderCode) {
        QueryResult qr = null;
        try {

            String platCode = orderPayStatementDAO.queryReadyPayPlatCode(orderCode);
            if (ConfigurationUtil.getString("ali_pay_code").equals(platCode)) {
                qr = alisingleTradeQuery(orderCode);
            } else if (ConfigurationUtil.getString("ten_pay_code").equals(platCode)) {
                qr = tenPaySingleTradeQuery(orderCode);
            } else if (ConfigurationUtil.getString("wx_pay_code").equals(platCode)) {
                qr = wxPaySingleTradeQuery(orderCode);
            } else if (ConfigurationUtil.getString("sd_pay_code").equals(platCode)) {
                //qr = sdPaySingleTradeQuery(orderCode);
            } else if (ConfigurationUtil.getString("kmt_pay_code").equals(platCode)) {
                qr = KMTPaySingleTradeQuery(orderCode);
            } else if (ConfigurationUtil.getString("yee_pay_code").equals(platCode) ||
                    ConfigurationUtil.getString("yee_pay_code").equals(platCode)) {
                qr = yeePaySingleTradeQuery(orderCode);
            }
        } catch (Exception e) {
            log.error("查询单笔订单支付情况发生异常" + orderCode, e);
        }
        return qr;
    }

    /**
     * 易宝查询
     */
    private QueryResult yeePaySingleTradeQuery(String orderCode) throws Exception {
        QueryResult qr;
        String hmac = DigestUtil.getHmac(
                new String[]{query_Cmd, ConfigurationUtil.getString("p1_MerId"), orderCode},
                ConfigurationUtil.getString("keyValue"));
        Map reParams = new HashMap();
        reParams.put("p0_Cmd", query_Cmd);
        reParams.put("p1_MerId", ConfigurationUtil.getString("p1_MerId"));
        reParams.put("p2_Order", orderCode);
        reParams.put("hmac", hmac);
        List responseStr;
        try {
            log.info("Begin http communications.data[" + reParams + "]");
            responseStr = HttpUtils.URLGet(ConfigurationUtil.getString("queryReqURL"), reParams);
            log.info("End http communications.responseStr.data[" + responseStr + "]");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        if (responseStr.size() == 0) {
            throw new RuntimeException("No response.");
        }

        qr = new QueryResult();
        for (Object aResponseStr : responseStr) {
            String currentResult = (String) aResponseStr;
            if (currentResult == null || currentResult.equals("")) {
                continue;
            }
            int i = currentResult.indexOf("=");
            if (i >= 0) {
                String sKey = currentResult.substring(0, i);
                String sValue = currentResult.substring(i + 1);
                try {
                    String decodeCharset = "GBK";
                    sValue = URLDecoder.decode(sValue, decodeCharset);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e.getMessage());
                }
                switch (sKey) {
                    case "r0_Cmd":
                        qr.setR0_Cmd(sValue);
                        break;
                    case "r1_Code":
                        qr.setR1_Code(sValue);
                        break;
                    case "r2_TrxId":
                        qr.setR2_TrxId(sValue);
                        break;
                    case "r3_Amt":
                        qr.setR3_Amt(sValue);
                        break;
                    case "r4_Cur":
                        qr.setR4_Cur(sValue);
                        break;
                    case "r5_Pid":
                        qr.setR5_Pid(sValue);
                        break;
                    case "r6_Order":
                        qr.setR6_Order(sValue);
                        break;
                    case "r8_MP":
                        qr.setR8_MP(sValue);
                        break;
                    case "rb_PayStatus":
                        qr.setRb_PayStatus(sValue);
                        break;
                    case "rc_RefundCount":
                        qr.setRc_RefundCount(sValue);
                        break;
                    case "rd_RefundAmt":
                        qr.setRd_RefundAmt(sValue);
                        break;
                    case "hmac":
                        qr.setHmac(sValue);
                        break;
                }
            }
        }
        if (!"1".equals(qr.getR1_Code())) {
            throw new RuntimeException("Query fail.Error code:" + qr.getR1_Code());
        }
        String newHmac = DigestUtil.getHmac(
                new String[]{qr.getR0_Cmd(), qr.getR1_Code(), qr.getR2_TrxId(), qr.getR3_Amt(), qr.getR4_Cur(), qr.getR5_Pid(), qr.getR6_Order(), qr.getR8_MP(), qr.getRb_PayStatus(), qr.getRc_RefundCount(), qr.getRd_RefundAmt()},
                ConfigurationUtil.getString("keyValue"));
        if (!newHmac.equals(qr.getHmac())) {
            throw new RuntimeException("Hmac error.");
        }
        qr.setR8_MP("2");

        return qr;
    }

    /**
     * 支付宝单笔查询
     */
    private QueryResult alisingleTradeQuery(String orderCode) throws Exception {
        QueryResult qr = null;
        String charset = ConfigurationUtil.getString("ali_input_charset");
        Map<String, String> sParaTemp = new HashMap<>();
        sParaTemp.put("service", "single_trade_query");// 接口名
        sParaTemp.put("partner", ConfigurationUtil.getString("ali_pay_id"));// 商户ID
        sParaTemp.put("_input_charset", charset);// 编码
        sParaTemp.put("out_trade_no", orderCode);// 订单号

        Map<String, String> sPara = PaymentUtil
                .buildRequestPara(sParaTemp, ConfigurationUtil.getString("ali_sign_type"),
                        ConfigurationUtil.getString("ali_private_key"));
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        request.setCharset(charset);
        request.setParameters(PaymentUtil.generatNameValuePair(sPara));
        request.setUrl(ALIPAY_GATEWAY_NEW + "_input_charset=" + charset);
        HttpResponse response = httpProtocolHandler.execute(request);
        if (response == null) {
            return null;
        }
        try {
            log.info("alisingleTradeQuery.data[" + sParaTemp + "]");
            String rs = response.getStringResult();
            log.info("alisingleTradeQuery.responseStr.data[" + rs + "]");
            Document doc = DocumentHelper.parseText(rs);
            Element alipay;
            if (null == doc || null == (alipay = doc.getRootElement()) ||
                    !"T".equals(alipay.elementTextTrim("is_success"))) {
                return null;
            }
            Iterator respIt = alipay.elementIterator("response");
            if (null == respIt) {
                return null;
            }
            Element resp = null;
            while (respIt.hasNext()) {
                resp = (Element) respIt.next();
                break;
            }
            if (null == resp) {
                return null;
            }
            Iterator tradeIt = resp.elementIterator("trade");
            if (null == tradeIt) {
                return null;
            }
            Element trade = null;
            while (tradeIt.hasNext()) {
                trade = (Element) tradeIt.next();
                break;
            }
            if (null != trade) {
                String status = trade.elementTextTrim("trade_status");
                status = ("WAIT_SELLER_SEND_GOODS".equals(status) ||
                        "TRADE_SUCCESS".equals(status) || "TRADE_FINISHED".equals(status)) ?
                        "SUCCESS" : "FAIL";
                qr = new QueryResult();
                qr.setR8_MP("3");
                qr.setR0_Cmd(query_Cmd);
                qr.setR1_Code("200");
                qr.setR4_Cur("CNY");
                qr.setR2_TrxId(trade.elementTextTrim("trade_no"));
                qr.setR3_Amt(trade.elementTextTrim("total_fee"));
                qr.setR5_Pid(trade.elementTextTrim("body"));
                qr.setR6_Order(trade.elementTextTrim("out_trade_no"));
                qr.setRb_PayStatus(status);
                qr.setRc_RefundCount(
                        StringUtil.isEmpty(trade.elementTextTrim("refund_id")) ? "0" : "1");
                qr.setRd_RefundAmt(trade.elementTextTrim("refund_fee"));
            }
        } catch (Exception e) {
            log.error("查询单笔订单支付情况发生异常" + orderCode, e);
        }
        return qr;
    }

    /**
     * 财付通单笔查询
     */
    private QueryResult tenPaySingleTradeQuery(String orderCode) throws Exception {
        QueryResult qr = null;
        String charset = ConfigurationUtil.getString("ten_pay_input_charset");
        SortedMap<String, String> reParams = new TreeMap<>();
        reParams.put("sign_type", ConfigurationUtil.getString("ten_pay_sign_type"));// 签名方式
        reParams.put("input_charset", charset);// 字符集
        reParams.put("sign_key_index",
                ConfigurationUtil.getString("ten_pay_sign_key_index"));// 密钥序号
        reParams.put("service_version",
                ConfigurationUtil.getString("ten_pay_notify_service_query_version"));// 接口版本
        reParams.put("partner", ConfigurationUtil.getString("ten_pay_partner"));// 商户号
        reParams.put("out_trade_no", orderCode);// 商户订单号

        List<String> responseInfo = HttpUtils.URLGet(PaymentUtil
                .getTenPayRequestURL(ConfigurationUtil.getString("ten_pay_order_query_url"),
                        reParams), charset); // 请求地址
        if (null != responseInfo && !responseInfo.isEmpty()) {
            Map<String, String> rsMap = XMLHelper.singleLevelAnalyze(responseInfo);
            if (null != rsMap && "0".equals(rsMap.get("retcode")) && PaymentUtil
                    .isTenpaySign(rsMap, ConfigurationUtil.getString("ten_pay_private_key"))) {
                qr = new QueryResult();
                qr.setR8_MP("4");
                qr.setR0_Cmd(query_Cmd);
                qr.setR1_Code("200");
                qr.setR4_Cur("CNY");
                qr.setR2_TrxId(rsMap.get("transaction_id"));
                qr.setR3_Amt(new BigDecimal(rsMap.get("total_fee")).divide(BigDecimal.TEN)
                        .divide(BigDecimal.TEN).toString());
                qr.setR6_Order(rsMap.get("out_trade_no"));
                qr.setRb_PayStatus("0".equals(rsMap.get("trade_state")) ? "SUCCESS" : "FAIL");
                String refundCount = "true".equals(rsMap.get("is_refund")) ? "1" : "0";
                BigDecimal refundedMoney = BigDecimal.ZERO;// 退款金额
                if ("1".equals(refundCount)) {
                    reParams.clear();
                    reParams.put("sign_type",
                            ConfigurationUtil.getString("ten_pay_sign_type"));// 签名方式
                    reParams.put("input_charset", charset);// 字符集
                    reParams.put("sign_key_index",
                            ConfigurationUtil.getString("ten_pay_sign_key_index"));// 密钥序号
                    reParams.put("partner", ConfigurationUtil.getString("ten_pay_partner"));// 商户号
                    reParams.put("out_trade_no", orderCode);// 商户订单号
                    responseInfo = HttpUtils.URLGet(PaymentUtil.getTenPayRequestURL(
                            ConfigurationUtil.getString("ten_pay_refund_query_url"), reParams),
                            charset); // 请求地址
                    if (null != responseInfo && !responseInfo.isEmpty()) {
                        rsMap = XMLHelper.singleLevelAnalyze(responseInfo);
                        if (null != rsMap && "0".equals(rsMap.get("retcode")) && PaymentUtil
                                .isTenpaySign(rsMap,
                                        ConfigurationUtil.getString("ten_pay_private_key"))) {
                            refundCount = rsMap.get("refund_count");
                            for (Map.Entry<String, String> entry : rsMap.entrySet()) {
                                if (entry.getKey().startsWith("refund_fee_")) {
                                    refundedMoney = refundedMoney
                                            .add(new BigDecimal(entry.getValue()));
                                }
                            }
                        }
                    }
                }
                qr.setRc_RefundCount(refundCount);
                qr.setRd_RefundAmt(
                        refundedMoney.divide(BigDecimal.TEN).divide(BigDecimal.TEN).toString());
            }
            if (null != rsMap) {
                log.error(rsMap.get("retmsg"));
            }
        }
        return qr;
    }

    /**
     * 微信单笔查询
     */
    private QueryResult wxPaySingleTradeQuery(String orderCode) throws Exception {
        QueryResult qr = null;
        try {
            String charset = ConfigurationUtil.getString("wx_pay_input_charset");// 字符集
            SortedMap<String, String> reParams = new TreeMap<>();
            reParams.put("appid", ConfigurationUtil.getString("wx_pay_appid"));// 公众账号
            // ID
            reParams.put("mch_id", ConfigurationUtil.getString("wx_pay_partner"));// 商户号
            reParams.put("out_trade_no", orderCode);// 商户订单号
            reParams.put("nonce_str", CommonUtil.CreateNoncestr());// 随机字符串

            List<String> responseInfo = HttpUtils
                    .URLPost(ConfigurationUtil.getString("wx_pay_order_query_url"),
                            PaymentUtil.getWXPayMap(reParams), charset); // 请求地址
            if (null != responseInfo && !responseInfo.isEmpty()) {
                Map<String, String> rsMap = XMLHelper.singleLevelAnalyze(responseInfo);
                if (null != rsMap && "SUCCESS".equals(rsMap.get("return_code")) &&
                        "SUCCESS".equals(rsMap.get("result_code")) && PaymentUtil
                        .isTenpaySign(rsMap, ConfigurationUtil.getString("wx_pay_private_key"))) {
                    qr = new QueryResult();
                    qr.setR8_MP("5");
                    qr.setR0_Cmd(query_Cmd);
                    qr.setR1_Code("200");
                    qr.setR4_Cur("CNY");
                    qr.setR2_TrxId(rsMap.get("transaction_id"));
                    qr.setR3_Amt(new BigDecimal(rsMap.get("total_fee")).divide(BigDecimal.TEN)
                            .divide(BigDecimal.TEN).toString());
                    qr.setR6_Order(rsMap.get("out_trade_no"));
                    qr.setRb_PayStatus(
                            "SUCCESS".equals(rsMap.get("trade_state")) ? "SUCCESS" : "FAIL");

                    BigDecimal refundedMoney = BigDecimal.ZERO;// 退款金额
                    String refundCount = null; // 退款笔数
                    if ("SUCCESS".equals(rsMap.get("result_code"))) {
                        reParams.clear();
                        reParams.put("appid", ConfigurationUtil.getString("wx_pay_appid"));// 公众账号
                        // ID
                        reParams.put("mch_id", ConfigurationUtil.getString("wx_pay_partner"));// 商户号
                        reParams.put("nonce_str", CommonUtil.CreateNoncestr());// 随机字符串
                        reParams.put("transaction_id", rsMap.get("transaction_id"));// 微信订单号
                        reParams.put("out_trade_no", orderCode);// 商户订单号

                        responseInfo = HttpUtils
                                .URLPost(WX_PAY_REFUND_QUERY_URL, PaymentUtil.getWXPayMap(reParams),
                                        charset); // 请求地址

                        if (null != responseInfo && !responseInfo.isEmpty()) {
                            rsMap = XMLHelper.singleLevelAnalyze(responseInfo);
                            if (null != rsMap && "SUCCESS".equals(rsMap.get("return_code")) &&
                                    "SUCCESS".equals(rsMap.get("result_code")) && PaymentUtil
                                    .isTenpaySign(rsMap,
                                            ConfigurationUtil.getString("wx_pay_private_key"))) {
                                refundCount = rsMap.get("refund_count");
                                for (Map.Entry<String, String> entry : rsMap.entrySet()) {
                                    if (entry.getKey().startsWith("refund_fee_")) {
                                        refundedMoney = refundedMoney
                                                .add(new BigDecimal(entry.getValue()));
                                    }
                                }
                            }
                        }
                    }
                    qr.setRc_RefundCount(refundCount);
                    qr.setRd_RefundAmt(
                            refundedMoney.divide(BigDecimal.TEN).divide(BigDecimal.TEN).toString());
                }
                if (null != rsMap) {
                    log.error(rsMap.get("retmsg"));
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return qr;
    }

    /**
     * 时代单笔查询
     *//*
    private QueryResult sdPaySingleTradeQuery(String orderCode) throws Exception {
        // OrderQryRemoteService orderQuery =
        // (OrderQryRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_ORDER,
        // "orderQryService ");
        OrderMain om = orderQryRemoteService.getOrderByCode(orderCode);
        if (null == om) {
            throw new Exception("订单" + orderCode + "不存在！");
        }
        QueryResult qr = null;
        IMemberInfo imi = loginService.findWebservice();
        String result = imi.TLPaymentQuery(om.getOrderCode(),
                StringUtil.getSimpleJoin(om.getCreateDate()));
        JSONObject json = JSONObject.fromObject(result);
        String strError = json.getString("strError");
        if (!StringUtil.isEmpty(strError)) {
            throw new Exception("时代订单" + orderCode + "单笔查询失败！原因：" + strError);
        }
        String outTradeNo = json.getString("outTradeNo");// 订单编号
        String totalFee = json.getString("totalFee");// 支付金额
        String notifyTime = json.getString("notifyTime");// 通知时间
        String tradeNo = json.getString("tradeNo");// 交易流水号
        String isSdPay = json.getString("isSdPay");// 时代支付标识
        String payResult = json.getString("payResult");// 支付状态
        String signMsg = json.getString("signMsg");// 加密串
        StringBuilder sb = new StringBuilder();
        sb.append("outTradeNo=").append(outTradeNo).append('&').append("totalFee=")
                .append(totalFee).append('&').append("notifyTime=").append(notifyTime)
                .append('&').append("tradeNo=").append(tradeNo).append('&').append("isSdPay=")
                .append(isSdPay).append('&').append("payResult=").append(payResult).append('&')
                .append("key=").append(ConfigurationUtil.getString("sd_pay_private_key"));
        if (PaymentUtil.isSdPaySign(sb.toString(), signMsg)) {
            qr = new QueryResult();
            qr.setR8_MP("6");
            qr.setR0_Cmd(query_Cmd);
            qr.setR1_Code("200");
            qr.setR4_Cur("CNY");
            qr.setR2_TrxId(tradeNo);
            qr.setR3_Amt(new BigDecimal(totalFee).divide(BigDecimal.TEN).divide(BigDecimal.TEN)
                    .toString());
            qr.setR6_Order(outTradeNo);
            qr.setRb_PayStatus("1".equals(payResult) ? "SUCCESS" : "FAIL");
            qr.setRc_RefundCount("0");
            qr.setRd_RefundAmt("0");
        }
        return qr;
    }*/

    /**
     * 康美通单笔查询
     */
    private QueryResult KMTPaySingleTradeQuery(String orderCode) throws Exception {
        QueryResult qr = null;
        SortedMap<String, String> reParams;
        reParams = new TreeMap<>();
        reParams.put("method", "kmpay.order.get");
        reParams.put("v", "2.0");
        reParams.put("format", "json");
        reParams.put("partner", ConfigurationUtil.getString("kmt_pay_partner"));
        reParams.put("inputCharset", ConfigurationUtil.getString("kmt_input_charset"));
        reParams.put("clientIp", ConfigurationUtil.getString("kmt_request_ip"));
        reParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        reParams.put("outTradeNo", orderCode);
        try {
            String responseStr = WebUtils.doPost(ConfigurationUtil.getString("kmt_refund_req_url"),
                    PaymentUtil.buildRequestParaForKMT(reParams,
                            ConfigurationUtil.getString("kmt_sign_type")), 20000, 20000, true);
            JSONObject result;
            if (null != responseStr && !responseStr.isEmpty() &&
                    null != (result = JSONObject.fromObject(responseStr)) &&
                    result.containsKey("data")) {
                Map<String, String> resMap = new HashMap<>();
                String value;
                JSONObject json = result.getJSONObject("data");
                log.info("康美通订单支付查询回调数据：{}",json);
                for (Object key : json.keySet()) {
                    value = json.getString(key.toString());
                    if (null != value && value.length() > 0 && !"null".equals(value)) {
                        resMap.put(key.toString(), value);
                    }
                }
                if (PaymentUtil.verifyIsKMTPay(resMap)) {
                    qr = new QueryResult();
                    qr.setR8_MP("7");
                    qr.setR0_Cmd(query_Cmd);
                    qr.setR1_Code("200");
                    qr.setR4_Cur("CNY");
                    qr.setR2_TrxId(resMap.get("tradeNo"));
                    qr.setR3_Amt(new BigDecimal(resMap.get("totalAmount")).divide(BigDecimal.TEN)
                            .divide(BigDecimal.TEN).toString());
                    qr.setR6_Order(resMap.get("outTradeNo"));
                    qr.setRb_PayStatus(
                            "SUCCESS".equals(resMap.get("orderStatus")) ? "SUCCESS" : "FAIL");
                    qr.setRc_RefundCount(resMap.containsKey("refund_status") ? "0" : "1");
                    qr.setRd_RefundAmt("0");
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return qr;
    }

}
