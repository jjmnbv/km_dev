package com.kmzyc.supplier.enums;

public enum ShopMainType {	

	FIAGSHIP(Short.valueOf("1"),"旗舰店"),
	BOUTIQUE(Short.valueOf("2"),"专营店"),
	EXCLUSIVE(Short.valueOf("3"),"专卖店");
	
	private Short status;
	private String title = null;
	
	ShopMainType(Short status, String title) {
		this.status = status;
		this.title = title;
	}
	
	public Short getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("ShopMainType[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}



}
