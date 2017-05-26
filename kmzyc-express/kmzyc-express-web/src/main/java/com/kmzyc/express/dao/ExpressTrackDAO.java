package com.kmzyc.express.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.express.entities.ExpressTrack;

@SuppressWarnings("unchecked")
public interface ExpressTrackDAO {
  /**
   * 查询物流跟踪详细信息
   * 
   * @param paramMap 查询参数
   * @return
   * @throws Exception
   */
  List queryExpressTrackList(Map<String, String> paramMap) throws Exception;

  /**
   * 根据物流跟踪详细信息数量
   * 
   * @param paramMap
   * @return
   * @throws Exception
   */
  int queryExpressTrackCount(Map<String, String> paramMap) throws Exception;

  /**
   * 新增物流跟踪明细信息
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  BigDecimal insertExpressTrack(ExpressTrack record) throws Exception;


  /**
   * 批量新增物流跟踪明细信息
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  Integer batchInsertExpressTrack(List recordList) throws Exception;


  /**
   * 根据订阅记录删除物流跟踪信息
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  Integer deleteExpressTrackBySubId(BigDecimal subId) throws Exception;



}
