package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.QualificationsApplyDO;

public interface QualificaitonsApplyService {

  Integer selectListQualificaitonsApplyCount(QualificationsApplyDO qualificationsApplyDO)
      throws SQLException;

  List selectListQualificaitonsApply(QualificationsApplyDO qualificationsApplyDO)
      throws SQLException;


  void deleteById(Integer id) throws SQLException;

  void updateQualificaitonsApply(QualificationsApplyDO qualificationsApplyDO) throws SQLException;

  QualificationsApplyDO queryQualificaitonsApply(Integer id) throws SQLException;


}
