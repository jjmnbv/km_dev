package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.StockLogOpType;
import com.kmzyc.promotion.app.enums.StockOutStatus;

/**
 * 出库单审核状态
 * 
 * @author luoyi
 */
@Service("stockOutStatusMap")
public class StockOutStatusMap {
	private static Map<String, String> map = ImmutableMap.copyOf(new HashMap<String, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 5321484843336764130L;

        {
            put(StockOutStatus.AUDIT.getStatus(), StockOutStatus.AUDIT.getTitle());
            put(StockOutStatus.UNAUDIT.getStatus(), StockOutStatus.UNAUDIT.getTitle());
            put(StockOutStatus.NOT_THROUGH_AUDIT.getStatus(), StockOutStatus.NOT_THROUGH_AUDIT.getTitle());
        }
    });

	public static Map<String, String> getMap() {
		
		return map;
	}

	public static String getValue(String key) {
		
		return map.get(key);
	}
}
