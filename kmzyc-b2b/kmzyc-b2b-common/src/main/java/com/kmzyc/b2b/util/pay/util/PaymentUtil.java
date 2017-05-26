package com.kmzyc.b2b.util.pay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.util.AlipayCore;
import com.alipay.util.httpClient.HttpProtocolHandler;
import com.alipay.util.httpClient.HttpRequest;
import com.alipay.util.httpClient.HttpResponse;
import com.alipay.util.httpClient.HttpResultType;
import com.kmzyc.b2b.third.util.WxHttpUtil;
import com.kmzyc.b2b.util.XMLHelper;
import com.kmzyc.b2b.util.wxpay.CommonUtil;
import com.kmzyc.b2b.util.wxpay.MD5Util;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
public class PaymentUtil {

    private static Logger log = LoggerFactory.getLogger(PaymentUtil.class);
    /**
     * 支付宝的公钥，无需修改该值
     */
    // private static final String ALI_PUBLIC_KEY = ConfigurationUtil.getString("ali_public_key");
    /**
     * 支付宝验证请求前缀
     */
    private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

    /**
     * 支付宝商户私钥
     */
    // private static final String ALI_PRIVATE_KEY = ConfigurationUtil.getString("ali_private_key");
    /**
     * 康美通商户私钥
     */
    // private static final String KMT_PRIVATE_KEY = ConfigurationUtil.getString("kmt_private_key");
    /**
     * 康美通返回公钥
     */
    // private static final String KMT_PUBLIC_KEY_CALL =
    // ConfigurationUtil.getString("kmt_public_key");

    /**
     * wap加密类型
     */
    // private static final String ALI_WAP_SIGN_TYPE = ConfigurationUtil
    // .getString("ali_wap_sign_type");
    /**
     * wap支付私钥
     */
    // private static final String ALI_WAP_MERCHANT_PRIVATE_KEY = ConfigurationUtil
    // .getString("ali_wap_merchant_private_key");
    /**
     * wap支付公钥
     */
    // private static final String ALI_WAP_PUBLIC_KEY = ConfigurationUtil
    // .getString("ali_wap_public_key");
    /**
     * 支付宝编码
     */
    // private static final String ALI_ENCODING = ConfigurationUtil.getString("ali_input_charset");
    /**
     * 财付通编码
     */
    // private static final String TEN_PAY_INPUT_CHARSET = ConfigurationUtil
    // .getString("ten_pay_input_charset");

    /**
     * 时代编码
     */
    // private static final String SD_ENCODING = ConfigurationUtil.getString("sd_input_charset");
    /**
     * 康美通编码
     */
    // private static final String KMT_ENCODING = ConfigurationUtil.getString("kmt_input_charset");

    // private static Logger log = Logger.getLogger(PaymentUtil.class);

    /**
     * 易宝在线支付生成hmac方法
     *
     * @param p0_Cmd          业务类型
     * @param p1_MerId        商户编号
     * @param p2_Order        商户订单号
     * @param p3_Amt          支付金额
     * @param p4_Cur          交易币种
     * @param p5_Pid          商品名称
     * @param p6_Pcat         商品种类
     * @param p7_Pdesc        商品描述
     * @param p8_Url          商户接收支付成功数据的地址
     * @param p9_SAF          送货地址
     * @param pa_MP           商户扩展信息
     * @param pd_FrpId        银行编码
     * @param pr_NeedResponse 应答机制
     * @param keyValue        商户密钥
     */
    public static String buildHmac4YeeBao(String p0_Cmd, String p1_MerId, String p2_Order,
                                          String p3_Amt, String p4_Cur, String p5_Pid,
                                          String p6_Pcat, String p7_Pdesc, String p8_Url,
                                          String p9_SAF, String pa_MP, String pd_FrpId,
                                          String pr_NeedResponse, String keyValue) {
        StringBuilder sValue = new StringBuilder();
        sValue.append(p0_Cmd);// 业务类型
        sValue.append(p1_MerId);// 商户编号
        sValue.append(p2_Order); // 商户订单号
        sValue.append(p3_Amt); // 支付金额
        sValue.append(p4_Cur); // 交易币种
        sValue.append(p5_Pid);// 商品名称
        sValue.append(p6_Pcat); // 商品种类
        sValue.append(p7_Pdesc);// 商品描述
        sValue.append(p8_Url);// 商户接收支付成功数据的地址
        sValue.append(p9_SAF);// 送货地址
        sValue.append(pa_MP);// 商户扩展信息
        sValue.append(pd_FrpId);// 银行编码
        sValue.append(pr_NeedResponse);// 应答机制
        return DigestUtil.hmacSign(sValue.toString(), keyValue);
    }

