package com.kmzyc.supplier.enums;

/**
 *　账户余额交易类型
 * 
 * @author luoyi
 * @createDate 2013/11/08
 * 
 */
public enum RechargeTypeEnum {
	ONLINERECHARGE(1, "在线充值"),
	BACKGROUNDRECHARGE(2, "后台充值"),
	BALANCERECHARGE(3, "余额支付"),
	CANCELORDER(4, "取消订单"),
	ORDERRETURN(5, "订单退款"),
	ENCHASHMENT(6, "取现");

	private int type;
	private String title = null;

	RechargeTypeEnum(int type, String title) {
		this.type = type;
		this.title = title;
	}

	public int getType() {
		return type;
	}

    private void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

    private void setTitle(String title) {
		this.title = title;
	}
}