package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.dao.CmsWindowDataDao;

@Component("cmsWindowDataDao")
public class CmsWindowDataDaoImpl extends DaoImpl implements CmsWindowDataDao {
  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  // 获取类目所有绑定数据
  public List getWindowData(String windowFormat) throws SQLException {
    List list =
        sqlMapClient.queryForList("CmsWindowData.abatorgenerated_queryWindowData", windowFormat);
    return list;

  }
}
