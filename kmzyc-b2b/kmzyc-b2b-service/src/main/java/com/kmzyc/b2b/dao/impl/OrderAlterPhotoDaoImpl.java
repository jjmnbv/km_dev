package com.kmzyc.b2b.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.dao.OrderAlterPhotoDao;

@Repository
public class OrderAlterPhotoDaoImpl extends DaoImpl implements OrderAlterPhotoDao {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

}
