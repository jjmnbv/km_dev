package com.pltfm.crowdsourcing.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.commons.general.model.Page;
import com.km.crowdsourcing.model.InstitutionApplyRecord;
import com.km.crowdsourcing.model.InstitutionApplyRecordCriteria;


public interface InstitutionApplyRecordDao {

  int queryInsAppRecordListCount(Page<Map<String, Object>> page) throws SQLException;

  List<InstitutionApplyRecord> queryInsAppRecordListByInsCode(String institutionCode)
      throws SQLException;

  InstitutionApplyRecord queryAuditingByInsId(int institutionId) throws SQLException;

  InstitutionApplyRecord queryAuditingByInsCodeOrInsName(
      InstitutionApplyRecord institutionApplyRecord) throws SQLException;

  InstitutionApplyRecord queryAuditingByInsName(InstitutionApplyRecord institutionApplyRecord)
      throws SQLException;



  int insertInsAppRecord(InstitutionApplyRecord institutionApplyRecord) throws SQLException;

  int updateInsAppRecord(InstitutionApplyRecord institutionApplyRecord) throws SQLException;

  public int selectApplyRecordCount(InstitutionApplyRecordCriteria institutionApplyRecordCriteria)
      throws SQLException;

  public List<InstitutionApplyRecord> selectApplyRecordList(
      InstitutionApplyRecordCriteria institutionApplyRecordCriteria) throws SQLException;

  public InstitutionApplyRecord selectApplyRecord(
      InstitutionApplyRecordCriteria institutionApplyRecordCriteria) throws SQLException;

  public InstitutionApplyRecord selectApplyRecordByInsCode(
      InstitutionApplyRecordCriteria institutionApplyRecordCriteria) throws SQLException;


}
