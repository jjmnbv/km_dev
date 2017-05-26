package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kmzyc.b2b.dao.OrderPayStatementDAO;
import com.kmzyc.b2b.model.OrderPayStatement;

@Repository
@SuppressWarnings("unchecked")
public class OrderPayStatementDAOImpl implements OrderPayStatementDAO {
  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  @Override
  public List<OrderPayStatement> queryByOrderCode(Map<String, String> map) throws SQLException {
    return this.sqlMapClient.queryForList("OrderPayStatement.findByOrderCode", map);
  }

  @Override
  public OrderPayStatement findrefundOrderPayStatement(String orderCode) throws SQLException {
    return (OrderPayStatement) this.sqlMapClient.queryForObject(
        "OrderPayStatement.findrefundOrderPayStatement", orderCode);
  }

  /**
   * 根据第三方流水号查询订单信息
   * 
   * @param batchNo
   * @return
   * @throws SQLException
   */
  public Map findOrderInfoByOuterStatementNo(String batchNo) throws SQLException {
    return (Map) this.sqlMapClient.queryForObject(
        "OrderPayStatement.findOrderInfoByOuterStatementNo", batchNo);
  }

  /**
   * 查询订单的预支付平台编码
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public String queryReadyPayPlatCode(String orderCode) throws SQLException {
    String queryc = null;
    if (orderCode.contains("a")) {// 如果订单号包含字母a,则说明是尾款支付回调
      queryc = orderCode.replace("a", "");
    }else{
      queryc = orderCode;
    }
    String platCode = null;
    List<String> list =
        sqlMapClient.queryForList("OrderPayStatement.QUERY_READY_PAY_PLAT_CODE", queryc);
    if (null != list && !list.isEmpty()) {
      platCode = list.get(0);
    }
    return platCode;
  }

@Override
public int updateByPrimaryKey(OrderPayStatement record) throws SQLException {
	int rows =
	        sqlMapClient.update("OrderPayStatement.ibatorgenerated_updateByPrimaryKey",
	            record);
	    return rows;
}

@Override
public Long insert(OrderPayStatement record) throws SQLException {
	 Object newKey =
		        sqlMapClient.insert("OrderPayStatement.ibatorgenerated_insert", record);
		    return (Long) newKey;
}

@Override
public OrderPayStatement findrefundOrderPayStatementForYs(Map<String,String> map)
    throws SQLException {
  OrderPayStatement ops = null;
  ops = (OrderPayStatement) this.sqlMapClient.queryForObject("OrderPayStatement.findrefundOrderPayStatementForYs", map);
  return ops;
}
}
