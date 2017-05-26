package com.pltfm.app.service;

import java.util.List;

import com.pltfm.app.entities.OrderDictionary;
import com.pltfm.exception.OrderDictionaryException;

@SuppressWarnings("unchecked")
public interface OrderDictionaryService {

  public List<OrderDictionary> getAll() throws Exception;

  public void insertOrder() throws OrderDictionaryException;

  /**
   * 根据orderDictionaryType获取订单字典值
   */
  public List getDictionaryByType(String orderDictionaryType) throws OrderDictionaryException;

  public void test();

  public List<OrderDictionary> queryDictionaryByType(String type) throws OrderDictionaryException;
}
