package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.LoginDao;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.RegistService;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.user.remote.service.AccountInfoRemoteService;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.util.StringUtil;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.LoginInfo;

// import com.km.framework.common.util.RemoteTool;

@SuppressWarnings({"unchecked", "SpringJavaAutowiringInspection"})
@Service
public class RegistServiceImp implements RegistService {
    private static Logger logger = LoggerFactory.getLogger(RegistServiceImp.class);

    @Resource(name = "loginDaoImp")
    private LoginDao loginDao;
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private AccountInfoRemoteService accountInfoRemoteService;

    @Autowired
    private CustomerRemoteService customerRemoteService;

    @Resource
    private CouponRemoteService couponRemoteService;

    /*
     * 调用注册的接口实现,注册时用户生成新用户 发送 优惠券信息以及积分
     */
    @Override
    public Map<String, String> regist(User user) throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setEmail(user.getEmail());
        loginInfo.setLoginAccount(user.getLoginAccount());
        loginInfo.setLoginPassword(user.getLoginPassword());
        if (!StringUtil.isEmpty(user.getMobile())) {
            loginInfo.setMobile(user.getMobile());
        }
        if (!StringUtil.isEmpty(user.getCardNum())) {
            loginInfo.setCardNum(user.getCardNum());
        }
        loginInfo.setN_CustomerTypeId(user.getCustomerTypeId());
        loginInfo.setRegister_Device(user.getRegisterDevice());
        loginInfo.setRegister_Platfrom(user.getRegisterPlatfrom());

        loginInfo = this.passwordTwiceEncryption(loginInfo);

