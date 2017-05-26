package com.kmzyc.promotion.app.enums;

/**
 * 出库单日结状态
 * 
 * @author luoyi
 * @since 2013/09/06
 * 
 */
public enum StockOutDayEndStatus {

	UNREPORT("0", "未日结"), REPORT("1", "已日结");

	private String title = null;
	private String status = null;

	StockOutDayEndStatus(String status, String title) {
		this.status = status;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("StockOutDayEndStatus[status=").append(this.status).append(",title=").append(this.title).append("]");

		return str.toString();
	}
}
