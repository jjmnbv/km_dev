package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.DetectionResultInfo;
import com.pltfm.app.vobject.DetectionResultInfoExample;

public interface DetectionResultInfoService {
  /**
   * 添加检测结果信息
   * 
   * @param 检测结果信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public void addDetectionResult(DetectionResultInfo detectionResultInfo) throws SQLException;

  /**
   * 删除检测结果信息
   * 
   * @param 检测结果信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public Integer deleteDetectionResult(List<String> nDetectionResultIds) throws SQLException;

  /**
   * 查询检测结果信息
   * 
   * @param 检测结果信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public List<DetectionResultInfoExample> queryDetectionResultList(
      DetectionResultInfoExample detectionResultInfo) throws SQLException;

  /**
   * 修改检测结果信息
   * 
   * @param 检测结果信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public Integer udpateDetectionResult(DetectionResultInfo detectionResultInfo) throws SQLException;

  /**
   * 跟据健康类属ID检测结果信息
   * 
   * @param HealthYgenericInfo 检测结果信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public DetectionResultInfo getHealthYgenericId(Integer nHealthYgenericId) throws SQLException;
}
