package com.kmzyc.mframework.mobile.etx;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kmzyc.mframework.mobile.MobileInterface;

@Service("etxMobileImpl")
public class ETXMobileImpl implements MobileInterface {
    private ETXSmsService etxSmsService = new ETXSmsService();
    private Logger logger = Logger.getLogger(ETXMobileImpl.class);


    @Override
    public Map<String, Object> sendMsg(String[] mobiles, String msg) {
        boolean success = false;
        Map<String, Object> map = new HashMap<String, Object>();
        String resultCode = "";
        if (mobiles == null || 0 == mobiles.length) {
            map.put("resultCode", resultCode);
            map.put("success", success);
            return map;
        }
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
            String result = etxSmsService.sendMessage(msg, stringMobiles.toString());
            String[] resultArray = result.split("&");
            String[] codeArray = resultArray[0].split("=");
            resultCode = codeArray[1] + "";
            if ("0".equals(codeArray[1])) {
                success = true;
            }
        } catch (Exception e) {
            logger.error("调用第三方短信服务发生异常" + e.getMessage(), e);
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
