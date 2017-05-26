package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ApprovalType;

public class ApprovalTypeMap {
	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
			map = new LinkedHashMap<String, String>();
			map.put(ApprovalType.OTHER.getStatus(),
					ApprovalType.OTHER.getTitle());
			map.put(ApprovalType.GY_ZHUN.getStatus(),
					ApprovalType.GY_ZHUN.getTitle());
			map.put(ApprovalType.GY_SHI.getStatus(),
					ApprovalType.GY_SHI.getTitle());
			map.put(ApprovalType.GY_JIAN.getStatus(),
					ApprovalType.GY_JIAN.getTitle());
			map.put(ApprovalType.GS_JIAN.getStatus(),
					ApprovalType.GS_JIAN.getTitle());
			map.put(ApprovalType.WS_JIAN.getStatus(),
					ApprovalType.WS_JIAN.getTitle());
			
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
