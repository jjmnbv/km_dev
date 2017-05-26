package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ProductRelationEdibleStatus;

public class ProductRelationEdibelMap {


	private static Map<Integer, String> map = null;
	/**
	 * 获取产品关联类型
	 * @return
	 */
	public static Map<Integer, String> getStatusMap() {

		if (map == null)
			map = new LinkedHashMap<Integer, String>();

		map.put(ProductRelationEdibleStatus.EDIBLE.getStatus(),ProductRelationEdibleStatus.EDIBLE.getTitle());
		map.put(ProductRelationEdibleStatus.UNEDIBLE.getStatus(), ProductRelationEdibleStatus.UNEDIBLE.getTitle());
  
		return map;
	}
	
	
	
}
