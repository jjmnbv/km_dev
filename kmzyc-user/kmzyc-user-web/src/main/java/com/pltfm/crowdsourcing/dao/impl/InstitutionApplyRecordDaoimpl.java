package com.pltfm.crowdsourcing.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.commons.general.model.Page;
import com.km.crowdsourcing.model.InstitutionApplyRecord;
import com.km.crowdsourcing.model.InstitutionApplyRecordCriteria;
import com.pltfm.crowdsourcing.dao.InstitutionApplyRecordDao;

@Component(value = "institutionApplyRecordDao")
public class InstitutionApplyRecordDaoimpl implements InstitutionApplyRecordDao {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;



  @Override
  public int queryInsAppRecordListCount(Page<Map<String, Object>> page) throws SQLException {
    return (Integer) sqlMapClient
        .queryForObject("CrowdInstitutionApplyRecord.todoInstitutionApplyRecordCount", page);
  }

  @Override
  public List<InstitutionApplyRecord> queryInsAppRecordListByInsCode(String institutionCode)
      throws SQLException {
    return sqlMapClient.queryForList(
        "CrowdInstitutionApplyRecord.todoqueryInsAppRecordListByInsCode", institutionCode);
  }

  @Override
  public int insertInsAppRecord(InstitutionApplyRecord institutionApplyRecord) throws SQLException {
    return (Integer) sqlMapClient.insert("CrowdInstitutionApplyRecord.ibatorgenerated_insert",
        institutionApplyRecord);
  }

  @Override
  public int updateInsAppRecord(InstitutionApplyRecord institutionApplyRecord) throws SQLException {
    return (Integer) sqlMapClient.update(
        "CrowdInstitutionApplyRecord.ibatorgenerated_updateByPrimaryKeyForAudit",
        institutionApplyRecord);
  }

  @Override
  public InstitutionApplyRecord queryAuditingByInsId(int institutionId) throws SQLException {
    return (InstitutionApplyRecord) sqlMapClient
        .queryForObject("CrowdInstitutionApplyRecord.ibatorgenerated_selectByInsId", institutionId);
  }

  @Override
  public InstitutionApplyRecord queryAuditingByInsName(
      InstitutionApplyRecord institutionApplyRecord) throws SQLException {
    return (InstitutionApplyRecord) sqlMapClient.queryForObject(
        "CrowdInstitutionApplyRecord.ibatorgenerated_selectAuditingByInstitutionName",
        institutionApplyRecord);
  }

  @Override
  public InstitutionApplyRecord queryAuditingByInsCodeOrInsName(
      InstitutionApplyRecord institutionApplyRecord) throws SQLException {
    return (InstitutionApplyRecord) sqlMapClient.queryForObject(
        "CrowdInstitutionApplyRecord.ibatorgenerated_selectAuditingByInstitutionCodeOrName",
        institutionApplyRecord);
  }

  @Override
  public int selectApplyRecordCount(InstitutionApplyRecordCriteria institutionApplyRecordCriteria)
      throws SQLException {
    int rows =
        (Integer) sqlMapClient.queryForObject("CrowdInstitutionApplyRecord.selectApplyRecordCount",
            institutionApplyRecordCriteria);
    return rows;

  }

  @Override
  public List<InstitutionApplyRecord> selectApplyRecordList(
      InstitutionApplyRecordCriteria institutionApplyRecordCriteria) throws SQLException {
    return sqlMapClient.queryForList("CrowdInstitutionApplyRecord.selectApplyRecordList",
        institutionApplyRecordCriteria);
  }

  @Override
  public InstitutionApplyRecord selectApplyRecord(
      InstitutionApplyRecordCriteria institutionApplyRecordCriteria) throws SQLException {
    return (InstitutionApplyRecord) sqlMapClient.queryForObject(
        "CrowdInstitutionApplyRecord.ibatorgenerated_selectByPrimaryKey",
        institutionApplyRecordCriteria);
  }

  @Override
  public InstitutionApplyRecord selectApplyRecordByInsCode(
      InstitutionApplyRecordCriteria institutionApplyRecordCriteria) throws SQLException {
    return (InstitutionApplyRecord) sqlMapClient.queryForObject(
        "CrowdInstitutionApplyRecord.ibatorgenerated_selectByInsCode",
        institutionApplyRecordCriteria);
  }


}
