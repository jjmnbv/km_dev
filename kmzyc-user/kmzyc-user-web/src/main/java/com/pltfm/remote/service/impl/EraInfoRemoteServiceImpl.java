package com.pltfm.remote.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.user.remote.service.EraInfoRemoteService;
import com.kmzyc.util.StringUtil;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.dao.EraInfoDAO;
import com.pltfm.app.dao.HealthYgenericInfoDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.PersonalBasicInfoDAO;
import com.pltfm.app.dao.PersonalityInfoDAO;
import com.pltfm.app.dataobject.UserLevelDO;
import com.pltfm.app.service.SaltInfoService;
import com.pltfm.app.service.UserLevelService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.RSA;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.HealthYgenericInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.SaltInfo;

import net.sf.json.JSONObject;
import redis.clients.jedis.JedisCluster;

/* import com.pltfm.app.service.BaseDockService; */

@SuppressWarnings("unchecked")
@Service(value = "eraInfoRemoteService")
public class EraInfoRemoteServiceImpl implements EraInfoRemoteService {
    private Logger logger = LoggerFactory.getLogger(EraInfoRemoteServiceImpl.class);
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Resource(name = "eraInfoDAOImpl")
    private EraInfoDAO eraInfoDAO;



    @Resource(name = "customerRemoteService")
    private CustomerRemoteService customerRemoteService;
    /**
     * 个人信息DAO接口
     */
    @Resource(name = "personalBasicInfoDAO")
    private PersonalBasicInfoDAO personalBasicInfoDAO;
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
     * 健康信息DAO接口
     */
    @Resource(name = "healthYgenericInfoDAO")
    private HealthYgenericInfoDAO healthYgenericInfoDAO;

    /**
     * 登录信息DAO接口
     */
    @Resource(name = "loginInfoDAO")
    private LoginInfoDAO loginInfoDAO;
    /** 总部信息推送 */
    /*
     * 删除总部会员推送 @Resource private BaseDockService baseDockService;
     */

    @Resource
    private JedisCluster jedis;

    @Resource
    private SaltInfoService saltInfoService;

    /**
     * 个性信息DAO接口
     */
    @Resource(name = "personalityInfoDAO")
    private PersonalityInfoDAO personalityInfoDAO;
    // 时代会员请求地址
    private static final String SOAPBINDINGADDRESS = "";
    // 远程时代接口请求命名空间
    private static final String QNAMESPACE = "";
    // 获取远程时代会员信息请求action
    private static final String AOAPACTION = "";
    // 获取远程时代会员请求方法
    private static final String AOAPElEMENT = "";


