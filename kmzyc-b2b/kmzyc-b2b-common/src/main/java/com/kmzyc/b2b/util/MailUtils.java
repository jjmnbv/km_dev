package com.kmzyc.b2b.util;

import java.util.HashMap;
import java.util.Map;

import com.kmzyc.util.StringUtil;


public class MailUtils {

    @SuppressWarnings("serial")
    private static Map<String, String> EMAIL_MAP = new HashMap<String, String>() {
        {
            put("126.com", "http://mail.126.com/");
            put("vip.126.com", "http://vip.126.com/");
            put("163.com", "http://mail.163.com/");
            put("vip.163.com", "http://vip.163.com/");
            put("188.com", "http://www.188.com/");
            put("qq.com", "http://mail.qq.com/");
            put("vip.qq.com", "http://mail.qq.com/");
            put("gmail.com", "http://mail.google.com/");
            put("outlook.com", "http://login.live.com/");
            put("live.cn", "http://login.live.com/");
            put("hotmail.com", "http://login.live.com/");
            put("live.com", "http://login.live.com/");
            put("139.com", "http://mail.10086.cn/");
            put("foxmail.com", "http://www.foxmail.com/");
            put("sina.com", "http://mail.sina.com.cn/");
            put("sohu.com", "http://mail.sohu.com/");
            put("yeah.net", "http://www.yeah.net/");
            put("132335mail.com", "http://mail.132335mail.com/");
        }
    };

    /**
     * 获取邮件链接地址
     * 
     * @return
     */
    public static String getMailLink(String mail) {
        if (null == mail || !StringUtil.validEmail(mail)) {
            return null;
        }
        int idx = mail.lastIndexOf("@") + 1;
        if (idx < 1 || idx > mail.length()) {
            return null;
        }

        String key = mail.substring(mail.lastIndexOf("@") + 1, mail.length());
        return EMAIL_MAP.containsKey(key) ? EMAIL_MAP.get(key) : "http://mail." + key + "/";
    }

    /**
     * 生成带*格式邮箱
     * 
     * @param email
     * @return
     */
    public static String genSecretEmail(String email) {
        String sMail = null;
        int index = 0;
        if (null != email && (index = email.indexOf("@")) > 0) {
            String name = email.substring(0, index);
            if ( name.length() > 3) {
                sMail = name.substring(0, 3) + "******" + email.substring(index, email.length());
            } else{
                sMail = name.charAt(0) + "******" + email.substring(index, email.length());
            }
        }
        return sMail;
    }

    /**
     * 生成带*格式手机
     * 
     * @param mobile
     * @return
     */
    public static String genSecretMobile(String mobile) {
        if (null != mobile && mobile.length() >= 8) {
            int len = mobile.length();
            return mobile.substring(0, len - 7) + "****" + mobile.substring(len - 4, len);
        }
        return null;
    }
}
