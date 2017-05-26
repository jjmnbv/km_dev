package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.StockOutStatus;
import com.kmzyc.promotion.app.enums.StockOutTypeStatus;

/**
 * 
 * @author luoyi 出库单类型Map
 * 
 */
@Service("stockOutTypeMap")
public class StockOutTypeMap {
    private static Map<Short, String> map = ImmutableMap.copyOf(new HashMap<Short, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 5321484843336764130L;

        {
            put(StockOutTypeStatus.ORDER.getStatus(), StockOutTypeStatus.ORDER.getTitle());
            put(StockOutTypeStatus.APPROPRIATION.getStatus(),
                    StockOutTypeStatus.APPROPRIATION.getTitle());
            put(StockOutTypeStatus.EXCHANGE.getStatus(),
                    StockOutTypeStatus.EXCHANGE.getTitle());
            put(StockOutTypeStatus.OTHER.getStatus(), StockOutTypeStatus.OTHER.getTitle());
        }
    });

    public static Map<Short, String> getMap() {

        return map;
    }

    public static String getValue(String key) {

        if (StringUtils.isBlank(key)) {

            return null;
        }

        return map.get(Short.valueOf(key));
    }
}
