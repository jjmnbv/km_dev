package com.kmzyc.b2b.vo;

public class VerifyPayInfo {
  private String verifyCode;

  private String mobileVerifyCode;

  private String payPassword;

  private boolean modifyStatus;

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

  public String getPayPassword() {
    return payPassword;
  }

  public void setPayPassword(String payPassword) {
    this.payPassword = payPassword;
  }

  public boolean isModifyStatus() {
    return modifyStatus;
  }

  public void setModifyStatus(boolean modifyStatus) {
    this.modifyStatus = modifyStatus;
  }
}
