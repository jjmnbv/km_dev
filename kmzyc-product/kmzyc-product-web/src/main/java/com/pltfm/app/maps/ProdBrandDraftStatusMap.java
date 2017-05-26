package com.pltfm.app.maps;

import com.pltfm.app.enums.ProdBrandDraftStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProdBrandDraftStatusMap {

	private static Map<String,String> productStatusMap = null;

	
	public static Map<String,String> getProdBrandDraftStatusMap(){
		if(productStatusMap == null) {
			Map<String,String> maps = new LinkedHashMap<String,String>();
			maps.put(ProdBrandDraftStatus.UNAUDIT.getStatus(), ProdBrandDraftStatus.UNAUDIT.getTitle());
			maps.put(ProdBrandDraftStatus.AUDITUNPASS.getStatus(), ProdBrandDraftStatus.AUDITUNPASS.getTitle());
			maps.put(ProdBrandDraftStatus.AUDIT.getStatus(), ProdBrandDraftStatus.AUDIT.getTitle());
			productStatusMap = maps;
		}
		return productStatusMap;
	}
	
}