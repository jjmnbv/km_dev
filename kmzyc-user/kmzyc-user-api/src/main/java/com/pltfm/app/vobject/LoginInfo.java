package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录信息类
 * 
 * @author cjm
 * @since 2013-7-10
 */
public class LoginInfo implements Serializable {

  /**
   * 登录主键
   */
  private Integer n_LoginId;
  /**
   * 客户级别主键
   */
  private Integer n_LevelId;
  /**
   * 客户类别主键
   */
  private Integer n_CustomerTypeId;
  /**
   * 登录账号
   */
  private String loginAccount;
  /**
   * 登录密码
   */
  private String loginPassword;
  /**
   * 手机号
   */
  private String mobile;
  /**
   * 电子邮箱
   */
  private String email;
  /**
   * 账号状态
   */
  private Integer n_Status;
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
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;
  /**
   * 姓名
   */
  private String name;
  /** 登录卡号 **/
  private String cardNum;
  // 注册平台
  private Integer register_Platfrom;
  // 注册设备
  private Integer register_Device;

  //登录密码加密串
  private String loginSalt;
  //支付密码加密串
  private String paySalt;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public Integer getN_LoginId() {
    return n_LoginId;
  }

  public void setN_LoginId(Integer nLoginId) {
    n_LoginId = nLoginId;
  }

  public Integer getN_LevelId() {
    return n_LevelId;
  }

  public void setN_LevelId(Integer nLevelId) {
    n_LevelId = nLevelId;
  }

  public Integer getN_CustomerTypeId() {
    return n_CustomerTypeId;
  }

  public void setN_CustomerTypeId(Integer nCustomerTypeId) {
    n_CustomerTypeId = nCustomerTypeId;
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

  public Integer getN_Status() {
    return n_Status;
  }

  public void setN_Status(Integer nStatus) {
    n_Status = nStatus;
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

  public Integer getRegister_Platfrom() {
    return register_Platfrom;
  }

  public void setRegister_Platfrom(Integer register_Platfrom) {
    this.register_Platfrom = register_Platfrom;
  }

  public Integer getRegister_Device() {
    return register_Device;
  }

  public void setRegister_Device(Integer register_Device) {
    this.register_Device = register_Device;
  }

  public String getCardNum() {
    return cardNum;
  }

  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
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

  public String toString() {
    return new StringBuffer().append("{\"n_LoginId\":").append(n_LoginId).append(",\"n_LevelId\":")
        .append(n_LevelId).append(",\"n_CustomerTypeId\":").append(n_CustomerTypeId)
        .append(",\"loginAccount\":").append(loginAccount).append(",\"loginPassword\":")
        .append(loginPassword).append(",\"mobile\":").append(mobile).append(",\"email\":")
        .append(email).append(",\"n_Status\":").append(n_Status).append(",\"d_CreateDate\":")
        .append(d_CreateDate).append(",\"n_Created\":").append(n_Created)
        .append(",\"d_ModifyDate\":").append(d_ModifyDate).append(",\"n_Modified\":")
        .append(n_Modified).append(",\"name\":").append(name).append("}").toString();
  }

}
