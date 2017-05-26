package com.kmzyc.promotion.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.promotion.app.enums.CouponGrantDetailType;

public class CouponGrantDetailTypeMap {

	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
			Map<String, String> maps = new LinkedHashMap<String, String>();
			maps.put(CouponGrantDetailType.MANUAL_COUPONGRANTDETAILTYPE.getType(),
					CouponGrantDetailType.MANUAL_COUPONGRANTDETAILTYPE.getTitle());
			maps.put(CouponGrantDetailType.REGIST_COUPONGRANTDETAILTYPE.getType(),
					CouponGrantDetailType.REGIST_COUPONGRANTDETAILTYPE.getTitle());
			maps.put(CouponGrantDetailType.ORDER_RELATEDACTIVITYGRANT.getType(),
					CouponGrantDetailType.ORDER_RELATEDACTIVITYGRANT.getTitle());
			maps.put(CouponGrantDetailType.ORDER_RETURNORDER.getType(), CouponGrantDetailType.ORDER_RETURNORDER
					.getTitle());
			maps.put(CouponGrantDetailType.POINTEXCUT_COUPONGRANTDETAILTYPE.getType(),
					CouponGrantDetailType.POINTEXCUT_COUPONGRANTDETAILTYPE.getTitle());
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
