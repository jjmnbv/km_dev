package com.kmzyc.b2b.util.pay.plugin;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.kmzyc.b2b.model.PayCommonObject;
import com.kmzyc.b2b.model.RefundParamInfo;
import com.kmzyc.b2b.model.RefundResult;
import com.kmzyc.b2b.util.pay.BasePayPlugIn;
import com.kmzyc.b2b.util.pay.util.PaymentUtil;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 支付宝wap支付
 * 
 * @author xlg
 * 
 */
@SuppressWarnings({"unchecked", "unused"})
public class AliWapPayPlugIn extends BasePayPlugIn {
    /**
     * 时间Long格式化
     */
    private static final DateTimeFormatter sdfLong = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 支付宝请求网关
     */
    // private static final String ConfigurationUtil.getString("ali_wap_request_url_prex") =
    // ConfigurationUtil
    // .getString("ali_wap_request_url_prex");

    /**
     * 请求
     */
    @Override
    public String onPay() throws Exception {
        PayCommonObject order = this.getOrder();
        Map<String, String> requestMap = null;
        if (!StringUtil.isEmpty(order.getOrderCode())) {
            requestMap = genAliWapPayMap(order);
        }
        return PaymentUtil.buildAliPayRequestForWap(requestMap,
                ConfigurationUtil.getString("ali_wap_request_url_prex"), "get", "确定");
    }

    /**
     * 回调
     */
    @Override
    public Object onReturn() throws Exception {
        HttpServletRequest callBackRequest = this.getCallBackRequest();
        String tradeNo = callBackRequest.getParameter("trade_no");
        String secId = callBackRequest.getParameter("sec_id");
        if (!StringUtil.isEmpty(tradeNo) || !StringUtil.isEmpty(secId)) {
            // 支付
            return genAliWapPayReturnObj(callBackRequest);
        } else {
            // 退款
            return null;
        }
    }

    /**
     * 授权Map
     * 
     * @param order
     * @return
     * @throws Exception
     */
    private static Map<String, String> genAliWapPayMap(PayCommonObject order) throws Exception {
        String paySource = order.getPaySource();
        // 授权
        String partner = ConfigurationUtil.getString("ali_pay_id");
        String encoding = ConfigurationUtil.getString("ali_input_charset");
        String signType = ConfigurationUtil.getString("ali_wap_sign_type");
        String format = ConfigurationUtil.getString("ali_wap_format");
        String version = ConfigurationUtil.getString("ali_wap_version");
        String notifyUrl = ConfigurationUtil.getString("ali_merchant_pay_callback_url");
        String returnUrl = ConfigurationUtil.getString("ali_merchant_pay_callback_url");
        if (null != paySource && "YS".equals(paySource.trim())) {// 设置预售订单回调方法
            notifyUrl = ConfigurationUtil.getString("ali_merchant_pay_callback_url_ys");
            returnUrl = ConfigurationUtil.getString("ali_merchant_pay_callback_url_ys");
        }
        String sellerAcount = ConfigurationUtil.getString("ali_login_count");
        String subject = ConfigurationUtil.getString("ali_order_name");
        String merchantUrl = ConfigurationUtil.getString("staticPath_WAP");
        String req_dataToken =
                "<direct_trade_create_req><notify_url>" + notifyUrl
                        + "</notify_url><call_back_url>" + returnUrl
                        + "</call_back_url><seller_account_name>" + sellerAcount
                        + "</seller_account_name><out_trade_no>" + order.getOrderCode()
                        + "</out_trade_no><subject>" + subject + "</subject><total_fee>"
                        + order.getMoneyStr() + "</total_fee><merchant_url>" + merchantUrl
                        + "</merchant_url></direct_trade_create_req>";
        Map<String, String> params = new HashMap<String, String>();
        params.put("service", "alipay.wap.trade.create.direct");
        params.put("partner", partner);
        params.put("_input_charset", encoding);
        params.put("sec_id", signType);
        params.put("format", format);
        params.put("v", version);
        params.put("req_id", genDateLong());
        params.put("req_data", req_dataToken);
        String token =
                PaymentUtil.getToken(ConfigurationUtil.getString("ali_wap_request_url_prex"),
                        params);

        if (null != token) {
            // 支付
            params.clear();
            String req_data =
                    "<auth_and_execute_req><request_token>" + token
                            + "</request_token></auth_and_execute_req>";
            params.put("service", "alipay.wap.auth.authAndExecute");
            params.put("partner", partner);
            params.put("_input_charset", encoding);
            params.put("sec_id", signType);
            params.put("format", format);
            params.put("v", version);
            params.put("req_data", req_data);
            return params;
        }
        return null;
    }

