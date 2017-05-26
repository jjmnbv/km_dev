package com.pltfm.app.util;

/**
 * String工具类
 *
 * @author cjm
 * @since 2014-4-2
 */
public class StringUtil {
    /**
     * 将每个渠道包装一对单引号
     */
    public static String packChannel(String channel) {
        String[] channels = channel.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < channels.length; i++) {
            if (channels.length == i + 1) {
                sb.append("'" + channels[i] + "'");
            } else {
                sb.append("'" + channels[i] + "',");
            }
        }
        return sb.toString();
    }
}
