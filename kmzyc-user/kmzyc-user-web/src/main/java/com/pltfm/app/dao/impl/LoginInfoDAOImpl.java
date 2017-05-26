package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.LoginInfoExample;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 登录信息处理类
 * 
 * @author cjm
 * @since 2013-7-9
 */
@SuppressWarnings({"unchecked", "unused"})
@Component(value = "loginInfoDAO")
public class LoginInfoDAOImpl implements LoginInfoDAO {
  @Resource(name = "sqlMapClient")
  /**
   * 数据库操作对象
   */
  private SqlMapClient sqlMapClient;

  /**
   * 添加登录信息
   * 
   * @param record 登录信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public Integer insert(LoginInfo record) throws SQLException {
    Object newKey = sqlMapClient.insert("LOGIN_INFO.abatorgenerated_insert", record);
    return (Integer) newKey;
  }

  /**
   * 修改登录信息
   * 
   * @param record 登录信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int updateByPrimaryKey(LoginInfo record) throws SQLException {
    int rows = sqlMapClient.update("LOGIN_INFO.abatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * 动态修改登录信息
   * 
   * @param record 登录信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int updateByPrimaryKeySelective(LoginInfo record) throws SQLException {
    int rows =
        sqlMapClient.update("LOGIN_INFO.abatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * 按等级id查询是否存在头衔
   * 
   * @param levelId 等级id
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int countLevel(Integer levelId) throws SQLException {
    Integer count = (Integer) sqlMapClient.queryForObject("LOGIN_INFO.selectUserLevel", levelId);
    return count.intValue();
  }

  /**
   * 按登录信息条件查询
   * 
   * @param example 登录信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public List selectByExample(LoginInfoExample example) throws SQLException {
    List list = sqlMapClient.queryForList("LOGIN_INFO.abatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * 按【客户类型信息查询
   * 
   * @param example 客户类型信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public List selectCustomerTypeId(Integer customerTypeId) throws SQLException {
    LoginInfo key = new LoginInfo();
    key.setN_CustomerTypeId(customerTypeId);
    List list = sqlMapClient.queryForList("LOGIN_INFO.abatorgenerated_selectCustomerTypeId", key);
    return list;
  }

  /**
   * 根据登录主键查询单条商户基本信息
   * 
   * @param nCommercialTenantId 登录主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public LoginInfo selectByPrimaryKey(Integer n_LoginId) throws SQLException {
    LoginInfo key = new LoginInfo();
    key.setN_LoginId(n_LoginId);
    LoginInfo record = (LoginInfo) sqlMapClient
        .queryForObject("LOGIN_INFO.abatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * 按登录信息条件进行删除
   * 
   * @param example 登录信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int deleteByExample(LoginInfoExample example) throws SQLException {
    int rows = sqlMapClient.delete("LOGIN_INFO.abatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * 根据登录主键删除商户基本信息
   * 
   * @param nCommercialTenantId 登录主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int deleteByPrimaryKey(Integer n_LoginId) throws SQLException {
    LoginInfo key = new LoginInfo();
    key.setN_LoginId(n_LoginId);
    int rows = sqlMapClient.delete("LOGIN_INFO.abatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * 按登录信息条件查询总条数
   * 
   * @param example 登录信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int countByExample(LoginInfoExample example) throws SQLException {
    Integer count =
        (Integer) sqlMapClient.queryForObject("LOGIN_INFO.abatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * 动态按登录信息条件进行修改
   * 
   * @param record 登录信息实体
   * @param example 登录信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int updateByExampleSelective(LoginInfo record, LoginInfoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("LOGIN_INFO.abatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * 按登录信息条件进行修改
   * 
   * @param record 登录信息实体
   * @param example 登录信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int updateByExample(LoginInfo record, LoginInfoExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("LOGIN_INFO.abatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * 按条件查询登录信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  @Override
public int selectCountByVo(LoginInfo record) throws SQLException {
    List list = sqlMapClient.queryForList("LOGIN_INFO.selectCountByVo", record);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();
    return recs;
  }

  /**
   * 根据vo条件查询登录信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  @Override
public List selectPageByVo(LoginInfo record) throws SQLException {
    List pageList = sqlMapClient.queryForList("LOGIN_INFO.searchPageByVo", record);
    return pageList;
  }

  /**
   * 取得登录名 姓名
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  @Override
public List getLoginAll() throws SQLException {
    return sqlMapClient.queryForList("LOGIN_INFO.getLoginAll");
  }

  /**
   * 通过客户基本信息访问类查询客户信息
   * 
   * @param userInfoDO 客户基本信息数据访问实体
   * @return
   * @throws SQLException 异常
   */
  @Override
public List queryBasicUserInfo(UserInfoDO userInfoDO) throws SQLException {
    return sqlMapClient.queryForList("LOGIN_INFO.abatorgenerated_pageByUserInfo", userInfoDO);
  }

  /**
   * 通过客户等级主键查询客户集合信息
   * 
   * @param levelId 客户等级主键
   * @return 客户信息集合
   * @throws SQLException 异常
   */
  @Override
public List selectByUserLevelId(UserInfoDO userInfoDO) throws SQLException {
    return sqlMapClient.queryForList("LOGIN_INFO.abatorgenerated_selectByUserLevelId", userInfoDO);
  }

