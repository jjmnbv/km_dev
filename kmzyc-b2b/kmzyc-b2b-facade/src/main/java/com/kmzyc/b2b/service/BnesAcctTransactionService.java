package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.BnesAcctTransaction;
import com.kmzyc.framework.exception.ServiceException;
import com.km.framework.page.Pagination;

public interface BnesAcctTransactionService {
  public List<BnesAcctTransaction> getBnesAcctTransactionById(long id) throws ServiceException;

  /**
   * 分页查询账户余额明细信息
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  public Pagination findAcctBalanceByUserId(Pagination page) throws SQLException;

  /**
   * 查询账户未充值的充值单
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public List<BnesAcctTransaction> queryTransaction(Map<String, String> map)
      throws ServiceException;

  public BnesAcctTransaction queryTransationExist(Map<String, String> map) throws ServiceException;

  public BnesAcctTransaction findByAccountNumber(String orderCode) throws ServiceException;

  public boolean isFirstRecharge(int accountId) throws ServiceException;
}
