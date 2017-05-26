package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ActivityChargeType;

public class ActivityChargeTypeMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
			map = new LinkedHashMap<Integer, String>();
			map.put(ActivityChargeType.FREE.getType(),
					ActivityChargeType.FREE.getTitle());
			map.put(ActivityChargeType.FIXED_CHARGES.getType(),
					ActivityChargeType.FIXED_CHARGES.getTitle());
			map.put(ActivityChargeType.SINGLE_CHARGES.getType(),
					ActivityChargeType.SINGLE_CHARGES.getTitle());
			map.put(ActivityChargeType.RABATE.getType(),
					ActivityChargeType.RABATE.getTitle());
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
