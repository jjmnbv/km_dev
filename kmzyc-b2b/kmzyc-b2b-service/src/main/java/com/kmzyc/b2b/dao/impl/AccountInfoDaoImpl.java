package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kmzyc.b2b.dao.AccountInfoDao;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.vo.SellerInfo;
import com.km.framework.persistence.impl.DaoImpl;

@Repository
@SuppressWarnings("unchecked")
public class AccountInfoDaoImpl extends DaoImpl implements AccountInfoDao {
  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  public AccountInfo findByLoginId(long loginid) throws SQLException {
    AccountInfo result = (AccountInfo) sqlMapClient.queryForObject("AccountInfo.findById", loginid);
    return result;
  }

  /**
   * 更新邮箱
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public int updateEmail(Map map) throws SQLException {
    return (int) sqlMapClient.update("AccountInfo.updateEmail", map);
  }

  /**
   * 更新手机
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public int updateMobile(Map map) throws SQLException {
    return (int) sqlMapClient.update("AccountInfo.updateMobile", map);
  }

  /**
   * 根据商户id查询供应商信息
   * 
   * @param merchantIds
   * @return
   * @throws SQLException
   */
  public List<SellerInfo> querySellerInfoByMerchantId(List<Object> merchantIds) throws SQLException {
    return (List<SellerInfo>) sqlMapClient.queryForList(
        "AccountInfo.SQL_QUERY_SELLERINFO_BY_MERCHANTID", merchantIds);
  }

  /**
   * 根据商户idd查询供应商信息
   * 
   * @param merchantId
   * @return
   * @throws SQLException
   */
  public SellerInfo getSellerInfo(Long merchantId) throws SQLException {
    return (SellerInfo) sqlMapClient.queryForObject("AccountInfo.SQL_GET_SELLERINFO", merchantId);
  }

  /**
   * 根据登录ID查询帐号信息，结算用
   * 
   * @param loginId
   * @return
   * @throws SQLException
   */
  public AccountInfo findAccountInfoByLoginIdForSett(Long loginId) throws SQLException {
    return (AccountInfo) sqlMapClient.queryForObject(
        "AccountInfo.SQL_QUERY_ACCOUNT_INFO_BY_LOGIN_ID_FOR_SETT", loginId);
  }

  /**
   * 根据登录id更新登录来源
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  @Override
  public int updateRegistSource(Map map) throws SQLException {
    return sqlMapClient.update("AccountInfo.updateSource", map);
  }

  /**
   * 查询用户安全信息
   * 
   * @param loginId
   * @return
   * @throws Exception
   */
  public Map<String, String> queryUserSecurityInfo(Long loginId) throws SQLException {
    return (Map<String, String>) sqlMapClient.queryForObject(
        "AccountInfo.SQL_QUERY_USER_SECURITY_INFO", loginId);
  }
}
