package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.Maps;
import com.kmzyc.b2b.shopcart.vo.SellerShop;

public class SellerShopList implements Serializable {

  private static final long serialVersionUID = 2852980923957620788L;

  private Map<Long, SellerShop> sellerShopMap = Maps.newLinkedHashMap();

  private Set<SellerShop> sellerShopSet = new TreeSet<SellerShop>();


  public void add(SellerShop shop) {
    sellerShopMap.put(shop.getSellerId(), shop);
  }

  public SellerShop get(Long sellerId) {
    return sellerShopMap.get(sellerId);
  }

  public int size() {
    return sellerShopMap.size();
  }

  public boolean isEmpty() {
    return sellerShopMap.isEmpty();
  }

  public boolean containsKey(Long key) {
    return sellerShopMap.containsKey(key);
  }

  public Map<Long, SellerShop> getSellerShopMap() {
    return sellerShopMap;
  }

  public Set<Long> keySet() {
    return sellerShopMap.keySet();
  }

  public Set<SellerShop> getSellerShopSet() {
    return sellerShopSet;
  }



}
