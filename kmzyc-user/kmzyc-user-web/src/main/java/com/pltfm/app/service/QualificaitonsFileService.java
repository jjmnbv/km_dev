package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.QualificaitonsFileDO;

public interface QualificaitonsFileService {

  Integer selectListQualificaitonsFileCount(QualificaitonsFileDO qualificationsFile)
      throws SQLException;

  List selectListQualificaitonsFile(QualificaitonsFileDO qualificationsFile) throws SQLException;

  QualificaitonsFileDO queryQualificaitonsFile(Integer bigDecimal) throws SQLException;

  void deleteById(Integer id) throws SQLException;

  void updateQualificaitonsFile(QualificaitonsFileDO qualificaitonsFileDO) throws SQLException;

  List queryListQualificaitonsFile(Integer userId) throws SQLException;

}
