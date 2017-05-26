package com.pltfm.app.enums;


/**
 * 活动款项类型
 * @author xkj
 *
 */
public enum ActivityPayStatementType {
	/*1：缴款
		2：退款*/
	/**
	 * 缴款
	 */
	PAY_TYPE(1,"缴款"),
	/**
	 * 退款
	 */
	REFUND_TYPE(2,"退款");
	
	private Integer type;
	private String title = null;
	
	
	ActivityPayStatementType(Integer type, String title) {
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
		strBuilder.append("ActivityPayStatementType[status=").append(this.type)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
