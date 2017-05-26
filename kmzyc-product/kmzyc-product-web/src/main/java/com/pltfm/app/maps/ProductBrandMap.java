package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pltfm.app.service.ProdBrandService;
import com.pltfm.app.vobject.ProdBrand;
@Service("productBrandMap")
public class ProductBrandMap {
	
	@Resource
	private ProdBrandService prodBrandService;
	
	private static Map<Long, String> map = new LinkedHashMap<Long, String>();

	public static Map<Long, String> getMap() {
		if (map == null) {
			map = new LinkedHashMap<Long, String>();
		}
		return map;
	}
	
	@PostConstruct
	private void setMap(){
		try {
			List<ProdBrand> prodBrandList = prodBrandService.findAllValidBrand();
			if(prodBrandList==null || prodBrandList.isEmpty()) return;
			for(ProdBrand brand : prodBrandList){
				map.put(brand.getBrandId(),brand.getBrandName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setValue(Long key,String value){
		ProductBrandMap.map.put(key, value);
	}
	
	public static void removeValue(Long key){
		ProductBrandMap.map.remove(key);
	}

	public static String getValue(Long key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}
}
