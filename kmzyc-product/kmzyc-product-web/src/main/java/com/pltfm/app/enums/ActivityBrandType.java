package com.pltfm.app.enums;


/**
 * 活动品牌类型
 * @author xkj
 *
 */
public enum ActivityBrandType {
	/*1：全部品牌
	2：指定品牌*/
	/**
	 * 全部品牌
	 */
	ALL_BRAND(1,"全部品牌"),
	/**
	 * 指定品牌
	 */
	DESIGNATED_BRAND(2,"指定品牌");
	
	private Integer type;
	private String title = null;
	
	
	ActivityBrandType(Integer type, String title) {
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
		strBuilder.append("ActivityBrandType[status=").append(this.type)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
