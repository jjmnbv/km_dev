package com.kmzyc.b2b.third.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.AlipayUserDetail;
import com.kmzyc.b2b.event.EventUtils;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.RegistService;
import com.kmzyc.b2b.third.dao.ThirdAccountInfoDao;
import com.kmzyc.b2b.third.model.ThirdAccountInfo;
import com.kmzyc.b2b.third.model.ThirdBindInfo;
import com.kmzyc.b2b.third.model.wechat.UserInfo;
import com.kmzyc.b2b.third.service.ThirdAccountInfoService;
import com.kmzyc.b2b.third.service.ThirdBindInfoService;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.b2b.third.util.ThirdEntityUtil;
import com.kmzyc.b2b.weibo.model.User;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.user.remote.service.LatestLoginRemoteService;
import com.kmzyc.util.StringUtil;
import com.qq.connect.javabeans.qzone.UserInfoBean;

/**
 * 实现类
 *
 * @author Administrator 2014-03-18
 */
@Service("thirdAccountInfoService")
public class ThirdAccountInfoServiceImpl implements ThirdAccountInfoService {

    @Resource(name = "thirdAccountInfoDao")
    private ThirdAccountInfoDao dao;


    @Resource(name = "thirdBindInfoService")
    private ThirdBindInfoService thirdBindInfoService;

    @Resource(name = "registServiceImp")
    private RegistService registerService;

    @Resource(name = "loginServiceImp")
    private LoginService loginService;


    @Resource
    private LatestLoginRemoteService latestLoginRemoteService;


    private static Logger log = LoggerFactory.getLogger(ThirdAccountInfoServiceImpl.class);

    @Override
    public void saveQQAccountInfo(UserInfoBean userInfoBean, String openId) throws SQLException {
        // 设置数据源

        // 统一转换成我们的实体
        ThirdAccountInfo qqAcct = ThirdEntityUtil.QQUserConvertToThirdAcct(userInfoBean, openId);

        dao.insert(qqAcct);

    }

    @Override
    public void saveQQAccountInfo(JSONObject jsonParams, String openId) throws SQLException {
        // 设置数据源

        // 统一转换成我们的实体
        ThirdAccountInfo qqAcct = ThirdEntityUtil.QQUserConvertToThirdAcct(jsonParams, openId);
        dao.insert(qqAcct);
    }

    @Override
    public void saveWxAccountInfo(UserInfo entity) throws SQLException {
        // 设置数据源

        // 统一转换成我们的实体
        ThirdAccountInfo wxAcct = ThirdEntityUtil.wxUserConvertToThirdAcct(entity);
        dao.insert(wxAcct);
    }

    @Override
    public void saveSinaAccountInfo(User user) throws SQLException {
        // 设置数据源

        // 统一转换成我们的实体
        ThirdAccountInfo sinaAcct = ThirdEntityUtil.sinaUserConvertToThirdAcct(user);
        dao.insert(sinaAcct);
    }

    @Override
    public void saveSinaAccountInfo(JSONObject jsonParams) throws SQLException {
        // 设置数据源

        // 统一转换成我们的实体
        ThirdAccountInfo sinaAcct = ThirdEntityUtil.sinaUserConvertToThirdAcct(jsonParams);
        dao.insert(sinaAcct);
    }

    @Override
    public void saveAlipayAccountInfo(AlipayUserDetail user) throws SQLException {
        // 设置数据源

        // 统一转换成我们的实体
        ThirdAccountInfo alipayAcct = ThirdEntityUtil.alipayUserConvertToThirdAcct(user);
        dao.insert(alipayAcct);
    }

    @Override
    public void saveTaobaoAccountInfo(com.taobao.api.domain.User user) throws SQLException {
        // 设置数据源

        dao.insert(ThirdEntityUtil.taobaoUserConvertToThirdAcct(user));
    }

    @Override
    public ThirdAccountInfo queryByOpenIdAndType(ThirdAccountInfo condition) throws SQLException {
        // 设置数据源

        return dao.queryByPrimaryKey(condition);
    }

    @Override
    public int updateQQAcctInfo(UserInfoBean userInfoBean, String openId) throws SQLException {
        // 设置数据源

        // 统一转换成我们的实体
        ThirdAccountInfo qqAcct = ThirdEntityUtil.QQUserConvertToThirdAcct(userInfoBean, openId);
        return dao.updateAcctInfo(qqAcct);
    }

    @Override
    public int updateWxAcctInfo(UserInfo userInfo) throws SQLException {
        // 设置数据源

        // 统一转换成我们的实体
        ThirdAccountInfo wxAcct = ThirdEntityUtil.wxUserConvertToThirdAcct(userInfo);
        return dao.updateAcctInfo(wxAcct);
    }

