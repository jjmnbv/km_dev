package com.pltfm.app.enums;


/**
 * 活动选择商家类型
 * @author xkj
 *
 */
public enum ActivitySupplierType {
	/*1：全部商家
2：按店铺评分选择
3：按类目选择
4：指定商家*/
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
	DESIGNATED_SUPPLIER(4,"指定商家");
	
	private Integer type;
	private String title = null;
	
	
	ActivitySupplierType(Integer type, String title) {
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
		strBuilder.append("ActivitySupplierType[status=").append(this.type)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
