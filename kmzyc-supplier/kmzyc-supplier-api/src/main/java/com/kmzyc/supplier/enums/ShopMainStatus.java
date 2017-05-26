package com.kmzyc.supplier.enums;


/**
 * 店铺状态
 * @author xkj
 *
 */
public enum ShopMainStatus {
	OPEN("1","开启"),
	CLOSE("0","关闭");
	
	private String status;
	private String title = null;
	
	ShopMainStatus(String status, String title) {
		this.status = status;
		this.title = title;
	}
	
	public String getStatus() {
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
