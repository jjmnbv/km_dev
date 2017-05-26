package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.HealthYgenericInfoService;
import com.pltfm.app.vobject.HealthYgenericInfo;

public class HealthYgenericAction extends ActionSupport implements ModelDriven {
  private HealthYgenericInfoService healthYgenericInfoService;
  private HealthYgenericInfo healthYgenericInfo;

  private int nLoginId;// 登录id
  /** 多条删除健康id集合 **/
  private List<String> nHealthYgenericIds;

  public HealthYgenericInfo getHealthYgenericInfo() {
    return healthYgenericInfo;
  }

  public void setHealthYgenericInfo(HealthYgenericInfo healthYgenericInfo) {
    this.healthYgenericInfo = healthYgenericInfo;
  }

  public int getnLoginId() {
    return nLoginId;
  }

  public void setnLoginId(int nLoginId) {
    this.nLoginId = nLoginId;
  }

  public List<String> getnHealthYgenericIds() {
    return nHealthYgenericIds;
  }

  public void setnHealthYgenericIds(List<String> nHealthYgenericIds) {
    this.nHealthYgenericIds = nHealthYgenericIds;
  }

  public HealthYgenericInfoService getHealthYgenericInfoService() {
    return healthYgenericInfoService;
  }

  public void setHealthYgenericInfoService(HealthYgenericInfoService healthYgenericInfoService) {
    this.healthYgenericInfoService = healthYgenericInfoService;
  }

  @Override
  public Object getModel() {
    healthYgenericInfo = new HealthYgenericInfo(); // TODO Auto-generated method stub
    return healthYgenericInfo;
  }

  public String add() {
    try {
      // healthYgenericInfo.setnHealthYgenericId(11);
      // healthYgenericInfo.setnLoginId(111);
      // healthYgenericInfo.setSocialSecurityNumber("dsadsad");
      // healthYgenericInfo.setnMaritalStatus(1);
      healthYgenericInfoService.addHealthYgenericInfo(healthYgenericInfo);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return SUCCESS;
  }

  public String delete() {
    try {
      healthYgenericInfoService.deleteHealthYgenericInfo(nHealthYgenericIds);
      return "delSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      return INPUT;
    }
  }

  public String update() {
    try {
      // healthYgenericInfo.setnHealthYgenericId(11);
      // healthYgenericInfo.setnLoginId(2222);
      // healthYgenericInfo.setSocialSecurityNumber("aaaaaaaaaaa");
      // healthYgenericInfo.setnMaritalStatus(0);
      healthYgenericInfoService.udpateHealthYgenericInfo(healthYgenericInfo);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return SUCCESS;
  }

  public String getLoginId() {
    try {
      healthYgenericInfo = healthYgenericInfoService.getLoginId(nLoginId);

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return SUCCESS;
  }
}
