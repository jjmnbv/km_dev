package com.pltfm.remote.service.impl;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.user.remote.service.SpecialistRemoteService;
import com.kmzyc.user.remote.service.UserGrowingService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.dao.CommercialTenantBasicCopyDAO;
import com.pltfm.app.dao.CommercialTenantBasicInfoDAO;
import com.pltfm.app.dao.HealthYgenericInfoDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.LoginRoseRelDAO;
import com.pltfm.app.dao.MdicalExcusieInfoDAO;
import com.pltfm.app.dao.PersonalBasicInfoDAO;
import com.pltfm.app.dao.PersonalityInfoDAO;
import com.pltfm.app.dataobject.LoginRoseRelDO;
import com.pltfm.app.dataobject.RankDO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.dataobject.UserLevelDO;
import com.pltfm.app.service.RankService;
import com.pltfm.app.service.UserLevelService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.MD5;
import com.pltfm.app.util.RemoteInvoking;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.LoginInfoExample;
import com.pltfm.app.vobject.MdicalExcusieInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.Specialist;

/**
 * 专家列表信息远程接口实现类
 * 
 * @author
 * @since 2013-8-7
 */
@SuppressWarnings("unchecked")
@Component(value = "specialistRemoteService")
public class SpecialistRemoteServiceImpl implements SpecialistRemoteService {

    /**
     * 
     */
    private static final long serialVersionUID = -8769028796124870987L;
    /**
     * 日志类
     */
    Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String ruleCode = ConfigurationUtil.getString("userScoreCode");

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
     * 客户等级业务逻辑接口
     */
    @Resource(name = "userLevelService")
    private UserLevelService userLevelService;

    /**
     * 个性信息DAO接口
     */
    @Resource(name = "personalityInfoDAO")
    private PersonalityInfoDAO personalityInfoDAO;

    /**
     * 头衔业务逻辑接口
     */
    private RankService rankService;
    /**
     * 医属信息DAo接口
     */
    @Resource(name = "mdicalExcusieInfoDAO")
    private MdicalExcusieInfoDAO mdicalExcusieInfoDAO;
    @Resource(name = "loginRoseRelDAO")
    private LoginRoseRelDAO loginRoseRelDAO;
    @Resource(name = "commercialTenantBasicInfoDAO")
    private CommercialTenantBasicInfoDAO commercialTenantBasicInfoDAO;
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
     * 供应商信息审核DAO接口
     */
    @Resource(name = "commercialTenantBasicCopyDAO")
    private CommercialTenantBasicCopyDAO commercialTenantBasicCopyDAO;

    @Resource(name = "userGrowingService")
    private UserGrowingService userGrowingService;

    @Autowired
    private CouponRemoteService couponRemoteService;

