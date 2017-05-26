package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.CouponStatus;

public class CouponStatusMap {
	private static Map<String, String> map = null;
	public static Map<String, String> getMap() {
		if (map == null) {
		Map<String,String>	maps = new LinkedHashMap<String, String>();
			maps.put(CouponStatus.HAVEGIVE_COUPONSTATUS.getType(),
					CouponStatus.HAVEGIVE_COUPONSTATUS.getTitle());
			maps.put(CouponStatus.HAVEUSE_COUPONSTATUS.getType(),
					CouponStatus.HAVEUSE_COUPONSTATUS.getTitle());
			maps.put(CouponStatus.NOTGIVE_COUPONSTATUS.getType(),
					CouponStatus.NOTGIVE_COUPONSTATUS.getTitle());
			maps.put(CouponStatus.NOTUSE_COUPONSTATUS.getType(),
					CouponStatus.NOTUSE_COUPONSTATUS.getTitle());
			maps.put(CouponStatus.HAVEPASSDATE_COUPONSTATUS.getType(),
					CouponStatus.HAVEPASSDATE_COUPONSTATUS.getTitle());
			maps.put(CouponStatus.FREEZE_COUPONSTATUS.getType(),
					CouponStatus.FREEZE_COUPONSTATUS.getTitle());
			maps.put(CouponStatus.INVALID_COUPONSTATUS.getType(),
					CouponStatus.INVALID_COUPONSTATUS.getTitle());
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
