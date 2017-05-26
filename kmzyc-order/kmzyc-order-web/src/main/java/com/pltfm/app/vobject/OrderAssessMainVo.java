package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

public class OrderAssessMainVo implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long orderId;

  private String customerAccount;

  private String customerName;

  private Date createDate;

  private String orderCode;

  private int assessInfoCount;

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public String getCustomerAccount() {
    return customerAccount;
  }

  public void setCustomerAccount(String customerAccount) {
    this.customerAccount = customerAccount;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public int getAssessInfoCount() {
    return assessInfoCount;
  }

  public void setAssessInfoCount(int assessInfoCount) {
    this.assessInfoCount = assessInfoCount;
  }

}
