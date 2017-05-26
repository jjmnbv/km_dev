package com.kmzyc.b2b.third.dao;

import java.sql.SQLException;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.third.model.ThirdAccountInfo;

/**
 * 对第三方登录信息的管理
 * 
 * @author Administrator 2014-03-18
 * 
 */
public interface ThirdAccountInfoDao extends Dao {
  /**
   * 将第三方登录后获取到的用户信息插入到third_account_info表中
   * 
   * @param entity 传参实体
   */
  public void insert(ThirdAccountInfo entity) throws SQLException;

  /**
   * 修改第三方账号的信息
   * 
   * @param entity
   * @throws SQLException
   */
  public int updateAcctInfo(ThirdAccountInfo entity) throws SQLException;

  /**
   * 根据loginId查询openId
   * 
   * @param loginId
   * @throws SQLException
   */
  public String queryOpenIdByLoginId(String loginId) throws SQLException;

  /**
   * 根据主键(openid,accountType)查询实体信息
   * 
   * @param condition 主键组合条件
   * @return
   */
  public ThirdAccountInfo queryByPrimaryKey(ThirdAccountInfo condition) throws SQLException;

  /**
   * 删除第三方账号的信息
   * 
   * @param condition
   * @throws SQLException
   */
  public void deleteAccountInfo(ThirdAccountInfo condition) throws SQLException;

  /**
   * 查询第三方帐号信息
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public ThirdAccountInfo queryThirdAccountInfo(Map<String, Object> params)throws SQLException;
}