    /**
     * 按专家客户信息条件查询专家客户信息
     * 
     * @param specialist 客户信息条件
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public Specialist getSpecialist_id(Specialist specialist, Integer type) throws Exception {
        log.warn(RemoteInvoking.remoteInvokingType(type));
        return personalBasicInfoDAO.selectPageBySpecialist(specialist);
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

        if (loginInfo != null) {
            if (loginInfo.getLoginAccount() == null
                    || loginInfo.getLoginAccount().trim().length() > 20
                    || loginInfo.getLoginAccount().trim().length() < 6) {
                messages.put("loginAccount", "请输入合法的登录账号");
            }

            LoginInfoExample example = new LoginInfoExample();
            example.createCriteria().andLoginAccountEqualTo(loginInfo.getLoginAccount());
            List<LoginInfo> list = loginInfoDAO.selectByExample(example);
            if (list != null && list.size() > 0) {
                messages.put("loginAccount", "登录账号已存在");
            }

            if (loginInfo.getLoginPassword() == null || loginInfo.getLoginPassword().length() > 20
                    || loginInfo.getLoginPassword().length() < 6) {
                messages.put("loginPassword", "请输入合法的登录密码");
            }
            if (loginInfo.getMobile() == null) {
                messages.put("mobile", "请输入合法的手机号码");
            } else {
                // 手机正则表达式
                Pattern mobilePattern =
                        Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

                Matcher mobileMatcher = mobilePattern.matcher(loginInfo.getMobile().trim());

                if (!mobileMatcher.matches()) {
                    messages.put("mobile", "请输入合法的手机号码");
                }
            }

            if (loginInfo.getEmail() == null) {
                messages.put("email", "请输入合法的邮箱");
            } else {
                // 邮箱正则表达式
                Pattern emailPattern = Pattern.compile(
                        "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$");

                Matcher emailMatcher = emailPattern.matcher(loginInfo.getEmail().trim());

                if (!emailMatcher.matches()) {
                    messages.put("email", "请输入合法的邮箱");
                }
            }
            if (messages.size() > 0) {
                return messages;
            }

            // 启用状态
            loginInfo.setN_Status(1);
            // 创建日期
            loginInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
            // MD5加密
            loginInfo.setLoginPassword(MD5.md5crypt(loginInfo.getLoginPassword()));

            Integer loginId = loginInfoDAO.insert(loginInfo);

            // 更新客户等级
            UserLevelDO userLevelDO = new UserLevelDO();
            userLevelDO.setLoginId(loginId);
            userLevelDO.setCustomerTypeId(loginInfo.getN_CustomerTypeId());
            userLevelDO.setExpend(BigDecimal.ZERO);
            userLevelService.searchUserLevelUpdateInfo(userLevelDO);

            log.warn(RemoteInvoking.remoteInvokingType(type));
        } else {
            messages.put("loginInfo", "请输入登录信息");
            return messages;
        }

        return null;
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
     * 注册医务信息
     * 
     * @param mdicalExcusieInfo 医务信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> registerMdicalExcusie(MdicalExcusieInfo mdicalExcusieInfo,
            Integer type) throws Exception {
        Map<String, String> messages = new HashMap<String, String>();
        if (mdicalExcusieInfo != null) {

            if (mdicalExcusieInfo.getName() != null) {
                if (mdicalExcusieInfo.getName().trim().length() > 16) {
                    messages.put("name", "请输入合法的姓名");
                }
            }

            if (mdicalExcusieInfo.getTheCity() != null) {
                if (mdicalExcusieInfo.getTheCity().trim().length() > 16) {
                    messages.put("thecity", "请输入合法的所在地");
                }
            }

            if (mdicalExcusieInfo.getTheHospital() != null) {
                if (mdicalExcusieInfo.getTheHospital().trim().length() > 16) {
                    messages.put("theHospital", "请输入合法的医院名称");
                }
            }

            if (mdicalExcusieInfo.getHospitalLevel() != null) {
                if (mdicalExcusieInfo.getHospitalLevel().trim().length() > 16) {
                    messages.put("hospitalLevel", "请输入合法的医院等级");
                }
            }

            if (mdicalExcusieInfo.getTheDepartment() != null) {
                if (mdicalExcusieInfo.getTheDepartment().trim().length() > 16) {
                    messages.put("theDepartment", "请输入合法的科室");
                }
            }

            if (mdicalExcusieInfo.getProfessionName() != null) {
                if (mdicalExcusieInfo.getProfessionName().trim().length() > 16) {
                    messages.put("professionName", "请输入合法的职业称号");
                }
            }
            if (mdicalExcusieInfo.getProfessionalExprtise() != null) {
                if (mdicalExcusieInfo.getProfessionalExprtise().trim().length() > 16) {
                    messages.put("professionalExprtise", "请输入合法的专长");
                }
            }
            if (mdicalExcusieInfo.getCertificateNumber() != null) {
                if (mdicalExcusieInfo.getCertificateNumber().trim().length() > 16) {
                    messages.put("certificateNumber", "请输入合法的证书编号");
                }
            }
            if (mdicalExcusieInfo.getAuditingPhone() != null) {
                if (mdicalExcusieInfo.getAuditingPhone().trim().length() > 9) {
                    messages.put("auditingPhone", "请输入合法的审核电话");
                }
            }
            if (messages.size() > 0) {
                return messages;
            }

            // 添加医务信息信息
            mdicalExcusieInfo.setD_createDate(DateTimeUtils.getCalendarInstance().getTime());
            mdicalExcusieInfoDAO.insertMdicalExcusieInfo(mdicalExcusieInfo);
        } else {
            messages.put("mdicalExcusieInfo", "请输医务信息信息");
            return messages;
        }

        return null;
    }

    /**
     * 注册个性信息
     * 
     * @param personalityInfo 个性信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> registerPersonality(PersonalityInfo personalityInfo, Integer type)
            throws Exception {
        Map<String, String> messages = new HashMap<String, String>();
        if (personalityInfo != null) {

            if (personalityInfo.getNickname() != null) {
                if (personalityInfo.getNickname().trim().length() > 16) {
                    messages.put("nickname", "请输入合法的昵称");
                }
            }

            if (personalityInfo.getPersonalityAutograph() != null) {
                if (personalityInfo.getPersonalityAutograph().trim().length() > 16) {
                    messages.put("personalityAutograph", "请输入合法的个性签名");
                }
            }

            if (personalityInfo.getCharacter() != null) {
                if (personalityInfo.getCharacter().trim().length() > 16) {
                    messages.put("character", "请输入合法的个性签名");
                }
            }

            if (personalityInfo.getHeadSculpture() != null) {
                int pture = personalityInfo.getHeadSculpture().indexOf(".");
                if (pture > -1) {
                    String uplopad = personalityInfo.getHeadSculpture().trim().substring(
                            personalityInfo.getHeadSculpture().indexOf("."),
                            personalityInfo.getHeadSculpture().length());

                    if (".jpg".equals(uplopad) || ".gif".equals(uplopad) || ".png".equals(uplopad)
                            || ".bmp".equals(uplopad)) {

                    } else {
                        messages.put("uploadBusinessLicencePictur", "请输入合法的头像照片");
                    }
                } else {
                    messages.put("uploadBusinessLicencePictur", "请输入合法的头像照片");
                }
            }

            if (personalityInfo.getInterest() != null) {
                if (personalityInfo.getInterest().trim().length() > 16) {
                    messages.put("interest", "请输入合法的兴趣爱好");
                }
            }

            if (personalityInfo.getMicroblogAddress() != null) {
                if (personalityInfo.getMicroblogAddress().trim().length() > 16) {
                    messages.put("microblogAddress", "请输入合法的微博地址");
                }
            }

            if (personalityInfo.getQqNumber() != null) {
                if (personalityInfo.getQqNumber().trim().length() > 16) {
                    messages.put("qqNumber", "请输入合法的QQ号");
                }
            }

            if (messages.size() > 0) {
                return messages;
            }

            // 添加个人个性信息
            personalityInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
            personalityInfo.setAmountConsume(BigDecimal.ZERO);
            personalityInfo.setN_EmpiricalValue(0);
            Integer personalityInfoId = personalityInfoDAO.insert(personalityInfo);

            RankDO rankDO = new RankDO();
            rankDO.setPersonalityId(personalityInfoId);
            rankDO.setExpend(personalityInfo.getN_TotalIntegral());

            LoginInfo loginInfo = loginInfoDAO.selectByPrimaryKey(personalityInfo.getN_LoginId());

            rankDO.setCustomerTypeId(loginInfo.getN_CustomerTypeId());
            rankService.updateRankName(rankDO);

        } else {
            messages.put("personality", "请输入个性信息");
            return messages;
        }

        return null;
    }

    @Override
    public Integer upToSupplier(Integer n_LoginId, Integer type, Long N_COMMERCIAL_ID)
            throws Exception {
        Integer result = 0;
        if (n_LoginId > 0 && type > 0) {
            LoginRoseRelDO loginRoseRelDO = new LoginRoseRelDO();
            loginRoseRelDO.setnLoginId(new BigDecimal(n_LoginId));
            loginRoseRelDO.setnCustomerTypeId(new BigDecimal(type));
            loginRoseRelDO.setnLevelId(new BigDecimal(388));
            Date dateTime = DateTimeUtils.getCalendarInstance().getTime();
            loginRoseRelDO.setdCreateDate(dateTime);
            loginRoseRelDO.setdModifyDate(dateTime);
            loginRoseRelDO.setN_CommercialTenantId(new BigDecimal(N_COMMERCIAL_ID));
            BigDecimal resultNum = loginRoseRelDAO.insertLoginRoseRelDO(loginRoseRelDO);
            if (resultNum.intValue() > 0) {
                result = 1;
            }
        }

        return result;
    }

    /**
     * 会员升级为采购商
     * 
     */
    /**
     * 注册会员升级为采购商
     * 
     * @param n_LoginId 会员ID，type---4为采购商
     * @param
     * @return 返回值 Integer 1--成功 0失败
     * @throws Exception 异常
     */
    /*
     * 删除采购商业务 @Override
     * 
     * @Transactional(rollbackFor = Exception.class) public Integer
     * upToBuyer(CommercialTenantBasicInfo commercialTenantBasicInfo, Integer type) throws Exception
     * { Integer resultNum = 0; if (commercialTenantBasicInfo.getN_LoginId() > 0 && type > 0) {
     * resultNum = commercialTenantBasicInfoDAO.insert(commercialTenantBasicInfo); LoginRoseRelDO
     * loginRoseRelDO = new LoginRoseRelDO(); loginRoseRelDO.setnLoginId(new
     * BigDecimal(commercialTenantBasicInfo.getN_LoginId())); loginRoseRelDO.setnCustomerTypeId(new
     * BigDecimal(type)); loginRoseRelDO.setnLevelId(new BigDecimal(388)); Date dateTime =
     * DateTimeUtils.getCalendarInstance().getTime(); loginRoseRelDO.setdCreateDate(dateTime);
     * loginRoseRelDO.setdModifyDate(dateTime); loginRoseRelDO.setStatus((short) 2);
     * loginRoseRelDO.setN_CommercialTenantId(new BigDecimal(resultNum));
     * loginRoseRelDAO.insertLoginRoseRelDO(loginRoseRelDO); } return resultNum;
     * 
     * }
     */

