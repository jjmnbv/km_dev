package com.kmzyc.b2b.vo;

import java.util.Date;

public class TraceInfoVO implements java.io.Serializable {
  private Date date;
  private String info;
  private String operator;
  private String orderCode;
  private String orderAlterCode;
  private int status;
  private int operatStatus;

  public int getOperatStatus() {
    return operatStatus;
  }

  public void setOperatStatus(int operatStatus) {
    this.operatStatus = operatStatus;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getOrderAlterCode() {
    return orderAlterCode;
  }

  public void setOrderAlterCode(String orderAlterCode) {
    this.orderAlterCode = orderAlterCode;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }
}
