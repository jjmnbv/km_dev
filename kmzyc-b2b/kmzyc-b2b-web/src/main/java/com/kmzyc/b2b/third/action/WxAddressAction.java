package com.kmzyc.b2b.third.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.third.util.BaseUtil;
import com.kmzyc.b2b.third.util.WxHttpUtil;
import com.kmzyc.b2b.util.wxpay.CommonUtil;
import com.kmzyc.b2b.util.wxpay.Sha1Util;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

import net.sf.json.JSONObject;

/**
 * 微信相关地址请求类 20150624 暂时仅支持做测试
 * 
 * @author maliqun
 * 
 */
@Controller("wxAddressAction")
@Scope("prototype")
public class WxAddressAction {
    // 日志记录
    protected Logger log = LoggerFactory.getLogger(WxAddressAction.class);
    private Logger logger;

    /**
     * 依据网页授权得来的code换取accessToken,并把签名字符串返回给页面供调用地址签名使用
     * 
     * @return
     */
    public String getAccessTokenByCode() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        // 用户授权获得的code
        String code = request.getParameter("code");

        // 获取state参数,此参数值为自定义的,可用来标识微信wap登录来源
        String state = request.getParameter("state");

        // this.code=code;
        // this.state=state;

        // 获取需要参与签名的调用获取地址的页面的url
        String signUrl = request.getParameter("signUrl");
        log.info("获取参与签名的signUrl=" + signUrl);

        log.info("WxAddressAction   getAccessTokenByCode() code=" + code + ",state=" + state);
        // code="adfasfasdf";
        // state="test";
        // 用户取消授权未获取到code
        if (code == null || code.isEmpty()) {
            log.error("code is null");
            this.writeMsg("error,code is null");
            return null;
        }

        log.info("获取微信返回回来的code=" + code);


        /******* 用于config签名begin ********/

        String accessToken = BaseUtil.getToken();
        if (accessToken == null) {
            this.writeMsg("获取基础调用accessToken发生错误!");
            return null;
        }

        // 用accessToken获取jsapi_ticket 暂且一试
        String url =
                "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken
                        + "&type=jsapi";
        JSONObject jsonObj = WxHttpUtil.httpRequest(url, "GET", null);

        if (jsonObj.get("ticket") == null || jsonObj.get("ticket").toString().equals("")
                || jsonObj.get("ticket").toString().isEmpty()) {
            this.writeMsg("使用token去获取jsapi_ticket发生错误,详情如下=" + jsonObj.get("errcode"));
            return null;
        }

        String jsapiTicket = jsonObj.getString("ticket");
        String nonceStr = CommonUtil.CreateNoncestr();
        String timeStamp = Long.toString(new Date().getTime());

        // 参与签名的url需要拼接上code和state参数
        if (signUrl.indexOf("?") > 0) {
            signUrl = signUrl + "&code=" + code + "&state=" + state;
        } else {
            signUrl = signUrl + "?code=" + code + "&state=" + state;
        }

        SortedMap<String, String> signParamsForConfig = new TreeMap<String, String>();
        // signParamsForConfig.put("appId", ConfigurationUtil.getString("wx_appid"));
        // config验证时appid不参与签名认证
        signParamsForConfig.put("url", signUrl);
        signParamsForConfig.put("nonceStr", nonceStr);
        signParamsForConfig.put("timeStamp", timeStamp);
        signParamsForConfig.put("jsapi_ticket", jsapiTicket);

        request.setAttribute("appId", ConfigurationUtil.getString("wx_appid"));
        request.setAttribute("nonceStr", nonceStr);
        request.setAttribute("timeStamp", timeStamp);
        request.setAttribute("signature", getSign(signParamsForConfig));

        /******* 用于config签名end ********/



        // 获取授权用的accessToken
        JSONObject jsonObject = BaseUtil.getAccessTokenByCode(code);

        if (jsonObject.get("errcode") != null) {
            log.error("用code获取accessToken错误详情如下,errcode=" + jsonObject.get("errcode"));
            this.writeMsg("用code获取accessToken错误详情如下,errcode=" + jsonObject.get("errcode"));
            return null;
        }

        String token = jsonObject.getString("access_token");

        // 使用排序的map把需要签名的参数拼接成url键值对的形式
        SortedMap<String, String> signParams = new TreeMap<String, String>();
        signParams.put("appId", ConfigurationUtil.getString("wx_appid"));


        signParams.put("url", signUrl);
        signParams.put("timeStamp", timeStamp);
        signParams.put("nonceStr", nonceStr);
        signParams.put("accessToken", token);


        request.setAttribute("addrSign", getSign(signParams));
        request.setAttribute("scope", "jsapi_address");
        request.setAttribute("signType", "sha1");

