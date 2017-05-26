package com.pltfm.app.vobject;

import java.io.Serializable;

import com.pltfm.app.entities.OrderAlterPayStatement;

public class OrderAlterPayStatementVo extends OrderAlterPayStatement implements Serializable {
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

  private String platFormName;

  public String getPlatFormName() {
    return platFormName;
  }

  public void setPlatFormName(String platFormName) {
    this.platFormName = platFormName;
  }

}
