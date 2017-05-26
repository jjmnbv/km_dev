package com.kmzyc.promotion.app.enums;

public enum PromotionShopSort {

	/**
	 * 所有商家
	 */
	ALL(1, "所有商家"),
	/**
	 * 指定入驻
	 */
	APPOINT(2, "指定入驻"),
	/**
	 * 自营代销
	 */
	SELF_SUPPORT(3, "自营代销");

	private Integer value;
	private String title = null;

	PromotionShopSort(Integer value, String title) {
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
		strBuilder.append("PromotionShopSort[shopsort=").append(this.value).append(",title=").append(this.title)
				.append("]");
		return strBuilder.toString();
	}
}
