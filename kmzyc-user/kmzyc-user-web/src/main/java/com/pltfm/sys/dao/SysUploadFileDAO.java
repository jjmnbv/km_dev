package com.pltfm.sys.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.sys.model.SysUploadFile;
import com.pltfm.sys.model.SysUploadFileExample;

public interface SysUploadFileDAO {
  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   *
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  int countByExample(SysUploadFileExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   *
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  int deleteByExample(SysUploadFileExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   *
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  int deleteByPrimaryKey(Integer fileId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   *
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  Integer insert(SysUploadFile record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   *
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  Integer insertSelective(SysUploadFile record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   *
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  List<SysUploadFile> selectByExample(SysUploadFileExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   *
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  SysUploadFile selectByPrimaryKey(Integer fileId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   *
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  int updateByExampleSelective(SysUploadFile record, SysUploadFileExample example)
      throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   *
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  int updateByExample(SysUploadFile record, SysUploadFileExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   *
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  int updateByPrimaryKeySelective(SysUploadFile record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_upload_file
   *
   * @ibatorgenerated Wed Dec 30 18:19:34 CST 2009
   */
  int updateByPrimaryKey(SysUploadFile record) throws SQLException;

  public List getFileListByIdStr(String idString) throws SQLException;
}
