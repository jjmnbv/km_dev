package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderRiskScore;

public interface OrderRiskScoreService {
  /**
   * 查询判断得分
   * 
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public List<OrderRiskScore> queryOrderRiskScore(String orderCode) throws ServiceException;
}
