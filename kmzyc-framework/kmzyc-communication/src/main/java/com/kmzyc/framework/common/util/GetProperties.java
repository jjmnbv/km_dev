package com.kmzyc.framework.common.util;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetProperties {
	private static Properties properties = new Properties();
	static Logger logger = LoggerFactory.getLogger(GetProperties.class);
	private static String filepath="remoteConfig.properties";
	static{
		try {
			properties.load(GetProperties.class.getClassLoader().getResourceAsStream(filepath));
		} catch (IOException e) {
			logger.error("加载配置文件"+ filepath +"出现异常！",e);
		}
	}

	public static String getConfig(String key){	

		return (String)properties.get(key);
	}

}
