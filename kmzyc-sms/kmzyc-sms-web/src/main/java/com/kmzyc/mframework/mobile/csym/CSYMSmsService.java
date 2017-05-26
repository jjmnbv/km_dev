package com.kmzyc.mframework.mobile.csym;

import java.util.HashMap;
import java.util.Map;

import com.kmzyc.mframework.logger.MessagelLogger;
import com.kmzyc.util.HttpClientUtils;
import com.kmzyc.zkconfig.ConfigurationUtil;


public class CSYMSmsService {

    private final static String loginName = ConfigurationUtil.getString("csym_loginName");
    private final static String password = ConfigurationUtil.getString("csym_password");
    private final static String postSMSURL = ConfigurationUtil.getString("csym_sms_url");
    private final static String signature = ConfigurationUtil.getString("sms_content_signature");

    public boolean sendMessage(String msg, String mobiles) throws Exception {
        msg = signature + msg;
        MessagelLogger.info("发送短信:手机为【" + mobiles + "】内容为【" + msg + "】");

        Map<String, Object> postParam = new HashMap<>();
        postParam.put("user", loginName);
        postParam.put("pwd", password);
        postParam.put("content", msg);
        postParam.put("phone", mobiles);

        String returnStr = HttpClientUtils.post(postSMSURL, postParam);
        // HttpPost.getInstance(postSMSURL).post(postParam);

        if (Double.valueOf(returnStr) > 0) {
            MessagelLogger.info("发送短信【成功】:手机为【" + mobiles + "】返回参数是【" + returnStr + "】");
            return true;
        } else {
            MessagelLogger.info("发送短信【失败】:手机为【" + mobiles + "】返回参数是【" + returnStr + "】");
            return false;
        }
    }
}
