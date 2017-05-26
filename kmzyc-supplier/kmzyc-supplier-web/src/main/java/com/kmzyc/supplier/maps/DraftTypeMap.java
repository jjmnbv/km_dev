package com.kmzyc.supplier.maps;

import com.pltfm.app.enums.DraftType;

import java.util.LinkedHashMap;
import java.util.Map;

public class DraftTypeMap {
	private static Map<Short, String> map = null;

	public static Map<Short, String> getMap() {
		if (map == null) {
			Map<Short, String> maps = new LinkedHashMap<Short, String>();
			maps.put(DraftType.ADD.getStatus(),DraftType.ADD.getTitle());
			maps.put(DraftType.UPDATE.getStatus(),DraftType.UPDATE.getTitle());
			maps.put(DraftType.ALONEPRICE.getStatus(),DraftType.ALONEPRICE.getTitle());
			maps.put(DraftType.ALONEIMAGE.getStatus(),DraftType.ALONEIMAGE.getTitle());
			map = maps;
		}
		return map;
	}

	public static String getValue(Short key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}
}
