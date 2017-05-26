package com.pltfm.app.vobject;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2014-07-21
 */
public class CommercialTenantBasicCopy implements Serializable {

  private static final long serialVersionUID = 140593236672563494L;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.N_COMMERCIAL_COPY_ID
   */
  private BigDecimal nCommercialCopyId;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.N_COMMERCIAL_TENANT_ID
   */
  private BigDecimal nCommercialTenantId;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.N_LOGIN_ID
   */
  private BigDecimal nLoginId;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.CONTACTS_NAME
   */
  private String contactsName;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.CONTACTS_DEPARTMENT
   */
  private String contactsDepartment;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.FIXED_PHONE
   */
  private String fixedPhone;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.MOBILE
   */
  private String mobile;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.CONTACTS_EMAIL
   */
  private String contactsEmail;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.CORPORATE_NAME
   */
  private String corporateName;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.CORPORATE_LOCATION
   */
  private String corporateLocation;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.CORPORATE_PROPERTY
   */
  private String corporateProperty;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.CORPORATE_TRADE
   */
  private String corporateTrade;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.N_ENTERPRISE_NUMBER_OF_PEOPLE
   */
  private BigDecimal nEnterpriseNumberOfPeople;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.D_FOUND_DATE
   */
  private Date dFoundDate;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.N_ENTERPRISE_STATUS
   */
  private Short nEnterpriseStatus;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.POSTALCODE
   */
  private String postalcode;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.UPLOAD_BUSINESS_LICENCE_PICTUR
   */
  private String uploadBusinessLicencePictur;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.ORGANIZATION_CODE
   */
  private String organizationCode;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.ENTERPRISE_LEGAL_REPRESENTATIV
   */
  private String enterpriseLegalRepresentativ;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.ENTERPRISE_REALM_NAME
   */
  private String enterpriseRealmName;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.D_CREATE_DATE
   */
  private Date dCreateDate;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.N_CREATED
   */
  private BigDecimal nCreated;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.D_MODIFY_DATE
   */
  private Date dModifyDate;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.N_MODIFIED
   */
  private BigDecimal nModified;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.AUTHENTICATION_STATUS
   */
  private Short authenticationStatus;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.BUSINESS_LICENCE_REGISTER
   */
  private String businessLicenceRegister;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.BUSINESS_TIME_LIMIT
   */
  private String businessTimeLimit;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.REGISTER_BANKROLL
   */
  private BigDecimal registerBankroll;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.BUSINESS_SCOPE
   */
  private String businessScope;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.BANK_ACCOUNT_NAME
   */
  private String bankAccountName;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.BANK_OF_DEPOSIT
   */
  private String bankOfDeposit;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.BANK_OF_DEPOSIT_BRANCH_NAME
   */
  private String bankOfDepositBranchName;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.COMPANY_ACCOUNT
   */
  private String companyAccount;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.N_CERTIFICATE_TYPE
   */
  private Short nCertificateType;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.CERTIFICATE_NUMBER
   */
  private String certificateNumber;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.LOCATION
   */
  private String location;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.CERTIFICATE_PICTURE
   */
  private String certificatePicture;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.BUSINESS_LICENCE_LOCATION
   */
  private String businessLicenceLocation;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.BLINCE_STARTDATE
   */
  private Date blinceStartdate;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.BLINCE_ENDDATE
   */
  private Date blinceEnddate;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.BLINCE_VERIFY
   */
  private Short blinceVerify;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.CID_VERIFY
   */
  private Short cidVerify;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.PROVINCE
   */
  private String province;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.CITY
   */
  private String city;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.AREA
   */
  private String area;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.CERTIFICATE_OTHERSID
   */
  private String certificateOthersid;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.TAXPAYER_IDNUMBER
   */
  private String taxpayerIdnumber;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.TAX_REG_CERTIFICATE_COPY
   */
  private String taxRegCertificateCopy;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.G_TAXPAYER_CERTIFICATE
   */
  private String gTaxpayerCertificate;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.TRCCOPY_VERIFY
   */
  private Short trccopyVerify;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.GTC_VERIFY
   */
  private Short gtcVerify;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.ATAX_VERIFY
   */
  private Short ataxVerify;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.REVIEW_CHANGE
   */
  private Short reviewChange;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.TAX_REGISTRATION_CNO
   */
  private String taxRegistrationCno;

