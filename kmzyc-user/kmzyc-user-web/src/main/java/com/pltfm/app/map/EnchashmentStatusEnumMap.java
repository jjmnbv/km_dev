package com.pltfm.app.map;

import com.google.common.collect.ImmutableMap;

import com.pltfm.app.util.EnchashmentStatusEnumType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 余额提现状态枚举Map
 *
 * @author lijianjun
 * @since 15-04-23
 */
public class EnchashmentStatusEnumMap {
    private static Map<String, String> map = ImmutableMap
            .of(EnchashmentStatusEnumType.Status_Examine.getType(),
                    EnchashmentStatusEnumType.Status_Examine.getTitle(),
                    EnchashmentStatusEnumType.Stuats_Complete.getType(),
                    EnchashmentStatusEnumType.Stuats_Complete.getTitle(),
                    EnchashmentStatusEnumType.Status_Refused.getType(),
                    EnchashmentStatusEnumType.Status_Refused.getTitle(),
                    EnchashmentStatusEnumType.Status_Pass.getType(),
                    EnchashmentStatusEnumType.Status_Pass.getTitle(),
                    EnchashmentStatusEnumType.Status_Fail.getType(),
                    EnchashmentStatusEnumType.Status_Fail.getTitle());

    public static Map<String, String> getMap() {
        return map;
    }

    public static String getValue(String key) {
        return map.get(key);
    }

}
