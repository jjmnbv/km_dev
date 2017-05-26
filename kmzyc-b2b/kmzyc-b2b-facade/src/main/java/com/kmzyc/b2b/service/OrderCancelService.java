package com.kmzyc.b2b.service;

public interface OrderCancelService {

  public int cancelOrder(String orderCode, String cancelReason, String ordertype,
      int TLOrderAmount, String TLOrderDatetime);

}
