package com.kmzyc.b2b.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tempuri.IMemberInfo;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.dao.EraInfoDAO;
import com.kmzyc.b2b.dao.LoginDao;
import com.kmzyc.b2b.event.EventUtils;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.RegistService;
import com.kmzyc.b2b.service.ShopCartInfoService;
import com.kmzyc.b2b.util.CheckEraInfoMap;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.user.remote.service.EraInfoRemoteService;
import com.kmzyc.util.StringUtil;
import com.whalin.MemCached.MemCachedClient;


// import com.km.framework.common.util.RemoteTool;

@SuppressWarnings({"unchecked", "BigDecimalMethodWithoutRoundingCalled"})
@Service
public class LoginServiceImp implements LoginService {
    // private static Logger logger = Logger.getLogger(LoginServiceImp.class);
    private static Logger logger = LoggerFactory.getLogger(LoginServiceImp.class);

    @Resource(name = "loginDaoImp")
    private LoginDao loginDao;

    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;

    @Resource
    private EraInfoDAO eraInfoDAO;

    @Resource(name = "registServiceImp")
    private RegistService registservice;

    @Resource(name = "shopCartInfoService")
    private ShopCartInfoService shopCartInfoService;

    @Resource
    private CustomerRemoteService customerRemoteService;

    @Resource
    private EraInfoRemoteService eraInfoRemoteService;


    // 登陆
    @Override
    public User login(User user) throws SQLException {
        user.setLoginPassword(user.getLoginPassword());
        return loginDao.queryByUser(user);
    }

    @Override
    public boolean checkRegistUserMobilOrEmailExist(User user) throws SQLException {

        boolean result = true;
        try {
            this.loginDao.queryRegistUserByMobilOrEmail(user);
            result = false;
        } catch (SQLException e) {
            logger.error("数据库查询异常" + e.getMessage(), e);
            throw new SQLException();
        }
        return result;
    }

