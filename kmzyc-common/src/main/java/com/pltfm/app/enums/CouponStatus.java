package com.pltfm.app.enums;

public enum CouponStatus {
	NOTGIVE_COUPONSTATUS("1","未发放"),
	HAVEGIVE_COUPONSTATUS("2","已发放"),
	NOTUSE_COUPONSTATUS("3","未使用"),
	HAVEUSE_COUPONSTATUS("4","已使用"),
	HAVEPASSDATE_COUPONSTATUS("5","已过期"),
	INVALID_COUPONSTATUS("6","已作废"),
	FREEZE_COUPONSTATUS("7","冻结");
	
	
	private String titile;
	 
	private String type;
	CouponStatus(String types,String title){
		this.type = types;
		this.titile=title;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return titile;
	}
	public void setTitle(String title) {
		this.titile = title;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("CouponStatus[type=").append(this.type)
		.append(",title=").append(this.titile).append("]");
		return strBuilder.toString();
	}
}
