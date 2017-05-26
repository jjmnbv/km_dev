package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ProductRelationTypeStatus;

public class RelationTypeMap {
	private static Map<String, String> map = null;
	
	private static Map<String, String> package_map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
            Map<String, String> maps = new LinkedHashMap<String, String>();
			maps.put(ProductRelationTypeStatus.RECOMME.getStatus().toString(),
					ProductRelationTypeStatus.RECOMME.getTitle());
			maps.put(ProductRelationTypeStatus.GLANCE.getStatus().toString(),
					ProductRelationTypeStatus.GLANCE.getTitle());
			maps.put(ProductRelationTypeStatus.PURCHASE.getStatus().toString(),
					ProductRelationTypeStatus.PURCHASE.getTitle());
            map = maps;
		}
		return map;
	}
	
	public static Map<String, String> getPackageMap() {
		if (package_map == null) {
            Map<String, String> package_maps = new LinkedHashMap<String, String>();
            package_maps.put(ProductRelationTypeStatus.PACKAGE.getStatus().toString(),
					ProductRelationTypeStatus.PACKAGE.getTitle());
            package_map = package_maps;
		}
		return package_map;
	}

	public static String getValue(String key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}
	
	public static String getPackageValue(String key) {
		if (map == null) {
			getPackageMap();
		}
		return package_map.get(key);
	}
}
