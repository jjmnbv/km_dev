package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.StockLogOpType;

public class StockLogOpTypeMap {
	private static Map<Short, String> map = null;

	public static Map<Short, String> getMap() {
		if (map == null) {
            Map<Short, String> maps = new LinkedHashMap<Short, String>();
			maps.put(StockLogOpType.IN.getType(),
					StockLogOpType.IN.getTitle());
			maps.put(StockLogOpType.OUT.getType(),
					StockLogOpType.OUT.getTitle());
			maps.put(StockLogOpType.ADD_IN_TRANSIT.getType(),
					StockLogOpType.ADD_IN_TRANSIT.getTitle());
			maps.put(StockLogOpType.DEC_IN_TRANSIT.getType(),
					StockLogOpType.DEC_IN_TRANSIT.getTitle());
			maps.put(StockLogOpType.ADD_ORDER.getType(),
					StockLogOpType.ADD_ORDER.getTitle());
			maps.put(StockLogOpType.DEC_ORDER.getType(),
					StockLogOpType.DEC_ORDER.getTitle());
			maps.put(StockLogOpType.OP_STOCK.getType(),
					StockLogOpType.OP_STOCK.getTitle());
            map = maps;
		}
		return map;
	}

	public static String getValue(Short type) {
		if (map == null) {
			getMap();
		}
		return map.get(type);
	}
}