    /**
     * 注册会员为采购商
     * 
     * @param n_LoginId 会员ID，type---4为采购商
     * @param
     * @return 返回值 Integer 1--成功 0失败
     * @throws Exception 异常
     */
    /*
     * 删除采购商yewu@Override public Integer registerBuyer(BuyerInfo buyerInfo, Integer type) throws
     * Exception { Integer resultNum = 0; if (buyerInfo != null) { String loginAccount =
     * buyerInfo.getLoginAccount(); LoginInfo loginInfo = new LoginInfo();
     * loginInfo.setLoginAccount(loginAccount);
     * loginInfo.setLoginPassword(buyerInfo.getLoginPassword()); loginInfo.setN_CustomerTypeId(1);
     * loginInfo.setN_Status(1);
     * loginInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime()); Integer loginId =
     * loginInfoDAO.insert(loginInfo); // 更新客户等级 UserLevelDO userLevelDO = new UserLevelDO();
     * userLevelDO.setLoginId(loginId); Integer customerid = 1;
     * userLevelDO.setCustomerTypeId(customerid); userLevelDO.setExpend(BigDecimal.ZERO);
     * userLevelService.searchUserLevelUpdateInfo(userLevelDO); // 增加账户信息 AccountInfo record = new
     * AccountInfo(); record.setN_LoginId(loginId);
     * record.setAccountLogin(buyerInfo.getLoginAccount()); record.setMobile(buyerInfo.getMobile());
     * record.setN_CustomerTypeId(1); record.setEmail(buyerInfo.getEmail()); record.setN_Status(1);
     * Date cdate = DateTimeUtils.getCalendarInstance().getTime(); record.setD_CreateDate(cdate);
     * record.setD_ModifyDate(cdate); accountInfoDAO.insert(record); // 增加基本信息 PersonalBasicInfo p =
     * new PersonalBasicInfo(); p.setN_LoginId(loginId); personalBasicInfoDAO.insert(p);
     * 
     * // 增加个性化信息 PersonalityInfo pi = new PersonalityInfo(); pi.setN_LoginId(loginId);
     * pi.setLastYear_Amount(0.00); personalityInfoDAO.insert(pi); // 增加健康信息 HealthYgenericInfo
     * healthYgenericInfo = new HealthYgenericInfo(); healthYgenericInfo.setN_LoginId(loginId); if
     * (healthYgenericInfo.getN_MaritalStatus() == null) { healthYgenericInfo.setN_MaritalStatus(2);
     * } if (healthYgenericInfo.getBloodType() == null) { healthYgenericInfo.setBloodType("0"); }
     * healthYgenericInfoDAO.insertHealthInfo(healthYgenericInfo); // 注册采购商基本资料信息
     * CommercialTenantBasicInfo commercialTenantBasicInfo = new CommercialTenantBasicInfo();
     * commercialTenantBasicInfo.setN_LoginId(loginId);
     * commercialTenantBasicInfo.setCorporateName(buyerInfo.getCorporateName());
     * commercialTenantBasicInfo.setFixedPhone(buyerInfo.getFixedPhone());
     * commercialTenantBasicInfo.setBusinessLicenceRegister(buyerInfo.getBusinessLicenceRegister());
     * commercialTenantBasicInfo.setBlinceStartdate(buyerInfo.getBlinceStartdate());
     * commercialTenantBasicInfo.setBlinceEnddate(buyerInfo.getBlinceEnddate()); //
     * commercialTenantBasicInfo.setMobile(buyerInfo.getMobile());
     * commercialTenantBasicInfo.setContactsEmail(buyerInfo.getEmail()); commercialTenantBasicInfo
     * .setUploadBusinessLicencePictur(buyerInfo.getUploadBusinessLicencePictur());
     * commercialTenantBasicInfo.setBlinceVerify((short) 0);
     * commercialTenantBasicInfo.setCidVerify((short) 0);
     * commercialTenantBasicInfo.setAuthenticationStatus(0);
     * commercialTenantBasicInfo.setN_EnterpriseStatus(1);
     * 
     * resultNum = commercialTenantBasicInfoDAO.insert(commercialTenantBasicInfo); // LoginRoseRelDO
     * loginRoseRelDO = new LoginRoseRelDO(); loginRoseRelDO.setnLoginId(new BigDecimal(loginId));
     * loginRoseRelDO.setnCustomerTypeId(new BigDecimal(4)); loginRoseRelDO.setnLevelId(new
     * BigDecimal(388)); Date dateTime = DateTimeUtils.getCalendarInstance().getTime();
     * loginRoseRelDO.setdCreateDate(dateTime); loginRoseRelDO.setdModifyDate(dateTime);
     * loginRoseRelDO.setStatus((short) 2); loginRoseRelDO.setN_CommercialTenantId(new
     * BigDecimal(resultNum)); loginRoseRelDAO.insertLoginRoseRelDO(loginRoseRelDO);
     * 
     * //发送验证邮件 if(buyerInfo.getEmail()!=null){ EmailRegisterVO emailRegister = new
     * EmailRegisterVO(); EmailSubscriptionRemoteService
     * emailSubscriptionRemoteService=(EmailSubscriptionRemoteService
     * )RemoteTool.getRemote("09","emailSubscriptionRemoteService");
     * emailRegister.setLoginId(loginId); emailRegister.setUserEmail(buyerInfo.getEmail());
     * emailRegister.setUserName(buyerInfo.getLoginAccount()); emailRegister.setUrl(""); // http://
     * test.kmb2b.com:8088/member/validMail.action?veCode=bd3bb69433a1d7d3ec1729d0b572e93e&user
     * .loginId=46079&user.loginAccount=hello56 boolean te
     * =emailSubscriptionRemoteService.emailRegisterSend(emailRegister, 1);
     * System.out.println("==========发送邮件-----"+te); }
     * 
     * // 普通注册送积分
     * 
     * 删除 Map<String, String> paramsMap = new HashMap<String, String>(); Integer scoreType = 1;
     * userGrowingService.updateUserScoreInfo(ruleCode, scoreType, loginAccount, paramsMap);
     * log.info("接口B2B注册会员" + loginAccount + "送积分"); // 普通会员注册送优惠劵 log.info("------主键：；:---" +
     * loginId); this.changeCustomGrantToGive(loginId); log.info("B2B注册会员" + loginAccount +
     * "发放优惠劵");
     * 
     * } return resultNum; }
     */

