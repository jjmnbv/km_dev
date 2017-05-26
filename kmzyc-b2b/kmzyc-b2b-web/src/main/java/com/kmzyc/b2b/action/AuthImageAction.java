package com.kmzyc.b2b.action;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.util.VerifyCodeUtils;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

@Controller("authImageAction")
@Scope("prototype")
@SuppressWarnings({"serial"})
public class AuthImageAction extends BaseAction {
    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;

    private static Logger logger = LoggerFactory.getLogger(AuthImageAction.class);

    /**
     * 生成图形的验证码
     */
    @Override
    public String execute() throws Exception {
        try {
            HttpServletResponse response = getResponse();
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");

            HttpServletRequest request = getRequest();
            String key = request.getParameter(ConfigurationUtil.getString("b2b_km_app_id_key"));
            // boolean isNum = "true".equals(request.getParameter("isNum"));
            String type = request.getParameter("type");
            if (null == key || key.length() < 1) {
                key = request.getSession().getId();
            }
            String result = VerifyCodeUtils.outputImage(200, 80, response.getOutputStream(), true);// 生成图片
            key = ConfigurationUtil.getString("b2b_km_valid_image_prex").concat(key)
                    .concat(null == type ? "" : type);
            memCachedClient.set(key, result, new Date(300000));// 有效期5分钟
            memCachedClient.set(key.concat("_count"), 0, new Date(300000));// 校验次数
        } catch (Exception e) {
            logger.error("生成图形的验证码ERROR", e);
        }
        return null;
    }

    /**
     * 验证码
     */
    public void verificate() {
        try {
            HttpServletRequest request = getRequest();
            HttpServletResponse response = getResponse();
            String code = request.getParameter("code");
            String key = request.getParameter(ConfigurationUtil.getString("b2b_km_app_id_key"));
            if (null == key || key.length() < 1) {
                key = request.getSession().getId();
            }
            int result = 0, ille = 0;// 比较结果/执行删除标识
            if (null != code) {
                Object countObj = memCachedClient.get(key.concat("_count"));
                Integer count = 2;
                if (null != countObj) {
                    count = Integer.parseInt(countObj.toString());
                    ille = 1;
                }
                if (count < 2 && code.equalsIgnoreCase((String) memCachedClient.get(key))) {
                    memCachedClient.set(key.concat("_count"), count + 1);
                    result = 1;
                }
            }
            if (0 == result && 1 == ille) {
                memCachedClient.delete(key);
                memCachedClient.delete(key.concat("_count"));
            }
            response.getWriter().print(result);
        } catch (Exception e) {
            logger.error("验证码ERROR", e);
        }
    }
}
