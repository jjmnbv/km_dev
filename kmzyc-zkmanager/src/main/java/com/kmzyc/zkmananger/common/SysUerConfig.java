package com.kmzyc.zkmananger.common;

import java.io.IOException;
import java.util.Properties;



public class SysUerConfig {
  private static final Properties SYS_CONFIG = new Properties();
  private static final String SYS_CONFIG_FILE = "/config/user.properties";
  static{
      try {
          SYS_CONFIG.load(SysUerConfig.class.getResourceAsStream(SYS_CONFIG_FILE));
      } catch (IOException e) {
          throw new RuntimeException("SYS_CONFIG_FILE load fail",e);
      }
  }
  private SysUerConfig(){}
  public static Properties getConfiguration(){
      return SYS_CONFIG;
  }
}
