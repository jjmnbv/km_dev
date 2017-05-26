package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kmzyc.b2b.dao.BnesAcctTransactionDAO;
import com.kmzyc.b2b.model.BnesAcctTransaction;
import com.km.framework.persistence.impl.DaoImpl;

@SuppressWarnings("unchecked")
@Component
public class BnesAcctTransactionDAOImpl extends DaoImpl implements BnesAcctTransactionDAO {
  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  @Override
  public List<BnesAcctTransaction> getBnesAcctTransactionById(long id) throws SQLException {
    return this.sqlMapClient.queryForList("BnesAcctTransaction.findById", id);
  }

  public List<BnesAcctTransaction> queryTransaction(Map<String, String> map) throws SQLException {
    return (List<BnesAcctTransaction>) this.sqlMapClient.queryForList(
        "BnesAcctTransaction.queryTransaction", map);
  }

  @Override
  public BnesAcctTransaction queryTransationExist(Map<String, String> map) throws SQLException {
    return (BnesAcctTransaction) this.sqlMapClient.queryForObject(
        "BnesAcctTransaction.queryTransationExist", map);
  }


  @Override
  public BnesAcctTransaction findByAccountNumber(String orderCode) throws SQLException {
    return (BnesAcctTransaction) this.sqlMapClient.queryForObject(
        "BnesAcctTransaction.findByAccountNumber", orderCode);
  }

  @Override
  public int isFirstRecharge(int accountId) throws SQLException {
    return (Integer) this.sqlMapClient.queryForObject("BnesAcctTransaction.queryCountTransation",
        accountId);
  }

  /**
   * 校验充值单、用户是否匹配
   * 
   * @param userId
   * @param orderCode
   * @return
   * @throws SQLException
   */
  @Override
  public Integer checkOrderUser(Map<String, Object> params) throws SQLException {
    return (Integer) this.sqlMapClient.queryForObject(
        "BnesAcctTransaction.SQL_QUERY_CHECK_ORDER_USER", params);
  }
}
