package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.AccountInfoExample;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 账户信息处理类
 * 
 * @author cjm
 * @since 2013-7-10
 */
@Component(value = "accountInfoDAO")
public class AccountInfoDAOImpl implements AccountInfoDAO {
  /**
   * 数据库操作对象
   */
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 添加账户信息
   * 
   * @param record 账户信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public Integer insert(AccountInfo record) throws SQLException {
    Object newKey = sqlMapClient.insert("ACCOUNT_INFO.abatorgenerated_insert", record);
    return (Integer) newKey;
  }

  /**
   * 修改账户信息
   * 
   * @param record 账户信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int updateByPrimaryKey(AccountInfo record) throws SQLException {
    int rows = sqlMapClient.update("ACCOUNT_INFO.abatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * 修改账户信息 abatorgenerated_updateByLoginIdKeySelective
   * 
   * @param record 账户信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int updateByLoginId(AccountInfo record) throws SQLException {
    int rows =
        sqlMapClient.update("ACCOUNT_INFO.abatorgenerated_updateByLoginIdKeySelective", record);
    return rows;
  }

  /**
   * 动态修改账户信息
   * 
   * @param record 账户信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int updateByPrimaryKeySelective(AccountInfo record) throws SQLException {
    int rows =
        sqlMapClient.update("ACCOUNT_INFO.abatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * 按账户信息条件查询
   * 
   * @param example 账户信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */

  @Override
public List selectByExample(AccountInfoExample example) throws SQLException {
    List list = sqlMapClient.queryForList("ACCOUNT_INFO.abatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * 根据账户主键查询单条商户基本信息
   * 
   * @param nCommercialTenantId 账户主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public AccountInfo selectByPrimaryKey(Integer nAccountId) throws SQLException {
    AccountInfo key = new AccountInfo();
    key.setN_AccountId(nAccountId);
    AccountInfo record = (AccountInfo) sqlMapClient
        .queryForObject("ACCOUNT_INFO.abatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * 根据登录主键查询单条商户基本信息
   * 
   * @param nCommercialTenantId 登录主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public AccountInfo selectByPrimaryLoginInfo(Integer nLoginId) throws SQLException {
    AccountInfo key = new AccountInfo();
    key.setN_LoginId(nLoginId);
    AccountInfo record = (AccountInfo) sqlMapClient
        .queryForObject("ACCOUNT_INFO.abatorgenerated_selectByPrimaryLoginInfo", key);
    return record;
  }

  @Override
  public AccountInfo selectByPrimaryLoginInfo(String accountLogin, String mobileNo)
      throws SQLException {
    AccountInfo key = new AccountInfo();
    key.setAccountLogin(accountLogin);
    key.setMobile(mobileNo);
    AccountInfo record = (AccountInfo) sqlMapClient
        .queryForObject("ACCOUNT_INFO.abatorgenerated_selectByPrimaryLoginInfo", key);
    return record;
  }

  /**
   * 根据登录id查询账户信息和公司名
   * 
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public AccountInfo selectAccountAndCnameByLoginId(Integer n_LoginId) throws SQLException {
    return (AccountInfo) sqlMapClient.queryForObject("ACCOUNT_INFO.selectAccountAndCname",
        n_LoginId);
  }

  /**
   * 按账户信息条件进行删除
   * 
   * @param example 账户信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int deleteByExample(AccountInfoExample example) throws SQLException {
    int rows = sqlMapClient.delete("ACCOUNT_INFO.abatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * 根据账户主键删除账户基本信息
   * 
   * @param nCommercialTenantId 账户主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int deleteByPrimaryKey(Integer nAccountId) throws SQLException {
    AccountInfo key = new AccountInfo();
    key.setN_AccountId(nAccountId);
    int rows = sqlMapClient.delete("ACCOUNT_INFO.abatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * 按账户信息条件查询总条数
   * 
   * @param example 账户信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int countByExample(AccountInfoExample example) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("ACCOUNT_INFO.abatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * 动态按账户信息条件进行修改
   * 
   * @param record 账户信息实体
   * @param example 账户信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int updateByExampleSelective(AccountInfo record, AccountInfoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("ACCOUNT_INFO.abatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * 按账户信息条件进行修改
   * 
   * @param record 账户信息实体
   * @param example 账户信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int updateByExample(AccountInfo record, AccountInfoExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("ACCOUNT_INFO.abatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * 按条件查询账户信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  @Override
  public int selectCountByVo(AccountInfo vo) throws SQLException {
    List list = sqlMapClient.queryForList("ACCOUNT_INFO.getAccountInfoCount", vo);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();
    return recs;
  }

  /**
   * 通过账户相关信息查询账户详细信息
   * 
   * @param vo 账户实体
   * @return 账户详细信息
   * @throws Exception 异常
   */
  @Override
public AccountInfo showAccountInfo(AccountInfo vo) throws Exception {
    AccountInfo record = (AccountInfo) sqlMapClient
        .queryForObject("ACCOUNT_INFO.abatorgenerated_showAccountInfo", vo);
    return record;
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 个人个性信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public List selectPageByVo(AccountInfo vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("ACCOUNT_INFO.searchPageByVo", vo);
    return pageList;
  }

  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * ACCOUNT_INFO 修改条件参数类
   * 
   * @abatorgenerated Wed Jul 10 15:14:23 CST 2013
   */
  private static class UpdateByExampleParms extends AccountInfoExample {
    private Object record;

    public UpdateByExampleParms(Object record, AccountInfoExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }

  /**
   * 修改用户余额
   * 
   * @param vo
   * @return
   * @throws SQLException
   */
  @Override
public int updateAccountBalance(Map<String, String> params) throws SQLException {
    return sqlMapClient.update("ACCOUNT_INFO.SQL_UPDATE_ACCOUNT_BALANCE", params);
  }

  @Override
  public UserBaseInfo queryUserPayPassword(UserBaseInfo userInfoVo) throws SQLException {
    return (UserBaseInfo)this.sqlMapClient.queryForObject("ACCOUNT_INFO.queryUserPayPassword",userInfoVo);
  }

@Override
public Integer insertOrUpdateAccountInfo(AccountInfo record) throws SQLException {
    Object newKey = sqlMapClient.insert("ACCOUNT_INFO.insertOrUpdateAccountInfo", record);
    return (Integer) newKey;
}

}
