package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.ProductRelationTypeStatus;
import com.kmzyc.promotion.app.enums.PurchaseInfoStatus;

public class RelationTypeMap {
	private static Map<String, String> map = ImmutableMap.copyOf(new HashMap<String, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 5666203001669258495L;

        {
            put(ProductRelationTypeStatus.RECOMME.getStatus().toString(), ProductRelationTypeStatus.RECOMME
                    .getTitle());
            put(ProductRelationTypeStatus.GLANCE.getStatus().toString(), ProductRelationTypeStatus.GLANCE
                    .getTitle());
            put(ProductRelationTypeStatus.PURCHASE.getStatus().toString(), ProductRelationTypeStatus.PURCHASE
                    .getTitle());
        }
    });

	private static Map<String, String> package_map = ImmutableMap.copyOf(new HashMap<String, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 5666203001669258495L;

        {
            put(ProductRelationTypeStatus.PACKAGE.getStatus().toString(), ProductRelationTypeStatus.PACKAGE
                    .getTitle());
        }
    });

	public static Map<String, String> getMap() {
		
		return map;
	}

	public static Map<String, String> getPackageMap() {
		
		return package_map;
	}

	public static String getValue(String key) {
		
		return map.get(key);
	}

	public static String getPackageValue(String key) {
		
		return package_map.get(key);
	}
}
