package com.kmzyc.supplier.model;

import java.io.Serializable;
import java.util.Date;

public class ShopMain implements Serializable {

    private static final long serialVersionUID = -8612867832520698182L;

    private Long shopId;

    private Long supplierId;

    private Short shopType;

    private String shopCode;

    private String shopName;

    private String shopTitle;

    private String logoPath;

    private String physhopImagePath;

    private String filePath;

    private String contactInfo;

    private String introduce;

    private Integer shopLevel;

    private String status;

    private String auditStatus;

//    private Long provinceId;

//    private Long cityId;

//    private Long countyId;

//    private Long shopManager;

//    private String corpName;

//    private String corpIdcode;

//    private String regNumber;

//    private String address;

    private String defaultDomainUrl;

    private String shopSite;

    private String shopSeoKey;

    private Long applyUser;

    private Date auditTime;

    private Long auditUser;

    private String remark;

    private String describe;

    private String describe_lazy;

    private String manageBrand;

    private String linkmanMobile;

    private String principalName;

    private String serviceQq;

    private Short serviceType;


    /**
     * 供应商名称
     */
    private String corporateName;

    /**
     * 主页模板类型
     */
    private Integer templateType;
    private Date applyTime;

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Short getShopType() {
        return shopType;
    }

    public void setShopType(Short shopType) {
        this.shopType = shopType;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getPhyshopImagePath() {
        return physhopImagePath;
    }

    public void setPhyshopImagePath(String physhopImagePath) {
        this.physhopImagePath = physhopImagePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(Integer shopLevel) {
        this.shopLevel = shopLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getDefaultDomainUrl() {
        return defaultDomainUrl;
    }

    public void setDefaultDomainUrl(String defaultDomainUrl) {
        this.defaultDomainUrl = defaultDomainUrl;
    }

    public String getShopSite() {
        return shopSite;
    }

    public void setShopSite(String shopSite) {
        this.shopSite = shopSite;
    }

    public String getShopSeoKey() {
        return shopSeoKey;
    }

    public void setShopSeoKey(String shopSeoKey) {
        this.shopSeoKey = shopSeoKey;
    }

    public Long getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(Long applyUser) {
        this.applyUser = applyUser;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Long getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(Long auditUser) {
        this.auditUser = auditUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getManageBrand() {
        return manageBrand;
    }

    public void setManageBrand(String manageBrand) {
        this.manageBrand = manageBrand;
    }

    public String getLinkmanMobile() {
        return linkmanMobile;
    }

    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile;
    }

    public String getDescribe_lazy() {
        return describe_lazy;
    }

    public void setDescribe_lazy(String describe_lazy) {
        this.describe_lazy = describe_lazy;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getServiceQq() {
        return serviceQq;
    }

    public void setServiceQq(String serviceQq) {
        this.serviceQq = serviceQq;
    }

    public Short getServiceType() {
        return serviceType;
    }

    public void setServiceType(Short serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }
}