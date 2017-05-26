package com.kmzyc.b2b.service.member;

import java.sql.SQLException;
import java.util.List;

public interface CMSAppVersionService {
  /**
   * 手机接口获取最新版本号
   * 
   * @return
   * @throws SQLException
   */
  public List getNewestVersion(String osType) throws SQLException;
}
