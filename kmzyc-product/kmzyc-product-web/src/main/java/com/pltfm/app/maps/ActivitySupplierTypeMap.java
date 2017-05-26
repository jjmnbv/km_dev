package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ActivitySupplierType;

public class ActivitySupplierTypeMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
            maps.put(ActivitySupplierType.ALL_BRAND.getType(),
					ActivitySupplierType.ALL_BRAND.getTitle());
            maps.put(ActivitySupplierType.CHOICE_OF_SCORE.getType(),
					ActivitySupplierType.CHOICE_OF_SCORE.getTitle());
            maps.put(ActivitySupplierType.CHOICE_OF_CATOGORYS.getType(),
					ActivitySupplierType.CHOICE_OF_CATOGORYS.getTitle());
            maps.put(ActivitySupplierType.DESIGNATED_SUPPLIER.getType(),
					ActivitySupplierType.DESIGNATED_SUPPLIER.getTitle());
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
