package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.StockLogBillType;
import com.kmzyc.promotion.app.enums.StockLogOpType;

public class StockLogOpTypeMap {
    private static Map<Short, String> map = ImmutableMap.copyOf(new HashMap<Short, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 5321484843336764130L;

        {
            put(StockLogOpType.IN.getType(), StockLogOpType.IN.getTitle());
            put(StockLogOpType.OUT.getType(), StockLogOpType.OUT.getTitle());
            put(StockLogOpType.ADD_IN_TRANSIT.getType(),
                    StockLogOpType.ADD_IN_TRANSIT.getTitle());
            put(StockLogOpType.DEC_IN_TRANSIT.getType(),
                    StockLogOpType.DEC_IN_TRANSIT.getTitle());
            put(StockLogOpType.ADD_ORDER.getType(), StockLogOpType.ADD_ORDER.getTitle());
            put(StockLogOpType.DEC_ORDER.getType(), StockLogOpType.DEC_ORDER.getTitle());
            put(StockLogOpType.OP_STOCK.getType(), StockLogOpType.OP_STOCK.getTitle());
        }
    });

    public static Map<Short, String> getMap() {
        
        return map;
    }

    public static String getValue(Integer type) {

        if (type == null) {

            return null;
        }

        return map.get(Short.valueOf(type.toString()));
    }
}
