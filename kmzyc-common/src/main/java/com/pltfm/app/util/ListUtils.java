package com.pltfm.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pltfm.app.entities.OrderMain;

/**
 * java.util.List处理的工具类
 * 
 * @author
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class ListUtils {

  /**
   * 判断java.util.List的size值是否大于0。
   * 
   * @param lst
   * @return 判断的布尔值
   */
  public static boolean isNotEmpty(List lst) {
    return lst != null && lst.size() > 0;
  }

  /**
   * 判断List中是否存在相同的字符串
   * 
   * @param str
   * @param list
   * @return 判断的布尔值
   */
  public static boolean isExist(String str, List list) {
    for (int i = 0; i < list.size(); i++) {
      String temp = list.get(i).toString();
      if (temp.equals(str)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 根据渠道分裂订单集合
   * 
   * @param orderMainMap订单集合
   * @return
   */
  public static Map<String, List<OrderMain>> splitByChannel(List<OrderMain> orderMainMap) {
    Map<String, List<OrderMain>> result = new HashMap<String, List<OrderMain>>();
    if (isNotEmpty(orderMainMap)) {
      for (OrderMain order : orderMainMap) {
        String channel = order.getOrderChannel();
        List<OrderMain> list = null;
        if (result.containsKey(channel)) {
          list = (List<OrderMain>) result.get(channel);
          list.add(order);
        } else {
          list = new ArrayList<OrderMain>();
          list.add(order);
        }
        result.put(channel, list);
      }
    }
    return result;
  }

  /**
   * 根据商家分裂订单集合
   * 
   * @param orderMainMap订单集合
   * @return
   */
  public static Map<String, List<OrderMain>> splitByCommerce(List<OrderMain> orderMainMap) {
    Map<String, List<OrderMain>> result = new HashMap<String, List<OrderMain>>();
    if (isNotEmpty(orderMainMap)) {
      for (OrderMain order : orderMainMap) {
        String commerceId = order.getCommerceId();
        List<OrderMain> list = null;
        if (result.containsKey(commerceId)) {
          list = (List<OrderMain>) result.get(commerceId);
          list.add(order);
        } else {
          list = new ArrayList<OrderMain>();
          list.add(order);
        }
        result.put(commerceId, list);
      }
    }
    return result;
  }
}
