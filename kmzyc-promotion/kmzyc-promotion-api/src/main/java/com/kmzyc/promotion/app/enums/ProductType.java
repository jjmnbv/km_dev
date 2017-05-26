package com.kmzyc.promotion.app.enums;

public enum ProductType {

	NOTDRUG(0, "非药品"), OTC(1, "OTC药品"), MDSIN(2, "医疗器械");

	private Integer key;
	private String value;

	ProductType(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("ProductType[key=").append(this.key).append(",value=").append(this.value).append("]");
		return strBuilder.toString();
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
