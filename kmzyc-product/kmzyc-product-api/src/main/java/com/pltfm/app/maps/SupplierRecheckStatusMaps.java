package com.pltfm.app.maps;

import com.pltfm.app.enums.ProductOperateType;
import com.pltfm.app.enums.SupplierRecheckStatus;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/10 16:11
 */
public class SupplierRecheckStatusMaps {

    private static Map<String, String> map = null;
    
    private static Map<Integer, SupplierRecheckStatus> enumsMap = null;

    public static Map<String, String> getMap() {
        if (map == null) {
            Map<String, String> maps = new LinkedHashMap();
            maps.put(SupplierRecheckStatus.TO_RECHECK.getStatus().toString(), SupplierRecheckStatus.TO_RECHECK.getTitle());
            maps.put(SupplierRecheckStatus.RECHECKING.getStatus().toString(), SupplierRecheckStatus.RECHECKING.getTitle());
            maps.put(SupplierRecheckStatus.PASS.getStatus().toString(), SupplierRecheckStatus.PASS.getTitle());
            maps.put(SupplierRecheckStatus.NOT_PASS.getStatus().toString(), SupplierRecheckStatus.NOT_PASS.getTitle());
            map = maps;
        }
        return map;
    }
    

    public static Map<Integer, SupplierRecheckStatus> getEnumsMap() {
        if (enumsMap == null) {
            Map<Integer, SupplierRecheckStatus> enumsMaps = new LinkedHashMap();
            enumsMaps.put(SupplierRecheckStatus.TO_RECHECK.getStatus(), SupplierRecheckStatus.TO_RECHECK);
            enumsMaps.put(SupplierRecheckStatus.RECHECKING.getStatus(), SupplierRecheckStatus.RECHECKING);
            enumsMaps.put(SupplierRecheckStatus.PASS.getStatus(), SupplierRecheckStatus.PASS);
            enumsMaps.put(SupplierRecheckStatus.NOT_PASS.getStatus(), SupplierRecheckStatus.NOT_PASS);
            enumsMap = enumsMaps;
        }
        return enumsMap;
    }

    public static String getValue(String key) {
        if (map == null) {
            getMap();
        }
        return map.get(key);
    }

    public static SupplierRecheckStatus getEnum(Integer key) {
        if (enumsMap == null) {
            getEnumsMap();
        }
        return enumsMap.get(key);
    }
}