package com.pltfm.app.enums;


/**
 * 物流单位
 * @author luoyi
 *
 */
public enum LogisticsCompanys{
	//此处不要将key设置为0
//	SHUNFENG("1","顺风快递"),
//	YUNDA("2","韵达快递"),
//	STO("3","申通快递");
//	
	SHUNFENG("顺丰快递","顺丰快递"),
	YUNDA("韵达快递","韵达快递"),
	STO("申通快递","申通快递");
	
	private String status;
	private String title = null;
	
	
	LogisticsCompanys(String status, String title) {
		this.status = status;
		this.title = title;
	}
	
	public String getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("LogisticsCompanys[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
