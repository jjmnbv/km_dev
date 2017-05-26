package com.kmzyc.mframework.mobile.hb;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kmzyc.mframework.logger.MessagelLogger;
import com.kmzyc.mframework.mobile.MobileInterface;

@Service("hbMobileImpl")
public class HBMobileImpl implements MobileInterface {
    private Logger logger = Logger.getLogger(HBMobileImpl.class);
    private HBSmsService hbSmsService = new HBSmsService();

    @Override
    public Map<String, Object> sendMsg(String[] mobiles, String msg) {
        boolean success = false;
        String companySignature = "【康美中药城】";
        MessagelLogger.info("HB：" + "发送内容为：签名自己加：【" + companySignature + msg + "】");
        Map<String, Object> map = new HashMap<String, Object>();
        String resultCode = "";
        /*** 白名单记录 ***/
        StringBuffer stringMobiles = new StringBuffer();
        for (int i = 0; i < mobiles.length; i++) {
            if (i == 0) {
                stringMobiles.append(mobiles[i]);
            } else {
                stringMobiles.append("," + mobiles[i]);
            }
        }
        try {
            String result =
                    hbSmsService.sendMessage(stringMobiles.toString(), companySignature + msg);
            String[] resultArray = new String[] {};
            MessagelLogger.info("昊博返回参数是【" + result + "】");
            if (result.indexOf("&") > -1) {
                resultArray = result.split("&");
                String[] codeArray = resultArray[1].split("=");
                resultCode = codeArray[1] + "";
                MessagelLogger.info("昊博返回 成功");
                success = true;
            } else if (result.indexOf("r=") > -1) {
                resultArray = result.split("=");
                resultCode = resultArray[1];
                success = false;
                MessagelLogger.info("昊博返回 失败");
                // 失败
            } else if (result.indexOf("id=") > -1) {
                resultArray = result.split("=");
                resultCode = resultArray[1];
                success = true;
                MessagelLogger.info("昊博返回 成功");
                // 成功
            }
        } catch (Exception e) {
            logger.error("调用第三方短信(hb)服务发生异常" + e.getMessage(), e);
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
