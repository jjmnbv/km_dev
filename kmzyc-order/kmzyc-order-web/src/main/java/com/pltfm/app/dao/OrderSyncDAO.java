package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.entities.OrderSync;

@SuppressWarnings("unchecked")
public interface OrderSyncDAO {
  /**
   * 新增订单同步
   * 
   * @param record
   */
  public void insert(OrderSync record) throws SQLException;

  /**
   * 获取订单同步计数
   * 
   * @param paramMap
   * @return
   * @throws SQLException
   */
  public int countByMap(Map<String, Object> paramMap) throws SQLException;

  /**
   * 获取订单同步列表
   * 
   * @param paramMap
   * @return
   * @throws SQLException
   */
  public List listByMap(Map<String, Object> paramMap) throws SQLException;

  /**
   * 根性订单同步数据
   * 
   * @param o
   * @return
   * @throws SQLException
   */
  public int updateOrderSync(OrderSync o) throws SQLException;

  /**
   * 更新订单同步数据
   * 
   * @param o
   * @return
   * @throws SQLException
   */
  public boolean updateOrderSync(List<OrderSync> OrderSyncs) throws SQLException;

  /**
   * 查询可同步订单
   * 
   * @return
   * @throws SQLException
   */
  public List<String> querySyncOrderCode(Integer flag) throws SQLException;

  /**
   * 查询可同步订单定时任务，确认收货时间和当前时间间隔大于15天,同步状态为未同步的订单同步
   * 
   * @return
   * @throws SQLException
   */
  public List<String> querySyncOrderCodeForJob() throws SQLException;

  /**
   * 新增订单同步
   * 
   * @param record
   */
  public void insertForAuto(OrderSync record) throws SQLException;
}
