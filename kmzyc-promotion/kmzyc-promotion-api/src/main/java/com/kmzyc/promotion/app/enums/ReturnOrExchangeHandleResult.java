package com.kmzyc.promotion.app.enums;

public enum ReturnOrExchangeHandleResult {

	UNPASS("0", "拒绝"), PASS("1", "同意"), UNPROCESSED("2", "尚未处理"), PROCESSEING("3", "处理中");

	private String key;

	private String value;

	ReturnOrExchangeHandleResult(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("ReturnOrExchangeHandleResult[key=").append(this.key).append(",value=").append(this.value)
				.append("]");
		return strBuilder.toString();
	}

}
