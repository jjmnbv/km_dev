package com.pltfm.activity.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.pltfm.activity.service.ActivityAuditService;
import com.pltfm.activity.service.ActivityService;
import com.pltfm.app.action.BaseAction;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.ActivityStatus;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.util.ActivityUtils;
import com.pltfm.app.vobject.ActivityInfo;
import com.kmzyc.commons.page.Page;

@Controller("activityAuditAction")
@Scope(value = "prototype")
public class ActivityAuditAction extends BaseAction {

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivityAuditService activityAuditService;

    // page 对象
    private Page page = new Page();
    private ActivityInfo activityInfo;
    private String entryStartTimeSearch;
    private String entryEndTimeSearch;
    private String activityStartTimeSearch;
    private String activityEndTimeSearch;

    /**
     * 跳转到活动列表审核页面
     * 
     * @return
     */
    public String activityAuditList() {
        if (activityInfo == null) {
            activityInfo = new ActivityInfo();
        }
        if (StringUtils.isNotBlank(entryStartTimeSearch)
                && StringUtils.isNotBlank(entryEndTimeSearch)) {
            activityInfo.setEntryStartTime(Timestamp.valueOf(entryStartTimeSearch.trim()));
            activityInfo.setEntryEndTime(Timestamp.valueOf(entryEndTimeSearch.trim()));
        }
        if (StringUtils.isNotBlank(activityStartTimeSearch)
                && StringUtils.isNotBlank(activityEndTimeSearch)) {
            activityInfo.setActivityStartTime(Timestamp.valueOf(activityStartTimeSearch.trim()));
            activityInfo.setActivityEndTime(Timestamp.valueOf(activityEndTimeSearch.trim()));
        }
        try {
            activityInfo.setActivityStatus(ActivityStatus.PRE_AUDIT.getStatus());
            activityInfo.setAuditStatus(Integer.valueOf(AuditStatus.UNAUDIT.getStatus()));
            activityService.searchPage(page, activityInfo, ActivityUtils.ACTIVITY_LIST);
            setActivityTypeMap();
            setAuditStatusMap();
            setActivityChargeTypeMap();
        } catch (Exception e) {
            logger.error("跳转到活动列表审核页面，查找活动列表失败：", e);
        }
        return SUCCESS;
    }

    /**
     * 审核活动信息
     * 
     * @return
     */
    public String auditActivityById() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Long activityId = activityInfo.getActivityId();
        try {
            Timestamp entryime = activityInfo.getEntryStartTime();
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            if (entryime.before(currentTime)) {
                jsonMap.put("result", false);
                jsonMap.put("msg", "报名开始时间已过，不可进行审核，请重新修改报名开始时间!");
                strWriteJson(JSON.toJSONString(jsonMap));
                return null;
            }
            if (activityId == null) {
                jsonMap.put("result", false);
                jsonMap.put("msg", "活动信息不存在!");
            } else {
                activityInfo.setAuditUser(Long.valueOf(super.getLoginUserId()));
                ResultMessage rmsg = activityAuditService.auditActivityById(activityInfo);
                jsonMap.put("result", rmsg.getIsSuccess());
                jsonMap.put("msg", rmsg.getMessage());
            }
        } catch (Exception e) {
            jsonMap.put("result", false);
            jsonMap.put("msg", "系统错误，审核失败!");
            logger.error("审核活动信息失败，", e);
        }
        strWriteJson(JSON.toJSONString(jsonMap));
        return null;
    }

    public String auditActivity() {

        return SUCCESS;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ActivityInfo getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(ActivityInfo activityInfo) {
        this.activityInfo = activityInfo;
    }

    public String getEntryStartTimeSearch() {
        return entryStartTimeSearch;
    }

    public void setEntryStartTimeSearch(String entryStartTimeSearch) {
        this.entryStartTimeSearch = entryStartTimeSearch;
    }

    public String getEntryEndTimeSearch() {
        return entryEndTimeSearch;
    }

    public void setEntryEndTimeSearch(String entryEndTimeSearch) {
        this.entryEndTimeSearch = entryEndTimeSearch;
    }

    public String getActivityStartTimeSearch() {
        return activityStartTimeSearch;
    }

    public void setActivityStartTimeSearch(String activityStartTimeSearch) {
        this.activityStartTimeSearch = activityStartTimeSearch;
    }

    public String getActivityEndTimeSearch() {
        return activityEndTimeSearch;
    }

    public void setActivityEndTimeSearch(String activityEndTimeSearch) {
        this.activityEndTimeSearch = activityEndTimeSearch;
    }

}
