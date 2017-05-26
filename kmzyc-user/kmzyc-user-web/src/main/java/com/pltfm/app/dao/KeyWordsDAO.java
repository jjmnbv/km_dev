package com.pltfm.app.dao;


import com.pltfm.app.dataobject.KeyWordsDO;
import com.pltfm.app.vobject.KeyWordsQuery;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据访问对象接口
 * 
 * @since 2013-10-24
 */
public interface KeyWordsDAO {

  /**
   * 插入数据
   * 
   * @param keyWordsDO
   * @return 插入数据的主键
   * @throws SQLException
   */
  public Integer insertKeyWordsDO(KeyWordsDO keyWordsDO) throws SQLException;

  /**
   * 统计记录数
   * 
   * @param keyWordsDO
   * @return 查出的记录数
   */
  public Integer countKeyWordsDOByExample(KeyWordsDO keyWordsDO) throws SQLException;;

  /**
   * 统计记录数
   * 
   * @param keyWordsQuery
   * @return 查出的记录数
   */
  public Integer countKeyWordsQueryByExample(KeyWordsQuery keyWordsQuery) throws SQLException;;

  /**
   * 更新记录
   * 
   * @param keyWordsDO
   * @return 受影响的行数
   */
  public Integer updateKeyWordsDO(KeyWordsDO keyWordsDO) throws SQLException;;

  /**
   * 获取对象列表
   * 
   * @param keyWordsDO
   * @return 对象列表
   */
  public List<KeyWordsDO> findListByExample(KeyWordsDO keyWordsDO) throws SQLException;;

  /**
   * 获取对象列表
   * 
   * @param keyWordsQuery
   * @return 对象列表
   */
  public List<KeyWordsQuery> findListByExample(KeyWordsQuery keyWordsQuery) throws SQLException;;

  /**
   * 根据主键获取keyWordsDO
   * 
   * @param keyWordsId
   * @return keyWordsDO
   */
  public KeyWordsDO findKeyWordsDOByPrimaryKey(Integer keyWordsId) throws SQLException;;

  /**
   * 删除记录
   * 
   * @param keyWordsId
   * @return 受影响的行数
   */
  public Integer deleteKeyWordsDOByPrimaryKey(Integer keyWordsId) throws SQLException;;

}
