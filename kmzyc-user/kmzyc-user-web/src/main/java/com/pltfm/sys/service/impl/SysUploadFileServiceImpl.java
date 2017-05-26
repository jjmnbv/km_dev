package com.pltfm.sys.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.sys.dao.SysUploadFileDAO;
import com.pltfm.sys.model.SysUploadFile;
import com.pltfm.sys.service.SysUploadFileService;

public class SysUploadFileServiceImpl implements SysUploadFileService {

  Logger log = LoggerFactory.getLogger(this.getClass());

  @Resource(name = "sqlMapClient")
  /**
   * 数据库操作对象
   */
  private SqlMapClient sqlMapClient;
  @Resource(name = "sysUploadFileDAO")
  private SysUploadFileDAO sysUploadFileDAO;

  /**
   * 新增上传后的附件信息
   * 
   * @param SysUploadFile
   * @return Integer
   * @exception Exception
   */
  @Override
  public Integer addUploadFileInfo(SysUploadFile upfile) throws Exception {
    try {
      sysUploadFileDAO.insert(upfile);
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
    }
    return upfile.getFileId();
  }

  /**
   * 得到详细信息
   * 
   * @param Integer
   * @return SysUploadFile
   * @exception Exception
   */
  @Override
  public SysUploadFile getDetail(Integer fileId) throws Exception {
    SysUploadFile sysUploadFile = new SysUploadFile();
    try {
      sysUploadFile = sysUploadFileDAO.selectByPrimaryKey(fileId);
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
    }
    return sysUploadFile;
  }

  /**
   * 根据Id字符串得到SysUploadFile列表
   * 
   * @param String
   * @return List
   * @exception Exception
   */
  @Override
  public List getFileListByIdStr(String fileId) throws Exception {
    List dataList = new ArrayList();
    try {
      if (fileId != null && !fileId.equals("")) {
        dataList = sysUploadFileDAO.getFileListByIdStr(fileId);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
    }
    return dataList;
  }

  /**
   * 根据Id字符串得到附件地址列表
   * 
   * @param String
   * @return List
   * @exception Exception
   */
  @Override
  public List getFileUrlListByIdStr(String fileId) throws Exception {
    List resultList = new ArrayList();
    List dataList = this.getFileListByIdStr(fileId);
    if (dataList != null && dataList.size() > 0) {
      for (int i = 0; i < dataList.size(); i++) {
        SysUploadFile suf = (SysUploadFile) dataList.get(i);
        resultList.add(suf.getFileUrl());
      }
    }
    return resultList;
  }

}
