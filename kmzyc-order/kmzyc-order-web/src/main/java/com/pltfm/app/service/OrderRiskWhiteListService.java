package com.pltfm.app.service;

import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.entities.OrderRiskBackList;

public interface OrderRiskWhiteListService {
  /**
   * 获取分控黑名单
   * 
   * @return
   */
  public Page backListByPage(Map<String, Object> paramMap, Page page) throws ServiceException;

  /**
   * 添加黑名单
   * 
   * @param back
   * @throws ServiceException
   */
  public void save(OrderRiskBackList back) throws ServiceException;
}
