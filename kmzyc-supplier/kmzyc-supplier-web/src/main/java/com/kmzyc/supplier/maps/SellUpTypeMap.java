package com.kmzyc.supplier.maps;

import java.util.LinkedHashMap;
import java.util.Map;


public class SellUpTypeMap {

	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
			Map<String, String> maps = new LinkedHashMap<String, String>();
			maps.put("1","停止销售直到活动结束");
			maps.put("2","恢复原价销售");
			map = maps;
		}
		return map;
	}
    
	
	public static String getValue(String key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}
}
