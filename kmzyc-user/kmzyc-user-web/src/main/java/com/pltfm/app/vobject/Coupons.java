package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.Date;

public class Coupons implements java.io.Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 3275421853998006552L;

  /**
   * 客户id
   */
  private Integer custmoId;
  
  /**
   * 优惠券ID
   */
  private Long couponId;
  
  /**
   * 发放业务类型
   */
  private Integer grantType;
  
  /**
   * 登录账户
   */
  private String loginAccount;
  
  /**
   * 客户名称custNmae
   */
  private String custName;


  /**
   * 消费卷名称 couponName
   * 
   */
  private String couponName;
  /**
   * 消费卷类型 :couponGivetypeId
   * 
   */
  private Long couponGivetypeId;
  /**
   * 优惠折扣金额:couponMoney
   * 
   */
  private BigDecimal couponMoney;

  /**
   * 优惠劵开始日期 :startTime
   * 
   */
  private Date startTime;

  /**
   * 优惠劵结束日期 :endTime:
   * 
   */
  private Date endTime;
  /**
   * 优惠劵状态：couponStatus：
   * 
   */
  private Long couponStatus;
  
  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;
  
  
  
  
  


  public Long getCouponId() {
    return couponId;
  }

  public void setCouponId(Long couponId) {
    this.couponId = couponId;
  }

  public Integer getGrantType() {
    return grantType;
  }

  public void setGrantType(Integer grantType) {
    this.grantType = grantType;
  }

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }

  public Integer getSkip() {
    return skip;
  }

  public void setSkip(Integer skip) {
    this.skip = skip;
  }

  public Integer getMax() {
    return max;
  }

  public void setMax(Integer max) {
    this.max = max;
  }

  public void setCustmoId(Integer custmoId) {
    this.custmoId = custmoId;
  }

  public Coupons(Integer custmoId, String custName, Long couponGivetypeId, BigDecimal couponMoney,
      Date startTime, Date endTime, Long couponStatus) {
    this.custmoId = custmoId;
    this.custName = custName;
    this.couponGivetypeId = couponGivetypeId;
    this.couponMoney = couponMoney;
    this.startTime = startTime;
    this.endTime = endTime;
    this.couponStatus = couponStatus;
  }

  public Coupons() {}

  public Integer getCustmoId() {
    return custmoId;
  }

  public void Integer(Integer custmoId) {
    this.custmoId = custmoId;
  }

  public String getCouponName() {
    return couponName;
  }

  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public Long getCouponGivetypeId() {
    return couponGivetypeId;
  }

  public void setCouponGivetypeId(Long couponGivetypeId) {
    this.couponGivetypeId = couponGivetypeId;
  }

  public BigDecimal getCouponMoney() {
    return couponMoney;
  }

  public void setCouponMoney(BigDecimal couponMoney) {
    this.couponMoney = couponMoney;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public Long getCouponStatus() {
    return couponStatus;
  }

  public void setCouponStatus(Long couponStatus) {
    this.couponStatus = couponStatus;
  }

  public String getCustName() {
    return custName;
  }

  public void setCustName(String custName) {
    this.custName = custName;
  }


}
