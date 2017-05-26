package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ActivityEntrySupplierType;

public class ActivityEntrySupplierTypeMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
			maps.put(ActivityEntrySupplierType.ALL_BRAND.getType(),
					ActivityEntrySupplierType.ALL_BRAND.getTitle());
			maps.put(ActivityEntrySupplierType.CHOICE_OF_SCORE.getType(),
					ActivityEntrySupplierType.CHOICE_OF_SCORE.getTitle());
			maps.put(ActivityEntrySupplierType.CHOICE_OF_CATOGORYS.getType(),
					ActivityEntrySupplierType.CHOICE_OF_CATOGORYS.getTitle());
			maps.put(ActivityEntrySupplierType.DESIGNATED_SUPPLIER.getType(),
					ActivityEntrySupplierType.DESIGNATED_SUPPLIER.getTitle());
			maps.put(ActivityEntrySupplierType.INVITE_SUPPLIER.getType(),
					ActivityEntrySupplierType.INVITE_SUPPLIER.getTitle());
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
