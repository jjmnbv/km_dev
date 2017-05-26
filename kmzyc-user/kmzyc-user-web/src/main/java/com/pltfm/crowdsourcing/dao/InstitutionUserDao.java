package com.pltfm.crowdsourcing.dao;

import java.sql.SQLException;
import java.util.List;

import com.km.commons.general.model.Page;
import com.km.crowdsourcing.model.ClearingUser;
import com.km.crowdsourcing.model.InstitutionUser;

public interface InstitutionUserDao {
  int deleteByPrimaryKey(Long id);

  int insert(InstitutionUser record);

  int insertSelective(InstitutionUser record) throws Exception;

  int checkUserExists(String mobie) throws Exception;

  int updateByPrimaryKeySelective(InstitutionUser record);

  int updateByPrimaryKey(InstitutionUser record) throws SQLException;

  List<ClearingUser> selectByClearingUser(List<String> ids) throws Exception;

  int selectCountByVo(Page institutionUser) throws Exception;

  List selectPageByVo(Page institutionUser) throws Exception;

  String getinstitutionUserIds(Page nPage) throws Exception;

  List<String> selectIdsByClearingUser(List<String> ids) throws Exception;

  List<String> selectIdsByUser(InstitutionUser institutionUser) throws Exception;

  Integer unClearByIds(String ids) throws Exception;

  List<ClearingUser> selectByClearingUserIds(List list) throws Exception;



}
