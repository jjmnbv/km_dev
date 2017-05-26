package com.kmzyc.b2b.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.dao.FAQDao;

@Component
public class FAQDaoImpl extends DaoImpl implements FAQDao {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

}
