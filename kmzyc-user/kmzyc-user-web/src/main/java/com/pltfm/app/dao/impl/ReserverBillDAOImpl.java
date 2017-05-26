package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ReserverBillDAO;
import com.pltfm.app.vobject.ReserverBill;

@Component(value = "reserverBillDAO")
public class ReserverBillDAOImpl implements ReserverBillDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;


  // 分页获取账单
  public List<ReserverBill> queryReserverBillAll(ReserverBill bill) throws SQLException {
    return sqlMapClient.queryForList("RESERVER_BILL.ibatorgenerated_selectReserverBill", bill);
  }

  // 计算账单总数
  public Integer queryReserverBillCount(ReserverBill bill) throws SQLException {
    return (Integer) sqlMapClient
        .queryForObject("RESERVER_BILL.ibatorgenerated_selectReserverBillCount", bill);
  }

  // 删除账单
  public int deleteByPrimaryKey(BigDecimal billId) throws SQLException {
    ReserverBill key = new ReserverBill();
    key.setBillId(billId);
    int rows = sqlMapClient.delete("RESERVER_BILL.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  // 添加账单
  public void insert(ReserverBill record) throws SQLException {
    sqlMapClient.insert("RESERVER_BILL.ibatorgenerated_insert", record);
  }

  // 查询账单
  public ReserverBill selectByPrimaryKey(ReserverBill reserverBill) throws SQLException {
    ReserverBill record = (ReserverBill) sqlMapClient
        .queryForObject("RESERVER_BILL.ibatorgenerated_selectByPrimaryKey", reserverBill);
    return record;
  }


  // 修改账单
  public int updateByPrimaryKeySelective(ReserverBill record) throws SQLException {
    int rows =
        sqlMapClient.update("RESERVER_BILL.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

}
