package com.kmzyc.promotion.app.maps;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class CertificateTypeMap {

	private static Map<Integer, String> OTC_MAP = ImmutableMap.copyOf(new HashMap<Integer, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 735620161445235101L;

        {
            put(1, "药品注册批件");
            put(2, "GMP认证");
            put(3, "药检报告（检验情况）");
            put(4, "包材备案件");
        }
    });

	private static Map<Integer, String> MDSIN_MAP = ImmutableMap.copyOf(new HashMap<Integer, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = -9078184548913020333L;

        {
            put(1, "注册证");
            put(2, "认证证书");
            put(3, "厂检情况");
            put(4, "注册登记表");
        }
    });

	public static Map<Integer, String> getOTCMAP() {

	    return OTC_MAP;
	}

	public static Map<Integer, String> getMDSINMAP() {

	    return MDSIN_MAP;
	}

}
