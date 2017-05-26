package com.kmzyc.b2b.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderSync {

  private Long syncId;

  private String orderCode;

  private String outCode;

  private Short syncFlag;

  private Date syncDate;

  private BigDecimal orderPv;

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
}
