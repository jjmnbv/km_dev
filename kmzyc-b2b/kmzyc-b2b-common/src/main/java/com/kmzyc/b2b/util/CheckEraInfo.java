package com.kmzyc.b2b.util;

public enum CheckEraInfo {
  SYSTEM_ERRO("0", "系统异常"),
  ERAINFO_NOEXCITS("-1", "会员不存在"),
  PASSWORD_ERRO("-2", "登陆密码错误"),
  SYSTEM_NOLOGIN("-3", "当前系统禁止登陆"),
  OUTOFTIME("-4", "已过实名期"),
  OUTOFDATE("-5", "续约已到期"),
  LOGIN_INVALID("-6","登陆权限失效,被录入黑名单"),
  OUTTIME_LOGIN("-7", "登陆权限失效，被限制区域登陆"),
  LOGIN_NOGRANT("-8", "会员编号未激活"),
  IS_LOGINOUT("-9", "会员编号已注销"),
  IS_FREEZ("-10", "会员编号已冻结");

  private String titile;

  private String type;

  CheckEraInfo(String types, String title) {
    this.type = types;
    this.titile = title;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTitle() {
    return titile;
  }

  public void setTitle(String title) {
    this.titile = title;
  }

  // public String toString() {
  // StringBuilder strBuilder = new StringBuilder();
  // strBuilder.append("CouponStatus[type=").append(this.type)
  // .append(",title=").append(this.titile).append("]");
  // return strBuilder.toString();
  // }
}
