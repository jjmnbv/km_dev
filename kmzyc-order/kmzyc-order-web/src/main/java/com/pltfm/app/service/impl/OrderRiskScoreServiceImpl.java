package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.OrderRiskScoreDao;
import com.pltfm.app.entities.OrderRiskScore;
import com.pltfm.app.service.OrderRiskScoreService;
import com.pltfm.sys.util.ErrorCode;

/**
 * 判断得分
 * 
 * @author Administrator
 * 
 */
@Service
@Scope("singleton")
public class OrderRiskScoreServiceImpl implements OrderRiskScoreService {

  @Resource
  private OrderRiskScoreDao orderRiskScoreDao;

  /**
   * 查询判断得分
   * 
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  @Override
public List<OrderRiskScore> queryOrderRiskScore(String orderCode) throws ServiceException {
    try {
      return orderRiskScoreDao.queryOrderRiskScore(orderCode);
    } catch (SQLException e) {
      throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR, "查询判断得分发生异常：" + e.getMessage());
    }
  }
}
