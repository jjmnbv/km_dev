package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ActivityBrandType;

public class ActivityBrandTypeMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
			maps.put(ActivityBrandType.ALL_BRAND.getType(),
					ActivityBrandType.ALL_BRAND.getTitle());
			maps.put(ActivityBrandType.DESIGNATED_BRAND.getType(),
					ActivityBrandType.DESIGNATED_BRAND.getTitle());
            map = maps;
		}
		return map;
	}

	public static String getValue(Integer key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}
}
