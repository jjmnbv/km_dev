package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.QualificationsDO;

public interface QualificaitonsService {

  Integer insertQualifications(QualificationsDO qualificationsDO) throws SQLException;

  void deleteById(Integer userId) throws SQLException;

  List qualificaitonsList(QualificationsDO qualificationsDO) throws SQLException;

  void updateQualificaitons(QualificationsDO qualificationsDO) throws SQLException;

  List selectListQualificaitons(QualificationsDO qualificaitonsDO) throws SQLException;

  Integer selectListQualificaitonsCount(QualificationsDO qualificaitonsDO) throws SQLException;

  QualificationsDO queryQualificaitons(Integer id) throws SQLException;

  Integer qualificaitonsEdit(QualificationsDO qualificaitonsDO) throws SQLException;

  List getQualificaitonsList(QualificationsDO qualificaitonsDO) throws SQLException;


}
