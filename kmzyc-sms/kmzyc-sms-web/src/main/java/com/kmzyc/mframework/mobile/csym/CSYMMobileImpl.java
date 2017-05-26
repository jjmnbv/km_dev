package com.kmzyc.mframework.mobile.csym;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kmzyc.mframework.mobile.MobileInterface;

@Service("csymMobileImpl")
public class CSYMMobileImpl implements MobileInterface {

    private CSYMSmsService csymSmsService = new CSYMSmsService();
    private Logger logger = Logger.getLogger(CSYMMobileImpl.class);

    @Override
    public Map<String, Object> sendMsg(String[] mobiles, String msg) {
        boolean success = false;
        Map<String, Object> map = new HashMap<String, Object>();

        if (mobiles == null || 0 == mobiles.length) {
            map.put("success", success);
            return map;
        }


        StringBuffer stringMobiles = new StringBuffer();
        for (int i = 0; i < mobiles.length; i++) {
            if (i == 0) {
                stringMobiles.append(mobiles[i]);
            } else {
                stringMobiles.append("," + mobiles[i]);
            }
        }

        try {

            success = csymSmsService.sendMessage(msg, stringMobiles.toString());

        } catch (Exception e) {
            logger.error("调用第三方短信(csym)服务发生异常" + e.getMessage(), e);
            success = false;
        }
        map.put("success", success);
        return map;
    }
}
