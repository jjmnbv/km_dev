package com.kmzyc.promotion.app.enums;

/**
 * 出库单状态
 * 
 * @author kinspar
 * 
 */
public enum StockOutStatus {
	UNAUDIT("0", "待审核"), AUDIT("1", "已审核(出库)"), NOT_THROUGH_AUDIT("2", "审核不通过");

	private String status;
	private String title = null;

	StockOutStatus(String status, String title) {
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
		strBuilder.append("StockOut[status=").append(this.status).append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
