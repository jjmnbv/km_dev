package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderMain;

public interface InvoiceService {

  /*
   * 根据订单号生成订单发票及发票明细
   */
  public Long save(OrderMain om) throws ServiceException;

}
