package com.pltfm.app.enums;


/**
 * 审核状态
 * @author xkj
 *
 */
public enum CorrespondingDataStatus {
	NO(0,"未对应"),
	YES(1,"已对应"),
	FAILURE(2,"对应失效");
	
	private Integer status;
	private String title = null;
	
	CorrespondingDataStatus(Integer status, String title) {
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
		strBuilder.append("AuditStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
