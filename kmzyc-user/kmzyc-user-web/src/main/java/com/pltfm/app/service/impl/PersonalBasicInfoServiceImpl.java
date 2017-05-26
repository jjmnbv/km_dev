package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.dao.AddressDAO;
import com.pltfm.app.dao.BnesCustomerTypeDAO;
import com.pltfm.app.dao.HealthYgenericInfoDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.MdicalExcusieInfoDAO;
import com.pltfm.app.dao.PersonalBasicInfoDAO;
import com.pltfm.app.dao.PersonalityInfoDAO;
import com.pltfm.app.dao.UserLevelDAO;
import com.pltfm.app.dataobject.RankDO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.dataobject.UserLevelDO;
import com.pltfm.app.service.PersonalBasicInfoService;
import com.pltfm.app.service.RankService;
import com.pltfm.app.service.UserLevelService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.util.MD5;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.Address;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;
import com.pltfm.app.vobject.HealthYgenericInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.LoginInfoExample;
import com.pltfm.app.vobject.MdicalExcusieInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.Rank;
import com.pltfm.app.vobject.UserLevel;
import com.pltfm.sys.util.ErrorCode;

@SuppressWarnings("unchecked")
@Component(value = "personalBasicInfoService")
public class PersonalBasicInfoServiceImpl implements PersonalBasicInfoService {

  private static Logger logger = Logger.getLogger(PersonalBasicInfoServiceImpl.class);

  private  SimpleDateFormat birthFormat = new SimpleDateFormat("yyyy-MM-dd");

  private  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Resource(name = "userLevelDAO")
  private UserLevelDAO userLevelDAO;
  /**
   * 个人信息DAO接口
   */
  @Resource(name = "personalBasicInfoDAO")
  private PersonalBasicInfoDAO personalBasicInfoDAO;

  /**
   * 登录信息DAO接口
   */
  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;

  /**
   * 个人个性信息DAO接口
   */
  @Resource(name = "personalityInfoDAO")
  private PersonalityInfoDAO personalityInfoDAO;

  /**
   * 健康信息DAo接口
   */
  @Resource(name = "healthYgenericInfoDAO")
  private HealthYgenericInfoDAO healthYgenericInfoDAO;
  /**
   * 医属信息DAo接口
   */
  @Resource(name = "mdicalExcusieInfoDAO")
  private MdicalExcusieInfoDAO mdicalExcusieInfoDAO;

  /**
   * 客户等级Service接口
   */
  @Resource(name = "userLevelService")
  private UserLevelService userLevelService;

  /**
   * 客户头衔Service接口
   */
  @Resource(name = "rankService")
  private RankService rankService;

  /**
   * 专家下的子级类别
   */
  @Resource(name = "bnesCustomerTypeDAO")
  private BnesCustomerTypeDAO bnesCustomerTypeDAO;
  /**
   * 地址
   */
  @Resource
  private AddressDAO addressDAO;
  /**
   * 账户DAO接口
   */
  @Resource(name = "accountInfoDAO")
  private AccountInfoDAO accountInfoDAO;
  private static final String promotionIbport =
      ConfigurationUtil.getString("promotionIbport");

  /**
   * 总部会员对接推送Service接口
   */
/*  @Resource(name = "baseDockService")
  private BaseDockService baseDockService;*/

  @SuppressWarnings("SpringJavaAutowiringInspection")
  @Autowired
  private CouponRemoteService couponRemoteService;
  
