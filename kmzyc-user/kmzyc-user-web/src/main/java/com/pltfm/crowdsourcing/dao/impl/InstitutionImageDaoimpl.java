package com.pltfm.crowdsourcing.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.crowdsourcing.model.InstitutionImage;
import com.pltfm.crowdsourcing.dao.InstitutionImageDao;

@Component(value = "institutionImageDao")
public class InstitutionImageDaoimpl implements InstitutionImageDao {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public int deleteByInsAppId(Long insAppId) throws SQLException {
    return (Integer) sqlMapClient.delete("CrowdInstitutionImage.ibatorgenerated_deleteByInsAppId",
        insAppId);
  }


  @Override
  public int insert(InstitutionImage record) throws SQLException {
    return (Integer) sqlMapClient.insert("CrowdInstitutionImage.ibatorgenerated_insert", record);
  }

  @Override
  public void bathInsert(List<InstitutionImage> recordList) throws SQLException {
    sqlMapClient.insert("CrowdInstitutionImage.ibatorgenerated_bathInsert", recordList);
  }

  @Override
  public List<InstitutionImage> selectByInsAppId(Long insAppId) throws SQLException {
    return sqlMapClient.queryForList("CrowdInstitutionImage.ibatorgenerated_selectByInsAppId",
        insAppId);
  }


  @Override
  public List<InstitutionImage> selectByInstitutionInfoId(Long id) throws SQLException {
    return sqlMapClient.queryForList("CrowdInstitutionImage.selectByInstitutionInfoId", id);
  }


  @Override
  public int updateById(InstitutionImage im) throws SQLException {
    return (Integer) sqlMapClient
        .update("CrowdInstitutionImage.ibatorgenerated_updateByPrimaryKeySelective", im);
  }
}
