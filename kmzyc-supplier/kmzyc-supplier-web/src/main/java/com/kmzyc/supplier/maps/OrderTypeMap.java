package com.kmzyc.supplier.maps;

import com.pltfm.app.util.OrderDictionaryEnum;

import java.util.LinkedHashMap;
import java.util.Map;

public class OrderTypeMap {
	private static Map<Long, String> map = null;

	public static Map<Long, String> getMap() {
		if (map == null) {
			Map<Long, String> maps = new LinkedHashMap<Long, String>();
			maps.put(Long.valueOf(OrderDictionaryEnum.Order_Type.Normal.getKey()),OrderDictionaryEnum.Order_Type.Normal.getValue());
			maps.put(Long.valueOf(OrderDictionaryEnum.Order_Type.Prize.getKey()),OrderDictionaryEnum.Order_Type.Prize.getValue());
			/*maps.put((long)OrderDictionaryEnum.Order_Type.Times.getKey(),OrderDictionaryEnum.Order_Type.Times.getValue());
			maps.put((long)OrderDictionaryEnum.Order_Type.TimesReg.getKey(),OrderDictionaryEnum.Order_Type.TimesReg.getValue());
			maps.put((long)OrderDictionaryEnum.Order_Type.TimesUp.getKey(),OrderDictionaryEnum.Order_Type.TimesUp.getValue());*/
			map = maps;
		}
		return map;
	}

	public static String getValue(Long key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}
}
