package com.kmzyc.b2b.model;

public class LoginInfo {
  // 手机号和状态
  private String mobile;
  private int mobileStatus = 0;
  // 邮箱和状态
  private String email;
  private int emailStatus = 0;

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public int getMobileStatus() {
    return mobileStatus;
  }

  public void setMobileStatus(int mobileStatus) {
    this.mobileStatus = mobileStatus;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getEmailStatus() {
    return emailStatus;
  }

  public void setEmailStatus(int emailStatus) {
    this.emailStatus = emailStatus;
  }

  @Override
  public String toString() {
    return "LoginInfo [mobile=" + mobile + ", mobileStatus=" + mobileStatus + ", email=" + email
        + ", emailStatus=" + emailStatus + "]";
  }

}
