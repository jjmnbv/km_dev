package com.pltfm.sys.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.sys.dao.ReportMainDAO;
import com.pltfm.sys.model.ReportMain;
import com.pltfm.sys.model.ReportMainExample;

@Component(value = "reportMainDAO")
public class ReportMainDAOImpl implements ReportMainDAO {

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * report_main
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  @Resource
  private SqlMapClient sqlMapClient;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table report_main
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */


  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table report_main
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public int countByExample(ReportMainExample example) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("report_main.ibatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table report_main
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public int deleteByExample(ReportMainExample example) throws SQLException {
    int rows = sqlMapClient.delete("report_main.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table report_main
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public int deleteByPrimaryKey(Integer reportId) throws SQLException {
    ReportMain key = new ReportMain();
    key.setReportId(reportId);
    int rows = sqlMapClient.delete("report_main.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table report_main
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public Integer insert(ReportMain record) throws SQLException {
    Object newKey = sqlMapClient.insert("report_main.ibatorgenerated_insert", record);
    return (Integer) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table report_main
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public Integer insertSelective(ReportMain record) throws SQLException {
    Object newKey = sqlMapClient.insert("report_main.ibatorgenerated_insertSelective", record);
    return (Integer) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table report_main
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public List selectByExample(ReportMainExample example) throws SQLException {
    List list = sqlMapClient.queryForList("report_main.ibatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table report_main
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public ReportMain selectByPrimaryKey(Integer reportId) throws SQLException {
    ReportMain key = new ReportMain();
    key.setReportId(reportId);
    ReportMain record = (ReportMain) sqlMapClient
        .queryForObject("report_main.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table report_main
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public int updateByExampleSelective(ReportMain record, ReportMainExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("report_main.ibatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table report_main
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public int updateByExample(ReportMain record, ReportMainExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("report_main.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table report_main
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public int updateByPrimaryKeySelective(ReportMain record) throws SQLException {
    int rows =
        sqlMapClient.update("report_main.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table report_main
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  public int updateByPrimaryKey(ReportMain record) throws SQLException {
    int rows = sqlMapClient.update("report_main.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * report_main
   * 
   * @ibatorgenerated Thu Jan 28 16:03:32 CST 2010
   */
  private static class UpdateByExampleParms extends ReportMainExample {
    private Object record;

    public UpdateByExampleParms(Object record, ReportMainExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }
}
