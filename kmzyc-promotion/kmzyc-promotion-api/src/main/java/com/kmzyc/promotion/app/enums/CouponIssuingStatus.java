package com.kmzyc.promotion.app.enums;
/**
 * 
 * @author hl
 *优惠券发放表的状态，主要针对于注册类型的优惠券
 */
public enum CouponIssuingStatus {
	NOT_STARTING("1", "未开始"), 
	HAVE_DONE("2", "已完成"), 
	IS_ING("3", "进行中"),
	IS_PALUSE("4","暂停"),
	HAVE_STOP("5","截止");

	private String titile;

	private String type;

	CouponIssuingStatus(String types, String title) {
		this.type = types;
		this.titile = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return titile;
	}

	public void setTitle(String title) {
		this.titile = title;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("CouponIssuingStatus[type=").append(this.type).append(",title=").append(this.titile).append("]");
		return strBuilder.toString();
	}
}
