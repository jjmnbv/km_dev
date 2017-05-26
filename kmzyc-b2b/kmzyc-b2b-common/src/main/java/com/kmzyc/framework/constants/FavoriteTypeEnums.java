package com.kmzyc.framework.constants;

/**
 * Description:收藏类型 User: lishiming Date: 13-10-15 下午3:45 Since: 1.0
 */
public enum FavoriteTypeEnums {
  /**
   * 商品
   */
  PRODUCT_SKU(1, "商品SKU"),
  /**
   * 店铺
   */
  SHOP(2, "店铺");

  private int value;
  private String title;

  FavoriteTypeEnums(int value, String title) {
    this.value = value;
    this.title = title;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return "FavoriteTypeEnums{" + "value=" + value + ", title='" + title + '\'' + '}';
  }
}
