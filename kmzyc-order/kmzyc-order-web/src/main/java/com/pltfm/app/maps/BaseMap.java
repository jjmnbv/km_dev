package com.pltfm.app.maps;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pltfm.app.entities.OrderDictionary;
import com.pltfm.app.service.OrderDictionaryService;

@Service("OrderDictionaryService")
public class BaseMap {
  private Logger log = Logger.getLogger(BaseMap.class);

  enum Color {
    red, blue, yellow;
  }

  @Resource
  private OrderDictionaryService orderDictionaryService;

  private String type;

  private static HashMap<String, Long> map = new HashMap<String, Long>();

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public static HashMap<String, Long> getMap() {
    return map;
  }

  @PostConstruct
  public void setMap() {
    List<OrderDictionary> list = null;
    try {
      list = orderDictionaryService.getAll();
      for (OrderDictionary dictionary : list) {
        if (dictionary.getOrderDictionaryType().equalsIgnoreCase(OrderStatusMap.name)) {
          OrderStatusMap.getMap().put(dictionary.getOrderDictionaryCode(),
              dictionary.getOrderDictionaryKey());
        }
      }
    } catch (Exception e) {
      log.error("异常！", e);
    }
  }

}
