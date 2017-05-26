package com.pltfm.crowdsourcing.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.crowdsourcing.model.QrcodeApplyRelation;
import com.pltfm.crowdsourcing.dao.QrcodeApplyRelationDao;

@Component("qrcodeApplyRelationDao")
public class QrcodeApplyRelationDaoImpl implements QrcodeApplyRelationDao {

  @Resource
  private SqlMapClient sqlMapClient;

  @Override
  public void bathInsertRelations(List<Long> list) throws SQLException {
    sqlMapClient.insert("CrowdQrcodeApplyRelation.bathInsertRelations", list);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<QrcodeApplyRelation> selectByRelation(QrcodeApplyRelation relation)
      throws SQLException {
    return sqlMapClient.queryForList("CrowdQrcodeApplyRelation.selectByRelation", relation);
  }

}
