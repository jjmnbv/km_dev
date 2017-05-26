package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.AuditStatus;

public class AuditStatusMap {
	private static Map<String, String> map = ImmutableMap.copyOf(new HashMap<String, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 7069651561296036266L;

        {
            put(AuditStatus.AUDIT.getStatus(), AuditStatus.AUDIT.getTitle());
            put(AuditStatus.NOT_THROUGH_AUDIT.getStatus(), AuditStatus.NOT_THROUGH_AUDIT.getTitle());
            put(AuditStatus.UNAUDIT.getStatus(), AuditStatus.UNAUDIT.getTitle());
        }
    });

	public static Map<String, String> getMap() {

        return map;
    }
	
	public static String getValue(String key) {

	    return map.get(key);
	}
}
