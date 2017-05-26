package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 套装产品
 * 
 * @author罗涛
 * 
 */
public class CompositionCartProduct extends CartProduct implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 6232986909855666238L;

  /**
   * 套装副产品
   */
  private List<NormalCartProduct> productList;

  private NormalCartProduct mainProduct;

  private int count;

  public List<NormalCartProduct> getProductList() {
    return productList;
  }


  public void setProductList(List<NormalCartProduct> productList) {
    this.productList = productList;
  }



  public NormalCartProduct getMainProduct() {
    return mainProduct;
  }

  public void setMainProduct(NormalCartProduct mainProduct) {
    this.mainProduct = mainProduct;
  }



}
