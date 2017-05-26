package com.kmzyc.b2b.action;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.km.framework.common.util.MD5;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.MessageRemoteService;
import com.kmzyc.b2b.service.RegistService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.b2b.vo.ChangePasswordInfo;
import com.kmzyc.b2b.vo.ChangePayPasswordInfo;
import com.kmzyc.b2b.vo.OrderTrailInfo;
import com.kmzyc.b2b.vo.SecurityCentreInfo;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.b2b.vo.VerifyMobileInfo;
import com.kmzyc.framework.ajax.AjaxUtil;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.MessageTypeEnum;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.user.remote.service.MobileCodeInfRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.vobject.MobileCodeInf;
import com.whalin.MemCached.MemCachedClient;

@SuppressWarnings({"serial", "unchecked"})
@Controller("securityCentreAction")
@Scope("prototype")
public class SecurityCentreAction extends BaseAction {
    private static Logger logger = LoggerFactory.getLogger(SecurityCentreAction.class);

    private SecurityCentreInfo securityCentreInfo;
    private ChangePasswordInfo changePasswordInfo;
    private VerifyMobileInfo verifyMobileInfo;
    private ChangePayPasswordInfo changePayPasswordInfo;
    private String sessionVarName;
    private User user;
    private String veCode; // 获取邮箱验证链接的类型
    private String uptype;// 判断是修改邮箱还是验证邮箱
    private String emiaurl;// 邮箱立即验证的链接
    private String times;// 链接超时
    private OrderTrailInfo orderTrailInfo;// 用于发送手机验证码
    private String emails;// 保存邮箱
    @Resource(name = "securityCentreServiceImpl")
    private SecurityCentreService securityCentreService;
    @Resource(name = "registServiceImp")
    private RegistService registservice;
    @Resource(name = "messageRemoteService")
    private MessageRemoteService messageRemoteService;
    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;
    @Resource(name = "myOrderServiceImpl")
    private MyOrderService myOrderService;

    @Resource
    private CustomerRemoteService customerRemoteService;

    @Resource
    private MobileCodeInfRemoteService mobileCodeInfRemoteService;

    private String payRange;
    // 旧的手机号码，页面校验使用
    private String oldMobileNumber;
    // 输入的手机号码，校验使用
    private String mobileNumber;
    private String isFile;// 用来区别提示信息
    // 页面传入的校验密码参数，为登录密码或登录密码
    private String passWord;
    // 验证码，用于校验
    private String verificationCode;
    // 短信校验有效时间,单位： 分钟
    // private String MESSAGE_VALID_TIME = ConfigurationUtil.getString("b2b_common_msg_valid_time");
    // 登录用户IDkey前缀
    // private String UID_PREX_KEY =
    // ConfigurationUtil.getString("b2b_login_uid_memcached_key_prex");
    // 判断密码安全级别的属性 1 ：弱 2：中等 3：强
    private int safeLevel;

    // private static final String SOURCE_URL =
    // ConfigurationUtil.getString("source_Url").toString();

    /* str是否包含searchChars */
    static boolean containsAny(String str, String searchChars) {
        return str.contains(searchChars);
    }

    /**
     * 显示安全中心
     * 
     * @return
     * @throws ServiceException
     */
    public String showSecurityCentre() throws ServiceException {
        HttpSession session = this.getSession();
        try {
            Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);

            /*
             * if ("Y".equals(session.getAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER))) {
             * logger.info("未完善资料的第三方帐号在访问安全中心！ userId:" + userId); return "thirdError"; }
             */
            if (Constants.LOGINTYPE.equals(session.getAttribute(Constants.SESSION_B2B_OR_ERA))) {
                /*
                 * logger.info("未完善资料的直销帐号在访问安全中心！ userId:" + userId); return "zxError";
                 */
                this.getRequest().setAttribute("isTimesMember", "Y");
            }

            user = securityCentreService.getUserByLoginId(userId);
            securityCentreInfo = securityCentreService.querySecurityCentreInfo(user);
            if (securityCentreInfo.getMobileNumber() != null) {
                securityCentreInfo
                        .setMobileNumber(securityCentreInfo.getMobileNumber().substring(0, 4)
                                + "****" + securityCentreInfo.getMobileNumber().substring(8, 11));
            }
            /*
             * if (securityCentreInfo.getEmailAddress() != null) { if
             * (securityCentreInfo.getEmailAddress() .substring(0,
             * securityCentreInfo.getEmailAddress().lastIndexOf("@")) .length() <= 4) {
             * securityCentreInfo.setEmailAddress(securityCentreInfo.getEmailAddress() .substring(0,
             * 1) + "*****" + securityCentreInfo.getEmailAddress().substring(
             * securityCentreInfo.getEmailAddress().lastIndexOf("@"),
             * securityCentreInfo.getEmailAddress().length())); } else {
             * securityCentreInfo.setEmailAddress(securityCentreInfo.getEmailAddress() .substring(0,
             * 3) + "*****" + securityCentreInfo.getEmailAddress().substring(
             * securityCentreInfo.getEmailAddress().lastIndexOf("@"),
             * securityCentreInfo.getEmailAddress().length())); } }
             */
        } catch (ServiceException se) {
            logger.error("初始化安全中心页面出现了异常：" + se.getMessage(), se);
            return ERROR;
        } catch (Exception e) {
            logger.error("初始化安全中心页面出现了异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 去修改密码页面
     * 
     * @return
     */
    public String goChangePassword() {
        /*** 150311 wangkai ***/
        HttpSession session = this.getSession();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);

        if ("Y".equals(session.getAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER))) {
            logger.info("未完善资料的第三方帐号在访问安全中心！ userId:" + userId);
            return "thirdError";
        }
        if ((String) session.getAttribute(Constants.SESSION_Zx_USER_NAME) == null
                && Constants.LOGINTYPE.equals(session.getAttribute(Constants.SESSION_B2B_OR_ERA))) {
            logger.info("未完善资料的直销帐号在访问安全中心！ userId:" + userId);
            return "zxError";
        }
        /******/

        return SUCCESS;
    }

