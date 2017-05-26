package com.kmzyc.supplier.maps;

import com.pltfm.app.util.OrderDictionaryEnum;

import java.util.LinkedHashMap;
import java.util.Map;

public class OrderSyncFlagMap {
	private static Map<Short, String> map = null;

	public static Map<Short, String> getMap() {
		if (map == null) {
			Map<Short, String> maps = new LinkedHashMap<Short, String>();
			maps.put((short)OrderDictionaryEnum.OrderSyncFlag.failed.getKey(),OrderDictionaryEnum.OrderSyncFlag.failed.getValue());
			maps.put((short)OrderDictionaryEnum.OrderSyncFlag.success.getKey(),OrderDictionaryEnum.OrderSyncFlag.success.getValue());
			maps.put((short)OrderDictionaryEnum.OrderSyncFlag.unSync.getKey(),OrderDictionaryEnum.OrderSyncFlag.unSync.getValue());
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
