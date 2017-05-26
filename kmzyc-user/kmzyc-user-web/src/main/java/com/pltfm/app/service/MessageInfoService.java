package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

/**
 *
 * 
 * @author cjm
 * @since 2013-8-8
 */
public interface MessageInfoService {

    /**
     *
     * 推广短信
     * @param uidList
     * @param mobilePhoneList
     * @param msg
     * @param sendType
     * 登录账号、手机号码、短信内容、短信接口类型 (1---验证2-----通知3----广告)、发送状态
     * @return
     */
    public Map<String,Object> sendMsgBySpread(List<Long> uidList, List<String> mobilePhoneList, String msg, Integer sendType, Integer systemType);

}
