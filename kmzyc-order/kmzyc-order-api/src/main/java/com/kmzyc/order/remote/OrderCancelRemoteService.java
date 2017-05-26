package com.kmzyc.order.remote;

import com.pltfm.app.entities.OrderCancelReason;

public interface OrderCancelRemoteService {

  public int saveOrderCancelReason(OrderCancelReason reason);
}
