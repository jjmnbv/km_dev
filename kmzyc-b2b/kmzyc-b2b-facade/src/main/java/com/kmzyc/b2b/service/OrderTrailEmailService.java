package com.kmzyc.b2b.service;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.OrderMain;

import java.sql.SQLException;

/**
 * 订单跟踪服务接口
 * 
 * @author Administrator
 * 
 */
public interface OrderTrailEmailService {


  /**
   * 查询订单
   * 
   * @param orderNo
   * @return
   * @throws SQLException
   */
  public OrderMain findOrderMainByOrderNo(String orderNo, String mobole) throws SQLException;

  /**
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  Pagination findOrderMainByPage(Pagination page) throws SQLException;

  /**
   * 根据手机号和邮箱进行查询
   * 
   * @param email
   * @param mobile
   * @return
   * @throws SQLException
   */
  public Integer findByorderEmailorMobile(String email, String mobile) throws SQLException;

  /**
   * 根据订单号好手机号进行查询看是否存在数据
   * 
   * @param orderNo
   * @param mobole
   * @return
   * @throws SQLException
   */
  public Integer findOrderMainByOrderNoOrMobile(String orderNo, String mobole) throws SQLException;

  // 查询订单 成果网
  Pagination findOrderMainByPageCgw(Pagination page) throws SQLException;

  // 查询订单 领客
  Pagination findOrderMainByPageLK(Pagination page) throws SQLException;

  // 查询订单 领客
  Pagination findOrderMainByPageDuoMai(Pagination page) throws SQLException;
}
