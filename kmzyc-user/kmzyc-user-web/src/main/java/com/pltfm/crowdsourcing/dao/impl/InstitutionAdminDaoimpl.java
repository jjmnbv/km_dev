package com.pltfm.crowdsourcing.dao.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.crowdsourcing.dao.InstitutionAdminDao;

@Component(value = "institutionAdminDao")
public class InstitutionAdminDaoimpl implements InstitutionAdminDao {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public Map<String, Object> queryUserInfoForLogin(Map<String, String> params) throws SQLException {
    return (Map<String, Object>) sqlMapClient.queryForObject(
        "CrowdInstitutionAdmininfo.ibatorgenerated_selectInfoForLogin", params);

  }

  @Override
  public int insActivateLoginInfo(Map<String, String> params) throws SQLException {
    return (int) sqlMapClient.update(
        "CrowdInstitutionAdmininfo.ibatorgenerated_activateIns_loginInfo", params);
  }

  @Override
  public int insActivateAccountInfo(Map<String, String> params) throws SQLException {
    return (int) sqlMapClient.update(
        "CrowdInstitutionAdmininfo.ibatorgenerated_activateIns_accountInfo", params);
  }

  @Override
  public int insActivateInsInfo(Map<String, String> params) throws SQLException {
    return (int) sqlMapClient.update(
        "CrowdInstitutionAdmininfo.ibatorgenerated_activateIns_insInfo", params);
  }

  @Override
  public Map<String, String> selectInsInfoForHomePage(String insCode) throws SQLException {
    return (Map<String, String>) sqlMapClient.queryForObject(
        "CrowdInstitutionAdmininfo.ibatorgenerated_selectInsInfoForHomePage", insCode);
  }

  @Override
  public void updatePassword(Map<String, String> params) throws SQLException {
    sqlMapClient.update("CrowdInstitutionAdmininfo.ibatorgenerated_updatePassword", params);
  }

  @Override
  public void updateLIVmobile(Map<String, String> params) throws SQLException {
    sqlMapClient.update("CrowdInstitutionAdmininfo.ibatorgenerated_updateLIVmobile", params);
  }

  @Override
  public void updateAIVmobile(Map<String, String> params) throws SQLException {
    sqlMapClient.update("CrowdInstitutionAdmininfo.ibatorgenerated_updateAIVmobile", params);
  }

  @Override
  public String queryLoginAccountByInstitutioncode(String institutionCode) throws SQLException {
    return (String) sqlMapClient.queryForObject(
        "CrowdInstitutionAdmininfo.queryLoginAccountByInstitutioncode", institutionCode);
  }

}
