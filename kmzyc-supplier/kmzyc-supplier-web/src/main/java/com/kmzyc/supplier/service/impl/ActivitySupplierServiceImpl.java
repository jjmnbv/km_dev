package com.kmzyc.supplier.service.impl;

import com.alibaba.fastjson.JSON;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.dao.ActivitySupplierDAO;
import com.kmzyc.supplier.service.ActivityPaymentDetailService;
import com.kmzyc.supplier.service.ActivityPaymentService;
import com.kmzyc.supplier.service.ActivitySkuService;
import com.kmzyc.supplier.service.ActivitySupplierService;
import com.kmzyc.supplier.vo.ActivitySkuVo;
import com.pltfm.app.enums.ActivityChargeType;
import com.pltfm.app.enums.ActivityEntryStatus;
import com.pltfm.app.enums.ActivityEntrySupplierType;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivitySupplierEntry;
import com.pltfm.app.vobject.ActivitySupplierEntryExample;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：供应商参加活动服务实现
 *
 * @author Zhoujiwei
 * @since 2016/3/18 9:48
 */
@Service("activitySupplierService")
public class ActivitySupplierServiceImpl implements ActivitySupplierService {

    private Logger logger = LoggerFactory.getLogger(ActivitySupplierServiceImpl.class);

    @Resource
    private ActivitySupplierDAO activitySupplierDAO;

    @Resource
    private ActivitySkuService activitySkuService;

    @Resource
    private ActivityPaymentService activityPaymentService;

    @Resource
    private ActivityPaymentDetailService activityPaymentDetailService;

    @Resource
    private JedisCluster jedisCluster;

    private static final String ACTIVITY_SKU_QUANTITY_KEY = "activity_sku_quantity_";

    @Override
    public void updateSkuStockQuantityCache(Long supplierEntryId, List<ActivitySkuVo> newActivitySkuList,
                                            List<ActivitySkuVo> exitsActivitySkuList, Boolean needAppend) {
        String key = ACTIVITY_SKU_QUANTITY_KEY + supplierEntryId;
        List<ActivitySkuVo> skuList = new ArrayList<ActivitySkuVo>();
        if (CollectionUtils.isNotEmpty(newActivitySkuList)) {
            skuList.addAll(newActivitySkuList);
        }
        if (CollectionUtils.isNotEmpty(exitsActivitySkuList)) {
            skuList.addAll(exitsActivitySkuList);
        }
        if (CollectionUtils.isEmpty(skuList)) {
            logger.error("更新sku预售数量缓存失败，没有找到sku信息。");
            return;
        }

        for (ActivitySkuVo skuVo : skuList) {
            Integer preSaleQuantity = skuVo.getPreSaleQuantity();
            if (preSaleQuantity == null) {
                logger.error("更新sku预售数量缓存失败，没有找到sku信息预售信息。");
                return;
            }

            Long skuId = skuVo.getProductSkuId();
            if (needAppend) {
                jedisCluster.incrBy(key + "_" + skuId, preSaleQuantity);
            } else {
                jedisCluster.set(key + "_" + skuId, preSaleQuantity.toString());
            }
        }
    }

    public ActivitySupplierEntry selectByExample(ActivitySupplierEntry entry) throws ServiceException {
        if (entry == null) {
            logger.error("没有找到商家报名查询参数信息。");
            throw new ServiceException("没有找到商家报名查询参数信息。");
        }

        ActivitySupplierEntryExample example = new ActivitySupplierEntryExample();
        ActivitySupplierEntryExample.Criteria criteria = example.createCriteria();
        if (entry.getActivityId() != null) {
            criteria.andActivityIdEqualTo(entry.getActivityId());
        }
        if (entry.getSupplierId() != null) {
            criteria.andSupplierIdEqualTo(entry.getSupplierId());
        }
        try {
            return activitySupplierDAO.selectByExample(example);
        } catch (SQLException e) {
            logger.error("查询当前商家报名情况失败，查询参数：{}，错误信息：{}，",
                    new Object[]{entry, e});
            throw new ServiceException(e);
        }
    }

