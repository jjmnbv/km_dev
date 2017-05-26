package com.kmzyc.promotion.app.enums;

/**
 * 入库单入库类型
 * 
 * @author ljh
 * 
 */
public enum TideSaleType {
	FAVORABLE(1, "优惠套餐"), RECOMMEND(2, "推荐套餐");
	private Integer status = null;
	private String title = null;

	private TideSaleType(Integer status, String title) {
		this.status = status;
		this.title = title;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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
		str.append("TideSaleType[status=").append(this.status).append(",title=").append(this.title).append("]");

		return str.toString();
	}
}
