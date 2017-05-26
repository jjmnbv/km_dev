package com.kmzyc.b2b.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.framework.exception.ServiceException;

public interface Live800Service {

  public long getUserId(String userName) throws ServiceException;

  /**
   * 获取用户信息（基本信息与收获地地址信息）
   * 
   * @param userName 登录用户名
   * @return
   */
  public Map<String, Object> getCustomInfo(long userId) throws ServiceException;

  /**
   * 获取用户订单信息
   * 
   * @param args 用户ID
   * @return
   */
  public Map<String, Object> getCustomOrderInfo(Map<String, Object> args) throws ServiceException;

  /**
   * 获取用户退换货信息
   * 
   * @param args
   * @return
   */
  public List<Map<String, Object>> getCustomExchangeInfo(Map<String, Object> args)
      throws ServiceException;
}
