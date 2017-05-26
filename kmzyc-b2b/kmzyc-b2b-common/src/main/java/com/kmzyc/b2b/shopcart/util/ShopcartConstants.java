package com.kmzyc.b2b.shopcart.util;

public class ShopcartConstants {


    /**
     * COOKIE购物车临时用户ID
     */
    public static final String COOKIE_SHOPCART_TEMPID = "b2b_cookie_shopcart_tempid";
    /**
     * 购物车redis前缀 2015-8-1 重构
     */
    /***
     * 购物车所有产品key
     */
    public static final String SHOPCART_PRODUCT = "b2b_shopcart_p_";
    public final static int MEMBER_SHOPCART_EXPIRE_TIME = 60 * 60 * 24 * 100;
    public final static int NON_MEMBER_SHOPCART_EXPIRE_TIME = 60 * 60 * 24 * 2;
    public static final String SHOPCART_ITEM = "r_cart_item_";


    /**
     * 产品类型 c:套餐
     */
    public static final String S_SC_SKUTYPE_C = "c";
    /**
     * 产品类型 n:单品
     */
    public static final String S_SC_SKUTYPE_N = "n";
    /**
     * 产品类型 c_
     */
    public static final String B_SC_SKUHEAD_C_ = "c_";
    /**
     * 产品类型 n_
     */
    public static final String B_SC_SKUHEAD_N_ = "n_";
    /**
     * 字符串分隔符 逗号
     */
    public static final String B_SC_SEPARATOR = ",";

    /**
     * OK
     */
    public static final String B_SC_OK = "OK";
    /**
     * 是否免邮 0-否
     */
    public static final String S_FREE_STATUS_0 = "0";
    /**
     * 是否免邮 1-是
     */
    public static final String S_FREE_STATUS_1 = "1";

}
