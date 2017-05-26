package com.pltfm.sys.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesUtil {
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private final static Properties props = new Properties();

    public void config(String classpath) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(classpath);
            PropertiesUtil.props.load(is);
            is.close();
        } catch (IOException e) {
            logger.error("PropertiesUtil.config异常：" + e.getMessage(), e);
            return;
        }
    }

    private static String CONFIG_FILE = "init";

    static {
        try {
            bundle = ResourceBundle.getBundle(CONFIG_FILE);
        } catch (MissingResourceException e) {
            logger.error("异常：" + e.getMessage(), e);
        }
    }

    public static String getValue(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            return "";
        }
    }

    private static ResourceBundle bundle;

}
