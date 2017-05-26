package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ActivityChannl;

public class ActivityChannlMap {
	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
            Map<String, String> maps = new LinkedHashMap<String, String>();
			maps.put(ActivityChannl.KMYD.getChannl(),
					ActivityChannl.KMYD.getTitle());
            maps.put(ActivityChannl.KMB2B.getChannl(), ActivityChannl.KMB2B.getTitle());
			maps.put(ActivityChannl.FLW.getChannl(),
					ActivityChannl.FLW.getTitle());
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
