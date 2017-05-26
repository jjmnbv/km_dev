package com.pltfm.app.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderSync implements Serializable {

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
  private int syncFlag;
  /**
   * 同步日期
   */
  private Date syncDate;

  /**
   * 订单pv
   */
  private BigDecimal orderPv;

  /**
   *收益金额
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

  public int getSyncFlag() {
    return syncFlag;
  }

  public void setSyncFlag(int syncFlag) {
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
