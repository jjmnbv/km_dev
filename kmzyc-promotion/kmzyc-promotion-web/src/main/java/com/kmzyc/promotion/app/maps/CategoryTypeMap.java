package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.ApprovalType;
import com.kmzyc.promotion.app.enums.CategoryType;

public class CategoryTypeMap {
    private static Map<Integer, String> map = ImmutableMap.copyOf(new HashMap<Integer, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 7200773259889029360L;

        {
            put(CategoryType.PHYSICS.getKey(), CategoryType.PHYSICS.getValue());
            put(CategoryType.BUISNESS.getKey(), CategoryType.BUISNESS.getValue());
        }
    });

    public static Map<Integer, String> getMap() {

        return map;
    }

    public static String getValue(Integer key) {

        return map.get(key);
    }
}
