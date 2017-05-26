package com.pltfm.app.map;

import com.google.common.collect.ImmutableMap;

import com.pltfm.app.util.SystemTypeEnumType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 系统编号枚举类
 *
 * @author lijianjun
 * @since 15-06-18
 */

public class SystemTypeEnumMap {
    private static Map<Integer, String> map = ImmutableMap
            .of(SystemTypeEnumType.SYSTEM_TYPE_ORDER.getType(),
                    SystemTypeEnumType.SYSTEM_TYPE_ORDER.getTitle());


    public static String getValue(Integer key) {
        return map.get(key);
    }
}
