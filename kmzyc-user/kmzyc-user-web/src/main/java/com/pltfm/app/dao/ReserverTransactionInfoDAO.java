package com.pltfm.app.dao;

import com.pltfm.app.vobject.ReserverTransactionInfo;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface ReserverTransactionInfoDAO {

  /**
   * 删除
   * 
   * @param transationId
   * @return
   * @throws SQLException
   */
  int deleteByPrimaryKey(BigDecimal transationId) throws SQLException;

  /**
   * 添加
   * 
   * @param record
   * @throws SQLException
   */
  void insert(ReserverTransactionInfo record) throws SQLException;

  /**
   * 查询
   * 
   * @param transationId
   * @return
   * @throws SQLException
   */
  ReserverTransactionInfo selectByPrimaryKey(BigDecimal transationId) throws SQLException;

  /**
   * 修改
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByPrimaryKeySelective(ReserverTransactionInfo record) throws SQLException;

}
