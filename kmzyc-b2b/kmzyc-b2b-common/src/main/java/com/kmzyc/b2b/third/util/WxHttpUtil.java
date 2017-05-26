package com.kmzyc.b2b.third.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.zkconfig.ConfigurationUtil;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 微信http工具类
 * 
 * @author Administrator 2014-05-12
 * 
 */
public class WxHttpUtil {

    private static final Logger log = LoggerFactory.getLogger(WxHttpUtil.class);

    /**
     * 用于主动发送http请求(可用来发送客服消息,以及获取access_token,以及查询或创建菜单)
     * 
     * @param requestUrl
     * @param requestMethod
     * @param outputStr
     * @return
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuilder buffer = new StringBuilder();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new X509TrustManager() {

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {}

                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {}
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }

    /**
     * 通用的获取access_token凭据(非授权的acccess_token)
     * 
     * @return
     */
    public static String getAccessToken() {
        String requestUrl =
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                        + ConfigurationUtil.getString("wx_appid") + "&secret="
                        + ConfigurationUtil.getString("wx_secret").trim();
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                return jsonObject.getString("access_token");

            } catch (JSONException e) {
                log.error(e.getMessage(),e);
            }
        }
        return null;
    }

    /**
     * 给与当前微信服务号在24小时以内有过交互的用户发送文本消息
     * 
     * @param openId
     * @param content
     * @return
     */
    public static void sendTextMsgToCustomer(String openId, String content) {
        String requestUrl =
                "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
                        + getAccessToken();
        JSONObject postData =
                JSONObject.fromObject("{'touser':'" + openId
                        + "','msgtype':'text','text':{'content':'" + content + "'}}");
        JSONObject returnJson = httpRequest(requestUrl, "POST", postData.toString());
        log.info("发消息返回的结果=========>" + returnJson);
    }

}
