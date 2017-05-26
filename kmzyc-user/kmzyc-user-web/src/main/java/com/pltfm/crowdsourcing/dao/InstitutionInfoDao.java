package com.pltfm.crowdsourcing.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.commons.general.model.Page;
import com.km.crowdsourcing.model.InstitutionApplyRecord;
import com.km.crowdsourcing.model.InstitutionInfo;


public interface InstitutionInfoDao {

  List<InstitutionInfo> queryInsInfoList(Page<Map<String, Object>> page) throws SQLException;

  int queryInsInfoListCount(Page<Map<String, Object>> page) throws SQLException;

  InstitutionInfo queryInsInfoByInsCode(String institutionCode) throws SQLException;

  InstitutionInfo queryInsInfoByInsCodeOrInsName(InstitutionApplyRecord institutionApplyRecord)
      throws SQLException;

  InstitutionInfo queryInsInfoByInsName(InstitutionApplyRecord institutionApplyRecord)
      throws SQLException;

  int insertInsInfo(InstitutionInfo institutionInfo) throws SQLException;

  int updateInsInfo(InstitutionInfo institutionInfo) throws SQLException;

  int updateInsInfoSelective(InstitutionInfo institutionInfo) throws SQLException;

  int deleteByPrimaryKey(int id) throws SQLException;

  int selectCountByVo(Page<InstitutionInfo> pageParam) throws SQLException;

  List<InstitutionInfo> selectPageByVo(Page<InstitutionInfo> pageParam) throws SQLException;

  InstitutionInfo getInstitutionInfo(Long id) throws SQLException;

  InstitutionInfo selectByPrimaryId(InstitutionInfo institutionInfo) throws SQLException;


}
