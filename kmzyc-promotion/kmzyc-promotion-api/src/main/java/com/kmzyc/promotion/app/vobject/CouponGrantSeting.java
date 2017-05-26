package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CouponGrantSeting implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Long couponGivetypeId;// COUPON_GIVETYPE_ID发放类型
  private Long couponId;
  private Long couponIssuingId;// COUPON_ISSUING_ID
  private String couponName;
  private Date issuingStartTime;// ISSUING_STARTTIME
  private Date issuingEndTime;// ISSUING_ENDTIME;
  private Date createTime;// CREATETIME
  private Long isStatus;// IS_STATUS\
  private Long issuingCount;// ISSUING_COUNT生成不记名优惠劵数量
  private String customId;// CUSTOM_ID
  private String customLeveId;// CUSTOM_LEVEIDS
  private String couponDesc;// 发放备注

  public String getCouponDesc() {
    return couponDesc;
  }

  public void setCouponDesc(String couponDesc) {
    this.couponDesc = couponDesc;
  }

  private Coupon coupon; // 此发放规则所对应的规则对象 20141105 add


  public String getCustomId() {
    return customId;
  }

  public void setCustomId(String customId) {
    this.customId = customId;
  }

  public String getCustomLeveId() {
    return customLeveId;
  }

  public void setCustomLeveId(String customLeveId) {
    this.customLeveId = customLeveId;
  }

  public Long getIssuingCount() {
    return issuingCount;
  }

  public void setIssuingCount(Long issuingCount) {
    this.issuingCount = issuingCount;
  }

  private BigDecimal payLeastMoney;// PAY_LEAST_MONEY;
  private BigDecimal couponMoney;
  private int startIndex;
  private int endIndex;


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

  public Long getCouponId() {
    return couponId;
  }

  public void setCouponId(Long couponId) {
    this.couponId = couponId;
  }

  public Long getCouponIssuingId() {
    return couponIssuingId;
  }

  public void setCouponIssuingId(Long couponIssuingId) {
    this.couponIssuingId = couponIssuingId;
  }

  public String getCouponName() {
    return couponName;
  }

  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public Date getIssuingStartTime() {
    return issuingStartTime;
  }

  public void setIssuingStartTime(Date issuingStartTime) {
    this.issuingStartTime = issuingStartTime;
  }

  public Date getIssuingEndTime() {
    return issuingEndTime;
  }

  public void setIssuingEndTime(Date issuingEndTime) {
    this.issuingEndTime = issuingEndTime;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Long getIsStatus() {
    return isStatus;
  }

  public void setIsStatus(Long isStatus) {
    this.isStatus = isStatus;
  }

  public Long getCouponGivetypeId() {
    return couponGivetypeId;
  }

  public void setCouponGivetypeId(Long couponGivetypeId) {
    this.couponGivetypeId = couponGivetypeId;
  }

  public BigDecimal getPayLeastMoney() {
    return payLeastMoney;
  }

  public void setPayLeastMoney(BigDecimal payLeastMoney) {
    this.payLeastMoney = payLeastMoney;
  }

  public BigDecimal getCouponMoney() {
    return couponMoney;
  }

  public void setCouponMoney(BigDecimal couponMoney) {
    this.couponMoney = couponMoney;
  }

  public Coupon getCoupon() {
    return coupon;
  }

  public void setCoupon(Coupon coupon) {
    this.coupon = coupon;
  }


}
