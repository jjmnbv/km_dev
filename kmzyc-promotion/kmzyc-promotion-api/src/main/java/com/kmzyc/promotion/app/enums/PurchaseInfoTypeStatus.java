package com.kmzyc.promotion.app.enums;

/**
 * 采购单状态
 * 
 * @author kinspar
 * 
 */
public enum PurchaseInfoTypeStatus {
	AUTOBUY("1", "系统采购"), PEOPLEBUY("2", "手动采购");

	private String status;
	private String title = null;

	PurchaseInfoTypeStatus(String status, String title) {
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
		strBuilder.append("PurchaseInfo[status=").append(this.status).append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
