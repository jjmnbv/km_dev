package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BloodSugarInfoDAO;
import com.pltfm.app.vobject.BloodSugarInfo;
import com.pltfm.app.vobject.BloodSugarInfoExample;

public class BloodSugarInfoDAOImpl implements BloodSugarInfoDAO {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * BLOOD_SUGAR_INFO
   *
   * @ibatorgenerated Fri Jul 12 08:44:48 CST 2013
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
   * table BLOOD_SUGAR_INFO
   *
   * @ibatorgenerated Fri Jul 12 08:44:48 CST 2013
   */
  public int countByExample(BloodSugarInfoExample example) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("BLOOD_SUGAR_INFO.ibatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_SUGAR_INFO
   *
   * @ibatorgenerated Fri Jul 12 08:44:48 CST 2013
   */
  public int deleteByExample(BloodSugarInfoExample example) throws SQLException {
    int rows = sqlMapClient.delete("BLOOD_SUGAR_INFO.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_SUGAR_INFO
   *
   * @ibatorgenerated Fri Jul 12 08:44:48 CST 2013
   */
  public int deleteByPrimaryKey(Integer nBloodSugarId) throws SQLException {
    BloodSugarInfo key = new BloodSugarInfo();
    key.setnBloodSugarId(nBloodSugarId);
    int rows = sqlMapClient.delete("BLOOD_SUGAR_INFO.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_SUGAR_INFO
   *
   * @ibatorgenerated Fri Jul 12 08:44:48 CST 2013
   */
  public void insert(BloodSugarInfo record) throws SQLException {
    sqlMapClient.insert("BLOOD_SUGAR_INFO.ibatorgenerated_insert", record);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_SUGAR_INFO
   *
   * @ibatorgenerated Fri Jul 12 08:44:48 CST 2013
   */
  public void insertSelective(BloodSugarInfo record) throws SQLException {
    sqlMapClient.insert("BLOOD_SUGAR_INFO.ibatorgenerated_insertSelective", record);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_SUGAR_INFO
   *
   * @ibatorgenerated Fri Jul 12 08:44:48 CST 2013
   */
  public List selectByExample(BloodSugarInfoExample example) throws SQLException {
    List list =
        sqlMapClient.queryForList("BLOOD_SUGAR_INFO.ibatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_SUGAR_INFO
   *
   * @ibatorgenerated Fri Jul 12 08:44:48 CST 2013
   */
  public BloodSugarInfo selectByPrimaryKey(Integer nBloodSugarId) throws SQLException {
    BloodSugarInfo key = new BloodSugarInfo();
    key.setnBloodSugarId(nBloodSugarId);
    BloodSugarInfo record = (BloodSugarInfo) sqlMapClient
        .queryForObject("BLOOD_SUGAR_INFO.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_SUGAR_INFO
   *
   * @ibatorgenerated Fri Jul 12 08:44:48 CST 2013
   */
  public int updateByExampleSelective(BloodSugarInfo record, BloodSugarInfoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("BLOOD_SUGAR_INFO.ibatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_SUGAR_INFO
   *
   * @ibatorgenerated Fri Jul 12 08:44:48 CST 2013
   */
  public int updateByExample(BloodSugarInfo record, BloodSugarInfoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("BLOOD_SUGAR_INFO.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_SUGAR_INFO
   *
   * @ibatorgenerated Fri Jul 12 08:44:48 CST 2013
   */
  public int updateByPrimaryKeySelective(BloodSugarInfo record) throws SQLException {
    int rows =
        sqlMapClient.update("BLOOD_SUGAR_INFO.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table BLOOD_SUGAR_INFO
   *
   * @ibatorgenerated Fri Jul 12 08:44:48 CST 2013
   */
  public int updateByPrimaryKey(BloodSugarInfo record) throws SQLException {
    int rows = sqlMapClient.update("BLOOD_SUGAR_INFO.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * BLOOD_SUGAR_INFO
   *
   * @ibatorgenerated Fri Jul 12 08:44:48 CST 2013
   */
  private static class UpdateByExampleParms extends BloodSugarInfoExample {
    private Object record;

    public UpdateByExampleParms(Object record, BloodSugarInfoExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }
}
