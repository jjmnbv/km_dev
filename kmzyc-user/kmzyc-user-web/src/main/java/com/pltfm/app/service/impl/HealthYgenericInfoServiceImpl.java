package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.dao.HealthYgenericInfoDAO;
import com.pltfm.app.service.HealthYgenericInfoService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.HealthYgenericInfo;
import com.pltfm.app.vobject.HealthYgenericInfoExample;

public class HealthYgenericInfoServiceImpl implements HealthYgenericInfoService {
  private HealthYgenericInfoDAO healthYgenericInfoDAO;

  public HealthYgenericInfoDAO getHealthYgenericInfoDAO() {
    return healthYgenericInfoDAO;
  }

  public void setHealthYgenericInfoDAO(HealthYgenericInfoDAO healthYgenericInfoDAO) {
    this.healthYgenericInfoDAO = healthYgenericInfoDAO;
  }

  /**
   * 添加健康类属信息
   * 
   * @param 健康类属信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public void addHealthYgenericInfo(HealthYgenericInfo healthYgenericInfo) throws SQLException {
    healthYgenericInfoDAO.insert(healthYgenericInfo);
  }

  /**
   * 删除健康类属信息
   * 
   * @param 健康类属信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public Integer deleteHealthYgenericInfo(List<String> nHealthYgenericIds) throws SQLException {
    Integer count = 0;
    if (ListUtils.isNotEmpty(nHealthYgenericIds)) {
      for (String id : nHealthYgenericIds) {
        count += healthYgenericInfoDAO.deleteByPrimaryKey(Integer.parseInt(id));
      }
    }
    return count;
  }

  /**
   * 查询健康类属信息
   * 
   * @param 健康类属信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public List<HealthYgenericInfoExample> queryHealthYgenericInfoList(
      HealthYgenericInfoExample healthYgenericInfo) throws SQLException {
    return healthYgenericInfoDAO.selectByExample(healthYgenericInfo);
  }

  /**
   * 修改健康类属信息
   * 
   * @param 健康类属信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public Integer udpateHealthYgenericInfo(HealthYgenericInfo healthYgenericInfo)
      throws SQLException {
    return healthYgenericInfoDAO.updateByPrimaryKey(healthYgenericInfo);
  }

  /**
   * 跟据登录ID健康类属信息
   * 
   * @param HealthYgenericInfo 健康类属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public HealthYgenericInfo getLoginId(Integer nLoginId) throws SQLException {
    return healthYgenericInfoDAO.selectByPrimaryFk(nLoginId);
  }

}
