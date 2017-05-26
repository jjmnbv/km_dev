package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户基本信息类
 * 
 * @author cjm
 * @since 2013-7-10
 */
public class CommercialTenantBasicInfo implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = -5489678537091972027L;

  /**
   * 商户主键
   */
  private Integer n_CommercialTenantId;

  /**
   * 登录主键
   */
  private Integer n_LoginId;
  /**
   * 登录账号
   */
  private String loginAccount;

  /**
   * 客户类别主键
   */
  private Integer n_CustomerTypeId;

  /**
   * 客户类别名称
   */
  private String customerName;

  /**
   * 客户级别主键
   */
  private Integer n_LevelId;
  /**
   * 登录账号状态
   */
  private Integer login_Status;
  /**
   * 客户级别名称
   */
  private String levelName;

  /**
   * 客户头衔主键
   */
  private Integer n_RankId;


  /**
   * 客户头衔名称
   */
  private String rankName;

  /**
   * 经验值
   */
  private Integer n_EmpiricalValue;
  /**
   * 总积分
   */
  private Double n_TotalIntegral;
  /**
   * 可用积分
   */
  private Double n_AvailableIntegral;
  /**
   * 信用值
   */
  private Integer n_IndividualCreditValue;


  /**
   * 联系人姓名
   */
  private String contactsName;

  /**
   * 联系人所在部门
   */
  private String contactsDepartment;

  /**
   * 固定电话
   */
  private String fixedPhone;

  /**
   * 手机
   */
  private String mobile;

  /**
   * 联系人邮箱
   */
  private String contactsEmail;

  /**
   * 公司名称
   */
  private String corporateName;

  /**
   * 公司所在地
   */
  private String corporateLocation;

  /**
   * 公司性质
   */
  private String corporateProperty;

  /**
   * 公司行业
   */
  private String corporateTrade;

  /**
   * 企业人数
   */
  private Integer n_EnterpriseNumberOfPeople;

  /**
   * 成立日期
   */
  private Date d_FoundDate;

  /**
   * 企业状态
   */
  private Integer n_EnterpriseStatus;

  /**
   * 邮政编码
   */
  private String postalcode;

  /**
   * 上传营业执照图片
   */
  private String uploadBusinessLicencePictur;

  /**
   * 组织代码
   */
  private String organizationCode;

  /**
   * 企业法定代表人
   */
  private String enterpriseLegalRepresentativ;

  /**
   * 企业域名
   */
  private String enterpriseRealmName;

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
   * 认证状态
   */
  private Integer authenticationStatus;

  /**
   * 营业执照注册号
   */
  private String businessLicenceRegister;

  /**
   * 营业期限(年)
   */
  private String businessTimeLimit;

  /**
   * 注册资金
   */
  private Double registerBankroll;

  /**
   * 营业范围
   */
  private String businessScope;

  /**
   * 银行开户名
   */
  private String bankAccountName;

  /**
   * 开户银行
   */
  private String bankOfDeposit;

  /**
   * 开户银行支行名称
   */
  private String bankOfDepositBranchName;

  /**
   * 公司对公账户
   */
  private String companyAccount;

  /**
   * 证件类型
   */
  private Integer n_CertificateType;

  /**
   * 证件号码
   */
  private String certificateNumber;

  /**
   * 法人代表所在地
   */
  private String location;

  /**
   * 证件图片正反面
   */
  private String certificatePicture;

  /**
   * 营业执照所在地
   */
  private String businessLicenceLocation;



  // 申请状态

  private String status;

  // 供应商状态
  private String sstatus;


  // 审核意见描述



  private String description;

  /**
   * 营业执照有效起始日期 column COMMERCIAL_TENANT_BASIC_INFO.BLINCE_STARTDATE
   */
  private Date blinceStartdate;

  /**
   * 营业执照有效结束日期 column COMMERCIAL_TENANT_BASIC_INFO.BLINCE_ENDDATE
   */
  private Date blinceEnddate;

  /**
   * 营业执照是否验证0----未验证1---已验证 column COMMERCIAL_TENANT_BASIC_INFO.BLINCE_VERIFY
   */
  private Short blinceVerify;

  /**
   * 企业法人身份证是否验证0----未验证1---已验证 column COMMERCIAL_TENANT_BASIC_INFO.CID_VERIFY
   */
  private Short cidVerify;

  /**
   * 所属区域-省 column COMMERCIAL_TENANT_BASIC_INFO.PROVINCE
   */
  private String province;

  /**
   * 所属区域-城市 column COMMERCIAL_TENANT_BASIC_INFO.CITY
   */
  private String city;

  /**
   * 所属区域-区 column COMMERCIAL_TENANT_BASIC_INFO.AREA
   */
  private String area;

  /**
   * 身份证件反面URL column COMMERCIAL_TENANT_BASIC_INFO.CERTIFICATE_OTHERSID
   */
  private String certificateOthersid;


  /**
   * column COMMERCIAL_TENANT_BASIC_INFO.TAXPAYER_IDNUMBER
   */
  private String taxpayerIdnumber;

  /**
   * column COMMERCIAL_TENANT_BASIC_INFO.TAX_REG_CERTIFICATE_COPY
   */
  private String taxRegCertificateCopy;

  /**
   * column COMMERCIAL_TENANT_BASIC_INFO.G_TAXPAYER_CERTIFICATE
   */
  private String taxpayerCertificate;

  /**
   * column COMMERCIAL_TENANT_BASIC_INFO.TRCCOPY_VERIFY
   */
  private Short trccopyVerify;

  /**
   * column COMMERCIAL_TENANT_BASIC_INFO.GTC_VERIFY
   */
  private Short gtcVerify;

  /**
   * column COMMERCIAL_TENANT_BASIC_INFO.ATAX_VERIFY
   */
  private Short ataxVerify;

  /**
   * column COMMERCIAL_TENANT_BASIC_INFO.TAX_REGISTRATION_CNO
   */
  private String taxRegistrationCno;

  /**
   * column COMMERCIAL_TENANT_BASIC_INFO.PHONE
   */
  private String phone;

  /**
   * column COMMERCIAL_TENANT_BASIC_INFO.ORGANIZATION_URL
   */
  private String organizationUrl;

  /**
   * column COMMERCIAL_TENANT_BASIC_INFO.RATING_DESCRIPTION
   */
  private String ratingDescription;

  /**
   * column COMMERCIAL_TENANT_BASIC_INFO.CREDIT_RATING
   */
  private Integer creditRating;
  /**
   * column LOGIN_ROSE_REL.IS_VALID
   */
  private Short isValid;

  public Short getIsValid() {
    return isValid;
  }

  public void setIsValid(Short isValid) {
    this.isValid = isValid;
  }

  public Integer getCreditRating() {
    return creditRating;
  }

  public void setCreditRating(Integer creditRating) {
    this.creditRating = creditRating;
  }

  public String getTaxpayerIdnumber() {
    return taxpayerIdnumber;
  }

  public void setTaxpayerIdnumber(String taxpayerIdnumber) {
    this.taxpayerIdnumber = taxpayerIdnumber;
  }

  public String getTaxRegCertificateCopy() {
    return taxRegCertificateCopy;
  }

  public void setTaxRegCertificateCopy(String taxRegCertificateCopy) {
    this.taxRegCertificateCopy = taxRegCertificateCopy;
  }

  public String getRatingDescription() {
    return ratingDescription;
  }

  public void setRatingDescription(String ratingDescription) {
    this.ratingDescription = ratingDescription;
  }


  public String getTaxpayerCertificate() {
    return taxpayerCertificate;
  }

  public void setTaxpayerCertificate(String taxpayerCertificate) {
    this.taxpayerCertificate = taxpayerCertificate;
  }

  public Short getTrccopyVerify() {
    return trccopyVerify;
  }

  public void setTrccopyVerify(Short trccopyVerify) {
    this.trccopyVerify = trccopyVerify;
  }

  public Short getGtcVerify() {
    return gtcVerify;
  }

  public void setGtcVerify(Short gtcVerify) {
    this.gtcVerify = gtcVerify;
  }

  public Short getAtaxVerify() {
    return ataxVerify;
  }

  public void setAtaxVerify(Short ataxVerify) {
    this.ataxVerify = ataxVerify;
  }

  public String getTaxRegistrationCno() {
    return taxRegistrationCno;
  }

  public void setTaxRegistrationCno(String taxRegistrationCno) {
    this.taxRegistrationCno = taxRegistrationCno;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getOrganizationUrl() {
    return organizationUrl;
  }

  public void setOrganizationUrl(String organizationUrl) {
    this.organizationUrl = organizationUrl;
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

  public Short getBlinceVerify() {
    return blinceVerify;
  }

  public void setBlinceVerify(Short blinceVerify) {
    this.blinceVerify = blinceVerify;
  }

  public Short getCidVerify() {
    return cidVerify;
  }

  public void setCidVerify(Short cidVerify) {
    this.cidVerify = cidVerify;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getCertificateOthersid() {
    return certificateOthersid;
  }

  public void setCertificateOthersid(String certificateOthersid) {
    this.certificateOthersid = certificateOthersid;
  }

  public Integer getN_CommercialTenantId() {
    return n_CommercialTenantId;
  }

  public void setN_CommercialTenantId(Integer nCommercialTenantId) {
    n_CommercialTenantId = nCommercialTenantId;
  }

  public Integer getN_LoginId() {
    return n_LoginId;
  }

  public void setN_LoginId(Integer nLoginId) {
    n_LoginId = nLoginId;
  }

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
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

  public Integer getN_LevelId() {
    return n_LevelId;
  }

  public void setN_LevelId(Integer nLevelId) {
    n_LevelId = nLevelId;
  }

  public String getLevelName() {
    return levelName;
  }

  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }

  public Integer getN_RankId() {
    return n_RankId;
  }

  public void setN_RankId(Integer nRankId) {
    n_RankId = nRankId;
  }

  public String getRankName() {
    return rankName;
  }

  public void setRankName(String rankName) {
    this.rankName = rankName;
  }

  public Integer getN_EmpiricalValue() {
    return n_EmpiricalValue;
  }

  public void setN_EmpiricalValue(Integer nEmpiricalValue) {
    n_EmpiricalValue = nEmpiricalValue;
  }

  public Double getN_AvailableIntegral() {
    return n_AvailableIntegral;
  }

  public void setN_AvailableIntegral(Double nAvailableIntegral) {
    n_AvailableIntegral = nAvailableIntegral;
  }

  public Integer getN_IndividualCreditValue() {
    return n_IndividualCreditValue;
  }

  public void setN_IndividualCreditValue(Integer nIndividualCreditValue) {
    n_IndividualCreditValue = nIndividualCreditValue;
  }

  public String getContactsName() {
    return contactsName;
  }

  public Integer getLogin_Status() {
    return login_Status;
  }

  public void setLogin_Status(Integer loginStatus) {
    login_Status = loginStatus;
  }

  public void setContactsName(String contactsName) {
    this.contactsName = contactsName;
  }

  public String getContactsDepartment() {
    return contactsDepartment;
  }

  public void setContactsDepartment(String contactsDepartment) {
    this.contactsDepartment = contactsDepartment;
  }

  public String getFixedPhone() {
    return fixedPhone;
  }

  public void setFixedPhone(String fixedPhone) {
    this.fixedPhone = fixedPhone;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getContactsEmail() {
    return contactsEmail;
  }

  public void setContactsEmail(String contactsEmail) {
    this.contactsEmail = contactsEmail;
  }

  public String getCorporateName() {
    return corporateName;
  }

  public void setCorporateName(String corporateName) {
    this.corporateName = corporateName;
  }

  public String getCorporateLocation() {
    return corporateLocation;
  }

  public void setCorporateLocation(String corporateLocation) {
    this.corporateLocation = corporateLocation;
  }

  public String getCorporateProperty() {
    return corporateProperty;
  }

  public void setCorporateProperty(String corporateProperty) {
    this.corporateProperty = corporateProperty;
  }

  public String getCorporateTrade() {
    return corporateTrade;
  }

  public void setCorporateTrade(String corporateTrade) {
    this.corporateTrade = corporateTrade;
  }

  public Integer getN_EnterpriseNumberOfPeople() {
    return n_EnterpriseNumberOfPeople;
  }

  public void setN_EnterpriseNumberOfPeople(Integer nEnterpriseNumberOfPeople) {
    n_EnterpriseNumberOfPeople = nEnterpriseNumberOfPeople;
  }

  public Date getD_FoundDate() {
    return d_FoundDate;
  }

  public void setD_FoundDate(Date dFoundDate) {
    d_FoundDate = dFoundDate;
  }

  public Integer getN_EnterpriseStatus() {
    return n_EnterpriseStatus;
  }

  public void setN_EnterpriseStatus(Integer nEnterpriseStatus) {
    n_EnterpriseStatus = nEnterpriseStatus;
  }

  public String getPostalcode() {
    return postalcode;
  }

  public void setPostalcode(String postalcode) {
    this.postalcode = postalcode;
  }

  public String getUploadBusinessLicencePictur() {
    return uploadBusinessLicencePictur;
  }

  public void setUploadBusinessLicencePictur(String uploadBusinessLicencePictur) {
    this.uploadBusinessLicencePictur = uploadBusinessLicencePictur;
  }

  public String getOrganizationCode() {
    return organizationCode;
  }

  public void setOrganizationCode(String organizationCode) {
    this.organizationCode = organizationCode;
  }

  public String getEnterpriseLegalRepresentativ() {
    return enterpriseLegalRepresentativ;
  }

  public void setEnterpriseLegalRepresentativ(String enterpriseLegalRepresentativ) {
    this.enterpriseLegalRepresentativ = enterpriseLegalRepresentativ;
  }

  public String getEnterpriseRealmName() {
    return enterpriseRealmName;
  }

  public void setEnterpriseRealmName(String enterpriseRealmName) {
    this.enterpriseRealmName = enterpriseRealmName;
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

  public Integer getAuthenticationStatus() {
    return authenticationStatus;
  }

  public void setAuthenticationStatus(Integer authenticationStatus) {
    this.authenticationStatus = authenticationStatus;
  }

  public String getBusinessLicenceRegister() {
    return businessLicenceRegister;
  }

  public void setBusinessLicenceRegister(String businessLicenceRegister) {
    this.businessLicenceRegister = businessLicenceRegister;
  }

  public String getBusinessTimeLimit() {
    return businessTimeLimit;
  }

  public void setBusinessTimeLimit(String businessTimeLimit) {
    this.businessTimeLimit = businessTimeLimit;
  }

  public Double getRegisterBankroll() {
    return registerBankroll;
  }

  public void setRegisterBankroll(Double registerBankroll) {
    this.registerBankroll = registerBankroll;
  }

  public String getBusinessScope() {
    return businessScope;
  }

  public void setBusinessScope(String businessScope) {
    this.businessScope = businessScope;
  }

  public String getBankAccountName() {
    return bankAccountName;
  }

  public void setBankAccountName(String bankAccountName) {
    this.bankAccountName = bankAccountName;
  }

  public String getBankOfDeposit() {
    return bankOfDeposit;
  }

  public void setBankOfDeposit(String bankOfDeposit) {
    this.bankOfDeposit = bankOfDeposit;
  }

  public String getBankOfDepositBranchName() {
    return bankOfDepositBranchName;
  }

  public void setBankOfDepositBranchName(String bankOfDepositBranchName) {
    this.bankOfDepositBranchName = bankOfDepositBranchName;
  }

  public String getCompanyAccount() {
    return companyAccount;
  }

  public void setCompanyAccount(String companyAccount) {
    this.companyAccount = companyAccount;
  }

  public Integer getN_CertificateType() {
    return n_CertificateType;
  }

  public void setN_CertificateType(Integer nCertificateType) {
    n_CertificateType = nCertificateType;
  }

  public String getCertificateNumber() {
    return certificateNumber;
  }

  public void setCertificateNumber(String certificateNumber) {
    this.certificateNumber = certificateNumber;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getCertificatePicture() {
    return certificatePicture;
  }

  public void setCertificatePicture(String certificatePicture) {
    this.certificatePicture = certificatePicture;
  }

  public String getBusinessLicenceLocation() {
    return businessLicenceLocation;
  }

  public void setBusinessLicenceLocation(String businessLicenceLocation) {
    this.businessLicenceLocation = businessLicenceLocation;
  }

  public Double getN_TotalIntegral() {
    return n_TotalIntegral;
  }

  public void setN_TotalIntegral(Double nTotalIntegral) {
    n_TotalIntegral = nTotalIntegral;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSstatus() {
    return sstatus;
  }

  public void setSstatus(String sstatus) {
    this.sstatus = sstatus;
  }

}
