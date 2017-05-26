package com.pltfm.app.dao;

/**
 * 缓存接口(所有缓存均必须实现该接口)
 * 
 * @author lydawen 2009-11-27
 * 
 * 
 */
public interface RedisICacheClient {

  /**
   * 添加一个值,如果存在则失败
   * 
   * @param key
   * @param value
   * @return 如果存在则false
   */
  String set(String key, String value);

  /**
   * 添加一个值,如果存在则失败
   * 
   * @param key
   * @param value
   * @param expiry 过期时间(毫秒)
   * @return
   */

  String get(String key);


}
