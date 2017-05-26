package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;

public class ShopCartUserInfo implements Serializable {

  String uid;
  boolean login;
  String loginType;
  String buyType;

  public ShopCartUserInfo() {};

  /**
   * 
   * @param uid 用户id
   * @param login 登录状态
   * @param loginType 登录类型 01表示普通会员，02 表示康美中药城会员
   * @param buyType 是否购买处方药
   */
  public ShopCartUserInfo(String uid, boolean login, String loginType, String buyType) {
    this.uid = uid;
    this.login = login;
    this.loginType = loginType;
    this.buyType = buyType;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public boolean isLogin() {
    return login;
  }

  public void setLogin(boolean login) {
    this.login = login;
  }

  public String getLoginType() {
    return loginType;
  }

  public void setLoginType(String loginType) {
    this.loginType = loginType;
  }

  public String getBuyType() {
    return buyType;
  }

  public void setBuyType(String buyType) {
    this.buyType = buyType;
  }


}
