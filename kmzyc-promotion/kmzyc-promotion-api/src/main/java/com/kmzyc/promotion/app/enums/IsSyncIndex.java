package com.kmzyc.promotion.app.enums;

public enum IsSyncIndex {

	ISSYNC(1, "已同步"), NOTSYNC(0, "未同步");

	private int key;
	private String value;

	IsSyncIndex(int key, String value) {
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("IsSyncIndex[key=").append(this.key).append(",value=").append(this.value).append("]");
		return strBuilder.toString();
	}

}
