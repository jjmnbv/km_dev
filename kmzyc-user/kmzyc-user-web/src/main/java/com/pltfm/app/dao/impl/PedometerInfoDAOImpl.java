package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.PedometerInfoDAO;
import com.pltfm.app.vobject.PedometerInfo;
import com.pltfm.app.vobject.PedometerInfoExample;

public class PedometerInfoDAOImpl implements PedometerInfoDAO {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * PEDOMETER_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:44:53 CST 2013
   */
  private SqlMapClient sqlMapClient;

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PEDOMETER_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:44:53 CST 2013
   */
  public int countByExample(PedometerInfoExample example) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("PEDOMETER_INFO.ibatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PEDOMETER_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:44:53 CST 2013
   */
  public int deleteByExample(PedometerInfoExample example) throws SQLException {
    int rows = sqlMapClient.delete("PEDOMETER_INFO.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PEDOMETER_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:44:53 CST 2013
   */
  public int deleteByPrimaryKey(Integer nPedometerId) throws SQLException {
    PedometerInfo key = new PedometerInfo();
    key.setnPedometerId(nPedometerId);
    int rows = sqlMapClient.delete("PEDOMETER_INFO.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PEDOMETER_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:44:53 CST 2013
   */
  public void insert(PedometerInfo record) throws SQLException {
    sqlMapClient.insert("PEDOMETER_INFO.ibatorgenerated_insert", record);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PEDOMETER_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:44:53 CST 2013
   */
  public void insertSelective(PedometerInfo record) throws SQLException {
    sqlMapClient.insert("PEDOMETER_INFO.ibatorgenerated_insertSelective", record);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PEDOMETER_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:44:53 CST 2013
   */
  public List selectByExample(PedometerInfoExample example) throws SQLException {
    List list =
        sqlMapClient.queryForList("PEDOMETER_INFO.ibatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PEDOMETER_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:44:53 CST 2013
   */
  public PedometerInfo selectByPrimaryKey(Integer nPedometerId) throws SQLException {
    PedometerInfo key = new PedometerInfo();
    key.setnPedometerId(nPedometerId);
    PedometerInfo record = (PedometerInfo) sqlMapClient
        .queryForObject("PEDOMETER_INFO.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PEDOMETER_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:44:53 CST 2013
   */
  public int updateByExampleSelective(PedometerInfo record, PedometerInfoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("PEDOMETER_INFO.ibatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PEDOMETER_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:44:53 CST 2013
   */
  public int updateByExample(PedometerInfo record, PedometerInfoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("PEDOMETER_INFO.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PEDOMETER_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:44:53 CST 2013
   */
  public int updateByPrimaryKeySelective(PedometerInfo record) throws SQLException {
    int rows =
        sqlMapClient.update("PEDOMETER_INFO.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PEDOMETER_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:44:53 CST 2013
   */
  public int updateByPrimaryKey(PedometerInfo record) throws SQLException {
    int rows = sqlMapClient.update("PEDOMETER_INFO.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * PEDOMETER_INFO
   *
   * @ibatorgenerated Thu Jul 11 16:44:53 CST 2013
   */
  private static class UpdateByExampleParms extends PedometerInfoExample {
    private Object record;

    public UpdateByExampleParms(Object record, PedometerInfoExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }
}
