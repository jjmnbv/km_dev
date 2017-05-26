package com.pltfm.app.enums;


/**
 * 活动类型
 * @author xkj
 *
 */
public enum ActivityType {
	/*1：促销推广
	2：图文推广
	3：渠道推广*/
	PROMOTION_TYPE(1,"促销推广"),
	GRAPHIC_TYPE(2,"图文推广"),
	CHANNL_TYPE(3,"渠道推广");
	
	private Integer type;
	private String title = null;
	
	
	ActivityType(Integer type, String title) {
		this.type = type;
		this.title = title;
	}
	
	public Integer getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("ActivityType[status=").append(this.type)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
