package com.pltfm.app.remote.impl;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderCancelRemoteService;
import com.pltfm.app.entities.OrderCancelReason;
import com.pltfm.app.service.OrderCancelService;

@Service("orderCancelRemoteService")
@Scope("singleton")
public class OrderCancelRemoteServiceImpl implements OrderCancelRemoteService {
  @Resource
  OrderCancelService orderCancelService;

  @Override
  public int saveOrderCancelReason(OrderCancelReason reason) {
    int result = 0;
    try {
      result = orderCancelService.saveOrderCancelReason(reason);
    } catch (ServiceException e) {
      result = 1;
      e.printStackTrace();
    }
    return result;
  }

}
