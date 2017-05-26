package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;
import com.pltfm.app.enums.ProductRelationStatus;

public class ProductRelationValidMap {

	
	private static Map<Integer, String> map = null;
	/**
	 * 获取产品关联类型
	 * @return
	 */
	public static Map<Integer, String> getStatusMap() {

		if (map == null)
			map = new LinkedHashMap<Integer, String>();

		//map.put(ProductRelationStatus.UNVALID.getStatus(),ProductRelationStatus.UNVALID.getTitle());
	//	map.put(ProductRelationStatus.VALID.getStatus(), ProductRelationStatus.VALID.getTitle());
		map.put(ProductRelationStatus.UP.getStatus(), ProductRelationStatus.UP.getTitle());
		map.put(ProductRelationStatus.DOWN.getStatus(), ProductRelationStatus.DOWN.getTitle());
		map.put(ProductRelationStatus.NEW.getStatus(), ProductRelationStatus.NEW.getTitle());
		return map;
	}

	
		
	
}
