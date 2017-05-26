package com.kmzyc.supplier.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.supplier.enums.ShopAuditStatus;

public class ShopAuditStatusMap {

	private static Map<String,String> map = null;
	
	
	public static Map<String,String> getMap(){
		if(map == null) {
			Map<String,String> maps = new LinkedHashMap<String,String>();
			maps.put(ShopAuditStatus.EDIT.getStatus(), ShopAuditStatus.EDIT.getTitle());
			maps.put(ShopAuditStatus.APPLY.getStatus(), ShopAuditStatus.APPLY.getTitle());
			maps.put(ShopAuditStatus.AUDIT.getStatus(), ShopAuditStatus.AUDIT.getTitle());
			maps.put(ShopAuditStatus.NOTPASS.getStatus(), ShopAuditStatus.NOTPASS.getTitle());
			maps.put(ShopAuditStatus.CLOSEED.getStatus(), ShopAuditStatus.CLOSEED.getTitle());
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
