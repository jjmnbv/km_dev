package com.pltfm.remote.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.commons.page.Page;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.dao.BnesAcctTransListDAO;
import com.pltfm.app.dao.BnesAcctTransactionDAO;
import com.pltfm.app.dao.CommercialTenantBasicInfoDAO;
import com.pltfm.app.dao.HealthYgenericInfoDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.PersonalBasicInfoDAO;
import com.pltfm.app.dao.PersonalityInfoDAO;
import com.pltfm.app.dao.PromInvitedOrganizationsDAO;
import com.pltfm.app.dao.PromMembersInvitationListDAO;
import com.pltfm.app.dao.PromRewardAmountListDAO;
import com.pltfm.app.dao.PromRewardRuleDAO;
import com.pltfm.app.dao.ScoreInfoDAO;
import com.pltfm.app.dataobject.BnesAcctTransListDO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.map.SystemTypeEnumMap;
import com.pltfm.app.service.SaltInfoService;
import com.pltfm.app.util.Constants;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.RemoteInvoking;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.app.vobject.CommercialTenantBasicInfo;
import com.pltfm.app.vobject.Customer;
import com.pltfm.app.vobject.HealthYgenericInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.LoginInfoExample;
import com.pltfm.app.vobject.PersonalBasicInfo;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.PromInvitedOrganizations;
import com.pltfm.app.vobject.PromMembersInvitationList;
import com.pltfm.app.vobject.PromRewardAmountList;
import com.pltfm.app.vobject.PromRewardRule;
import com.pltfm.app.vobject.SaltInfo;
import com.pltfm.app.vobject.ScoreInfo;

import redis.clients.jedis.JedisCluster;


/**
 * 客户列表信息远程接口实现类
 *
 * @author cjm
 * @since 2013-8-7
 */
@SuppressWarnings("unchecked")
@Component(value = "customerRemoteService")
public class CustomerRemoteServiceImpl implements CustomerRemoteService {

    /**
     * 日志类
     */
    Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     * 商户信息DAO接口
     */
    @Resource(name = "commercialTenantBasicInfoDAO")
    private CommercialTenantBasicInfoDAO commercialTenantBasicInfoDAO;

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
     * 个性信息DAO接口
     */
    @Resource(name = "personalityInfoDAO")
    private PersonalityInfoDAO personalityInfoDAO;
    /**
     * 积分DAO
     */
    @Resource(name = "scoreInfoDAO")
    private ScoreInfoDAO scoreInfoDAO;


    /**
     * 健康信息DAO接口
     */
    @Resource(name = "healthYgenericInfoDAO")
    private HealthYgenericInfoDAO healthYgenericInfoDAO;

    /**
     * 账户信息DAO接口
     */
    @Resource(name = "accountInfoDAO")
    private AccountInfoDAO accountInfoDAO;

    /**
     * 邀请机构认证接口
     */
    @Resource(name = "promInvitedOrganizationsDAO")
    private PromInvitedOrganizationsDAO promInvitedOrganizationsDAO;

    /**
     * 邀请记录接口
     */
    @Resource(name = "promMembersInvitationListDAO")
    private PromMembersInvitationListDAO promMembersInvitationListDAO;

    /**
     * 奖励规则接口
     */
    @Resource(name = "promRewardRuleDAO")
    private PromRewardRuleDAO promRewardRuleDAO;

    /**
     * 奖励记录接口
     */
    @Resource(name = "promRewardAmountListDAO")
    private PromRewardAmountListDAO promRewardAmountListDAO;

    /**
     * 余额变动流水接口
     */
    @Resource(name = "bnesAcctTransactionDAO")
    private BnesAcctTransactionDAO bnesAcctTransactionDAO;

    /**
     * 余额变化记录
     */
    @Resource(name = "bnesAcctTransListDAO")
    private BnesAcctTransListDAO bnesAcctTransListDAO;

    @Resource
    private SaltInfoService saltInfoService;

    @Resource
    private JedisCluster jedis;

    private static int DEFAULT_PAGE_SIZE = 20;

