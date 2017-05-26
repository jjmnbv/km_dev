package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductRelationDetailAll implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long relationDetailId;

  private Long relationId;

  private Long relationSkuId;

  private BigDecimal relationSkuPrice;
  private BigDecimal marketPrice;

  private Short relationDetailType;

  private String productTile;// 产品标题
  private String productName;// 产品名称
  private String imagePath;// 图片路径
  private String imagePath6;
  private String imagePath7;

  /**
   * 库存
   */
  private Integer stock;
  /**
   * 状态
   */
  private Integer status;
  /**
   * 每份产品数量
   */
  private Long productCount;

  public BigDecimal getRelationSkuPrice() {
    return relationSkuPrice;
  }

  public void setRelationSkuPrice(BigDecimal relationSkuPrice) {
    this.relationSkuPrice = relationSkuPrice;
  }

  public Short getRelationDetailType() {
    return relationDetailType;
  }

  public void setRelationDetailType(Short relationDetailType) {
    this.relationDetailType = relationDetailType;
  }

  public Long getRelationDetailId() {
    return relationDetailId;
  }

  public void setRelationDetailId(Long relationDetailId) {
    this.relationDetailId = relationDetailId;
  }

  public Long getRelationId() {
    return relationId;
  }

  public void setRelationId(Long relationId) {
    this.relationId = relationId;
  }

  public Long getRelationSkuId() {
    return relationSkuId;
  }

  public void setRelationSkuId(Long relationSkuId) {
    this.relationSkuId = relationSkuId;
  }

  public Long getProductCount() {
    return productCount;
  }

  public void setProductCount(Long productCount) {
    this.productCount = productCount;
  }

  public String getProductTile() {
    return productTile;
  }

  public void setProductTile(String productTile) {
    this.productTile = productTile;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getImagePath6() {
    return imagePath6;
  }

  public void setImagePath6(String imagePath6) {
    this.imagePath6 = imagePath6;
  }

  public String getImagePath7() {
    return imagePath7;
  }

  public void setImagePath7(String imagePath7) {
    this.imagePath7 = imagePath7;
  }

  public BigDecimal getMarketPrice() {
    return marketPrice;
  }

  public void setMarketPrice(BigDecimal marketPrice) {
    this.marketPrice = marketPrice;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
