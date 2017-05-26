package com.kmzyc.b2b.dao.member;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.model.OrderAlter;
import com.kmzyc.b2b.vo.TraceInfoVO;

public interface ReturnGoodsTraceDao {
  /**
   * 查询订单操作流水信息
   * 
   * @param orderCode
   * @return
   */
  public List<TraceInfoVO> getOrderInfo(String orderCode) throws SQLException;

  /**
   * 查询退货操作流水信息
   * 
   * @param orderAlterCode
   * @return
   */
  public List<TraceInfoVO> getReturnGoodsInfo(String orderAlterCode) throws SQLException;

  /**
   * 根据退货号查询退货信息
   * 
   * @param orderAlterCode
   * @return
   * @throws SQLException
   */
  public OrderAlter qryOrderAlterByCode(String orderAlterCode) throws SQLException;
}
