package com.kmzyc.order.remote;

import java.io.Serializable;
import java.math.BigDecimal;

import com.kmzyc.commons.exception.ServiceException;

/**
 * 退换货单退款接口
 * 
 * @author chenwei
 * @date 2013.09.10
 */
@SuppressWarnings("unused")
public interface OrderAlterPayRemoteService extends Serializable {
  /**
   * 退款
   * 
   * @param operator 操作人
   * @param paymentWay 支付方式
   * @param account 客户账号
   * @param alterCode 退换货单号
   * @param orderMoney 付款金额
   * @param outsidePayStatementNo 第三方支付流水号
   * @param flag 付款/退款标识
   * @param preferentialNo 优惠券编号
   * @return
   * @throws ServiceException 1 操作成功 -1 未知请求 -2 此状态下不能进行此操作 -3 不支持此退款方式 -4 调用远程接口异常 -5 数据查询异常 0
   *         账户金额修改失败 2 账户号不存在 3 账户金额小于订单交易金额 4 订单号不能为空 5 交易内容不能为空
   */
  public int pay(String operator, Long paymentWay, String account, String alterCode,
      BigDecimal orderMoney, String outsidePayStatementNo, Long flag, BigDecimal preferentialNo)
      throws ServiceException;

}
