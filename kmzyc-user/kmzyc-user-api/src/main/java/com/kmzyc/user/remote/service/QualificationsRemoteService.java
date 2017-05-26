package com.kmzyc.user.remote.service;

import java.sql.SQLException;

import com.pltfm.app.vobject.QualificationsDO;

/**
 * 资格相关信息远程接口
 * 
 * @author cjm
 * @since 2013-8-16
 */
@SuppressWarnings("unused")
public interface QualificationsRemoteService {
  public Integer insertQualificaitonsDO(QualificationsDO qualificationsDO) throws SQLException;
}
