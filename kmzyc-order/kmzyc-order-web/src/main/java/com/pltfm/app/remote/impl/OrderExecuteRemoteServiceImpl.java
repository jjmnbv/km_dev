package com.pltfm.app.remote.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderExecuteRemoteService;
import com.pltfm.app.entities.OrderCarry;
import com.pltfm.app.service.OrderExecuteService;

/**
 * 订单结转
 * 
 * @author xlg
 * 
 */
@Service("orderExecuteRemoteService")
@SuppressWarnings("unchecked")
public class OrderExecuteRemoteServiceImpl implements OrderExecuteRemoteService {
  private static final long serialVersionUID = 1L;
  private static final Logger log = Logger.getLogger(OrderExecuteRemoteServiceImpl.class);
  @Resource
  private OrderExecuteService orderExecuteService;

  /**
   * 查询订单并结转
   * 
   * @param paramsMap
   * @return
   * @throws ServiceException
   */
  @Override
public List<OrderCarry> orderExecute(Map paramsMap) throws ServiceException {
    List<OrderCarry> list = null;
    if (null != paramsMap && !paramsMap.isEmpty() && null != paramsMap.get("commerceId")) {
      try {
        list = new ArrayList<OrderCarry>();
        String commerceId = paramsMap.get("commerceId").toString();
        paramsMap.put("operator", commerceId);
        list.add(orderExecuteService.OrderExecuteEntrance(commerceId, paramsMap));
      } catch (Exception e) {
        log.error("发生错误", e);
      }
    }
    return list;
  }
}