    @Override
    public Long getSupplierEntryId(Long supplierId, String activityId, Boolean isInvited) throws ServiceException {
        if (StringUtils.isBlank(activityId)) {
            logger.error("查看商家是否报名活动失败，没有找到活动id。");
            throw new ServiceException("查看商家是否报名活动失败，没有找到活动id。");
        }

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("supplierId", supplierId);
            paramMap.put("activityId", activityId);
            if (isInvited) {
                paramMap.put("isInvited", isInvited);
            }
            return activitySupplierDAO.getSupplierEntryId(paramMap);
        } catch (SQLException e) {
            logger.error("查询当前商家{}报名此次活动{}的报名状态，失败{}，",
                    new Object[]{supplierId, activityId, e});
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isInvited(Long supplierId, String activityId) throws ServiceException {
        if (StringUtils.isBlank(activityId)) {
            logger.error("判断商家是否为邀请报名类型失败，没有找到活动id。");
            throw new ServiceException("判断商家是否为邀请报名类型失败，没有找到活动id。");
        }

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("supplierId", supplierId);
            paramMap.put("activityId", activityId);
            Integer supplierType = activitySupplierDAO.getActivitySupplierType(paramMap);
            return supplierType != null
                    && supplierType.intValue() == ActivityEntrySupplierType.INVITE_SUPPLIER.getType().intValue();
        } catch (SQLException e) {
            logger.error("查询当前商家{}报名此次活动{}的报名状态，失败{}，",
                    new Object[]{supplierId, activityId, e});
            throw new ServiceException(e);
        }
    }

