package com.kmzyc.b2b.util;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.b2b.util.pay.XmlConverUtil;

@SuppressWarnings("unchecked")
public class HttpUtils {

  static Logger logger= LoggerFactory.getLogger(HttpUtils.class);
  private static final String URL_PARAM_CONNECT_FLAG = "&";
  private static final int SIZE = 1024 * 1024;

  private HttpUtils() {}

  /**
   * GET METHOD
   * 
   * @param strUrl String
   * @param map Map
   * @throws IOException
   * @return List
   */
  public static List URLGet(String strUrl, Map map) throws IOException {
    String strtTotalURL = "";
    List result = new ArrayList();
    if (strtTotalURL.indexOf("?") == -1) {
      strtTotalURL = strUrl + "?" + getUrl(map);
    } else {
      strtTotalURL = strUrl + "&" + getUrl(map);
    }
    URL url = new URL(strtTotalURL);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setUseCaches(false);
    HttpURLConnection.setFollowRedirects(true);
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()), SIZE);
    String str = null;
    while (null != (str = in.readLine())) {
      result.add(str);
    }
    in.close();
    con.disconnect();
    return (result);
  }

  /**
   * GET METHOD
   * 
   * @param strUrl String
   * @param charset Map
   * @throws IOException
   * @return List
   */
  public static List URLGet(String strUrl, String charset) throws IOException {
    List<String> result = new ArrayList();
    URL url = new URL(strUrl);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setUseCaches(false);
    if (null == charset) {
      charset = "GBK";
    }
    con.setRequestProperty("contentType", charset);
    HttpURLConnection.setFollowRedirects(true);
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()), SIZE);
    String str = null;
    while (null != (str = in.readLine())) {
      result.add(URLDecoder.decode(str, charset));
    }
    in.close();
    con.disconnect();
    return (result);
  }

  /**
   * POST METHOD
   * 
   * @param strUrl String
   * @param charset Map
   * @throws IOException
   * @return List
   */
  public static List URLPost(String strUrl, Map map, String charset) throws IOException {
    String content = "";
    content = XmlConverUtil.maptoXml(map);
    URL url = new URL(strUrl);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setDoInput(true);
    con.setDoOutput(true);
    con.setAllowUserInteraction(false);
    con.setUseCaches(false);
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
    BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
    bout.write(content);
    bout.flush();
    bout.close();
    BufferedReader bin = new BufferedReader(new InputStreamReader(con.getInputStream()), SIZE);
    List result = new ArrayList();
    String str = null;
    while (null != (str = bin.readLine())) {
      result.add(str);
    }
    bin.close();
    con.disconnect();
    return (result);
  }

  /**
   * 生成URL
   * 
   * @param map
   * @return
   */
  private static String getUrl(Map map) {
    if (null == map || map.keySet().size() == 0) {
      return ("");
    }
    StringBuilder url = new StringBuilder();
    Set keys = map.keySet();
    for (Iterator i = keys.iterator(); i.hasNext();) {
      String key = String.valueOf(i.next());
      if (map.containsKey(key)) {
        Object val = map.get(key);
        String str = val != null ? val.toString() : "";
        try {
          str = URLEncoder.encode(str, "GBK");
        } catch (UnsupportedEncodingException e) {
          logger.error(e.getMessage(),e);
        }
        url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
      }
    }
    String strURL = "";
    strURL = url.toString();
    if (URL_PARAM_CONNECT_FLAG.equals("" + strURL.charAt(strURL.length() - 1))) {
      strURL = strURL.substring(0, strURL.length() - 1);
    }
    return (strURL);
  }

  /**
   * 获取客户端IP
   * 
   * @param request
   * @return
   */
  public static String getIP(HttpServletRequest request) {
    String ipAddress = null;
    ipAddress = request.getHeader("x-forwarded-for");
    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getRemoteAddr();
      if (ipAddress.equals("127.0.0.1")) {
        InetAddress inet = null;
        try {
          inet = InetAddress.getLocalHost();
        } catch (Exception e) {}
        ipAddress = inet.getHostAddress();
      }
    }
    if (ipAddress != null && ipAddress.indexOf(",") > 0) {
      ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
    }
    return ipAddress;
  }

}
