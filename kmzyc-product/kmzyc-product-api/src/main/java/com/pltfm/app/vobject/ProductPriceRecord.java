package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

public class ProductPriceRecord implements Serializable {

    private static final long serialVersionUID = -3126093253561767673L;

    private Long recordId;

    private Long productId;

    private Long productSkuId;

    private Long productSkuCode;

    private Double price;
    
    private Double markPrice;

    private Double unitWeight;

    private Double pvValue;

    private Long createUser;

    private String createUserName;

    private String supplierAccount;

    private Date createTime;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

	public Double getMarkPrice() {
		return markPrice;
	}

	public void setMarkPrice(Double markPrice) {
		this.markPrice = markPrice;
	}

	public Double getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Long getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(Long productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public String getSupplierAccount() {
        return supplierAccount;
    }

    public void setSupplierAccount(String supplierAccount) {
        this.supplierAccount = supplierAccount;
    }

    public Double getPvValue() {
        return pvValue;
    }

    public void setPvValue(Double pvValue) {
        this.pvValue = pvValue;
    }

    @Override
    public String toString() {
        return "ProductPriceRecord{" +
                "recordId=" + recordId +
                ", productId=" + productId +
                ", productSkuId=" + productSkuId +
                ", productSkuCode=" + productSkuCode +
                ", price=" + price +
                ", markPrice=" + markPrice +
                ", unitWeight=" + unitWeight +
                ", pvValue=" + pvValue +
                ", createUser=" + createUser +
                ", createUserName='" + createUserName + '\'' +
                ", supplierAccount='" + supplierAccount + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public ProductPriceRecord() {
    }
}