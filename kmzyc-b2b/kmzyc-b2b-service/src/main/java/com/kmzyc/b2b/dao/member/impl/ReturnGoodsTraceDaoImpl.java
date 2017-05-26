package com.kmzyc.b2b.dao.member.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.member.ReturnGoodsTraceDao;
import com.kmzyc.b2b.model.OrderAlter;
import com.kmzyc.b2b.vo.TraceInfoVO;
import com.km.framework.persistence.impl.DaoImpl;

@Component("returnGoodsTraceDao")
public class ReturnGoodsTraceDaoImpl extends DaoImpl implements ReturnGoodsTraceDao {

  @javax.annotation.Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public List<TraceInfoVO> getOrderInfo(String orderCode) throws SQLException {
    return sqlMapClient.queryForList("TraceInfo.qryOrderInfo", orderCode);
  }

  @Override
  public List<TraceInfoVO> getReturnGoodsInfo(String orderAlterCode) throws SQLException {
    return sqlMapClient.queryForList("TraceInfo.qryAlterInfo", orderAlterCode);
  }

  @Override
  public OrderAlter qryOrderAlterByCode(String orderAlterCode) throws SQLException {
    return (OrderAlter) sqlMapClient.queryForObject("TraceInfo.qryOrderAlter", orderAlterCode);
  }

}
