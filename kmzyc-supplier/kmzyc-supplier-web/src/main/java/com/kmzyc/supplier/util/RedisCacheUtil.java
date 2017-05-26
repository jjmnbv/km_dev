package com.kmzyc.supplier.util;


import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.supplier.util.SerializeUtil;
import com.pltfm.app.vobject.ProductSkuPrice;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

public class RedisCacheUtil {

    public static final String productPriceCacheRedisCode = ConfigurationUtil.getString("r_product_promotion_key");

    public static final Long discountCacheRedisCode = -1L;

    public static final String VALID_COUNT = "valid.count.";

    public static final int MAX_NAME_ERROR_COUNT = 5;// 用户名最大错误次数

    public static final int MAX_IP_ERROR_COUNT = 5;// IP最大错误次数

    public static String getJedisKeyString(Long skuId) {
        return RedisCacheUtil.productPriceCacheRedisCode.concat(skuId.toString());
    }

    public static byte[] getJedisKey(Long skuId) {
        try {
            return getJedisKeyString(skuId).getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getProductFinalPrice(JedisCluster jedisCluster, Long skuId) {
        byte[] key = getJedisKey(skuId);
        byte[] data = jedisCluster.get(key);
        return data;
    }

    public static List<ProductSkuPrice> toProductSkuPriceCacheList(byte[] data) {
        if (data == null) return null;
        List<ProductSkuPrice> list = (List<ProductSkuPrice>) SerializeUtil.unserialize(data);
        return list;
    }

    public static ProductSkuPrice getProductPriceByDate(List<ProductSkuPrice> list, Date now) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (ProductSkuPrice prices : list) {
            Date start = prices.getStartTime();
            Date end = prices.getEndTime();
            if (start.before(now) && end.after(now)) {
                return prices;
            }
        }
        return null;
    }

    public static ProductSkuPrice getNowProductSkuPrice(JedisCluster jedisCluster, Long skuId) throws JedisConnectionException {
        byte[] data = getProductFinalPrice(jedisCluster, skuId);
        if (data == null) return null;
        List<ProductSkuPrice> list = toProductSkuPriceCacheList(data);
        if (list == null) return null;
        ProductSkuPrice priceObject = getProductPriceByDate(list, new Date());
        return priceObject;
    }

    /**
     * 验证失败次数
     *
     * @param name 一般是loginName
     * @return -1 不合法 其它：合法次数
     */
    public static int getErrorCount(JedisCluster jedisCluster, String name, String ip) {
        String ipCount = jedisCluster.get(VALID_COUNT + ip);
        String nameCount = jedisCluster.get(VALID_COUNT + name);
        int count = 0;
        if (null != ipCount && (count = Integer.parseInt(ipCount)) >= MAX_IP_ERROR_COUNT) {
            return -1;
        }else if (nameCount != null
                && count < Integer.parseInt(nameCount)
                && (count = Integer.parseInt(nameCount)) >= MAX_NAME_ERROR_COUNT) {
            return -1;
        } else {
            return count;
        }
    }

    /**
     * 校验是否登陆频繁
     * @param jedisCluster  jedis
     * @param name          登陆名
     * @param ip            ip
     *
     * @return          true登陆频繁/false未到达上限
     */
    public static boolean validLogin(JedisCluster jedisCluster, String name, String ip) {
        return -1 == getErrorCount(jedisCluster, name, ip);
    }

    /**
     * 获取用户名登陆失败次数
     *
     * @param name 一般是loginName
     */
    public static int getNameErrorCount(JedisCluster jedisCluster, String name) {
        String nameCount = jedisCluster.get(VALID_COUNT + name);
        if (StringUtils.isNotBlank(nameCount)) {
            return Integer.parseInt(nameCount);
        }
        return 0;
    }

    /***
     * 失败次数自增,失败后调用
     * <note>
     *     用户名：2小时5次 IP：10分钟5次
     * </note>
     * @param key 一般是loginName
     * @param ip ip
     */
    public static boolean incrErrorCount(JedisCluster jedisCluster, String key, String ip) {
        boolean isOut = false;
        String nameKey = VALID_COUNT + key;
        long count = 0;
        if (1 == (count = jedisCluster.incr(nameKey))) {
            jedisCluster.expire(nameKey, 60 * 60 * 2);//2h
        }
        isOut = count + 1 > MAX_NAME_ERROR_COUNT;
        String ipKey = VALID_COUNT + ip;
        count = 0;
        if (1 == (count = jedisCluster.incr(ipKey))) {
            jedisCluster.expire(ipKey, 60 * 10);//10m
        }
        return isOut || (count + 1 > MAX_IP_ERROR_COUNT);
    }

}
