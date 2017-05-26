package com.kmzyc.promotion.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.promotion.app.enums.SuppliersStatus;

public class SuppliersStatusMap {

	private static Map<Short, String> map = null;

	public static Map<Short, String> getMap() {
		if (map == null) {
			Map<Short, String> maps = new LinkedHashMap<Short, String>();
			maps.put(SuppliersStatus.APPLYING.getStatus(), SuppliersStatus.APPLYING.getTitle());
			maps.put(SuppliersStatus.AUDIT.getStatus(), SuppliersStatus.AUDIT.getTitle());
			maps.put(SuppliersStatus.UNAUDIT.getStatus(), SuppliersStatus.UNAUDIT.getTitle());
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
