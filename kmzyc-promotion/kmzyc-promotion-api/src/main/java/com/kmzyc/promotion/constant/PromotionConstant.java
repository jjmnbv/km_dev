package com.kmzyc.promotion.constant;

public final class PromotionConstant {


  /** 活动商品 sortedSet */
  public final static String PROMOTION_SKU = "r_promo_sku_";
  /** 活动优惠数据 sortedSet */
  public final static String PROMOTION_RULE = "r_promo_rule_";
  /** 活动基本信息 string */
  public final static String PROMOTION = "r_promo_";
  /** 商品对应的所有活动信息 ，key为该前缀加skuId,结构为storedSet，endTime为score */
  public static final String PRODUCT_PROMOTION_INFO = "r_promo_p_";// "r_pro_promo_";
  /** 商品对应活动的限购信息 ，key为该前缀加promotionId+skuId,结构为hash */
  public static final String PRODUCT_PROMOTION_RESTRICTION_INFO = "r_pro_promo_restr_";
  /** 商品对应附赠信息 */
  public static final String PRODUCT_PROMOTION_GANT_INFO = "r_pro_promo_gant_";
  /** 活动可用数量 */
  public final static String PRODUCT_ORDER_QUANTITY_FIELD = "availableQuantity";
  /** 活动库存 */
  public final static String PRODUCT_PROMOTION_STOCK_FIELD = "promotionStock";
  /** 已售数据 */
  public final static String PRODUCT_PROMOTION_ALREADY_SELL = "alreadySell";


  /** 商品信息 hash */
  public final static String PRODUCT_SKU_INFO = "r_sku_";

  /** 价格 */
  public final static String PRODUCT_PRICE_FIELD = "price"; //



  /***/
  public final static String PRODUCT_STOCK_COUNT_FIELD = "stock";

  /**
   * 是否同步缓存 0-未同步
   */
  public static final int PROM_ISSYCNCACHE_0 = 0;
  /**
   * 是否同步缓存 1-正在同步
   */
  public static final int PROM_ISSYCNCACHE_1 = 1;
  /**
   * 是否同步缓存 2-同步成功
   */
  public static final int PROM_ISSYCNCACHE_2 = 2;
  /**
   * 是否同步缓存 3-同步失败
   */
  public static final int PROM_ISSYCNCACHE_3 = 3;

  /**
   * app请求来源
   */
  public static final String CHANNEL_APP = "APP";

}
