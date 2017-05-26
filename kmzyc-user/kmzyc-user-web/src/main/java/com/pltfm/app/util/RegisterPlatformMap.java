package com.pltfm.app.util;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.pltfm.app.enums.RegisterPlatform;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/4/23 11:50
 */
public class RegisterPlatformMap {

    private static Map<Integer, String> map = ImmutableMap.copyOf(new HashMap<Integer, String>() {
        {
            put(RegisterPlatform.KMB2B.getType(), RegisterPlatform.KMB2B.getTitle());
            put(RegisterPlatform.KJ.getType(), RegisterPlatform.KJ.getTitle());
            put(RegisterPlatform.ZRZC.getType(), RegisterPlatform.ZRZC.getTitle());
            /* put(RegisterPlatform.SQDL.getType(), RegisterPlatform.SQDL.getTitle()); */
        }
    });

    private static Map<Integer, RegisterPlatform> registerPlatformMap =
            ImmutableMap.copyOf(new HashMap<Integer, RegisterPlatform>() {
                {
                    put(RegisterPlatform.KMB2B.getType(), RegisterPlatform.KMB2B);
                    put(RegisterPlatform.KJ.getType(), RegisterPlatform.KJ);
                    put(RegisterPlatform.ZRZC.getType(), RegisterPlatform.ZRZC);
                }
            });

    public static Map<Integer, String> getMap() {
        return map;
    }

    public static String getValue(Integer key) {
        return map.get(key);
    }

    public static Map<Integer, RegisterPlatform> getRegisterPlatformMap() {
        return registerPlatformMap;
    }

    public static RegisterPlatform getRegisterPlatform(Integer key) {
        return registerPlatformMap.get(key);
    }

}
