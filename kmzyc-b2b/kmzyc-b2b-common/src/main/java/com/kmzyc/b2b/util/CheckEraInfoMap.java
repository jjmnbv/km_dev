package com.kmzyc.b2b.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class CheckEraInfoMap {
  private static Map<String, String> map = null;

  public static Map<String, String> getMap() {
    if (map == null) {
      Map<String, String> maps = new LinkedHashMap<String, String>();
      maps.put(CheckEraInfo.SYSTEM_ERRO.getType(), CheckEraInfo.SYSTEM_ERRO.getTitle());
      maps.put(CheckEraInfo.ERAINFO_NOEXCITS.getType(), CheckEraInfo.ERAINFO_NOEXCITS.getTitle());
      maps.put(CheckEraInfo.PASSWORD_ERRO.getType(), CheckEraInfo.PASSWORD_ERRO.getTitle());
      maps.put(CheckEraInfo.SYSTEM_NOLOGIN.getType(), CheckEraInfo.SYSTEM_NOLOGIN.getTitle());
      maps.put(CheckEraInfo.OUTOFTIME.getType(), CheckEraInfo.OUTOFTIME.getTitle());
      maps.put(CheckEraInfo.OUTOFDATE.getType(), CheckEraInfo.OUTOFDATE.getTitle());
      maps.put(CheckEraInfo.LOGIN_INVALID.getType(), CheckEraInfo.LOGIN_INVALID.getTitle());
      maps.put(CheckEraInfo.OUTTIME_LOGIN.getType(), CheckEraInfo.OUTTIME_LOGIN.getTitle());
      maps.put(CheckEraInfo.LOGIN_NOGRANT.getType(), CheckEraInfo.LOGIN_NOGRANT.getTitle());
      maps.put(CheckEraInfo.IS_LOGINOUT.getType(), CheckEraInfo.IS_LOGINOUT.getTitle());
      maps.put(CheckEraInfo.IS_FREEZ.getType(), CheckEraInfo.IS_FREEZ.getTitle());

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
