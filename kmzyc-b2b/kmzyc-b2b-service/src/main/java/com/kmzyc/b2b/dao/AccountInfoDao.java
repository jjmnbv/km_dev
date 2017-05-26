package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.vo.SellerInfo;
import com.km.framework.persistence.Dao;

@SuppressWarnings("unchecked")
public interface AccountInfoDao extends Dao {
  AccountInfo findByLoginId(long loginid) throws SQLException;

  /**
   * 更新邮箱
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public int updateEmail(Map map) throws SQLException;

  /**
   * 更新手机
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public int updateMobile(Map map) throws SQLException;

  /**
   * 根据商户id查询供应商信息
   * 
   * @param merchantIds
   * @return
   * @throws SQLException
   */
  public List<SellerInfo> querySellerInfoByMerchantId(List<Object> merchantIds) throws SQLException;

  /**
   * 根据商户idd查询供应商信息
   * 
   * @param merchantId
   * @return
   * @throws SQLException
   */
  public SellerInfo getSellerInfo(Long merchantId) throws SQLException;


  /**
   * 根据登录ID查询帐号信息，结算用
   * 
   * @param loginId
   * @return
   * @throws SQLException
   */
  public AccountInfo findAccountInfoByLoginIdForSett(Long loginId) throws SQLException;

  /**
   * 根据登录id更新登录来源
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public int updateRegistSource(Map map) throws SQLException;

  /**
   * 查询用户安全信息
   * 
   * @param loginId
   * @return
   * @throws Exception
   */
  public Map<String, String> queryUserSecurityInfo(Long loginId) throws SQLException;
}
