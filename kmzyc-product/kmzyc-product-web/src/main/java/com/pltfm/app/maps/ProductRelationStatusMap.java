package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ProductRelationTypeStatus;


public class ProductRelationStatusMap {

	
	
	private static Map<Integer, String> map = null;
	/**
	 * 获取产品关联类型
	 * @return
	 */
	public static Map<Integer, String> getStatusMap() {

		if (map == null)
			map = new LinkedHashMap<Integer, String>();

		//map.put(ProductRelationTypeStatus.PACKAGE.getStatus(), ProductRelationTypeStatus.PACKAGE.getTitle());
		map.put(ProductRelationTypeStatus.RECOMME.getStatus(),
				ProductRelationTypeStatus.RECOMME.getTitle());
       map.put(ProductRelationTypeStatus.GLANCE.getStatus(), ProductRelationTypeStatus.GLANCE.getTitle());
       map.put(ProductRelationTypeStatus.PURCHASE.getStatus(), ProductRelationTypeStatus.PURCHASE.getTitle());
		return map;
	}

	
	
	
	
	
	
	
	
	
	
}
