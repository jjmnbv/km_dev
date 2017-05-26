package com.kmzyc.b2b.util.pay.plugin;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
 * 财付通
 * 
 * @author xlg
 * 
 */
@SuppressWarnings({"unchecked", "BigDecimalMethodWithoutRoundingCalled"})
public class TenpayPlugIn extends BasePayPlugIn {

    /**
     * 请求
     */
    @Override
    public String onPay() throws Exception {
        PayCommonObject order = this.getOrder();
        RefundParamInfo refundInfo = this.getRefundInfo();
        String reqUrl = null;
        Map<String, String> requestMap = null;
        if (null != order && !StringUtil.isEmpty(order.getOrderCode())) {
            // 支付
            reqUrl = ConfigurationUtil.getString("ten_pay_pay_req_url");
            requestMap = genTenPayMap(order, this.getCallBackRequest());
        } else if (null != refundInfo) {
            reqUrl = ConfigurationUtil.getString("ten_pay_refund_req_url");
            requestMap = genTenRefundMap(refundInfo);
        }
        return PaymentUtil.buildTenPayRequest(requestMap, reqUrl, "post", "确定");
    }

    /**
     * 回调
     */
    @Override
    public Object onReturn() throws Exception {
        HttpServletRequest callBackRequest = this.getCallBackRequest();
        if (!StringUtil.isEmpty(callBackRequest.getParameter("transaction_id"))) {
            // 支付
            return genTenPayReturnObj(callBackRequest);
        } else if (!StringUtil.isEmpty(callBackRequest.getParameter("refund_status"))) {
            // 退款
            return genTenRefundReturnObj(callBackRequest);
        }
        return null;
    }

    /**
     * 生成支付Map
     * 
     * @param order
     * @return
     */
    private static Map<String, String> genTenPayMap(PayCommonObject order,
            HttpServletRequest callBackRequest) throws Exception {
        SortedMap<String, String> requestMap = new TreeMap<String, String>();
        String extInfo = order.getExtInfo();
        extInfo = null == extInfo ? "" : extInfo.replaceAll("\\|", "^");

        requestMap.put("partner", ConfigurationUtil.getString("ten_pay_partner"));// 商户号
        requestMap.put("out_trade_no", order.getOrderCode());// 商家订单号
        requestMap.put("total_fee", new BigDecimal(order.getMoneyStr()).multiply(BigDecimal.TEN)
                .multiply(BigDecimal.TEN).toBigInteger().toString());// 商品金额,以分为单位
        requestMap.put("fee_type", "1"); // 币种，1人民币
        requestMap.put("return_url", ConfigurationUtil.getString("ten_pay_callback_url"));// 交易完成后跳转的URL
        requestMap.put("notify_url", ConfigurationUtil.getString("ten_pay_callback_url"));// 接收财付通通知的URL
        requestMap.put("body", ConfigurationUtil.getString("ten_pay_body"));// 商品描述
        requestMap.put("subject", ConfigurationUtil.getString("ten_pay_subject"));// 商品名称
        requestMap.put("spbill_create_ip", callBackRequest.getRemoteAddr()); // 用户的公网ip，不是商户服务器IP
        // 系统可选参数
        requestMap.put("sign_type", ConfigurationUtil.getString("ten_pay_sign_type"));// 签名类型
        requestMap.put("service_version",
                ConfigurationUtil.getString("ten_pay_pay_service_version")); // 版本号，默认为1.0
        requestMap.put("input_charset", ConfigurationUtil.getString("ten_pay_input_charset")); // 字符编码
        requestMap.put("sign_key_index", ConfigurationUtil.getString("ten_pay_sign_key_index")); // 密钥序号
        String bankType = (String) callBackRequest.getAttribute("bank_type");
        if (!StringUtil.isEmpty(bankType)) {
            requestMap.put("bank_type", bankType); // 附加数据
        }
        // 业务可选参数
        requestMap.put("attach", extInfo); // 附加数据
        requestMap.put("trans_type", "1"); // 交易类型，1实物交易，2虚拟交易
        requestMap.put("seller_id", ""); // 卖家商户号，为空则等同于partner
        return requestMap;
    }

    /**
     * 生成支付回调对象
     * 
     * @param callBackRequest
     * @return
     */
    private static PayCommonObject genTenPayReturnObj(HttpServletRequest callBackRequest)
            throws Exception {
        PayCommonObject obj = null;
        if (!"0".equals(callBackRequest.getParameter("trade_state"))) {
            return obj;
        }
        SortedMap<String, String> params = new TreeMap<String, String>();
        Map<String, String[]> requestParams = callBackRequest.getParameterMap();
        for (Map.Entry<String, String[]> entry:requestParams.entrySet()) {
            String key =entry.getKey();
            String[] values = entry.getValue();
            String valueStr = StringUtils.join(values,",");
            params.put(key, valueStr);
        }
        if (PaymentUtil.verifyIsTenPay(params)) {
            obj = new PayCommonObject();
            obj.setOrderCode(callBackRequest.getParameter("out_trade_no"));
            obj.setCurrencyCode("CNY");
            String extInfo = callBackRequest.getParameter("attach");
            extInfo = null == extInfo ? "" : extInfo.replaceAll("\\^", "|");
            obj.setExtInfo(extInfo);
            obj.setMoneyStr(new BigDecimal(callBackRequest.getParameter("total_fee"))
                    .divide(BigDecimal.TEN).divide(BigDecimal.TEN).toString());
            obj.setOrderCode(callBackRequest.getParameter("out_trade_no"));
            obj.setPayDateStr(callBackRequest.getParameter("time_end"));
            obj.setTransitionNO(callBackRequest.getParameter("transaction_id"));
            obj.setTrxTime(callBackRequest.getParameter("time_end"));
            obj.setPayStateCode("1");
            obj.setBankId(callBackRequest.getParameter("bank_type"));
            obj.setBankOrderId(callBackRequest.getParameter("bank_billno"));
        }
        return obj;
    }

    /**
     * 生成退款Map
     * 
     * @param refundInfo
     * @return
     */
    private static Map<String, String> genTenRefundMap(RefundParamInfo refundInfo) {
        return null;
    }

    /**
     * 生成退款回调对象
     * 
     * @param callBackRequest
     * @return
     */
    private static RefundResult genTenRefundReturnObj(HttpServletRequest callBackRequest)
            throws Exception {
        return null;
    }
}