  /**
   * column COMMERCIAL_TENANT_BASIC_COPY.PHONE
   */
  private String phone;

  public CommercialTenantBasicCopy() {
    super();
  }



  public CommercialTenantBasicCopy(BigDecimal nCommercialCopyId, BigDecimal nCommercialTenantId,
      BigDecimal nLoginId, String contactsName, String contactsDepartment, String fixedPhone,
      String mobile, String contactsEmail, String corporateName, String corporateLocation,
      String corporateProperty, String corporateTrade, BigDecimal nEnterpriseNumberOfPeople,
      Date dFoundDate, Short nEnterpriseStatus, String postalcode,
      String uploadBusinessLicencePictur, String organizationCode,
      String enterpriseLegalRepresentativ, String enterpriseRealmName, Date dCreateDate,
      BigDecimal nCreated, Date dModifyDate, BigDecimal nModified, Short authenticationStatus,
      String businessLicenceRegister, String businessTimeLimit, BigDecimal registerBankroll,
      String businessScope, String bankAccountName, String bankOfDeposit,
      String bankOfDepositBranchName, String companyAccount, Short nCertificateType,
      String certificateNumber, String location, String certificatePicture,
      String businessLicenceLocation, Date blinceStartdate, Date blinceEnddate, Short blinceVerify,
      Short cidVerify, String province, String city, String area, String certificateOthersid,
      String taxpayerIdnumber, String taxRegCertificateCopy, String gTaxpayerCertificate,
      Short trccopyVerify, Short gtcVerify, Short ataxVerify, Short reviewChange,
      String taxRegistrationCno, String phone) {
    this.nCommercialCopyId = nCommercialCopyId;
    this.nCommercialTenantId = nCommercialTenantId;
    this.nLoginId = nLoginId;
    this.contactsName = contactsName;
    this.contactsDepartment = contactsDepartment;
    this.fixedPhone = fixedPhone;
    this.mobile = mobile;
    this.contactsEmail = contactsEmail;
    this.corporateName = corporateName;
    this.corporateLocation = corporateLocation;
    this.corporateProperty = corporateProperty;
    this.corporateTrade = corporateTrade;
    this.nEnterpriseNumberOfPeople = nEnterpriseNumberOfPeople;
    this.dFoundDate = dFoundDate;
    this.nEnterpriseStatus = nEnterpriseStatus;
    this.postalcode = postalcode;
    this.uploadBusinessLicencePictur = uploadBusinessLicencePictur;
    this.organizationCode = organizationCode;
    this.enterpriseLegalRepresentativ = enterpriseLegalRepresentativ;
    this.enterpriseRealmName = enterpriseRealmName;
    this.dCreateDate = dCreateDate;
    this.nCreated = nCreated;
    this.dModifyDate = dModifyDate;
    this.nModified = nModified;
    this.authenticationStatus = authenticationStatus;
    this.businessLicenceRegister = businessLicenceRegister;
    this.businessTimeLimit = businessTimeLimit;
    this.registerBankroll = registerBankroll;
    this.businessScope = businessScope;
    this.bankAccountName = bankAccountName;
    this.bankOfDeposit = bankOfDeposit;
    this.bankOfDepositBranchName = bankOfDepositBranchName;
    this.companyAccount = companyAccount;
    this.nCertificateType = nCertificateType;
    this.certificateNumber = certificateNumber;
    this.location = location;
    this.certificatePicture = certificatePicture;
    this.businessLicenceLocation = businessLicenceLocation;
    this.blinceStartdate = blinceStartdate;
    this.blinceEnddate = blinceEnddate;
    this.blinceVerify = blinceVerify;
    this.cidVerify = cidVerify;
    this.province = province;
    this.city = city;
    this.area = area;
    this.certificateOthersid = certificateOthersid;
    this.taxpayerIdnumber = taxpayerIdnumber;
    this.taxRegCertificateCopy = taxRegCertificateCopy;
    this.gTaxpayerCertificate = gTaxpayerCertificate;
    this.trccopyVerify = trccopyVerify;
    this.gtcVerify = gtcVerify;
    this.ataxVerify = ataxVerify;
    this.reviewChange = reviewChange;
    this.taxRegistrationCno = taxRegistrationCno;
    this.phone = phone;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.N_COMMERCIAL_COPY_ID
   */
  public BigDecimal getnCommercialCopyId() {
    return nCommercialCopyId;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.N_COMMERCIAL_COPY_ID
   * 
   * @param nCommercialCopyId
   */
  public void setnCommercialCopyId(BigDecimal nCommercialCopyId) {
    this.nCommercialCopyId = nCommercialCopyId;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.N_COMMERCIAL_TENANT_ID
   */
  public BigDecimal getnCommercialTenantId() {
    return nCommercialTenantId;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.N_COMMERCIAL_TENANT_ID
   * 
   * @param nCommercialTenantId
   */
  public void setnCommercialTenantId(BigDecimal nCommercialTenantId) {
    this.nCommercialTenantId = nCommercialTenantId;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.N_LOGIN_ID
   */
  public BigDecimal getnLoginId() {
    return nLoginId;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.N_LOGIN_ID
   * 
   * @param nLoginId
   */
  public void setnLoginId(BigDecimal nLoginId) {
    this.nLoginId = nLoginId;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.CONTACTS_NAME
   */
  public String getContactsName() {
    return contactsName;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.CONTACTS_NAME
   * 
   * @param contactsName
   */
  public void setContactsName(String contactsName) {
    this.contactsName = contactsName;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.CONTACTS_DEPARTMENT
   */
  public String getContactsDepartment() {
    return contactsDepartment;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.CONTACTS_DEPARTMENT
   * 
   * @param contactsDepartment
   */
  public void setContactsDepartment(String contactsDepartment) {
    this.contactsDepartment = contactsDepartment;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.FIXED_PHONE
   */
  public String getFixedPhone() {
    return fixedPhone;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.FIXED_PHONE
   * 
   * @param fixedPhone
   */
  public void setFixedPhone(String fixedPhone) {
    this.fixedPhone = fixedPhone;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.MOBILE
   */
  public String getMobile() {
    return mobile;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.MOBILE
   * 
   * @param mobile
   */
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.CONTACTS_EMAIL
   */
  public String getContactsEmail() {
    return contactsEmail;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.CONTACTS_EMAIL
   * 
   * @param contactsEmail
   */
  public void setContactsEmail(String contactsEmail) {
    this.contactsEmail = contactsEmail;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.CORPORATE_NAME
   */
  public String getCorporateName() {
    return corporateName;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.CORPORATE_NAME
   * 
   * @param corporateName
   */
  public void setCorporateName(String corporateName) {
    this.corporateName = corporateName;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.CORPORATE_LOCATION
   */
  public String getCorporateLocation() {
    return corporateLocation;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.CORPORATE_LOCATION
   * 
   * @param corporateLocation
   */
  public void setCorporateLocation(String corporateLocation) {
    this.corporateLocation = corporateLocation;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.CORPORATE_PROPERTY
   */
  public String getCorporateProperty() {
    return corporateProperty;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.CORPORATE_PROPERTY
   * 
   * @param corporateProperty
   */
  public void setCorporateProperty(String corporateProperty) {
    this.corporateProperty = corporateProperty;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.CORPORATE_TRADE
   */
  public String getCorporateTrade() {
    return corporateTrade;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.CORPORATE_TRADE
   * 
   * @param corporateTrade
   */
  public void setCorporateTrade(String corporateTrade) {
    this.corporateTrade = corporateTrade;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.N_ENTERPRISE_NUMBER_OF_PEOPLE
   */
  public BigDecimal getnEnterpriseNumberOfPeople() {
    return nEnterpriseNumberOfPeople;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.N_ENTERPRISE_NUMBER_OF_PEOPLE
   * 
   * @param nEnterpriseNumberOfPeople
   */
  public void setnEnterpriseNumberOfPeople(BigDecimal nEnterpriseNumberOfPeople) {
    this.nEnterpriseNumberOfPeople = nEnterpriseNumberOfPeople;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.D_FOUND_DATE
   */
  public Date getdFoundDate() {
    return dFoundDate;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.D_FOUND_DATE
   * 
   * @param dFoundDate
   */
  public void setdFoundDate(Date dFoundDate) {
    this.dFoundDate = dFoundDate;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.N_ENTERPRISE_STATUS
   */
  public Short getnEnterpriseStatus() {
    return nEnterpriseStatus;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.N_ENTERPRISE_STATUS
   * 
   * @param nEnterpriseStatus
   */
  public void setnEnterpriseStatus(Short nEnterpriseStatus) {
    this.nEnterpriseStatus = nEnterpriseStatus;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.POSTALCODE
   */
  public String getPostalcode() {
    return postalcode;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.POSTALCODE
   * 
   * @param postalcode
   */
  public void setPostalcode(String postalcode) {
    this.postalcode = postalcode;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.UPLOAD_BUSINESS_LICENCE_PICTUR
   */
  public String getUploadBusinessLicencePictur() {
    return uploadBusinessLicencePictur;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.UPLOAD_BUSINESS_LICENCE_PICTUR
   * 
   * @param uploadBusinessLicencePictur
   */
  public void setUploadBusinessLicencePictur(String uploadBusinessLicencePictur) {
    this.uploadBusinessLicencePictur = uploadBusinessLicencePictur;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.ORGANIZATION_CODE
   */
  public String getOrganizationCode() {
    return organizationCode;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.ORGANIZATION_CODE
   * 
   * @param organizationCode
   */
  public void setOrganizationCode(String organizationCode) {
    this.organizationCode = organizationCode;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.ENTERPRISE_LEGAL_REPRESENTATIV
   */
  public String getEnterpriseLegalRepresentativ() {
    return enterpriseLegalRepresentativ;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.ENTERPRISE_LEGAL_REPRESENTATIV
   * 
   * @param enterpriseLegalRepresentativ
   */
  public void setEnterpriseLegalRepresentativ(String enterpriseLegalRepresentativ) {
    this.enterpriseLegalRepresentativ = enterpriseLegalRepresentativ;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.ENTERPRISE_REALM_NAME
   */
  public String getEnterpriseRealmName() {
    return enterpriseRealmName;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.ENTERPRISE_REALM_NAME
   * 
   * @param enterpriseRealmName
   */
  public void setEnterpriseRealmName(String enterpriseRealmName) {
    this.enterpriseRealmName = enterpriseRealmName;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.D_CREATE_DATE
   */
  public Date getdCreateDate() {
    return dCreateDate;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.D_CREATE_DATE
   * 
   * @param dCreateDate
   */
  public void setdCreateDate(Date dCreateDate) {
    this.dCreateDate = dCreateDate;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.N_CREATED
   */
  public BigDecimal getnCreated() {
    return nCreated;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.N_CREATED
   * 
   * @param nCreated
   */
  public void setnCreated(BigDecimal nCreated) {
    this.nCreated = nCreated;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.D_MODIFY_DATE
   */
  public Date getdModifyDate() {
    return dModifyDate;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.D_MODIFY_DATE
   * 
   * @param dModifyDate
   */
  public void setdModifyDate(Date dModifyDate) {
    this.dModifyDate = dModifyDate;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.N_MODIFIED
   */
  public BigDecimal getnModified() {
    return nModified;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.N_MODIFIED
   * 
   * @param nModified
   */
  public void setnModified(BigDecimal nModified) {
    this.nModified = nModified;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.AUTHENTICATION_STATUS
   */
  public Short getAuthenticationStatus() {
    return authenticationStatus;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.AUTHENTICATION_STATUS
   * 
   * @param authenticationStatus
   */
  public void setAuthenticationStatus(Short authenticationStatus) {
    this.authenticationStatus = authenticationStatus;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.BUSINESS_LICENCE_REGISTER
   */
  public String getBusinessLicenceRegister() {
    return businessLicenceRegister;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.BUSINESS_LICENCE_REGISTER
   * 
   * @param businessLicenceRegister
   */
  public void setBusinessLicenceRegister(String businessLicenceRegister) {
    this.businessLicenceRegister = businessLicenceRegister;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.BUSINESS_TIME_LIMIT
   */
  public String getBusinessTimeLimit() {
    return businessTimeLimit;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.BUSINESS_TIME_LIMIT
   * 
   * @param businessTimeLimit
   */
  public void setBusinessTimeLimit(String businessTimeLimit) {
    this.businessTimeLimit = businessTimeLimit;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.REGISTER_BANKROLL
   */
  public BigDecimal getRegisterBankroll() {
    return registerBankroll;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.REGISTER_BANKROLL
   * 
   * @param registerBankroll
   */
  public void setRegisterBankroll(BigDecimal registerBankroll) {
    this.registerBankroll = registerBankroll;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.BUSINESS_SCOPE
   */
  public String getBusinessScope() {
    return businessScope;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.BUSINESS_SCOPE
   * 
   * @param businessScope
   */
  public void setBusinessScope(String businessScope) {
    this.businessScope = businessScope;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.BANK_ACCOUNT_NAME
   */
  public String getBankAccountName() {
    return bankAccountName;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.BANK_ACCOUNT_NAME
   * 
   * @param bankAccountName
   */
  public void setBankAccountName(String bankAccountName) {
    this.bankAccountName = bankAccountName;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.BANK_OF_DEPOSIT
   */
  public String getBankOfDeposit() {
    return bankOfDeposit;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.BANK_OF_DEPOSIT
   * 
   * @param bankOfDeposit
   */
  public void setBankOfDeposit(String bankOfDeposit) {
    this.bankOfDeposit = bankOfDeposit;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.BANK_OF_DEPOSIT_BRANCH_NAME
   */
  public String getBankOfDepositBranchName() {
    return bankOfDepositBranchName;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.BANK_OF_DEPOSIT_BRANCH_NAME
   * 
   * @param bankOfDepositBranchName
   */
  public void setBankOfDepositBranchName(String bankOfDepositBranchName) {
    this.bankOfDepositBranchName = bankOfDepositBranchName;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.COMPANY_ACCOUNT
   */
  public String getCompanyAccount() {
    return companyAccount;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.COMPANY_ACCOUNT
   * 
   * @param companyAccount
   */
  public void setCompanyAccount(String companyAccount) {
    this.companyAccount = companyAccount;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.N_CERTIFICATE_TYPE
   */
  public Short getnCertificateType() {
    return nCertificateType;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.N_CERTIFICATE_TYPE
   * 
   * @param nCertificateType
   */
  public void setnCertificateType(Short nCertificateType) {
    this.nCertificateType = nCertificateType;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.CERTIFICATE_NUMBER
   */
  public String getCertificateNumber() {
    return certificateNumber;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.CERTIFICATE_NUMBER
   * 
   * @param certificateNumber
   */
  public void setCertificateNumber(String certificateNumber) {
    this.certificateNumber = certificateNumber;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.LOCATION
   */
  public String getLocation() {
    return location;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.LOCATION
   * 
   * @param location
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.CERTIFICATE_PICTURE
   */
  public String getCertificatePicture() {
    return certificatePicture;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.CERTIFICATE_PICTURE
   * 
   * @param certificatePicture
   */
  public void setCertificatePicture(String certificatePicture) {
    this.certificatePicture = certificatePicture;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.BUSINESS_LICENCE_LOCATION
   */
  public String getBusinessLicenceLocation() {
    return businessLicenceLocation;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.BUSINESS_LICENCE_LOCATION
   * 
   * @param businessLicenceLocation
   */
  public void setBusinessLicenceLocation(String businessLicenceLocation) {
    this.businessLicenceLocation = businessLicenceLocation;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.BLINCE_STARTDATE
   */
  public Date getBlinceStartdate() {
    return blinceStartdate;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.BLINCE_STARTDATE
   * 
   * @param blinceStartdate
   */
  public void setBlinceStartdate(Date blinceStartdate) {
    this.blinceStartdate = blinceStartdate;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.BLINCE_ENDDATE
   */
  public Date getBlinceEnddate() {
    return blinceEnddate;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.BLINCE_ENDDATE
   * 
   * @param blinceEnddate
   */
  public void setBlinceEnddate(Date blinceEnddate) {
    this.blinceEnddate = blinceEnddate;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.BLINCE_VERIFY
   */
  public Short getBlinceVerify() {
    return blinceVerify;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.BLINCE_VERIFY
   * 
   * @param blinceVerify
   */
  public void setBlinceVerify(Short blinceVerify) {
    this.blinceVerify = blinceVerify;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.CID_VERIFY
   */
  public Short getCidVerify() {
    return cidVerify;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.CID_VERIFY
   * 
   * @param cidVerify
   */
  public void setCidVerify(Short cidVerify) {
    this.cidVerify = cidVerify;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.PROVINCE
   */
  public String getProvince() {
    return province;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.PROVINCE
   * 
   * @param province
   */
  public void setProvince(String province) {
    this.province = province;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.CITY
   */
  public String getCity() {
    return city;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.CITY
   * 
   * @param city
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.AREA
   */
  public String getArea() {
    return area;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.AREA
   * 
   * @param area
   */
  public void setArea(String area) {
    this.area = area;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.CERTIFICATE_OTHERSID
   */
  public String getCertificateOthersid() {
    return certificateOthersid;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.CERTIFICATE_OTHERSID
   * 
   * @param certificateOthersid
   */
  public void setCertificateOthersid(String certificateOthersid) {
    this.certificateOthersid = certificateOthersid;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.TAXPAYER_IDNUMBER
   */
  public String getTaxpayerIdnumber() {
    return taxpayerIdnumber;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.TAXPAYER_IDNUMBER
   * 
   * @param taxpayerIdnumber
   */
  public void setTaxpayerIdnumber(String taxpayerIdnumber) {
    this.taxpayerIdnumber = taxpayerIdnumber;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.TAX_REG_CERTIFICATE_COPY
   */
  public String getTaxRegCertificateCopy() {
    return taxRegCertificateCopy;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.TAX_REG_CERTIFICATE_COPY
   * 
   * @param taxRegCertificateCopy
   */
  public void setTaxRegCertificateCopy(String taxRegCertificateCopy) {
    this.taxRegCertificateCopy = taxRegCertificateCopy;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.G_TAXPAYER_CERTIFICATE
   */
  public String getgTaxpayerCertificate() {
    return gTaxpayerCertificate;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.G_TAXPAYER_CERTIFICATE
   * 
   * @param gTaxpayerCertificate
   */
  public void setgTaxpayerCertificate(String gTaxpayerCertificate) {
    this.gTaxpayerCertificate = gTaxpayerCertificate;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.TRCCOPY_VERIFY
   */
  public Short getTrccopyVerify() {
    return trccopyVerify;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.TRCCOPY_VERIFY
   * 
   * @param trccopyVerify
   */
  public void setTrccopyVerify(Short trccopyVerify) {
    this.trccopyVerify = trccopyVerify;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.GTC_VERIFY
   */
  public Short getGtcVerify() {
    return gtcVerify;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.GTC_VERIFY
   * 
   * @param gtcVerify
   */
  public void setGtcVerify(Short gtcVerify) {
    this.gtcVerify = gtcVerify;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.ATAX_VERIFY
   */
  public Short getAtaxVerify() {
    return ataxVerify;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.ATAX_VERIFY
   * 
   * @param ataxVerify
   */
  public void setAtaxVerify(Short ataxVerify) {
    this.ataxVerify = ataxVerify;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.REVIEW_CHANGE
   */
  public Short getReviewChange() {
    return reviewChange;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.REVIEW_CHANGE
   * 
   * @param reviewChange
   */
  public void setReviewChange(Short reviewChange) {
    this.reviewChange = reviewChange;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.TAX_REGISTRATION_CNO
   */
  public String getTaxRegistrationCno() {
    return taxRegistrationCno;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.TAX_REGISTRATION_CNO
   * 
   * @param taxRegistrationCno
   */
  public void setTaxRegistrationCno(String taxRegistrationCno) {
    this.taxRegistrationCno = taxRegistrationCno;
  }

  /**
   * getter for Column COMMERCIAL_TENANT_BASIC_COPY.PHONE
   */
  public String getPhone() {
    return phone;
  }

  /**
   * setter for Column COMMERCIAL_TENANT_BASIC_COPY.PHONE
   * 
   * @param phone
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }



}
