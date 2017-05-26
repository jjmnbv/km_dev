package com.kmzyc.promotion.app.enums;

/**
 * 运营属性
 * 
 * @author xkj
 * 
 */
public enum ProductMsg {
	IDS("IDS"), // SKU id
	OP("OP"); // 索引操作

	private String type;

	ProductMsg(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("OperationAttr[status=").append(this.type).append("]");
		return strBuilder.toString();
	}
}
