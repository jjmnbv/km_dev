package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.QualificationsDAO;
import com.pltfm.app.service.QualificaitonsService;
import com.pltfm.app.vobject.QualificationsDO;


@Component(value = "qualificaitonsService")

public class QualificaitonsServiceImpl implements QualificaitonsService {

  @Resource(name = "qualificationsDAO")
  private QualificationsDAO qualificationsDAO;



  public QualificationsDAO getQualificationsDAO() {
    return qualificationsDAO;
  }



  public void setQualificationsDAO(QualificationsDAO qualificationsDAO) {
    this.qualificationsDAO = qualificationsDAO;
  }



  public Integer insertQualifications(QualificationsDO qualificationsDO) throws SQLException {
    // TODO Auto-generated method stub
    return qualificationsDAO.insertQualificationsDO(qualificationsDO);
  }



  @Override
  public void deleteById(Integer userId) throws SQLException {
    // TODO Auto-generated method stub
    qualificationsDAO.deleteQualificationsDOByPrimaryKey(userId);
  }



  @Override
  public List qualificaitonsList(QualificationsDO qualificationsDO) throws SQLException {
    // TODO Auto-generated method stub
    return qualificationsDAO.qualificaitonsList(qualificationsDO);
  }



  @Override
  public void updateQualificaitons(QualificationsDO qualificationsDO) throws SQLException {
    // TODO Auto-generated method stub
    qualificationsDAO.updateQualificationsDO(qualificationsDO);
  }



  @Override
  public List selectListQualificaitons(QualificationsDO qualificaitonsDO) throws SQLException {
    // TODO Auto-generated method stub
    return qualificationsDAO.qualificaitonsList(qualificaitonsDO);
  }



  @Override
  public Integer selectListQualificaitonsCount(QualificationsDO qualificaitonsDO)
      throws SQLException {
    // TODO Auto-generated method stub
    return qualificationsDAO.countQualificationsDOByExample(qualificaitonsDO);
  }



  @Override
  public QualificationsDO queryQualificaitons(Integer id) throws SQLException {
    // TODO Auto-generated method stub
    return qualificationsDAO.findQualificationsDOByPrimaryKey(id);
  }



  @Override
  public Integer qualificaitonsEdit(QualificationsDO qualificaitonsDO) throws SQLException {
    // TODO Auto-generated method stub
    return qualificationsDAO.qualificaitonsEdit(qualificaitonsDO);
  }



  @Override
  public List getQualificaitonsList(QualificationsDO qualificaitonsDO) throws SQLException {
    // TODO Auto-generated method stub
    return qualificationsDAO.getQualificaitonsList(qualificaitonsDO);
  }



}
