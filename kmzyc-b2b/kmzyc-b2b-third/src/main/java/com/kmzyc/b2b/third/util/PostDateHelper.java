package com.kmzyc.b2b.third.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kmzyc.b2b.model.WxPayOrderInfoResult;
import com.kmzyc.b2b.util.wxpay.CommonUtil;
import com.kmzyc.b2b.util.wxpay.MD5Util;
import com.kmzyc.b2b.util.wxpay.SDKRuntimeException;
import com.kmzyc.b2b.util.wxpay.Sha1Util;
import com.kmzyc.zkconfig.ConfigurationUtil;

import net.sf.json.JSONObject;

public class PostDateHelper {
    // private static Logger log = LoggerFactory.getLogger(PostDateHelper.class);
    private static Logger log = LoggerFactory.getLogger(PostDateHelper.class);

    // private String appid = ConfigurationUtil.getString("WX_APPID");
    private String timestamp = Long.toString(new Date().getTime() / 1000);
    // private String app_signature;
    private String sign_method = "sha1";
    private String out_trade_no;
    // private String partner = ConfigurationUtil.getString("WX_PARTNERID");
    private String AppKey = ConfigurationUtil.getString("WX_PARTNERKEY");

    public static WxPayOrderInfoResult checkorderIsPay(String orderCode) {
        String sendUrl =
                "https://api.weixin.qq.com/pay/orderquery?access_token=" + BaseUtil.getToken();
        PostDateHelper helper = new PostDateHelper();
        helper.setOut_trade_no(orderCode);

        JSONObject json;
        JSONObject orderJson;
        try {
            json = helper.getPostJsonDate();

            org.apache.http.HttpResponse httpResponse = postJson(sendUrl, json, "UTF-8");
            StringBuilder result = new StringBuilder();
            int httpCode = httpResponse.getStatusLine().getStatusCode();

            if (httpCode == HttpURLConnection.HTTP_OK) {
                HttpEntity entity = httpResponse.getEntity();

                // 读取服务器返回的json数据（接受json服务器数据）
                InputStream inputStream = entity.getContent();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);// 读字符串用的。

                String s;

                while (((s = reader.readLine()) != null)) {
                    result.append(s);
                    System.out.println(s);
                }

                reader.close();// 关闭输入流
                System.out.println(result);
                JSONObject jsonObject = JSONObject.fromObject(result);
                String orderInfo = jsonObject.getString("order_info");
                orderJson = JSONObject.fromObject(orderInfo);
                WxPayOrderInfoResult orderInfoResult =
                        (WxPayOrderInfoResult) JSONObject.toBean(orderJson,
                                WxPayOrderInfoResult.class);
                return orderInfoResult;
            }
        } catch (Exception e) {
            log.error("微信单笔订单" + orderCode + "查询出错：" + e.getMessage(),e);
            return null;
        }
        return null;
    }

    public JSONObject getPostJsonDate() throws Exception {
        String packageStr = this.getpackage();
        HashMap<String, String> objectMap = new HashMap<String, String>();
        objectMap.put("appid", ConfigurationUtil.getString("WX_APPID"));
        objectMap.put("package", packageStr);
        objectMap.put("timestamp", timestamp);
        objectMap.put("appkey", AppKey);

        String app_signature = getBizSign(objectMap);
        System.out.println("app_signature====" + app_signature);
        System.out.println("app_signature====" + getBizSign(objectMap));
        objectMap.put("app_signature", app_signature);
        objectMap.put("sign_method", sign_method);
        JSONObject json = JSONObject.fromObject(objectMap);
        System.out.println(json);
        return json;
    }

    private String getpackage() {
        String packagestr =
                "out_trade_no=" + out_trade_no + "&partner="
                        + ConfigurationUtil.getString("WX_PARTNERID") + "&sign=";
        String originalStr =
                "out_trade_no=" + out_trade_no + "&partner="
                        + ConfigurationUtil.getString("WX_PARTNERID") + "&key=" + AppKey;
        originalStr = MD5Util.MD5(originalStr).toUpperCase();
        packagestr += originalStr;
        return packagestr;
    }

    public String getsign(HashMap<String, String> bizObj) {
        String appid = bizObj.get("appid");
        String appkey = bizObj.get("appkey");
        String packageStr = bizObj.get("package");
        String timestamp = bizObj.get("timestamp");
        String s =
                "appid=" + appid + "&appkey=" + appkey + "&package=" + packageStr + "timestamp="
                        + timestamp;
        // s = CommonUtil.FormatBizQueryParaMap(s+"&key="+AppKey,false);
        System.out.println("s======" + s);
        return Sha1Util.sha1((s + "&key=" + AppKey).toLowerCase());
    }

    public String getBizSign(HashMap<String, String> bizObj) throws SDKRuntimeException {
        HashMap<String, String> bizParameters = new HashMap<String, String>();

        List<Map.Entry<String, String>> infoIds =
                new ArrayList<Map.Entry<String, String>>(bizObj.entrySet());

        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        for (int i = 0; i < infoIds.size(); i++) {
            Map.Entry<String, String> item = infoIds.get(i);
            if (!Strings.isNullOrEmpty(item.getKey())) {
                bizParameters.put(item.getKey().toLowerCase(), item.getValue());
            }
        }

        if (Strings.isNullOrEmpty(AppKey)) {
            throw new SDKRuntimeException("APPKEY为空！");
        }
        bizParameters.put("appkey", AppKey);
        String bizString = CommonUtil.FormatBizQueryParaMap(bizParameters, false);
        // System.out.println(bizString);
        System.out.println(bizString);
        return Sha1Util.sha1(bizString);

    }

    public static HttpResponse postJson(String url, JSONObject json, String charSet) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding(charSet);
            s.setContentType("application/json");
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

}
