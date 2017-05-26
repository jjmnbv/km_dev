package com.kmzyc.promotion.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.promotion.app.enums.WarehouseInfoStatus;

public class WarehouseStatusMap {

	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
			Map<String, String> maps = new LinkedHashMap<String, String>();
			maps.put(WarehouseInfoStatus.STOP.getStatus(), WarehouseInfoStatus.STOP.getTitle());
			maps.put(WarehouseInfoStatus.START.getStatus(), WarehouseInfoStatus.START.getTitle());
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
