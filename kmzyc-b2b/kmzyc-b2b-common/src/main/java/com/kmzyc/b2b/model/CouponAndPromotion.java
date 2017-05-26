package com.kmzyc.b2b.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券和使用范围实体类
 * 
 * @author luoyi
 * @createDate 2013/11/28
 */

public class CouponAndPromotion implements java.io.Serializable {

  private static final long serialVersionUID = 985290829398428499L;
  // Fields
  private Long couponId;
  private Long couponGivetypeId;
  private String couponName;
  private String couponDescribe;
  private BigDecimal couponMoney;
  private Date starttime;
  private Date endtime;
  private Date createtime;
  private Long status;
  private String customLeveid;
  private Date customRegStart;
  private Date customRegEnd;
  private BigDecimal payLeastMoney;
  private String customId;
  private String isGrant;
  private int couponValidDay;
  private Integer productFilterType;
  private String productFilterTypeStr;// 范围说明
  private String shopCode;// 商户CODE
  private Integer timeType;

  public Long getCouponId() {
    return couponId;
  }

  public void setCouponId(Long couponId) {
    this.couponId = couponId;
  }

  public Long getCouponGivetypeId() {
    return couponGivetypeId;
  }

  public void setCouponGivetypeId(Long couponGivetypeId) {
    this.couponGivetypeId = couponGivetypeId;
  }

  public String getCouponName() {
    return couponName;
  }

  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public String getCouponDescribe() {
    return couponDescribe;
  }

  public void setCouponDescribe(String couponDescribe) {
    this.couponDescribe = couponDescribe;
  }

  public BigDecimal getCouponMoney() {
    return couponMoney;
  }

  public void setCouponMoney(BigDecimal couponMoney) {
    this.couponMoney = couponMoney;
  }

  public Date getStarttime() {
    return starttime;
  }

  public void setStarttime(Date starttime) {
    this.starttime = starttime;
  }

  public Date getEndtime() {
    return endtime;
  }

  public void setEndtime(Date endtime) {
    this.endtime = endtime;
  }

  public Date getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  public String getCustomLeveid() {
    return customLeveid;
  }

  public void setCustomLeveid(String customLeveid) {
    this.customLeveid = customLeveid;
  }

  public Date getCustomRegStart() {
    return customRegStart;
  }

  public void setCustomRegStart(Date customRegStart) {
    this.customRegStart = customRegStart;
  }

  public Date getCustomRegEnd() {
    return customRegEnd;
  }

  public void setCustomRegEnd(Date customRegEnd) {
    this.customRegEnd = customRegEnd;
  }

  public BigDecimal getPayLeastMoney() {
    return payLeastMoney;
  }

  public void setPayLeastMoney(BigDecimal payLeastMoney) {
    this.payLeastMoney = payLeastMoney;
  }

  public String getCustomId() {
    return customId;
  }

  public void setCustomId(String customId) {
    this.customId = customId;
  }

  public String getIsGrant() {
    return isGrant;
  }

  public void setIsGrant(String isGrant) {
    this.isGrant = isGrant;
  }

  public int getCouponValidDay() {
    return couponValidDay;
  }

  public void setCouponValidDay(int couponValidDay) {
    this.couponValidDay = couponValidDay;
  }

  public Integer getProductFilterType() {
    return productFilterType;
  }

  public void setProductFilterType(Integer productFilterType) {
    this.productFilterType = productFilterType;
  }

  public String getProductFilterTypeStr() {
    return productFilterTypeStr;
  }

  public void setProductFilterTypeStr(String productFilterTypeStr) {
    this.productFilterTypeStr = productFilterTypeStr;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public Integer getTimeType() {
    return timeType;
  }

  public void setTimeType(Integer timeType) {
    this.timeType = timeType;
  }

}
