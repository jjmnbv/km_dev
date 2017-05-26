package com.kmzyc.b2b.service;

import com.kmzyc.b2b.model.PayCommonObject;
import com.kmzyc.framework.exception.ServiceException;

public interface OrderPayService {
  /**
   * 此方法为以下情况调用 1、余额+优惠券支付==订单金额 2 、从银行回来 如果使用余额+优惠券支付，只用调用orderPayRemoteService
   * .batchPay(paymentVO);的时候paymentVO中的list传入优惠券支付流水就可以了 如果从银行回来 调用orderPayRemoteService
   * .batchPay(paymentVO);的时候paymentVO中的list传入优惠券和银行支付流水
   * 
   * @param orderCode
   * @param loginId
   * @param payCommon
   * @return
   * @throws ServiceException
   */
  public Boolean orderRemotePay(String orderCode, Long loginId, PayCommonObject payCommon)
      throws ServiceException;
  
  /**
   * 预售订单支付定金添加支付流水,操作流水,更改订单状态
   *
   *
   * @author KM 
   * @param orderCode
   * @param loginId
   * @param payCommon
   * @return
   * @throws ServiceException
   */
  public Boolean orderRemotePayForYsDeposit(String orderCode, Long loginId, PayCommonObject payCommon)
      throws ServiceException;
}
