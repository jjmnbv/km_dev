package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kmzyc.b2b.dao.OrderDictonaryDao;
import com.kmzyc.b2b.model.OrderDictionary;
import com.km.framework.persistence.impl.DaoImpl;

@Repository
@SuppressWarnings("unchecked")
public class OrderDictonaryDaoImpl extends DaoImpl implements OrderDictonaryDao {
  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  @Override
  public List<OrderDictionary> getPaymentModel() throws SQLException {

    List<OrderDictionary> result =
        (List<OrderDictionary>) sqlMapClient.queryForList("OrderDictionary.findPaymentModel");
    return result;
  }

  @Override
  public List<OrderDictionary> getorderdictonary(String dictionaryType) throws SQLException {
    List<OrderDictionary> result =
        (List<OrderDictionary>) sqlMapClient.queryForList("OrderDictionary.findOrderDictionary",
            dictionaryType);
    return result;
  }

  /**
   * 获取订单数据字典信息
   */
  @Override
  public OrderDictionary getOrderDictionaryByOrderDict(OrderDictionary orderDict)
      throws SQLException {
    return (OrderDictionary) sqlMapClient.queryForObject("OrderDictionary.findByOrderDict",
        orderDict);
  }
}
