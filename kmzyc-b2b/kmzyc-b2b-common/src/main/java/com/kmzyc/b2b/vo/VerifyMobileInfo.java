package com.kmzyc.b2b.vo;

public class VerifyMobileInfo {
  private String loginPassword;
  private String verifyCode;
  // 添加登陆ID
  private Long loginId;
  private String mobileNumber;

  private String mobileVerifyCode;

  private boolean modifyStatus;

  public String getLoginPassword() {
    return loginPassword;
  }

  public void setLoginPassword(String loginPassword) {
    this.loginPassword = loginPassword;
  }

  public String getVerifyCode() {
    return verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }

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

  public boolean isModifyStatus() {
    return modifyStatus;
  }

  public void setModifyStatus(boolean modifyStatus) {
    this.modifyStatus = modifyStatus;
  }

  public Long getLoginId() {
    return loginId;
  }

  public void setLoginId(Long loginId) {
    this.loginId = loginId;
  }

}
