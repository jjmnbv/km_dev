package com.kmzyc.b2b.dao.member;

import java.sql.SQLException;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.ScoreInfo;

public interface ScoreInfoDao extends Dao {

  /**
   * 添加积分明细数据
   * 
   * @param scoreInfo 积分明细实体
   * @throws SQLException 异常
   */
  public Integer insert(ScoreInfo record) throws SQLException;
}
