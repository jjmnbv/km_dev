package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pltfm.app.enums.ConsultCheckStatus;
import com.pltfm.app.enums.ConsultReplyStatus;

public class ConsultStatusMap {

	private static Map<String, String> map = null;
	
	 
	/**
	 * 取得回复状态
	 * @return
	 */
	public static Map<String, String> getReplyMap() {
	 
			map = new LinkedHashMap<String, String>();
			map.put(ConsultReplyStatus.NOTRESPONSE.getStatus(),
					ConsultReplyStatus.NOTRESPONSE.getTitle());
			map.put(ConsultReplyStatus.HAVERESPONSE.getStatus(),
					ConsultReplyStatus.HAVERESPONSE.getTitle());
		 
		return map;
	}
	
	/**
	 * 取得审核状态
	 * @return
	 */
	public static Map<String,String> getCheckMap(){
		 
			map = new LinkedHashMap<String, String>();
			map.put(ConsultCheckStatus.WAITCHECK.getStatus(),
					ConsultCheckStatus.WAITCHECK.getTitle());
			map.put(ConsultCheckStatus.NOTPASSED.getStatus(),
					ConsultCheckStatus.NOTPASSED.getTitle());
			map.put(ConsultCheckStatus.HAVEPASSED.getStatus(),
					ConsultCheckStatus.HAVEPASSED.getTitle());
		 
		return map;
	}

	public static String getReplyValue(String key) {
		if (map == null) {
			getReplyMap();
		}
		return map.get(key);
	}
	public static String getCheckValue(String key) {
		if (map == null) {
			getCheckMap();
		}
		return map.get(key);
	}
}
