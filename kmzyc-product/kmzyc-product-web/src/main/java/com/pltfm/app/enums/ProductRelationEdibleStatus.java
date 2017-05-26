package com.pltfm.app.enums;

public enum ProductRelationEdibleStatus {

	

	EDIBLE(0,"可编辑"),
	UNEDIBLE(1,"不可编辑");
	private Integer status;
	private String title = null;
	
	
	ProductRelationEdibleStatus(Integer status, String title) {
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
		strBuilder.append("ProductRelationEdibleStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}		
	
	
	
	
	
	
	
	
}
