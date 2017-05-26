package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.StockOutStatus;

/**
 * 出库单审核状态
 * @author luoyi
 */
public class StockOutStatusMap {
	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
            Map<String, String> maps = new LinkedHashMap<String, String>();
			maps.put(StockOutStatus.AUDIT.getStatus(),
					StockOutStatus.AUDIT.getTitle());
			maps.put(StockOutStatus.UNAUDIT.getStatus(),
					StockOutStatus.UNAUDIT.getTitle());
			maps.put(StockOutStatus.NOT_THROUGH_AUDIT.getStatus(),
					StockOutStatus.NOT_THROUGH_AUDIT.getTitle());
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