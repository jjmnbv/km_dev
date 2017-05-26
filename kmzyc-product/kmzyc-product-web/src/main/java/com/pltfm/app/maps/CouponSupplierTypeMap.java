package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.CouponSupplierType;

public class CouponSupplierTypeMap {
	private static Map<String, String> map = null;
	public static Map<String, String> getMap() {
		 
		Map<String,String>	maps = new LinkedHashMap<String, String>();
			maps.put(CouponSupplierType.SELF_SUPPORT.getType(),
					CouponSupplierType.SELF_SUPPORT.getTitile());
			maps.put(CouponSupplierType.SELF_ENTER.getType(),
					CouponSupplierType.SELF_ENTER.getTitile());
			maps.put(CouponSupplierType.SELF_PROXY.getType(),
					CouponSupplierType.SELF_PROXY.getTitile());
			map = maps;
	 
		return map;
	}

	public static String getValue(String key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}

}
