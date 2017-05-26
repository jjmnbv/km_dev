package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.DistributionInfostatus;

/**
 * 配送单到达状态
 * @author luoyi
 *
 */
public class DistriButionInfoMap {
	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
            Map<String, String> maps = new LinkedHashMap<String, String>();
            maps.put(DistributionInfostatus.UNDELIVERIES.getStatus(),
					DistributionInfostatus.UNDELIVERIES.getTitle());
            maps.put(DistributionInfostatus.ISDELIVERIES.getStatus(),
					DistributionInfostatus.ISDELIVERIES.getTitle());
            map=maps;
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
