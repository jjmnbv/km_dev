package com.kmzyc.promotion.app.enums;

public enum SuppliersType {

	EMTER(new Short("1"), "自营"), SELL(new Short("2"), "入驻"), SUPPORT(new Short("3"), "代销");

	private Short status;
	private String title = null;

	SuppliersType(Short status, String title) {
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
		strBuilder.append("SuppliersType[status=").append(this.status).append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}

}
