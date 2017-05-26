package com.kmzyc.b2b.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderOperateStatement {

  private Long statementId;

  private String orderCode;

  private Long orderItemId;

  private Long previousOrderStatus;

  private Long nowOrderStatus;

  private String previousOperator;

  private String nowOperator;

  private Date previousOperateDate;

  private Date nowOperateDate;

  private Long previousOperateType;

  private Long nowOperateType;

  private BigDecimal previousOrderSum;

  private BigDecimal nowOrderSum;

  private String operateInfo;

  public Long getStatementId() {
    return statementId;
  }

  public void setStatementId(Long statementId) {
    this.statementId = statementId;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public Long getOrderItemId() {
    return orderItemId;
  }

  public void setOrderItemId(Long orderItemId) {
    this.orderItemId = orderItemId;
  }

  public Long getPreviousOrderStatus() {
    return previousOrderStatus;
  }

  public void setPreviousOrderStatus(Long previousOrderStatus) {
    this.previousOrderStatus = previousOrderStatus;
  }

  public Long getNowOrderStatus() {
    return nowOrderStatus;
  }

  public void setNowOrderStatus(Long nowOrderStatus) {
    this.nowOrderStatus = nowOrderStatus;
  }

  public String getPreviousOperator() {
    return previousOperator;
  }

  public void setPreviousOperator(String previousOperator) {
    this.previousOperator = previousOperator;
  }

  public String getNowOperator() {
    return nowOperator;
  }

  public void setNowOperator(String nowOperator) {
    this.nowOperator = nowOperator;
  }

  public Date getPreviousOperateDate() {
    return previousOperateDate;
  }

  public void setPreviousOperateDate(Date previousOperateDate) {
    this.previousOperateDate = previousOperateDate;
  }

  public Date getNowOperateDate() {
    return nowOperateDate;
  }

  public void setNowOperateDate(Date nowOperateDate) {
    this.nowOperateDate = nowOperateDate;
  }

  public Long getPreviousOperateType() {
    return previousOperateType;
  }

  public void setPreviousOperateType(Long previousOperateType) {
    this.previousOperateType = previousOperateType;
  }

  public Long getNowOperateType() {
    return nowOperateType;
  }

  public void setNowOperateType(Long nowOperateType) {
    this.nowOperateType = nowOperateType;
  }

  public BigDecimal getPreviousOrderSum() {
    return previousOrderSum;
  }

  public void setPreviousOrderSum(BigDecimal previousOrderSum) {
    this.previousOrderSum = previousOrderSum;
  }

  public BigDecimal getNowOrderSum() {
    return nowOrderSum;
  }

  public void setNowOrderSum(BigDecimal nowOrderSum) {
    this.nowOrderSum = nowOrderSum;
  }

  public String getOperateInfo() {
    return operateInfo;
  }

  public void setOperateInfo(String operateInfo) {
    this.operateInfo = operateInfo;
  }

  @Override
  public String toString() {
    return "OrderOperateStatement [statementId=" + statementId + ", orderCode=" + orderCode
        + ", orderItemId=" + orderItemId + ", previousOrderStatus=" + previousOrderStatus
        + ", nowOrderStatus=" + nowOrderStatus + ", previousOperator=" + previousOperator
        + ", nowOperator=" + nowOperator + ", previousOperateDate=" + previousOperateDate
        + ", nowOperateDate=" + nowOperateDate + ", previousOperateType=" + previousOperateType
        + ", nowOperateType=" + nowOperateType + ", previousOrderSum=" + previousOrderSum
        + ", nowOrderSum=" + nowOrderSum + ", operateInfo=" + operateInfo + "]";
  }

}
