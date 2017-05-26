package com.pltfm.app.vobject;

import java.io.Serializable;

public class OrderLogisticInfo implements Serializable {

  private static final long serialVersionUID = -4696610059438445548L;

  private String logisticsOrderNo;
  private String logisticsCode;
  private String logisticsName;
  private String orderCode;

  public String getLogisticsOrderNo() {
    return logisticsOrderNo;
  }

  public void setLogisticsOrderNo(String logisticsOrderNo) {
    this.logisticsOrderNo = logisticsOrderNo;
  }

  public String getLogisticsCode() {
    return logisticsCode;
  }

  public void setLogisticsCode(String logisticsCode) {
    this.logisticsCode = logisticsCode;
  }

  public String getLogisticsName() {
    return logisticsName;
  }

  public void setLogisticsName(String logisticsName) {
    this.logisticsName = logisticsName;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

}
