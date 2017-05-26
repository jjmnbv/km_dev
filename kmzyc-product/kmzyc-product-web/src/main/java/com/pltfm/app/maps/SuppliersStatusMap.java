package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.supplier.enums.SuppliersStatus;

public class SuppliersStatusMap {



	private static Map<Short, String> map = null;

	public static Map<Short, String> getMap() {
		if (map == null) {
			Map<Short, String> maps = new LinkedHashMap<Short, String>();
			maps.put(SuppliersStatus.UNAPPLY.getStatus(),
					SuppliersStatus.UNAPPLY.getTitle());
			maps.put(SuppliersStatus.APPLYING.getStatus(),
					SuppliersStatus.APPLYING.getTitle());
			maps.put(SuppliersStatus.AUDIT.getStatus(),
					SuppliersStatus.AUDIT.getTitle());
			maps.put(SuppliersStatus.UNAUDIT.getStatus(),
					SuppliersStatus.UNAUDIT.getTitle());
			maps.put(SuppliersStatus.NOTCONFIRM.getStatus(),
							SuppliersStatus.NOTCONFIRM.getTitle());
				
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