  /**
   * 添加专家信息
   * 
   * @param p 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer insertPersonalBasicInfo(PersonalBasicInfo p, LoginInfo loginInfo,
      MdicalExcusieInfo mdicalExcusieInfo, PersonalityInfo

      personalityInfo, HealthYgenericInfo healthYgenericInfo, AccountInfo accountInfo) throws SQLException {
    // 当前日期
    loginInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    // MD5加密
    loginInfo.setLoginPassword(MD5.md5crypt(loginInfo.getLoginPassword()));
    loginInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    loginInfo.setN_CustomerTypeId(2);
    loginInfo.setN_Status(1);
    // loginInfo.setN_CustomerTypeId(2);
    Integer n_LoginId = loginInfoDAO.insert(loginInfo);
    // 个人基本信息
    p.setMobile(loginInfo.getMobile());
    p.setN_LoginId(n_LoginId);
    p.setEmail(loginInfo.getEmail());
    p.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    p.setN_Status(loginInfo.getN_Status());
    p.setN_CustomerTypeId(loginInfo.getN_CustomerTypeId());
    personalBasicInfoDAO.insertPersonalBasicInfo(p);
    // 医属信息

    mdicalExcusieInfo.setN_personal_id(p.getN_PersonalId());
    mdicalExcusieInfo.setName(p.getName());
    mdicalExcusieInfo.setTheHospital(p.getWorkUnit());
    mdicalExcusieInfo.setProfessionName(p.getProfession());
    mdicalExcusieInfo.setTheCity(p.getLocation());
    mdicalExcusieInfo.setD_createDate(DateTimeUtils.getCalendarInstance().getTime());


    // 添加个人个性信息
    personalityInfo.setN_LoginId(n_LoginId);
    personalityInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    personalityInfo.setN_AvailableIntegral(personalityInfo.getN_TotalIntegral

    ());
    personalityInfo.setAmountConsume(BigDecimal.ZERO);
    personalityInfo.setN_TotalIntegral(BigDecimal.ZERO);
    personalityInfo.setN_EmpiricalValue(0);
    Integer personalityInfoId = personalityInfoDAO.insert(personalityInfo);
    // 添加健康信息
    healthYgenericInfo.setN_LoginId(n_LoginId);
    healthYgenericInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    healthYgenericInfoDAO.insert(healthYgenericInfo);
    // 添加账户信息
    accountInfo.setAccountLogin(loginInfo.getLoginAccount());
    accountInfo.setAddress(p.getLocation());
    accountInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    accountInfo.setEmail(loginInfo.getEmail());
    accountInfo.setMobile(loginInfo.getMobile());
    accountInfo.setN_CustomerTypeId(2);
    accountInfo.setN_Status(1);
    accountInfo.setName(p.getName());
    accountInfo.setN_LoginId(n_LoginId);
    accountInfo.setAcconutId(p.getCertificateNumber());
    // MD5加密
    accountInfo.setPaymentpwd(MD5.md5crypt(accountInfo.getPaymentpwd()));
    accountInfoDAO.insert(accountInfo);
    // 更新客户等级
    UserLevelDO userLevelDO = new UserLevelDO();
    userLevelDO.setLoginId(n_LoginId);
    userLevelDO.setCustomerTypeId(loginInfo.getN_CustomerTypeId());
    userLevelDO.setExpend(personalityInfo.getAmountConsume());
    try {
      userLevelService.searchUserLevelUpdateInfo(userLevelDO);
    } catch (Exception e) {
      e.printStackTrace();
    }

    RankDO rankDO = new RankDO();
    rankDO.setPersonalityId(personalityInfoId);
    rankDO.setIntegralnumber(personalityInfo.getN_EmpiricalValue());
    // rankDO.setExpend(1);
    rankDO.setCustomerTypeId(loginInfo.getN_CustomerTypeId());
    rankService.updateRankName(rankDO);
    // 增加注册发放优惠券
    this.changeCustomGrantToGive(n_LoginId);

    return mdicalExcusieInfoDAO.insertMdicalExcusieInfo(mdicalExcusieInfo);
  }

  /**
   * 查询专家下的子级类别集合
   */
  @Override
public List<BnesCustomerTypeQuery> queryByComm() {

    return bnesCustomerTypeDAO.findParentList(2);
  }