    @Override
    public int updateSinaAcctInfo(User user) throws SQLException {
        // 设置数据源

        // 统一转换成我们的实体
        ThirdAccountInfo sinaAcct = ThirdEntityUtil.sinaUserConvertToThirdAcct(user);
        return dao.updateAcctInfo(sinaAcct);
    }

    @Override
    public void deleteAccountInfo(ThirdAccountInfo condition) throws SQLException {
        // 设置数据源

        dao.deleteAccountInfo(condition);
    }

    @Override
    public String queryOpenIdByLoginId(String loginId) throws SQLException {
        // 设置数据源

        return dao.queryOpenIdByLoginId(loginId);
    }

    @Override
    public Map<String, Object> commonBizAbountThirdLogin4App(Object obj, String acctType,
            String qqOpenId) throws Exception {

        // 需要返回的页面结果常量和错误原因
        Map<String, Object> resultMap = new HashMap<String, Object>();
        UserInfo wxUser = null;
        AlipayUserDetail aliUser = null;
        com.taobao.api.domain.User taobaoUser = null;

        // 需要存入session中的登录user对象
        com.kmzyc.b2b.model.User loginUser;

        // 判断数据库中是否有该第三方账号的信息
        ThirdAccountInfo condition = new ThirdAccountInfo();

        // 强转成我们的目标类型
        if ("QQ".equalsIgnoreCase(acctType)) {
            // qqUser = (UserInfoBean) obj;
            condition.setOpenId(qqOpenId);
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_QQ);
        } else if ("wx".equalsIgnoreCase(acctType)) {
            wxUser = (UserInfo) obj;
            condition.setOpenId(wxUser.getOpenid());
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_WX);
        } else if ("sina".equalsIgnoreCase(acctType)) {
            // sinaUser = (User) obj;
            condition.setOpenId(qqOpenId);
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_SINA);
        } else if ("alipay".equalsIgnoreCase(acctType)) {
            aliUser = (AlipayUserDetail) obj;
            condition.setOpenId(aliUser.getAlipayUserId());
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_ALIPAY);
        } else if ("taobao".equalsIgnoreCase(acctType)) {
            taobaoUser = (com.taobao.api.domain.User) obj;
            condition.setOpenId(taobaoUser.getUid());
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_TAOBAO);
        }

        // 判断该信息在third_account_info表中是否已经存在
        ThirdAccountInfo result = null;

        try {
            result = queryByOpenIdAndType(condition);

            // 若第三方账号信息已存在,说明早已生成一条绑定记录
            if (result != null) {

                if ("wx".equals(acctType)) {
                    // 微信则更新UNIONID,第三方账号切换第一期需求，第二期将删除
                    result.setUnionid(wxUser.getUnionid());
                    this.dao.updateAcctInfo(result);
                }

                // 将其绑定的康美会员的信息加载出来
                String loginInfo = thirdBindInfoService.queryLoginIdByThridAcctInfo(condition);

                // 若由于调用注册接口出现异常,导致事务没有回滚,则将其账户信息删除,重新释放
                if (null == loginInfo || "".equals(loginInfo)) {
                    deleteAccountInfo(condition);
                    resultMap.put("errorMsg", "登录失败，请稍后重试或联系客服人员。");
                    return resultMap;
                }

                String[] infoArray = loginInfo.split(",");

                System.out.println("loginInfo......." + loginInfo);
                log.info("第三方账号所对应的会员的id和nickName=" + loginInfo);

                try {
                    loginUser = loginService.queryUserByLoginId(infoArray[0]);

                    if (infoArray.length > 1) {
                        loginUser.setNickName(infoArray[1]);
                    }
                } catch (Exception e) {
                    resultMap.put("errorMsg", "登录失败，请稍后重试或联系客服人员。");
                    return resultMap;
                }
                // 将绑定的康美会员的信息返回去
                if (null != loginUser) {
                    resultMap.put("uid", loginUser.getLoginId());
                    resultMap.put("loginAccount", loginUser.getLoginAccount());
                    resultMap.put("isOldMember", 1);// 1:老用户 0:新用户
                }
                System.out.println("the account already exist.......");
                return resultMap;

                // // 判断其是否绑定的是需要完善信息的正式会员
                // boolean isBindTourist =
                // thirdBindInfoService.isBindTourist(condition);
                //
                // // 如果绑定的是临时会员,则提供完善资料的入口
                // if (isBindTourist) {
                // resultMap.put("pageResult", "toFillInfo");
                // return resultMap;
                // }
                //
                // // 已是正式会员,则将其到首页去
                // resultMap.put("pageResult", "toIndex");
                // return resultMap;

            }

            // 没有第三方信息则将信息存入到数据库并生成临时会员进行绑定
            if ("QQ".equalsIgnoreCase(acctType)) {
                saveQQAccountInfo((JSONObject) obj, qqOpenId);
            } else if ("wx".equalsIgnoreCase(acctType)) {
                saveWxAccountInfo(wxUser);
            } else if ("sina".equalsIgnoreCase(acctType)) {
                saveSinaAccountInfo((JSONObject) obj);
            } else if ("alipay".equalsIgnoreCase(acctType)) {
                saveAlipayAccountInfo(aliUser);
            } else if ("taobao".equalsIgnoreCase(acctType)) {
                saveTaobaoAccountInfo(taobaoUser);
            }

            // 生成一个临时会员(相当于游客身份进行绑定操作,之后将这个临时用户的信息写入到session中)
            com.kmzyc.b2b.model.User tempUser = new com.kmzyc.b2b.model.User();
            tempUser.setMobile("");
            tempUser.setEmail("");
            tempUser.setRegisterDevice(Constants.REGISTER_DEVICE_APP);
            tempUser.setRegisterPlatfrom(Constants.REGISTER_PLATFORM_DEFAULT);
            // 第一次生成都是游客类型(20140414 生成需要完善信息的正式会员)
            tempUser.setCustomerTypeId(ThirdConstant.THIRD_CUSTOMER_TYPE_NEED_FILLINFO);
            tempUser.setLoginAccount(StringUtil.getLoginAccount(Constants.CUSTOMER_TYPE_SD_MEMBER));

            Map<String, String> registReturnMap;
            try {
                registReturnMap = registerService.regist(tempUser);

                // TODO 邮件相关
                /*
                 * // 调用邮件订阅接口(默认订阅所有的邮件类型) try {
                 * emailSubscriptionRemoteService.addEmailRrsHisByUidAndIds(
                 * Long.parseLong(registReturnMap.get("loginId")), null);
                 * System.out.println("===============邮件订阅成功"); } catch (SQLException se) {
                 * se.printStackTrace(); log.error("+++++邮件订阅失败！+++++" + se.getMessage()); }
                 */
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("regist tempUser error");
                // 若生成临时用户失败,则提示失败原因将第三方账号插入的表的数据直接删除
                deleteAccountInfo(condition);
                resultMap.put("errorMsg", "登录失败，请稍后重试或联系客服人员。");
                log.error("register tempUser failed...." + e.getMessage(), e);
                return resultMap;
            }

            // 生成成功,则将第三方账号和生成的临时会员进行临时绑定(生成绑定记录)
            ThirdBindInfo bindInfo = new ThirdBindInfo();
            if ("QQ".equalsIgnoreCase(acctType)) {
                bindInfo.setOpenId(qqOpenId);
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_QQ);
            } else if ("wx".equalsIgnoreCase(acctType)) {
                bindInfo.setOpenId(wxUser.getOpenid());
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_WX);
            } else if ("sina".equalsIgnoreCase(acctType)) {
                bindInfo.setOpenId(qqOpenId);
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_SINA);
            } else if ("alipay".equalsIgnoreCase(acctType)) {
                bindInfo.setOpenId(aliUser.getAlipayUserId());
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_ALIPAY);
            } else if ("taobao".equalsIgnoreCase(acctType)) {
                bindInfo.setOpenId(taobaoUser.getUid());
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_TAOBAO);
            }

            bindInfo.setnLoginId(registReturnMap.get("loginId"));
            bindInfo.setIsBind("Y");
            bindInfo.setBindType(ThirdConstant.THIRD_BIND_TYPE_TEMP);
            bindInfo.setLastUpdateTime(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            bindInfo.setRemark("第三方" + acctType + "账号已登录进kmb2b便生成临时用户(需要完善信息的正式会员)进行绑定");

            // 为了防止程序有不严谨性,删除之前的记录
            thirdBindInfoService.deleteBindInfoByOpenId(bindInfo.getOpenId(),
                    bindInfo.getThirdAccountType());

            // 插入绑定信息
            thirdBindInfoService.saveBindInfo(bindInfo);

            try {
                loginUser = loginService.queryUserByLoginId(registReturnMap.get("loginId"));
                // resultMap.put("loginUser", loginUser);
                if (null != loginUser) {
                    resultMap.put("uid", loginUser.getLoginId());
                    resultMap.put("loginAccount", loginUser.getLoginAccount());
                    resultMap.put("isOldMember", 0);// 1:老用户 0:新用户
                }
            } catch (Exception e) {
                resultMap.put("errorMsg", "登录失败，请稍后重试或联系客服人员。");
                log.info("query loginUser has occourred exception...." + e.getMessage());
                return resultMap;
            }

            // 可提示其去完善信息
            // pageResult = "toFillInfo";

        } catch (SQLException e) {
            // e.printStackTrace();
            resultMap.put("errorMsg", "登录失败，请稍后重试或联系客服人员。");
            log.error("insert thirdaccountinfo error：/n" + e.getMessage(), e);
            return resultMap;
        }
        // resultMap.put("pageResult", pageResult);
        return resultMap;
    }

    @Override
    public Map<String, Object> commonBindWithNormalMember4App(Object obj, String acctType,
            String loginId, String qqOpenId) throws ServiceException {
        // 需要返回的页面结果常量和错误原因
        Map<String, Object> resultMap = new HashMap<String, Object>();

        UserInfo wxUser = null;
        AlipayUserDetail aliUser = null;
        com.taobao.api.domain.User taobaoUser = null;

        // 判断数据库中是否有该第三方账号的信息
        ThirdAccountInfo condition = new ThirdAccountInfo();

        // 强转成我们的目标类型
        if ("QQ".equalsIgnoreCase(acctType)) {
            condition.setOpenId(qqOpenId);
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_QQ);
        } else if ("wx".equalsIgnoreCase(acctType)) {
            wxUser = (UserInfo) obj;
            condition.setOpenId(wxUser.getOpenid());
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_WX);
        } else if ("sina".equalsIgnoreCase(acctType)) {
            condition.setOpenId(qqOpenId);
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_SINA);
        } else if ("alipay".equalsIgnoreCase(acctType)) {
            aliUser = (AlipayUserDetail) obj;
            condition.setOpenId(aliUser.getAlipayUserId());
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_ALIPAY);
        } else if ("taobao".equalsIgnoreCase(acctType)) {
            taobaoUser = (com.taobao.api.domain.User) obj;
            condition.setOpenId(taobaoUser.getUid());
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_TAOBAO);
        }

        // 判断该信息在third_account_info表中是否已经存在
        try {
            ThirdAccountInfo result = queryByOpenIdAndType(condition);

            // 该第三方账号已和其它康美账号绑定!
            if (result != null) {

                if ("wx".equals(acctType)) {
                    // 微信则更新UNIONID,第三方账号切换第一期需求，第二期将删除
                    result.setUnionid(wxUser.getUnionid());
                    this.dao.updateAcctInfo(result);
                }
                resultMap.put("alreadyBind", "Y");
                return resultMap;
            }
        } catch (SQLException e1) {
            resultMap.put("errorMsg", "查询第三方账号发生异常!" + e1.getMessage());
            log.error("查询第三方账号发生异常!" + e1.getMessage(), e1);
            return resultMap;
        }

        try {
            // 存储第三方账号的信息
            if ("QQ".equalsIgnoreCase(acctType)) {
                saveQQAccountInfo((JSONObject) obj, qqOpenId);
            } else if ("wx".equalsIgnoreCase(acctType)) {
                saveWxAccountInfo(wxUser);
            } else if ("sina".equalsIgnoreCase(acctType)) {
                saveSinaAccountInfo((JSONObject) obj);
            } else if ("alipay".equalsIgnoreCase(acctType)) {
                saveAlipayAccountInfo(aliUser);
            } else if ("taobao".equalsIgnoreCase(acctType)) {
                saveTaobaoAccountInfo(taobaoUser);
            }

            ThirdBindInfo bindInfo = new ThirdBindInfo();

            if ("QQ".equalsIgnoreCase(acctType)) {
                bindInfo.setOpenId(qqOpenId);
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_QQ);
            } else if ("wx".equalsIgnoreCase(acctType)) {
                bindInfo.setOpenId(wxUser.getOpenid());
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_WX);
            } else if ("sina".equalsIgnoreCase(acctType)) {
                bindInfo.setOpenId(qqOpenId);
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_SINA);
            } else if ("alipay".equalsIgnoreCase(acctType)) {
                bindInfo.setOpenId(aliUser.getAlipayUserId());
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_ALIPAY);
            } else if ("taobao".equalsIgnoreCase(acctType)) {
                bindInfo.setOpenId(taobaoUser.getUid());
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_TAOBAO);
            }
            bindInfo.setnLoginId(loginId);
            bindInfo.setIsBind("Y");
            bindInfo.setBindType(ThirdConstant.THIRD_BIND_TYPE_FORMAL);
            bindInfo.setLastUpdateTime(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            bindInfo.setRemark("第三方" + acctType + "账号和康美正式会员通过绑定管理绑定");

            // 为了防止程序有不严谨性,删除之前的记录
            thirdBindInfoService.deleteBindInfoByOpenId(bindInfo.getOpenId(),
                    bindInfo.getThirdAccountType());
            // 插入绑定信息
            thirdBindInfoService.saveBindInfo(bindInfo);

        } catch (SQLException e) {
            resultMap.put("errorMsg", "插入信息异常" + e.getMessage());
            log.error("ThirdAccountInfoServiceImpl ---->插入third_account_info和third_bind_info出错."
                    + e.getMessage(), e);
        }

        return resultMap;
    }

    /**
     * 新增外接账户信息
     *
     * @param thirdAccountInfo 参数实体
     */
    @Override
    public void saveOutAccountInfo(ThirdAccountInfo thirdAccountInfo) throws ServiceException {
        // 设置数据源

        try {
            dao.insert(thirdAccountInfo);
        } catch (Exception e) {
            throw new ServiceException(0, "新增外接账户信息发生异常", e);
        }
    }

    @Override
    public Map<String, Object> commonBindWithNormalMember(Object obj, String acctType,
            String loginId, String qqOpenId) throws ServiceException {
        // 需要返回的页面结果常量和错误原因
        Map<String, Object> resultMap = new HashMap<String, Object>();

        UserInfoBean qqUser = null;
        UserInfo wxUser = null;
        User sinaUser = null;
        AlipayUserDetail aliUser = null;
        com.taobao.api.domain.User taobaoUser = null;

        // 判断数据库中是否有该第三方账号的信息
        ThirdAccountInfo condition = new ThirdAccountInfo();

        // 强转成我们的目标类型
        if ("QQ".equals(acctType)) {
            qqUser = (UserInfoBean) obj;
            condition.setOpenId(qqOpenId);
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_QQ);
        } else if ("wx".equals(acctType)) {
            wxUser = (UserInfo) obj;
            condition.setOpenId(wxUser.getOpenid());
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_WX);
        } else if ("sina".equals(acctType)) {
            sinaUser = (User) obj;
            condition.setOpenId(sinaUser.getId());
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_SINA);
        } else if ("alipay".equals(acctType)) {
            aliUser = (AlipayUserDetail) obj;
            condition.setOpenId(aliUser.getAlipayUserId());
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_ALIPAY);
        } else if ("taobao".equals(acctType)) {
            taobaoUser = (com.taobao.api.domain.User) obj;
            condition.setOpenId(taobaoUser.getUid());
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_TAOBAO);
        }

        // 判断该信息在third_account_info表中是否已经存在
        try {
            ThirdAccountInfo result = queryByOpenIdAndType(condition);

            // 该第三方账号已和其它康美账号绑定!
            if (result != null) {
                if ("wx".equals(acctType)) {
                    // 微信则更新UNIONID,第三方账号切换第一期需求，第二期将删除
                    result.setUnionid(wxUser.getUnionid());
                    this.dao.updateAcctInfo(result);
                }
                resultMap.put("alreadyBind", "Y");
                return resultMap;
            }
        } catch (SQLException e1) {
            resultMap.put("errorMsg", "查询第三方账号发生异常!" + e1.getMessage());
            log.error("查询第三方账号发生异常!" + e1.getMessage(), e1);
            return resultMap;
        }

        try {
            // 存储第三方账号的信息
            if ("QQ".equals(acctType)) {
                saveQQAccountInfo(qqUser, qqOpenId);
            } else if ("wx".equals(acctType)) {
                saveWxAccountInfo(wxUser);
            } else if ("sina".equals(acctType)) {
                saveSinaAccountInfo(sinaUser);
            } else if ("alipay".equals(acctType)) {
                saveAlipayAccountInfo(aliUser);
            } else if ("taobao".equals(acctType)) {
                saveTaobaoAccountInfo(taobaoUser);
            }

            ThirdBindInfo bindInfo = new ThirdBindInfo();

            if ("QQ".equals(acctType)) {
                bindInfo.setOpenId(qqOpenId);
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_QQ);
            } else if ("wx".equals(acctType)) {
                bindInfo.setOpenId(wxUser.getOpenid());
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_WX);
            } else if ("sina".equals(acctType)) {
                bindInfo.setOpenId(sinaUser.getId());
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_SINA);
            } else if ("alipay".equals(acctType)) {
                bindInfo.setOpenId(aliUser.getAlipayUserId());
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_ALIPAY);
            } else if ("taobao".equals(acctType)) {
                bindInfo.setOpenId(taobaoUser.getUid());
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_TAOBAO);
            }
            bindInfo.setnLoginId(loginId);
            bindInfo.setIsBind("Y");
            bindInfo.setBindType(ThirdConstant.THIRD_BIND_TYPE_FORMAL);
            bindInfo.setLastUpdateTime(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            bindInfo.setRemark("第三方" + acctType + "账号和康美正式会员通过绑定管理绑定");

            // 为了防止程序有不严谨性,删除之前的记录
            thirdBindInfoService.deleteBindInfoByOpenId(bindInfo.getOpenId(),
                    bindInfo.getThirdAccountType());
            // 插入绑定信息
            thirdBindInfoService.saveBindInfo(bindInfo);

            // 绑定之后去到页面,value值为页面配置地址
            resultMap.put("pageResult", "toBindManage");

        } catch (SQLException e) {
            resultMap.put("errorMsg", "插入信息异常" + e.getMessage());
            log.error("ThirdAccountInfoServiceImpl ---->插入third_account_info和third_bind_info出错."
                    + e.getMessage(), e);
        }

        return resultMap;
    }

    @Override
    public Map<String, Object> commonBizAbountThirdLogin(Object obj, String acctType,
            String qqOpenId, int device, int platfrom) throws Exception {

        // 需要返回的页面结果常量和错误原因
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String pageResult = "";

        UserInfoBean qqUser = null;
        UserInfo wxUser = null;
        User sinaUser = null;
        AlipayUserDetail aliUser = null;
        com.taobao.api.domain.User taobaoUser = null;

        // 需要存入session中的登录user对象
        com.kmzyc.b2b.model.User loginUser;

        // 判断数据库中是否有该第三方账号的信息
        ThirdAccountInfo condition = new ThirdAccountInfo();

        // 强转成我们的目标类型
        if ("QQ".equals(acctType)) {
            qqUser = (UserInfoBean) obj;
            condition.setOpenId(qqOpenId);
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_QQ);
        } else if ("wx".equals(acctType)) {
            wxUser = (UserInfo) obj;
            condition.setOpenId(wxUser.getOpenid());
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_WX);
        } else if ("sina".equals(acctType)) {
            sinaUser = (User) obj;
            condition.setOpenId(sinaUser.getId());
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_SINA);
        } else if ("alipay".equals(acctType)) {
            aliUser = (AlipayUserDetail) obj;
            condition.setOpenId(aliUser.getAlipayUserId());
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_ALIPAY);
        } else if ("taobao".equals(acctType)) {
            taobaoUser = (com.taobao.api.domain.User) obj;
            condition.setOpenId(taobaoUser.getUid());
            condition.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_TAOBAO);
        } /*
           * else if ("fanli".equals(acctType)) { // 20151214 add condition = (ThirdAccountInfo)
           * obj; }
           */

        // 判断该信息在third_account_info表中是否已经存在
        ThirdAccountInfo result = null;

        try {
            result = queryByOpenIdAndType(condition);

            // 若第三方账号信息已存在,说明早已生成一条绑定记录
            if (result != null) {

                if ("wx".equals(acctType)) {
                    // 微信则更新UNIONID,第三方账号切换第一期需求，第二期将删除
                    result.setUnionid(wxUser.getUnionid());
                    this.dao.updateAcctInfo(result);
                }

                // 将其绑定的康美会员的信息加载出来
                String loginInfo = thirdBindInfoService.queryLoginIdByThridAcctInfo(condition);

                // 若由于调用注册接口出现异常,导致事务没有回滚,则将其账户信息删除,重新释放
                if (null == loginInfo || "".equals(loginInfo)) {
                    deleteAccountInfo(condition);
                    resultMap.put("errorMsg",
                            "query loginUser has occourred exception.. please try again!");
                    return resultMap;
                }

                String[] infoArray = loginInfo.split(",");

                System.out.println("loginInfo......." + loginInfo);
                log.info("第三方账号所对应的会员的id和nickName=" + loginInfo);

                try {
                    loginUser = loginService.queryUserByLoginId(infoArray[0]);

                    if (infoArray.length > 1) {
                        loginUser.setNickName(infoArray[1]);
                    }
                } catch (Exception e) {
                    resultMap.put("errorMsg",
                            "query loginUser has occourred exception.." + e.getMessage());
                    return resultMap;
                }
                // 将绑定的康美会员的信息返回去
                resultMap.put("loginUser", loginUser);

                // 如果是微信的话,则既刻返回
                // if ("wx".equals(acctType)) {
                // resultMap.put("nextUrl", "nextUrl");
                // return resultMap;
                // }

                log.info("the account already exist.......");

                // 判断其是否绑定的是需要完善信息的正式会员
                boolean isBindTourist = thirdBindInfoService.isBindTourist(condition);

                // 如果绑定的是临时会员,则提供完善资料的入口
                if (isBindTourist) {
                    resultMap.put("pageResult", "toFillInfo");
                    return resultMap;
                }

                // 已是正式会员,则将其到首页去
                resultMap.put("pageResult", "toIndex");

                EventUtils.postLatestLogin(loginUser.getLoginId().intValue(),
                        loginUser.getLoginAccount(), "joint");
                // 20151214 add 需要将其最后一次登录时间记录进去
                // LatestLogin latestLogin = new LatestLogin();
                // latestLogin.setN_LoginId(loginUser.getLoginId().intValue());
                // latestLogin.setLoginAccount(loginUser.getLoginAccount());
                // latestLogin.setLoginIp("joint");
                // LatestLoginRemoteService latestLoginRemoteService = (LatestLoginRemoteService)
                // RemoteTool
                // .getRemote(Constants.REMOTE_SERVICE_CUSTOMER, "latestLoginRemoteService");
                // latestLoginRemoteService.addLatestLogin(latestLogin, 3);
                return resultMap;
            }

            // 没有第三方信息则将信息存入到数据库并生成临时会员进行绑定
            if ("QQ".equals(acctType)) {
                saveQQAccountInfo(qqUser, qqOpenId);
            } else if ("wx".equals(acctType)) {
                saveWxAccountInfo(wxUser);
            } else if ("sina".equals(acctType)) {
                saveSinaAccountInfo(sinaUser);
            } else if ("alipay".equals(acctType)) {
                saveAlipayAccountInfo(aliUser);
            } else if ("taobao".equals(acctType)) {
                saveTaobaoAccountInfo(taobaoUser);
            } /*
               * else if ("fanli".equals(acctType)) { // 20151214 add saveOutAccountInfo(condition);
               * }
               */

            // 生成一个临时会员(相当于游客身份进行绑定操作,之后将这个临时用户的信息写入到session中)
            com.kmzyc.b2b.model.User tempUser = new com.kmzyc.b2b.model.User();
            tempUser.setMobile("");
            tempUser.setEmail("");
            // 第一次生成都是游客类型(20140414 生成需要完善信息的正式会员)
            tempUser.setCustomerTypeId(ThirdConstant.THIRD_CUSTOMER_TYPE_NEED_FILLINFO);
            tempUser.setLoginAccount(StringUtil.getLoginAccount(Constants.CUSTOMER_TYPE_SD_MEMBER));

            /*
             * // 20151214 add if ("fanli".equals(acctType)) {
             * tempUser.setMobile(condition.getExtend3());
             * tempUser.setEmail(condition.getExtend4());
             * tempUser.setLoginAccount(condition.getNickName().replace('@', '_'));
             * tempUser.setRegisterPlatfrom(Constants.REGISTER_PLATFORM_FL); } else {
             * tempUser.setRegisterPlatfrom(platfrom); }
             */
            tempUser.setRegisterPlatfrom(platfrom);

            tempUser.setRegisterDevice(device);
            Map<String, String> registReturnMap;
            try {

                registReturnMap = registerService.regist(tempUser);

                // TODO 邮件相关
                /*
                 * // 调用邮件订阅接口(默认订阅所有的邮件类型) try {
                 * emailSubscriptionRemoteService.addEmailRrsHisByUidAndIds(
                 * Long.parseLong(registReturnMap.get("loginId")), null);
                 * log.info("===============邮件订阅成功"); } catch (SQLException se) {
                 * se.printStackTrace(); log.error("+++++邮件订阅失败！+++++" + se.getMessage()); }
                 */
            } catch (Exception e) {
                // 若生成临时用户失败,则提示失败原因将第三方账号插入的表的数据直接删除

                resultMap.put("errorMsg",
                        "调用注册接口注册用户失败=register tempUser failed...." + e.getMessage());
                log.error("register tempUser failed...." + e.getMessage(), e);
                deleteAccountInfo(condition);
                return resultMap;
            }

            // 生成成功,则将第三方账号和生成的临时会员进行临时绑定(生成绑定记录)
            ThirdBindInfo bindInfo = new ThirdBindInfo();
            if ("QQ".equals(acctType)) {
                bindInfo.setOpenId(qqOpenId);
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_QQ);
            } else if ("wx".equals(acctType)) {
                bindInfo.setOpenId(wxUser.getOpenid());
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_WX);
            } else if ("sina".equals(acctType)) {
                bindInfo.setOpenId(sinaUser.getId());
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_SINA);
            } else if ("alipay".equals(acctType)) {
                bindInfo.setOpenId(aliUser.getAlipayUserId());
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_ALIPAY);
            } else if ("taobao".equals(acctType)) {
                bindInfo.setOpenId(taobaoUser.getUid());
                bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_TAOBAO);
            } /*
               * else if ("fanli".equals(acctType)) { // 20151214 add
               * bindInfo.setOpenId(condition.getOpenId());
               * bindInfo.setThirdAccountType(ThirdConstant.THIRD_ACCOUNT_TYPE_FANLI); }
               */

            bindInfo.setnLoginId(registReturnMap.get("loginId"));
            bindInfo.setIsBind("Y");
            bindInfo.setBindType(ThirdConstant.THIRD_BIND_TYPE_TEMP);
            bindInfo.setLastUpdateTime(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            bindInfo.setRemark("第三方" + acctType + "账号已登录进kmb2b便生成临时用户(需要完善信息的正式会员)进行绑定");

            /*
             * // 20151214 if ("fanli".equals(acctType)) { bindInfo.setRemark("第三方" + acctType +
             * "账号已登录进kmb2b便生成临时用户进行绑定,该临时用户不做近一步的转化."); }
             */

            // 如果有需要,可以将绑定康美会员的id也返回
            resultMap.put("nLoginId", registReturnMap.get("loginId"));

            // 为了防止程序有不严谨性,删除之前的记录
            thirdBindInfoService.deleteBindInfoByOpenId(bindInfo.getOpenId(),
                    bindInfo.getThirdAccountType());

            // 插入绑定信息
            thirdBindInfoService.saveBindInfo(bindInfo);

            try {
                loginUser = loginService.queryUserByLoginId(registReturnMap.get("loginId"));
                resultMap.put("loginUser", loginUser);
            } catch (Exception e) {
                resultMap.put("errorMsg",
                        "query loginUser has occourred exception.." + e.getMessage());
                log.info("query loginUser has occourred exception...." + e.getMessage());
                return resultMap;
            }

            // 如果是微信的话,则既刻返回
            // if ("wx".equals(acctType)) {
            // //resultMap.put("nextUrl", "nextUrl");
            // resultMap.put("pageResult", "toFillInfo");
            // return resultMap;
            // }

            // 可提示其去完善信息
            pageResult = "toFillInfo";
            // 异步记录日志
            EventUtils.postLatestLogin(loginUser.getLoginId().intValue(),
                    loginUser.getLoginAccount(), "joint");
            // 20151214 add 需要将其最后一次登录时间记录进去
            // LatestLogin latestLogin = new LatestLogin();
            // latestLogin.setN_LoginId(loginUser.getLoginId().intValue());
            // latestLogin.setLoginAccount(loginUser.getLoginAccount());
            // latestLogin.setLoginIp("joint"); // 代表联合登录
            // LatestLoginRemoteService latestLoginRemoteService = (LatestLoginRemoteService)
            // RemoteTool
            // .getRemote(Constants.REMOTE_SERVICE_CUSTOMER, "latestLoginRemoteService");
            // latestLoginRemoteService.addLatestLogin(latestLogin, 3);

        } catch (SQLException e) {
            log.error("insert thirdaccountinfo error" + e.getMessage(), e);
            resultMap.put("errorMsg", "insert thirdaccountinfo error.." + e.getMessage());

            return resultMap;
        }
        resultMap.put("pageResult", pageResult);
        return resultMap;
    }

    /**
     * 查询第三方帐号信息
     */
    @Override
    public ThirdAccountInfo queryThirdAccountInfo(Long loginId, String type)
            throws ServiceException {
        try {

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("loginId", loginId);
            params.put("type", type);
            return dao.queryThirdAccountInfo(params);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 20160105 add 让其统一事务进行回滚
     *
     * @param accountInfo
     * @param bindInfo
     * @return
     * @throws ServiceException
     */
    /*
     * public boolean addThirdInfo(ThirdAccountInfo accountInfo, ThirdBindInfo bindInfo) throws
     * ServiceException { try { // 设置数据源
     * 
     * dao.insert(accountInfo); bindInfoDao.insert(bindInfo); return true; } catch (Exception e) {
     * log.info("addThirdInfo 方法插入第三方账户信息表以及第三方信息绑定表出现异常,具体信息为=" + e.getCause());
     * e.printStackTrace(); throw new ServiceException(500, "addThirdInfo插入第三方信息异常"); } }
     */

    /* *//**
          * 单独只做注册动作 20160105
          *
          * @param user
          * @return
          *//*
            * public Map<String, String> registJustForFanli(com.kmzyc.b2b.model.User user) {
            * Map<String, String> map = new HashMap<String, String>(); try { LoginInfo loginInfo =
            * new LoginInfo(); // loginInfo.setEmail(user.getEmail());
            * loginInfo.setLoginAccount(user.getLoginAccount());
            * loginInfo.setLoginPassword(user.getLoginPassword()); // if
            * (!StringUtil.isEmpty(user.getMobile())) { // loginInfo.setMobile(user.getMobile()); //
            * } loginInfo.setN_CustomerTypeId(user.getCustomerTypeId());
            * loginInfo.setRegister_Device(user.getRegisterDevice());
            * loginInfo.setRegister_Platfrom(user.getRegisterPlatfrom()); // CustomerRemoteService
            * customerRemoteService = (CustomerRemoteService) RemoteTool //
            * .getRemote(Constants.REMOTE_SERVICE_CUSTOMER, "customerRemoteService"); map =
            * customerRemoteService.registerLoginInfo(loginInfo, 3); return map; } catch (Exception
            * e) { map.put("remoteOut", e.getMessage()); return map; } }
            */


}
