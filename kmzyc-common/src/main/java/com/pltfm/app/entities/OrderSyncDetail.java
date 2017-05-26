package com.pltfm.app.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderSyncDetail implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 同步ID
   */
  private Long syncId;
  /**
   * 订单编号
   */
  private String orderCode;
  /**
   * 外部编号
   */
  private String outCode;
  /**
   * 同步标识
   */
  private Short syncFlag;
  /**
   * 同步日期
   */
  private Date syncDate;

  /**
   * 用户
   */
  private String customerAccount;
  /**
   * 订单类型
   */
  private Long orderType;
  /**
   * 总额
   */
  private BigDecimal commoditySum;
  /**
   * 订单状态
   */
  private Long orderStatus;
  /**
   * 支付日期
   */
  private Date payDate;
  /**
   * 完成日期
   */
  private Date finishDate;
  /**
   * 订单状态
   * 
   * @return
   */
  private String orderStatusStr;
  /**
   * 同步PV
   */
  private BigDecimal syncPv;
  /**
   *pv值
   * 
   */
  private BigDecimal orderPv;

  /**
   * 同步收益
   */
  private BigDecimal syncProfit;
  /**
   * 订单收益
   */
  private BigDecimal orderProfit;

  public Long getSyncId() {
    return syncId;
  }

  public void setSyncId(Long syncId) {
    this.syncId = syncId;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getOutCode() {
    return outCode;
  }

  public void setOutCode(String outCode) {
    this.outCode = outCode;
  }

  public Short getSyncFlag() {
    return syncFlag;
  }

  public void setSyncFlag(Short syncFlag) {
    this.syncFlag = syncFlag;
  }

  public Date getSyncDate() {
    return syncDate;
  }

  public void setSyncDate(Date syncDate) {
    this.syncDate = syncDate;
  }

  public Long getOrderType() {
    return orderType;
  }

  public void setOrderType(Long orderType) {
    this.orderType = orderType;
  }

  public BigDecimal getCommoditySum() {
    return commoditySum;
  }

  public void setCommoditySum(BigDecimal commoditySum) {
    this.commoditySum = commoditySum;
  }

  public Long getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(Long orderStatus) {
    this.orderStatus = orderStatus;
  }

  public Date getPayDate() {
    return payDate;
  }

  public void setPayDate(Date payDate) {
    this.payDate = payDate;
  }

  public String getCustomerAccount() {
    return customerAccount;
  }

  public void setCustomerAccount(String customerAccount) {
    this.customerAccount = customerAccount;
  }

  public String getOrderStatusStr() {
    return orderStatusStr;
  }

  public void setOrderStatusStr(String orderStatusStr) {
    this.orderStatusStr = orderStatusStr;
  }

  public BigDecimal getOrderPv() {
    return orderPv;
  }

  public void setOrderPv(BigDecimal orderPv) {
    this.orderPv = orderPv;
  }

  public BigDecimal getOrderProfit() {
    return orderProfit;
  }

  public void setOrderProfit(BigDecimal orderProfit) {
    this.orderProfit = orderProfit;
  }

  public Date getFinishDate() {
    return finishDate;
  }

  public void setFinishDate(Date finishDate) {
    this.finishDate = finishDate;
  }

  public BigDecimal getSyncPv() {
    return syncPv;
  }

  public void setSyncPv(BigDecimal syncPv) {
    this.syncPv = syncPv;
  }

  public BigDecimal getSyncProfit() {
    return syncProfit;
  }

  public void setSyncProfit(BigDecimal syncProfit) {
    this.syncProfit = syncProfit;
  }
}
