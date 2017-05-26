package com.kmzyc.promotion.app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.km.framework.page.Pagination;
import com.kmzyc.promotion.app.dao.ProductSkuDAO;
import com.kmzyc.promotion.app.service.ProductPriceCacheService;
import com.kmzyc.promotion.app.service.PromotionInfoService;
import com.kmzyc.promotion.app.service.PromotionProductService;
import com.kmzyc.promotion.app.thread.CacheThread;
import com.kmzyc.promotion.app.util.DateUtil;
import com.kmzyc.promotion.app.util.ListUtils;
import com.kmzyc.promotion.app.util.PromotionInfoUtil;
import com.kmzyc.promotion.app.vobject.ProductAndSku;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.app.vobject.ProductSkuPrice;
import com.kmzyc.promotion.app.vobject.ProductStock;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.sys.util.RedisCacheUtil;

import redis.clients.jedis.JedisCluster;

@Service("productPriceCacheService")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class ProductPriceCacheServiceImpl implements ProductPriceCacheService {
    // 日志记录
    private static final Logger logger =
            LoggerFactory.getLogger(ProductPriceCacheServiceImpl.class);

    @Resource
    private JedisCluster jedisCluster;
    @Resource
    private PromotionProductService promotionProductService;

    @Resource
    private PromotionInfoService promotionInfoService;

    @Resource
    private ProductSkuDAO productSkuDAO;

    @Override
    public void createProductPriceCache() throws Exception {
        List<PromotionInfo> infoList =
                promotionInfoService.getExpiryPromotionListByTime(new Date());
        eachPromotionInfo(infoList);
    }

    /** 更新所有商品促销信息缓存 */
    @Override
    public void updateAllProductPriceCache() throws Exception {
        Set<String> skuIdSet = null;
        skuIdSet = jedisCluster.hkeys(RedisCacheUtil.PRODUCT_SKU_ID_MAP_KEY);

        if (skuIdSet == null || skuIdSet.isEmpty()) {
            createProductPriceCache();
        } else {
            Iterator<String> iter = skuIdSet.iterator();
            List<Long> skuIds = new ArrayList<Long>();
            while (iter.hasNext()) {
                try {
                    skuIds.add(Long.valueOf(iter.next()));
                } catch (Exception e) {
                    continue;
                }
            }
            List<PromotionInfo> infoList =
                    promotionInfoService.getExpiryPromotionListByTime(new Date());
            List<ProductAndSku> pasList = queryProductAndSkuForBatchForPage(skuIds);
            Map<Long, ProductAndSku> pasMap = new HashMap<Long, ProductAndSku>();
            if (null != pasList && !pasList.isEmpty()) {
                for (ProductAndSku pas : pasList) {
                    pasMap.put(pas.getProductSkuId(), pas);
                }
            }
            for (Long skuId : skuIds) {
                try {
                    updateSkuProductPriceCache(skuId, pasMap.get(skuId), infoList, jedisCluster);
                } catch (Exception e) {
                    continue;
                }
            }
        }
        // 全场促销活动
        createGlobalProductPriceCache();
    }

    @Override
    public void createGlobalProductPriceCache() throws Exception {
        List<PromotionInfo> promotionList =
                promotionInfoService.getExpiryPromotionListByTime(new Date());
        // 创建缓存
        createProductPriceCache(RedisCacheUtil.globalCacheRedisCode, promotionList);
    }

    /**
     * 创建所有活动商品价格促销信息缓存
     * 
     * @throws Exception
     */
    private void eachPromotionInfo(List<PromotionInfo> infoList) throws Exception {
        if (infoList == null || infoList.isEmpty())
            return;
        Map<Long, ProductSku> hasCreadedMap = new HashMap<Long, ProductSku>();
        boolean isOver = false;
        List<PromotionInfo> iList = infoList;
        for (PromotionInfo info : iList) {
            if (info.getProductFilterType() == 1 && info.getShopSort() == 1 && !isOver) {// 全场
                isOver = true;
                createGlobalProductPriceCache();
            }

            Pagination page = new Pagination();
            page.setNumperpage(50);
            page.setPage(1);
            do {
                page = promotionProductService.queryProductListByPromotionInfo(page, info);
                page.setPage(page.getPage() + 1);
                List<ProductSku> skuList = page.getRecordList();
                if (skuList == null || skuList.isEmpty()) {
                    continue;
                }

                List<Long> skuIds = new ArrayList<Long>();
                for (ProductSku productSku : skuList) {
                    skuIds.add(productSku.getProductSkuId());
                }
                // List<ProductAndSku> pasList =
                // productSkuDAO.queryProductAndSkuForBatch(skuIds);
                List<ProductAndSku> pasList = queryProductAndSkuForBatchForPage(skuIds);
                Map<Long, ProductAndSku> pasMap = new HashMap<Long, ProductAndSku>();
                if (null != pasList && !pasList.isEmpty()) {
                    for (ProductAndSku pas : pasList) {
                        pasMap.put(pas.getProductSkuId(), pas);
                    }
                }

                for (ProductSku productSku : skuList) {
                    Long skuId = productSku.getProductSkuId();
                    if (hasCreadedMap.get(skuId) != null)
                        continue;
                    List<ProductSkuPrice> list = null;
                    try {
                        ProductAndSku pas = pasMap.get(skuId);
                        if (null != pas && null != pas.getProductSkuId()
                                && pas.getProductSkuId() > 0) {
                            list = this.getProductPriceList(pas, iList);
                        } else {
                            list = this.getProductPriceListFormDb(skuId, iList);
                        }
                        RedisCacheUtil.createProductPriceCache(jedisCluster, skuId, list);
                    } catch (Exception e) {
                        logger.error("--------------sku:{}------------------------------创建缓存fail",
                                skuId, e);
                        break;
                    }
                    hasCreadedMap.put(skuId, productSku);
                }

            } while (page.getPage() <= page.getTotalpage());
        }
    }

    @Override
    public void updatePriceCacheByPromotion(PromotionInfo promotion) throws Exception {
        if (promotion.getProductFilterType().intValue() == 1
                && promotion.getShopSort().intValue() == 1) {
            try {
                // 更新全部缓存
                updateAllProductPriceCache();
            } catch (Exception e) {
                logger.error("更新指定活动缓存失败,promotionId：{}", promotion, e);
            }
            return;
        }

        List<PromotionInfo> infoList =
                promotionInfoService.getExpiryPromotionListByTime(new Date());
        Pagination page = new Pagination();
        page.setNumperpage(50);
        page.setPage(1);
        do {
            page = promotionProductService.queryProductListByPromotionInfo(page, promotion);
            List<ProductSku> skuList = page.getRecordList();
            if (skuList == null || skuList.isEmpty()) {
                continue;
            }

            List<Long> skuIds = new ArrayList<Long>();
            for (ProductSku productSku : skuList) {
                skuIds.add(productSku.getProductSkuId());
            }
            // List<ProductAndSku> pasList =
            // productSkuDAO.queryProductAndSkuForBatch(skuIds);
            List<ProductAndSku> pasList = queryProductAndSkuForBatchForPage(skuIds);
            Map<Long, ProductAndSku> pasMap = new HashMap<Long, ProductAndSku>();
            if (null != pasList && !pasList.isEmpty()) {
                for (ProductAndSku pas : pasList) {
                    pasMap.put(pas.getProductSkuId(), pas);
                }
            }

            for (ProductSku productSku : skuList) {
                Long skuId = productSku.getProductSkuId();
                List<ProductSkuPrice> list = null;
                try {
                    List<PromotionInfo> iList = infoList;
                    ProductAndSku pas = pasMap.get(skuId);
                    if (null != pas && null != pas.getProductSkuId() && pas.getProductSkuId() > 0) {
                        list = this.getProductPriceList(pas, iList);
                    } else {
                        list = this.getProductPriceListFormDb(skuId, iList);
                    }
                    RedisCacheUtil.createProductPriceCache(jedisCluster, skuId, list);
                } catch (Exception e) {
                    logger.error("--------------sku:{}------------------------------创建缓存fail",
                            skuId, e);
                    break;
                }
            }
            page.setPage(page.getPage() + 1);
        } while (page.getPage() <= page.getTotalpage());
    }


    // 更新指定商品缓存
    private void updateSkuProductPriceCache(Long productSkuId, ProductAndSku pas,
            final List<PromotionInfo> infoList, JedisCluster jedisCluster) throws Exception {
        if (productSkuId == null) {
            return;
        }
        List<PromotionInfo> iList = infoList;
        if (productSkuId <= 0) {
            List<PromotionInfo> infoListall = Lists.newArrayList();// 全场活动list
            for (PromotionInfo p : iList) {
                if (p.getProductFilterType() == 1 && p.getShopSort() == 1) {
                    infoListall.add(p);
                }
            }
            iList = infoListall;
        }
        List<ProductSkuPrice> list = null;
        if (null != pas && null != pas.getProductSkuId() && pas.getProductSkuId() > 0) {
            list = this.getProductPriceList(pas, iList);
        } else {
            list = this.getProductPriceListFormDb(productSkuId, iList);
        }
        RedisCacheUtil.createProductPriceCache(jedisCluster, productSkuId, list);
    }

    // 更新指定商品缓存
    @Override
    public void createProductPriceCache(Long productSkuId, final List<PromotionInfo> infoList)
            throws Exception {
        if (productSkuId == null) {
            return;
        }
        List<PromotionInfo> iList = infoList;
        // 全场活动list
        if (productSkuId <= 0) {
            List<PromotionInfo> infoListall = Lists.newArrayList();
            for (PromotionInfo p : iList) {
                if (p.getProductFilterType() == 1 && p.getShopSort() == 1) {
                    infoListall.add(p);
                }
            }
            iList = infoListall;
        }
        List<ProductSkuPrice> list = this.getProductPriceListFormDb(productSkuId, iList);

        RedisCacheUtil.createProductPriceCache(jedisCluster, productSkuId, list);
    }

    /**
     * 创建缓存使用
     */
    public List<ProductSkuPrice> getProductPriceListFormDb(Long productSkuId,
            List<PromotionInfo> infoList) throws Exception {
        ProductAndSku sku = null;
        Double price = null;
        if (productSkuId <= 0) {// 各个渠道全场活动缓存
            sku = new ProductAndSku();
            sku.setProductSkuId(productSkuId);
            sku.setPrice(0d);
            sku.setProductId(productSkuId);
            sku.setShopCode("all");
        } else {
            sku = productSkuDAO.findProduct(productSkuId);
        }
        if (sku == null) {
            return null;
        }
        price = sku.getPrice();
        if (price == null) {
            return null;
        }
        ProductSku skunew = new ProductSku();
        skunew.setPrice(sku.getPrice());
        skunew.setProductId(sku.getProductId());
        skunew.setProductSkuId(productSkuId);
        List<PromotionInfo> promotionInfoList =
                promotionInfoService.getAllPromotionInfoByProductSku(sku, infoList);
        if (promotionInfoList == null || promotionInfoList.isEmpty()) {
            return null;
        }
        List<ProductSkuPrice> priceList = new ArrayList<ProductSkuPrice>();
        List<Date> dateList = PromotionInfoUtil.subsectionPromotionDate(promotionInfoList);
        for (int i = 0; i < dateList.size() - 1; i++) {
            Date startDate = dateList.get(i);
            Date endDate = dateList.get(i + 1);
            if (endDate.before(new Date())) {
                continue;// 过期的活动不建缓存
            }
            Date middDate = DateUtil.getMiddDate(startDate, endDate);
            ProductSkuPrice productSkuPrice = new ProductSkuPrice();
            productSkuPrice.setStartTime(startDate);
            productSkuPrice.setEndTime(endDate);
            productSkuPrice.setUnitPrice(BigDecimal.valueOf(price));
            productSkuPrice = promotionInfoService.getProductSkuPricePromotionInfoByDB(
                    productSkuPrice, middDate, promotionInfoList);
            // 设置限购数据
            setRestriction(productSkuId, productSkuPrice);
            priceList.add(productSkuPrice);
        }
        return priceList;
    }

    /**
     * 创建缓存使用
     */
    public List<ProductSkuPrice> getProductPriceList(ProductAndSku sku,
            List<PromotionInfo> infoList) throws Exception {
        Double price = null;
        if (sku == null) {
            return null;
        }
        price = sku.getPrice();
        if (price == null) {
            return null;
        }
        ProductSku skunew = new ProductSku();
        skunew.setPrice(sku.getPrice());
        skunew.setProductId(sku.getProductId());
        skunew.setProductSkuId(sku.getProductSkuId());
        List<PromotionInfo> promotionInfoList =
                promotionInfoService.getAllPromotionInfoByProductSku(sku, infoList);
        if (promotionInfoList == null || promotionInfoList.isEmpty()) {
            return null;
        }
        List<ProductSkuPrice> priceList = new ArrayList<ProductSkuPrice>();
        List<Date> dateList = PromotionInfoUtil.subsectionPromotionDate(promotionInfoList);
        if (null != dateList && !dateList.isEmpty()) {
            for (int i = 0; i < dateList.size() - 1; i++) {
                Date startDate = dateList.get(i);
                Date endDate = dateList.get(i + 1);
                if (endDate.before(new Date())) {
                    continue;// 过期的活动不建缓存
                }
                Date middDate = DateUtil.getMiddDate(startDate, endDate);
                ProductSkuPrice productSkuPrice = new ProductSkuPrice();
                productSkuPrice.setStartTime(startDate);
                productSkuPrice.setEndTime(endDate);
                productSkuPrice.setUnitPrice(BigDecimal.valueOf(price));
                productSkuPrice = promotionInfoService.getProductSkuPricePromotionInfoByDB(
                        productSkuPrice, middDate, promotionInfoList);
                setRestriction(sku.getProductSkuId(), productSkuPrice);
                priceList.add(productSkuPrice);
            }
        }
        return priceList;
    }

    /**
     * 创建缓存使用
     */
    public List<ProductSkuPrice> getProductPriceListFormDbForBatch(Long productSkuId,
            List<PromotionInfo> infoList) throws Exception {
        ProductAndSku sku = null;
        Double price = null;
        if (productSkuId <= 0) {// 各个渠道全场活动缓存
            sku = new ProductAndSku();
            sku.setProductSkuId(productSkuId);
            sku.setPrice(0d);
            sku.setProductId(productSkuId);
            sku.setShopCode("all");
        } else {
            sku = productSkuDAO.findProduct(productSkuId);
        }
        if (sku == null) {
            return null;
        }
        price = sku.getPrice();
        if (price == null) {
            return null;
        }
        ProductSku skunew = new ProductSku();
        skunew.setPrice(sku.getPrice());
        skunew.setProductId(sku.getProductId());
        skunew.setProductSkuId(productSkuId);
        List<PromotionInfo> promotionInfoList =
                promotionInfoService.getAllPromotionInfoByProductSku(sku, infoList);
        if (promotionInfoList == null || promotionInfoList.isEmpty()) {
            return null;
        }
        List<ProductSkuPrice> priceList = new ArrayList<ProductSkuPrice>();
        List<Date> dateList = PromotionInfoUtil.subsectionPromotionDate(promotionInfoList);
        for (int i = 0; i < dateList.size() - 1; i++) {
            Date startDate = dateList.get(i);
            Date endDate = dateList.get(i + 1);
            if (endDate.before(new Date())) {
                continue;// 过期的活动不建缓存
            }
            Date middDate = DateUtil.getMiddDate(startDate, endDate);
            ProductSkuPrice productSkuPrice = new ProductSkuPrice();
            productSkuPrice.setStartTime(startDate);
            productSkuPrice.setEndTime(endDate);
            productSkuPrice.setUnitPrice(BigDecimal.valueOf(price));
            productSkuPrice = promotionInfoService.getProductSkuPricePromotionInfoByDB(
                    productSkuPrice, middDate, promotionInfoList);
            // ProductSkuPrice productSkuPrice = this.initProductSkuPrice(map,
            // startDate, endDate);
            // 设置限购数据
            setRestriction(productSkuId, productSkuPrice);
            priceList.add(productSkuPrice);
        }
        return priceList;
    }

    // 设置限购数据
    private void setRestriction(Long productSkuId, ProductSkuPrice productSkuPrice) {

        boolean hasRestriction = false;
        PromotionInfo sale = productSkuPrice.getSalePromotionInfo();

        if (sale != null && sale.getProductFilterType().equals(2)) {
            hasRestriction = promotionInfoService.setRestriction(sale.getPromotionId(),
                    productSkuId, productSkuPrice);

        }
        if (hasRestriction) {
            productSkuPrice.setRestrictionType(0);
            return;
        }
        PromotionInfo discount = productSkuPrice.getDiscountPromotionInfo();
        if (discount != null && discount.getProductFilterType().equals(2)) {
            hasRestriction = promotionInfoService.setRestriction(discount.getPromotionId(),
                    productSkuId, productSkuPrice);
        }
        if (hasRestriction) {
            productSkuPrice.setRestrictionType(1);
            return;
        } else {
            productSkuPrice.setRestrictionType(2);
        }

    }

    /**
     * 根据活动更新缓存
     * 
     * @param promotionInfoId
     */
    public void updateCacheByPromotionInfo(Long promotionInfoId) {
        // 根据活动id获取活动信息
        PromotionInfo promotionInfo = promotionInfoService.getPromotionById(promotionInfoId);
        if (promotionInfo == null)
            return;
        try {
            // 根据活动更新缓存
            updatePriceCacheByPromotion(promotionInfo);
        } catch (Exception e) {
            logger.error("更新缓存发生异常", e);
        }
    }

    /**
     * 根据活动通知MQ
     * 
     * @param promotionInfoId
     */
    public void updateMQByPromotionInfo(Long promotionInfoId) {

    }

    /**
     * 活动发布撤销时更新缓存
     */
    @Override
    public void notifyByPromotionInfoId(Long promotionInfoId) {
        CacheThread cacheThread = new CacheThread(promotionInfoId) {
            @Override
            public void run() {
                try {
                    // 更新缓存
                    updateCacheByPromotionInfo((Long) this.getObject());
                } catch (Exception e) {
                    logger.error("更新缓存发生异常", e);
                }
            }
        };
        cacheThread.start();
    }

    /**
     * 活动商品变化时更新缓存
     */
    @Override
    public void notifyByProductSkuId(Long productSkuId) {
        CacheThread cacheThread = new CacheThread(productSkuId) {
            @Override
            public void run() {
                try {
                    final List<PromotionInfo> infoList =
                            promotionInfoService.getExpiryPromotionListByTime(new Date());
                    // 更新缓存
                    createProductPriceCache((Long) this.getObject(), infoList);
                } catch (Exception e) {
                    logger.error("更新缓存发生异常", e);
                }
            }
        };
        cacheThread.start();
    }

    /**
     * 更新活动销量时更新缓存，存在延迟，前端未使用
     */
    @Override
    public void notifyByProductSkuIdList(List<ProductStock> updateList) throws Exception {
        if (!ListUtils.isNotEmpty(updateList)) {
            return;
        }
        final List<PromotionInfo> infoList =
                promotionInfoService.getExpiryPromotionListByTime(new Date());
        CacheThread cacheThread = new CacheThread(updateList) {
            @Override
            public void run() {
                for (ProductStock ps : (List<ProductStock>) this.getObject()) {
                    try {
                        createProductPriceCache(ps.getSkuAttributeId(), infoList);
                    } catch (Exception e) {
                        logger.error("更新缓存发生异常", e);
                    }
                }

            }
        };
        cacheThread.start();
    }

    // 批量获取sku
    public List<ProductAndSku> queryProductAndSkuForBatchForPage(List<Long> skuIds)
            throws Exception {
        List<ProductAndSku> skuList = Lists.newArrayList();
        int skuSize = skuIds.size();
        if (skuSize <= 1000) {
            skuList = productSkuDAO.queryProductAndSkuForBatch(skuIds);
        } else {
            // 超过一千条时需分次查询
            List<ProductAndSku> list = null;
            int pages = skuSize / 1000;
            for (int i = 0; i < pages; i++) {
                list = productSkuDAO
                        .queryProductAndSkuForBatch(skuIds.subList(i * 1000, (i + 1) * 1000));
                skuList.addAll(list);
                list = null;
            }
            if (skuSize % 1000 != 0) {
                list = productSkuDAO
                        .queryProductAndSkuForBatch(skuIds.subList(pages * 1000, skuSize));
            }
            if (ListUtils.isNotEmpty(list)) {
                skuList.addAll(list);
            }
        }
        return skuList;
    }

}
