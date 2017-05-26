package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.IsValidStatus;

public class IsValidMap {
	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
            Map<String, String> maps = new LinkedHashMap<String, String>();
            maps.put(IsValidStatus.VALID.getStatus(),
					IsValidStatus.VALID.getTitle());
            maps.put(IsValidStatus.UNVALID.getStatus(),
					IsValidStatus.UNVALID.getTitle());
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
