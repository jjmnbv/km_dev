package com.kmzyc.b2b.vo;

public class ChangePasswordInfo {

  private String oldPassword;

  private String newPassword;
  private String verifyNewPassword;

  private String verifyCode;

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getVerifyCode() {
    return verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }

  public String getVerifyNewPassword() {
    return verifyNewPassword;
  }

  public void setVerifyNewPassword(String verifyNewPassword) {
    this.verifyNewPassword = verifyNewPassword;
  }

}
