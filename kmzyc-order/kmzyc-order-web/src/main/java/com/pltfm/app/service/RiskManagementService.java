package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;

/**
 * 风控
 * 
 * @author xlg
 * 
 */
public interface RiskManagementService {
  /**
   * 新增订单风控判断任务
   * 
   * @param commerceId 商家ID
   * @param orderCode 订单
   * @throws ServiceException
   */
  public boolean addRiskJudgeTask(String commerceId,String commerceName, String orderCode) throws ServiceException;

  /**
   * 执行订单风控
   * 
   * @param orderCode
   * @throws ServiceException
   */
  public void execRiskJudge(String orderCode) throws ServiceException;
}
