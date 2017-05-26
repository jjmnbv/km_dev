package com.kmzyc.b2b.vo;

/**
 * 订单跟踪传输对象
 * 
 * @author Administrator
 * 
 */
public class OrderTrailInfo {
  /** 手机号码 */
  private String mobileNumber;
  /** 订单编号 */
  private String orderNo;
  /** 验证码 */
  private String verifyCode;
  /** 手机验证码 */
  private String mobileVerifyCode;
  /** 电子邮件地址 */
  private String emailAddress;
  /** 快递公司名称 */
  private String expressComName;
  /** 快递单号 */
  private String expressNo;
  /** 订单状态 */
  private String orderStatus;
  /** 最后状态时间 */
  private String lastItemTime;
  /** 最后状态描述 */
  private String lastItemContext;

  private String backFlag;

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getVerifyCode() {
    return verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }

  public String getMobileVerifyCode() {
    return mobileVerifyCode;
  }

  public void setMobileVerifyCode(String mobileVerifyCode) {
    this.mobileVerifyCode = mobileVerifyCode;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getExpressComName() {
    return expressComName;
  }

  public void setExpressComName(String expressComName) {
    this.expressComName = expressComName;
  }

  public String getExpressNo() {
    return expressNo;
  }

  public void setExpressNo(String expressNo) {
    this.expressNo = expressNo;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String getLastItemTime() {
    return lastItemTime;
  }

  public void setLastItemTime(String lastItemTime) {
    this.lastItemTime = lastItemTime;
  }

  public String getLastItemContext() {
    return lastItemContext;
  }

  public void setLastItemContext(String lastItemContext) {
    this.lastItemContext = lastItemContext;
  }

  public String getBackFlag() {
    return backFlag;
  }

  public void setBackFlag(String backFlag) {
    this.backFlag = backFlag;
  }

}
