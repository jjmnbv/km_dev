package com.kmzyc.framework.constants;

/**
 * 返回给前端ajax请求的结果代码
 * User: lishiming
 * Date: 13-10-14
 * Time: 下午3:52
 */
public class InterfaceResultCode {

    /**
     * 成功
     */
    public static final String SUCCESS = "200";

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
