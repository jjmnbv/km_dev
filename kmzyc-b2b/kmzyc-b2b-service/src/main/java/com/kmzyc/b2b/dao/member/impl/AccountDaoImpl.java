package com.kmzyc.b2b.dao.member.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.dao.member.AccountDao;
import com.kmzyc.zkconfig.ConfigurationUtil;

@Component
public class AccountDaoImpl extends DaoImpl implements AccountDao {

  // static Logger logger = Logger.getLogger(AccountDaoImpl.class);

  private static Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 计算某用户一段时间内的消费总额
   * 
   * @param userId
   * @param beginDate
   * @param endDate
   * @return
   * @throws SQLException
   */
  @Override
public BigDecimal countAmountPayable(long userId, Date beginDate, Date endDate)
      throws SQLException {
    BigDecimal amountPayableSum = null;
    try {
      Map<String, Object> para = new HashMap<String, Object>();
      para.put("userId", userId);
      para.put("beginDate", beginDate);
      para.put("endDate", endDate);
      amountPayableSum =
          (BigDecimal) this.sqlMapClient.queryForObject("OrderMain.countAmountPayable", para);
    } catch (SQLException e) {
      logger.error("计算用户的总消费金额出错：" + e.getMessage(), e);
      throw e;
    }
    return amountPayableSum;
  }

  /**
   * 计算某用户一段时间内已完成订单的消费总额
   * 
   * @param userId
   * @param beginDate
   * @param endDate
   * @return
   * @throws SQLException
   */
  @Override
public BigDecimal countAmountCompleteable(long userId, Date beginDate, Date endDate)
      throws SQLException {
    BigDecimal amountPayableSum = null;
    try {
      Map<String, Object> para = new HashMap<String, Object>();
      para.put("userId", userId);
      para.put("beginDate", beginDate);
      para.put("endDate", endDate);
      para.put("channel", ConfigurationUtil.getString("CHANNEL"));
      amountPayableSum =
          (BigDecimal) this.sqlMapClient.queryForObject("OrderMain.countAmountCompleteAble", para);
    } catch (SQLException e) {
      logger.error("计算用户已完成订单的总消费金额出错：" + e.getMessage(), e);
      throw e;
    }
    return amountPayableSum;
  }

  /**
   * 计算某用户一段时间内的总退货返换金额
   * 
   * @param userId
   * @param beginDate
   * @param endDate
   * @return
   * @throws SQLException
   */
  @Override
public BigDecimal countReturnAmount(long userId, Date beginDate, Date endDate)
      throws SQLException {
    BigDecimal returnSum = null;
    try {
      Map<String, Object> para = new HashMap<String, Object>();
      para.put("userId", userId);
      para.put("beginDate", beginDate);
      para.put("endDate", endDate);
      returnSum =
          (BigDecimal) this.sqlMapClient.queryForObject("OrderMain.countReturnAmount", para);
    } catch (SQLException e) {
      logger.error("计算用户一段时间内的总退货返还金额出错：" + e.getMessage(), e);
      throw e;
    }
    return returnSum;
  }

}