    /**
     * 返回值 Integer -1时代会员主键id和编号不能为空， -----0 失败，----成功 -主键ID值
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer registerEraInfo(EraInfo eraInfo, Integer type) throws Exception {
        int result = 0;

        String lockKey = "JedisLock_";

        try {
            if (eraInfo.getEraNo() != null && eraInfo.getEraId() != null) {
                lockKey =lockKey.concat(eraInfo.getEraNo()).intern();
                Long i = jedis.setnx(lockKey, "1");
                if (i > 0) {
                    jedis.expire(lockKey, 60 * 60 * 3); // 缓存过期 3小时
                    if (null != eraInfo.getLoginAccount()
                            && eraInfo.getLoginAccount().length() > 0) {
                        eraInfo.setLoginAccount(eraInfo.getLoginAccount().toLowerCase());
                    }
                    LoginInfo loginInfo = new LoginInfo();
                    loginInfo.setLoginAccount(eraInfo.getEraNo().toLowerCase());
                    loginInfo.setN_CustomerTypeId(type);
                    loginInfo.setN_Status(1);
                    loginInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
                    loginInfo.setLoginPassword(eraInfo.getLoginPassword());

                    Integer loginId = loginInfoDAO.insert(loginInfo);


                    // 密码加盐信息
                    SaltInfo saltInfo = new SaltInfo();
                    saltInfo.setLoginId(Long.valueOf(loginId));
                    saltInfo.setLoginSalt(eraInfo.getLoginSalt());
                    saltInfo.setPaySalt(eraInfo.getPaySalt());
                    this.saltInfoService.insertSaltInfo(saltInfo);

                    // 更新客户等级
                    UserLevelDO userLevelDO = new UserLevelDO();
                    userLevelDO.setLoginId(loginId);

                    userLevelDO.setCustomerTypeId(type);
                    userLevelDO.setExpend(BigDecimal.ZERO);
                    userLevelService.searchUserLevelUpdateInfo(userLevelDO);
                    // 增加账户信息
                    AccountInfo record = new AccountInfo();
                    record.setN_LoginId(loginId);
                    record.setAccountLogin(loginInfo.getLoginAccount());
                    /** 注册的平台，1：机构 2:微商,3：时代，4：总部，5：砍价，6：51返利，7：cps,8:常规(1和2不在portal项目) **/
                    record.setRegister_Platfrom(eraInfo.getRegister_Platfrom());
                    record.setRegister_Device(eraInfo.getRegister_Device());
                    record.setN_CustomerTypeId(type);
                    record.setN_Status(1);
                    Date cdate = DateTimeUtils.getCalendarInstance().getTime();
                    record.setD_CreateDate(cdate);
                    record.setD_ModifyDate(cdate);
                    accountInfoDAO.insert(record);
                    // 增加基本信息
                    PersonalBasicInfo p = new PersonalBasicInfo();
                    p.setN_LoginId(loginId);
                    int personId = personalBasicInfoDAO.insert(p);

                    // 增加个性化信息
                    PersonalityInfo pi = new PersonalityInfo();
                    pi.setN_LoginId(loginId);
                    pi.setLastYear_Amount(0d);
                    pi.setNickname(loginInfo.getLoginAccount());
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

