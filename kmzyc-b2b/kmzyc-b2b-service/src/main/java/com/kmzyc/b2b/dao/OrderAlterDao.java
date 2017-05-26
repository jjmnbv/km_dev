package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.OrderAlter;

@SuppressWarnings("unchecked")
public interface OrderAlterDao extends Dao {
  /**
   * 查订单对应商品的退货信息 （用来计算同步Pv）
   * 
   * @param map
   * @return OrderAlter
   * @throws SQLException
   */
  OrderAlter findOrderAlterPv(Map map) throws SQLException;

}
