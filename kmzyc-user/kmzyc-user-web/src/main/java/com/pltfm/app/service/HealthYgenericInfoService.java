package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.HealthYgenericInfo;
import com.pltfm.app.vobject.HealthYgenericInfoExample;

public interface HealthYgenericInfoService {

  /**
   * 添加健康类属信息
   * 
   * @param 健康类属信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public void addHealthYgenericInfo(HealthYgenericInfo healthYgenericInfo) throws SQLException;

  /**
   * 删除健康类属信息
   * 
   * @param 健康类属信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public Integer deleteHealthYgenericInfo(List<String> nHealthYgenericIds) throws SQLException;

  /**
   * 查询健康类属信息
   * 
   * @param 健康类属信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public List<HealthYgenericInfoExample> queryHealthYgenericInfoList(
      HealthYgenericInfoExample healthYgenericInfo) throws SQLException;

  /**
   * 修改健康类属信息
   * 
   * @param 健康类属信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public Integer udpateHealthYgenericInfo(HealthYgenericInfo healthYgenericInfo)
      throws SQLException;

  /**
   * 跟据登录ID健康类属信息
   * 
   * @param HealthYgenericInfo 健康类属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public HealthYgenericInfo getLoginId(Integer nLoginId) throws SQLException;

}