    /**
     * 返回校验hmac方法
     *
     * @param hmac     支付网关发来的加密验证码
     * @param p1_MerId 商户编号
     * @param r0_Cmd   业务类型
     * @param r1_Code  支付结果
     * @param r2_TrxId 易宝支付交易流水号
     * @param r3_Amt   支付金额
     * @param r4_Cur   交易币种
     * @param r5_Pid   商品名称
     * @param r6_Order 商户订单号
     * @param r7_Uid   易宝支付会员ID
     * @param r8_MP    商户扩展信息
     * @param r9_BType 交易结果返回类型
     * @param keyValue 密钥
     */
    public static boolean verifyCallback4YeePay(String hmac, String p1_MerId, String r0_Cmd,
                                                String r1_Code, String r2_TrxId, String r3_Amt,
                                                String r4_Cur, String r5_Pid, String r6_Order,
                                                String r7_Uid, String r8_MP, String r9_BType,
                                                String keyValue) {
        StringBuilder sValue = new StringBuilder();
        sValue.append(p1_MerId); // 商户编号
        sValue.append(r0_Cmd); // 业务类型
        sValue.append(r1_Code); // 支付结果
        sValue.append(r2_TrxId);// 易宝支付交易流水号
        sValue.append(r3_Amt);// 支付金额
        sValue.append(r4_Cur); // 交易币种
        sValue.append(r5_Pid); // 商品名称
        sValue.append(r6_Order);// 商户订单号
        sValue.append(r7_Uid);// 易宝支付会员ID
        sValue.append(r8_MP);// 商户扩展信息
        sValue.append(r9_BType);// 交易结果返回类型
        if (hmac.equals(DigestUtil.hmacSign(sValue.toString(), keyValue))) {
            return true;
        }
        return false;
    }

