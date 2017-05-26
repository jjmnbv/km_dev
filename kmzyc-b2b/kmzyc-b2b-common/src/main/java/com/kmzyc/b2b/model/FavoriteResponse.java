package com.kmzyc.b2b.model;

import java.math.BigDecimal;

public class FavoriteResponse {

  private String skuId;

  private Long favoriteId;

  private String contentCode;

  private BigDecimal finalPrice;//销售价

  private BigDecimal marketPrice;//市场价
  
  private BigDecimal markdownPrice;//降价金额
  
  private BigDecimal pValue;//pv值
  
  private String pStatus;//产品上下架状态

  private String tag;

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getSkuId() {
    return skuId;
  }

  public BigDecimal getMarketPrice() {
    return marketPrice;
  }

  public void setMarketPrice(BigDecimal marketPrice) {
    this.marketPrice = marketPrice;
  }

  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  private String productName;

  private String imgPath;

  private int contentNum;

  private String imgPath5;

  public int getContentNum() {
    return contentNum;
  }

  public void setContentNum(int contentNum) {
    this.contentNum = contentNum;
  }

  public Long getFavoriteId() {
    return favoriteId;
  }

  public void setFavoriteId(Long favoriteId) {
    this.favoriteId = favoriteId;
  }

  public String getContentCode() {
    return contentCode;
  }

  public void setContentCode(String contentCode) {
    this.contentCode = contentCode;
  }

  public BigDecimal getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(BigDecimal finalPrice) {
    this.finalPrice = finalPrice;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public String getImgPath5() {
    return imgPath5;
  }

  public void setImgPath5(String imgPath5) {
    this.imgPath5 = imgPath5;
  }
  public BigDecimal getPValue() {
      return pValue;
  }

  public void setPValue(BigDecimal pValue) {
      this.pValue = pValue;
  }
  
  public String getPStatus() {
      return pStatus;
  }

  public void setPStatus(String pStatus) {
      this.pStatus = pStatus;
  }
  public BigDecimal getMarkdownPrice() {
      return markdownPrice;
  }

  public void setMarkdownPrice(BigDecimal markdownPrice) {
      this.markdownPrice = markdownPrice;
  }

}
