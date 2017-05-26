package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.ApprovalType;
import com.kmzyc.promotion.app.enums.PurchaseInfoStatus;

/**
 * 采购单map
 * 
 * @author luoyi
 * 
 */
@Service("purchaseInfoStatusMap")
public class PurchaseInfoStatusMap {
    private static Map<Integer, String> map = ImmutableMap.copyOf(new HashMap<Integer, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 5666203001669258495L;

        {
            put(PurchaseInfoStatus.UNAUDIT.getStatus(), PurchaseInfoStatus.UNAUDIT.getTitle());
            put(PurchaseInfoStatus.AUDIT.getStatus(), PurchaseInfoStatus.AUDIT.getTitle());
            put(PurchaseInfoStatus.DOING.getStatus(), PurchaseInfoStatus.DOING.getTitle());
            put(PurchaseInfoStatus.FINISH.getStatus(), PurchaseInfoStatus.FINISH.getTitle());
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
