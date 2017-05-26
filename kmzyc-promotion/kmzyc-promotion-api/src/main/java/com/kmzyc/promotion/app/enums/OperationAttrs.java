package com.kmzyc.promotion.app.enums;

/**
 * 运营属性
 * 
 * @author xkj
 * 
 */
public enum OperationAttrs {
	IS_NOTICE(1l, "是否支持到货通知");

	private Long status;
	private String title = null;

	OperationAttrs(Long status, String title) {
		this.status = status;
		this.title = title;
	}

	public Long getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("OperationAttr[status=").append(this.status).append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
