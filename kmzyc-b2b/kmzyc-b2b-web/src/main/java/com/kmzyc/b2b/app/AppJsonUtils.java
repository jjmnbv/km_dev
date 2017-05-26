package com.kmzyc.b2b.app;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.framework.util.Base64Util;

public class AppJsonUtils {

  // private static final Logger logger = Logger.getLogger(AppJsonUtils.class);
  private static Logger logger = LoggerFactory.getLogger(AppJsonUtils.class);

  /**
   * 获取json对象
   * 
   * @param request
   * @return
   */
  public static JSONObject getJsonObject(HttpServletRequest request) {
    JSONObject json = null;
    String ip = null;
    try {
      ip = request.getRemoteAddr();
      String jsonStr = request.getParameter("parameters");
      if (null != jsonStr && jsonStr.length() > 0) {
        json = JSONObject.parseObject(URLDecoder.decode(Base64Util.decode(jsonStr), "UTF-8"));
      } else {
        logger.error(ip + "请求手机接口无参数");
      }
    } catch (Exception e) {
      logger.error(ip + "请求手机接口数据错误：" + e.getMessage(), e);
    }
    return json;
  }
}
