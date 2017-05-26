package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kmzyc.supplier.model.SupplierFare;

/**
 * 购物车
 * 
 * @author luotao
 * 
 */
public class ShopCart extends SubTotal implements Serializable {

  private static final long serialVersionUID = 3899332850827070900L;
  /**
   * 自营和代销的key
   */
  public static final Long SELF_AND_PROXY_KEY = 1L;
  /**
   * 购物车唯一编码
   */
  private transient String code;


  /**
   * 卖方商家
   */
  private SellerShopList sellerShopList;


  /**
   * 购物车item
   */
  private transient Map<String, ShopCartItem> shopCartItemMap;

  /** 购物车商品 */
  private Map<String, ShopCartProduct> productMap;
  /** 数据库产品对象 */
  private transient Map<String, CartProduct> cartProductMap;


  /*** 购物车产品Error信息 */
  private transient Map<String, ShopCartProductReminder> productErrorReminder;

  /** 活动生效的列表 */
  private transient List<ShopCartItem> meetOrderPromotionList;

  private Map<Long, SupplierFare> supplierFareMap;
  /** 赠品加价购附赠的库存量 */
  private Map<String, Integer> giftStockMap;
  /**
   * 用户ID 如果游客则为sessionid
   */
  private transient String userID;
  /**
   * 是否登录
   */
  private transient Boolean userIsLogin;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public Boolean getUserIsLogin() {
    return userIsLogin;
  }

  public void setUserIsLogin(Boolean userIsLogin) {
    this.userIsLogin = userIsLogin;
  }

  public Map<String, ShopCartItem> getShopCartItemMap() {
    return shopCartItemMap;
  }

  public void setShopCartItemMap(Map<String, ShopCartItem> shopCartItemMap) {
    this.shopCartItemMap = shopCartItemMap;
  }



  public SellerShopList getSellerShopList() {
    return sellerShopList;
  }

  public void setSellerShopList(SellerShopList sellerShopList) {
    this.sellerShopList = sellerShopList;
  }

  /** 购物车所有商品集合 */
  public Map<String, ShopCartProduct> getProductMap() {
    return productMap;
  }

  /** 购物车所有商品集合 */
  public void setProductMap(Map<String, ShopCartProduct> productMap) {
    this.productMap = productMap;
  }

  /** 生效活动列表 */
  public List<ShopCartItem> getMeetOrderPromotionList() {
    return meetOrderPromotionList;
  }

  /** 生效活动列表 */
  public void addMeetOrderPromotion(ShopCartItem e) {
    if (meetOrderPromotionList == null) {
      meetOrderPromotionList = Lists.newArrayList();
    }
    meetOrderPromotionList.add(e);
  }

  public Map<String, ShopCartProductReminder> getProductErrorReminder() {
    return productErrorReminder;
  }

  public void setProductErrorReminder(Map<String, ShopCartProductReminder> productErrorReminder) {
    this.productErrorReminder = productErrorReminder;
  }

  public void addProductErrorReminder(String key, ShopCartProductReminder reminder) {
    if (this.productErrorReminder == null) {
      this.productErrorReminder = Maps.newHashMap();
    }
    productErrorReminder.put(key, reminder);
  }

  public Map<Long, SupplierFare> getSupplierFareMap() {
    return supplierFareMap;
  }

  public void setSupplierFareMap(Map<Long, SupplierFare> supplierFareMap) {
    this.supplierFareMap = supplierFareMap;
  }

  /** 赠品加价购的库存量 */
  public Map<String, Integer> getGiftStockMap() {
    return giftStockMap;
  }

  /** 赠品加价购附赠的库存量 */
  public void setGiftStockMap(Map<String, Integer> giftStockMap) {
    this.giftStockMap = giftStockMap;
  }

  /** 数据库产品对象 */
  public Map<String, CartProduct> getCartProductMap() {
    return cartProductMap;
  }

  /** 数据库产品对象 */
  public void setCartProductMap(Map<String, CartProduct> cartProductMap) {
    this.cartProductMap = cartProductMap;
  }
}
