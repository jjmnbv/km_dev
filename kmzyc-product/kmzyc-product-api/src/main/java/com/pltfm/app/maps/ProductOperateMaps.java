package com.pltfm.app.maps;

import com.pltfm.app.enums.ProductOperateType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/10 16:11
 */
public class ProductOperateMaps {

    private static Map<Integer, String> map = null;

    public static Map<Integer, String> getMap() {
        if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap();
            maps.put(ProductOperateType.UP.getStatus(), ProductOperateType.UP.getTitle());
            maps.put(ProductOperateType.DOWN.getStatus(), ProductOperateType.DOWN.getTitle());
            maps.put(ProductOperateType.ILLEGAL_DOWN.getStatus(), ProductOperateType.ILLEGAL_DOWN.getTitle());
            maps.put(ProductOperateType.SYSTEM_DOWN.getStatus(), ProductOperateType.SYSTEM_DOWN.getTitle());
            maps.put(ProductOperateType.RELEASE_PRODUCT_PRICE.getStatus(), ProductOperateType.RELEASE_PRODUCT_PRICE.getTitle());
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
