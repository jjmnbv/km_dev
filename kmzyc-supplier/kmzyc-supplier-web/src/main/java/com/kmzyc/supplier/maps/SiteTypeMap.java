package com.kmzyc.supplier.maps;


import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.cms.remote.util.SiteType;

public class SiteTypeMap {

	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
			Map<String, String> maps = new LinkedHashMap<String, String>();
            maps.put(SiteType.B2B.getKey(), SiteType.B2B.getValue());
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
