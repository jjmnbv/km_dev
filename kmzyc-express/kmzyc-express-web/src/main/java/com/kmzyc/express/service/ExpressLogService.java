package com.kmzyc.express.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressLog;

@SuppressWarnings("unchecked")
public interface ExpressLogService {

  List queryExpressLogListByPage(Map<String, String> paramMap) throws ServiceException;

  int queryExpressLogCount(Map<String, String> paramMap) throws ServiceException;

  /**
   * 新增订阅日志信息
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  public BigDecimal insertExpressLog(ExpressLog record) throws ServiceException;


}
