package com.pltfm.app.enums;


/**
 *是与否状态
 * @author xkj
 *
 */
public enum YesOrNoStatus {
	NO("0","否"),
	YES("1","是");
	
	private String status;
	private String title = null;
	
	
	YesOrNoStatus(String status, String title) {
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
		strBuilder.append("YesOrNoStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
