package com.pltfm.app.dao;

import com.pltfm.app.vobject.BnesAcctTransactionQuery;

import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * 数据访问对象接口
 * 
 * @since 2013-07-17
 */
public interface BnesAcctTransactionDAO {
  /**
   * 计算流水和
   * 
   * @param bnesAcctTransactionQuery
   * @return
   * @throws SQLException
   */
  BigDecimal queryAmountAccount(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws SQLException;

  /**
   * 查询所有流水
   * 
   * @param bnesAcctTransactionQuery
   * @return
   */
  List<BnesAcctTransactionQuery> selectTransactionAll(
      BnesAcctTransactionQuery bnesAcctTransactionQuery) throws SQLException;

  /**
   * 查询
   * 
   * @param bnesAcctTransactionQuery
   * @return
   */
  List<BnesAcctTransactionQuery> findListByExample(
      BnesAcctTransactionQuery bnesAcctTransactionQuery) throws DataAccessException;


  /**
   * 统计记录数
   * 
   * @param bnesAcctTransactionQuery
   * @return 查出的记录数
   */

  Integer countBnesAcctTransactionQueryByExample(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws DataAccessException;

  /**
   * 统计记录数
   * 
   * @param bnesAcctTransactionQuery
   * @return 查出的记录数findSumListByQueryList
   */

  Integer sumBnesAcctTransactionQueryByExample(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws DataAccessException;



  /**
   * 分页查询查询
   * 
   * @param bnesAcctTransactionDO
   * @return
   * @throws SQLException
   */

  List<BnesAcctTransactionQuery> findListByPageExample(
      BnesAcctTransactionQuery bnesAcctTransactionQuery) throws DataAccessException, SQLException;

  /**
   * 新增充值
   * 
   * @param bnesAcctTransactionDO
   * @return 插入数据的主键
   */
  Integer insertBnesAcctTransactionDO(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws Exception;


  /**
   * 修改充值
   * 
   * @param bnesAcctTransactionDO
   * @return 受影响的行数
   */

  Integer updateBnesAcctTransactionDO(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws DataAccessException;


  /**
   * 查询充值信息
   * 
   * @param bnesAcctTransactionDO
   * @return bnesAcctTransactionQuery
   */
  BnesAcctTransactionQuery findById(Integer accountTransactionId) throws DataAccessException;

  /**
   * 删除记录
   * 
   * @param accountTransactionId
   * @return 受影响的行数
   */

  Integer deleteBnesAcctTransactionDOByPrimaryKey(Integer accountTransactionId)
      throws DataAccessException;

}
