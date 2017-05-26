package com.kmzyc.promotion.app.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.promotion.app.dao.UserInfoDao;
import com.pltfm.app.dataobject.UserInfoDO;

@Repository("userInfoDao")
public class UserInfoDaoImpl implements UserInfoDao {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 查询用户信息
   * 
   * @return
   * @throws SQLException
   */
  @Override
  public UserInfoDO queryUserInfo(Long uid) throws SQLException {
    // mkw 20150819 add

    // end
    return (UserInfoDO) sqlMapClient.queryForObject("productmapper.SQL_QUERY_USER_INFO", uid);
  }
}
