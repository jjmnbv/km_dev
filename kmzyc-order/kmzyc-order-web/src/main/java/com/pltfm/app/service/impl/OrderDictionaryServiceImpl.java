package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.dao.OrderDictionaryDAO;
import com.pltfm.app.entities.OrderDictionary;
import com.pltfm.app.entities.OrderDictionaryExample;
import com.pltfm.app.service.OrderDictionaryService;
import com.pltfm.exception.OrderDictionaryException;

@SuppressWarnings("unchecked")
@Service("orderDictionaryService")
public class OrderDictionaryServiceImpl implements OrderDictionaryService {
  private Logger log = Logger.getLogger(OrderDictionaryServiceImpl.class);
  @Resource
  private OrderDictionaryDAO orderDictionaryDAO;

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = OrderDictionaryException.class)
  public List<OrderDictionary> getAll() throws SQLException {
    return orderDictionaryDAO.selectByExample(null);

  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = OrderDictionaryException.class)
  public void insertOrder() throws OrderDictionaryException {
    OrderDictionary record = new OrderDictionary();
    OrderDictionary record1 = new OrderDictionary();
    record.setOrder_dictionary_id(0);
    record1.setOrder_dictionary_id(0);
    // OrderDictionary record2 = new OrderDictionary();
    // record2.setOrder_dictionary_id(1);
    // record2.setOrderDictionaryComment(null);
    try {
      // orderDictionaryDAO.updateByPrimaryKey(record2);
      orderDictionaryDAO.insert(record);
      orderDictionaryDAO.insert(record1);
    } catch (SQLException e) {
      log.error(e);
      throw new OrderDictionaryException();
    }

  }

  public void updateOrder() throws OrderDictionaryException {
    OrderDictionary record2 = new OrderDictionary();
    record2.setOrder_dictionary_id(1);
    record2.setOrderDictionaryComment(null);
    try {
      orderDictionaryDAO.updateByPrimaryKey(record2);
    } catch (SQLException e) {
      log.error(e);
      throw new OrderDictionaryException();
    }
  }

  @Override
  public List getDictionaryByType(String orderDictionaryType) throws OrderDictionaryException {
    try {
      OrderDictionaryExample example = new OrderDictionaryExample();
      OrderDictionaryExample.Criteria criteria = example.createCriteria();
      criteria.andOrderDictionaryTypeEqualTo(orderDictionaryType);
      return orderDictionaryDAO.selectByExample(example);
    } catch (SQLException e) {
      log.error(e);
      throw new OrderDictionaryException();
    }
  }

  public List<OrderDictionary> queryDictionaryByType(String type) throws OrderDictionaryException {
    List<OrderDictionary> dictList = new ArrayList<OrderDictionary>();
    try {
      dictList = this.orderDictionaryDAO.queryDictionaryByType(type);
    } catch (SQLException e) {
      log.error(e);
      throw new OrderDictionaryException();
    }
    return dictList;
  }

  public void test() {

  }

}
