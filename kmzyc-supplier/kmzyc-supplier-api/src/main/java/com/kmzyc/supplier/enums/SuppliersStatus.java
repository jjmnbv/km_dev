package com.kmzyc.supplier.enums;


/**
 * 审核状态
 * @author xkj
 *
 */
public enum SuppliersStatus {
	UNAPPLY(Short.valueOf("1"),"待申请"),
	APPLYING(Short.valueOf("2"),"待审核"),
	AUDIT(Short.valueOf("3"),"审核通过"),
	UNAUDIT(Short.valueOf("4"),"审核不通过"),
	NOTCONFIRM(Short.valueOf("5"),"商家待确认");
	
	private Short status;
	private String title = null;
	
	SuppliersStatus(Short status, String title) {
		this.status = status;
		this.title = title;
	}
	
	public Short getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("SuppliersStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
