package com.pltfm.activity.action;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.page.Page;
import com.pltfm.activity.service.ActivityPromotionCostService;
import com.pltfm.app.action.BaseAction;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;

/**
 * 渠道推广费用管理
 * 
 *
 */
@Controller("activityPromotionCostAction")
@Scope(value = "prototype")
public class ActivityPromotionCostAction extends BaseAction {

    private static final long serialVersionUID = 7071410867030905336L;

    @Resource
    private ActivityPromotionCostService activityChannelPromotionCostService;

    private ActivitySupplierEntry activitySupplierEntry;

    private ActivitySku activitySku;

    private String activityStartTime;

    private String activityEndTime;

    private Page page = new Page();

    /**
     * 查询活动的推广费用（渠道推广）
     * 
     * @return
     */
    public String queryActivityPromotionCost() {
        if (activitySupplierEntry == null) {
            activitySupplierEntry = new ActivitySupplierEntry();
        }
        try {
            if (StringUtils.isNotBlank(activityStartTime) && StringUtils.isNotBlank(activityEndTime)) {
                activitySupplierEntry.getActivityInfo().setActivityStartTime(
                        Timestamp.valueOf(activityStartTime.trim()));
                activitySupplierEntry.getActivityInfo().setActivityEndTime(
                        Timestamp.valueOf(activityEndTime.trim()));
            }
            activityChannelPromotionCostService.queryActivityPromotionCost(page, activitySupplierEntry);
            setActivityStatusMap();
            setActivityPaymentStatusMap();
        } catch (Exception e) {
            logger.error("查询活动的推广费用（渠道推广）失败：", e);
        }
        return SUCCESS;
    }

    /**
     * 分页查找活动推广销售明细（渠道推广）
     * 
     * @return
     */
    public String queryActivitySupplierCostDetail() {
        if (activitySku == null) {
            activitySku = new ActivitySku();
        }

        try {
            activityChannelPromotionCostService.queryActivitySupplierCostDetail(page, activitySku);
        } catch (Exception e) {
            logger.error("查找活动推广销售明细（渠道推广）失败：", e);
        }
        return SUCCESS;
    }

    public ActivitySupplierEntry getActivitySupplierEntry() {
        return activitySupplierEntry;
    }

    public void setActivitySupplierEntry(ActivitySupplierEntry activitySupplierEntry) {
        this.activitySupplierEntry = activitySupplierEntry;
    }

    @Override
    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(String activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public String getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(String activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public ActivitySku getActivitySku() {
        return activitySku;
    }

    public void setActivitySku(ActivitySku activitySku) {
        this.activitySku = activitySku;
    }

}
