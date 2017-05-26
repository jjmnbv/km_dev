package com.kmzyc.promotion.app.enums;

/**
 * 审核状态
 * 
 * @author xkj
 * 
 */
public enum SuppliersStatus {
	UNAPPLY(new Short("1"), "待申请"), APPLYING(new Short("2"), "提交申请"), AUDIT(new Short("3"), "审核通过"), UNAUDIT(new Short(
			"4"), "审核不通过");

	private Short status;
	private String title = null;

	SuppliersStatus(Short status, String title) {
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
		strBuilder.append("SuppliersStatus[status=").append(this.status).append(",title=").append(this.title).append(
				"]");
		return strBuilder.toString();
	}
}
