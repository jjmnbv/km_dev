package com.kmzyc.promotion.app.enums;

public enum supplierRegisterMoney {

	MONEY(new Short("100"), "100万"), MONEY1(new Short("1000"), "1000万"), MONEY2(new Short("2000"), "2000万"), MONEY3(
			new Short("3000"), "3000万"), MONEY4(new Short("4000"), "4000万");

	private Short status;
	private String title = null;

	supplierRegisterMoney(Short status, String title) {
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
		strBuilder.append("supplierRegisterMoney[status=").append(this.status).append(",title=").append(this.title)
				.append("]");
		return strBuilder.toString();
	}

}
