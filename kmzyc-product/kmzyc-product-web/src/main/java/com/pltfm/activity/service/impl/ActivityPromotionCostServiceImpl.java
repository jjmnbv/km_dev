package com.pltfm.activity.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.ProductSkuPriceCache;
import com.kmzyc.promotion.remote.service.PromotionRemoteService;
import com.pltfm.activity.dao.ActivityPromotionCostDAO;
import com.pltfm.activity.service.ActivityPromotionCostService;
import com.pltfm.app.enums.ActivityPaymentStatus;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;

import redis.clients.jedis.JedisCluster;

@Service("activityPromotionCostService")
public class ActivityPromotionCostServiceImpl implements ActivityPromotionCostService {

    protected Logger logger = LoggerFactory.getLogger(ActivityPromotionCostServiceImpl.class);

    @Resource
    private ActivityPromotionCostDAO activityPromotionCostDao;

    @Resource
    private PromotionRemoteService promotionRemoteService;

    @Resource
    private JedisCluster jedisCluster;

    private static final String SUPPLIER_ENTRY_SKU_KEY = "supplierEntrySku_";

    @Override
    public void queryActivityPromotionCost(Page page, ActivitySupplierEntry activitySupplierEntry)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;
        Map map = new HashMap();

        if (StringUtils.isNotBlank(activitySupplierEntry.getCompanyShowName())) {
            map.put("companyShowName", activitySupplierEntry.getCompanyShowName());
        }
        if (StringUtils.isNotBlank(activitySupplierEntry.getLoginAccount())) {
            map.put("loginAccount", activitySupplierEntry.getLoginAccount());
        }
        if (activitySupplierEntry.getActivityId() != null) {
            map.put("activityId", activitySupplierEntry.getActivityId());
        }
        if (activitySupplierEntry.getActivityInfo() != null
                && StringUtils.isNotBlank(activitySupplierEntry.getActivityInfo().getActivityName())) {
            map.put("activityName", activitySupplierEntry.getActivityInfo().getActivityName());
        }
        // 查询使用创建初始时间/创建末时间
        if (activitySupplierEntry.getActivityInfo() != null
                && activitySupplierEntry.getActivityInfo().getActivityStartTime() != null
                && activitySupplierEntry.getActivityInfo().getActivityEndTime() != null) {
            map.put("activityStartTime", activitySupplierEntry.getActivityInfo()
                    .getActivityStartTime());
            map.put("activityEndTime", activitySupplierEntry.getActivityInfo().getActivityEndTime());
        }
        // 活动状态
        if (activitySupplierEntry.getActivityInfo() != null
                && activitySupplierEntry.getActivityInfo().getActivityStatus() != null) {
            map.put("activityStatus", activitySupplierEntry.getActivityInfo().getActivityStatus());
        } else {
            // 查询全部活动状态
            map.put("activityStatus", "-1");
        }
        // 款项状态，状态：已退款，待退款(无需退款)，查询数据库中款项状态为未退款的记录，再与缓存中的已销售数量对比，显示具体的款项状态（待退款、无需退款）
        if (activitySupplierEntry.getActivityPaymentStatus() != null) {
            map.put("activityPaymentStatus", activitySupplierEntry.getActivityPaymentStatus());
        } else {
            // 查询全部款项状态
            map.put("activityPaymentStatus", "-1");
        }
        BigDecimal residualCost;
        try {
           int count = activityPromotionCostDao.queryActivityPromotionCostCount(map);
            List<ActivitySupplierEntry> promotionList = activityPromotionCostDao.queryActivityPromotionCost(map, skip, max);

            for (ActivitySupplierEntry activitySupplier : promotionList) {
                List<ActivitySku> skuList = activitySupplier.getActivitySkuList();
                residualCost = new BigDecimal("0.00");
                // 设置活动商家退款状态
                if (activitySupplier.getRefundActivityAmount() != null
                        && activitySupplier.getRefundActivityAmount().compareTo(BigDecimal.ZERO) > 0) {
                    // 已退款
                    activitySupplier.setActivityPaymentStatus(ActivityPaymentStatus.REFUNDED.getStatus());
                    // 剩余活动费用取数据库中的退款记录
                    residualCost = activitySupplier.getRefundActivityAmount();
                } else {
                    // 未退款
                    for (ActivitySku sku : skuList) {
                        // 获取缓存SKU已售数量
                        String saleQuantity = jedisCluster.get(SUPPLIER_ENTRY_SKU_KEY
                                        + activitySupplier.getSupplierEntryId() + "_"
                                        + sku.getProductSkuId());
                        if (saleQuantity == null) {
                            saleQuantity = "0";
                            // 商家商品活动费用累加,在没有销售商品的时候，直接取数据库中商品的总费用，减少计算所带来的金额误差
                            residualCost = residualCost.add(sku.getSkuTotalPrice());
                        } else {
                            Integer residualQuantity = sku.getPreSaleQuantity() - Integer.parseInt(saleQuantity);
                            // 单个商品的剩余费用
                            BigDecimal residualSkuPrice = sku.getActivityPrice()
                                            .multiply(sku.getCommissionRate())
                                            .multiply(new BigDecimal(residualQuantity))
                                            .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
                            // 商家商品活动费用累加
                            residualCost = residualCost.add(residualSkuPrice);
                        }
                    }
                    if (residualCost.compareTo(BigDecimal.ZERO) == 0) {
                        activitySupplier.setActivityPaymentStatus(ActivityPaymentStatus.NEEDLESSREFUND.getStatus());
                    } else {
                        activitySupplier.setActivityPaymentStatus(ActivityPaymentStatus.NOT_REFUND.getStatus());
                    }
                }
                // 设置活动商家剩余费用
                activitySupplier.setResidualActivityPrice(residualCost);
            }
            page.setRecordCount(count);
            page.setDataList(promotionList);
        } catch (Exception e) {
            logger.error("获取缓存SKU已售数量失败：", e);
            throw new ServiceException(e);
        }

    }

    @Override
    public void queryActivitySupplierCostDetail(Page page, ActivitySku activitySku)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        Integer residualQuantity = null;
        // 单个商品的剩余费用
        BigDecimal residualSkuPrice = null;
        List<ProductSkuPriceCache> cachePriceList = null;
        List<ProductSkuPriceCache> productSkuPriceCacheList = new ArrayList<ProductSkuPriceCache>();
        ProductSkuPriceCache productSkuPriceCache = null;

        try {
            int count = activityPromotionCostDao.querySupplierCostDetailCount(activitySku);
            List<ActivitySku> promotionList = activityPromotionCostDao.querySupplierCostDetail(activitySku, skip, max);

            for (ActivitySku sku : promotionList) {
                // 获取缓存SKU已售数量
                String saleQuantity = jedisCluster.get(SUPPLIER_ENTRY_SKU_KEY + sku.getSupplierEntryId() + "_"
                                + sku.getProductSkuId());
                if (saleQuantity == null) {
                    saleQuantity = "0";
                    // 销售数量为0时，剩余费用从数据库取，减少计算所带来的金额误差
                    residualSkuPrice = sku.getSkuTotalPrice();
                } else {
                    residualQuantity = sku.getPreSaleQuantity() - Integer.parseInt(saleQuantity);
                    // 单个商品的剩余费用
                    residualSkuPrice = sku.getActivityPrice()
                            .multiply(sku.getCommissionRate())
                            .multiply(new BigDecimal(residualQuantity))
                            .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
                }
                sku.setResidualSkuPrice(residualSkuPrice);
                sku.setSaleQuantity(Integer.valueOf(saleQuantity));
                // 设置查询商品售价的查询条件
                productSkuPriceCache = new ProductSkuPriceCache();
                productSkuPriceCache.setProductSkuId(sku.getProductSkuId());
                productSkuPriceCacheList.add(productSkuPriceCache);
            }
            // 获取商品的促销价
            cachePriceList = promotionRemoteService.getProductSkuPriceBatch(productSkuPriceCacheList,
                    new Date());
            // 设置商品的销售价
            for (int skuNum = 0; skuNum < promotionList.size(); skuNum++) {
                if (promotionList.get(skuNum).getProductSkuId().toString()
                        .equals(cachePriceList.get(skuNum).getProductSkuId().toString())
                        && null != cachePriceList.get(skuNum).getPromotionPrice()) {
                    promotionList.get(skuNum).setPrice(new BigDecimal(cachePriceList.get(skuNum).getPromotionPrice()));
                }
            }
            page.setDataList(promotionList);
            page.setRecordCount(count);
        } catch (Exception e) {
            logger.error(" queryActivitySupplierCostDetail方法获取缓存SKU已售数量失败：", e);
            throw new ServiceException(e);
        }
    }

}