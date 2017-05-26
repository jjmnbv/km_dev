package com.pltfm.app.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.util.ErrorCode;
import com.pltfm.app.dao.ExpressSubscriptionDAO;
import com.pltfm.app.service.ExpressCompanyService;
import com.pltfm.app.service.ExpressLogService;
import com.pltfm.app.service.ExpressSubscriptionService;
import com.pltfm.app.service.ExpressTrackService;

@SuppressWarnings("unchecked")
@Service("expressSubscriptionService")
@Scope("singleton")
public class ExpressSubscriptionServiceImpl implements ExpressSubscriptionService {
  private static Logger logger = Logger.getLogger(ExpressSubscriptionServiceImpl.class);

  @Resource
  private ExpressSubscriptionDAO expressSubscriptionDAO;

  @Resource
  ExpressLogService expressLogService;

  @Resource
  ExpressTrackService expressTrackService;

  @Resource
  ExpressCompanyService expressCompanyService;

  public static final ExecutorService executors = Executors.newFixedThreadPool(500);// 线程池

  @Override
  public int queryExpressSubscriptionCount(Map<String, String> paramMap) throws ServiceException {
    try {
      return expressSubscriptionDAO.queryExpressSubscriptionCount(paramMap);
    } catch (Exception e) {
      logger.error("查询物流订阅列表数量发生异常：" + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "查询物流订阅列表数量发生异常：" + e.getMessage());
    }
  }

  @Override
  public List queryExpressSubscriptionListByPage(Map<String, String> paramMap)
      throws ServiceException {
    try {
      return expressSubscriptionDAO.queryExpressSubscriptionList(paramMap);
    } catch (Exception e) {
      logger.error("查询物流订阅列表发生异常：" + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "查询物流订阅列表发生异常：" + e.getMessage());
    }
  }

  @Override
  public List queryExpressSubWithTrackDetail(String logisticsCode, String logisticsNo)
      throws ServiceException {
    try {
      return expressSubscriptionDAO.queryExpressSubWithTrackDetail(logisticsCode, logisticsNo);
    } catch (Exception e) {
      logger.error("查询物流订阅及物流跟踪信息发生异常：" + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "查询物流订阅及物流跟踪信息发生异常：" + e.getMessage());
    }
  }

  @Override
  public ExpressSubscription selectSubscriptionByPrimaryKey(ExpressSubscription record)
      throws ServiceException {
    try {
      return expressSubscriptionDAO.selectSubscriptionByPrimaryKey(record);
    } catch (Exception e) {
      logger.error("根据主键获取物流订阅信息异常：subId=" + record.getSubId().toPlainString() + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "根据主键获取物流订阅信息异常：" + e.getMessage());
    }
  }

  @Override
  public int querySubCountByLosCodeAndNo(Map<String, String> paramMap) throws ServiceException {
    try {
      return expressSubscriptionDAO.querySubCountByLosCodeAndNo(paramMap);
    } catch (Exception e) {
      logger.error("根据公司编码和物流单号查询数量发生失败：" + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "根据公司编码和物流单号查询数量发生失败：" + e.getMessage());
    }
  }
}
