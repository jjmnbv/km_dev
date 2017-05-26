package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能：供应商导出实体对象
 *
 * @author Zhoujiwei
 * @since 2016/11/11 15:08
 */
public class SupplierForExport implements Serializable{

    private static final long serialVersionUID = -8691327987455197199L;

    /**
     * 联系人手机
     */
    private String mobile;

    /**
     * 公司名称
     */
    private String corporateName;

    /**
     * 组织机构代码
     */
    private String organizationCode;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 营业执照注册号
     */
    private String businessLicenceRegister;

    /**
     * 公司所在地
     */
    private String corporateLocation;
    private String province;
    private String city;
    private String area;
    /**
     * 企业状态
     */
    private String enterpriseStatus;

    /**
     * 商户类型
     */
    private Short supplierType;

    /**
     * 申请状态
     */
    private Short supplierStatus;

    /**
     * 固定电话
     */
    private String fixedPhone;

    /**
     * 税务登记证号
     */
    private String taxRegistrationCno;

    /**
     * 法定营业范围
     */
    private String businessScope;

    /**
     * 开户银行
     */
    private String bankOfDeposit;
    /**
     * 银行开户名
     */
    private String bankAccountName;

    /**
     * 银行账号
     */
    private String companyAccount;

    /**
     * 注册资金
     */
    private Double registerBankroll;

    /**
     * 商户一级经营类目
     */
    private String firstCategoryName;

    /**
     * 商户二级经营类目
     */
    private String secondCategoryName;

    /**
     * 商户仓库
     */
    private String warehouseName;

    @Override
    public String toString() {
        return "SupplierForExport{" +
                "mobile='" + mobile + '\'' +
                ", corporateName='" + corporateName + '\'' +
                ", organizationCode='" + organizationCode + '\'' +
                ", createDate=" + createDate +
                ", businessLicenceRegister='" + businessLicenceRegister + '\'' +
                ", corporateLocation='" + corporateLocation + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", enterpriseStatus='" + enterpriseStatus + '\'' +
                ", supplierType=" + supplierType +
                ", supplierStatus=" + supplierStatus +
                ", fixedPhone='" + fixedPhone + '\'' +
                ", taxRegistrationCno='" + taxRegistrationCno + '\'' +
                ", businessScope='" + businessScope + '\'' +
                ", bankOfDeposit='" + bankOfDeposit + '\'' +
                ", bankAccountName='" + bankAccountName + '\'' +
                ", companyAccount='" + companyAccount + '\'' +
                ", registerBankroll=" + registerBankroll +
                ", firstCategoryName='" + firstCategoryName + '\'' +
                ", secondCategoryName='" + secondCategoryName + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                '}';
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getBusinessLicenceRegister() {
        return businessLicenceRegister;
    }

    public void setBusinessLicenceRegister(String businessLicenceRegister) {
        this.businessLicenceRegister = businessLicenceRegister;
    }

    public String getCorporateLocation() {
        return corporateLocation;
    }

    public void setCorporateLocation(String corporateLocation) {
        this.corporateLocation = corporateLocation;
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

    public String getEnterpriseStatus() {
        return enterpriseStatus;
    }

    public void setEnterpriseStatus(String enterpriseStatus) {
        this.enterpriseStatus = enterpriseStatus;
    }

    public Short getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(Short supplierType) {
        this.supplierType = supplierType;
    }

    public Short getSupplierStatus() {
        return supplierStatus;
    }

    public void setSupplierStatus(Short supplierStatus) {
        this.supplierStatus = supplierStatus;
    }

    public String getFixedPhone() {
        return fixedPhone;
    }

    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone;
    }

    public String getTaxRegistrationCno() {
        return taxRegistrationCno;
    }

    public void setTaxRegistrationCno(String taxRegistrationCno) {
        this.taxRegistrationCno = taxRegistrationCno;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getBankOfDeposit() {
        return bankOfDeposit;
    }

    public void setBankOfDeposit(String bankOfDeposit) {
        this.bankOfDeposit = bankOfDeposit;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount;
    }

    public Double getRegisterBankroll() {
        return registerBankroll;
    }

    public void setRegisterBankroll(Double registerBankroll) {
        this.registerBankroll = registerBankroll;
    }

    public String getFirstCategoryName() {
        return firstCategoryName;
    }

    public void setFirstCategoryName(String firstCategoryName) {
        this.firstCategoryName = firstCategoryName;
    }

    public String getSecondCategoryName() {
        return secondCategoryName;
    }

    public void setSecondCategoryName(String secondCategoryName) {
        this.secondCategoryName = secondCategoryName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}