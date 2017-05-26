package com.kmzyc.promotion.app.enums;

public enum ProductRelationTypeStatus {

	// PACKAGE(0, "套餐"), RECOMME(1, "人气"),GLANCE(2,"浏览行为"),PURCHASE(3,"购买行为");
	PACKAGE(0, "套餐商品"), RECOMME(1, "人气"), GLANCE(2, "浏览行为"), PURCHASE(3, "购买行为");
	private Integer status = null;
	private String title = null;

	ProductRelationTypeStatus(Integer status, String title) {
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
		StringBuilder str = new StringBuilder();
		str.append("ProductRelationTypeStatus[status=").append(this.status).append(",title=").append(this.title)
				.append("]");

		return str.toString();
	}

}
