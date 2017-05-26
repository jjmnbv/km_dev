package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts2.json.annotations.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.framework.exception.ObjectTransformException;

/**
 * 优惠券发放表
 * 
 * @author Administrator
 * 
 */
public class CouponGrant implements Serializable {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  // private static Logger logger = Logger.getLogger(CouponGrant.class);
  private static Logger logger = LoggerFactory.getLogger(CouponGrant.class);

  private Long couponGrantId;

  private Long couponId;

  private Integer customId;

  private Long couponStatus;

  private List<Coupon> couponList;

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
  private Long couponIssuingId;

  /**
   * 本地对象转为产品的对象
   * 
   * @return
   * @throws ObjectTransformException
   */
    public com.kmzyc.promotion.app.vobject.CouponGrant transFormToRemoteAddress()
      throws ObjectTransformException {
        com.kmzyc.promotion.app.vobject.CouponGrant grant =
                new com.kmzyc.promotion.app.vobject.CouponGrant();
    try {
      PropertyUtils.copyProperties(grant, this);
    } catch (IllegalAccessException e) {
      logger.error("将本地CouponGrant对象转换为远程对象出错：" + e.getMessage(), e);
      throw new ObjectTransformException("将本地OrderMain对象转换为远程对象出错！");
    } catch (InvocationTargetException e) {
      logger.error("将本地CouponGrant对象转换为远程对象出错：" + e.getMessage(), e);
      throw new ObjectTransformException("将本地CouponGrant对象转换为远程对象出错！");
    } catch (NoSuchMethodException e) {
      logger.error("将本地CouponGrant对象转换为远程对象出错：" + e.getMessage(), e);
    }

    return grant;
  }

  /**
   * 本地对象转为促销的对象
   * 
   * @return
   * @throws ObjectTransformException
   */
    public com.kmzyc.promotion.app.vobject.CouponGrant transFormToPromotionAddress()
      throws ObjectTransformException {
        com.kmzyc.promotion.app.vobject.CouponGrant grant =
                new com.kmzyc.promotion.app.vobject.CouponGrant();
    try {
      PropertyUtils.copyProperties(grant, this);
    } catch (IllegalAccessException e) {
      logger.error("将本地CouponGrant对象转换为远程对象出错：" + e.getMessage(), e);
      throw new ObjectTransformException("将本地OrderMain对象转换为远程对象出错！");
    } catch (InvocationTargetException e) {
      logger.error("将本地CouponGrant对象转换为远程对象出错：" + e.getMessage(), e);
      throw new ObjectTransformException("将本地CouponGrant对象转换为远程对象出错！");
    } catch (NoSuchMethodException e) {
      logger.error("将本地CouponGrant对象转换为远程对象出错：" + e.getMessage(), e);
    }

    return grant;
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

  @JSON(format = "yyyy-MM-dd HH:mm:ss")
  public Date getGrantEndtime() {
    return grantEndtime;
  }

  public void setGrantEndtime(Date grantEndtime) {
    this.grantEndtime = grantEndtime;
  }

  @JSON(format = "yyyy-MM-dd HH:mm:ss")
  public Date getStarttime_coupon() {
    return starttime_coupon;
  }

  public void setStarttime_coupon(Date starttime_coupon) {
    this.starttime_coupon = starttime_coupon;
  }

  @JSON(format = "yyyy-MM-dd HH:mm:ss")
  public Date getEndtime_coupon() {
    return endtime_coupon;
  }

  public void setEndtime_coupon(Date endtime_coupon) {
    this.endtime_coupon = endtime_coupon;
  }

  @JSON(format = "yyyy-MM-dd HH:mm:ss")
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

  @Override
  public String toString() {
    return "CouponGrant [couponGrantId=" + couponGrantId + ", couponId=" + couponId + ", customId="
        + customId + ", couponStatus=" + couponStatus + ", couponList=" + couponList
        + ", activeCode=" + activeCode + ", grantCreattime=" + grantCreattime + ", grantCreateman="
        + grantCreateman + ", grantUpdatetime=" + grantUpdatetime + ", grantUpdateman="
        + grantUpdateman + ", grantType=" + grantType + ", grantRelatedCode=" + grantRelatedCode
        + ", grantRemark=" + grantRemark + ", grantEndtime=" + grantEndtime + ", starttime_coupon="
        + starttime_coupon + ", endtime_coupon=" + endtime_coupon + ", grant_acttime="
        + grant_acttime + ", startTime=" + startTime + ", endTime=" + endTime + ", actTime="
        + actTime + ", couponIssuingId=" + couponIssuingId + "]";
  }


}
