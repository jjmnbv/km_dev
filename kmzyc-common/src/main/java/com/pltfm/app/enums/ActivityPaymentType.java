package com.pltfm.app.enums;


/**
 * 活动款项类型
 * @author xkj
 *
 */
public enum ActivityPaymentType {
	/*1：首次缴款
2：追加缴款
3：退款*/
	/**
	 * 首次缴款
	 */
	FIRST_PAY(1,"首次缴款"),
	/**
	 * 追加缴款
	 */
	ADD_PAY(2,"追加缴款"),
	/**
	 * 活动结束退款
	 */
	ACTIVITY_STOP_REFUND(3,"活动结束退款"),
	/**
	 * 活动终止退款
	 */
	ACTIVITY_CANCLE_REFUND(4,"活动终止退款"),
	/**
	 * 撤销报名退款
	 */
	ENTRY_CANCLE_REFUND(5,"撤销报名退款"),
	/**
	 * 报名审核不通过退款
	 */
	ENTRY_NOT_THROUGH_AUDIT_REFUND(6,"报名审核不通过退款");
	
	private Integer type;
	private String title = null;
	
	
	ActivityPaymentType(Integer type, String title) {
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
		strBuilder.append("ActivityPaymentType[status=").append(this.type)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
