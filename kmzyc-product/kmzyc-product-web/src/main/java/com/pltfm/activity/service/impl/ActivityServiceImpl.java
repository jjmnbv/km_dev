package com.pltfm.activity.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.activity.dao.ActivityBrandDAO;
import com.pltfm.activity.dao.ActivityCategorysDAO;
import com.pltfm.activity.dao.ActivityChargeDAO;
import com.pltfm.activity.dao.ActivityInfoDAO;
import com.pltfm.activity.dao.ActivitySupplierEntryDAO;
import com.pltfm.activity.dao.ActivitySupplierScoreDAO;
import com.pltfm.activity.service.ActivityService;
import com.pltfm.app.enums.ActivityEntryStatus;
import com.pltfm.app.enums.ActivityStatus;
import com.pltfm.app.enums.ActivitySupplierType;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.vobject.ActivityBrand;
import com.pltfm.app.vobject.ActivityCategorys;
import com.pltfm.app.vobject.ActivityCharge;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivityInfoExample;
import com.pltfm.app.vobject.ActivityInfoExample.Criteria;
import com.pltfm.app.vobject.ActivitySupplierEntry;
import com.pltfm.app.vobject.ActivitySupplierScore;
import com.kmzyc.commons.page.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    protected Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Resource
    private ActivityInfoDAO activityInfoDao;

    @Resource
    private ActivitySupplierScoreDAO activitySupplierScoreDao;

    @Resource
    private ActivityBrandDAO activityBrandDao;

    @Resource
    private ActivityCategorysDAO activityCategorysDao;

    @Resource
    private ActivityChargeDAO activityChargeDao;

    @Resource
    private ActivitySupplierEntryDAO activitySupplierEntryDao;

    @Resource
    private JedisCluster jedisCluster;

    // 活动缓存key
    public static final String ACTIVITY_KEY = "activity_";

    // 活动信息商家报名名额限制
    public static final String ACTIVITY_SUPPLIER_MAX = "activity_supplier_max_";

    public void updateActivityInfoCache(ActivityInfo activityInfo, Long activityId,
                                        boolean needUpdateSupplierMax) throws ServiceException {
        // 推送一条redis缓存记录
        logger.info("------ 审核活动信息新增/更新缓存开始,活动信息id：{} -------", activityId);
        try {
            if (activityInfo == null) {
                activityInfo = activityInfoDao.findActivityInfoById(activityId);
            }

            // 插入/更新活动信息缓存
            int time = 30 * 24 * 60 * 60;
            jedisCluster.setex(ACTIVITY_KEY + activityId, time, JSON.toJSONString(activityInfo));
            if (needUpdateSupplierMax) {
                if (activityInfo.getSupplierMaximum() > 0) {
                    // 缓存活动信息商家报名名额限制
                    jedisCluster.set(ACTIVITY_SUPPLIER_MAX + activityId, activityInfo.getSupplierMaximum().toString());
                } else if (jedisCluster.exists(ACTIVITY_SUPPLIER_MAX + activityId)) {
                    jedisCluster.del(ACTIVITY_SUPPLIER_MAX + activityId);
                }
            }
            logger.info("------ 审核活动信息新增/更新缓存成功,活动信息id：{} -------", activityId);
        } catch (Exception e) {
            logger.error("审核活动信息新增/更新缓存失败,活动信息id：{}", activityId);
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, Object> getActivityList(Map<String, String> param) throws ServiceException {
        //第几页,总页数,总数
        int pageSize = 10;
        int pageIndex = 1;

        ActivityInfoExample example = new ActivityInfoExample();
        Criteria criteria = example.createCriteria();
        if (MapUtils.isNotEmpty(param)) {
            //分类
            String activityLabel = param.get("activityLabel");
            if (StringUtils.isNotBlank(activityLabel)) {
                criteria.andActivityLabelIn(Lists.newArrayList(StringUtils.split(activityLabel, ",")));
            }
            //行业
            String industry = param.get("industry");
            if (StringUtils.isNotBlank(industry)) {
                criteria.andIndustryIn(Lists.newArrayList(StringUtils.split(industry, ",")));
            }
            //收费类型
            String chargeType = param.get("chargeType");
            if ("0".equals(chargeType)) {
                criteria.andChargeTypeNotEqualTo(1);
            } else if ("1".equals(chargeType)) {
                criteria.andChargeTypeEqualTo(1);
            }
            //级别
            String activityLevel = param.get("activityLevel");
            if (StringUtils.isNotBlank(activityLevel)) {
                criteria.andActivityLevelEqualTo(activityLevel);
            }

            try {
                String pageNumber = param.get("pageIndex");
                if (StringUtils.isNotBlank(pageNumber)) {
                    pageIndex = Integer.parseInt(pageNumber);
                }
            } catch (Exception e) {
                logger.error("查询异常1，参数[{}],错误信息：{}", new Object[]{param, e});
                pageIndex = 1;
            }
            try {
                String size = param.get("pageSize");
                if (StringUtils.isNotBlank(size)) {
                    pageSize = Integer.parseInt(size);
                }
            } catch (Exception e) {
                logger.error("查询异常2，参数[{}],错误信息：{}", new Object[]{param, e});
                pageSize = 10;
            }
        }

        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<ActivityInfo> activityList = activityInfoDao.getActivityList(example, skip, max);
            int recordCount = activityInfoDao.countActivityList(example);
            //多少页
            int pageCount = 0;
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
        } catch (Exception e) {
            logger.error("接口查询活动列表失败，参数[{}],错误信息：{}", new Object[]{param, e});
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public ActivityInfo getActivityById(Long activityId) throws ServiceException {
        try {
            if (activityId == null) {
                logger.error("没有找到活动id。");
                throw new ServiceException("没有找到活动id。");
            }

            ActivityInfo activityInfo = activityInfoDao.getActivityById(activityId);
            if (activityInfo == null) {
                logger.error("没有找到活动id[{}]的活动详情。", activityId);
                throw new ServiceException("没有找到活动[" + activityId + "]的活动详情。");
            }
            return activityInfo;
        } catch (Exception e) {
            logger.error("获取活动详情失败，activityId[{}],错误信息：{}", new Object[]{activityId, e});
            throw new ServiceException(e);
        }
    }

    /**
     * 分页 查找活动数据
     *
     * @param page
     */
    @Override
    public void searchPage(Page page, ActivityInfo activityInfo, int activityListType)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;

        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ActivityInfoExample aie = new ActivityInfoExample();
        Criteria criteria = aie.createCriteria();

        //ID查询
        if (null != activityInfo.getActivityId()) {
            aie.setActivityId(activityInfo.getActivityId());
        }
        //活动名称查询
        if (StringUtils.isNotBlank(activityInfo.getActivityName())) {
            criteria.andActivityNameLike("%" + activityInfo.getActivityName().trim() + "%");
        }
        //活动类型查询
        if (null != activityInfo.getActivityType()) {
            criteria.andActivityTypeEqualTo(activityInfo.getActivityType());
        }
        //活动状态查询
        if (null != activityInfo.getActivityStatus() && ActivityStatus.DRAFT.getStatus().intValue() != activityInfo.getActivityStatus().intValue()
                && ActivityStatus.PRE_AUDIT.getStatus().intValue() != activityInfo.getActivityStatus().intValue()
                && ActivityStatus.ACTIVITY_CANCELL.getStatus().intValue() != activityInfo.getActivityStatus().intValue()
                && ActivityStatus.ACTIVITY_STOP.getStatus().intValue() != activityInfo.getActivityStatus().intValue()) {

            Timestamp newTime = new Timestamp(System.currentTimeMillis());
            // 未到报名时间
            if (ActivityStatus.NOT_ENTRY_TIME.getStatus().intValue() == activityInfo.getActivityStatus().intValue()) {
                criteria.andEntryStartTimeGreaterThan(newTime);
                //草稿状态、已撤销状态需要屏蔽
                criteria.andActivityStatusNotEqualTo(ActivityStatus.DRAFT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.PRE_AUDIT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_CANCELL.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_STOP.getStatus());
            } else if (ActivityStatus.ENTRY_IN.getStatus().intValue() == activityInfo.getActivityStatus().intValue()) {// 报名中
                criteria.andEntryStartTimeLessThanOrEqualTo(newTime);
                criteria.andEntryEndTimeGreaterThanOrEqualTo(newTime);
                criteria.andActivityStatusNotEqualTo(ActivityStatus.DRAFT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.PRE_AUDIT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_CANCELL.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_STOP.getStatus());
            } else if (ActivityStatus.ACTIVITY_NOT_START.getStatus().intValue() == activityInfo.getActivityStatus().intValue()) {// 活动未开始
                criteria.andEntryEndTimeLessThan(newTime);
                criteria.andActivityStartTimeGreaterThan(newTime);
                criteria.andActivityStatusNotEqualTo(ActivityStatus.DRAFT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.PRE_AUDIT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_CANCELL.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_STOP.getStatus());
            } else if (ActivityStatus.ACTIVITY_IN.getStatus().intValue() == activityInfo.getActivityStatus().intValue()) {// 活动进行中
                criteria.andActivityStartTimeLessThanOrEqualTo(newTime);
                criteria.andActivityEndTimeGreaterThanOrEqualTo(newTime);
                criteria.andActivityStatusNotEqualTo(ActivityStatus.DRAFT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.PRE_AUDIT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_CANCELL.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_STOP.getStatus());
            } else if (ActivityStatus.ACTIVITY_END.getStatus().intValue() == activityInfo.getActivityStatus().intValue()) {// 活动已结束
                criteria.andActivityEndTimeLessThan(newTime);
                criteria.andActivityStatusNotEqualTo(ActivityStatus.DRAFT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.PRE_AUDIT.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_CANCELL.getStatus());
                criteria.andActivityStatusNotEqualTo(ActivityStatus.ACTIVITY_STOP.getStatus());
            }
        } else if (null != activityInfo.getActivityStatus()) {
            criteria.andActivityStatusEqualTo(activityInfo.getActivityStatus());
            //报名开始结束时间
            if (activityInfo.getEntryStartTime() != null && activityInfo.getEntryEndTime() != null) {
                criteria.andEntryStartTimeGreaterThanOrEqualTo(activityInfo.getEntryStartTime());
                criteria.andEntryEndTimeLessThan(activityInfo.getEntryEndTime());
            }
            //活动开始结束时间
            if (activityInfo.getActivityStartTime() != null && activityInfo.getActivityEndTime() != null) {
                criteria.andActivityStartTimeGreaterThanOrEqualTo(activityInfo.getActivityStartTime());
                criteria.andActivityEndTimeLessThan(activityInfo.getActivityEndTime());
            }
        } else {
            switch (activityListType) {
                case 1://活动报名管理列表查询
                    List<Integer> list = new ArrayList();
                    list.add(ActivityStatus.DRAFT.getStatus());
                    list.add(ActivityStatus.PRE_AUDIT.getStatus());
                    list.add(ActivityStatus.ACTIVITY_CANCELL.getStatus());
                    criteria.andActivityStatusIsNotNull();
                    criteria.andActivityStatusNotIn(list);
                    break;
                default:
            }
            //报名开始结束时间
            if (activityInfo.getEntryStartTime() != null && activityInfo.getEntryEndTime() != null) {
                criteria.andEntryStartTimeGreaterThanOrEqualTo(activityInfo.getEntryStartTime());
                criteria.andEntryEndTimeLessThan(activityInfo.getEntryEndTime());
            }
            //活动开始结束时间
            if (activityInfo.getActivityStartTime() != null && activityInfo.getActivityEndTime() != null) {
                criteria.andActivityStartTimeGreaterThanOrEqualTo(activityInfo.getActivityStartTime());
                criteria.andActivityEndTimeLessThan(activityInfo.getActivityEndTime());
            }
        }
        //审核状态查询
        if (null != activityInfo.getAuditStatus()) {
            criteria.andAuditStatusEqualTo(activityInfo.getAuditStatus());
        }
        aie.setOrderByClause("ACTIVITY_INFO.ACTIVITY_ID DESC");

        try {
            int count = activityInfoDao.countByExample(aie);
            List<ActivityInfo> activityInfoList = activityInfoDao.selectByExampleWithoutCLOBs(aie, skip, max);
            page.setDataList(activityInfoList);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveActivityInfo(ActivityInfo activityInfo, List<Long> brandIdList,
                                 List<Long> categoryIdList, List<Long> supplierIdList)
            throws ServiceException {
        try {
            Long activityId = activityInfoDao.saveActivity(activityInfo);
            ActivityCharge activityCharge = activityInfo.getActivityCharge();
            ActivitySupplierScore activitySupplierScore = activityInfo.getActivitySupplierScore();
            List<ActivityBrand> activityBrandList = new ArrayList<ActivityBrand>();//activityInfo.getActivityBrandList();
            List<ActivityCategorys> activityCategorysList = new ArrayList<ActivityCategorys>();//activityInfo.getActivityCategorysList();
            List<ActivitySupplierEntry> activitySupplierEntryList = new ArrayList<ActivitySupplierEntry>();//activityInfo.getActivitySupplierEntryList();

            //保存活动费用值
            if (null != activityCharge
                    && (null != activityCharge.getCommissionRate()
                    || null != activityCharge.getFixedCharge()
                    || null != activityCharge.getSingleCharge())) {
                activityCharge.setActivityId(activityId);
                activityChargeDao.saveActivityCharge(activityCharge);
            }
            //保存活动商家评分
            if (null != activitySupplierScore && null != activitySupplierScore.getGreatEqualPoint()) {
                activitySupplierScore.setActivityId(activityId);
                activitySupplierScoreDao.saveActivitySupplierScore(activitySupplierScore);
            }
            //保存活动品牌
            if (CollectionUtils.isNotEmpty(brandIdList)) {
                ActivityBrand activityBrand = null;
                for (Long brandId : brandIdList) {
                    activityBrand = new ActivityBrand();
                    activityBrand.setActivityId(activityId);
                    activityBrand.setBrandId(brandId);
                    activityBrandList.add(activityBrand);
                }
                int count = activityBrandDao.batchSaveActivityBrand(activityBrandList);
                if (count < 1) {
                    throw new ServiceException("系统错误,保存活动品牌失败!");
                }
            }
            //保存活动类目
            if (CollectionUtils.isNotEmpty(categoryIdList)) {
                ActivityCategorys activityCategorys = null;
                for (Long categoryId : categoryIdList) {
                    activityCategorys = new ActivityCategorys();
                    activityCategorys.setActivityId(activityId);
                    activityCategorys.setCategoryId(categoryId);
                    activityCategorysList.add(activityCategorys);
                }
                int count = activityCategorysDao.batchSaveActivityCategorys(activityCategorysList);
                if (count < 1) {
                    throw new ServiceException("系统错误,保存活动品牌失败!");
                }
            }
            //保存活动商家
            if (CollectionUtils.isNotEmpty(supplierIdList)) {
                ActivitySupplierEntry activitySupplierEntry = null;
                for (Long supplierId : supplierIdList) {
                    activitySupplierEntry = new ActivitySupplierEntry();
                    activitySupplierEntry.setActivityId(activityId);
                    activitySupplierEntry.setSupplierId(supplierId);
                    activitySupplierEntry.setEntryStatus(ActivityEntryStatus.NOT_ENTRY.getStatus());
                    activitySupplierEntry.setActivitySupplierType(ActivitySupplierType.DESIGNATED_SUPPLIER.getType());
                    activitySupplierEntry.setAuditStatus(Integer.valueOf(AuditStatus.UNAUDIT.getStatus()));
                    activitySupplierEntry.setCreateUser(activityInfo.getCreateUser());
                    activitySupplierEntryList.add(activitySupplierEntry);
                }
                int count = activitySupplierEntryDao.batchSaveActivitySupplierEntry(activitySupplierEntryList);
                if (count < 1) {
                    throw new ServiceException("系统错误,保存活动商家失败!");
                }
            }

        } catch (Exception e) {
            logger.error("新增活动出错:", e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteActivityById(Long activityId) throws ServiceException {
        try {
            activityChargeDao.deleteActivityChargeByActivityId(activityId);
            activityCategorysDao.deleteActivityCategorysByActivityId(activityId);
            activityBrandDao.deleteActivityBrandByActivityId(activityId);
            activitySupplierScoreDao.deleteActivitySupplierScoreByActivityId(activityId);
            activitySupplierEntryDao.deleteSupplierEntryByActivityId(activityId);
            if (activityInfoDao.deleteActivityById(activityId) < 1) {
                throw new ServiceException("系统错误,删除活动出错或者活动ID为：" + activityId + "的活动状态为不可删除状态!");
            }
        } catch (Exception e) {
            logger.error("终止活动出错:", e.getMessage(), e);
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void stopActivityById(Long activityId) throws ServiceException {

        try {
            if (activityInfoDao.stopActivityById(activityId) < 1) {
                throw new ServiceException("系统错误,终止活动出错或者活动ID为：" + activityId + "的活动类型为不可终止类型!");
            }
            //TO DO 调用促销接口等后续操作
        } catch (Exception e) {
            logger.error("查询活动列表出错:", e.getMessage(), e);
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void submitAuditActivityById(Long activityId) throws ServiceException {
        try {
            if (activityInfoDao.submitAuditActivityById(activityId) < 1) {
                throw new ServiceException("系统错误,活动提交审核出错或者活动ID为：" + activityId + "的活动状态为不可提交审核类型!");
            }
        } catch (Exception e) {
            logger.error("活动提交审核出错:", e.getMessage(), e);
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void cancelSubmitAuditActivityById(Long activityId) throws ServiceException {

        try {
            if (activityInfoDao.cancelSubmitAuditActivityById(activityId) < 1) {
                throw new ServiceException("系统错误,活动撤销提审出错或者活动ID为：" + activityId + "的活动状态为不可撤销提审类型!");
            }
        } catch (Exception e) {
            logger.error("活动撤销提审出错:", e.getMessage(), e);
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void cancelAuditActivityById(Long activityId) throws ServiceException {

        try {
            if (activityInfoDao.cancelAuditActivityById(activityId) < 1) {
                throw new ServiceException("系统错误,活动撤销审核出错或者活动ID为：" + activityId + "的活动状态为不可撤销审核类型!");
            }
        } catch (Exception e) {
            logger.error("活动撤销审核出错:", e.getMessage(), e);
            throw new ServiceException(e);
        }

    }

    @Override
    public ActivityInfo findActivityInfoById(Long activityId) throws ServiceException {
        ActivityInfo activityInfo = null;
        try {
            activityInfo = activityInfoDao.findActivityInfoById(activityId);
            if (activityInfo == null) {
                logger.error("根据id未查找到活动信息");
                throw new ServiceException("根据id未查找到活动信息");
            }

        } catch (Exception e) {
            logger.error("根据id查找到活动信息失败：", e);
            throw new ServiceException(e);
        }
        return activityInfo;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateActivityInfo(ActivityInfo activityInfo,
                                   List<Long> brandIdList, List<Long> categoryIdList,
                                   List<Long> supplierIdList) throws ServiceException {
        try {
            int count = activityInfoDao.updateActivityInfo(activityInfo);
            if (count < 1) {
                throw new ServiceException("系统错误,修改活动信息失败!");
            }

            Long activityId = activityInfo.getActivityId();
            activitySupplierScoreDao.deleteActivitySupplierScoreByActivityId(activityId);
            activityChargeDao.deleteActivityChargeByActivityId(activityId);
            activityBrandDao.deleteActivityBrandByActivityId(activityId);
            activityCategorysDao.deleteActivityCategorysByActivityId(activityId);
            activitySupplierEntryDao.deleteSupplierEntryByActivityId(activityId);

            ActivityCharge activityCharge = activityInfo.getActivityCharge();
            ActivitySupplierScore activitySupplierScore = activityInfo.getActivitySupplierScore();

            List<ActivityBrand> activityBrandList = new ArrayList<ActivityBrand>();//activityInfo.getActivityBrandList();
            List<ActivityCategorys> activityCategorysList = new ArrayList<ActivityCategorys>();//activityInfo.getActivityCategorysList();
            List<ActivitySupplierEntry> activitySupplierEntryList = new ArrayList<ActivitySupplierEntry>();//activityInfo.getActivitySupplierEntryList();

            //保存活动费用值
            if (null != activityCharge
                    && (null != activityCharge.getCommissionRate()
                    || null != activityCharge.getFixedCharge()
                    || null != activityCharge.getSingleCharge())) {
                activityCharge.setActivityId(activityId);
                activityChargeDao.saveActivityCharge(activityCharge);
            }
            //保存活动商家评分
            if (null != activitySupplierScore && null != activitySupplierScore.getGreatEqualPoint()) {
                activitySupplierScore.setActivityId(activityId);
                activitySupplierScoreDao.saveActivitySupplierScore(activitySupplierScore);
            }
            //保存活动品牌
            if (CollectionUtils.isNotEmpty(brandIdList)) {
                ActivityBrand activityBrand = null;
                for (Long brandId : brandIdList) {
                    activityBrand = new ActivityBrand();
                    activityBrand.setActivityId(activityId);
                    activityBrand.setBrandId(brandId);
                    activityBrandList.add(activityBrand);
                }
                count = activityBrandDao.batchSaveActivityBrand(activityBrandList);
                if (count < 1) {
                    throw new ServiceException("系统错误,修改活动品牌失败!");
                }
            }
            //保存活动类目
            if (CollectionUtils.isNotEmpty(categoryIdList)) {
                ActivityCategorys activityCategorys = null;
                for (Long categoryId : categoryIdList) {
                    activityCategorys = new ActivityCategorys();
                    activityCategorys.setActivityId(activityId);
                    activityCategorys.setCategoryId(categoryId);
                    activityCategorysList.add(activityCategorys);
                }
                count = activityCategorysDao.batchSaveActivityCategorys(activityCategorysList);
                if (count < 1) {
                    throw new ServiceException("系统错误,修改活动品牌失败!");
                }
            }
            //保存活动商家
            if (CollectionUtils.isNotEmpty(supplierIdList)) {
                ActivitySupplierEntry activitySupplierEntry = null;
                for (Long supplierId : supplierIdList) {
                    activitySupplierEntry = new ActivitySupplierEntry();
                    activitySupplierEntry.setActivityId(activityId);
                    activitySupplierEntry.setSupplierId(supplierId);
                    activitySupplierEntry.setEntryStatus(ActivityEntryStatus.NOT_ENTRY.getStatus());
                    activitySupplierEntry.setActivitySupplierType(ActivitySupplierType.DESIGNATED_SUPPLIER.getType());
                    activitySupplierEntry.setAuditStatus(Integer.valueOf(AuditStatus.UNAUDIT.getStatus()));
                    activitySupplierEntry.setCreateUser(activityInfo.getCreateUser());
                    activitySupplierEntryList.add(activitySupplierEntry);
                }
                count = activitySupplierEntryDao.batchSaveActivitySupplierEntry(activitySupplierEntryList);
                if (count < 1) {
                    throw new ServiceException("系统错误,修改活动商家失败!");
                }
            }

        } catch (Exception e) {
            logger.error("修改活动出错:", e.getMessage(), e);
            throw new ServiceException(e);
        }
    }
}
