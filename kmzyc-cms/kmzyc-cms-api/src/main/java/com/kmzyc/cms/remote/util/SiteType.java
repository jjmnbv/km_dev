package com.kmzyc.cms.remote.util;

/**
 * 站点数据类型
 * @author river
 *
 */
public enum SiteType {
	
    B2B("B2B", "康美中药城");// 康美商城站点
	
	private String key;
	private String value;
	
	SiteType(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	
	
	
}
