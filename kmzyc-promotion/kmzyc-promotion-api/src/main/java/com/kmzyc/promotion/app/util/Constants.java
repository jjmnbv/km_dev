package com.kmzyc.promotion.app.util;

public class Constants {

  /**
   * 全场活动标识
   */
  public static final String PROMOTION_FULL_COURT_FLAG = "all";
  /**
   * 指定入驻商家活动标识
   */
  public static final int PROMOTION_APPOINT_SELLER_FLAG = 2;
  /**
   * 自营代销活动标识
   */
  public static final int PROMOTION_SELF_AND_PROXY_SELLER_FLAG = 3;

  /**
   * 自营商铺编码
   */
  public static final String SELF_SELLER_SHOP_CODE = "221";
  /**
   * 活动筛选商品类型-全场
   */
  public static final int PROMOTION_FILTER_TYPE_FULL_COURT = 1;
  /**
   * 活动筛选商品类型-指定商品
   */
  public static final int PROMOTION_FILTER_TYPE_APPOINT_PRODUCT = 2;
  /**
   * 活动筛选商品类型-商品类目
   */
  public static final int PROMOTION_FILTER_TYPE_APPOINT_CATEGORY = 3;
  /**
   * 活动筛选商品类型-商品品牌
   */
  public static final int PROMOTION_FILTER_TYPE_APPOINT_BRAND = 4;
   /**
   * 互斥全场活动标识
   */
  public static final String PROMOTION_MUTEX_ALL_PROMOTION_FLAG = "all";

  /**
   * 自营和代销的key
   */
  public static final Long SELF_AND_PROXY_KEY = 1L;

  /**
   * 查询异常
   */
  public static final int DATAEXCEPTION = -1002;
  /**
   * 库存不足
   */
  public static final int UNDERSTOCK = -1001;

  /**
   * 优惠券状态优 1:未发放
   */
  public static final Long COUPONSTATUSNOUSE = 1L;

  /**
   * memcached变量：优惠券规则下的产品，类目
   */
  public static final String COUPON_PRODUCT_CATE_CACHED = "promotion_coupon_product_cate_";
}
