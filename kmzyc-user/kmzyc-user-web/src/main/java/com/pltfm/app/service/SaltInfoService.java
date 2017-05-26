package com.pltfm.app.service;

import java.sql.SQLException;

import com.kmzyc.b2b.vo.UserBaseInfo;
import com.pltfm.app.vobject.SaltInfo;

/**
 * 用户密码加盐
 */
public interface SaltInfoService {

  /**
   * 根据 loginid或者loginaccount或者mobil 查询用户密码加盐信息
   * @param userInfoVo
   * @return
   * @throws SQLException
   */
  public SaltInfo querySaltInfo(UserBaseInfo userInfoVo);
  /**
   * 插入
   * @param saltInfo
   */
  public void insertSaltInfo(SaltInfo saltInfo);
}