    /**
     * wap版去修改密码的页面
     * 
     * @return
     */
    public String goWapChangePassword() {
        return SUCCESS;
    }

    /**
     * wap 验证手机第一步，验证登录密码
     * 
     * @return
     */
    public String goWapMobileVerifyPassword() {
        return SUCCESS;
    }

    /**
     * wap版去手机验证的页面
     * 
     * @return
     */
    public String goWapVerifyMobile() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        user = securityCentreService.getUserByLoginId(userId);
        String password = verifyMobileInfo.getLoginPassword();
        try {
            boolean rs = securityCentreService.verifyPassword(password, user);
            oldMobileNumber = user.getMobile();
            verifyMobileInfo.setLoginId(user.getLoginId());
            if (oldMobileNumber != null) {
                verifyMobileInfo.setMobileNumber(oldMobileNumber.substring(0, 4) + "****"
                        + oldMobileNumber.substring(8, 11));
            }
            if (rs) {
                return SUCCESS;
            } else {
                return "input";
            }
        } catch (Exception e) {
            logger.error("提交手机验证时异常：" + e.getMessage(), e);
            return ERROR;
        }
    }

    /**
     * 提交修改密码
     * 
     * @return
     */
    public String postChangePassword() {
        HttpSession session = this.getSession();
        try {
            Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
            user = securityCentreService.getUserByLoginId(userId);
            String newPassWord =
                    java.net.URLDecoder.decode(changePasswordInfo.getNewPassword(), "UTF-8");
            changePasswordInfo.setNewPassword(newPassWord);
            boolean rs = securityCentreService.changePassword(user, changePasswordInfo);
            if (rs) {
                memCachedClient.delete(
                        ConfigurationUtil.getString("b2b_login_uid_memcached_key_prex") + userId);// 移除令牌
                return SUCCESS;
            } else {
                return "input";
            }
        } catch (Exception e) {
            logger.error("修改密码异常：" + e.getMessage(), e);
            return ERROR;
        }
    }

    /**
     * wap完成修改密码
     * 
     * @return
     */
    public String wapChangePasswordDone() {
        HttpSession session = this.getSession();
        try {
            Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
            user = securityCentreService.getUserByLoginId(userId);
            String newPassWord =
                    java.net.URLDecoder.decode(changePasswordInfo.getNewPassword(), "UTF-8");
            changePasswordInfo.setNewPassword(newPassWord);
            boolean rs = securityCentreService.changePassword(user, changePasswordInfo);
            if (rs) {
                memCachedClient.delete(
                        ConfigurationUtil.getString("b2b_login_uid_memcached_key_prex") + userId);// 移除令牌
                return SUCCESS;
            } else {
                return "input";
            }
        } catch (Exception e) {
            logger.error("修改密码异常：" + e.getMessage(), e);
            return ERROR;
        }
    }

    /**
     * 去手机验证身份界面
     * 
     * @return
     */
    public String goMobileVerifyPassword() {
        HttpSession session = this.getSession();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        /*** 第三方和直销绑定验证 ***/
        if ("Y".equals(session.getAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER))) {
            logger.info("未完善资料的第三方帐号在访问安全中心！ userId:" + userId);
            return "thirdError";
        }
        if ((String) session.getAttribute(Constants.SESSION_Zx_USER_NAME) == null
                && Constants.LOGINTYPE.equals(session.getAttribute(Constants.SESSION_B2B_OR_ERA))) {
            logger.info("未完善资料的直销帐号在访问安全中心！ userId:" + userId);
            return "zxError";
        }
        /******/
        return SUCCESS;
    }

    public String accountManage() {
        HttpSession session = this.getSession();
        try {
            Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
            user = securityCentreService.getUserByLoginId(userId);
            securityCentreInfo = securityCentreService.querySecurityCentreInfo(user);
            if (securityCentreInfo.getMobileNumber() != null) {
                securityCentreInfo
                        .setMobileNumber(securityCentreInfo.getMobileNumber().substring(0, 4)
                                + "****" + securityCentreInfo.getMobileNumber().substring(8, 11));
            }
            if (securityCentreInfo.getEmailAddress() != null) {
                if (securityCentreInfo.getEmailAddress()
                        .substring(0, securityCentreInfo.getEmailAddress().lastIndexOf("@"))
                        .length() <= 4) {
                    securityCentreInfo
                            .setEmailAddress(
                                    securityCentreInfo.getEmailAddress().substring(0, 1) + "*****"
                                            + securityCentreInfo.getEmailAddress().substring(
                                                    securityCentreInfo.getEmailAddress()
                                                            .lastIndexOf("@"),
                                                    securityCentreInfo.getEmailAddress().length()));
                } else {
                    securityCentreInfo
                            .setEmailAddress(
                                    securityCentreInfo.getEmailAddress().substring(0, 3) + "*****"
                                            + securityCentreInfo.getEmailAddress().substring(
                                                    securityCentreInfo.getEmailAddress()
                                                            .lastIndexOf("@"),
                                                    securityCentreInfo.getEmailAddress().length()));
                }
            }
        } catch (ServiceException se) {
            logger.error("初始化安全中心页面出现了异常：" + se.getMessage(), se);
            return ERROR;
        } catch (Exception e) {
            logger.error("初始化安全中心页面出现了异常：" + e.getMessage(), e);
            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * 提交手机验证身份
     * 
     * @return
     */
    public String postMobileVerifyPassword() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        user = securityCentreService.getUserByLoginId(userId);
        String password = verifyMobileInfo.getLoginPassword();
        try {
            boolean rs = securityCentreService.verifyPassword(password, user);
            oldMobileNumber = user.getMobile();
            verifyMobileInfo.setLoginId(user.getLoginId());
            if (oldMobileNumber != null) {
                verifyMobileInfo.setMobileNumber(oldMobileNumber.substring(0, 4) + "****"
                        + oldMobileNumber.substring(8, 11));
            }
            // 判断手机是否验证以及是否存在一笔在线支付交易订单完成记录
            /*
             * int info = myOrderService.enablePayPWD(userId); if(info != 1){ return "mobInput"; }
             */
            if (rs) {
                return SUCCESS;
            } else {
                return "input";
            }
        } catch (Exception e) {
            logger.error("提交手机验证时异常：" + e.getMessage(), e);
            return ERROR;
        }
    }

    /**
     * 判断手机是否验证
     */
    public String reVerifyMobile() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        user = securityCentreService.getUserByLoginId(userId);
        String info;
        try {
            if (StringUtil.isEmpty(user.getMobile())) {// 未绑定手机
                info = "1";
            } else {
                info = "0";
            }
            getResponse().getWriter().print(info);
        } catch (Exception e) {
            logger.error("判断手机是否绑定功能异常：" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    /**
     * 验证手机跟支付值大于5
     */
    public String validateMobilePayPw() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        int info = myOrderService.enablePayPWD(userId);
        try {
            getResponse().getWriter().print(info);
        } catch (Exception e) {
            logger.error("验证手机跟支付值大于5：" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    /**
     * 判断支付密码是否启用
     */
    public String reVerifyPay() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        user = securityCentreService.getUserByLoginId(userId);
        String info;
        try {
            AccountInfo accountInfo = securityCentreService.accountByUserId(user.getLoginId());
            if (StringUtil.isEmpty(accountInfo.getPaymentpwd())) {// 未支付密码
                info = "1";
            } else {
                info = "0";
            }
            getResponse().getWriter().print(info);
        } catch (Exception e) {
            logger.error("判断手机是否绑定功能异常：" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    /**
     * 提交手机验证 : 安全中心 手机验证功能
     * 
     * @return
     */
    public String postVerifyMobile() {
        HttpSession session = this.getSession();
        try {
            Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
            String sessionmobileVerifyCode =
                    (String) session.getAttribute("OrderTrailMobileVerifyCode");
            // 获取Session 中的手机号码
            String mobilePhone = (String) session.getAttribute("mobilePhone");
            // 判断是否是首次验证手机
            boolean ifFirstVerifyMobile = true;
            user = securityCentreService.getUserByLoginId(userId);
            if (user.getMobile() != null && !(user.getMobile()).equals("")) {
                ifFirstVerifyMobile = false;
            }
            // ---20150824 修改手机号码 （是首次 是否给奖励 用户系统判断）---

            if (null == verifyMobileInfo) {
                logger.error("提交发送手机验证时失败:参数为空 " + userId);
                return ERROR;
            }

            boolean rs = securityCentreService.verifyMobile(verifyMobileInfo, user,
                    sessionmobileVerifyCode, mobilePhone);

            user = securityCentreService.getUserByLoginId(user.getLoginId());
            verifyMobileInfo.setMobileNumber(user.getMobile());
            verifyMobileInfo.setLoginId(user.getLoginId());
            AccountInfo accountInfo = securityCentreService.accountByUserId(user.getLoginId());
            // 添加判断是否当前用户修改手机验证
            // 获取用户ID
            String loginId = getRequest().getParameter("loginId");
            if (Integer.valueOf(loginId).compareTo(userId.intValue()) != 0) {
                logger.error("非当前用户进行手机验证,用户id:" + userId);
                return ERROR;
            }
            securityCentreInfo = new SecurityCentreInfo();
            if (accountInfo.getPaymentpwd() != null && accountInfo.getPaymentpwd().length() > 0) {
                securityCentreInfo.setPayPasswordInvocation(true);
            } else {
                securityCentreInfo.setPayPasswordInvocation(false);
            }
            if (rs) {
                session.removeAttribute("OrderTrailMobileVerifyCode");
                // 将更新后的用户信息写入缓存
                user.setMobile(verifyMobileInfo.getMobileNumber());
                return SUCCESS;
            } else {
                return "input";
            }
        } catch (Exception e) {
            logger.error("提交发送手机验证时异常：" + e.getMessage(), e);
            return ERROR;
        }

    }

    /**
     * wap版本验证手机提交
     * 
     * @return
     */
    public String postWapVerifyMobile() {
        HttpSession session = this.getSession();
        try {
            Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
            String sessionmobileVerifyCode =
                    (String) session.getAttribute("OrderTrailMobileVerifyCode");
            // 获取Session 中的手机号码
            String mobilePhone = (String) session.getAttribute("mobilePhone");
            // 判断是否是首次验证手机
            boolean ifFirstVerifyMobile = true;
            user = securityCentreService.getUserByLoginId(userId);
            if (user.getMobile() != null && !(user.getMobile()).equals("")) {
                ifFirstVerifyMobile = false;
            }
            // ---20150824 修改手机号码 （是首次 是否给奖励 用户系统判断）---

            if (null == verifyMobileInfo) {
                logger.error("提交发送手机验证时失败:参数为空 " + userId);
                return ERROR;
            }

            boolean rs = securityCentreService.verifyMobile(verifyMobileInfo, user,
                    sessionmobileVerifyCode, mobilePhone);

            user = securityCentreService.getUserByLoginId(user.getLoginId());
            verifyMobileInfo.setMobileNumber(user.getMobile());
            verifyMobileInfo.setLoginId(user.getLoginId());
            AccountInfo accountInfo = securityCentreService.accountByUserId(user.getLoginId());
            // 添加判断是否当前用户修改手机验证
            // 获取用户ID
            String loginId = getRequest().getParameter("loginId");
            if (Integer.valueOf(loginId).compareTo(userId.intValue()) != 0) {
                logger.error("非当前用户进行手机验证,用户id:" + userId);
                return ERROR;
            }
            securityCentreInfo = new SecurityCentreInfo();
            if (accountInfo.getPaymentpwd() != null && accountInfo.getPaymentpwd().length() > 0) {
                securityCentreInfo.setPayPasswordInvocation(true);
            } else {
                securityCentreInfo.setPayPasswordInvocation(false);
            }
            // 判断手机是否验证以及是否存在一笔在线支付交易订单完成记录
            /*
             * int flag = myOrderService.enablePayPWD(userId); if(flag != 1){ return "mobInput"; }
             */
            if (rs) {
                session.removeAttribute("OrderTrailMobileVerifyCode");
                // 将更新后的用户信息写入缓存
                user.setMobile(verifyMobileInfo.getMobileNumber());
                return SUCCESS;
            } else {
                return "input";
            }

        } catch (Exception e) {
            logger.error("提交发送手机验证时异常：" + e.getMessage(), e);
            return ERROR;
        }

    }

    /**
     * 去邮箱身份验证页面
     * 
     * @return
     */
    public String goEmailVerifyPassword() {
        HttpSession session = this.getSession();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        /*** 第三方和直销绑定验证 ***/
        if ("Y".equals(session.getAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER))) {
            logger.info("未完善资料的第三方帐号在访问安全中心！ userId:" + userId);
            return "thirdError";
        }
        if ((String) session.getAttribute(Constants.SESSION_Zx_USER_NAME) == null
                && Constants.LOGINTYPE.equals(session.getAttribute(Constants.SESSION_B2B_OR_ERA))) {
            logger.info("未完善资料的直销帐号在访问安全中心！ userId:" + userId);
            return "zxError";
        }
        /******/

        return SUCCESS;
    }

    /**
     * 邮箱验证身份
     * 
     * @return
     */
    public String verifyUserPassword() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        try {
            boolean rs = securityCentreService.verifyUserPassword(sessionVarName, userId);
            Map map = new HashMap<String, Boolean>();
            map.put("verifyLoginPasswordrs", rs);
            AjaxUtil.writeJSONToResponse(map);
        } catch (Exception e) {
            logger.error("邮箱验证身份出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return NONE;
    }

    /**
     * 获取web项目根路径
     * 
     * @return
     */
    protected String getWebRoot() {

        logger.error("获取web项目根路径");
        HttpServletRequest request = this.getRequest();

        // 得到协议如：http
        String scheme = request.getScheme();

        // 得到服务器名称如：127.0.0.1
        String serverName = request.getServerName();

        // 得到端口号如8080
        int serverPort = request.getServerPort();

        // 得到当前上下文路径，也就是安装后的文件夹位置。
        String contextPath = request.getContextPath();

        // 连起来拼成完整的url
        String webRoot = scheme + "://" + serverName + (serverPort != 80 ? (":" + serverPort) : "")
                + contextPath + "/";

        return webRoot;
    }

    /**
     * 去支付启用身份验证界面
     * 
     * @return
     */
    public String goPayVerifyPassword() {
        HttpSession session = this.getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        try {
            // 跳转之前在做判断，判断手机通过验证 以及支付超过5元的订单
            int info = myOrderService.enablePayPWD(userId);
            if (info != 1) {
                return "valid";
            }

            /*** 第三方和直销绑定验证 ***/
            if ("Y".equals(session.getAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER))) {
                logger.info("未完善资料的第三方帐号在访问安全中心！ userId:" + userId);
                return "thirdError";
            }
            if ((String) session.getAttribute(Constants.SESSION_Zx_USER_NAME) == null
                    && Constants.LOGINTYPE
                            .equals(session.getAttribute(Constants.SESSION_B2B_OR_ERA))) {
                logger.info("未完善资料的直销帐号在访问安全中心！ userId:" + userId);
                return "zxError";
            }
            /******/
            user = securityCentreService.getUserByLoginId(userId);
            if (verifyMobileInfo == null) {
                verifyMobileInfo = new VerifyMobileInfo();
            }
            if (user.getMobile() != null) {
                verifyMobileInfo.setMobileNumber(user.getMobile().substring(0, 4) + "****"
                        + user.getMobile().substring(8, 11));
            }
            // 设置真实的手机号码
            mobileNumber = user.getMobile();

        } catch (Exception e) {
            logger.error("支付启用身份验证出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * wap版去支付启用身份验证界面
     * 
     * @return
     */
    public String goWapPayVerifyPassword() {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
            // 跳转之前在做判断，判断手机通过验证 以及支付超过5元的订单
            int info = myOrderService.enablePayPWD(userId);
            if (info != 1) {
                return "valid";
            }

            user = securityCentreService.getUserByLoginId(userId);
            if (verifyMobileInfo == null) {
                verifyMobileInfo = new VerifyMobileInfo();
            }
            if (user.getMobile() != null) {
                verifyMobileInfo.setMobileNumber(user.getMobile().substring(0, 4) + "****"
                        + user.getMobile().substring(8, 11));
            }
            // 设置真实的手机号码
            mobileNumber = user.getMobile();
        } catch (Exception e) {
            logger.error("支付启用身份验证出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 支付密码，发送手机验证码
     * 
     * @return
     * @throws ServiceException
     */
    public String sendMobileVerifyCode() throws ServiceException {
        HttpSession session = this.getSession();
        // String mobile = changePayPasswordInfo.getMobileNumber();
        HttpServletRequest request = ServletActionContext.getRequest();

        try {
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
            user = securityCentreService.getUserByLoginId(userId);
            String mobile = user.getMobile();
            /*** wk20150909添回来源 ***/
            String url = getRequest().getHeader("Referer");
            logger.info("来源打印：" + url);
            if (null != url) {
                if (!containsAny(url, ConfigurationUtil.getString("source_Url"))
                        && !containsAny(url, "kmb2b.com") && !containsAny(url, "v.kmb2b.com")
                        && !containsAny(url, "m.kmb2b.com")) {
                    logger.info("非正常来源  " + mobile + " 来源：" + url);
                    AjaxUtil.writeJSONToResponse(false);
                    return NONE;
                }
            } else {
                logger.info("非正常来源  " + mobile);
                AjaxUtil.writeJSONToResponse(false);
                return NONE;
            }

            /*** wk20150909添回来源 ***/
            boolean rs = messageRemoteService.sendMobileVerifyCode(mobile, session,
                    MessageTypeEnum.ORDERTRAIL.getStatus());
            AjaxUtil.writeJSONToResponse(rs);
        } catch (Exception e) {
            logger.error("支付密码，发送手机验证码出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return NONE;
    }

    /**
     * 校验手机校验码 add by songmiao
     * 
     * @return
     * @throws ServiceException
     */
    public String getSessionVar() throws ServiceException {
        HttpSession session = this.getSession();
        Map<String, String> map = new HashMap<String, String>();
        String sessionMobileVerifyCode = null;
        String url = getRequest().getHeader("Referer");
        logger.info("来源打印：" + url);
        // if (containsAny(url, "goPayVerifyPassword.action")
        // || containsAny(url, "goWapPayVerifyPassword.action")
        // || containsAny(url, "postMobileVerifyPassword.action")
        // || containsAny(url, "goWapVerifyMobile.action")) {
        // sessionMobileVerifyCode = (String) session.getAttribute("VoiceMobileVerifyCode");
        // } else {
        sessionMobileVerifyCode = (String) session.getAttribute("OrderTrailMobileVerifyCode");
        // }
        // 获取Session 中的手机号码
        String sessinonMobilePhone = (String) session.getAttribute("mobilePhone");
        if (sessionMobileVerifyCode != null
                && verificationCode.trim().equals(sessionMobileVerifyCode.trim())
                && sessinonMobilePhone != null && sessinonMobilePhone.equals(mobileNumber.trim())) {
            Date date = (Date) session.getAttribute("mobileVerifyCodeTime");
            if (null != date && (new Date().getTime() - date.getTime()) < (Integer.parseInt(
                    ConfigurationUtil.getString("b2b_common_msg_valid_time")) * 60 * 1000)) {
                // 成功通过校验
                map.put(sessionVarName, "1");
            } else {
                // 校验码正确，但超时
                map.put(sessionVarName, "2");
            }
        } else {
            // 校验码错误
            map.put(sessionVarName, "0");
        }

        AjaxUtil.writeJSONToResponse(map);

        return NONE;
    }

    public String verifyLoginPassword() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
            user = securityCentreService.getUserByLoginId(userId);
            boolean rs = securityCentreService.verifyPassword(sessionVarName, user);
            Map map = new HashMap();
            map.put("verifyLoginPasswordrs", rs);
            AjaxUtil.writeJSONToResponse(map);
        } catch (Exception e) {
            logger.error("SecurityCentreAction的verifyLoginPassword()方法出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return NONE;
    }

    /**
     * 
     * @return
     */
    public String postPayVerifyPassword() {
        String mobileVerifyCode = changePayPasswordInfo.getMobileVerifyCode();
        HttpSession session = this.getSession();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        // 跳转之前在做判断，判断手机通过验证 以及支付超过5元的订单
        int info = myOrderService.enablePayPWD(userId);
        if (info != 1) {
            return "valid";
        }

        user = securityCentreService.getUserByLoginId(userId);
        String sessionMobileVerifyCode =
                (String) session.getAttribute("OrderTrailMobileVerifyCode");
        if (sessionMobileVerifyCode != null && mobileVerifyCode != null
                && mobileVerifyCode.trim().equals(sessionMobileVerifyCode.trim())) {
            // 插入成功数据到mobile_code_inf
            // MobileCodeInfRemoteService mobileCodeInfRemoteService;
            try {
                // mobileCodeInfRemoteService =
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
                mobileCodeInf.setTattedCode(mobileVerifyCode);
                mobileCodeInf.setMobile(user.getMobile());
                mobileCodeInf.setMobile_Type(3);// 短信验证码类型 : 忘记密码 ------1 手机验证
                // ------2 支付密码修改 ----3
                mobileCodeInf.setIs_Status(1);
                int row = mobileCodeInfRemoteService.addMobileCodeInf(mobileCodeInf, 3);
                // 手机随机码记录主键
                mobileCodeInf.setN_CellPhoneTattedCodeId(row);
                mobileCodeInfRemoteService.updateMobileCodeInfByPrimaryKey(mobileCodeInf, 3);
                session.removeAttribute("OrderTrailMobileVerifyCode");
            } catch (MalformedURLException e) {
                logger.error("SecurityCentreAction的postPayVerifyPassword()方法出现异常：" + e.getMessage(),
                        e);
                return ERROR;
            } catch (ClassNotFoundException e) {
                logger.error("SecurityCentreAction的postPayVerifyPassword()方法出现异常：" + e.getMessage(),
                        e);
                return ERROR;
            } catch (Exception e) {
                logger.error("SecurityCentreAction的postPayVerifyPassword()方法出现异常：" + e.getMessage(),
                        e);
            }
            session.setAttribute("PayVerifyPassword_" + userId, "0");
            return SUCCESS;
        } else {
            return "input";
        }
    }

    /**
     * 
     * @return
     */
    public String postWapPayVerifyPassword() {
        String mobileVerifyCode = changePayPasswordInfo.getMobileVerifyCode();
        HttpSession session = this.getSession();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        // 跳转之前在做判断，判断手机通过验证 以及支付超过5元的订单
        int info = myOrderService.enablePayPWD(userId);
        if (info != 1) {
            return "valid";
        }
        user = securityCentreService.getUserByLoginId(userId);
        String sessionMobileVerifyCode =
                (String) session.getAttribute("OrderTrailMobileVerifyCode");
        if (sessionMobileVerifyCode != null && mobileVerifyCode != null
                && mobileVerifyCode.trim().equals(sessionMobileVerifyCode.trim())) {
            // 插入成功数据到mobile_code_inf
            // MobileCodeInfRemoteService mobileCodeInfRemoteService;
            try {
                // mobileCodeInfRemoteService =
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
                mobileCodeInf.setTattedCode(mobileVerifyCode);
                mobileCodeInf.setMobile(user.getMobile());
                mobileCodeInf.setMobile_Type(3);// 短信验证码类型 : 忘记密码 ------1 手机验证
                // ------2 支付密码修改 ----3
                mobileCodeInf.setIs_Status(1);
                int row = mobileCodeInfRemoteService.addMobileCodeInf(mobileCodeInf, 3);
                // 手机随机码记录主键
                mobileCodeInf.setN_CellPhoneTattedCodeId(row);
                mobileCodeInfRemoteService.updateMobileCodeInfByPrimaryKey(mobileCodeInf, 3);
                session.removeAttribute("OrderTrailMobileVerifyCode");
            } catch (Exception e) {
                logger.error("SecurityCentreAction的postPayVerifyPassword()方法出现异常：" + e.getMessage(),
                        e);
            }
            session.setAttribute("PayVerifyPassword_" + userId, "0");
            return SUCCESS;
        } else {
            return "input";
        }
    }

    /**
     * 修改支付密码提交 add by songmiao 2013-11-20
     * 
     * @return
     */
    public String postModifyPayPassword() {
        HttpSession session = this.getSession();
        try {
            Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
            String PayVerifyPassword = (String) session.getAttribute("PayVerifyPassword_" + userId);
            if (!"0".equals(PayVerifyPassword)) {
                return ERROR;
            }
            // 跳转之前在做判断，判断手机通过验证 以及支付超过5元的订单
            int info = myOrderService.enablePayPWD(userId);
            if (info != 1) {
                return "valid";
            }
            String newPayPassWord =
                    java.net.URLDecoder.decode(changePayPasswordInfo.getPayPassword(), "UTF-8");
            changePayPasswordInfo.setPayPassword(newPayPassWord);
            boolean isChange = false;

            user = securityCentreService.getUserByLoginId(userId);
            AccountInfo accountInfo = securityCentreService.accountByUserId(user.getLoginId());

            if (accountInfo.getPaymentpwd() != null && !accountInfo.getPaymentpwd().equals("")) {
                isChange = true;
            } else {
                isChange = false;
            }
            // 提交修改支付密码
            boolean rs = securityCentreService.modifyPayPassword(changePayPasswordInfo, user);
            changePayPasswordInfo.setModifyStatus(isChange);
            if (rs) {
                session.removeAttribute("PayVerifyPassword_" + userId);
                return SUCCESS;
            } else {
                return "input";
            }
        } catch (Exception e) {
            logger.error("SecurityCentreAction的postModifyPayPassword()方法出现异常：" + e.getMessage(), e);
            return ERROR;
        }
    }

    /**
     * 修改支付密码提交 add by liaotiansheng 2015-04-17
     * 
     * @return
     */
    public String postWapModifyPayPassword() {
        HttpSession session = this.getSession();
        try {
            Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
            // 跳转之前在做判断，判断手机通过验证 以及支付超过5元的订单
            int info = myOrderService.enablePayPWD(userId);
            if (info != 1) {
                return "valid";
            }

            String PayVerifyPassword = (String) session.getAttribute("PayVerifyPassword_" + userId);
            if (!"0".equals(PayVerifyPassword)) {
                return ERROR;
            }
            String newPayPassWord =
                    java.net.URLDecoder.decode(changePayPasswordInfo.getPayPassword(), "UTF-8");
            changePayPasswordInfo.setPayPassword(newPayPassWord);
            boolean isChange = false;

            user = securityCentreService.getUserByLoginId(userId);
            AccountInfo accountInfo = securityCentreService.accountByUserId(user.getLoginId());

            if (accountInfo.getPaymentpwd() != null && !accountInfo.getPaymentpwd().equals("")) {
                isChange = true;
            } else {
                isChange = false;
            }
            // 提交修改支付密码
            boolean rs = securityCentreService.modifyPayPassword(changePayPasswordInfo, user);
            changePayPasswordInfo.setModifyStatus(isChange);
            if (rs) {
                session.removeAttribute("PayVerifyPassword_" + userId);
                return SUCCESS;
            } else {
                return "input";
            }
        } catch (Exception e) {
            logger.error("SecurityCentreAction的postModifyPayPassword()方法出现异常：" + e.getMessage(), e);
            return ERROR;
        }
    }

    public String goPaySetting() {
        logger.info("SecurityCentreAction的goPaySetting()方法");
        HttpSession session = this.getSession();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        user = securityCentreService.getUserByLoginId(userId);
        changePayPasswordInfo = new ChangePayPasswordInfo();
        AccountInfo accountInfo = securityCentreService.getAccountInfo(user);
        changePayPasswordInfo.setPayRange(accountInfo.getPayRange());
        return SUCCESS;
    }

    public String goWapPaySetting() {
        logger.info("SecurityCentreAction的goWapPaySetting()方法");
        HttpSession session = this.getSession();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        user = securityCentreService.getUserByLoginId(userId);
        changePayPasswordInfo = new ChangePayPasswordInfo();
        AccountInfo accountInfo = securityCentreService.getAccountInfo(user);
        changePayPasswordInfo.setPayRange(accountInfo.getPayRange());
        return SUCCESS;
    }

    public String postPaySetting() {
        HttpSession session = this.getSession();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        user = securityCentreService.getUserByLoginId(userId);
        try {
            if (StringUtil.isEmpty(payRange)) {
                payRange = "1";
            } else {
                payRange = "1," + payRange.trim();
            }
            boolean rs = securityCentreService.saveAccountInfoPayRange(payRange, user);
            if (rs) {
                return SUCCESS;
            } else {
                return "input";
            }
        } catch (Exception e) {
            logger.error("SecurityCentreAction的postPaySetting()方法出现异常：" + e.getMessage(), e);
            return ERROR;
        }
    }

    /**
     * 
     * @return
     */
    public String postWapPaySetting() {
        HttpSession session = this.getSession();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        user = securityCentreService.getUserByLoginId(userId);
        try {
            if (StringUtil.isEmpty(payRange)) {
                payRange = "1";
            } else {
                payRange = "1," + payRange.trim();
            }
            boolean rs = securityCentreService.saveAccountInfoPayRange(payRange, user);
            if (rs) {
                return SUCCESS;
            } else {
                return "input";
            }
        } catch (Exception e) {
            logger.error("SecurityCentreAction的postWapPaySetting()方法出现异常：" + e.getMessage(), e);
            return ERROR;
        }
    }

    /**
     * 登录密码修改，手机验证，发送手机验证码
     * 
     * @return
     */
    public String sendmyTelVeriyCode() {
        HttpSession session = this.getSession();
        try {
            /*** wk20150909添回来源 ***/
            String url = getRequest().getHeader("Referer");
            logger.info("来源打印：" + url);
            if (null != url) {
                if (!containsAny(url, ConfigurationUtil.getString("source_Url"))
                        && !containsAny(url, "kmb2b.com") && !containsAny(url, "v.kmb2b.com")
                        && !containsAny(url, "m.kmb2b.com")) {
                    logger.info("非正常来源  " + orderTrailInfo.getMobileVerifyCode() + " 来源：" + url);
                    AjaxUtil.writeJSONToResponse(false);
                    return NONE;
                }
            } else {
                logger.info("非正常来源  " + orderTrailInfo.getMobileVerifyCode());
                AjaxUtil.writeJSONToResponse(false);
                return NONE;
            }
            /*** wk20150909添回来源 ***/
            boolean flag =
                    messageRemoteService.sendMobileVerifyCode(orderTrailInfo.getMobileVerifyCode(),
                            session, MessageTypeEnum.ORDERTRAIL.getStatus());
            AjaxUtil.writeJSONToResponse(flag);
        } catch (Exception e) {
            logger.error("登录密码修改出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return NONE;
    }

    /**
     * 校验绑定的手机号码是否已被使用 add by songmiao 2013-11-11
     * 
     * @return
     * @throws Exception
     */
    public String numberIsUsed() {
        String resultMessage;// 传回页面的信息
        try {
            /**
             * //判断手机号码是否占用分为两种情况 1账号为手机号码的 2.绑定的手机是否被占用
             */
            boolean isUsed =
                    securityCentreService.mobileNumberIsUsed(verifyMobileInfo.getMobileNumber());
            if (isUsed) {
                resultMessage = "0";// 手机号码已被绑定
            } else {
                resultMessage = "1";// 没有注册
            }
            getResponse().getWriter().print(resultMessage);
        } catch (IOException e) {
            logger.error("登录出现IO异常：" + e.getMessage(), e);
            return ERROR;
        } catch (Exception e) {
            logger.error("出现异常：" + e.getMessage(), e);
            return ERROR;
        }

        return null;
    }

    /**
     * 校验修改支付密码中的新输入支付密码是否和登录密码相同 add by songmiao 2013-11-15
     * 
     * @return
     */
    public String ifSameWithLoginPW() {
        try {
            String resultMessage = "";// 返回页面的信息
            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
            user = securityCentreService.getUserByLoginId(userId);
            String loginPassWord = user.getLoginPassword();
            passWord = MD5.getMD5Str(passWord.trim());

            UserBaseInfo userBaseInfo = new UserBaseInfo();
            userBaseInfo.setLoginId(user.getLoginId());
            userBaseInfo.setPassword(passWord);
            userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "login");
            // SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);
            if (null == userBaseInfo) {
                resultMessage = "1";
            } else {

                if (userBaseInfo.getPassword().equals(loginPassWord)) {
                    resultMessage = "0";
                } else {
                    resultMessage = "1";
                }
            }

            try {
                getResponse().getWriter().print(resultMessage);
            } catch (IOException e) {
                logger.error("登录出现IO异常：" + e.getMessage(), e);
                return ERROR;
            } catch (Exception e) {
                logger.error("出现异常：" + e.getMessage(), e);
                return ERROR;
            }

        } catch (Exception e) {
            logger.error("校验支付密码异常", e);
        }
        return null;
    }

    /**
     * 判断新修改的登录密码是否和支付密码相同 add by songmiao 2013-11-15
     * 
     * @return
     */
    public String ifSameWithPayPW() {
        try {
            String resultMessage;// 返回页面的信息
            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
            user = securityCentreService.getUserByLoginId(userId);
            AccountInfo accountInfo = securityCentreService.getAccountInfo(user);
            String payPassWord = accountInfo.getPaymentpwd();
            passWord = MD5.getMD5Str(passWord);

            UserBaseInfo userBaseInfo = new UserBaseInfo();
            userBaseInfo.setLoginId(user.getLoginId());
            userBaseInfo.setPassword(passWord);
            userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo, "pay");
            // SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userBaseInfo);
            if (null == userBaseInfo) {
                resultMessage = "0";
            } else {
                if (userBaseInfo.getPassword().equals(payPassWord)) {
                    resultMessage = "0";
                } else {
                    resultMessage = "1";
                }
            }
            getResponse().getWriter().print(resultMessage);
        } catch (Exception e) {
            logger.error("出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    public SecurityCentreInfo getSecurityCentreInfo() {
        return securityCentreInfo;
    }

    public void setSecurityCentreInfo(SecurityCentreInfo securityCentreInfo) {
        this.securityCentreInfo = securityCentreInfo;
    }

    public ChangePasswordInfo getChangePasswordInfo() {
        return changePasswordInfo;
    }

    public void setChangePasswordInfo(ChangePasswordInfo changePasswordInfo) {
        this.changePasswordInfo = changePasswordInfo;
    }

    public VerifyMobileInfo getVerifyMobileInfo() {
        return verifyMobileInfo;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public User getUser() {
        return user;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getEmiaurl() {
        return emiaurl;
    }

    public void setEmiaurl(String emiaurl) {
        this.emiaurl = emiaurl;
    }

    public String getUptype() {
        return uptype;
    }

    public void setUptype(String uptype) {
        this.uptype = uptype;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVeCode() {
        return veCode;
    }

    public void setVeCode(String veCode) {
        this.veCode = veCode;
    }

    public RegistService getRegistservice() {
        return registservice;
    }

    public void setRegistservice(RegistService registservice) {
        this.registservice = registservice;
    }

    public ChangePayPasswordInfo getChangePayPasswordInfo() {
        return changePayPasswordInfo;
    }

    public void setChangePayPasswordInfo(ChangePayPasswordInfo changePayPasswordInfo) {
        this.changePayPasswordInfo = changePayPasswordInfo;
    }

    public SecurityCentreService getSecurityCentreService() {
        return securityCentreService;
    }

    public void setSecurityCentreService(SecurityCentreService securityCentreService) {
        this.securityCentreService = securityCentreService;
    }

    public String getSessionVarName() {
        return sessionVarName;
    }

    public void setSessionVarName(String sessionVarName) {
        this.sessionVarName = sessionVarName;
    }

    public MessageRemoteService getMessageRemoteService() {
        return messageRemoteService;
    }

    public void setMessageRemoteService(MessageRemoteService messageRemoteService) {
        this.messageRemoteService = messageRemoteService;
    }

    public OrderTrailInfo getOrderTrailInfo() {
        return orderTrailInfo;
    }

    public void setOrderTrailInfo(OrderTrailInfo orderTrailInfo) {
        this.orderTrailInfo = orderTrailInfo;
    }

    public String getPayRange() {
        return payRange;
    }

    public void setPayRange(String payRange) {
        this.payRange = payRange;
    }

    public String getOldMobileNumber() {
        return oldMobileNumber;
    }

    public void setOldMobileNumber(String oldMobileNumber) {
        this.oldMobileNumber = oldMobileNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getIsFile() {
        return isFile;
    }

    public void setIsFile(String isFile) {
        this.isFile = isFile;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public int getSafeLevel() {
        return safeLevel;
    }

    public void setSafeLevel(int safeLevel) {
        this.safeLevel = safeLevel;
    }

    public void setVerifyMobileInfo(VerifyMobileInfo verifyMobileInfo) {
        this.verifyMobileInfo = verifyMobileInfo;
    }
}