  /**
   * 通过客户基本信息访问类查询客户信息总数
   * 
   * @param userInfoDO 客户基本信息数据访问实体
   * @return
   * @throws SQLException 异常
   */
  @Override
public Integer byCountBasicUserInfo(UserInfoDO userInfoDO) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("LOGIN_INFO.abatorgenerated_byCountUserInfo", userInfoDO);
    return count;
  }

  /**
   * 通过登录id 查询客户基本信息
   * 
   * @param logInId 登录主键
   * @return
   * @throws SQLException 异常信息
   */
  @Override
public UserInfoDO selectByLoginId(UserInfoDO userInfoDO) throws SQLException {
    return (UserInfoDO) sqlMapClient.queryForObject("LOGIN_INFO.abatorgenerated_selectByLoginId",
        userInfoDO);
  }

  /**
   * 通过登录UserInfoDO查询客户基本信息
   * 
   * @param UserInfoDO
   * @return List<UserInfoDO>
   * @throws SQLException 异常信息
   */

  @Override
public List<UserInfoDO> selectByUserInfoDO(UserInfoDO userInfoDO) throws SQLException {
    return sqlMapClient.queryForList("LOGIN_INFO.abatorgenerated_selectByLoginId", userInfoDO);

  }

  @Override
public List<UserInfoDO> selectUserInfoDOByLoginId(UserInfoDO userInfoDO) throws SQLException {
    return sqlMapClient.queryForList("LOGIN_INFO.selectUserInfoDOByLoginId", userInfoDO);
  }

  @Override
public Integer countCheckLoginEmail(UserInfoDO userInfoDO) throws SQLException {

    Integer count = 0;
    try {
      count = (Integer) sqlMapClient.queryForObject("LOGIN_INFO.countByEmail", userInfoDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return count;

  }
  
  @Override
public Integer countCheckLoginMobile(UserInfoDO userInfoDO) throws SQLException {
      return (Integer) sqlMapClient.queryForObject("LOGIN_INFO.countByMobile", userInfoDO);

    }

  /**
   * 获取客户基本信息
   * 
   * @param userInfoDO 客户数据方位实体
   * @return
   * @throws Exception 异常
   */
  @Override
public List<LoginInfo> getLoginInfo(UserInfoDO userInfoDO) throws Exception {
    List<LoginInfo> list = null;
    list = sqlMapClient.queryForList("LOGIN_INFO.login_ByInfo", userInfoDO);
    return list;

  }

  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * LOGIN_INFO 修改条件参数类
   * 
   * @abatorgenerated Tue Jul 09 16:19:36 CST 2013
   */
  private static class UpdateByExampleParms extends LoginInfoExample {
    private Object record;

    public UpdateByExampleParms(Object record, LoginInfoExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  @Override
  public List getAllMobileUser() throws SQLException {
    return sqlMapClient.queryForList("LOGIN_INFO.getAllMobileUser");
  }

  /**
   * 根据登录名查询用户信息
   * 
   * @return
   * @throws Exception
   */
  @Override
  public LoginInfo queryUserInfoByLoginAccount(String loginAccount) throws Exception {
    return (LoginInfo) sqlMapClient
        .queryForObject("LOGIN_INFO.SQL_QUERY_USER_INFO_BY_LOGIN_ACCOUNT", loginAccount);
  }


  /**
   * 根据用户ID查询用户信息
   * 
   * @param LoginId
   * @return
   * @throws SQLException
   */
  @Override
public UserInfoDO queryUserByLoginId(Long LoginId) throws SQLException {
    return (UserInfoDO) sqlMapClient.queryForObject("LOGIN_INFO.SQL_QUERY_USER_BY_LOGIN_ID",
        LoginId);
  }

  @Override
  public String generateUserCardNum() throws SQLException {
    return (String) sqlMapClient.queryForObject("LOGIN_INFO.generateUserCardNum");
  }

  @Override
  public String queryUserCardNumByLoginAccount(String accountLogin) throws SQLException {
    return (String) sqlMapClient.queryForObject("LOGIN_INFO.queryUserCardNumByLoginAccount",
        accountLogin);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, BigDecimal> queryUserInfoPrimaryKeyByCardNum(String cardNum)
      throws SQLException {
    return (Map<String, BigDecimal>) sqlMapClient
        .queryForObject("LOGIN_INFO.queryUserInfoPrimaryKeyByCardNum", cardNum);
  }


  @Override
  public UserBaseInfo queryUserLoginPassword(UserBaseInfo userInfoVo) throws SQLException {
    return (UserBaseInfo)this.sqlMapClient.queryForObject("LOGIN_INFO.queryUserLoginPassword");
  }

@Override
public Integer insertOrUpdate(LoginInfo info) throws  SQLException{  
    Integer inre = (Integer)sqlMapClient.insert("LOGIN_INFO.insertOrUpdate_loginInfo",info);
    return inre;
}
}
