package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.PromotionStatus;


public class PromotionStatusMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
			maps.put(PromotionStatus.ISSUE.getValue(),
			        PromotionStatus.ISSUE.getTitle());
            maps.put(PromotionStatus.NOT_ISSUE.getValue(),
                PromotionStatus.NOT_ISSUE.getTitle());
            maps.put(PromotionStatus.ONLINE.getValue(),
                PromotionStatus.ONLINE.getTitle());
            maps.put(PromotionStatus.EXPIRED.getValue(),
                PromotionStatus.EXPIRED.getTitle());
            maps.put(PromotionStatus.NOT_ONLINE.getValue(),
                PromotionStatus.NOT_ONLINE.getTitle());
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
