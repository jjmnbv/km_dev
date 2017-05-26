package com.kmzyc.express.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressTrack;

@SuppressWarnings("unchecked")
public interface ExpressTrackService {

  List queryExpressTrackListByPage(Map<String, String> paramMap) throws ServiceException;

  int queryExpressTrackCount(Map<String, String> paramMap) throws ServiceException;

  /**
   * 新增物流跟踪明细信息
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  BigDecimal insertExpressTrack(ExpressTrack record) throws ServiceException;


  Integer batchInsertExpressTrack(List recordList) throws ServiceException;

  Integer deleteExpressTrackBySubId(BigDecimal subId) throws ServiceException;

}
