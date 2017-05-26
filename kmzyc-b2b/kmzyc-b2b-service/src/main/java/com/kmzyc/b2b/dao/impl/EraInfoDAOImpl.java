package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.dao.EraInfoDAO;
import com.kmzyc.b2b.vo.EraInfo;

@Repository("eraInfoDAO")
public class EraInfoDAOImpl extends DaoImpl implements EraInfoDAO {

  // static Logger logger = Logger.getLogger(EraInfoDAOImpl.class);
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
public EraInfo findEraInfoById(Map<String, Object> mapConditon) throws SQLException {
    EraInfo eranInfo =
        (EraInfo) sqlMapClient.queryForObject("ERA_INFO.findEranInfoByLogin", mapConditon);
    return eranInfo;
  }

  @Override
public EraInfo selectEranInfoAndLoginInfoById(Map<String, Object> mapConditon)
      throws SQLException {
    EraInfo eranInfo =
        (EraInfo) sqlMapClient.queryForObject("ERA_INFO.findEranInfoByLoginInfo", mapConditon);
    return eranInfo;
  }

  /**
   * 查询时代会员
   * 
   * @param loginId
   * @return
   * @throws SQLException
   */
  @Override
public EraInfo queryEraInfoByLoginId(Long loginId) throws SQLException {
    return (EraInfo) sqlMapClient
        .queryForObject("ERA_INFO.SQL_QUERY_ERA_INFO_BY_LOGIN_ID", loginId);
  }

  /**
   * 根据ID查询时代个人信息
   * 
   * @param loginId
   * @return
   * @throws SQLException
   */
  @Override
  public EraInfo queryEraPersonalInfo(Long loginId) throws SQLException {
    return (EraInfo) sqlMapClient.queryForObject("ERA_INFO.queryEraPersonalInfo", loginId);
  }

  /**
   * 根据时代编号查询时代帐号
   * 
   * @param eraNo
   * @return
   * @throws SQLException
   */
  @Override
  public EraInfo queryEraInfoByEraNo(String eraNo) throws SQLException {
    return (EraInfo) sqlMapClient.queryForObject(
        "ERA_INFO.SQL_QUERY_ERAINFO_BY_ERANO", eraNo);
  }
}
