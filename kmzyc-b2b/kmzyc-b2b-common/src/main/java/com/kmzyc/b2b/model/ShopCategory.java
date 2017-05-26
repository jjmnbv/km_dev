package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺分类信息 20150917 mlq add 暂时只为嵌合app接口
 * 
 * @author KM
 * 
 */
public class ShopCategory implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 店内分类描述信息,对应kmproduct.shop_categorys表中的remark字段
   */
  private String categoryDesc;

  /**
   * 店内分类id
   */
  private Long categoryId;

  /**
   * 店内分类名称
   */
  private String categoryName;

  /**
   * 子级类目集合
   */
  private List<ShopCategory> shopCategoryChildrenList;

  public String getCategoryDesc() {
    return categoryDesc;
  }

  public void setCategoryDesc(String categoryDesc) {
    this.categoryDesc = categoryDesc;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public List<ShopCategory> getShopCategoryChildrenList() {
    return shopCategoryChildrenList;
  }

  public void setShopCategoryChildrenList(List<ShopCategory> shopCategoryChildrenList) {
    this.shopCategoryChildrenList = shopCategoryChildrenList;
  }
}
