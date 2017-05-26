package com.kmzyc.b2b.util.pay.plugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.kmzyc.b2b.model.PayCommonObject;
import com.kmzyc.b2b.model.RefundParamInfo;
import com.kmzyc.b2b.model.RefundResult;
import com.kmzyc.b2b.util.pay.BasePayPlugIn;
import com.kmzyc.b2b.util.pay.util.PaymentUtil;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 支付宝即时到账交易 回调参数在windows下需要编码，linux无需编码
 * 
 * @author xiongliguang
 */
@SuppressWarnings({"unchecked", "unused"})
public class AliPayPlugIn extends BasePayPlugIn {

    // 请求前缀
    // private static final String reqUrl = ConfigurationUtil.getString("ali_pay_request_url_prex");

    /**
     * 请求
     */
    @Override
    public String onPay() throws Exception {
        PayCommonObject order = this.getOrder();
        RefundParamInfo refundInfo = this.getRefundInfo();
        Map<String, String> requestMap = null;
        if (null != order && !StringUtil.isEmpty(order.getOrderCode())) {
            // 支付
            requestMap = genAliPayMap(order);
        } else if (null != refundInfo) {
            // 退款
            requestMap = genAliRefundMap(refundInfo);
        }
        return PaymentUtil.buildAliPayRequest(requestMap,
                ConfigurationUtil.getString("ali_pay_request_url_prex"), "post", "确定");
    }

    /**
     * 回调
     */
    @Override
    public Object onReturn() throws Exception {
        HttpServletRequest callBackRequest = this.getCallBackRequest();
        String tradeNo = callBackRequest.getParameter("trade_no");
        if (!StringUtil.isEmpty(tradeNo)) {
            // 支付
            return genAliPayReturnObj(callBackRequest);
        } else {
            // 退款
            return genAliRefundReturnObj(callBackRequest);
        }
    }

    /**
     * 生成支付Map
     * 
     * @param order
     * @return
     */
    private static Map<String, String> genAliPayMap(PayCommonObject order) throws Exception {
        String paySource = order.getPaySource();
        String extInfo = order.getExtInfo();
        extInfo = null == extInfo ? "" : extInfo.replaceAll("\\|", "^");
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("service", "create_direct_pay_by_user");// 接口名称
        requestMap.put("partner", ConfigurationUtil.getString("ali_pay_id"));// 合作身份者ID
        requestMap.put("_input_charset", ConfigurationUtil.getString("ali_input_charset"));// 字符编码格式
        requestMap.put("payment_type", ConfigurationUtil.getString("ali_default_payment_type"));// 支付类型
        String notifyUrl = ConfigurationUtil.getString("ali_merchant_pay_callback_url");
        String returnUrl = ConfigurationUtil.getString("ali_merchant_pay_callback_url");
        if (null != paySource && "YS".equals(paySource.trim())) {// 设置预售订单回调方法
            notifyUrl = ConfigurationUtil.getString("ali_merchant_pay_callback_url_ys");
            returnUrl = ConfigurationUtil.getString("ali_merchant_pay_callback_url_ys");
        }
        requestMap.put("return_url", returnUrl);// 页面跳转同步通知页面路径
        requestMap.put("notify_url", notifyUrl);// 页面跳转异步通知页面路径
        requestMap.put("seller_email", ConfigurationUtil.getString("ali_login_count"));// 卖家支付宝帐户
        requestMap.put("out_trade_no", order.getOrderCode());// 商户订单号
        requestMap.put("subject", ConfigurationUtil.getString("ali_order_name"));// 订单名称
        requestMap.put("total_fee", order.getMoneyStr());// 交易金额
        requestMap.put("body", ConfigurationUtil.getString("ali_order_desc"));// 订单描述
        if (null != order.getBankId() && order.getBankId().length() > 0) {
            requestMap.put("defaultbank", order.getBankId());// 银行
        }
        requestMap.put("extra_common_param", extInfo);
        return requestMap;
    }

