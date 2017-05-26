package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

public class DrugCategoryMap {
	
	private static Map<String, String> map = new LinkedHashMap<String, String>();

	public static Map<String, String> getMap() {
		if (map == null) {
			map = new LinkedHashMap<String, String>();
		}
		return map;
	}
	
	public static void setValue(String key,String value){
		DrugCategoryMap.map.put(key, value);
	}
	
	public static void removeValue(String key){
		DrugCategoryMap.map.remove(key);
	}

	public static String getValue(String key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}
}