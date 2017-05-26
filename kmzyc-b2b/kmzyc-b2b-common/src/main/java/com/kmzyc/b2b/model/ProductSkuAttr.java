package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author hl 产品类目属性
 */
public class ProductSkuAttr implements Serializable {

  private BigDecimal productSkuAttrId;
  private BigDecimal categoryAttrId;
  private BigDecimal productSkuId;
  private BigDecimal categoryAttrValueId;
  private String categoryAttrName;
  private String status;
  // 对应的类目id
  private CategoryAttr categoryAttr;

  // 对应的类目值
  private CategoryAttrValue categoryAttrValue;

  public BigDecimal getProductSkuAttrId() {
    return productSkuAttrId;
  }

  public void setProductSkuAttrId(BigDecimal productSkuAttrId) {
    this.productSkuAttrId = productSkuAttrId;
  }

  public BigDecimal getCategoryAttrId() {
    return categoryAttrId;
  }

  public void setCategoryAttrId(BigDecimal categoryAttrId) {
    this.categoryAttrId = categoryAttrId;
  }

  public BigDecimal getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(BigDecimal productSkuId) {
    this.productSkuId = productSkuId;
  }

  public BigDecimal getCategoryAttrValueId() {
    return categoryAttrValueId;
  }

  public void setCategoryAttrValueId(BigDecimal categoryAttrValueId) {
    this.categoryAttrValueId = categoryAttrValueId;
  }

  public String getCategoryAttrName() {
    return categoryAttrName;
  }

  public void setCategoryAttrName(String categoryAttrName) {
    this.categoryAttrName = categoryAttrName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public CategoryAttr getCategoryAttr() {
    return categoryAttr;
  }

  public void setCategoryAttr(CategoryAttr categoryAttr) {
    this.categoryAttr = categoryAttr;
  }

  public CategoryAttrValue getCategoryAttrValue() {
    return categoryAttrValue;
  }

  public void setCategoryAttrValue(CategoryAttrValue categoryAttrValue) {
    this.categoryAttrValue = categoryAttrValue;
  }

}
