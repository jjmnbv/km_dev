package com.kmzyc.b2b.vo;

import java.util.List;

/**
 * 购物车前端显示列表的传输对象
 * 
 * @author Administrator
 * 
 */
public class CarItemView {
  /** 活动类型 0无活动 1套装 2折扣 3满减 */
  private int actionineType;
  /** 活动名称 */
  private String actionineName;

  /** 简单产品列表 */
  List<CarProduct> carProducts;
  /** 套装产品 */
  CompositionCarProduct compositionCarProduct;

  public enum ActionineType {
    NOACTION, SUITACTION, DISCOUNT, DECREASE
  }

  public int getActionineType() {
    return actionineType;
  }

  public void setActionineType(int actionineType) {
    this.actionineType = actionineType;
  }

  public String getActionineName() {
    return actionineName;
  }

  public void setActionineName(String actionineName) {
    this.actionineName = actionineName;
  }

  public List<CarProduct> getCarProducts() {
    return carProducts;
  }

  public void setCarProducts(List<CarProduct> carProducts) {
    this.carProducts = carProducts;
  }

  public CompositionCarProduct getCompositionCarProduct() {
    return compositionCarProduct;
  }

  public void setCompositionCarProduct(CompositionCarProduct compositionCarProduct) {
    this.compositionCarProduct = compositionCarProduct;
  }

}
