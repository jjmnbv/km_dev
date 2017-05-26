package com.pltfm.activity.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.activity.service.ActivityEntryService;
import com.pltfm.activity.service.ActivityService;
import com.pltfm.activity.service.ActivitySkuService;
import com.pltfm.app.action.BaseAction;
import com.pltfm.app.enums.ActivityEntryStatus;
import com.pltfm.app.enums.ActivityEntrySupplierType;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.util.ActivityUtils;
import com.pltfm.app.util.RedisLock;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;

@Controller("activityEntryAction")
@Scope(value = "prototype")
public class ActivityEntryAction extends BaseAction {

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivityEntryService activityEntryService;

    @Resource
    private ActivitySkuService activitySkuService;

    @Resource
    private RedisLock redisLock;

    private static final String PREFIX_LOCK = "/locks/refund";

    private ActivityInfo activityInfo;

    private ActivitySupplierEntry activitySupplierEntry;

    private ActivitySku activitySku;

    private List<ActivitySku> activitySkuList;

    // 审核不通过需要标记活动产品id
    private String[] activitySkuIds;

    // page 对象
    private Page page = new Page();

    private String entryStartTime;

    private String entryEndTime;

    private String activityStartTime;

    private String activityEndTime;

    private String imagePath = ConfigurationUtil.getString("supplierPreviewPath");
    
    private PromotionInfo promotionInfo;
    
    private Long stTime;

    private Long enTime;

    private Long nowTime;

