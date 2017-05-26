package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.SystemCode;

public class SystemCodeMap {
	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
            Map<String, String> maps = new LinkedHashMap<String, String>();
            maps.put(SystemCode.JIEKE.getCode(),
					SystemCode.JIEKE.getTitle());
            maps.put(SystemCode.KMB2B.getCode(), SystemCode.KMB2B.getTitle());
            maps.put(SystemCode.JXC.getCode(),
					SystemCode.JXC.getTitle());
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
