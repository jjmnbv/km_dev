package com.kmzyc.promotion.app.enums;

public enum ConsultReplyStatus {

	NOTRESPONSE("0", "未回复"), HAVERESPONSE("1", "已回复");

	private String status = null;
	private String title = null;

	ConsultReplyStatus(String status, String title) {
		this.status = status;
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("ConsultReplyStatus[status=").append(this.status).append(",title=").append(this.title)
				.append("]");
		return strBuilder.toString();
	}
}
