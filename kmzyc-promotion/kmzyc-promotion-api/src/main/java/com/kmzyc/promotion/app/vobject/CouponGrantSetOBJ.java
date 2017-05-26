package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.util.Date;

public class CouponGrantSetOBJ implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long couponIssuingId;
	private Long couponId;
	private Date issuingStartTime;
	private Date issuingEndTime;
	private Long isStatus;
	private Long issuingCount;//ISSUING_COUNT生成不记名优惠劵数量
	private String customLeveId;//CUSTOM_LEVEID客户等级ID
	private String customId;//CUSTOM_ID客户ID 
	private Long  couponGiveTypeId;//COUPON_GIVETYPE_ID 优惠券发放类别ID     1:手工发放类型 2：注册发放类型 6：不记名发放
	public Long getCouponIssuingId() {
		return couponIssuingId;
	}
	public void setCouponIssuingId(Long couponIssuingId) {
		this.couponIssuingId = couponIssuingId;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
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
	public Long getIsStatus() {
		return isStatus;
	}
	public void setIsStatus(Long isStatus) {
		this.isStatus = isStatus;
	}
	public Long getIssuingCount() {
		return issuingCount;
	}
	public void setIssuingCount(Long issuingCount) {
		this.issuingCount = issuingCount;
	}
	public String getCustomLeveId() {
		return customLeveId;
	}
	public void setCustomLeveId(String customLeveId) {
		this.customLeveId = customLeveId;
	}
	public String getCustomId() {
		return customId;
	}
	public void setCustomId(String customId) {
		this.customId = customId;
	}
	public Long getCouponGiveTypeId() {
		return couponGiveTypeId;
	}
	public void setCouponGiveTypeId(Long couponGiveTypeId) {
		this.couponGiveTypeId = couponGiveTypeId;
	}


}