    /**
     * 生成支付回调对象
     * 
     * @param callBackRequest
     * @return
     */
    private static PayCommonObject genAliPayReturnObj(HttpServletRequest callBackRequest)
            throws Exception {
        PayCommonObject obj = null;
        String tradeStatus = callBackRequest.getParameter("trade_status");
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = callBackRequest.getParameterMap();
        String isSuccess = callBackRequest.getParameter("is_success");
        for (Map.Entry<String, String[]> entry:requestParams.entrySet()) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            String valueStr = StringUtils.join(values,",");
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), charset);
            params.put(name, valueStr);
        }
        if (("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus))
                && PaymentUtil.verifyIsAliPay(params)) {
            obj = new PayCommonObject();
            obj.setOrderCode(callBackRequest.getParameter("out_trade_no"));
            obj.setCurrencyCode("CNY");
            String extInfo = callBackRequest.getParameter("extra_common_param");
            extInfo = null == extInfo ? "" : extInfo.replaceAll("\\^", "|");
            obj.setExtInfo(extInfo);
            obj.setMoneyStr(callBackRequest.getParameter("total_fee"));
            obj.setOrderCode(callBackRequest.getParameter("out_trade_no"));
            obj.setPayDateStr(callBackRequest.getParameter("notify_time"));
            obj.setTransitionNO(callBackRequest.getParameter("trade_no"));
            obj.setTrxTime(callBackRequest.getParameter("notify_time"));
            obj.setPayStateCode("1");
            obj.setBankId("");
            obj.setBankOrderId("");
            obj.setSynchCall("T".equals(isSuccess));
        }
        return obj;
    }

    /**
     * 生成退款Map
     * 
     * @param order
     * @return
     */
    private static Map<String, String> genAliRefundMap(RefundParamInfo refundInfo) {
        String extParamsStr = refundInfo.getExtParmas();
        if (StringUtil.isEmpty(extParamsStr)) {
            return null;
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String charset = ConfigurationUtil.getString("ali_input_charset");
        Date date = new Date();
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("service", "refund_fastpay_by_platform_pwd");// 接口名称
        requestMap.put("partner", ConfigurationUtil.getString("ali_pay_id"));// 合作身份者ID
        requestMap.put("_input_charset", charset);// 字符编码格式
        requestMap.put("sign_type", ConfigurationUtil.getString("ali_sign_type"));
        requestMap.put("notify_url",
                ConfigurationUtil.getString("ali_merchant_refund_callback_url"));// 页面跳转同步通知页面路径
        requestMap.put("seller_email", ConfigurationUtil.getString("ali_login_count"));// 卖家支付宝帐户
        requestMap.put("seller_user_id", ConfigurationUtil.getString("ali_pay_id"));// 卖家ID
        requestMap.put("refund_date", sdf1.format(date));// 退款请求时间
        requestMap.put("batch_no", refundInfo.getBatchNo());// 退款批次号
        requestMap.put("detail_data", extParamsStr);
        requestMap.put("batch_num", extParamsStr.split("#").length + "");
        return requestMap;
    }

    /**
     * 生成退款回调对象
     * 
     * @param callBackRequest
     * @return
     */
    private static RefundResult genAliRefundReturnObj(HttpServletRequest callBackRequest)
            throws Exception {
        RefundResult obj = null;
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = callBackRequest.getParameterMap();
        for (Map.Entry<String, String[]> entry:requestParams.entrySet()) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            String valueStr = StringUtils.join(values,",");
            params.put(name, valueStr);
        }
        int successNum = 0;
        String successNumStr = params.get("success_num");
        if (null != successNumStr) {
            successNum = Integer.parseInt(successNumStr);
        }
        if (successNum > 0 && PaymentUtil.verifyIsAliPay(params)) {
            obj = new RefundResult();
            obj.setExtParams(params.get("result_details"));
            obj.setRefundBatchNo(params.get("batch_no"));
            obj.setR4_Cur("CNY");
        }
        return obj;
    }
}
