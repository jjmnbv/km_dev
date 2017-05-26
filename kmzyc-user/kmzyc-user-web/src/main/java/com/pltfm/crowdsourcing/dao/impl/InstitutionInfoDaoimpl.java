package com.pltfm.crowdsourcing.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.commons.general.model.Page;
import com.km.crowdsourcing.model.InstitutionApplyRecord;
import com.km.crowdsourcing.model.InstitutionInfo;
import com.pltfm.crowdsourcing.dao.InstitutionInfoDao;

@Component(value = "institutionInfoDao")
public class InstitutionInfoDaoimpl implements InstitutionInfoDao {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;



  @Override
  public List<InstitutionInfo> queryInsInfoList(Page<Map<String, Object>> page)
      throws SQLException {
    return sqlMapClient.queryForList("CrowdInstitutionInfo.todoInstitutionInfo", page);
  }

  @Override
  public int queryInsInfoListCount(Page<Map<String, Object>> page) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("CrowdInstitutionInfo.todoInstitutionInfoCount",
        page);
  }

  @Override
  public InstitutionInfo queryInsInfoByInsCode(String institutionCode) throws SQLException {
    return (InstitutionInfo) sqlMapClient.queryForObject(
        "CrowdInstitutionInfo.ibatorgenerated_selectByInstitutionCode", institutionCode);
  }

  @Override
  public InstitutionInfo queryInsInfoByInsCodeOrInsName(
      InstitutionApplyRecord institutionApplyRecord) throws SQLException {
    return (InstitutionInfo) sqlMapClient.queryForObject(
        "CrowdInstitutionInfo.ibatorgenerated_selectByInstitutionCodeOrName",
        institutionApplyRecord);
  }

  @Override
  public InstitutionInfo queryInsInfoByInsName(InstitutionApplyRecord institutionApplyRecord)
      throws SQLException {
    return (InstitutionInfo) sqlMapClient.queryForObject(
        "CrowdInstitutionInfo.ibatorgenerated_selectByInstitutionName", institutionApplyRecord);
  }

  @Override
  public int insertInsInfo(InstitutionInfo institutionInfo) throws SQLException {
    return (Integer) sqlMapClient.insert("CrowdInstitutionInfo.ibatorgenerated_insert",
        institutionInfo);
  }

  @Override
  public int updateInsInfo(InstitutionInfo institutionInfo) throws SQLException {
    return (Integer) sqlMapClient.update(
        "CrowdInstitutionInfo.ibatorgenerated_updateByPrimaryKeySelective", institutionInfo);
  }

  @Override
  public int updateInsInfoSelective(InstitutionInfo institutionInfo) throws SQLException {

    return (Integer) sqlMapClient.update(
        "CrowdInstitutionInfo.ibatorgenerated_updateByPrimaryLloginIdSelective", institutionInfo);
  }

  @Override
  public int deleteByPrimaryKey(int id) throws SQLException {
    return (Integer) sqlMapClient.delete("CrowdInstitutionInfo.todoupdateInsAppRecord", id);
  }

  @Override
  public int selectCountByVo(Page<InstitutionInfo> pageParam) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("CrowdInstitutionInfo.selectCountByVo", pageParam);
  }

  @Override
  public List<InstitutionInfo> selectPageByVo(Page<InstitutionInfo> pageParam) throws SQLException {
    return sqlMapClient.queryForList("CrowdInstitutionInfo.selectListByVo", pageParam);

  }

  @Override
  public InstitutionInfo getInstitutionInfo(Long id) throws SQLException {
    return (InstitutionInfo) sqlMapClient
        .queryForObject("CrowdInstitutionInfo.ibatorgenerated_selectById", id);
  }

  @Override
  public InstitutionInfo selectByPrimaryId(InstitutionInfo institutionInfo) throws SQLException {
    return (InstitutionInfo) sqlMapClient
        .queryForObject("CrowdInstitutionInfo.ibatorgenerated_selectByPrimaryKey", institutionInfo);
  }


}
