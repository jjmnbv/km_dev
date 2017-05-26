package com.pltfm.app.enums;

public enum CouponProductType {
	SINGLE(0,"单独商品"),
	INPACKAGE(1,"在套餐中的商品");
	
	
	
	private Integer key;
	private String value;
	
	CouponProductType(Integer key,String value){
		this.key = key;
		this.value = value;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("CouponProductType[key=").append(this.key)
		.append(",value=").append(this.value).append("]");
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
