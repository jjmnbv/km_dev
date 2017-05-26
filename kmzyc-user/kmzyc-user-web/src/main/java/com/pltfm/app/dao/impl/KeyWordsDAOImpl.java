package com.pltfm.app.dao.impl;



import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.KeyWordsDAO;
import com.pltfm.app.dataobject.KeyWordsDO;
import com.pltfm.app.vobject.KeyWordsQuery;

/**
 * 数据访问对象实现类
 * 
 * @since 2013-10-24
 */
@Component(value = "keyWordsDAO")
public class KeyWordsDAOImpl implements KeyWordsDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;



  /**
   * 插入数据
   * 
   * @param keyWordsDO
   * @return 插入数据的主键
   */
  public Integer insertKeyWordsDO(KeyWordsDO keyWordsDO) {
    Integer KEY_WORDS_ID = 0;
    try {
      KEY_WORDS_ID = (Integer) sqlMapClient.insert("KeyWords.insert", keyWordsDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return (Integer) KEY_WORDS_ID;
  }

  /**
   * 统计记录数
   * 
   * @param keyWordsDO
   * @return 查出的记录数
   */
  public Integer countKeyWordsDOByExample(KeyWordsDO keyWordsDO) {
    Integer count = 0;
    try {
      count = (Integer) sqlMapClient.queryForObject("KeyWords.countByDOExample", keyWordsDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return count;
  }

  /**
   * 统计记录数
   * 
   * @param keyWordsQuery
   * @return 查出的记录数
   */
  public Integer countKeyWordsQueryByExample(KeyWordsQuery keyWordsQuery) {
    Integer count = 0;
    try {
      count = (Integer) sqlMapClient.queryForObject("KeyWords.countByQueryExample", keyWordsQuery);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return count;
  }

  /**
   * 更新记录
   * 
   * @param keyWordsDO
   * @return 受影响的行数
   */
  public Integer updateKeyWordsDO(KeyWordsDO keyWordsDO) {
    int result = 0;
    try {
      result = sqlMapClient.update("KeyWords.update", keyWordsDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 获取对象列表
   * 
   * @param keyWordsDO
   * @return 对象列表
   */
  @SuppressWarnings("unchecked")
  public List<KeyWordsDO> findListByExample(KeyWordsDO keyWordsDO) {
    List<KeyWordsDO> list = null;
    try {
      list = sqlMapClient.queryForList("KeyWords.findListByDO", keyWordsDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }

  /**
   * 获取对象列表
   * 
   * @param keyWordsQuery
   * @return 对象列表
   */
  @SuppressWarnings("unchecked")
  public List<KeyWordsQuery> findListByExample(KeyWordsQuery keyWordsQuery) {
    List<KeyWordsQuery> list = null;
    try {
      list = sqlMapClient.queryForList("KeyWords.findListByQuery", keyWordsQuery);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }

  /**
   * 根据主键获取keyWordsDO
   * 
   * @param keyWordsId
   * @return keyWordsDO
   */
  public KeyWordsDO findKeyWordsDOByPrimaryKey(Integer keyWordsId) {
    KeyWordsDO keyWordsDO = null;
    try {
      keyWordsDO =
          (KeyWordsDO) sqlMapClient.queryForObject("KeyWords.findByPrimaryKey", keyWordsId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return keyWordsDO;
  }

  /**
   * 删除记录
   * 
   * @param keyWordsId
   * @return 受影响的行数
   */
  public Integer deleteKeyWordsDOByPrimaryKey(Integer keyWordsId) {
    Integer rows = 0;
    try {
      rows = (Integer) sqlMapClient.delete("KeyWords.deleteByPrimaryKey", keyWordsId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rows;
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

}
