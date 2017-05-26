package com.pltfm.activity.action;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.activity.dao.ActivityInfoDAO;
import com.pltfm.activity.service.ActivityEntryService;
import com.pltfm.activity.service.ActivityService;
import com.pltfm.app.action.BaseAction;
import com.pltfm.app.enums.ActivityBrandType;
import com.pltfm.app.enums.ActivityChargeType;
import com.pltfm.app.enums.ActivityEntryStatus;
import com.pltfm.app.enums.ActivityEntrySupplierType;
import com.pltfm.app.enums.ActivityStatus;
import com.pltfm.app.enums.ActivitySupplierType;
import com.pltfm.app.enums.ActivityType;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.service.ProdBrandService;
import com.pltfm.app.util.ActivityUtils;
import com.pltfm.app.util.FileUploadUtils;
import com.pltfm.app.vobject.ActivityCharge;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivitySupplierEntry;
import com.pltfm.app.vobject.ActivitySupplierScore;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.ProdBrand;

@Controller("activityAction")
@Scope(value = "prototype")
public class ActivityAction extends BaseAction {

    @Resource
    private ActivityService activityService;

    @Resource
    private ProdBrandService prodBrandService;

    @Resource
    private ActivityEntryService activityEntryService;

    @Resource
    private ActivityInfoDAO activityInfoDAO;

    private Category categorys = new Category();

    private MerchantInfoOrSuppliers mer = new MerchantInfoOrSuppliers();

    private ProdBrand prodBrand = new ProdBrand();

    // page 对象
    private Page page = new Page();

    // 跳转页数
    private int pageNum = 0;

    private ActivityInfo activityInfo;

    private String entryStartTime;

    private String entryEndTime;

    private String activityStartTime;

    private String activityEndTime;

    private String activityId;

    private String activityType;

    private String activityChargeType;

    private String fixedCharge;

    private String singleCharge;

    private String commissionRate;

    private String supplierChoiceType;

    private String activityLevel;

    private String greatEqualPoint;

    private String brandChoiceType;

    private String supplierMaximum;

    private String skuMaximum;

    private String[] activityChannls;

    private String[] activityIndustrys;

    private String[] activityLabels;

    private String[] brandIds;

    private String[] categoryIds;

    private String[] supplierIds;

    // 用于区分从活动审核页面进去查看活动详细信息（“activityAuditPage”）
    private String pageType;

    // 活动的Logo图片
    private File logoFile;

    // 最大文件大小
    private long activityLogoSize = 1 * 1024 * 1024;

    private String imagePath = ConfigurationUtil.getString("activityPreviewPath");

