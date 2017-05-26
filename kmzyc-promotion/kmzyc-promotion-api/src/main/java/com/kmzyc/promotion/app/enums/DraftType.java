package com.kmzyc.promotion.app.enums;

public enum DraftType {

	ADD((short) 1, "新增"), UPDATE((short) 2, "修改所有信息"), ALONEPRICE((short) 3, "单独修改价格"), ALONEIMAGE((short) 4, "单独修改图片"),
	// 20140730 maliqun add begin
	ALONEB2BPRICE((short) 5, "单独修改B2B价格");
	// 20140730 maliqun add end
	private Short status;
	private String title;

	DraftType(short status, String title) {
		this.status = status;
		this.title = title;
	}

	public Short getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
	}

}
