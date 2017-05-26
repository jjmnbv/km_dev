package com.kmzyc.supplier.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MerchantInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7460720891118955965L;

	/**
	 * 商户ID
	 */
	private Long merchantId;

    /**
     * 登录ID
     */
    private Long loginId;

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
     *公司行业
     */
    private String corporateTrade;

    /**
     * 企业人数
     */
    private Long enterpriseNumberOfPeople;

    /**
     * 成立日期
     */
    private Date foundDate;

    /**
     * "企业状态：0.禁用 1.可用"
     */
    private Short enterpriseStatus;

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
    private Date createDate;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 修改日期
     */
    private Date modifyDate;

    /**
     * 修改人
     */
    private Long modifyUser;

    /**
     * "认证状态：0.未认证 1.通过 2.待认证 3.不通过"
     */
    private Short authenticationStatus;

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
    private BigDecimal registerBankroll;

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
     * "证件类型：0.身份证1.护照 2.回乡证"
     */
    private Short certificateType;

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
    
    /**
     * 税务登记证号
     */
    private String taxRegistrationCno;
    
    /**
     * 联系人电话号码
     */
    private String phone;
    
    /**
     * 营业执照有效开始时间
     */
    private Date blinceStartdate;
    
    /**
     * 营业执照有效结束时间
     */
    private Date blinceEnddate;

    /**
     * 纳税人识别码
     */
    private String taxpayerIdnumber;
    
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    
    private String organizationUrl;
    
    private String taxRegCertificateCopy;
    
    private String headSculpture;//头像

    public String getHeadSculpture() {
		return headSculpture;
	}

	public void setHeadSculpture(String headSculpture) {
		this.headSculpture = headSculpture;
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

	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public Long getEnterpriseNumberOfPeople() {
		return enterpriseNumberOfPeople;
	}

	public void setEnterpriseNumberOfPeople(Long enterpriseNumberOfPeople) {
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Short getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(Short certificateType) {
		this.certificateType = certificateType;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Long getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(Long modifyUser) {
		this.modifyUser = modifyUser;
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

	public String getTaxpayerIdnumber() {
		return taxpayerIdnumber;
	}

	public void setTaxpayerIdnumber(String taxpayerIdnumber) {
		this.taxpayerIdnumber = taxpayerIdnumber;
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

	public String getOrganizationUrl() {
		return organizationUrl;
	}

	public void setOrganizationUrl(String organizationUrl) {
		this.organizationUrl = organizationUrl;
	}

	public String getTaxRegCertificateCopy() {
		return taxRegCertificateCopy;
	}

	public void setTaxRegCertificateCopy(String taxRegCertificateCopy) {
		this.taxRegCertificateCopy = taxRegCertificateCopy;
	}
}