package com.kmzyc.b2b.dao.member.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.member.ScoreInfoDao;
import com.kmzyc.b2b.model.ScoreInfo;
import com.kmzyc.b2b.service.impl.AccountInfoServiceImp;
import com.km.framework.persistence.impl.DaoImpl;

@Repository
public class ScoreInfoDaoImpl extends DaoImpl implements ScoreInfoDao {

  //static Logger logger = Logger.getLogger(MyCouponDaoImpl.class);
  private static Logger logger = LoggerFactory.getLogger(ScoreInfoDaoImpl.class);

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;


  /**
   * 保存积分明细信息
   */
  public Integer insert(ScoreInfo record) throws SQLException {
    sqlMapClient.insert("ScoreInfo.abatorgenerated_insert", record);
    return 1;
  }
}
