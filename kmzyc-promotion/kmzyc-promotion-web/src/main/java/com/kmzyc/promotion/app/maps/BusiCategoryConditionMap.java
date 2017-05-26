package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.AuditStatus;
import com.kmzyc.promotion.app.enums.BusiCategoryCondition;

public class BusiCategoryConditionMap {
    private static Map<String, String> map = ImmutableMap.copyOf(new HashMap<String, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = -8759732372628287909L;

        {
            put(BusiCategoryCondition.PRODTITLE.getKey(),
                    BusiCategoryCondition.PRODTITLE.getValue());
            put(BusiCategoryCondition.BRANDNAME.getKey(),
                    BusiCategoryCondition.BRANDNAME.getValue());
            put(BusiCategoryCondition.CATENAME.getKey(),
                    BusiCategoryCondition.CATENAME.getValue());
        }
    });

    public static Map<String, String> getMap() {

        return map;
    }
    
    public static String getValue(String key) {

        return map.get(key);
    }
}
