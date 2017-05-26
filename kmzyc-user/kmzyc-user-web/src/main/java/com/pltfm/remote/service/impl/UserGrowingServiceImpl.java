package com.pltfm.remote.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.user.remote.service.UserGrowingService;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.dao.BnesAcctTransactionDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.PersonalityInfoDAO;
import com.pltfm.app.dao.ScoreInfoDAO;
import com.pltfm.app.dao.ScoreRuleDAO;
import com.pltfm.app.dao.UserLevelDAO;
import com.pltfm.app.dataobject.UserLevelDO;
import com.pltfm.app.util.JavaScriptExpress;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.MySorce;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.ScoreInfo;
import com.pltfm.app.vobject.ScoreRule;
import com.pltfm.app.vobject.ScoreRuleExample;
import com.pltfm.app.vobject.UserLevel;

/* 删除邮件业务 import com.kmzyc.mailmobile.service.EmailSubscriptionRemoteService; */
/* import com.pltfm.app.service.BaseDockService; */

/**
 * 客户成长远程接口实现类
 *
 * @author zhl
 * @since 2013-08-06
 */
@Component(value = "userGrowingService")
public class UserGrowingServiceImpl implements UserGrowingService {
    private static final long serialVersionUID = 6788615363791043973L;
    Logger log = LoggerFactory.getLogger(this.getClass());
    // 积分规则DAO
    @Resource(name = "scoreRuleDAO")
    private ScoreRuleDAO scoreRuleDAO;
    // 积分明细dao
    @Resource(name = "scoreInfoDAO")
    private ScoreInfoDAO scoreInfoDAO;
    // 个人信息
    @Resource(name = "personalityInfoDAO")
    private PersonalityInfoDAO personalityInfoDAO;
    // 积分规则DAO
    @Resource(name = "userLevelDAO")
    private UserLevelDAO userLevelDAO;
    // 客户登录DAO
    @Resource(name = "loginInfoDAO")
    private LoginInfoDAO loginInfoDAO;
    // 交易
    @Resource(name = "bnesAcctTransactionDAO")
    private BnesAcctTransactionDAO bnesAcctTransactionDAO;
    //
    @Resource(name = "accountInfoDAO")
    private AccountInfoDAO accountInfoDAO;
    /**
     * 总部信息推送
     */
    /*
     * 删除推送到总部会员 @Resource private BaseDockService baseDockService;
     */

    /*
     * 删除邮件业务 @Autowired private EmailSubscriptionRemoteService emailSubscriptionRemoteService;
     */
    /**
     * 更新客户积分远程接口
     *
     * @param ruleCode 规则编号 rule001 个人购买商品 rule002 个人注册会员
     * @param scoreType 积分类型 1 积分积累0积分消费（所有退款、撤销等按照积分消费来处理）
     * @param loginAccount 登录账号
     * @param paramsMap 积分规则表达式参数对应值集合
     * @return 0 代表出现异常失败 1代表执行成功 2:时代会员不需要积分操作
     * @throws Exception 异常信息
     */
    /*
     * 被updateScoreInfoForTimeMember @Override public Integer updateUserScoreInfo(String ruleCode,
     * Integer scoreType, String loginAccount, Map<String, String> paramsMap) throws Exception {
     * ScoreRule scoreRule = null; BigDecimal calc = null; LoginInfo loginInfo = null; if (null !=
     * paramsMap && null != (scoreRule = scoreRuleDAO.queryScoreRuleByRuleCode(ruleCode)) && null !=
     * scoreRule.getScoreExpress() && null != (calc =
     * JavaScriptExpress.calcExpress(scoreRule.getScoreExpress(), paramsMap.get("A"))) && null !=
     * (loginInfo = loginInfoDAO.queryUserInfoByLoginAccount(loginAccount))) { double scoreNumber =
     * scoreType == 0 ? (0 - calc.intValue()) : calc.intValue(); int result = 0; Map<String, Object>
     * params = new HashMap<String, Object>(); params.put("loginId", loginInfo.getN_LoginId());
     * params.put("ruleCode", ruleCode); params.put("scoreNumber", scoreNumber); if ((result =
     * personalityInfoDAO.updateUserScore(params)) == 1) { ScoreInfo scoreInfo = new ScoreInfo();
     * scoreInfo.setN_loginId(loginInfo.getN_LoginId()); scoreInfo.setDiscribe(scoreType == 0 ?
     * (scoreRule.getDiscribe().replaceAll("购买", "退单").replaceAll("获得", "扣减")) :
     * scoreRule.getDiscribe()); scoreInfo.setScoreNumber(scoreNumber);
     * scoreInfo.setScoreType(scoreType); scoreInfo.setD_createDate(new Date());
     * scoreInfo.setN_scoreRuleId(scoreRule.getN_scoreRuleId()); Integer id =
     * scoreInfoDAO.insert(scoreInfo); if (id < 0) { throw new Exception("积分插入出现异常"); } 删除推送总部会员
     * else { UserInfoDO userInfoDO =
     * loginInfoDAO.queryUserByLoginId(loginInfo.getN_LoginId().longValue()); String
     * scoreInfoToString = "{\"scoreNumber\":" + "\"" + scoreNumber + "\"" +
     * ",\"AvailableScoreNumber\":" + "\"" + (userInfoDO.getN_AvailableIntegral()) + "\"" +
     * ",\"discribe\":" + "\"" + scoreInfo.getDiscribe() + "\"" + ",\"ScoreType\":" + "\"" +
     * scoreInfo.getScoreType() + "\"" + ",\"createDate\":" + "\"" + scoreInfo.getD_createDate() +
     * "\"" + ",\"scoreRuleId\":" + "\"" + scoreInfo.getN_scoreRuleId() + "\"" + ",\"cardNum\":" +
     * "\"" + userInfoDO.getCardNum() + "\"" + ",\"n_scoreInfoId\":" + "\"" + id + "\"" + "}";
     * baseDockService.pushBaseDockData(0, BaseDockType.USER_SCOREINFO.getValue(),
     * scoreInfoToString); return 1; } } else if (result > 1) { throw new Exception("受影响条数超过1条"); }
     * } return 0; }
     */