  /**
   * 修改专家信息
   * 
   * @param p 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer udpatePersonalBasicInfo(PersonalBasicInfo p, LoginInfo loginInfo,
      MdicalExcusieInfo mdicalExcusieInfo, PersonalityInfo personalityInfo,
      HealthYgenericInfo healthYgenericInfo, AccountInfo accountInfo) throws SQLException {
    // 当前日期
    loginInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    loginInfoDAO.updateByPrimaryKeySelective(loginInfo);
    // 个人基本信息
    p.setMobile(loginInfo.getMobile());
    p.setN_LoginId(loginInfo.getN_LoginId());
    p.setEmail(loginInfo.getEmail());
    p.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());
    p.setN_Status(loginInfo.getN_Status());
    p.setN_CustomerTypeId(loginInfo.getN_CustomerTypeId());
    personalBasicInfoDAO.udpatePersonalBasicInfo(p);
    // 医属信息
    mdicalExcusieInfo.setN_personal_id(p.getN_PersonalId());
    mdicalExcusieInfo.setName(p.getName());
    mdicalExcusieInfo.setProfessionName(p.getProfession());
    mdicalExcusieInfo.setTheHospital(p.getWorkUnit());
    mdicalExcusieInfo.setTheCity(p.getLocation());
    mdicalExcusieInfo.setD_modifyDate(DateTimeUtils.getCalendarInstance().getTime());
    mdicalExcusieInfoDAO.udpateMdicalExcusieInfo(mdicalExcusieInfo);
    // 修改个人个性信息
    personalityInfo.setN_LoginId(loginInfo.getN_LoginId());
    personalityInfo.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());
    personalityInfo.setN_AvailableIntegral(personalityInfo.getN_TotalIntegral());
    personalityInfoDAO.updateByPrimaryKeySelective(personalityInfo);
    // 修改账户信息
    accountInfo.setAddress(p.getLocation());
    accountInfo.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());
    accountInfo.setEmail(loginInfo.getEmail());
    accountInfo.setMobile(loginInfo.getMobile());
    accountInfo.setName(p.getName());
    accountInfo.setAcconutId(p.getCertificateNumber());

    accountInfoDAO.updateByPrimaryKeySelective(accountInfo);
    // 修改健康信息
    healthYgenericInfo.setN_LoginId(loginInfo.getN_LoginId());
    healthYgenericInfo.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());
    healthYgenericInfoDAO.updateByPrimaryKey(healthYgenericInfo);
    return personalBasicInfoDAO.udpatePersonalBasicInfo(p);

  }

  /**
   * 获取客户等级列表信息
   * 
   * @param nCustomerTypeId 客户等级vo
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
public List<UserLevel> getUserLevellist(int nCustomerTypeId) throws Exception {
    UserLevel userLevel = new UserLevel();
    userLevel.setN_customer_type_id(nCustomerTypeId);
    return userLevelDAO.queryUserLevelList(userLevel);
  }

  /**
   * 根据vo条件查询专家分布信息page
   * 
   * @param pageParam 分页实体
   * @param vo 专家基本信息实体
   * @return 返回值
   */
  @Override
public Page PagePersonalBasicInfo(Page pageParam, PersonalBasicInfo vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new PersonalBasicInfo();
    }
    int totalNum = personalBasicInfoDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    pageParam.setDataList(personalBasicInfoDAO.selectPageByVo(vo));
    return pageParam;
  }

  /**
   * 跟据登录id查登录信息
   * 
   * @param n_LoginId
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public LoginInfo getLogin_Id(Integer n_LoginId) throws SQLException {
    return loginInfoDAO.selectByPrimaryKey(n_LoginId);
  }

  /**
   * 跟据个人id查询医务专属信息
   * 
   * @param medicalMattersExclusive_id 医务专属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public MdicalExcusieInfo getMedicalMattersExclusive_id(Integer medicalMattersExclusive_id)
      throws SQLException {
    return mdicalExcusieInfoDAO.getPersonalId_FK(medicalMattersExclusive_id);
  }

  /**
   * 获取专家基本信息总条数
   * 
   * @param p 专家基本信息实体
   * @return 返回值
   * @throws SQLException 异常
   */
  @Override
