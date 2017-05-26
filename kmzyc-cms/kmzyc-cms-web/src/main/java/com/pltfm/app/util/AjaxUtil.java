package com.pltfm.app.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * AJAX工具类
 *
 * @author
 */
public class AjaxUtil {
	private static Logger logger = LoggerFactory.getLogger(AjaxUtil.class);

    /**
     * @param json
     * @param needCheckLogin
     * @throws IOException
     */
    public static void writeJSONToResponse(Object object) {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json-rpc;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expires", "0");
        response.setHeader("Pragma", "No-cache");
        try {
            JSONObject jsONObject = JSONObject.fromObject(object);
            String json = jsONObject.toString();

            response.setContentLength(json.getBytes("utf-8").length);
            PrintWriter out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            logger.error("erro", e);
        }
    }


    public static void writeJSONToResponseByObject(Object object, boolean isJsonp, String jsoncallback) {
        JSONObject jsONObject = JSONObject.fromObject(object);
        String json = jsONObject.toString();
        writeJSONToResponse(json, isJsonp, jsoncallback);
    }

    /**
     * Ajax跨域访问
     */
    public static void writeJSONToResponse(String json, boolean isJsonp, String jsoncallback) {
        HttpServletResponse response = ServletActionContext.getResponse();
        if (isJsonp) {
            response.setContentType("text/javascript;charset=utf-8");
        } else {
            response.setContentType("application/json-rpc;charset=utf-8");
        }
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expires", "0");
        response.setHeader("Pragma", "No-cache");
        try {
            PrintWriter out = response.getWriter();
            logger.debug("json:" + json);
            if (isJsonp) {
                json = jsoncallback + "(" + json + ")";
            }
            response.setContentLength(json.getBytes("utf-8").length);
            out.print(json);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     *
     * @param list
     */
    public static void writeJSONListToResponse(List<?> list) {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json-rpc;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expires", "0");
        response.setHeader("Pragma", "No-cache");
        System.out.println(JSONArray.fromObject(list));
        try {
            JSONObject json = new JSONObject();
            JSONArray members = JSONArray.fromObject(list);

            json.put("size", list.size());
            json.put("rows", members);
            String result = json.toString();
            response.setContentLength(result.getBytes("utf-8").length);
            PrintWriter out = response.getWriter();
            out.print(result);
        } catch (Exception e) {
            logger.error("erro", e);
        }
    }

    public static void writeJSONStringToResponse(String str) {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json-rpc;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expires", "0");
        response.setHeader("Pragma", "No-cache");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.print(str);
        } catch (IOException e) {
            logger.error("erro", e);
        }

    }
}
