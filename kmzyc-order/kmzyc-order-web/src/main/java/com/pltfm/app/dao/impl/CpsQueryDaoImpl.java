/*
 * 删除cps业务 package com.pltfm.app.dao.impl;
 * 
 * import java.sql.SQLException; import java.util.List; import java.util.Map;
 * 
 * import org.springframework.stereotype.Repository;
 * 
 * import com.pltfm.app.dao.BaseDAO; import com.pltfm.app.dao.CpsQueryDao; import
 * com.kmzyc.commons.exception.ServiceException;
 * 
 * @Repository("cpsQueryDao")
 * 
 * @SuppressWarnings("unchecked") public class CpsQueryDaoImpl extends BaseDAO implements
 * CpsQueryDao {
 *//**
   * 查询CPS跳转数据
   * 
   * @return
   * @throws ServiceException
   *//*
  public List queryCPSTrackInfo(Map<String, String> paramsMap) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_CPS_QUERY.SQL_QUERY_CPS_TRACK_INFO", paramsMap);
  }

  *//**
   * 查询CPS跳转数据条数
   * 
   * @return
   * @throws ServiceException
   *//*
  public Integer queryCPSTrackInfoCount(Map<String, String> paramsMap) throws SQLException {
    return (Integer) sqlMapClient.queryForObject(
        "KMORDER_CPS_QUERY.SQL_QUERY_CPS_TRACK_INFO_COUNT", paramsMap);
  }

  *//**
   * CPS订单标识信息数据
   * 
   * @param paramsMap
   * @return
   * @throws SQLException
   *//*
  public List queryCPSOrderFlag(Map<String, String> paramsMap) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_CPS_QUERY.SQL_QUERY_CPS_ORDER_FLAG", paramsMap);
  }

  *//**
   * CPS订单标识信息条数
   * 
   * @param paramsMap
   * @return
   * @throws SQLException
   *//*
  public Integer queryCPSOrderFlagCount(Map<String, String> paramsMap) throws SQLException {
    return (Integer) sqlMapClient.queryForObject(
        "KMORDER_CPS_QUERY.SQL_QUERY_CPS_ORDER_FLAG_COUNT", paramsMap);
  }

  *//**
   * CPS请求明细数据
   * 
   * @param paramsMap
   * @return
   * @throws SQLException
   *//*
  public List queryCPSRequestInfo(Map<String, String> paramsMap) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_CPS_QUERY.SQL_QUERY_CPS_REQUEST_INFO", paramsMap);
  }

  *//**
   * CPS请求明细条数
   * 
   * @param paramsMap
   * @return
   * @throws SQLException
   *//*
  public Integer queryCPSRequestInfoCount(Map<String, String> paramsMap) throws SQLException {
    return (Integer) sqlMapClient.queryForObject(
        "KMORDER_CPS_QUERY.SQL_QUERY_CPS_REQUEST_INFO_COUNT", paramsMap);
  }

  *//**
   * CPS订单数据
   * 
   * @param paramsMap
   * @return
   * @throws SQLException
   *//*
  public List queryCPSOrderInfo(Map<String, String> paramsMap) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_CPS_QUERY.SQL_QUERY_CPS_ORDER_INFO", paramsMap);
  }

  *//**
   * CPS订单数据条数
   * 
   * @param paramsMap
   * @return
   * @throws SQLException
   *//*
  public Integer queryCPSOrderInfoCount(Map<String, String> paramsMap) throws SQLException {
    return (Integer) sqlMapClient.queryForObject(
        "KMORDER_CPS_QUERY.SQL_QUERY_CPS_ORDER_INFO_COUNT", paramsMap);
  }
}
*/
