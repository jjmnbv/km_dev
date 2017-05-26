package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数据对象
 * 
 * @since 2014-09-22
 */
public class PurchaseListDetailDO implements Serializable {

  private static final long serialVersionUID = 141137054914013747L;

  /**
   * column PURCHASE_LIST_DETAIL.PURCHASE_DETAIL_ID
   */
  private BigDecimal purchaseDetailId;

  /**
   * column PURCHASE_LIST_DETAIL.PURCHASE_ID
   */
  private BigDecimal purchaseId;

  /**
   * column PURCHASE_LIST_DETAIL.SKU_CODE
   */
  private String skuCode;

  /**
   * column PURCHASE_LIST_DETAIL.PRODUCT_PRICE
   */
  private BigDecimal productPrice;

  /**
   * column PURCHASE_LIST_DETAIL.PRODUCT_COUNT
   */
  private BigDecimal productCount;

  /**
   * column PURCHASE_LIST_DETAIL.AMOUNT
   */
  private BigDecimal amount;

  private String productTitle;

  private String channel;
  private String frandName;
  private String col;
  private BigDecimal status;

  private Long categoryId;

  private String shopCode;
  private String supplierType;



  public String getSupplierType() {
    return supplierType;
  }

  public void setSupplierType(String supplierType) {
    this.supplierType = supplierType;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * 最小值
   */
  private Integer skip;

  public Integer getSkip() {
    return skip;
  }

  public void setSkip(Integer skip) {
    this.skip = skip;
  }

  /**
   * 最大值
   */
  private Integer max;



  public Integer getMax() {
    return max;
  }

  public void setMax(Integer max) {
    this.max = max;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public PurchaseListDetailDO() {
    super();
  }

  public PurchaseListDetailDO(BigDecimal purchaseDetailId, BigDecimal purchaseId, String skuCode,
      BigDecimal productPrice, BigDecimal productCount, BigDecimal amount, String productTitle,
      String channel, String frandName, String col, BigDecimal status, Long categoryId) {
    this.purchaseDetailId = purchaseDetailId;
    this.purchaseId = purchaseId;
    this.skuCode = skuCode;
    this.productPrice = productPrice;
    this.productCount = productCount;
    this.amount = amount;
    this.productTitle = productTitle;
    this.channel = channel;
    this.frandName = frandName;
    this.col = col;
    this.status = status;
    this.categoryId = categoryId;
  }


  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getFrandName() {
    return frandName;
  }

  public void setFrandName(String frandName) {
    this.frandName = frandName;
  }

  public String getCol() {
    return col;
  }

  public void setCol(String col) {
    this.col = col;
  }

  public BigDecimal getStatus() {
    return status;
  }

  public void setStatus(BigDecimal status) {
    this.status = status;
  }

  /**
   * getter for Column PURCHASE_LIST_DETAIL.PURCHASE_DETAIL_ID
   */
  public BigDecimal getPurchaseDetailId() {
    return purchaseDetailId;
  }

  /**
   * setter for Column PURCHASE_LIST_DETAIL.PURCHASE_DETAIL_ID
   * 
   * @param purchaseDetailId
   */
  public void setPurchaseDetailId(BigDecimal purchaseDetailId) {
    this.purchaseDetailId = purchaseDetailId;
  }

  /**
   * getter for Column PURCHASE_LIST_DETAIL.PURCHASE_ID
   */
  public BigDecimal getPurchaseId() {
    return purchaseId;
  }

  /**
   * setter for Column PURCHASE_LIST_DETAIL.PURCHASE_ID
   * 
   * @param purchaseId
   */
  public void setPurchaseId(BigDecimal purchaseId) {
    this.purchaseId = purchaseId;
  }

  /**
   * getter for Column PURCHASE_LIST_DETAIL.SKU_CODE
   */
  public String getSkuCode() {
    return skuCode;
  }

  /**
   * setter for Column PURCHASE_LIST_DETAIL.SKU_CODE
   * 
   * @param skuCode
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  /**
   * getter for Column PURCHASE_LIST_DETAIL.PRODUCT_PRICE
   */
  public BigDecimal getProductPrice() {
    return productPrice;
  }

  /**
   * setter for Column PURCHASE_LIST_DETAIL.PRODUCT_PRICE
   * 
   * @param productPrice
   */
  public void setProductPrice(BigDecimal productPrice) {
    this.productPrice = productPrice;
  }

  /**
   * getter for Column PURCHASE_LIST_DETAIL.PRODUCT_COUNT
   */
  public BigDecimal getProductCount() {
    return productCount;
  }

  /**
   * setter for Column PURCHASE_LIST_DETAIL.PRODUCT_COUNT
   * 
   * @param productCount
   */
  public void setProductCount(BigDecimal productCount) {
    this.productCount = productCount;
  }

  /**
   * getter for Column PURCHASE_LIST_DETAIL.AMOUNT
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * setter for Column PURCHASE_LIST_DETAIL.AMOUNT
   * 
   * @param amount
   */
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

}
