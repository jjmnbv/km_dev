package com.pltfm.app.maps;


import com.pltfm.app.enums.SupplierType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/10 16:11
 */
public class SupplierTypeMaps {

    private static Map<Integer, String> map = null;

    private static Map<Integer, SupplierType> enumsMap = null;

    public static Map<Integer, String> getMap() {
        if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap();
            maps.put(SupplierType.BUSINESS.getStatus(), SupplierType.BUSINESS.getTitle());
            maps.put(SupplierType.PRODUCTION.getStatus(), SupplierType.PRODUCTION.getTitle());
            maps.put(SupplierType.AGRICULTURAL.getStatus(), SupplierType.AGRICULTURAL.getTitle());
            map = maps;
        }
        return map;
    }

    public static Map<Integer, SupplierType> getEnumsMap() {
        if (enumsMap == null) {
            Map<Integer, SupplierType> enumsMaps = new LinkedHashMap();
            enumsMaps.put(SupplierType.BUSINESS.getStatus(), SupplierType.BUSINESS);
            enumsMaps.put(SupplierType.PRODUCTION.getStatus(), SupplierType.PRODUCTION);
            enumsMaps.put(SupplierType.AGRICULTURAL.getStatus(), SupplierType.AGRICULTURAL);
            enumsMap = enumsMaps;
        }
        return enumsMap;
    }

    public static String getValue(Integer key) {
        if (map == null) {
            getMap();
        }
        return map.get(key);
    }

    public static SupplierType getEnum(Integer key) {
        if (enumsMap == null) {
            getEnumsMap();
        }
        return enumsMap.get(key);
    }
}