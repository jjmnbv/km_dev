package com.pltfm.app.enums;


/**
 * 产品属性类型
 * @author xkj
 *
 */
public enum ProductAttrType {
	CATEGORY(1,"类目属性"),
	DEFINITION(2,"自定义属性"),
	OPERATION(3,"运营属性"),
	//20150317maliqun add begin 支持按方抓药 药方等相关属性
	PRESCRIPT(4,"按方抓药属性"),
	PRESCRIPT_GROUP(41,"药方属性"),
	PRESCRIPT_CREATOR(42,"药方创建人属性"),
	PRESCRIPT_PEOPLEIMAGE(43,"药方创建人图片属性"),
	PRESCRIPT_PEOPLEINTRODUCE(44,"药方创建人介绍属性"),
	PRESCRIPT_USEDESCRIPTION(45,"使用说明属性"),
	PRESCRIPT_AFTERSERVICE(46,"售后服务属性");
	//20150317maliqun add end 支持经典方剂药方等相关属性
	
	private Integer type;
	private String title = null;
	
	
	ProductAttrType(Integer type, String title) {
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
		strBuilder.append("ProductAttrType[type=").append(this.type)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
