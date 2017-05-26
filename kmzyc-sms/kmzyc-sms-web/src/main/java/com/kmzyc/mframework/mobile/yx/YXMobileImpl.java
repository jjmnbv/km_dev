package com.kmzyc.mframework.mobile.yx;

import com.kmzyc.mframework.logger.MessagelLogger;
import com.kmzyc.mframework.mobile.MobileInterface;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

@Service("yxMobileImpl")
public class YXMobileImpl implements MobileInterface {
    private Logger logger = Logger.getLogger(YXMobileImpl.class);
    @Resource(name = "yxSmsService")
    private YXSmsService yxSmsService;

    @Override
    public Map<String, Object> sendMsg(String[] mobiles, String msg) {
        boolean success = false;
        MessagelLogger.info("悦信yx：" + "发送内容为：签名不用自己加：【" + msg.toString() + "】");
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
            String result = yxSmsService.sendMessage(stringMobiles.toString(), msg);
            JSONObject jsonObject = new JSONObject(result);
            String code = (String) jsonObject.get("code");
            if ("9001".equals(code)) {
                success = true;
                resultCode = (String) jsonObject.get("batch");
            } else {
                success = false;
            }
            MessagelLogger.info("悦信返回参数是【" + result + "】");
        } catch (Exception e) {
            logger.error("调用第三方短信（yx）服务发生异常" + e.getMessage(), e);
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
