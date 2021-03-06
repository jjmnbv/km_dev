package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.OrderAlterPhotoDAO;
import com.pltfm.app.entities.OrderAlterPhoto;
import com.pltfm.app.entities.OrderAlterPhotoExample;

@SuppressWarnings("unchecked")
@Repository("orderAlterPhotoDAO")
@Scope("singleton")
public class OrderAlterPhotoDAOImpl extends BaseDAO implements OrderAlterPhotoDAO {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  // private SqlMapClient sqlMapClient;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  // public FareTypeDAOImpl(SqlMapClient sqlMapClient) {
  // super();
  // this.sqlMapClient = sqlMapClient;
  // }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  public int countByExample(OrderAlterPhotoExample example) throws SQLException {
    Integer count =
        (Integer) sqlMapClient.queryForObject(
            "KMORDER_ORDER_ALTER_PHOTO.ibatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  public int deleteByExample(OrderAlterPhotoExample example) throws SQLException {
    int rows =
        sqlMapClient.delete("KMORDER_ORDER_ALTER_PHOTO.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  public int deleteByPrimaryKey(Long photoId) throws SQLException {
    OrderAlterPhoto key = new OrderAlterPhoto();
    key.setPhotoId(photoId);
    int rows =
        sqlMapClient.delete("KMORDER_ORDER_ALTER_PHOTO.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  public Long insert(OrderAlterPhoto record) throws SQLException {
    Object newKey = sqlMapClient.insert("KMORDER_ORDER_ALTER_PHOTO.ibatorgenerated_insert", record);
    return (Long) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  public Long insertSelective(OrderAlterPhoto record) throws SQLException {
    Object newKey =
        sqlMapClient.insert("KMORDER_ORDER_ALTER_PHOTO.ibatorgenerated_insertSelective", record);
    return (Long) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  public List selectByExample(OrderAlterPhotoExample example) throws SQLException {
    List list =
        sqlMapClient.queryForList("KMORDER_ORDER_ALTER_PHOTO.ibatorgenerated_selectByExample",
            example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  public OrderAlterPhoto selectByPrimaryKey(Long photoId) throws SQLException {
    OrderAlterPhoto key = new OrderAlterPhoto();
    key.setPhotoId(photoId);
    OrderAlterPhoto record =
        (OrderAlterPhoto) sqlMapClient.queryForObject(
            "KMORDER_ORDER_ALTER_PHOTO.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  public int updateByExampleSelective(OrderAlterPhoto record, OrderAlterPhotoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("KMORDER_ORDER_ALTER_PHOTO.ibatorgenerated_updateByExampleSelective",
            parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  public int updateByExample(OrderAlterPhoto record, OrderAlterPhotoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("KMORDER_ORDER_ALTER_PHOTO.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  public int updateByPrimaryKeySelective(OrderAlterPhoto record) throws SQLException {
    int rows =
        sqlMapClient.update(
            "KMORDER_ORDER_ALTER_PHOTO.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  public int updateByPrimaryKey(OrderAlterPhoto record) throws SQLException {
    int rows =
        sqlMapClient.update("KMORDER_ORDER_ALTER_PHOTO.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  private static class UpdateByExampleParms extends OrderAlterPhotoExample {
    private static final long serialVersionUID = 1L;
    private Object record;

    public UpdateByExampleParms(Object record, OrderAlterPhotoExample example) {
      super(example);
      this.record = record;
    }

    @SuppressWarnings("unused")
    public Object getRecord() {
      return record;
    }
  }
}
