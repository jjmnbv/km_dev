package com.kmzyc.framework.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.apache.struts2.ServletActionContext;

/**
 * AJAX工具类
 *
 * @author
 */
public class AjaxUtil {

    private static Logger logger = LoggerFactory.getLogger(AjaxUtil.class);

    /**
     * @param object
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
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error("erro", e);
        }
    }


    public static void writeJSONToResponseByObject(Object object, boolean isJsonp, String jsonCallBack) {
        JSONObject jsONObject = JSONObject.fromObject(object);
        String json = jsONObject.toString();
        writeJSONToResponse(json, isJsonp, jsonCallBack);
    }

    /**
     * Ajax跨域访问
     *
     * @param json
     * @param isJsonp
     */
    public static void writeJSONToResponse(String json, boolean isJsonp, String jsonCallBack) {
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
                json = jsonCallBack + "(" + json + ")";
            }
            response.setContentLength(json.getBytes("utf-8").length);
            out.print(json);
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * @param list
     */
    public static void writeJSONListToResponse(List<?> list) {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json-rpc;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expires", "0");
        response.setHeader("Pragma", "No-cache");
        try {
            JSONObject json = new JSONObject();
            JSONArray members = JSONArray.fromObject(list);

            json.put("size", list.size());
            json.put("rows", members);
            String result = json.toString();
            response.setContentLength(result.getBytes("utf-8").length);
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
            out.close();
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
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("erro", e);
        }

    }
}
