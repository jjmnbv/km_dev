package com.pltfm.remote.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.user.remote.service.QualificationsRemoteService;
import com.pltfm.app.dao.QualificationsDAO;
import com.pltfm.app.vobject.QualificationsDO;


@Component(value = "qualificationsRemoteService")
public class QualificationsRemoteServiceImpl implements QualificationsRemoteService {



  @Resource(name = "qualificationsDAO")
  private QualificationsDAO qualificationsDAO;

  public Integer insertQualificaitonsDO(QualificationsDO qualificationsDO) throws SQLException {
    // TODO Auto-generated method stub
    return qualificationsDAO.insertQualificationsDO(qualificationsDO);
  }

  public QualificationsDAO getQualificationsDAO() {
    return qualificationsDAO;
  }

  public void setQualificationsDAO(QualificationsDAO qualificationsDAO) {
    this.qualificationsDAO = qualificationsDAO;
  }



}
