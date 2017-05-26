package com.pltfm.crowdsourcing.dao;

import java.sql.SQLException;
import java.util.Map;


public interface InstitutionAdminDao {
  public Map<String, Object> queryUserInfoForLogin(Map<String, String> params) throws SQLException;

  public int insActivateLoginInfo(Map<String, String> params) throws SQLException;

  public int insActivateAccountInfo(Map<String, String> params) throws SQLException;

  public int insActivateInsInfo(Map<String, String> params) throws SQLException;

  public void updatePassword(Map<String, String> params) throws SQLException;

  public void updateLIVmobile(Map<String, String> params) throws SQLException;

  public void updateAIVmobile(Map<String, String> params) throws SQLException;

  public Map<String, String> selectInsInfoForHomePage(String insCode) throws SQLException;

  public String queryLoginAccountByInstitutioncode(String institutionCode) throws SQLException;

}