    // 修改用户余额(loginId)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void UpdateUserBalance(BnesAcctTransactionQuery bnesAcctTransactionQuery, int systemType)
            throws Exception {
        log.info("-----" + SystemTypeEnumMap.getValue(systemType) + "调用分用户修改余额接口------");
        if (bnesAcctTransactionQuery.getAmount().compareTo(BigDecimal.ZERO) != 0) {
            // 获取账户余额信息
            AccountInfo accountInfo =
                    accountInfoDAO.selectByPrimaryLoginInfo(bnesAcctTransactionQuery.getLoginId());
            if (accountInfo == null) {
                log.info("账户为空，传入登录ID：" + bnesAcctTransactionQuery.getLoginId());
            } else {
                BigDecimal beforeAmount = BigDecimal.ZERO;
                BigDecimal beforeAmountAccount = BigDecimal.ZERO;
                if (accountInfo.getAmountAvlibal() == null) {
                    accountInfo.setAmountAvlibal(BigDecimal.ZERO);
                }
                beforeAmount = accountInfo.getAmountAvlibal();
                beforeAmountAccount = accountInfo.getN_AccountAmount();
                if (bnesAcctTransactionQuery.getAmount().doubleValue() < 0
                        && (beforeAmount.add(bnesAcctTransactionQuery.getAmount()))
                                .doubleValue() < 0) {
                    // 如果余额小于0, 根据登陆ID获取商户信息
                    CommercialTenantBasicInfo commericalInfo = commercialTenantBasicInfoDAO
                            .selectByPrimaryLoginInfo(bnesAcctTransactionQuery.getLoginId());
                    // 判断账户类型，如果该登陆ID不是商户则余额调整为0,如果是商家用户，则允许为负值
                    if (commericalInfo == null) {
                        // 如果当前余额小于需要减去的金额，则需减去的金额等于余额（为保证减去金额后，余额不为负数）
                        bnesAcctTransactionQuery.setAmount(BigDecimal.ZERO.subtract(beforeAmount));
                    }
                }
                BigDecimal afterAmount = beforeAmount.add(bnesAcctTransactionQuery.getAmount());
                BigDecimal afterAmountAccount =
                        beforeAmountAccount.add(bnesAcctTransactionQuery.getAmount());
                accountInfo.setAmountAvlibal(afterAmount);
                accountInfo.setN_AccountAmount(afterAmountAccount);
                accountInfo.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                accountInfoDAO.updateByPrimaryKeySelective(accountInfo);
                // 生成交易流水
                bnesAcctTransactionQuery.setAccountId(accountInfo.getN_AccountId());
                bnesAcctTransactionQuery.setStatus(1);
                String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
                bnesAcctTransactionQuery.setAccountNumber(number);
                // 添加创建时间
                bnesAcctTransactionQuery
                        .setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                bnesAcctTransactionQuery.setCreatedId(bnesAcctTransactionQuery.getLoginId());
                bnesAcctTransactionQuery.setModifieId(bnesAcctTransactionQuery.getLoginId());
                bnesAcctTransactionQuery
                        .setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                int accountTransactionId = bnesAcctTransactionDAO
                        .insertBnesAcctTransactionDO(bnesAcctTransactionQuery);
                // 添加余额交易变化记录
                BnesAcctTransListDO bnesAcctTransListDO = new BnesAcctTransListDO();
                bnesAcctTransListDO.setAccountId(Integer.valueOf(accountInfo.getN_AccountId()));
                bnesAcctTransListDO.setAccountTransactionId(accountTransactionId);
                bnesAcctTransListDO.setBeforeAmount(beforeAmount);
                bnesAcctTransListDO.setAfterAmount(afterAmount);
                bnesAcctTransListDO.setMoneyAmount(bnesAcctTransactionQuery.getAmount());
                bnesAcctTransListDO.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                bnesAcctTransListDO.setCreatedId(bnesAcctTransactionQuery.getLoginId());
                bnesAcctTransListDO.setModifieId(bnesAcctTransactionQuery.getLoginId());
                bnesAcctTransListDO.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                bnesAcctTransListDAO.insertBnesAcctTransListDO(bnesAcctTransListDO);
            }
        }
    }

    /**
     * 分销邀请返利业务处理
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userReward(BigDecimal membersInvitationListId, String ruleCode, Integer loginId,
            Integer nLoginId) throws Exception {
        if (loginId != null) {
            // 获取当前被邀请者用户信息
            AccountInfo account = accountInfoDAO.selectByPrimaryLoginInfo(nLoginId);
            String content = "";
            if (Integer.parseInt(ruleCode) == 1) {
                content = "注册奖励，被邀请者会员：" + account.getAccountLogin();
            } else if (Integer.valueOf(ruleCode) == 2) {
                content = "验证手机奖励，被邀请者会员：" + account.getAccountLogin();
            } else {
                content = "首次购物奖励，被邀请者会员：" + account.getAccountLogin();
            }
            // 获取邀请奖励规则
            PromRewardRule promRewardRule = new PromRewardRule();
            promRewardRule.setRuleCode(ruleCode);
            promRewardRule = promRewardRuleDAO.selectByPrimaryKey(promRewardRule);
            // 添加奖励事件
            PromRewardAmountList promRewardAmountList = new PromRewardAmountList();
            promRewardAmountList.setMembersInvitationListId(membersInvitationListId);
            promRewardAmountList.setRewardRuleId(promRewardRule.getRewardRuleId());
            promRewardAmountList.setRewardAmountList(promRewardRule.getRewardAmount());
            promRewardAmountList.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
            promRewardAmountListDAO.insert(promRewardAmountList);
            // 奖励大于0执行下面业务
            BigDecimal amout = promRewardRule.getRewardAmount();
            if (amout.doubleValue() > Double.valueOf(0.00)) {
                // 获取邀请者账户余额信息
                AccountInfo accountInfo = accountInfoDAO.selectByPrimaryLoginInfo(loginId);
                BigDecimal beforeAmount = BigDecimal.ZERO;
                BigDecimal beforeAmountAccount = BigDecimal.ZERO;
                if (accountInfo.getAmountAvlibal() != null) {
                    beforeAmount = accountInfo.getAmountAvlibal();
                    beforeAmountAccount = accountInfo.getN_AccountAmount();
                } else {
                    beforeAmount = BigDecimal.ZERO;
                    beforeAmountAccount = BigDecimal.ZERO;
                }
                // 修改邀请者余额
                BigDecimal afterAmount = beforeAmount.add(amout);
                BigDecimal afterAmountAccount = beforeAmountAccount.add(amout);
                accountInfo.setAmountAvlibal(afterAmount);
                accountInfo.setN_AccountAmount(afterAmountAccount);
                accountInfo.setD_ModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                accountInfoDAO.updateByPrimaryKeySelective(accountInfo);
                // 生成余额变化流水
                BnesAcctTransactionQuery bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
                bnesAcctTransactionQuery.setAccountId(accountInfo.getN_AccountId());
                bnesAcctTransactionQuery.setType(Constants.RANSACTION_TYPE_INVITATION_REWARD);
                bnesAcctTransactionQuery.setContent(content);
                bnesAcctTransactionQuery.setAmount(amout);
                bnesAcctTransactionQuery.setStatus(1);
                String number = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
                bnesAcctTransactionQuery.setAccountNumber(number);
                // 添加创建时间
                bnesAcctTransactionQuery
                        .setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                bnesAcctTransactionQuery.setCreatedId(loginId);
                // 添加变更后插入流水
                int accountTransactionId = bnesAcctTransactionDAO
                        .insertBnesAcctTransactionDO(bnesAcctTransactionQuery);
                BnesAcctTransListDO bnesAcctTransListDO = new BnesAcctTransListDO();
                bnesAcctTransListDO.setAccountId(Integer.valueOf(accountInfo.getN_AccountId()));
                bnesAcctTransListDO.setAccountTransactionId(accountTransactionId);
                bnesAcctTransListDO.setBeforeAmount(beforeAmount);
                bnesAcctTransListDO.setAfterAmount(afterAmount);
                bnesAcctTransListDO.setMoneyAmount(amout);
                bnesAcctTransListDO.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                bnesAcctTransListDAO.insertBnesAcctTransListDO(bnesAcctTransListDO);
            }
        }
    }

    /**
     * 受邀用户用户首次购物分销业务处理
     *
     * @return -1:异常，0：非受邀用户；1：奖励成功
     */
    @Override
    public int userFisrtShop(Integer loginId) throws Exception {
        int count = 0;
        // 判断是否是受邀用户
        if (loginId != null) {
            try {
                // 判断是否是受邀者
                PromMembersInvitationList promMembersInvitationList =
                        new PromMembersInvitationList();
                promMembersInvitationList.setInviteesId(new BigDecimal(loginId));
                promMembersInvitationList =
                        promMembersInvitationListDAO.selectByPrimaryKey(promMembersInvitationList);
                // 受邀用户
                if (promMembersInvitationList != null) {
                    // 邀请返利相关业务处理
                    userReward(promMembersInvitationList.getMembersInvitationListId(),
                            Constants.RULECODE_FIRSTBUY,
                            promMembersInvitationList.getInvitationId().intValue(), loginId);
                }
                count = 1;
            } catch (Exception e) {
                count = -1;
                log.error(loginId + "：首次购物分销返利异常" + e.getMessage(), e);
                throw e;
            }
        }
        return count;
    }

