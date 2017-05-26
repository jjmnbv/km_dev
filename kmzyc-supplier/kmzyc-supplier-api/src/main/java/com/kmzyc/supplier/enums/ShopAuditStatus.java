package com.kmzyc.supplier.enums;


/**
 * 店铺状态
 * @author xkj
 *
 */
public enum ShopAuditStatus {
	EDIT("0","编辑"),
	APPLY("1","申请中，未审核"),
	AUDIT("2","审核通过"),
	NOTPASS("3","审核未通过"),
	CLOSEED("4","永久关闭");
	
	private String status;
	private String title = null;
	
	ShopAuditStatus(String status, String title) {
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
		strBuilder.append("ShopAuditStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
