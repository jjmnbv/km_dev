package com.kmzyc.promotion.sys.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.vobject.BaseProduct;
import com.kmzyc.promotion.app.vobject.ProductSkuPrice;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.app.vobject.PromotionRuleData;
import com.kmzyc.promotion.constant.PromotionConstant;
import com.kmzyc.promotion.exception.ServiceException;
import com.kmzyc.promotion.optimization.model.RestrictionProduct;
import com.kmzyc.promotion.util.RedisTemplate;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Component
public class RedisCacheUtil {
    // 日志记录
    protected static Logger logger = LoggerFactory.getLogger(RedisCacheUtil.class);
    /** redis中价格缓存key前缀 */
    public static final String PRODUCT_PRICE_CACHE_REDIS_CODE = "r_product_promotion";
    /** redis中存放创建过价格缓存商品的skuId键前缀 */
    public static final String PRODUCT_SKU_ID_MAP_KEY = "r_product_promotion_productSkuId_map";
    /** redis中存放创建过价格缓存商品的skuId键前缀,b2b渠道 */
    public static final String PRODUCT_SKU_ID_MAP_KEY_FOR_B2B =
            "r_product_promotion_productSkuId_map_b2b";
    /**
     * redis存放促销规则的key前缀
     */
    public static final String PROMOTION_RULE_DATA_PREX = "r_promotion_rule_data_prex_";

    /**
     * 参加活动的产品数据key前缀
     */
    public static final String PROMOTION_IN_PRODUCT_DATA = "r_promotion_in_product_data_";
    /**
     * 产品是否参加活动据key前缀
     */
    public static final String PROMOTION_IN_PRODUCT_PREX = "r_promotion_in_product_";

    /**
     * redis存放参加活动产品前缀
     */
    public static final String PROMOTION_PRODUCT_PREX = "r_promotion_product_prex_";
    /**
     * redis存放活动赠品、加价购缓存
     */
    public static final String PROMOTION_RULE_PRODUCT = "r_promotion_rule_product_prex_";
    /**
     * 活动成功列表
     */
    public static final String ROMOTION_SUCCESS = "r_promotion_success_";
    public static final Long globalCacheRedisCode = 0L;

    @Resource
    public RedisTemplate redisTemplate;

    @Resource
    private JedisCluster jedisCluster;