    /**
     * 构建请求参数
     */
    public static String buildForm(Map sPara, String gateway, String strMethod, String key) {
        List keys = new ArrayList(sPara.keySet());
        StringBuilder sbHtml = new StringBuilder();
        sbHtml.append(
                (new StringBuilder("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\""))
                        .append(gateway).append("\" method=\"").append(strMethod).append("\">")
                        .toString());
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);
            sbHtml.append((new StringBuilder("<input type=\"hidden\" name=\"")).append(name)
                    .append("\" value=\"").append(value).append("\"/>").toString());
        }
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
        return sbHtml.toString();
    }

    // ////////////////////// 支付宝 /////////////////////////////////////

    /**
     * 构建支付宝请求表单
     *
     * @param reqMap  请求参数
     * @param action  请求action
     * @param method  请求方式post或get
     * @param btnName 确认按钮名称
     */
    public static String buildAliPayRequest(Map<String, String> reqMap, String action,
                                            String method, String btnName) {
        Map<String, String> sPara = buildRequestPara(reqMap,
                ConfigurationUtil.getString("ali_sign_type"),
                ConfigurationUtil.getString("ali_private_key"));
        List<String> keys = new ArrayList<String>(sPara.keySet());
        StringBuilder sbHtml = new StringBuilder();
        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + action +
                "_input_charset=" + ConfigurationUtil.getString("ali_input_charset") +
                "\" method=\"" + method + "\">");
        for (int i = 0; i < keys.size(); i++) {
            String name = keys.get(i);
            String value = sPara.get(name);
            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        // submit按钮控件无name属性
        sbHtml.append(
                "<input type=\"submit\" value=\"" + btnName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
        return sbHtml.toString();
    }

    /**
     * 生成要请求给支付宝的参数数组
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp,
                                                       String signType, String privateKey) {
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        if ("RSA".equals(signType)) {
            // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            String linkStr = AlipayCore.createLinkString(sPara);
            sPara.put("sign", RSA.sign(linkStr, privateKey, sPara.get("_input_charset")));
        }
        sPara.put("sign_type", signType);
        return sPara;
    }

    /**
     * 是否是支付宝返回
     */
    public static boolean verifyIsAliPay(Map<String, String> params) {
        String responseTxt = "true";
        if (params.get("notify_id") != null) {
            String notify_id = params.get("notify_id");
            responseTxt = verifyResponse(notify_id);
        }
        if (!"true".equals(responseTxt)) {
            return false;
        }
        String sign = params.get("sign");
        sign = null == sign ? "" : sign;
        Map<String, String> sParaNew = AlipayCore.paraFilter(params);
        if ("RSA".equals(ConfigurationUtil.getString("ali_sign_type"))) {
            String preSignStr = AlipayCore.createLinkString(sParaNew);
            return RSA.verify(preSignStr, sign, ConfigurationUtil.getString("ali_public_key"),
                    ConfigurationUtil.getString("ali_input_charset"));
        }
        return false;
    }

    /**
     * 是否是支付宝响应
     */
    private static String verifyResponse(String notify_id) {
        String partner = ConfigurationUtil.getString("ali_pay_id");
        String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;
        BufferedReader in = null;
        try {
            URL url = new URL(veryfy_url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            return in.readLine();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     *
     * @param properties MAP类型数组
     * @return NameValuePair类型数组
     */
    public static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        return nameValuePair;
    }

    /**
     * 构建支付宝请求表单
     *
     * @param reqMap  请求参数
     * @param action  请求action
     * @param method  请求方式post或get
     * @param btnName 确认按钮名称
     */
    public static String buildAliPayRequestForWap(Map<String, String> reqMap, String action,
                                                  String method, String btnName) {
        Map<String, String> sPara = buildRequestParaFowWap(reqMap);
        List<String> keys = new ArrayList<String>(sPara.keySet());
        StringBuilder sbHtml = new StringBuilder();
        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + action +
                "_input_charset=" + ConfigurationUtil.getString("ali_input_charset") +
                "\" method=\"" + method + "\">");
        for (int i = 0; i < keys.size(); i++) {
            String name = keys.get(i);
            String value = sPara.get(name);
            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        // submit按钮控件无name属性
        sbHtml.append(
                "<input type=\"submit\" value=\"" + btnName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
        return sbHtml.toString();
    }

    /**
     * 生成要请求给支付宝的参数数组
     */
    public static Map<String, String> buildRequestParaFowWap(Map<String, String> sParaTemp) {
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        if ("0001".equals(ConfigurationUtil.getString("ali_wap_sign_type"))) {
            // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            String linkStr = AlipayCore.createLinkString(sPara);
            sPara.put("sign",
                    RSA.sign(linkStr, ConfigurationUtil.getString("ali_wap_merchant_private_key"),
                            sPara.get("_input_charset")));
        }
        if (!sPara.get("service").equals("alipay.wap.trade.create.direct") &&
                !sPara.get("service").equals("alipay.wap.auth.authAndExecute")) {
            sPara.put("sign_type", ConfigurationUtil.getString("ali_wap_sign_type"));
        }
        return sPara;
    }

    /**
     * 是否是支付宝返回
     */
    public static boolean verifyIsAliPayForWap(Map<String, String> params) {
        String responseTxt = "true";
        if (params.get("notify_id") != null) {
            String notify_id = params.get("notify_id");
            responseTxt = verifyResponse(notify_id);
        }
        if (!"true".equals(responseTxt)) {
            return false;
        }
        String sign = params.get("sign");
        sign = null == sign ? "" : sign;
        Map<String, String> sParaNew = AlipayCore.paraFilter(params);
        if ("0001".equals(ConfigurationUtil.getString("ali_wap_sign_type"))) {
            String preSignStr = null;
            if (params.containsKey("sec_id")) {
                preSignStr = createLinkStringNoSort(sParaNew);
            } else {
                preSignStr = AlipayCore.createLinkString(sParaNew);
            }
            return RSA.verify(preSignStr, sign, ConfigurationUtil.getString("ali_wap_public_key"),
                    ConfigurationUtil.getString("ali_input_charset"));
        }
        return false;
    }

    /**
     * 模拟请求并解析，获取token
     */
    public static String getToken(String prex, Map<String, String> params) throws Exception {
        Map<String, String> sPara = buildRequestParaFowWap(params);
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        request.setCharset(ConfigurationUtil.getString("ali_input_charset"));
        request.setParameters(generatNameValuePair(sPara));
        request.setUrl(prex + "_input_charset=" + ConfigurationUtil.getString("ali_input_charset"));
        HttpResponse response = httpProtocolHandler.execute(request);
        if (response == null) {
            return null;
        }
        String text = URLDecoder.decode(response.getStringResult(),
                ConfigurationUtil.getString("ali_input_charset"));

        String token = null;
        String[] strArray = text.split("&");
        Map<String, String> rsMap = new HashMap<String, String>();
        for (String str : strArray) {
            int nPos = str.indexOf("=");
            int nLen = str.length();
            rsMap.put(str.substring(0, nPos), str.substring(nPos + 1, nLen));
        }
        String resData = rsMap.get("res_data");
        if (null != resData) {
            if ("0001".equals(ConfigurationUtil.getString("ali_wap_sign_type"))) {
                resData = RSA.decrypt(resData,
                        ConfigurationUtil.getString("ali_wap_merchant_private_key"),
                        ConfigurationUtil.getString("ali_input_charset"));
            }
            Document document = DocumentHelper.parseText(resData);
            token = document.selectSingleNode("//direct_trade_create_res/request_token").getText();
        }
        return token;
    }

    /**
     * 解密
     */
    public static Map<String, String> decryptForWap(Map<String, String> inputPara) throws
            Exception {
        inputPara.put("notify_data", RSA.decrypt(inputPara.get("notify_data"),
                ConfigurationUtil.getString("ali_wap_merchant_private_key"),
                ConfigurationUtil.getString("ali_input_charset")));
        return inputPara;
    }

    /**
     * 无序
     */
    private static String createLinkStringNoSort(Map<String, String> params) {
        StringBuilder gotoSign_params = new StringBuilder();
        gotoSign_params.append("service=" + params.get("service"));
        gotoSign_params.append("&v=" + params.get("v"));
        gotoSign_params.append("&sec_id=" + params.get("sec_id"));
        gotoSign_params.append("&notify_data=" + params.get("notify_data"));
        return gotoSign_params.toString();
    }

    // ////////////////////// 支付宝 /////////////////////////////////////


    // //////////////////////////财付通/////////////////////////////////////////

    /**
     * 构建财付通请求
     *
     * @return String
     */
    public static String buildTenPayRequest(Map<String, String> reqMap, String action,
                                            String method, String btnName) {
        signMD5ForTenOrWxPay(reqMap, ConfigurationUtil.getString("ten_pay_private_key"));
        StringBuilder sbHtml = new StringBuilder();
        sbHtml.append("<form id=\"tenpaysubmit\" name=\"tenpaysubmit\" action=\"" + action +
                "\" method=\"" + method + "\">");
        Iterator<String> keysIt = reqMap.keySet().iterator();
        for (Map.Entry<String, String> entry : reqMap.entrySet()) {
            String key = entry.getKey();
            sbHtml.append(
                    "<input type=\"hidden\" name=\"" + key + "\" value=\"" + entry.getValue() +
                            "\"/>");
        }
        sbHtml.append(
                "<input type=\"submit\" value=\"" + btnName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['tenpaysubmit'].submit();</script>");
        return sbHtml.toString();
    }

    /**
     * 财付通或微信MD5签名
     */
    public static void signMD5ForTenOrWxPay(Map<String, String> reqMap, String keyVal) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> keysIt = reqMap.keySet().iterator();
        for (Map.Entry<String, String> entry : reqMap.entrySet()) {
            String key = entry.getKey();//keysIt.next();
            String value = entry.getValue();// reqMap.get(key);
            if (!StringUtil.isEmpty(value) && !"sign".equals(key) && !"key".equals(key)) {
                sb.append(key).append('=').append(value).append('&');
            }
        }
        sb.append("key=").append(keyVal);
        reqMap.put("sign", MD5Util.MD5Encode(sb.toString(),
                ConfigurationUtil.getString("ten_pay_input_charset")).toUpperCase());
    }

    /**
     * 是否是财付通响应
     */
    public static boolean verifyIsTenPay(Map<String, String> params) {
        SortedMap<String, String> reqMap;
        reqMap = new TreeMap<String, String>();
        reqMap.put("notify_id", params.get("notify_id"));
        reqMap.put("sign_type", ConfigurationUtil.getString("ten_pay_sign_type"));
        reqMap.put("service_version",
                ConfigurationUtil.getString("ten_pay_notify_service_query_version"));
        reqMap.put("input_charset", ConfigurationUtil.getString("ten_pay_input_charset"));
        reqMap.put("sign_key_index",
                ConfigurationUtil.getString("ten_pay_notify_service_query_version"));
        reqMap.put("input_charset", ConfigurationUtil.getString("ten_pay_input_charset"));
        reqMap.put("partner", ConfigurationUtil.getString("ten_pay_partner"));
        BufferedReader in = null;
        try {
            URL url = new URL(
                    getTenPayRequestURL(ConfigurationUtil.getString("ten_pay_notify_query_url"),
                            reqMap));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String str = null;
            StringBuilder sb = new StringBuilder();
            while (null != (str = in.readLine())) {
                sb.append(str);
            }
            Map<String, String> rsMap = XMLHelper.singleLevelAnalyze(sb.toString());
            return null != rsMap && "0".equals(rsMap.get("retcode")) &&
                    isTenpaySign(params, ConfigurationUtil.getString("ten_pay_private_key"));
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return false;
    }

    /**
     * 生成财付通get请求
     */
    public static String getTenPayRequestURL(String url, Map<String, String> reqMap) throws
            UnsupportedEncodingException {
        signMD5ForTenOrWxPay(reqMap, ConfigurationUtil.getString("ten_pay_private_key"));
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : reqMap.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        return url + "?" + sb.substring(0, sb.lastIndexOf("&"));
    }

    /**
     * 生成微信get请求
     */
    public static String getWXPayRequestURL(String url, Map<String, String> reqMap) throws
            UnsupportedEncodingException {
        signMD5ForTenOrWxPay(reqMap, ConfigurationUtil.getString("wx_pay_private_key"));
        StringBuilder sb = new StringBuilder();
        Iterator<String> keysIt = reqMap.keySet().iterator();
        while (keysIt.hasNext()) {
            String key = keysIt.next();
            sb.append(key + "=" + reqMap.get(key) + "&");
        }
        return url + "?" + sb.substring(0, sb.lastIndexOf("&"));
    }

    /**
     * 签名，生成微信Map
     *
     * @return reqMap
     */
    public static Map<String, String> getWXPayMap(Map<String, String> reqMap) throws
            UnsupportedEncodingException {
        signMD5ForTenOrWxPay(reqMap, ConfigurationUtil.getString("wx_pay_private_key"));
        return reqMap;
    }

    /**
     * 通过code换取网页授权access_token
     */
    public static JSONObject getAccessTokenByCode(String code) {
        String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +
                ConfigurationUtil.getString("wx_pay_appid") + "&secret=" +
                ConfigurationUtil.getString("wx_secret") + "&code=" + code +
                "&grant_type=authorization_code";
        JSONObject jsonObject = WxHttpUtil.httpRequest(tokenUrl, "GET", null);
        return jsonObject;
    }

    /**
     * 通过redirectUri，不弹出授权页面，直接跳转，只能获取用户openid
     */
    public static String getOauth2ByRedirectUri(String redirectUri) {
        String tokenUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                ConfigurationUtil.getString("wx_pay_appid") + "&redirect_uri=" + redirectUri +
                "&response_type=code&scope=snsapi_base&state=" + CommonUtil.CreateNoncestr() +
                "#wechat_redirect";
        return tokenUrl;
    }

    /**
     * 是否财付通签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @return boolean
     */
    public static boolean isTenpaySign(Map<String, String> reqMap, String keyVal) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry:reqMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!"sign".equals(key) && !StringUtil.isEmpty(value)) {
                sb.append(key).append('=').append(value).append('&');
            }
        }
        sb.append("key=").append(keyVal);
        return MD5Util
                .MD5Encode(sb.toString(), ConfigurationUtil.getString("ten_pay_input_charset"))
                .toLowerCase().equals(reqMap.get("sign").toLowerCase());
    }

    // //////////////////////////财付通/////////////////////////////////////////

    // //////////////////////////时代支付/////////////////////////////////////////

    /**
     * 是否时代通签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @return boolean
     */
    public static boolean isSdPaySign(String reqString, String signMsg) {
        return MD5Util.MD5Encode(reqString, ConfigurationUtil.getString("sd_input_charset"))
                .toUpperCase().equals(signMsg);
    }

    /**
     * 构建时代请求表单
     */
    public static String buildSdPayRequest(Map<String, String> reqMap, String action, String method,
                                           String btnName) {
        List<String> keys = new ArrayList<String>(reqMap.keySet());
        StringBuilder sbHtml = new StringBuilder();
        sbHtml.append("<form id=\"sdpaysubmit\" name=\"sdpaysubmit\" action=\"" + action +
                "\" method=\"" + method + "\">");
        for (int i = 0; i < keys.size(); i++) {
            String name = keys.get(i);
            String value = reqMap.get(name);
            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        // submit按钮控件无name属性
        sbHtml.append(
                "<input type=\"submit\" value=\"" + btnName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['sdpaysubmit'].submit();</script>");
        return sbHtml.toString();
    }

    // //////////////////////////时代支付/////////////////////////////////////////

    // //////////////////////////康美通/////////////////////////////////////////

    /**
     * 构建时代请求表单
     *
     * @param reqMap  请求参数
     * @param action  请求action
     * @param method  请求方式post或get
     * @param btnName 确认按钮名称
     */
    public static String buildKMTPayRequest(Map<String, String> reqMap, String action,
                                            String method, String btnName) {
        Map<String, String> sPara = buildRequestParaForKMT(reqMap,
                ConfigurationUtil.getString("kmt_sign_type"));
        StringBuilder sbHtml = new StringBuilder();
        sbHtml.append("<form id=\"kmtpaysubmit\" name=\"kmtpaysubmit\" action=\"").append(action)
                .append("\" method=\"").append(method).append("\">");
        for (Map.Entry<String, String> entry:sPara.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            sbHtml.append("<input type=\"hidden\" name=\"").append(name).append("\" value=\"")
                    .append(value).append("\"/>");
        }
        sbHtml.append("<input type=\"submit\" value=\"").append(btnName)
                .append("\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['kmtpaysubmit'].submit();</script>");
        return sbHtml.toString();
    }

    /**
     * 生成要请求给康美通的参数数组
     */
    public static Map<String, String> buildRequestParaForKMT(Map<String, String> sParaTemp,
                                                             String signType) {
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        String linkStr = simpleCreateLinkString(sPara);
        sPara.put("sign", RSA.sign(linkStr, ConfigurationUtil.getString("kmt_private_key"),
                sPara.get("inputCharset")));
        sPara.put("signType", signType);
        return sPara;
    }

    /**
     * 简单参数拼接
     */
    public static String simpleCreateLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        StringBuilder prestr = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            prestr.append(key).append('=').append(params.get(key));
        }
        return prestr.toString();
    }

    /**
     * 是否是康美通返回
     */
    public static boolean verifyIsKMTPay(Map<String, String> params) {
        String sign = params.get("sign");
        sign = null == sign ? "" : sign;
        params.remove("signType");
        params.remove("wap");
        params.remove("app");
        Map<String, String> sParaNew = AlipayCore.paraFilter(params);
        if ("RSA".equals(ConfigurationUtil.getString("kmt_sign_type"))) {
            String preSignStr = simpleCreateLinkString(sParaNew);
            log.info("康美通RAS验签字符串:{}",preSignStr);
            return RSA.verify(preSignStr, sign, ConfigurationUtil.getString("kmt_public_key"),
                    ConfigurationUtil.getString("kmt_input_charset"));
        }
        return false;
    }

    /**
     * 生成康美通请求
     */
    public static String getKMTPayRequestURL(String url, Map<String, String> reqMap) {
        Map<String, String> sPara = KMTParaFilter(reqMap);
        if ("RSA".equals(ConfigurationUtil.getString("kmt_sign_type"))) {
            String linkStr = simpleCreateLinkString(sPara);// 仅是UTF-8才可用
            reqMap.put("sign", RSA.sign(linkStr, ConfigurationUtil.getString("kmt_private_key"),
                    sPara.get("inputCharset")));
        }
        StringBuilder sb = new StringBuilder();
        Iterator<String> keysIt = reqMap.keySet().iterator();
        while (keysIt.hasNext()) {
            String key = keysIt.next();
            sb.append(key + "=" + reqMap.get(key) + "&");
        }
        return url + "?" + sb.substring(0, sb.lastIndexOf("&"));
    }

    /**
     * 生成康美通排序Map
     */
    public static Map KMTParaFilter(Map sArray) {
        Map result = new HashMap();
        if (sArray == null || sArray.size() <= 0) return result;
        for (Iterator iterator = sArray.keySet().iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            String value = (String) sArray.get(key);
            if (value != null && !value.equals("") && !key.equalsIgnoreCase("sign") &&
                    !key.equalsIgnoreCase("signType")) {
                result.put(key, value);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        HashMap<String, String> reqMap = new HashMap<>();
        reqMap.put("key1", "value1");
        reqMap.put("key2", "value2");
        String join = StringUtils.join(reqMap.entrySet().stream()
                        .map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.toList()),
                "&");
        System.out.println(join);
    }

    // //////////////////////////康美通/////////////////////////////////////////
}
