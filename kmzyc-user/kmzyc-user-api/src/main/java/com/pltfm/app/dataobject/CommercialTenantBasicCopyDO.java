package com.pltfm.app.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2014-07-24
 */
public class CommercialTenantBasicCopyDO implements Serializable {
  private static final long serialVersionUID = 140616833316550409L;
  private BigDecimal commercialCopyId;
  private BigDecimal commercialTenantId;
  private BigDecimal loginId;
  private String contactsName;
  private String contactsDepartment;
  private String fixedPhone;
  private String mobile;
  private String contactsEmail;
  private String corporateName; // 公司姓名
  private String corporateLocation;
  private String corporateProperty; // 公司性质
  private String corporateTrade;// 公司行业
  private BigDecimal enterpriseNumberOfPeople;
  private Date foundDate;
  private Short enterpriseStatus;
  private String postalcode;
  private String uploadBusinessLicencePictur;
  private String organizationCode;
  private String enterpriseLegalRepresentativ;
  private String enterpriseRealmName;
  private Date createDate;
  private BigDecimal created;
  private Date modifyDate;
  private BigDecimal Modified;
  private Short authenticationStatus;
  private String businessLicenceRegister;
  private String businessTimeLimit;
  private BigDecimal registerBankroll;
  private String businessScope;
  private String bankAccountName;
  private String bankOfDeposit;
  private String bankOfDepositBranchName;
  private String companyAccount;
  private Short certificateType;
  private String certificateNumber;
  private String location;
  private String certificatePicture;
  private String businessLicenceLocation;
  private Date blinceStartdate;
  private Date blinceEnddate;
  private Short blinceVerify;
  private Short cidVerify;
  private String province;
  private String city;
  private String area;
  private String certificateOthersid;
  private String taxpayerIdnumber;
  private String taxRegCertificateCopy;
  private String taxpayerCertificate;
  private Short trccopyVerify;
  private Short gtcVerify;
  private Short ataxVerify;
  private Short reviewChange;
  private String taxRegistrationCno;
  private String phone;
  private String organizationUrl;
  private String description;// 备注
  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;
  // 登陆名
  private String loginName;
  // 用户级别
  private Integer creditRating;

  public BigDecimal getCommercialCopyId() {
    return commercialCopyId;
  }

  public void setCommercialCopyId(BigDecimal commercialCopyId) {
    this.commercialCopyId = commercialCopyId;
  }

  public BigDecimal getCommercialTenantId() {
    return commercialTenantId;
  }

  public void setCommercialTenantId(BigDecimal commercialTenantId) {
    this.commercialTenantId = commercialTenantId;
  }

  public BigDecimal getLoginId() {
    return loginId;
  }

  public void setLoginId(BigDecimal loginId) {
    this.loginId = loginId;
  }

  public String getContactsName() {
    return contactsName;
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

  public BigDecimal getEnterpriseNumberOfPeople() {
    return enterpriseNumberOfPeople;
  }

  public void setEnterpriseNumberOfPeople(BigDecimal enterpriseNumberOfPeople) {
    this.enterpriseNumberOfPeople = enterpriseNumberOfPeople;
  }

  public Date getFoundDate() {
    return foundDate;
  }

  public void setFoundDate(Date foundDate) {
    this.foundDate = foundDate;
  }

  public Short getEnterpriseStatus() {
    return enterpriseStatus;
  }

  public void setEnterpriseStatus(Short enterpriseStatus) {
    this.enterpriseStatus = enterpriseStatus;
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

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public BigDecimal getCreated() {
    return created;
  }

  public void setCreated(BigDecimal created) {
    this.created = created;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public BigDecimal getModified() {
    return Modified;
  }

  public void setModified(BigDecimal modified) {
    Modified = modified;
  }

  public Short getAuthenticationStatus() {
    return authenticationStatus;
  }

  public void setAuthenticationStatus(Short authenticationStatus) {
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

  public BigDecimal getRegisterBankroll() {
    return registerBankroll;
  }

  public void setRegisterBankroll(BigDecimal registerBankroll) {
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

  public Short getCertificateType() {
    return certificateType;
  }

  public void setCertificateType(Short certificateType) {
    this.certificateType = certificateType;
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

  public String getTaxpayerCertificate() {
    return taxpayerCertificate;
  }

  public void setgTaxpayerCertificate(String taxpayerCertificate) {
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

  public Short getReviewChange() {
    return reviewChange;
  }

  public void setReviewChange(Short reviewChange) {
    this.reviewChange = reviewChange;
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

  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public Integer getCreditRating() {
    return creditRating;
  }

  public void setCreditRating(Integer creditRating) {
    this.creditRating = creditRating;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setTaxpayerCertificate(String taxpayerCertificate) {
    this.taxpayerCertificate = taxpayerCertificate;
  }



}
