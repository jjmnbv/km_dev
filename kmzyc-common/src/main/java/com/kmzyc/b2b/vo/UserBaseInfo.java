package com.kmzyc.b2b.vo;

import java.io.Serializable;

/**
 * 用户基础信息
 * 
 * @author Administrator
 * 
 */
public class UserBaseInfo implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long loginId;
  private String loginAccount;
  private String loginPassword;
  private String mobile;
  private String email;
  private String modifyDate;
  private Integer modified;

  private String loginSalt;
  private String paySalt;
  private String password;

  public Long getLoginId() {
    return loginId;
  }

  public void setLoginId(Long loginId) {
    this.loginId = loginId;
  }

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }

  public String getLoginPassword() {
    return loginPassword;
  }

  public void setLoginPassword(String loginPassword) {
    this.loginPassword = loginPassword;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(String modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Integer getModified() {
    return modified;
  }

  public void setModified(Integer modified) {
    this.modified = modified;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLoginSalt() {
    return loginSalt;
  }

  public void setLoginSalt(String loginSalt) {
    this.loginSalt = loginSalt;
  }

  public String getPaySalt() {
    return paySalt;
  }

  public void setPaySalt(String paySalt) {
    this.paySalt = paySalt;
  }
}