        return "success";
    }



    /**
     * 去到获取地址的页面 先准备好需要的签名参数值
     * 
     * @return
     */
    public String toEditAdress() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();


        response.setContentType("text/html;charset=utf-8");
        // 用户授权获得的code
        String code = request.getParameter("code");

        // 获取state参数,此参数值为自定义的,可用来标识微信wap登录来源
        String state = request.getParameter("state");


        // 如果是从获取地址请求过来
        if (state != null && !state.isEmpty() && "y".equals(state.toLowerCase())) {

            // 获取需要参与签名的调用获取地址的页面的url
            String signUrl = request.getParameter("signUrl");
            log.info("获取参与签名的signUrl=" + signUrl);

            log.info("WxAddressAction   getAccessTokenByCode() code=" + code + ",state=" + state);
            // 用户取消授权未获取到code
            if (code == null || code.isEmpty()) {
                log.error("code is null");
                this.writeMsg("error,code is null");
                return null;
            }

            log.info("获取微信返回回来的code=" + code);
            /******* 用于config签名begin ********/


            String accessToken = BaseUtil.getToken();
            if (accessToken == null) {
                this.writeMsg("获取基础调用accessToken发生错误!");
                return null;
            }

            // 用accessToken获取jsapi_ticket 暂且一试
            String url =
                    "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
                            + accessToken + "&type=jsapi";
            JSONObject jsonObj = WxHttpUtil.httpRequest(url, "GET", null);
            // jsonObj.put("ticket", "adfasdfasdf");

            if (jsonObj.get("ticket") == null || jsonObj.get("ticket").toString().equals("")
                    || jsonObj.get("ticket").toString().isEmpty()) {
                this.writeMsg("使用token去获取jsapi_ticket发生错误,详情如下=" + jsonObj.get("errcode"));
                return null;
            }

            String jsapiTicket = jsonObj.getString("ticket");
            String nonceStr = CommonUtil.CreateNoncestr();
            String timeStamp = Long.toString(new Date().getTime());

            // 参与签名的url需要拼接上code和state参数
            if (signUrl.indexOf("?") > 0) {
                signUrl = signUrl + "&code=" + code + "&state=" + state;
            } else {
                signUrl = signUrl + "?code=" + code + "&state=" + state;
            }

            SortedMap<String, String> signParamsForConfig = new TreeMap<String, String>();
            signParamsForConfig.put("url", signUrl);
            signParamsForConfig.put("nonceStr", nonceStr);
            signParamsForConfig.put("timeStamp", timeStamp);
            signParamsForConfig.put("jsapi_ticket", jsapiTicket);

            request.setAttribute("appId", ConfigurationUtil.getString("wx_appid"));
            request.setAttribute("nonceStr", nonceStr);
            request.setAttribute("timeStamp", timeStamp);
            request.setAttribute("signature", getSign(signParamsForConfig));

            /******* 用于config签名end ********/

            // 获取授权用的accessToken
            JSONObject jsonObject = BaseUtil.getAccessTokenByCode(code);

            if (jsonObject.get("errcode") != null) {
                log.error("用code获取accessToken错误详情如下,errcode=" + jsonObject.get("errcode"));
                this.writeMsg("用code获取accessToken错误详情如下,errcode=" + jsonObject.get("errcode"));
                return null;
            }

            String token = jsonObject.getString("access_token");

            // 使用排序的map把需要签名的参数拼接成url键值对的形式
            SortedMap<String, String> signParams = new TreeMap<String, String>();
            signParams.put("appId", ConfigurationUtil.getString("wx_appid"));


            signParams.put("url", signUrl);
            signParams.put("timeStamp", timeStamp);
            signParams.put("nonceStr", nonceStr);
            signParams.put("accessToken", token);


            request.setAttribute("addrSign", getSign(signParams));
            request.setAttribute("scope", "jsapi_address");
            request.setAttribute("signType", "sha1");
            request.setAttribute("isFirstReq", state); // 标识是第一次请求

        }

        return "editAddrJsp";
    }


    /**
     * 返回地址需要的签名字符串 记得参与签名的参数名都应变成小写
     */
    public String getSign(Map<String, String> reqMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry:reqMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!StringUtil.isEmpty(value)) {
                sb.append(key.toLowerCase()).append('=').append(value).append('&');
            }
        }

        String signAfterSha1 = Sha1Util.sha1(sb.substring(0, sb.length() - 1));
        return signAfterSha1;
    }


    /**
     * 统一调用方法返回给客户端
     * 
     * @param obj 待返回给前端的信息
     */
    public void writeMsg(Object obj) {
        PrintWriter out = null;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        try {
            out = response.getWriter();
            out.print(JSONObject.fromObject(obj));
        } catch (IOException e) {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("error", e.getMessage());
            out.print(JSONObject.fromObject(returnMap));
            log.error("WxAddressAction==> writeMsg 写入write出现异常,详情如下=" + e.getMessage(), e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

    }


}
