package com.kmzyc.b2b.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kmzyc.b2b.dao.ActivityDAO;
import com.kmzyc.b2b.service.ActivityService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.LotteryResultCode;
import com.pltfm.app.vobject.ActivityInfo;

/**
 * 功能：活动远程接口实现
 *
 * @author Zhoujiwei
 * @since 2016/4/18 11:50
 */
@Component("activityRemoteService")
public class ActivityServiceImpl implements ActivityService {

    private Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    // private static final String ACTIVITY_KEY = "activity_";

    @Resource
    private ActivityDAO activityDAO;

    @Override
    public ReturnResult<Map<String, Object>> getActivityList(String activityType,
            String activityLabel, String industry, String chargeType, String activityLevel,
            String pageNumber, String size) {

        ReturnResult<Map<String, Object>> returnResult = new ReturnResult<>();
        // 第几页,总页数,总数
        int pageSize = 10;
        int pageIndex = 1;

        Map<String, Object> condition = new HashMap<>();
        // 分类
        if (StringUtils.isNotBlank(activityLabel)) {
            try {
                activityLabel = URLDecoder.decode(activityLabel, "utf-8");
                condition.put("activityLabel", "%" + activityLabel + "%");
            } catch (UnsupportedEncodingException e) {
                logger.error("查询异常,分类="+activityLabel, e);
            }
        }
        // 行业
        if (StringUtils.isNotBlank(industry)) {
            try {
                industry = URLDecoder.decode(industry, "utf-8");
                condition.put("industry", "%" + industry + "%");
            } catch (UnsupportedEncodingException e) {
                logger.error("查询异常,行业="+industry, e);
            }
        }
        // activityType活动类型（1所有活动/2征集中的活动）
        if ("2".equals(activityType)) {
            condition.put("wasEntryStart", true);
        }
        // 收费类型 1免费/2收费
        if ("2".equals(chargeType)) {
            condition.put("notFree", true);
        } else if ("1".equals(chargeType)) {
            condition.put("free", true);
        }
        // 级别
        if (StringUtils.isNotBlank(activityLevel)) {
            condition.put("activityLevel", activityLevel);
        }

        try {
            if (StringUtils.isNotBlank(pageNumber)) {
                pageIndex = Integer.parseInt(pageNumber);
            }
        } catch (Exception e) {
            logger.error("查询异常1", e);
            pageIndex = 1;
        }
        try {
            if (StringUtils.isNotBlank(size)) {
                pageSize = Integer.parseInt(size);
            }
        } catch (Exception e) {
            logger.error("查询异常2", e);
            pageSize = 10;
        }

        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        Map<String, Object> result = new HashMap<>();
        try {
            List<ActivityInfo> activityList = activityDAO.getActivityList(condition, skip, max);
            int recordCount = activityDAO.countActivityList(condition);
            // 多少页
            int pageCount;
            if (recordCount > 0) {
                if (recordCount % pageSize == 0) {
                    pageCount = recordCount / pageSize;
                } else {
                    pageCount = recordCount / pageSize + 1;
                }
            } else {
                pageCount = 1;
            }

            result.put("pageIndex", pageIndex);
            result.put("pageCount", pageCount);
            result.put("recordCount", recordCount);
            result.put("dataList", activityList);
            returnResult.setCode(LotteryResultCode.SUCCESS);
            returnResult.setMessage("成功");
            returnResult.setReturnObject(result);
        } catch (Exception e) {
            logger.error("接口查询活动列表失败", e);
            // throw new ServiceException("查询活动列表失败！");
            returnResult.setCode(LotteryResultCode.FAILED);
            returnResult.setMessage("接口查询活动列表失败.");
        }
        return returnResult;
    }

    @Override
    public ActivityInfo getActivityById(Long activityId) {

        ActivityInfo activityInfo = null;

        if (activityId == null) {
            logger.error("没有找到活动id。");
            // throw new ServiceException(0, "没有找到活动id。");
            return null;
        }


        try {
            activityInfo = activityDAO.getActivityById(activityId);

        } catch (Exception e) {
            logger.error("获取活动详情失败，activityId="+activityId, e);

        }

        return activityInfo;
    }
}
