package com.kmzyc.supplier.maps;

import org.springframework.stereotype.Service;

import com.kmzyc.supplier.constrant.RechargeStatusEnum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 账号余额明细充值状态map
 * @author luoyi
 * @createDate 2013/11/11
 *
 */
public class RechargeDetailStatusMap {

	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap();
            maps.put(Integer.valueOf(RechargeStatusEnum.SUCCESS.getStatus()),
					RechargeStatusEnum.SUCCESS.getTitle());
            maps.put(Integer.valueOf(RechargeStatusEnum.FAIL.getStatus()),
					RechargeStatusEnum.FAIL.getTitle());
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
