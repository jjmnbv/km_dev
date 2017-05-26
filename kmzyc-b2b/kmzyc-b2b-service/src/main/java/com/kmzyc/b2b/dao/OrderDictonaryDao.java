package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.model.OrderDictionary;

public interface OrderDictonaryDao {
  public List<OrderDictionary> getPaymentModel() throws SQLException;

  public List<OrderDictionary> getorderdictonary(String dictionaryType) throws SQLException;

  public OrderDictionary getOrderDictionaryByOrderDict(OrderDictionary orderDict)
      throws SQLException;
}
