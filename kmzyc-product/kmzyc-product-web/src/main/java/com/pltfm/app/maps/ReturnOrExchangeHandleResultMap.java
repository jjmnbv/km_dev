package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ReturnOrExchangeHandleResult;

public class ReturnOrExchangeHandleResultMap {

	private static Map<String, String> map = null;

	public static Map<String, String> getMap() {
		if (map == null) {
            Map<String, String> maps = new LinkedHashMap<String, String>();
			maps.put(ReturnOrExchangeHandleResult.UNPASS.getKey(),
					ReturnOrExchangeHandleResult.UNPASS.getValue());
			maps.put(ReturnOrExchangeHandleResult.PASS.getKey(),
					ReturnOrExchangeHandleResult.PASS.getValue());
			maps.put(ReturnOrExchangeHandleResult.UNPROCESSED.getKey(),
					ReturnOrExchangeHandleResult.UNPROCESSED.getValue());
			maps.put(ReturnOrExchangeHandleResult.PROCESSEING.getKey(),
					ReturnOrExchangeHandleResult.PROCESSEING.getValue());
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
