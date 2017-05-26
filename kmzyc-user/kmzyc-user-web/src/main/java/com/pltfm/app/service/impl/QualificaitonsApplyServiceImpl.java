package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.QualificationsApplyDAO;
import com.pltfm.app.service.QualificaitonsApplyService;
import com.pltfm.app.vobject.QualificationsApplyDO;


@Component(value = "qualificaitonsApplyService")

public class QualificaitonsApplyServiceImpl implements QualificaitonsApplyService {


  @Resource(name = "qualificationsApplyDAO")
  private QualificationsApplyDAO qualificationsApplyDAO;


  public QualificationsApplyDAO getQualificationsApplyDAO() {
    return qualificationsApplyDAO;
  }


  public void setQualificationsApplyDAO(QualificationsApplyDAO qualificationsApplyDAO) {
    this.qualificationsApplyDAO = qualificationsApplyDAO;
  }


  @Override
  public void deleteById(Integer id) throws SQLException {

    qualificationsApplyDAO.deleteQualificationsApplyDOByPrimaryKey(id);
  }



  @Override
  public List selectListQualificaitonsApply(QualificationsApplyDO qualificationsApplyDO)
      throws SQLException {
    // TODO Auto-generated method stub
    return qualificationsApplyDAO.findListByExample(qualificationsApplyDO);
  }


  @Override
  public Integer selectListQualificaitonsApplyCount(QualificationsApplyDO qualificationsApplyDO)
      throws SQLException {
    // TODO Auto-generated method stub
    return qualificationsApplyDAO.countQualificationsApplyDOByExample(qualificationsApplyDO);
  }


  @Override
  public void updateQualificaitonsApply(QualificationsApplyDO qualificationsApplyDO)
      throws SQLException {

    qualificationsApplyDAO.updateQualificationsApplyDO(qualificationsApplyDO);
  }


  @Override
  public QualificationsApplyDO queryQualificaitonsApply(Integer id) throws SQLException {
    // TODO Auto-generated method stub
    return qualificationsApplyDAO.findQualificationsApplyDOByPrimaryKey(id);
  }



}
