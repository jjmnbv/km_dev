package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.dao.BnesCustomerTypeDAO;
import com.pltfm.app.dao.CommercialTenantBasicInfoDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.LoginRoseRelDAO;
import com.pltfm.app.dao.PersonalityInfoDAO;
import com.pltfm.app.dataobject.LoginRoseRelDO;
import com.pltfm.app.dataobject.RankDO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.dataobject.UserLevelDO;
import com.pltfm.app.service.CommercialTenantBasicInfoService;
import com.pltfm.app.service.RankService;
import com.pltfm.app.service.UserLevelService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.util.MD5;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.AccountInfoExample;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;
import com.pltfm.app.vobject.CommercialTenantBasicInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.LoginInfoExample;
import com.pltfm.app.vobject.LoginRoseRelQuery;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.PersonalityInfoExample;
import com.pltfm.app.vobject.Rank;
import com.pltfm.app.vobject.UserLevel;

/**
 * 商户信息业务逻辑类
 * 
 * @author cjm
 * @since 2013-7-17
 */
@SuppressWarnings("unchecked")
@Component(value = "commercialTenantBasicInfoService")
public class CommercialTenantBasicInfoServiceImpl implements CommercialTenantBasicInfoService {
  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;
  /**
   * 商户信息DAO接口
   */
  @Resource(name = "commercialTenantBasicInfoDAO")
  private CommercialTenantBasicInfoDAO commercialTenantBasicInfoDAO;

  /**
   * 个性信息DAO接口
   */
  @Resource(name = "personalityInfoDAO")
  private PersonalityInfoDAO personalityInfoDAO;

  /**
   * 客户类别DAO接口
   */
  @Resource(name = "bnesCustomerTypeDAO")
  private BnesCustomerTypeDAO bnesCustomerTypeDAO;
  /**
   * 客户等级业务逻辑接口
   */
  @Resource(name = "userLevelService")
  private UserLevelService userLevelService;

  /**
   * 账户信息DAO接口
   */
  @Resource(name = "accountInfoDAO")
  private AccountInfoDAO accountInfoDAO;

  /**
   * 客户头衔业务逻辑接口
   */
  @Resource(name = "rankService")
  private RankService rankService;

  @Resource(name = "loginRoseRelDAO")
  private LoginRoseRelDAO loginRoseRelDAO;
  @Autowired
  private CouponRemoteService couponRemoteService;
  
  public LoginRoseRelDAO getLoginRoseRelDAO() {
    return loginRoseRelDAO;
  }

  public void setLoginRoseRelDAO(LoginRoseRelDAO loginRoseRelDAO) {
    this.loginRoseRelDAO = loginRoseRelDAO;
  }

