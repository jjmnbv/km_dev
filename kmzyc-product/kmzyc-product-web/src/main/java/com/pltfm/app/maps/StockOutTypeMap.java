package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.StockOutTypeStatus;

/**
 * 
 * @author luoyi
 * 出库单类型Map
 *
 */
public class StockOutTypeMap {
	private static Map<Short, String> map = null;

	public static Map<Short, String> getMap() {
		if (map == null) {
            Map<Short, String> maps = new LinkedHashMap<Short, String>();
			maps.put(StockOutTypeStatus.ORDER.getStatus(),
					StockOutTypeStatus.ORDER.getTitle());
			maps.put(StockOutTypeStatus.APPROPRIATION.getStatus(),
					StockOutTypeStatus.APPROPRIATION.getTitle());
			maps.put(StockOutTypeStatus.EXCHANGE.getStatus(),
					StockOutTypeStatus.EXCHANGE.getTitle());
			maps.put(StockOutTypeStatus.OTHER.getStatus(),
					StockOutTypeStatus.OTHER.getTitle());
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