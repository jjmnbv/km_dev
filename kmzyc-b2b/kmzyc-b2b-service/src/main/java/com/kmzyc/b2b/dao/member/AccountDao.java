package com.kmzyc.b2b.dao.member;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import com.km.framework.persistence.Dao;

public interface AccountDao extends Dao {

  /**
   * 计算某用户一段时间内的消费总额
   * 
   * @param userId
   * @param beginDate
   * @param endDate
   * @return
   * @throws SQLException
   */
  public BigDecimal countAmountPayable(long userId, Date beginDate, Date endDate)
      throws SQLException;

  /**
   * 计算某用户一段时间内已完成订单的消费总额
   * 
   * @param userId
   * @param beginDate
   * @param endDate
   * @return
   * @throws SQLException
   */
  public BigDecimal countAmountCompleteable(long userId, Date beginDate, Date endDate)
      throws SQLException;

  /**
   * 计算某用户一段时间内的总退货返换金额
   * 
   * @param userId
   * @param beginDate
   * @param endDate
   * @return
   * @throws SQLException
   */
  public BigDecimal countReturnAmount(long userId, Date beginDate, Date endDate)
      throws SQLException;
}
