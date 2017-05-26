package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.CouponGrantType;

public class CouponGrantTypeMap {
	private static Map<String, String> map = null;
	public static Map<String, String> getMap() {
		 
		Map<String,String>	maps = new LinkedHashMap<String, String>();
			maps.put(CouponGrantType.MANUAL_COUPONGRANTTYPE.getType(),
					CouponGrantType.MANUAL_COUPONGRANTTYPE.getTitle());
			maps.put(CouponGrantType.ORDER_COUPONGRANTTYPE.getType(),
					CouponGrantType.ORDER_COUPONGRANTTYPE.getTitle());
			maps.put(CouponGrantType.REGIST_COUPONGRANTTYPE.getType(),
					CouponGrantType.REGIST_COUPONGRANTTYPE.getTitle());
			maps.put(CouponGrantType.POINTEXCUT_COUPONGRANTTYPE.getType(),
					CouponGrantType.POINTEXCUT_COUPONGRANTTYPE.getTitle());
			maps.put(CouponGrantType.LOTTERY_PRIZE.getType(),
					CouponGrantType.LOTTERY_PRIZE.getTitle());
			maps.put(CouponGrantType.UNREGISTERED_COUPONGRANTTYPE.getType(), 
					CouponGrantType.UNREGISTERED_COUPONGRANTTYPE.getTitle());
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
