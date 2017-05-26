package com.kmzyc.b2b.vo;

/**
 * 安全中心显示信息
 * 
 * @author Administrator
 * 
 */
public class SecurityCentreInfo {

  /** 电子邮箱是否验证 true 为已验证 false为未验证 */
  private boolean emailValidate;

  /** 电子邮箱地址 */
  private String emailAddress;

  /** 手机号码是否验证 true为已验证 false为未验证 */
  private boolean mobileValidate;

  /** 手机号码 */
  private String mobileNumber;

  /** 支付密码是否启用 true为已启用 false为未启用 */
  private boolean payPasswordInvocation;

  /** 总体安全级别 1为低 两个为中 三个 为高 */
  private String safeLevel = "3";

  /** 支付密码安全级别 1为低 2为中 3为高 */
  private String payPasswordLevel;

  private String payPassword;

  /** 密码安全级别 1为低 2为中 3为高 */
  private String loginPasswordLevel;

  private String loginPassword;

  public boolean isEmailValidate() {
    return emailValidate;
  }

  public void setEmailValidate(boolean emailValidate) {
    this.emailValidate = emailValidate;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public boolean isMobileValidate() {
    return mobileValidate;
  }

  public void setMobileValidate(boolean mobileValidate) {
    this.mobileValidate = mobileValidate;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public boolean isPayPasswordInvocation() {
    return payPasswordInvocation;
  }

  public void setPayPasswordInvocation(boolean payPasswordInvocation) {
    this.payPasswordInvocation = payPasswordInvocation;
  }

  public String getSafeLevel() {
    int level = 0;
   /* if (emailValidate) {
      level = level + 1;
    }*/
    if (mobileValidate) {
      level = level + 1;
    }
    if (payPasswordInvocation) {
      level = level + 1;
    }
    return String.valueOf(level);
  }

  public void setSafeLevel(String safeLevel) {
    this.safeLevel = safeLevel;
  }

  public String getPayPasswordLevel() {
    return checkPassword(payPassword);
  }

  public void setPayPasswordLevel(String payPasswordLevel) {
    this.payPasswordLevel = payPasswordLevel;
  }

  public String getPayPassword() {
    return payPassword;
  }

  public void setPayPassword(String payPassword) {
    this.payPassword = payPassword;
  }

  public String checkPassword(String passwordStr) {
    String str = "/^[0-9]{1,20}$/"; // 不超过20位的数字组合
    String str1 = "/^[0-9|a-z|A-Z]{1,20}$/"; // 由字母、数字组成，不超过20位
    String str2 = "/^[a-zA-Z]{1,20}$/"; // 由字母不超过20位
    return "1";
    // if (passwordStr.matches(str)) {
    // return "1";
    // }
    // if (passwordStr.matches(str2)) {
    // return "2";
    // }
    // if (passwordStr.matches(str1)) {
    // return "3";
    // }
    // return passwordStr;

  }

  public String getLoginPasswordLevel() {
    return checkPassword(loginPassword);
  }

  public void setLoginPasswordLevel(String loginPasswordLevel) {
    this.loginPasswordLevel = loginPasswordLevel;
  }

  public String getLoginPassword() {
    return loginPassword;
  }

  public void setLoginPassword(String loginPassword) {
    this.loginPassword = loginPassword;
  }
}
