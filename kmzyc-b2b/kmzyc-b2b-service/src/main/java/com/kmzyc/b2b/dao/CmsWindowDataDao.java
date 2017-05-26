package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.persistence.Dao;

public interface CmsWindowDataDao extends Dao {
  // 获取类目所有绑定数据
  public List getWindowData(String windowFormat) throws SQLException;

}
