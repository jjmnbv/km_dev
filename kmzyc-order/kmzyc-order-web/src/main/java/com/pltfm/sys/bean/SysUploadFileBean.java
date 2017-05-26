package com.pltfm.sys.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.pltfm.sys.dao.SysUploadFileDAOImpl;
import com.pltfm.sys.model.SysUploadFile;

/**
 * 类 SysUploadFileBean 附件类
 * 
 * @author
 * @version 2.1
 * @since JDK1.5
 */
@SuppressWarnings("unchecked")
public class SysUploadFileBean extends BaseBean {

  Logger log = Logger.getLogger(this.getClass());

  public SysUploadFileBean() {
    // 加载总的sqlmap配置文件
    super();
  }

  /**
   * 新增上传后的附件信息
   * 
   * @param SysUploadFile
   * @return Integer
   * @exception Exception
   */
  public Integer addUploadFileInfo(SysUploadFile upfile) throws Exception {
    try {
      SysUploadFileDAOImpl dao = new SysUploadFileDAOImpl(sqlMap);
      dao.insert(upfile);
    } catch (SQLException e) {
      log.error(e);
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
  public SysUploadFile getDetail(Integer fileId) throws Exception {
    SysUploadFile sysUploadFile = new SysUploadFile();
    try {
      SysUploadFileDAOImpl dao = new SysUploadFileDAOImpl(sqlMap);
      sysUploadFile = dao.selectByPrimaryKey(fileId);
    } catch (SQLException e) {
      log.error(e);
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
  public List getFileListByIdStr(String fileId) throws Exception {
    List dataList = new ArrayList();
    try {
      SysUploadFileDAOImpl dao = new SysUploadFileDAOImpl(sqlMap);
      if (fileId != null && !fileId.equals("")) {
        dataList = dao.getFileListByIdStr(fileId);
      }
    } catch (SQLException e) {
      log.error(e);
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
