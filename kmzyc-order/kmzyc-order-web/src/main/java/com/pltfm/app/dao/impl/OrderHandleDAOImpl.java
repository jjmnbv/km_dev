package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.OrderHandleDAO;
import com.pltfm.app.entities.OrderHandle;
import com.pltfm.app.entities.OrderHandleExample;

@SuppressWarnings("unchecked")
@Repository("orderHandleDAO")
public class OrderHandleDAOImpl extends BaseDAO implements OrderHandleDAO {
  // /**
  // * This field was generated by Apache iBATIS ibator.
  // * This field corresponds to the database table KMORDER.ORDER_HANDLE
  // *
  // * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
  // */
  // private SqlMapClient sqlMapClient;
  //
  // /**
  // * This method was generated by Apache iBATIS ibator.
  // * This method corresponds to the database table KMORDER.ORDER_HANDLE
  // *
  // * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
  // */
  // public OrderHandleDAOImpl(SqlMapClient sqlMapClient) {
  // super();
  // this.sqlMapClient = sqlMapClient;
  // }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public int countByExample(OrderHandleExample example) throws SQLException {
    Integer count =
        (Integer) sqlMapClient.queryForObject(
            "KMORDER_ORDER_HANDLE.ibatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public int deleteByExample(OrderHandleExample example) throws SQLException {
    int rows = sqlMapClient.delete("KMORDER_ORDER_HANDLE.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public int deleteByPrimaryKey(Long handleNo) throws SQLException {
    OrderHandle key = new OrderHandle();
    key.setHandleNo(handleNo);
    int rows = sqlMapClient.delete("KMORDER_ORDER_HANDLE.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public Long insert(OrderHandle record) throws SQLException {
    Object newKey = sqlMapClient.insert("KMORDER_ORDER_HANDLE.ibatorgenerated_insert", record);
    return (Long) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public Long insertSelective(OrderHandle record) throws SQLException {
    Object newKey =
        sqlMapClient.insert("KMORDER_ORDER_HANDLE.ibatorgenerated_insertSelective", record);
    return (Long) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public List selectByExample(OrderHandleExample example) throws SQLException {
    List list =
        sqlMapClient.queryForList("KMORDER_ORDER_HANDLE.ibatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public OrderHandle selectByPrimaryKey(Long handleNo) throws SQLException {
    OrderHandle key = new OrderHandle();
    key.setHandleNo(handleNo);
    OrderHandle record =
        (OrderHandle) sqlMapClient.queryForObject(
            "KMORDER_ORDER_HANDLE.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public int updateByExampleSelective(OrderHandle record, OrderHandleExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("KMORDER_ORDER_HANDLE.ibatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public int updateByExample(OrderHandle record, OrderHandleExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("KMORDER_ORDER_HANDLE.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public int updateByPrimaryKeySelective(OrderHandle record) throws SQLException {
    int rows =
        sqlMapClient.update("KMORDER_ORDER_HANDLE.ibatorgenerated_updateByPrimaryKeySelective",
            record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public int updateByPrimaryKey(OrderHandle record) throws SQLException {
    int rows =
        sqlMapClient.update("KMORDER_ORDER_HANDLE.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  private static class UpdateByExampleParms extends OrderHandleExample {
    private static final long serialVersionUID = 1L;
    private Object record;

    public UpdateByExampleParms(Object record, OrderHandleExample example) {
      super(example);
      this.record = record;
    }

    @SuppressWarnings("unused")
    public Object getRecord() {
      return record;
    }
  }
}