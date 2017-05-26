package com.pltfm.app.enums;
/**
 * 
 * @author Administrator
 *优惠券发放类型枚举
 */
public enum CouponGrantType {
	MANUAL_COUPONGRANTTYPE("1","手工发放"),
	REGIST_COUPONGRANTTYPE("2","注册发放"),
	ORDER_COUPONGRANTTYPE("3","订单发放"),
	POINTEXCUT_COUPONGRANTTYPE("4","积分兑换发放"),
	LOTTERY_PRIZE("5","抽奖奖品"),
	UNREGISTERED_COUPONGRANTTYPE("6","不记名发放");
	
	private String titile;
	 
	private String type;
	CouponGrantType(String types,String title){
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
		strBuilder.append("CouponType[type=").append(this.type)
		.append(",title=").append(this.titile).append("]");
		return strBuilder.toString();
	}
}
