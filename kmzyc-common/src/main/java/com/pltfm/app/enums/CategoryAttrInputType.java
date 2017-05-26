package com.pltfm.app.enums;


/**
 * 类目属性类型
 * @author hmy
 *
 */
public enum CategoryAttrInputType {
	TEXT(0,"文本框"),
	RADIO(1,"单选框"),
	CHECKBOX(2,"多选框"),
	SELECT(3,"下拉框"),
    TEXTAREA(4,"文本域"),
	FILE(5,"文件");

	private Integer type;
	private String title = null;
	
	
	CategoryAttrInputType(Integer type, String title) {
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
		strBuilder.append("CategoryAttrInputType[type=").append(this.type)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
