/**
 * project : 康美中药城商城 module : promotion-api package : com.kmzyc.promotion.app.util date:
 * 2016年9月23日上午11:17:58 Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.kmzyc.promotion.app.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * s使用httpclient发送post请求
 *
 * @author zhuyanling
 * @date 2016年9月23日 上午11:17:58
 * @version
 * @see
 */
public class HttpClientUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    public static String requestByPostMethod(String url, String params) {
        if (StringUtils.isEmpty(url)) {
            return "false";
        }
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        try {
            // 设置post参数
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("datas", params));
            httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == 200
                    || response.getStatusLine().getStatusCode() == 302) {
                return EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            log.error("调用post请求失败", e);
        } finally {
            httpclient.getConnectionManager().shutdown();
            httppost.abort();// 释放post请求
        }

        return "false";
    }

}