                    // 同步时代直销会员信息
                    Date tdate = DateTimeUtils.getCalendarInstance().getTime();
                    eraInfo.setCreateDate(tdate);
                    eraInfo.setModifyDate(tdate);
                    eraInfo.setLoginId(new BigDecimal(loginId));
                    eraInfo.setPersonalId(new BigDecimal(personId));
                    BigDecimal erId = eraInfoDAO.insert(eraInfo);
                    logger.info("------时代会员添加成功" + erId);
                    result = erId.intValue();
                    /*
                     * 删除推送总部会员系统 if (loginId != null) {// 当成功添加loginInfo信息时向总部推送数据 UserInfoDO
                     * userInfoDO =
                     * customerRemoteService.selectUserInfoByLoginId(loginId.longValue());
                     * baseDockService.pushBaseDockData(0, BaseDockType.USER_REGISTER.getValue(),
                     * userInfoDO.toKmUserString()); }
                     */
                } else {
                    logger.info("key:{},加锁失败，申请时代会员缓存正在创建,请稍候在试。", eraInfo.getEraNo());
                    result = 0;
                }
            } else {
                result = -1;
                logger.info("------时代会员ID和编号不能为空");
            }
        } catch (Exception e) {
            logger.error("时代会员数据添加失败" + e.getMessage(), e);
            result = 0;
            jedis.del(lockKey);
            throw new Exception("时代会员数据添加失败" + e.getMessage());
        }
        return result;
    }

    // 不同步更新user时代会员信息
    @Override
    public Integer updateUseEraInfo(EraInfo eraInfo) throws Exception {
        if (eraInfo.getEraNo() == null) {
            logger.error("更新时代会员信息传入参数异常");
            return 0;
        }
        if (null != eraInfo.getLoginAccount() && eraInfo.getLoginAccount().length() > 0) {
            eraInfo.setLoginAccount(eraInfo.getLoginAccount().toLowerCase());
        }
        int rows = eraInfoDAO.updateByPrimaryKeySelective(eraInfo);
        if (rows > 0) {
            logger.info("更新时代会员信息成功");
        }
        return rows;
    }


    /**
     * 更新同步时代会员信息(-1:参数为空，1:修改成功，0：失败)
     */
    @Override
    public Integer updateEraInfo(EraInfo eraInfo) throws Exception {
        if (eraInfo.getEraInfoId() == null || eraInfo.getEraNo() == null) {
            logger.error("更新时代会员信息传入参数异常");
            return 0;
        }
        EndpointReference endpointReference = new EndpointReference(SOAPBINDINGADDRESS);
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace namespace = factory.createOMNamespace(QNAMESPACE, "xsd_String");
        OMElement nameElement = factory.createOMElement("number", namespace);
        nameElement.setText(eraInfo.getEraNo());
        OMElement method = factory.createOMElement(AOAPElEMENT, namespace);
        method.addChild(nameElement);

        OMElement nameElement2 = factory.createOMElement("sign", namespace);
        String sign = RSA.sign("number=" + eraInfo.getEraNo(),"", "utf-8");
        nameElement2.setText(sign);
        method.addChild(nameElement2);

        Options options = new Options();
        options.setAction(AOAPACTION);
        options.setTo(endpointReference);
        options.setManageSession(true);
        options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, true);
        options.setTimeOutInMilliSeconds(5000L);

        ServiceClient sender;
        sender = new ServiceClient();
        sender.setOptions(options);
        OMElement result = sender.sendReceive(method);
        sender.cleanupTransport();
        Iterator iter = result.getChildElements();
        String returnVal = null;
        while (iter.hasNext()) {
            OMNode omNode = (OMNode) iter.next();
            if (omNode.getType() == OMNode.ELEMENT_NODE) {
                OMElement omElement = (OMElement) omNode;
                returnVal = omElement.getText().trim();
            }
        }
        int rows = 0;
        if (StringUtils.isBlank(returnVal)) {
            logger.info("获取时代信息失败");
        } else {
            JSONObject obj = JSONObject.fromObject(returnVal);
            logger.info("----返回参数：-------" + obj);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            eraInfo.setBirthday(sdf.parse(obj.get("Birthday").toString()));
            // 证件号码
            eraInfo.setCertificateNumber(obj.get("PaperNumber").toString());
            // 联系电话
            eraInfo.setContactInformation(obj.get("MobileTele").toString());
            eraInfo.setEraGradeName(obj.get("Level").toString());
            // 时代等级折扣
            eraInfo.setEraGradeRate(new BigDecimal(obj.get("Discount").toString()));
            // 时代会员编号
            eraInfo.setEraNo(obj.get("Number").toString());
            // 推荐人编号
            eraInfo.setRecommendedNo(obj.get("Direct").toString());
            // 性别
            eraInfo.setSex(obj.get("Sex").toString());
            // 真实姓名
            eraInfo.setName(obj.get("Name").toString());
            // 昵称
            eraInfo.setNickname(obj.get("PetName").toString());
            // 时代会员id
            eraInfo.setEraId(Long.parseLong(obj.get("ID").toString()));
            // 体验积分
            eraInfo.setExpIntegralValue(new BigDecimal(obj.get("Score").toString()));
            // 证件类型
            eraInfo.setPapertype(obj.getString("PaperType").toString());
            // 修改时间
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            eraInfo.setModifyDate(sdf.parse(sdf.format(new Date())));
            rows = eraInfoDAO.updateByPrimaryKeySelective(eraInfo);
            if (rows > 0) {
                logger.info("更新时代会员信息成功");
            } else {
                logger.info("更新时代会员信息失败");
            }
        }
        return rows;
    }

    /**
     * 更新会员账号信息
     * 
     * @param loginInfo
     * @return
     * @throws Exception (-1:参数为空，1:修改成功，0：失败)updateByPrimaryKey
     */
    @Override
    public Integer updateLogin(LoginInfo loginInfo) throws Exception {
        int result = 0;
        try {
            if (loginInfo.getN_LoginId() != null) {
                AccountInfo accountInfo = new AccountInfo();
                EraInfo eraInfo = new EraInfo();
                if (null != loginInfo.getLoginAccount()
                        && loginInfo.getLoginAccount().length() > 0) {
                    loginInfo.setLoginAccount(loginInfo.getLoginAccount().toLowerCase());
                    accountInfo.setAccountLogin(loginInfo.getLoginAccount());
                    eraInfo.setLoginAccount(loginInfo.getLoginAccount());
                }
                String email = loginInfo.getEmail();
                loginInfo.setEmail("");
                result = loginInfoDAO.updateByPrimaryKey(loginInfo);

                accountInfo.setN_LoginId(loginInfo.getN_LoginId());
                accountInfo.setEmail(email);
                accountInfoDAO.updateByLoginId(accountInfo);

                eraInfo.setLoginId(new BigDecimal(loginInfo.getN_LoginId()));
                eraInfoDAO.updateByLoginIdSelective(eraInfo);
                logger.info("修改成功！");
            } else {
                result = -1;
                logger.info("修改失败！登录账号id不能为空！");
            }
        } catch (Exception e) {
            logger.error("修改会员账号失败！" + e.getMessage(), e);
            result = 0;
        }
        return result;
    }

    /**
     * 同步时代会员信息
     * 
     * @param eraInfos
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfoForTimes(List<EraInfo> eraInfos) throws Exception {

        try {

            for (int i = 0; i < eraInfos.size(); i++) {

                EraInfo eraInfo = eraInfos.get(i);

                if (!StringUtil.isEmpty(eraInfo.getEraNo())
                        && !StringUtil.isEmpty(eraInfo.getEraId())
                        && !StringUtil.isEmpty(eraInfo.getOperation())) {


                    EraInfo eraInfoTemp = this.eraInfoDAO.selectByEraNo(eraInfo.getEraNo());
                    if (null != eraInfoTemp) {
                        // 修改时代会员信息
                        this.updateUseEraInfo(eraInfo);

                        // 修改登录信息
                        LoginInfo loginInfo = new LoginInfo();
                        loginInfo.setN_LoginId(eraInfoTemp.getLoginId().intValue());
                        if (eraInfo.getOperation().intValue() == 20) {// 已注销的账户信息
                            loginInfo.setN_Status(0);// 禁用账户
                        }else{
                            loginInfo.setN_Status(1);// 启用账户
                        }

                        UserBaseInfo userBaseInfo = new UserBaseInfo();
                        userBaseInfo.setLoginAccount(eraInfo.getEraNo());
                        userBaseInfo.setPassword(eraInfo.getLoginPassword());

                        userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo,"login");

                        if (null == userBaseInfo) {
                            throw new Exception("更新时代会员信息失败:("+eraInfo.getLoginAccount()+")缺少加盐信息");
                        } else {
                            loginInfo.setLoginPassword(userBaseInfo.getPassword());

                        }

                        this.loginInfoDAO.updateByPrimaryKeySelective(loginInfo);

                    } else {

                        // 登录密码二次加密
                        Map<String, String> saltMap = StringUtil
                                .generatePasswordTwiceEncryption(eraInfo.getLoginPassword());
                        // 重新设置登录密码
                        eraInfo.setLoginSalt(saltMap.get("saltStr"));
                        eraInfo.setLoginPassword(saltMap.get("twicePassword"));

                        int rs = this.registerEraInfo(eraInfo, 2);
                        if(rs <= 0){
                            logger.error("更新时代会员信息失败:注册失败");
                            throw new Exception("更新时代会员信息失败:注册失败");
                        }
                    }

                } else {
                    logger.error("更新时代会员信息失败:参数缺失");
                    throw new Exception("更新时代会员信息失败:参数缺失");
                }

            }
        } catch (Exception e) {
            logger.error("更新时代会员信息异常", e);
            throw new Exception(e);
        }

    }
}
