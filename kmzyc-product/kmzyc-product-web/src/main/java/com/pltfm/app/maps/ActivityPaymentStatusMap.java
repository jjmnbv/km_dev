package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ActivityPaymentStatus;

public class ActivityPaymentStatusMap {
    private static Map<Integer, String> map = null;

    public static Map<Integer, String> getMap() {
        if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
            maps.put(ActivityPaymentStatus.NOT_PAY.getStatus(),
                    ActivityPaymentStatus.NOT_PAY.getTitle());
            maps.put(ActivityPaymentStatus.PAYED.getStatus(), ActivityPaymentStatus.PAYED.getTitle());
            maps.put(ActivityPaymentStatus.NOT_REFUND.getStatus(),
                    ActivityPaymentStatus.NOT_REFUND.getTitle());
            maps.put(ActivityPaymentStatus.REFUNDED.getStatus(),
                    ActivityPaymentStatus.REFUNDED.getTitle());
            maps.put(ActivityPaymentStatus.NEEDLESSREFUND.getStatus(),
                    ActivityPaymentStatus.NEEDLESSREFUND.getTitle());
            map = maps;
        }
        return map;
    }

    public static String getValue(Integer key) {
        if (map == null) {
            getMap();
        }
        return map.get(key);
    }
}
