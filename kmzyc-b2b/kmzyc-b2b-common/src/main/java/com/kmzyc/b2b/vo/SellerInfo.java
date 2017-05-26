package com.kmzyc.b2b.vo;

import java.io.Serializable;

/**
 * 商家信息
 * 
 * @author xlg
 * 
 */
public class SellerInfo implements Serializable {
  /**
	 * 
	 */
  private static final long serialVersionUID = -8242675790448302054L;
  /**
   * 店铺ID
   */
  private Long shopId;
  /**
   * 商家ID
   */
  private Long merchantId;
  /**
   * 商家类型
   */
  private Short supplierType;
  /**
   * 商家状态
   */
  private Short status;
  /**
   * 店铺名称
   */
  private String shopName;

  /**
   * 商家公司
   */
  private String merchantCompany;
  /**
   * 登录ID
   */
  private Long loginId;
  /**
   * 商家性质
   */
  private String merchantProperty;

  /**
   * 商家地区 省-市
   */
  private String merchantArea;

  /**
   * 商家联系方式
   */
  private String merchantEmail;

  public Long getShopId() {
    return shopId;
  }

  public void setShopId(Long shopId) {
    this.shopId = shopId;
  }

  public Long getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(Long merchantId) {
    this.merchantId = merchantId;
  }

  public Short getSupplierType() {
    return supplierType;
  }

  public void setSupplierType(Short supplierType) {
    this.supplierType = supplierType;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public String getMerchantCompany() {
    return merchantCompany;
  }

  public void setMerchantCompany(String merchantCompany) {
    this.merchantCompany = merchantCompany;
  }

  public Long getLoginId() {
    return loginId;
  }

  public void setLoginId(Long loginId) {
    this.loginId = loginId;
  }

  public String getMerchantProperty() {
    return merchantProperty;
  }

  public void setMerchantProperty(String merchantProperty) {
    this.merchantProperty = merchantProperty;
  }

  public String getMerchantArea() {
    return merchantArea;
  }

  public void setMerchantArea(String merchantArea) {
    this.merchantArea = merchantArea;
  }

  public String getMerchantEmail() {
    return merchantEmail;
  }

  public void setMerchantEmail(String merchantEmail) {
    this.merchantEmail = merchantEmail;
  }
}