    /**
     * 修改采购商信息
     * 
     * @param n_LoginId 会员ID，type---4为采购商
     * @param
     * @return 返回值 Integer 1--成功 0失败
     * @throws Exception 异常
     */
    /*
     * 删除采购商业务 @Override
     * 
     * @Transactional(rollbackFor = Exception.class) public Integer updateBuyer(BuyerInfo buyerInfo,
     * Integer type) throws Exception { Integer result = 0; if (buyerInfo.getN_LoginId() > 0 && type
     * > 0 && buyerInfo.getN_CommercialTenantId() > 0) { CommercialTenantBasicInfo
     * commercialTenantBasicInfo = new CommercialTenantBasicInfo();
     * commercialTenantBasicInfo.setN_LoginId(buyerInfo.getN_LoginId());
     * commercialTenantBasicInfo.setN_CommercialTenantId(buyerInfo.getN_CommercialTenantId());
     * commercialTenantBasicInfo.setCorporateName(buyerInfo.getCorporateName());
     * commercialTenantBasicInfo.setFixedPhone(buyerInfo.getFixedPhone());
     * commercialTenantBasicInfo.setBusinessLicenceRegister(buyerInfo.getBusinessLicenceRegister());
     * log.info("------------=============-----------" + buyerInfo.getBlinceStartdate());
     * log.info("------------=============-----------" + buyerInfo.getBlinceEnddate());
     * commercialTenantBasicInfo.setBlinceStartdate(buyerInfo.getBlinceStartdate());
     * commercialTenantBasicInfo.setBlinceEnddate(buyerInfo.getBlinceEnddate());
     * commercialTenantBasicInfo.setMobile(buyerInfo.getMobile()); commercialTenantBasicInfo
     * .setUploadBusinessLicencePictur(buyerInfo.getUploadBusinessLicencePictur());
     * commercialTenantBasicInfo.setD_ModifyDate(new Date());
     * 
     * result = commercialTenantBasicInfoDAO.updateByer(commercialTenantBasicInfo);
     * 
     * 
     * } return result; }
     */

