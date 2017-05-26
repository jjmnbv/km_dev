package com.kmzyc.b2b.vo;

public class ChangePayPasswordInfo {
  private String mobileNumber;
  private String mobileVerifyCode;

  private String payPassword;

  private String verifyPayPassword;

  private String payRange;

  private String verifyCode;

  private boolean modifyStatus;

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getMobileVerifyCode() {
    return mobileVerifyCode;
  }

  public void setMobileVerifyCode(String mobileVerifyCode) {
    this.mobileVerifyCode = mobileVerifyCode;
  }

  public String getPayPassword() {
    return payPassword;
  }

  public void setPayPassword(String payPassword) {
    this.payPassword = payPassword;
  }

  public String getPayRange() {
    return payRange;
  }

  public void setPayRange(String payRange) {
    this.payRange = payRange;
  }

  public String getVerifyCode() {
    return verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }

  public boolean isModifyStatus() {
    return modifyStatus;
  }

  public void setModifyStatus(boolean modifyStatus) {
    this.modifyStatus = modifyStatus;
  }

  public String getVerifyPayPassword() {
    return verifyPayPassword;
  }

  public void setVerifyPayPassword(String verifyPayPassword) {
    this.verifyPayPassword = verifyPayPassword;
  }
}
