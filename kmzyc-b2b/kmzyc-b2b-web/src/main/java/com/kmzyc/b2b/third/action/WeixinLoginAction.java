package com.kmzyc.b2b.third.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.third.model.wechat.UserInfo;
import com.kmzyc.b2b.third.service.ThirdAccountInfoService;
import com.kmzyc.b2b.third.util.BaseUtil;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.b2b.third.util.WxHttpUtil;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

import net.sf.json.JSONObject;

/**
 * 微信授权后返回到指定的return_url的相关逻辑操作
 *
 * @author Administrator
 */
@Controller("wxLoginAction")
@Scope("prototype")
public class WeixinLoginAction extends ThirdLoginAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(WeixinLoginAction.class);

    @Resource(name = "thirdAccountInfoService")
    private ThirdAccountInfoService thirdAcctInfoService;

    private String No;

    /**
     * 检查用户
     */
    public String wxLogin() throws IOException {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        // 用户授权获得的code
        String code = request.getParameter("code");

        // 获取state参数,此参数值为自定义的,可用来标识微信登录来源
        String state = request.getParameter("state");

        // 暂时写死重定向的地址为wap的首页
        String redirectPath = "http://m.kmb2b.com";

        String targetPageNo = request.getParameter("targetPageNo");
        this.No = targetPageNo;
        log.info("targetPage=" + targetPageNo + "获取微信返回回来的code=" + code);


        // 未获取到code
        if (null == code) {
            log.error("和微信链接异常,未获取到code或者说用户已取消授权!");
            return "error";
        }

        // 根据code获取授权的access_token
        JSONObject jsonObject = BaseUtil.getAccessTokenByCode(code);
        String openId = jsonObject.getString("openid");

        // 以后整合到定时处理机制里面去
        @SuppressWarnings("unused")
        String token = BaseUtil.getToken();
        UserInfo userInfo = BaseUtil.getUserInfo(token, openId);

        System.out.println("nickName=============" + userInfo.getNickname());
        // 将登录类型放入session 以便注册时绑定
        request.getSession().setAttribute("thirdLoginType", ThirdConstant.THIRD_ACCOUNT_TYPE_WX);
        request.getSession().setAttribute("nikeName", userInfo.getNickname());
        request.getSession().setAttribute("userImg", userInfo.getHeadimgurl());
        request.getSession().setAttribute("openId", userInfo.getOpenid());

        request.getSession().setAttribute("unionid",
                StringUtil.isEmpty(userInfo.getUnionid()) ? "" : userInfo.getUnionid());

        try {
            int cookie = cookieUserSource();
            // 普通登录业务逻辑,具体注释请看service层
            Map<String, Object> resultMap = thirdAcctInfoService.commonBizAbountThirdLogin(userInfo,
                    "wx", null, Constants.REGISTER_DEVICE_PC, cookie);
            if (null != resultMap.get("errorMsg") && !"".equals(resultMap.get("errorMsg"))) {
                request.setAttribute("errorMsg", resultMap.get("errorMsg"));
                log.info("errorMsg:" + resultMap.get("errorMsg"));
                return "error";
            }

            // 将完善信息所需要相关信息存入session,并非登录信息
            com.kmzyc.b2b.model.User loginUser =
                    (com.kmzyc.b2b.model.User) resultMap.get("loginUser");
            System.out.println("loginUser...." + loginUser.toString());
            // 将第三方账户所对应的康美会员的信息写入到session
            super.putLoginInfoToSession(loginUser, null);

            // maliqun add 20150202 用于wap登录 去到wap首页
            if (state != null && "authorizeForWap".equals(state)) {
                // return "wpIndex"; //也可直接重定向到wap首页
                response.sendRedirect(redirectPath);
                return null;
            }

            // 执行真正的url (此为之前做微信支付时先登录,貌似现在这个功能已经没用了)
            if ("nextUrl".equals(resultMap.get("nextUrl"))) {
                // 去到商品展示页面
                return "wxIndex";
            }
        } catch (Exception e) {
            request.setAttribute("errorMsg", "Exception has occurred." + e.getMessage());
            log.error("Exception:" + e.getMessage(), e);
            return "error";
        }
        return null;
    }

    /**
     * 微信wap登录或者自主绑定
     */
    public String wxAfterLoginForWap() throws IOException {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        // 用户授权获得的code
        String code = request.getParameter("code");

        // 获取state参数,此参数值为自定义的,可用来标识微信wap登录来源
        String state = request.getParameter("state");

        // 暂时写死重定向的地址为wap的首页
        // String redirectPath="http://m.kmb2b.com";
        String redirectPath = ConfigurationUtil.getString("portalPath_WAP");

        // 需要返回的上一级操作页面
        String returnUrl = request.getParameter("returnUrl");
        // 上一级页面所需要传递的参数
        String paraStrForReturnUrl = request.getParameter("paraStrForReturnUrl");

        log.info("wxAfterLoginForWap() code=" + code + ",state=" + state + ",returnUrl= "
                + returnUrl + ",paraStrForReturnUrl=" + paraStrForReturnUrl);

        // 用户取消授权未获取到code
        if (code == null || code.isEmpty()) {
            log.info("wxAfterLoginForWap==> 用户取消授权,code为空!");

            String loginId =
                    String.valueOf(request.getSession().getAttribute(Constants.SESSION_USER_ID));
            // 是否是临时会员(有可能浏览器的回退导致进入到这里)
            String isTempMember = String
                    .valueOf(request.getSession().getAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER));

            // 此属情况1
            if ("null".equals(loginId)) {
                // redirectPath=redirectPath+"/html/loginPage.htm"; pc端的登录页
                redirectPath = redirectPath + "/html/wapLogin.jsp"; // wap端的登录页
                response.sendRedirect(redirectPath);
                return null;
                // 此属情况2
            } else if (!"null".equals(loginId)
                    && ("null".equals(isTempMember) || "N".equals(isTempMember))) {
                // return "toBindManage";
                response.sendRedirect(redirectPath);
                return null;
            } else {
                // 此属情况3 临时会员通过其它途径:如浏览器的后退键到达此处是,让其去到首页
                response.sendRedirect(redirectPath);
                return null;
            }
        }

        log.info("获取微信返回回来的code=" + code);

        // 根据得来的code换取token
        JSONObject jsonObject = BaseUtil.getAccessTokenByCode(code);

        String token = jsonObject.getString("access_token");

        // String refresh_token=jsonObject.getString("refresh_token");
        String openId = jsonObject.getString("openid");

        // 以后整合到定时处理机制里面去
        // @SuppressWarnings("unused")
        // JSONObject jobj= BaseUtil.refreshToken(refresh_token);
        // String token=BaseUtil.getToken();
        // 获取用户基本信息,调用微信接口
        UserInfo userInfo = BaseUtil.getUserInfo(token, openId);

        // 将登录类型放入session 以便注册时绑定
        request.getSession().setAttribute("thirdLoginType", ThirdConstant.THIRD_ACCOUNT_TYPE_WX);
        request.getSession().setAttribute("nikeName", userInfo.getNickname());
        request.getSession().setAttribute("userImg", userInfo.getHeadimgurl());
        request.getSession().setAttribute("openId", userInfo.getOpenid());

        // 执行和康美会员绑定关系的返回结果map
        Map<String, Object> resultMap;

        // 是否是从绑定管理中心过来的主动绑定
        String isComeFromBindManage;
        isComeFromBindManage = request.getParameter(ThirdConstant.IS_COMEFROMBINDMANAGE);

        try {

            // 如果是从正式会员那块过来的主动绑定操作
            if ("Y".equals(isComeFromBindManage)) {
                System.out.println(
                        "1.是通过康美中药城方式登录进来.... 2.通过第三方登录账号绑定的正式用户登录进来........ 3.第三方账号登录进来的临时会员成为正式会员后所作的操作...");

                String loginId = String
                        .valueOf(request.getSession().getAttribute(Constants.SESSION_USER_ID));
                // 和正式会员绑定的公共方法,具体注释请见service层
                resultMap = thirdAcctInfoService.commonBindWithNormalMember(userInfo, "wx", loginId,
                        null);

                if (null != resultMap.get("errorMsg") && !"".equals(resultMap.get("errorMsg"))) {
                    request.setAttribute("errorMsg", resultMap.get("errorMsg"));
                    log.info("SinaAfterLoginRedirectAction ---> 和正式会员绑定发生异常;"
                            + resultMap.get("errorMsg"));
                    return "error";
                } else {
                    // 该第三方账号已和其它康美会员绑定
                    if (resultMap.containsKey("alreadyBind")) {
                        log.info("errorMsg:该第三方账号已和其它康美账号绑定!");
                        this.bindTip = "Y_wx"; // 提示用户该第三方账号已经绑定其它会员
                        return "toBindManage";
                    }
                    // 绑定成功去到指定的页面 去到绑定管理页面,并显示相应提示信息
                    return "toBindManage";
                }
            }
            int cookie = cookieUserSource();
            // 普通登录业务逻辑,具体注释请看service层
            resultMap = thirdAcctInfoService.commonBizAbountThirdLogin(userInfo, "wx", null,
                    Constants.REGISTER_DEVICE_WAP, cookie);
            // 返回错误码
            if (null != resultMap.get("errorMsg") && !"".equals(resultMap.get("errorMsg"))) {
                request.setAttribute("errorMsg", resultMap.get("errorMsg"));
                log.info(" wxAfterLoginForWap() ====>errorMsg:" + resultMap.get("errorMsg"));
                return "error";
            }

            // 将第三方账户所对应的康美会员的信息写入到session
            com.kmzyc.b2b.model.User loginUser =
                    (com.kmzyc.b2b.model.User) resultMap.get("loginUser");
            System.out.println("loginUser...." + loginUser.toString());

            // 提示其完善信息20150327 wap登录需求不再是已登录成功绑定的临时会员就跳入完善信息的页面,而是去到会员中心
            if ("toFillInfo".equals(resultMap.get("pageResult"))) {
                // 是需要完善信息的正式会员,需要完善信息后才可以操作某些菜单,凭此值做判断,若是已经完善信息后,该值将会从session移除
                ServletActionContext.getRequest().getSession()
                        .setAttribute(ThirdConstant.THIRD_ISTEMP_MEMBER, "Y");
                // return "toWapFillInfo";
            }

            log.info("第三方用户所对应的loginUser=" + loginUser.toString());
            super.putLoginInfoToSession(loginUser, null);
            log.info("==============微信登录成功~");

            // 回到上一级操作页面
            if (returnUrl != null && !returnUrl.isEmpty() && !"null".equals(returnUrl)) {

                paraStrForReturnUrl =
                        (StringUtil.isEmpty(paraStrForReturnUrl) ? "" : (paraStrForReturnUrl + "&"))
                                + "nikeName=" + URLEncoder.encode(userInfo.getNickname(), "utf-8")
                                + "&openId=" + userInfo.getOpenid() + "&userId="
                                + loginUser.getLoginId();

                /* 20150430 add begin 动态传参 */
                if (!paraStrForReturnUrl.isEmpty() && !"null".equals(paraStrForReturnUrl)) {
                    returnUrl = returnUrl + "?" + paraStrForReturnUrl;
                }
                /* 20150430 add end */

                /* 20150430注释掉 begin */
                // if (returnUrl.indexOf("?") > -1) {
                // returnUrl = returnUrl + "&productSkuID=" + request.getParameter("productSkuID") +
                // "&productVary="
                // + request.getParameter("productVary");
                // } /*20150430 注释掉 end*/
                response.sendRedirect(returnUrl);
                return null;
            } else {
                response.sendRedirect(redirectPath);
                return null;
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", "Exception has occurred." + e.getMessage());
            log.error("Exception:" + e.getMessage(), e);
            return "error";
        }
    }


    /**
     * 20150511 仅作测试,为了配合总部新项目对接,(20150619 现已正式启用)
     */
    public String wxAfterLoginForOther() throws IOException {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        // 用户授权获得的code
        String code = request.getParameter("code");

        // 获取state参数,此参数值为自定义的,可用来标识微信wap登录来源
        String state = request.getParameter("state");

        String isNoramal = request.getParameter("isNormal");

        String appId = ConfigurationUtil.getString("wx_appid");
        String appSecret = ConfigurationUtil.getString("wx_secret");


        // 一下是测试的appid以及app_secret
        if (isNoramal != null && "N".equals(isNoramal)) {
            appId = "wx509cef5ad61c9ece";
            appSecret = "676636e7a374b72ebd57c3f5fa962311";
        }

        // 需要返回的上一级操作页面
        String returnUrl = request.getParameter("returnUrl");
        // 上一级页面所需要传递的参数
        String paraStrForReturnUrl = request.getParameter("paraStrForReturnUrl");

        log.info("wxAfterLoginForOther() code=" + code + ",state=" + state + ",returnUrl= "
                + returnUrl + ",paraStrForReturnUrl=" + paraStrForReturnUrl);

        // 用户取消授权未获取到code
        if (code == null || code.isEmpty()) {
            log.info("wxAfterLoginForOther==> 用户取消授权,code为空!");
            PrintWriter out = response.getWriter();
            out.print("wxAfterLoginForOther==> code为空!appId=" + appId);
            out.close();
            return null;
        }

        log.info("获取微信返回回来的code=" + code);


        // 请求授权用的token
        String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId
                + "&secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code";
        JSONObject jsonObject = WxHttpUtil.httpRequest(tokenUrl, "GET", null);

        if (jsonObject.get("errcode") != null) {
            PrintWriter out = response.getWriter();
            out.print("使用接口" + tokenUrl + "\n用code=" + code
                    + "\n换取获取用户的access_token发生错误,具体详情为   appid=" + appId + "&secret=" + appSecret
                    + "的认证信息去获取code拿accessToken出现异常,返回详情=" + jsonObject);
            out.flush();
            out.close();
            return null;
        }

        String token = jsonObject.getString("access_token");

        // String refresh_token=jsonObject.getString("refresh_token");
        String openId = jsonObject.getString("openid");

        // 获取用户基本信息,调用微信接口
        UserInfo userInfo = BaseUtil.getUserInfo(token, openId);

        if (jsonObject.get("errcode") != null) {
            PrintWriter out = response.getWriter();
            out.print("使用接口" + tokenUrl + "\n用token换取获取用户信息发生错误\n token=" + token
                    + "\n具体详情为   appid=" + appId + "&secret=" + appSecret
                    + "的认证信息去获取code拿accessToken出现异常,返回详情=" + jsonObject);
            out.flush();
            out.close();
            return null;
        }
        request.setAttribute("userJsonData", JSONObject.fromObject(userInfo));
        request.setAttribute("state", state);

        // 20150629 add 配合总部要求,新增返回参数
        jsonObject.put("code", code); // jsonObject 使用code获取access_token返回的json数据,往里面添加一个code参数值
        request.setAttribute("postStr", jsonObject); // postStr参数名是总部那边的开发人员约定好的

        return "authorizeTest";
    }


    /**
     * 20150630 微信地址授权返回相关参数 (应总部相关需求) 暂未启用,但方法保留
     */
    public String wxAfterLoginForWxAddress() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");


        System.out.println("queryString=" + request.getQueryString());
        PrintWriter out = response.getWriter();

        String code = request.getParameter("code");
        String state = request.getParameter("state");

        if (code == null || code.isEmpty() || "".equals(code)) {
            out.print("code参数尚未获取到,用户取消授权,code=" + code);
            out.flush();
            out.close();
            return null;
        }

        JSONObject jsonData = new JSONObject();
        jsonData.put("code", code);
        jsonData.put("state", state);
        jsonData.put("queryString", request.getQueryString());
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String paramName = en.nextElement();
            String paramValue = request.getParameter(paramName);
            jsonData.put(paramName, paramValue);
        }

        request.setAttribute("postStr", jsonData);
        // 拼接表单提交参数http://weixin.kangmei.com.cn/wechat/redirect/index.html
        // StringBuilder sbHtml = new StringBuilder();
        // sbHtml.append("<form id=\"form\" name=\"form\" action=\"/third/test.action\" " +
        // " method=\"post\">");
        // sbHtml.append("<input type=\"hidden\" name=\"postStr\" value=\" "+jsonData.toString()
        // +" \"/>");
        // sbHtml.append("<input type=\"submit\" style=\"display:none;\"></form>");
        // sbHtml.append("<script>document.forms['form'].submit();</script>");
        // out.print(sbHtml);
        // out.flush();
        // out.close();
        return "success";
    }


    /**
     * 20150922 mlq add 微信组方需求,点击组方购买的时候需要微信授权 暂时只用做获取openId,并没有做openId和商城用户的关联
     */
    public String wxAfterLoginForWxScanProduct() throws IOException {
        // TODO: 1.此处为微信组方授权,此处业务逻辑和之前的稍有不同,所以新开一个方法

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        // 用户授权获得的code
        String code = request.getParameter("code");

        // 获取state参数,此参数值为自定义的,可用来标识微信wap登录来源
        String state = request.getParameter("state");

        // 暂时写死重定向的地址为wap的首页
        // String redirectPath="http://m.kmb2b.com";
        String redirectPath = ConfigurationUtil.getString("portalPath_WAP");

        // 需要返回的上一级操作页面
        String returnUrl = request.getParameter("returnUrl");
        // 上一级页面所需要传递的参数
        String paraStrForReturnUrl = request.getParameter("paraStrForReturnUrl");

        log.info("wxAfterLoginForWap() code=" + code + ",state=" + state + ",returnUrl= "
                + returnUrl + ",paraStrForReturnUrl=" + paraStrForReturnUrl);

        // 用户取消授权未获取到code
        if (code == null || code.isEmpty()) {
            log.info("wxAfterLoginForWxScanProduct==> 用户取消授权,code为空!");
            response.sendRedirect(redirectPath);
            return null;
        }

        log.info("获取微信返回回来的code=" + code);

        // 根据得来的code换取token
        JSONObject jsonObject = BaseUtil.getAccessTokenByCode(code);


        // 20151013 加入调试信息
        if (jsonObject == null || jsonObject.get("openid") == null) {
            response.getWriter().print("用code换取access_token为空,error1(jsonObj=null)="
                    + (jsonObject == null)
                    + (jsonObject == null
                            ? (",error2(getOpeind=null)=" + (jsonObject.get("openid") == null))
                            : null));
            return null;
        }

        // 获取到用户的openId
        String openId = jsonObject.getString("openid");

        // 将openId放入到session
        super.getRequest().getSession().setAttribute(Constants.SESSION_WX_OPENID, openId);

        // 回到上一级操作页面
        if (returnUrl != null && !returnUrl.isEmpty() && !"null".equals(returnUrl)) {

            if (paraStrForReturnUrl != null && !paraStrForReturnUrl.isEmpty()
                    && !"null".equals(paraStrForReturnUrl)) {
                returnUrl = returnUrl + "?" + paraStrForReturnUrl;
            }
            response.sendRedirect(returnUrl);
        } else {
            response.sendRedirect(redirectPath);
        }
        return null;
    }


    public String test() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        JSONObject returnJson = new JSONObject();
        System.out.println("postStr=" + request.getParameter("postStr"));

        returnJson.put("postStr", request.getParameter("postStr"));
        returnJson.put("userJsonData", request.getParameter("userJsonData"));
        out.print(returnJson);
        out.flush();
        out.close();
        return null;

    }


    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }


}
