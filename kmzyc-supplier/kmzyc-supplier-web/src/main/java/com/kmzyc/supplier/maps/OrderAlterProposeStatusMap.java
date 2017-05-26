package com.kmzyc.supplier.maps;

import com.pltfm.app.util.OrderAlterDictionaryEnum;

import java.util.LinkedHashMap;
import java.util.Map;

public class OrderAlterProposeStatusMap {
	private static Map<Integer,String> map = null;

	public static Map<Integer,String> getMap() {
		if (map == null) {
            Map<Integer,String> maps = new LinkedHashMap();
            maps.put(Integer.valueOf(OrderAlterDictionaryEnum.Propose_Status.Veto.getKey()),
                    OrderAlterDictionaryEnum.Propose_Status.Veto.getValue());
            maps.put(Integer.valueOf(OrderAlterDictionaryEnum.Propose_Status.Cancel.getKey()),
                    OrderAlterDictionaryEnum.Propose_Status.Cancel.getValue());
            maps.put(Integer.valueOf(OrderAlterDictionaryEnum.Propose_Status.Audit.getKey()),
                    OrderAlterDictionaryEnum.Propose_Status.Audit.getValue());
            maps.put(Integer.valueOf(OrderAlterDictionaryEnum.Propose_Status.Pass.getKey()),
                    OrderAlterDictionaryEnum.Propose_Status.Pass.getValue());
            maps.put(Integer.valueOf(OrderAlterDictionaryEnum.Propose_Status.Returning.getKey()),
                    OrderAlterDictionaryEnum.Propose_Status.Returning.getValue());
            maps.put(Integer.valueOf(OrderAlterDictionaryEnum.Propose_Status.Pickup.getKey()),
                    OrderAlterDictionaryEnum.Propose_Status.Pickup.getValue());
            maps.put(Integer.valueOf(OrderAlterDictionaryEnum.Propose_Status.F_Backpay.getKey()),
                    OrderAlterDictionaryEnum.Propose_Status.F_Backpay.getValue());
            maps.put(Integer.valueOf(OrderAlterDictionaryEnum.Propose_Status.F_Stockout.getKey()),
                    OrderAlterDictionaryEnum.Propose_Status.F_Stockout.getValue());
            maps.put(Integer.valueOf(OrderAlterDictionaryEnum.Propose_Status.F_BackShop.getKey()),
                    OrderAlterDictionaryEnum.Propose_Status.F_BackShop.getValue());
            maps.put(Integer.valueOf(OrderAlterDictionaryEnum.Propose_Status.ExchangeToReturn.getKey()),
                    OrderAlterDictionaryEnum.Propose_Status.ExchangeToReturn.getValue());
            maps.put(Integer.valueOf(OrderAlterDictionaryEnum.Propose_Status.Backpay.getKey()),
                    OrderAlterDictionaryEnum.Propose_Status.Backpay.getValue());
            maps.put(Integer.valueOf(OrderAlterDictionaryEnum.Propose_Status.Stockout.getKey()),
                    OrderAlterDictionaryEnum.Propose_Status.Stockout.getValue());
            maps.put(Integer.valueOf(OrderAlterDictionaryEnum.Propose_Status.BackShop.getKey()),
                    OrderAlterDictionaryEnum.Propose_Status.BackShop.getValue());
            maps.put(Integer.valueOf(OrderAlterDictionaryEnum.Propose_Status.Returns_Done.getKey()),
                    OrderAlterDictionaryEnum.Propose_Status.Returns_Done.getValue());
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
