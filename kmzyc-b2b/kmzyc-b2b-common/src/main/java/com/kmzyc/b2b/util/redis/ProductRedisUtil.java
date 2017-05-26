package com.kmzyc.b2b.util.redis;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.JedisCluster;

/**
 * 获取缓存产品信息
 * 
 * @author KM
 *
 */
@Component
public class ProductRedisUtil {

  private static Logger log = LoggerFactory.getLogger(ProductRedisUtil.class);

  @Resource(name = "jedisCluster")
 private JedisCluster jedisCluster;


  /**
   * SKU 缓存的KEY
   */
  private final static String SKU_DETAIL_KEY = "sku_details";


  /**
   * 根据产品SKUID获取SKU详细信息
   * 
   * @param productSkuId
   * @return
   */
  public JSONObject getProductSkuDetail(String productSkuId) {
    JSONObject json = null;
    try {
      String str = jedisCluster.hget(SKU_DETAIL_KEY, productSkuId);
      json = JSONObject.parseObject(str);

    } catch (Exception e) {
      log.error("redis获取缓存SKU信息异常：skuid:{}", productSkuId);
    }
    return json;
  }
}
