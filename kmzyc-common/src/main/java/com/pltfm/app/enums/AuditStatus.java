package com.pltfm.app.enums;


/**
 * 审核状态
 * @author xkj
 *
 */
public enum AuditStatus {
	UNAUDIT("0","未审核"),
	AUDIT("1","审核已通过"),
	NOT_THROUGH_AUDIT("2","审核不通过");
	
	private String status;
	private String title = null;
	
	AuditStatus(String status, String title) {
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
		strBuilder.append("AuditStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
