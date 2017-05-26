package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ActivityType;

public class ActivityTypeMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
			map = new LinkedHashMap<Integer, String>();
			map.put(ActivityType.PROMOTION_TYPE.getType(),
					ActivityType.PROMOTION_TYPE.getTitle());
			map.put(ActivityType.GRAPHIC_TYPE.getType(),
					ActivityType.GRAPHIC_TYPE.getTitle());
			map.put(ActivityType.CHANNL_TYPE.getType(),
					ActivityType.CHANNL_TYPE.getTitle());
			
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
