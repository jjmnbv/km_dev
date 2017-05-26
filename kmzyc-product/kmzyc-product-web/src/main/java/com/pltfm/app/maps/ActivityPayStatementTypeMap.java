package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ActivityPayStatementType;

public class ActivityPayStatementTypeMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
			maps.put(ActivityPayStatementType.PAY_TYPE.getType(),
					ActivityPayStatementType.PAY_TYPE.getTitle());
			maps.put(ActivityPayStatementType.REFUND_TYPE.getType(),
					ActivityPayStatementType.REFUND_TYPE.getTitle());
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
