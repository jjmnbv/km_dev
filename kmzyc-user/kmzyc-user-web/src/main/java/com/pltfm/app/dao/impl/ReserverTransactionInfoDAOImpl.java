package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ReserverTransactionInfoDAO;
import com.pltfm.app.vobject.ReserverTransactionInfo;

@Component(value = "reserverTransactionInfoDAO")
public class ReserverTransactionInfoDAOImpl implements ReserverTransactionInfoDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;


  // 删除
  public int deleteByPrimaryKey(BigDecimal transationId) throws SQLException {
    ReserverTransactionInfo key = new ReserverTransactionInfo();
    key.setTransationId(transationId);
    int rows =
        sqlMapClient.delete("RESERVER_TRANSACTION_INFO.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  // 添加
  public void insert(ReserverTransactionInfo record) throws SQLException {
    sqlMapClient.insert("RESERVER_TRANSACTION_INFO.ibatorgenerated_insert", record);
  }

  // 查询
  public ReserverTransactionInfo selectByPrimaryKey(BigDecimal transationId) throws SQLException {
    ReserverTransactionInfo key = new ReserverTransactionInfo();
    key.setTransationId(transationId);
    ReserverTransactionInfo record = (ReserverTransactionInfo) sqlMapClient
        .queryForObject("RESERVER_TRANSACTION_INFO.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  // 修改
  public int updateByPrimaryKeySelective(ReserverTransactionInfo record) throws SQLException {
    int rows = sqlMapClient
        .update("RESERVER_TRANSACTION_INFO.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

}
