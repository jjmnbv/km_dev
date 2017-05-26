package com.kmzyc.framework.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.kmzyc.zkconfig.ConfigurationUtil;

@Service
public class LoginFilterMap {

	private static Map<String, String> map = new HashMap<String, String>();
	
	public static Map<String, String> getMap() {
		if (map == null) {
			map = new HashMap<String, String>();
		}
		return map;
	}
	
	@PostConstruct
	private void setMap(){
		try {
			String loginResponse = ConfigurationUtil.getString("loginUnCheck");
			StringTokenizer loginResponses = new StringTokenizer(loginResponse, ",", false);
			while (loginResponses.hasMoreElements()) {
				map.put(loginResponses.nextToken(), "loginUnCheck");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
