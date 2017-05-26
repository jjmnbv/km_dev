package com.kmzyc.b2b.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProductRelationDetail implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  private Long relationId;
  private String relationName;
  private Date createDate;
  private String productTile;
  private String productName;
  private String imgPath;
  private String imgPath6;
  private String imgPath7;
  private Integer productCount;
  private Long mskuId;
  private BigDecimal price;
  private Long productSkuId;

  public Long getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }

  public Long getRelationId() {
    return relationId;
  }

  public void setRelationId(Long relationId) {
    this.relationId = relationId;
  }

  public String getRelationName() {
    return relationName;
  }

  public void setRelationName(String relationName) {
    this.relationName = relationName;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
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

  public Integer getProductCount() {
    return productCount;
  }

  public void setProductCount(Integer productCount) {
    this.productCount = productCount;
  }

  public Long getMskuId() {
    return mskuId;
  }

  public void setMskuId(Long mskuId) {
    this.mskuId = mskuId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public String getImgPath6() {
    return imgPath6;
  }

  public void setImgPath6(String imgPath6) {
    this.imgPath6 = imgPath6;
  }

  public String getImgPath7() {
    return imgPath7;
  }

  public void setImgPath7(String imgPath7) {
    this.imgPath7 = imgPath7;
  }
}
