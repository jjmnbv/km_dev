package com.kmzyc.promotion.optimization.cache;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.kmzyc.promotion.app.dao.ProductSkuDAO;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.service.PromotionInfoService;
import com.kmzyc.promotion.app.service.PromotionProductDataService;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.constant.PromotionConstant;
import com.kmzyc.promotion.optimization.model.RestrictionProduct;
import com.kmzyc.promotion.optimization.service.PromotionProductNewService;
import com.kmzyc.promotion.sys.util.RedisCacheUtil;
import com.kmzyc.promotion.sys.util.StringUtil;
import com.kmzyc.promotion.util.PromotionCacheUtil;
import com.kmzyc.promotion.util.RedisTemplate;

import redis.clients.jedis.JedisCluster;

@Component
public class PromotionProductCacheProcess {
    private static final Logger logger =
            LoggerFactory.getLogger(PromotionProductCacheProcess.class);
    @Resource
    PromotionInfoService promotionInfoService;

    @Resource
    PromotionProductNewService promotionProductNewService;

    @Resource
    PromotionProductDataService promotionProductDataService;

    @Resource
    ProductSkuDAO productSkuDAO;

    @Resource
    RedisCacheUtil redisCacheUtil;

    @Resource
    PromotionCacheUtil promotionCacheUtil;

    @Resource
    private JedisCluster jedisCluster;

    @Resource
    RedisTemplate redisTemplate;

    public void createProductPriceCache(PromotionInfo promotion) throws Exception {
        // 创建活动商品缓存信息
        Long promotionId = promotion.getPromotionId();
        // 可以读缓存 可以分批
        Set<String> set = redisCacheUtil.getPromotionProduct(promotionId);
        if (StringUtil.isEmpty(set)) {
            logger.error("更新指定活动缓存失败,promotionId：{},缓存中没有活动商品！", promotionId);
            return;
        }
        /**
         * List<ProductAndSku> pasList = null; /** 获取活动下所有商品的基本信息 pasList =
         * promotionProductNewService.queryProductAndSkuForBatch(set, promotionId); if
         * (StringUtil.isEmpty(pasList)) { return; }
         */
        List<PromotionInfo> infoList =
                promotionInfoService.getExpiryPromotionListByTime(new Date());
        for (String pas : set) {
            createProductCache(infoList, Long.valueOf(pas));
        }
    }

    private void createProductCache(List<PromotionInfo> infoList, Long skuId) throws Exception {
        String lockKey = skuId.toString();
        logger.info("开始创建skuId:{} 缓存", lockKey);
        boolean lockOk = redisTemplate.tryLock(lockKey);
        if (!lockOk) {
            logger.info("skuId:{} 缓存已经在创建了", lockKey);
            return;
        }
        try {
            this.createProductPromtoionInfoCahce(skuId, infoList);
            logger.info("创建skuId:{} 缓存 over", lockKey);
            // RedisCacheUtil.createProductPriceCache(shardedJedis, skuId, list);
        } finally {
            redisTemplate.release(lockKey);
        }
    }

    /** 重建商品活动缓存 */
    public void createProductPromtoionInfoCahce(Long skuId, List<PromotionInfo> promotionList) {
        String key = PromotionConstant.PRODUCT_PROMOTION_INFO + skuId;
        jedisCluster.del(key);
        Date expireAt = null;
        for (PromotionInfo info : promotionList) {
            Long promotionId = info.getPromotionId();
            Double score = jedisCluster.zscore(PromotionConstant.PROMOTION_SKU + promotionId,
                    skuId.toString());
            boolean inner = score != null;
            if (inner) {
                Date endTime = info.getEndTime();
                if (info.getPromotionType().intValue() == PromotionTypeEnums.SALE.getValue()
                        .intValue()
                        || info.getPromotionType().intValue() == PromotionTypeEnums.DISCOUNT
                                .getValue().intValue()
                        || info.getPromotionType().intValue() == PromotionTypeEnums.SALE_APP
                                .getValue().intValue()) {
                    // 商品限购数据 特价价格
                    RestrictionProduct product = queryRestrictionProduct(skuId, promotionId);
                    Map<String, String> restrictionInfo =
                            RedisCacheUtil.buildRestrictionInfo(info, product);
                    String restrictionInfoKey = PromotionConstant.PRODUCT_PROMOTION_RESTRICTION_INFO
                            + promotionId + "_" + skuId;
                    if (!StringUtil.isEmpty(restrictionInfo)) {

                        String promotionStockStr = restrictionInfo
                                .get(PromotionConstant.PRODUCT_PROMOTION_STOCK_FIELD); // 活动库存

                        if (jedisCluster.exists(restrictionInfoKey)) {

                            String alreadySellStr = jedisCluster.hget(restrictionInfoKey,
                                    PromotionConstant.PRODUCT_PROMOTION_ALREADY_SELL);

                            int alreadySell = (StringUtil.isEmpty(alreadySellStr)) ? 0
                                    : Integer.valueOf(alreadySellStr);

                            if (!StringUtil.isEmpty(promotionStockStr)) {

                                int promotionStock = Integer.valueOf(promotionStockStr);

                                if (promotionStock - alreadySell <= 0) {
                                    restrictionInfo.put(
                                            PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD,
                                            String.valueOf(0));
                                } else {
                                    restrictionInfo.put(
                                            PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD,
                                            String.valueOf(promotionStock - alreadySell));
                                }
                            }

                            restrictionInfo.put(PromotionConstant.PRODUCT_PROMOTION_ALREADY_SELL,
                                    String.valueOf(alreadySell));

                            jedisCluster.del(restrictionInfoKey);
                        } else {
                            if (!StringUtil.isEmpty(promotionStockStr)) {
                                restrictionInfo
                                        .put(PromotionConstant.PRODUCT_PROMOTION_ALREADY_SELL, "0");
                            }
                        }

                        jedisCluster.hmset(restrictionInfoKey, restrictionInfo);
                        jedisCluster.expire(restrictionInfoKey,
                                Long.valueOf(
                                        (endTime.getTime() - System.currentTimeMillis()) / 1000)
                                        .intValue() + 60 * 60 * 25);
                    }

                } else if (info.getPromotionType().intValue() == PromotionTypeEnums.GANT.getValue()
                        .intValue()
                        && (info.getPromotionData() == null || info.getPromotionData()
                                .compareTo(BigDecimal.valueOf(-1)) != 0)) {//
                    // 附赠活动且活动商品筛选类型不是全场
                    // mkw 20151117 增加附赠活动
                    List<JSONObject> gantList =
                            this.promotionProductDataService.queryGantProduct(promotionId, skuId);

                    if (gantList != null && gantList.size() > 0) {

                        String gantInfoKey = PromotionConstant.PRODUCT_PROMOTION_GANT_INFO
                                + promotionId + "_" + skuId;

                        jedisCluster.del(gantInfoKey);
                        jedisCluster.set(gantInfoKey, JSONArray.toJSONString(gantList));
                        jedisCluster.expire(gantInfoKey,
                                Long.valueOf(
                                        (endTime.getTime() - System.currentTimeMillis()) / 1000)
                                        .intValue() + 60 * 60 * 25);

                    }
                    // end
                }
                // 商品促销信息
                // String jsonStr = RedisCacheUtil.buildPromotionJsonInfo(info).toJSONString();
                String jsonStr = promotionId.toString();
                jedisCluster.zadd(key, endTime.getTime(), jsonStr);
                if (expireAt == null || expireAt.compareTo(endTime) < 0) {
                    expireAt = info.getEndTime();
                }
            }
        }
        if (expireAt != null) {
            int seconds = (int) ((expireAt.getTime() - System.currentTimeMillis()) / 1000) + 1;
            jedisCluster.expire(key, seconds);
        }
    }

