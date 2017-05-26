package com.pltfm.sys.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.vobject.SpreaderRPInfoCriteria;
import com.pltfm.sys.dao.SpreaderRPInfoDAO;

@SuppressWarnings("unchecked")
@Repository("spreaderRPInfoDAO")
public class SpreaderRPInfoDAOImpl implements SpreaderRPInfoDAO {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  public List<HashMap> selectUserRpInfoList(SpreaderRPInfoCriteria criteria) throws SQLException {
    List<HashMap> lt = sqlMapClient.queryForList("SPREADER_RP_INFO.selectUserRpInfoList", criteria);
    if (lt != null && !lt.isEmpty()) return lt;
    return null;
  }
}
