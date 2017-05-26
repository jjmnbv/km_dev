package com.kmzyc.b2b.model;

import java.math.BigDecimal;

import com.kmzyc.b2b.vo.BaseProduct;

public class ViewProductRelationPurchase extends BaseProduct {

  private static final long serialVersionUID = -4540791021878390458L;

  private Long mainSkuId;

  private Long relationSkuId;

  private BigDecimal price;
  private BigDecimal markPrice;

  private Integer relationType;
  private String procuctName;
  private String productTitle;

  private String imgPath;

  private String imgPath1;

  private String imgPath2;

  private String imgPath3;

  private String imgPath4;

  private String imgPath5;

  private String imgPath6;

  private String imgPath7;

  private String imgPath8;

  private String imgPath9;

  private String imgPath10;

  public Long getProductSkuId() {
    return relationSkuId;
  }

  public Long getMainSkuId() {
    return mainSkuId;
  }

  public void setMainSkuId(Long mainSkuId) {
    this.mainSkuId = mainSkuId;
  }

  public Long getRelationSkuId() {
    return relationSkuId;
  }

  public void setRelationSkuId(Long relationSkuId) {
    this.relationSkuId = relationSkuId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getProcuctName() {
    return procuctName;
  }

  public void setProcuctName(String procuctName) {
    this.procuctName = procuctName;
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public String getImgPath1() {
    return imgPath1;
  }

  public void setImgPath1(String imgPath1) {
    this.imgPath1 = imgPath1;
  }

  public String getImgPath2() {
    return imgPath2;
  }

  public void setImgPath2(String imgPath2) {
    this.imgPath2 = imgPath2;
  }

  public String getImgPath3() {
    return imgPath3;
  }

  public void setImgPath3(String imgPath3) {
    this.imgPath3 = imgPath3;
  }

  public String getImgPath4() {
    return imgPath4;
  }

  public void setImgPath4(String imgPath4) {
    this.imgPath4 = imgPath4;
  }

  public String getImgPath5() {
    return imgPath5;
  }

  public void setImgPath5(String imgPath5) {
    this.imgPath5 = imgPath5;
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

  public String getImgPath8() {
    return imgPath8;
  }

  public void setImgPath8(String imgPath8) {
    this.imgPath8 = imgPath8;
  }

  public String getImgPath9() {
    return imgPath9;
  }

  public void setImgPath9(String imgPath9) {
    this.imgPath9 = imgPath9;
  }

  public String getImgPath10() {
    return imgPath10;
  }

  public void setImgPath10(String imgPath10) {
    this.imgPath10 = imgPath10;
  }

  public BigDecimal getMarkPrice() {
    if (markPrice == null) return BigDecimal.valueOf(0);
    return markPrice;
  }

  public void setMarkPrice(BigDecimal markPrice) {
    this.markPrice = markPrice;
  }

  public Integer getRelationType() {
    return relationType;
  }

  public void setRelationType(Integer relationType) {
    this.relationType = relationType;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }
}
