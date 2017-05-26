package com.kmzyc.framework.constants;

/**
 * 返回给前端ajax请求的结果代码 User: lishiming Date: 13-10-14 Time: 下午3:52
 */
public class InterfaceResultCode {
  /**
   * 手机号不合法
   */
  public static final String MOBILE_ILLEGAL = "205";
  /**
   * 频繁操作
   */
  public static final String FREQUENTLY = "204";
  /**
   * 不可用
   */
  public static final String DISABLE = "203";
  /**
   * 不存在
   */
  public static final String NOT_EXIST = "201";

  /**
   * 成功
   */
  public static final String SUCCESS = "200";
  /**
   * 已保存
   */
  public static final String SAVED = "202";

  /**
   * 失败
   */
  public static final String FAILED = "0";

  /**
   * 未登录
   */
  public static final String NOT_LOGIN = "1";

  /**
   * 包含敏感词汇
   */
  public static final String HAVE_SENSITIVE = "2";

  /**
   * 添加购物车部分商品失败
   */
  public static final String HAVE_FAIL = "3";

  /**
   * 系统异常
   */
  public static final String SYS_ERROR = "4";
}
