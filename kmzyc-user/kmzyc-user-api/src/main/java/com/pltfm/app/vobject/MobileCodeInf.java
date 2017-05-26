package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 手机随机码类
 * 
 * @author cjm
 * @since 2013-7-23
 */
public class MobileCodeInf implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -7425386469279040686L;


  /**
   * 手机随机码ID
   */
  private Integer n_CellPhoneTattedCodeId;


  /**
   * 账户ID
   */
  private Integer n_AccountId;

  /**
   * 登录账号ID
   */
  private Integer n_LoginId;
  /**
   * 随机码
   */
  private String tattedCode;

  /**
   * 最后发送随机码时间
   */
  private String lastSendTattedcodeTime;

  /**
   * 失效时间值
   */
  private Integer n_FailureTimeValue;

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
   * 手机号码
   */
  private String mobile;

  // ------------ for page
  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;

  /**
   * 状态
   */
  private Integer is_Status;
  /**
   * 验证码类型MOBLIE_TYPE
   */
  private Integer mobile_Type;



  public Integer getMobile_Type() {
    return mobile_Type;
  }

  public void setMobile_Type(Integer mobile_Type) {
    this.mobile_Type = mobile_Type;
  }

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


  public Integer getN_CellPhoneTattedCodeId() {
    return n_CellPhoneTattedCodeId;
  }

  public void setN_CellPhoneTattedCodeId(Integer nCellPhoneTattedCodeId) {
    n_CellPhoneTattedCodeId = nCellPhoneTattedCodeId;
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

  public String getAccountLogin() {
    return accountLogin;
  }

  public void setAccountLogin(String accountLogin) {
    this.accountLogin = accountLogin;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
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

}
