package com.kmzyc.b2b.vo;

import java.math.BigDecimal;

import com.kmzyc.b2b.model.ProductInfo;

public class PurchaseListDetail {

  private Long purchaseDetailId;

  private Long purchaseId;

  // 商品编码
  private String skuCode;

  private BigDecimal productPrice;

  private Long productCount;

  private BigDecimal amount;

  // 产品的信息
  private ProductInfo productInfo;

  private String col;

  public Long getPurchaseDetailId() {
    return purchaseDetailId;
  }

  public void setPurchaseDetailId(Long purchaseDetailId) {
    this.purchaseDetailId = purchaseDetailId;
  }

  public Long getPurchaseId() {
    return purchaseId;
  }

  public void setPurchaseId(Long purchaseId) {
    this.purchaseId = purchaseId;
  }

  public String getSkuCode() {
    return skuCode;
  }

  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  public Long getProductCount() {
    return productCount;
  }

  public void setProductCount(Long productCount) {
    this.productCount = productCount;
  }

  public ProductInfo getProductInfo() {
    return productInfo;
  }

  public void setProductInfo(ProductInfo productInfo) {
    this.productInfo = productInfo;
  }

  public BigDecimal getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(BigDecimal productPrice) {
    this.productPrice = productPrice;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getCol() {
    return col;
  }

  public void setCol(String col) {
    this.col = col;
  }

}
