package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.model.OrderDictionary;

public interface OrderDictonaryService {
  public List<OrderDictionary> getPaymentModel() throws SQLException;

  public List<OrderDictionary> getOrderDictionary(String dictionaryType) throws SQLException;

  public OrderDictionary getOrderDictionaryByOrderDict(OrderDictionary orderDict)
      throws SQLException;
}
