package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ReturnOrExchangeStatus;

public class ReturnOrExchangeStatusMap {

	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
            Map<String, String> maps = new LinkedHashMap<String, String>();
			maps.put(ReturnOrExchangeStatus.UNARRIVAL.getKey(),
					ReturnOrExchangeStatus.UNARRIVAL.getValue());
			maps.put(ReturnOrExchangeStatus.ARRIVALOFGOODS.getKey(),
					ReturnOrExchangeStatus.ARRIVALOFGOODS.getValue());
			maps.put(ReturnOrExchangeStatus.QUALITYTESTING.getKey(),
					ReturnOrExchangeStatus.QUALITYTESTING.getValue());
			maps.put(ReturnOrExchangeStatus.ARRIVALOFGOODS.getKey(),
					ReturnOrExchangeStatus.ARRIVALOFGOODS.getValue());
			maps.put(ReturnOrExchangeStatus.PASS.getKey(),
					ReturnOrExchangeStatus.PASS.getValue());
			maps.put(ReturnOrExchangeStatus.UNPASS.getKey(),
					ReturnOrExchangeStatus.UNPASS.getValue());
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
