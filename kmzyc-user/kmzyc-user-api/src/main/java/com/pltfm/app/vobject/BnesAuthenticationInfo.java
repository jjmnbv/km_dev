package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 实名认证信息类
 * 
 * @author cjm
 * @since 2013-8-1
 */
public class BnesAuthenticationInfo implements Serializable {
  /**
   * 实名认证ID
   */
  private Integer authenticationId;

  /**
   * 账户ID
   */
  private Integer accountId;

  /**
   * 客户类别主键
   */
  private Integer n_CustomerTypeId;

  /**
   * 客户类别名称
   */
  private String customerName;

  /**
   * 认证方式
   */
  private Integer authenticationMode;

  /**
   * 认证描述备注
   */
  private String certificateDiscription;

  /**
   * 认证审批是否通过
   */
  private Integer examinationValue;

  /**
   * 审批人
   */
  private Integer examinationPerson;

  /**
   * 认证审批通过日期
   */
  private Date examinationDate;

  /**
   * 认证创建日期
   */
  private Date createDate;

  /**
   * 创建人
   */
  private Integer createdId;

  /**
   * 修改日期
   */
  private Date modifyDate;

  /**
   * 修改人
   */
  private Integer modifieId;

  /**
   * 账户号
   */
  private String accountLogin;

  /**
   * 账户真实姓名
   */
  private String name;

  /**
   * 身份证号码
   */
  private String acconutId;

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

  public Integer getAuthenticationId() {
    return authenticationId;
  }

  public void setAuthenticationId(Integer authenticationId) {
    this.authenticationId = authenticationId;
  }

  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public Integer getAuthenticationMode() {
    return authenticationMode;
  }

  public void setAuthenticationMode(Integer authenticationMode) {
    this.authenticationMode = authenticationMode;
  }

  public String getCertificateDiscription() {
    return certificateDiscription;
  }

  public void setCertificateDiscription(String certificateDiscription) {
    this.certificateDiscription = certificateDiscription;
  }

  public Integer getExaminationValue() {
    return examinationValue;
  }

  public void setExaminationValue(Integer examinationValue) {
    this.examinationValue = examinationValue;
  }

  public Integer getExaminationPerson() {
    return examinationPerson;
  }

  public void setExaminationPerson(Integer examinationPerson) {
    this.examinationPerson = examinationPerson;
  }

  public Date getExaminationDate() {
    return examinationDate;
  }

  public void setExaminationDate(Date examinationDate) {
    this.examinationDate = examinationDate;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Integer getCreatedId() {
    return createdId;
  }

  public void setCreatedId(Integer createdId) {
    this.createdId = createdId;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Integer getModifieId() {
    return modifieId;
  }

  public void setModifieId(Integer modifieId) {
    this.modifieId = modifieId;
  }

  public String getAccountLogin() {
    return accountLogin;
  }

  public void setAccountLogin(String accountLogin) {
    this.accountLogin = accountLogin;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAcconutId() {
    return acconutId;
  }

  public void setAcconutId(String acconutId) {
    this.acconutId = acconutId;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
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

  public Integer getN_CustomerTypeId() {
    return n_CustomerTypeId;
  }

  public void setN_CustomerTypeId(Integer nCustomerTypeId) {
    n_CustomerTypeId = nCustomerTypeId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }


}
