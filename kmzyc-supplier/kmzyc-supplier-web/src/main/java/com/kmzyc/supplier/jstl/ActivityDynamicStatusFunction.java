package com.kmzyc.supplier.jstl;

import java.sql.Timestamp;

import com.kmzyc.supplier.enums.SupplierEntryStatus;
import com.kmzyc.supplier.vo.ActivityInfoVo;
import com.pltfm.app.enums.ActivityEntryStatus;
import com.pltfm.app.enums.ActivityEntrySupplierType;
import com.pltfm.app.enums.ActivityPaymentStatus;
import com.pltfm.app.enums.ActivityStatus;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.maps.ActivityStatusMap;

/**
 * 活动动态状态JSTL标签
 *
 * @author xkj
 */
public class ActivityDynamicStatusFunction {

    /**
     * 活动动态状态输出
     *
     * @param activityInfo
     * @return
     * @throws Exception
     */
    public static String activityInfoDynamicStatus(ActivityInfoVo activityInfo) throws Exception {
        return ActivityStatusMap.getValue(getActivityInfoDynamicStatus(activityInfo));
    }

    /**
     * 判断是否已过活动报名时间
     *
     * @param activityInfo
     * @return
     * @throws Exception
     */
    public static boolean haveEntryIn(ActivityInfoVo activityInfo, boolean isInvited) throws Exception {
        Integer status = getActivityInfoDynamicStatus(activityInfo);
        Integer supplierType = activityInfo.getSupplierType();
        if (isInvited) {
            if (supplierType == null) {
            } else if (supplierType.intValue() == ActivityEntrySupplierType.INVITE_SUPPLIER.getType().intValue()) {
                return !(status.intValue() == ActivityStatus.ENTRY_IN.getStatus().intValue()
                        || status.intValue() == ActivityStatus.ACTIVITY_NOT_START.getStatus().intValue());
            } else if (supplierType.intValue() == ActivityEntrySupplierType.DESIGNATED_SUPPLIER.getType().intValue()) {
                return status.intValue() != ActivityStatus.ENTRY_IN.getStatus().intValue();
            }

            return !(status.intValue() == ActivityStatus.ENTRY_IN.getStatus().intValue()
                    || status.intValue() == ActivityStatus.ACTIVITY_NOT_START.getStatus().intValue());
        } else {
            return status.intValue() != ActivityStatus.ENTRY_IN.getStatus().intValue();
        }
    }

    /**
     * 根据时间动态输出活动状态
     *
     * @param activityInfo
     * @return
     * @throws Exception
     */
    private static Integer activityInfoStatusByTime(ActivityInfoVo activityInfo) throws Exception {

        Timestamp entryStartTime = activityInfo.getEntryStartTime();
        Timestamp entryEndTime = activityInfo.getEntryEndTime();
        Timestamp activityStartTime = activityInfo.getActivityStartTime();
        Timestamp activityEndTime = activityInfo.getActivityEndTime();
        Timestamp newTime = new Timestamp(System.currentTimeMillis());

        //未到报名时间
        if (entryStartTime.compareTo(newTime) > 0) {
            return ActivityStatus.NOT_ENTRY_TIME.getStatus();
        } else if (entryStartTime.compareTo(newTime) <= 0 && entryEndTime.compareTo(newTime) >= 0) {//报名中
            return ActivityStatus.ENTRY_IN.getStatus();
        } else if (activityStartTime.compareTo(newTime) > 0) {//活动未开始
            return ActivityStatus.ACTIVITY_NOT_START.getStatus();
        } else if (activityStartTime.compareTo(newTime) <= 0 && activityEndTime.compareTo(newTime) >= 0) {//活动进行中
            return ActivityStatus.ACTIVITY_IN.getStatus();
        } else {//活动已结束
            return ActivityStatus.ACTIVITY_END.getStatus();
        }
    }

    /**
     * 我参加的活动动态状态输出
     *
     * @param activityInfo
     * @return
     * @throws Exception
     */
    public static Integer myActivityInfoDynamicStatus(ActivityInfoVo activityInfo) throws Exception {
        try {
            //报名状态
            Integer entryStatus = activityInfo.getEntryStatus();
            //报名审核状态
            Integer auditStatus = activityInfo.getAuditStatus();
            //缴款状态
            Integer activityPaymentStatus = activityInfo.getActivityPaymentStatus();
            //活动开始时间
            Timestamp activityStartTime = activityInfo.getActivityStartTime();
            Timestamp newTime = new Timestamp(System.currentTimeMillis());
            //活动是否已经开始
            boolean wasActivityStart = activityStartTime.compareTo(newTime) <= 0;
            if (auditStatus.intValue() == Integer.parseInt(AuditStatus.AUDIT.getStatus())) {//审核通过
                if (activityPaymentStatus != null && activityPaymentStatus.intValue() == ActivityPaymentStatus.PAYED.getStatus().intValue()) {//已缴款
                    return SupplierEntryStatus.PASS.getStatus();
                }
            } else if (auditStatus.intValue() == Integer.parseInt(AuditStatus.NOT_THROUGH_AUDIT.getStatus())) {//审核不通过
                if (wasActivityStart) {
                    return SupplierEntryStatus.ENTRY_FAIL.getStatus();
                } else {
                    return SupplierEntryStatus.NOT_PASS.getStatus();
                }
            } else if (entryStatus.intValue() == ActivityEntryStatus.CANCEL_ENTRY.getStatus().intValue()) {//已撤销
                if (wasActivityStart) {
                    return SupplierEntryStatus.ENTRY_FAIL.getStatus();
                } else {
                    return SupplierEntryStatus.REVOKE.getStatus();
                }
            } else if (auditStatus.intValue() == Integer.parseInt(AuditStatus.UNAUDIT.getStatus())) {//未审核
                if (activityPaymentStatus == null) {
                    return SupplierEntryStatus.ENTRY_FAIL.getStatus();
                }

                if (activityPaymentStatus.intValue() == ActivityPaymentStatus.NOT_PAY.getStatus().intValue()) {//待缴费
                    if (wasActivityStart) {
                        return SupplierEntryStatus.ENTRY_FAIL.getStatus();
                    } else {
                        return SupplierEntryStatus.NOT_PAY.getStatus();
                    }
                } else if (activityPaymentStatus.intValue() == ActivityPaymentStatus.PAYED.getStatus().intValue()) {//已缴款
                    if (wasActivityStart) {
                        return SupplierEntryStatus.ENTRY_FAIL.getStatus();
                    } else {
                        return SupplierEntryStatus.NOT_AUDIT.getStatus();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取活动状态
     *
     * @param activityInfo
     * @return
     * @throws Exception
     */
    public static Integer getActivityInfoDynamicStatus(ActivityInfoVo activityInfo) throws Exception {
        Integer activityInfoStatus = activityInfo.getActivityStatus();

        switch (activityInfoStatus) {
            case 7:
            case 8:
                break;
            default:
                activityInfoStatus = activityInfoStatusByTime(activityInfo);
        }

        return activityInfoStatus;
    }

}