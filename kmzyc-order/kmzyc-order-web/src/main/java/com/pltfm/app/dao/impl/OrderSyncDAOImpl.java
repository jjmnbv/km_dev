package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.OrderSyncDAO;
import com.pltfm.app.entities.OrderSync;

@SuppressWarnings("unchecked")
@Repository("orderSyncDAO")
public class OrderSyncDAOImpl extends BaseDAO implements OrderSyncDAO {

  public OrderSyncDAOImpl() {
    super();
  }

  /**
   * 新增订单同步
   * 
   * @param record
   */
  @Override
  public void insert(OrderSync record) throws SQLException {
    getSqlMapClientTemplate().insert("KMORDER_ORDER_SYNC.SQL_INSERT_ORDER_SYNC", record);
  }

  /**
   * 获取订单同步计数
   * 
   * @param paramMap
   * @return
   * @throws SQLException
   */
  @Override
  public int countByMap(Map<String, Object> paramMap) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("KMORDER_ORDER_SYNC.SQL_COUNT_ORDER_SYNC",
        paramMap);
  }

  /**
   * 获取订单同步列表
   * 
   * @param paramMap
   * @return
   * @throws SQLException
   */
  @Override
  public List listByMap(Map<String, Object> paramMap) throws SQLException {
    return (List) sqlMapClient.queryForList("KMORDER_ORDER_SYNC.SQL_QUERY_ORDER_SYNC", paramMap);
  }

  /**
   * 更新订单同步数据
   * 
   * @param o
   * @return
   * @throws SQLException
   */
  @Override
  public int updateOrderSync(OrderSync o) throws SQLException {
    return sqlMapClient.update("KMORDER_ORDER_SYNC.SQL_UPDATE_ORDER_SYNC", o);
  }

  /**
   * 更新订单同步数据
   * 
   * @param o
   * @return
   * @throws SQLException
   */
  @Override
  public boolean updateOrderSync(List<OrderSync> OrderSyncs) throws SQLException {
    sqlMapClient.startBatch();
    for (OrderSync os : OrderSyncs) {
      sqlMapClient.update("KMORDER_ORDER_SYNC.SQL_UPDATE_ORDER_SYNC", os);
    }
    sqlMapClient.executeBatch();
    return true;
  }

  /**
   * 查询可同步订单
   * 
   * @return
   * @throws SQLException
   */
  @Override
  public List<String> querySyncOrderCode(Integer flag) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_ORDER_SYNC.SQL_QUERY_SYNC_ORDER_CODE", flag);
  }

  /**
   * 查询可同步订单定时任务，确认收货时间和当前时间间隔大于15天,同步状态为未同步的订单同步
   * 
   * @return
   * @throws SQLException
   */
  @Override
  public List<String> querySyncOrderCodeForJob() throws SQLException {
    return sqlMapClient.queryForList("KMORDER_ORDER_SYNC.SQL_QUERY_SYNC_ORDER_CODE_FOR_JOB");
  }

  /**
   * 新增订单同步
   * 
   * @param record
   */
  @Override
  public void insertForAuto(OrderSync os) throws SQLException {
    sqlMapClient.insert("KMORDER_ORDER_SYNC.SQL_INSERT_ORDER_SYNC_FOR_AUTO", os);
  }
}
