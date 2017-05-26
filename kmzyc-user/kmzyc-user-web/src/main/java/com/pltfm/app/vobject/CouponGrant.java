package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 优惠券发放表
 * 
 * @author wangkai
 * 
 */
public class CouponGrant implements Serializable {

  /**
     * 
     */
    private static final long serialVersionUID = -4737487238500431844L;

private static Logger logger = LoggerFactory.getLogger(CouponGrant.class);

  private Long couponGrantId;
  private Long couponGivetypeId;
  private Long couponId;
  private String couponName;
  private Integer customId;
  
  private BigDecimal couponMoney;

  private Long couponStatus;

  private List<Coupon> couponList;
  
  private String couponInfoNo;

  private String activeCode;

  // 新增
  private Date grantCreattime;

  private Long grantCreateman;

  private Date grantUpdatetime;

  private Long grantUpdateman;

  // ---新增发放业务关联
  private Long grantType;

  private String grantRelatedCode;

  private String grantRemark;

  private Date grantEndtime;

  private Date starttime_coupon;

  private Date endtime_coupon;

  private Date grant_acttime;

  private Date startTime;
  private Date endTime;
  private Date actTime;
  private Date grantUsetime;
  private Long couponIssuingId;
  

  public BigDecimal getCouponMoney() {
    return couponMoney;
  }

  public void setCouponMoney(BigDecimal couponMoney) {
    this.couponMoney = couponMoney;
  }

  public Date getGrantUsetime() {
    return grantUsetime;
  }

  public void setGrantUsetime(Date grantUsetime) {
    this.grantUsetime = grantUsetime;
  }

  public String getCouponInfoNo() {
    return couponInfoNo;
  }

  public void setCouponInfoNo(String couponInfoNo) {
    this.couponInfoNo = couponInfoNo;
  }

  public String getActiveCode() {
    return activeCode;
  }

  public void setActiveCode(String activeCode) {
    this.activeCode = activeCode;
  }

  public Long getCouponGrantId() {
    return couponGrantId;
  }

  public void setCouponGrantId(Long couponGrantId) {
    this.couponGrantId = couponGrantId;
  }

  public Long getCouponId() {
    return couponId;
  }

  public void setCouponId(Long couponId) {
    this.couponId = couponId;
  }

  public Long getCouponStatus() {
    return couponStatus;
  }

  public void setCouponStatus(Long couponStatus) {
    this.couponStatus = couponStatus;
  }

  public Integer getCustomId() {
    return customId;
  }

  public void setCustomId(Integer customId) {
    this.customId = customId;
  }

  public List<Coupon> getCouponList() {
    return couponList;
  }

  public void setCouponList(List<Coupon> couponList) {
    this.couponList = couponList;
  }

  public Date getGrantCreattime() {
    return grantCreattime;
  }

  public void setGrantCreattime(Date grantCreattime) {
    this.grantCreattime = grantCreattime;
  }

  public Long getGrantCreateman() {
    return grantCreateman;
  }

  public void setGrantCreateman(Long grantCreateman) {
    this.grantCreateman = grantCreateman;
  }

  public Date getGrantUpdatetime() {
    return grantUpdatetime;
  }

  public void setGrantUpdatetime(Date grantUpdatetime) {
    this.grantUpdatetime = grantUpdatetime;
  }

  public Long getGrantUpdateman() {
    return grantUpdateman;
  }

  public void setGrantUpdateman(Long grantUpdateman) {
    this.grantUpdateman = grantUpdateman;
  }

  public Long getGrantType() {
    return grantType;
  }

  public void setGrantType(Long grantType) {
    this.grantType = grantType;
  }

  public String getGrantRelatedCode() {
    return grantRelatedCode;
  }

  public void setGrantRelatedCode(String grantRelatedCode) {
    this.grantRelatedCode = grantRelatedCode;
  }

  public String getGrantRemark() {
    return grantRemark;
  }

  public void setGrantRemark(String grantRemark) {
    this.grantRemark = grantRemark;
  }

  public Date getGrantEndtime() {
    return grantEndtime;
  }

  public void setGrantEndtime(Date grantEndtime) {
    this.grantEndtime = grantEndtime;
  }

  public Date getStarttime_coupon() {
    return starttime_coupon;
  }

  public void setStarttime_coupon(Date starttime_coupon) {
    this.starttime_coupon = starttime_coupon;
  }

  public Date getEndtime_coupon() {
    return endtime_coupon;
  }

  public void setEndtime_coupon(Date endtime_coupon) {
    this.endtime_coupon = endtime_coupon;
  }

  public Date getGrant_acttime() {
    return grant_acttime;
  }

  public void setGrant_acttime(Date grant_acttime) {
    this.grant_acttime = grant_acttime;
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

  public Date getActTime() {
    return actTime;
  }

  public void setActTime(Date actTime) {
    this.actTime = actTime;
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

  public Long getCouponGivetypeId() {
    return couponGivetypeId;
  }

  public void setCouponGivetypeId(Long couponGivetypeId) {
    this.couponGivetypeId = couponGivetypeId;
  }


}
