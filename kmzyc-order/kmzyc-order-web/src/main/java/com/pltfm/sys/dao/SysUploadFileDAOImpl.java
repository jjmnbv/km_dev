package com.pltfm.sys.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.sys.model.SysUploadFile;
import com.pltfm.sys.model.SysUploadFileExample;

@SuppressWarnings("unchecked")
public class SysUploadFileDAOImpl implements SysUploadFileDAO {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * sys_upload_file
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  private SqlMapClient sqlMapClient;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public SysUploadFileDAOImpl(SqlMapClient sqlMapClient) {
    super();
    this.sqlMapClient = sqlMapClient;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public int countByExample(SysUploadFileExample example) throws SQLException {
    Integer count =
        (Integer) sqlMapClient.queryForObject("sys_upload_file.ibatorgenerated_countByExample",
            example);
    return count;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public int deleteByExample(SysUploadFileExample example) throws SQLException {
    int rows = sqlMapClient.delete("sys_upload_file.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public int deleteByPrimaryKey(Integer fileId) throws SQLException {
    SysUploadFile key = new SysUploadFile();
    key.setFileId(fileId);
    int rows = sqlMapClient.delete("sys_upload_file.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public Integer insert(SysUploadFile record) throws SQLException {
    Object newKey = sqlMapClient.insert("sys_upload_file.ibatorgenerated_insert", record);
    return (Integer) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public Integer insertSelective(SysUploadFile record) throws SQLException {
    Object newKey = sqlMapClient.insert("sys_upload_file.ibatorgenerated_insertSelective", record);
    return (Integer) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public List<SysUploadFile> selectByExample(SysUploadFileExample example) throws SQLException {
    List<SysUploadFile> list =
        sqlMapClient.queryForList("sys_upload_file.ibatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public SysUploadFile selectByPrimaryKey(Integer fileId) throws SQLException {
    SysUploadFile key = new SysUploadFile();
    key.setFileId(fileId);
    SysUploadFile record =
        (SysUploadFile) sqlMapClient.queryForObject(
            "sys_upload_file.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public int updateByExampleSelective(SysUploadFile record, SysUploadFileExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("sys_upload_file.ibatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public int updateByExample(SysUploadFile record, SysUploadFileExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("sys_upload_file.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public int updateByPrimaryKeySelective(SysUploadFile record) throws SQLException {
    int rows =
        sqlMapClient.update("sys_upload_file.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  public int updateByPrimaryKey(SysUploadFile record) throws SQLException {
    int rows = sqlMapClient.update("sys_upload_file.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * sys_upload_file
   * 
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  private static class UpdateByExampleParms extends SysUploadFileExample {
    private Object record;

    public UpdateByExampleParms(Object record, SysUploadFileExample example) {
      super(example);
      this.record = record;
    }

    @SuppressWarnings("unused")
    public Object getRecord() {
      return record;
    }
  }

  /**
   * 根据Id字符串得到SysUploadFile列表
   * 
   * @param String
   * @return List
   */
  public List getFileListByIdStr(String idString) throws SQLException {
    if (idString == null || idString.equals("")) {
      idString = "0";
    }
    SysUploadFile suf = new SysUploadFile();
    suf.setIdString(idString);
    List list = sqlMapClient.queryForList("sys_upload_file.getFileListByIdStr", suf);
    return list;
  }
}
