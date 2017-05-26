package com.pltfm.app.dao;

import java.sql.SQLException;

import com.pltfm.app.entities.OrderCancelReason;

public interface OrderCancelDAO {

  Integer saveOrderCancelReason(OrderCancelReason reason) throws SQLException;

}
