package com.kmzyc.b2b.vo;

import java.util.Date;
import java.util.List;

/**
 * 店铺分类
 * 
 * @author Administrator
 * 
 */
public class ShopCategorys {

  private Long shopCategoryId;

  private Long shopId;

  private Long parentCategoryId;

  private String categoryCode;

  private String categoryName;

  private Short categoryLevel;

  private Short status;

  private Integer sortno;

  private Date createTime;

  private Date modifTime;

  private Long modifUser;

  private String execSql;

  private Short isSuggest;

  private String remark;

  // 子类目
  private List<ShopCategorys> sonShopCate;

  // 是否展开
  private String isExpandall;

  public Long getShopCategoryId() {
    return shopCategoryId;
  }

  public void setShopCategoryId(Long shopCategoryId) {
    this.shopCategoryId = shopCategoryId;
  }

  public Long getShopId() {
    return shopId;
  }

  public void setShopId(Long shopId) {
    this.shopId = shopId;
  }

  public Long getParentCategoryId() {
    return parentCategoryId;
  }

  public void setParentCategoryId(Long parentCategoryId) {
    this.parentCategoryId = parentCategoryId;
  }

  public String getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public Short getCategoryLevel() {
    return categoryLevel;
  }

  public void setCategoryLevel(Short categoryLevel) {
    this.categoryLevel = categoryLevel;
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

  public Date getModifTime() {
    return modifTime;
  }

  public void setModifTime(Date modifTime) {
    this.modifTime = modifTime;
  }

  public Long getModifUser() {
    return modifUser;
  }

  public void setModifUser(Long modifUser) {
    this.modifUser = modifUser;
  }

  public String getExecSql() {
    return execSql;
  }

  public void setExecSql(String execSql) {
    this.execSql = execSql;
  }

  public Short getIsSuggest() {
    return isSuggest;
  }

  public void setIsSuggest(Short isSuggest) {
    this.isSuggest = isSuggest;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public List<ShopCategorys> getSonShopCate() {
    return sonShopCate;
  }

  public void setSonShopCate(List<ShopCategorys> sonShopCate) {
    this.sonShopCate = sonShopCate;
  }

  public String getIsExpandall() {
    return isExpandall;
  }

  public void setIsExpandall(String isExpandall) {
    this.isExpandall = isExpandall;
  }

}
