package com.pltfm.app.enums;

public enum CouponSupplierType {
	SELF_SUPPORT("1","所有商家"),
	SELF_ENTER("2","康美自营代销"),
	SELF_PROXY("3","指定商家入驻");
	
	private String titile;
	 
	private String type;

	private CouponSupplierType(String type,String titile) {
		this.titile = titile;
		this.type = type;
	}

	public String getTitile() {
		return titile;
	}

    private void setTitile(String titile) {
		this.titile = titile;
	}

	public String getType() {
		return type;
	}

    private void setType(String type) {
		this.type = type;
	}
	
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("CouponType[type=").append(this.type)
		.append(",title=").append(this.titile).append("]");
		return strBuilder.toString();
	}
}
