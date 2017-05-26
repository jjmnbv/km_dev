package com.pltfm.app.dao.impl;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public abstract class BaseOrderMainDAO {
  Logger log = Logger.getLogger(this.getClass());
  protected static SqlMapClient sqlMapClient = null;
  private static final String resource = "com/pltfm/app/sqlmap/sql-map-config.xml";

  public BaseOrderMainDAO() {
    if (sqlMapClient == null) {
      try {
        java.io.Reader reader = com.ibatis.common.resources.Resources.getResourceAsReader(resource);
        sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
        log.warn("================init iBatis SqlMapConfig.xml success !!!");
      } catch (Exception e) {
        log.warn("×××××××× init iBatis SqlMapConfig.xml false ... ");
        log.warn("【请检查相关sqlmap文件是否有误！！！】 ");
      }
    }
  }
}
