package com.pltfm.app.util;

import com.google.common.collect.ImmutableMap;

import com.pltfm.app.enums.RegisterDevice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/4/23 11:50
 */
public class RegisterDeviceMap {

    private static Map<Integer, String> map = ImmutableMap.copyOf(new HashMap<Integer, String>() {
        {
            put(RegisterDevice.PC.getType(), RegisterDevice.PC.getTitle());
            put(RegisterDevice.WAP.getType(), RegisterDevice.WAP.getTitle());
            put(RegisterDevice.APP.getType(), RegisterDevice.APP.getTitle());
        }
    });

    private static Map<Integer, RegisterDevice> registerDeviceMap = ImmutableMap
            .copyOf(new HashMap<Integer, RegisterDevice>() {{
                put(RegisterDevice.PC.getType(), RegisterDevice.PC);
                put(RegisterDevice.WAP.getType(), RegisterDevice.WAP);
                put(RegisterDevice.APP.getType(), RegisterDevice.APP);
            }});

    public static Map<Integer, String> getMap() {
        return map;
    }

    public static String getValue(Integer key) {
        return map.get(key);
    }

    public static Map<Integer, RegisterDevice> getRegisterDeviceMap() {
        return registerDeviceMap;
    }

    public static RegisterDevice getRegisterDevice(Integer key) {
        return registerDeviceMap.get(key);
    }

}
