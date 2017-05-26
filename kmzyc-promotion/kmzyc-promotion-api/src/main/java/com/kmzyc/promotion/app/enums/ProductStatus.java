package com.kmzyc.promotion.app.enums;

/**
 * 产品状态
 * 
 * @author xkj
 * 
 */
public enum ProductStatus {
	DRAFT("0", "草稿"), UNAUDIT("1", "待审核"), AUDIT("2", "已审核,待上架"), UP("3", "已上架"), DOWN("4", "已下架"), SYSDOWN("5", "系统下架"), AUDITUNPASS(
			"6", "审核未通过");

	private String status;

	private String title = null;

	ProductStatus(String status, String title) {
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
		strBuilder.append("ProductStatus[status=").append(this.status).append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
