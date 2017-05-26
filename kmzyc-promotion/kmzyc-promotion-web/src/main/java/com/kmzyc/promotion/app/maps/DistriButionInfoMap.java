package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.kmzyc.promotion.app.enums.DistributionInfostatus;

/**
 * 配送单到达状态
 * 
 * @author luoyi
 * 
 */
@Service("distriButionInfoMap")
public class DistriButionInfoMap {
    private static Map<String, String> map = ImmutableMap.copyOf(new HashMap<String, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 5321484843336764130L;

        {
            put(DistributionInfostatus.UNDELIVERIES.getStatus(),
                    DistributionInfostatus.UNDELIVERIES.getTitle());
            put(DistributionInfostatus.ISDELIVERIES.getStatus(),
                    DistributionInfostatus.ISDELIVERIES.getTitle());
        }
    });

    public static Map<String, String> getMap() {

        return map;
    }

    public static String getValue(String key) {

        return map.get(key);
    }
}
