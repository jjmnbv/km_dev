package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.vo.EraInfo;

public interface EraInfoDAO extends Dao {

  public EraInfo findEraInfoById(Map<String, Object> mapConditon) throws SQLException;

  public EraInfo selectEranInfoAndLoginInfoById(Map<String, Object> mapConditon)
      throws SQLException;

  /**
   * 查询时代会员
   * 
   * @param loginId
   * @return
   * @throws SQLException
   */
  public EraInfo queryEraInfoByLoginId(Long loginId) throws SQLException;

  /**
   * 根据ID查询时代个人信息
   * 
   * @param loginId
   * @return
   * @throws SQLException
   */
  public EraInfo queryEraPersonalInfo(Long loginId) throws SQLException;

  /**
   * 根据时代编号查询时代帐号
   * 
   * @param eraNo
   * @return
   * @throws SQLException
   */
  public EraInfo queryEraInfoByEraNo(String eraNo) throws SQLException;
}
