package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家购物车产品
 * 
 * @author xiongliguang
 * 
 */
@Deprecated
public class SellerShopProduct implements Serializable {

  private static final long serialVersionUID = 1L;
  /**
   * 商家ID
   */
  private Long sellerId;

  /**
   * 商家信息
   */
  private SellerInfo sellerInfo;

  /**
   * 商家商品数量
   */
  private Integer productCount = 0;

  /**
   * 产品map
   */
  private Map<Long, CarProduct> carProducts;
  /**
   * 套装产品map
   */
  private Map<Long, CompositionCarProduct> compositionCarProducts;

  private boolean isChecked = false;

  public boolean clear() {
    boolean rs = true;
    try {
      sellerId = null;
      carProducts.clear();
      compositionCarProducts.clear();
      this.productCount = 0;
    } catch (Exception e) {
      rs = false;
    }
    return rs;
  }

  public SellerShopProduct() {
    compositionCarProducts = new LinkedHashMap<Long, CompositionCarProduct>();
    carProducts = new LinkedHashMap<Long, CarProduct>();
    productCount = 0;
    sellerInfo = new SellerInfo();
  }

  /**
   * 计算商品总数
   * 
   * @return
   */
  public Integer calcProductCount() {
    Integer allCount = 0;
    if (null != carProducts && !carProducts.isEmpty()) {
      for (Iterator<CarProduct> cpIt = carProducts.values().iterator(); cpIt.hasNext();) {
        allCount += cpIt.next().getAmount();
      }
    }
    if (null != compositionCarProducts && !compositionCarProducts.isEmpty()) {
      for (Iterator<CompositionCarProduct> ccpIt = compositionCarProducts.values().iterator(); ccpIt
          .hasNext();) {
        CompositionCarProduct ccp = ccpIt.next();
        for (CarProduct cp : ccp.getProductList()) {
          allCount += cp.getAmount();
        }
      }
    }
    productCount = allCount;
    return allCount;
  }

  /**
   * 更新产品数量，并返回变量
   * 
   * @param cid
   * @param amount
   */
  public Integer updateCountForProduct(Long cid, Integer amount) {
    int org = null == carProducts.get(cid) ? 0 : carProducts.get(cid).getAmount();
    int rs = amount - org;
    productCount += rs;
    return rs;
  }

  /**
   * 更新产品数量，并返回变量
   * 
   * @param cid
   * @param amount
   */
  public Integer updateCountForComposition(Long cid, List<CarProduct> cpList, Integer amount) {
    int org =
        null == compositionCarProducts.get(cid) ? 0 : compositionCarProducts.get(cid).getAmount();
    int sumAmout = 0, plusAmount = amount - org;
    if (0 == org) {
      org = 1;
    }
    if (null != cpList && !cpList.isEmpty()) {
      for (CarProduct cp : cpList) {
        sumAmout += cp.getAmount() / org * plusAmount;
      }
    }
    productCount += sumAmout;
    return sumAmout;
  }

  /**
   * 商家购物车商品为空
   * 
   * @return
   */
  public boolean isEmpty() {
    return null == carProducts || carProducts.isEmpty() || null == compositionCarProducts
        || compositionCarProducts.isEmpty();
  }

  public Long getSellerId() {
    return sellerId;
  }

  public void setSellerId(Long sellerId) {
    this.sellerId = sellerId;
  }

  public SellerInfo getSellerInfo() {
    return sellerInfo;
  }

  public void setSellerInfo(SellerInfo sellerInfo) {
    this.sellerInfo = sellerInfo;
  }

  public Integer getProductCount() {
    return productCount;
  }

  public void setProductCount(Integer productCount) {
    this.productCount = productCount;
  }

  public Map<Long, CarProduct> getCarProducts() {
    return carProducts;
  }

  public void setCarProducts(Map<Long, CarProduct> carProducts) {
    this.carProducts = carProducts;
  }

  public Map<Long, CompositionCarProduct> getCompositionCarProducts() {
    return compositionCarProducts;
  }

  public void setCompositionCarProducts(Map<Long, CompositionCarProduct> compositionCarProducts) {
    this.compositionCarProducts = compositionCarProducts;
  }

  public boolean isChecked() {
    return isChecked;
  }

  public void setChecked(boolean isChecked) {
    this.isChecked = isChecked;
  }

}
