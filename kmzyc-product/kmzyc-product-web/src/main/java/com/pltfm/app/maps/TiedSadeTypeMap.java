package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.TideSaleType;

public class TiedSadeTypeMap {

	
	
	private static Map<Integer, String>  tiedSadeType= null;
	/**
	 * 存放入库单的状态
	 * @return
	 */
	public static Map<Integer, String> getTiedSaleTypeMap() {

		if (tiedSadeType == null)
			tiedSadeType = new LinkedHashMap<Integer, String>();
	
		tiedSadeType.put(TideSaleType.FAVORABLE.getStatus(), TideSaleType.FAVORABLE.getTitle());
		tiedSadeType.put(TideSaleType.RECOMMEND.getStatus(),
				TideSaleType.RECOMMEND.getTitle());

		return tiedSadeType;
	}	
	
	
	
	
	
	
	
	
	
	
}
