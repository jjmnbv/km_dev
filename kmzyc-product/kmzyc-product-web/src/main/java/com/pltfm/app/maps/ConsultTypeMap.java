package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ConsultType;
/**
 * 
 * @author Administrator
 *咨询类型Map
 */
public class ConsultTypeMap {
	
	private static Map<String, String> map = null;
	
	public static Map<String, String> getMap() {
		if (map == null) {
		Map<String,String>	maps = new LinkedHashMap<String, String>();
			maps.put(ConsultType.COMMODITY_CONSULT.getType(),
					ConsultType.COMMODITY_CONSULT.getTitle());
			maps.put(ConsultType.INVENTORY_CONSULT.getType(),
					ConsultType.INVENTORY_CONSULT.getTitle());
			maps.put(ConsultType.PAYMENT_CONSULT.getType(),
					ConsultType.PAYMENT_CONSULT.getTitle());
			maps.put(ConsultType.AFTERHELP_CONSULT.getType(),
					ConsultType.AFTERHELP_CONSULT.getTitle());
			maps.put(ConsultType.SALESACTIVITY_CONSULT.getType(),
					ConsultType.SALESACTIVITY_CONSULT.getTitle());
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
