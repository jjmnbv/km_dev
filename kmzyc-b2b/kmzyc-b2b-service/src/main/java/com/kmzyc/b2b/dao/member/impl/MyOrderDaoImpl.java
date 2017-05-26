package com.kmzyc.b2b.dao.member.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.member.MyOrderDao;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.OrderPreferential;
import com.kmzyc.b2b.model.ProductImage;
import com.kmzyc.b2b.vo.ExeOrderData;
import com.kmzyc.framework.exception.ServiceException;
import com.km.framework.persistence.impl.DaoImpl;

@SuppressWarnings("unchecked")
@Component
public class MyOrderDaoImpl extends DaoImpl implements MyOrderDao {

 // static Logger logger = Logger.getLogger(MyOrderDaoImpl.class);
  private static Logger logger = LoggerFactory.getLogger(MyOrderDaoImpl.class);

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public String findRootOrderCode(String orderCode) throws SQLException {
    Object obj = sqlMapClient.queryForObject("OrderMain.findRootOrder", orderCode);
    if (obj != null) {
      return (String) obj;
    }
    return null;
  }

  /**
   * 根据skuId查找产品的默认图片信息（主图）
   * 
   * @param sqlId
   * @param skuCode
   * @return
   */
  public ProductImage findDefaultProductImageBySkuCode(String sqlId, String skuCode)
      throws SQLException {
    ProductImage productImage = null;
    try {
      productImage = (ProductImage) this.sqlMapClient.queryForObject(sqlId, skuCode);
    } catch (Exception e) {
      logger.error("查找产品图片信息出现异常，异常原因：" + e.getMessage(), e);
      throw new SQLException(e.getMessage(), e);
    }
    return productImage;
  }

  /**
   * 根据订单号查询子订单
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public List<OrderMain> queryChildOrderByOrderCode(List<String> orderCode) throws SQLException {
    return (List<OrderMain>) sqlMapClient.queryForList("OrderMain.SQL_QUERY_MY_CHILD_ORDER",
        orderCode);
  }

  /**
   * 根据订单号查询订单
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  @Override
  public OrderMain queryOrderMainByOrderCode(String orderCode) throws SQLException {
    return (OrderMain) sqlMapClient.queryForObject("OrderMain.SQL_QUERY_ORDER_MAIN_BY_ORDER_CODE",
        orderCode);
  }

  /**
   * 根据订状态统计我的订单数量
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  @Override
  public Map<String, BigDecimal> queryMyOrderStatusCount(Map<String, Object> map)
      throws SQLException {
    return (Map<String, BigDecimal>) sqlMapClient
        .queryForObject("OrderMain.SQL_QUERY_MY_ORDER_STATUS_COUNT", map);
  }

  /**
   * 根据订状态统计我的订单数量
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  @Override
  public Map<String, BigDecimal> queryMyOrderAppraiseCount(Map<String, Object> map)
      throws SQLException {
    return (Map<String, BigDecimal>) sqlMapClient.queryForObject(
        "OrderMain.SQL_QUERY_MY_ORDER_ADDAPPRAISECOUNTANDAPPRAISECOUNTANDITEM_COUNT", map);
  }


  /**
   * OMS查询结转订单
   * 
   * @return
   * @throws SQLException
   */
  @Override
  public List<ExeOrderData> queryExeOrder(Map<String, String> params) throws SQLException {
    return (List<ExeOrderData>) sqlMapClient.queryForList("OrderMain.SQL_QUERY_EXE_ORDER", params);
  }

  /**
   * OMS查询结转订单
   * 
   * @return
   * @throws SQLException
   */
  @Override
  public Integer queryExeOrderCount(Map<String, String> params) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("OrderMain.SQL_QUERY_EXE_ORDER_COUNT", params);
  }

  /**
   * 查询用户启用支付密码条件
   * 
   * @param uid
   * @return
   * @throws SQLException
   */
  @Override
  public Map<String, String> queryEnablePayPWDCondition(Long uid) throws SQLException {
    return (Map<String, String>) sqlMapClient
        .queryForObject("OrderMain.SQL_QUERY_ENABLE_PAY_PWD_CONDITION", uid);
  }

  @Override
  public BigDecimal sumPvByCustomerId(long customerId) throws SQLException {
    return (BigDecimal) sqlMapClient.queryForObject("OrderMain.SQL_SUM_PV", customerId);

  }
  
  @Override
  public List<Map<String, Object>> findReserveByOrderCode(String orederCode) throws SQLException {
	return sqlMapClient.queryForList("OrderMain.FIND_RESERVE_BY_ORDER_CODE", orederCode);
  }
  
  @Override
  public String findReserveByOrderPhone(String orederCode) throws SQLException {
	return sqlMapClient.queryForObject("OrderMain.FIND_RESERVE_BY_ORDER_PHONE", orederCode)+"";
  }
  
  @Override
  public List<Map<String, Object>> findReserveByOrderMoney(String orederCode) throws SQLException {
	return sqlMapClient.queryForList("OrderMain.FIND_RESERVE_BY_ORDER_MONEY", orederCode);
  }
  
  @Override
  public List<Map<String, Object>> findStateByOrderCode(String orederCode) throws SQLException {
	return sqlMapClient.queryForList("OrderMain.FIND_STATE_BY_ORDER_CODE", orederCode);
  }
  
  @Override
  public Integer findPresellStatusByOrderCode(String orederCode) throws SQLException {
	return (Integer) sqlMapClient.queryForObject("OrderMain.FIND_PRESELL_STATUS_BY_ORDER_CODE", orederCode);
  }
  
  @Override
  public String judgeDepositTimeByOrderMoney(String orederCode) throws SQLException {
	return sqlMapClient.queryForObject("OrderMain.JUDGE_DEPOSIT_TIME_BY_ORDER_MONEY", orederCode)+"";
  }
  
  @Override
  public String findDepositTailByOrderCode(String orederCode) throws SQLException {
	return sqlMapClient.queryForObject("OrderMain.FIND_DEPOSIT_TAIL_BY_ORDER_CODE", orederCode)+"";
  }

    @Override
    public List<OrderPreferential> orderPreferentialList(Map map) throws Exception {
        return (List<OrderPreferential>)this.sqlMapClient.queryForList("OrderMain.queryOrderPreferentialList", map);
    }


}
