package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.YesOrNoStatus;

public class YesOrNoMap {
	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
			map = new LinkedHashMap<String, String>();
			map.put(YesOrNoStatus.NO.getStatus(),
					YesOrNoStatus.NO.getTitle());
			map.put(YesOrNoStatus.YES.getStatus(),
					YesOrNoStatus.YES.getTitle());
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
