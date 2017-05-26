package com.kmzyc.order.remote;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderCarry;

/**
 * 订单结转
 * 
 * @author xlg
 * 
 */
@SuppressWarnings("unchecked")
public interface OrderExecuteRemoteService extends Serializable {

  /**
   * 查询订单并结转
   * 
   * @param paramsMap
   * @return
   * @throws ServiceException
   */
  public List<OrderCarry> orderExecute(Map paramsMap) throws ServiceException;
}
