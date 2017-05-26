package com.kmzyc.order.remote;

import com.kmzyc.b2b.model.RefundResult;
import com.kmzyc.commons.exception.ServiceException;

public interface RefundMentRemoteService {

  /*
   * 退款后
   */
  void refundMentDone(RefundResult refundResult) throws ServiceException;

  /**
   * 退款回调
   * 
   * @param refundResult退款结果
   * @param paltCode支付平台
   * @throws ServiceException
   */
  public void refundCallBack(RefundResult refundResult, Integer paltCode) throws ServiceException;
}
