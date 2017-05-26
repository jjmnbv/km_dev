package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pltfm.app.enums.WarehouseInfoStatus;
import com.pltfm.app.service.WarehouseInfoService;
import com.pltfm.app.vobject.WarehouseInfo;
@Service("warehouseInfoMap")
public class WarehouseInfoMap {
	
	@Resource
	private WarehouseInfoService warehouseInfoService;
	
	private static Map<Long, String> map = new LinkedHashMap<Long, String>();
	
	private static Map<Long, String> statusMap = new LinkedHashMap<Long, String>();

	public static Map<Long, String> getMap() {
		if (map == null) {
			map = new LinkedHashMap<Long, String>();
		}
		return map;
	}
	
	public static Map<Long, String> getStatusMap() {
		if (statusMap == null) {
			statusMap = new LinkedHashMap<Long, String>();
		}
		return statusMap;
	}
	
	@PostConstruct
	private void setStatusMap(){
		try {
			List<WarehouseInfo> warehouseList = warehouseInfoService.findAllWarehouseInfo(WarehouseInfoStatus.START.getStatus());
			if(warehouseList==null || warehouseList.isEmpty()) return;
			for(WarehouseInfo warehouse : warehouseList){
				statusMap.put(warehouse.getWarehouseId(),warehouse.getWarehouseName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@PostConstruct
	private void setMap(){
		try {
			List<WarehouseInfo> warehouseList = warehouseInfoService.findAllWarehouseInfo(null);
			for(WarehouseInfo warehouse : warehouseList){
				map.put(warehouse.getWarehouseId(),warehouse.getWarehouseName()+new StringBuilder("[")
				.append(WarehouseStatusMap.getValue(warehouse.getStatus())).append("]").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void removeValue(Long key){
		WarehouseInfoMap.map.remove(key);
	}
	
	public static void removeStatusValue(Long key){
		WarehouseInfoMap.statusMap.remove(key);
	}
	
	public static void setValue(Long key,String value){
		WarehouseInfoMap.map.put(key, value);
	}

	public static String getValue(Long key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}
	
	public static void setStatusValue(Long key,String value){
		WarehouseInfoMap.statusMap.put(key, value);
	}
	
	public static String getStatusValue(Long key) {
		if (statusMap == null) {
			getMap();
		}
		return statusMap.get(key);
	}
}
