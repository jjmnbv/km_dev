package com.kmzyc.supplier.enums;


/**
 * 店铺联系方式
 * @author xgh
 *
 */
public enum ShopMainServiceType {
	SERQQ(Short.valueOf("1"),"QQ"),
	WANGWANG(Short.valueOf("2"),"旺旺");
	
	private Short status;
	private String title = null;
	
	ShopMainServiceType(Short status, String title) {
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
		strBuilder.append("ShopMainStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
