package com.kmzyc.b2b.event;

import com.google.common.eventbus.EventBus;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by min on 2016/11/24.
 */
public class AbstractEventHandler {
    @Resource
    private EventBus eventBus;

    @PostConstruct
    public void init(){
        eventBus.register(this);
    }
}
