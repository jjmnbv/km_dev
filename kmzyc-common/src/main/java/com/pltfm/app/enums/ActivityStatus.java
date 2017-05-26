package com.pltfm.app.enums;


/**
 * 活动状态
 * @author xkj
 *
 */
public enum ActivityStatus {
	/*活动状态
	0：草稿
	1：提交审核（待审核）
	---动态状态
	2：未到报名时间
	3：报名中
	4：活动未开始
	5：活动进行中
	6：活动已结束
	---
	7：已撤销
	8：活动中止*/
	DRAFT(0,"草稿"),
	PRE_AUDIT(1,"待审核"),
	NOT_ENTRY_TIME(2,"未到报名时间"),
	ENTRY_IN(3,"报名中"),
	ACTIVITY_NOT_START(4,"活动未开始"),
	ACTIVITY_IN(5,"活动进行中"),
	ACTIVITY_END(6,"活动已结束"),
	ACTIVITY_CANCELL(7,"已撤销"),
	ACTIVITY_STOP(8,"活动中止");
	
	private Integer status;
	private String title = null;
	
	
	ActivityStatus(Integer status, String title) {
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
		strBuilder.append("ActivityStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
