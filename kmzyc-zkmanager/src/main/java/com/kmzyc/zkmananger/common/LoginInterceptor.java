package com.kmzyc.zkmananger.common;

import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.km.commons.config.core.ConfigurationUtil;

public class LoginInterceptor extends HandlerInterceptorAdapter {
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    HandlerMethod method = (HandlerMethod) handler;
    Method requestMethod = method.getMethod();
    Permission permission = requestMethod.getAnnotation(Permission.class);
    boolean pass = true;
    if (ConfigurationUtil.getBooleanValue("permission") && permission != null && permission.login()) {
      pass = request.getSession().getAttribute("loginName") != null;
    }
    request.getSession().setAttribute(
        "isAdmin",
        request.getSession().getAttribute("loginName") != null
            || ConfigurationUtil.getBooleanValue("permission") == false);
    if (pass) {
      return pass;
    }
    PrintWriter pw = null;
    try {
      JSONObject json = new JSONObject();
      json.put("code", "nonlogin");
      pw = response.getWriter();
      pw.print(json.toJSONString());
    } finally {
      if (pw != null) {
        pw.flush();
        pw.close();
      }
    }

    return pass;
  }
}
