package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/11/11 15:08
 */
public class ProductSkuForExport implements Serializable{

    private static final long serialVersionUID = -3965566860224968931L;

    /**
     * 品牌
     */
    private String brandName;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品标题
     */
    private String productTitle;

    /**
     * sku描述
     */
    private String ProductSkuDesc;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 商品编码
     */
    private String productNo;

    /**
     * 商品SKU编码
     */
    private String ProductSkuCode;

    /**
     * SKU状态
     */
    private String productSkuStatus;

    /**
     * 成本价
     */
    private Double costPrice;

    /**
     * 市场价
     */
    private Double markPrice;

    /**
     * 销售单价
     */
    private Double price;

    /**
     * PV值
     */
    private String pvValue;

    /**
     * 上架/下架
     */
    private String status;

    /**
     * 一级类目
     */
    private String firstCategoryName;

    /**
     * 二级类目
     */
    private String secondCategoryName;

    /**
     * 三级类目
     */
    private String thirdCategoryName;

    /**
     * 商家
     */
    private String supplierName;

    /**
     * 商家类型
     */
    private String supplierType;

    /**
     * 上架时间
     */
    private Date upTime;

    /**
     * 下架时间
     */
    private Date archiveTime;

    /**
     * 批注
     */
    private String postil;

    /**
     * skuId
     */
    private Long productSkuId;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductSkuDesc() {
        return ProductSkuDesc;
    }

    public void setProductSkuDesc(String productSkuDesc) {
        ProductSkuDesc = productSkuDesc;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductSkuCode() {
        return ProductSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        ProductSkuCode = productSkuCode;
    }

    public String getProductSkuStatus() {
        return productSkuStatus;
    }

    public void setProductSkuStatus(String productSkuStatus) {
        this.productSkuStatus = productSkuStatus;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getMarkPrice() {
        return markPrice;
    }

    public void setMarkPrice(Double markPrice) {
        this.markPrice = markPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPvValue() {
        return pvValue;
    }

    public void setPvValue(String pvValue) {
        this.pvValue = pvValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getThirdCategoryName() {
        return thirdCategoryName;
    }

    public void setThirdCategoryName(String thirdCategoryName) {
        this.thirdCategoryName = thirdCategoryName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public Date getArchiveTime() {
        return archiveTime;
    }

    public void setArchiveTime(Date archiveTime) {
        this.archiveTime = archiveTime;
    }

    public String getPostil() {
        return postil;
    }

    public void setPostil(String postil) {
        this.postil = postil;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    @Override
    public String toString() {
        return "ProductSkuForExport{" +
                "brandName='" + brandName + '\'' +
                ", productName='" + productName + '\'' +
                ", productTitle='" + productTitle + '\'' +
                ", ProductSkuDesc='" + ProductSkuDesc + '\'' +
                ", productType='" + productType + '\'' +
                ", productNo=" + productNo +
                ", ProductSkuCode=" + ProductSkuCode +
                ", productSkuStatus='" + productSkuStatus + '\'' +
                ", costPrice=" + costPrice +
                ", markPrice=" + markPrice +
                ", price=" + price +
                ", pvValue=" + pvValue +
                ", status='" + status + '\'' +
                ", firstCategoryName='" + firstCategoryName + '\'' +
                ", secondCategoryName='" + secondCategoryName + '\'' +
                ", thirdCategoryName='" + thirdCategoryName + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", supplierType='" + supplierType + '\'' +
                ", upTime=" + upTime +
                ", archiveTime=" + archiveTime +
                ", postil='" + postil + '\'' +
                ", productSkuId=" + productSkuId +
                '}';
    }

}