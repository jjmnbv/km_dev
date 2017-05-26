package com.pltfm.app.dao;

import com.pltfm.app.vobject.ReserverBill;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface ReserverBillDAO {
  /**
   * 分页获取所有账单
   * 
   * @param bill
   * @return
   * @throws SQLException
   */
  List<ReserverBill> queryReserverBillAll(ReserverBill bill) throws SQLException;

  /**
   * 计算所有账单总数
   * 
   * @param bill
   * @return
   * @throws SQLException
   */
  Integer queryReserverBillCount(ReserverBill bill) throws SQLException;

  /**
   * 删除账单
   * 
   * @param billId
   * @return
   * @throws SQLException
   */
  int deleteByPrimaryKey(BigDecimal billId) throws SQLException;

  /**
   * 添加账单
   * 
   * @param record
   * @throws SQLException
   */
  void insert(ReserverBill record) throws SQLException;



  /**
   * 查询账单
   * 
   * @param reserverBill
   * @return
   * @throws SQLException
   */
  ReserverBill selectByPrimaryKey(ReserverBill reserverBill) throws SQLException;


  /**
   * 修改账单
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByPrimaryKeySelective(ReserverBill record) throws SQLException;
}
