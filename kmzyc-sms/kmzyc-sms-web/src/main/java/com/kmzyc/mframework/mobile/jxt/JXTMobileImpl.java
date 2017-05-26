package com.kmzyc.mframework.mobile.jxt;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kmzyc.mframework.logger.MessagelLogger;
import com.kmzyc.mframework.mobile.MobileInterface;
import com.kmzyc.zkconfig.ConfigurationUtil;

@Service("jxtMobileImpl")
public class JXTMobileImpl implements MobileInterface {

    private JXTSmsService jxtSmsService = new JXTSmsService();

    private Logger logger = Logger.getLogger(JXTMobileImpl.class);

    /**
     * 发送短信
     * 
     * @param mobiles 短信接收手机号码
     * @param msg 短信内容 ，不超过70个字符
     * @return
     */
    @Override
    public Map<String, Object> sendMsg(String[] mobiles, String msg) {
        MessagelLogger.info("发送的手机号码为：【" + StringUtils.join(mobiles) + "】,内容为:【" + msg + "】");
        Map<String, Object> map = new HashMap<String, Object>();
        if (mobiles == null || mobiles.length <= 0) {
            logger.error("发送短信的手机号码格式不正确!");
            map.put("success", false);
            return map;
        }
        if (mobiles == null || 0 == mobiles.length) {
            map.put("success", false);
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
        String resultCode = jxtSmsService.sendSms(stringMobiles.toString(), msg, "");
        String code = ConfigurationUtil.getString("messageResultCode");
        String[] codeArray = code.split(",");
        boolean success = true;
        for (String str : codeArray) {
            if (resultCode.equals(str)) {// 如果错误码出现,返回false
                success = false;
                logger.error("短信发送错误,返回的code为" + str);
                break;
            }
        }
        map.put("success", success);
        return map;
    }

}
