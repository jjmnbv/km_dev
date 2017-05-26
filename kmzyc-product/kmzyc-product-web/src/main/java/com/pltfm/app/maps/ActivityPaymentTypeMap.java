package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ActivityPaymentType;

public class ActivityPaymentTypeMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
			maps.put(ActivityPaymentType.FIRST_PAY.getType(),
					ActivityPaymentType.FIRST_PAY.getTitle());
			maps.put(ActivityPaymentType.ADD_PAY.getType(),
					ActivityPaymentType.ADD_PAY.getTitle());
			maps.put(ActivityPaymentType.ACTIVITY_STOP_REFUND.getType(),
					ActivityPaymentType.ACTIVITY_STOP_REFUND.getTitle());
			maps.put(ActivityPaymentType.ACTIVITY_CANCLE_REFUND.getType(),
					ActivityPaymentType.ACTIVITY_CANCLE_REFUND.getTitle());
			maps.put(ActivityPaymentType.ENTRY_CANCLE_REFUND.getType(),
					ActivityPaymentType.ENTRY_CANCLE_REFUND.getTitle());
			maps.put(ActivityPaymentType.ENTRY_NOT_THROUGH_AUDIT_REFUND.getType(),
					ActivityPaymentType.ENTRY_NOT_THROUGH_AUDIT_REFUND.getTitle());
            map = maps;
		}
		return map;
	}

	public static String getValue(Integer key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}
}
