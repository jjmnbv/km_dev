package com.kmzyc.supplier.model;

import java.io.Serializable;
import java.util.List;

public class SuppliersInfo implements Serializable {

    private static final long serialVersionUID = -1900155130200882902L;
    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 商户ID
     */
    private Long merchantId;

    /**
     * 供应商品描述
     */
    private String saleProductDescribe;

    /**
     * 单位（天）
     */
    private Short settlementCycle;

    /**
     * 余额 银行
     */
    private Short settlementWay;

    /**
     * 经销商 厂商
     */
    private Short supplierType;

    /**
     * 1、待申请 2、提交申请 3、审核通过 4、审核不通过
     */
    private Short status;

    /**
     * 描述
     */
    private String describe;

    /**
     * 控制登录状态
     */
    private Short loginStatus;

    /**
     * 控制业务状态
     *
     * @ibatorgenerated Tue Apr 07 16:46:55 CST 2015
     */
    private Short businessStatus;

    /**
     * 控制开关
     *
     * @ibatorgenerated Tue Apr 07 16:46:55 CST 2015
     */
    private Short closeStatus;


    /**
     * 供应商店铺浏览量启用状态
     * 1.关闭  2.启用
     */
    private Short shopBrowseStatus;

    private String companyShowName;//公司显示名称

    private List<ShopMain> shopMainList;

    public List<ShopMain> getShopMainList() {
        return shopMainList;
    }

    public void setShopMainList(List<ShopMain> shopMainList) {
        this.shopMainList = shopMainList;
    }

    /**
     * 获取 供应商ID
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * 设置  供应商ID
     *
     * @param supplierId
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 获取 商户ID
     */
    public Long getMerchantId() {
        return merchantId;
    }

    /**
     * 设置  商户ID
     *
     * @param merchantId
     */
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * 获取 供应商品描述
     */
    public String getSaleProductDescribe() {
        return saleProductDescribe;
    }

    /**
     * 设置  供应商品描述
     *
     * @param saleProductDescribe
     */
    public void setSaleProductDescribe(String saleProductDescribe) {
        this.saleProductDescribe = saleProductDescribe;
    }

    /**
     * 获取 单位（天）
     */
    public Short getSettlementCycle() {
        return settlementCycle;
    }

    /**
     * 设置  单位（天）
     *
     * @param settlementCycle
     */
    public void setSettlementCycle(Short settlementCycle) {
        this.settlementCycle = settlementCycle;
    }

    /**
     * 获取 余额银行
     */
    public Short getSettlementWay() {
        return settlementWay;
    }

    /**
     * 设置  余额银行
     *
     * @param settlementWay
     */
    public void setSettlementWay(Short settlementWay) {
        this.settlementWay = settlementWay;
    }

    /**
     * 获取 经销商厂商
     */
    public Short getSupplierType() {
        return supplierType;
    }

    /**
     * 设置  经销商厂商
     *
     * @param supplierType
     */
    public void setSupplierType(Short supplierType) {
        this.supplierType = supplierType;
    }

    /**
     * 获取 1、待申请  2、提交申请  3、审核通过  4、审核不通过
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 设置  1、待申请 2、提交申请 3、审核通过 4、审核不通过
     *
     * @param status
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * 获取 描述
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * 设置  描述
     *
     * @param describe
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "SuppliersInfo [supplierId=" + supplierId + ", merchantId="
                + merchantId + ", saleProductDescribe=" + saleProductDescribe
                + ", settlementCycle=" + settlementCycle + ", settlementWay="
                + settlementWay + ", supplierType=" + supplierType
                + ", status=" + status + ", describe=" + describe + "]";
    }

    public Short getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Short loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Short getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(Short businessStatus) {
        this.businessStatus = businessStatus;
    }

    public Short getCloseStatus() {
        return closeStatus;
    }

    public void setCloseStatus(Short closeStatus) {
        this.closeStatus = closeStatus;
    }

    public Short getShopBrowseStatus() {
        return shopBrowseStatus;
    }

    public void setShopBrowseStatus(Short shopBrowseStatus) {
        this.shopBrowseStatus = shopBrowseStatus;
    }

    public String getCompanyShowName() {
        return companyShowName;
    }

    public void setCompanyShowName(String companyShowName) {
        this.companyShowName = companyShowName;
    }


}