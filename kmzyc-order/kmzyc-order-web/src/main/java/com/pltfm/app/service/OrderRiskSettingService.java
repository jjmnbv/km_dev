package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderRiskCondition;

/**
 * 风控设置
 * 
 * @author xlg
 * 
 */
public interface OrderRiskSettingService {
  /**
   * 保存风控设置
   * 
   * @return
   */
  public void saveOrderRiskSetting(List<OrderRiskCondition> list) throws ServiceException;

  /**
   * 查询风控设置
   * 
   * @return
   */
  public Map<String, OrderRiskCondition> queryOrderRiskCondition() throws ServiceException;

  /**
   * 查询风控设置
   * 
   * @return
   */
  public OrderRiskCondition queryOrderRisk(String key) throws ServiceException;

}
