package com.kmzyc.b2b.dao.member;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.model.OrderSync;

public interface OrderSyncDAO {
  int deleteByPrimaryKey(Long syncId) throws SQLException;

  void insert(OrderSync record) throws SQLException;

  void insertSelective(OrderSync record) throws SQLException;

  OrderSync selectByPrimaryKey(Long syncId) throws SQLException;

  int updateByPrimaryKeySelective(OrderSync record) throws SQLException;

  int updateByPrimaryKey(OrderSync record) throws SQLException;

  List<OrderSync> selectOrderSyncByOrderCode(String orderCode) throws SQLException;
}
