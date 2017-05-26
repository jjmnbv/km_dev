package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;


import com.pltfm.app.enums.SuppliersShopStatus;

public class SuppliersShopStatusMap {





	private static Map<Short, String> map = null;

	public static Map<Short, String> getMap() {
		if (map == null) {
			Map<Short, String> maps = new LinkedHashMap<Short, String>();
			//maps.put(SuppliersShopStatus.EDIT.getStatus(),
					//SuppliersShopStatus.EDIT.getTitle());
			maps.put(SuppliersShopStatus.UNAPPLY.getStatus(),
					SuppliersShopStatus.UNAPPLY.getTitle());
			maps.put(SuppliersShopStatus.APPLYING.getStatus(),
					SuppliersShopStatus.APPLYING.getTitle());
			maps.put(SuppliersShopStatus.AUDIT.getStatus(),
					SuppliersShopStatus.AUDIT.getTitle());
			maps.put(SuppliersShopStatus.UNAUDIT.getStatus(),
					SuppliersShopStatus.UNAUDIT.getTitle());
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
