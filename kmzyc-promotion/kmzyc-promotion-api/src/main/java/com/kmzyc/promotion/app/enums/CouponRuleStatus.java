package com.kmzyc.promotion.app.enums;

public enum CouponRuleStatus {
	NOTGIVE_COUPONSTATUS("1", "未发放"), HAVEGIVE_COUPONSTATUS("2", "已发放"), HAVEPASSDATE_COUPONSTATUS("5", "已过期");

	private String titile;

	private String type;

	CouponRuleStatus(String types, String title) {
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
		strBuilder.append("CouponStatus[type=").append(this.type).append(",title=").append(this.titile).append("]");
		return strBuilder.toString();
	}
}
