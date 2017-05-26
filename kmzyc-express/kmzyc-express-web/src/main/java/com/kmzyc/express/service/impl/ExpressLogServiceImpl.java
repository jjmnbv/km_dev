package com.kmzyc.express.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.dao.ExpressLogDAO;
import com.kmzyc.express.entities.ExpressLog;
import com.kmzyc.express.service.ExpressLogService;
import com.kmzyc.express.util.ErrorCode;

@Service("expressLogService")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class ExpressLogServiceImpl implements ExpressLogService {

  private static Logger logger = Logger.getLogger(ExpressLogServiceImpl.class);

  @Resource
  private ExpressLogDAO expressLogDAO;

  @Override
  public int queryExpressLogCount(Map<String, String> paramMap) throws ServiceException {
    try {
      return expressLogDAO.queryExpressLogCount(paramMap);
    } catch (Exception e) {
      logger.error("查询物流同步日志列表数量发生异常：" + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_LOG_ERR, "查询物流同步日志列表数量发生异常：" + e.getMessage());
    }
  }

  @Override
  public List queryExpressLogListByPage(Map<String, String> paramMap) throws ServiceException {
    try {
      return expressLogDAO.queryExpressLogList(paramMap);
    } catch (Exception e) {
      logger.error("查询物流同步日志列表发生异常：" + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_LOG_ERR, "查询物流同步日志列表发生异常：" + e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public BigDecimal insertExpressLog(ExpressLog record) throws ServiceException {
    try {
      return expressLogDAO.insertExpressLog(record);
    } catch (SQLException e) {
      logger.error("新增物流订阅日志信息发生异常：" + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_LOG_ERR, "新增物流订阅日志信息发生异常：" + e.getMessage());
    }
  }
}
