package com.kmzyc.b2b.third.action;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.third.service.ThirdAccountInfoService;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

/**
 * QQ登录的请求处理类
 */
@Controller("QQAfterLoginRedirectAction")
@Scope("prototype")
public class QQAfterLoginRedirectAction extends ThirdLoginAction {
    /**
     * 
     */
    private static final long serialVersionUID = 4148700178777025291L;

    /**
     * 
     */
    // private static Logger log = LoggerFactory.getLogger(QQAfterLoginRedirectAction.class);
    private static Logger log = LoggerFactory.getLogger(QQAfterLoginRedirectAction.class);

    @Resource(name = "thirdAccountInfoService")
    private ThirdAccountInfoService thirdAcctInfoService;

    /**
     * pc端登录请求
     * 
     * @return
     */
    public String qqAfterLoginRedirect() {

        log.info("QQAfterLoginRedirectAction QQ登录请求进入到qqAfterLoginRedirect()方法begin");
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        String redirectPath = ConfigurationUtil.getString("portalPath");
        response.setContentType("text/html;charset=utf-8");
        String loginId =
                String.valueOf(request.getSession().getAttribute(Constants.SESSION_USER_ID));
        try {
            // 用code换取accessToken
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

            System.out.println("accessTokenObj" + accessTokenObj);
            log.info("qqAfterLoginRedirect获取accessTokenObj=" + accessTokenObj);

            String accessToken = null, openID = null;

            if (accessTokenObj.getAccessToken().equals("")) {
                log.info("qqAfterLoginRedirect()没有获取到响应参数accessToken");
                if (!"null".equals(loginId)) {
                    return "toFillInfo";
                }
                request.setAttribute("errorMsg", "没有获取到响应参数.");
                log.error("qqAfterLoginRedirect() 方法 accessToken获取异常");
                return "error";
            }
            accessToken = accessTokenObj.getAccessToken();
            // 利用获取到的accessToken 去获取当前用的openid
            OpenID openIDObj = new OpenID(accessToken);
            openID = openIDObj.getUserOpenID();

            // 利用获取到的accessToken,openid 去获取用户信息;
            UserInfo userInfo = new UserInfo(accessToken, openID);
            UserInfoBean userInfoBean = userInfo.getUserInfo();

            request.getSession().setAttribute("thirdLoginType",
                    ThirdConstant.THIRD_ACCOUNT_TYPE_QQ);
            request.getSession().setAttribute("nikeName", userInfoBean.getNickname());
            request.getSession().setAttribute("userImg", userInfoBean.getAvatar().getAvatarURL50());
            request.getSession().setAttribute("openId", openID);

            log.info("qqAfterLoginRedirect()存入session的值如下: thirdLoginType="
                    + ThirdConstant.THIRD_ACCOUNT_TYPE_QQ + ",nickName="
                    + userInfoBean.getNickname() + ",openId=" + openID);

            // 执行和康美会员绑定关系的返回结果map
            Map<String, Object> resultMap = null;

            // 是否是从绑定管理中心过来的主动绑定
            String isComeFromBindManage = request.getParameter(ThirdConstant.IS_COMEFROMBINDMANAGE);

            // 如果是从正式会员那块过来的主动绑定操作
            if ("Y".equals(isComeFromBindManage)) {
                log.info(
                        "1.是通过康美中药城方式登录进来.... 2.通过第三方登录账号绑定的正式用户登录进来........ 3.第三方账号登录进来的临时会员成为正式会员后所作的操作...");

                // 和正式会员绑定的公共方法,具体注释请见service层
                resultMap = thirdAcctInfoService.commonBindWithNormalMember(userInfoBean, "QQ",
                        loginId, openID);

                if (null != (String) resultMap.get("errorMsg")
                        && !"".equals(resultMap.get("errorMsg"))) {
                    request.setAttribute("errorMsg", resultMap.get("errorMsg"));
                    log.info("QQAfterLoginRedirectAction ---> 和正式会员绑定发生异常;openId=" + openID
                            + ",errorMsg=" + resultMap.get("errorMsg"));
                    return "error";
                } else {
                    // 该第三方账号已和其它康美会员绑定
                    if (resultMap.containsKey("alreadyBind")) {
                        log.info("errorMsg:该QQ第三方账号已和其它康美账号绑定!");
                        this.bindTip = "Y_QQ"; // 提示用户该第三方账号已经绑定其它会员
                        return "toBindManage";
                    }
                    // 绑定成功去到指定的页面 去到绑定管理页面
                    return "toBindManage";
                }
            }
            int cookie = cookieUserSource();
            // 第三方普通登录业务逻辑,具体注释请看service层
            resultMap = thirdAcctInfoService.commonBizAbountThirdLogin(userInfoBean, "QQ", openID,
                    Constants.REGISTER_DEVICE_PC, cookie);

            if (null != (String) resultMap.get("errorMsg")
                    && !"".equals(resultMap.get("errorMsg"))) {
                request.setAttribute("errorMsg", resultMap.get("errorMsg"));
                log.error("qq请求授权后处理对应账户信息发生异常,openId=" + openID + ",errorMsg:"
                        + resultMap.get("errorMsg"));
                return "error";
            }

            // 将完善信息所需要相关信息存入session,并非登录信息
            com.kmzyc.b2b.model.User loginUser =
                    (com.kmzyc.b2b.model.User) resultMap.get("loginUser");
            log.info("第三方QQ用户登录对应的康美loginUser...." + loginUser.toString());
            // 将第三方账户所对应的康美会员的信息写入到session
            super.putLoginInfoToSession(loginUser, request.getParameter("clientIp"));

            // 提示其完善信息
            if ("toFillInfo".equals(resultMap.get("pageResult"))) {
                // 是需要完善信息的正式会员,需要完善信息后才可以操作某些菜单
                ServletActionContext.getRequest().getSession()
                        .setAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER, "Y");
                // 将第三方账户所对应的康美会员的信息写入到session
                return (String) resultMap.get("pageResult");
            }

            log.info("QQAfterLoginRedirectAction QQ登录请求进入到qqAfterLoginRedirect()方法end");
            // 如果该第三方账号所对应的康美会员已是完善信息的,则让其去到首页
            response.sendRedirect(redirectPath);
            return null;
        } catch (QQConnectException e) {
            log.error("QQException:" + "与QQ链接错误" + e, e);

            // 说明是通过页面刷新过来的
            if (!"null".equals(loginId)) {
                return "toFillInfo";
            }
            // 和QQ链接发生错误
            request.setAttribute("errorMsg", "与QQ链接错误");
            return "error";

        } catch (Exception e) {
            log.error("Exception:", e);
            return "error";
        }
    }

    /**
     * wap端登录以及绑定请求
     * 
     * @return
     * @throws IOException
     */
    public String qqAfterLoginRedirectForWap() throws IOException {

        System.out.println("qqAfterLoginRedirectForWap-----begin");
        log.info("QQAfterLoginRedirectAction QQ登录请求进入到qqAfterLoginRedirectForWap()方法begin");
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        String redirectPath = ConfigurationUtil.getString("portalPath_WAP");
        response.setContentType("text/html;charset=utf-8");
        String loginId =
                String.valueOf(request.getSession().getAttribute(Constants.SESSION_USER_ID));
        try {
            // 用code换取accessToken
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

            System.out.println("accessTokenObj" + accessTokenObj);
            log.info("qqAfterLoginRedirectForWap获取accessTokenObj=" + accessTokenObj);

            String accessToken = null, openID = null;

            if (accessTokenObj.getAccessToken().equals("")) {
                log.info("qqAfterLoginRedirectForWap()没有获取到响应参数accessToken");
                if (!"null".equals(loginId)) {
                    // 去首页
                    response.sendRedirect(redirectPath);
                    return null;
                    // return "toFillInfo";
                }
                request.setAttribute("errorMsg", "没有获取到响应参数.");
                log.error("qqAfterLoginRedirectForWap() 方法 accessToken获取异常");
                return "error";
            }
            accessToken = accessTokenObj.getAccessToken();
            // 利用获取到的accessToken 去获取当前用的openid
            OpenID openIDObj = new OpenID(accessToken);
            openID = openIDObj.getUserOpenID();

            // 利用获取到的accessToken,openid 去获取用户信息;
            UserInfo userInfo = new UserInfo(accessToken, openID);
            UserInfoBean userInfoBean = userInfo.getUserInfo();

            request.getSession().setAttribute("thirdLoginType",
                    ThirdConstant.THIRD_ACCOUNT_TYPE_QQ);
            request.getSession().setAttribute("nikeName", userInfoBean.getNickname());
            request.getSession().setAttribute("userImg", userInfoBean.getAvatar().getAvatarURL50());
            request.getSession().setAttribute("openId", openID);

            log.info("qqAfterLoginRedirectForWap()存入session的值如下: thirdLoginType="
                    + ThirdConstant.THIRD_ACCOUNT_TYPE_QQ + ",nickName="
                    + userInfoBean.getNickname() + ",openId=" + openID);

            // 执行和康美会员绑定关系的返回结果map
            Map<String, Object> resultMap = null;

            // 是否是从绑定管理中心过来的主动绑定
            String isComeFromBindManage = request.getParameter(ThirdConstant.IS_COMEFROMBINDMANAGE);

            // 如果是从正式会员那块过来的主动绑定操作
            if ("Y".equals(isComeFromBindManage)) {
                log.info(
                        "1.是通过康美中药城方式登录进来.... 2.通过第三方登录账号绑定的正式用户登录进来........ 3.第三方账号登录进来的临时会员成为正式会员后所作的操作...");

                // 和正式会员绑定的公共方法,具体注释请见service层
                resultMap = thirdAcctInfoService.commonBindWithNormalMember(userInfoBean, "QQ",
                        loginId, openID);

                if (null != (String) resultMap.get("errorMsg")
                        && !"".equals(resultMap.get("errorMsg"))) {
                    request.setAttribute("errorMsg", resultMap.get("errorMsg"));
                    log.info("qqAfterLoginRedirectForWap ---> 和正式会员绑定发生异常;openId=" + openID
                            + ",errorMsg=" + resultMap.get("errorMsg"));
                    return "error";
                } else {
                    // 该第三方账号已和其它康美会员绑定
                    if (resultMap.containsKey("alreadyBind")) {
                        log.info("绑定提示:该QQ第三方账号已和其它康美账号绑定!");
                        this.bindTip = "Y_QQ"; // 提示用户该第三方账号已经绑定其它会员
                        return "toWapBindManage";
                    }
                    // 绑定成功去到指定的页面 去到绑定管理页面
                    return "toWapBindManage";
                }
            }
            int cookie = cookieUserSource();
            // 第三方普通登录业务逻辑,具体注释请看service层
            resultMap = thirdAcctInfoService.commonBizAbountThirdLogin(userInfoBean, "QQ", openID,
                    Constants.REGISTER_DEVICE_WAP, cookie);

            if (null != (String) resultMap.get("errorMsg")
                    && !"".equals(resultMap.get("errorMsg"))) {
                request.setAttribute("errorMsg", resultMap.get("errorMsg"));
                log.error("qq请求授权后处理对应账户信息发生异常,openId=" + openID + ",errorMsg:"
                        + resultMap.get("errorMsg"));
                return "error";
            }

            // 将完善信息所需要相关信息存入session,并非登录信息
            com.kmzyc.b2b.model.User loginUser =
                    (com.kmzyc.b2b.model.User) resultMap.get("loginUser");
            log.info("第三方QQ用户登录对应的康美loginUser...." + loginUser.toString());

            // 提示其完善信息20150327 wap登录需求不再是已登录成功绑定的临时会员就跳入完善信息的页面,而是去到会员中心
            if ("toFillInfo".equals(resultMap.get("pageResult"))) {
                // 是需要完善信息的正式会员,需要完善信息后才可以操作某些菜单,凭此值做判断,若是已经完善信息后,该值将会从session移除
                ServletActionContext.getRequest().getSession()
                        .setAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER, "Y");
                // return "toWapFillInfo";
            }

            // 将第三方账户所对应的康美会员的信息写入到session
            super.putLoginInfoToSession(loginUser, request.getParameter("clientIp"));

            log.info("QQAfterLoginRedirectAction QQ登录请求进入到qqAfterLoginRedirectForWap()方法end");

            // 回到上一级操作页面
            String backUrl = request.getParameter("returnUrl");
            log.info("QQAfterLoginRedirectAction 的qqAfterLoginRedirectForWap方法获取到回到上一级操作页面的具体值是="
                    + backUrl);
            if (backUrl != null && !backUrl.isEmpty() && !"null".equals(backUrl)) {
                redirectPath = backUrl;

                // 如果有带参数
                String paraStr = request.getParameter("paraStr");
                log.info(
                        "QQAfterLoginRedirectAction 的qqAfterLoginRedirectForWap方法获取到回到上一级操作页面的所带参数的具体值是="
                                + paraStr);
                if (paraStr != null && !backUrl.isEmpty() && !"null".equals(paraStr)) {
                    redirectPath = backUrl + "?" + paraStr;
                }
            }
            // 如果该第三方账号所对应的康美会员已是完善信息的,则让其去到首页
            response.sendRedirect(redirectPath);
            return null;

        } catch (QQConnectException e) {
            log.error("QQException:" + "与QQ链接错误" + e, e);

            // 说明是通过页面刷新过来的
            if (!"null".equals(loginId)) {
                response.sendRedirect(
                        ConfigurationUtil.getString("portalPath_WAP") + "/goWapMyHome.action");
                return null;
            }
            // 和QQ链接发生错误
            request.setAttribute("errorMsg", "与QQ链接错误");
            return "error";

        } catch (Exception e) {
            request.setAttribute("errorMsg", "与QQ链接错误,具体错误信息为=" + e.getMessage());
            log.error("Exception:" + e, e);
            return "error";
        }
    }

}
