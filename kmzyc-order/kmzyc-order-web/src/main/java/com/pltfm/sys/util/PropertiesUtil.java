package com.pltfm.sys.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class PropertiesUtil {
  private static Logger log = Logger.getLogger(PropertiesUtil.class);
  private final static Properties props = new Properties();

  public void config(String classpath) {
    try {
      InputStream is = getClass().getClassLoader().getResourceAsStream(classpath);
      PropertiesUtil.props.load(is);
      is.close();
    } catch (IOException e) {
      log.error(e);
      return;
    }
  }

  private static String CONFIG_FILE = "init";
  static {
    try {
      bundle = ResourceBundle.getBundle(CONFIG_FILE);
    } catch (MissingResourceException e) {
      log.error(e);
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
