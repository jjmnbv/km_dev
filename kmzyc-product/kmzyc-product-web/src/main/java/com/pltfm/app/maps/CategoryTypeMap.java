package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.CategoryType;

public class CategoryTypeMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
			Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
            maps.put(CategoryType.PHYSICS.getKey(),
					CategoryType.PHYSICS.getValue());
            maps.put(CategoryType.BUISNESS.getKey(),
					CategoryType.BUISNESS.getValue());
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
