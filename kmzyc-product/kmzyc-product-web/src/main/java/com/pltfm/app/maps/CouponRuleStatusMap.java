package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.CouponRuleStatus;
import com.pltfm.app.enums.CouponStatus;

public class CouponRuleStatusMap {
	private static Map<String, String> map = null;
	public static Map<String, String> getMap() {
		if (map == null) {
		Map<String,String>	maps = new LinkedHashMap<String, String>();
			maps.put(CouponRuleStatus.HAVEGIVE_COUPONSTATUS.getType(),
					CouponRuleStatus.HAVEGIVE_COUPONSTATUS.getTitle());
			maps.put(CouponRuleStatus.NOTGIVE_COUPONSTATUS.getType(),
					CouponRuleStatus.NOTGIVE_COUPONSTATUS.getTitle());
			maps.put(CouponRuleStatus.HAVEPASSDATE_COUPONSTATUS.getType(),
					CouponRuleStatus.HAVEPASSDATE_COUPONSTATUS.getTitle());
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
