package com.kmzyc.b2b.util.pay.plugin;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
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
public class AliAppPayPlugIn extends BasePayPlugIn {
    /**
     * 时间Long格式化
     */
    private static final DateTimeFormatter sdfLong = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    /**
     * 支付宝请求网关
     */
//    private static final String ALI_WAP_REQUEST_URL_PREX = ConfigurationUtil
//            .getString("ali_wap_request_url_prex");

    /**
     * 请求
     */
    @Override
    public String onPay() throws Exception {
        PayCommonObject order = this.getOrder();
        Map<String, String> requestMap = null;
        if (!StringUtil.isEmpty(order.getOrderCode())) {
            requestMap = genAliAppPayMap(order);
        }
        return PaymentUtil.buildAliPayRequestForWap(requestMap, ConfigurationUtil.getString("ali_wap_request_url_prex"), "get",
                "确定");
    }

    /**
     * app请求参数拼接
     */
    public static String genAliAppPayString(PayCommonObject order) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        String partner = ConfigurationUtil.getString("ali_pay_id");
        String subject = ConfigurationUtil.getString("ali_order_name");
        String notifyUrl = ConfigurationUtil.getString("ali_merchant_pay_callback_url");
        String SEMICOLON = "\"";

        // 签约合作者身份ID
        stringBuilder.append("partner=\"" + partner + SEMICOLON);

        // 签约卖家支付宝账号
        stringBuilder.append("&seller_id=\"" + partner + SEMICOLON);

        // 商户网站唯一订单号
        stringBuilder.append("&out_trade_no=\"" + order.getOrderCode() + SEMICOLON);

        // 商品名称
        stringBuilder.append("&subject=\"" + subject + SEMICOLON);
        // 商品详情
        stringBuilder.append("&body=\"" + subject + SEMICOLON);

        // 商品金额
        stringBuilder.append("&total_fee=\"" + order.getMoneyStr() + SEMICOLON);

        // 服务器异步通知页面路径
        stringBuilder.append("&notify_url=\"" + notifyUrl + SEMICOLON);

        // 服务接口名称， 固定值
        stringBuilder.append("&service=\"mobile.securitypay.pay\"");

        // 支付类型， 固定值
        stringBuilder.append("&payment_type=\"1\"");

        // 参数编码， 固定值
        stringBuilder.append("&_input_charset=\"utf-8\"");
        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        stringBuilder.append("&it_b_pay=\"30m\"");
        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        stringBuilder.append("&show_url=\"m.alipay.com\"");
        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return stringBuilder.toString();
    }



    /**
     * 授权Map
     * 
     * @param order
     * @return
     * @throws Exception
     */
    public static Map<String, String> genAliAppPayMap(PayCommonObject order) throws Exception {
        // 授权
        String partner = ConfigurationUtil.getString("ali_pay_id");
        String encoding = ConfigurationUtil.getString("ali_input_charset");
        String notifyUrl = ConfigurationUtil.getString("ali_merchant_pay_callback_url");
        String returnUrl = ConfigurationUtil.getString("ali_merchant_pay_callback_url");
        String sellerAcount = ConfigurationUtil.getString("ali_login_count");
        String subject = ConfigurationUtil.getString("ali_order_name");
        String merchantUrl = ConfigurationUtil.getString("staticPath_WAP");
//        String req_dataToken =
//                "<direct_trade_create_req><notify_url>" + notifyUrl
//                        + "</notify_url><call_back_url>" + returnUrl
//                        + "</call_back_url><seller_account_name>" + sellerAcount
//                        + "</seller_account_name><out_trade_no>" + order.getOrderCode()
//                        + "</out_trade_no><subject>" + subject + "</subject><total_fee>"
//                        + order.getMoneyStr() + "</total_fee><merchant_url>" + merchantUrl
//                        + "</merchant_url></direct_trade_create_req>";
        Map<String, String> params = new HashMap<>();
        // params.put("service", "mobile.securitypay.pay");
        params.put("partner", partner);
        params.put("seller_id", partner);// new
        params.put("out_trade_no", order.getOrderCode());// new
        params.put("subject", subject);// new
        params.put("body", subject);// new
        params.put("total_fee", order.getMoneyStr());// new
        params.put("notify_url", notifyUrl);// new
        params.put("service", "mobile.securitypay.pay");
        params.put("payment_type", "1");// new
        params.put("_input_charset", encoding);
        params.put("it_b_pay", "30m");// new
        params.put("show_url", "m.alipay.com");// new
        // params.put("sec_id", signType);
        // params.put("format", format);
        // params.put("v", version);
        // params.put("req_id", genDateLong());
        // params.put("req_data", req_dataToken);
        // String token = PaymentUtil.getToken(ALI_WAP_REQUEST_URL_PREX, params);

        // if (null != token) {
        // // 支付
        // params.clear();
        // String req_data =
        // "<auth_and_execute_req><request_token>" + token
        // + "</request_token></auth_and_execute_req>";
        // params.put("service", "alipay.wap.auth.authAndExecute");
        // params.put("partner", partner);
        // params.put("_input_charset", encoding);
        // params.put("sec_id", signType);
        // params.put("format", format);
        // params.put("v", version);
        // params.put("req_data", req_data);
        // return params;
        // }
        return params;
    }

    /**
     * 生成时间Long串，追加6个随机数
     * 
     * @return
     */
    private static String genDateLong() {
        return sdfLong.format(Instant.now()) + new Random().nextInt(1000000);
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
        String tradeStatus = null;
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
     * @return
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
