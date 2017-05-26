package com.kmzyc.promotion.app.enums;

public enum BusiCategoryCondition {

	PRODTITLE("prodTitle", "产品标题"), BRANDNAME("brandName", "品牌名称"), CATENAME("pcId", "类目");

	private String key;
	private String value;

	BusiCategoryCondition(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("BusiCategoryCondition[key=").append(this.key).append(",value=").append(this.value).append(
				"]");
		return strBuilder.toString();
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
