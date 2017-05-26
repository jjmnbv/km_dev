package com.pltfm.app.map;

import com.google.common.collect.ImmutableMap;

import com.pltfm.app.util.EnchashmentResourceEnumType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 余额提现来源枚举Map
 *
 * @author lijianjun
 * @since 15-04-23
 */
public class EnchashmentResourceEnumMap {
    private static Map<String, String> map = ImmutableMap
            .of(EnchashmentResourceEnumType.ENCHASHMENT_RESOURCE_GYS.getType(),
                    EnchashmentResourceEnumType.ENCHASHMENT_RESOURCE_GYS.getTitle()
                   /* EnchashmentResourceEnumType.ENCHASHMENT_RESOURCE_VS.getType(),
                    EnchashmentResourceEnumType.ENCHASHMENT_RESOURCE_VS.getTitle(),
                    EnchashmentResourceEnumType.ENCHASHMENT_RESOURCE_JG.getType(),
                    EnchashmentResourceEnumType.ENCHASHMENT_RESOURCE_JG.getTitle()*/);


    public static Map<String, String> getMap() {
        return map;
    }

    public static String getValue(String key) {
        return map.get(key);

    }


}
