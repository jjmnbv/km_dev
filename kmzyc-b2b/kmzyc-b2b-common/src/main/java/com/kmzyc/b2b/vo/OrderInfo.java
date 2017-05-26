package com.kmzyc.b2b.vo;

import java.util.Date;
import java.util.List;

public class OrderInfo {
  private Long orderId;

  private Date createDate;

  private String orderCode;

  private Integer assessStatus;

  private List<Commodity> commodities;

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
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

  public Integer getAssessStatus() {
    return assessStatus;
  }

  public void setAssessStatus(Integer assessStatus) {
    this.assessStatus = assessStatus;
  }

  public List<Commodity> getCommodities() {
    return commodities;
  }

  public void setCommodities(List<Commodity> commodities) {
    this.commodities = commodities;
  }
}
