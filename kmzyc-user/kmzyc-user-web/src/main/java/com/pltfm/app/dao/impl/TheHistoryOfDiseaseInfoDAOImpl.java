package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.TheHistoryOfDiseaseInfoDAO;
import com.pltfm.app.vobject.TheHistoryOfDiseaseInfo;
import com.pltfm.app.vobject.TheHistoryOfDiseaseInfoExample;

public class TheHistoryOfDiseaseInfoDAOImpl implements TheHistoryOfDiseaseInfoDAO {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * THE_HISTORY_OF_DISEASE_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
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
   * table THE_HISTORY_OF_DISEASE_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public int countByExample(TheHistoryOfDiseaseInfoExample example) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("THE_HISTORY_OF_DISEASE_INFO.ibatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table THE_HISTORY_OF_DISEASE_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public int deleteByExample(TheHistoryOfDiseaseInfoExample example) throws SQLException {
    int rows =
        sqlMapClient.delete("THE_HISTORY_OF_DISEASE_INFO.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table THE_HISTORY_OF_DISEASE_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public int deleteByPrimaryKey(Integer nTheHistoryOfDiseaseId) throws SQLException {
    TheHistoryOfDiseaseInfo key = new TheHistoryOfDiseaseInfo();
    key.setnTheHistoryOfDiseaseId(nTheHistoryOfDiseaseId);
    int rows =
        sqlMapClient.delete("THE_HISTORY_OF_DISEASE_INFO.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table THE_HISTORY_OF_DISEASE_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public void insert(TheHistoryOfDiseaseInfo record) throws SQLException {
    sqlMapClient.insert("THE_HISTORY_OF_DISEASE_INFO.ibatorgenerated_insert", record);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table THE_HISTORY_OF_DISEASE_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public void insertSelective(TheHistoryOfDiseaseInfo record) throws SQLException {
    sqlMapClient.insert("THE_HISTORY_OF_DISEASE_INFO.ibatorgenerated_insertSelective", record);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table THE_HISTORY_OF_DISEASE_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public List selectByExample(TheHistoryOfDiseaseInfoExample example) throws SQLException {
    List list = sqlMapClient
        .queryForList("THE_HISTORY_OF_DISEASE_INFO.ibatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table THE_HISTORY_OF_DISEASE_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public TheHistoryOfDiseaseInfo selectByPrimaryKey(Integer nTheHistoryOfDiseaseId)
      throws SQLException {
    TheHistoryOfDiseaseInfo key = new TheHistoryOfDiseaseInfo();
    key.setnTheHistoryOfDiseaseId(nTheHistoryOfDiseaseId);
    TheHistoryOfDiseaseInfo record = (TheHistoryOfDiseaseInfo) sqlMapClient
        .queryForObject("THE_HISTORY_OF_DISEASE_INFO.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table THE_HISTORY_OF_DISEASE_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public int updateByExampleSelective(TheHistoryOfDiseaseInfo record,
      TheHistoryOfDiseaseInfoExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient
        .update("THE_HISTORY_OF_DISEASE_INFO.ibatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table THE_HISTORY_OF_DISEASE_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public int updateByExample(TheHistoryOfDiseaseInfo record, TheHistoryOfDiseaseInfoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("THE_HISTORY_OF_DISEASE_INFO.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table THE_HISTORY_OF_DISEASE_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public int updateByPrimaryKeySelective(TheHistoryOfDiseaseInfo record) throws SQLException {
    int rows = sqlMapClient
        .update("THE_HISTORY_OF_DISEASE_INFO.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table THE_HISTORY_OF_DISEASE_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public int updateByPrimaryKey(TheHistoryOfDiseaseInfo record) throws SQLException {
    int rows = sqlMapClient.update("THE_HISTORY_OF_DISEASE_INFO.ibatorgenerated_updateByPrimaryKey",
        record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * THE_HISTORY_OF_DISEASE_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  private static class UpdateByExampleParms extends TheHistoryOfDiseaseInfoExample {
    private Object record;

    public UpdateByExampleParms(Object record, TheHistoryOfDiseaseInfoExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }
}