    /**
     * 修改采购商状态
     * 
     * @param n_LoginId 会员ID，type---4为采购商
     * @param
     * @return 返回值 Integer 1--成功 0失败
     * @throws Exception 异常
     */
    /*
     * 删除采购商业务 public Integer updateBuyerStatus(Integer n_LoginId, Integer status, Integer type)
     * throws Exception { Integer result = 0; if (n_LoginId > 0 && type > 0 && status > 0) {
     * CommercialTenantBasicInfo commercialTenantBasicInfo = new CommercialTenantBasicInfo();
     * commercialTenantBasicInfo.setN_LoginId(n_LoginId);
     * commercialTenantBasicInfo.setLogin_Status(status);
     * commercialTenantBasicInfo.setN_CustomerTypeId(type);
     * commercialTenantBasicInfoDAO.updateByerStaus(commercialTenantBasicInfo);
     * 
     * result = 1; } return result; }
     */

    /**
     * 变更采购商基本资料
     * 
     * @param CommercialTenantBasicCopy，type---4为采购商
     * @param
     * @return 返回值 Integer 1--成功 0失败
     * @throws Exception 异常
     */
    /*
     * 删除采购商业务 @Override
     * 
     * @Transactional(rollbackFor = Exception.class) public Integer
     * toExamineBuyerInfo(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO, Integer type)
     * throws Exception { Integer result = 0; if
     * (commercialTenantBasicCopyDO.getCommercialTenantId().intValue() > 0) {
     * CommercialTenantBasicInfo commercialTenantBasicInfo = new CommercialTenantBasicInfo();
     * 
     * commercialTenantBasicInfo = commercialTenantBasicInfoDAO
     * .selectByPrimaryKey(commercialTenantBasicCopyDO.getCommercialTenantId().intValue());
     * commercialTenantBasicCopyDO.setCreditRating(commercialTenantBasicInfo.getCreditRating());
     * commercialTenantBasicCopyDO.setBlinceVerify(commercialTenantBasicInfo.getBlinceVerify());
     * commercialTenantBasicCopyDO.setCidVerify(commercialTenantBasicInfo.getCidVerify());
     * commercialTenantBasicCopyDO.setAtaxVerify(commercialTenantBasicInfo.getAtaxVerify());
     * commercialTenantBasicCopyDO.setTrccopyVerify(commercialTenantBasicInfo.getTrccopyVerify());
     * commercialTenantBasicCopyDO.setGtcVerify(commercialTenantBasicInfo.getGtcVerify());
     * 
     * 
     * // 提交审核 commercialTenantBasicCopyDO.setReviewChange((short) 0);
     * commercialTenantBasicCopyDAO.deleteCommercialTenantBasicCopyDOByPrimaryKey(
     * commercialTenantBasicCopyDO.getCommercialTenantId()); BigDecimal copyId =
     * commercialTenantBasicCopyDAO .insertCommercialTenantBasicCopyDO(commercialTenantBasicCopyDO);
     * result = copyId.intValue(); log.info("采购商申请资料变更"); } return result; }
     */

