package com.kmzyc.order.remote;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.vobject.PaymentVO;

@SuppressWarnings("unchecked")
public interface OrderPayRemoteService extends Serializable {
  // /**
  // * 订单支付接口
  // * @param operator 操作人
  // * @param paymentWay 支付方式
  // * @param account 客户账号id
  // * @param orderCode 订单号
  // * @param orderMoney 付款金额
  // * @param outsidePayStatementNo 第三方支付流水号
  // * @param flag 付款/退款标识
  // * @param preferentialNo 优惠券编号
  // * @param bankName 支付银行名称
  // * @param platFormName 第三方支付平台名称
  // * @param bankCode 支付银行代码
  // * @param platFormCode 支付平台代码
  // * @return
  // * @throws ServiceException
  // */
  // public int pay(String operator,Long paymentWay, String account,
  // String orderCode, BigDecimal orderMoney,
  // String outsidePayStatementNo, Long flag,
  // BigDecimal preferentialNo,String bankName,String platFormName,
  // String bankCode, String platFormCode) throws ServiceException;

  /**
   * 获取各种支付方式的支付总额
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public BigDecimal getOrderPay(Map map) throws ServiceException;

  /**
   * 获取未付款金额
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public BigDecimal getNotPay(String orderCode) throws ServiceException;

  /**
   * 批量处理支付 用于组合支付场景：如有优惠券支付，余额支付一起提交
   * 
   * @param paymentVOList
   * @return
   * @throws ServiceException
   */
  public int batchPay(PaymentVO paymentVO) throws ServiceException;

  /**
   * 批量保存订单支付流水
   * 
   * @param paymentVOList
   * @return 1为成功，其他为失败
   * @throws ServiceException
   */
  public int savePayStatement(List<OrderPayStatement> orderPayStatementList)
      throws ServiceException;
  
   /**
   * 预售订单支付定金回调方法调用
   * 添加支付流水，操作流水，修改订单状态
   * TODO 描述这个方法的作用<br/> 
   * @author KM 
   * @param paymentVO
   * @return
   * @throws ServiceException
   */
  public int orderRemotePayForYsDeposit(PaymentVO paymentVO) throws ServiceException;

}
