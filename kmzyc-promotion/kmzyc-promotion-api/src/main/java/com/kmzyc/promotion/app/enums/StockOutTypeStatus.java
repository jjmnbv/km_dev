package com.kmzyc.promotion.app.enums;

/**
 * 出库单类型状态
 * 
 * @author kinspar
 * 
 */
public enum StockOutTypeStatus {
	ORDER(new Short("1"), "订单出库"), APPROPRIATION(new Short("2"), "调拨出库"), EXCHANGE(new Short("3"), "换货出库"), OTHER(
			new Short("4"), "其他出库");

	private Short status;
	private String title = null;

	StockOutTypeStatus(Short status, String title) {
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
		strBuilder.append("StockOut[status=").append(this.status).append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
