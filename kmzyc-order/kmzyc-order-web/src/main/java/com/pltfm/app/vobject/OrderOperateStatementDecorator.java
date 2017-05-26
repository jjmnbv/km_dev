package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderOperateStatementDecorator implements Serializable {

  private static final long serialVersionUID = -6002088346445605665L;

  private String comment;
  private Date now;
  private Long nowOperateType;
  private String operator;
  private Long nowOrderStatus;
  private BigDecimal nowOrderMoney;
  private String orderCode;

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Date getNow() {
    return now;
  }

  public void setNow(Date now) {
    this.now = now;
  }

  public Long getNowOperateType() {
    return nowOperateType;
  }

  public void setNowOperateType(Long nowOperateType) {
    this.nowOperateType = nowOperateType;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public Long getNowOrderStatus() {
    return nowOrderStatus;
  }

  public void setNowOrderStatus(Long nowOrderStatus) {
    this.nowOrderStatus = nowOrderStatus;
  }

  public BigDecimal getNowOrderMoney() {
    return nowOrderMoney;
  }

  public void setNowOrderMoney(BigDecimal nowOrderMoney) {
    this.nowOrderMoney = nowOrderMoney;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

}
