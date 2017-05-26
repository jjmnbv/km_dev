package com.kmzyc.supplier.app;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

public class AppJsonUtils {

    /**
     * 获取json对象
     *
     * @param request
     * @return
     */
    public static JSONObject getJsonObject(HttpServletRequest request) {
        JSONObject json = null;
        try {
            json = JSONObject.parseObject(request.getParameter("json"));
        } catch (Exception e) {

        }
        return json;
    }
}
