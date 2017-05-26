package com.kmzyc.supplier.enums;


/**
 * 店铺状态
 * @author xkj
 *
 */
public enum ShopPageTemplateType {
	DEFAULT(2,"默认模板"),
	SIMPLE(3,"简易模版");
	
	private Integer type;
	private String title = null;
	
	ShopPageTemplateType(Integer type, String title) {
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
		strBuilder.append("ShopPageTemplateType[type=").append(this.type)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
