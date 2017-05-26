package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.AuditStatus;

public class AuditStatusMap {
	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
            Map<String, String> maps = new LinkedHashMap<String, String>();
			maps.put(AuditStatus.AUDIT.getStatus(),
					AuditStatus.AUDIT.getTitle());
			maps.put(AuditStatus.NOT_THROUGH_AUDIT.getStatus(),
					AuditStatus.NOT_THROUGH_AUDIT.getTitle());
			maps.put(AuditStatus.UNAUDIT.getStatus(),
					AuditStatus.UNAUDIT.getTitle());
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
