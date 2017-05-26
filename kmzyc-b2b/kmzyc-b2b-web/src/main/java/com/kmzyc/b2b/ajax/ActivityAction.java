package com.kmzyc.b2b.ajax;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.service.ActivityService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.LotteryResultCode;
import com.pltfm.app.vobject.ActivityInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/4/18 19:39
 */
@Controller
@Scope("prototype")
public class ActivityAction extends BaseAction {

    private Logger logger = LoggerFactory.getLogger(ActivityAction.class);

    private static final long serialVersionUID = -5815491949207343315L;

    private String activityId;

    /**
     * 活动类型（1所有活动/2征集中的活动）
     * 所有活动是指全部平台活动（不含指定商家和邀请参加的）。
     * 征集中的活动是平台活动中，正在报名期间的活动。
     */
    private String activityType = "1";

    /**
     * 活动标签（多选用英文,隔开）
     */
    private String activityLabel;

    /**
     * 所属行业（多选用英文,隔开）
     */
    private String industry;

    /**
     * 收费类型(1免费/2收费)
     */
    private String chargeType;

    /**
     * 活动级别
     */
    private String activityLevel;

    /**
     * 第几页
     */
    private String pageIndex;

    /**
     * 页码
     */
    private String pageSize;

    private ReturnResult<Map<String, Object>> returnResult;

    @Resource
    private ActivityService activityService;

    public String getActivityList() {
        returnResult = activityService.getActivityList(activityType, activityLabel, industry, chargeType,
                activityLevel, pageIndex, pageSize);
        return SUCCESS;
    }

    public String getActivityInfoById(){
        Map<String, Object> map = new HashMap<String, Object>();
        returnResult = new ReturnResult<Map<String, Object>>();
        if (StringUtils.isBlank(activityId)) {
            returnResult.setCode(LotteryResultCode.FAILED);
            returnResult.setMessage("没有找到活动id。");
            return SUCCESS;
        }

        ActivityInfo activityInfo = null;
        try {
            activityInfo = activityService.getActivityById(Long.parseLong(activityId));
        } catch (Exception e) {
            logger.error(String.format("获取活动[%s]信息失败.",activityId),e);
            returnResult.setCode(LotteryResultCode.FAILED);
            returnResult.setMessage("获取活动[" + activityId + "]信息失败");
            return SUCCESS;
        }

        if (activityInfo == null) {
            returnResult.setCode(LotteryResultCode.FAILED);
            returnResult.setMessage("没有找到活动[" + activityId + "]信息。");
            return SUCCESS;
        }

        returnResult.setCode(LotteryResultCode.SUCCESS);
        returnResult.setMessage("成功");
        map.put("data", activityInfo);
        returnResult.setReturnObject(map);
        return SUCCESS;
    }

    /**
     * 将对象返回成Json字符串
     *
     * @param object
     */
    private void writeJson(Object object) {
        PrintWriter print = null;
        try {
            print = getResponse().getWriter();
            print.write(JSON.toJSONString(object));
        } catch (Exception e) {
            logger.error("返回JSON字符串出错!",e);
        } finally {
            if (print != null) {
                print.flush();
                print.close();
            }
        }
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public ReturnResult getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult returnResult) {
        this.returnResult = returnResult;
    }
}
