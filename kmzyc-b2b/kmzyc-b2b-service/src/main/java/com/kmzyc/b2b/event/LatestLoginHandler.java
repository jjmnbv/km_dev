package com.kmzyc.b2b.event;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.kmzyc.user.remote.service.LatestLoginRemoteService;
import com.pltfm.app.vobject.LatestLogin;

/**
 * Created by min on 2016/11/24.
 * 登录日志事件处理<br/>
 * 事件处理机制请参考guava {@link com.google.common.eventbus.EventBus}
 */

@Component
public class LatestLoginHandler extends AbstractEventHandler {

    private static Logger logger= LoggerFactory.getLogger(LatestLoginHandler.class);
    @Resource
    private LatestLoginRemoteService latestLoginRemoteService;

    @Subscribe
    @AllowConcurrentEvents
    public void record(LatestLogin latestLogin){
        try {
            latestLoginRemoteService.addLatestLogin(latestLogin,3);
        } catch (Exception e) {
            logger.error("latestLoginRemoteService.addLatestLogin ERROR",e);
        }
    }
}
