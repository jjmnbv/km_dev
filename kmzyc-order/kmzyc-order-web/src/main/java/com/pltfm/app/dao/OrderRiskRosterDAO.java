package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.entities.OrderRiskBackList;
import com.pltfm.app.entities.OrderRiskWhiteList;

public interface OrderRiskRosterDAO {

  /**
   * 分页获取风控黑名单
   * 
   * @return
   */
  public Page queryBlackListByPage(Page page, Map<String, String> params) throws SQLException;

  /**
   * 添加黑名单
   * 
   * @param back
   * @throws ServiceException
   */
  public boolean saveBlackList(OrderRiskBackList back) throws SQLException;

  /**
   * 添加黑名单
   * 
   * @param back
   * @throws ServiceException
   */
  public boolean saveBlackList(List<OrderRiskBackList> list) throws SQLException;

  /**
   * 删除黑名单
   * 
   * @param bid
   * @return
   * @throws SQLException
   */
  public boolean deleteBlackList(Long bid) throws SQLException;

  /**
   * 查询黑名单
   * 
   * @param bid
   * @return
   * @throws SQLException
   */
  public OrderRiskBackList queryOrderRiskBackListById(Long bid) throws SQLException;

  /**
   * 分页获取风控白名单
   * 
   * @return
   */
  public Page queryWhiteListByPage(Page page, Map<String, String> params) throws SQLException;

  /**
   * 添加白名单
   * 
   * @param back
   * @throws ServiceException
   */
  public boolean saveWhiteList(List<OrderRiskWhiteList> list) throws SQLException;

  /**
   * 添加白名单
   * 
   * @param back
   * @throws ServiceException
   */
  public boolean saveWhiteList(OrderRiskWhiteList orwl) throws SQLException;

  /**
   * 删除白名单
   * 
   * @param bid
   * @return
   * @throws SQLException
   */
  public boolean deleteWhiteList(Long wid) throws SQLException;

  /**
   * 查询白名单
   * 
   * @param bid
   * @return
   * @throws SQLException
   */
  public OrderRiskWhiteList queryOrderRiskWhiteListById(Long wid) throws SQLException;

  /**
   * 查询黑名单
   * 
   * @param params
   */
  public List<OrderRiskBackList> queryBlackList(Map<String, Object> params) throws SQLException;

  /**
   * 查询白名单
   * 
   * @param params
   */
  public List<OrderRiskWhiteList> queryWhiteList(Map<String, Object> params) throws SQLException;

  /**
   * 查找账号信息
   * 
   * @param content
   * @return
   * @throws SQLException
   */
  Long queryLoginInfo(String content) throws SQLException;

  /**
   * 查找商家信息
   * 
   * @param content
   * @return
   * @throws SQLException
   */
  Long queryCorporate(String content) throws SQLException;

  /**
   * 根据loginId查询黑名单
   * 
   * @param params
   */
  public boolean queryBlackListByLoginId(long loginId) throws SQLException;
  
}
