package com.kmzyc.b2b.dao.member.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kmzyc.b2b.dao.member.CMSAppVersionDao;
import com.kmzyc.b2b.model.AppVersion;

@Repository("versionDao")
public class CMSAppVersionDaoImpl implements CMSAppVersionDao {
  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  @Override
  public List<AppVersion> getNewestVersion(String osType) throws SQLException {
    return (List<AppVersion>) this.sqlMapClient.queryForList("CMS_APP_VERSION.selectNew", osType);
  }

}
