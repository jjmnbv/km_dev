package com.kmzyc.promotion.app.dao;

import java.sql.SQLException;

import com.pltfm.app.dataobject.UserInfoDO;
/**
 * 用户信息
 * @author xlg
 *
 */
public interface UserInfoDao {
	/**
	 * 查询用户信息
	 * @return
	 * @throws SQLException
	 */
	public UserInfoDO queryUserInfo(Long uid) throws SQLException;
}
