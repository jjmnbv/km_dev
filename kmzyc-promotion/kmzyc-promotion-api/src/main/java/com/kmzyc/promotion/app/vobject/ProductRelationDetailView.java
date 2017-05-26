package com.kmzyc.promotion.app.vobject;

import java.math.BigDecimal;

public class ProductRelationDetailView {

  private Long relationDetailId; // 关联子表的主键id

  private BigDecimal price; // sku 价格

  private String productSkuCode; // skuCode

  private String procuctName; // 产品名称

  private String productNo; // 产品编号

  private String brandName; // 品牌名称

  private BigDecimal newPrice;// 产品设定的新价格

  private int remainingQuantity; // 剩余数量
  private Long skuId; // 产品skuId

  private int status; // 产品的状态

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getProductSkuCode() {
    return productSkuCode;
  }

  public void setProductSkuCode(String productSkuCode) {
    this.productSkuCode = productSkuCode;
  }

  public String getProcuctName() {
    return procuctName;
  }

  public void setProcuctName(String procuctName) {
    this.procuctName = procuctName;
  }

  public String getProductNo() {
    return productNo;
  }

  public void setProductNo(String productNo) {
    this.productNo = productNo;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public BigDecimal getNewPrice() {
    return newPrice;
  }

  public void setNewPrice(BigDecimal newPrice) {
    this.newPrice = newPrice;
  }

  public Long getRelationDetailId() {
    return relationDetailId;
  }

  public void setRelationDetailId(Long relationDetailId) {
    this.relationDetailId = relationDetailId;
  }

  public int getRemainingQuantity() {
    return remainingQuantity;
  }

  public void setRemainingQuantity(int remainingQuantity) {
    this.remainingQuantity = remainingQuantity;
  }

  public Long getSkuId() {
    return skuId;
  }

  public void setSkuId(Long skuId) {
    this.skuId = skuId;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

}
