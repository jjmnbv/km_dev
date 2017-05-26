package com.pltfm.app.dao.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pltfm.app.dao.RedisCache;
import com.pltfm.app.util.SerializeUtil;

import redis.clients.jedis.JedisCluster;



@Component(value = "redisCacheManager")
public class RedisCacheManger implements RedisCache {
 
    @Resource
    private JedisCluster jedis;

  private Logger log = LoggerFactory.getLogger(RedisCacheManger.class);

 

  public <T> T getRedisCacheInfo(String key) {

    try {
      log.info("get from redisCache :" + key);
      return (T) jedis.get(key);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  public <T> boolean setRedisCacheInfo(String key, T value) {

    try {
      log.info("add to redisCache :" + key);
      // System.out.println("add to rediscache");
      jedis.set(key, (String) value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static void main(String[] args) {
    new RedisCacheManger().setRedisCacheInfo("12345", "asdfg");
    // System.out.println("----------------------------------"+new
    // RedisCacheManger().getRedisCacheInfo("12345"));
    // new RedisCacheManger().del("12345");
  }


  public String setShardedJedis(byte[] key, byte[] value) {
    try {
      log.info("add to redisCache :" + SerializeUtil.unserialize(key));
      return jedis.set(key, value);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;

  }

  public byte[] get(byte[] key) {
    try {
      log.info("add to redisCache :" + SerializeUtil.unserialize(key));
      return jedis.get(key);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public Long del(String key) {
    try {
      log.info("delete to redisCache :" + key);


      return jedis.del(key);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;

  }

  public String setex(String key, int seconds, String value) {
    try {
      log.info("set to redisCache :" + key);
      return jedis.setex(key, seconds, value);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


}
