package com.kmzyc.b2b.dao.member.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.member.ConsultDao;
import com.kmzyc.b2b.service.impl.AccountInfoServiceImp;
import com.km.framework.persistence.impl.DaoImpl;

/**
 * Description:咨询信息数据访问 User: lishiming Date: 13-10-17 下午3:45 Since: 1.0
 */
@Component
public class ConsultDaoImpl extends DaoImpl implements ConsultDao {

 // static Logger logger = Logger.getLogger(ConsultDaoImpl.class);
  private static Logger logger = LoggerFactory.getLogger(ConsultDaoImpl.class);

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

}
