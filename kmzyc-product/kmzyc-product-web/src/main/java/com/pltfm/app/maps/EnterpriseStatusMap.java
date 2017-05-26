package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.EnterpriseType;


public class EnterpriseStatusMap {
    private static Map<String, String> map = null;

    public static Map<String, String> getMap() {
        if (map == null) {
            Map<String, String> maps = new LinkedHashMap<String, String>();
            maps.put(EnterpriseType.OPEN.getStatus(),
                EnterpriseType.OPEN.getTitle());
            maps.put(EnterpriseType.CLOSE.getStatus(), EnterpriseType.CLOSE.getTitle());
            map = maps;
        }
        return map;
    }

    public static String getValue(String key) {
        if (map == null) {
            getMap();
        }
        return map.get(key);
    }
}
