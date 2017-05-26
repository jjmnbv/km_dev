package com.kmzyc.b2b.service.member;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.model.OrderAlter;
import com.kmzyc.b2b.vo.TraceInfoVO;

public interface ReturnGoodsTraceService {
  /**
   * 根据退货单号和订单号查询整个操作流水信息
   * 
   * @param orderAlterCode
   * @param orderCode
   * @return
   */
  public List<TraceInfoVO> getTraceInfo(String orderAlterCode) throws Exception;

  /**
   * 获取退货信息
   * 
   * @param orderAlterCode
   * @return
   * @throws SQLException
   */
  public OrderAlter getOrderAlter(String orderAlterCode) throws SQLException;
}
