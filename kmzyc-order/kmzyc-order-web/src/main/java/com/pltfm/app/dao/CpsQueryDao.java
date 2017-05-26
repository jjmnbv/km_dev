/*
 * 删除cps业务 package com.pltfm.app.dao;
 * 
 * import java.sql.SQLException; import java.util.List; import java.util.Map;
 * 
 * import com.kmzyc.commons.exception.ServiceException;
 * 
 *//**
 * cps 查询dao
 * 
 * @author xlg
 * 
 *//*
@SuppressWarnings("unchecked")
public interface CpsQueryDao {
  *//**
   * 查询CPS跳转数据
   * 
   * @return
   * @throws ServiceException
   *//*
  public List queryCPSTrackInfo(Map<String, String> paramsMap) throws SQLException;

  *//**
   * 查询CPS跳转数据条数
   * 
   * @return
   * @throws ServiceException
   *//*
  public Integer queryCPSTrackInfoCount(Map<String, String> paramsMap) throws SQLException;

  *//**
   * CPS订单标识信息数据
   * 
   * @param paramsMap
   * @return
   * @throws SQLException
   *//*
  public List queryCPSOrderFlag(Map<String, String> paramsMap) throws SQLException;

  *//**
   * CPS订单标识信息条数
   * 
   * @param paramsMap
   * @return
   * @throws SQLException
   *//*
  public Integer queryCPSOrderFlagCount(Map<String, String> paramsMap) throws SQLException;

  *//**
   * CPS请求明细数据
   * 
   * @param paramsMap
   * @return
   * @throws SQLException
   *//*
  public List queryCPSRequestInfo(Map<String, String> paramsMap) throws SQLException;

  *//**
   * CPS请求明细条数
   * 
   * @param paramsMap
   * @return
   * @throws SQLException
   *//*
  public Integer queryCPSRequestInfoCount(Map<String, String> paramsMap) throws SQLException;

  *//**
   * CPS订单数据
   * 
   * @param paramsMap
   * @return
   * @throws SQLException
   *//*
  public List queryCPSOrderInfo(Map<String, String> paramsMap) throws SQLException;

  *//**
   * CPS订单数据条数
   * 
   * @param paramsMap
   * @return
   * @throws SQLException
   *//*
  public Integer queryCPSOrderInfoCount(Map<String, String> paramsMap) throws SQLException;
}
*/
