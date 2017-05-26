package com.pltfm.app.service;

public interface MyOrderService {
  /**
   * 取消订单，分两种情况： 1.付款前； 2.付款后送货前，以及送货失败；
   * 
   * @param userId
   * @param orderMainCode
   * @param orderMainStatus
   * @throws Exception
   */
  public int cancelOrderMain(String userAccount, String orderMainCode, long orderMainStatus)
      throws Exception;
}
