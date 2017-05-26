package com.pltfm.app.maps;


import com.pltfm.app.enums.CertificateType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/10 16:11
 */
public class CertificateTypeMaps {

    private static Map<Integer, String> map = null;

    private static Map<Integer, CertificateType> enumsMap = null;

    public static Map<Integer, String> getMap() {
        if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap();
            maps.put(CertificateType.THREE.getStatus(), CertificateType.THREE.getTitle());
            maps.put(CertificateType.BUSINESS_LICENCE.getStatus(), CertificateType.BUSINESS_LICENCE.getTitle());
            maps.put(CertificateType.ORGANIZATION.getStatus(), CertificateType.ORGANIZATION.getTitle());
            maps.put(CertificateType.TAX_REG_CERTIFICATE.getStatus(), CertificateType.TAX_REG_CERTIFICATE.getTitle());
            maps.put(CertificateType.ID_CARD.getStatus(), CertificateType.ID_CARD.getTitle());
            maps.put(CertificateType.FOOD_BUSINESS_LICENSE.getStatus(), CertificateType.FOOD_BUSINESS_LICENSE.getTitle());
            maps.put(CertificateType.FOOD_SAFETY_MANAGEMENT_LICENSE.getStatus(), CertificateType.FOOD_SAFETY_MANAGEMENT_LICENSE.getTitle());
            maps.put(CertificateType.FOOD_CIRCULATION_LICENSE.getStatus(), CertificateType.FOOD_CIRCULATION_LICENSE.getTitle());
            maps.put(CertificateType.HYGIENE_LICENSE.getStatus(), CertificateType.HYGIENE_LICENSE.getTitle());
            maps.put(CertificateType.FOOD_PRODUCTION_LICENSE.getStatus(), CertificateType.FOOD_PRODUCTION_LICENSE.getTitle());
            map = maps;
        }
        return map;
    }

    public static Map<Integer, CertificateType> getEnumsMap() {
        if (enumsMap == null) {
            Map<Integer, CertificateType> enumsMaps = new LinkedHashMap();
            enumsMaps.put(CertificateType.THREE.getStatus(), CertificateType.THREE);
            enumsMaps.put(CertificateType.BUSINESS_LICENCE.getStatus(), CertificateType.BUSINESS_LICENCE);
            enumsMaps.put(CertificateType.ORGANIZATION.getStatus(), CertificateType.ORGANIZATION);
            enumsMaps.put(CertificateType.TAX_REG_CERTIFICATE.getStatus(), CertificateType.TAX_REG_CERTIFICATE);
            enumsMaps.put(CertificateType.ID_CARD.getStatus(), CertificateType.ID_CARD);
            enumsMaps.put(CertificateType.FOOD_BUSINESS_LICENSE.getStatus(), CertificateType.FOOD_BUSINESS_LICENSE);
            enumsMaps.put(CertificateType.FOOD_SAFETY_MANAGEMENT_LICENSE.getStatus(), CertificateType.FOOD_SAFETY_MANAGEMENT_LICENSE);
            enumsMaps.put(CertificateType.FOOD_CIRCULATION_LICENSE.getStatus(), CertificateType.FOOD_CIRCULATION_LICENSE);
            enumsMaps.put(CertificateType.HYGIENE_LICENSE.getStatus(), CertificateType.HYGIENE_LICENSE);
            enumsMaps.put(CertificateType.FOOD_PRODUCTION_LICENSE.getStatus(), CertificateType.FOOD_PRODUCTION_LICENSE);
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

    public static CertificateType getEnum(Integer key) {
        if (enumsMap == null) {
            getEnumsMap();
        }
        return enumsMap.get(key);
    }
}