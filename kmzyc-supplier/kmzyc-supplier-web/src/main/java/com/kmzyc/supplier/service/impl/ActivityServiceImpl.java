package com.kmzyc.supplier.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.supplier.dao.ActivityDAO;
import com.kmzyc.supplier.enums.ActivitySupplierStatus;
import com.kmzyc.supplier.enums.SupplierEntryStatus;
import com.kmzyc.supplier.maps.SupplierEntryStatusMap;
import com.kmzyc.supplier.service.ActivityPaymentService;
import com.kmzyc.supplier.service.ActivityService;
import com.kmzyc.supplier.service.ActivitySkuService;
import com.kmzyc.supplier.service.ActivitySupplierService;
import com.kmzyc.supplier.service.SupplierCategorysService;
import com.kmzyc.supplier.util.CodeUtils;
import com.kmzyc.supplier.util.FileUploadUtils;
import com.kmzyc.supplier.vo.ActivityInfoVo;
import com.kmzyc.supplier.vo.ActivitySkuVo;
import com.kmzyc.commons.exception.ServiceException;
import com.km.framework.fileupload.Upload;
import com.km.framework.page.Pagination;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.*;
import com.pltfm.app.util.TransactionTypeEnum;
import com.pltfm.app.vobject.*;
import com.kmzyc.user.remote.service.TrationListRemoteService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * 功能：活动服务实现
 *
 * @author Zhoujiwei
 * @since 2016/3/18 9:47
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    private Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Resource
    private ActivityDAO activityDAO;

    @Resource
    private JedisCluster jedisCluster;

    @Resource
    private SupplierCategorysService supplierCategorysService;

    @Resource
    private ActivitySupplierService activitySupplierService;

    @Resource
    private ActivityPaymentService activityPaymentService;

    @Resource
    private ActivitySkuService activitySkuService;

    @Resource
    private TrationListRemoteService trationListRemoteService;

    private static final String ACTIVITY_KEY = "activity_";

    private static final String ACTIVITY_SUPPLIER_MAX = "activity_supplier_max_";

    private static final String ACTIVITY_APPEND = "activity_append_";

    private static final int ACTIVITY_APPEND_TIMEOUT = 30 * 60;

    private static String[] ALLOW_TYPES = {".jpg", ".jpeg", ".png", ".JPG", ".JPEG", ".PNG", ".GIF", ".gif"};

    @Override
    public boolean isStop(Long activityId) throws ServiceException {
        try {
            return activityDAO.isStop(activityId);
        } catch (SQLException e) {
            logger.error("获取当前活动[{}]是否已经活动终止,错误信息：{}",
                    new Object[]{activityId, e});
            throw new ServiceException(e);
        }
    }

    @Override
    public Pagination getActivityList(ActivitySupplierStatus type, ActivityInfoVo activityParam,
                                      Long supplierId, Pagination page) throws ServiceException {
        Map<String, Object> objCondition = new HashMap<String, Object>();
        objCondition.put("supplierId", supplierId);

        if (StringUtils.isNotBlank(activityParam.getActivityName())) {
            objCondition.put("activityName", "%" + activityParam.getActivityName() + "%");
        }
        if (activityParam.getActivityId() != null) {
            objCondition.put("activityId", "%" + activityParam.getActivityId() + "%");
        }
        if (activityParam.getActivityType() != null) {
            objCondition.put("activityType", activityParam.getActivityType());
        }
        if (activityParam.getActivityStatus() != null) {
            handleActivityStatusCondition(objCondition, activityParam.getActivityStatus());
        }
        if (activityParam.getEntryStatus() != null) {
            handleEntryStatusCondition(objCondition, activityParam.getEntryStatus());
        }

        String listSql = "ACTIVITY_INFO.getAllActivityList";
        String countSql = "ACTIVITY_INFO.countAllActivityList";
        switch (type) {
            case ALL:
                objCondition.put("all", "true");
                break;
            case INVITED:
                //报名状态为未报名，且活动商家类型为制定商家或者邀请商家即非全部商家
                objCondition.put("invited", "true");
                break;
            case MY:
                //报名状态为已报名或撤销报名
                objCondition.put("my", "true");
                listSql = "ACTIVITY_INFO.getMyActivityList";
                countSql = "ACTIVITY_INFO.countMyActivityList";
                break;
            default:
                objCondition.put("all", "true");
        }
        page.setObjCondition(objCondition);

        try {
            activityDAO.getActivityList(page, listSql, countSql);
        } catch (Exception e) {
            logger.error("查询活动列表出错：查询类型{}，查询参数{}，错误信息：{}",
                    new Object[]{type, activityParam, e});
            throw new ServiceException(e);
        }
        return page;
    }

    /**
     * 处理报名状态
     *
     * @param objCondition
     * @param entryStatus
     */
    private void handleEntryStatusCondition(Map<String, Object> objCondition, Integer entryStatus) {
        //我已经参加的活动
        SupplierEntryStatus supplierEntryStatus = SupplierEntryStatusMap.getSupplierEntryStatus(entryStatus);
        switch (supplierEntryStatus) {
            case PASS:
                objCondition.put("supplierPass", true);
                break;
            case NOT_PAY:
                objCondition.put("supplierNotPay", true);
                break;
            case NOT_AUDIT:
                objCondition.put("supplierNotAudit", true);
                break;
            case NOT_PASS:
                objCondition.put("supplierNotPass", true);
                break;
            case ENTRY_FAIL:
                objCondition.put("supplierEntryFail", true);
                break;
            case REVOKE:
                objCondition.put("supplierEntryRevoke", true);
                break;
            case NOT_ENTRY:
            default:
                objCondition.put("entryStatus", entryStatus);
                break;
        }
    }

    /**
     * 处理活动状态
     *
     * @param objCondition   查询条件
     * @param activityStatus 状态
     */
    private void handleActivityStatusCondition(Map<String, Object> objCondition, Integer activityStatus) {
        switch (activityStatus) {
            case 2:
                objCondition.put("activityNotEntry", true);
                break;
            case 3:
                objCondition.put("activityEntryIn", true);
                break;
            case 4:
                objCondition.put("activityNotStart", true);
                break;
            case 5:
                objCondition.put("activityIn", true);
                break;
            case 6:
                objCondition.put("activityEnd", true);
                break;
            case 1:
            case 7:
            case 8:
            default:
                objCondition.put("activityStatus", activityStatus);
                break;
        }
    }

    @Override
    public ActivityInfo getActivityById(String activityId) throws ServiceException {
        if (StringUtils.isBlank(activityId)) {
            logger.error("查看活动详情失败，没有找到活动id。");
            throw new ServiceException("查看活动详情失败，没有找到活动id。");
        }

        String activityJson = jedisCluster.get(ACTIVITY_KEY + activityId);
        ActivityInfo activityInfo = null;
        if (StringUtils.isNotBlank(activityJson)) {
            activityInfo = JSON.parseObject(activityJson, ActivityInfo.class);
        }
        try {
            if (activityInfo == null) {
                activityInfo = activityDAO.getActivityById(Long.parseLong(activityId));
                //缓存
                int time = 30 * 24 * 60 * 60;
                jedisCluster.setex(ACTIVITY_KEY + activityId, time, JSON.toJSONString(activityInfo));
            }

            if (activityInfo == null) {
                logger.error("没有找到活动信息，活动id为{}", activityId);
                throw new ServiceException("没有找到活动信息，活动id为" + activityId);
            }
        } catch (SQLException e) {
            logger.error("获取活动信息失败,活动id={},错误信息{}:", new Object[]{activityId, e});
            throw new ServiceException(e);
        } catch (NumberFormatException e) {
            logger.error("获取活动信息失败,活动id={},错误信息{}:", new Object[]{activityId, e});
            throw new ServiceException(e);
        }
        return activityInfo;
    }

    @Override
    public ResultMessage checkAuthority(ActivityInfo activity, Long supplierId, String activityId, Boolean isUpdate)
            throws ServiceException {
        if (activity == null) {
            activity = getActivityById(activityId);
        }
        ResultMessage resultMessage = new ResultMessage();

        //1.获取活动与当前商家的常用数据
        ActivitySupplierEntry entry = transformParam2Entry(supplierId, activityId);
        entry = activitySupplierService.selectByExample(entry);
        //是否为邀请类型的商家
        boolean inviteSupplier     = Boolean.FALSE;
        //是否为指定类型的商家
        boolean designatedSupplier = Boolean.FALSE;
        //活动商家类型
        Integer activitySupplierType = 0;
        if (entry != null) {
            activitySupplierType = entry.getActivitySupplierType();
            inviteSupplier = activitySupplierType.intValue() == ActivityEntrySupplierType.INVITE_SUPPLIER.getType().intValue();
            designatedSupplier = activitySupplierType.intValue() == ActivityEntrySupplierType.DESIGNATED_SUPPLIER.getType().intValue();
            //已报名或者撤销报名---编辑时不校验
            if (!isUpdate){
                if (AuditStatus.NOT_THROUGH_AUDIT.getStatus().equals(entry.getAuditStatus().toString())) {
                    resultMessage.setIsSuccess(Boolean.FALSE);
                    resultMessage.setMessage("您的报名审核不通过，请到\"我已参加的活动\"中重新修改并提交！");
                    return resultMessage;
                } else if (entry.getEntryStatus().intValue() == ActivityEntryStatus.ALREADY_ENTRY.getStatus().intValue()) {
                    resultMessage.setIsSuccess(Boolean.FALSE);
                    resultMessage.setMessage("您已经报名了该活动，请到\"我已参加的活动\"中查看！");
                    return resultMessage;
                } else if (entry.getEntryStatus().intValue() == ActivityEntryStatus.CANCEL_ENTRY.getStatus().intValue()) {
                    resultMessage.setIsSuccess(Boolean.FALSE);
                    resultMessage.setMessage("您已经撤销了该活动报名，请到\"我已参加的活动\"中查看！");
                    return resultMessage;
                }
            }
        }

        //2.检查活动信息
        if (checkActivity(activity,inviteSupplier, resultMessage)) {
            return resultMessage;
        }

        //3.非邀请商家，根据活动的商家选择类型校验商家是否满足条件
        if (!inviteSupplier) {
            Integer supplierChoiceType = activity.getSupplierChoiceType();
            switch (supplierChoiceType) {
                case 4://指定商家
                    //不是指定
                    if (!designatedSupplier) {
                        resultMessage.setMessage("不在本次活动的参与商家范围内，无法报名！");
                        resultMessage.setIsSuccess(Boolean.FALSE);
                        return resultMessage;
                    }
                    break;
                case 3://按类目选择
                    if (checkActivityCategory(activity, supplierId, resultMessage)) {
                        return resultMessage;
                    }
                    break;
                case 2://按店铺评分
                    if(checkActivityScore(activity, supplierId, resultMessage)) {
                        return resultMessage;
                    }
                    break;
                case 1://全部商家
                    break;
                default://默认
                    resultMessage.setIsSuccess(Boolean.FALSE);
                    resultMessage.setMessage("未找到活动商家范围，报名失败！");
                    return resultMessage;
            }
        }

        resultMessage.setIsSuccess(Boolean.TRUE);
        return resultMessage;
    }

    /**
     * 检查活动相关信息
     *
     * @param activity          活动信息
     * @param inviteSupplier    是否为邀请商家
     * @param resultMessage     返回信息
     *
     * @return  true不符合条件/false数据ok
     */
    private boolean checkActivity(ActivityInfo activity, boolean inviteSupplier, ResultMessage resultMessage) {
        //1.判断活动报名是否开始
        Timestamp entryStartTime = activity.getEntryStartTime();
        Date date = new Date();
        if (date.compareTo(entryStartTime) < 0) {
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("报名未开始，请稍后再试！");
            return Boolean.TRUE;
        }

        //2.校验活动状态
        Integer activityStatus = activity.getActivityStatus();
        if (activityStatus.intValue() == ActivityStatus.ACTIVITY_STOP.getStatus().intValue()) {//活动终止
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("活动已中止，无法报名！");
            return Boolean.TRUE;
        } else if (activityStatus.intValue() == ActivityStatus.ACTIVITY_CANCELL.getStatus().intValue()) {//活动取消
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("活动已撤销，无法报名！");
            return Boolean.TRUE;
        } else if (activityStatus.intValue() == ActivityStatus.PRE_AUDIT.getStatus().intValue()) {
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("活动在待审核中，无法报名！");
            return Boolean.TRUE;
        } else if (activityStatus.intValue() == ActivityStatus.DRAFT.getStatus().intValue()) {
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("活动还未审核，无法报名！");
            return Boolean.TRUE;
        }

        //3.是否为邀请的商家
        if (inviteSupplier) {
            //3.1邀请状态下  邀请商家检查活动开始时间
            Timestamp activityStartTime = activity.getActivityStartTime();
            if (date.compareTo(activityStartTime) >= 0) {
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("活动进行中，无法报名！");
                return Boolean.TRUE;
            }

            //供后续不需要校验商家数额限定,mark为2表示为邀请商家
            resultMessage.setMark(2);
        } else {
            //3.2非邀请状态下   检查活动报名结束时间
            Timestamp entryEndTime = activity.getEntryEndTime();
            if (date.compareTo(entryEndTime) >= 0) {
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("报名已结束，无法报名！");
                return Boolean.TRUE;
            }
        }
        resultMessage.setIsSuccess(Boolean.TRUE);
        return Boolean.FALSE;
    }

    /**
     * 检查当前商家店铺评分是否大于活动所评分值
     *
     * @param activity      活动信息
     * @param supplierId    供应商id
     * @param resultMessage 返回信息
     * @return
     */
    private boolean checkActivityScore(ActivityInfo activity, Long supplierId, ResultMessage resultMessage)
            throws ServiceException{
        ActivitySupplierScore supplierScore = activity.getActivitySupplierScore();
        if (supplierScore == null) {
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("没有找到活动相关的评分，无法报名！");
            return Boolean.TRUE;
        }

        BigDecimal point = supplierScore.getGreatEqualPoint();
        if (activitySupplierService.getShopScore(supplierId) < point.doubleValue()) {
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("供应商店铺评分小于活动所选店铺评分【"+point.doubleValue()+"】!");
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 检查当前商家是否有此活动所包含的类目
     *
     * @param activity      活动信息
     * @param supplierId    供应商id
     * @param resultMessage 返回信息
     * @return
     */
    private boolean checkActivityCategory(ActivityInfo activity, Long supplierId,
                                          ResultMessage resultMessage) throws ServiceException {
        //活动相关的一级类目
        List<ActivityCategorys> activityCategoryList = activity.getActivityCategorysList();
        if (CollectionUtils.isEmpty(activityCategoryList)) {
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("没有找到活动相关的类目选择，无法报名！");
            return Boolean.TRUE;
        }

        //供应商一级类目
        List<String> categoryList = supplierCategorysService.findSupplierCategory(supplierId);
        if (CollectionUtils.isEmpty(categoryList)) {
            resultMessage.setMessage("没有找到供应商对应的类目集合，无法报名！");
            resultMessage.setIsSuccess(Boolean.FALSE);
            return Boolean.TRUE;
        }

        //含有活动相关的一级类目
        boolean haveCategory = Boolean.FALSE;
        for (ActivityCategorys category : activityCategoryList) {
            if (categoryList.contains(String.valueOf(category.getCategoryId()))) {
                haveCategory = Boolean.TRUE;
                break;
            }
        }
        if (!haveCategory) {
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("没有该活动所选类目的权限，无法报名！");
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public ResultMessage checkActivityAppend(ActivityInfo activity, String activityId)
            throws ServiceException {
        if (StringUtils.isBlank(activityId)) {
            logger.error("商家追加推广失败，没有找到活动id。");
            throw new ServiceException("商家追加推广失败，没有找到活动id。");
        }
        if (activity == null) {
            activity = getActivityById(activityId);
        }

        //1.活动必须是渠道推广，
        Integer activityType = activity.getActivityType();
        ResultMessage resultMessage = new ResultMessage();
        if (activityType.intValue() != ActivityType.CHANNL_TYPE.getType().intValue()) {
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("非渠道推广活动，不能追加推广费用！");
            return resultMessage;
        }

        //2.并且活动必须大于活动开始时间，小于活动结束时间
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp activityStartTime = activity.getActivityStartTime();
        Timestamp activityEndTime = activity.getActivityEndTime();
        if (now.compareTo(activityEndTime) > 0 || now.compareTo(activityStartTime) < 0) {
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("必须在活动进行中才能追加推广费用！");
            return resultMessage;
        }

        //3.校验活动是否已经终止
        if (isStop(Long.parseLong(activityId))) {
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("活动已中止，不能追加推广费用！");
            return resultMessage;
        }
        resultMessage.setIsSuccess(Boolean.TRUE);
        return resultMessage;
    }

    @Override
    public ResultMessage checkSku(ActivityInfo activity, String activityId,
                                  Long supplierId, List<ActivitySkuVo> newActivitySkuList, Boolean isUpdate,
                                  List<ActivitySkuVo> exitsActivitySkuList, ResultMessage resultMessage)
            throws ServiceException {
        if (StringUtils.isBlank(activityId)) {
            logger.error("查看活动失败，没有找到活动id。");
            throw new ServiceException("查看活动失败，没有找到活动id。");
        }
        if (activity == null) {
            activity = getActivityById(activityId);
        }

        //检查当前所参加活动的商品是否满足需求，
        resultMessage.setIsSuccess(Boolean.FALSE);
        //1.检查参加活动的sku数量上限
        Integer skuMaximum = activity.getSkuMaximum();
        //是否有sku
        boolean haveSku = Boolean.FALSE;
        //sku数量是否大于活动最大sku上限
        boolean haveOverSkuMax = Boolean.FALSE;
        if (isUpdate) {
            haveSku = CollectionUtils.isNotEmpty(newActivitySkuList) || CollectionUtils.isNotEmpty(exitsActivitySkuList);
            haveOverSkuMax = skuMaximum.intValue() != 0
                    && ((CollectionUtils.isNotEmpty(newActivitySkuList) ? newActivitySkuList.size() : 0)
                    + (CollectionUtils.isNotEmpty(exitsActivitySkuList) ? exitsActivitySkuList.size() : 0))
                    > skuMaximum.intValue();
        } else {
            haveSku = CollectionUtils.isNotEmpty(newActivitySkuList);
            haveOverSkuMax = skuMaximum.intValue() != 0  && haveSku && newActivitySkuList.size() > skuMaximum.intValue();
        }
        if (!haveSku) {//没有商品
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("未选择商品报名活动，报名失败！");
            return resultMessage;
        } else if (haveOverSkuMax) {//商品数量大于sku上限数量
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("参加活动的商品数量超过上限" + skuMaximum.intValue() + "，报名失败！");
            return resultMessage;
        }
        //2.品牌要求 暂时不校验，所勾选的sku前提就是符合品牌要求的。

        List<ActivitySkuVo> skuList = new ArrayList<ActivitySkuVo>();
        //4.校验sku
        //4.1校验新增的sku
        if (CollectionUtils.isNotEmpty(newActivitySkuList)) {
            skuList.addAll(newActivitySkuList);
            checkSkuAndHandlerPrice(supplierId, activity, newActivitySkuList, Boolean.TRUE, isUpdate, resultMessage);
            if (!resultMessage.getIsSuccess()) {
                return resultMessage;
            }
        }
        //4.2校验之前的sku
        if (CollectionUtils.isNotEmpty(exitsActivitySkuList)) {
            skuList.addAll(exitsActivitySkuList);
            checkSkuAndHandlerPrice(supplierId, activity, exitsActivitySkuList, Boolean.FALSE, isUpdate, resultMessage);
            if (!resultMessage.getIsSuccess()) {
                return resultMessage;
            }
        }

        //5固定收费时再计算出最大skuId的
        if (activity.getChargeType().intValue() == ActivityChargeType.FIXED_CHARGES.getType().intValue()) {
            computeFixMoney(newActivitySkuList, exitsActivitySkuList, activity.getActivityCharge());
        }

        //3.实际库存为0
        for (ActivitySkuVo skuVo : skuList) {
            if (skuVo.getStock() == null || skuVo.getStock() <= 0) {
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("商品库存不能为0！");
                return resultMessage;
            }
        }

        resultMessage.setIsSuccess(Boolean.TRUE);
        return resultMessage;
    }

    /**
     * 计算固定收费时，最大skuId的费用为fix
     *
     * @param newActivitySkuList
     * @param exitsActivitySkuList
     * @param activityCharge
     */
    private void computeFixMoney(List<ActivitySkuVo> newActivitySkuList,
                                 List<ActivitySkuVo> exitsActivitySkuList, ActivityCharge activityCharge) {
        //1.先将新增的和修改的全部合并
        List<ActivitySkuVo> activitySkuList = new ArrayList<ActivitySkuVo>();
        if (CollectionUtils.isNotEmpty(exitsActivitySkuList)) {
            activitySkuList.addAll(exitsActivitySkuList);
        }
        if (CollectionUtils.isNotEmpty(newActivitySkuList)) {
            activitySkuList.addAll(newActivitySkuList);
        }

        //2.如果都为空，直接返回
        if (CollectionUtils.isEmpty(activitySkuList)) {
            return;
        }

        //3.找出skuId最大的数据，没有则返回
        Long maxSkuId = 0l;
        for (ActivitySkuVo activitySkuVo : activitySkuList) {
            Long skuId = activitySkuVo.getProductSkuId();
            if (skuId > maxSkuId) {
                maxSkuId = skuId;
            }
        }

        //4.循环新增的，如果最大skuId存在，并设置其费用为固定费用
        if (CollectionUtils.isNotEmpty(newActivitySkuList)) {
            for (ActivitySkuVo activitySkuVo : newActivitySkuList) {
                Long skuId = activitySkuVo.getProductSkuId();
                if (skuId.longValue() == maxSkuId.longValue()) {
                    activitySkuVo.setSkuTotalPrice(activityCharge.getFixedCharge());
                } else {
                    activitySkuVo.setSkuTotalPrice(new BigDecimal("0.00"));
                }
            }
        }

        //5.循环修改的，如果最大skuId存在，并设置其费用为固定费用
        if (CollectionUtils.isNotEmpty(exitsActivitySkuList)) {
            for (ActivitySkuVo activitySkuVo : exitsActivitySkuList) {
                Long skuId = activitySkuVo.getProductSkuId();
                if (skuId.longValue() == maxSkuId.longValue()) {
                    activitySkuVo.setSkuTotalPrice(activityCharge.getFixedCharge());
                } else {
                    activitySkuVo.setSkuTotalPrice(new BigDecimal("0.00"));
                }
            }
        }
    }

    /**
     * 检查sku并处理sku对应的费用
     *
     *
     * @param supplierId      供应商id
     * @param activity        活动信息
     * @param activitySkuList sku信息
     * @param isNew           是否为新增
     * @return
     */
    private void checkSkuAndHandlerPrice(Long supplierId, ActivityInfo activity, List<ActivitySkuVo> activitySkuList,
                                         Boolean isNew, Boolean isUpdate, ResultMessage resultMessage)
            throws ServiceException{
        resultMessage.setIsSuccess(Boolean.FALSE);
        int skuSize = activitySkuList.size();
        Integer activityType = activity.getActivityType();
        ActivityCharge activityCharge = activity.getActivityCharge();
        List<Long> skuIdList = new ArrayList<Long>();
        //新增的sku的图片地址集合
        List<String> newFilePathList = new ArrayList<String>();
        //修改过的sku的图片地址集合
        List<String> exitsFilePathList = new ArrayList<String>();

        for (int index = 0; index < skuSize; index++) {
            ActivitySkuVo activitySkuVo = activitySkuList.get(index);
            computeMoney(activitySkuVo, activity.getChargeType(), activityCharge, index);

            //根据类型先判断
            switch (activityType) {
                case 1://促销
                    //促销id不能为空
                    /*if (activitySkuVo.getPromotionId() == null) {
                        resultMessage.setIsSuccess(Boolean.FALSE);
                        resultMessage.setMessage("促销ID不能为空，报名失败！");
                        return;
                    }*/
                    break;
                case 2://图文
                    File file = activitySkuVo.getActivitySkuImageFile();
                    String fileName = activitySkuVo.getActivitySkuImageFileName();
                    if (file == null && StringUtils.isEmpty(fileName)) {
                        //如果是修改以前的，并且没有新增图片,清空地址链接并直接跳出
                        if (!isNew) {
                            activitySkuVo.setActivitySkuImage(null);
                        }
                        break;
                        /*resultMessage.setIsSuccess(Boolean.FALSE);
                        resultMessage.setMessage("图文活动必须上传图片，报名失败！");
                        return;*/
                    }
                    // 文件后缀名
                    int i = StringUtils.lastIndexOf(fileName, '.');
                    if (i < 0) {
                        resultMessage.setIsSuccess(Boolean.FALSE);
                        resultMessage.setMessage("图文活动必须上传小于1M的jpg、png、gif格式的图片，报名失败！");
                        return;
                    }
                    // 获取文件后缀名
                    String extFileName = StringUtils.substring(fileName, i);
                    if (!Upload.isValid(extFileName, ALLOW_TYPES)) {
                        resultMessage.setMessage("图文活动必须上传小于1M的jpg、png、gif格式的图片，报名失败！");
                        resultMessage.setIsSuccess(Boolean.FALSE);
                        return;
                    }
                    //图片不能大于1M
                    if (((double) file.length() / 1024 / 1024) > 1d) {
                        resultMessage.setIsSuccess(Boolean.FALSE);
                        resultMessage.setMessage("图文活动必须上传小于1M的jpg、png、gif格式的图片，报名失败！");
                        return;
                    }

                    //logo图片路径设置
                    String filename = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")
                            + "_" + new Random().nextInt(1000) + extFileName.toLowerCase(Locale.US);
                    //保存在服务器上的路径
                    String savePath = FileUploadUtils.createSavePath("activity");
                    // 相对路径，预览时使用,数据库保存
                    String relativePath = savePath.substring(ConfigurationUtil.getString("pictureUploadPath").length());
                    File destFile = new File(savePath, filename);
                    try {
                        FileUtils.copyFile(file, destFile);
                    } catch (IOException e) {
                        logger.error("图片上传失败，失败信息：{}，错误信息:{}",
                                new Object[]{activitySkuVo, e});
                        throw new ServiceException(e);
                    }
                    newFilePathList.add(savePath + filename);
                    if (!isNew) {
                        exitsFilePathList.add(ConfigurationUtil.getString("pictureUploadPath")
                                + activitySkuVo.getActivitySkuImage());
                    }
                    activitySkuVo.setActivitySkuImage(relativePath + filename);
                    // 删除临时文件
                    file.delete();
                    break;
                case 3://渠道
                    if (activityCharge == null) {
                        resultMessage.setIsSuccess(Boolean.FALSE);
                        resultMessage.setMessage("活动数据异常，没有找到此活动的推广佣金比例，报名失败！");
                        return;
                    }

                    BigDecimal activityCommissionRate = activityCharge.getCommissionRate();
                    BigDecimal commissionRate = activitySkuVo.getCommissionRate();
                    Integer preSaleQuantity = activitySkuVo.getPreSaleQuantity();
                    Integer stock = activitySkuVo.getStock();
                    if (stock < 100) {//库存必须大于100
                        resultMessage.setIsSuccess(Boolean.FALSE);
                        resultMessage.setMessage("参与活动的商品库存数必须大于等于100，报名失败！");
                        return;
                    } else if (preSaleQuantity.intValue() > stock.intValue()
                            || preSaleQuantity.intValue() < 100 ) {//预售数量必须大于等于100的整数
                        resultMessage.setIsSuccess(Boolean.FALSE);
                        resultMessage.setMessage("预销数量输入错误！预销数量必须是≥100且≤库存数的整数！报名失败！");
                        return;
                    } else if (activityCommissionRate.subtract(commissionRate).longValue() > 0) {
                        resultMessage.setIsSuccess(Boolean.FALSE);
                        resultMessage.setMessage("佣金比例必须大于活动的推广比例，报名失败！");
                        return;
                    }
                    skuIdList.add(activitySkuVo.getProductSkuId());
                    break;
                default:
                    resultMessage.setIsSuccess(Boolean.FALSE);
                    resultMessage.setMessage("活动类型异常，报名失败！");
                    return;
            }
        }

        //校验当前sku是否有参加其他渠道推广活动,一个sku只能报名参加一个未结束渠道推广活动
        if (activityType == ActivityType.CHANNL_TYPE.getType().intValue()) {
            List<String> unfinishedSkuList = activitySkuService.getSkuInUnfinishedActivity(skuIdList,
                    supplierId,activity.getActivityId());
            if (CollectionUtils.isNotEmpty(unfinishedSkuList)) {
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("当前活动中的商品【" + StringUtils.join(unfinishedSkuList, ",")
                        + "】已报名参加其他渠道推广活动，且此活动还未结束，报名失败！");
                return;
            }
        }

        //图文推广时，将下面的路径保存好，供后续活动报名不成功时，删除掉
        if (activityType.intValue() == ActivityType.GRAPHIC_TYPE.getType().intValue()) {
            //将之前的sku图片路径放置新的message中
            Object object = resultMessage.getObject();
            if (object != null && object instanceof List) {
                List fileList = (List) object;
                newFilePathList.addAll(fileList);
            }
            resultMessage.setObject(newFilePathList);
            resultMessage.setObject2(exitsFilePathList);
        }

        resultMessage.setIsSuccess(Boolean.TRUE);
    }

    /**
     * 计算单品sku费用
     *
     * @param activitySkuVo  单个sku信息
     * @param chargeType     收费类型
     * @param activityCharge 收费数据
     * @param index          sku下标
     */
    private void computeMoney(ActivitySkuVo activitySkuVo, Integer chargeType,
                              ActivityCharge activityCharge, int index) {
        if (chargeType.intValue() == ActivityChargeType.FREE.getType().intValue()) {//免费
            activitySkuVo.setSkuTotalPrice(new BigDecimal("0.00"));
        } else if (chargeType.intValue() == ActivityChargeType.FIXED_CHARGES.getType().intValue()) {//固定收费
            if (index == 0) {//第一个数据才有效，其他为0
                activitySkuVo.setSkuTotalPrice(activityCharge.getFixedCharge());
            } else {
                activitySkuVo.setSkuTotalPrice(new BigDecimal("0.00"));
            }
        } else if (chargeType.intValue() == ActivityChargeType.SINGLE_CHARGES.getType().intValue()) {//单个sku收费
            activitySkuVo.setSkuTotalPrice(activityCharge.getSingleCharge());
        } else if (chargeType.intValue() == ActivityChargeType.RABATE.getType().intValue()) {//佣金比例收费
            //计算价格
            activitySkuVo.setSkuTotalPrice(activitySkuService.computeChannelMoney(activitySkuVo.getPreSaleQuantity(),
                    activitySkuVo.getActivityPrice(), activitySkuVo.getCommissionRate()));
        }
    }

    @Override
    public ResultMessage checkSupplier(String activityId, boolean isCheck,
                                       ResultMessage resultMessage) throws ServiceException {
        resultMessage.setIsSuccess(Boolean.FALSE);
        String supplierKey = ACTIVITY_SUPPLIER_MAX + activityId;

        try {
            //检验供应商数量
            if (jedisCluster.exists(supplierKey)) {
                if (Integer.parseInt(jedisCluster.get(supplierKey)) <= 0) {
                    resultMessage.setIsSuccess(Boolean.FALSE);
                    resultMessage.setMessage("参加活动的供应商已达到上限，无法报名！");
                    return resultMessage;
                } else if (!isCheck) {//非检查状态（即新增时），数量减一
                    jedisCluster.decr(supplierKey);
                    //标记已经对供应商数量减一
                    resultMessage.setMark(0);
                }
            }
        } catch (Exception e) {
            logger.error("检查供应商数量和sku数量上限失败，活动id:{},检查状态:{},错误信息：{}",
                    new Object[]{activityId, isCheck, e});
            if (resultMessage.getMark() == 0) {
                jedisCluster.incr(supplierKey);
            }
            throw new ServiceException(e);
        }

        resultMessage.setIsSuccess(Boolean.TRUE);
        return resultMessage;
    }

    @Override
    public void incrSupplierMax(String activityId) {
        String supplierKey = ACTIVITY_SUPPLIER_MAX + activityId;
        if (jedisCluster.exists(supplierKey)) {
            jedisCluster.incr(supplierKey);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void cancelActivityEntry(String activityId, Long supplierId,
                                    Long loginUserId, String loginUserName) throws ServiceException {
        if (StringUtils.isBlank(activityId)) {
            logger.error("撤销报名活动失败，没有找到活动id。");
            throw new ServiceException("撤销报名活动失败，没有找到活动id。");
        }

        try {
            //1.先获取当前活动报名商家id
            Long entryId = activitySupplierService.getSupplierEntryId(supplierId, activityId, Boolean.FALSE);

            //2.撤销报名情况
            activitySupplierService.cancelActivity(entryId, loginUserId);

            //3.撤销退款情况
            activityPaymentService.cancelActivityPayment(activityId, entryId, supplierId, loginUserId, loginUserName);

            //4.查询当前撤销的报名是否为邀请
            //非邀请下撤销报名，活动商家上限加1
            if (!activitySupplierService.isInvited(supplierId, activityId)) {
                incrSupplierMax(activityId);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ResultMessage checkMatchCancel(ActivityInfo activity, String activityId, Long supplierId) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        if (StringUtils.isBlank(activityId)) {
            logger.error("检查撤销报名权限失败，没有找到活动id。供应商id:{}", supplierId);
            resultMessage.setMessage("没有找到活动id，请重新撤销！");
            return resultMessage;
        }

        try {
            if (activity == null) {
                activity = getActivityById(activityId);
            }

            //1.活动已经开始
            Timestamp activityStartTime = activity.getActivityStartTime();
            Timestamp nowTime = new Timestamp(System.currentTimeMillis());
            if (nowTime.getTime() > activityStartTime.getTime()) {
                logger.error("检查撤销报名权限失败，活动已开始，活动id：{}，商家id:{}。",
                        new Object[]{activityId, supplierId});
                resultMessage.setMessage("活动已开始，无法撤销！");
                return resultMessage;

            }

            //2.并且已经报名且待审核的报名才可以撤销
            Map<String, Long> statusMap = activitySupplierService.getActivitySupplierEntryStatus(supplierId, activityId);
            if (MapUtils.isEmpty(statusMap)) {
                logger.error("检查撤销报名权限失败，没有找到报名活动的信息，活动id：{}，商家id:{}。",
                        new Object[]{activityId, supplierId});
                resultMessage.setMessage("没有找到报名活动的信息，无法撤销！");
                return resultMessage;
            }

            Long entryStatus = statusMap.get("entryStatus");
            Long auditStatus = statusMap.get("auditStatus");
            //非报名
            if (entryStatus.intValue() != ActivityEntryStatus.ALREADY_ENTRY.getStatus().intValue()) {
                logger.error("报名状态为非报名状态，活动id：{}，商家id:{},报名状态信息：{}。",
                        new Object[]{activityId, supplierId, statusMap});
                resultMessage.setMessage("参加活动为非报名状态，无法撤销！");
                return resultMessage;
                //非未审核
            } else if (auditStatus.intValue() != Integer.parseInt(AuditStatus.UNAUDIT.getStatus())) {
                logger.error("活动报名情况已经经过审核，活动id：{}，商家id:{}, 报名状态信息：{}。",
                        new Object[]{activityId, supplierId, statusMap});
                resultMessage.setMessage("活动报名已经过审核，无法撤销！");
                return resultMessage;
            }
        } catch (Exception e) {
            logger.error("活动报名情况校验失败，活动id：{}，商家id:{}, 错误信息：{}。",
                    new Object[]{activityId, supplierId, e});
            resultMessage.setMessage("检查活动报名状态失败，无法撤销！");
            return resultMessage;
        }

        resultMessage.setIsSuccess(Boolean.TRUE);
        return resultMessage;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage pay(String activityId, String loginUserName, Long supplierId) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        try {
            //1.查询活动费用
            BigDecimal totalPrice = activitySupplierService.getActivityTotalPrice(supplierId, activityId);
            if (totalPrice == null || totalPrice.doubleValue() == 0d) {
                resultMessage.setMessage("缴费失败，当前活动报名没有找到报名费用！");
                return resultMessage;
            }

            //2.调用付款接口
            if (trationListRemoteService == null) {
                logger.error("供应商[{}],用户名[{}],活动[{}]缴费失败，远程接口获取不到！",
                        new Object[]{supplierId, loginUserName, activityId});
                throw new ServiceException("付款失败,远程接口获取不到。");
            }

            String orderCode = activityId + "_" + new Random().nextInt(1000);
            Integer resultCode = trationListRemoteService.orderTrationAccout(loginUserName,
                    -totalPrice.doubleValue(),
                    orderCode,
                    "活动缴费,活动ID："+ activityId,
                    TransactionTypeEnum.TRANSACTION_TYPE_PROMOTION_PAY.getType());
            logger.info("首次缴费，商家用户名{}，活动{},费用{}，返回状态{}.",
                    new Object[]{loginUserName, activityId, totalPrice, resultCode});

            //3.将商家的首次付款有效信息改为已付款
            //交易成功
            if (resultCode == 1) {
                activityPaymentService.pay(activityId, supplierId);
            } else if (resultCode == 3) {
                resultMessage.setMessage("缴费失败，账户余额不足！");
                resultMessage.setMark(2);//2标记缴费失败，余额不足。
                return resultMessage;
            } else {
                resultMessage.setMessage("缴费失败，请重试！");
                return resultMessage;
            }
        } catch (Exception e) {
            logger.error("付款失败，商家{}参加活动{},错误信息：{}",
                    new Object[]{supplierId, activityId, e});
            throw new ServiceException(e);
        }
        resultMessage.setIsSuccess(Boolean.TRUE);
        return resultMessage;
    }

    @Override
    public ResultMessage saveAppendSku(String activityId, Long supplierId, Long loginUserId,
                                       List<ActivitySkuVo> activitySkuList) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        try {
            if (CollectionUtils.isNotEmpty(activitySkuList)) {
                Timestamp now = new Timestamp(System.currentTimeMillis());
                for (ActivitySkuVo sku : activitySkuList) {
                    sku.setCreateUser(loginUserId);
                    sku.setModifUser(loginUserId);
                    sku.setCreateTime(now);
                    sku.setModifyTime(now);
                }
            }

            String setex = jedisCluster.setex(ACTIVITY_APPEND + activityId + "_" + supplierId,
                    ACTIVITY_APPEND_TIMEOUT,
                    JSON.toJSONString(activitySkuList));

            if (!"OK".equals(setex)) {
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("SKU追加不成功，请重新追加！");
                return resultMessage;
            }
        } catch (Exception e) {
            logger.error("追加推广失败，失败信息：", e);
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("追加推广失败！");
            return resultMessage;
        }

        resultMessage.setIsSuccess(Boolean.TRUE);
        return resultMessage;
    }

    @Override
    public ResultMessage checkAndHandlerAppendSku(List<ActivitySkuVo> activitySkuList, String activityId,
                                                  Long loginUserId) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        if (CollectionUtils.isEmpty(activitySkuList)) {
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("没有找到需要追加的sku列表，追加失败！");
            return resultMessage;
        }

        //校验库存，并计算单个sku费用
        Timestamp now = new Timestamp(System.currentTimeMillis());
        for (ActivitySkuVo activitySkuVo : activitySkuList) {
            Integer preSaleQuantity = activitySkuVo.getPreSaleQuantity();
            Integer stock = activitySkuVo.getStock();
            if (stock < 100) {//库存必须大于100
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("参与活动的商品库存数必须大于100，追加失败！");
                return resultMessage;
            }else if (preSaleQuantity.intValue() > stock.intValue()
                    || preSaleQuantity.intValue() < 100 ) {//追加数量必须是≥100且≤库存数
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("追加数量输入错误！追加数量必须是≥100且≤库存数的整数！追加失败！");
                return resultMessage;
            }
            BigDecimal skuTotalPrice = activitySkuService.computeChannelMoney(preSaleQuantity,
                    activitySkuVo.getActivityPrice(), activitySkuVo.getCommissionRate());
            activitySkuVo.setSkuTotalPrice(skuTotalPrice);
            activitySkuVo.setActivityId(Long.parseLong(activityId));
            activitySkuVo.setActivitySkuType(2);//追加提交
            activitySkuVo.setCreateUser(loginUserId);
            activitySkuVo.setModifUser(loginUserId);
            activitySkuVo.setCreateTime(now);
            activitySkuVo.setModifyTime(now);
        }
        resultMessage.setIsSuccess(Boolean.TRUE);
        return resultMessage;
    }

    @Override
    public BigDecimal getAppendTotalPrice(Long supplierId, String activityId) throws ServiceException {
        BigDecimal total = BigDecimal.ZERO;
        List<ActivitySkuVo> appendSkuList = getAppendSkuList(supplierId, activityId);
        try {
            if (CollectionUtils.isEmpty(appendSkuList)) {
                logger.error("没有找到当前商家{},活动{}的追加推广。",
                        new Object[]{supplierId, activityId});
                throw new ServiceException("没有找到当前商家{" + supplierId + "},活动{" + activityId + "}的追加推广。");
            }

            for (ActivitySkuVo skuVo : appendSkuList) {
                total = total.add(skuVo.getSkuTotalPrice());
            }
        } catch (Exception e) {
            logger.error("追加推广失败，失败信息：", e);
            throw new ServiceException(e);
        }

        return total;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage payAppend(String activityId, String loginUserName,
                                   Long supplierId, Long loginUserId) throws ServiceException {
        ResultMessage resultMessage = null;
        try {
            resultMessage = checkActivityAppend(getActivityById(activityId), activityId);
            if (!resultMessage.getIsSuccess()) {
                return resultMessage;
            }
            //1.查询活动费用
            BigDecimal totalPrice = getAppendTotalPrice(supplierId, activityId);

            //2.修改商家报名总费用
            activitySupplierService.appendPrice(totalPrice, supplierId, loginUserId, activityId);

            //3.增加追加推广的sku数据
            //追加推广的sku
            List<ActivitySkuVo> appendSkuList = getAppendSkuList(supplierId, activityId);
            //商家报名id
            Long entryId = activitySupplierService.getSupplierEntryId(supplierId, activityId, Boolean.FALSE);
            activitySkuService.saveAppendSku(appendSkuList, entryId);

            //5.更新预售数量缓存
            activitySupplierService.updateSkuStockQuantityCache(entryId, appendSkuList, null, Boolean.TRUE);

            //6.补充商家付款payment相关数据--payment/payment_details
            String paymentCode = CodeUtils.getActivityPaymentCode(Boolean.FALSE);
            activityPaymentService.saveAppend(totalPrice, appendSkuList, loginUserId,
                    activityId, supplierId, entryId, paymentCode);

            //7.调用付款接口
            //接口校验
            if (trationListRemoteService == null) {
                logger.error("供应商[{}],用户名[{}],活动[{}]缴费失败，接口为空！",
                        new Object[]{supplierId, loginUserName, activityId});
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("缴费失败，系统不稳定请稍后再试！");
                //手动回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultMessage;
            }
            //返回结果
            Integer resultCode = trationListRemoteService.orderTrationAccout(loginUserName,
                    -totalPrice.doubleValue(),
                    paymentCode,
                    "活动追加推广缴费,活动ID："+ activityId,
                    TransactionTypeEnum.TRANSACTION_TYPE_PROMOTION_PAY.getType());
            logger.info("加推广缴费，商家用户名{}，活动{},费用{}，返回状态{}.",
                    new Object[]{loginUserName, activityId, totalPrice, resultCode});
            //交易成功
            if (resultCode == 3) {
                logger.error("供应商[{}],用户名[{}],活动[{}]缴费失败，账户余额不足！，resultCode[{}]。",
                        new Object[]{supplierId, loginUserName, activityId, resultCode});
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMark(2);//2标记缴费失败，余额不足。
                resultMessage.setMessage("缴费失败，账户余额不足！");
                //手动回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultMessage;
            } else if (resultCode != 1) {
                logger.error("缴费失败，resultCode{}。", new Object[]{resultCode});
                throw new ServiceException("缴费失败，请重试！");
            }

            //8.清除追加缓存
            deleteAppendCache(supplierId, activityId);
        } catch (Exception e) {
            logger.error("付款失败，商家{}参加活动{},错误信息：{}",
                    new Object[]{supplierId, activityId, e});
            throw new ServiceException(e);
        }

        resultMessage.setIsSuccess(Boolean.TRUE);
        return resultMessage;
    }

    private List<ActivitySkuVo> getAppendSkuList(Long supplierId, String activityId) throws ServiceException {
        try {
            String key = ACTIVITY_APPEND + activityId + "_" + supplierId;
            if (!jedisCluster.exists(key)) {
                logger.error("没有找到当前商家{},活动{}的追加推广。",
                        new Object[]{supplierId, activityId});
                throw new ServiceException("没有找到当前商家{" + supplierId + "},活动{" + activityId + "}的追加推广。");
            }
            String json = jedisCluster.get(key);
            return JSONObject.parseArray(json, ActivitySkuVo.class);

        } catch (Exception e) {
            logger.error("追加推广失败，失败信息：", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 删除追加推广缓存
     *
     * @param supplierId
     * @param activityId
     */
    private void deleteAppendCache(Long supplierId, String activityId) {
        String key = ACTIVITY_APPEND + activityId + "_" + supplierId;
        try {
            jedisCluster.del(key);
        } catch (Exception e) {
            logger.error("删除追加推广缓存失败，失败信息：", e);
            //throw new ServiceException(e);
        }
    }

    /**
     * 切换成供应商报名实体
     *
     * @param supplierId 供应商id
     * @param activityId 活动id
     * @return
     */
    private ActivitySupplierEntry transformParam2Entry(Long supplierId, String activityId) {
        ActivitySupplierEntry entry = new ActivitySupplierEntry();
        entry.setSupplierId(supplierId);
        entry.setActivityId(Long.parseLong(activityId));
        return entry;
    }

    @Override
    public String bnesAcctTransactionById(Integer accountTransactionId) throws ServiceException {
        try {
            return activityDAO.bnesAcctTransactionById(accountTransactionId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
