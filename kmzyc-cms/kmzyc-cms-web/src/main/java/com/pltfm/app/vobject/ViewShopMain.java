package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 供应商店铺视图对象
 */
public class ViewShopMain implements Serializable {

    private static final long serialVersionUID = -8612867832520698182L;


    private Double freePrice;//多少钱免运费
    private Double firstHeavyFreight;//首重多少钱
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


    private String defaultDomainUrl;


    private String shopSite;


    private String shopSeoKey;


    private Long applyUser;


    private Date applyTime;

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


    public Double getFreePrice() {
        return freePrice;
    }

    public void setFreePrice(Double freePrice) {
        this.freePrice = freePrice;
    }

    public Double getFirstHeavyFreight() {
        return firstHeavyFreight;
    }

    public void setFirstHeavyFreight(Double firstHeavyFreight) {
        this.firstHeavyFreight = firstHeavyFreight;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.SHOP_ID
     *
     * @return the value of SHOP_MAIN.SHOP_ID
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public Long getShopId() {
        return shopId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.SHOP_ID
     *
     * @param shopId the value for SHOP_MAIN.SHOP_ID
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.SUPPLIER_ID
     *
     * @return the value of SHOP_MAIN.SUPPLIER_ID
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.SUPPLIER_ID
     *
     * @param supplierId the value for SHOP_MAIN.SUPPLIER_ID
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.SHOP_TYPE
     *
     * @return the value of SHOP_MAIN.SHOP_TYPE
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public Short getShopType() {
        return shopType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.SHOP_TYPE
     *
     * @param shopType the value for SHOP_MAIN.SHOP_TYPE
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setShopType(Short shopType) {
        this.shopType = shopType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.SHOP_CODE
     *
     * @return the value of SHOP_MAIN.SHOP_CODE
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getShopCode() {
        return shopCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.SHOP_CODE
     *
     * @param shopCode the value for SHOP_MAIN.SHOP_CODE
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.SHOP_NAME
     *
     * @return the value of SHOP_MAIN.SHOP_NAME
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.SHOP_NAME
     *
     * @param shopName the value for SHOP_MAIN.SHOP_NAME
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.SHOP_TITLE
     *
     * @return the value of SHOP_MAIN.SHOP_TITLE
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getShopTitle() {
        return shopTitle;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.SHOP_TITLE
     *
     * @param shopTitle the value for SHOP_MAIN.SHOP_TITLE
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.LOGO_PATH
     *
     * @return the value of SHOP_MAIN.LOGO_PATH
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getLogoPath() {
        return logoPath;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.LOGO_PATH
     *
     * @param logoPath the value for SHOP_MAIN.LOGO_PATH
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.PHYSHOP_IMAGE_PATH
     *
     * @return the value of SHOP_MAIN.PHYSHOP_IMAGE_PATH
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getPhyshopImagePath() {
        return physhopImagePath;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.PHYSHOP_IMAGE_PATH
     *
     * @param physhopImagePath the value for SHOP_MAIN.PHYSHOP_IMAGE_PATH
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setPhyshopImagePath(String physhopImagePath) {
        this.physhopImagePath = physhopImagePath;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.FILE_PATH
     *
     * @return the value of SHOP_MAIN.FILE_PATH
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.FILE_PATH
     *
     * @param filePath the value for SHOP_MAIN.FILE_PATH
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.CONTACT_INFO
     *
     * @return the value of SHOP_MAIN.CONTACT_INFO
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.CONTACT_INFO
     *
     * @param contactInfo the value for SHOP_MAIN.CONTACT_INFO
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.INTRODUCE
     *
     * @return the value of SHOP_MAIN.INTRODUCE
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.INTRODUCE
     *
     * @param introduce the value for SHOP_MAIN.INTRODUCE
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.SHOP_LEVEL
     *
     * @return the value of SHOP_MAIN.SHOP_LEVEL
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public Integer getShopLevel() {
        return shopLevel;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.SHOP_LEVEL
     *
     * @param shopLevel the value for SHOP_MAIN.SHOP_LEVEL
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setShopLevel(Integer shopLevel) {
        this.shopLevel = shopLevel;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.STATUS
     *
     * @return the value of SHOP_MAIN.STATUS
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.STATUS
     *
     * @param status the value for SHOP_MAIN.STATUS
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.AUDIT_STATUS
     *
     * @return the value of SHOP_MAIN.AUDIT_STATUS
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getAuditStatus() {
        return auditStatus;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.AUDIT_STATUS
     *
     * @param auditStatus the value for SHOP_MAIN.AUDIT_STATUS
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.DEFAULT_DOMAIN_URL
     *
     * @return the value of SHOP_MAIN.DEFAULT_DOMAIN_URL
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getDefaultDomainUrl() {
        return defaultDomainUrl;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.DEFAULT_DOMAIN_URL
     *
     * @param defaultDomainUrl the value for SHOP_MAIN.DEFAULT_DOMAIN_URL
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setDefaultDomainUrl(String defaultDomainUrl) {
        this.defaultDomainUrl = defaultDomainUrl;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.SHOP_SITE
     *
     * @return the value of SHOP_MAIN.SHOP_SITE
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getShopSite() {
        return shopSite;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.SHOP_SITE
     *
     * @param shopSite the value for SHOP_MAIN.SHOP_SITE
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setShopSite(String shopSite) {
        this.shopSite = shopSite;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.SHOP_SEO_KEY
     *
     * @return the value of SHOP_MAIN.SHOP_SEO_KEY
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getShopSeoKey() {
        return shopSeoKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.SHOP_SEO_KEY
     *
     * @param shopSeoKey the value for SHOP_MAIN.SHOP_SEO_KEY
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setShopSeoKey(String shopSeoKey) {
        this.shopSeoKey = shopSeoKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.APPLY_USER
     *
     * @return the value of SHOP_MAIN.APPLY_USER
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public Long getApplyUser() {
        return applyUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.APPLY_USER
     *
     * @param applyUser the value for SHOP_MAIN.APPLY_USER
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setApplyUser(Long applyUser) {
        this.applyUser = applyUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.APPLY_TIME
     *
     * @return the value of SHOP_MAIN.APPLY_TIME
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.APPLY_TIME
     *
     * @param applyTime the value for SHOP_MAIN.APPLY_TIME
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.AUDIT_TIME
     *
     * @return the value of SHOP_MAIN.AUDIT_TIME
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.AUDIT_TIME
     *
     * @param auditTime the value for SHOP_MAIN.AUDIT_TIME
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.AUDIT_USER
     *
     * @return the value of SHOP_MAIN.AUDIT_USER
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public Long getAuditUser() {
        return auditUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.AUDIT_USER
     *
     * @param auditUser the value for SHOP_MAIN.AUDIT_USER
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setAuditUser(Long auditUser) {
        this.auditUser = auditUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.REMARK
     *
     * @return the value of SHOP_MAIN.REMARK
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.REMARK
     *
     * @param remark the value for SHOP_MAIN.REMARK
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SHOP_MAIN.describe
     *
     * @return the value of SHOP_MAIN.describe
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SHOP_MAIN.describe
     *
     * @param describe the value for SHOP_MAIN.describe
     * @ibatorgenerated Thu Apr 10 11:17:54 CST 2014
     */
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
