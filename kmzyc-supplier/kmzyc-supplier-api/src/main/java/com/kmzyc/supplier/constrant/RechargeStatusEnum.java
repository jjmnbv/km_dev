package com.kmzyc.supplier.constrant;

/**
 * 　账户余额交易状态(未付款、成功、失败)
 * 
 * @author luoyi
 * @createDate 2013/11/08
 * 
 */
public enum RechargeStatusEnum {
	//UNPAY(0, "未付款"), 
	SUCCESS(1, "成功"), 
	FAIL(2, "失败");

	private int status;
	private String title = null;

	RechargeStatusEnum(int status, String title) {
		this.status = status;
		this.title = title;
	}

	public int getStatus() {
		return status;
	}

    private void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

}