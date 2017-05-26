/**
 * 
 */
package com.kmzyc.b2b.util.pay;

/**
 * @author zengming
 * @version 1.0
 * @since 2013-10-15 为满足多支付平台需要该抽象工厂类做支撑
 * @modifier zengming
 * @version 1.1
 * @since 2014-09-03 为满足支付宝的wap端接口增加plugInType
 */
public abstract class BasePayPlugInFactory {
  /**
   * 直连
   */
  public static final String PAYTYPE_DIRECT = "direct";
  /**
   * 网关
   */
  public static final String PAYTYPE_GATEWAY = "gateway";
  /**
   * 快捷支付
   */
  public static final String PAYTYPE_QUICKPAY = "quickpay";

  /**
   * pc端接入支付
   */
  public static final int ACCESSTYPE_WEB = 1;

  /**
   * 手机wap页面接入支付
   */
  public static final int ACCESSTYPE_WAP = 2;

  /**
   * 手机app端接入支付
   */
  public static final int ACCESSTYPE_APP = 3;

  /**
   * 手机微信页面接入支付
   */
  public static final int ACCESSTYPE_WX = 4;

  /**
   * 生成支付组件
   * 
   * @return
   * @throws Exception
   */
  public abstract BasePayPlugIn createPayPlugIn() throws Exception;
}
