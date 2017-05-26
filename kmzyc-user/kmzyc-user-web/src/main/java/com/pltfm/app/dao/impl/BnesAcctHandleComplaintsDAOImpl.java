package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BnesAcctHandleComplaintsDAO;
import com.pltfm.app.vobject.BnesAcctHandleComplaints;
import com.pltfm.app.vobject.BnesAcctHandleComplaintsExample;

@Component(value = "bnesAcctHandleComplaintsDAO")
public class BnesAcctHandleComplaintsDAOImpl implements BnesAcctHandleComplaintsDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;



  // 处理申诉
  public void insert(BnesAcctHandleComplaints record) throws SQLException {
    sqlMapClient.insert("BNES_ACCT_HANDLE_COMPLAINTS.abatorgenerated_insert", record);
  }


  public int updateByPrimaryKey(BnesAcctHandleComplaints record) throws SQLException {
    int rows = sqlMapClient.update("BNES_ACCT_HANDLE_COMPLAINTS.abatorgenerated_updateByPrimaryKey",
        record);
    return rows;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_ACCT_HANDLE_COMPLAINTS
   *
   * @abatorgenerated Wed Aug 07 16:31:30 CST 2013
   */
  public int updateByPrimaryKeySelective(BnesAcctHandleComplaints record) throws SQLException {
    int rows = sqlMapClient
        .update("BNES_ACCT_HANDLE_COMPLAINTS.abatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_ACCT_HANDLE_COMPLAINTS
   *
   * @abatorgenerated Wed Aug 07 16:31:30 CST 2013
   */
  public List selectByExample(BnesAcctHandleComplaintsExample example) throws SQLException {
    List list = sqlMapClient
        .queryForList("BNES_ACCT_HANDLE_COMPLAINTS.abatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_ACCT_HANDLE_COMPLAINTS
   *
   * @abatorgenerated Wed Aug 07 16:31:30 CST 2013
   */
  public BnesAcctHandleComplaints selectByPrimaryKey(Integer handleComplaintsId)
      throws SQLException {
    BnesAcctHandleComplaints key = new BnesAcctHandleComplaints();
    key.setHandleComplaintsId(handleComplaintsId);
    BnesAcctHandleComplaints record = (BnesAcctHandleComplaints) sqlMapClient
        .queryForObject("BNES_ACCT_HANDLE_COMPLAINTS.abatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_ACCT_HANDLE_COMPLAINTS
   *
   * @abatorgenerated Wed Aug 07 16:31:30 CST 2013
   */
  public int deleteByExample(BnesAcctHandleComplaintsExample example) throws SQLException {
    int rows =
        sqlMapClient.delete("BNES_ACCT_HANDLE_COMPLAINTS.abatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_ACCT_HANDLE_COMPLAINTS
   *
   * @abatorgenerated Wed Aug 07 16:31:30 CST 2013
   */
  public int deleteByPrimaryKey(Integer handleComplaintsId) throws SQLException {
    BnesAcctHandleComplaints key = new BnesAcctHandleComplaints();
    key.setHandleComplaintsId(handleComplaintsId);
    int rows =
        sqlMapClient.delete("BNES_ACCT_HANDLE_COMPLAINTS.abatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_ACCT_HANDLE_COMPLAINTS
   *
   * @abatorgenerated Wed Aug 07 16:31:30 CST 2013
   */
  public int countByExample(BnesAcctHandleComplaintsExample example) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("BNES_ACCT_HANDLE_COMPLAINTS.abatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_ACCT_HANDLE_COMPLAINTS
   *
   * @abatorgenerated Wed Aug 07 16:31:30 CST 2013
   */
  public int updateByExampleSelective(BnesAcctHandleComplaints record,
      BnesAcctHandleComplaintsExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient
        .update("BNES_ACCT_HANDLE_COMPLAINTS.abatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * This method was generated by Abator for iBATIS. This method corresponds to the database table
   * BNES_ACCT_HANDLE_COMPLAINTS
   *
   * @abatorgenerated Wed Aug 07 16:31:30 CST 2013
   */
  public int updateByExample(BnesAcctHandleComplaints record,
      BnesAcctHandleComplaintsExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("BNES_ACCT_HANDLE_COMPLAINTS.abatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * BNES_ACCT_HANDLE_COMPLAINTS
   *
   * @abatorgenerated Wed Aug 07 16:31:30 CST 2013
   */
  private static class UpdateByExampleParms extends BnesAcctHandleComplaintsExample {
    private Object record;

    public UpdateByExampleParms(Object record, BnesAcctHandleComplaintsExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }


}