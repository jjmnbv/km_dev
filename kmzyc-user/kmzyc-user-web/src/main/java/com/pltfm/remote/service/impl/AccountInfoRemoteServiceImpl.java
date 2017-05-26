package com.pltfm.remote.service.impl;

import java.sql.SQLException;
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

import com.kmzyc.user.remote.service.AccountInfoRemoteService;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.dao.BnesAcctAppealInfoDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.service.PersonalBasicInfoService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.MD5;
import com.pltfm.app.util.RemoteInvoking;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.AccountInfoExample;
import com.pltfm.app.vobject.BnesAcctAppealInfo;
import com.pltfm.app.vobject.LoginInfo;

/**
 * 账户列表信息远程接口实现类
 * 
 * @author cjm
 * @since 2013-8-7
 */
@Component(value = "accountInfoRemoteService")
public class AccountInfoRemoteServiceImpl implements AccountInfoRemoteService {
    /**
     * 日志类
     */
    Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 账户信息DAO接口
     */
    @Resource(name = "accountInfoDAO")
    private AccountInfoDAO accountInfoDAO;

    /**
     * 登录信息DAO接口
     */
    @Resource(name = "loginInfoDAO")
    private LoginInfoDAO loginInfoDAO;
    /**
     * 账户申诉DAO接口
     */
    @Resource(name = "bnesAcctAppealInfoDAO")
    private BnesAcctAppealInfoDAO bnesAcctAppealInfoDAO;

    /**
     * 个人信息业务逻辑接口
     */
    @Autowired
    private PersonalBasicInfoService personalBasicInfoService;