  /**
   * 添加商户信息
   * 
   * @param record 商户信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer addCommercialTenantBasicInfo(CommercialTenantBasicInfo commercialTenantBasicInfo,
      LoginInfo loginInfo, PersonalityInfo personalityInfo, AccountInfo accountInfo)
      throws Exception {
    // 当前日期
    loginInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    // MD5加密
    loginInfo.setLoginPassword(MD5.md5crypt(loginInfo.getLoginPassword()));
    loginInfo.setN_Status(1);

    Integer n_LoginId = loginInfoDAO.insert(loginInfo);

    // 添加个人个性信息
    personalityInfo.setN_LoginId(n_LoginId);
    personalityInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    personalityInfo.setAmountConsume(BigDecimal.ZERO);
    personalityInfo.setN_TotalIntegral(BigDecimal.ZERO);
    Integer personalityInfoId = personalityInfoDAO.insert(personalityInfo);

    // 添加商户客户信息
    commercialTenantBasicInfo.setMobile(loginInfo.getMobile());
    commercialTenantBasicInfo.setN_LoginId(n_LoginId);
    commercialTenantBasicInfo.setContactsEmail(loginInfo.getEmail());
    commercialTenantBasicInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    commercialTenantBasicInfo.setN_EnterpriseStatus(1);

    // 添加账户信息
    accountInfo.setAccountLogin(loginInfo.getLoginAccount());
    accountInfo.setAddress(commercialTenantBasicInfo.getCorporateLocation());
    accountInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    accountInfo.setEmail(loginInfo.getEmail());
    accountInfo.setMobile(loginInfo.getMobile());
    accountInfo.setN_CustomerTypeId(loginInfo.getN_CustomerTypeId());
    accountInfo.setN_Status(1);
    accountInfo.setName(commercialTenantBasicInfo.getContactsName());
    accountInfo.setAcconutId(commercialTenantBasicInfo.getCertificateNumber());

    accountInfo.setN_LoginId(n_LoginId);
    // MD5加密
    accountInfo.setPaymentpwd(MD5.md5crypt(accountInfo.getPaymentpwd()));

    accountInfoDAO.insert(accountInfo);

    UserLevelDO userLevelDO = new UserLevelDO();
    userLevelDO.setLoginId(n_LoginId);
    userLevelDO.setCustomerTypeId(loginInfo.getN_CustomerTypeId());
    userLevelDO.setExpend(personalityInfo.getAmountConsume());

    userLevelService.searchUserLevelUpdateInfo(userLevelDO);

    RankDO rankDO = new RankDO();
    rankDO.setPersonalityId(personalityInfoId);
    rankDO.setExpend(personalityInfo.getN_TotalIntegral());
    rankDO.setCustomerTypeId(loginInfo.getN_CustomerTypeId());
    rankService.updateRankName(rankDO);
    // 注册发放优惠劵
    this.changeCustomGrantToGive(n_LoginId);

    return commercialTenantBasicInfoDAO.insert(commercialTenantBasicInfo);
  }

  /**
   * 查询登录账号是否存在
   * 
   * @param accountLogin
   * @return
   * @throws Exception
   */
  @Override
  public Integer checkLoginAccount(String loginAccount) throws Exception {
    List<AccountInfo> list = null;
    Integer rows = 0;
    LoginInfoExample loginInfoExample = new LoginInfoExample();
    loginInfoExample.createCriteria().andLoginAccountEqualTo(loginAccount);
    list = loginInfoDAO.selectByExample(loginInfoExample);
    if (list != null && list.size() > 0) {
      rows = 1;
    }
    return rows;
  }

  /**
   * 根据主键进行删除单条商户信息
   * 
   * @param n_CommercialTenantIds 商户信息ID集合
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer deleteByPrimaryKey(List<Integer> nCommercialTenantIds) throws Exception {
    Integer count = 0;
    if (ListUtils.isNotEmpty(nCommercialTenantIds)) {
      for (Integer id : nCommercialTenantIds) {
        CommercialTenantBasicInfo commercialTenantBasicInfo =
            commercialTenantBasicInfoDAO.selectByPrimaryKey(id);
        PersonalityInfoExample example = new PersonalityInfoExample();
        example.createCriteria().andn_LoginIdEqualTo(commercialTenantBasicInfo.getN_LoginId());
        personalityInfoDAO.deleteByExample(example);

        AccountInfoExample accountInfoExample = new AccountInfoExample();
        accountInfoExample.createCriteria()
            .andn_LoginIdEqualTo(commercialTenantBasicInfo.getN_LoginId());
        accountInfoDAO.deleteByExample(accountInfoExample);

        loginInfoDAO.deleteByPrimaryKey(commercialTenantBasicInfo.getN_LoginId());
        count += commercialTenantBasicInfoDAO.deleteByPrimaryKey(id);
      }
    }
    return count;
  }

  /**
   * 查询全部登录信息
   * 
   * @return
   * @throws Exception
   */
  @Override
  public List<LoginInfo> queryLoginInfoList() throws Exception {
    return loginInfoDAO.selectByExample(null);
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 账户信息实体
   * @return 返回值
   * @throws Exception
   */
  @Override
  public Page searchPageByVo(Page pageParam, CommercialTenantBasicInfo vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new CommercialTenantBasicInfo();
    }
    // 获取客户积分规则总数
    int totalNum = commercialTenantBasicInfoDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    // 设置查询开始结束索引
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());

