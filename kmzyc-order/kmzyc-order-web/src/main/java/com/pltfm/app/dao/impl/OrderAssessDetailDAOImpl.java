package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.OrderAssessDetailDAO;
import com.pltfm.app.entities.OrderAssessDetail;
import com.pltfm.app.entities.OrderAssessDetailExample;

@SuppressWarnings("unchecked")
@Repository("orderAssessDetailDAO")
public class OrderAssessDetailDAOImpl extends BaseDAO implements OrderAssessDetailDAO {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * KMORDER.ORDER_ASSESS_DETAIL
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  // private SqlMapClient sqlMapClient;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_DETAIL
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  // public OrderAssessDetailDAOImpl(SqlMapClient sqlMapClient) {
  // super();
  // this.sqlMapClient = sqlMapClient;
  // }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_DETAIL
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  public int countByExample(OrderAssessDetailExample example) throws SQLException {
    Integer count =
        (Integer) sqlMapClient.queryForObject(
            "KMORDER_ORDER_ASSESS_DETAIL.ibatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_DETAIL
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  public int deleteByExample(OrderAssessDetailExample example) throws SQLException {
    int rows =
        sqlMapClient.delete("KMORDER_ORDER_ASSESS_DETAIL.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_DETAIL
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  public int deleteByPrimaryKey(Long assessDetailId) throws SQLException {
    OrderAssessDetail key = new OrderAssessDetail();
    key.setAssessDetailId(assessDetailId);
    int rows =
        sqlMapClient.delete("KMORDER_ORDER_ASSESS_DETAIL.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_DETAIL
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  public Long insert(OrderAssessDetail record) throws SQLException {
    Object newKey =
        sqlMapClient.insert("KMORDER_ORDER_ASSESS_DETAIL.ibatorgenerated_insert", record);
    return (Long) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_DETAIL
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  public Long insertSelective(OrderAssessDetail record) throws SQLException {
    Object newKey =
        sqlMapClient.insert("KMORDER_ORDER_ASSESS_DETAIL.ibatorgenerated_insertSelective", record);
    return (Long) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_DETAIL
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  public List selectByExample(OrderAssessDetailExample example) throws SQLException {
    List list =
        sqlMapClient.queryForList("KMORDER_ORDER_ASSESS_DETAIL.ibatorgenerated_selectByExample",
            example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_DETAIL
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  public OrderAssessDetail selectByPrimaryKey(Long assessDetailId) throws SQLException {
    OrderAssessDetail key = new OrderAssessDetail();
    key.setAssessDetailId(assessDetailId);
    OrderAssessDetail record =
        (OrderAssessDetail) sqlMapClient.queryForObject(
            "KMORDER_ORDER_ASSESS_DETAIL.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_DETAIL
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  public int updateByExampleSelective(OrderAssessDetail record, OrderAssessDetailExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("KMORDER_ORDER_ASSESS_DETAIL.ibatorgenerated_updateByExampleSelective",
            parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_DETAIL
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  public int updateByExample(OrderAssessDetail record, OrderAssessDetailExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("KMORDER_ORDER_ASSESS_DETAIL.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_DETAIL
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  public int updateByPrimaryKeySelective(OrderAssessDetail record) throws SQLException {
    int rows =
        sqlMapClient.update(
            "KMORDER_ORDER_ASSESS_DETAIL.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_DETAIL
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  public int updateByPrimaryKey(OrderAssessDetail record) throws SQLException {
    int rows =
        sqlMapClient.update("KMORDER_ORDER_ASSESS_DETAIL.ibatorgenerated_updateByPrimaryKey",
            record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * KMORDER.ORDER_ASSESS_DETAIL
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  private static class UpdateByExampleParms extends OrderAssessDetailExample {
    private static final long serialVersionUID = 1L;
    private Object record;

    public UpdateByExampleParms(Object record, OrderAssessDetailExample example) {
      super(example);
      this.record = record;
    }

    @SuppressWarnings("unused")
    public Object getRecord() {
      return record;
    }
  }

  public void insertList(List<OrderAssessDetail> records) throws SQLException {
    super.batchInsertData("KMORDER_ORDER_ASSESS_DETAIL.ibatorgenerated_insert", records);
  }

  @Override
  public List<OrderAssessDetail> selectListByAssessInfoID(Map map) throws SQLException {
    List list = sqlMapClient.queryForList("KMORDER_ORDER_ASSESS_DETAIL.selectByassessInfoID", map);
    return list;
  }

  @Override
  public void deleteByOrderCode(String orderCode) throws SQLException {
    OrderAssessDetail oad = new OrderAssessDetail();
    oad.setOrderCode(orderCode);
    sqlMapClient.delete("KMORDER_ORDER_ASSESS_DETAIL.ibatorgenerated_deleteByOrderCode", oad);
  }

  @Override
  public int bathinsertOrderAssess(List<OrderAssessDetail> oaList) throws SQLException {
    try {
      if (!CollectionUtils.isEmpty(oaList)) {
        sqlMapClient.startBatch();
        for (int i = 0; i < oaList.size(); i++) {
          sqlMapClient.insert("KMORDER_ORDER_ASSESS_DETAIL.ibatorgenerated_insert", oaList.get(i));
        }
        sqlMapClient.executeBatch();
        return 1;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
    return 0;

  }

}
