package com.kmzyc.user.remote.service;

import java.sql.SQLException;

import com.pltfm.app.vobject.QualificaitonsFileDO;

/**
 * 资格文件相关信息远程接口
 * 
 * @author cjm
 * @since 2013-8-16
 */
@SuppressWarnings("unused")
public interface QualificaitonsFileRemoteService {

  public Integer insertQualificaitonsFileDO(QualificaitonsFileDO qualificaitonsFileDO)
      throws SQLException;


  public Integer updateQualificaitonsFileDO(QualificaitonsFileDO qualificaitonsFileDO)
      throws SQLException;


}
