package com.kmzyc.b2b.util.pay.plugin;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.b2b.model.PayCommonObject;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.util.pay.BasePayPlugIn;
import com.kmzyc.b2b.util.pay.util.PaymentUtil;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 康美通支付
 * 
 * @author xlg
 * 
 */
@SuppressWarnings({"unchecked", "unused", "BigDecimalMethodWithoutRoundingCalled"})
public class KMTWapPayPlugIn extends BasePayPlugIn {
    private static Logger log = LoggerFactory.getLogger(KMTWapPayPlugIn.class);
    // 支付
    // private static final String payUrl = ConfigurationUtil.getString("kmt_pay_url_wap");
    // 退款
    // private static final String refundUrl =
    // ConfigurationUtil.getString("kmt_refund_req_url_wap");

    /**
     * 请求
     */
    @Override
    public String onPay() throws Exception {
        PayCommonObject order = this.getOrder();
        if (null != order && !StringUtil.isEmpty(order.getOrderCode())) {
            // 支付
            Map<String, String> requestMap  = genKMTWapPayMap(order, this.getCallBackRequest());
            return PaymentUtil.buildKMTPayRequest(requestMap,
                    ConfigurationUtil.getString("kmt_pay_url_wap"), "get", "确定");
        }
        return null;
    }

    /**
     * 回调
     */
    @Override
    public Object onReturn() throws Exception {
        HttpServletRequest callBackRequest = this.getCallBackRequest();
        String tradeNo = callBackRequest.getParameter("outTradeNo");
        if (!StringUtil.isEmpty(tradeNo)) {
            // 支付
            return genKMTPayReturnObj(callBackRequest);
        }
        return null;
    }

    /**
     * 生成支付Map
     * 
     * @param order
     * @return
     */
    private Map<String, String> genKMTWapPayMap(PayCommonObject order,
            HttpServletRequest callBackRequest) throws Exception {
        String paySource = order.getPaySource();
        SortedMap<String, String> requestMap = new TreeMap<String, String>();
        requestMap.put("partner", ConfigurationUtil.getString("kmt_pay_partner_wap"));// 商户号
        requestMap.put("outTradeNo", order.getOrderCode());// 商家订单号
        requestMap.put("sellerEmail", ConfigurationUtil.getString("kmt_seller_email_wap"));// 卖家账号
        requestMap.put("totalAmount", new BigDecimal(order.getMoneyStr()).multiply(BigDecimal.TEN)
                .multiply(BigDecimal.TEN).toBigInteger().toString());// 商品金额,以分为单位
        String notifyUrl = ConfigurationUtil.getString("kmt_pay_callback_url_wap");
        if (null != paySource && "YS".equals(paySource.trim())) {// 设置预售订单回调方法
            notifyUrl = ConfigurationUtil.getString("kmt_pay_callback_url_ys");
        }
        requestMap.put("notifyUrl", notifyUrl);// 交易完成后跳转的URL
        requestMap.put("returnUrl", ConfigurationUtil.getString("kmt_order_list_url") + "?t="
                + System.currentTimeMillis());// 页面跳转同步通知页面路径
        // 接收财付通通知的URL
        requestMap.put("subject", ConfigurationUtil.getString("kmt_pay_subject"));// 商品名称
        requestMap.put("body", ConfigurationUtil.getString("kmt_pay_body"));// 商品描述
        requestMap.put("clientIp", callBackRequest.getRemoteAddr()); // 请求IP
        requestMap.put("price", requestMap.get("totalAmount")); // 商品单价
        requestMap.put("quantity", "1"); // 商品数量 测试
        // 系统可选参数
        // requestMap.put("requestIp", callBackRequest.getRemoteAddr()); // 用户的公网ip，不是商户服务器IP
        // requestMap.put("signType", ConfigurationUtil.getString("kmt_sign_type"));// 签名类型
        requestMap.put("inputCharset", ConfigurationUtil.getString("ten_pay_input_charset")); // 字符编码
        requestMap.put("paymentType", "1"); // 交易类型，1实物，2服务
        requestMap.put("timestamp", String.valueOf(new Date().getTime())); // 时间戳
        requestMap.put("showUrl", "http://www.kmb2b.com/"); // 商品展示地址

        // 康美通对接额外参数
        User user = this.getUser();
        if (null != user) {

            if (!StringUtil.isEmpty(user.getMobile())) {
                requestMap.put("kmpayAccount", user.getMobile());
            }
            if (!StringUtil.isEmpty(user.getLoginAccount())) {
                requestMap.put("srcAccount", user.getLoginAccount());
            }
            requestMap.put("src", "KMDIRSIR");

        }


        return requestMap;
    }

    /**
     * 生成支付回调对象
     * 
     * @param callBackRequest
     * @return
     */
    private static PayCommonObject genKMTPayReturnObj(HttpServletRequest callBackRequest)
            throws Exception {
        PayCommonObject obj = null;
        String tradeStatus = callBackRequest.getParameter("orderStatus");
        String isSuccess = callBackRequest.getParameter("isSuccess");
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = callBackRequest.getParameterMap();
        for (Map.Entry<String, String[]> entry:requestParams.entrySet()) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            String valueStr = StringUtils.join(values,",");
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), charset);
            params.put(name, valueStr);
        }
        if ("SUCCESS".equals(tradeStatus) && PaymentUtil.verifyIsKMTPay(params)) {
            obj = new PayCommonObject();
            obj.setOrderCode(callBackRequest.getParameter("outTradeNo"));
            obj.setCurrencyCode("CNY");
            obj.setMoneyStr(new BigDecimal(callBackRequest.getParameter("totalAmount"))
                    .divide(BigDecimal.TEN).divide(BigDecimal.TEN).toString());
            obj.setPayDateStr(callBackRequest.getParameter("notifyTime"));
            obj.setTransitionNO(callBackRequest.getParameter("tradeNo"));
            obj.setTrxTime(callBackRequest.getParameter("notifyTime"));
            obj.setPayStateCode("1");
            obj.setBankId("");
            obj.setBankOrderId("");
            obj.setSynchCall("T".equals(isSuccess));
            
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            log.info("订单:"+callBackRequest.getParameter("outTradeNo")+"支付回调(genKMTPayReturnObj)时间："+df.format(new Date()));
        }
        return obj;
    }

}
