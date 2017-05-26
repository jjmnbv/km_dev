package com.kmzyc.b2b.util.pay.plugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.kmzyc.b2b.model.PayCommonObject;
import com.kmzyc.b2b.util.HttpUtils;
import com.kmzyc.b2b.util.XMLHelper;
import com.kmzyc.b2b.util.pay.BasePayPlugIn;
import com.kmzyc.b2b.util.pay.util.PaymentUtil;
import com.kmzyc.b2b.util.wxpay.CommonUtil;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

import net.sf.json.JSONObject;

/**
 * 微信
 * 
 * @author cjm
 * @since 2015-1-21
 */
@SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
public class WxPayPlugIn extends BasePayPlugIn {
    private static final int SIZE = 1024 * 1024;

    /**
     * 请求
     */
    @Override
    public String onPay() throws Exception {
        PayCommonObject order = this.getOrder();
        if (null != order && !StringUtil.isEmpty(order.getOrderCode())) {
            return getWxPayJson(order, this.getCallBackRequest());
        }
        return null;
    }

    /**
     * 回调
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object onReturn() throws Exception {
        HttpServletRequest callBackRequest = this.getCallBackRequest();

        List wxResult = new ArrayList();
        Map<String, String> rsMap;
        BufferedReader bin =
                new BufferedReader(new InputStreamReader(callBackRequest.getInputStream()), SIZE);
        String str;
        while (null != (str = bin.readLine())) {
            wxResult.add(str);
        }

        if (!wxResult.isEmpty()) {
            rsMap = XMLHelper.singleLevelAnalyze(wxResult);
            if (!StringUtil.isEmpty(rsMap.get("transaction_id"))) {
                // 支付
                return genWxPayReturnObj(rsMap);
            } else if (!StringUtil.isEmpty(rsMap.get("refund_status"))) {
                // 退款
                return null;
            }
        }

        return null;
    }

    /**
     * 请求支付并拼接Json
     */
    @SuppressWarnings("unchecked")
    private String getWxPayJson(PayCommonObject order, HttpServletRequest callBackRequest)
            throws Exception {
        String paySource = order.getPaySource();
        String extInfo = order.getExtInfo();
        extInfo = null == extInfo ? "" : extInfo.replaceAll("\\|", "^");
        SortedMap<String, String> reParams = new TreeMap<>();
        String charset = ConfigurationUtil.getString("wx_pay_input_charset");// 字符集
        reParams.put("appid", ConfigurationUtil.getString("wx_pay_appid"));// 公众账号
        // ID
        reParams.put("mch_id", ConfigurationUtil.getString("wx_pay_partner"));// 商户号
        reParams.put("nonce_str", CommonUtil.CreateNoncestr());// 随机字符串
        reParams.put("body", ConfigurationUtil.getString("wx_order_desc"));// 商品描述
        reParams.put("attach", extInfo);// 附加信息
        reParams.put("out_trade_no", order.getOrderCode());// 商户订单号
        reParams.put("total_fee", new BigDecimal(order.getMoneyStr()).multiply(BigDecimal.TEN)
                .multiply(BigDecimal.TEN).toBigInteger().toString());// 总金额
        reParams.put("spbill_create_ip", "127.0.0.1");// 终端 IP
        String notifyUrl = ConfigurationUtil.getString("wx_pay_callback_url");
        if (null != paySource && "YS".equals(paySource.trim())) {// 设置预售订单回调方法
            notifyUrl = ConfigurationUtil.getString("wx_pay_callback_url_ys");
        }
        reParams.put("notify_url", notifyUrl);// 通知地址
        reParams.put("trade_type", ConfigurationUtil.getString("wx_pay_trade_type"));// 交易类型
        reParams.put("openid", (String) callBackRequest.getAttribute("openid"));// 用户标识

        List<String> responseInfo =
                HttpUtils.URLPost(ConfigurationUtil.getString("wx_pay_order_url"),
                        PaymentUtil.getWXPayMap(reParams), charset); // 请求地址
        if (null != responseInfo && !responseInfo.isEmpty()) {
            Map<String, String> rsMap = XMLHelper.singleLevelAnalyze(responseInfo);
            if (null != rsMap
                    && "SUCCESS".equals(rsMap.get("return_code"))
                    && "SUCCESS".equals(rsMap.get("result_code"))
                    && PaymentUtil.isTenpaySign(rsMap,
                            ConfigurationUtil.getString("wx_pay_private_key"))) {
                SortedMap<String, String> params = new TreeMap<>();
                params.put("appId", ConfigurationUtil.getString("wx_pay_appid"));
                params.put("timeStamp", Long.toString(new Date().getTime()));
                params.put("nonceStr", CommonUtil.CreateNoncestr());
                params.put("package", "prepay_id=" + rsMap.get("prepay_id"));
                params.put("signType", ConfigurationUtil.getString("wx_pay_sign_type"));
                PaymentUtil.signMD5ForTenOrWxPay(params,
                        ConfigurationUtil.getString("wx_pay_private_key"));

                params.put("packageValue", "prepay_id=" + rsMap.get("prepay_id")); // 这里用packageValue是预防package是关键字在js获取值出错
                String userAgent = callBackRequest.getHeader("user-agent");
                char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
                params.put("agent", new String(new char[] {agent}));// 微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。
                params.put("total_fee", new BigDecimal(order.getMoneyStr())
                        .multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).toBigInteger()
                        .toString());
                params.put("orderCode", order.getOrderCode());
                return JSONObject.fromObject(params).toString();
            }
        }
        return null;
    }

    /**
     * 生成支付回调对象
     * 
     * @param rsMap
     */
    private static PayCommonObject genWxPayReturnObj(Map<String, String> rsMap) throws Exception {
        PayCommonObject obj = null;
        if (null != rsMap && "SUCCESS".equals(rsMap.get("return_code"))
                && "SUCCESS".equals(rsMap.get("result_code"))) {
            obj = new PayCommonObject();
            obj.setOrderCode(rsMap.get("out_trade_no"));
            obj.setCurrencyCode("CNY");
            String extInfo = rsMap.get("attach");
            extInfo = null == extInfo ? "" : extInfo.replaceAll("\\^", "|");
            obj.setExtInfo(extInfo);
            obj.setMoneyStr(new BigDecimal(rsMap.get("total_fee")).divide(BigDecimal.TEN)
                    .divide(BigDecimal.TEN).toString());
            obj.setPayDateStr(rsMap.get("time_end"));
            obj.setTransitionNO(rsMap.get("transaction_id"));
            obj.setTrxTime(rsMap.get("time_end"));
            obj.setPayStateCode("1");
            obj.setBankId(rsMap.get("bank_type"));
            obj.setBankOrderId("");
        }
        return obj;
    }
}
