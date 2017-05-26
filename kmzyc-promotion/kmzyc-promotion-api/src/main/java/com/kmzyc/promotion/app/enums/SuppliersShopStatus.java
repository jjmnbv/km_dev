package com.kmzyc.promotion.app.enums;

public enum SuppliersShopStatus {

	// EDIT(new Short("0"),"编辑"),
	UNAPPLY(new Short("1"), "申请中，未审核"), APPLYING(new Short("2"), "审核通过"), AUDIT(new Short("3"), "审核未通过"), UNAUDIT(
			new Short("4"), "永久关闭");

	private Short status;
	private String title = null;

	SuppliersShopStatus(Short status, String title) {
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
		strBuilder.append("SuppliersShopStatus[status=").append(this.status).append(",title=").append(this.title)
				.append("]");
		return strBuilder.toString();
	}

}
