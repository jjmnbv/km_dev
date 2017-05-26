package com.pltfm.app.dao.impl;


import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BnesResPermissionDAO;
import com.pltfm.app.dataobject.BnesResPermissionDO;
import com.pltfm.app.vobject.BnesResPermissionQuery;

/**
 * 数据访问对象实现类
 * 
 * @since 2013-07-17
 */
@Component(value = "bnesResPermissionDAO")
public class BnesResPermissionDAOImpl implements BnesResPermissionDAO {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 插入数据
   * 
   * @param bnesResPermissionDO
   * @return 插入数据的主键
   */
  public Integer insertBnesResPermissionDO(BnesResPermissionQuery bnesResPermissionQuery)
      throws DataAccessException {
    Object RES_PERMISSION_ID = 0;
    try {
      RES_PERMISSION_ID = sqlMapClient.insert("BnesResPermission.insert", bnesResPermissionQuery);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return (Integer) RES_PERMISSION_ID;
  }

  /**
   * 删除记录
   * 
   * @param customerTypeId
   * @return 受影响的行数
   */
  public Integer deleteBnesResPermissionDOByPrimaryKey(Integer customerTypeId)
      throws DataAccessException {
    Integer rows = null;
    try {
      rows = (Integer) sqlMapClient.delete("BnesResPermission.deleteByPrimaryKey", customerTypeId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rows;
  }

  /**
   * 获取业务开通权限
   * 
   * @param bnesResPermissionQuery
   * @return List<BnesResPermissionQuery>
   */
  @SuppressWarnings("unchecked")
  public List<BnesResPermissionQuery> findListByExample(
      BnesResPermissionQuery bnesResPermissionQuery) throws DataAccessException {
    List<BnesResPermissionQuery> list = null;
    try {
      list = sqlMapClient.queryForList("BnesResPermission.findListByQuery", bnesResPermissionQuery);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }

  // --------------------------------------------------------
  /**
   * 统计记录数
   * 
   * @param bnesResPermissionDO
   * @return 查出的记录数
   */
  public Integer countBnesResPermissionDOByExample(BnesResPermissionDO bnesResPermissionDO)
      throws DataAccessException {
    Integer count = null;
    try {
      count = (Integer) sqlMapClient.queryForObject("BnesResPermission.countByDOExample",
          bnesResPermissionDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return count;
  }

  /**
   * 统计记录数
   * 
   * @param bnesResPermissionQuery
   * @return 查出的记录数
   */
  public Integer countBnesResPermissionQueryByExample(BnesResPermissionQuery bnesResPermissionQuery)
      throws DataAccessException {
    Integer count = 0;
    try {
      count = (Integer) sqlMapClient.queryForObject("BnesResPermission.countByQueryExample",
          bnesResPermissionQuery);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return count;
  }

  /**
   * 更新记录
   * 
   * @param bnesResPermissionDO
   * @return 受影响的行数
   */
  public Integer updateBnesResPermissionDO(BnesResPermissionDO bnesResPermissionDO)
      throws DataAccessException {
    int result = 0;
    try {
      result = sqlMapClient.update("BnesResPermission.update", bnesResPermissionDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 获取对象列表
   * 
   * @param bnesResPermissionDO
   * @return 对象列表
   */
  @SuppressWarnings("unchecked")
  public List<BnesResPermissionDO> findListByExample(BnesResPermissionDO bnesResPermissionDO)
      throws DataAccessException {
    List<BnesResPermissionDO> list = null;
    try {
      list = sqlMapClient.queryForList("BnesResPermission.findListByDO", bnesResPermissionDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }


  /**
   * 根据主键获取bnesResPermissionDO
   * 
   * @param resPermissionId
   * @return bnesResPermissionDO
   */
  public BnesResPermissionDO findBnesResPermissionDOByPrimaryKey(Integer resPermissionId)
      throws DataAccessException {
    BnesResPermissionDO bnesResPermissionDO = null;
    try {
      bnesResPermissionDO = (BnesResPermissionDO) sqlMapClient
          .queryForObject("BnesResPermission.findByPrimaryKey", resPermissionId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return bnesResPermissionDO;
  }



  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }
}
