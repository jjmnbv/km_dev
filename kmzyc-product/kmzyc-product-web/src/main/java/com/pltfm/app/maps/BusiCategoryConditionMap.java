package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.BusiCategoryCondition;

public class BusiCategoryConditionMap {
	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
            Map<String, String> maps = new LinkedHashMap<String, String>();
            maps.put(BusiCategoryCondition.PRODTITLE.getKey(),
					BusiCategoryCondition.PRODTITLE.getValue());
            maps.put(BusiCategoryCondition.BRANDNAME.getKey(),
					BusiCategoryCondition.BRANDNAME.getValue());
            maps.put(BusiCategoryCondition.CATENAME.getKey(),
					BusiCategoryCondition.CATENAME.getValue());
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
