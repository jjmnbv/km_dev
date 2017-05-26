package com.kmzyc.b2b.dao.member.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.dao.member.ReturnShopDao;

@Repository
public class ReturnShopDaoImpl extends DaoImpl implements ReturnShopDao {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

}
