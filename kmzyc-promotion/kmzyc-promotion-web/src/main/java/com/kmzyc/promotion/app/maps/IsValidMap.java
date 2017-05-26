package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.IsValidStatus;

public class IsValidMap {
    private static Map<String, String> map = ImmutableMap.copyOf(new HashMap<String, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 5666203001669258495L;

        {
            put(IsValidStatus.VALID.getStatus(), IsValidStatus.VALID.getTitle());
            put(IsValidStatus.UNVALID.getStatus(), IsValidStatus.UNVALID.getTitle());
        }
    });


    public static Map<String, String> getMap() {

        return map;
    }

    public static String getValue(String key) {

        return map.get(key);
    }
}
