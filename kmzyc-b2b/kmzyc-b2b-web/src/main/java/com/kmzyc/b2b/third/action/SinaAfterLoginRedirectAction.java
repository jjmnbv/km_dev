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
import com.kmzyc.b2b.weibo.Oauth;
import com.kmzyc.b2b.weibo.Users;
import com.kmzyc.b2b.weibo.http.AccessToken;
import com.kmzyc.b2b.weibo.model.User;
import com.kmzyc.b2b.weibo.model.WeiboException;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 新浪登录时的请求处理类
 * 
 * @author Administrator
 * 
 */
@Controller("sinaAfterLoginRedirectAction")
@Scope("prototype")
public class SinaAfterLoginRedirectAction extends ThirdLoginAction {

    /**
     * 
     */
    private static final long serialVersionUID = -7269431379482621953L;

    @Resource(name = "thirdAccountInfoService")
    private ThirdAccountInfoService thirdAcctInfoService;

    private static Logger log = LoggerFactory.getLogger(SinaAfterLoginRedirectAction.class);

    public String sinaAfterLoginRedirect() throws IOException {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        response.setContentType("text/html;charset=utf-8");
        String code = request.getParameter("code");
        String redirectPath = ConfigurationUtil.getString("portalPath");
        // 当code为空时有三种情况 : 1.用户首次借助新浪账号登录,点击取消
        // 2.用户登录后从绑定管理中心处过来,点击主动绑定新浪账号时,后来点击取消 3.临时会员通过控制浏览器的后退键到达该页面 20140417
        if (null == code || "".equals(code)) {

            String loginId =
                    String.valueOf(request.getSession().getAttribute(Constants.SESSION_USER_ID));
            // 是否是临时会员(有可能浏览器的回退导致进入到这里)
            String isTempMember = String
                    .valueOf(request.getSession().getAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER));

            // 此属情况1
            if ("null".equals(loginId)) {
                redirectPath = redirectPath + "/html/loginPage.htm";
                response.sendRedirect(redirectPath);
                return null;
                // 此属情况2
            } else if (!"null".equals(loginId)
                    && ("null".equals(isTempMember) || "N".equals(isTempMember))) {
                return "toBindManage";
            } else {
                // 此属情况3 临时会员通过其它途径:如浏览器的后退键到达此处是,让其去到首页
                response.sendRedirect(redirectPath);
                return null;
            }
        }

        System.out.println("get code =" + code);
        Oauth oauth = new Oauth();

        try {
            AccessToken accessToken = oauth.getAccessTokenByCode(code);
            String uid = accessToken.getUid();
            Users users = new Users();
            users.client.setToken(accessToken.getAccessToken());
            User user = users.showUserById(uid);
            request.getSession().setAttribute("thirdLoginType",
                    ThirdConstant.THIRD_ACCOUNT_TYPE_SINA);
            request.getSession().setAttribute("nikeName", user.getName());
            request.getSession().setAttribute("userImg", user.getProfileImageUrl());
            request.getSession().setAttribute("openId", user.getId());

            // 执行和康美会员绑定关系的返回结果map
            Map<String, Object> resultMap = null;

            // 是否是从绑定管理中心过来的主动绑定
            String isComeFromBindManage = request.getParameter(ThirdConstant.IS_COMEFROMBINDMANAGE);
            log.info("comeFromBindManage=======" + isComeFromBindManage);

            // 如果是从正式会员那块过来的主动绑定操作
            if ("Y".equals(isComeFromBindManage)) {
                System.out.println(
                        "1.是通过康美中药城方式登录进来.... 2.通过第三方登录账号绑定的正式用户登录进来........ 3.第三方账号登录进来的临时会员成为正式会员后所作的操作...");

                String loginId = String
                        .valueOf(request.getSession().getAttribute(Constants.SESSION_USER_ID));
                // 和正式会员绑定的公共方法,具体注释请见service层
                resultMap = thirdAcctInfoService.commonBindWithNormalMember(user, "sina", loginId,
                        null);

                if (null != (String) resultMap.get("errorMsg")
                        && !"".equals(resultMap.get("errorMsg"))) {
                    request.setAttribute("errorMsg", resultMap.get("errorMsg"));
                    log.info("SinaAfterLoginRedirectAction ---> 和正式会员绑定发生异常;"
                            + resultMap.get("errorMsg"));
                    return "error";
                } else {
                    // 该第三方账号已和其它康美会员绑定
                    if (resultMap.containsKey("alreadyBind")) {
                        log.info("errorMsg:该第三方账号已和其它康美账号绑定!");
                        this.bindTip = "Y_sina"; // 提示用户该第三方账号已经绑定其它会员
                        return "toBindManage";
                    }
                    // 绑定成功去到指定的页面 去到绑定管理页面
                    return "toBindManage";
                }
            }
            int cookie = cookieUserSource();
            // 第三方普通登录业务逻辑,具体注释请看service层
            resultMap = thirdAcctInfoService.commonBizAbountThirdLogin(user, "sina", null,
                    Constants.REGISTER_DEVICE_PC, cookie);
            if (null != (String) resultMap.get("errorMsg")
                    && !"".equals(resultMap.get("errorMsg"))) {
                request.setAttribute("errorMsg", resultMap.get("errorMsg"));
                log.error("errorMsg:" + resultMap.get("errorMsg"));
                return "error";
            }

            // 将完善信息所需要相关信息存入session,并非登录信息
            com.kmzyc.b2b.model.User loginUser =
                    (com.kmzyc.b2b.model.User) resultMap.get("loginUser");
            log.info("loginUser...." + loginUser.toString());
            // 将第三方账户所对应的康美会员的信息写入到session
            super.putLoginInfoToSession(loginUser, request.getParameter("clientIp"));

            // 提示其完善信息
            if ("toFillInfo".equals(resultMap.get("pageResult"))) {
                // 是需要完善信息的正式会员,需要完善信息后才可以操作某些菜单
                ServletActionContext.getRequest().getSession()
                        .setAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER, "Y");
                return (String) resultMap.get("pageResult");
            }
            // 如果该第三方账号所对应的康美会员已是完善信息的,则让其去到首页
            response.sendRedirect(redirectPath);

        } catch (WeiboException e) {
            log.error("WeiboException:" + "获取微博信息错误" + e.getMessage(), e);
            String loginId =
                    String.valueOf(request.getSession().getAttribute(Constants.SESSION_USER_ID));
            // 说明是通过页面刷新过来的
            if (!"null".equals(loginId)) {
                return "toFillInfo";
            }
            // 和QQ链接发生错误
            request.setAttribute("errorMsg", "获取微博信息错误(康美中药城的上线应用还在审核当中!)" + e);
            return "error";
        } catch (Exception e) {
            log.error("Exception has occurred:" + e.getMessage(), e);
            return "error";
        }
        return null;
    }
}
