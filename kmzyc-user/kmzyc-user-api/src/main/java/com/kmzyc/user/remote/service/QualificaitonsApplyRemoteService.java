package com.kmzyc.user.remote.service;

import java.sql.SQLException;

import com.pltfm.app.vobject.QualificationsApplyDO;

/**
 * 资格申请记录相关信息远程接口
 * 
 * @author cjm
 * @since 2013-8-16
 */
@SuppressWarnings("unused")
public interface QualificaitonsApplyRemoteService {

  public Integer insertQualificaitonsApplyDO(QualificationsApplyDO qualificaitonsApplyDO)
      throws SQLException;

}
