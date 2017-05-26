/**
 * 
 */
package com.kmzyc.b2b.util.pay;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.NameValuePair;

import com.kmzyc.b2b.model.PayCommonObject;
import com.kmzyc.b2b.model.RefundParamInfo;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.util.httpClient.HttpProtocolHandler;
import com.kmzyc.b2b.util.httpClient.HttpRequest;
import com.kmzyc.b2b.util.httpClient.HttpResponse;

/**
 * @author zengming
 * @version 1.0
 * @since 2013-10-15 为满足多支付平台需要该基础抽象支付插件类做支撑
 */
@SuppressWarnings("unused")
public class BasePayPlugIn implements PayPlugInInterface {

    // 调用第三方支付平台支付网关时自定义的request
    private HttpRequest extRequest;
    // 支付通用对象
    private PayCommonObject order;
    // 退款通用对象
    private RefundParamInfo refundInfo;
    // 回调时传入的request
    private HttpServletRequest callBackRequest;
    // 回调时传入的response
    private HttpServletResponse callBackResponse;
    // 支付用户信息
    private User user;

    public HttpRequest getExtRequest() {
        return extRequest;
    }

    public void setExtRequest(HttpRequest extRequest) {
        this.extRequest = extRequest;
    }

    public HttpServletRequest getCallBackRequest() {
        return callBackRequest;
    }

    public void setCallBackRequest(HttpServletRequest callBackRequest) {
        this.callBackRequest = callBackRequest;
    }

    public HttpServletResponse getCallBackResponse() {
        return callBackResponse;
    }

    public void setCallBackResponse(HttpServletResponse callBackResponse) {
        this.callBackResponse = callBackResponse;
    }

    public PayCommonObject getOrder() {
        return order;
    }

    public void setOrder(PayCommonObject order) {
        this.order = order;
    }

    @Override
    public String onPay() throws Exception {
        // 执行调用
        HttpResponse response = HttpProtocolHandler.getInstance().execute(extRequest, "", "");
        if (response == null) {
            return null;
        }
        String strResult = response.getStringResult();
        return strResult;
    }

    @Override
    public Object onReturn() throws Exception {
        // 根据不同的支付平台提取request中相关数据组装成通用对象PayCommonReq返回
        // String localName = callBackRequest.getLocalName();
        // 第三方支付流水号
        String transitionNO = null;
        // 订单编码
        String orderCode = null;
        // 交易日期
        String payDateStr = null;
        // 交易金额
        String moneyStr = null;
        // 交易币种
        String currencyCode = null;
        // 支付状态码
        String payStateCode = null;
        return null;
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     * 
     * @param properties MAP类型数组
     * @return NameValuePair类型数组
     */
    protected static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        return nameValuePair;
    }

    public RefundParamInfo getRefundInfo() {
        return refundInfo;
    }

    public void setRefundInfo(RefundParamInfo refundInfo) {
        this.refundInfo = refundInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
