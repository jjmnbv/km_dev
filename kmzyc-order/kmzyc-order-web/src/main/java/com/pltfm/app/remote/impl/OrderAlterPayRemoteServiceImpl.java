package com.pltfm.app.remote.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderAlterPayRemoteService;
import com.pltfm.app.service.OrderAlterPayStatementService;
import com.pltfm.sys.util.ErrorCode;

@Service("orderAlterPayRemoteService")
public class OrderAlterPayRemoteServiceImpl implements OrderAlterPayRemoteService {

  private static final long serialVersionUID = 1L;
  @Resource
  OrderAlterPayStatementService orderAlterPayService;

  @Override
  public int pay(String operator, Long paymentWay, String account, String alterCode,
      BigDecimal orderMoney, String outsidePayStatementNo, Long flag, BigDecimal preferentialNo)
      throws ServiceException {
    int result = 0;
    try {
      result =
          orderAlterPayService.pay(operator, paymentWay, account, alterCode, orderMoney,
              outsidePayStatementNo, flag, preferentialNo, 0, null);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_RETURNS_REFUND_ERROR, "退换货单退款发生错误："
          + e.getMessage());
    }
    return result;
  }

}
