package com.pltfm.app.enums;


/**
 * 类目状态
 * @author xkj
 *
 */
public enum CategoryStatus {
	UNVALID(0,"无效"),
	VALID(1,"有效"),
	DELETED(2,"删除");
	
	private Integer status;
	private String title = null;
	
	
	CategoryStatus(Integer status, String title) {
		this.status = status;
		this.title = title;
	}
	
	public Integer getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("CategoryStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