    /**
     * 查询采购商总共数量
     * 
     * @param CommercialTenantBasicCopy，type---4为采购商
     * @param
     * @return 返回值 Integer 采购商总数--成功 0失败
     * @throws Exception 异常
     */
    /*
     * 删除采购商业务 public Integer getCountBuyerInfo(CommercialTenantBasicInfo commercialTenantBasicInfo,
     * Integer type) throws Exception { int count = 0; // 查询采购商 try {
     * commercialTenantBasicInfo.setN_CustomerTypeId(4); count =
     * commercialTenantBasicInfoDAO.selectCountByVo(commercialTenantBasicInfo);
     * log.info("接口查询采购商总数"); } catch (Exception e) { log.error("接口查询采购商总数异常" + e.getMessage(), e);
     * } return count; }
     */
    /**
     * 分页查询采购商信息
     * 
     * @param CommercialTenantBasicCopy，type---4为采购商
     * @param
     * @return 返回值 List --成功
     * @throws Exception 异常
     */
    /*
     * 删除采购商业务 public List<CommercialTenantBasicInfo> selectByCommercialTenantBasicInfo(
     * CommercialTenantBasicInfo commercialTenantBasicInfo, Integer type) throws Exception {
     * List<CommercialTenantBasicInfo> list = null; try {
     * 
     * 
     * // 查询采购商 if (commercialTenantBasicInfo.getSkip() <= 0) {
     * commercialTenantBasicInfo.setSkip(0); } // 初始化显示10条记录 if (commercialTenantBasicInfo.getMax()
     * <= 0) { commercialTenantBasicInfo.setMax(10);
     * 
     * } commercialTenantBasicInfo.setN_CustomerTypeId(4); list =
     * commercialTenantBasicInfoDAO.selectPageByVo(commercialTenantBasicInfo);
     * log.info("接口分页查询采购商信息SpecialistRemoteService.selectByCommercialTenantBasicInfo"); } catch
     * (Exception e) { log.error(
     * " 接口分页查询采购商信息失败SpecialistRemoteService.selectByCommercialTenantBasicInfo" + e.getMessage(),
     * e); } return list; }
     */


