package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;



public class ShopMap {
	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
			map = new LinkedHashMap<String, String>();
//			map.put(CodeUtils.KANGMEI_CODE,
//					"康美");
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
