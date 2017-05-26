package com.kmzyc.b2b.model;

import java.math.BigDecimal;

public class ProductInfo {
  private String productName;// PROCUCT_NAME
  private String categoryName;// category_name
  private String productTitle;// PRODUCT_TITLE
  private String productNo;// PRODUCT_NO
  private String brandName;// brand_name
  private String imgPath;// img_path
  private String imgPath1;
  private String imgPath2;
  private String imgPath3;
  private String imgPath4;
  private String imgPath5;
  private Long SKUID;
  private BigDecimal price;

  public Long getSKUID() {
    return SKUID;
  }

  public void setSKUID(Long sKUID) {
    SKUID = sKUID;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
