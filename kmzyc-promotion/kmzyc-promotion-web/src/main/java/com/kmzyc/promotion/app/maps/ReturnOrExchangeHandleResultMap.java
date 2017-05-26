package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.DistributionInfostatus;
import com.kmzyc.promotion.app.enums.ReturnOrExchangeHandleResult;

public class ReturnOrExchangeHandleResultMap {

	private static Map<String, String> map = ImmutableMap.copyOf(new HashMap<String, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 5321484843336764130L;

        {
            put(ReturnOrExchangeHandleResult.UNPASS.getKey(), ReturnOrExchangeHandleResult.UNPASS.getValue());
            put(ReturnOrExchangeHandleResult.PASS.getKey(), ReturnOrExchangeHandleResult.PASS.getValue());
            put(ReturnOrExchangeHandleResult.UNPROCESSED.getKey(), ReturnOrExchangeHandleResult.UNPROCESSED
                    .getValue());
            put(ReturnOrExchangeHandleResult.PROCESSEING.getKey(), ReturnOrExchangeHandleResult.PROCESSEING
                    .getValue());
        }
    });

	public static Map<String, String> getMap() {

	    return map;
	}

	public static String getValue(String key) {

	    return map.get(key);
	}

}
