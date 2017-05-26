package com.kmzyc.supplier.maps;

import org.springframework.stereotype.Service;

import com.kmzyc.supplier.constrant.CertificateTypeEnum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 供应商证件类型相关的map
 * 
 * @author luoyi
 * @version 1.0
 */
public class CertificateTypeMap {
	private static Map<Integer, String> map = null;

	public static Map<Integer, String> getMap() {
		if (map == null) {
            Map<Integer, String> maps = new LinkedHashMap();
            maps.put(Integer.valueOf(CertificateTypeEnum.Shen_Fen_Zheng.getType()),CertificateTypeEnum.Shen_Fen_Zheng.getTitle());
            maps.put(Integer.valueOf(CertificateTypeEnum.Hu_Zhao.getType()), CertificateTypeEnum.Hu_Zhao.getTitle());
            maps.put(Integer.valueOf(CertificateTypeEnum.Hui_Xiang_Zheng.getType()), CertificateTypeEnum.Hui_Xiang_Zheng.getTitle());
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
