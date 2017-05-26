package com.pltfm.app.service;

import com.kmzyc.b2b.model.RefundResult;
import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.entities.OrderMain;

public interface RefundMentService {

  /*
   * 取消中
   */
  void cancelOrderReady(String operator, OrderMain om) throws ServiceException;

  /*
   * 退款中
   */
  void refundMentReady(OrderMain om, OrderAlter oa) throws ServiceException;

  /*
   * 退款后
   */
  void refundMentDone(RefundResult refundResult) throws ServiceException;

}