    /**
     * 生成时间Long串，追加6个随机数
     * 
     * @return
     */
    private static String genDateLong() {
        return sdfLong.format(new Date().toInstant()) + new Random().nextInt(1000000);
    }

    /**
     * 生成支付回调对象
     * 
     * @param callBackRequest
     * @return
     */
    private static PayCommonObject genAliWapPayReturnObj(HttpServletRequest callBackRequest)
            throws Exception {
        PayCommonObject obj = null;
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = callBackRequest.getParameterMap();
        for (Map.Entry<String, String[]> entry:requestParams.entrySet()) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            String valueStr = StringUtils.join(values,",");
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
            params.put(name, valueStr);
        }
        String secId = params.get("sec_id");
        String tradeStatus;
        Document docNotifyData = null;
        if (!StringUtil.isEmpty(secId)) {
            if ("0001".equals(ConfigurationUtil.getString("ali_wap_sign_type"))) {
                params = PaymentUtil.decryptForWap(params);
            }
            docNotifyData = DocumentHelper.parseText(params.get("notify_data"));
            tradeStatus = docNotifyData.selectSingleNode("//notify/trade_status").getText();
        } else {
            tradeStatus = params.get("result");
        }
        if (("success".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED"
                .equals(tradeStatus)) && PaymentUtil.verifyIsAliPayForWap(params)) {
            obj = new PayCommonObject();
            if (!StringUtil.isEmpty(secId)) {
                String outTradeNo =
                        docNotifyData.selectSingleNode("//notify/out_trade_no").getText();
                String totalFee = docNotifyData.selectSingleNode("//notify/total_fee").getText();
                String notifyTime =
                        docNotifyData.selectSingleNode("//notify/notify_time").getText();
                String tradeno = docNotifyData.selectSingleNode("//notify/trade_no").getText();
                obj.setOrderCode(outTradeNo);
                obj.setMoneyStr(totalFee);
                obj.setPayDateStr(notifyTime);
                obj.setTransitionNO(tradeno);
                obj.setTrxTime(notifyTime);
                obj.setSynchCall(true);
            } else {
                obj.setOrderCode(params.get("out_trade_no"));
                obj.setMoneyStr(params.get("total_fee"));
                obj.setPayDateStr(params.get("notify_time"));
                obj.setTransitionNO(params.get("trade_no"));
                obj.setTrxTime(params.get("notify_time"));
            }
            obj.setCurrencyCode("CNY");
            obj.setExtInfo("1");// wap标识
            obj.setPayStateCode("1");
            obj.setBankId("");
            obj.setBankOrderId("");
        }
        return obj;
    }

    /**
     * 生成退款Map 仅支持单笔
     * 
     * @param refundInfo
     */
    private static Map<String, String> genAliRefundMap(RefundParamInfo refundInfo) {
        // @TODO 暂不实现
        return null;
    }

    /**
     * 生成退款回调对象
     * 
     * @param callBackRequest
     * @return
     */
    private static RefundResult genAliRefundReturnObj(HttpServletRequest callBackRequest)
            throws Exception {
        // @TODO 暂不实现
        return null;
    }
}
