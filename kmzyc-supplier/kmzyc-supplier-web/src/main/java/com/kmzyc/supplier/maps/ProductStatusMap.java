package com.kmzyc.supplier.maps;

import com.pltfm.app.enums.ProductStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProductStatusMap {

	private static Map<String,String> productStatusMap = null;
	private static Map<String,String> productDraftStatusMap = null;
	
	
	public static Map<String,String> getProductStatusMap(){
		if(productStatusMap == null) {
			Map<String,String> maps = new LinkedHashMap<String,String>();
			maps.put(ProductStatus.DRAFT.getStatus(), ProductStatus.DRAFT.getTitle());
			maps.put(ProductStatus.UNAUDIT.getStatus(), ProductStatus.UNAUDIT.getTitle());
			maps.put(ProductStatus.AUDIT.getStatus(), ProductStatus.AUDIT.getTitle());
			maps.put(ProductStatus.UP.getStatus(), ProductStatus.UP.getTitle());
			maps.put(ProductStatus.DOWN.getStatus(), ProductStatus.DOWN.getTitle());
			maps.put(ProductStatus.SYSDOWN.getStatus(), ProductStatus.SYSDOWN.getTitle());
			maps.put(ProductStatus.AUDITUNPASS.getStatus(), ProductStatus.AUDITUNPASS.getTitle());
			productStatusMap = maps;
		}
		return productStatusMap;
	}
	
	public static Map<String,String> getProductDraftMap(){
		if(productDraftStatusMap == null) {
			Map<String,String> maps = new LinkedHashMap<String,String>();
			maps.put(ProductStatus.DRAFT.getStatus(), ProductStatus.DRAFT.getTitle());
			maps.put(ProductStatus.UNAUDIT.getStatus(), ProductStatus.UNAUDIT.getTitle());
			maps.put(ProductStatus.AUDIT.getStatus(), ProductStatus.AUDIT.getTitle());
			maps.put(ProductStatus.AUDITUNPASS.getStatus(), ProductStatus.AUDITUNPASS.getTitle());
			productDraftStatusMap = maps;
		}
		return productDraftStatusMap;
	}
	
	public static String getProductStatusValue(String key){
		if(productStatusMap == null) {
			getProductStatusMap();
		}
		return productStatusMap.get(key);
	}
	
	public static String getProductDraftValue(String key){
		if(productDraftStatusMap == null) {
			getProductDraftMap();
		}
		return productDraftStatusMap.get(key);
	}
	
}