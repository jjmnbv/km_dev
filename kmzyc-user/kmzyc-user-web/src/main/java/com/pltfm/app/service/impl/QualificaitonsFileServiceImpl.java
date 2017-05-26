package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.QualificaitonsFileDAO;
import com.pltfm.app.service.QualificaitonsFileService;
import com.pltfm.app.vobject.QualificaitonsFileDO;


@Component(value = "qualificaitonsFileService")

public class QualificaitonsFileServiceImpl implements QualificaitonsFileService {


  @Resource(name = "qualificaitonsFileDAO")
  private QualificaitonsFileDAO qualificaitonsFileDAO;

  public QualificaitonsFileDAO getQualificaitonsFileDAO() {
    return qualificaitonsFileDAO;
  }

  public void setQualificaitonsFileDAO(QualificaitonsFileDAO qualificaitonsFileDAO) {
    this.qualificaitonsFileDAO = qualificaitonsFileDAO;
  }

  @Override
  public List selectListQualificaitonsFile(QualificaitonsFileDO qualificationsFile)
      throws SQLException {
    // TODO Auto-generated method stub
    return qualificaitonsFileDAO.findListByExample(qualificationsFile);
  }


  public Integer selectListQualificaitonsFileCount(QualificaitonsFileDO qualificationsFile)
      throws SQLException {
    return qualificaitonsFileDAO.countQualificaitonsFileDOByExample(qualificationsFile);
  }



  public QualificaitonsFileDO queryQualificaitonsFile(Integer bigDecimal) throws SQLException {
    // TODO Auto-generated method stub
    return qualificaitonsFileDAO.findQualificaitonsFileDOByPrimaryKey(bigDecimal);
  }

  @Override
  public void deleteById(Integer id) throws SQLException {
    // TODO Auto-generated method stub
    qualificaitonsFileDAO.deleteQualificaitonsFileDOByPrimaryKey(id);
  }

  @Override
  public void updateQualificaitonsFile(QualificaitonsFileDO qualificaitonsFileDO)
      throws SQLException {

    qualificaitonsFileDAO.updateQualificaitonsFileDO(qualificaitonsFileDO);
  }

  @Override
  public List queryListQualificaitonsFile(Integer userId) throws SQLException {
    // TODO Auto-generated method stub
    return qualificaitonsFileDAO.findListQualificaitonsFile(userId);
  }


}
