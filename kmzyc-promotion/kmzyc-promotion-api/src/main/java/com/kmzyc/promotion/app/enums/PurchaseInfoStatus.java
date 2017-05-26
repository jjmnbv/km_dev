package com.kmzyc.promotion.app.enums;

/**
 * 采购单状态
 * 
 * @author kinspar
 * 
 */
public enum PurchaseInfoStatus {
	UNAUDIT(0, "待审核"), AUDIT(1, "已审核"), DOING(2, "部分入库"), FINISH(3, "全部入库");

	private Integer status;
	private String title = null;

	PurchaseInfoStatus(Integer status, String title) {
		this.status = status;
		this.title = title;
	}

	public Integer getStatus() {
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
