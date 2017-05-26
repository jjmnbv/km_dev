package com.kmzyc.framework.constants;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 账号余额明细充值状态map
 * 
 * @author luoyi
 * @createDate 2013/11/11
 * 
 */
@Service("rechargeDetailStatusMap")
public class RechargeDetailStatusMap {
  private static Map<Integer, String> map;

  static {
    map = new LinkedHashMap<>();
    map.put(RechargeStatusEnum.UNPAY.getStatus(), RechargeStatusEnum.UNPAY.getTitle());
    map.put(RechargeStatusEnum.SUCCESS.getStatus(), RechargeStatusEnum.SUCCESS.getTitle());
    map.put(RechargeStatusEnum.FAIL.getStatus(), RechargeStatusEnum.FAIL.getTitle());
  }
  public static Map<Integer, String> getMap() {
    return map;
  }

  public static String getValue(Integer key) {
    return map.get(key);
  }
}
