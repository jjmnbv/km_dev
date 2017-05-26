package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.framework.exception.ServiceException;

/**
 * 
 * @author houlin
 * 
 */
public interface EraInfoService {

  /***
   * 根据loginId 查询是否是时代会员
   */
  public EraInfo selectEranInfoById(Map<String, Object> mapConditon) throws SQLException;

  /***
   * 根据loginId 直销会员信息
   */
  public EraInfo selectEranInfoAndLoginInfoById(Map<String, Object> mapConditon)
      throws SQLException;

  /**
   * 查询时代会员
   * 
   * @param loginId
   * @return
   * @throws ServiceException
   */
  public EraInfo queryEraInfoByLoginId(Long loginId) throws ServiceException;
  
  /**
   * 根据登录ID查询时代个人信息
   * @param loginId
   * @return
   * @throws Exception
   */
  public EraInfo  queryEraPersonalInfo(Long loginId) throws Exception;

  /**
   * 更新时代会员信息
   * @param jsonArray
   * @return
   */
  Map<String,String> updateEraInfo(JSONArray jsonArray);

}
