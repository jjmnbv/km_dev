package com.kmzyc.framework.log.aop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.kmzyc.framework.util.SplitUtil;

import java.util.Properties;

/**
 * 日志配置对象
 *
 * @author zheng xin
 * @version 1.0
 */
public class LogConfig {

    /**
     * 是否从CAS中获取用户名
     */
    private static boolean casUse;

    /**
     * 用户存session中key值
     */
    private static String userSessionKey;

    /**
     * 获取用户名的方法名
     */
    private static String userNameByMethod;

    private static Map<String, String[]> aopConfig = new HashMap<String, String[]>();

    /**
     * 静态初始化配置文件
     */
    static {
        Properties pro = new Properties();
        try {
            pro.load(LogConfig.class.getResourceAsStream("/aopLog.properties"));
            String key = null;
            String val = null;
            casUse = "1".equals(getValByPeoperty(pro, "casUse", "1")) ? true : false;
            userSessionKey = getValByPeoperty(pro, "userSessionKey", "user");
            userNameByMethod = getValByPeoperty(pro, "userNameByMethod", "getLoginAccount");
            for (Entry<Object, Object> entry : pro.entrySet()) {
                key = entry.getKey().toString();
                val = entry.getValue().toString();
                String[] strArr = SplitUtil.split(val, "$", 1, 0);
                if (strArr.length != 3)
                    continue;
                aopConfig.put(key, strArr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUserSessionKey() {
        return userSessionKey;
    }

    public static void setUserSessionKey(String userSessionKey) {
        LogConfig.userSessionKey = userSessionKey;
    }

    public static String getUserNameByMethod() {
        return userNameByMethod;
    }

    public static void setUserNameByMethod(String userNameByMethod) {
        LogConfig.userNameByMethod = userNameByMethod;
    }

    public static String[] getConfigByKey(String key) {
        return aopConfig.get(key);
    }

    public static boolean isCasUse() {
        return casUse;
    }

    public static void setCasUse(boolean casUse) {
        LogConfig.casUse = casUse;
    }

    private static String getValByPeoperty(Properties pro, String key, String def) {
        return (String) (pro.containsKey(key) ? pro.get(key) : def);
    }
}
