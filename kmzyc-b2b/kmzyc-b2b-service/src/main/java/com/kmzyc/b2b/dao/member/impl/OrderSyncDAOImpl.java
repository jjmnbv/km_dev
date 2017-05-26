package com.kmzyc.b2b.dao.member.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.member.OrderSyncDAO;
import com.kmzyc.b2b.model.OrderSync;

@Component
public class OrderSyncDAOImpl implements OrderSyncDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  public OrderSyncDAOImpl() {

  }

  public OrderSyncDAOImpl(SqlMapClient sqlMapClient) {
    super();
    this.sqlMapClient = sqlMapClient;
  }

  public int deleteByPrimaryKey(Long syncId) throws SQLException {
    OrderSync key = new OrderSync();
    key.setSyncId(syncId);
    int rows = sqlMapClient.delete("ORDER_SYNC.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  public void insert(OrderSync record) throws SQLException {
    sqlMapClient.insert("ORDER_SYNC.ibatorgenerated_insert", record);
  }

  public void insertSelective(OrderSync record) throws SQLException {
    sqlMapClient.insert("ORDER_SYNC.ibatorgenerated_insertSelective", record);
  }

  public OrderSync selectByPrimaryKey(Long syncId) throws SQLException {
    OrderSync key = new OrderSync();
    key.setSyncId(syncId);
    OrderSync record =
        (OrderSync) sqlMapClient.queryForObject("ORDER_SYNC.ibatorgenerated_selectByPrimaryKey",
            key);
    return record;
  }


  public int updateByPrimaryKeySelective(OrderSync record) throws SQLException {
    int rows =
        sqlMapClient.update("ORDER_SYNC.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  public int updateByPrimaryKey(OrderSync record) throws SQLException {
    int rows = sqlMapClient.update("ORDER_SYNC.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  @Override
  public List<OrderSync> selectOrderSyncByOrderCode(String orderCode) throws SQLException {
    List<OrderSync> list =
        sqlMapClient.queryForList("ORDER_SYNC.selectOrderSyncByOrderCode", orderCode);
    return list;
  }
}
