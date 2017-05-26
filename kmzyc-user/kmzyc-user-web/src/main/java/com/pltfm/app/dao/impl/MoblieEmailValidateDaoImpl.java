package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.MoblieEmailValidateDao;
import com.pltfm.app.vobject.MoblieEmailValidate;

@Component(value = "moblieEmailValidateDao")
public class MoblieEmailValidateDaoImpl implements MoblieEmailValidateDao {
  /**
   * 数据库操作对象
   */
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  // 分页
  public List selectListMoblieEmail(MoblieEmailValidate moblieEmailValidate) throws SQLException {
    List pageList = sqlMapClient.queryForList("MOBLIE_EMAIL_VALIDATE.selectListMoblieEmail",
        moblieEmailValidate);
    return pageList;
  }

  // 统计条数
  public Integer selectListMoblieEmailCount(MoblieEmailValidate moblieEmailValidate)
      throws SQLException {
    // TODO Auto-generated method stub
    Integer count = (Integer) sqlMapClient
        .queryForObject("MOBLIE_EMAIL_VALIDATE.selectListMoblieEmailCount", moblieEmailValidate);
    return count;
  }

  @Override
  public void updateEmailValidate(MoblieEmailValidate moblieEmailValidate) throws SQLException {

    sqlMapClient.update("MOBLIE_EMAIL_VALIDATE.updateEmailValidate", moblieEmailValidate);

  }

  @Override
  public void mobileValidate(MoblieEmailValidate moblieEmailValidate) throws SQLException {
    // TODO Auto-generated method stub

    sqlMapClient.update("MOBLIE_EMAIL_VALIDATE.mobileValidate", moblieEmailValidate);
  }



}
