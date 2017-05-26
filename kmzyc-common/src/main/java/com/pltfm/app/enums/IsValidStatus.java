package com.pltfm.app.enums;


/**
 * 是否有效
 * @author xkj
 *
 */
public enum IsValidStatus {
	UNVALID("0","无效"),
	VALID("1","有效");
	
	private String status;
	private String title = null;
	
	
	IsValidStatus(String status, String title) {
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
		strBuilder.append("IsValidStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
