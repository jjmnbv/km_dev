package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.ApprovalType;
import com.kmzyc.promotion.app.enums.PurchaseInfoTypeStatus;

/**
 * 采购单下单类型map
 * 
 * @author luoyi
 * 
 */
@Service("purchaseInfoTypeMap")
public class PurchaseInfoTypeMap {
	private static Map<String, String> map = ImmutableMap.copyOf(new HashMap<String, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 5666203001669258495L;

        {
            put(PurchaseInfoTypeStatus.AUTOBUY.getStatus(), PurchaseInfoTypeStatus.AUTOBUY.getTitle());
            put(PurchaseInfoTypeStatus.PEOPLEBUY.getStatus(), PurchaseInfoTypeStatus.PEOPLEBUY.getTitle());
        }
    });

	public static Map<String, String> getMap() {

	    return map;
	}

	public static String getValue(String key) {

	    return map.get(key);
	}
}
