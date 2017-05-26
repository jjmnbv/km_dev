package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.OrderOperateStatement;
import com.kmzyc.express.entities.ExpressSubscription;

/**
 * 订单跟踪服务接口
 * 
 * @author Administrator
 * 
 */
public interface OrderTrailService {
  /**
   * 查询订单
   * 
   * @param orderNo
   * @return
   * @throws SQLException
   */
  public OrderMain findOrderMainByOrderNo(Integer orderNo) throws SQLException;

  /**
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  Pagination findOrderMainByPage(Pagination page) throws SQLException;

  /**
   * 合并快递查询和系统流水查询
   * 
   * @param list
   * @param expressPath
   */
  public void mergeOrderAndExpress(List<OrderOperateStatement> list, ExpressSubscription expressSub);
}
