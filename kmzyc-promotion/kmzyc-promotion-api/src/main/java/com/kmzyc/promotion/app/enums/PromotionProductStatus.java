package com.kmzyc.promotion.app.enums;

public enum PromotionProductStatus {

	/**
	 * 已上线
	 */
	ONLINE(2, "已上线"),
	/**
	 * 已过期
	 */
	EXPIRED(3, "已下线"),
	/**
	 * 未上线
	 */
	NOT_ONLINE(1, "未上线");
	private Integer value;
	private String title = null;

	PromotionProductStatus(Integer value, String title) {
		this.value = value;
		this.title = title;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("PromotionStatus[status=").append(this.value).append(",title=").append(this.title)
				.append("]");
		return strBuilder.toString();
	}
}
