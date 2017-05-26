package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.StockLogBillType;

public class StockLogBillTypeMap {
    private static Map<Short, String> map = ImmutableMap.copyOf(new HashMap<Short, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 5321484843336764130L;

        {
            put(StockLogBillType.ADD_STOCK.getType(), StockLogBillType.ADD_STOCK.getTitle());
            put(StockLogBillType.UPDATE_STOCK.getType(),
                    StockLogBillType.UPDATE_STOCK.getTitle());
            put(StockLogBillType.PURCHASE_INFO.getType(),
                    StockLogBillType.PURCHASE_INFO.getTitle());
            put(StockLogBillType.STOCK_IN.getType(), StockLogBillType.STOCK_IN.getTitle());
            put(StockLogBillType.STOCK_OUT.getType(), StockLogBillType.STOCK_OUT.getTitle());
            put(StockLogBillType.ORDER.getType(), StockLogBillType.ORDER.getTitle());
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
