package com.kmzyc.promotion.app.enums;

/**
 * 审核状态
 * 
 * @author xkj
 * 
 */
public enum WarehouseInfoStatus {
	STOP("0", "停用"), START("1", "启用");

	private String status;
	private String title = null;

	WarehouseInfoStatus(String status, String title) {
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
		strBuilder.append("WarehouseInfoStatus[status=").append(this.status).append(",title=").append(this.title)
				.append("]");
		return strBuilder.toString();
	}
}
