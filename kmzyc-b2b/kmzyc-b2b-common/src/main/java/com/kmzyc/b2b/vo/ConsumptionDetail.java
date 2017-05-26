package com.kmzyc.b2b.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ConsumptionDetail {

  /**
   * 订单编码，作为对外展示和调用
   */
  private String orderCode;

  /**
   * 订单支付完成的时间
   */
  private Date payDate;

  /**
   * 应付金额=商品金额+运费-优惠金额
   */
  private BigDecimal amountPayable;

  /**
   * 优惠劵支付金额
   */
  private BigDecimal preferentialPay;

  /**
   * 余额支付金额
   */
  private BigDecimal balancePay;

  /**
   * 其它支付金额（网银+信用卡+支付平台）
   */
  private BigDecimal otherPay;

  /**
   * 退货返还金额
   */
  private BigDecimal returnSum;

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public Date getPayDate() {
    return payDate;
  }

  public void setPayDate(Date payDate) {
    this.payDate = payDate;
  }

  public BigDecimal getAmountPayable() {
    return amountPayable;
  }

  public void setAmountPayable(BigDecimal amountPayable) {
    this.amountPayable = amountPayable;
  }

  public BigDecimal getPreferentialPay() {
    return preferentialPay;
  }

  public void setPreferentialPay(BigDecimal preferentialPay) {
    this.preferentialPay = preferentialPay;
  }

  public BigDecimal getBalancePay() {
    return balancePay;
  }

  public void setBalancePay(BigDecimal balancePay) {
    this.balancePay = balancePay;
  }

  public BigDecimal getOtherPay() {
    return otherPay;
  }

  public void setOtherPay(BigDecimal otherPay) {
    this.otherPay = otherPay;
  }

  public BigDecimal getReturnSum() {
    return returnSum;
  }

  public void setReturnSum(BigDecimal returnSum) {
    this.returnSum = returnSum;
  }

  @Override
  public String toString() {
    return "ConsumptionDetail [orderCode=" + orderCode + ", payDate=" + payDate
        + ", amountPayable=" + amountPayable + ", preferentialPay=" + preferentialPay
        + ", balancePay=" + balancePay + ", otherPay=" + otherPay + ", returnSum=" + returnSum
        + "]";
  }

}
