package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.StockLogBillType;

public class StockLogBillTypeMap {
	private static Map<Short, String> map = null;

	public static Map<Short, String> getMap() {
		if (map == null) {
            Map<Short, String> maps = new LinkedHashMap<Short, String>();
			maps.put(StockLogBillType.ADD_STOCK.getType(),
					StockLogBillType.ADD_STOCK.getTitle());
			maps.put(StockLogBillType.UPDATE_STOCK.getType(),
					StockLogBillType.UPDATE_STOCK.getTitle());
			maps.put(StockLogBillType.PURCHASE_INFO.getType(),
					StockLogBillType.PURCHASE_INFO.getTitle());
			maps.put(StockLogBillType.STOCK_IN.getType(),
					StockLogBillType.STOCK_IN.getTitle());
			maps.put(StockLogBillType.STOCK_OUT.getType(),
					StockLogBillType.STOCK_OUT.getTitle());
			maps.put(StockLogBillType.ORDER.getType(),
					StockLogBillType.ORDER.getTitle());
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
