package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.PromotionTypeEnums;


public class PromotionTypeMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap<Integer, String>();
			maps.put(PromotionTypeEnums.SALE.getValue(),
			        PromotionTypeEnums.SALE.getTitle());
            maps.put(PromotionTypeEnums.DISCOUNT.getValue(),
                PromotionTypeEnums.DISCOUNT.getTitle());
            maps.put(PromotionTypeEnums.REDUCTION.getValue(),
                PromotionTypeEnums.REDUCTION.getTitle());
            maps.put(PromotionTypeEnums.INCREASE.getValue(),
                PromotionTypeEnums.INCREASE.getTitle());
            maps.put(PromotionTypeEnums.COUPON.getValue(),
                PromotionTypeEnums.COUPON.getTitle());
            maps.put(PromotionTypeEnums.GIFT.getValue(),
                PromotionTypeEnums.GIFT.getTitle());
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
