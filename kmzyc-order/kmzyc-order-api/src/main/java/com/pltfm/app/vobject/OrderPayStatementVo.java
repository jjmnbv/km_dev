package com.pltfm.app.vobject;

import java.io.Serializable;

import com.pltfm.app.entities.OrderPayStatement;

public class OrderPayStatementVo extends OrderPayStatement implements Serializable {
  private static final long serialVersionUID = 1L;

  private String paymentWayStr;// 支付方式

  public String getPaymentWayStr() {
    return paymentWayStr;
  }

  public void setPaymentWayStr(String paymentWayStr) {
    this.paymentWayStr = paymentWayStr;
  }

  private String stateStr;// 支付状态

  public String getStateStr() {
    return stateStr;
  }

  public void setStateStr(String stateStr) {
    this.stateStr = stateStr;
  }

  private String flagStr;// 付/退款

  public String getFlagStr() {
    return flagStr;
  }

  public void setFlagStr(String flagStr) {
    this.flagStr = flagStr;
  }

  private String operate;
  private int status;
  private String alterCode;

  public String getOperate() {
    return operate;
  }

  public void setOperate(String operate) {
    this.operate = operate;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getAlterCode() {
    return alterCode;
  }

  public void setAlterCode(String alterCode) {
    this.alterCode = alterCode;
  }

}
