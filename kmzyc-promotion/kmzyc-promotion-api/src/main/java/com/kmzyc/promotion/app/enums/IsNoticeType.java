package com.kmzyc.promotion.app.enums;

/**
 * 是否到货通知
 * 
 * @author xkj
 * 
 */
public enum IsNoticeType {
	NONSUPPORT("0", "不支持"), SUPPORT("1", "支持");

	private String status;
	private String title = null;

	IsNoticeType(String status, String title) {
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
		strBuilder.append("IsNoticeType[status=").append(this.status).append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
