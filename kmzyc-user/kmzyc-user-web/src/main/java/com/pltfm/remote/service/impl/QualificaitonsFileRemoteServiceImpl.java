package com.pltfm.remote.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.user.remote.service.QualificaitonsFileRemoteService;
import com.pltfm.app.dao.QualificaitonsFileDAO;
import com.pltfm.app.vobject.QualificaitonsFileDO;

@Component(value = "qualificaitonsFileRemoteService")
public class QualificaitonsFileRemoteServiceImpl implements QualificaitonsFileRemoteService {
  @Resource(name = "qualificaitonsFileDAO")
  private QualificaitonsFileDAO qualificaitonsFileDAO;

  public QualificaitonsFileDAO getQualificaitonsFileDAO() {
    return qualificaitonsFileDAO;
  }

  public void setQualificaitonsFileDAO(QualificaitonsFileDAO qualificaitonsFileDAO) {
    this.qualificaitonsFileDAO = qualificaitonsFileDAO;
  }

  public Integer insertQualificaitonsFileDO(QualificaitonsFileDO qualificaitonsFileDO)
      throws SQLException {
    return qualificaitonsFileDAO.insertQualificaitonsFileDO(qualificaitonsFileDO);
  }

  @Override
  public Integer updateQualificaitonsFileDO(QualificaitonsFileDO qualificaitonsFileDO)
      throws SQLException {
    // TODO Auto-generated method stub
    return qualificaitonsFileDAO.updateQualificaitonsFileDO(qualificaitonsFileDO);
  }

}
