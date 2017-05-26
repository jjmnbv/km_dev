package com.kmzyc.promotion.app.util;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 本地远程接口获取工具
 * 
 * @author hwf
 * 
 */
public class RemoteUtil {
  private static String serviceUrl = "http://10.2.20.17:8888/hessian/couponService";

  @SuppressWarnings("unchecked")
  public static <T> T getRemoteService(Class<?> api) throws MalformedURLException {
    HessianProxyFactory factory = new HessianProxyFactory();
    return (T) factory.create(api, serviceUrl);
  }

  @SuppressWarnings("unchecked")
  public static <T> T getRemoteService(Class<?> api, String url) throws MalformedURLException {
    HessianProxyFactory factory = new HessianProxyFactory();
    return (T) factory.create(api, url);
  }

  public static String getServiceUrl() {
    return serviceUrl;
  }

  public static void setServiceUrl(String serviceUrl) {
    RemoteUtil.serviceUrl = serviceUrl;
  }

}
