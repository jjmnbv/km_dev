package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.TriggerStatus;

public class TriggerStatusMap {
	private static Map<String,String> map = null;
	
	public static Map<String,String> getMap(){
			Map<String,String> maps = new LinkedHashMap<String,String>();
			maps.put(TriggerStatus.TR_WAIT.getStatus(), TriggerStatus.TR_WAIT.getTitle());
			maps.put(TriggerStatus.TR_RUN.getStatus(), TriggerStatus.TR_RUN.getTitle());
			maps.put(TriggerStatus.TR_OVER.getStatus(), TriggerStatus.TR_OVER.getTitle());
			maps.put(TriggerStatus.TR_PARTFAIL.getStatus(), TriggerStatus.TR_PARTFAIL.getTitle());
			maps.put(TriggerStatus.TR_SYSFAIL.getStatus(), TriggerStatus.TR_SYSFAIL.getTitle());
			maps.put(TriggerStatus.TR_SUCEESS.getStatus(), TriggerStatus.TR_SUCEESS.getTitle());
			map = maps;
		return map;
	}
	
	public static String getValue(String key){
		if(map == null) {
			getMap();
		}
		return map.get(key);
	}
}
