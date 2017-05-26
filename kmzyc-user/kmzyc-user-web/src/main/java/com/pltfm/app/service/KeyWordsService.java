package com.pltfm.app.service;



import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dataobject.KeyWordsDO;
import com.pltfm.app.vobject.KeyWordsQuery;

/**
 * 数据访问对象接口
 * 
 * @since 2013-10-24
 */
public interface KeyWordsService {

  /**
   * 插入数据
   * 
   * @param keyWordsDO
   * @return 插入数据的主键
   */
  public Integer insertKeyWordsDO(KeyWordsDO keyWordsDO);

  /**
   * 统计记录数
   * 
   * @param keyWordsDO
   * @return 查出的记录数
   */
  public Integer countKeyWordsDOByExample(KeyWordsDO keyWordsDO);

  /**
   * 分页统计记录数
   * 
   * @param keyWordsQuery
   * @return 查出的记录数
   */
  public Page findKeyWordsQueryByExample(Page pageParam, KeyWordsQuery keyWordsQuery);

  /**
   * 统计记录数
   * 
   * @param keyWordsQuery
   * @return 查出的记录数
   */
  public Integer countKeyWordsQueryByExample(KeyWordsQuery keyWordsQuery);

  /**
   * 更新记录
   * 
   * @param keyWordsDO
   * @return 受影响的行数
   */
  public Integer updateKeyWordsDO(KeyWordsDO keyWordsDO);

  /**
   * 获取对象列表
   * 
   * @param keyWordsDO
   * @return 对象列表
   */
  public List<KeyWordsDO> findListByExample(KeyWordsDO keyWordsDO);

  /**
   * 获取对象列表
   * 
   * @param keyWordsQuery
   * @return 对象列表
   */
  public List<KeyWordsQuery> findListByExample(KeyWordsQuery keyWordsQuery);

  /**
   * 根据主键获取keyWordsDO
   * 
   * @param keyWordsId
   * @return keyWordsDO
   */
  public KeyWordsDO findKeyWordsDOByPrimaryKey(Integer keyWordsId);

  /**
   * 删除记录
   * 
   * @param keyWordsId
   * @return 受影响的行数
   */
  public Integer deleteKeyWordsDOByPrimaryKey(Integer keyWordsId);

  /**
   * 删除全部记录
   * 
   * @param keyWordsId
   * @return 受影响的行数
   */
  public Integer deleteAllKeyWords(List<Integer> keyWordsIds);

  /**
   * 
   * @param keyWordsId
   * @return 受影响的行数
   */
  public String getkey(String key);

  /**
   * 
   * @param keyWordsId
   * @return 受影响的行数
   */
  public String getRedisKeys(String key);

  public String set(byte[] key, byte[] value);

  public byte[] get(byte[] key);

  public Integer setAllRedisKeys(short WordsType);

  public Long del(String key);

}
