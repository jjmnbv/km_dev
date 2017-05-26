package com.pltfm.activity.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.activity.dao.ActivityInfoDAO;
import com.pltfm.activity.jstl.ActivityDynamicStatusFuntion;
import com.pltfm.activity.service.ActivityAuditService;
// import com.pltfm.activity.service.ActivityMessageService;
import com.pltfm.activity.service.ActivityService;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.vobject.ActivityInfo;
import com.kmzyc.commons.exception.ServiceException;

@Service("activityAuditService")
public class ActivityAuditServiceImpl implements ActivityAuditService {

    protected Logger logger = LoggerFactory.getLogger(ActivityAuditServiceImpl.class);

    @Resource
    private ActivityInfoDAO activityInfoDao;

    @Resource
    private ActivityService activityService;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage auditActivityById(ActivityInfo activityInfo) throws ServiceException {
        ResultMessage rmsg = new ResultMessage();
        rmsg.setIsSuccess(true);
        rmsg.setMessage("审核通过！");
        try {
            ActivityInfo activityUpdate = new ActivityInfo();
            activityUpdate.setActivityId(activityInfo.getActivityId());
            Timestamp newTime = new Timestamp(System.currentTimeMillis());
            activityUpdate.setAuditTime(newTime);
            activityUpdate.setAuditUser(activityInfo.getAuditUser());
            // 审核活动，设置状态
            // 动态获取活动状态
            Integer activityStatus =
                    ActivityDynamicStatusFuntion.activityInfoStatusByTime(activityInfo);
            activityUpdate.setActivityStatus(activityStatus);
            activityUpdate.setAuditStatus(Integer.valueOf(AuditStatus.AUDIT.getStatus()));
            // 修改活动状态，审核状态
            if (activityInfoDao.updateActivityInfoForAudit(activityUpdate) < 1) {
                rmsg.setIsSuccess(false);
                rmsg.setMessage("活动信息报名开始时间小于当前时间或活动信息已被审核！");
                return rmsg;
            }
            activityInfo = activityInfoDao.findActivityInfoById(activityInfo.getActivityId());
            // 发送消息
            // activityMessageService.sendMessage(activityInfo);
            activityService.updateActivityInfoCache(activityInfo, activityInfo.getActivityId(),
                    Boolean.TRUE);
        } catch (Exception e) {
            logger.error("活动信息id为：{}审核失败", activityInfo.getActivityId(), e);
            throw new ServiceException(e);
        }
        return rmsg;
    }
}
