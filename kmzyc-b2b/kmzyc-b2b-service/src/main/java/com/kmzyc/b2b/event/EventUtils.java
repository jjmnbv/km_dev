package com.kmzyc.b2b.event;

import com.google.common.eventbus.EventBus;

import com.pltfm.app.vobject.LatestLogin;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by min on 2016/11/24.
 */
@Component
public class EventUtils {
    private static EventBus eventBus;
    @Resource
    public void setEventBus(EventBus eventBus) {
        EventUtils.eventBus = eventBus;
    }

    /**
     * 发布登录日志事件 {@link LatestLoginHandler}
     * @param nLoginId
     * @param loginAccount
     * @param loginIp
     */
    public static void postLatestLogin(Integer nLoginId,String loginAccount,String loginIp){
        LatestLogin latestLogin = new LatestLogin();
        latestLogin.setN_LoginId(nLoginId);
        latestLogin.setLoginAccount(loginAccount);
        latestLogin.setLoginIp(loginIp);
        eventBus.post(latestLogin);
    }



}
