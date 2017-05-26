package com.pltfm.app.enums;

public enum CouponGrantFlowStatus {
	GIVE_COUPONFLOWSTATUS("1","发放"),
	FREEZE_COUPONFLOWSTATUS("2","冻结"),
	UNFREEZE_COUPONFLOWSTATUS("3","解冻"),
	HAVEUSE_COUPONFLOWSTATUS("4","使用"),
	HAVEPASSDATE_COUPONFLOWSTATUS("5","过期"),
	INVALID_COUPONFLOWSTATUS("6","作废"),
	ACTIVATION_COUPONFLOWSTATUS("7","激活");
	
	
	private String titile;
	 
	private String type;
	CouponGrantFlowStatus(String types,String title){
		this.type = types;
		this.titile=title;
	}
	
	public String getType() {
		return type;
	}
    private void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return titile;
	}
    private void setTitle(String title) {
		this.titile = title;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("CouponStatus[type=").append(this.type)
		.append(",title=").append(this.titile).append("]");
		return strBuilder.toString();
	}
}