    /**
     * 分销邀请版本手机验证
     *
     * @param loginInfo 登录信息实体
     * @return 0：验证失败，1：成功;-1:异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int userMobileVerification(LoginInfo loginInfo) throws Exception {
        int rows = 0;
        try {
            log.info("验证手机传入参数uId：" + loginInfo.getN_LoginId() + "---参数手机号："
                    + loginInfo.getMobile());
            // 判断是否是首次验证手机
            LoginInfo info = loginInfoDAO.selectByPrimaryKey(loginInfo.getN_LoginId());
            // 首次验证处理
            if (StringUtils.isBlank(info.getMobile())) {
                // 判断是否是受邀者
                PromMembersInvitationList promMembersInvitationList =
                        new PromMembersInvitationList();
                promMembersInvitationList.setInviteesId(new BigDecimal(loginInfo.getN_LoginId()));
                promMembersInvitationList =
                        promMembersInvitationListDAO.selectByPrimaryKey(promMembersInvitationList);
                // 受邀者 需要首次增加验证奖励业务
                if (promMembersInvitationList != null
                        && StringUtils.isBlank(promMembersInvitationList.getVerificationMobile())) {
                    // 修改邀请列表 添加手机验证字段属性
                    promMembersInvitationList.setVerificationMobile(loginInfo.getMobile());
                    promMembersInvitationListDAO
                            .updateByPrimaryKeySelective(promMembersInvitationList);
                    // 邀请返利相关业务处理
                    userReward(promMembersInvitationList.getMembersInvitationListId(),
                            Constants.RULECODE_VERIFICATION,
                            promMembersInvitationList.getInvitationId().intValue(),
                            loginInfo.getN_LoginId());
                }
                // 赠送积分
                /*
                 * 删除 userGrowingService.updateUserScoreInfo("RU0003", 1, info.getLoginAccount(),
                 * new HashMap());
                 */
            }
            rows = updateByPrimaryKeySelective(loginInfo);
            // 添加修改account_info的功能
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setN_LoginId(loginInfo.getN_LoginId());
            /*
             * accountInfo.setEmail(loginInfo.getEmail());
             * accountInfo.setAccountLogin(loginInfo.getLoginAccount());
             */
            accountInfo.setMobile(loginInfo.getMobile());
            accountInfoDAO.updateByLoginId(accountInfo);
        } catch (Exception e) {
            rows = -1;
            log.error(loginInfo.getN_LoginId() + "验证手机异常" + e.getMessage(), e);
            throw e;
        }
        return rows;
    }

    /**
     * 分销邀请链接注册登录
     *
     * @param loginInfo 登录信息实体
     * @param type 1、产品 2、订单 3、B2B 4、促销
     * @param invitationId 邀请者Id
     * @param organiCode 邀请机构
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> registerRewardLoginInfo(LoginInfo loginInfo, Integer type,
            String invitationId, String organiCode) throws Exception {
        // 邀请注册登录接口沿用正常注册接口
        Map<String, String> messages = registerLoginInfo(loginInfo, type);
        // 注册成功执行邀请奖励相关操作
        if (StringUtils.isNotBlank(messages.get("loginId"))) {
            try {
                // 添加邀请记录
                PromMembersInvitationList promMembersInvitationList =
                        new PromMembersInvitationList();
                PromInvitedOrganizations promInvitedOrganizations = new PromInvitedOrganizations();
                if (StringUtils.isNotBlank(organiCode)) {
                    promInvitedOrganizations.setOrganiCode(organiCode);
                    // 认证邀请机构
                    promInvitedOrganizations = promInvitedOrganizationsDAO
                            .selectByPrimaryKey(promInvitedOrganizations);
                    if (promInvitedOrganizations != null && promInvitedOrganizations
                            .getOrgantState().equals(Constants.ORGANISTATETURE)) {
                        // 属于认证机构并且机构有效 获取机构对应ID
                        promMembersInvitationList.setInvitedOrganizationsId(
                                promInvitedOrganizations.getInvitedOrganizationsId());
                    }
                }
                if (StringUtils.isNotBlank(invitationId)) {
                    if (promMembersInvitationList.getInvitedOrganizationsId() == null) {
                        // 设置当前邀请人变成上级的受邀请人(邀请记录)
                        promMembersInvitationList.setInviteesId(new BigDecimal(invitationId));
                        PromMembersInvitationList promMembersInvitationListOld =
                                promMembersInvitationListDAO
                                        .selectByPrimaryKey(promMembersInvitationList);
                        // 上一级也不存在机构 邀请来源为个人
                        if (promMembersInvitationListOld != null && promMembersInvitationListOld
                                .getInvitedOrganizationsId() != null) {
                            // 认证上级邀请机构是否有效
                            promInvitedOrganizations.setOrganiCode(promMembersInvitationListOld
                                    .getInvitedOrganizationsId().toString());
                            promInvitedOrganizations = promInvitedOrganizationsDAO
                                    .selectByPrimaryKey(promInvitedOrganizations);
                            if (promInvitedOrganizations != null && promInvitedOrganizations
                                    .getOrgantState().equals(Constants.ORGANISTATETURE)) {
                                promMembersInvitationList.setInvitedOrganizationsId(
                                        promMembersInvitationListOld.getInvitedOrganizationsId());
                            }
                        }
                    }
                    // 邀请者登陆ID
                    promMembersInvitationList.setInvitationId(new BigDecimal(invitationId));
                }
                if (StringUtils.isNotBlank(invitationId)
                        || promMembersInvitationList.getInvitedOrganizationsId() != null) {
                    // 受邀者登陆ID
                    promMembersInvitationList
                            .setInviteesId(new BigDecimal(messages.get("loginId")));
                    // 创建时间
                    promMembersInvitationList
                            .setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                    BigDecimal membersInvitationListId =
                            promMembersInvitationListDAO.insert(promMembersInvitationList);
                    // 邀请返利相关业务处理
                    userReward(membersInvitationListId, Constants.RULECODE_REGIST,
                            Integer.valueOf(invitationId),
                            Integer.valueOf(messages.get("loginId")));
                }
            } catch (Exception e) {
                log.error(messages.get("loginId") + "分销返利异常" + e.getMessage(), e);
            }
        }

        return messages;
    }

    /**
     * 按客户信息条件查询客户信息
     *
     * @param customer 客户信息条件
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public Customer selectByCustomer(Customer customer, Integer type) throws Exception {
        Customer cust = null;
        if (customer != null) {
            if (customer.getN_PersonalId() != null) {
                cust = personalBasicInfoDAO.selectPageByCustomer(customer);
            }
            if (customer.getN_CommercialTenantId() != null) {
                cust = commercialTenantBasicInfoDAO.selectByCustomer(customer);
            }
        }
        log.warn(RemoteInvoking.remoteInvokingType(type));
        return cust;
    }

    /**
     * 注册登录信息
     *
     * @param loginInfo 登录信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> registerLoginInfo(LoginInfo loginInfo, Integer type)
            throws Exception {
        Map<String, String> messages = new HashMap<String, String>();
        long startTime = System.currentTimeMillis();
        if (loginInfo != null) {
            try {
                String lockKey = ("JedisLock_".concat(loginInfo.getMobile())).intern();
                Long i = jedis.setnx(lockKey, "1");
                if (i > 0) {
                    jedis.expire(lockKey, 60 * 60 * 1); // 缓存过期 1小时
                    /* if (redisTemplate.tryLock(loginInfo.getLoginAccount())) { */

                    log.info("注册消耗时间1:" + (System.currentTimeMillis() - startTime) + "毫秒");
                    LoginInfoExample example = new LoginInfoExample();
                    example.createCriteria().andLoginAccountEqualTo(loginInfo.getLoginAccount());
                    List<LoginInfo> list = loginInfoDAO.selectByExample(example);
                    if (list != null && list.size() > 0) {
                        messages.put("loginAccount", "loginAccount:登录账号已存在");
                    }
                    if (loginInfo.getN_CustomerTypeId() == null) {
                        messages.put("n_CustomerTypeId", "n_CustomerTypeId:请输入注册客户类别");

                    }
                    if (messages.size() > 0) {
                        return messages;
                    }

                    // 启用状态
                    loginInfo.setN_Status(1);
                    // 创建日期
                    loginInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
                    String Email = loginInfo.getEmail();
                    loginInfo.setEmail("");
                    String mobile = loginInfo.getMobile();

                    // 密码二次加密
                    // Map<String,String> saltMap =
                    // StringUtil.passwordTwiceEncryption(loginInfo.getLoginPassword());
                    // loginInfo.setLoginPassword(saltMap.get("twicePassword"));
                    Integer loginId = loginInfoDAO.insert(loginInfo);

                    // 密码加盐信息
                    SaltInfo saltInfo = new SaltInfo();
                    saltInfo.setLoginId(Long.valueOf(loginId));
                    saltInfo.setLoginSalt(loginInfo.getLoginSalt());
                    saltInfo.setPaySalt(loginInfo.getPaySalt());
                    this.saltInfoService.insertSaltInfo(saltInfo);

                    // 增加账户信息
                    AccountInfo record = new AccountInfo();
                    record.setN_LoginId(loginId);
                    record.setAccountLogin(loginInfo.getLoginAccount());
                    record.setN_CustomerTypeId(loginInfo.getN_CustomerTypeId());
                    record.setMobile(mobile);
                    record.setEmail(Email);
                    record.setN_Status(1);
                    record.setRegister_Device(loginInfo.getRegister_Device());
                    record.setRegister_Platfrom(loginInfo.getRegister_Platfrom());
                    Date cdate = DateTimeUtils.getCalendarInstance().getTime();
                    record.setD_CreateDate(cdate);
                    record.setD_ModifyDate(cdate);
                    accountInfoDAO.insert(record);
                    // 增加基本信息
                    PersonalBasicInfo p = new PersonalBasicInfo();
                    p.setN_LoginId(loginId);
                    personalBasicInfoDAO.insert(p);
                    // 增加个性化信息
                    PersonalityInfo pi = new PersonalityInfo();
                    pi.setN_LoginId(loginId);
                    pi.setLastYear_Amount(0.00);
                    personalityInfoDAO.insert(pi);
                    // 增加健康信息
                    HealthYgenericInfo healthYgenericInfo = new HealthYgenericInfo();
                    healthYgenericInfo.setN_LoginId(loginId);
                    if (healthYgenericInfo.getN_MaritalStatus() == null) {
                        healthYgenericInfo.setN_MaritalStatus(2);
                    }
                    if (healthYgenericInfo.getBloodType() == null) {
                        healthYgenericInfo.setBloodType("0");
                    }
                    healthYgenericInfoDAO.insertHealthInfo(healthYgenericInfo);

                    messages.put("success", "注册成功，主键值：" + loginId);
                    messages.put("loginId", "" + loginId);
                    log.info("注册消耗时间2:" + (System.currentTimeMillis() - startTime) + "毫秒");

                    return messages;
                } else {
                    log.info("key:{},加锁失败，申请会员缓存正在创建,请稍候在试。", loginInfo.getMobile());
                    return messages;
                }
            } finally {
                log.info("注册消耗时间4:" + (System.currentTimeMillis() - startTime) + "毫秒");
            }
        } else {
            messages.put("loginInfo", "loginInfo:请输入登录信息");
            return messages;
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfoDO selectUserInfoByLoginId(Long loginId) {
        UserInfoDO userInfoDO = null;
        try {
            userInfoDO = loginInfoDAO.queryUserByLoginId(loginId);
        } catch (SQLException e) {
            return null;
        }
        return userInfoDO;
    }

    /**
     * 注册个人信息
     *
     * @param personalBasicInfo 个人信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> registerPersonal(PersonalBasicInfo personalBasicInfo, Integer type)
            throws Exception {
        Map<String, String> messages = new HashMap<String, String>();
        if (personalBasicInfo != null) {
            if (personalBasicInfo.getName() != null) {
                if (personalBasicInfo.getName().trim().length() > 16) {
                    messages.put("name", "请输入合法的姓名");
                }
            }

            if (personalBasicInfo.getSex() == null) {
                messages.put("sex", "请输入合法的性别");
            }

            if (personalBasicInfo.getN_Age() != null) {
                if (personalBasicInfo.getN_Age() < 0 || personalBasicInfo.getN_Age() > 100) {
                    messages.put("n_Age", "请输入合法的年龄");
                }
            }
            if (personalBasicInfo.getMobile() != null) {
                // 手机正则表达式
                Pattern mobilePattern =
                        Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

                Matcher mobileMatcher = mobilePattern.matcher(personalBasicInfo.getMobile());

                if (!mobileMatcher.matches()) {
                    messages.put("mobile", "请输入合法的手机号码");
                }
            }

            if (personalBasicInfo.getEmail() != null) {
                // 邮箱正则表达式
                Pattern emailPattern = Pattern.compile(
                        "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$");

                Matcher emailMatcher = emailPattern.matcher(personalBasicInfo.getEmail());

                if (!emailMatcher.matches()) {
                    messages.put("email", "请输入合法的邮箱");
                }
            }

            if (personalBasicInfo.getN_CertificateType() == null) {
                messages.put("n_CertificateType", "请输入合法的证件类型");
            }

            if (personalBasicInfo.getCertificateNumber() != null) {
                // 证件号码正则表达式
                Pattern certificatePattern = Pattern.compile("^(\\w){0,30}$");

                Matcher certificateMatcher =
                        certificatePattern.matcher(personalBasicInfo.getCertificateNumber());

                if (!certificateMatcher.matches()) {
                    messages.put("certificateNumber", "请输入合法的证件号码");
                }
            }

            if (personalBasicInfo.getLocation() != null) {
                if (personalBasicInfo.getLocation().trim().length() > 16) {
                    messages.put("location", "请输入合法的所在地");
                }
            }

            if (personalBasicInfo.getHometownLocation() != null) {
                if (personalBasicInfo.getHometownLocation().trim().length() > 16) {
                    messages.put("hometownLocation", "请输入合法的故乡所在地");
                }
            }

            if (personalBasicInfo.getEducationalStatus() != null) {
                if (personalBasicInfo.getName().trim().length() > 16) {
                    messages.put("educationalStatus", "请输入合法的教育状况");
                }
            }

            if (personalBasicInfo.getWorkUnit() != null) {
                if (personalBasicInfo.getWorkUnit().trim().length() > 16) {
                    messages.put("workUnit", "请输入合法的工作单位");
                }
            }

            if (personalBasicInfo.getProfessionType() != null) {
                if (personalBasicInfo.getProfessionType().trim().length() > 16) {
                    messages.put("professionType", "请输入合法的职业类型");
                }
            }

            if (personalBasicInfo.getProfession() != null) {
                if (personalBasicInfo.getProfession().trim().length() > 16) {
                    messages.put("profession", "请输入合法的职业");
                }
            }

            if (personalBasicInfo.getN_AnnualIncome() != null) {
                // 年收入正则表达式
                Pattern annualIncomePattern = Pattern.compile("^\\d{18}$");

                Matcher annualIncomeMatcher = annualIncomePattern
                        .matcher(personalBasicInfo.getN_AnnualIncome().toString());

                if (!annualIncomeMatcher.matches()) {
                    messages.put("n_AnnualIncome", "请输入合法的年收入");
                }
            }

            if (messages.size() > 0) {
                return messages;
            }

            // 添加个人信息
            personalBasicInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
            personalBasicInfo.setN_Status(1);

            personalBasicInfoDAO.insert(personalBasicInfo);

            log.warn(RemoteInvoking.remoteInvokingType(type));

        } else {
            messages.put("personalBasicInfo", "请输入个人信息");
            return messages;
        }

        return null;
    }

    /**
     * 注册商户信息
     *
     * @param commercialTenantBasicInfo 商户信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> registerCommercial(
            CommercialTenantBasicInfo commercialTenantBasicInfo, Integer type) throws Exception {
        Map<String, String> messages = new HashMap<String, String>();
        if (commercialTenantBasicInfo != null) {
            if (commercialTenantBasicInfo.getContactsName() != null) {
                if (commercialTenantBasicInfo.getContactsName().trim().length() > 16) {
                    messages.put("contactsName", "请输入合法的联系人姓名");
                }
            }

            if (commercialTenantBasicInfo.getContactsDepartment() != null) {
                if (commercialTenantBasicInfo.getContactsDepartment().trim().length() > 16) {
                    messages.put("contactsDepartment", "请输入合法的联系人所在部门");
                }
            }

            if (commercialTenantBasicInfo.getFixedPhone() != null) {
                if (commercialTenantBasicInfo.getFixedPhone().trim().length() > 16) {
                    messages.put("fixedPhone", "请输入合法的固定电话");
                }
            }

            if (commercialTenantBasicInfo.getMobile() != null) {
                // 手机正则表达式
                Pattern mobilePattern =
                        Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

                Matcher mobileMatcher =
                        mobilePattern.matcher(commercialTenantBasicInfo.getMobile());

                if (!mobileMatcher.matches()) {
                    messages.put("mobile", "请输入合法的手机号码");
                }

            }

            if (commercialTenantBasicInfo.getContactsEmail() != null) {
                // 邮箱正则表达式
                Pattern emailPattern = Pattern.compile(
                        "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$");

                Matcher emailMatcher =
                        emailPattern.matcher(commercialTenantBasicInfo.getContactsEmail());

                if (!emailMatcher.matches()) {
                    messages.put("contactsEmail", "请输入合法的邮箱");
                }

            }

            if (commercialTenantBasicInfo.getCorporateName() != null) {
                if (commercialTenantBasicInfo.getCorporateName().trim().length() > 16) {
                    messages.put("corporateName", "请输入合法的联系人姓名");
                }
            }

            if (commercialTenantBasicInfo.getCorporateLocation() != null) {
                if (commercialTenantBasicInfo.getCorporateLocation().trim().length() > 16) {
                    messages.put("corporateLocation", "请输入合法的公司所在地");
                }
            }

            if (commercialTenantBasicInfo.getCorporateProperty() != null) {
                if (commercialTenantBasicInfo.getCorporateProperty().trim().length() > 16) {
                    messages.put("corporateProperty", "请输入合法的公司性质");
                }
            }

            if (commercialTenantBasicInfo.getN_EnterpriseNumberOfPeople() != null) {
                // 企业人数正则表达式
                Pattern peoplePattern = Pattern.compile("^\\d{18}$");

                Matcher peopleMatcher = peoplePattern.matcher(
                        commercialTenantBasicInfo.getN_EnterpriseNumberOfPeople().toString());

                if (!peopleMatcher.matches()) {
                    messages.put("n_EnterpriseNumberOfPeople", "请输入合法的企业人数");
                }
            }

            if (commercialTenantBasicInfo.getPostalcode() != null) {
                // 邮政编码正则表达式
                Pattern postalcodePattern = Pattern.compile("^\\d{16}$");

                Matcher postalcodeMatcher = postalcodePattern.matcher(
                        commercialTenantBasicInfo.getN_EnterpriseNumberOfPeople().toString());

                if (!postalcodeMatcher.matches()) {
                    messages.put("postalcode", "请输入合法的邮政编码");
                }

            }

            if (commercialTenantBasicInfo.getUploadBusinessLicencePictur() != null) {
                int pictur =
                        commercialTenantBasicInfo.getUploadBusinessLicencePictur().indexOf(".");
                if (pictur > -1) {
                    String uplopad = commercialTenantBasicInfo.getUploadBusinessLicencePictur()
                            .trim().substring(0, commercialTenantBasicInfo
                                    .getUploadBusinessLicencePictur().indexOf("."));

                    if (".jpg".equals(uplopad) || ".gif".equals(uplopad) || ".png".equals(uplopad)
                            || ".bmp".equals(uplopad)) {

                    } else {
                        messages.put("uploadBusinessLicencePictur", "请输入合法的上传营业执照图片");
                    }
                }
            }

            if (commercialTenantBasicInfo.getOrganizationCode() != null) {
                if (commercialTenantBasicInfo.getOrganizationCode().trim().length() > 16) {
                    messages.put("organizationCode", "请输入合法的组织代码");
                }
            }

            if (commercialTenantBasicInfo.getEnterpriseRealmName() != null) {
                if (commercialTenantBasicInfo.getEnterpriseRealmName().trim().length() > 16) {
                    messages.put("enterpriseRealmName", "请输入合法的企业法定代表人");
                }
            }

            if (commercialTenantBasicInfo.getEnterpriseLegalRepresentativ() != null) {
                if (commercialTenantBasicInfo.getEnterpriseLegalRepresentativ().trim()
                        .length() > 16) {
                    messages.put("enterpriseLegalRepresentativ", "请输入合法的企业域名");
                }
            }
            if (messages.size() > 0) {
                return messages;
            }

            // 添加商户客户信息
            commercialTenantBasicInfo
                    .setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
            commercialTenantBasicInfo.setN_EnterpriseStatus(1);
            commercialTenantBasicInfoDAO.insert(commercialTenantBasicInfo);
            log.warn(RemoteInvoking.remoteInvokingType(type));

        } else {
            messages.put("personalBasicInfo", "请输入个人信息");
            return messages;
        }

        return null;
    }

    /**
     * 通过客户基本信息访问类查询客户信息
     *
     * @param userInfoDO 客户基本信息数据访问实体
     * @throws SQLException 异常
     */
    @Override
    public List queryBasicUserInfo(UserInfoDO userInfoDO) throws SQLException {
        return loginInfoDAO.queryBasicUserInfo(userInfoDO);
    }

    /**
     * 通过客户基本信息访问类查询客户信息总数
     *
     * @param userInfoDO 客户基本信息数据访问实体
     * @throws SQLException 异常
     */
    @Override
    public Integer byCountBasicUserInfo(UserInfoDO userInfoDO) throws SQLException {
        return loginInfoDAO.byCountBasicUserInfo(userInfoDO);
    }

    /**
     * 通过登录id 查询客户基本信息
     *
     * @param loginId 登录主键
     * @throws SQLException 异常信息
     */
    @Override
    public UserInfoDO selectByPrimaryKey(Integer loginId) throws SQLException {
        UserInfoDO userInfoDO = new UserInfoDO();
        userInfoDO.setLoginId(loginId);
        return loginInfoDAO.selectByLoginId(userInfoDO);
    }

    /**
     * 通过客户等级主键查询客户集合信息
     *
     * @param levelId 客户等级主键
     * @return 客户信息集合
     * @throws SQLException 异常
     */
    @Override
    public List selectByUserLevelId(Integer levelId) throws SQLException {
        UserInfoDO userInfoDO = new UserInfoDO();
        userInfoDO.setLevelId(levelId);
        return loginInfoDAO.selectByUserLevelId(userInfoDO);
    }


    /**
     * 添加个人个性信息
     *
     * @param record 个人个性信息实体
     * @return 返回值
     * @throws SQLException sql异常
     */
    @Override
    public Integer insert(PersonalityInfo record) throws SQLException {
        return personalityInfoDAO.insert(record);
    }

    /**
     * 修改个人个性信息
     *
     * @param record 个人个性信息实体
     * @return 返回值
     * @throws SQLException sql异常
     */
    @Override
    public Integer updateByPrimaryKey(PersonalityInfo record) throws SQLException {
        int row = 0;
        if (record.getN_PersonalityId() != null) {
            // row=personalityInfoDAO.updateByPrimaryKey(record);
            row = personalityInfoDAO.updateByPrimaryKeySelective(record);
        }
        return row;
    }

    /**
     * 增加健康信息
     *
     * @param record 健康实体
     * @return 返回值
     * @throws SQLException sql异常
     */
    @Override
    public Integer insertHealthInfo(HealthYgenericInfo record) throws SQLException {
        return healthYgenericInfoDAO.insertHealthInfo(record);
    }

    /**
     * 修改健康信息
     *
     * @param record 健康实体
     * @return 返回值
     * @throws SQLException sql异常
     */
    @Override
    public Integer updateByHealthPKeySelective(HealthYgenericInfo record) throws SQLException {
        int row = 0;
        if (record.getN_HealthYgenericId() != null) {
            row = healthYgenericInfoDAO.updateByPrimaryKeySelective(record);
        }
        return row;
    }

    /**
     * 添加个人信息
     *
     * @param p 个人信息实体
     * @return 返回值
     * @throws SQLException sql异常
     */
    @Override
    public Integer insert(PersonalBasicInfo p) throws SQLException {
        return personalBasicInfoDAO.insert(p);
    }

    /**
     * 修改个人基本信息
     *
     * @param p 个人信息实体
     * @return 返回值
     * @throws SQLException sql异常
     */
    @Override
    public Integer udpatePersonalBasicInfo(PersonalBasicInfo p) throws SQLException {
        int row = 0;
        if (p.getN_PersonalId() != null) {
            row = personalBasicInfoDAO.udpatePersonalBasicInfo(p);
        }
        return row;
    }

    /**
     * 通过登录id 查询客户基本信息
     *
     * @param userInfoDO 登录主键
     * @throws SQLException 异常信息
     */
    @Override
    public UserInfoDO selectByLoginId(UserInfoDO userInfoDO) throws SQLException {
        return loginInfoDAO.selectByLoginId(userInfoDO);
    }

    @Override
    public List<UserInfoDO> selectByUserInfoDOByObj(UserInfoDO userInfoDO) throws SQLException {
        if (userInfoDO == null) {
            userInfoDO = new UserInfoDO();
        }
        userInfoDO.setStatus(1);
        return loginInfoDAO.selectUserInfoDOByLoginId(userInfoDO);
    }

    /**
     * 通过登录UserInfoDO查询客户基本信息
     *
     * @return List<UserInfoDO>
     * @throws SQLException 异常信息
     */

    @Override
    public List<UserInfoDO> selectByUserInfoDO(UserInfoDO userInfoDO) throws SQLException {
        return loginInfoDAO.selectByUserInfoDO(userInfoDO);
    }

    /**
     * 通过登录ID修改登录信息
     *
     * @return int
     * @throws SQLException 异常信息
     */

    @Override
    public int updateByPrimaryKeySelective(LoginInfo loginInfo) throws SQLException {
        int row = 0;
        if (loginInfo.getN_LoginId() != null) {
            row = loginInfoDAO.updateByPrimaryKeySelective(loginInfo);
            /*
             * 删除推送总部会员系统 try { // 修改的信息处理同步 List<String> lstAccountLogin = new ArrayList<String>();
             * lstAccountLogin.add(loginInfo.getLoginAccount());
             * personalBasicInfoService.syncPersonalBasicInfo2Base(lstAccountLogin); } catch
             * (ServiceException e) { log.error("同步修改的用户信息到总部系统失败:loginInfo:" +
             * loginInfo.toString()); }
             */
        }
        return row;
    }

    /**
     * 通过用户卡号查积分信息
     *
     * @return String
     * @throws Exception 异常信息
     */
    @Override
    public String pushKmScoreInfoByUserCardNum(String cardNum, int pageNo) {
        log.info("入参CardNum：" + cardNum + ",pageNo:" + pageNo);
        log.info("开始时间：" + System.currentTimeMillis());
        UserInfoDO userInfoDO = null;
        ScoreInfo scoreInfo = null;
        JSONObject json = new JSONObject();
        try {
            userInfoDO = personalityInfoDAO.queryUserScoreInfoByCardNum(cardNum);
            if (userInfoDO != null) {
                Page page = new Page();
                scoreInfo = new ScoreInfo();
                scoreInfo.setCardNum(cardNum);
                int count = scoreInfoDAO.countByCardNum(scoreInfo);
                page.setRecordCount(count);
                page.setPageNo(pageNo);
                scoreInfo.setStartIndex(page.getStartIndex());
                scoreInfo.setEndIndex(page.getStartIndex() + DEFAULT_PAGE_SIZE);
                List list = scoreInfoDAO.queryForPageByCardNum(scoreInfo);
                if (list.size() > 0) {
                    json.put("scoreInfoList", list);
                }

                json.put("AvailableScoreNumber", userInfoDO.getN_AvailableIntegral());
                json.put("totalNum", count);
                json.put("totalPage", page.getPageCount());
                json.put("code", 200);// 成功
            } else {
                json.put("code", 0);// 失败用户不存在
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            json.put("code", 202);// 异常
        }
        log.info("结束时间 ：" + System.currentTimeMillis());
        log.info("结果：" + json.toJSONString());
        return json.toJSONString();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(String accountLogin, PersonalBasicInfo p,
            PersonalityInfo personalityInfo, HealthYgenericInfo healthYgenericInfo)
            throws Exception {
        try {
            // 修改个人基本信息 0为失败 1---成功
            if (udpatePersonalBasicInfo(p) == 0) {
                log.info("修改个人基本信息失败");
            }

            // 修改个性化信息 0为失败 1---成功
            if (updateByPrimaryKey(personalityInfo) == 0) {
                log.info("修改个性化信息失败");
            }

            // 修改健康信息表 0为失败 1---成功
            if (updateByHealthPKeySelective(healthYgenericInfo) == 0) {
                log.info("修改健康信息表失败");
            }

            // 同步个人信息到总部系统
            /*
             * 删除推送总部会员系统 List<String> lstAccountLogin = new ArrayList<String>();
             * lstAccountLogin.add(accountLogin);
             * personalBasicInfoService.syncPersonalBasicInfo2Base(lstAccountLogin);
             */
        } catch (Exception e) {
            log.error("更新个人信息失败:" + e.getMessage());
        }
    }

    @Override
    public boolean checkUserLoginPassword(UserBaseInfo userBaseInfo) {
        try {
            UserBaseInfo userInfoVo = this.queryUserPasswordTwice(userBaseInfo, "login");
            if (null == userInfoVo) {
                return false;
            } else {
                userInfoVo = this.loginInfoDAO.queryUserLoginPassword(userInfoVo);
                if (null == userInfoVo) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("checkUserLoginPassword 校验用户名及登录密码是否匹配异常", e);
        }
        return false;
    }

    @Override
    public boolean checkUserPayPassword(UserBaseInfo userBaseInfo) {
        try {
            UserBaseInfo userInfoVo = this.queryUserPasswordTwice(userBaseInfo, "pay");
            if (null == userInfoVo) {
                return false;
            } else {
                userInfoVo = this.accountInfoDAO.queryUserPayPassword(userInfoVo);
                if (null == userInfoVo) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("checkUserPayPassword 校验用户名及登录密码是否匹配异常", e);
        }
        return false;
    }

    @Override
    public UserBaseInfo queryUserPasswordTwice(UserBaseInfo userInfoVo, String flag)
            throws Exception {
        if (com.kmzyc.util.StringUtil.isEmpty(userInfoVo.getLoginId())
                && com.kmzyc.util.StringUtil.isEmpty(userInfoVo.getLoginAccount())
                && com.kmzyc.util.StringUtil.isEmpty(userInfoVo.getMobile())) {
            log.error("checkUserPassword 校验用户名及登录密码是否匹配错误:参数缺失");
            return null;
        }

        if (!"login".equals(flag) && !"pay".equals(flag)) {
            log.error("checkUserPassword 校验用户名及登录密码是否匹配错误:密码标识错误");
            return null;
        }

        try {

            SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userInfoVo);

            if (null == saltInfo) {
                log.error("checkUserPassword 校验用户名及登录密码是否匹配错误:加盐信息缺失");
                return null;
            } else {
                userInfoVo.setLoginId(saltInfo.getLoginId());
                userInfoVo.setLoginSalt(saltInfo.getLoginSalt());
                userInfoVo.setPaySalt(saltInfo.getPaySalt());
                if ("login".equals(flag)) {
                    userInfoVo.setPassword(com.kmzyc.util.StringUtil.passwordTwiceEncryption(
                            userInfoVo.getPassword().toLowerCase(), saltInfo.getLoginSalt()));
                } else {
                    userInfoVo.setPassword(com.kmzyc.util.StringUtil.passwordTwiceEncryption(
                            userInfoVo.getPassword().toLowerCase(), saltInfo.getPaySalt()));
                }
                return userInfoVo;
            }

        } catch (Exception e) {
            log.error("checkUserPassword 校验用户名及登录密码是否匹配异常", e);
        }

        return null;
    }

}
