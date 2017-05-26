package com.pltfm.activity.jstl;

import java.sql.Timestamp;

import com.pltfm.app.enums.ActivityStatus;
import com.pltfm.app.enums.ActivityType;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.maps.ActivityStatusMap;
import com.pltfm.app.vobject.ActivityInfo;

/**
 * 活动动态状态JSTL标签
 * 
 * @author xkj
 * 
 */
public class ActivityDynamicStatusFuntion {
    /**
     * 活动动态状态输出
     * 
     * @param activityInfo
     * @return
     * @throws Exception
     */
    public static String activityInfoDynamicStatus(ActivityInfo activityInfo) throws Exception {
        Integer activityInfoStatus = activityInfo.getActivityStatus();

        if (ActivityStatus.DRAFT.getStatus().intValue() != activityInfoStatus.intValue()
                && ActivityStatus.PRE_AUDIT.getStatus().intValue() != activityInfoStatus.intValue()
                && ActivityStatus.ACTIVITY_CANCELL.getStatus().intValue() != activityInfoStatus
                        .intValue()
                && ActivityStatus.ACTIVITY_STOP.getStatus().intValue() != activityInfoStatus
                        .intValue()) {
            activityInfoStatus = activityInfoStatusByTime(activityInfo);
        }

        return ActivityStatusMap.getValue(activityInfoStatus);
    }

    /**
     * 根据时间动态输出活动状态
     * 
     * @param activityInfo
     * @return
     * @throws Exception
     */
    public static Integer activityInfoStatusByTime(ActivityInfo activityInfo) throws Exception {

        Timestamp entryStartTime = activityInfo.getEntryStartTime();
        Timestamp entryEndTime = activityInfo.getEntryEndTime();
        Timestamp activityStartTime = activityInfo.getActivityStartTime();
        Timestamp activityEndTime = activityInfo.getActivityEndTime();

        Timestamp newTime = new Timestamp(System.currentTimeMillis());

        // 未到报名时间
        if (entryStartTime.compareTo(newTime) > 0) {
            return ActivityStatus.NOT_ENTRY_TIME.getStatus();
        } else if (entryStartTime.compareTo(newTime) <= 0 && entryEndTime.compareTo(newTime) >= 0) {// 报名中
            return ActivityStatus.ENTRY_IN.getStatus();
        } else if (activityStartTime.compareTo(newTime) > 0) {// 活动未开始
            return ActivityStatus.ACTIVITY_NOT_START.getStatus();
        } else if (activityStartTime.compareTo(newTime) <= 0
                && activityEndTime.compareTo(newTime) >= 0) {// 活动进行中
            return ActivityStatus.ACTIVITY_IN.getStatus();
        } else {// 活动已结束
            return ActivityStatus.ACTIVITY_END.getStatus();
        }
    }

    /**
     * 根据状态和时间判断活动列表输出撤销审核按钮
     * 
     * @return
     */
    public static Boolean isCancelActivity(ActivityInfo activityInfo) throws Exception {

        // 审核已通过 且 未到报名时间
        if (AuditStatus.AUDIT.getStatus().equals(activityInfo.getAuditStatus().toString())
                && activityInfo.getEntryStartTime().compareTo(
                        new Timestamp(System.currentTimeMillis())) > 0) {
            return true;
        }
        return false;

    }

    /**
     * 根据状态和时间判断活动列表输出邀请商家按钮（活动未开始）
     * 
     * @return
     */
    public static Boolean isInviteSupplier(ActivityInfo activityInfo) throws Exception {
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        // 审核已通过 且 活动在报名中/活动报名结束但活动未开始
        if (AuditStatus.AUDIT.getStatus().equals(activityInfo.getAuditStatus().toString())
                && activityInfo.getEntryStartTime().compareTo(currentDate) < 0
                && activityInfo.getActivityStartTime().compareTo(currentDate) > 0) {
            return true;
        }
        return false;

    }

    /**
     * 根据状态和时间判断活动列表输出终止活动按钮
     * 
     * @return
     */
    public static Boolean isStopActivity(ActivityInfo activityInfo) throws Exception {

        Timestamp newTime = new Timestamp(System.currentTimeMillis());
        // 审核已通过,渠道推广类型 且 活动进行中
        if (ActivityStatus.ACTIVITY_STOP.getStatus().intValue()!=activityInfo.getActivityStatus().intValue()
        		&& AuditStatus.AUDIT.getStatus().equals(activityInfo.getAuditStatus().toString())
                && ActivityType.CHANNL_TYPE.getType().intValue() == activityInfo.getActivityType()
                        .intValue() && activityInfo.getActivityStartTime().compareTo(newTime) <= 0
                && activityInfo.getActivityEndTime().compareTo(newTime) >= 0) {
            return true;
        }
        return false;

    }

}