    /**
     * 注册发放优惠劵
     * 
     * @return
     */
    public int changeCustomGrantToGive(int LoginId) {
        int result = 0;
        try {
            // 调用远程接口
            // 查询发放注册类型优惠劵
            List<Coupon> list = couponRemoteService.selectCouponByType((long) 2);
            for (Coupon cn : list) {
                log.info("------------------:" + list.size() + "-----");
                UserInfoDO userInfoDO = new UserInfoDO();
                userInfoDO.setLoginId(LoginId);
                // 发放优惠劵
                log.info("-----------" + cn.getCouponId());
                result = couponRemoteService.changeCustomGrantToGive(userInfoDO, cn.getCouponId(),
                        Long.valueOf(LoginId), Long.valueOf(21), String.valueOf(LoginId), null);

            }
        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (Exception e) {
            log.error(
                    " 接口注册发放优惠劵失败：SpecialistRemoteService.changeCustomGrantToGive" + e.getMessage(),
                    e);
        }
        return result;
    }


    public PersonalBasicInfoDAO getPersonalBasicInfoDAO() {
        return personalBasicInfoDAO;
    }

    public void setPersonalBasicInfoDAO(PersonalBasicInfoDAO personalBasicInfoDAO) {
        this.personalBasicInfoDAO = personalBasicInfoDAO;
    }

    public LoginInfoDAO getLoginInfoDAO() {
        return loginInfoDAO;
    }

    public void setLoginInfoDAO(LoginInfoDAO loginInfoDAO) {
        this.loginInfoDAO = loginInfoDAO;
    }

    public UserLevelService getUserLevelService() {
        return userLevelService;
    }

    public void setUserLevelService(UserLevelService userLevelService) {
        this.userLevelService = userLevelService;
    }

    public PersonalityInfoDAO getPersonalityInfoDAO() {
        return personalityInfoDAO;
    }

    public void setPersonalityInfoDAO(PersonalityInfoDAO personalityInfoDAO) {
        this.personalityInfoDAO = personalityInfoDAO;
    }

    public RankService getRankService() {
        return rankService;
    }

    public MdicalExcusieInfoDAO getMdicalExcusieInfoDAO() {
        return mdicalExcusieInfoDAO;
    }

    public void setMdicalExcusieInfoDAO(MdicalExcusieInfoDAO mdicalExcusieInfoDAO) {
        this.mdicalExcusieInfoDAO = mdicalExcusieInfoDAO;
    }

    public void setRankService(RankService rankService) {
        this.rankService = rankService;
    }

    public LoginRoseRelDAO getLoginRoseRelDAO() {
        return loginRoseRelDAO;
    }

    public void setLoginRoseRelDAO(LoginRoseRelDAO loginRoseRelDAO) {
        this.loginRoseRelDAO = loginRoseRelDAO;
    }

    public CommercialTenantBasicInfoDAO getCommercialTenantBasicInfoDAO() {
        return commercialTenantBasicInfoDAO;
    }

    public void setCommercialTenantBasicInfoDAO(
            CommercialTenantBasicInfoDAO commercialTenantBasicInfoDAO) {
        this.commercialTenantBasicInfoDAO = commercialTenantBasicInfoDAO;
    }


    public AccountInfoDAO getAccountInfoDAO() {
        return accountInfoDAO;
    }

    public void setAccountInfoDAO(AccountInfoDAO accountInfoDAO) {
        this.accountInfoDAO = accountInfoDAO;
    }

    public HealthYgenericInfoDAO getHealthYgenericInfoDAO() {
        return healthYgenericInfoDAO;
    }

    public void setHealthYgenericInfoDAO(HealthYgenericInfoDAO healthYgenericInfoDAO) {
        this.healthYgenericInfoDAO = healthYgenericInfoDAO;
    }



    public CommercialTenantBasicCopyDAO getCommercialTenantBasicCopyDAO() {
        return commercialTenantBasicCopyDAO;
    }

    public void setCommercialTenantBasicCopyDAO(
            CommercialTenantBasicCopyDAO commercialTenantBasicCopyDAO) {
        this.commercialTenantBasicCopyDAO = commercialTenantBasicCopyDAO;
    }


    public UserGrowingService getUserGrowingService() {
        return userGrowingService;
    }

    public void setUserGrowingService(UserGrowingService userGrowingService) {
        this.userGrowingService = userGrowingService;
    }
    /*
     * public HttpServletRequest getRequest() { return request; }
     * 
     * public void setRequest(HttpServletRequest request) { this.request = request; }
     */

}