public Integer countByExample(PersonalBasicInfo p) throws Exception {
    return personalBasicInfoDAO.selectCountByVo(p);
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 个人基本信息实体
   * @return 返回值
   * @throws Exception
   */
  @Override
public Page searchPageByVo(Page pageParam, PersonalBasicInfo vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new PersonalBasicInfo();
    }

    int totalNum = personalBasicInfoDAO.selectCountByPersonalBasicInfo(vo);
    pageParam.setRecordCount(totalNum);
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());

    pageParam.setDataList(personalBasicInfoDAO.selectPageByPersonalBasicInfo(vo));
    return pageParam;
  }

  /**
   * 添加专家信息
   * 
   * @param p 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public void insertPersonalBasicInfo(PersonalBasicInfo p) throws SQLException {
    personalBasicInfoDAO.insertPersonalBasicInfo(p);
  }

  /**
   * 删除专家信息
   * 
   * @param personalIds 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer deletePersonalBasicInfo(List<Integer> personalIds) throws SQLException {
    Integer count = 0;
    if (ListUtils.isNotEmpty(personalIds)) {
      for (Integer id : personalIds) {
        PersonalBasicInfo personalBasicInfo = null;
        personalBasicInfo = personalBasicInfoDAO.getParentId(id);
        MdicalExcusieInfo mdicalExcusieInfo =
            mdicalExcusieInfoDAO.getPersonalId_FK(personalBasicInfo.getN_PersonalId());

        PersonalityInfo personalityInfo =
            personalityInfoDAO.selectByPersonalityInfo(personalBasicInfo.getN_LoginId());

        HealthYgenericInfo healthYgenericInfo =
            healthYgenericInfoDAO.selectByPrimaryFk(personalBasicInfo.getN_LoginId());

        LoginInfo loginInfo = loginInfoDAO.selectByPrimaryKey(personalBasicInfo.getN_LoginId());

        personalityInfoDAO.deleteByPrimaryKey(personalityInfo.getN_PersonalityId());
        healthYgenericInfoDAO.deleteByPrimaryKey(healthYgenericInfo.getN_HealthYgenericId());

        mdicalExcusieInfoDAO.deleteMdicalExcusieInfo(mdicalExcusieInfo);
        AccountInfo accountInfo =
            accountInfoDAO.selectByPrimaryLoginInfo(personalBasicInfo.getN_LoginId());

        accountInfoDAO.deleteByPrimaryKey(accountInfo.getN_AccountId());
        count += personalBasicInfoDAO.deletePersonalBasicInfo(personalBasicInfo);

        loginInfoDAO.deleteByPrimaryKey(loginInfo.getN_LoginId());
      }
    }
    return count;
  }

  /**
   * 修改专家信息
   * 
   * @param p 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public Integer udpatePersonalBasicInfo(PersonalBasicInfo p) throws SQLException {
    return personalBasicInfoDAO.udpatePersonalBasicInfo(p);
  }

  /**
   * 跟据专家id查询
   * 
   * @param personalId 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public PersonalBasicInfo getPersonalId(Integer personalId) throws SQLException {
    return personalBasicInfoDAO.getPersonalId(personalId);
  }

  /**
   * 跟据个人id查询专家信息
   * 
   * @param personalId
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public PersonalBasicInfo getParentId(Integer personalId) throws SQLException {
    return personalBasicInfoDAO.getParentId(personalId);
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 个人基本信息实体
   * @return 返回值
   * @throws Exception
   */
  @Override
  public Page searchPageByPersonalBasicInfo(Page pageParam, PersonalBasicInfo vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new PersonalBasicInfo();
    }
    int totalNum = personalBasicInfoDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());

    pageParam.setDataList(personalBasicInfoDAO.selectPageByVo(vo));
    return pageParam;
  }

  // 条件查询个人信息
  @Override
