package com.kmzyc.supplier.maps;

import com.pltfm.app.enums.ApprovalType;

import java.util.LinkedHashMap;
import java.util.Map;

public class ApprovalTypeMap {
	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
            Map<String,String> maps = new LinkedHashMap();
            maps.put(ApprovalType.OTHER.getStatus(),
					ApprovalType.OTHER.getTitle());
            maps.put(ApprovalType.GY_ZHUN.getStatus(),
					ApprovalType.GY_ZHUN.getTitle());
            maps.put(ApprovalType.GY_SHI.getStatus(),
					ApprovalType.GY_SHI.getTitle());
            maps.put(ApprovalType.GY_JIAN.getStatus(),
					ApprovalType.GY_JIAN.getTitle());
            maps.put(ApprovalType.GS_JIAN.getStatus(),
					ApprovalType.GS_JIAN.getTitle());
            maps.put(ApprovalType.WS_JIAN.getStatus(),
					ApprovalType.WS_JIAN.getTitle());
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
