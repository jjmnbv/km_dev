package com.kmzyc.promotion.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kmzyc.promotion.app.enums.PresellStatus;

public class PresellSatusTypeMap {
  private static Map<Integer, String> map = null;

  public static Map<Integer, String> getMap() {

    Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
    maps.put(PresellStatus.DRAFT.getStatus(), PresellStatus.DRAFT.getTitle());
    maps.put(PresellStatus.PRE_AUDIT.getStatus(), PresellStatus.PRE_AUDIT.getTitle());
    maps.put(PresellStatus.PRESELL_STOP.getStatus(), PresellStatus.PRESELL_STOP.getTitle());
    maps.put(PresellStatus.DEPOSIT_TIME.getStatus(), PresellStatus.DEPOSIT_TIME.getTitle());
    maps.put(PresellStatus.NO_FINALPAY_TIME.getStatus(), PresellStatus.NO_FINALPAY_TIME.getTitle());
    maps.put(PresellStatus.FINALPAY_TIME.getStatus(), PresellStatus.FINALPAY_TIME.getTitle());
    maps.put(PresellStatus.PRESELL_END.getStatus(), PresellStatus.PRESELL_END.getTitle());
    map = maps;

    return map;
  }

  public static String getValue(Integer key) {
    if (map == null) {
      getMap();
    }
    return map.get(key);
  }

}