public List<PersonalBasicInfo> queryPersonalInfoList(PersonalBasicInfo vo) throws Exception {
    return personalBasicInfoDAO.queryPersonalInfoList(vo);
  }

  /**
   * 添加个人信息
   * 
   * @param personalBasicInfo 个人实体
   * @param loginInfo 登录实体
   * @param personalityInfo 个人个性实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer insertPersonalBasicInfos(PersonalBasicInfo personalBasicInfo, LoginInfo loginInfo,
      PersonalityInfo personalityInfo, HealthYgenericInfo healthYgenericInfo,
      AccountInfo accountInfo) throws Exception {
    // 当前日期
    loginInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    // MD5加密
    loginInfo.setLoginPassword(MD5.md5crypt(loginInfo.getLoginPassword()));
    loginInfo.setN_CustomerTypeId(1);
    loginInfo.setN_Status(1);
    // 添加登录信息
    Integer n_LoginId = loginInfoDAO.insert(loginInfo);

    // 添加个人个性信息
    personalityInfo.setN_LoginId(n_LoginId);
    personalityInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    personalityInfo.setN_TotalIntegral(BigDecimal.ZERO);
    personalityInfo.setAmountConsume(BigDecimal.ZERO);
    Integer personalityInfoId = personalityInfoDAO.insert(personalityInfo);

    // 添加健康信息
    healthYgenericInfo.setN_LoginId(n_LoginId);
    healthYgenericInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    healthYgenericInfoDAO.insert(healthYgenericInfo);

    // 添加个人信息
    personalBasicInfo.setMobile(loginInfo.getMobile());
    personalBasicInfo.setN_LoginId(n_LoginId);
    personalBasicInfo.setEmail(loginInfo.getEmail());
    personalBasicInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    personalBasicInfo.setN_Status(1);

    // 添加账户信息
    accountInfo.setAccountLogin(loginInfo.getLoginAccount());
    accountInfo.setAddress(personalBasicInfo.getLocation());
    accountInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
    accountInfo.setEmail(loginInfo.getEmail());
    accountInfo.setMobile(loginInfo.getMobile());
    accountInfo.setN_CustomerTypeId(1);
    accountInfo.setN_Status(1);
    accountInfo.setName(personalBasicInfo.getName());
    accountInfo.setN_LoginId(n_LoginId);
    accountInfo.setAcconutId(personalBasicInfo.getCertificateNumber());
    // MD5加密
    accountInfo.setPaymentpwd(MD5.md5crypt(accountInfo.getPaymentpwd()));

    accountInfoDAO.insert(accountInfo);

    // 更新等级
    UserLevelDO userLevelDO = new UserLevelDO();
    userLevelDO.setLoginId(n_LoginId);
    userLevelDO.setCustomerTypeId(loginInfo.getN_CustomerTypeId());
    userLevelDO.setExpend(personalityInfo.getAmountConsume());

    userLevelService.searchUserLevelUpdateInfo(userLevelDO);

    // 更新头衔
    RankDO rankDO = new RankDO();
    rankDO.setPersonalityId(personalityInfoId);
    rankDO.setExpend(personalityInfo.getN_TotalIntegral());
    rankDO.setCustomerTypeId(loginInfo.getN_CustomerTypeId());
    rankService.updateRankName(rankDO);
    // 注册发放优惠券信息、
    this.changeCustomGrantToGive(n_LoginId);
    return personalBasicInfoDAO.insert(personalBasicInfo);
  }

  /**
   * 修改个人信息
   * 
   * @param personalBasicInfo 个人实体
   * @param loginInfo 登录实体
   * @param personalityInfo 个人个性实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer udpatePersonalBasicInfos(PersonalBasicInfo personalBasicInfo, LoginInfo loginInfo,
      PersonalityInfo personalityInfo, HealthYgenericInfo healthYgenericInfo,
      AccountInfo accountInfo) throws SQLException {
    // 当前日期
    loginInfo.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());

    loginInfo.setN_CustomerTypeId(1);
    // 修改登录信息
    loginInfoDAO.updateByPrimaryKeySelective(loginInfo);

    // 修改个人个性信息
    personalityInfo.setN_LoginId(loginInfo.getN_LoginId());
    personalityInfo.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());
    personalityInfoDAO.updateByPrimaryKeySelective(personalityInfo);

    // 修改健康信息
    healthYgenericInfo.setN_LoginId(loginInfo.getN_LoginId());
    healthYgenericInfo.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());
    healthYgenericInfoDAO.updateByPrimaryKeySelective(healthYgenericInfo);

    // 修改个人信息
    personalBasicInfo.setMobile(loginInfo.getMobile());
    personalBasicInfo.setN_LoginId(loginInfo.getN_LoginId());
    personalBasicInfo.setEmail(loginInfo.getEmail());
    personalBasicInfo.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());

    // 修改账户信息
    accountInfo.setAddress(personalBasicInfo.getLocation());
    accountInfo.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());
    accountInfo.setEmail(loginInfo.getEmail());
    accountInfo.setMobile(loginInfo.getMobile());
    accountInfo.setName(personalBasicInfo.getName());
    accountInfo.setAcconutId(personalBasicInfo.getCertificateNumber());

    accountInfoDAO.updateByPrimaryKeySelective(accountInfo);

    return personalBasicInfoDAO.udpatePersonalBasicInfo(personalBasicInfo);
  }

  /**
   * 根据商户类别客户查询商户客户的头衔
   * 
   * @return
   */
  @Override
  public List<Rank> queryPersonalRank(Integer customerTypeId) {
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
  public List<UserLevel> queryPersonalserLevel(Integer customerTypeId) {
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
   * 根据登录ID查询单个健康信息
   */
  @Override
  public HealthYgenericInfo selectByHealthYgenericInfo(Integer nLoginId) throws Exception {
    return healthYgenericInfoDAO.selectByPrimaryFk(nLoginId);
  }

  /**
   * 根据登录ID查询单个登录信息
   */
  @Override
  public LoginInfo selectByLoginInfo(Integer nLoginId) throws Exception {
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
   * 查询登录账号是否存在
   * 
   * @param loginAccount
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
   * 查询邮件地址
   * 
   * @param loginid
   * @return
   * @throws Exception
   */
  @Override
  public Integer checkLoginEmail(String email, Integer loginid) throws Exception {

    Integer rows = 0;
    UserInfoDO userInfoDO = new UserInfoDO();
    userInfoDO.setLoginId(loginid);
    userInfoDO.setEmail(email);
    rows = loginInfoDAO.countCheckLoginEmail(userInfoDO);
    return rows;

  }
  
  /**
   * 查询手机号码
   * 
   * @param loginid
   * @return
   * @throws Exception
   */
  @Override
  public Integer checkLoginMobile(String mobile, Integer loginid) throws Exception {

    Integer rows = 0;
    UserInfoDO userInfoDO = new UserInfoDO();
    userInfoDO.setLoginId(loginid);
    userInfoDO.setMobile(mobile);;
    rows = loginInfoDAO.countCheckLoginMobile(userInfoDO);
    return rows;

  }
  
  

  /**
   * 删除个人信息
   * 
   * @param personalIds 个人信息ID
   * @return
   * @throws SQLException
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer deletePersonalBasicInfoList(List<Integer> personalIds) throws Exception {
    Integer count = 0;
    if (ListUtils.isNotEmpty(personalIds)) {
      for (Integer id : personalIds) {
        PersonalBasicInfo personalBasicInfo = null;
        personalBasicInfo = personalBasicInfoDAO.getPersonalId(id);

        PersonalityInfo personalityInfo =
            personalityInfoDAO.selectByPersonalityInfo(personalBasicInfo.getN_LoginId());

        HealthYgenericInfo healthYgenericInfo =
            healthYgenericInfoDAO.selectByPrimaryFk(personalBasicInfo.getN_LoginId());

        LoginInfo loginInfo = loginInfoDAO.selectByPrimaryKey(personalBasicInfo.getN_LoginId());

        AccountInfo accountInfo =
            accountInfoDAO.selectByPrimaryLoginInfo(personalBasicInfo.getN_LoginId());

        accountInfoDAO.deleteByPrimaryKey(accountInfo.getN_AccountId());

        personalityInfoDAO.deleteByPrimaryKey(personalityInfo.getN_PersonalityId());

        healthYgenericInfoDAO.deleteByPrimaryKey(healthYgenericInfo.getN_HealthYgenericId());

        count += personalBasicInfoDAO.deletePersonalBasicInfo(personalBasicInfo);

        loginInfoDAO.deleteByPrimaryKey(loginInfo.getN_LoginId());
      }
    }
    return count;
  }


  /**
   * 查询个人客户的头衔
   * 
   * @return
   */
  @Override
  public List<Rank> queryByPersonalRank() {
    List<Rank> rankList = null;
    try {
      rankList = rankService.getCustomerTypeIdKey(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rankList;
  }

  /**
   * 查询个人客户的等级
   * 
   * @return
   */
  @Override
  public List<UserLevel> queryByPersonalUserLevel() {
    List<UserLevel> levelList = null;
    UserLevel userLevel = new UserLevel();
    userLevel.setN_customer_type_id(1);
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
      // 调用远程接口.
      // 查询发放注册类型优惠劵
      List<Coupon> list = couponRemoteService.selectCouponByType((long) 2);
      for (Coupon cn : list) {
        UserInfoDO userInfoDO = new UserInfoDO();
        userInfoDO.setLoginId(LoginId);
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

  @Override
  public Page searchPageByVoForRebate(Page pageParam, PersonalBasicInfo vo) throws SQLException {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new PersonalBasicInfo();
    }

    int totalNum = personalBasicInfoDAO.selectCountByPersonalBasicInfoForRebate(vo);
    pageParam.setRecordCount(totalNum);
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    List<PersonalBasicInfo> list = personalBasicInfoDAO.selectPageByPersonalBasicInfoForRebate(vo);
    List<PersonalBasicInfo> resultList = new ArrayList();
    for (PersonalBasicInfo p : list) {
      List<Address> addressList = addressDAO.queryListByLoginId(p.getN_LoginId());
      if (addressList != null) p.setAddressList(addressList);
      resultList.add(p);
    }

    pageParam.setDataList(resultList);
    return pageParam;
  }


 /* @Override
  public int syncPersonalBasicInfo2Base(List<String> lstAccountLogin) throws ServiceException {
    int result = 0;
    if (lstAccountLogin == null || lstAccountLogin.isEmpty()) {
      return 1;
    }

    JSONObject json = null;
    String pushId = null;
    try {
      List<SyncPeronalInfoVO> lstInfos =
          this.personalBasicInfoDAO.queryPersonalInfoByAccountLogin(lstAccountLogin);
      if (lstInfos != null && lstInfos.size() > 0) {
        for (SyncPeronalInfoVO tempInfo : lstInfos) {
          json = new JSONObject();
          json.put("cardNum", StringUtils.defaultIfBlank(tempInfo.getCardNum(), ""));
          json.put("accountLogin", StringUtils.defaultIfBlank(tempInfo.getAccountLogin(), ""));
          json.put("name", StringUtils.defaultIfBlank(tempInfo.getName(), ""));
          json.put("sex", StringUtils.defaultIfBlank(tempInfo.getSex(), ""));
          json.put("mobile", StringUtils.defaultIfBlank(tempInfo.getMobile(), ""));
          json.put("address", StringUtils.defaultIfBlank(tempInfo.getAddress(), ""));
          json.put("email", StringUtils.defaultIfBlank(tempInfo.getEmail(), ""));
          json.put("n_MaritalStatus",
              StringUtils.defaultIfBlank(String.valueOf(tempInfo.getN_MaritalStatus()), ""));
          json.put("d_Birthday",
              tempInfo.getD_Birthday() == null ? "" : birthFormat.format(tempInfo.getD_Birthday()));
          json.put("d_CreateDate", tempInfo.getD_CreateDate() == null
              ? ""
              : dateFormat.format(tempInfo.getD_CreateDate()));
          json.put("d_ModifyDate", tempInfo.getD_ModifyDate() == null
              ? ""
              : dateFormat.format(tempInfo.getD_ModifyDate()));
          pushId = baseDockService.pushBaseDockData(
              Integer.parseInt(ConfigurationUtil.getString("userIbport")),
              BaseDockType.USER_MODIFY_PUSH.getValue(), json.toJSONString());
        }
      }
      result = 1;
    } catch (Exception e) {
      logger.error("同步用户信息到总部发生异常：pushId:" + pushId + e.getMessage());
      throw new ServiceException(ErrorCode.SYNC_PERSONAL_INFO_ERROR,
          "同步用户信息到总部发生异常：" + e.getMessage());
    }
    return result;
  }*/

  @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int processBase2PersonalBasicInfo(String strJson) throws ServiceException {
    int result = -1;
    Map<String, String> userInfoMap = null;
    JSONObject jsonObject = null;
    String tempStr = null;
    Date tempDate = null;
    JSONObject jsonValue = null;
    Map<String, BigDecimal> userPrimaryKeyMap = null;
    try {
      jsonObject = JSONObject.parseObject(strJson);
      userInfoMap = new HashMap<String, String>();

      // 如果传过来的value是字符串那么尝试将键值为value的值转为JSON，
      // 如果无法转换则尝试直接获取valueJSON对象
      try {
        jsonValue = JSONObject.parseObject(jsonObject.getString("value"));
      } catch (Exception ex) {
        jsonValue = jsonObject.getJSONObject("value");
      }

      for (java.util.Map.Entry<String, Object> entry : jsonValue.entrySet()) {
        userInfoMap.put(entry.getKey(), (String) entry.getValue());
      }

      // 根据卡号查询用户的登录ID,个人信息ID，健康状况信息ID，账户信息ID
      userPrimaryKeyMap = loginInfoDAO.queryUserInfoPrimaryKeyByCardNum(userInfoMap.get("CardId"));

      // 判断是否存在卡号对应的用户信息,如果存在则进行更新
      if (userPrimaryKeyMap != null && userPrimaryKeyMap.get("N_LOGIN_ID") != null) {
        HealthYgenericInfo healthYgenericInfo = new HealthYgenericInfo();
        LoginInfo loginInfo = new LoginInfo();
        AccountInfo accountInfo = new AccountInfo();
        PersonalBasicInfo personalBasicInfo = new PersonalBasicInfo();

        healthYgenericInfo
            .setN_HealthYgenericId(userPrimaryKeyMap.get("N_HEALTH_YGENERIC_ID").intValue());
        loginInfo.setN_LoginId(userPrimaryKeyMap.get("N_LOGIN_ID").intValue());
        accountInfo.setAcconutId(userPrimaryKeyMap.get("N_ACCOUNT_ID").toPlainString());
        personalBasicInfo.setN_PersonalId(userPrimaryKeyMap.get("N_PERSONAL_ID").intValue());

        // 0：未婚 1：已婚 2：保密
        tempStr = userInfoMap.get("maritalStatus");
        if (StringUtils.isNotEmpty(tempStr)) {
          healthYgenericInfo.setN_MaritalStatus(Integer.parseInt(tempStr));
        }

        tempStr = userInfoMap.get("name");
        if (StringUtils.isNotEmpty(tempStr)) {
          personalBasicInfo.setName(tempStr);
        }

        // 用户系统0表示男，1表示女 ，总部系统 1:先生 0：女士
        tempStr = userInfoMap.get("sex");
        if (StringUtils.isNotEmpty(tempStr)) {
          personalBasicInfo.setSex(tempStr);
        }

        tempStr = userInfoMap.get("birthday");
        if (StringUtils.isNotEmpty(tempStr)) {
          personalBasicInfo.setD_Birthday(birthFormat.parse(tempStr));
        }

        tempStr = userInfoMap.get("mobile");
        if (StringUtils.isNotEmpty(tempStr)) {
          loginInfo.setMobile(tempStr);
        }

        tempStr = userInfoMap.get("email");
        if (StringUtils.isNotEmpty(tempStr)) {
          loginInfo.setEmail(tempStr);
          accountInfo.setEmail(tempStr);
        }

        tempStr = userInfoMap.get("address");
        if (StringUtils.isNotEmpty(tempStr)) {
          personalBasicInfo.setDetailedAddress(tempStr);
          accountInfo.setAddress(tempStr);
        }

        // 修改时间，如果修改时间为空，则取当前是时间更新用户信息
        tempStr = userInfoMap.get("endtime");
        if (StringUtils.isEmpty(tempStr)) {
          tempDate = Calendar.getInstance().getTime();
        } else {
          tempDate = dateFormat.parse(tempStr);
        }
        accountInfo.setD_ModifyDate(tempDate);
        loginInfo.setD_ModifyDate(tempDate);
        personalBasicInfo.setD_ModifyDate(tempDate);
        healthYgenericInfo.setD_ModifyDate(tempDate);

        // 跟新信息
        healthYgenericInfoDAO.updateByPrimaryKeySelective(healthYgenericInfo);
        personalBasicInfoDAO.udpatePersonalBasicInfo(personalBasicInfo);
        loginInfoDAO.updateByPrimaryKeySelective(loginInfo);
        accountInfoDAO.updateByPrimaryKeySelective(accountInfo);
      }
      result = 1;
    } catch (Exception e) {
      logger.error("接收总部会员信息同步异常：strJson:" + strJson + "，" + e.getMessage());
      throw new ServiceException(ErrorCode.SYNC_PERSONAL_INFO_ERROR,
          "同步用户信息到总部发生异常：" + e.getMessage());
    }
    // 返回
    return result;
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 个人基本信息实体
   * @return 返回值
   * @throws Exception
   */
  @Override
public Page selectPageByVo(Page pageParam, PersonalBasicInfo vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new PersonalBasicInfo();
    }

    int totalNum = personalBasicInfoDAO.selectPersonalBasicInfoCount(vo);
    pageParam.setRecordCount(totalNum);
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    pageParam.setDataList(personalBasicInfoDAO.selectPersonalBasicInfoByPage(vo));
    return pageParam;
  }
  
  
  public LoginInfoDAO getLoginInfoDAO() {
    return loginInfoDAO;
  }

  public void setLoginInfoDAO(LoginInfoDAO loginInfoDAO) {
    this.loginInfoDAO = loginInfoDAO;
  }

  public PersonalityInfoDAO getPersonalityInfoDAO() {
    return personalityInfoDAO;
  }

  public void setPersonalityInfoDAO(PersonalityInfoDAO personalityInfoDAO) {
    this.personalityInfoDAO = personalityInfoDAO;
  }

  public HealthYgenericInfoDAO getHealthYgenericInfoDAO() {
    return healthYgenericInfoDAO;
  }

  public void setHealthYgenericInfoDAO(HealthYgenericInfoDAO healthYgenericInfoDAO) {
    this.healthYgenericInfoDAO = healthYgenericInfoDAO;
  }

  public UserLevelDAO getUserLevelDAO() {
    return userLevelDAO;
  }

  public void setUserLevelDAO(UserLevelDAO userLevelDAO) {
    this.userLevelDAO = userLevelDAO;
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

  public BnesCustomerTypeDAO getBnesCustomerTypeDAO() {
    return bnesCustomerTypeDAO;
  }

  public void setBnesCustomerTypeDAO(BnesCustomerTypeDAO bnesCustomerTypeDAO) {
    this.bnesCustomerTypeDAO = bnesCustomerTypeDAO;
  }

  public MdicalExcusieInfoDAO getMdicalExcusieInfoDAO() {
    return mdicalExcusieInfoDAO;
  }

  public void setMdicalExcusieInfoDAO(MdicalExcusieInfoDAO mdicalExcusieInfoDAO) {
    this.mdicalExcusieInfoDAO = mdicalExcusieInfoDAO;
  }

  public PersonalBasicInfoDAO getPersonalBasicInfoDAO() {
    return personalBasicInfoDAO;
  }

  public void setPersonalBasicInfoDAO(PersonalBasicInfoDAO personalBasicInfoDAO) {
    this.personalBasicInfoDAO = personalBasicInfoDAO;
  }

  public AccountInfoDAO getAccountInfoDAO() {
    return accountInfoDAO;
  }

  public void setAccountInfoDAO(AccountInfoDAO accountInfoDAO) {
    this.accountInfoDAO = accountInfoDAO;
  }

  @Override
public PersonalBasicInfo getLogin(Integer login) throws SQLException {
    return personalBasicInfoDAO.getLogin(login);
  }

@Override
public PersonalBasicInfo getPersonalInfoByLogId(Integer loginId) throws SQLException {
    return personalBasicInfoDAO.getPersonalInfoByLogId(loginId);
}
}
