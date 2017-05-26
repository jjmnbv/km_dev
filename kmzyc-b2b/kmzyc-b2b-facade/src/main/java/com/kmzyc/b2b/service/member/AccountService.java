package com.kmzyc.b2b.service.member;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.OrderPayStatement;
import com.kmzyc.b2b.model.User;

public interface AccountService {

  /**
   * 根据用户id查询对应的账户信息
   * 
   * @param userId
   * @return
   * @throws SQLException
   */
  public AccountInfo findAccountByUserId(long userId) throws SQLException;

  /**
   * 计算一段时间内客户消费的总金额
   * 
   * @param userId
   * @param beginDate
   * @param endDate
   * @return
   * @throws SQLException
   */
  public BigDecimal countConsumptionSum(long userId, Date beginDate, Date endDate)
      throws SQLException;

  /**
   * 计算一段时间内已完成客户消费的总金额
   * 
   * @param userId
   * @param beginDate
   * @param endDate
   * @return
   * @throws SQLException
   */
  public BigDecimal countCompleteOrder(long userId, Date beginDate, Date endDate)
      throws SQLException;

  /**
   * 查询用户的消费明细
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  public Pagination findConsumptionDetailByPage(Pagination page) throws SQLException;

  /**
   * 查询用户的消费明细
   * 
   * @param paramsMap
   * @return
   * @throws SQLException
   */
  public List<OrderPayStatement> findConsumptionDetailByOrderCode(Map<String, Object> paramsMap)
      throws SQLException;
  
  /**
   * 个人中心我的信息接口整合查询
   */
  public User findHomeLoadById (Map<String,Object> paramMap) throws  SQLException;

}
