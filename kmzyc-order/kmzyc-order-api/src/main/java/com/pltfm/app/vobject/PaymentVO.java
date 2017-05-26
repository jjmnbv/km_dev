package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.pltfm.app.entities.OrderPayStatement;

public class PaymentVO implements Serializable {

  private static final long serialVersionUID = 2824801643274125999L;
  /**
   * 订单支付接口入参对象
   * 
   * @param paymentWay 支付方式
   * @param account 客户账号id
   * @param orderCode 订单号
   * @param orderMoney 付款金额
   * @param flag 付款/退款标识 1:付款；2:退款
   * @param orderPayStatementList 支付对象列表
   * @return
   */
  private Long paymentWay;
  private String account;
  private String orderCode;
  private BigDecimal orderMoney;
  private Long flag;
  private Long logInId;
  private List<OrderPayStatement> orderPayStatementList;

  public Long getPaymentWay() {
    return paymentWay;
  }

  public void setPaymentWay(Long paymentWay) {
    this.paymentWay = paymentWay;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public BigDecimal getOrderMoney() {
    return orderMoney;
  }

  public void setOrderMoney(BigDecimal orderMoney) {
    this.orderMoney = orderMoney;
  }

  public Long getFlag() {
    return flag;
  }

  public void setFlag(Long flag) {
    this.flag = flag;
  }

  public List<OrderPayStatement> getOrderPayStatementList() {
    return orderPayStatementList;
  }

  public void setOrderPayStatementList(List<OrderPayStatement> orderPayStatementList) {
    this.orderPayStatementList = orderPayStatementList;
  }

  public Long getLogInId() {
    return logInId;
  }

  public void setLogInId(Long logInId) {
    this.logInId = logInId;
  }

}
