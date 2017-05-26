package com.pltfm.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateStrSingleton {
  private static final Logger log = Logger.getLogger(DateStrSingleton.class);
  private final SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssms");
  private String dateStr;

  private DateStrSingleton() {
    dateStr = sdf.format(new Date());
    log.info(dateStr);
    log.info(dateStr.length());
  }

  private static class SingletonHolder {
    public final static DateStrSingleton instance = new DateStrSingleton();
  }

  public static DateStrSingleton getInstance() {
    return SingletonHolder.instance;
  }

  public void setDateStr(String dateStr) {
    this.dateStr = dateStr;
  }

  public String getDateStr() {
    return dateStr;
  }

  public static void main(String[] args) {
    // DateStrSingleton ds =
//    DateStrSingleton.getInstance();
     Long ll = new Long(3);
     System.out.println(ll==3);
      
     
  }

}
