package com.pltfm.app.dao.impl;


import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BnesFunctionsDAO;
import com.pltfm.app.dataobject.BnesFunctionsDO;
import com.pltfm.app.vobject.BnesFunctionsQuery;

/**
 * 数据访问对象实现类
 * 
 * @since 2013-07-17
 */
@Component(value = "bnesFunctionsDAO")
public class BnesFunctionsDAOImpl implements BnesFunctionsDAO {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;


  /**
   * 获取对象列表
   * 
   * @param bnesFunctionsQuery
   * @return 对象列表
   */
  @SuppressWarnings("unchecked")
  public List<BnesFunctionsQuery> findListByExample(BnesFunctionsQuery bnesFunctionsQuery)
      throws DataAccessException {
    List<BnesFunctionsQuery> list = null;
    try {
      list = sqlMapClient.queryForList("BnesFunctions.findListByQuery", bnesFunctionsQuery);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }

  /**
   * 根据主键获取bnesFunctionsDO
   * 
   * @param binesFunctionId
   * @return bnesFunctionsDO
   */
  public List<BnesFunctionsQuery> findBnesFunctionsDOByPrimaryKey(Integer binesFunctionId)
      throws DataAccessException {
    List<BnesFunctionsQuery> list = null;
    try {
      list = sqlMapClient.queryForList("BnesFunctions.findByPrimaryKey", binesFunctionId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }

  /**
   * 获取对象列表 ================================================================
   */

  /**
   * 插入数据
   * 
   * @param bnesFunctionsDO
   * @return 插入数据的主键
   */
  public Integer insertBnesFunctionsDO(BnesFunctionsDO bnesFunctionsDO) throws DataAccessException {
    Object BINES_FUNCTION_ID = null;
    try {
      BINES_FUNCTION_ID = sqlMapClient.insert("BnesFunctions.insert", bnesFunctionsDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return (Integer) BINES_FUNCTION_ID;
  }

  /**
   * 统计记录数
   * 
   * @param bnesFunctionsDO
   * @return 查出的记录数
   */
  public Integer countBnesFunctionsDOByExample(BnesFunctionsDO bnesFunctionsDO)
      throws DataAccessException {
    Integer count = null;
    try {
      count =
          (Integer) sqlMapClient.queryForObject("BnesFunctions.countByDOExample", bnesFunctionsDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return count;
  }

  /**
   * 统计记录数
   * 
   * @param bnesFunctionsQuery
   * @return 查出的记录数
   */
  public Integer countBnesFunctionsQueryByExample(BnesFunctionsQuery bnesFunctionsQuery)
      throws DataAccessException {
    Integer count = null;
    try {
      count = (Integer) sqlMapClient.queryForObject("BnesFunctions.countByQueryExample",
          bnesFunctionsQuery);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return count;
  }

  /**
   * 更新记录
   * 
   * @param bnesFunctionsDO
   * @return 受影响的行数
   */
  public Integer updateBnesFunctionsDO(BnesFunctionsDO bnesFunctionsDO) throws DataAccessException {
    int result = 0;
    try {
      result = sqlMapClient.update("BnesFunctions.update", bnesFunctionsDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 获取对象列表
   * 
   * @param bnesFunctionsDO
   * @return 对象列表
   */
  @SuppressWarnings("unchecked")
  public List<BnesFunctionsDO> findListByExample(BnesFunctionsDO bnesFunctionsDO)
      throws DataAccessException {
    List<BnesFunctionsDO> list = null;
    try {
      list = sqlMapClient.queryForList("BnesFunctions.findListByDO", bnesFunctionsDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }



  /**
   * 删除记录
   * 
   * @param binesFunctionId
   * @return 受影响的行数
   */
  public Integer deleteBnesFunctionsDOByPrimaryKey(Integer binesFunctionId)
      throws DataAccessException {
    Integer rows = null;
    try {
      rows = (Integer) sqlMapClient.delete("BnesFunctions.deleteByPrimaryKey", binesFunctionId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rows;
  }

  // ---------------------------------------------------------------------------------------------
  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

}
