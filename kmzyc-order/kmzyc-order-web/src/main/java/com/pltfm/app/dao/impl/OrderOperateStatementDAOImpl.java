package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.OrderOperateStatementDAO;
import com.pltfm.app.entities.OrderOperateStatement;
import com.pltfm.app.entities.OrderOperateStatementExample;

@SuppressWarnings("unchecked")
@Repository("orderOperateStatementDAO")
public class OrderOperateStatementDAOImpl extends BaseDAO implements OrderOperateStatementDAO {
  public int countByExample(OrderOperateStatementExample example) throws SQLException {
    Integer count =
        (Integer) sqlMapClient.queryForObject(
            "KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_countByExample", example);
    return count.intValue();
  }

  public int deleteByExample(OrderOperateStatementExample example) throws SQLException {
    int rows =
        sqlMapClient.delete("KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_deleteByExample",
            example);
    return rows;
  }

  public int deleteByPrimaryKey(Long statementId) throws SQLException {
    OrderOperateStatement key = new OrderOperateStatement();
    key.setStatementId(statementId);
    int rows =
        sqlMapClient.delete("KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_deleteByPrimaryKey",
            key);
    return rows;
  }

  public Long insert(OrderOperateStatement record) throws SQLException {
    Object newKey =
        sqlMapClient.insert("KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_insert", record);
    return (Long) newKey;
  }

  public Long insertSelective(OrderOperateStatement record) throws SQLException {
    Object newKey =
        sqlMapClient.insert("KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_insertSelective",
            record);
    return (Long) newKey;
  }

  public List selectByExample(OrderOperateStatementExample example) throws SQLException {
    List list =
        sqlMapClient.queryForList(
            "KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_selectByExample", example);
    return list;
  }

  public OrderOperateStatement selectByPrimaryKey(Long statementId) throws SQLException {
    OrderOperateStatement key = new OrderOperateStatement();
    key.setStatementId(statementId);
    OrderOperateStatement record =
        (OrderOperateStatement) sqlMapClient.queryForObject(
            "KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  public int updateByExampleSelective(OrderOperateStatement record,
      OrderOperateStatementExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update(
            "KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  public int updateByExample(OrderOperateStatement record, OrderOperateStatementExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_updateByExample",
            parms);
    return rows;
  }

  public int updateByPrimaryKeySelective(OrderOperateStatement record) throws SQLException {
    int rows =
        sqlMapClient.update(
            "KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  public int updateByPrimaryKey(OrderOperateStatement record) throws SQLException {
    int rows =
        sqlMapClient.update("KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_updateByPrimaryKey",
            record);
    return rows;
  }

  private static class UpdateByExampleParms extends OrderOperateStatementExample {
    private static final long serialVersionUID = 1L;
    private Object record;

    public UpdateByExampleParms(Object record, OrderOperateStatementExample example) {
      super(example);
      this.record = record;
    }

    @SuppressWarnings("unused")
    public Object getRecord() {
      return record;
    }
  }

  @Override
  public OrderOperateStatement selectLatestByPrimaryKey(String orderCode) throws SQLException {
    OrderOperateStatement key = new OrderOperateStatement();
    key.setOrderCode(orderCode);
    OrderOperateStatement record =
        (OrderOperateStatement) sqlMapClient.queryForObject(
            "KMORDER_ORDER_OPERATE_STATEMENT.selectLatestByPrimaryKey", key);
    return record;
  }

  @Override
  public OrderOperateStatement selectNewest(String orderCode) throws SQLException {
    return (OrderOperateStatement) sqlMapClient.queryForObject(
        "KMORDER_ORDER_OPERATE_STATEMENT.selectNewest", orderCode);
  }

  public void updateList(List<OrderOperateStatement> records) throws SQLException {
    super.batchUpdateData(
        "KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_updateByPrimaryKeySelective", records);
  }

  public void deleteList(List<OrderOperateStatement> records) throws SQLException {
    super.batchDeleteData("KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_deleteByPrimaryKey",
        records);
  }

  public void insertList(List<OrderOperateStatement> records) throws SQLException {
    super.batchInsertData("KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_insert", records);
  }

  @Override
  public List<OrderOperateStatement> selectByOrderCodeList(List<String> codeList)
      throws SQLException {
    List<OrderOperateStatement> relist =
        sqlMapClient.queryForList("KMORDER_ORDER_OPERATE_STATEMENT.ibatorgenerated_selectByList",
            codeList);
    return relist;
  }

  /**
   * 获取订单物流信息修改次数
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  @Override
  public Integer queryUpdateLogisticCount(String orderCode) throws SQLException {
    return (Integer) sqlMapClient.queryForObject(
        "KMORDER_ORDER_OPERATE_STATEMENT.SQL_QUERY_UPDATE_COUNT_FOR_LOGISTIC", orderCode);
  }

  /**
   * 查询待确认收货的订单编号
   * 
   * @return
   * @throws SQLException
   */
  public List<String> queryUnconfirmReceiptOrderCode() throws SQLException {
    return sqlMapClient
        .queryForList("KMORDER_ORDER_OPERATE_STATEMENT.SQL_QUERY_UN_CONFIRM_RECEIPT_ORDER_CODE");
  }

  @Override
  public String getOperatorNameById(String commerceId) throws SQLException {
    return (String) sqlMapClient.queryForObject(
        "KMORDER_ORDER_OPERATE_STATEMENT.getOperatorNameById", commerceId);
  }

  @Override
  public OrderOperateStatement selectNewestWithStatus(String orderCode, long orderStatus)
      throws SQLException {
    Map<String, String> paramMap = new HashMap<String, String>(2);
    paramMap.put("orderCode", orderCode);
    paramMap.put("orderStatus", String.valueOf(orderStatus));
    return (OrderOperateStatement) sqlMapClient.queryForObject(
        "KMORDER_ORDER_OPERATE_STATEMENT.selectNewestWithStatus", paramMap);
  }
}
