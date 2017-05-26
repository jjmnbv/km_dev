package com.pltfm.crowdsourcing.dao;

import java.sql.SQLException;
import java.util.List;

import com.km.crowdsourcing.model.InstitutionImage;


public interface InstitutionImageDao {
  int deleteByInsAppId(Long insAppId) throws SQLException;

  int insert(InstitutionImage record) throws SQLException;

  void bathInsert(List<InstitutionImage> recordList) throws SQLException;

  List<InstitutionImage> selectByInsAppId(Long insAppId) throws SQLException;

  List<InstitutionImage> selectByInstitutionInfoId(Long id) throws SQLException;

  int updateById(InstitutionImage im) throws SQLException;
}
