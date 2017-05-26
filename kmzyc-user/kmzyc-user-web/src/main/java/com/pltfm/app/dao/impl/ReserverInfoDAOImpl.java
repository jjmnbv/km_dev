package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ReserverInfoDAO;
import com.pltfm.app.vobject.ReserverInfo;

@Component(value = "reserverInfoDAO")
public class ReserverInfoDAOImpl implements ReserverInfoDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  // 根据用户名获取预备金账户
  public List<ReserverInfo> selectReserverInfoByAccountLogin(String accountLogin)
      throws SQLException {
    return sqlMapClient.queryForList(
        "RESERVER_INFO.ibatorgenerated_selectReserverInfoByAccountLogion", accountLogin);
  }

  // 获取所有预备金账户
  public List<ReserverInfo> selectAllReserverInfo() throws SQLException {
    return sqlMapClient.queryForList("RESERVER_INFO.selectAllReserverInfo");
  }

  // 获取条件下开通的预备金总条数
  public int selectCountByInfoAll(ReserverInfo vo) throws SQLException {
    return (Integer) sqlMapClient
        .queryForObject("RESERVER_INFO.ibatorgenerated_selectCountByInfoAll", vo);
  }

  // 分页查询开通的预备金列表
  public List<ReserverInfo> selectReserverInfoAll(ReserverInfo vo) throws SQLException {
    return sqlMapClient.queryForList("RESERVER_INFO.ibatorgenerated_selectReserverInfoAll", vo);
  }

  // 获取条件下预备金总条数
  public int selectCountByInfo(ReserverInfo vo) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("RESERVER_INFO.ibatorgenerated_selectCountByInfo",
        vo);
  }

  // 分页查询预备金列表
  public List<ReserverInfo> selectReserverInfo(ReserverInfo vo) throws SQLException {
    return sqlMapClient.queryForList("RESERVER_INFO.ibatorgenerated_selectReserverInfo", vo);
  }

  // 删除
  public int deleteByPrimaryKey(BigDecimal reserveId) throws SQLException {
    ReserverInfo key = new ReserverInfo();
    key.setReserveId(reserveId);
    int rows = sqlMapClient.delete("RESERVER_INFO.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  // 添加
  public void insertSelective(ReserverInfo record) throws SQLException {
    sqlMapClient.insert("RESERVER_INFO.ibatorgenerated_insertSelective", record);
  }

  // 查询
  public ReserverInfo selectByPrimaryKey(ReserverInfo record) throws SQLException {
    return (ReserverInfo) sqlMapClient
        .queryForObject("RESERVER_INFO.ibatorgenerated_selectByPrimaryKey", record);
  }

  // 修改
  public int updateByPrimaryKey(ReserverInfo record) throws SQLException {
    int rows = sqlMapClient.update("RESERVER_INFO.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }


}
