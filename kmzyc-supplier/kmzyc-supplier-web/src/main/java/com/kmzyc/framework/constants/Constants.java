package com.kmzyc.framework.constants;

import com.kmzyc.zkconfig.ConfigurationUtil;


/**
 * @title 系统常量类
 * @describtion 存放系统所有常量
 * @author Administrator
 * @date 2013-09-24
 */
public class Constants {

  /**
   * 从session中获取用户信息的属性名称
   */
  public static final String SESSION_USER_KEY = "user";

  /**
   * session用户ID
   */
  public static final String SESSION_USER_ID = "seesionUserId";

  /**
   * session用户名
   */
  public static final String SESSION_USER_NAME = "sessionUserName";

  /**
   * session用户密码
   */
  public static final String SESSION_USER_PWD = "sessionUserPwd";

  /**
   * session供应商ID
   */
  public static final String SESSION_SUPPLIER_ID = "seesionSupplierId";
  
  /**
   * session用户商户ID
   */
  public static final String SESSION_MERCHANT_ID = "seesionMerchantId";

  /**
   * session康美中药城供应商ID
   */
    public static final String SESSION_KMB2BSUPPLIER_ID = "seesionKmb2bSupplierId";

  /**
   * session康美中药城供应商type
   */
    public static final String SESSION_KMB2BSUPPLIER_TYPE = "seesionKmb2bSupplierType";
  /**
   * session供应商公司名
   */
  public static final String SESSION_SUPPLIER_COMPANY = "seesionSupplierCompany";
  /**
   * session供应商手机号
   */
  public static final String SESSION_SUPPLIER_MOBILE = "seesionSupplierMobile";
  /**
   * session供应商邮箱
   */
  public static final String SESSION_SUPPLIER_EMIAL = "seesionSupplierEmail";
    /**
     * session供应商店铺名(B2B)
     */
  public static final String SESSION_SUPPLIER_SHOPNAME = "seesionSupplierShopName";
    /**
     * session供应商店铺类型(B2B)
     */
  public static final String SESSION_SUPPLIER_SHOPTYPE = "seesionSupplierShopType";
    /**
     * session供应商店铺首页地址(B2BB2B)
     */
  public static final String SESSION_SUPPLIER_SHOPURL = "seesionSupplierShopUrl";
  /**
   * session供应商头像
   */
  public static final String SESSION_SUPPLIER_HEADIMAGE = "seesionSupplierHeadImage";

  /**
   * 产品详情页面路径
   */
  public static final String PRODUCT_VIEW_PATH = ConfigurationUtil.getString("CMS_PRODUCT_PATH");

  /**
   * 在线支付
   */
  public static final Long PAY_METHOD_ONLINE = 5l;

  /**
   * 充值失败
   */
  public static final Integer RECHARGE_FALSE = 2;


  /**
   * 每次页面展示页数
   */
  public static final int VIEW_PAGE = 5;


  /**
   * 店铺默认分类的唯一标识
   */
  public static final String IS_DEFAULT_FOR_SHOPCATEGORY = "0";

  /** 20150916 maliqun add 系统给店铺的默认分类相关属性 begin */

  /**
   * 默认分配顶级分类的父类id
   */
  public static final int DEFAULT_SHOP_CAGEORY_PARENTID = 0;
  /**
   * 一级类目level值
   */
  public static final int DEFAULT_SHOP_CAGEORY_LEVEL_FIRST = 1;
  /**
   * 二级类目level值
   */
  public static final int DEFAULT_SHOP_CAGEORY_LEVEL_SECOND = 2;
  /**
   * 标识是否是默认分类值
   */
  public static final String SHOP_CAGEORY_DEFAULT = "0";
  /**
   * 默认展开值
   */
  public static final String DEFAULT_SHOP_CAGEORY_EXPAND = "1";
  /**
   * 默认推荐值
   */
  public static final int DEFAULT_SHOP_CAGEORY_SUGGEST = 1;
  /**
   * 默认排序值
   */
  public static final int DEFAULT_SHOP_CAGEORY_SORTNO = 0;
  /**
   * 默认状态值
   */
  public static final int DEFAULT_SHOP_CAGEORY_STATUS = 1;
  /**
   * 默认名称
   */
  public static final String DEFAULT_SHOP_CAGEORY_NAME = "默认分类";
  /** 系统给店铺的默认分类相关属性 end */


}
