package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ProductType;

public class ProductTypeMap {

	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
            maps.put(ProductType.NOTDRUG.getKey(),ProductType.NOTDRUG.getValue());
            maps.put(ProductType.OTC.getKey(),ProductType.OTC.getValue());
            map =maps;
		}
		return map;
	}
	
	public static String getValue(Integer key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}
	
	
}
