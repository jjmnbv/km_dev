package com.kmzyc.supplier.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.supplier.enums.ShopMainStatus;

public class ShopMainStatusMap {

	private static Map<String,String> map = null;
	
	
	public static Map<String,String> getMap(){
		if(map == null) {
			Map<String,String> maps = new LinkedHashMap<String,String>();
			maps.put(ShopMainStatus.OPEN.getStatus(), ShopMainStatus.OPEN.getTitle());
			maps.put(ShopMainStatus.CLOSE.getStatus(), ShopMainStatus.CLOSE.getTitle());
			map = maps;
		}
		return map;
	}
	
	public static String getProductStatusValue(String key){
		if(map == null) {
			getMap();
		}
		return map.get(key);
	}
	
}
