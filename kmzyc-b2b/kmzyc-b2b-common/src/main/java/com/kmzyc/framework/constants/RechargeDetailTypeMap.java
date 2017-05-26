package com.kmzyc.framework.constants;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 账号余额明细充值类型map
 * 
 * @author luoyi
 * @createDate 2013/11/11
 * 
 */
@Service("rechargeDetailTypeMap")
public class RechargeDetailTypeMap {
  private static Map<Integer, String> map = null;

  static {
    map = new LinkedHashMap<>();
    map
            .put(RechargeTypeEnum.ONLINERECHARGE.getType(), RechargeTypeEnum.ONLINERECHARGE
                    .getTitle());
    map.put(RechargeTypeEnum.BACKGROUNDRECHARGE.getType(), RechargeTypeEnum.BACKGROUNDRECHARGE
            .getTitle());
    map.put(RechargeTypeEnum.BALANCERECHARGE.getType(), RechargeTypeEnum.BALANCERECHARGE
            .getTitle());
    map.put(RechargeTypeEnum.CANCELORDER.getType(), RechargeTypeEnum.CANCELORDER.getTitle());
    map.put(RechargeTypeEnum.ORDERRETURN.getType(), RechargeTypeEnum.ORDERRETURN.getTitle());
    map.put(RechargeTypeEnum.ENCHASHMENT.getType(), RechargeTypeEnum.ENCHASHMENT.getTitle());
    map.put(RechargeTypeEnum.TRANREWARD.getType(), RechargeTypeEnum.TRANREWARD.getTitle());
    map.put(RechargeTypeEnum.TRANSACTION_TYPE_MERCHANT.getType(),
            RechargeTypeEnum.TRANSACTION_TYPE_MERCHANT.getTitle());

    map.put(RechargeTypeEnum.TRANSACTION_TYPE_FREEZING.getType(),
            RechargeTypeEnum.TRANSACTION_TYPE_FREEZING.getTitle());
    map.put(RechargeTypeEnum.TRANSACTION_TYPE_ENCHASHMENT.getType(),
            RechargeTypeEnum.TRANSACTION_TYPE_ENCHASHMENT.getTitle());
    map.put(RechargeTypeEnum.TRANSACTION_TYPE_THAW.getType(),
            RechargeTypeEnum.TRANSACTION_TYPE_THAW.getTitle());
    map.put(RechargeTypeEnum.TRANSACTION_TYPE_CONSUMPTION.getType(),
            RechargeTypeEnum.TRANSACTION_TYPE_CONSUMPTION.getTitle());
    map.put(RechargeTypeEnum.TRANSACTION_TYPE_SALE.getType(),
            RechargeTypeEnum.TRANSACTION_TYPE_SALE.getTitle());
    map.put(RechargeTypeEnum.TRANSACTION_TYPE_DISTRIBUTION.getType(),
            RechargeTypeEnum.TRANSACTION_TYPE_DISTRIBUTION.getTitle());
  }
  public static Map<Integer, String> getMap() {
    return map;
  }

  public static String getValue(Integer key) {
    return map.get(key);
  }
}