    pageParam.setDataList(commercialTenantBasicInfoDAO.selectPageByVo(vo));
    return pageParam;
  }

  /**
   * 根据主键查询单条商户信息
   * 
   * @param n_PersonalityId 商户信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public CommercialTenantBasicInfo selectByPrimaryKey(Integer nCommercialTenantId)
      throws Exception {
    return commercialTenantBasicInfoDAO.selectByPrimaryKey(nCommercialTenantId);
  }

  /**
   * 根据登录主键查询单条商户信息
   * 
   * @param loginId 登录信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
public CommercialTenantBasicInfo selectloginId(Integer loginId) throws Exception {
    return commercialTenantBasicInfoDAO.selectByPrimaryLoginInfo(loginId);
  }

  /**
   * 修改商户信息
   * 
   * @param record 商户信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer updateCommercialTenantBasicInfo(
      CommercialTenantBasicInfo commercialTenantBasicInfo, LoginInfo loginInfo,
      PersonalityInfo personalityInfo, AccountInfo accountInfo) throws Exception {
    // 当前日期
    loginInfo.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());

    // loginInfoDAO.updateByPrimaryKeySelective(loginInfo);

    // 修改个人个性信息
    personalityInfo.setN_LoginId(loginInfo.getN_LoginId());
    personalityInfo.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());

    if (personalityInfo.getN_TotalIntegral() == null) {
      personalityInfo.setN_TotalIntegral(BigDecimal.ZERO);
    }

    if (personalityInfo.getAmountConsume() == null) {
      personalityInfo.setAmountConsume(BigDecimal.ZERO);
    }

    // personalityInfoDAO.updateByPrimaryKeySelective(personalityInfo);

    commercialTenantBasicInfo.setN_LoginId(loginInfo.getN_LoginId());
    commercialTenantBasicInfo.setMobile(commercialTenantBasicInfo.getMobile());
    commercialTenantBasicInfo.setContactsEmail(commercialTenantBasicInfo.getContactsEmail());
    commercialTenantBasicInfo.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());


    // 修改账户信息
    /*
     * accountInfo.setAccountLogin(loginInfo.getLoginAccount());
     * accountInfo.setAddress(commercialTenantBasicInfo.getCorporateLocation());
     * accountInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
     * accountInfo.setEmail(loginInfo.getEmail()); accountInfo.setMobile(loginInfo.getMobile());
     * accountInfo.setN_CustomerTypeId(loginInfo.getN_CustomerTypeId());
     * accountInfo.setName(commercialTenantBasicInfo.getContactsName());
     * accountInfo.setAcconutId(commercialTenantBasicInfo.getCertificateNumber());
     * accountInfoDAO.updateByPrimaryKeySelective(accountInfo);
     */

    /*
     * UserLevelDO userLevelDO = new UserLevelDO();
     * userLevelDO.setLoginId(loginInfo.getN_LoginId());
     * userLevelDO.setCustomerTypeId(loginInfo.getN_CustomerTypeId());
     * userLevelDO.setExpend(personalityInfo.getAmountConsume());
     * 
     * userLevelService.searchUserLevelUpdateInfo(userLevelDO);
     */

    /*
     * RankDO rankDO = new RankDO(); rankDO.setPersonalityId(personalityInfo.getN_PersonalityId());
     * rankDO.setExpend(personalityInfo.getN_TotalIntegral());
     * rankDO.setCustomerTypeId(loginInfo.getN_CustomerTypeId());
     * rankService.updateRankName(rankDO);
     */
    LoginRoseRelDO loginRoseRelDO = new LoginRoseRelDO();
    loginRoseRelDO.setN_CommercialTenantId(
        new BigDecimal(commercialTenantBasicInfo.getN_CommercialTenantId()));
    loginRoseRelDO.setIsValid(commercialTenantBasicInfo.getIsValid());
    loginRoseRelDAO.updateLoginRoseRelDO(loginRoseRelDO);

    return commercialTenantBasicInfoDAO.updateByPrimaryKeySelective(commercialTenantBasicInfo);
  }



  /**
   * 查询商户下的子级类别集合
   */
  @Override
  public List<BnesCustomerTypeQuery> queryByCommCustomer() {

    return bnesCustomerTypeDAO.findParentList(3);
  }

  /**
   * 根据主键查询单条登录信息
   * 
   * @param n_PersonalityId 登录信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public LoginInfo selectByn_LoginId(Integer nLoginId) throws Exception {
    return loginInfoDAO.selectByPrimaryKey(nLoginId);
  }

  /**
   * 根据登录ID查询单个账户信息
   */
  @Override
  public AccountInfo selectByAccountInfo(Integer nLoginId) throws Exception {
    return accountInfoDAO.selectByPrimaryLoginInfo(nLoginId);
  }

  /**
   * 根据登录ID查询单个个人个性信息
   */
  @Override
  public PersonalityInfo selectByPersonalityInfo(Integer nLoginId) throws Exception {
    return personalityInfoDAO.selectByPersonalityInfo(nLoginId);
  }

  /**
   * 根据商户类别客户查询商户客户的头衔
   * 
   * @return
   */
  @Override
  public List<Rank> queryByCommRank(Integer customerTypeId) {
    List<Rank> rankList = null;
    try {
      rankList = rankService.getCustomerTypeIdKey(customerTypeId);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rankList;
  }

  /**
   * 根据商户类别客户查询商户客户的等级
   * 
   * @return
   */
  @Override
  public List<UserLevel> queryByCommUserLevel(Integer customerTypeId) {
    List<UserLevel> levelList = null;
    UserLevel userLevel = new UserLevel();
    userLevel.setN_customer_type_id(customerTypeId);
    try {
      levelList = userLevelService.getUserLevellist(userLevel);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return levelList;
  }

  /**
   * 注册发放优惠劵
   * 
   * @return
   */
  public int changeCustomGrantToGive(int LoginId) {
    int result = 0;
    try {
      // 查询发放注册类型优惠劵
      List<Coupon> list = couponRemoteService.selectCouponByType((long) 2);
      for (Coupon cn : list) {
        UserInfoDO userInfoDO = new UserInfoDO();
        userInfoDO.setLoginId(LoginId);
        // userInfoDO.setMobile(mobile)
        // 发放优惠劵
        result = couponRemoteService.changeCustomGrantToGive(userInfoDO, cn.getCouponId(),
            Long.valueOf(LoginId), Long.valueOf(21), String.valueOf(LoginId), null);

      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public LoginInfoDAO getLoginInfoDAO() {
    return loginInfoDAO;
  }

  public void setLoginInfoDAO(LoginInfoDAO loginInfoDAO) {
    this.loginInfoDAO = loginInfoDAO;
  }

  public CommercialTenantBasicInfoDAO getCommercialTenantBasicInfoDAO() {
    return commercialTenantBasicInfoDAO;
  }

  public void setCommercialTenantBasicInfoDAO(
      CommercialTenantBasicInfoDAO commercialTenantBasicInfoDAO) {
    this.commercialTenantBasicInfoDAO = commercialTenantBasicInfoDAO;
  }

  public PersonalityInfoDAO getPersonalityInfoDAO() {
    return personalityInfoDAO;
  }

  public void setPersonalityInfoDAO(PersonalityInfoDAO personalityInfoDAO) {
    this.personalityInfoDAO = personalityInfoDAO;
  }

  public BnesCustomerTypeDAO getBnesCustomerTypeDAO() {
    return bnesCustomerTypeDAO;
  }

  public void setBnesCustomerTypeDAO(BnesCustomerTypeDAO bnesCustomerTypeDAO) {
    this.bnesCustomerTypeDAO = bnesCustomerTypeDAO;
  }

  public UserLevelService getUserLevelService() {
    return userLevelService;
  }

  public void setUserLevelService(UserLevelService userLevelService) {
    this.userLevelService = userLevelService;
  }

  public RankService getRankService() {
    return rankService;
  }

  public void setRankService(RankService rankService) {
    this.rankService = rankService;
  }

  public AccountInfoDAO getAccountInfoDAO() {
    return accountInfoDAO;
  }

  public void setAccountInfoDAO(AccountInfoDAO accountInfoDAO) {
    this.accountInfoDAO = accountInfoDAO;
  }

  /**
   * 根据登录主键查询单条商户信息
   * 
   * @param nLoginId 登录信息ID
   * @return 返回值
   * @throws SQLException
   * @throws Exception 异常
   */
  @Override
  public CommercialTenantBasicInfo selectByPrimaryLoginInfo(Integer nLoginId) throws SQLException {
    return commercialTenantBasicInfoDAO.selectByPrimaryLoginInfo(nLoginId);
  }

  @Override
  public void verifyPass(CommercialTenantBasicInfo commercialTenantBasicInfo) throws SQLException {
    commercialTenantBasicInfoDAO.verifyPass(commercialTenantBasicInfo);

  }

  @Override
  public void notPass(CommercialTenantBasicInfo commercialTenantBasicInfo) throws SQLException {
    commercialTenantBasicInfoDAO.notPass(commercialTenantBasicInfo);

  }

  @Override
  public LoginRoseRelQuery selectByNcustomerId(Integer nCommercialTenantId) throws SQLException {
    return commercialTenantBasicInfoDAO.selectByNcustomerId(nCommercialTenantId);
  }
}
