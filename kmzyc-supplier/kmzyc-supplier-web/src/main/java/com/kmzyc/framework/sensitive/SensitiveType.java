package com.kmzyc.framework.sensitive;

public enum SensitiveType {
	
	commit(1), 	// 提交
	registe(2); // 注册
	
	
	private int code;
	
	SensitiveType (int code) {
		this.setCode(code);
	}
	
	public int getCode() {
		return code;
	}
    private void setCode(int code) {
		this.code = code;
	}
	
}
