package com.pltfm.app.enums;


/**
 * 活动收费类型
 * @author xkj
 *
 */
public enum ActivityChargeType {
	/*1：免费
	2：固定收费
	3：单品收费
	4：返佣*/
	/**
	 * 免费
	 */
	FREE(1,"免费"),
	/**
	 * 固定收费
	 */
	FIXED_CHARGES(2,"固定收费"),
	/**
	 * 单品收费
	 */
	SINGLE_CHARGES(3,"单品收费"),
	/**
	 * 返佣
	 */
	RABATE(4,"返佣");
	
	private Integer type;
	private String title = null;
	
	
	ActivityChargeType(Integer type, String title) {
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
		strBuilder.append("ActivityChargeType[status=").append(this.type)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
