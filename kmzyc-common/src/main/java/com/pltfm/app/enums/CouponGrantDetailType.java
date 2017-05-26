package com.pltfm.app.enums;
/**
 * 
 * @author Administrator
 *优惠券发放明细类型
 */
public enum CouponGrantDetailType {
	MANUAL_COUPONGRANTDETAILTYPE("11","手工类型发放"),
	REGIST_COUPONGRANTDETAILTYPE("21","注册类型发放"),
	ORDER_RELATEDACTIVITYGRANT("31","订单满足活动类型发放"),
	ORDER_RETURNORDER("32","订单退货"),
	ORDER_FREEZORDER("33","订单冻结"),
	POINTEXCUT_COUPONGRANTDETAILTYPE("41","积分兑换发放"),
	ORDER_UNFREEZORDER("34","订单解冻"),
	PHONE_SHAKEGRANT("51","手机端发放"),
	ORDER_PAYUSEING("35","订单支付使用"),
	LOTTERY_COUPONGRANTDETAILTYPE("36","注册类型发放"),
	LOTTERY_GRANT("51","抽奖奖品发放"),
	ANONYMOUS_GRANT("61","不记名发放");
	
	
	private String titile;
	 
	private String type;
	CouponGrantDetailType(String types,String title){
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
