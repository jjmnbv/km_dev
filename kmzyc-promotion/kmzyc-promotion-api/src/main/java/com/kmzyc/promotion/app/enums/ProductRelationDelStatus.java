package com.kmzyc.promotion.app.enums;

public enum ProductRelationDelStatus {

	NOTDEL(0, "无效"), DEL(1, "有效");
	private Integer status;
	private String title = null;

	ProductRelationDelStatus(Integer status, String title) {
		this.status = status;
		this.title = title;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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
		strBuilder.append("ProductRelationDelStatus[status=").append(this.status).append(",title=").append(this.title)
				.append("]");
		return strBuilder.toString();
	}

}
