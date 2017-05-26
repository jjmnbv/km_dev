package com.kmzyc.supplier.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.dao.ActivitySkuDAO;
import com.kmzyc.supplier.service.ActivitySkuService;
import com.kmzyc.supplier.vo.ActivitySkuVo;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySkuExample;
import com.pltfm.app.vobject.ViewProductSku;
import com.kmzyc.promotion.app.vobject.ProductSkuPriceCache;
import com.kmzyc.promotion.remote.service.PromotionRemoteService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/24 14:31
 */
@Service("activitySkuService")
public class ActivitySkuServiceImpl implements ActivitySkuService {

    private Logger logger = LoggerFactory.getLogger(ActivitySkuServiceImpl.class);

    @Resource
    private ActivitySkuDAO activitySkuDAO;
    
    @Resource
    private PromotionRemoteService promotionRemoteService;

    @Override
    public Pagination selectProductSkuList4Activity(Long supplierId, String brandIds, String haveSkuId,
                                                    ViewProductSku viewProductSku,
                                                    Pagination page) throws ServiceException {
        Map<String, Object> objCondition = new HashMap<String, Object>();
        objCondition.put("supplierId", supplierId.toString());
        objCondition.put("status", viewProductSku.getStatus());

        if (StringUtils.isNotBlank(viewProductSku.getProductTitle())) {
            objCondition.put("productTitle", viewProductSku.getProductTitle().trim());
        }
        if (StringUtils.isNotBlank(viewProductSku.getProductSkuCode())) {
            objCondition.put("productSkuCode", viewProductSku.getProductSkuCode().trim());
        }
        if (viewProductSku.getCategoryId() != null) {
            objCondition.put("categoryId", viewProductSku.getCategoryId());
        } else if (viewProductSku.getMCategoryId() != null) {
            objCondition.put("mCategoryId", viewProductSku.getMCategoryId());
        } else if (viewProductSku.getBCategoryId() != null) {
            objCondition.put("bCategoryId", viewProductSku.getBCategoryId());
        }
        if (StringUtils.isNotBlank(haveSkuId)) {
            objCondition.put("haveSkuId", haveSkuId.split(","));
        }
        if (StringUtils.isNotBlank(brandIds)) {
            objCondition.put("brandIds", brandIds.split(","));
        }
        page.setObjCondition(objCondition);
        try {
            activitySkuDAO.selectProductSkuList4Activity(page);

            List recordList = page.getRecordList();
            if (CollectionUtils.isNotEmpty(recordList)) {
                int recordLength = recordList.size();
                List<ProductSkuPriceCache> cachePriceList = new ArrayList<ProductSkuPriceCache>(recordLength);
                ProductSkuPriceCache cache = null;
                for (Object o : recordList) {
                    if (o instanceof ViewProductSku) {
                        viewProductSku = (ViewProductSku) o;
                        cache = new ProductSkuPriceCache();
                        cache.setProductSkuId(viewProductSku.getProductSkuId());
                        cachePriceList.add(cache);
                    }
                }

                cachePriceList = promotionRemoteService.getProductSkuPriceBatch(cachePriceList, new Date());
                for (int i = 0;i< recordLength;i++) {
                    cache = cachePriceList.get(i);
                    viewProductSku = (ViewProductSku) recordList.get(i);
                    Double promotionPrice = cache.getPromotionPrice();
                    if (viewProductSku.getProductSkuId().longValue() == cache.getProductSkuId().longValue()
                            && promotionPrice != null) {
                        viewProductSku.setPrice(new BigDecimal(promotionPrice));
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("查询sku列表失败1，供应商id:{}, 品牌ids:{}, 查询参数:{}, 错误信息：{}.",
                    new Object[]{supplierId, brandIds, viewProductSku, e});
            throw new ServiceException(e);
        } 
        return page;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchSaveActivitySku(List<ActivitySkuVo> newActivitySkuList,
                                     Long loginUserId, Long supplierEntryId) throws ServiceException {
        Timestamp newTime = new Timestamp(System.currentTimeMillis());
        List<ActivitySku> skuList = new ArrayList<ActivitySku>();
        ActivitySku sku = null;
        for (ActivitySkuVo activitySkuVo : newActivitySkuList) {
            sku = new ActivitySku();
            sku.setActivityId(activitySkuVo.getActivityId());
            sku.setSupplierEntryId(supplierEntryId);
            sku.setProductSkuId(activitySkuVo.getProductSkuId());
            sku.setAuditStatus(Integer.parseInt(AuditStatus.UNAUDIT.getStatus()));
            sku.setPreSaleQuantity(activitySkuVo.getPreSaleQuantity());
            sku.setSkuTotalPrice(activitySkuVo.getSkuTotalPrice());
            sku.setActivitySkuImage(activitySkuVo.getActivitySkuImage());
            sku.setPromotionId(activitySkuVo.getPromotionId());
            sku.setActivityPrice(activitySkuVo.getActivityPrice());
            if (activitySkuVo.getCommissionRate() != null) {
                sku.setCommissionRate(activitySkuVo.getCommissionRate()
                        .divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP));
            }
            sku.setActivitySkuType(1);
            sku.setCreateTime(newTime);
            sku.setModifyTime(newTime);
            sku.setCreateUser(loginUserId);
            sku.setModifUser(loginUserId);
            skuList.add(sku);
        }

        try {
            int result = activitySkuDAO.batchSaveActivitySku(skuList);
            if (result == 0) {
                throw new ServiceException("新增报名商品的sku失败！");
            }
        } catch (SQLException e) {
            logger.error("新增报名活动的sku失败，失败信息为：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public BigDecimal computeChannelMoney(Integer preSaleQuantity, BigDecimal activityPrice,
                                          BigDecimal commissionRate) {
        BigDecimal bigDecimal = new BigDecimal("-1");
        if (activityPrice.doubleValue() < 0) {
            activityPrice = activityPrice.multiply(bigDecimal);
        }
        if (commissionRate.doubleValue() < 0) {
            commissionRate = commissionRate.multiply(bigDecimal);
        }
        BigDecimal quantity = new BigDecimal(preSaleQuantity);
        if (preSaleQuantity<0) {
            quantity = quantity.multiply(bigDecimal);
        }

        return activityPrice.multiply(commissionRate)
                .multiply(quantity)
                .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public List<String> getSkuInUnfinishedActivity(List<Long> productSkuIdList, Long supplierId, Long activityId) throws ServiceException {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("activityId",       activityId);
        condition.put("supplierId",       supplierId);
        condition.put("productSkuIdList", productSkuIdList);

        try {
            return activitySkuDAO.getSkuInUnfinishedActivity(condition);
        } catch (SQLException e) {
            logger.error("查询是否还有商品在还未结束的促销推广活动中失败，参数：{}, 错误信息：{}。",
                    new Object[]{productSkuIdList, e});
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ActivitySkuVo> getActivitySku(Long supplierId, String activityId) throws ServiceException {
        if (StringUtils.isBlank(activityId)) {
            logger.error("查看商家报名活动的商品列表失败，没有找到活动id。");
            throw new ServiceException("查看商家报名活动的商品列表失败，没有找到活动id。");
        }

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("supplierId", supplierId);
        condition.put("activityId", activityId);

        List<ActivitySkuVo> list = null;
        try {
            list = activitySkuDAO.getActivitySku(condition);
            if (CollectionUtils.isNotEmpty(list)) {
                int recordLength = list.size();
                List<ProductSkuPriceCache> cachePriceList = new ArrayList<ProductSkuPriceCache>(recordLength);
                ProductSkuPriceCache cache = null;
                for (ActivitySkuVo skuVo : list) {
                    cache = new ProductSkuPriceCache();
                    cache.setProductSkuId(skuVo.getProductSkuId());
                    cachePriceList.add(cache);
                }

                cachePriceList = promotionRemoteService.getProductSkuPriceBatch(cachePriceList, new Date());
                for (int i = 0;i< recordLength;i++) {
                    cache = cachePriceList.get(i);
                    ActivitySkuVo skuVo = list.get(i);
                    Double promotionPrice = cache.getPromotionPrice();
                    if (skuVo.getProductSkuId().longValue() == cache.getProductSkuId().longValue()
                            && promotionPrice != null) {
                        skuVo.setPrice(new BigDecimal(promotionPrice));
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("获取当前商家报名此次活动的商品失败1，商家id为：{}, 活动id为：{}，错误信息：{}。",
                    new Object[]{supplierId, activityId, e});
            throw new ServiceException(e);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteActivitySku(String deleteSkuId, Long supplierEntryId) throws ServiceException {
        if (StringUtils.isBlank(deleteSkuId)) {
            return;
        }
        ActivitySkuExample example = new ActivitySkuExample();
        ActivitySkuExample.Criteria criteria = example.createCriteria();
        criteria.andSupplierEntryIdEqualTo(supplierEntryId);
        String[] skuIds = deleteSkuId.split(",");
        criteria.andProductSkuIdIn(new ArrayList(Arrays.asList(skuIds)));
        try {
            activitySkuDAO.deleteActivitySku(example);
        } catch (SQLException e) {
            logger.error("获取当前商家报名此次活动的商品失败，skuId为：{}，错误信息：{}。",
                    new Object[]{deleteSkuId, e});
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void batchUpdateActivitySku(List<ActivitySkuVo> activitySkuList, Long loginUserId) throws ServiceException {
        if (CollectionUtils.isEmpty(activitySkuList)) {
            return;
        }

        Timestamp newTime = new Timestamp(System.currentTimeMillis());
        List<ActivitySku> skuList = new ArrayList<ActivitySku>();
        ActivitySku sku = null;
        for (ActivitySkuVo activitySkuVo : activitySkuList) {
            sku = new ActivitySku();
            sku.setActivitySkuId(activitySkuVo.getActivitySkuId());
            sku.setAuditStatus(Integer.parseInt(AuditStatus.UNAUDIT.getStatus()));
            sku.setPreSaleQuantity(activitySkuVo.getPreSaleQuantity());
            sku.setSkuTotalPrice(activitySkuVo.getSkuTotalPrice());
            sku.setActivitySkuImage(activitySkuVo.getActivitySkuImage());
            sku.setPromotionId(activitySkuVo.getPromotionId());
            sku.setActivityPrice(activitySkuVo.getActivityPrice());
            if (activitySkuVo.getCommissionRate() != null) {
                sku.setCommissionRate(activitySkuVo.getCommissionRate()
                        .divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP));
            }
            sku.setModifyTime(newTime);
            sku.setModifUser(loginUserId);
            skuList.add(sku);
        }

        try {
            int count = activitySkuDAO.batchUpdateActivitySku(skuList);
            if (count == 0) {
                throw new ServiceException("修改报名商品的sku失败！");
            }
        } catch (SQLException e) {
            logger.error("批量修改参加活动的sku失败，失败信息为：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveAppendSku(List<ActivitySkuVo> appendSkuList, Long supplierEntryId) throws ServiceException {
        List<ActivitySku> skuList = new ArrayList<ActivitySku>();
        ActivitySku sku = null;
        for (ActivitySkuVo activitySkuVo : appendSkuList) {
            sku = new ActivitySku();
            sku.setActivityId(activitySkuVo.getActivityId());
            sku.setActivitySkuType(activitySkuVo.getActivitySkuType());
            sku.setSupplierEntryId(supplierEntryId);
            sku.setProductSkuId(activitySkuVo.getProductSkuId());
            sku.setAuditStatus(Integer.parseInt(AuditStatus.AUDIT.getStatus()));
            sku.setPreSaleQuantity(activitySkuVo.getPreSaleQuantity());
            sku.setSkuTotalPrice(activitySkuVo.getSkuTotalPrice());
            sku.setActivityPrice(activitySkuVo.getActivityPrice());
            if (activitySkuVo.getCommissionRate() != null) {
                sku.setCommissionRate(activitySkuVo.getCommissionRate()
                        .divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP));
            }
            sku.setCreateUser(activitySkuVo.getCreateUser());
            sku.setModifUser(activitySkuVo.getModifUser());
            sku.setCreateTime(activitySkuVo.getCreateTime());
            sku.setModifyTime(activitySkuVo.getModifyTime());
            skuList.add(sku);
        }

        try {
            activitySkuDAO.batchSaveActivitySku(skuList);
        } catch (SQLException e) {
            logger.error("新增追加推广的sku失败，失败信息为：", e);
            throw new ServiceException(e);
        }
    }
}
