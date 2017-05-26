package com.pltfm.app.enums;


/**
 * 活动级别
 * @author xkj
 *
 */
public enum ActivityLevel {
	/*1：钻级
		2：大型
		3：中型
		4：小型*/
	DIAMOND_ACTIVITY(1,"钻级"),
	LARGE_ACTIVITY(2,"大型"),
	MEDIUM_ACTIVITY(3,"中型"),
	SMALL_ACTIVITY(4,"小型");
	
	private Integer level;
	private String title = null;
	
	
	ActivityLevel(Integer level, String title) {
		this.level = level;
		this.title = title;
	}
	
	public Integer getLevel() {
		return level;
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("ActivityLevel[status=").append(this.level)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
