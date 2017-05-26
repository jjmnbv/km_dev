package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.vobject.OrderPreferentialVO;

/**
 * 下单服务接口
 * 
 * @author lvxingxing
 * @version 1.0
 * @since 2013-7-25
 */
public interface AgencyCreateOrderService {

  /**
   * 订单生成（普通商品）
   * 
   * @return order：订单实体 orderItemList：订单明细集合 返回值：成功则返回订单编号，失败则是0
   */
  public String agencyCreateOrder(OrderMain order, List<OrderItem> orderItemList,
      List<OrderPreferentialVO> orderPreferentialList, List<OrderPayStatement> orderPayStatementList)
      throws ServiceException;

  /**
   * 订单生成（奖品）
   * 
   * @return order：订单实体 orderItemList：订单明细集合 返回值：成功则返回订单编号，失败则是0
   */
  public String agencyCreateOrderForPrize(OrderMain order, List<OrderItem> orderItemList,
      List<OrderPreferentialVO> orderPreferentialList, List<OrderPayStatement> orderPayStatementList)
      throws ServiceException;

  /**
   * 更新订单
   * 
   * @return
   */
  public Boolean updateOrder(OrderMain order) throws SQLException;

  /**
   * 批量更新order
   * 
   * @param list
   * @throws SQLException
   */
  public void updateList(List<OrderMain> list) throws SQLException;
}
