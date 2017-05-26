package com.pltfm.app.enums;

public enum ZFProductRelationStatus {

	

	VALID(0,"无效"),
	NULLITY(1,"有效");
	private Integer status;
	private String title = null;
	
	
	ZFProductRelationStatus(Integer status, String title) {
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
		strBuilder.append("ProductRelationDelStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}	
	
	
}
