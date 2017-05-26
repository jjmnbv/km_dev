package com.pltfm.remote.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.user.remote.service.QualificaitonsApplyRemoteService;
import com.pltfm.app.dao.QualificationsApplyDAO;
import com.pltfm.app.vobject.QualificationsApplyDO;


@Component(value = "qualificaitonsApplyRemoteService")
public class QualificaitonsApplyRemoteServiceImpl implements QualificaitonsApplyRemoteService {

  @Resource(name = "qualificationsApplyDAO")
  private QualificationsApplyDAO qualificationsApplyDAO;



  public QualificationsApplyDAO getQualificationsApplyDAO() {
    return qualificationsApplyDAO;
  }



  public void setQualificationsApplyDAO(QualificationsApplyDAO qualificationsApplyDAO) {
    this.qualificationsApplyDAO = qualificationsApplyDAO;
  }



  public Integer insertQualificaitonsApplyDO(QualificationsApplyDO qualificationsApplyDO)
      throws SQLException {
    return qualificationsApplyDAO.insertQualificationsApplyDO(qualificationsApplyDO);
  }



}