    /**
     * 跳转到活动列表页面
     * 
     * @return
     */
    public String activityList() {
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

            activityService.searchPage(page, activityInfo, ActivityUtils.ACTIVITY_LIST);
        } catch (Exception e) {
            logger.error("查询活动列表出错:" + e.getMessage(), e);
            return ERROR;
        }
        setActivityTypeMap();
        setActivityChargeTypeMap();
        setActivityStatusMap();
        return SUCCESS;
    }

    /**
     * 根据活动id查询活动详细信息
     * 
     * @return
     */
    public String findActivityInfoById() {
        Long activityId = activityInfo.getActivityId();
        if (activityId == null) {
            logger.error("根据活动id查询活动详细信息时，活动id为空");
            return ERROR;
        } else {
            try {
                activityInfo = activityService.findActivityInfoById(activityId);
                setActivityTypeMap();
                setActivityChargeTypeMap();
                setActivityChannelMap();
                setActivityLabelList();
                setActivityIndustryList();
            } catch (Exception e) {
                logger.error("根据活动id查询活动详细信息失败：" + e.getMessage(), e);
                return ERROR;
            }
        }
        return SUCCESS;
    }

    /**
     * 跳转到活动创建页面
     * 
     * @return
     */
    public String toActivityAdd() {
        if (null == activityInfo) activityInfo = new ActivityInfo();
        setActivityTypeMap();
        setActivityChannelMap();
        setActivityChargeTypeMap();
        setActivityLabelList();
        setActivityIndustryList();
        return SUCCESS;
    }

    /**
     * 跳转到活动创建页面
     * 
     * @return
     */
    public String toActivityModify() {
        if (null == activityInfo) activityInfo = new ActivityInfo();
        try {
            activityInfo = activityService.findActivityInfoById(activityInfo.getActivityId());
            setActivityTypeMap();
            setActivityChargeTypeMap();
            setActivityChannelMap();
            setActivityLabelList();
            setActivityIndustryList();
        } catch (Exception e) {
            logger.error("根据活动id查询活动详细信息失败：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    public String updateActivity() {
        Map jsonMap = new HashMap();
        ActivityCharge activityCharge = null;
        ActivitySupplierScore activitySupplierScore = null;
        List<Long> brandIdList = null;
        List<Long> categoryIdList = null;
        List<Long> supplierIdList = null;
        StringBuilder activityChannlBuffer = null;
        StringBuilder activityIndustryBuffer = null;
        StringBuilder activityLabelBuffer = null;
        try {

            if (logoFile != null) {

                if (logoFile.length() > activityLogoSize) {// 判断图片的分辨率大小
                    jsonMap.put("flag", false);
                    jsonMap.put("msg", "图片大小不能超过1M!");
                    writeJson(jsonMap);
                    return null;
                }
                // 获取文件后缀名
                String fileExt =
                        activityInfo.getLogoPath()
                                .substring(activityInfo.getLogoPath().lastIndexOf(".") + 1)
                                .toLowerCase();
                if (!fileExt.toLowerCase().equals("jpg") && !fileExt.toLowerCase().equals("gif")
                        && !fileExt.toLowerCase().equals("jpeg")
                        && !fileExt.toLowerCase().equals("png")) {
                    jsonMap.put("flag", false);
                    jsonMap.put("msg", "请上传合法格式的logo图片!");
                    writeJson(jsonMap);
                    return null;
                }
                // 文件名
                String fileName = DateFormatUtils.format(new java.util.Date(), "yyyyMMddHHmmssSSS") + "_"
                                + new Random().nextInt(1000) + "." + fileExt;
                // 绝对路径，上传时使用
                String savePath = FileUploadUtils.createSavePath("activity");
                // 相对路径，预览时使用
                String relativePath = savePath.substring(ConfigurationUtil.getString("activityUploadPath").length());
                File deskFile = new File(savePath, fileName);
                // 上传文件
                FileUtils.copyFile(logoFile, deskFile);
                activityInfo.setLogoPath(relativePath + fileName);
            }

            if (StringUtils.isNotBlank(entryStartTime) && StringUtils.isNotBlank(entryEndTime)) {
                activityInfo.setEntryStartTime(Timestamp.valueOf(entryStartTime.trim()));
                activityInfo.setEntryEndTime(Timestamp.valueOf(entryEndTime.trim()));
            } else {
                jsonMap.put("flag", false);
                jsonMap.put("msg", "活动报名起止时间必填!");
                writeJson(jsonMap);
                return null;
            }
            if (StringUtils.isNotBlank(activityStartTime)
                    && StringUtils.isNotBlank(activityEndTime)) {
                activityInfo.setActivityStartTime(Timestamp.valueOf(activityStartTime.trim()));
                activityInfo.setActivityEndTime(Timestamp.valueOf(activityEndTime.trim()));
            } else {
                jsonMap.put("flag", false);
                jsonMap.put("msg", "活动起止时间必填!");
                writeJson(jsonMap);
                return null;
            }
            if (StringUtils.isNotBlank(activityType)) {
                activityInfo.setActivityType(Integer.valueOf(activityType));
            }
            if (StringUtils.isNotBlank(activityChargeType)) {
                activityInfo.setChargeType(Integer.valueOf(activityChargeType));
            }

            if (ActivityChargeType.FIXED_CHARGES.getType().equals(
                    Integer.valueOf(activityChargeType))
                    && StringUtils.isNotBlank(fixedCharge)) {
                activityCharge = new ActivityCharge();
                activityCharge.setFixedCharge(BigDecimal.valueOf(Double.valueOf(fixedCharge)));
                activityInfo.setActivityCharge(activityCharge);
            }
            if (ActivityChargeType.SINGLE_CHARGES.getType().equals(
                    Integer.valueOf(activityChargeType))
                    && StringUtils.isNotBlank(singleCharge)) {
                activityCharge = new ActivityCharge();
                activityCharge.setSingleCharge(BigDecimal.valueOf(Double.valueOf(singleCharge)));
                activityInfo.setActivityCharge(activityCharge);
            }
            if (ActivityChargeType.RABATE.getType().equals(Integer.valueOf(activityChargeType))
                    && StringUtils.isNotBlank(commissionRate)) {
                activityCharge = new ActivityCharge();
                BigDecimal rabate = new BigDecimal(commissionRate);
                activityCharge.setCommissionRate(rabate.divide(new BigDecimal(100), 4,
                        BigDecimal.ROUND_HALF_UP));
                activityInfo.setActivityCharge(activityCharge);
            }
            if (StringUtils.isNotBlank(supplierChoiceType)) {
                activityInfo.setSupplierChoiceType(Integer.valueOf(supplierChoiceType));
            }
            if (ActivitySupplierType.CHOICE_OF_SCORE.getType().equals(
                    Integer.valueOf(supplierChoiceType))
                    && StringUtils.isNotBlank(greatEqualPoint)) {
                activitySupplierScore = new ActivitySupplierScore();
                activitySupplierScore.setGreatEqualPoint(BigDecimal.valueOf(Double
                        .valueOf(greatEqualPoint)));
                activityInfo.setActivitySupplierScore(activitySupplierScore);
            }
            if (StringUtils.isNotBlank(brandChoiceType)) {
                activityInfo.setBrandChoiceType(Integer.valueOf(brandChoiceType));
            }
            if (StringUtils.isNotBlank(supplierMaximum)) {
                activityInfo.setSupplierMaximum(Integer.valueOf(supplierMaximum));
            } else {
                activityInfo.setSupplierMaximum(0);
            }
            if (StringUtils.isNotBlank(skuMaximum)) {
                activityInfo.setSkuMaximum(Integer.valueOf(skuMaximum));
            } else {
                activityInfo.setSkuMaximum(0);
            }
            if (ActivityType.CHANNL_TYPE.getType().equals(Integer.valueOf(activityType))
                    && null != activityChannls && activityChannls.length > 0) {
                activityChannlBuffer = new StringBuilder();
                for (String activityChannl : activityChannls) {
                    if (StringUtils.isNotBlank(activityChannl)) {
                        activityChannlBuffer.append(activityChannl).append(",");
                    }
                }
                activityInfo.setActivityChannel(activityChannlBuffer.toString().substring(0,
                        activityChannlBuffer.length() - 1));
            }
            if (null != activityIndustrys && activityIndustrys.length > 0) {
                activityIndustryBuffer = new StringBuilder();
                for (String activityIndustry : activityIndustrys) {
                    if (StringUtils.isNotBlank(activityIndustry)) {
                        activityIndustryBuffer.append(activityIndustry).append(",");
                    }
                }
                activityInfo.setIndustry(activityIndustryBuffer.toString().substring(0,
                        activityIndustryBuffer.length() - 1));
            }
            if (null != activityLabels && activityLabels.length > 0) {
                activityLabelBuffer = new StringBuilder();
                for (String activityLabel : activityLabels) {
                    if (StringUtils.isNotBlank(activityLabel)) {
                        activityLabelBuffer.append(activityLabel).append(",");
                    }
                }
                activityInfo.setActivityLabel(activityLabelBuffer.toString().substring(0,
                        activityLabelBuffer.length() - 1));
            }
            if (ActivityBrandType.DESIGNATED_BRAND.getType().equals(
                    Integer.valueOf(brandChoiceType))
                    && null != brandIds && brandIds.length > 0) {
                brandIdList = new ArrayList<Long>();
                for (String brandId : brandIds) {
                    if (StringUtils.isNotBlank(brandId)) {
                        brandIdList.add(Long.valueOf(brandId));
                    }
                }
            }
            if (ActivitySupplierType.CHOICE_OF_CATOGORYS.getType().equals(
                    Integer.valueOf(supplierChoiceType))
                    && null != categoryIds && categoryIds.length > 0) {
                categoryIdList = new ArrayList<Long>();
                for (String categoryId : categoryIds) {
                    if (StringUtils.isNotBlank(categoryId)) {
                        categoryIdList.add(Long.valueOf(categoryId));
                    }
                }
            }
            if (ActivitySupplierType.DESIGNATED_SUPPLIER.getType().equals(
                    Integer.valueOf(supplierChoiceType))
                    && null != supplierIds && supplierIds.length > 0) {
                supplierIdList = new ArrayList<Long>();
                for (String supplierId : supplierIds) {
                    if (StringUtils.isNotBlank(supplierId)) {
                        supplierIdList.add(Long.valueOf(supplierId));
                    }
                }
            }
            activityInfo.setActivityStatus(ActivityStatus.DRAFT.getStatus());
            activityInfo.setAuditStatus(Integer.valueOf(AuditStatus.UNAUDIT.getStatus()));
            activityInfo.setCreateUser(super.getLoginUserId().longValue());
            activityService.updateActivityInfo(activityInfo, brandIdList, categoryIdList,
                    supplierIdList);
            jsonMap.put("flag", true);
            jsonMap.put("msg", "修改活动成功!");
        } catch (Exception e) {
            logger.error("修改活动信息出错:" + e.getMessage(), e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "修改活动失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    /**
     * 添加活动信息
     * 
     * @return
     */
    public String addActivity() {
        Map jsonMap = new HashMap();
        ActivityCharge activityCharge = null;
        ActivitySupplierScore activitySupplierScore = null;
        List<Long> brandIdList = null;
        List<Long> categoryIdList = null;
        List<Long> supplierIdList = null;
        StringBuilder activityChannlBuffer = null;
        StringBuilder activityIndustryBuffer = null;
        StringBuilder activityLabelBuffer = null;
        try {

            if (logoFile != null) {

                if (logoFile.length() > activityLogoSize) {// 判断图片的分辨率大小
                    jsonMap.put("flag", false);
                    jsonMap.put("msg", "图片大小不能超过1M!");
                    writeJson(jsonMap);
                    return null;
                }
                // 获取文件后缀名
                String fileExt =
                        activityInfo.getLogoPath()
                                .substring(activityInfo.getLogoPath().lastIndexOf(".") + 1)
                                .toLowerCase();
                if (!fileExt.toLowerCase().equals("jpg") && !fileExt.toLowerCase().equals("gif")
                        && !fileExt.toLowerCase().equals("jpeg")
                        && !fileExt.toLowerCase().equals("png")) {
                    jsonMap.put("flag", false);
                    jsonMap.put("msg", "请上传合法格式的logo图片!");
                    writeJson(jsonMap);
                    return null;
                }
                // 文件名
                String fileName =
                        DateFormatUtils.format(new java.util.Date(), "yyyyMMddHHmmssSSS") + "_"
                                + new Random().nextInt(1000) + "." + fileExt;
                // 绝对路径，上传时使用
                String savePath = FileUploadUtils.createSavePath("activity");
                // 相对路径，预览时使用
                String relativePath =
                        savePath.substring(ConfigurationUtil.getString("activityUploadPath")
                                .length());
                File deskFile = new File(savePath, fileName);
                // 上传文件
                FileUtils.copyFile(logoFile, deskFile);
                activityInfo.setLogoPath(relativePath + fileName);
            }

            if (StringUtils.isNotBlank(entryStartTime) && StringUtils.isNotBlank(entryEndTime)) {
                activityInfo.setEntryStartTime(Timestamp.valueOf(entryStartTime.trim()));
                activityInfo.setEntryEndTime(Timestamp.valueOf(entryEndTime.trim()));
            } else {
                jsonMap.put("flag", false);
                jsonMap.put("msg", "活动报名起止时间必填!");
                writeJson(jsonMap);
                return null;
            }
            if (StringUtils.isNotBlank(activityStartTime)
                    && StringUtils.isNotBlank(activityEndTime)) {
                activityInfo.setActivityStartTime(Timestamp.valueOf(activityStartTime.trim()));
                activityInfo.setActivityEndTime(Timestamp.valueOf(activityEndTime.trim()));
            } else {
                jsonMap.put("flag", false);
                jsonMap.put("msg", "活动起止时间必填!");
                writeJson(jsonMap);
                return null;
            }
            if (StringUtils.isNotBlank(activityType)) {
                activityInfo.setActivityType(Integer.valueOf(activityType));
            }
            if (StringUtils.isNotBlank(activityChargeType)) {
                activityInfo.setChargeType(Integer.valueOf(activityChargeType));
            }

            if (ActivityChargeType.FIXED_CHARGES.getType().equals(
                    Integer.valueOf(activityChargeType))
                    && StringUtils.isNotBlank(fixedCharge)) {
                activityCharge = new ActivityCharge();
                activityCharge.setFixedCharge(BigDecimal.valueOf(Double.valueOf(fixedCharge)));
                activityInfo.setActivityCharge(activityCharge);
            }
            if (ActivityChargeType.SINGLE_CHARGES.getType().equals(
                    Integer.valueOf(activityChargeType))
                    && StringUtils.isNotBlank(singleCharge)) {
                activityCharge = new ActivityCharge();
                activityCharge.setSingleCharge(BigDecimal.valueOf(Double.valueOf(singleCharge)));
                activityInfo.setActivityCharge(activityCharge);
            }
            if (ActivityChargeType.RABATE.getType().equals(Integer.valueOf(activityChargeType))
                    && StringUtils.isNotBlank(commissionRate)) {
                activityCharge = new ActivityCharge();
                BigDecimal rabate = new BigDecimal(commissionRate);
                activityCharge.setCommissionRate(rabate.divide(new BigDecimal(100), 4,
                        BigDecimal.ROUND_HALF_UP));
                activityInfo.setActivityCharge(activityCharge);
            }
            if (StringUtils.isNotBlank(supplierChoiceType)) {
                activityInfo.setSupplierChoiceType(Integer.valueOf(supplierChoiceType));
            }
            if (ActivitySupplierType.CHOICE_OF_SCORE.getType().equals(
                    Integer.valueOf(supplierChoiceType))
                    && StringUtils.isNotBlank(greatEqualPoint)) {
                activitySupplierScore = new ActivitySupplierScore();
                activitySupplierScore.setGreatEqualPoint(BigDecimal.valueOf(Double
                        .valueOf(greatEqualPoint)));
                activityInfo.setActivitySupplierScore(activitySupplierScore);
            }
            if (StringUtils.isNotBlank(brandChoiceType)) {
                activityInfo.setBrandChoiceType(Integer.valueOf(brandChoiceType));
            }
            if (StringUtils.isNotBlank(supplierMaximum)) {
                activityInfo.setSupplierMaximum(Integer.valueOf(supplierMaximum));
            } else {
                activityInfo.setSupplierMaximum(0);
            }
            if (StringUtils.isNotBlank(skuMaximum)) {
                activityInfo.setSkuMaximum(Integer.valueOf(skuMaximum));
            } else {
                activityInfo.setSkuMaximum(0);
            }
            if (ActivityType.CHANNL_TYPE.getType().equals(Integer.valueOf(activityType))
                    && null != activityChannls && activityChannls.length > 0) {
                activityChannlBuffer = new StringBuilder();
                for (String activityChannl : activityChannls) {
                    if (StringUtils.isNotBlank(activityChannl)) {
                        activityChannlBuffer.append(activityChannl).append(",");
                    }
                }
                activityInfo.setActivityChannel(activityChannlBuffer.toString().substring(0,
                        activityChannlBuffer.length() - 1));
            }

            if (null != activityIndustrys && activityIndustrys.length > 0) {
                activityIndustryBuffer = new StringBuilder();
                for (String activityIndustry : activityIndustrys) {
                    if (StringUtils.isNotBlank(activityIndustry)) {
                        activityIndustryBuffer.append(activityIndustry).append(",");
                    }
                }
                activityInfo.setIndustry(activityIndustryBuffer.toString().substring(0,
                        activityIndustryBuffer.length() - 1));
            }
            if (null != activityLabels && activityLabels.length > 0) {
                activityLabelBuffer = new StringBuilder();
                for (String activityLabel : activityLabels) {
                    if (StringUtils.isNotBlank(activityLabel)) {
                        activityLabelBuffer.append(activityLabel).append(",");
                    }
                }
                activityInfo.setActivityLabel(activityLabelBuffer.toString().substring(0,
                        activityLabelBuffer.length() - 1));
            }
            if (ActivityBrandType.DESIGNATED_BRAND.getType().equals(
                    Integer.valueOf(brandChoiceType))
                    && null != brandIds && brandIds.length > 0) {
                brandIdList = new ArrayList<Long>();
                for (String brandId : brandIds) {
                    if (StringUtils.isNotBlank(brandId)) {
                        brandIdList.add(Long.valueOf(brandId));
                    }
                }
            }
            if (ActivitySupplierType.CHOICE_OF_CATOGORYS.getType().equals(
                    Integer.valueOf(supplierChoiceType))
                    && null != categoryIds && categoryIds.length > 0) {
                categoryIdList = new ArrayList<Long>();
                for (String categoryId : categoryIds) {
                    if (StringUtils.isNotBlank(categoryId)) {
                        categoryIdList.add(Long.valueOf(categoryId));
                    }
                }
            }
            if (ActivitySupplierType.DESIGNATED_SUPPLIER.getType().equals(
                    Integer.valueOf(supplierChoiceType))
                    && null != supplierIds && supplierIds.length > 0) {
                supplierIdList = new ArrayList<Long>();
                for (String supplierId : supplierIds) {
                    if (StringUtils.isNotBlank(supplierId)) {
                        supplierIdList.add(Long.valueOf(supplierId));
                    }
                }
            }
            activityInfo.setActivityStatus(ActivityStatus.DRAFT.getStatus());
            activityInfo.setAuditStatus(Integer.valueOf(AuditStatus.UNAUDIT.getStatus()));
            activityInfo.setCreateUser(super.getLoginUserId().longValue());
            activityService.saveActivityInfo(activityInfo, brandIdList, categoryIdList,
                    supplierIdList);
            jsonMap.put("flag", true);
            jsonMap.put("msg", "创建活动成功!");
        } catch (Exception e) {
            logger.error("添加活动信息出错:" + e.getMessage(), e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "创建活动失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    // 删除活动
    public String deleteActivity() {
        Map jsonMap = new HashMap();
        try {
            activityService.deleteActivityById(Long.valueOf(activityId));
            jsonMap.put("flag", true);
            jsonMap.put("msg", "刪除活动成功!");
        } catch (Exception e) {
            logger.error("删除活动出错:" + e.getMessage(), e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "刪除活动失败!");
        }
        writeJson(jsonMap);
        return null;
    }

    // 终止活动
    public String stopActivity() {
        Map jsonMap = new HashMap();
        try {
            activityService.stopActivityById(Long.valueOf(activityId));

            activityService.updateActivityInfoCache(null, Long.valueOf(activityId), Boolean.FALSE);
            jsonMap.put("flag", true);
            jsonMap.put("msg", "终止活动成功!");
        } catch (Exception e) {
            logger.error("终止活动出错:" + e.getMessage(), e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "终止活动失败!");
        }
        writeJson(jsonMap);
        return null;
    }

    // 提交审核
    public String submitAuditActivity() {
        Map jsonMap = new HashMap();
        try {
            activityService.submitAuditActivityById(Long.valueOf(activityId));
            jsonMap.put("flag", true);
            jsonMap.put("msg", "活动提交审核成功!");
        } catch (Exception e) {
            logger.error("活动提交审核出错:" + e.getMessage(), e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "活动提交审核失败!");
        }
        writeJson(jsonMap);
        return null;
    }

    // 撤销提审
    public String cancelSubmitAuditActivity() {
        Map jsonMap = new HashMap();
        try {
            activityService.cancelSubmitAuditActivityById(Long.valueOf(activityId));
            jsonMap.put("flag", true);
            jsonMap.put("msg", "活动撤销提审成功!");
        } catch (Exception e) {
            logger.error("活动撤销提审出错:" + e.getMessage(), e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "活动撤销提审失败!");
        }
        writeJson(jsonMap);
        return null;
    }

    // 撤销审核
    public String cancelAuditActivity() {
        Map jsonMap = new HashMap();
        try {
            activityService.cancelAuditActivityById(Long.valueOf(activityId));
            jsonMap.put("flag", true);
            jsonMap.put("msg", "活动撤销审核成功!");
        } catch (Exception e) {
            logger.error("活动撤销审核出错:" + e.getMessage(), e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "活动撤销审核失败!");
        }
        writeJson(jsonMap);
        return null;
    }

    /**
     * 保存邀请商家
     * 
     * @return
     */
    public String saveInviteSuppliers() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Long activityId = activityInfo.getActivityId();
        jsonMap.put("result", true);
        jsonMap.put("msg", "保存邀请商家成功!");
        try {
            ActivityInfo activityInfo = activityService.getActivityById(activityId);
            Timestamp currentDate = new Timestamp(System.currentTimeMillis());
            if (activityInfo.getActivityStartTime().before(currentDate)) {
                jsonMap.put("msg", "活动已开始，不可再邀请商家！");
                writeJson(jsonMap);
                return null;
            }
            // 保存活动商家
            if (null != supplierIds && supplierIds.length > 0) {
                List<ActivitySupplierEntry> activitySupplierEntryList =
                        new ArrayList<ActivitySupplierEntry>();
                ActivitySupplierEntry activitySupplierEntry = null;
                for (String supplierId : supplierIds) {
                    activitySupplierEntry = new ActivitySupplierEntry();
                    activitySupplierEntry.setActivityId(activityId);
                    activitySupplierEntry.setSupplierId(Long.valueOf(supplierId));
                    activitySupplierEntry.setEntryStatus(ActivityEntryStatus.NOT_ENTRY.getStatus());
                    activitySupplierEntry
                            .setActivitySupplierType(ActivityEntrySupplierType.INVITE_SUPPLIER
                                    .getType());
                    activitySupplierEntry.setAuditStatus(Integer.valueOf(AuditStatus.UNAUDIT
                            .getStatus()));
                    activitySupplierEntry.setCreateUser(activityInfo.getCreateUser());
                    activitySupplierEntryList.add(activitySupplierEntry);
                }
                activityEntryService.saveInviteSuppliers(activitySupplierEntryList);
            }
        } catch (Exception e) {
            logger.error("系统错误，保存邀请商家失败,活动id：{} ", activityId, e);
            jsonMap.put("result", false);
            jsonMap.put("msg", "系统错误，保存邀请商家失败!");
        }
        writeJson(jsonMap);
        return null;
    }

    /**
     * 跳转到创建活动页面
     * 
     * @return
     */
    public String createActivity() {
        return SUCCESS;
    }

    /**
     * 查询物理类目
     * 
     * @return
     */
    public String categorysSelect() {
        try {
            if (pageNum != 0) {
                page.setPageNo(pageNum);
            }
            categorys.setIsPhy(1);// 物理类目
            categoryService.queryCategoryOneLevelList(categorys, page);
        } catch (Exception e) {
            logger.error("查询物理类目失败(categorysSelect):" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查询商户信息失败
     * 
     * @return
     */
    public String shopSelect() {
        try {
            supplierAuditService.queryMerList(mer, page);
            setSuppliersTypeMap();
            // activityService.selectListShop(page);
            String inviteSuppliers = getRequest().getParameter("pageType");
            if (inviteSuppliers != null && !"".equals(inviteSuppliers)) {
                getRequest().setAttribute("inviteSuppliers", inviteSuppliers);
            }
        } catch (Exception e) {
            logger.error("查询商户信息失败(shopSelect):" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查询品牌信息
     * 
     * @return
     */
    public String brandSelect() {
        try {
            prodBrandService.searchPage(page, prodBrand);
        } catch (Exception e) {
            logger.error("查询商户信息失败(shopSelect):" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查询活动待邀请的商户信息列表 ，数据范围：入驻商家过滤掉创建活动指定的商家，活动已报名商家；加上商家报名之后撤销的商家
     * 
     * @return
     */
    public String selectSupplierForInvite() {
        try {
            supplierAuditService.querySuppliersForInviteList(mer, activityInfo.getActivityId(),
                    page);
            setSuppliersTypeMap();
            getRequest().setAttribute("activityId", activityInfo.getActivityId());
        } catch (Exception e) {
            logger.error("查询商户信息失败(selectSupplierForInvite):" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查询已邀请商家
     * 
     * @return
     */
    public String queryInvitedSupplier() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Long activityId = activityInfo.getActivityId();
        List<String> invitedSupplierList = new ArrayList<String>();
        jsonMap.put("result", true);
        jsonMap.put("msg", "查询已邀请商家成功!");
        try {
            if (null != activityId) {
                invitedSupplierList = activityInfoDAO.queryInvitedSupplier(activityId);
            }
        } catch (Exception e) {
            logger.error("查询商户信息失败(selectSupplierForInvite):" + e.getMessage(), e);
            jsonMap.put("result", false);
            jsonMap.put("msg", "查询已邀请商家失败!");
        }
        jsonMap.put("invitedSuppliers", invitedSupplierList);
        writeJson(jsonMap);
        return null;
    }

    public ActivityInfo getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(ActivityInfo activityInfo) {
        this.activityInfo = activityInfo;
    }

    public MerchantInfoOrSuppliers getMer() {
        return mer;
    }

    public ProdBrand getProdBrand() {
        return prodBrand;
    }

    public void setMer(MerchantInfoOrSuppliers mer) {
        this.mer = mer;
    }

    public void setProdBrand(ProdBrand prodBrand) {
        this.prodBrand = prodBrand;
    }

    @Override
    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String[] getActivityChannls() {
        return activityChannls;
    }

    public void setActivityChannls(String[] activityChannls) {
        this.activityChannls = activityChannls;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityChargeType() {
        return activityChargeType;
    }

    public void setActivityChargeType(String activityChargeType) {
        this.activityChargeType = activityChargeType;
    }

    public String getFixedCharge() {
        return fixedCharge;
    }

    public void setFixedCharge(String fixedCharge) {
        this.fixedCharge = fixedCharge;
    }

    public String getSingleCharge() {
        return singleCharge;
    }

    public void setSingleCharge(String singleCharge) {
        this.singleCharge = singleCharge;
    }

    public String getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(String commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getSupplierChoiceType() {
        return supplierChoiceType;
    }

    public void setSupplierChoiceType(String supplierChoiceType) {
        this.supplierChoiceType = supplierChoiceType;
    }

    public String getGreatEqualPoint() {
        return greatEqualPoint;
    }

    public void setGreatEqualPoint(String greatEqualPoint) {
        this.greatEqualPoint = greatEqualPoint;
    }

    public String getBrandChoiceType() {
        return brandChoiceType;
    }

    public void setBrandChoiceType(String brandChoiceType) {
        this.brandChoiceType = brandChoiceType;
    }

    public String getSupplierMaximum() {
        return supplierMaximum;
    }

    public void setSupplierMaximum(String supplierMaximum) {
        this.supplierMaximum = supplierMaximum;
    }

    public String getSkuMaximum() {
        return skuMaximum;
    }

    public void setSkuMaximum(String skuMaximum) {
        this.skuMaximum = skuMaximum;
    }

    public String[] getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String[] brandIds) {
        this.brandIds = brandIds;
    }

    public String[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String[] categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String[] getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(String[] supplierIds) {
        this.supplierIds = supplierIds;
    }

    public File getLogoFile() {
        return logoFile;
    }

    public void setLogoFile(File logoFile) {
        this.logoFile = logoFile;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String[] getActivityIndustrys() {
        return activityIndustrys;
    }

    public void setActivityIndustrys(String[] activityIndustrys) {
        this.activityIndustrys = activityIndustrys;
    }

    public String[] getActivityLabels() {
        return activityLabels;
    }

    public void setActivityLabels(String[] activityLabels) {
        this.activityLabels = activityLabels;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

}
