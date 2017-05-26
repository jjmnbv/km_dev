package com.kmzyc.b2b.shopcart.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.b2b.util.CookieUtil;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 购物车操作类，不更新缓存
 * 
 * @author xlg
 * 
 */
public class ShopUtil {


  public static final Long SELF_AND_PROXY_KEY = 1L;

  /**
   * 购物车渠道
   */
  public final static String SHOP_CAR_CHANNEL =
      ConfigurationUtil.getString("CHANNEL");

  /**购物车有效时间*/
  public static final int SETTLEMENT_TIME_OUT =
      60 * 60 * Integer.parseInt(ConfigurationUtil.getString("SETTLEMENT_TIME_OUT")) * 1000;

  /**
   * 失败
   */
  public static final String FAILED = "0";


  /**
   * 成功
   */
  public static final String SUCCESS = "200";



  /**
   * 获取购物车ID和登录状态
   * 
   * @param request
   * @param response
   * @return
   */
  public static String getUid(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession();
    Object sessionUserId;
    String isTmall = request.getParameter("isTmall");
    boolean isLogin;
    if (StringUtil.isEmpty(isTmall)) {
      sessionUserId = session.getAttribute(Constants.SESSION_USER_ID);
      isLogin = null != sessionUserId;
    } else {
      sessionUserId = session.getAttribute(ThirdConstant.TMALL_USER_ID);
      isLogin = true;
    }
    String userId;
    if (isLogin) {
      userId = sessionUserId.toString();
    } else {
      userId = getTempUserId(request, response);
    }
    return userId;
  }



  /**
   * 获取游客ID
   * 
   * @param request
   * @param response
   * @return
   */
  public static String getTempUserId(HttpServletRequest request, HttpServletResponse response) {
    String id = CookieUtil.getCookieValue(request, ShopcartConstants.COOKIE_SHOPCART_TEMPID);
    if (StringUtil.isEmpty(id)) {
      id = request.getSession().getId();
      CookieUtil.createCookie(response,Constants.COOKIE_SHOPCART_TEMPID,id,ConfigurationUtil.getString("cookie_domain"),60 * 60 * 24 * 2);
    }
    return id;
  }



}
