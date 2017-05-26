package com.pltfm.app.dao.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.OrderCancelDAO;
import com.pltfm.app.entities.OrderCancelReason;

@SuppressWarnings("unchecked")
@Repository("orderCancelDAO")
public class OrderCancelDAOImpl extends BaseDAO implements OrderCancelDAO {

  @Override
  public Integer saveOrderCancelReason(OrderCancelReason reason) throws SQLException {
    int result = 0;
    try {
      sqlMapClient.insert("KMORDER_ORDER_CANCEL.ibatorgenerated_insert", reason);
    } catch (Exception e) {
      result = 1;
      e.printStackTrace();
    }
    return result;
  }

}