    /**
     * 跳转到活动报名管理列表页面
     * 
     * @return
     */
    public String activityEntryList() {
        if (null == activityInfo) activityInfo = new ActivityInfo();
        try {
            if (StringUtils.isNotBlank(entryStartTime) && StringUtils.isNotBlank(entryEndTime)) {
                activityInfo.setEntryStartTime(Timestamp.valueOf(entryStartTime.trim()));
                activityInfo.setEntryEndTime(Timestamp.valueOf(entryEndTime.trim()));
            }
            if (StringUtils.isNotBlank(activityStartTime)
                    && StringUtils.isNotBlank(activityEndTime)) {
                activityInfo.setActivityStartTime(Timestamp.valueOf(activityStartTime.trim()));
                activityInfo.setActivityEndTime(Timestamp.valueOf(activityEndTime.trim()));
            }
            activityService.searchPage(page, activityInfo, ActivityUtils.ACTIVITY_ENTRY_LIST);
            setActivityTypeMap();
            setActivityChargeTypeMap();
            setActivityOutDraftCanSellStatusMap();
        } catch (Exception e) {
            logger.error("查询活动列表失败:", e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 跳转到活动商家报名列表页面
     * 
     * @return
     */
    public String querySupplierEntryList() {
        if (null == activitySupplierEntry) activitySupplierEntry = new ActivitySupplierEntry();
        try {
            if (StringUtils.isNotBlank(entryStartTime) && StringUtils.isNotBlank(entryEndTime)) {
                activitySupplierEntry.setEntryStartTime(Timestamp.valueOf(entryStartTime.trim()));
                activitySupplierEntry.setEntryEndTime(Timestamp.valueOf(entryEndTime.trim()));
            }
            activitySupplierEntry.setEntryStatus(ActivityEntryStatus.CANCEL_ENTRY.getStatus());
            activityEntryService.searchPage(page, activitySupplierEntry);
            // 活动报名商家审核状态
            setAuditStatusMap();
            // 活动报名商家缴费状态
            setActivityPaymentStatusMap();
        } catch (Exception e) {
            logger.error("查询活动商家报名列表失败:", e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 跳转到活动商家报名详情页面
     * 
     * @return
     */
    public String querySupplierEntryDeatil() {
        if (null == activitySupplierEntry) activitySupplierEntry = new ActivitySupplierEntry();
        try {
            // 页面有用：活动报名商家详情
            activitySupplierEntry = activityEntryService.activityEntryDetail(activitySupplierEntry);
            activitySkuService
                    .activityProductList(page, activitySupplierEntry.getSupplierEntryId());
            // 活动报名商家审核状态
            setAuditStatusMap();
            // 活动报名商家缴费状态
            setActivityPaymentStatusMap();
            // 活动费用
            setActivityChargeTypeMap();
        } catch (Exception e) {
            logger.error("跳转到活动商家报名详情页面失败:", e.getMessage(), e);
        }
        return SUCCESS;
    }
    
    /**
     * 查询促销详情
     * @return
     */
    public String selectPromotion() {
        try {
            promotionInfo = activityEntryService.selectPromotion(promotionInfo.getPromotionId());
//          promotionInfo = activityEntryService.selectPromotion(26102L);
            if (promotionInfo != null) {
                stTime = promotionInfo.getStartTime().getTime();
                enTime = promotionInfo.getEndTime().getTime();
                nowTime = new Date().getTime();
                setPromotionTypeMap();
                setPromotionStatusMap();
            }
        } catch (Exception e) {
            logger.error("查询促销详情失败(categorysSelect):" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 审核校验
     */
    public void verifyStauts() {
        Map jsonMap = new HashMap();
        try {
            List<ActivitySupplierEntry> list =
                    activityEntryService.verifyStatus(activitySupplierEntry);
            // 活动收费类型 1：免费 2：固定收费 3：单品收费 4：返佣
            int chargeType = list.get(0).getActivityInfo().getChargeType();
            // 活动开始时间
            Timestamp activityStartTime = list.get(0).getActivityInfo().getActivityStartTime();
            // 审核状态 0：未审核 1：已审核 2：审核不通过
            int auditStatus = list.get(0).getAuditStatus();
            // 款项状态1：待缴费 2：已缴费 3：待退款 4：已退款
            int paymentStatus = list.get(0).getActivityPayment().getActivityPaymentStatus();
            jsonMap.put("flag", true);
            jsonMap.put("chargeType", chargeType);
            jsonMap.put("auditStatus", auditStatus);
            jsonMap.put("paymentStatus", paymentStatus);
            jsonMap.put("activityStartTime", activityStartTime);
        } catch (Exception e) {
            logger.error("审核校验失败:", e.getMessage(), e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "操作失败，请稍后重试或与管理员联系！");
        }
        writeJson(jsonMap);
    }

    /**
     * 审核
     */
    public void activityAuditEntry() {
        Map jsonMap = new HashMap();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Long loginUserId = super.getLoginUserId().longValue();
        activitySupplierEntry.setAuditTime(ts);
        activitySupplierEntry.setAuditUser(loginUserId);
        activitySupplierEntry.setModifUser(super.getLoginUserId().longValue());
        String nodeKey = PREFIX_LOCK + "/" + activitySupplierEntry.getActivityId() + "/"
                        + activitySupplierEntry.getSupplierId();
        try {
            logger.info("线程[{}]开始去获取审核锁[{}].", new Object[]{Thread.currentThread().getName(), nodeKey});
            if (!redisLock.tryLock(nodeKey)) {
                logger.error("线程[{}],没有获取到审核锁{}.", new Object[] {Thread.currentThread().getName(), nodeKey});
                jsonMap.put("flag", false);
                jsonMap.put("msg", "审核失败!");
                writeJson(jsonMap);
                return;
            }
            logger.info("线程{}已获取到审核锁{}.", new Object[] {Thread.currentThread().getName(),  nodeKey});

            if (activitySupplierEntry.getAuditStatus().toString().equals(AuditStatus.NOT_THROUGH_AUDIT.getStatus())) {// 审核不通过
                // 审核不通过（涉及退款操作，审核时加锁）
                String auditString = activityEntryService.activityAuditEntryNoPass(activitySupplierEntry, activitySkuIds);
                jsonMap.put("flag", true);
                jsonMap.put("msg", auditString);
            } else if (activitySupplierEntry.getAuditStatus().toString().equals(AuditStatus.AUDIT.getStatus())) {// 审核通过
                if (StringUtils.isNotBlank(activityStartTime) && StringUtils.isNotBlank(activityEndTime)) {
                    activitySupplierEntry.setCreateStartTime(Timestamp.valueOf(activityStartTime.trim()));
                    activitySupplierEntry.setCreateEndTime(Timestamp.valueOf(activityEndTime.trim()));
                }
                // 审核通过
                String auditString = activityEntryService.activityAuditEntryPass(activitySupplierEntry, activitySkuList);
                jsonMap.put("flag", true);
                jsonMap.put("msg", auditString);
            } else {
                jsonMap.put("flag", false);
                jsonMap.put("msg", "检查审核状态错误，审核失败!");
            }
        } catch (Exception e) {
            logger.error("审核失败:", e.getMessage(), e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "审核失败!");
        } finally {
            redisLock.release(nodeKey);
            logger.info("线程[{}]释放审核锁[{}].", new Object[] {Thread.currentThread().getName(), nodeKey});
        }
        writeJson(jsonMap);
    }

    /**
     * 跳转到活动商家报名产品列表页面
     * 
     * @return
     */
    public String querySupplierProductList() {
        if (null == activitySku) activitySku = new ActivitySku();
        try {
            activitySkuService.activityEntryProductList(page, activitySku);
        } catch (Exception e) {
            logger.error("查询活动商家报名产品列表失败:", e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 导出活动商家报名产品列表
     * 
     * @return
     */
    public String exportActivitySupplierProductList() {
        if (null == activitySku) activitySku = new ActivitySku();
        FileInputStream f= null;
        ByteArrayInputStream bais = null;
        try {
            activitySkuService.exportActivitySupplierProductList(activitySku);
            File file = new File(ConfigurationUtil.getString("exportExcelPath") + File.separator
                            + "activityProduct.xls");
            f = new FileInputStream(file);
            byte[] fb = new byte[f.available()];
            f.read(fb);
            ServletActionContext.getResponse().setHeader("Content-disposition",
                    "attachment; filename=" + new String("活动报名商品表.xls".getBytes("gb2312"), "iso8859-1"));
            bais = new ByteArrayInputStream(fb);
            int b;
            while ((b = bais.read()) != -1) {
                ServletActionContext.getResponse().getOutputStream().write(b);
            }
            ServletActionContext.getResponse().getOutputStream().flush();
            f.close();
            bais.close();
        } catch (Exception e) {
            logger.error("导出活动商家报名产品列表失败:", e.getMessage(), e);
            return ERROR;
        } finally {
            try {
                if (f != null) {
                    f.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 跳转到活动商家报名列表页面
     * 
     * @return
     */
    public String queryInviteSupplierEntryList() {
        if (null == activitySupplierEntry) return ERROR;
        try {
            if (StringUtils.isNotBlank(activityStartTime)
                    && StringUtils.isNotBlank(activityEndTime)) {
                activitySupplierEntry.setCreateStartTime(Timestamp
                        .valueOf(activityStartTime.trim()));
                activitySupplierEntry.setCreateEndTime(Timestamp.valueOf(activityEndTime.trim()));
            }
            activitySupplierEntry.setActivitySupplierType(ActivityEntrySupplierType.INVITE_SUPPLIER
                    .getType());
            activityEntryService.queryInviteSupplierEntry(page, activitySupplierEntry);
        } catch (Exception e) {
            logger.error("查询已报名的商家失败:", e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    public ActivitySku getActivitySku() {
        return activitySku;
    }

    public void setActivitySku(ActivitySku activitySku) {
        this.activitySku = activitySku;
    }

    public List<ActivitySku> getActivitySkuList() {
        return activitySkuList;
    }

    public void setActivitySkuList(List<ActivitySku> activitySkuList) {
        this.activitySkuList = activitySkuList;
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

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public ActivityInfo getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(ActivityInfo activityInfo) {
        this.activityInfo = activityInfo;
    }

    public String[] getActivitySkuIds() {
        return activitySkuIds;
    }

    public void setActivitySkuIds(String[] activitySkuIds) {
        this.activitySkuIds = activitySkuIds;
    }

    public String getEntryStartTime() {
        return entryStartTime;
    }

    public void setEntryStartTime(String entryStartTime) {
        this.entryStartTime = entryStartTime;
    }

    public String getEntryEndTime() {
        return entryEndTime;
    }

    public void setEntryEndTime(String entryEndTime) {
        this.entryEndTime = entryEndTime;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public PromotionInfo getPromotionInfo() {
        return promotionInfo;
    }

    public void setPromotionInfo(PromotionInfo promotionInfo) {
        this.promotionInfo = promotionInfo;
    }

    public Long getStTime() {
        return stTime;
    }

    public void setStTime(Long stTime) {
        this.stTime = stTime;
    }

    public Long getEnTime() {
        return enTime;
    }

    public void setEnTime(Long enTime) {
        this.enTime = enTime;
    }

    public Long getNowTime() {
        return nowTime;
    }

    public void setNowTime(Long nowTime) {
        this.nowTime = nowTime;
    }

}
