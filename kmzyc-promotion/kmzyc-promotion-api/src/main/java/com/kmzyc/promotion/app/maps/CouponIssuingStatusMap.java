package com.kmzyc.promotion.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.promotion.app.enums.CouponIssuingStatus;

public class CouponIssuingStatusMap {
	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
			Map<String, String> maps = new LinkedHashMap<String, String>();
			maps.put(CouponIssuingStatus.NOT_STARTING.getType(), CouponIssuingStatus.NOT_STARTING
					.getTitle());
			maps.put(CouponIssuingStatus.HAVE_DONE.getType(), CouponIssuingStatus.HAVE_DONE.getTitle());
			maps.put(CouponIssuingStatus.IS_ING.getType(), CouponIssuingStatus.IS_ING
					.getTitle());
			maps.put(CouponIssuingStatus.IS_PALUSE.getType(), CouponIssuingStatus.IS_PALUSE.getTitle());
			maps.put(CouponIssuingStatus.HAVE_STOP.getType(), CouponIssuingStatus.HAVE_STOP.getTitle());
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
