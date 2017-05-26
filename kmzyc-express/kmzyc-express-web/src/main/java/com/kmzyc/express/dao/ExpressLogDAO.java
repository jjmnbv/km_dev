package com.kmzyc.express.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.express.entities.ExpressLog;

@SuppressWarnings("unchecked")
public interface ExpressLogDAO {
  /**
   * 查询日志
   * 
   * @param paramMap 查询参数
   * @return
   * @throws Exception
   */
  List queryExpressLogList(Map<String, String> paramMap) throws Exception;

  /**
   * 根据查询参数查询日志数量
   * 
   * @param paramMap
   * @return
   * @throws Exception
   */
  int queryExpressLogCount(Map<String, String> paramMap) throws Exception;

  /**
   * 新增订阅日志信息
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  public BigDecimal insertExpressLog(ExpressLog record) throws SQLException;

}
