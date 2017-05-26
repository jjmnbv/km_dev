package com.kmzyc.mframework.mobile.jcxg;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kmzyc.mframework.logger.MessagelLogger;
import com.kmzyc.mframework.mobile.MobileInterface;
import com.kmzyc.zkconfig.ConfigurationUtil;

@Service("jcxgMobileImpl")
public class JCXGMobileImpl implements MobileInterface {

    private final static String userName = ConfigurationUtil.getString("jcxg_loginName");
    private final static String password = ConfigurationUtil.getString("jcxg_password");
    private final static String url = ConfigurationUtil.getString("jcxg_sms_url");
    private final static String signature = ConfigurationUtil.getString("sms_content_signature");


    @Override
    public Map<String, Object> sendMsg(String[] mobiles, String msg) {
        boolean success = false;
        String resultCode = "";
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer stringMobiles = new StringBuffer();

        msg = signature + msg;
        /* String companySignature=""; */
        MessagelLogger.info("君诚信鸽：" + "发送内容为：签名自己加：【" + msg + "】");

        if (mobiles == null || 0 == mobiles.length) {
            map.put("resultCode", resultCode);
            map.put("success", success);
            return map;
        }
        /*** 白名单记录 ***/
        for (int i = 0; i < mobiles.length; i++) {
            if (i == 0) {
                stringMobiles.append(mobiles[i]);
            } else {
                stringMobiles.append("," + mobiles[i]);
            }
        }
        Client client;
        try {
            client = new Client(userName, password, url);
            String result = client.mt(URLEncoder.encode(msg, "UTF-8"), stringMobiles.toString(), "",
                    "", "", "");

            MessagelLogger.info("君诚信鸽：" + "返回内容为：【" + result + "】");
            String strCode = result.split("\n")[0];
            long code = 0;
            code = Long.valueOf(strCode);
            resultCode = code + "";
            String Info = null;
            if (code > 0) {// 成功提交
                Info = "发送成功";
                success = true;
            } else if (code == 0) {
                Info = "发送失败";
                success = false;
            } else if (code == -1) { // 用户名密码不正确
                Info = "用户名密码不正确";
                success = false;
            } else if (code == -2) { // 必填选项为空
                Info = "必填选项为空";
                success = false;
            } else if (code == -3) { // 短信内容0个字节
                Info = "短信内容0个字节";
                success = false;
            } else if (code == -4) { // 0个有效号码
                Info = "0个有效号码";
                success = false;
            } else if (code == -5) { // 余额不够
                Info = "余额不够";
                success = false;
            } else if (code == -10) { // 用户被禁用
                Info = "用户被禁用";
                success = false;
            } else if (code == -11) { // 短信内容过长
                Info = "短信内容过长";
                success = false;
            } else if (code == -12) { // 用户无扩展权限
                Info = "无扩展权限";
                success = false;
            } else if (code == -13) { // IP地址校验错
                Info = "IP校验错误";
                success = false;
            } else if (code == -14) { // 内容解析异常
                Info = "内容解析异常";
                success = false;
            } else {
                Info = "未知错误";
                success = false;
            }
            MessagelLogger.info("返回信息：" + Info + "--" + code + "--" + client.getPwd());
        } catch (UnsupportedEncodingException e) {
            MessagelLogger.info("发送短信异常：" + e.getMessage());
            success = false;
            map.put("resultCode", resultCode);
            map.put("success", success);
            return map;
        }
        map.put("resultCode", resultCode);
        map.put("success", success);
        return map;
    }

}
