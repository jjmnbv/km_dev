package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.BnesAcctTransaction;

public interface BnesAcctTransactionDAO extends Dao {

  public List<BnesAcctTransaction> getBnesAcctTransactionById(long id) throws SQLException;

  public List<BnesAcctTransaction> queryTransaction(Map<String, String> map) throws SQLException;

  public BnesAcctTransaction queryTransationExist(Map<String, String> map) throws SQLException;

  public BnesAcctTransaction findByAccountNumber(String orderCode) throws SQLException;

  public int isFirstRecharge(int accountId) throws SQLException;

  /**
   * 校验充值单、用户是否匹配
   * 
   * @param userId
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public Integer checkOrderUser(Map<String, Object> params) throws SQLException;
}
