package com.kmzyc.framework.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class OrderAssessPointMap {
  private static Map<String, String> map = null;

  public static Map<String, String> getMap() {
    if (map == null) {
      Map<String, String> maps = new LinkedHashMap<String, String>();
      maps.put(OrderAssessPoint.ONE_POINT.getType(), OrderAssessPoint.ONE_POINT.getTitle());
      maps.put(OrderAssessPoint.TWO_POINT.getType(), OrderAssessPoint.TWO_POINT.getTitle());
      maps.put(OrderAssessPoint.THREE_POINT.getType(), OrderAssessPoint.THREE_POINT.getTitle());
      maps.put(OrderAssessPoint.FOUR_POINT.getType(), OrderAssessPoint.FOUR_POINT.getTitle());
      maps.put(OrderAssessPoint.FIVE_POINT.getType(), OrderAssessPoint.FIVE_POINT.getTitle());
      map = maps;

    }
    return map;
  }

  public static String getValue(String key) {
    if (map == null) {
      getMap();
    }
    return map.get(key);
  }
}
