package com.pltfm.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ResourceFactory {
  private static final Properties prop = new Properties();

  public static String getPropertyValue(String key) {
    return prop.getProperty(key);
  }

  public static void loadFromXML(InputStream inputstream) {
    try {
      prop.loadFromXML(inputstream);
      if (log.isDebugEnabled()) {
        prop.list(System.out);
      }
    } catch (IOException e) {
      log.error("加载xml发生错误", e);
    }
  }

  private static final Logger log = Logger.getLogger(ResourceFactory.class);
}