    private RestrictionProduct queryRestrictionProduct(Long skuId, Long promotionId) {
        return promotionProductNewService.queryRestrictionProduct(skuId, promotionId);
    }



    /**
     * 活动商品变化，更新商品促销信息
     * 
     * @throws Exception
     */
    public boolean createOrUpdateProductPromotionCache(long skuId, long promotionId, int type) {
        logger.info("添加或者删除活动商品，更新商品促销信息,skuId:{},promotionId:{},type:{}", skuId, promotionId,
                type);
        if (type == 1) {// 移除
            removeProductFromPromotionCache(skuId, promotionId);
        }
        if (type == 2) {// 加入 可以优化 不用重建所有
            // PromotionInfo info = promotionInfoService.getPromotionById(promotionId);
            addProductToPromotionCache(skuId, promotionId);
            List<PromotionInfo> infoList =
                    promotionInfoService.getExpiryPromotionListByTime(new Date());
            try {
                createProductCache(infoList, skuId);
            } catch (Exception e) {
                logger.error("动商品变化，更新商品促销信息异常,skuId:{},promotionId:{},type:{}", skuId, promotionId,
                        type);
                return false;
            }
        }
        return true;
    }

    // 待优化
    private void addProductToPromotionCache(long skuId, long promotionId) {
        logger.info("加入活动商品，skuId:{},promotionId:{}", skuId, promotionId);
        Map<String, Double> map = Maps.newHashMap();
        map.put(skuId + "", Double.valueOf(skuId));
        jedisCluster.zadd(PromotionConstant.PROMOTION_SKU + promotionId, map);
    }

    private void removeProductFromPromotionCache(long skuId, long promotionId) {
        logger.info("移除活动商品，skuId:{},promotionId:{}", skuId, promotionId);

        String skuIdStr = skuId + "";
        long zremOk = jedisCluster.zrem(PromotionConstant.PROMOTION_SKU + promotionId, skuIdStr);
        if (zremOk < 1) {
            logger.error("redis zrem 异常,key:{},member:skuIdStr:{}",
                    PromotionConstant.PROMOTION_SKU + promotionId, skuIdStr);
        }

        long zremOk2 = jedisCluster.zrem(PromotionConstant.PRODUCT_PROMOTION_INFO + skuIdStr,
                String.valueOf(promotionId));
        if (zremOk2 < 1) {
            logger.error("redis zrem 异常,key:{},member:{}",
                    PromotionConstant.PRODUCT_PROMOTION_INFO + skuIdStr, promotionId);
        }
    }

    /**
     * 产品上线更新活动缓存
     * 
     * @param productSkuId
     * @param list
     */
    public void updateProductPromotionCache(long productSkuId, List<PromotionInfo> list) {

        Map<String, Double> map = null;
        for (PromotionInfo promotionInfo : list) {
            logger.info("加入活动商品，skuId:{},promotionId:{}", productSkuId,
                    promotionInfo.getPromotionId());
            map = Maps.newHashMap();
            map.put(productSkuId + "", Double.valueOf(productSkuId));
            jedisCluster.zadd(PromotionConstant.PROMOTION_SKU + promotionInfo.getPromotionId(),
                    map);
        }

        try {
            createProductCache(list, productSkuId);
        } catch (Exception e) {

            logger.error("产品上线更新活动缓存异常,skuId:{},promotion:{}", productSkuId, list);
        }
    }
}
