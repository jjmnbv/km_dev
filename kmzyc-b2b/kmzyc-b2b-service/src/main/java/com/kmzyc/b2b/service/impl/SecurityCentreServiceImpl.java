package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.framework.common.util.MD5;
import com.kmzyc.b2b.dao.AccountInfoDao;
import com.kmzyc.b2b.dao.LoginDao;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.vo.ChangePasswordInfo;
import com.kmzyc.b2b.vo.ChangePayPasswordInfo;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.b2b.vo.SecurityCentreInfo;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.b2b.vo.VerifyMobileInfo;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.user.remote.service.AccountInfoRemoteService;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.user.remote.service.MobileCodeInfRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.MobileCodeInf;


@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("securityCentreServiceImpl")
public class SecurityCentreServiceImpl implements SecurityCentreService {

    private static Logger logger = LoggerFactory.getLogger(SecurityCentreServiceImpl.class);


    @Resource(name = "accountInfoDaoImpl")
    private AccountInfoDao accountInfoDao;

    @Resource(name = "loginDaoImp")
    private LoginDao loginDao;
    @Resource
    private CustomerRemoteService customerRemoteService;
    @Resource
    private AccountInfoRemoteService accountInfoRemoteService;
    @Resource
    private MobileCodeInfRemoteService mobileCodeInfRemoteService;

    @Resource(name = "loginServiceImp")
    private LoginService loginService;


