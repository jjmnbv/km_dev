package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

public class CertificateTypeMap {
	
	private static Map<Integer,String> OTC_MAP = null;
	
	private static Map<Integer,String> MDSIN_MAP = null;
	
	
	public static Map<Integer,String> getOTCMAP(){
		if(OTC_MAP == null){
            Map<Integer,String> OTC_MAPS = new LinkedHashMap<Integer,String>();
            OTC_MAPS.put(1, "药品注册批件");
            OTC_MAPS.put(2, "GMP认证");
            OTC_MAPS.put(3, "药检报告（检验情况）");
            OTC_MAPS.put(4, "包材备案件");
            OTC_MAP = OTC_MAPS;
		}
		return OTC_MAP;
	}
	
	public static Map<Integer,String> getMDSINMAP(){
		if(MDSIN_MAP == null){
            Map<Integer,String> MDSIN_MAPS = new LinkedHashMap<Integer,String>();
            MDSIN_MAPS.put(1, "注册证");
            MDSIN_MAPS.put(2, "认证证书");
            MDSIN_MAPS.put(3, "厂检情况");
            MDSIN_MAPS.put(4, "注册登记表");
            MDSIN_MAP = MDSIN_MAPS;
		}
		return MDSIN_MAP;
	}
	

}