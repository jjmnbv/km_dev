package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kmzyc.supplier.model.SupplierFare;

/**
 * 卖方商家
 * 
 * @author luotao
 * 
 */
public class SellerShop extends SubTotal implements Serializable, Comparable<SellerShop> {

  private static final long serialVersionUID = 1L;
  /**
   * 商家ID
   */
  private Long sellerId;
  /** 排序依据 */
  private Long time = 0l;

  /**
   * 店铺名称
   */
  private String shopName;

  /****
   * 商家类型
   */

  private Integer supplierType;

  /**
   * 包邮
   */
  private SupplierFare freeFare;

  /***
   * 时代促销政策信息字段
   */
  private String info;

  /****
   * 时代促销详情url
   */
  private String url; //

  /***
   * 时代等级名称
   */
  private String eraGradeName;
  /**
   * 时代等级折扣率百分比
   */
  private BigDecimal eraGradeRate = BigDecimal.ZERO;

  /**
   * 时代等级折扣率百分比
   */
  public BigDecimal getEraGradeRate() {
    return eraGradeRate;
  }

  /**
   * 时代等级折扣率百分比
   */
  public void setEraGradeRate(BigDecimal eraGradeRate) {
    this.eraGradeRate = eraGradeRate;
  }

  /***
   * /** 产品map
   */
  private Map<String, ShopCartItem> shopCartItemMap;

  private Set<ShopCartItem> shopCartItemSet;

  public Long getSellerId() {
    return sellerId;
  }

  public void setSellerId(Long sellerId) {
    this.sellerId = sellerId;
  }



  public Map<String, ShopCartItem> getShopCartItemMap() {
    return shopCartItemMap;
  }

  public void setShopCartItemMap(Map<String, ShopCartItem> cartProductItemMap) {
    this.shopCartItemMap = cartProductItemMap;
  }



  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof SellerShop) {
      SellerShop shop = (SellerShop) obj;
      return sellerId.equals(shop.getSellerId());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return sellerId.hashCode();
  }

  public SupplierFare getFreeFare() {
    return freeFare;
  }

  public void setFreeFare(SupplierFare freeFare) {
    this.freeFare = freeFare;
  }

  public void addShopCartItem(ShopCartItem item) {
    if (shopCartItemMap == null) {
      shopCartItemMap = Maps.newHashMap();
    }
    if (shopCartItemSet == null) {
      shopCartItemSet = Sets.newTreeSet();
    }
    ShopCartItem it = shopCartItemMap.put(item.getId(), item);
    if (it == null) {
      shopCartItemSet.add(item);
    }
    if (time < item.getTime()) {
      time = item.getTime();
    }
  }


  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  /** 4时代商家 */
  public Integer getSupplierType() {
    return supplierType;
  }

  /** 4时代商家 */
  public void setSupplierType(Integer supplierType) {
    this.supplierType = supplierType;
  }



  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getEraGradeName() {
    return eraGradeName;
  }

  public void setEraGradeName(String eraGradeName) {
    this.eraGradeName = eraGradeName;
  }

  /*** 排序依据 */
  public Long getTime() {
    return time;
  }

  public Set<ShopCartItem> getShopCartItemSet() {
    return shopCartItemSet;
  }


  @Override
  public int compareTo(SellerShop o) {
    if (this.getSellerId().longValue() == 1l) {
      return -1;
    }
    if (o.getSellerId().longValue() == 1l) {
      return 1;
    }
    int compare = this.getTime().compareTo(o.getTime());
    if (compare == 0) {
      compare = this.getSellerId().compareTo(o.getSellerId());
    }
    return compare * -1;
  }

}
