package com.kmzyc.promotion.app.vobject;
import java.io.Serializable;
public class CouponGrantInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long couponGrantId;
	private String couponInfoNo;
	private String activeCode;
	private Long couponInfoId;//COUPON_INFO_ID
	public Long getCouponInfoId() {
		return couponInfoId;
	}
	public void setCouponInfoId(Long couponInfoId) {
		this.couponInfoId = couponInfoId;
	}
	public Long getCouponGrantId() {
		return couponGrantId;
	}
	public void setCouponGrantId(Long couponGrantId) {
		this.couponGrantId = couponGrantId;
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

}
