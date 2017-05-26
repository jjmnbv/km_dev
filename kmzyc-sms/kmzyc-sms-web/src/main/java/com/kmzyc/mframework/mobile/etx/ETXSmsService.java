package com.kmzyc.mframework.mobile.etx;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.kmzyc.mframework.logger.MessagelLogger;
import com.kmzyc.util.HttpClientUtils;
import com.kmzyc.zkconfig.ConfigurationUtil;

public class ETXSmsService {
    private final static String url = ConfigurationUtil.getString("ext_sms_url");
    private final static String loginName = ConfigurationUtil.getString("ext_loginName");
    private final static String password = ConfigurationUtil.getString("ext_password");
    private final static String spCode = ConfigurationUtil.getString("ext_spCode");

    public String getSerialNumber() {
        Long timeNumber = System.currentTimeMillis();
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 6; i++) {
            result += random.nextInt(10);
        }
        String serialNumber = timeNumber.toString() + result;
        return serialNumber;

    }

    public String sendMessage(String msg, String mobiles) throws Exception {
        MessagelLogger.info("开始发送短信，手机为【" + mobiles + "】内容为【" + msg + "】");
        String serialNumber = getSerialNumber();
        Map<String, Object> postParam = new HashMap<String, Object>();
        postParam.put("SpCode", spCode);
        postParam.put("LoginName", loginName);
        postParam.put("Password", password);
        postParam.put("MessageContent", msg);
        postParam.put("UserNumber", mobiles);
        postParam.put("SerialNumber", serialNumber);
        postParam.put("ScheduleTime", "");
        postParam.put("f", "1");
        String returnStr;
//        returnStr = HttpPost.getInstance(url).post(postParam);
        returnStr = HttpClientUtils.post(url,postParam,null, Charset.forName("GBK"),Charset.forName("GBK"));
        // HttpPost.getInstance(url).post(postParam);
        MessagelLogger.info("返回参数是【" + returnStr + "】");
        return returnStr;
    }

}
