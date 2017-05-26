package com.kmzyc.b2b.vo;

public class WechatPayInfoVO {
  private String orderCode;
  private String partner = "1218318301";// ConfigurationUtil.getString("WX_PARTNERID");
  private String total_fee;
  private String partnerKey = "e201c3c2c776e75321b8e1ec93de44f9";// ConfigurationUtil.getString("WX_PARTNERKEY");
  private String appKey =
      "naukUUGkYFRG2XdkUOSPM4OULi2FWBzrusr55dMptEIsEGKdX8xOtdEIkApBx2FAL1TJwpLiyM6wCkaXzI86KIoYEAwMB51hC2LDWbhPch2khRQ4nvKH27ZhXPERGMKN";// ConfigurationUtil.getString("WX_APPKEY");
  private String appId = "wxc7bd3ceb78662e29";// ConfigurationUtil.getString("WX_APPID");

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getPartner() {
    return partner;
  }

  public void setPartner(String partner) {
    this.partner = partner;
  }

  public String getTotal_fee() {
    return total_fee;
  }

  public void setTotal_fee(String total_fee) {
    this.total_fee = total_fee;
  }

  public String getPartnerKey() {
    return partnerKey;
  }

  public void setPartnerKey(String partnerKey) {
    this.partnerKey = partnerKey;
  }

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }
}
