package com.kmzyc.supplier.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.supplier.enums.ShopMainType;

public class ShopMainTypeMap {



	private static Map<Short, String> map = null;

	public static Map<Short, String> getMap() {
		if (map == null) {
			Map<Short, String> maps = new LinkedHashMap<Short, String>();
			maps.put(ShopMainType.FIAGSHIP.getStatus(),
					ShopMainType.FIAGSHIP.getTitle());
			maps.put(ShopMainType.BOUTIQUE.getStatus(),
					ShopMainType.BOUTIQUE.getTitle());
			maps.put(ShopMainType.EXCLUSIVE.getStatus(),
					ShopMainType.EXCLUSIVE.getTitle());
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
