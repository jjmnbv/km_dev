package com.pltfm.sys.service;

import java.util.List;

import com.pltfm.sys.model.SysUploadFile;

public interface SysUploadFileService {
  /**
   * 新增上传后的附件信息
   * 
   * @param SysUploadFile
   * @return Integer
   * @exception Exception
   */
  public Integer addUploadFileInfo(SysUploadFile upfile) throws Exception;

  /**
   * 得到详细信息
   * 
   * @param Integer
   * @return SysUploadFile
   * @exception Exception
   */
  public SysUploadFile getDetail(Integer fileId) throws Exception;

  /**
   * 根据Id字符串得到SysUploadFile列表
   * 
   * @param String
   * @return List
   * @exception Exception
   */
  public List getFileListByIdStr(String fileId) throws Exception;

  /**
   * 根据Id字符串得到附件地址列表
   * 
   * @param String
   * @return List
   * @exception Exception
   */
  public List getFileUrlListByIdStr(String fileId) throws Exception;


}
