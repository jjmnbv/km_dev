package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.CouponGrantFlowStatus;

public class CouponGrantFlowTypeMap {
	private static Map<String, String> map = null;
	public static Map<String, String> getMap() {
		 
		Map<String,String>	maps = new LinkedHashMap<String, String>();
			maps.put(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType(),
					CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getTitle());
			maps.put(CouponGrantFlowStatus.FREEZE_COUPONFLOWSTATUS.getType(),
					CouponGrantFlowStatus.FREEZE_COUPONFLOWSTATUS.getTitle());
			maps.put(CouponGrantFlowStatus.UNFREEZE_COUPONFLOWSTATUS.getType(),
					CouponGrantFlowStatus.UNFREEZE_COUPONFLOWSTATUS.getTitle());
			maps.put(CouponGrantFlowStatus.HAVEUSE_COUPONFLOWSTATUS.getType(),
					CouponGrantFlowStatus.HAVEUSE_COUPONFLOWSTATUS.getTitle());
			maps.put(CouponGrantFlowStatus.HAVEPASSDATE_COUPONFLOWSTATUS.getType(),
					CouponGrantFlowStatus.HAVEPASSDATE_COUPONFLOWSTATUS.getTitle());
			maps.put(CouponGrantFlowStatus.INVALID_COUPONFLOWSTATUS.getType(),
					CouponGrantFlowStatus.INVALID_COUPONFLOWSTATUS.getTitle());
			maps.put(CouponGrantFlowStatus.ACTIVATION_COUPONFLOWSTATUS.getType(),
					CouponGrantFlowStatus.ACTIVATION_COUPONFLOWSTATUS.getTitle());
			
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
