package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderCancelReason;

public interface OrderCancelService {

  public int saveOrderCancelReason(OrderCancelReason reason) throws ServiceException;;
}
