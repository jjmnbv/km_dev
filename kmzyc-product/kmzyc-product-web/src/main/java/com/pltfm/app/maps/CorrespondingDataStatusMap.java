package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.CorrespondingDataStatus;

public class CorrespondingDataStatusMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
			maps.put(CorrespondingDataStatus.NO.getStatus(),
					CorrespondingDataStatus.NO.getTitle());
			maps.put(CorrespondingDataStatus.YES.getStatus(),
					CorrespondingDataStatus.YES.getTitle());
			maps.put(CorrespondingDataStatus.FAILURE.getStatus(),
					CorrespondingDataStatus.FAILURE.getTitle());
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
