package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;


/**
 * 手机验证类
 * 
 * @author luotao
 * @since 2014-6-03
 */
public class MoblieEmailValidate implements Serializable {
  private Integer loginId; // 登陆Id
  private String loginAccount;// 登陆账号
  private Date createDate; // 创建日期
  private String email;// 邮箱
  private String mobile;// 手机号码
  private Integer mobileStatus;// 手机验证状态 0--未验证 1--通过验证
  private Integer emailStatus;// 邮箱验证 0--未通过 1--通过验证
  private Integer customerTypeId;// 客户类别
  private Integer Status;// 账号状态
  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;



  public Integer getSkip() {
    return skip;
  }

  public void setSkip(Integer skip) {
    this.skip = skip;
  }

  public Integer getMax() {
    return max;
  }

  public void setMax(Integer max) {
    this.max = max;
  }

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Integer getMobileStatus() {
    return mobileStatus;
  }

  public void setMobileStatus(Integer mobileStatus) {
    this.mobileStatus = mobileStatus;
  }

  public Integer getEmailStatus() {
    return emailStatus;
  }

  public void setEmailStatus(Integer emailStatus) {
    this.emailStatus = emailStatus;
  }

  public Integer getCustomerTypeId() {
    return customerTypeId;
  }

  public void setCustomerTypeId(Integer customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  public Integer getStatus() {
    return Status;
  }

  public void setStatus(Integer status) {
    Status = status;
  }



}
