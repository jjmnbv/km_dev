package com.kmzyc.supplier.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.supplier.enums.ShopMainServiceType;

public class ShopMainServiceTypeMap {

	private static Map<Short, String> map = null;

	public static Map<Short, String> getMap() {
		if (map == null) {
			Map<Short, String> maps = new LinkedHashMap<Short, String>();
			maps.put(ShopMainServiceType.SERQQ.getStatus(),
					ShopMainServiceType.SERQQ.getTitle());
			maps.put(ShopMainServiceType.WANGWANG.getStatus(),
					ShopMainServiceType.WANGWANG.getTitle());
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
