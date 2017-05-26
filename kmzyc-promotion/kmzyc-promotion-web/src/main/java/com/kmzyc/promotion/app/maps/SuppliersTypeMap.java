package com.kmzyc.promotion.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.promotion.app.enums.SuppliersType;

public class SuppliersTypeMap {

	private static Map<Short, String> map = null;

	public static Map<Short, String> getMap() {
		if (map == null) {
			Map<Short, String> maps = new LinkedHashMap<Short, String>();
			maps.put(SuppliersType.EMTER.getStatus(), SuppliersType.EMTER.getTitle());
			maps.put(SuppliersType.SELL.getStatus(), SuppliersType.SELL.getTitle());
			maps.put(SuppliersType.SUPPORT.getStatus(), SuppliersType.SUPPORT.getTitle());
			map = maps;
		}
		return map;
	}

	public static String getValue(Short key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}

}
