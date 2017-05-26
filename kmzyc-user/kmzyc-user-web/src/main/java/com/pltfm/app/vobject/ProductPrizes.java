package com.pltfm.app.vobject;

import java.math.BigDecimal;


public class ProductPrizes {
  // 产品id
  private String productId;
  // 产品名称
  private String productName;
  // 产品编码
  private String productNo;
  // 状态
  private Integer status;
  // 渠道
  private String channel;
  // 类目id
  private Long categoryId;
  // 反推类目Id
  private String categoryL;
  // 类目名称
  private String categoryName;
  private String productTitle;
  // 商户ID
  private String shopCode;
  /**
   * 仓库ID
   */
  private BigDecimal warehouseId;
  // 商品成本价
  private BigDecimal costPrice;

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  // skuId
  private Long productSkuId;
  // sku编码
  private String productSkuCode;
  // 分页索引最小值
  private Integer minNum;
  // 分页索引最大值
  private Integer maxNum;


  public String getCategoryL() {
    return categoryL;
  }

  public void setCategoryL(String categoryL) {
    this.categoryL = categoryL;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public Integer getMinNum() {
    return minNum;
  }

  public void setMinNum(Integer minNum) {
    this.minNum = minNum;
  }

  public Integer getMaxNum() {
    return maxNum;
  }

  public void setMaxNum(Integer maxNum) {
    this.maxNum = maxNum;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductNo() {
    return productNo;
  }

  public void setProductNo(String productNo) {
    this.productNo = productNo;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public Long getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }

  public String getProductSkuCode() {
    return productSkuCode;
  }

  public void setProductSkuCode(String productSkuCode) {
    this.productSkuCode = productSkuCode;
  }

  public BigDecimal getWarehouseId() {
    return warehouseId;
  }

  public void setWarehouseId(BigDecimal warehouseId) {
    this.warehouseId = warehouseId;
  }

  public BigDecimal getCostPrice() {
    return costPrice;
  }

  public void setCostPrice(BigDecimal costPrice) {
    this.costPrice = costPrice;
  }

  @Override
  public String toString() {
    return "ProductPrizes [productId=" + productId + ", productName=" + productName + ", productNo="
        + productNo + ", status=" + status + ", channel=" + channel + ", categoryId=" + categoryId
        + ", categoryL=" + categoryL + ", categoryName=" + categoryName + ", productSkuId="
        + productSkuId + ", productSkuCode=" + productSkuCode + ", minNum=" + minNum + ", maxNum="
        + maxNum + "]";
  }



}
