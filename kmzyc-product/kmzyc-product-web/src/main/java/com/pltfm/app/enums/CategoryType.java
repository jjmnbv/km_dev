package com.pltfm.app.enums;

public enum CategoryType {

	PHYSICS(1,"物理类目"),
	BUISNESS(2,"运营类目");
	
	private int key;
	private String value;
	
	CategoryType(int key, String value) {
		this.key = key;
		this.value = value;
	}
	
	
	public int getKey() {
		return key;
	}

    private void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

    private void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("CategoryStatus[key=").append(this.key)
		.append(",value=").append(this.value).append("]");
		return strBuilder.toString();
	}
	
}
