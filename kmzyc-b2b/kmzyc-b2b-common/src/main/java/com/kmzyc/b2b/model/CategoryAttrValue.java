package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CategoryAttrValue implements Serializable {

  private BigDecimal categoryAttrValueId;

  private BigDecimal categoryAttrId;

  private Short status;

  private String categoryAttrValue;

  public BigDecimal getCategoryAttrValueId() {
    return categoryAttrValueId;
  }

  public void setCategoryAttrValueId(BigDecimal categoryAttrValueId) {
    this.categoryAttrValueId = categoryAttrValueId;
  }

  public BigDecimal getCategoryAttrId() {
    return categoryAttrId;
  }

  public void setCategoryAttrId(BigDecimal categoryAttrId) {
    this.categoryAttrId = categoryAttrId;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public String getCategoryAttrValue() {
    return categoryAttrValue;
  }

  public void setCategoryAttrValue(String categoryAttrValue) {
    this.categoryAttrValue = categoryAttrValue;
  }

}
