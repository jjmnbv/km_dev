package com.pltfm.app.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.OrderPayStatementVo;

/**
 * 退换货退款接口
 * 
 * @author Administrator
 * 
 */
public interface OrderAlterPayStatementService {

  /**
   * 退款 参数：操作人、支付方式、客户账号、退换货单号、付款金额、第三方支付流水号、付款/退款标识、优惠券编号
   */
  public int pay(String operator, Long paymentWay, String account, String alterCode,
      BigDecimal orderMoney, String string, Long flag, BigDecimal preferentialNo, int status,
      Long state) throws ServiceException;
  
  /**
   * 预售---退款 参数：操作人、支付方式、客户账号、退换货单号、付款金额、第三方支付流水号、付款/退款标识、优惠券编号
   */
  public int pay(String operator, Long paymentWay, String account, String alterCode,
      BigDecimal orderMoney, String string, Long flag, BigDecimal preferentialNo, int status,
      Long state,String ysFlag) throws ServiceException;

  public int pay(OrderPayStatementVo vo) throws ServiceException;

  public BigDecimal needToRefund(Map<String, Object> map) throws ServiceException;

  /**
   * 记录退换货单支付流水 参数：支付方式、支付状态、客户账号、退换货单号、付款金额、生成时间、支付完成时间、第三方支付流水号、付款/退款标识、优惠券编号
   */
  public void save(Long paymentWay, Long state, String account, String orderAlterCode,
      BigDecimal orderMoney, Date createDate, Date endDate, String outsidePayStatementNo,
      Long flag, BigDecimal preferentialNo) throws ServiceException;

  public void save(OrderPayStatementVo vo) throws ServiceException;
}
