package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮箱验证信息类
 * 
 * @author cjm
 * @since 2013-7-23
 */
public class EmailInfo implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 6646522862407706942L;

  /**
   * 邮箱验证ID
   */
  private Integer n_EmailId;

  /**
   * 账户ID
   */
  private Integer n_AccountId;

  /**
   * 随机码
   */
  private String tattedCode;
  /**
   * 登录账号ID
   */
  private Integer n_LoginId;


  /**
   * 状态
   */
  private Integer is_Status;


  /**
   * 最后发送随机码时间
   */
  private String lastSendTattedcodeTime;

  /**
   * 失效时间值
   */
  private Integer n_FailureTimeValue;

  /**
   * 邮件发送密码修改地址有效时间
   */
  private Integer n_EaillinkTimeValue;

  /**
   * 邮件URL地址
   */
  private String urlAddress;

  /**
   * 创建日期
   */
  private Date d_CreateDate;

  /**
   * 创建人
   */
  private Integer n_Created;

  /**
   * 修改日期
   */
  private Date d_ModifyDate;

  /**
   * 修改人
   */
  private Integer n_Modified;
  /**
   * 账户号
   */
  private String accountLogin;
  /**
   * 邮件地址
   */
  private String email;



  // ------------ for page
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



  public Integer getN_EmailId() {
    return n_EmailId;
  }

  public void setN_EmailId(Integer nEmailId) {
    n_EmailId = nEmailId;
  }

  public Integer getN_AccountId() {
    return n_AccountId;
  }

  public void setN_AccountId(Integer nAccountId) {
    n_AccountId = nAccountId;
  }

  public String getTattedCode() {
    return tattedCode;
  }

  public void setTattedCode(String tattedCode) {
    this.tattedCode = tattedCode;
  }

  public String getLastSendTattedcodeTime() {
    return lastSendTattedcodeTime;
  }

  public void setLastSendTattedcodeTime(String lastSendTattedcodeTime) {
    this.lastSendTattedcodeTime = lastSendTattedcodeTime;
  }

  public Integer getN_FailureTimeValue() {
    return n_FailureTimeValue;
  }

  public void setN_FailureTimeValue(Integer nFailureTimeValue) {
    n_FailureTimeValue = nFailureTimeValue;
  }

  public Integer getN_EaillinkTimeValue() {
    return n_EaillinkTimeValue;
  }

  public void setN_EaillinkTimeValue(Integer nEaillinkTimeValue) {
    n_EaillinkTimeValue = nEaillinkTimeValue;
  }


  public Date getD_CreateDate() {
    return d_CreateDate;
  }

  public void setD_CreateDate(Date dCreateDate) {
    d_CreateDate = dCreateDate;
  }

  public Integer getN_Created() {
    return n_Created;
  }

  public void setN_Created(Integer nCreated) {
    n_Created = nCreated;
  }

  public Date getD_ModifyDate() {
    return d_ModifyDate;
  }

  public void setD_ModifyDate(Date dModifyDate) {
    d_ModifyDate = dModifyDate;
  }

  public Integer getN_Modified() {
    return n_Modified;
  }

  public void setN_Modified(Integer nModified) {
    n_Modified = nModified;
  }

  public String getUrlAddress() {
    return urlAddress;
  }

  public void setUrlAddress(String urlAddress) {
    this.urlAddress = urlAddress;
  }

  public String getAccountLogin() {
    return accountLogin;
  }

  public void setAccountLogin(String accountLogin) {
    this.accountLogin = accountLogin;
  }

  public Integer getN_LoginId() {
    return n_LoginId;
  }

  public void setN_LoginId(Integer n_LoginId) {
    this.n_LoginId = n_LoginId;
  }

  public Integer getIs_Status() {
    return is_Status;
  }

  public void setIs_Status(Integer is_Status) {
    this.is_Status = is_Status;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
