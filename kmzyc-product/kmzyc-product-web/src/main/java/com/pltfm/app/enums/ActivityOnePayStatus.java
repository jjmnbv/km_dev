package com.pltfm.app.enums;


/**
 * 活动报名状态
 * @author xkj
 *
 */
public enum ActivityOnePayStatus {
	
	INVALID(0,"无效"),
	VALID(1,"有效");
	
	private Integer status;
	private String title = null;
	
	
	ActivityOnePayStatus(Integer status, String title) {
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
		strBuilder.append("ActivityOnePayStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
