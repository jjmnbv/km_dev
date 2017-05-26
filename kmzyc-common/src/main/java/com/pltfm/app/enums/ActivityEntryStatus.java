package com.pltfm.app.enums;


/**
 * 活动报名状态
 * @author xkj
 *
 */
public enum ActivityEntryStatus {
	/*1：未报名
	2：已报名
	3：撤销报名*/
	NOT_ENTRY(1,"未报名"),
	ALREADY_ENTRY(2,"已报名"),
	CANCEL_ENTRY(3,"撤销报名");
	
	private Integer status;
	private String title = null;
	
	
	ActivityEntryStatus(Integer status, String title) {
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
		strBuilder.append("ActivityEntryStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
