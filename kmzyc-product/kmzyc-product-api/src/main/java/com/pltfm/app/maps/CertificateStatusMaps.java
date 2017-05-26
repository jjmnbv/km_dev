package com.pltfm.app.maps;


import com.pltfm.app.enums.CertificateStatus;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 功能：供应商资质验证状态Maps
 *
 * @author Zhoujiwei
 * @since 2016/8/10 16:11
 */
public class CertificateStatusMaps {

    private static Map<Integer, String> map = null;

    private static Map<Integer, CertificateStatus> enumsMap = null;

    public static Map<Integer, String> getMap() {
        if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap();
            maps.put(CertificateStatus.TO_CHECK.getStatus(), CertificateStatus.TO_CHECK.getTitle());
            maps.put(CertificateStatus.PASS.getStatus(), CertificateStatus.PASS.getTitle());
            maps.put(CertificateStatus.NOT_PASS.getStatus(), CertificateStatus.NOT_PASS.getTitle());
            map = maps;
        }
        return map;
    }

    public static Map<Integer, CertificateStatus> getEnumsMap() {
        if (enumsMap == null) {
            Map<Integer, CertificateStatus> enumsMaps = new LinkedHashMap();
            enumsMaps.put(CertificateStatus.TO_CHECK.getStatus(), CertificateStatus.TO_CHECK);
            enumsMaps.put(CertificateStatus.PASS.getStatus(), CertificateStatus.PASS);
            enumsMaps.put(CertificateStatus.NOT_PASS.getStatus(), CertificateStatus.NOT_PASS);
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

    public static CertificateStatus getEnum(Integer key) {
        if (enumsMap == null) {
            getEnumsMap();
        }
        return enumsMap.get(key);
    }
}