    @Override
    public boolean changePassword(User user, ChangePasswordInfo changePasswordInfo)
            throws Exception {

        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setLoginId(user.getLoginId());
        userBaseInfo.setPassword(MD5.getMD5Str(changePasswordInfo.getOldPassword()));
        // SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);
        userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "login");
        if (null == userBaseInfo) {
            return false;
        } else {
            // 验证验证码 如果不对则抛出异常或者 返回false
            logger.info("changePassword方法: 查询条件user" + user + ";changePasswordInfo:"
                    + changePasswordInfo);

            if (userBaseInfo.getPassword().equals(user.getLoginPassword())) {
                // 更新密码
                LoginInfo loginInfo = new LoginInfo();
                // int row = 0;
                loginInfo.setN_LoginId(user.getLoginId().intValue());
                loginInfo.setLoginPassword(StringUtil.passwordTwiceEncryption(
                        MD5.getMD5Str(changePasswordInfo.getNewPassword()),
                        userBaseInfo.getLoginSalt()));
                customerRemoteService.updateByPrimaryKeySelective(loginInfo);

                return true;
            } else {
                return false;
            }
        }

    }

    @Override
    public boolean verifyPassword(String password, User user) throws Exception {

        String loginPassword = user.getLoginPassword();
        String md5password = MD5.getMD5Str(password);

        if (Constants.CUSTOMER_TYPE_SD_MEMBER == user.getCustomerTypeId().intValue()) {

            // 如果B2B不存在或密码错误才去查询易创网
            User usertem = new User();
            usertem.setLoginAccount(user.getLoginAccount().toLowerCase());
            usertem.setLoginPassword(md5password.substring(0, 30));

            usertem = this.loginService.userLogin(usertem);
            if (null != usertem) {
                logger.info("获取时代会员({})信息,存在该用户直接返回:{}", user.getLoginAccount(),
                        usertem.toString());
                return true;
            } else {
                EraInfo ei = loginService.queryEraInfoForWebService(
                        user.getLoginAccount().toLowerCase(), md5password);
                return null != ei;
            }
        } else {

            UserBaseInfo userBaseInfo = new UserBaseInfo();
            userBaseInfo.setLoginId(user.getLoginId());
            // SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);
            userBaseInfo.setPassword(md5password);
            userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "login");
            return null != userBaseInfo && userBaseInfo.getPassword().equals(loginPassword);
        }
    }

    /**
     * 登录密码验证
     * 
     * @param password
     * @param loginId
     * @return
     * @throws Exception
     */
    @Override
    public boolean verifyUserPassword(String password, Long loginId) {

        try {
            User user = loginDao.getUserByLoginId(loginId);
            String loginPassword = user.getLoginPassword();
            String md5password = MD5.getMD5Str(password);
            UserBaseInfo userBaseInfo = new UserBaseInfo();
            userBaseInfo.setLoginId(user.getLoginId());
            // SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);

            userBaseInfo.setPassword(md5password);
            userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "login");

            return null != userBaseInfo && userBaseInfo.getPassword().equals(loginPassword);
        } catch (Exception e) {
            logger.error("邮箱验证身份出现异常" + e.getMessage(), e);
            return false;
        }

    }

    /**
     * 登录密码验证app使用 不需要再次MD5
     * 
     * @param password
     * @param loginId
     * @return
     * @throws Exception
     */
    @Override
    public boolean appVerifyUserPassword(String password, Long loginId) {

        try {

            User user = loginDao.getUserByLoginId(loginId);

            if (null == user) {
                return false;
            }

            if (Constants.CUSTOMER_TYPE_SD_MEMBER == user.getCustomerTypeId().intValue()) {

                // 如果B2B不存在或密码错误才去查询易创网
                User usertem = new User();
                usertem.setLoginAccount(user.getLoginAccount().toLowerCase());
                usertem.setLoginPassword(password.substring(0, 30));

                usertem = this.loginService.userLogin(usertem);
                if (null != usertem) {
                    logger.info("获取时代会员({})信息,B2B存在该用户直接返回:{}", user.getLoginAccount(),
                            usertem.toString());
                    return true;
                } else {
                    EraInfo ei = loginService.queryEraInfoForWebService(
                            user.getLoginAccount().toLowerCase(), password);
                    return null != ei;
                }

            } else {
                UserBaseInfo userBaseInfo = new UserBaseInfo();
                userBaseInfo.setLoginId(loginId);
                userBaseInfo.setPassword(password);
                userBaseInfo =
                        this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "login");

                // SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);
                if (null == userBaseInfo) {
                    return false;
                }
                return userBaseInfo.getPassword().equalsIgnoreCase(user.getLoginPassword());
            }
        } catch (Exception e) {
            logger.error("邮箱验证身份出现异常" + e.getMessage(), e);
            return false;
        }

    }

    /**
     * 根据用户id查询
     * 
     * @return
     */
    @Override
    public com.kmzyc.b2b.model.AccountInfo accountByUserId(long loginid) throws SQLException {
        com.kmzyc.b2b.model.AccountInfo accountInfo = null;
        try {

            accountInfo = accountInfoDao.findByLoginId(loginid);
        } catch (SQLException e) {
            logger.error("根据用户id查询出现异常" + e.getMessage(), e);
        }
        return accountInfo;
    }

    @Override
    public boolean modifyPayPassword(ChangePayPasswordInfo changePayPasswordInfo, User user) {

        String md5password = MD5.getMD5Str(changePayPasswordInfo.getPayPassword());
        try {

            UserBaseInfo userBaseInfo = new UserBaseInfo();
            userBaseInfo.setLoginId(user.getLoginId());

            userBaseInfo.setPassword(md5password);
            userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "pay");

            // SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);
            if (null == userBaseInfo) {
                return false;
            } else {
                AccountInfo accountInfo = new AccountInfo();
                accountInfo.setN_LoginId(user.getLoginId().intValue());
                accountInfo.setPaymentpwd(userBaseInfo.getPassword());

                com.kmzyc.b2b.model.AccountInfo newAccountInfo =
                        accountInfoDao.findByLoginId(user.getLoginId());
                // 判断是设置初始支付密码还是 修改支付密码
                if (newAccountInfo.getPaymentpwd() == null
                        || "".equals(newAccountInfo.getPaymentpwd())) {
                    // 启用支付范围：1--使用余额2---使用优惠券3--使用礼品卡4--使用积分；5--使用预备金;多项中间以逗号隔开如：1,2
                    accountInfo.setPay_Range("1");
                }
                /*------------2014-07-21 wangkai-----------*/

                accountInfoRemoteService.updateByLoginId(accountInfo);
                return true;
            }
        } catch (Exception e) {
            logger.error("修改密码出现异常" + e.getMessage(), e);
            return false;
        }

    }

    @Override
    public boolean appModifyPayPassword(ChangePayPasswordInfo changePayPasswordInfo, User user) {

        try {

            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setN_LoginId(user.getLoginId().intValue());
            accountInfo.setPaymentpwd(changePayPasswordInfo.getPayPassword());
            /*---------- 2014-07-21 wangkai------------*/
            // 根据userId查出accountInfo表中的信息
            com.kmzyc.b2b.model.AccountInfo newAccountInfo =
                    accountInfoDao.findByLoginId(user.getLoginId());
            // 判断是设置初始支付密码还是 修改支付密码
            if (newAccountInfo.getPaymentpwd() == null
                    || "".equals(newAccountInfo.getPaymentpwd())) {
                // 启用支付范围：1--使用余额2---使用优惠券3--使用礼品卡4--使用积分；5--使用预备金;多项中间以逗号隔开如：1,2
                accountInfo.setPay_Range("1");
            }
            /*------------2014-07-21 wangkai-----------*/

            accountInfoRemoteService.updateByLoginId(accountInfo);
            return true;
        } catch (Exception e) {
            logger.error("修改密码出现异常" + e.getMessage(), e);
            return false;
        }

    }

    // 添加验证手机号码参数
    @Override
    public boolean verifyMobile(VerifyMobileInfo verifyMobileInfo, User user,
            String sessionmobileVerifyCode, String mobilePhone) throws Exception {

        String mobileVerifyCode = verifyMobileInfo.getMobileVerifyCode();
        String phone = verifyMobileInfo.getMobileNumber();
        if (sessionmobileVerifyCode != null && mobileVerifyCode != null
                && mobileVerifyCode.trim().equals(sessionmobileVerifyCode.trim())
                && mobilePhone != null && phone != null
                && phone.trim().equals(mobilePhone.trim())) {
            try {
                // MobileCodeInfRemoteService mobileCodeInfRemoteService =
                // (MobileCodeInfRemoteService)
                // RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
                // "mobileCodeInfRemoteService");
                MobileCodeInf mobileCodeInf = new MobileCodeInf();
                // 用户登录ID值
                mobileCodeInf.setN_LoginId(user.getLoginId().intValue());
                // 失效分钟值
                mobileCodeInf.setN_FailureTimeValue(
                        Integer.valueOf(ConfigurationUtil.getString("b2b_common_msg_valid_time")));
                // 随机码
                mobileCodeInf.setTattedCode(verifyMobileInfo.getMobileVerifyCode());
                mobileCodeInf.setMobile(verifyMobileInfo.getMobileNumber());
                // 短信验证码类型 : 忘记密码 ------1 手机验证 ------2 支付密码修改 ----3
                mobileCodeInf.setMobile_Type(3);
                int row = mobileCodeInfRemoteService.addMobileCodeInf(mobileCodeInf, 3);
                // 手机随机码记录主键
                mobileCodeInf.setN_CellPhoneTattedCodeId(row);
                mobileCodeInfRemoteService.updateMobileCodeInfByPrimaryKey(mobileCodeInf, 3);

                // 更新AccoutInfo表数据
                Map<String, Object> hm = new HashMap<>();
                hm.put("mobile", verifyMobileInfo.getMobileNumber());
                hm.put("userId", user.getLoginId().intValue());
                accountInfoDao.updateMobile(hm);

                // 更新login表的数据
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setN_LoginId(user.getLoginId().intValue());
                loginInfo.setMobile(verifyMobileInfo.getMobileNumber());
                // 消息推送核心字段，根据该字段来查询accountInfo表获取email并推送
                loginInfo.setLoginAccount(user.getLoginAccount());
                // 20150824 修改手机号码 （是首次 是否给奖励 用户系统判断）---
                int res = customerRemoteService.userMobileVerification(loginInfo);
                if (res == 0) {
                    logger.info("调用用户修改手机号接口修改失败！" + verifyMobileInfo.getMobileNumber());
                }
                user.setMobile(verifyMobileInfo.getMobileNumber());
            } catch (Exception e) {
                logger.error("短信验证出现异常" + e.getMessage(), e);
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public SecurityCentreInfo querySecurityCentreInfo(User user) throws ServiceException {
        try {

            SecurityCentreInfo securityCentreInfo = new SecurityCentreInfo();
            securityCentreInfo.setMobileNumber(user.getMobile());
            securityCentreInfo.setLoginPassword(user.getLoginPassword());

            /*
             * // 判断邮箱是否验证 User users = loginDao.getUserByLoginId(user.getLoginId().intValue());
             * securityCentreInfo.setEmailAddress(users.getEmail());// 安全中心显示邮箱 if
             * (!"".equals(users.getEmail()) && null != users.getEmail()) {
             * securityCentreInfo.setEmailValidate(true); } else {
             * securityCentreInfo.setEmailValidate(false); }
             */
            com.kmzyc.b2b.model.AccountInfo accountInfo =
                    accountInfoDao.findByLoginId(user.getLoginId());
            // 查询个人账户的支付密码
            String payPassword = accountInfo.getPaymentpwd();
            if (payPassword != null && !payPassword.equals("")) {
                securityCentreInfo.setPayPasswordInvocation(true);
                securityCentreInfo.setPayPassword(payPassword);
            } else {
                securityCentreInfo.setPayPasswordInvocation(false);
            }
            // 判断手机是否已验证
            String mobileNumer = user.getMobile();
            if (mobileNumer != null && !mobileNumer.equals("")) {
                securityCentreInfo.setMobileValidate(true);
            } else {
                securityCentreInfo.setMobileValidate(false);
            }
            return securityCentreInfo;
        } catch (Exception e) {
            logger.error("安全中心初始化值出现异常" + e.getMessage(), e);
            throw new ServiceException(1, e.getMessage());
        }
    }


    @Override
    public com.kmzyc.b2b.model.AccountInfo getAccountInfo(User user) {
        try {

            com.kmzyc.b2b.model.AccountInfo accountInfo;
            accountInfo = accountInfoDao.findByLoginId(user.getLoginId());
            return accountInfo;
        } catch (SQLException e) {
            logger.error("获取用户信息异常" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean saveAccountInfoPayRange(String payRange, User user) {
        try {

            com.kmzyc.b2b.model.AccountInfo accountInfo =
                    accountInfoDao.findByLoginId(user.getLoginId());
            AccountInfo accountInfos = new AccountInfo();
            // AccountInfoRemoteService accountInfoRemoteService =
            // (AccountInfoRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
            // "/accountInfoRemoteService");
            accountInfos.setPay_Range(payRange);
            accountInfos.setN_AccountId(accountInfo.getNaccountId());
            accountInfos.setN_LoginId(user.getLoginId().intValue());
            accountInfoRemoteService.updateByLoginId(accountInfos);
            return true;
        } catch (Exception e) {
            logger.error("saveAccountInfoPayRange方法出现异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 判断输入的绑定手机号码是否已被使用 add by songmiao 2013-11-11
     * 
     * @throws Exception
     */
    @Override
    public boolean mobileNumberIsUsed(String mobileNumber) throws Exception {
        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
            User selectUser = new User();
            selectUser.setMobile(mobileNumber);
            selectUser.setLoginId(userId);
            // List<User> list = (List<User>) loginDao
            // .queryListByMobile(selectUser);
            // 判断输入的绑定手机号码是否已被使用
            List<User> userList = loginDao.getListByMobile(selectUser);
            if (null != userList) {
                if (userList.size() > 0) {
                    return true;// 已经占有
                }
            }
        } catch (Exception e) {
            logger.error("判断输入的绑定手机号码是否已被使用" + e.getMessage(), e);
            throw e;
        }
        return false;
    }

    /**
     * 根据登录ID获取用户信息
     * 
     * @param loginId
     * @return
     */
    @Override
    public User getUserByLoginId(long loginId) {
        User user = null;
        try {

            user = loginDao.getUserByLoginId(loginId);
        } catch (Exception e) {
            logger.error("数据库错误" + e.getMessage(), e);
        }
        return user;
    }

    @Override
    public boolean mobileIsUsed(String mobile) throws Exception {

        try {
            List<User> userList = loginDao.listByMobile(mobile);
            if (null != userList) {
                if (userList.size() > 0) {
                    return true;// 已经占有
                }
            }
        } catch (Exception e) {
            logger.error("判断输入的绑定手机号码是否已被使用" + e.getMessage(), e);
            throw e;
        }
        return false;
    }

    @Override
    public int updateSource(Map<String, Object> map) throws Exception {

        return accountInfoDao.updateRegistSource(map);
    }

    /**
     * 查询用户安全信息
     * 
     * @param loginId
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> queryUserSecurityInfo(Long loginId) throws Exception {

        return accountInfoDao.queryUserSecurityInfo(loginId);
    }
}
