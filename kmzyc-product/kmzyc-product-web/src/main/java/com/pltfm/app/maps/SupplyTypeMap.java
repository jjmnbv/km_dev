package com.pltfm.app.maps;

import com.kmzyc.supplier.enums.SuppliersType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/5/17 14:12
 */
public class SupplyTypeMap {

    private static Map<Integer, String> map = null;

    public SupplyTypeMap() {

    }

    public static Map<Integer, String> getMap() {
        if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
            maps.put(Integer.valueOf(SuppliersType.EMTER.getStatus().toString()), SuppliersType.EMTER.getTitle());
            maps.put(Integer.valueOf(SuppliersType.SELL.getStatus().toString()), "第三方");
            map = maps;
        }

        return map;
    }

    public static String getValue(Integer key) {
        if (map == null) {
            getMap();
        }

        return map.get(key);
    }
}
