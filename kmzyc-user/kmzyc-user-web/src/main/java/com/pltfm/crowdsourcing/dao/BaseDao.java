package com.pltfm.crowdsourcing.dao;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dataobject.BnesAcctTransListDO;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;

@Repository
@SuppressWarnings({"unchecked", "hiding"})
public class BaseDao<T extends Object> extends SqlMapClientDaoSupport {
  @Autowired
  public void setsqlMapClient4(SqlMapClient sqlMapClient) {
    super.setSqlMapClient(sqlMapClient);
  }

  @Resource
  protected SqlMapClient sqlMapClient;


  /**
   * 机构批量更新余额
   * 
   * @param acctTransactionStatement
   */
  public int batchInsert(final String accountStatement, final java.util.Map<T, T> accountInfoMap,
      final String institutionStatement, final List<T> institutionUserList,
      final String acctTransactionStatement, final List<T> bnesAcctTransactionQueryList,
      final String acctTransStatement, final List<BnesAcctTransListDO> bnesAcctTransListDOList)
      throws SQLException {
    try {
      if (!CollectionUtils.isEmpty(accountInfoMap) && !CollectionUtils.isEmpty(institutionUserList)
          && !CollectionUtils.isEmpty(bnesAcctTransactionQueryList)
          && !CollectionUtils.isEmpty(bnesAcctTransListDOList)) {
        // 开始事务
        sqlMapClient.startTransaction();
        sqlMapClient.startBatch();
        // 更新机构状态 交易流水
        for (int i = 0; i < institutionUserList.size(); i++) {
          sqlMapClient.update(institutionStatement, institutionUserList.get(i));
          Integer id = (Integer) sqlMapClient.insert(acctTransactionStatement,
              bnesAcctTransactionQueryList.get(i));
          BnesAcctTransListDO bt = bnesAcctTransListDOList.get(i);
          bt.setAccountTransactionId(id);
          sqlMapClient.insert(acctTransStatement, bt);

        }
        // 更新余额
        for (T key : accountInfoMap.keySet()) {
          sqlMapClient.update(accountStatement, accountInfoMap.get(key));
        }

        sqlMapClient.executeBatch();
        // 提交事务
        // sqlMapClient.commitTransaction();
        return 1;
      }
    } catch (Exception e) {
      throw new SQLException(e);
    } finally {
      sqlMapClient.endTransaction();
    }
    return 0;
  }



  /**
   * 批量更新（一次,无事务）
   * 
   * @param dataList
   */
  public int batchUpdateNt(final List<T> dataList, final String statement) throws SQLException {
    try {
      if (!CollectionUtils.isEmpty(dataList)) {
        sqlMapClient.startBatch();
        for (int i = 0; i < dataList.size(); i++) {
          sqlMapClient.update(statement, dataList.get(i));
        }
        sqlMapClient.executeBatch();
        return 1;
      }
    } catch (Exception e) {
      throw new SQLException(e);
    }
    return 0;
  }

  /**
   * 批量更新（一次）
   * 
   * @param statementName
   * @param parameterList
   * @return
   */
  public int batchUpdateData(final String statementName, final java.util.Map<T, T> parameterList)
      throws SQLException {
    try {
      if (!CollectionUtils.isEmpty(parameterList)) {
        sqlMapClient.startBatch();
        for (T key : parameterList.keySet()) {
          sqlMapClient.update(statementName, parameterList.get(key));
        }
        sqlMapClient.executeBatch();
        return 1;
      }
    } catch (Exception e) {
      // e.printStackTrace();
      // return 0;
      throw new SQLException(e);
    }
    return 0;
  }

  /**
   * 批量更新（一次）
   * 
   * @param statementName
   * @param statementNameOne
   * @return
   */
  public int batchInsertData(final String statementName, final String statementNameOne,
      final java.util.List<BnesAcctTransactionQuery> BnesAcctTransactionQueryList,
      final java.util.List<BnesAcctTransListDO> BnesAcctTransListDOList) throws SQLException {
    try {
      if (!CollectionUtils.isEmpty(BnesAcctTransactionQueryList)
          && !CollectionUtils.isEmpty(BnesAcctTransListDOList)) {
        sqlMapClient.startBatch();
        for (int i = 0; i < BnesAcctTransactionQueryList.size(); i++) {
          Integer id =
              (Integer) sqlMapClient.insert(statementName, BnesAcctTransactionQueryList.get(i));
          BnesAcctTransListDO bt = BnesAcctTransListDOList.get(i);
          bt.setAccountTransactionId(id);
          sqlMapClient.insert(statementNameOne, bt);
        }
        sqlMapClient.executeBatch();
        return 1;
      }
    } catch (Exception e) {
      // e.printStackTrace();
      // return 0;
      throw new SQLException(e);
    }
    return 0;
  }

}
