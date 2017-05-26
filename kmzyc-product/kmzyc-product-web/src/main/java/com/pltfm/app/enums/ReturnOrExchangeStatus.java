package com.pltfm.app.enums;

public enum ReturnOrExchangeStatus {

	UNARRIVAL("1","商品尚未收到"),
	ARRIVALOFGOODS("2","商品已收到"),
	QUALITYTESTING("3","质检中"),
	PASS("4","质检通过"),
	UNPASS("5","质检不通过");
	
	private String key;
	private String value;
	
	
	ReturnOrExchangeStatus(String key,String value){
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
		strBuilder.append("ReturnOrExchangeStatus[key=").append(this.key)
				.append(",value=").append(this.value).append("]");
		return strBuilder.toString();
	}
	
}
