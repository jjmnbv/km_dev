package com.kmzyc.b2b.third.action;

import java.io.IOException;
import java.util.HashMap;
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
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.User;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.api.request.UserBuyerGetRequest;
import com.taobao.api.response.UserBuyerGetResponse;

import net.sf.json.JSONObject;

@Controller("taobaoAfterLoginRedirectAction")
@Scope("prototype")
public class TaobaoAfterLoginRedirectAction extends ThirdLoginAction {
    /**
     * 
     */
    private static final long serialVersionUID = 4148700178777025291L;

    /**
     * 
     */
    private static Logger log = LoggerFactory.getLogger(TaobaoAfterLoginRedirectAction.class);

    @Resource(name = "thirdAccountInfoService")
    private ThirdAccountInfoService thirdAcctInfoService;

    /**
     * 获取到code,按照Auth2.0的授权过程获得用户信息
     * 
     * @return
     * @throws IOException
     */
    public String taobaoAuthorization() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        String code = request.getParameter("code");
        log.info("TaobaoAfterLoginRedirectAction ==> get code=" + code);
        String redirectPath = ConfigurationUtil.getString("portalPath");
        try {
            if (null == code) {
                log.info("TaobaoAfterLoginRedirectAction 获取code参数为空!");
                String loginId = String
                        .valueOf(request.getSession().getAttribute(Constants.SESSION_USER_ID));
                // 是否是临时会员(有可能浏览器的回退导致进入到这里)
                String isTempMember = String.valueOf(
                        request.getSession().getAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER));

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

            String clientId = ConfigurationUtil.getString("taobao_qt_appkey");
            String secret = ConfigurationUtil.getString("taobao_qt_secret");
            String tokenUrl = ConfigurationUtil.getString("taobao_normal_tokenurl");
            String env = ConfigurationUtil.getString("taobao_normal_env");

            // 封装获取token的参数
            Map<String, String> param = new HashMap<String, String>();
            param.put("grant_type", "authorization_code");
            param.put("code", code);
            param.put("client_id", clientId);
            param.put("client_secret", secret);
            param.put("redirect_uri", ConfigurationUtil.getString("taobao_website_redirectURL"));
            param.put("view", request.getParameter("view"));
            param.put("state", request.getParameter("state"));

            // 获取token
            String responseJson = WebUtils.doPost(tokenUrl, param, 3000, 3000);
            JSONObject tokenJson = JSONObject.fromObject(responseJson);

            System.out.println("tokenJson==========" + tokenJson);

            String sessionKey = tokenJson.getString("access_token");

            String userNick = java.net.URLDecoder
                    .decode(tokenJson.get("taobao_user_nick").toString(), "utf-8");
            String userId = tokenJson.get("taobao_user_id").toString();
            log.info("accessToken=====>" + tokenJson.get("access_token") + "~~~~~~userNick===>"
                    + userNick + tokenJson.get("taobao_user_nick") + "~~~~~~userId"
                    + tokenJson.get("taobao_user_id"));

            TaobaoClient client = new DefaultTaobaoClient(env, clientId, secret);
            UserBuyerGetRequest req = new UserBuyerGetRequest();
            req.setFields("nick,avatar");
            UserBuyerGetResponse rep = client.execute(req, sessionKey);
            System.out.println("获取用户信息======" + rep.getBody());

            User user = rep.getUser();
            user.setUid(userId);
            user.setNick(userNick);

            System.out.println("=====" + user.getAvatar());

            request.getSession().setAttribute("thirdLoginType",
                    ThirdConstant.THIRD_ACCOUNT_TYPE_TAOBAO);
            request.getSession().setAttribute("nikeName", user.getNick());
            request.getSession().setAttribute("userImg", null);
            request.getSession().setAttribute("openId", user.getUid());

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
                resultMap = thirdAcctInfoService.commonBindWithNormalMember(user, "taobao", loginId,
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
                        this.bindTip = "Y_taobao"; // 提示用户该第三方账号已经绑定其它会员
                        return "toBindManage";
                    }
                    // 绑定成功去到指定的页面 去到绑定管理页面
                    return "toBindManage";
                }
            }
            int cookie = cookieUserSource();
            // 第三方普通登录业务逻辑,具体注释请看service层
            resultMap = thirdAcctInfoService.commonBizAbountThirdLogin(user, "taobao", null,
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
        } catch (IOException e) {
            log.info("TaobaoAfterLoginRedirectAction==>" + e.getMessage(), e);
            // 刷新所报错
            if (e.getMessage().contains("invalid_client")) {
                return "toFillInfo";
            }
            return "error";
        } catch (Exception e) {
            log.info("TaobaoAfterLoginRedirectAction==>" + e.getMessage(), e);
            return "error";
        }
        return "toFillInfo";

    }

    /**
     * 获取到code,按照Auth2.0的授权过程获得用户信息 20150330 maliqun add 淘宝登录wap登录以及绑定管理需求
     * 
     * @return
     * @throws IOException
     */
    public String taobaoAuthorizationForWap() throws IOException {
        System.out.println("taobaoAuthorizationForWap-----begin");
        log.info("taobaoAuthorizationForWap 淘宝登录请求进入到taobaoAuthorizationForWap()方法begin");
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        String code = request.getParameter("code");
        log.info("taobaoAuthorizationForWap ==> get code=" + code);
        String redirectPath = ConfigurationUtil.getString("portalPath_WAP");
        try {
            if (null == code) {
                log.info("taobaoAuthorizationForWap 获取code参数为空!");
                String loginId = String
                        .valueOf(request.getSession().getAttribute(Constants.SESSION_USER_ID));
                // 是否是临时会员(有可能浏览器的回退导致进入到这里)
                String isTempMember = String.valueOf(
                        request.getSession().getAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER));

                // 此属情况1
                if ("null".equals(loginId)) {
                    log.info("taobaoAuthorizationForWap ===> 用户尚未登录!loginId=" + loginId);
                    redirectPath = redirectPath + "/html/wapLogin.jsp";
                    response.sendRedirect(redirectPath);
                    return null;
                    // 此属情况2
                } else if (!"null".equals(loginId)
                        && ("null".equals(isTempMember) || "N".equals(isTempMember))) {
                    return "toWapBindManage";
                } else {
                    // 此属情况3 临时会员通过其它途径:如浏览器的后退键到达此处是,让其去到首页
                    response.sendRedirect(redirectPath);
                    return null;
                }
            }

            String clientId = ConfigurationUtil.getString("taobao_qt_appkey");
            String secret = ConfigurationUtil.getString("taobao_qt_secret");
            String tokenUrl = ConfigurationUtil.getString("taobao_normal_tokenurl");
            String env = ConfigurationUtil.getString("taobao_normal_env");

            // 封装获取token的参数
            Map<String, String> param = new HashMap<String, String>();
            param.put("grant_type", "authorization_code");
            param.put("code", code);
            param.put("client_id", clientId);
            param.put("client_secret", secret);
            param.put("redirect_uri", ConfigurationUtil.getString("taobao_website_redirectURL"));
            param.put("view", request.getParameter("view"));
            param.put("state", request.getParameter("state"));

            // 获取token
            String responseJson = WebUtils.doPost(tokenUrl, param, 3000, 3000);
            JSONObject tokenJson = JSONObject.fromObject(responseJson);

            log.info("taobaoAuthorizationForWap获取token所返回的json字符串==>jsonData=" + tokenJson);

            System.out.println("tokenJson==========" + tokenJson);

            String sessionKey = tokenJson.getString("access_token");

            String userNick = java.net.URLDecoder
                    .decode(tokenJson.get("taobao_user_nick").toString(), "utf-8");
            String userId = tokenJson.get("taobao_user_id").toString();

            log.info("taobaoAuthorizationForWap获取token所返回的个具体参数值 accessToken="
                    + tokenJson.get("access_token") + "~~~~~~userNick=" + userNick
                    + tokenJson.get("taobao_user_nick") + "~~~~~~userId="
                    + tokenJson.get("taobao_user_id"));

            // taobao自己api的封装请求
            TaobaoClient client = new DefaultTaobaoClient(env, clientId, secret);
            UserBuyerGetRequest req = new UserBuyerGetRequest();
            req.setFields("nick,avatar");
            UserBuyerGetResponse rep = client.execute(req, sessionKey);
            System.out.println("获取用户信息======" + rep.getBody());

            log.info("taobaoAuthorizationForWap请求用户头像和昵称,返回结果resultBody=" + rep.getBody());

            User user = rep.getUser();
            user.setUid(userId);
            user.setNick(userNick);

            System.out.println("=====" + user.getAvatar());

            request.getSession().setAttribute("thirdLoginType",
                    ThirdConstant.THIRD_ACCOUNT_TYPE_TAOBAO);
            request.getSession().setAttribute("nikeName", user.getNick());
            request.getSession().setAttribute("userImg", user.getAvatar());
            request.getSession().setAttribute("openId", user.getUid());

            // 执行和康美会员绑定关系的返回结果map
            Map<String, Object> resultMap = null;

            // 是否是从绑定管理中心过来的主动绑定
            String isComeFromBindManage = request.getParameter(ThirdConstant.IS_COMEFROMBINDMANAGE);
            log.info("taobaoAuthorizationForWap 是否是从绑定管理中心过来,comeFromBindManage======="
                    + isComeFromBindManage);
            // 如果是从正式会员那块过来的主动绑定操作
            if ("Y".equals(isComeFromBindManage)) {
                log.info("taobaoAuthorizationForWap 正式会员主动绑定第三方账号 begin---");
                System.out.println(
                        "1.是通过康美中药城方式登录进来.... 2.通过第三方登录账号绑定的正式用户登录进来........ 3.第三方账号登录进来的临时会员成为正式会员后所作的操作...");

                String loginId = String
                        .valueOf(request.getSession().getAttribute(Constants.SESSION_USER_ID));
                // 和正式会员绑定的公共方法,具体注释请见service层
                resultMap = thirdAcctInfoService.commonBindWithNormalMember(user, "taobao", loginId,
                        null);

                if (null != (String) resultMap.get("errorMsg")
                        && !"".equals(resultMap.get("errorMsg"))) {
                    request.setAttribute("errorMsg", resultMap.get("errorMsg"));
                    log.info("taobaoAuthorizationForWap ---> 和正式会员绑定发生异常;"
                            + resultMap.get("errorMsg"));
                    return "error";
                } else {
                    // 该第三方账号已和其它康美会员绑定
                    if (resultMap.containsKey("alreadyBind")) {
                        log.info("taobaoAuthorizationForWap 正式绑定返回结果  resultMsg:该第三方账号已和其它康美账号绑定!");
                        this.bindTip = "Y_taobao"; // 提示用户该第三方账号已经绑定其它会员
                    }
                    // 绑定成功去到指定的页面 去到绑定管理页面
                    return "toWapBindManage";
                }
            }
            int cookie = cookieUserSource();
            // 第三方普通登录业务逻辑,具体注释请看service层
            resultMap = thirdAcctInfoService.commonBizAbountThirdLogin(user, "taobao", null,
                    Constants.REGISTER_DEVICE_WAP, cookie);
            if (null != (String) resultMap.get("errorMsg")
                    && !"".equals(resultMap.get("errorMsg"))) {
                request.setAttribute("errorMsg", resultMap.get("errorMsg"));
                log.error("taobaoAuthorizationForWap 绑定需要完善信息的会员发生异常,具体错误信息errorMsg:"
                        + resultMap.get("errorMsg"));
                return "error";
            }

            // 将完善信息所需要相关信息存入session,并非登录信息
            com.kmzyc.b2b.model.User loginUser =
                    (com.kmzyc.b2b.model.User) resultMap.get("loginUser");
            log.info("taobaoAuthorizationForWap 第三方账号所对应的康美登录账号loginUser...."
                    + loginUser.toString());


            // 提示其完善信息
            if ("toFillInfo".equals(resultMap.get("pageResult"))) {
                // 是需要完善信息的正式会员,需要完善信息后才可以操作某些菜单,后面前端控制是否能进行一些正式会员操作的判断依据,在完善信息后该值会从session中移除
                ServletActionContext.getRequest().getSession()
                        .setAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER, "Y");

                // 本地测试代码begin
                // return "toWapFillInfo";
                // 本地测试代码end
            }

            // 将第三方账户所对应的康美会员的信息写入到session
            super.putLoginInfoToSession(loginUser, request.getParameter("clientIp"));

            // 回到上一级操作页面
            String backUrl = request.getParameter("returnUrl");
            String paraStr = request.getParameter("state"); // returnUrl的参数信息
            System.out.println("encode 之后的stateparaStr=" + request.getParameter("state"));
            System.out.println("backUrl==" + backUrl);
            log.info("TaobaoAfterLoginRedirectAction 的taobaoAuthorizationForWap方法获取到回到上一级操作页面的具体值是="
                    + backUrl + ",参数列表=" + paraStr);
            if (backUrl != null && !backUrl.isEmpty() && !"null".equals(backUrl)) {
                redirectPath = backUrl;
                // 如果有带参数
                if (paraStr != null && !backUrl.isEmpty() && !"null".equals(paraStr)
                        && !"6818".equals(paraStr)) {
                    redirectPath = backUrl + "?" + paraStr;
                }
            }

            // 如果该第三方账号所对应的康美会员已是完善信息的,则让其去到首页
            response.sendRedirect(redirectPath);
        } catch (IOException e) {
            log.info("taobaoAuthorizationForWap发生异常==>" + e.getMessage(), e);

            // 如果是刷新所报错
            if (e.getMessage().contains("invalid_client")) {
                // request.setAttribute("errorMsg", "您的授权已经过期,请您重新授权!");
                response.sendRedirect(
                        ConfigurationUtil.getString("portalPath_WAP") + "/goWapMyHome.action");
                return null;
            }
            return "error";
        } catch (Exception e) {
            log.info("TaobaoAfterLoginRedirectAction==>" + e.getMessage(), e);
            request.setAttribute("errorMsg", e.getMessage());
            return "error";
        }
        return null;

    }

}
