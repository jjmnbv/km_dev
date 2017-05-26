package com.kmzyc.supplier.maps;

import java.util.LinkedHashMap;
import java.util.Map;


public class MeetDataTypeMap {

	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
			Map<String, String> maps = new LinkedHashMap<String, String>();
			maps.put("1","金额（元）");
			maps.put("2","数量（件）");
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
