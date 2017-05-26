package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.util.MD5;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.AccountInfoExample;
import com.pltfm.app.vobject.LoginInfo;

/**
 * 账户信息业务逻辑处理类
 * 
 * @author cjm
 * @since 2013-7-10
 */
@Component(value = "accountInfoService")
public class AccountInfoServiceImpl implements AccountInfoService {
  /**
   * 账户信息DAO接口
   */
  @Resource(name = "accountInfoDAO")
  private AccountInfoDAO accountInfoDAO;

  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;

  /**
   * 添加账户信息
   * 
   * @param record 账户信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer addAccountInfo(AccountInfo accountInfo) throws Exception {
    LoginInfo loginInfo = null;
    loginInfo = loginInfoDAO.selectByPrimaryKey(accountInfo.getN_LoginId());
    accountInfo.setN_CustomerTypeId(loginInfo.getN_CustomerTypeId());
    accountInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    accountInfo.setN_Status(1);
    // MD5加密
    accountInfo.setPaymentpwd(MD5.md5crypt(accountInfo.getPaymentpwd()));
    return accountInfoDAO.insert(accountInfo);
  }

  /**
   * 根据主键进行删除单条账户信息
   * 
   * @param n_PersonalityId 账户信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer deleteByPrimaryKey(List<Integer> nAccountIds) throws Exception {
    Integer count = 0;
    if (ListUtils.isNotEmpty(nAccountIds)) {
      for (Integer id : nAccountIds) {
        count += accountInfoDAO.deleteByPrimaryKey(id);
      }
    }
    return count;
  }

  /**
   * 分页查询账户信息
   * 
   * @param pageParam 分页实体
   * @param vo 账户实体
   * @return
   * @throws Exception 异常
   */
  @Override
  public Page searchPageByVo(Page pageParam, AccountInfo vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new AccountInfo();
    }
    // 获取客户积分规则总数
    int totalNum = accountInfoDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    // 设置查询开始结束索引
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());

    pageParam.setDataList(accountInfoDAO.selectPageByVo(vo));
    return pageParam;
  }

  /**
   * 根据主键查询单条账户信息
   * 
   * @param n_PersonalityId 账户信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public AccountInfo selectByPrimaryKey(Integer nAccountId) throws Exception {
    return accountInfoDAO.selectByPrimaryKey(nAccountId);
  }

  /**
   * 修改账户信息
   * 
   * @param record 账户信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer updateAccountInfo(AccountInfo accountInfo) throws Exception {
    accountInfo.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());
    return accountInfoDAO.updateByPrimaryKeySelective(accountInfo);
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
    return accountInfoDAO.showAccountInfo(vo);
  }

  /**
   * 根据登录主键查询单条账户基本信息
   * 
   * @param loginId 账号主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public AccountInfo selectByPrimaryLoginInfo(Integer loginId) throws SQLException {
    return accountInfoDAO.selectByPrimaryLoginInfo(loginId);
  }

  @Override
public AccountInfo selectByPrimaryLoginInfo(String accountLogin, String mobileNo)
      throws SQLException {
    return accountInfoDAO.selectByPrimaryLoginInfo(accountLogin, mobileNo);
  }

  @Override
public int updateByLoginId(AccountInfo vo) throws SQLException {
    return accountInfoDAO.updateByLoginId(vo);
  }

  /**
   * 根据登录id查询账号信息和公司名
   * 
   * @param loginId 账号主键
   * @return 返回值
   * @throws Exception sql异常
   */
  @Override
public AccountInfo selectAccountAndCnameByLoginId(Integer n_LoginId) throws Exception {
    return accountInfoDAO.selectAccountAndCnameByLoginId(n_LoginId);
  }

  public AccountInfoDAO getAccountInfoDAO() {
    return accountInfoDAO;
  }

  public void setAccountInfoDAO(AccountInfoDAO accountInfoDAO) {
    this.accountInfoDAO = accountInfoDAO;
  }

  /**
   * 查询账户号是否存在
   */
  @Override
  public Integer checkAccountLogin(String accountLogin) throws Exception {
    List<AccountInfo> list = null;
    Integer rows = 0;
    AccountInfoExample accountInfoExample = new AccountInfoExample();
    accountInfoExample.createCriteria().andAccountLoginEqualTo(accountLogin);
    list = accountInfoDAO.selectByExample(accountInfoExample);
    if (list != null && list.size() > 0) {
      rows = 1;
    }
    return rows;
  }

  /**
   * 查询全部登录信息
   */
  @Override
  public List<LoginInfo> queryLoginInfoList() throws Exception {
    return loginInfoDAO.selectByExample(null);
  }

  public LoginInfoDAO getLoginInfoDAO() {
    return loginInfoDAO;
  }

  public void setLoginInfoDAO(LoginInfoDAO loginInfoDAO) {
    this.loginInfoDAO = loginInfoDAO;
  }

}
