package com.kmzyc.supplier.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.express.util.constant.ExpressSubConstants.ExpressStatus;

public class ExpressStatusMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
			Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
			maps.put(ExpressStatus.COLLECTED.getIntegerKey(),ExpressStatus.COLLECTED.getValue());
			maps.put(ExpressStatus.EXCHANGE.getIntegerKey(),ExpressStatus.EXCHANGE.getValue());
			maps.put(ExpressStatus.ON_WAY.getIntegerKey(),ExpressStatus.ON_WAY.getValue());
			maps.put(ExpressStatus.PUZZLED.getIntegerKey(),ExpressStatus.PUZZLED.getValue());
			maps.put(ExpressStatus.RETURND.getIntegerKey(),ExpressStatus.RETURND.getValue());
			maps.put(ExpressStatus.SENDING.getIntegerKey(),ExpressStatus.SENDING.getValue());
			maps.put(ExpressStatus.SIGN_IN.getIntegerKey(),ExpressStatus.SIGN_IN.getValue());
			maps.put(ExpressStatus.SIGN_OUT.getIntegerKey(),ExpressStatus.SIGN_OUT.getValue());
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
