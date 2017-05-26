package com.kmzyc.b2b.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kmzyc.b2b.shopcart.util.ShopcartConstants;

/**
 * cookie操作 工具类
 *
 * @author mkw
 */
public class CookieUtil {

    /**
     * 获取Cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String key) {
        Cookie[] cs = request.getCookies();
        String id = "";
        if (cs != null) {
            for (Cookie c : cs) {
                if (c.getName().equals(key)) {
                    id = c.getValue();
                    break;
                }
            }
        }
        return id;
    }

    /**
     *
     * @param response
     * @param name
     * @param value
     */
    public static void createCookie(HttpServletResponse response, String name, String value,
                                    int age) {
        Cookie c = new Cookie(name, value);
        c.setPath("/");
        c.setMaxAge(age);
        response.addCookie(c);
    }

    /**
     *
     * @param response
     * @param name
     * @param value
     */
    public static void createCookie(HttpServletResponse response, String name, String value,String domain,
                                    int age) {
        Cookie c = new Cookie(name, value);
        c.setPath("/");
        c.setDomain(domain);
        c.setMaxAge(age);
        response.addCookie(c);
    }
}
