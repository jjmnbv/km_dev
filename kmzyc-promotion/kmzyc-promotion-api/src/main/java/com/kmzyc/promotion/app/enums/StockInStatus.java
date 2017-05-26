package com.kmzyc.promotion.app.enums;

/**
 * 入库单状态
 * 
 * @author ljh
 * 
 */
public enum StockInStatus {

	UNAUDIT("0", "待审核"), AUDIT("1", "已审核");

	private String title = null;
	private String status = null;

	StockInStatus(String status, String title) {
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
		str.append("StockInStatus[status=").append(this.status).append(",title=").append(this.title).append("]");

		return str.toString();
	}

}
