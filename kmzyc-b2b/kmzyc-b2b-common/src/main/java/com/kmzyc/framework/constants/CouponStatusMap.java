package com.kmzyc.framework.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class CouponStatusMap {
  private static Map<String, String> map = null;

  public static Map<String, String> getMap() {
    if (map == null) {
      Map<String, String> maps = new LinkedHashMap<String, String>();

      maps.put("0", "全部状态");
      maps.put(CouponStatus.HAVEUSE_COUPONSTATUS.getType(), CouponStatus.HAVEUSE_COUPONSTATUS
          .getTitle());
      maps.put(CouponStatus.NOTUSE_COUPONSTATUS.getType(), CouponStatus.NOTUSE_COUPONSTATUS
          .getTitle());
      maps.put(CouponStatus.HAVEPASSDATE_COUPONSTATUS.getType(),
          CouponStatus.HAVEPASSDATE_COUPONSTATUS.getTitle());
      maps.put(CouponStatus.FREEZE_COUPONSTATUS.getType(), CouponStatus.FREEZE_COUPONSTATUS
          .getTitle());
      maps.put(CouponStatus.INVALID_COUPONSTATUS.getType(), CouponStatus.INVALID_COUPONSTATUS
          .getTitle());
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