    /**
     * 根据规则查询积分值
     *
     * @param ruleCode 规则编号 rule002 个人购买商品 rule001 个人注册会员
     * @param paramsMap 积分规则表达式参数对应值集合
     * @return 0 代表出现异常失败 1代表执行成功
     * @throws Exception 异常信息
     */
    @Override
    public Integer getScoreNumber(String ruleCode, Map<String, String> paramsMap) throws Exception {
        Integer scoreNumber = 0;
        if (null != paramsMap) {
            ScoreRuleExample example = new ScoreRuleExample();
            example.createCriteria().andCodeEqualTo(ruleCode);
            List list = scoreRuleDAO.selectByExample(example);
            if (ListUtils.isNotEmpty(list)) {
                ScoreRule scoreRule = (ScoreRule) list.get(0);
                BigDecimal calc = JavaScriptExpress.calcExpress(scoreRule.getScoreExpress(),
                        paramsMap.get("A"));
                scoreNumber = null != calc ? calc.intValue() : 0;
            }
        }
        return scoreNumber;
    }

    /**
     * 通过客户交易金额更新客户消费总额以及客户等级信息
     *
     * @param feeNum 交易金额
     * @param loginAccount 客户主键
     * @param transactionType 交易类型 0 代表退款金额 1代表消费金额
     * @return 0 代表执行失败 1代表执行成功
     * @throws Exception
     */
    /*
     * 删除用户等级 @Deprecated
     * 
     * @Override
     * 
     * @Transactional(rollbackFor = Exception.class) public synchronized Integer
     * byFeeUpdateUserLevel(Double feeNum, String loginAccount, Integer transactionType) throws
     * Exception {
     * 
     * // 获取客户资料信息
     * 
     * LoginInfoExample loginInfoExample = new LoginInfoExample();
     * loginInfoExample.createCriteria().andLoginAccountEqualTo(loginAccount); List infoList =
     * loginInfoDAO.selectByExample(loginInfoExample); if (ListUtils.isNotEmpty(infoList)) {
     * LoginInfo loginInfo = (LoginInfo) infoList.get(0);
     * 
     * // 更新客户消费金额 Double lastYearAmount = 0.00; if (loginInfo.getN_LoginId() != null) {
     * PersonalityInfo personalityInfo =
     * personalityInfoDAO.selectByPersonalityInfo(loginInfo.getN_LoginId()); if (personalityInfo !=
     * null) { PersonalityInfo personalityInfoNew = new PersonalityInfo();
     * personalityInfoNew.setN_PersonalityId(personalityInfo.getN_PersonalityId()); if
     * (transactionType == 0) { double freeAmount = personalityInfo.getAmountConsume() - feeNum; if
     * (freeAmount < 0) { freeAmount = 0.00; } personalityInfoNew.setAmountConsume(freeAmount); }
     * else if (transactionType == 1) { personalityInfoNew
     * .setAmountConsume(personalityInfo.getAmountConsume() + feeNum); }
     * personalityInfoDAO.updateByPrimaryKeySelective(personalityInfoNew); } lastYearAmount =
     * personalityInfo.getLastYear_Amount();
     * 
     * // 通过客户消费总额查询客户等级信息 PersonalityInfo personInfo =
     * personalityInfoDAO.selectByPersonalityInfo(loginInfo.getN_LoginId()); UserLevelDO userLevelDO
     * = new UserLevelDO(); userLevelDO.setExpend(personInfo.getAmountConsume());
     * userLevelDO.setYearMin(lastYearAmount); userLevelDO.setCustomerTypeId(1); UserLevel userLevel
     * = new UserLevel(); userLevel = userLevelDAO.selectByUserLevelDO(userLevelDO); if (userLevel
     * == null) { UserLevel userLevel1 = new UserLevel(); userLevel1.setYearMin(lastYearAmount);
     * userLevel1.setN_customer_type_id(1); List<UserLevel> listUserLevel =
     * userLevelDAO.getNewUserLevelList(userLevel1); userLevel = listUserLevel.get(0); }
     * 
     * // 更新客户信息中客户等级信息 if (userLevel != null && lastYearAmount >= userLevel.getYearMin()) {
     * LoginInfo loginInfoNew = new LoginInfo();
     * loginInfoNew.setN_LevelId(userLevel.getN_level_id());
     * loginInfoNew.setN_LoginId(loginInfo.getN_LoginId());
     * loginInfoDAO.updateByPrimaryKeySelective(loginInfoNew); } else { LoginInfo loginInfoNew = new
     * LoginInfo(); loginInfoNew.setN_LevelId(userLevel.getN_level_id());
     * loginInfoNew.setN_LoginId(loginInfo.getN_LoginId());
     * loginInfoDAO.updateByPrimaryKeySelective(loginInfoNew); } return 1; } else { return 0; } }
     * else { return 0; }
     * 
     * return 1;
     * 
     * }
     */

