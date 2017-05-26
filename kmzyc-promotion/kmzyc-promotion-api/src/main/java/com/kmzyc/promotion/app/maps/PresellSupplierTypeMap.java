package com.kmzyc.promotion.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.promotion.app.enums.PresellSupplierType;

public class PresellSupplierTypeMap {
  private static Map<String, String> map = null;

  public static Map<String, String> getMap() {

    Map<String, String> maps = new LinkedHashMap<String, String>();
    maps.put(PresellSupplierType.SELF_ENTER.getType(), PresellSupplierType.SELF_ENTER.getTitile());
    maps.put(PresellSupplierType.SELF_PROXY.getType(), PresellSupplierType.SELF_PROXY.getTitile());
    map = maps;

    return map;
  }

  public static String getValue(String key) {
    if (map == null) {
      getMap();
    }
    return map.get(key);
  }

}
