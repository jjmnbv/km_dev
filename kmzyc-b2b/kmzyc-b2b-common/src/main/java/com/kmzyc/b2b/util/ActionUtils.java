package com.kmzyc.b2b.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.framework.constants.Constants;

@SuppressWarnings("unchecked")
public class ActionUtils {

  /**
   * 此类用于Action类中常用方法集成
   */

  /**
   * 用于从前台获取查询条件，并将查询条件放置于Map形式的查询条件集合中 并将非空的查询条件放入request中
   * 
   * @param request
   * @param map
   * @param strs jsp页面查询条件name值数组
   */
  public static void putValueIntoMap(HttpServletRequest request, Map map, String[] strs) {
    if (null == strs) {
      return;
    }
    for (String s : strs) {
      String str = request.getParameter(s);
      if (StringUtils.isNotBlank(str)) {
        map.put(s, str.trim());
        request.setAttribute(s, str);
      }
    }
  }

  /**
   * APP接口获取参数后封装参数数据返回userinfo
   * 
   * @param jsonParams
   * @param resultMap
   */
  public static void putValue2UserInfo4App(JSONObject jsonParams, Map resultMap) {
    Map map = new HashMap();
    if (null != jsonParams) {
      for (String key : jsonParams.keySet()) {
        if (key.equalsIgnoreCase("openid") || key.equalsIgnoreCase("acctType")) continue;
        map.put(key, jsonParams.getString(key));
      }
    }
    resultMap.put("userinfo", map);
  }


  public static String getAppType(HttpServletRequest request) {
    String UA = request.getHeader("user-agent").toUpperCase();
    String androidString = "ANDROID";
    String appType = null;
    if (UA.indexOf(androidString) != -1 || UA.contains("LINUX")) {
      appType = Constants.APP_TYPE_ANDROID;
    } else if (UA.indexOf("LIKE MAC OS X") != -1 || UA.matches(".*((IOS)|(iPAD)).*(IPH).*")) {
      appType = Constants.APP_TYPE_IOS;
    }
    return appType;
  }
}
