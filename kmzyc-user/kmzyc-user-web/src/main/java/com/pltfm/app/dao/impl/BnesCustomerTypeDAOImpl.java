package com.pltfm.app.dao.impl;


import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BnesCustomerTypeDAO;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;

/**
 * 数据访问对象实现类
 * 
 * @since 2013-07-17
 */
@Component(value = "bnesCustomerTypeDAO")
public class BnesCustomerTypeDAOImpl implements BnesCustomerTypeDAO {


  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 客户类别查询
   * 
   * @param bnesCustomerTypeQuery
   * @return BnesCustomerTypeQuery
   */

  @SuppressWarnings("unchecked")
  public List<BnesCustomerTypeQuery> findListByExample(BnesCustomerTypeQuery bnesCustomerTypeQuery)
      throws DataAccessException {
    List<BnesCustomerTypeQuery> list = null;
    try {
      list = sqlMapClient.queryForList("BnesCustomerType.findListByQuery", bnesCustomerTypeQuery);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }

  /**
   * 根据主键获取查询客户类别下所有成员
   * 
   * @param customerTypeId
   * @return List<BnesCustomerTypeQuery>
   */
  @SuppressWarnings("unchecked")
  public List<BnesCustomerTypeQuery> findListBnesCustTypeById(Integer customerTypeId)
      throws DataAccessException {
    List<BnesCustomerTypeQuery> list = null;
    try {
      list = sqlMapClient.queryForList("BnesCustomerType.findListByQueryById", customerTypeId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }

  /**
   * 客户类别明细查询
   * 
   * @param bnesCustomerTypeQuery
   * @return 插入数据的主键
   */

  public BnesCustomerTypeQuery findBnesCustomerTypeDOByPrimaryKey(Integer customerTypeId)
      throws DataAccessException {
    BnesCustomerTypeQuery bnesCustomerTypeQuery = null;
    try {
      bnesCustomerTypeQuery = (BnesCustomerTypeQuery) sqlMapClient
          .queryForObject("BnesCustomerType.findByPrimaryKey", customerTypeId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return bnesCustomerTypeQuery;
  }

  /**
   * 修改客户
   * 
   * @param BnesCustomerTypeQuery
   * @return Integer受影响的行数
   */

  public Integer updateBnesCustomerTypeDO(BnesCustomerTypeQuery bnesCustomerTypeQuery)
      throws DataAccessException {
    int result = 0;
    try {
      result = sqlMapClient.update("BnesCustomerType.update", bnesCustomerTypeQuery);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }


  /**
   * 查询客户类别是否有客户成员
   * 
   * @param BnesCustomerTypeQuery
   * @return List
   */

  @SuppressWarnings("unchecked")
  public List<BnesCustomerTypeQuery> findParentList(Integer customerTypeId)
      throws DataAccessException {
    List<BnesCustomerTypeQuery> list = null;

    try {
      list = sqlMapClient.queryForList("BnesCustomerType.findParentQuery", customerTypeId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return list;

  }


  /**
   * 刪除客户成员
   * 
   * @param customerTypeId
   * @return 受影响的行数
   */

  public Integer deleteBnesCustomerTypeDOByPrimaryKey(Integer customerTypeId)
      throws DataAccessException {
    Integer rows = 0;
    try {
      rows = (Integer) sqlMapClient.delete("BnesCustomerType.deleteByPrimaryKey", customerTypeId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rows;

  }


  /**
   * 新增客户類別成员
   * 
   * @param customerTypeId
   * @return 受影响的行数
   */

  public Integer insertBnesCustomerTypeDO(BnesCustomerTypeQuery bnesCustomerTypeQuery)
      throws DataAccessException {
    Object CUSTOMER_TYPE_ID = null;
    try {
      CUSTOMER_TYPE_ID = sqlMapClient.insert("BnesCustomerType.insert", bnesCustomerTypeQuery);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return (Integer) CUSTOMER_TYPE_ID;

  }


  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

}
