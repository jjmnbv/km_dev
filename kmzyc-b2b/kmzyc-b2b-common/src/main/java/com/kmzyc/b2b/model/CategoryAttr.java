package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CategoryAttr implements Serializable {

  private BigDecimal categoryAttrId;

  private BigDecimal categoryId;

  private String categoryAttrName;

  private Short inputType;

  private Short isReq;

  private Short isNav;

  private Short status;

  private Integer sortno;

  private Date createTime;

  private Short isSku;

  private Date modifTime;

  private BigDecimal modifUser;

  public BigDecimal getCategoryAttrId() {
    return categoryAttrId;
  }

  public void setCategoryAttrId(BigDecimal categoryAttrId) {
    this.categoryAttrId = categoryAttrId;
  }

  public BigDecimal getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(BigDecimal categoryId) {
    this.categoryId = categoryId;
  }

  public String getCategoryAttrName() {
    return categoryAttrName;
  }

  public void setCategoryAttrName(String categoryAttrName) {
    this.categoryAttrName = categoryAttrName;
  }

  public Short getInputType() {
    return inputType;
  }

  public void setInputType(Short inputType) {
    this.inputType = inputType;
  }

  public Short getIsReq() {
    return isReq;
  }

  public void setIsReq(Short isReq) {
    this.isReq = isReq;
  }

  public Short getIsNav() {
    return isNav;
  }

  public void setIsNav(Short isNav) {
    this.isNav = isNav;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public Integer getSortno() {
    return sortno;
  }

  public void setSortno(Integer sortno) {
    this.sortno = sortno;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Short getIsSku() {
    return isSku;
  }

  public void setIsSku(Short isSku) {
    this.isSku = isSku;
  }

  public Date getModifTime() {
    return modifTime;
  }

  public void setModifTime(Date modifTime) {
    this.modifTime = modifTime;
  }

  public BigDecimal getModifUser() {
    return modifUser;
  }

  public void setModifUser(BigDecimal modifUser) {
    this.modifUser = modifUser;
  }

}
