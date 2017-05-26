package com.kmzyc.mframework.mobile;

import java.util.Map;


/**
 * 短信相关接口
 * 
 * @author Administrator
 * 
 */
public interface MobileInterface {
    /**
     * 发送短信
     * 
     * @param mobiles 短信接收手机号码
     * @param msg 短信内容 ，不超过70个字符
     * @return
     */
    Map<String, Object> sendMsg(String[] mobiles, String msg);


}
