package com.kmzyc.b2b.model;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.framework.exception.ObjectTransformException;

/**
 * Coupon entity. @author MyEclipse Persistence Tools
 */

public class Coupon implements java.io.Serializable {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1524436462543657288L;

  // private static Logger logger = Logger.getLogger(Coupon.class);
  private static Logger logger = LoggerFactory.getLogger(Coupon.class);
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
  private BigDecimal couponValidDay;
  // 使用限制 ， 1：不可和余额同时使用
  private String useLimitsType;
  private Long timeType;

    public com.kmzyc.promotion.app.vobject.Coupon transFormToRemoteAddress()
      throws ObjectTransformException {
        com.kmzyc.promotion.app.vobject.Coupon coupon =
                new com.kmzyc.promotion.app.vobject.Coupon();
    try {
      // 转换名称相同的属性
      BeanUtils.copyProperties(coupon, this);
    } catch (IllegalAccessException e) {
      logger.error("将本地Coupon对象转换为远程对象出错：" + e.getMessage(), e);
      throw new ObjectTransformException("将本地Coupon对象转换为远程对象出错！");
    } catch (InvocationTargetException e) {
      logger.error("将本地Coupon对象转换为远程对象出错：" + e.getMessage(), e);
      throw new ObjectTransformException("将本地Coupon对象转换为远程对象出错！");
    }

    return coupon;
  }

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

  public BigDecimal getCouponValidDay() {
    return couponValidDay;
  }

  public void setCouponValidDay(BigDecimal couponValidDay) {
    this.couponValidDay = couponValidDay;
  }



  public Long getTimeType() {
    return timeType;
  }

  public void setTimeType(Long timeType) {
    this.timeType = timeType;
  }

  /** 使用限制 ， 1：不可和余额同时使用 */
  public String getUseLimitsType() {
    return useLimitsType;
  }

  /** 使用限制 ， 1：不可和余额同时使用 */
  public void setUseLimitsType(String useLimitsType) {
    this.useLimitsType = useLimitsType;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("{\"couponId\":").append(1).append(",\"couponGivetypeId\":")
        .append(1).append(",\"couponName\":").append(1).append(",\"couponDescribe\":").append(1)
        .append(",\"couponMoney\":").append(1).append(",\"starttime\":").append(1)
        .append(",\"endtime\":").append(1).append(",\"createtime\":").append(1)
        .append(",\"status\":").append(1).append(",\"customLeveid\":").append(1)
        .append(",\"customRegStart\":").append(1).append(",\"customRegEnd\":").append(1)
        .append(",\"payLeastMoney\":").append(1).append(",\"customId\":").append(1)
        .append(",\"isGrant\":").append(1).append(",\"couponValidDay\":").append(1).append("}")
        .toString();
  }
}
