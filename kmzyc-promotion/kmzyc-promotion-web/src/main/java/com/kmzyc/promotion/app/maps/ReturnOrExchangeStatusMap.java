package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.DistributionInfostatus;
import com.kmzyc.promotion.app.enums.ReturnOrExchangeStatus;

public class ReturnOrExchangeStatusMap {

	private static Map<String, String> map = ImmutableMap.copyOf(new HashMap<String, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 5321484843336764130L;

        {
            put(ReturnOrExchangeStatus.UNARRIVAL.getKey(), ReturnOrExchangeStatus.UNARRIVAL.getValue());
            put(ReturnOrExchangeStatus.ARRIVALOFGOODS.getKey(), ReturnOrExchangeStatus.ARRIVALOFGOODS.getValue());
            put(ReturnOrExchangeStatus.QUALITYTESTING.getKey(), ReturnOrExchangeStatus.QUALITYTESTING.getValue());
            put(ReturnOrExchangeStatus.ARRIVALOFGOODS.getKey(), ReturnOrExchangeStatus.ARRIVALOFGOODS.getValue());
            put(ReturnOrExchangeStatus.PASS.getKey(), ReturnOrExchangeStatus.PASS.getValue());
            put(ReturnOrExchangeStatus.UNPASS.getKey(), ReturnOrExchangeStatus.UNPASS.getValue());
        }
    });

	public static Map<String, String> getMap() {
		
		return map;
	}

	public static String getValue(String key) {

	    return map.get(key);
	}
}
