package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.ApprovalType;

public class ApprovalTypeMap {
    private static Map<String, String> map = ImmutableMap.copyOf(new HashMap<String, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 5666203001669258495L;

        {
            put(ApprovalType.OTHER.getStatus(), ApprovalType.OTHER.getTitle());
            put(ApprovalType.GY_ZHUN.getStatus(), ApprovalType.GY_ZHUN.getTitle());
            put(ApprovalType.GY_SHI.getStatus(), ApprovalType.GY_SHI.getTitle());
            put(ApprovalType.GY_JIAN.getStatus(), ApprovalType.GY_JIAN.getTitle());
            put(ApprovalType.GS_JIAN.getStatus(), ApprovalType.GS_JIAN.getTitle());
            put(ApprovalType.WS_JIAN.getStatus(), ApprovalType.WS_JIAN.getTitle());
        }
    });

    public static Map<String, String> getMap() {

        return map;
    }
    
    public static String getValue(String key) {

        return map.get(key);
    }
}
