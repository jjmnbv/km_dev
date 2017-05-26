package com.kmzyc.b2b.third.model.wxCard;

/**
 * 微信卡券 存储参数实体
 * 
 * @author Administrator
 * 
 */
public class QueryPara {

  /**
   * 导入code时所用
   */
  private String couponId;
  /**
   * 开始页码 分页查询
   */
  private int startIndex;

  /**
   * 结束页码 分页查询导入code
   */
  private int endIndex;

  /**
   * 激活码
   */
  private String code;

  /**
   * 店铺验证ID
   */
  private String shopVerifyCode;

  /**
   * 表名
   */
  private String tableName;

  public String getCouponId() {
    return couponId;
  }

  public void setCouponId(String couponId) {
    this.couponId = couponId;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public void setStartIndex(int startIndex) {
    this.startIndex = startIndex;
  }

  public int getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(int endIndex) {
    this.endIndex = endIndex;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getShopVerifyCode() {
    return shopVerifyCode;
  }

  public void setShopVerifyCode(String shopVerifyCode) {
    this.shopVerifyCode = shopVerifyCode;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
}
