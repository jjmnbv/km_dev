package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.entities.OrderRiskBackList;
import com.pltfm.app.entities.OrderRiskWhiteList;

public interface OrderRiskRosterService {
  /**
   * 分页获取风控黑名单
   * 
   * @return
   */
  public Page queryBlackListByPage(Page page, Map<String, String> params) throws ServiceException;

  /**
   * 添加黑名单
   * 
   * @param back
   * @throws ServiceException
   */
  public boolean saveBlackList(OrderRiskBackList orbl) throws ServiceException;

  /**
   * 删除黑名单
   * 
   * @param bid
   * @return
   * @throws SQLException
   */
  public boolean deleteBlackList(Long bid) throws ServiceException;

  /**
   * 分页获取风控白名单
   * 
   * @return
   */
  public Page queryWhiteListByPage(Page page, Map<String, String> params) throws ServiceException;

  /**
   * 添加白名单
   * 
   * @param back
   * @throws ServiceException
   */
  public boolean saveWhiteList(OrderRiskWhiteList orwl) throws ServiceException;

  /**
   * 删除白名单
   * 
   * @param bid
   * @return
   * @throws SQLException
   */
  public boolean deleteWhiteList(Long wid) throws ServiceException;

  /**
   * 查找黑名单
   * 
   * @param type
   * @param content
   * @return
   * @throws ServiceException
   */
  public List<OrderRiskBackList> queryBlack(Integer type, String content) throws ServiceException;

  /**
   * 查找白名单
   * 
   * @param type
   * @param content
   * @return
   * @throws ServiceException
   */
  public List<OrderRiskWhiteList> queryWhite(Integer type, String content) throws ServiceException;

  /**
   * 检查是黑名单是否已经存在
   * 
   * @param type
   * @param content
   * @return
   * @throws ServiceException
   */
  public boolean isExistBlack(Integer type, String content) throws ServiceException;

  /**
   * 检查白名单是否已存在
   * 
   * @param type
   * @param content
   * @return
   * @throws ServiceException
   */
  public boolean isExistWhite(Integer type, String content) throws ServiceException;

  /**
   * 检查账号或者入驻商家是否存在
   * 
   * @param type
   * @param content
   * @return
   * @throws ServiceException
   */
  boolean isExistAccount(Integer type, String content) throws ServiceException;

  /**
   * 根据loginId查风控黑名单
   * 
   * @param loginId
   * @return boolean
   * @throws ServiceException
   */
  public boolean queryBlackList(long loginId) throws ServiceException;
}
