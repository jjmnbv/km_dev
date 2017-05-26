package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

public class SyncPeronalInfoVO implements Serializable {
  /**
   * UID
   */
  private static final long serialVersionUID = 4944660931423014886L;
  /**
   * 账户号
   */
  private String accountLogin;
  /**
   * 卡号
   */
  private String cardNum;
  /**
   * 姓名
   */
  private String name;

  /**
   * 性别
   */
  private String sex;

  /**
   * 生日
   */
  private Date d_Birthday;

  /**
   * 手机号
   */
  private String mobile;

  /**
   * 电子邮箱
   */
  private String email;

  /**
   * 地址
   */
  private String address;

  /**
   * 修改日期
   */
  private Date d_ModifyDate;

  /**
   * 创建日期
   */
  private Date d_CreateDate;



  private Integer n_MaritalStatus;



  public String getAccountLogin() {
    return accountLogin;
  }



  public void setAccountLogin(String accountLogin) {
    this.accountLogin = accountLogin;
  }



  public String getCardNum() {
    return cardNum;
  }



  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }



  public String getName() {
    return name;
  }



  public void setName(String name) {
    this.name = name;
  }



  public String getSex() {
    return sex;
  }



  public void setSex(String sex) {
    this.sex = sex;
  }



  public Date getD_Birthday() {
    return d_Birthday;
  }



  public void setD_Birthday(Date dBirthday) {
    d_Birthday = dBirthday;
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



  public String getAddress() {
    return address;
  }



  public void setAddress(String address) {
    this.address = address;
  }



  public Date getD_ModifyDate() {
    return d_ModifyDate;
  }



  public void setD_ModifyDate(Date dModifyDate) {
    d_ModifyDate = dModifyDate;
  }



  public Date getD_CreateDate() {
    return d_CreateDate;
  }



  public void setD_CreateDate(Date dCreateDate) {
    d_CreateDate = dCreateDate;
  }



  public Integer getN_MaritalStatus() {
    return n_MaritalStatus;
  }



  public void setN_MaritalStatus(Integer nMaritalStatus) {
    n_MaritalStatus = nMaritalStatus;
  }

}
