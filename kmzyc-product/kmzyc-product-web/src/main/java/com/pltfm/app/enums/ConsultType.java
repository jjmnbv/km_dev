package com.pltfm.app.enums;
/**
 * 
 * @author Administrator
 *咨询类型枚举
 */
public enum ConsultType {
	COMMODITY_CONSULT("1","商品咨询"),
	INVENTORY_CONSULT("2","库存配送咨询"),
	PAYMENT_CONSULT("3","支付帮助"),
	AFTERHELP_CONSULT("4","售后帮助"),
	SALESACTIVITY_CONSULT("5","促销活动");
	
	private String type;
	
	private String title = null ;
	ConsultType(String types,String title){
		this.type = types;
		this.title = title;
	}
	public String getType() {
		return type;
	}
    private void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
    private void setTitle(String title) {
		this.title = title;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("ConsultType[type=").append(this.type)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
