package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class Productmain implements Serializable {

  private Long productId;

  private Long categoryId;

  private String procuctName;

  private String productDesc;

  private String packListing;

  private String productTitle;

  private String productSubtitle;

  private String keyword;

  private String status;

  private Long createUser;

  private Date createTime;

  private Long producthot;

  private Date archiveTime;

  private Date checkTime;

  private Date modifTime;

  private String shopCode;

  private Long modifUser;

  private Date upTime;

  private String channel;

  private BigDecimal marketPrice;

  private BigDecimal costPrice;

  private String productNo;

  private Long checkUser;

  private String checkUserName;

  private String createUserName;

  private Long brandId;

  private String brandName;

  private Short approvalType;

  private String approvalNo;

  private String categoryName;

  // private String isNotice;

  private Long drugCateId;

  private String drugCateCode;

  private String introduce;
  
  private String isKmSelf;

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public String getProcuctName() {
    return procuctName;
  }

  public void setProcuctName(String procuctName) {
    this.procuctName = procuctName;
  }

  public String getProductDesc() {
    return productDesc;
  }

  public void setProductDesc(String productDesc) {
    this.productDesc = productDesc;
  }

  public String getPackListing() {
    return packListing;
  }

  public void setPackListing(String packListing) {
    this.packListing = packListing;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Long getCreateUser() {
    return createUser;
  }

  public void setCreateUser(Long createUser) {
    this.createUser = createUser;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Long getProducthot() {
    return producthot;
  }

  public void setProducthot(Long producthot) {
    this.producthot = producthot;
  }

  public Date getArchiveTime() {
    return archiveTime;
  }

  public void setArchiveTime(Date archiveTime) {
    this.archiveTime = archiveTime;
  }

  public Date getCheckTime() {
    return checkTime;
  }

  public void setCheckTime(Date checkTime) {
    this.checkTime = checkTime;
  }

  public Date getModifTime() {
    return modifTime;
  }

  public void setModifTime(Date modifTime) {
    this.modifTime = modifTime;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public Long getModifUser() {
    return modifUser;
  }

  public void setModifUser(Long modifUser) {
    this.modifUser = modifUser;
  }

  public Date getUpTime() {
    return upTime;
  }

  public void setUpTime(Date upTime) {
    this.upTime = upTime;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public BigDecimal getMarketPrice() {
    return marketPrice;
  }

  public void setMarketPrice(BigDecimal marketPrice) {
    this.marketPrice = marketPrice;
  }

  public BigDecimal getCostPrice() {
    return costPrice;
  }

  public void setCostPrice(BigDecimal costPrice) {
    this.costPrice = costPrice;
  }

  public String getProductNo() {
    return productNo;
  }

  public void setProductNo(String productNo) {
    this.productNo = productNo;
  }

  public Long getCheckUser() {
    return checkUser;
  }

  public void setCheckUser(Long checkUser) {
    this.checkUser = checkUser;
  }

  public String getCheckUserName() {
    return checkUserName;
  }

  public void setCheckUserName(String checkUserName) {
    this.checkUserName = checkUserName;
  }

  public String getCreateUserName() {
    return createUserName;
  }

  public void setCreateUserName(String createUserName) {
    this.createUserName = createUserName;
  }

  public Long getBrandId() {
    return brandId;
  }

  public void setBrandId(Long brandId) {
    this.brandId = brandId;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public Short getApprovalType() {
    return approvalType;
  }

  public void setApprovalType(Short approvalType) {
    this.approvalType = approvalType;
  }

  public String getApprovalNo() {
    return approvalNo;
  }

  public void setApprovalNo(String approvalNo) {
    this.approvalNo = approvalNo;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  // public String getIsNotice() {
  // return isNotice;
  // }
  //
  // public void setIsNotice(String isNotice) {
  // this.isNotice = isNotice;
  // }

  public Long getDrugCateId() {
    return drugCateId;
  }

  public void setDrugCateId(Long drugCateId) {
    this.drugCateId = drugCateId;
  }

  public String getDrugCateCode() {
    return drugCateCode;
  }

  public void setDrugCateCode(String drugCateCode) {
    this.drugCateCode = drugCateCode;
  }

  public String getIntroduce() {
    return introduce;
  }

  public void setIntroduce(String introduce) {
    this.introduce = introduce;
  }

  public String getProductSubtitle() {
    return productSubtitle;
  }

  public void setProductSubtitle(String productSubtitle) {
    this.productSubtitle = productSubtitle;
  }

public String getIsKmSelf() {
	return isKmSelf;
}

public void setIsKmSelf(String isKmSelf) {
	this.isKmSelf = isKmSelf;
}

}
