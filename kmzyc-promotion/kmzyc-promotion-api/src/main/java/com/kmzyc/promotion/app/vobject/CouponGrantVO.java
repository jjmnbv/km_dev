package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.util.Date;

public class CouponGrantVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long couponGrantId;
	private  Long actStatus;
	private Date starttime;
	private Date endtime;
	private String customerName;
	private Long couponStatus;
	private Date grantUpdatetime;
	private Long couponIssuingId;//COUPON_ISSUING_ID
	private String couponInfoNo;//COUPON_INFO_NO
	private String activeCode;//ACTIVE_CODE
	//激活时间
	private Date ActTime;
	//使用时间
	private Date useTime;
	public Date getActTime() {
		return ActTime;
	}
	public void setActTime(Date actTime) {
		ActTime = actTime;
	}
	public Date getUseTime() {
		return useTime;
	}
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
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
	public Long getCouponIssuingId() {
		return couponIssuingId;
	}
	public void setCouponIssuingId(Long couponIssuingId) {
		this.couponIssuingId = couponIssuingId;
	}
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
	public Long getCouponGrantId() {
		return couponGrantId;
	}
	public void setCouponGrantId(Long couponGrantId) {
		this.couponGrantId = couponGrantId;
	}
	public Long getActStatus() {
		return actStatus;
	}
	public void setActStatus(Long actStatus) {
		this.actStatus = actStatus;
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Long getCouponStatus() {
		return couponStatus;
	}
	public void setCouponStatus(Long couponStatus) {
		this.couponStatus = couponStatus;
	}
	public Date getGrantUpdatetime() {
		return grantUpdatetime;
	}
	public void setGrantUpdatetime(Date grantUpdatetime) {
		this.grantUpdatetime = grantUpdatetime;
	}

}
