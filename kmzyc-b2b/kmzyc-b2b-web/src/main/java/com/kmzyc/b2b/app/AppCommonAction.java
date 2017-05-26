package com.kmzyc.b2b.app;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.model.AppVersion;
import com.kmzyc.b2b.service.member.CMSAppVersionService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.framework.util.Base64Util;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

/**
 * APP公用接口
 * 
 * @author xlg
 * 
 */
@Controller("appCommonAction")
@Scope("prototype")
@SuppressWarnings({"serial", "unchecked"})
public class AppCommonAction extends AppBaseAction {
    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;
    @Resource(name = "versionService")
    private CMSAppVersionService versionService;
    // private static Logger logger = Logger.getLogger(AppCommonAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppCommonAction.class);
    protected String KM_VALID_IMAGE_PREX = ConfigurationUtil.getString("b2b_km_valid_image_prex");// 图形验证码前缀
    private ReturnResult<HashMap<String, Object>> returnResult;

    /**
     * 验证图形验证码
     */
    public void authValid() throws Exception {
        String rs = "0";
        try {
            HttpServletRequest request = getRequest();
            String key = "";
            String deviceInfo = request.getHeader(B2B_KM_APP_INFO_KEY);
            if (null != deviceInfo) {
                JSONObject json = JSONObject.parseObject(Base64Util.decode(deviceInfo));
                key = json.containsKey(KM_APP_ID_KEY) ? json.getString(KM_APP_ID_KEY) : "";
            }
            JSONObject params = AppJsonUtils.getJsonObject(request);
            String type = getJsonStr(params, "type");
            String code = getJsonStr(params, "imgCode");
            key = KM_VALID_IMAGE_PREX.concat(key).concat(null == type ? "" : type);
            if (null != code && code.equalsIgnoreCase(memCachedClient.get(key).toString())) {
                rs = "1";
            }
            memCachedClient
                    .delete(KM_VALID_IMAGE_PREX.concat(key).concat(null == type ? "" : type));
        } catch (Exception e) {
            logger.error("", e);
        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, null,
                JSONObject.parse("{\"result\":\"" + rs + "\"}"));
        printJsonString(returnResult);
    }

    /**
     * 手机获取APP版本号和下载地址
     * 
     * @return
     * @throws SQLException
     */
    public void getAppVersion() throws ServiceException {
        AppVersion aversion = null;
        String osType = null;
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        osType = jsonParams.getString("osType");
        // osType为必传参数
        if (jsonParams.isEmpty() || StringUtils.isEmpty(osType)) {
            returnResult = new ReturnResult("0", "操作系统参数为必填，请选择ios或android！", null);
            this.printJsonString(returnResult);
            return;
        }
        try {
            List<AppVersion> list = versionService.getNewestVersion(osType);
            if (null != list && !list.isEmpty()) {
                aversion = list.get(0);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, null, aversion);
        printJsonString(returnResult);
    }

    // APP校验返回码：-1:token失效
    private static final String APP_CHECK_OUT_SESSION = "-1";

    /**
     * 非法URL
     * 
     * @return
     * @throws Exception
     */
    public void illegalUrl() throws Exception {
        try {

            if (APP_CHECK_OUT_SESSION.equals(getRequest().getParameter("flag"))) {
                logger.info("APP请求失败：密钥失效,新的密钥({})", getRequest().getParameter("token"));
                returnResult = new ReturnResult("-101", "密钥失效", getRequest().getParameter("token"));
            } else {
                returnResult = new ReturnResult("-100", "非法链接", null);
            }
            printJsonString(returnResult);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 获取系统时间
     */
    public void getSystemTime() {
        try {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED,
                    String.valueOf(System.currentTimeMillis()), null);
            printJsonString(returnResult);
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