        Map<String, String> map = customerRemoteService.registerLoginInfo(loginInfo, 3);
        map.put("success", "yes");
        doAfterRegist(user.getCustomerTypeId(), Long.valueOf(map.get("loginId")),
                user.getLoginAccount(), user.getMobile(), user.getEmail());
        return map;

    }

    @Override
    public Map<String, String> regist(boolean isMobile, User user) throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginAccount(user.getLoginAccount());
        loginInfo.setLoginPassword(user.getLoginPassword());
        if (!StringUtil.isEmpty(user.getMobile())) {
            loginInfo.setMobile(user.getMobile());
        }
        loginInfo.setN_CustomerTypeId(user.getCustomerTypeId());

        loginInfo = this.passwordTwiceEncryption(loginInfo);

        Map<String, String> map = customerRemoteService.registerLoginInfo(loginInfo, 3);
        if (map.containsKey("loginId")) {
            map.put("success", "yes");
            doAfterRegist(user.getCustomerTypeId(), Long.valueOf(map.get("loginId")),
                    user.getLoginAccount(), user.getMobile(), user.getEmail());
        }
        return map;
    }

    public Map<String, String> registReward(boolean isMobile, User user, String invitationId,
            String invitedOrganizatton, int Device, int Platfrom) throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setEmail(user.getEmail());
        loginInfo.setLoginAccount(user.getLoginAccount());
        loginInfo.setLoginPassword(user.getLoginPassword());
        loginInfo.setRegister_Device(Device);
        loginInfo.setRegister_Platfrom(Platfrom);
        if (!StringUtil.isEmpty(user.getMobile())) {
            loginInfo.setMobile(user.getMobile());
        }

        loginInfo.setN_CustomerTypeId(user.getCustomerTypeId());

        loginInfo = this.passwordTwiceEncryption(loginInfo);

        Map<String, String> map =
                customerRemoteService.registerRewardLoginInfo(loginInfo, 3, invitationId,
                        invitedOrganizatton);
        loginInfo.setN_LoginId(Integer.parseInt(map.get("loginId")));
        /*if (isMobile) {
            // 首次验证送积分
            customerRemoteService.userMobileVerification(loginInfo);
        }*/
        map.put("success", "yes");
        doAfterRegist(user.getCustomerTypeId(), Long.valueOf(map.get("loginId")),
                user.getLoginAccount(), user.getMobile(), user.getEmail());
        return map;
    }



    @Override
    public Map<String, String> sdBindMember(User user) throws Exception {
        Map<String, String> map = new HashMap<>();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setEmail(user.getEmail()); // 邮箱
        loginInfo.setLoginAccount(user.getLoginAccount());
        loginInfo.setN_LoginId(user.getLoginId().intValue());
        if (!StringUtil.isEmpty(user.getMobile())) {
            loginInfo.setMobile(user.getMobile());
        }
        loginInfo.setN_CustomerTypeId(user.getCustomerTypeId());
        map.put("loginId", user.getLoginId().toString());
        doAfterRegist(user.getCustomerTypeId(), user.getLoginId(), user.getLoginAccount(),
                user.getMobile(), user.getEmail());
        map.put("success", "yes");
        return map;
    }

    @Override
    public Boolean verilyEmail(String email) throws SQLException {

        User user = new User();
        user.setEmail(email);
        List list;
        try {
            list = loginDao.queryListByEmail(user);
        } catch (SQLException e) {
            logger.error("查询异常:" + e.getMessage(), e);
            throw new SQLException();
        }
        return null == list || list.size() == 0;
    }

    @Override
    public int verilyName(String name) throws Exception {

        return loginDao.validUserName(name);
    }

    /**
     * 查询临时用户
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public User queryTempUser(User user) throws SQLException {

        return loginDao.queryTempUser(user);
    }

    /**
     * 更新临时用户
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public boolean updateTempUserInfo(User user) throws Exception {

        // 更新 login_info表
        boolean result = loginDao.updateTempUserInfo(user);

        // 更新 account_info，并推送消息到总部会员
        if (!StringUtil.isEmpty(user.getLoginAccount())) {
            AccountInfo ai = new AccountInfo();
            ai.setN_LoginId(user.getLoginId().intValue());
            ai.setAccountLogin(user.getLoginAccount());
            if (!StringUtil.isEmpty(user.getMobile())) {
                ai.setMobile(user.getMobile());
            }
            if (!StringUtil.isEmpty(user.getEmail())) {
                ai.setEmail(user.getEmail());
            }
            if (null != user.getCustomerTypeId() && 0 != user.getCustomerTypeId()) {
                ai.setN_CustomerTypeId(user.getCustomerTypeId());
            }
            accountInfoRemoteService.updateByLoginId(ai);
        }
        return result;
    }

    /**
     * 用户类型
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public long queryCustomerType(User user) throws SQLException {

        return loginDao.queryCustomerType(user);
    }

    /**
     * 验证用户是否存在：注册、修改用户名、手机号、邮箱 0不存在，1邮箱，2用户名，3手机，4非法用户名，6游客
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public int checkUserExists(User user) throws ServiceException {

        int result;
        try {
            if (6 == loginDao.queryCustomerType(user)) {
                return 6;
            }
            Map map = loginDao.checkUserExists(user);
            if (null == map || map.isEmpty()) {
                result = 0;
            } else {
                String mobile = (String) map.get("MOBILE");
                String email = (String) map.get("EMAIL");
                String uname = (String) map.get("UNAME");
                Object isIllegal = map.get("ISILLEGAL");
                result = (null == mobile && null == email && null == uname) ? 0 : 1;

                if(!StringUtil.isEmpty(user.getMobile()) && !StringUtil.isMobile(user.getMobile())){
                    return 3;
                }

                if (!StringUtil.isEmpty(email) && email.equals(user.getEmail())) {
                    result = 1;
                } else if (!StringUtil.isEmpty(uname) && uname.equals(user.getLoginAccount())) {
                    result = 2;
                } else if (!StringUtil.isEmpty(mobile) && mobile.equals(user.getMobile())) {
                    result = 3;
                } else if (null != isIllegal && 1 == Integer.parseInt(isIllegal.toString())) {
                    result = 4;
                }
            }
        } catch (Exception e) {
            throw new ServiceException(0, "获取用户信息错误：" + e.getMessage());
        }
        return result;
    }

    /**
     * 验证用户是否存在 1存在 0 不存在 -1存在申请记录
     * 
     * @param user
     * @return
     * @throws ServiceException
     */
    public int checkUserExistsForVshop(User user) throws ServiceException {
        int rs = 0;
        try {
            Map<String, String> map = loginDao.checkUserExistsForVshop(user);
            if (null != map && !"0".equals(map.get("ISAPPLAY"))) {
                rs = -1;
            } else if (null != map) {
                rs = 1;
            }
        } catch (Exception e) {
            throw new ServiceException(0, "获取用户信息错误：" + e.getMessage());
        }
        return rs;
    }

    /* 页面手机注册 */
    @Override
    public Map<String, String> registPCPhone(User userR, int device, int platfrom) throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setRegister_Device(device);
        loginInfo.setRegister_Platfrom(platfrom);
        loginInfo.setLoginAccount(userR.getLoginAccount());
        loginInfo.setLoginPassword(userR.getLoginPassword());
        if (!StringUtil.isEmpty(userR.getMobile())) {
            loginInfo.setMobile(userR.getMobile());
        }
        loginInfo.setN_CustomerTypeId(userR.getCustomerTypeId());

        loginInfo = this.passwordTwiceEncryption(loginInfo);

        Map<String, String> map = customerRemoteService.registerLoginInfo(loginInfo, 3);
        loginInfo.setN_LoginId(Integer.parseInt(map.get("loginId")));
        // 设置客户手机为已验证
        customerRemoteService.userMobileVerification(loginInfo);
        map.put("success", "yes");
        doAfterRegist(userR.getCustomerTypeId(), Long.valueOf(map.get("loginId")),
                userR.getLoginAccount(), userR.getMobile(), userR.getEmail());
        return map;
    }



    /* PC端浮动框手机注册 */
    @Override
    public Map<String, String> registDivPhone(User userRs) throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginAccount(userRs.getLoginAccount());
        loginInfo.setLoginPassword(userRs.getLoginPassword());
        if (!StringUtil.isEmpty(userRs.getMobile())) {
            loginInfo.setMobile(userRs.getMobile());
        }

        loginInfo.setN_CustomerTypeId(userRs.getCustomerTypeId());

        loginInfo = this.passwordTwiceEncryption(loginInfo);

        Map<String, String> map = customerRemoteService.registerLoginInfo(loginInfo, 3);
        loginInfo.setN_LoginId(Integer.parseInt(map.get("loginId")));
        // 设置客户手机为已验证
        customerRemoteService.userMobileVerification(loginInfo);

        map.put("success", "yes");
        doAfterRegist(userRs.getCustomerTypeId(), Long.valueOf(map.get("loginId")),
                userRs.getLoginAccount(), userRs.getMobile(), userRs.getEmail());
        return map;
    }

    /**
     * 注册后事件
     *
     * @throws ServiceException
     */
    @Override
    public void doAfterRegist(int pUserType, Long pLoginId, String pLoginName, String pMobile,
            String pEmail) throws ServiceException {
        // final int userType = pUserType;
        final Long loginId = pLoginId;
        final String loginName = pLoginName;
        // final String email = pEmail;
        taskExecutor.execute(() -> {
            try {
                // CouponRemoteService couponService =
                // (CouponRemoteService) RemoteTool.getRemote(Constants.PROMOTION_SYSTEM_CODE,
                // "couponService");
                User utd = new User();
                utd.setLoginId(loginId);
                utd.setLoginAccount(loginName);
                couponRemoteService.grantCouponForSdFirstLogin(utd);// 注册送券

                /*userGrowingService.updateUserScoreInfo("RU0001", 1, loginName,
                        new HashMap<String, String>());// 注册积分
                if (!StringUtil.isEmpty(mobile)) {
                    userGrowingService.updateUserScoreInfo("RU0003", 1, loginName,
                            new HashMap<String, String>());// 手机验证积分
                }*/
            } catch (Exception e) {
                logger.error(loginId + "执行注册后事件发生异常" + e.getMessage(), e);
            }
        });
    }

    /**
     * 登录密码二次加密
     * @param loginInfo
     * @return
     */
    private LoginInfo passwordTwiceEncryption(LoginInfo loginInfo){
        //登录密码二次加密
        Map<String,String> saltMap = StringUtil.generatePasswordTwiceEncryption(loginInfo.getLoginPassword());
        //重新设置登录密码
        loginInfo.setLoginPassword(saltMap.get("twicePassword"));
        loginInfo.setLoginSalt(saltMap.get("saltStr"));

        //支付密码加密串
        saltMap = StringUtil.generatePasswordTwiceEncryption("");
        loginInfo.setPaySalt(saltMap.get("saltStr"));

        return loginInfo;
    }
}