    @Override
    public List<User> checkMobileExist(String mobile) {

        try {
            return this.loginDao.queryMobilExist(mobile);
        } catch (SQLException e) {
            logger.error("数据库查询异常" + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<User> checkEmailExist(String email) {

        /*
         * User user = new User(); user.setEmail(email);
         */
        try {
            return this.loginDao.queryEmailExist(email);
        } catch (SQLException e) {
            logger.error("数据库查询异常" + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List queryRelationSKUID(Long SKUID) throws SQLException {

        return this.loginDao.queryRelationDetail(SKUID);
    }

    @Override
    public List queryProductInfo(List<Long> list) throws SQLException {

        return this.loginDao.queryProductInfo(list);
    }

    @Override
    public User verifyLogin(HttpServletRequest request) {
        User user = null;
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        if (userId != null) {
            // 浏览器未关闭
            try {
                user = loginDao.getUserByLoginId(userId);
            } catch (Exception e) {
                logger.error("获取用户失败" + e.getMessage(), e);
                return null;
            }
        } else {
            // 获取cookie
            Cookie[] cs = request.getCookies();
            String loginSessionID = null;
            if (cs != null) {
                for (Cookie c : cs) {
                    if (c.getName().equals(Constants.COOKIE_SESSION_ID)) {
                        loginSessionID = c.getValue();
                    }
                }
            }
            if (loginSessionID != null) {
                if (memCachedClient.get(loginSessionID) != null) {
                    // 记录了自动登录
                    user = (User) memCachedClient.get(loginSessionID);
                }
            }
        }
        return user;
    }

    /**
     * 登录记录，每天首次登录添加积分
     */
    @Override
    public boolean loginRecord(User user, String clientIp) throws Exception {
        EventUtils.postLatestLogin(user.getLoginId().intValue(), user.getLoginAccount(), clientIp);

        // 登录记录
        // LatestLogin latestLogin = new LatestLogin();
        // latestLogin.setN_LoginId(user.getLoginId().intValue());
        // latestLogin.setLoginAccount(user.getLoginAccount());
        // latestLogin.setLoginIp(clientIp);
        //
        // latestLoginRemoteService.addLatestLogin(latestLogin, 3);

        return true;
    }


    /**
     * 根据loginId查询用户信息
     */
    @Override
    public User queryUserByLoginId(String loginId) throws ServiceException {

        try {
            return this.loginDao.queryUserByLoginId(loginId);
        } catch (SQLException e) {
            logger.error("通过loginID查询用户失败" + e.getMessage(), e);
            throw new ServiceException(0, e.getMessage());
        }
    }

    @Override
    public User findLoginInfoByOrderCode(Map<String, Object> map) throws ServiceException {

        try {
            return this.loginDao.findLoginInfoByOrderCode(map);
        } catch (SQLException e) {
            logger.error("通过MAP查询用户失败" + e.getMessage(), e);
            throw new ServiceException(0, e.getMessage());
        }
    }

    /**
     * 根据验证手机查询用户信息
     */
    @Override
    public List<User> queryListByMobile(String mobile) throws ServiceException {

        try {
            User user = new User();
            user.setMobile(mobile);
            return loginDao.queryListByMobile(user);
        } catch (SQLException e) {
            logger.error("根据验证手机查询用户信息失败" + e.getMessage(), e);
            throw new ServiceException(0, e.getMessage());
        }
    }

    /**
     * 根据用户名、邮箱、手机查询用户信息
     */
    @Override
    public User queryUserInfoByNEM(String loginAccount) throws ServiceException {

        try {
            return loginDao.queryUserInfoByNEM(loginAccount);
        } catch (SQLException e) {
            logger.error("根据用户名、邮箱、手机查询用户信息失败" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据用户名、邮箱、手机查询用户信息 包括禁用账户
     */
    @Override
    public User queryAllUserInfoByName(String loginAccount) throws Exception {

        return loginDao.queryAllUserInfoByName(loginAccount);
    }

    @Override
    public IMemberInfo findWebservice() throws Exception {
        IMemberInfo ie = null;
        if (null == ie) {
            throw new Exception("获取时代Webservice接口异常");
        }
        return ie;
    }

    @Override
    public EraInfo findUserOBJ(String userCode) throws ServiceException {

        EraInfo n = null;
        try {
            n = loginDao.findTimesInfo(userCode);
        } catch (SQLException e) {
            logger.error("查询失败", e);
            throw new ServiceException(e);
        }
        return n;
    }

    @Override
    public Boolean updateEraInfo(EraInfo einfo) throws ServiceException {

        try {

            UserBaseInfo userBaseInfo = new UserBaseInfo();
            userBaseInfo.setLoginAccount(einfo.getLoginAccount().toLowerCase());
            userBaseInfo.setPassword(einfo.getLoginPassword());

            userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "login");

            if (null == userBaseInfo) {
                logger.error("更新时代会员({})信息失败:查询加盐信息为空", einfo.getLoginAccount().toLowerCase());
                return false;
            } else {
                einfo.setLoginPassword(userBaseInfo.getPassword());
            }
            // 更新时代会员信息
            eraInfoRemoteService.updateUseEraInfo(einfo);
            // 更新登录用户信息
            User user = new User();
            user.setLoginId(einfo.getLoginId().longValue());
            user.setLoginPassword(einfo.getLoginPassword());
            return this.loginDao.updateTempUserInfo(user);
        } catch (Exception e) {
            logger.error("更新时代会员({})信息异常", einfo.getLoginAccount().toLowerCase(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Boolean insertEraInfo(EraInfo einfo) throws ServiceException {

        // EraInfoRemoteService eraInfoRemoteService;
        try {
            // eraInfoRemoteService = (EraInfoRemoteService) RemoteTool
            // .getRemote(Constants.REMOTE_SERVICE_CUSTOMER, "eraInfoRemoteService");

            einfo = this.passwordTwiceEncryption(einfo);

            return eraInfoRemoteService.registerEraInfo(einfo,
                    Constants.CUSTOMER_TYPE_SD_MEMBER) > 0;
        } catch (Exception e) {
            logger.error("更新时代会员信息失败", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Boolean updateRemoteEraInfo(EraInfo einfo) throws ServiceException {

        // EraInfoRemoteService eraInfoRemoteService;
        try {

            // eraInfoRemoteService = (EraInfoRemoteService) RemoteTool
            // .getRemote(Constants.REMOTE_SERVICE_CUSTOMER, "eraInfoRemoteService");
            return eraInfoRemoteService.updateUseEraInfo(einfo) > 0;
        } catch (Exception e) {
            logger.error("更新时代会员信息失败", e);
            throw new ServiceException(e);
        }
    }

    /**
     * 根据登录帐号实时获取时代会员折扣
     */
    @Override
    public BigDecimal getactualDiscount(String loginNo) throws ServiceException {
        BigDecimal disCount = BigDecimal.ONE;
        IMemberInfo ii = null;
        try {
            ii = findWebservice();
            String jsonString = ii.memberGetInfo(loginNo);
            if (null == jsonString) {
                return disCount;
            }
            EraInfo einfo = getEraInfoOBJ(JSONObject.parseObject(jsonString));
            if (einfo.getEraGradeRate() != null) {
                disCount = einfo.getEraGradeRate();
            }
            if (null == disCount) {
                disCount = BigDecimal.ONE;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return disCount.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).setScale(2);
    }

    @Override
    public User selectOrganInfo(Map map) throws ServiceException {

        try {
            return loginDao.selectOrganInfo(map);
        } catch (SQLException e) {
            logger.error("通过用户ID查询组织机构异常" + e.getMessage(), e);
            throw new ServiceException(0, e.getMessage());
        }
    }

    /**
     * 查询用户手机号码
     */
    @Override
    public Map<String, String> queryUserMobileForVShop(Long loginId) throws ServiceException {

        try {
            return loginDao.queryUserMobileForVShop(loginId);
        } catch (Exception e) {
            logger.error("查询用户手机号码异常" + e.getMessage(), e);
            throw new ServiceException(0, e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    @Override
    public User userLogin(User params) throws ServiceException {

        try {
            UserBaseInfo userBaseInfo = new UserBaseInfo();
            userBaseInfo.setLoginId(params.getLoginId());
            userBaseInfo.setLoginAccount(params.getLoginAccount());
            userBaseInfo.setMobile(params.getMobile());

            userBaseInfo.setPassword(params.getLoginPassword());

            userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "login");

            if (null == userBaseInfo) {
                return null;
            } else {
                params.setLoginPassword(userBaseInfo.getPassword());
                return loginDao.userLogin(params);
            }
        } catch (Exception e) {
            throw new ServiceException(0, e.getMessage());
        }
    }

    @Override
    public EraInfo queryEraInfoForWebService(String loginName, String loginPass) {
        EraInfo ei = null;

        logger.info("获取时代会员({})信息", loginName);
        try {
            // 时代密码加密少了2位
            IMemberInfo im = findWebservice();
            String userCode = im.memberLoginValid(loginName, loginPass.substring(0, 30));
            if (null != userCode && userCode.length() > 3) {
                String jsonString = StringUtil.toNormalStr(im.memberGetInfo(userCode));
                logger.info("获取时代会员({})信息,远程接口返回：{}", loginName, jsonString);
                ei = getEraInfoOBJ(JSONObject.parseObject(jsonString));
            } else {
                logger.info("获取时代会员({})信息,远程接口返回码为：{}", loginName,
                        CheckEraInfoMap.getValue(userCode));
            }
        } catch (Exception e) {
            logger.error("获取时代会员({})信息异常", loginName, e);
        }

        return ei;
    }

    /**
     * 时代用户登录
     */
    @Override
    public User timeLogin(String loginName, String loginPass, int registerDevice)
            throws ServiceException {
        User userInfo = null;
        try {

            // 时代密码加密少了2位
            // 如果B2B不存在或密码错误才去查询易创网
            User user = new User();
            user.setLoginAccount(loginName);
            user.setLoginPassword(loginPass.substring(0, 30));

            user = this.userLogin(user);
            if (null != user) {
                logger.info("获取时代会员({})信息,B2B存在该用户直接返回:{}", loginName, user.toString());
                return user;
            } else {
                EraInfo ei = this.queryEraInfoForWebService(loginName, loginPass);

                if (null != ei) {
                    ei.setRegister_Device(registerDevice);
                    ei.setRegister_Platfrom(Constants.REGISTER_PLATFORM_ERA);
                    ei.setLoginAccount(ei.getEraNo().toLowerCase());

                    EraInfo eiTemp = null;

                    if ((null == (eiTemp =
                            eraInfoDAO.queryEraInfoByEraNo(ei.getEraNo().toLowerCase())))) {

                        ei = this.passwordTwiceEncryption(ei);
                        int tem = eraInfoRemoteService.registerEraInfo(ei,
                                Constants.CUSTOMER_TYPE_SD_MEMBER);

                        if (tem > 0 && null != (eiTemp =
                                eraInfoDAO.queryEraInfoByEraNo(ei.getEraNo().toLowerCase()))) {
                            registservice.doAfterRegist(6, eiTemp.getLoginId().longValue(),
                                    eiTemp.getLoginAccount(), null, null);
                        }
                    } else {
                        ei.setEraInfoId(eiTemp.getPersonalId());
                        ei.setLoginId(eiTemp.getLoginId());
                        updateEraInfo(ei);
                    }
                    userInfo = loginDao.queryUserByLoginAccount(
                            (null == eiTemp || null == eiTemp.getLoginAccount())
                                    ? loginName.toLowerCase() : eiTemp.getLoginAccount());
                    if (null != userInfo && null != eiTemp) {
                        com.kmzyc.b2b.vo.EraInfo tempEraInfo =
                                eraInfoDAO.queryEraInfoByEraNo(eiTemp.getEraNo());
                        com.kmzyc.b2b.vo.EraInfo sbEi = new com.kmzyc.b2b.vo.EraInfo();
                        sbEi.setEraNo(eiTemp.getEraNo());
                        sbEi.setEraInfoId(null == eiTemp.getPersonalId() ? new BigDecimal(0)
                                : eiTemp.getPersonalId());
                        sbEi.setLoginAccount(tempEraInfo.getLoginAccount());
                        userInfo.setEarInfo(sbEi);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("时代会员登录异常", e);
            throw new ServiceException(0, e.getMessage());
        }
        return userInfo;
    }

    @Override
    public EraInfo getEraInfoOBJ(JSONObject jobj) {

        EraInfo eif = new EraInfo();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            eif.setBirthday(sdf.parse(jobj.get("Birthday").toString()));
            eif.setCertificateNumber(jobj.get("PaperNumber").toString());
            eif.setContactInformation(jobj.get("MobileTele").toString());
            eif.setEraGradeName(jobj.get("Level").toString());
            eif.setEraGradeRate(new BigDecimal(jobj.get("Discount").toString()));
            eif.setEraNo(jobj.get("Number").toString().toLowerCase());
            eif.setRecommendedNo(jobj.get("Direct").toString());
            eif.setSex(jobj.get("Sex").toString());
            eif.setName(jobj.get("Name").toString());
            eif.setNickname(jobj.get("PetName").toString());
            eif.setEraId(Long.valueOf(jobj.get("ID").toString()));
            eif.setExpIntegralValue(new BigDecimal(jobj.get("Score").toString()));
            eif.setModifyDate(new Date());
            if (StringUtil.isEmpty(jobj.get("LoginPass"))) {
                // 默认密码 #gedy*&^
                eif.setLoginPassword("85be2fc381e1e1b8a4a85eab049e3a04");
                logger.info("时代用户({})信息数据转换失败：接口返回登录密码为空,使用默认密码 #gedy*&^", eif.getEraNo());
            } else {
                eif.setLoginPassword(jobj.get("LoginPass").toString().toLowerCase());
            }
            eif.setOperation(jobj.getInteger("OperationType"));
            eif.setLoginAccount(eif.getEraNo());
            eif.setPapertype(jobj.getString("PaperType"));
        } catch (Exception e) {
            logger.error("时代用户信息数据转换异常", e);
            return null;
        }
        return eif;
    }

    /**
     * 登录密码二次加密
     * 
     * @param eraInfo
     * @return
     */
    private EraInfo passwordTwiceEncryption(EraInfo eraInfo) {
        // 登录密码二次加密
        Map<String, String> saltMap =
                StringUtil.generatePasswordTwiceEncryption(eraInfo.getLoginPassword());
        // 重新设置登录密码
        eraInfo.setLoginSalt(saltMap.get("saltStr"));
        eraInfo.setLoginPassword(saltMap.get("twicePassword"));

        // 支付密码加密串
        saltMap = StringUtil.generatePasswordTwiceEncryption("");
        eraInfo.setPaySalt(saltMap.get("saltStr"));

        return eraInfo;
    }

    /**
     * 根据用户卡号查询用户信息
     *
     * @param cardNum
     * @return User
     * @throws ServiceException
     */
    /*
     * public User queryUserByCardNum(String cardNum) throws ServiceException {
     * 
     * try { return loginDao.queryUserByCardNum(cardNum); } catch (SQLException e) {
     * logger.error("根据用户卡号查询用户信息失败" + e.getMessage(), e); return null; } }
     */

    /**
     * 登录后事件
     */
    @Override
    public void doAfterLogin(User pUser, final String tempId, final String platform,
            final String ip) throws ServiceException {
        final User user = pUser;
        shopCartInfoService.mergeShopCar(user.getLoginId().toString(), tempId);// 合并购物车
        EventUtils.postLatestLogin(user.getLoginId().intValue(), user.getLoginAccount(), ip);
        // taskExecutor.execute(new Runnable() {
        // public void run() {
        //
        // try {
        //
        // // 登录记录
        // LatestLogin latestLogin = new LatestLogin();
        // latestLogin.setN_LoginId(user.getLoginId().intValue());
        // latestLogin.setLoginAccount(user.getLoginAccount());
        // latestLogin.setLoginIp(ip);
        // latestLoginRemoteService.addLatestLogin(latestLogin, 3);
        // } catch (Exception e) {
        // logger.error("doAfterLogin异常", e);
        // }
        // }
        // });
    }

    /*
     * public Map<String, String> queryWebServiceCardNumAndMobile(String cardNum, String mobile)
     * throws ServiceException { Map<String, String> kmResult = null; logger.info(
     * "查询总部是否有此用户，入参  卡号：" + cardNum + "，手机号：" + mobile); org.apache.axis.client.Service service =
     * new org.apache.axis.client.Service(); Call call = null; String result = "";
     * 
     * try { call = (Call) service.createCall(); // 设置端口号 call.setTargetEndpointAddress(new
     * java.net.URL(ConfigurationUtil .getString("webservice_HQ_member_endpoint"))); //
     * 设置wsdl中所提供的方法名称 call.setOperationName(new QName(ConfigurationUtil
     * .getString("webservice_HQ_member_nameSpace"), "getMemByWec")); // 添加参数
     * call.addParameter("arg0", org.apache.axis.encoding.XMLType.XSD_STRING,
     * javax.xml.rpc.ParameterMode.IN); // 设置返回类型为String
     * call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型 // 执行结果 result =
     * (String) call .invoke(new Object[] {WsReturnResult.returnXmlStr(cardNum, mobile)}); // result
     * = // "<?xml version='1.0' encoding='TF-8' //
     * ?><RESULT><ROW><BILLNUM></BILLNUM><RETURNCODE>1</RETURNCODE><MESSAGECONTENT>13246711111</
     * MESSAGECONTENT></ROW></RESULT>";
     * 
     * System.out.println("执行结果:" + result); logger.info("查询总部用户执行结果:" + result); } catch (Exception
     * e) { logger.error("查询总部用户异常：" + e.getMessage()); return null; } if
     * (result.contains("<RETURNCODE>1</RETURNCODE>")) { try { result = result.substring(47,
     * result.length() - 9); kmResult = XMLHelper.singleLevelAnalyze(result);// 该xml解析只能解析单层的 }
     * catch (Exception e) { return null; } } else { return null; } return kmResult; }
     */

    @Override
    public User queryUser(User user) throws ServiceException {

        try {
            return this.loginDao.queryUser(user);
        } catch (SQLException e) {
            logger.error("查询用户信息异常", e);
            throw new ServiceException(0, e.getMessage());
        }
    }

}
