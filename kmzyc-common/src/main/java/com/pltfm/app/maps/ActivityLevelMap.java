package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ActivityLevel;

public class ActivityLevelMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
			map = new LinkedHashMap<Integer, String>();
			map.put(ActivityLevel.DIAMOND_ACTIVITY.getLevel(),
					ActivityLevel.DIAMOND_ACTIVITY.getTitle());
			map.put(ActivityLevel.LARGE_ACTIVITY.getLevel(),
					ActivityLevel.LARGE_ACTIVITY.getTitle());
			map.put(ActivityLevel.MEDIUM_ACTIVITY.getLevel(),
					ActivityLevel.MEDIUM_ACTIVITY.getTitle());
			map.put(ActivityLevel.SMALL_ACTIVITY.getLevel(),
					ActivityLevel.SMALL_ACTIVITY.getTitle());
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
