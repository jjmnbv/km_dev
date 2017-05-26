package com.kmzyc.promotion.optimization.vo;

import java.io.Serializable;


public class PromotionProductData implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   * 
   * @ibatorgenerated Wed Nov 18 14:01:26 CST 2015
   */
  private Long promotionProductDataId;

  /**
   * 促销活动产品ID
   * 
   * @ibatorgenerated Wed Nov 18 14:01:26 CST 2015
   */
  private Long promotionProductId;

  private Long promotionId;

  private Long productSkuId;

  private Long num;

  private Long prarentSkuId;
  private Long status;

  private String productTitle;

  private String skuAttrValue;

  private String imagePath;

  private Integer stockCount;

  // APP需要的数量是总数据，而附赠商品使用了缓存，所以这里加字段给APP单独使用
  private Long appNum;
  private String appProductTitle;
  private String appImagePath;

  // end


  public Long getAppNum() {
    return appNum;
  }

  public void setAppNum(Long appNum) {
    this.appNum = appNum;
  }

  public String getAppProductTitle() {
    return appProductTitle;
  }

  public void setAppProductTitle(String appProductTitle) {
    this.appProductTitle = appProductTitle;
  }

  public String getAppImagePath() {
    return appImagePath;
  }

  public void setAppImagePath(String appImagePath) {
    this.appImagePath = appImagePath;
  }

  public Integer getStockCount() {
    return stockCount;
  }

  public void setStockCount(Integer stockCount) {
    this.stockCount = stockCount;
  }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  public String getSkuAttrValue() {
    return skuAttrValue;
  }

  public void setSkuAttrValue(String skuAttrValue) {
    this.skuAttrValue = skuAttrValue;
  }

  public Long getPromotionProductDataId() {
    return promotionProductDataId;
  }

  public void setPromotionProductDataId(Long promotionProductDataId) {
    this.promotionProductDataId = promotionProductDataId;
  }

  public Long getPromotionProductId() {
    return promotionProductId;
  }

  public void setPromotionProductId(Long promotionProductId) {
    this.promotionProductId = promotionProductId;
  }

  public Long getPromotionId() {
    return promotionId;
  }

  public void setPromotionId(Long promotionId) {
    this.promotionId = promotionId;
  }

  public Long getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }

  public Long getNum() {
    return num;
  }

  public void setNum(Long num) {
    this.num = num;
  }

  public Long getPrarentSkuId() {
    return prarentSkuId;
  }

  public void setPrarentSkuId(Long prarentSkuId) {
    this.prarentSkuId = prarentSkuId;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }



}
