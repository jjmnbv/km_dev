package com.kmzyc.product.remote.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.pltfm.activity.service.ActivityService;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.ActivityRemoteService;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 功能：活动远程接口实现
 *
 * @author Zhoujiwei
 * @since 2016/4/18 11:50
 */
@Component("activityRemoteService")
public class ActivityRemoteServiceImpl implements ActivityRemoteService {

    @Resource
    private ActivityService activityService;

    @Override
    public String getActivityList(Map<String, String> param) throws ServiceException {
        return JSONObject.toJSONString(activityService.getActivityList(param));
    }

    @Override
    public String getActivityById(Long activityId) throws ServiceException {
        return JSONObject.toJSONString(activityService.getActivityById(activityId));
    }
}
