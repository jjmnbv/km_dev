package com.pltfm.app.dao.impl;

import javax.annotation.Resource;

import com.pltfm.app.dao.RedisICacheClient;

import redis.clients.jedis.JedisCluster;



/**
 * 缓存的一个实现类(client)
 * 
 * @author 2009-11-27
 * 
 * 
 */

public class RedisCacheClientImpl implements RedisICacheClient {

    @Resource
    private JedisCluster jedis;
    
  @Override
  public String set(String key, String value) {
    // TODO Auto-generated method stub
    return jedis.set(key, value);
  }

  @Override
  public String get(String key) {
    // TODO Auto-generated method stub
    return jedis.get(key);
  }


}
