package com.kmzyc.b2b.app;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.FavoriteResponse;
import com.kmzyc.framework.util.Base64Util;
import com.kmzyc.promotion.util.UserChannelContextHolder;
import com.kmzyc.zkconfig.ConfigurationUtil;

public class AppBaseAction extends BaseAction {

    private static final long serialVersionUID = 1427265905943098734L;
    // private static Logger logger = Logger.getLogger(AppBaseAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppBaseAction.class);
    protected String APP_LOGIN_FLAG = ConfigurationUtil.getString("b2b_km_app_user_id");// 登录KEy
    protected String APP_LOGIN_USER_TYPE = ConfigurationUtil.getString("b2b_km_app_user_type");// 登录类型
    protected String KM_APP_ID_KEY = ConfigurationUtil.getString("b2b_km_app_id_key");// 设备ID
    protected String B2B_KM_APP_INFO_KEY = ConfigurationUtil.getString("b2b_km_app_info_key");// 设备信息

    /** 转字符串输出，工具为fastjson，json串base64编码后输出 */
    protected void printJsonString(Object object) {
        try {
            String json = JSONObject.toJSONString(object,
                    SerializerFeature.DisableCircularReferenceDetect);
            json = Base64Util.encode(json);
            outPrintString(json);
        } catch (Exception e) {
            logger.error("", e);
        }

    }

    /** 转base64编码输出 */
    protected void printString(String str) {
        try {
            str = str.replaceAll("\\\\\\\\", "/");
            str = str.replaceAll("\\\\/", "/");
            // System.out.println(str);
            // logger.info(str);
            str = Base64Util.encode(str);
            outPrintString(str);
        } catch (Exception e) {
            logger.error("", e);
        }

    }

    private void outPrintString(String ciphertext) throws Exception {
        PrintWriter out = null;
        try {
            UserChannelContextHolder.destroyUserChannel();
            HttpServletResponse response = this.getResponse();
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expires", "0");
            response.setHeader("Pragma", "No-cache");
            // org.apache.commons.codec.binary.Base64.
            response.setContentLength(ciphertext.getBytes("utf-8").length);
            out = response.getWriter();
            out.print(ciphertext);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    // 获取用户id
    protected String getUserid() {
        Object obj = getRequest().getAttribute(APP_LOGIN_FLAG);
        if (obj == null) {
            return null;
        }
        return (String) obj;
    }

    /**
     * 获取jsonString值
     * 
     * @param params
     * @param key
     * @return
     */
    protected String getJsonStr(JSONObject params, String key) {
        return params.containsKey(key) ? params.getString(key) : null;
    }

    /**
     * 按照降价金额排序
     */
    protected List<FavoriteResponse> getPriceSort(List<FavoriteResponse> listFavoriteResponse) {
        Collections.sort(listFavoriteResponse, new Comparator<FavoriteResponse>() {
            @Override
            public int compare(FavoriteResponse arg0, FavoriteResponse arg1) {
                return arg1.getMarkdownPrice().compareTo(arg0.getMarkdownPrice());
            }
        });
        return listFavoriteResponse;
    }

    /**
     * 分页
     */
    protected List<FavoriteResponse> getPaging(List<FavoriteResponse> listFavoriteResponse,
            int pageNo, int pageSize) {
        List<FavoriteResponse> paging = new ArrayList();
        int num = 0;
        int startNum = pageSize * pageNo - 10;
        if (listFavoriteResponse.size() <= 10 && pageNo < 2) {// 数据条数小于10条
            num = listFavoriteResponse.size();
        } else if (listFavoriteResponse.size() > pageSize * pageNo) {// 数据条数大于请求页数
            num = 10;
        } else if ((listFavoriteResponse.size() + 10) > pageSize * pageNo) {// 数据条数在请求页数范围之内
            num = (listFavoriteResponse.size() + 10) - pageSize * pageNo;
        }
        for (int i = 0; i < num; i++) {
            paging.add(listFavoriteResponse.get(startNum + i));
        }
        return paging;
    }
}
