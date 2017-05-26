package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

public class BuyerInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * 商户信息主键
   */
  private Integer n_CommercialTenantId;
  /**
   * 登录账号主键
   */
  private Integer n_LoginId;


  /**
   * 登录账号
   */
  private String loginAccount;
  /**
   * 登录密码
   */
  private String loginPassword;

  /**
   * 电子邮箱
   */
  private String email;


  /**
   * 手机号码
   */
  private String mobile;


  /**
   * 业务联系方式
   */
  private String fixedPhone;

  /**
   * 公司名称
   */
  private String corporateName;
  /**
   * 营业执照注册号
   */
  private String businessLicenceRegister;
  /**
   * 营业执照有效起始日期 column COMMERCIAL_TENANT_BASIC_INFO.BLINCE_STARTDATE
   */
  private Date blinceStartdate;

  /**
   * 营业执照有效结束日期 column COMMERCIAL_TENANT_BASIC_INFO.BLINCE_ENDDATE
   */
  private Date blinceEnddate;

  /**
   * 上传营业执照图片
   */
  private String uploadBusinessLicencePictur;

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFixedPhone() {
    return fixedPhone;
  }

  public void setFixedPhone(String fixedPhone) {
    this.fixedPhone = fixedPhone;
  }

  public String getCorporateName() {
    return corporateName;
  }

  public void setCorporateName(String corporateName) {
    this.corporateName = corporateName;
  }

  public String getBusinessLicenceRegister() {
    return businessLicenceRegister;
  }

  public void setBusinessLicenceRegister(String businessLicenceRegister) {
    this.businessLicenceRegister = businessLicenceRegister;
  }

  public Date getBlinceStartdate() {
    return blinceStartdate;
  }

  public void setBlinceStartdate(Date blinceStartdate) {
    this.blinceStartdate = blinceStartdate;
  }

  public Date getBlinceEnddate() {
    return blinceEnddate;
  }

  public void setBlinceEnddate(Date blinceEnddate) {
    this.blinceEnddate = blinceEnddate;
  }

  public String getUploadBusinessLicencePictur() {
    return uploadBusinessLicencePictur;
  }

  public void setUploadBusinessLicencePictur(String uploadBusinessLicencePictur) {
    this.uploadBusinessLicencePictur = uploadBusinessLicencePictur;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Integer getN_CommercialTenantId() {
    return n_CommercialTenantId;
  }

  public void setN_CommercialTenantId(Integer n_CommercialTenantId) {
    this.n_CommercialTenantId = n_CommercialTenantId;
  }

  public Integer getN_LoginId() {
    return n_LoginId;
  }

  public void setN_LoginId(Integer n_LoginId) {
    this.n_LoginId = n_LoginId;
  }
}
