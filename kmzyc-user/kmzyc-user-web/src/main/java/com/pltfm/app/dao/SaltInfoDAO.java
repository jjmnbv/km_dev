package com.pltfm.app.dao;

import java.sql.SQLException;

import com.kmzyc.b2b.vo.UserBaseInfo;
import com.pltfm.app.vobject.SaltInfo;

/**
 * 用户密码加盐
 * 
 * @author zhl
 * @since 2013-07-08
 */
public interface SaltInfoDAO {


  /**
   * 根据loginId 查询用户密码加盐信息
   * @param userInfoVo
   * @return
   * @throws SQLException
   */
  public SaltInfo querySaltInfo(UserBaseInfo userInfoVo) throws SQLException;

  /**
   * 插入
   * @param saltInfo
   */
  public void insertSaltInfo(SaltInfo saltInfo) throws SQLException;
}
