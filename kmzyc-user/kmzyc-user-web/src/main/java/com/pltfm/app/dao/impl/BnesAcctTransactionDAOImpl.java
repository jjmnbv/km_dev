package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BnesAcctTransactionDAO;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 数据访问对象实现类
 * 
 * @since 2013-07-17
 */
@SuppressWarnings("unchecked")
@Component(value = "bnesAcctTransactionDAO")
public class BnesAcctTransactionDAOImpl implements BnesAcctTransactionDAO {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  // 计算流水和
  public BigDecimal queryAmountAccount(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws SQLException {
    return (BigDecimal) sqlMapClient.queryForObject("BnesAcctTransaction.queryAmountAccount",
        bnesAcctTransactionQuery);
  }

  // 查询所有流水
  public List<BnesAcctTransactionQuery> selectTransactionAll(
      BnesAcctTransactionQuery bnesAcctTransactionQuery) throws SQLException {
    return sqlMapClient.queryForList("BnesAcctTransaction.selectTransactionAll",
        bnesAcctTransactionQuery);
  }

  /**
   * 获取对象列表
   * 
   * @param bnesAcctTransactionQuery
   * @return 对象列表
   */

  public List<BnesAcctTransactionQuery> findListByExample(
      BnesAcctTransactionQuery bnesAcctTransactionQuery) throws DataAccessException {
    List<BnesAcctTransactionQuery> list = null;
    try {
      list = sqlMapClient.queryForList("BnesAcctTransaction.findListByQuery",
          bnesAcctTransactionQuery);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  /**
   * 统计记录数
   * 
   * @param bnesAcctTransactionQuery
   * @return 查出的记录数
   */
  public Integer countBnesAcctTransactionQueryByExample(
      BnesAcctTransactionQuery bnesAcctTransactionQuery) throws DataAccessException {
    int recs = 0;
    try {
      List list = sqlMapClient.queryForList("BnesAcctTransaction.findListByQueryList",
          bnesAcctTransactionQuery);
      SysModelUtil countResult = (SysModelUtil) list.get(0);
      // 总条数
      recs = countResult.getTheCount().intValue();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return recs;
  }


  /**
   * 统计记录数
   * 
   * @param bnesAcctTransactionQuery
   * @return 查出的记录数findSumListByQueryList
   */

  public Integer sumBnesAcctTransactionQueryByExample(
      BnesAcctTransactionQuery bnesAcctTransactionQuery) throws DataAccessException {
    int recs = 0;
    try {
      List list = sqlMapClient.queryForList("BnesAcctTransaction.findSumListByQueryList",
          bnesAcctTransactionQuery);
      SysModelUtil countResult = (SysModelUtil) list.get(0);
      // 总条数
      if (countResult.getTheCount() != null) {
        recs = countResult.getTheCount().intValue();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return recs;

  }

  /**
   * 分页查询查询
   * 
   * @param bnesAcctTransactionDO
   * @return
   * @throws SQLException
   */

  public List<BnesAcctTransactionQuery> findListByPageExample(
      BnesAcctTransactionQuery bnesAcctTransactionQuery) throws SQLException {

    List<BnesAcctTransactionQuery> pageList =
        sqlMapClient.queryForList("BnesAcctTransaction.findListByQuery", bnesAcctTransactionQuery);

    return pageList;
  }

  /**
   * 新增充值
   * 
   * @param bnesAcctTransactionDO
   * @return 插入数据的主键
   */
  @Override
  public Integer insertBnesAcctTransactionDO(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws Exception {
   return (Integer) sqlMapClient.insert("BnesAcctTransaction.insert", bnesAcctTransactionQuery);
  }

  /**
   * 修改充值
   * 
   * @param bnesAcctTransactionDO
   * @return 受影响的行数
   */
  @Override
  public Integer updateBnesAcctTransactionDO(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws DataAccessException {
    int result = 0;
    try {
      result = sqlMapClient.update("BnesAcctTransaction.update", bnesAcctTransactionQuery);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;

  }

  /**
   * 查询充值信息
   * 
   * @param bnesAcctTransactionDO
   * @return bnesAcctTransactionQuery
   */
  @Override
  public BnesAcctTransactionQuery findById(Integer accountTransactionId)
      throws DataAccessException {
    BnesAcctTransactionQuery bnesAcctTransactionQuery = null;
    try {
      bnesAcctTransactionQuery = (BnesAcctTransactionQuery) sqlMapClient
          .queryForObject("BnesAcctTransaction.findByPrimaryKey", accountTransactionId);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return bnesAcctTransactionQuery;

  }

  /**
   * 删除记录
   * 
   * @param accountTransactionId
   * @return 受影响的行数
   */
  @Override
  public Integer deleteBnesAcctTransactionDOByPrimaryKey(Integer accountTransactionId)
      throws DataAccessException {

    Integer rows = null;
    try {
      rows = (Integer) sqlMapClient.delete("BnesAcctTransaction.deleteByPrimaryKey",
          accountTransactionId);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rows;
  }

}
