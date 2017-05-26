package com.kmzyc.promotion.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.promotion.app.enums.supplierRegisterMoney;

public class supplierRegisterMoneyMap {

	private static Map<Short, String> map = null;

	public static Map<Short, String> getMap() {
		if (map == null) {
			Map<Short, String> maps = new LinkedHashMap<Short, String>();
			maps.put(supplierRegisterMoney.MONEY.getStatus(), supplierRegisterMoney.MONEY.getTitle());
			maps.put(supplierRegisterMoney.MONEY1.getStatus(), supplierRegisterMoney.MONEY1.getTitle());
			maps.put(supplierRegisterMoney.MONEY2.getStatus(), supplierRegisterMoney.MONEY2.getTitle());
			maps.put(supplierRegisterMoney.MONEY3.getStatus(), supplierRegisterMoney.MONEY3.getTitle());
			maps.put(supplierRegisterMoney.MONEY4.getStatus(), supplierRegisterMoney.MONEY4.getTitle());
			map = maps;
		}
		return map;
	}

	public static String getValue(Short key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}

}
