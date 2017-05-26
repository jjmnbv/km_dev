package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/11/11 15:08
 */
public class ShopForExport implements Serializable {

    private static final long serialVersionUID = -3529694989843710987L;

    /**
     * 店铺标题
     */
    private String shopTitle;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺状态
     */
    private String status;

    /**
     * 审核状态
     */
    private String auditStatus;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 商户名称
     */
    private String corporateName;

    /**
     * 经营品牌
     */
    private String manageBrand;

    /**
     * 店铺负责人姓名
     */
    private String principalName;

    /**
     * 店铺负责人电话
     */
    private String linkmanMobile;

    /**
     * 店铺负责人联系类型
     */
    private Short serviceType;

    /**
     * 客服联系
     */
    private String serviceQq;

    /**
     * 店铺类型
     */
    private Short shopType;

    @Override
    public String toString() {
        return "ShopForExport{" +
                "shopTitle='" + shopTitle + '\'' +
                ", shopName='" + shopName + '\'' +
                ", status='" + status + '\'' +
                ", auditStatus='" + auditStatus + '\'' +
                ", applyTime=" + applyTime +
                ", corporateName='" + corporateName + '\'' +
                ", manageBrand='" + manageBrand + '\'' +
                ", principalName='" + principalName + '\'' +
                ", linkmanMobile='" + linkmanMobile + '\'' +
                ", serviceType=" + serviceType +
                ", serviceQq='" + serviceQq + '\'' +
                ", shopType=" + shopType +
                '}';
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }

    public String getShopName() {
        return shopName;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getManageBrand() {
        return manageBrand;
    }

    public void setManageBrand(String manageBrand) {
        this.manageBrand = manageBrand;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getLinkmanMobile() {
        return linkmanMobile;
    }

    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile;
    }

    public Short getServiceType() {
        return serviceType;
    }

    public void setServiceType(Short serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceQq() {
        return serviceQq;
    }

    public void setServiceQq(String serviceQq) {
        this.serviceQq = serviceQq;
    }

    public Short getShopType() {
        return shopType;
    }

    public void setShopType(Short shopType) {
        this.shopType = shopType;
    }
}