package com.pltfm.app.dao;

public interface RedisCache {
  public <T> T getRedisCacheInfo(String key);

  public <T> boolean setRedisCacheInfo(String key, T value);

  public String setShardedJedis(byte[] key, byte[] value);

  public byte[] get(byte[] key);

  public Long del(String key);

  public String setex(String key, int seconds, String value);

}
