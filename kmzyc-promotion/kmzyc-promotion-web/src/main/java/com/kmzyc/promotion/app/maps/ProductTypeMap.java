package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.ApprovalType;
import com.kmzyc.promotion.app.enums.ProductType;

public class ProductTypeMap {

    private static Map<Integer, String> map = ImmutableMap.copyOf(new HashMap<Integer, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 5666203001669258495L;

        {
            put(ProductType.NOTDRUG.getKey(), ProductType.NOTDRUG.getValue());
            put(ProductType.OTC.getKey(), ProductType.OTC.getValue());
            put(ProductType.MDSIN.getKey(), ProductType.MDSIN.getValue());
        }
    });

    public static Map<Integer, String> getMap() {

        return map;
    }

    public static String getValue(String key) {

        if (StringUtils.isBlank(key)) {

            return null;
        }

        return map.get(Integer.valueOf(key));
    }

}
