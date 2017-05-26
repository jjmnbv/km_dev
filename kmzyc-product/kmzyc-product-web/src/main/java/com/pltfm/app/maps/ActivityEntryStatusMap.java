package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ActivityEntryStatus;

public class ActivityEntryStatusMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
			maps.put(ActivityEntryStatus.NOT_ENTRY.getStatus(),
					ActivityEntryStatus.NOT_ENTRY.getTitle());
			maps.put(ActivityEntryStatus.ALREADY_ENTRY.getStatus(),
					ActivityEntryStatus.ALREADY_ENTRY.getTitle());
			maps.put(ActivityEntryStatus.CANCEL_ENTRY.getStatus(),
					ActivityEntryStatus.CANCEL_ENTRY.getTitle());
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