    /**
     * 序列化对象
     */
    private static byte[] serialize(Object obj) throws IOException {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } finally {
            if (oos != null)
                oos.close();
            if (baos != null)
                baos.close();
        }
    }

    /**
     * 反序列化对象
     */
    private static Object unserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        if (bytes == null || 0 == bytes.length)
            return null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            if (ois != null)
                ois.close();
            if (bais != null)
                bais.close();
        }
    }


    /**
     * 获取缓存信息
     * 
     * @param jedisCluster
     * @param skuId
     * @return
     */
    private static byte[] getProductFinalPrice(JedisCluster jedisCluster, Long skuId) {
        try {
            return jedisCluster.get(getJedisKey(skuId));
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 获取B2B缓存信息
     * 
     * @param jedisCluster
     * @param skuId
     * @return
     */
    private static byte[] getProductFinalPriceForB2B(JedisCluster jedisCluster, Long skuId) {
        try {
            return jedisCluster.get(getJedisKeyForB2B(skuId));
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 转换
     * 
     * @param data
     * @return
     */
    private static List<ProductSkuPrice> toProductSkuPriceCacheList(byte[] data) {
        if (data == null)
            return null;
        List<ProductSkuPrice> list = null;
        try {
            list = (List<ProductSkuPrice>) unserialize(data);
        } catch (Exception e) {
            logger.info("转换价格异常");
        }
        return list;
    }

    /**
     * 过滤符合条件的缓存
     * 
     * @param list
     * @param date
     * @return
     */
    public static ProductSkuPrice getProductPriceByDate(List<ProductSkuPrice> list, Date date) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (ProductSkuPrice prices : list) {
            Date start = prices.getStartTime();
            Date end = prices.getEndTime();
            if (start.before(date) && end.after(date)) {
                return prices;
            }
        }
        return null;
    }

    /**
     * 获取当前时间的活动缓存
     * 
     * @param jedisCluster
     * @param skuId
     * @return
     * @throws JedisConnectionException
     */
    public static ProductSkuPrice getNowProductSkuPrice(JedisCluster jedisCluster, Long skuId)
            throws JedisConnectionException {
        byte[] data = getProductFinalPrice(jedisCluster, skuId);
        if (data == null)
            return null;
        List<ProductSkuPrice> list = toProductSkuPriceCacheList(data);
        if (list == null)
            return null;
        ProductSkuPrice priceObject = getProductPriceByDate(list, new Date());
        return priceObject;
    }

    /**
     * 获取B2B当前时间的活动缓存
     * 
     * @param jedisCluster
     * @param skuId
     * @return
     * @throws JedisConnectionException
     */
    public static ProductSkuPrice getNowProductSkuPriceForB2B(JedisCluster jedisCluster, Long skuId)
            throws JedisConnectionException {
        byte[] data = getProductFinalPriceForB2B(jedisCluster, skuId);
        if (data == null)
            return null;
        List<ProductSkuPrice> list = toProductSkuPriceCacheList(data);
        if (list == null)
            return null;
        ProductSkuPrice priceObject = getProductPriceByDate(list, new Date());
        return priceObject;
    }

    /**
     * 获取产品活动缓存
     * 
     * @param jedisCluster
     * @param skuId
     * @return
     * @throws JedisConnectionException
     */
    public static List<ProductSkuPrice> getProductSkuPriceList(JedisCluster jedisCluster,
            Long skuId) throws JedisConnectionException {
        byte[] data = getProductFinalPrice(jedisCluster, skuId);
        if (data == null)
            return null;
        List<ProductSkuPrice> list = toProductSkuPriceCacheList(data);
        if (list == null)
            return null;
        return list;
    }

    /**
     * 获取B2B产品活动缓存
     * 
     * @param jedisCluster
     * @param skuId
     * @return
     * @throws JedisConnectionException
     */
    public static List<ProductSkuPrice> getProductSkuPriceListForB2B(JedisCluster jedisCluster,
            Long skuId) throws JedisConnectionException {
        byte[] data = getProductFinalPriceForB2B(jedisCluster, skuId);
        if (data == null)
            return null;
        List<ProductSkuPrice> list = toProductSkuPriceCacheList(data);
        if (list == null)
            return null;
        return list;
    }

    /**
     * 新建缓存
     * 
     * @param jedisCluster
     * @param productSkuId
     * @param list
     */
    public static void createProductPriceCache(JedisCluster jedisCluster, Long productSkuId,
            List<ProductSkuPrice> list) {
        if (list == null || list.isEmpty()) {
            jedisCluster.hdel(PRODUCT_SKU_ID_MAP_KEY, productSkuId.toString());
            jedisCluster.del(getJedisKeyString(productSkuId));
            return;
        }
        byte[] data = null;
        try {
            data = serialize(list);
        } catch (IOException e) {
            e.printStackTrace();
            jedisCluster.hdel(PRODUCT_SKU_ID_MAP_KEY, productSkuId.toString());
            jedisCluster.del(getJedisKeyString(productSkuId));
            return;
        }
        byte[] key = getJedisKey(productSkuId);
        jedisCluster.set(key, data);
        jedisCluster.hset(PRODUCT_SKU_ID_MAP_KEY, productSkuId.toString(), productSkuId.toString());
        Date endDate = list.get(list.size() - 1).getEndTime();
        long lastTime = endDate.getTime();
        jedisCluster.expireAt(key, lastTime);
        // System.out.println(list);
        logger.info("--------------sku:" + productSkuId + "------------------------------创建缓存OK");

    }

    /**
     * 新建B2B缓存
     * 
     * @param jedisCluster
     * @param productSkuId
     * @param list
     */
    public static void createProductPriceCacheForB2B(JedisCluster jedisCluster, Long productSkuId,
            List<ProductSkuPrice> list) {
        if (list == null || list.isEmpty()) {
            jedisCluster.hdel(PRODUCT_SKU_ID_MAP_KEY_FOR_B2B, productSkuId.toString());
            jedisCluster.del(getJedisKeyStringForB2B(productSkuId));
            return;
        }
        byte[] data = null;
        try {
            data = serialize(list);
        } catch (IOException e) {
            e.printStackTrace();
            jedisCluster.hdel(PRODUCT_SKU_ID_MAP_KEY_FOR_B2B, productSkuId.toString());
            jedisCluster.del(getJedisKeyStringForB2B(productSkuId));
            return;
        }
        byte[] key = getJedisKeyForB2B(productSkuId);
        jedisCluster.set(key, data);
        jedisCluster.hset(PRODUCT_SKU_ID_MAP_KEY_FOR_B2B, productSkuId.toString(),
                productSkuId.toString());
        Date endDate = list.get(list.size() - 1).getEndTime();
        long lastTime = endDate.getTime();
        jedisCluster.expireAt(getJedisKeyForB2B(productSkuId), lastTime);
        // System.out.println(list);
        logger.info("--------------sku:" + productSkuId + "------------------------------创建缓存OK");

    }

    /**
     * B2B JedisKey
     * 
     * @param skuId
     * @return
     */
    public static String getJedisKeyStringForB2B(Long skuId) {
        return PRODUCT_PRICE_CACHE_REDIS_CODE.concat(skuId.toString()).concat("B2B");
    }

    /**
     * B2B JedisKey
     * 
     * @param skuId
     * @return
     */
    public static byte[] getJedisKeyForB2B(Long skuId) {
        return getJedisKeyStringForB2B(skuId).getBytes();
    }

    /**
     * JedisKey
     * 
     * @param skuId
     * @return
     */
    public static String getJedisKeyString(Long skuId) {
        return PRODUCT_PRICE_CACHE_REDIS_CODE.concat(skuId.toString());
    }

    /**
     * JedisKey
     * 
     * @param skuId
     * @return
     */
    public static byte[] getJedisKey(Long skuId) {
        return getJedisKeyString(skuId).getBytes();
    }

    /**
     * redis存放促销规则
     * 
     * @param skuId
     * @param pid
     * @param in
     * @throws ServiceException
     */
    public static void createPromotionProductRuleDataCache(JedisCluster jedisCluster, Long pid,
            Date endDate, List<PromotionRuleData> prdList) throws ServiceException {
        if (null == prdList || null == pid) {
            return;
        }
        String key = PROMOTION_RULE_DATA_PREX.concat(pid.toString());
        try {
            jedisCluster.set(key.getBytes(), serialize(prdList));
        } catch (Exception e) {
            logger.error("redis存放促销规则发生异常", e);
        }
        if (null != endDate) {
            jedisCluster.expireAt(key.getBytes(), endDate.getTime());
        } else {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 5);// 无过期时间默认存储五天
            jedisCluster.expireAt(key.getBytes(), cal.getTimeInMillis());
        }
    }

    /**
     * 获取redis存放促销规则
     * 
     * @param skuId
     * @param pid
     * @param in
     * @throws ServiceException
     */
    public static List<PromotionRuleData> getPromotionProductRuleDataCache(
            JedisCluster jedisCluster, Long pid) throws ServiceException {
        List<PromotionRuleData> prdList = null;
        if (null != pid) {
            String key = PROMOTION_RULE_DATA_PREX.concat(pid.toString());
            try {
                prdList = (List<PromotionRuleData>) unserialize(jedisCluster.get(key.getBytes()));
            } catch (Exception e) {
                logger.error("redis存放促销规则发生异常", e);
            }
        }
        return prdList;
    }

    /**
     * 创建产品是否参加活动缓存
     * 
     * @param skuId
     * @param pid
     * @param in
     * @throws ServiceException
     */
    public static void createProductInPromotionCache(JedisCluster jedisCluster, Long skuId,
            PromotionInfo info, Boolean in) throws ServiceException {
        if (null == skuId || null == info || null == info.getPromotionId() || null == in) {
            return;
        }
        String key = PROMOTION_IN_PRODUCT_PREX.concat(info.getPromotionId().toString());
        try {
            jedisCluster.hset(key, skuId.toString(), in.toString());
        } catch (Exception e) {
            logger.error("redis创建产品是否参加活动缓存发生异常", e);
        }
        if (null != info.getEndTime()) {
            jedisCluster.expireAt(key.getBytes(), info.getEndTime().getTime());
        } else {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 5);// 无过期时间默认存储五天
            jedisCluster.expireAt(key.getBytes(), cal.getTimeInMillis());
        }
    }

    /**
     * 获取产品是否参加活动缓存
     * 
     * @param skuId
     * @param pid
     * @param in
     * @throws ServiceException
     */
    public static String getProductInPromotionCache(JedisCluster jedisCluster, Long skuId, Long pid)
            throws ServiceException {
        if (null == skuId || null == pid) {

            return null;
        }

        String key = PROMOTION_IN_PRODUCT_PREX.concat(pid.toString());

        return jedisCluster.hget(key, skuId.toString());
    }

    /**
     * 创建参加活动的产品数据
     * 
     * @param skuId
     * @param pid
     * @param in
     * @throws ServiceException
     */
    public static void createPPCache(JedisCluster jedisCluster, BaseProduct bp, PromotionInfo info,
            Boolean in) throws ServiceException {
        if (null == bp || null == bp.getProductSkuId() || null == info
                || null == info.getPromotionId() || null == in) {
            return;
        }
        String key = PROMOTION_IN_PRODUCT_DATA.concat(info.getPromotionId().toString());
        try {
            if (in) {
                jedisCluster.hset(key.getBytes(), bp.getProductSkuId().toString().getBytes(),
                        serialize(bp));
            } else {
                jedisCluster.hset(key.getBytes(), bp.getProductSkuId().toString().getBytes(),
                        serialize(new BaseProduct()));
            }
            jedisCluster.hset(PROMOTION_IN_PRODUCT_PREX.concat(info.getPromotionId().toString()),
                    bp.getProductSkuId().toString(), in.toString());
        } catch (Exception e) {
            logger.error("redis创建参加活动的产品数据发生异常", e);
        }
        if (null != info.getEndTime()) {
            jedisCluster.expireAt(key.getBytes(), info.getEndTime().getTime());
        } else {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 5);// 无过期时间默认存储五天
            jedisCluster.expireAt(key.getBytes(), cal.getTimeInMillis());
        }
    }

    /**
     * 获取参加活动的产品数据
     * 
     * @param skuId
     * @param pid
     * @param in
     * @throws ServiceException
     */
    public static BaseProduct getPPCache(JedisCluster jedisCluster, Long skuId,
            PromotionInfo info) {
        if (null == skuId || null == info || null == info.getPromotionId()) {
            return null;
        }
        String key = PROMOTION_IN_PRODUCT_DATA.concat(info.getPromotionId().toString());
        try {
            return (BaseProduct) unserialize(
                    jedisCluster.hget(key.getBytes(), skuId.toString().getBytes()));
        } catch (Exception e) {
            logger.error("redis获取参加活动的产品数据发生异常", e);
        }
        return null;
    }

    /**
     * 创建活动的产品
     * 
     * @param skuId
     * @param pid
     * @param in
     * @throws ServiceException
     */
    public static void createPromotionProductCache(JedisCluster jedisCluster, PromotionProduct pp)
            throws ServiceException {
        if (null == pp || null == pp.getProductSkuId() || null == pp.getPromotionId()) {
            return;
        }
        String key = PROMOTION_PRODUCT_PREX.concat(pp.getPromotionId().toString());
        try {
            jedisCluster.hset(key.getBytes(), pp.getProductSkuId().toString().getBytes(),
                    serialize(pp));
        } catch (Exception e) {
            logger.error("redis创建参加活动的产品数据发生异常", e);
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 5);// 无过期时间默认存储五天
        jedisCluster.expireAt(key.getBytes(), cal.getTimeInMillis());
    }

    /**
     * 创建活动的产品
     * 
     * @param skuId
     * @param pid
     * @param in
     * @throws ServiceException
     */
    public static void createPromotionProductCacheForNull(JedisCluster jedisCluster,
            Long productSkuId, Long promotionId) throws ServiceException {
        if (null == promotionId || null == productSkuId) {
            return;
        }
        String key = PROMOTION_PRODUCT_PREX.concat(promotionId.toString());
        try {
            jedisCluster.hset(key.getBytes(), productSkuId.toString().getBytes(), serialize(null));
        } catch (Exception e) {
            logger.error("redis创建参加活动的产品数据发生异常", e);
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 5);// 无过期时间默认存储五天
        jedisCluster.expireAt(key.getBytes(), cal.getTimeInMillis());
    }

    /**
     * 获取活动的产品
     * 
     * @param skuId
     * @param pid
     * @param in
     * @throws ServiceException
     */
    public static PromotionProduct getPromotionProductCache(JedisCluster jedisCluster, Long pid,
            Long skuId) throws ServiceException {
        if (null == pid) {
            return null;
        }
        try {
            return (PromotionProduct) unserialize(
                    jedisCluster.hget(PROMOTION_PRODUCT_PREX.concat(pid.toString()).getBytes(),
                            skuId.toString().getBytes()));
        } catch (Exception e) {
            logger.error("redis获取参加活动的产品数据发生异常", e);
        }
        return null;
    }

    /**
     * 创建赠品、加价购缓存
     * 
     * @param skuId
     * @param pid
     * @param in
     * @throws ServiceException
     */
    public static void createPromotionRuleProductCache(JedisCluster jedisCluster,
            PromotionInfo info, List<PromotionProduct> ppList) throws ServiceException {
        if (null == ppList || ppList.isEmpty() || null == info || null == info.getPromotionId()) {
            return;
        }
        String key = PROMOTION_RULE_PRODUCT.concat(info.getPromotionId().toString());
        try {
            for (PromotionProduct pp : ppList) {
                jedisCluster.hset(key.getBytes(), pp.getProductSkuId().toString().getBytes(),
                        serialize(pp));
            }
        } catch (Exception e) {
            logger.error("redis创建参加活动的产品数据发生异常", e);
        }
        if (null != info.getEndTime()) {
            jedisCluster.expireAt(key.getBytes(), info.getEndTime().getTime());
        } else {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 5);// 无过期时间默认存储五天
            jedisCluster.expireAt(key.getBytes(), cal.getTimeInMillis());
        }
    }

    /**
     * 获取赠品、加价购缓存
     * 
     * @param skuId
     * @param pid
     * @param in
     * @throws ServiceException
     */
    public static Map<Long, List<PromotionProduct>> getPromotionRuleProductCache(
            JedisCluster jedisCluster, List<Long> pids) throws ServiceException {
        if (null == pids || pids.isEmpty()) {
            return null;
        }
        List<PromotionProduct> ppList = null;
        Map<byte[], byte[]> tempMap = null;
        Map<Long, List<PromotionProduct>> ppMap = new HashMap<Long, List<PromotionProduct>>();
        for (Long pid : pids) {
            try {
                ppList = new ArrayList<PromotionProduct>();
                tempMap = jedisCluster
                        .hgetAll(PROMOTION_RULE_PRODUCT.concat(pid.toString()).getBytes());
                for (byte[] bt : tempMap.keySet()) {
                    ppList.add((PromotionProduct) unserialize(tempMap.get(bt)));
                }
                ppMap.put(pid, ppList);
            } catch (Exception e) {
                logger.error("获取活动的产品发生异常", e);
            }
        }
        return ppMap;
    }

    /**
     * 活动删除缓存
     * 
     * @param jedisCluster
     * @param pid
     */
    public static void removePromotionData(JedisCluster jedisCluster, Long pid) {
        Date now = new Date();
        jedisCluster.expireAt(PROMOTION_IN_PRODUCT_DATA.concat(pid.toString()).getBytes(),
                now.getTime());
        jedisCluster.expireAt(PROMOTION_PRODUCT_PREX.concat(pid.toString()).getBytes(),
                now.getTime());
        jedisCluster.expireAt(PROMOTION_IN_PRODUCT_PREX.concat(pid.toString()).getBytes(),
                now.getTime());
        jedisCluster.expireAt(PROMOTION_RULE_DATA_PREX.concat(pid.toString()), now.getTime());
        jedisCluster.expireAt(PROMOTION_RULE_PRODUCT.concat(pid.toString()), now.getTime());
    }

    /**
     * 创建活动是否创建成功缓存
     * 
     * @param skuId
     * @param pid
     * @param in
     * @throws ServiceException
     */
    public static void createPromotionSuccessCache(JedisCluster jedisCluster, PromotionInfo info,
            Boolean in) throws ServiceException {

        String key = ROMOTION_SUCCESS.concat(info.getPromotionId().toString());
        jedisCluster.set(key, in.toString());
        jedisCluster.expireAt(key, info.getEndTime().getTime());
    }

    /**
     * 获取活动是否创建成功缓存
     * 
     * @param skuId
     * @param pid
     * @param in
     * @throws ServiceException
     */
    public static Boolean getPSCache(JedisCluster jedisCluster, String id) throws ServiceException {

        String key = ROMOTION_SUCCESS.concat(id);
        String rs = jedisCluster.get(key);
        return (null == rs || "".equals(rs)) ? false : Boolean.valueOf(rs);
    }


    public void savePromotionInfo(PromotionInfo info,
            List<com.alibaba.fastjson.JSONObject> ruleDataAndPrizeEntityInfo) {
        String id = info.getPromotionId().toString();
        Long expireAt = info.getEndTime().getTime();
        // int se = (int) ((expireAt - System.currentTimeMillis()) / 1000) + 1;
        int se = Long.valueOf((expireAt - System.currentTimeMillis()) / 1000).intValue();
        if (se <= 0) {
            jedisCluster.del(PromotionConstant.PROMOTION + id);
        } else {
            // 构建活动缓存数据
            JSONObject promotionJson = buildPromotionJson(info, ruleDataAndPrizeEntityInfo);
            jedisCluster.setex(PromotionConstant.PROMOTION + id, se, promotionJson.toString());
        }
    }

    /***
     * 
     * @param info 活动基本数据
     * @param ruleDataAndPrizeEntityInfo 梯度
     * @return
     */
    public static JSONObject buildPromotionJson(PromotionInfo info,
            List<JSONObject> ruleDataAndPrizeEntityInfo) {
        JSONObject json = new JSONObject();
        String name = info.getPromotionName();
        Long sellerId = info.getSellerId();

        json.put("id", info.getPromotionId());
        json.put("name", name);
        json.put("level", info.getPromotionPriority());
        json.put("sellerId", sellerId);
        json.put("endTime", info.getEndTime());
        json.put("startTime", info.getStartTime());
        int type = info.getPromotionType();
        if (type == PromotionTypeEnums.DISCOUNT.getIntValue()) {
            json.put("data", info.getPromotionData().divide(BigDecimal.TEN));
        }
        json.put("type", type);
        // json.put("sellUpType", info.getSellUpType());
        if (type == PromotionTypeEnums.GANT.getIntValue()) {
            BigDecimal gantFilterType = info.getPromotionData();
            json.put("gantFilterType", gantFilterType);
        }
        // json.put("shopSort", info.getShopSort());
        // json.put("filterValue", info.getProductFilterSql());
        if (!com.kmzyc.promotion.util.StringUtil.isEmpty(ruleDataAndPrizeEntityInfo)) {
            json.put("ruleDataAndPrizeEntityInfo", ruleDataAndPrizeEntityInfo);
        }
        return json;
    }

    public void savePromotionProductInfo(Long promotionId, Date expireAt, Map<String, Double> ids) {
        redisTemplate.zaddex(PromotionConstant.PROMOTION_SKU + promotionId, expireAt.getTime(),
                ids);
    }

    /** 删除活动缓存 */
    public boolean deletePromotionCache(long promotionId) {

        jedisCluster.del(PromotionConstant.PROMOTION + promotionId);
        jedisCluster.del(PromotionConstant.PROMOTION_SKU + promotionId);
        return true;
    }

    public boolean isParticipatorMember(Long promotionId, Long skuId) {
        return redisTemplate.sismember(promotionId.toString(), skuId.toString());
    }



    public static JSONObject buildPromotionJsonInfo(PromotionInfo info) {
        JSONObject json = new JSONObject();
        json.put("id", info.getPromotionId());
        if (info.getPromotionData() != null) {
            json.put("data", info.getPromotionData().divide(new BigDecimal(10)));
        }
        json.put("type", info.getPromotionType());
        json.put("name", info.getPromotionName());
        json.put("endTime", info.getEndTime());
        json.put("startTime", info.getStartTime());
        json.put("level", info.getPromotionPriority());
        return json;
    }

    public Set<String> getPromotionProduct(Long promtionId) {
        String key = PromotionConstant.PROMOTION_SKU + promtionId;

        return redisTemplate.zrevrange(key, 0, -1);
    }

    public static Map<String, String> buildRestrictionInfo(PromotionInfo info,
            RestrictionProduct product) {
        if (product == null) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        if (product.getMinBuy() != null) {
            map.put("min", product.getMinBuy().toString());
        }
        if (product.getMaxBuy() != null) {
            map.put("max", product.getMaxBuy().toString());
        }
        if (product.getPromotionStock() != null) {
            map.put(PromotionConstant.PRODUCT_ORDER_QUANTITY_FIELD,
                    product.getPromotionStock().toString());
            map.put(PromotionConstant.PRODUCT_PROMOTION_STOCK_FIELD,
                    product.getPromotionStock().toString());
            if (info.getSellUpType() != null) {
                map.put("sellUpType", info.getSellUpType().toString());
            }
        }
        if (product.getSalePrice() != null) {
            map.put("salePrice", product.getSalePrice().doubleValue() + "");
        }
        if (map.isEmpty()) {
            map = null;
            return null;
        }
        return map;
    }



    public void deletePromotionProductCache(long promotionId) {
        redisTemplate.del(PromotionConstant.PROMOTION_SKU + promotionId);
    }
}
