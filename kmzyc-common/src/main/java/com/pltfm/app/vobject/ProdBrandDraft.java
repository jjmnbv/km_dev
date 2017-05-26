package com.pltfm.app.vobject;


import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class ProdBrandDraft implements java.io.Serializable {

    private static final long serialVersionUID = 3867087353402944475L;

    private Long brandId;

    private String brandName;

    private String nation;

    private String logoPath;

    private String engName;

    private String chnSpell;

    private String status;

    private Long createUser;

    private Date createTime;

    private Long checkUser;

    private Date checkTime;

    private Long modifUser;

    private Date modifTime;

    private Date applyTime;

    private Date beforeApplyTime;

    private Date endApplyTime;

    private String shopCode;

    private String merchantName;

    private String reasons;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        if (StringUtils.isNotBlank(brandName)) {
            this.brandName = brandName.trim();
        }
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public Date getBeforeApplyTime() {
        return beforeApplyTime;
    }

    public void setBeforeApplyTime(Date beforeApplyTime) {
        this.beforeApplyTime = beforeApplyTime;
    }

    public Date getEndApplyTime() {
        return endApplyTime;
    }

    public void setEndApplyTime(Date endApplyTime) {
        this.endApplyTime = endApplyTime;
    }

    public String getChnSpell() {
        return chnSpell;
    }

    public void setChnSpell(String chnSpell) {
        this.chnSpell = chnSpell;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(Long checkUser) {
        this.checkUser = checkUser;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Long getModifUser() {
        return modifUser;
    }

    public void setModifUser(Long modifUser) {
        this.modifUser = modifUser;
    }

    public Date getModifTime() {
        return modifTime;
    }

    public void setModifTime(Date modifTime) {
        this.modifTime = modifTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    @Override
    public String toString() {
        return "ProdBrandDraft{" +
                "brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", nation='" + nation + '\'' +
                ", logoPath='" + logoPath + '\'' +
                ", engName='" + engName + '\'' +
                ", chnSpell='" + chnSpell + '\'' +
                ", status='" + status + '\'' +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", checkUser=" + checkUser +
                ", checkTime=" + checkTime +
                ", modifUser=" + modifUser +
                ", modifTime=" + modifTime +
                ", applyTime=" + applyTime +
                ", beforeApplyTime=" + beforeApplyTime +
                ", endApplyTime=" + endApplyTime +
                ", shopCode='" + shopCode + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", reasons='" + reasons + '\'' +
                '}';
    }
}