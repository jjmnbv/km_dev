package com.kmzyc.mframework.mobile.mdkj;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kmzyc.mframework.logger.MessagelLogger;
import com.kmzyc.mframework.mobile.MobileInterface;
import com.kmzyc.zkconfig.ConfigurationUtil;

@Service("mdkjMobileImpl")
public class MDKJMobileImpl implements MobileInterface {

    private final static String userName = ConfigurationUtil.getString("mdkj_userName");
    private final static String password = ConfigurationUtil.getString("mdkj_password");
    private final static String smsurl = ConfigurationUtil.getString("mdkj_sms_url");
    private final static String ext = ConfigurationUtil.getString("mdkj_ext");
    private final static String signature = ConfigurationUtil.getString("sms_content_signature");

    @Override
    public Map<String, Object> sendMsg(String[] mobiles, String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean success = false;
        try {
            /*** 联通后置移动前置 ***/
            StringBuffer stringMobiles = new StringBuffer();
            msg = signature + msg;

            MessagelLogger.info("漫道科技：" + "发送内容为：签名自己加后置：【" + msg + "】");
            for (int i = 0; i < mobiles.length; i++) {
                if (i == 0) {
                    stringMobiles.append(mobiles[i]);
                } else {
                    stringMobiles.append("," + mobiles[i]);
                }
            }
            Client client = new Client(userName, password, smsurl);
            // 最后是拼s成xml了
            if (msg.indexOf("&") >= 0) {
                msg = msg.replace("&", "&amp;");
            }

            if (msg.indexOf("<") >= 0) {

                msg = msg.replace("<", "&lt;");

            }
            if (msg.indexOf(">") >= 0) {
                msg = msg.replace(">", "&gt;");
            }
            // 短信发送
            String result_mt = client.mt(stringMobiles.toString(), msg, ext, "", "");
            if (result_mt.startsWith("-") || result_mt.equals(""))// 以负号判断是否发送成功
            {
                MessagelLogger.info("发送失败！返回值为：" + result_mt + "。请查看webservice返回值对照表");
                map.put("success", false);
                return map;
            }
            // 输出返回标识，为小于19位的正数，String类型的，记录您发送的批次
            else {
                MessagelLogger.info("发送成功，返回值为：" + result_mt);
                map.put("resultCode", result_mt);
                map.put("success", true);
                return map;
            }
        } catch (Exception e) {
            MessagelLogger.info("发送异常:" + e.getMessage());
            map.put("success", false);
            return map;
        }
    }

}
