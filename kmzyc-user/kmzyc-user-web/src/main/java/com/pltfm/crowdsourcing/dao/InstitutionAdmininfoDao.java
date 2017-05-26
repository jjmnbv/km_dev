package com.pltfm.crowdsourcing.dao;

import com.km.crowdsourcing.model.InstitutionAdmininfo;


public interface InstitutionAdmininfoDao {
  int deleteByPrimaryKey(Long id);

  int insert(InstitutionAdmininfo record);

  int insertSelective(InstitutionAdmininfo record);

  InstitutionAdmininfo selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(InstitutionAdmininfo record);

  int updateByPrimaryKey(InstitutionAdmininfo record);
}
