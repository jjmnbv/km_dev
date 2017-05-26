package com.kmzyc.promotion.app.enums;

/**
 * 产品属性类型
 * 
 * @author xkj
 * 
 */
public enum ProductAttrType {
	CATEGORY(1, "类目属性"), DEFINITION(2, "自定义属性"), OPERATION(3, "运营属性");

	private Integer type;
	private String title = null;

	ProductAttrType(Integer type, String title) {
		this.type = type;
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("ProductAttrType[type=").append(this.type).append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
