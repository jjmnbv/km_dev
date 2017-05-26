package com.pltfm.app.enums;


public enum ProductRelationStatus {

	//UNVALID(0,"无效"),
	//VALID(1,"有效"),
	NEW(2,"新增"),
	UP(3,"已上架"),
	DOWN(4,"已下架");
	private Integer status;
	private String title = null;
	
	
	ProductRelationStatus(Integer status, String title) {
		this.status = status;
		this.title = title;
	}

	
	public Integer getStatus() {
		return status;
	}


    private void setStatus(Integer status) {
		this.status = status;
	}


	public String getTitle() {
		return title;
	}


    private void setTitle(String title) {
		this.title = title;
	}


	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("ProductRelationStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}	
	

