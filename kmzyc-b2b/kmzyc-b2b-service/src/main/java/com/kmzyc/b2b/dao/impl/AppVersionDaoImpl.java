package com.kmzyc.b2b.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.dao.AppVersionDao;

@Component
public class AppVersionDaoImpl extends DaoImpl implements AppVersionDao {

  // static Logger logger = Logger.getLogger(AppVersionDaoImpl.class);
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

}
