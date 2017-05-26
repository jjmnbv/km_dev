package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ZFProductRelationStatus;


public class ZFProductRelationValidMap {

	
	private static Map<Integer, String> map = null;
	/**
	 * 获取组方产品关联类型
	 * @return
	 */
	public static Map<Integer, String> getStatusMap() {

		if (map == null)
			map = new LinkedHashMap<Integer, String>();

		map.put(ZFProductRelationStatus.VALID.getStatus(), ZFProductRelationStatus.VALID.getTitle());
		map.put(ZFProductRelationStatus.NULLITY.getStatus(), ZFProductRelationStatus.NULLITY.getTitle());
		return map;
	}

	
		
	
}
