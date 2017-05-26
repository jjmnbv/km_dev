package com.kmzyc.express.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class ExpressHttpRequestUtil {
  // 连接等待超时
  private static final int CONN_TIME_OUT_MILLISECOND = 4 * 1000;
  // 数据等待超时
  private static final int SO_TIME_OUT_MILLISECOND = 6 * 1000;
  // 最大请求连接数
  private static final int CONN_MAX_TOTAL_NUM = 200;
  // 单host最大请求连接数
  private static final int CONN_MAX_PER_HOST_NUM = 200;
  // 设置连接关闭等待的最大时间120秒
  private static final int LINGER_TIME_OUT_SECOND = 120;

  // 连接池
  private final static HttpConnectionManager connManager;
  private final static HttpClient httpClient;

  static {
    connManager = new MultiThreadedHttpConnectionManager();
    HttpConnectionManagerParams params = connManager.getParams();
    params.setConnectionTimeout(CONN_TIME_OUT_MILLISECOND);
    params.setSoTimeout(SO_TIME_OUT_MILLISECOND);
    params.setMaxTotalConnections(CONN_MAX_TOTAL_NUM);
    params.setDefaultMaxConnectionsPerHost(CONN_MAX_PER_HOST_NUM);
    params.setLinger(LINGER_TIME_OUT_SECOND);
    httpClient = new HttpClient(connManager);
    // client.getHostConfiguration().setHost(url, 80, "http");
  }

  public static String addUrl(String head, String tail) {
    if (head.endsWith("/")) {
      if (tail.startsWith("/")) {
        return head.substring(0, head.length() - 1) + tail;
      } else {
        return head + tail;
      }
    } else {
      if (tail.startsWith("/")) {
        return head + tail;
      } else {
        return head + "/" + tail;
      }
    }
  }

  /**
   * post请求
   * 
   * @param url
   * @param params
   * @param codePage
   * @return
   * @throws Exception
   */
  public static String postData(String url, Map<String, String> params, String codePage)
      throws Exception {
    final PostMethod method = new PostMethod(url);
    if (params != null) {
      method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, codePage);
      method.setRequestBody(assembleRequestParams(params));
    }
    String result = "";
    try {
      httpClient.executeMethod(method);
      result = new String(method.getResponseBody(), codePage);
    } catch (Exception e) {
      throw e;
    } finally {
      method.releaseConnection();
    }
    return result;
  }

  /**
   * 无参get请求
   * 
   * @param url
   * @param codePage
   * @return
   * @throws Exception
   */
  public synchronized static String postData(String url, String codePage) throws Exception {
    final GetMethod method = new GetMethod(url);
    String result = "";
    try {
      httpClient.executeMethod(method);
      result = new String(method.getResponseBody(), codePage);
    } catch (Exception e) {
      throw e;
    } finally {
      method.releaseConnection();
    }
    return result;
  }

  /**
   * 组装http请求参数
   * 
   * @param params
   * @param menthod
   * @return
   */
  private synchronized static NameValuePair[] assembleRequestParams(Map<String, String> data) {
    final List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
    Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
      nameValueList.add(new NameValuePair((String) entry.getKey(), (String) entry.getValue()));
    }
    return nameValueList.toArray(new NameValuePair[nameValueList.size()]);
  }
}