    /**
     * 按账户信息条件查询账户信息
     * 
     * @param accountInfo 账户信息条件
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public AccountInfo selectByAccountInfo(AccountInfo accountInfo, Integer type) throws Exception {
        AccountInfo account = null;
        List<AccountInfo> list = null;
        AccountInfoExample example = null;
        // 当账户号不为null时，则根据账户号查询
        if (accountInfo != null) {
            example = new AccountInfoExample();
            example.createCriteria().andAccountLoginEqualTo(accountInfo.getAccountLogin());
        }

        list = accountInfoDAO.selectByExample(example);

        if (null != list && list.size() > 0) {
            account = list.get(0);
        }

        log.warn(RemoteInvoking.remoteInvokingType(type));
        return account;
    }

    /**
     * 注册账户信息
     * 
     * @param personalBasicInfo 账户信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> registerAccoutInfo(AccountInfo accountInfo, Integer type)
            throws Exception {
        Map<String, String> messages = new HashMap<String, String>();
        if (accountInfo != null) {
            if (accountInfo.getAccountLogin() == null
                    || accountInfo.getAccountLogin().trim().length() > 20
                    || accountInfo.getAccountLogin().trim().length() < 6) {
                messages.put("accountLogin", "请输入合法的账户号");
            }

            AccountInfoExample example = new AccountInfoExample();
            example.createCriteria().andAccountLoginEqualTo(accountInfo.getAccountLogin());
            List<AccountInfo> list = accountInfoDAO.selectByExample(example);
            if (list != null && list.size() > 0) {
                messages.put("accountLogin", "账户号已存在");
            }

            if (accountInfo.getPaymentpwd() == null || accountInfo.getPaymentpwd().length() > 20
                    || accountInfo.getPaymentpwd().length() < 6) {
                messages.put("paymentpwd", "请输入合法的支付密码");
            }
            if (accountInfo.getMobile() == null) {
                messages.put("mobile", "请输入合法的手机号码");
            } else {
                // 手机正则表达式
                Pattern mobilePattern =
                        Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

                Matcher mobileMatcher = mobilePattern.matcher(accountInfo.getMobile().trim());

                if (!mobileMatcher.matches()) {
                    messages.put("mobile", "请输入合法的手机号码");
                }
            }

            if (accountInfo.getEmail() == null) {
                messages.put("email", "请输入合法的邮箱");
            } else {
                // 邮箱正则表达式
                Pattern emailPattern = Pattern.compile(
                        "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$");

                Matcher emailMatcher = emailPattern.matcher(accountInfo.getEmail().trim());

                if (!emailMatcher.matches()) {
                    messages.put("email", "请输入合法的邮箱");
                }
            }

            if (accountInfo.getName() == null || accountInfo.getName().trim().length() > 16
                    || accountInfo.getName().trim().length() < 2) {
                messages.put("name", "请输入合法的姓名");
            }

            if (accountInfo.getAcconutId() == null) {
                messages.put("acconutId", "请输入合法的证件号码");
            } else {
                // 证件号码正则表达式
                Pattern acconutIdPattern = Pattern.compile("^(\\w){0,30}$");

                Matcher acconutIdMatcher = acconutIdPattern.matcher(accountInfo.getAcconutId());

                if (!acconutIdMatcher.matches()) {
                    messages.put("acconutId", "请输入合法的证件号码");
                }
            }

            if (accountInfo.getAddress() != null) {
                if (accountInfo.getAddress().trim().length() > 16) {
                    messages.put("address", "请输入合法的地址");
                }
            }


            if (messages.size() > 0) {
                return messages;
            }
            LoginInfo loginInfo = null;
            loginInfo = loginInfoDAO.selectByPrimaryKey(accountInfo.getN_LoginId());
            accountInfo.setN_CustomerTypeId(loginInfo.getN_CustomerTypeId());
            accountInfo.setD_CreateDate(DateTimeUtils.getCalendarInstance().getTime());
            accountInfo.setN_Status(1);
            // MD5加密
            accountInfo.setPaymentpwd(MD5.md5crypt(accountInfo.getPaymentpwd()));

            accountInfoDAO.insert(accountInfo);

            log.warn(RemoteInvoking.remoteInvokingType(type));
        } else {
            messages.put("accountInfo", "请输入登录信息");
            return messages;
        }

        return null;
    }

    /**
     * 修改账户信息 abatorgenerated_updateByLoginIdKeySelective
     * 
     * @param record 账户信息实体
     * @return 返回值
     * @throws SQLException sql异常
     */
    @Override
    public int updateByLoginId(AccountInfo accountInfo) throws Exception {
        int row = 0;
        if (accountInfo.getN_LoginId() != null) {
            row = accountInfoDAO.updateByLoginId(accountInfo);
            /*
             * 删除推送总部会员系统 try { // 修改的信息处理同步 List<String> lstAccountLogin = new ArrayList<String>();
             * lstAccountLogin.add(accountInfo.getAccountLogin());
             * personalBasicInfoService.syncPersonalBasicInfo2Base(lstAccountLogin); } catch
             * (ServiceException e) { log.error("同步修改的用户信息到总部系统失败:accountInfo:" +
             * accountInfo.toString()); }
             */
        }
        return row;
    }

    // 添加申诉
    @Override
    public int insertAcctAppealInfo(BnesAcctAppealInfo record) throws SQLException {
        int row = 0;
        if (record.getnLoginId() != null) {
            row = bnesAcctAppealInfoDAO.insertAcctAppealInfo(record);
        }
        return row;
    }

    public AccountInfoDAO getAccountInfoDAO() {
        return accountInfoDAO;
    }


    public void setAccountInfoDAO(AccountInfoDAO accountInfoDAO) {
        this.accountInfoDAO = accountInfoDAO;
    }

    public BnesAcctAppealInfoDAO getBnesAcctAppealInfoDAO() {
        return bnesAcctAppealInfoDAO;
    }

    public void setBnesAcctAppealInfoDAO(BnesAcctAppealInfoDAO bnesAcctAppealInfoDAO) {
        this.bnesAcctAppealInfoDAO = bnesAcctAppealInfoDAO;
    }

}
