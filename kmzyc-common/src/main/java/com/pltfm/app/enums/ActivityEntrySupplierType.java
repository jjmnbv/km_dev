package com.pltfm.app.enums;


/**
 * 活动报名商家类型
 * @author xkj
 *
 */
public enum ActivityEntrySupplierType {
	/*1：全部商家
2：指定商家
3：邀请商家*/
	/**
	 * 全部商家
	 */
	ALL_BRAND(1,"全部商家"),
	/**
	 * 按店铺评分选择
	 */
	CHOICE_OF_SCORE(2,"按店铺评分选择"),
	/**
	 * 按类目选择
	 */
	CHOICE_OF_CATOGORYS(3,"按类目选择"),
	/**
	 * 指定商家
	 */
	DESIGNATED_SUPPLIER(4,"指定商家"),
	/**
	 * 邀请商家
	 */
	INVITE_SUPPLIER(5,"邀请商家");
	
	private Integer type;
	private String title = null;
	
	
	ActivityEntrySupplierType(Integer type, String title) {
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
		strBuilder.append("ActivityEntrySupplierType[status=").append(this.type)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