    @Override
    public BigDecimal getActivityTotalPrice(Long supplierId, String activityId) throws ServiceException {
        if (StringUtils.isBlank(activityId)) {
            logger.error("查看商家报名活动总费用失败，没有找到活动id。");
            throw new ServiceException("查看商家报名活动总费用失败，没有找到活动id。");
        }

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("supplierId", supplierId);
            paramMap.put("activityId", activityId);
            return activitySupplierDAO.getActivityTotalPrice(paramMap);
        } catch (SQLException e) {
            logger.error("查询当前商家{}报名此次活动{}的报名总费用，失败{}，",
                    new Object[]{supplierId, activityId, e});
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveSupplierEntry(String activityId, Long supplierId, Long loginUserId, ActivityInfo activity,
                                  List<ActivitySkuVo> newActivitySkuList) throws ServiceException {
        //查看报名时是否为邀请或者指定的商家
        Long supplierEntryId = getSupplierEntryId(supplierId, activityId, Boolean.FALSE);
        boolean needUpdate = supplierEntryId != null;
        Timestamp newTime = new Timestamp(System.currentTimeMillis());
        BigDecimal activityTotalPrice = getActivityTotalPrice(activity, newActivitySkuList);

        try {
            //2.保存商家报名信息
            ActivitySupplierEntry supplierEntry = new ActivitySupplierEntry();
            supplierEntry.setActivityId(Long.parseLong(activityId));
            supplierEntry.setSupplierId(supplierId);
            supplierEntry.setEntryStatus(ActivityEntryStatus.ALREADY_ENTRY.getStatus());
            supplierEntry.setAuditStatus(Integer.parseInt(AuditStatus.UNAUDIT.getStatus()));
            supplierEntry.setModifyTime(newTime);
            supplierEntry.setModifUser(loginUserId);
            supplierEntry.setEntryTime(newTime);
            supplierEntry.setActivityTotalPrice(activityTotalPrice);
            if (needUpdate) {
                //修改
                supplierEntry.setSupplierEntryId(supplierEntryId);
                activitySupplierDAO.updateSupplierEntry(supplierEntry);
            } else {
                //新增
                supplierEntry.setActivitySupplierType(activity.getSupplierChoiceType());
                supplierEntry.setCreateTime(newTime);
                supplierEntry.setCreateUser(loginUserId);
                supplierEntryId = activitySupplierDAO.saveSupplierEntry(supplierEntry);
            }

            //3.保存商家报名的sku信息
            activitySkuService.batchSaveActivitySku(newActivitySkuList, loginUserId, supplierEntryId);

            //4.更新预售数量缓存
            updateSkuStockQuantityCache(supplierEntryId, newActivitySkuList, null, Boolean.FALSE);

            //5.保存商家缴费信息
            Long paymentId = activityPaymentService.saveFirstActivityPayment(activityId, supplierEntryId,
                    supplierId, activityTotalPrice, loginUserId);

            //6.保存商家缴费明细信息
            activityPaymentDetailService.batchInsertPaymentDetail(paymentId, newActivitySkuList);
        } catch (SQLException e) {
            logger.error("商家报名活动失败，", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 计算当前活动所需要的费用
     *
     * @param activity          活动信息
     * @param activitySkuList   当前所选择的sku
     * @return
     */
    private BigDecimal getActivityTotalPrice(ActivityInfo activity, List<ActivitySkuVo> activitySkuList) {
        //1.先计算所有sku的总费用
        //免费直接跳出即为0
        BigDecimal activityTotalPrice = new BigDecimal("0.00");
        //固定收费、单个sku收费、返佣比例收费
        if (activity.getChargeType().intValue() == ActivityChargeType.SINGLE_CHARGES.getType().intValue()
                || activity.getChargeType().intValue() == ActivityChargeType.RABATE.getType().intValue()
                || activity.getChargeType().intValue() == ActivityChargeType.FIXED_CHARGES.getType().intValue()) {
            BigDecimal decimal = new BigDecimal("-1");
            for (ActivitySkuVo activitySkuVo : activitySkuList) {
                BigDecimal skuTotalPrice = activitySkuVo.getSkuTotalPrice();
                if (skuTotalPrice.doubleValue() < 0) {
                    skuTotalPrice = skuTotalPrice.multiply(decimal);
                }
                activityTotalPrice = activityTotalPrice.add(skuTotalPrice);
            }
        }
        return activityTotalPrice;
    }

    @Override
    public Map<String, Long> getActivitySupplierEntryStatus(Long supplierId, String activityId) throws ServiceException {
        if (StringUtils.isBlank(activityId)) {
            logger.error("获取活动商家报名状态的具体情况失败，没有找到活动id。");
            throw new ServiceException("获取活动商家报名状态的具体情况，没有找到活动id。");
        }

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("supplierId", supplierId);
            paramMap.put("activityId", activityId);
            return activitySupplierDAO.getActivitySupplierEntryStatus(paramMap);
        } catch (SQLException e) {
            logger.error("查询当前商家{}报名此次活动{}的报名状态，失败{}，",
                    new Object[]{supplierId, activityId, e});
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateSupplierEntry(String activityId, Long supplierId,
                                    Long loginUserId, ActivityInfo activity,
                                    List<ActivitySkuVo> newActivitySkuList,
                                    List<ActivitySkuVo> exitsActivitySkuList,
                                    String deleteSkuId) throws ServiceException {
        //查看报名ID
        Long supplierEntryId = getSupplierEntryId(supplierId, activityId, Boolean.FALSE);
        Timestamp newTime = new Timestamp(System.currentTimeMillis());

        //1.先计算所有sku的总费用
        List<ActivitySkuVo> activitySkuList = new ArrayList<ActivitySkuVo>();
        if (CollectionUtils.isNotEmpty(exitsActivitySkuList)) {
            activitySkuList.addAll(exitsActivitySkuList);
        }
        if (CollectionUtils.isNotEmpty(newActivitySkuList)) {
            activitySkuList.addAll(newActivitySkuList);
        }
        BigDecimal activityTotalPrice = getActivityTotalPrice(activity, activitySkuList);

        try {
            //2.修改商家报名信息
            ActivitySupplierEntry supplierEntry = new ActivitySupplierEntry();
            supplierEntry.setEntryStatus(ActivityEntryStatus.ALREADY_ENTRY.getStatus());
            supplierEntry.setAuditStatus(Integer.parseInt(AuditStatus.UNAUDIT.getStatus()));
            supplierEntry.setModifyTime(newTime);
            supplierEntry.setModifUser(loginUserId);
            supplierEntry.setEntryTime(newTime);
            supplierEntry.setActivityTotalPrice(activityTotalPrice);
            supplierEntry.setSupplierEntryId(supplierEntryId);
            activitySupplierDAO.updateSupplierEntry(supplierEntry);
            //3.删除已经删除的sku信息
            activitySkuService.deleteActivitySku(deleteSkuId, supplierEntryId);
            //4.保存商家报名的sku信息
            if (CollectionUtils.isNotEmpty(newActivitySkuList)) {
                activitySkuService.batchSaveActivitySku(newActivitySkuList, loginUserId, supplierEntryId);
            }
            //5.修改原有的报名sku信息
            if (CollectionUtils.isNotEmpty(exitsActivitySkuList)) {
                activitySkuService.batchUpdateActivitySku(exitsActivitySkuList, loginUserId);
            }
            //6.更新预售数量缓存
            updateSkuStockQuantityCache(supplierEntryId, newActivitySkuList, exitsActivitySkuList, Boolean.FALSE);

            //7.保存商家缴费信息
            Long paymentId = activityPaymentService.saveFirstActivityPayment(activityId, supplierEntryId,
                    supplierId, activityTotalPrice, loginUserId);

            //8.保存商家缴费明细信息
            activityPaymentDetailService.batchInsertPaymentDetail(paymentId, activitySkuList);
        } catch (Exception e) {
            logger.error("商家报名活动失败，", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int appendPrice(BigDecimal totalPrice, Long supplierId, Long loginUserId, String activityId)
            throws ServiceException {
        Timestamp newTime = new Timestamp(System.currentTimeMillis());
        ActivitySupplierEntry supplierEntry = new ActivitySupplierEntry();
        supplierEntry.setModifyTime(newTime);
        supplierEntry.setModifUser(loginUserId);
        supplierEntry.setActivityTotalPrice(totalPrice);
        supplierEntry.setActivityId(Long.parseLong(activityId));
        supplierEntry.setSupplierId(supplierId);

        try {
            return activitySupplierDAO.appendPrice(supplierEntry);
        } catch (SQLException e) {
            logger.error("追加推广时，修改商家报名的总费用失败，", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Double getShopScore(Long supplierId) throws ServiceException {
        String key = "shopScore" + "_" + supplierId;
        double avg = 5d;
        try {
            String json = jedisCluster.get(key);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            //存在缓存
            if (StringUtils.isNotBlank(json)) {
                resultMap = JSON.parseObject(json, Map.class);
                if (MapUtils.isNotEmpty(resultMap)) {
                    avg = Double.parseDouble(resultMap.get("avgScore").toString());
                }
            } else {//没有缓存查库后插入到缓存中
                Map<String, BigDecimal> score = activitySupplierDAO.getShopScore(supplierId);
                //总评
                BigDecimal count = MapUtils.isNotEmpty(score) ? score.get("ASSESSCOUNT") : BigDecimal.ZERO;
                if (MapUtils.isEmpty(score) || BigDecimal.ZERO.compareTo(count) >= 0) {
                    resultMap.put("assess_Type_one", 5);
                    resultMap.put("assess_Type_two", 5);
                    resultMap.put("assess_Type_three", 5);
                    resultMap.put("assess_Type_four", 5);
                    resultMap.put("avgScore", 5);
                } else {
                    //描述、发货、配送、售后、最大值
                    BigDecimal max = new BigDecimal(5);
                    BigDecimal at1 = score.get("ASSESS_TYPE_ONE")  .divide(count, 2, BigDecimal.ROUND_HALF_DOWN);
                    BigDecimal at2 = score.get("ASSESS_TYPE_TWO")  .divide(count, 2, BigDecimal.ROUND_HALF_DOWN);
                    BigDecimal at3 = score.get("ASSESS_TYPE_THREE").divide(count, 2, BigDecimal.ROUND_HALF_DOWN);
                    BigDecimal at4 = score.get("ASSESS_TYPE_FOUR") .divide(count, 2, BigDecimal.ROUND_HALF_DOWN);
                    if (at1.compareTo(max) > 0) {
                        at1 = max;
                    }
                    if (at2.compareTo(max) > 0) {
                        at2 = max;
                    }
                    if (at3.compareTo(max) > 0) {
                        at3 = max;
                    }
                    if (at4.compareTo(max) > 0) {
                        at4 = max;
                    }
                    resultMap.put("assess_Type_one", at1);
                    resultMap.put("assess_Type_two", at2);
                    resultMap.put("assess_Type_three", at3);
                    resultMap.put("assess_Type_four", at4);
                    avg = at1.add(at2).add(at3).add(at4).divide(new BigDecimal(4), 2,
                            BigDecimal.ROUND_HALF_DOWN).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                    resultMap.put("avgScore", avg);
                }

                jedisCluster.setex(key, 24 * 60 * 60, JSON.toJSONString(resultMap));
            }
        } catch (SQLException e) {
            logger.error("获取当铺评分失败，供应商id：{}，错误信息{}.",
                    new Object[]{supplierId, e});
            throw new ServiceException(e);
        }
        return avg;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int cancelActivity(Long entryId, Long loginUserId) throws ServiceException {
        if (entryId == null) {
            logger.error("撤销活动报名失败，没有找到商家报名id。");
            throw new ServiceException("撤销活动报名失败，没有找到商家报名id。");
        }

        try {
            ActivitySupplierEntry entry = new ActivitySupplierEntry();
            entry.setSupplierEntryId(entryId);
            entry.setEntryStatus(ActivityEntryStatus.CANCEL_ENTRY.getStatus());
            entry.setModifUser(loginUserId);
            entry.setModifyTime(new Timestamp(System.currentTimeMillis()));
            int result = activitySupplierDAO.updateByPrimaryKeySelective(entry);
            if (result != 1) {
                logger.error("撤销活动报名失败，撤销记录不对,活动报名id：{}，使用者{}.",
                        new Object[]{entryId, loginUserId});
                throw new ServiceException("撤销活动报名失败，撤销了多条报名记录。");
            }
            return result;
        } catch (SQLException e) {
            logger.error("撤销活动报名失败，活动报名id：{}，使用者{}.",
                    new Object[]{entryId, loginUserId});
            throw new ServiceException(e);
        }
    }
}
