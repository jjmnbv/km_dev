package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.dao.DetectionResultInfoDAO;
import com.pltfm.app.service.DetectionResultInfoService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.DetectionResultInfo;
import com.pltfm.app.vobject.DetectionResultInfoExample;

public class DetectionResultInfoServiceImpl implements DetectionResultInfoService {
  private DetectionResultInfoDAO detectionResultInfoDAO;

  public DetectionResultInfoDAO getDetectionResultInfoDAO() {
    return detectionResultInfoDAO;
  }

  public void setDetectionResultInfoDAO(DetectionResultInfoDAO detectionResultInfoDAO) {
    this.detectionResultInfoDAO = detectionResultInfoDAO;
  }

  /**
   * 添加检测结果信息
   * 
   * @param 检测结果信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public void addDetectionResult(DetectionResultInfo detectionResultInfo) throws SQLException {
    detectionResultInfoDAO.insert(detectionResultInfo);
  }

  /**
   * 删除检测结果信息
   * 
   * @param 检测结果信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public Integer deleteDetectionResult(List<String> nDetectionResultIds) throws SQLException {
    Integer count = 0;
    if (ListUtils.isNotEmpty(nDetectionResultIds)) {
      for (String id : nDetectionResultIds) {
        count += detectionResultInfoDAO.deleteByPrimaryKey(Integer.parseInt(id));
      }
    }
    return count;
  }

  /**
   * 查询检测结果信息
   * 
   * @param 检测结果信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public List<DetectionResultInfoExample> queryDetectionResultList(
      DetectionResultInfoExample detectionResultInfo) throws SQLException {
    return detectionResultInfoDAO.selectByExample(detectionResultInfo);
  }

  /**
   * 修改检测结果信息
   * 
   * @param 检测结果信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public Integer udpateDetectionResult(DetectionResultInfo detectionResultInfo)
      throws SQLException {
    return detectionResultInfoDAO.updateByPrimaryKey(detectionResultInfo);
  }

  /**
   * 跟据健康类属ID检测结果信息
   * 
   * @param HealthYgenericInfo 检测结果信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public DetectionResultInfo getHealthYgenericId(Integer nHealthYgenericId) throws SQLException {
    return detectionResultInfoDAO.selectByPrimaryFk(nHealthYgenericId);
  }

}
