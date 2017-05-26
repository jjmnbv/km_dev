package com.kmzyc.b2b.dao.member;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.model.AppVersion;

public interface CMSAppVersionDao {
  /**
   * 手机接口获取最新版本号
   * 
   * @return
   * @throws SQLException
   */
  List<AppVersion> getNewestVersion(String osType) throws SQLException;
}
