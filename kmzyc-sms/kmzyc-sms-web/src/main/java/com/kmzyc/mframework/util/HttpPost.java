package com.kmzyc.mframework.util;

// package com.kmzyc.mframework.util;
//
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.methods.RequestEntity;
//import org.apache.log4j.Logger;
//
//public class HttpPost {
//
//    private static HttpClient httpclient = null;
//    private static HashMap map = new HashMap();
//    private static Object object = new Object();
//
//    private String postURL = null;
//
//    private Logger logger = Logger.getLogger(HttpPost.class);
//
//    private HttpPost() {}
//
//    public static HttpPost getInstance(String strURL) {
//        HttpPost instance = null;
//        if ((instance = (HttpPost) map.get(strURL)) == null) {
//            synchronized (object) {
//                if ((instance = (HttpPost) map.get(strURL)) == null) {
//                    if (httpclient == null) {
//                        MultiThreadedHttpConnectionManager connectionManager =
//                                new MultiThreadedHttpConnectionManager();
//                        httpclient = new HttpClient(connectionManager);
//                    }
//                    instance = new HttpPost();
//                    instance.postURL = strURL;
//                    map.put(strURL, instance);
//                }
//            }
//        }
//        return instance;
//    }
//
//    public String post(Map m) throws Exception {
//        return post(m, "application/x-www-form-urlencoded;charset=GBK", "Mozilla/4.0");
//    }
//
//    public String post(Map m, String contentType, String userAgent) throws Exception {
//        return post(m, contentType, userAgent, 0);
//    }
//
//    public String post(Map m, String contentType, String userAgent, int timeout) throws Exception {
//        if (0 <= timeout) {
//            httpclient.getHttpConnectionManager().getParams().setSoTimeout(timeout);
//        } else {
//            httpclient.getHttpConnectionManager().getParams().setSoTimeout(0);
//        }
//        PostMethod post = new PostMethod(postURL);
//
//        post.addRequestHeader("Content-Type", contentType);
//        post.addRequestHeader("User-Agent", userAgent);
//
//        NameValuePair valuePairs[] = new NameValuePair[m.size()];
//        @SuppressWarnings("rawtypes")
//        java.util.Collection colKeys = m.entrySet();
//        @SuppressWarnings("rawtypes")
//        java.util.Iterator itKeys = colKeys.iterator();
//
//        int i = 0;
//        StringBuffer data = new StringBuffer();
//        while (itKeys.hasNext()) {
//            @SuppressWarnings("rawtypes")
//            String strKey = ((Map.Entry) itKeys.next()).getKey().toString();
//            valuePairs[i++] = new NameValuePair(strKey, (String) m.get(strKey));
//
//            data.append("&" + strKey + "=" + URLEncoder.encode((String) m.get(strKey)));
//        }
//        post.setRequestBody(valuePairs);
//
//        httpclient.executeMethod(post);
//        String charSet = post.getResponseCharSet();
//        return (new String(post.getResponseBodyAsString().getBytes(charSet), "GBK"));
//
//    }
//
//    public String postSignXml(String xmlMsg, String contentType, String userAgent) {
//
//        PostMethod post = new PostMethod(postURL);
//
//        post.addRequestHeader("Content-Type", contentType);
//        post.addRequestHeader("User-Agent", userAgent);
//        post.addRequestHeader("XML", "directclient");
//        post.addRequestHeader("BOCB2E", "directclient");
//        post.addRequestHeader("BOCB2E-XML", "block");
//
//        java.io.InputStream input = null;
//
//        try {
//            input = new java.io.ByteArrayInputStream(xmlMsg.getBytes("UTF-8"));
//        } catch (java.io.UnsupportedEncodingException e) {}
//
//        RequestEntity entity = new InputStreamRequestEntity(input, "text/xml; charset=ISO-8859-1");
//        post.setRequestEntity(entity);
//        try {
//            httpclient.executeMethod(post);
//            String charSet = post.getResponseCharSet();
//            return (new String(post.getResponseBodyAsString().getBytes(charSet), "GBK"));
//        } catch (java.io.IOException e) {
//            logger.error("发送短信出错：" + e.getMessage(), e);
//        }
//        return null;
//    }
//
//}
