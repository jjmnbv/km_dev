package com.kmzyc.promotion.app.enums;

/**
 * 入库单入库类型
 * 
 * @author ljh
 * 
 */
public enum StockInType

{
	PURCHASEIN(new Short("1"), "采购入库"), REPLACEMENT(new Short("4"), "换货入库"), REJECTEDMENT(new Short("3"), "退货入库"), OTHERIN(
			new Short("5"), "其他入库");

	private Short status = null;
	private String title = null;

	StockInType(short status, String title) {
		this.status = status;
		this.title = title;

	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("StockInType[status=").append(this.status).append(",title=").append(this.title).append("]");

		return str.toString();
	}

}
