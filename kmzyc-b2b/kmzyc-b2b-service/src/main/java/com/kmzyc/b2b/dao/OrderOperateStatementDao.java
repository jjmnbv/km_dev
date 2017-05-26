package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.model.OrderOperateStatement;
import com.kmzyc.framework.exception.DaoException;

public interface OrderOperateStatementDao {
  List<OrderOperateStatement> findByOrderNo(String orderNo) throws SQLException, DaoException;

  /**
   * 
   * @param orderNo
   * @return
   * @throws SQLException
   * @throws DaoException
   */
  List<OrderOperateStatement> findByOrderNoForView(String orderNo) throws SQLException,
      DaoException;
}