    /**
     * 判断积分是否大于每日上限
     *
     * @param n_score_rule_id 、积分规则主键
     * @return boolean
     */
    public int isDayMaxSorce(Integer n_login_id, Integer n_score_rule_id) {
        int isDayMax = 0;

        ScoreInfo scoreInfo = new ScoreInfo();
        scoreInfo.setN_loginId(n_login_id);
        scoreInfo.setN_scoreRuleId(n_score_rule_id);

        try {
            isDayMax = scoreInfoDAO.isDayMaxSorce(scoreInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isDayMax;
    }

    /**
     * 人个性信息并更新客户总积分信息
     *
     * @param scoreNumber 、积分规则主键
     * @return boolean
     */
    public boolean updatePersonalityInfo(int n_LoginId, int scoreType, Double scoreNumber) {
        boolean isUpdate = false;
        BigDecimal scoreVal = new BigDecimal(scoreNumber);

        try {
            PersonalityInfo info = personalityInfoDAO.selectByPersonalityInfo(n_LoginId);
            BigDecimal totalNumber = info.getN_AvailableIntegral().subtract(scoreVal);
            PersonalityInfo personInfo = new PersonalityInfo();
            if (scoreType == 0 && totalNumber.doubleValue() >= 0) {
                personInfo.setN_TotalIntegral(info.getN_TotalIntegral().subtract(scoreVal));
                personInfo.setN_AvailableIntegral(info.getN_AvailableIntegral().subtract(scoreVal));
                personInfo.setN_PersonalityId(info.getN_PersonalityId());
                personalityInfoDAO.updateByPrimaryKeySelective(personInfo);
                isUpdate = true;
                return true;
            } else if (scoreType == 1) {
                personInfo.setN_TotalIntegral(info.getN_TotalIntegral().add(scoreVal));
                personInfo.setN_AvailableIntegral(info.getN_AvailableIntegral().add(scoreVal));
                personInfo.setN_PersonalityId(info.getN_PersonalityId());
                personalityInfoDAO.updateByPrimaryKeySelective(personInfo);
                isUpdate = true;
                return isUpdate;
            } else {
                isUpdate = false;
                return isUpdate;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdate;
    }

    /**
     * 根据查询条件获取客户等级总数
     *
     * @param userLevel 客户等级实体
     */
    public Integer countByLevel(UserLevel userLevel) throws Exception {
        return userLevelDAO.countByLevel(userLevel);
    }

    /**
     * 通过查询条件获取客户等级信息
     */
    public List queryForUserLevel(UserLevel userLevel) throws Exception {
        return userLevelDAO.queryForPage(userLevel);
    }

    /**
     * 通过主键获取客户等级信息
     *
     * @param userLevelId 客户等级主键
     * @throws SQLException 异常
     */

    public UserLevel selectByPrimaryKey(Integer userLevelId) throws SQLException {
        return userLevelDAO.selectByPrimaryKey(userLevelId);
    }

    /**
     * 通过登录主键获取我的积分信息
     *
     * @throws SQLException 异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MySorce getMysorc(Integer n_LoginId) throws Exception {
        MySorce mySorce = new MySorce();
        if (n_LoginId != null) {
            mySorce.setN_LoginId(n_LoginId);
            Integer n_AvailableIntegral = 0;
            // 积分值
            PersonalityInfo personalityInfo = personalityInfoDAO.selectByPersonalityInfo(n_LoginId);
            n_AvailableIntegral = personalityInfo.getN_AvailableIntegral().intValue();
            // 可用积分值
            mySorce.setN_AvailableIntegral(n_AvailableIntegral);
            // 等级名称
            LoginInfo loginInfo = loginInfoDAO.selectByPrimaryKey(n_LoginId);
            // System.out.println("loginInfo.getN_LevelId()"+loginInfo.getN_LevelId());
            UserLevel userLevel = userLevelDAO.selectByPrimaryKey(loginInfo.getN_LevelId());
            mySorce.setLevel_Name(userLevel.getLevel_name());
            // 等级下一级
            BigDecimal max = userLevel.getExpend_max().add(new BigDecimal(1));

            UserLevelDO userLevelDO = new UserLevelDO();
            userLevelDO.setExpend(max);
            userLevelDO.setCustomerTypeId(1);
            UserLevel userLevel2 = userLevelDAO.selectByUserLevelDO(userLevelDO);
            mySorce.setNext_Level_Name(userLevel2.getLevel_name());
            // sumBnesAcctTransactionQueryByExample
            AccountInfo ccountInfo = accountInfoDAO.selectByPrimaryLoginInfo(n_LoginId);
            BnesAcctTransactionQuery bnesAcctTransactionQuery = new BnesAcctTransactionQuery();

            bnesAcctTransactionQuery.setAccountId(ccountInfo.getN_AccountId());
            int sum = bnesAcctTransactionDAO
                    .sumBnesAcctTransactionQueryByExample(bnesAcctTransactionQuery);
            // 消费金额
            mySorce.setAmount(sum);
            // 需要再消费金额升级
            int afterAmount = max.add(new BigDecimal(sum)).intValue();
            mySorce.setAfterAmount(afterAmount);

        }
        return mySorce;

    }

    /**
     * 积分兑换优惠劵信息
     *
     * @throws SQLException 异常
     */

    public int changeSorcToCoupon(ScoreInfo scoreInfo) throws Exception {
        int result = 0;
        if (scoreInfo.getN_loginId() != null) {
            // 获得登录账号下积分
            PersonalityInfo personalityInfo =
                    personalityInfoDAO.selectByPersonalityInfo(scoreInfo.getN_loginId());
            int n_AvailableIntegral = personalityInfo.getN_AvailableIntegral().intValue();
            int sorceNumber = scoreInfo.getScoreNumber().intValue();
            if (!((n_AvailableIntegral - sorceNumber) < 0)) {
                personalityInfo
                        .setN_AvailableIntegral(new BigDecimal(n_AvailableIntegral - sorceNumber));
                personalityInfoDAO.updateByPrimaryKey(personalityInfo);
                scoreInfoDAO.insert(scoreInfo);
                result = 1;
            } else {
                result = 2;
            }
        }

        return result;

    }

    /**
     * 更新用户积分
     */
    @Override
    @Transactional
    public boolean updateScoreInfo(String ruleCode, Long loginId, BigDecimal amount)
            throws Exception {
        ScoreRule scoreRule;
        BigDecimal calc;
        if (null != amount && null != (scoreRule = scoreRuleDAO.queryScoreRuleByRuleCode(ruleCode))
                && null != scoreRule.getScoreExpress() && null != (calc =
                        JavaScriptExpress.calcExpress(scoreRule.getScoreExpress(), amount))) {
            Integer scoreType = amount.compareTo(BigDecimal.ZERO) >= 0 ? 1 : 2;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("loginId", loginId);
            params.put("ruleCode", ruleCode);
            params.put("scoreNumber", calc.intValue());
            if (personalityInfoDAO.updateUserScore(params) == 1) {
                ScoreInfo scoreInfo = new ScoreInfo();
                scoreInfo.setN_loginId(loginId.intValue());
                scoreInfo.setDiscribe(scoreType == 0
                        ? (scoreRule.getDiscribe().replaceAll("购买", "退单").replaceAll("获得", "扣减"))
                        : scoreRule.getDiscribe());
                scoreInfo.setScoreNumber(calc.doubleValue());
                scoreInfo.setScoreType(scoreType);
                scoreInfo.setD_createDate(new Date());
                scoreInfo.setN_scoreRuleId(scoreRule.getN_scoreRuleId());
                if (scoreInfoDAO.insert(scoreInfo) < 0) {
                    throw new Exception("积分插入出现异常");
                } else {
                    return true;
                }
            } else {
                throw new Exception("受影响条数超过1条");
            }
        }
        return false;
    }


    @Override
    public Integer updateScoreInfoForTimeMember(Integer scoreType, String loginAccount,
            BigDecimal pv) throws Exception {

        LoginInfo loginInfo;
        int result = 0;
        if (null != loginAccount && null != pv
                && null != (loginInfo = loginInfoDAO.queryUserInfoByLoginAccount(loginAccount))) {
            double scoreNumber = scoreType == 0 ? (0 - pv.intValue()) : pv.intValue();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("loginId", loginInfo.getN_LoginId());
            params.put("scoreNumber", scoreNumber);
            try {
                if ((result = personalityInfoDAO.updateUserScore(params)) == 1) {
                    ScoreInfo scoreInfo = new ScoreInfo();
                    scoreInfo.setN_loginId(loginInfo.getN_LoginId());
                    scoreInfo.setDiscribe(scoreType == 0 ? "退单扣减pv积分" : "购物新增pv积分");
                    scoreInfo.setScoreNumber(scoreNumber);
                    scoreInfo.setScoreType(scoreType);
                    scoreInfo.setD_createDate(new Date());
                    Integer id = scoreInfoDAO.insert(scoreInfo);
                    if (id < 0) {
                        log.error("会员" + loginInfo.getLoginAccount() + "积分插入出现出错!");
                        throw new Exception("积分插入出现异常");
                    }
                } else if (result > 1 || result == 0) {
                    log.error("更新会员" + loginInfo.getLoginAccount() + "积分失败!result = " + result);
                    throw new Exception("更新用户" + loginInfo.getLoginAccount() + "积分失败!");
                }
            } catch (Exception e) {
                log.error("更新时代会员" + loginInfo.getLoginAccount() + "pv积分 失败!" + e.getMessage());
                throw new Exception("更新用户" + loginInfo.getLoginAccount() + "pv积分 失败!!");

            }
        }

        return result;

    }

    /*
     * 删除活动积分 @Override
     * 
     * @Transactional public boolean updateScoreInfoAndCreateEP(String ruleCode, Long loginId,
     * BigDecimal amount, ExtractPrize ep) throws Exception {
     * 
     * boolean flag = true; try { // 扣的积分不为0 if (amount.compareTo(BigDecimal.ZERO) != 0) { flag =
     * this.updateScoreInfo(ruleCode, loginId, amount); } } catch (Exception e) { flag = false;
     * log.info("用户:" + loginId + "更新参与次数缓存失败"); throw e; }
     * 
     * if (flag) {
     * 
     * flag = this.extractPrizeManageRemoteService.createExtractPrize(ep); }
     * 
     * return flag; }
     */

}
