package com.kmzyc.b2b.third.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.b2b.third.model.wechat.UserInfo;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.b2b.third.util.WxHttpUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.utils.RandomStatusGenerator;

import net.sf.json.JSONObject;

public class BaseUtil {
    private static final Logger log = LoggerFactory.getLogger(BaseUtil.class);

    static public String getToken() {
        String tokenUrl =
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                        + ConfigurationUtil.getString("wx_appid") + "&secret="
                        + ConfigurationUtil.getString("wx_secret").trim();
        JSONObject jsonObject = WxHttpUtil.httpRequest(tokenUrl, "GET", null);
        return jsonObject.getString("access_token");
    }

    // 通过code换取网页授权access_token
    static public JSONObject getAccessTokenByCode(String code) {
        String tokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                        + ConfigurationUtil.getString("wx_appid") + "&secret="
                        + ConfigurationUtil.getString("wx_secret") + "&code=" + code
                        + "&grant_type=authorization_code";
        JSONObject jsonObject = WxHttpUtil.httpRequest(tokenUrl, "GET", null);
        System.err.println("access_token-------------json------------------" + jsonObject);
        return jsonObject;
    }

    // 刷新access_token
    static public JSONObject refreshToken(String refreshTokenString) {
        String refreshTokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="
                        + ConfigurationUtil.getString("wx_appid")
                        + "&grant_type=refresh_token&refresh_token=" + refreshTokenString;
        JSONObject jsonObject = WxHttpUtil.httpRequest(refreshTokenUrl, "GET", null);
        System.err.println("refreshToken-------------json------------------" + jsonObject);
        return jsonObject;
    }

    /**
     * https://api.weixin.qq.com/sns/userinfo?access_token= OezXcEiiBSKSxW0eoylIeDxHGV-
     * FtGVaPqX3Wvv2jRjoUC1PdZxem7c0rj86o4rSZhpkzvHnKj8WqcMLfLdUsvWmHqA4uSHf4otiAmF06GJiyK7l9rZZUQt4XcHLCMgo_HBkpW9gbX5JjrkDi4wc9g
     * &openid=oFXg-ty3CtphZ0o7Fq4KYtHlpGrs&lang=zh_CN
     * 
     * @param accessToken
     * @param openId
     * @return
     */
    static public UserInfo getUserInfo(String accessToken, String openId) {
        String getUserUrl =
                "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid="
                        + openId + "&lang=zh_CN";
        JSONObject json = WxHttpUtil.httpRequest(getUserUrl, "GET", null);
        System.err.println("UserInfo-------snsapi_userinfo------json------------------" + json);
        return jsonObjectToBean(json);
    }

    static public UserInfo getUserInfo(String accessToken, String openId, String language) {
        String getUserUrl =
                "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken
                        + "&openid=" + openId + "&lang=" + language;
        JSONObject json = WxHttpUtil.httpRequest(getUserUrl, "GET", null);
        System.err.println("UserInfo-------snsapi_userinfo------json------------------" + json);
        return jsonObjectToBean(json);
    }

    static public UserInfo jsonObjectToBean(JSONObject json) {
        UserInfo userInfo;
        userInfo = (UserInfo) JSONObject.toBean(json, UserInfo.class);
        return userInfo;
    }


    /**
     * 20150708 add 新增获取jsApiTicket的方法
     * 
     * @param accessToken 此token为全局普通的token
     * @return
     */
    static public JSONObject getJsApiTicket(String accessToken) {
        String url =
                "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken
                        + "&type=jsapi";
        JSONObject jsonObj = WxHttpUtil.httpRequest(url, "GET", null);
        return jsonObj;
    }

    /**
     * 
     * 生成qq登录授权获取code的url
     * 
     * @param request
     * @param isComeFromBindManage 是否是从绑定管理处过来 Y表示是,其它值表示不是
     * @param clientIp 客户端的IP值
     * @param isWap 是否来源wap Y值为是 20150327 接入wap登录
     * @param backUrl 返回上一级操作页面,wap需求
     * @return
     * @throws QQConnectException
     */
    public static String getAuthorizeURLForQQ(ServletRequest request, String isComeFromBindManage,
            String clientIp, String isWap, String backUrl) throws QQConnectException {
        String state = RandomStatusGenerator.getUniqueState();
        ((HttpServletRequest) request).getSession().setAttribute("qq_connect_state", state);
        String scope = ConfigurationUtil.getString("QQ_scope");

        String redirect_uri = ConfigurationUtil.getString("QQ_redirect_URI").trim();

        if (isWap != null && "Y".equals(isWap.toUpperCase())) {
            redirect_uri = ConfigurationUtil.getString("QQ_redirect_URI_WAP").trim();
        }

        // 返回上一级操作页面的url是否有带参数
        String paraStr = request.getParameter("paraStr");

        System.out.println("QQ所需要传入的参数=" + request.getParameter("paraStr"));
        log.info("BaseUtil  ===>QQ所需要传入的参数=" + request.getParameter("paraStr"));
        if (paraStr != null && !paraStr.isEmpty()) {
            paraStr = URLEncodeUtils.encodeURL(paraStr);
        }
        String returnstr =
                redirect_uri + "?" + ThirdConstant.IS_COMEFROMBINDMANAGE + "="
                        + isComeFromBindManage + "&clientIp=" + clientIp + "&isWap=" + isWap
                        + "&returnUrl=" + backUrl + "&paraStr=" + paraStr;
        String returnUrl = URLEncodeUtils.encodeURL(returnstr);

        System.out.println("return Url------------" + returnUrl);
        log.info("BaseUtil  ===>qq登录回调地址请求=" + returnUrl);

        if (scope != null && !scope.equals(""))
            return (new StringBuilder())
                    .append(ConfigurationUtil.getString("QQ_authorizeURL").trim())
                    .append("?client_id=").append(ConfigurationUtil.getString("QQ_app_ID").trim())
                    .append("&redirect_uri=").append(returnUrl).append("&response_type=")
                    .append("code").append("&state=").append(state).append("&scope=").append(scope)
                    .toString();
        else {
            return (new StringBuilder())
                    .append(ConfigurationUtil.getString("QQ_authorizeURL").trim())
                    .append("?client_id=").append(ConfigurationUtil.getString("QQ_app_ID").trim())
                    .append("&redirect_uri=").append(returnUrl).append("&response_type=")
                    .append("code").append("&state=").append(state).toString();
        }
    }

    /**
     * 生成taobao 用户请求code链接
     * 
     * @param response_type 请求类型为code
     * @param state 用户随机填,也可以用这个参数标识 维持应用的状态，传入值与返回值保持一致。
     * @param isComeFromBindManage 是否是从绑定管理过来
     * @param clientIp 客户端IP地址
     * @param isWap 是否来源于wap Y值为是 20150327 接入wap登录
     * @param backUrl 返回上一级操作页面,wap需求
     * @return
     */
    public static String taobaoGetAuthorizeURl(String response_type, String state,
            String isComeFromBindManage, String clientIp, String isWap, String backUrl) {

        String redirect_uri = ConfigurationUtil.getString("taobao_website_redirectURL").trim();
        String viewType = "web";

        if (isWap != null && "Y".equals(isWap.toUpperCase())) {
            redirect_uri = ConfigurationUtil.getString("taobao_website_redirectURL_WAP").trim();
            viewType = "wap";
        }
        String returnstr =
                redirect_uri + "?" + ThirdConstant.IS_COMEFROMBINDMANAGE + "="
                        + isComeFromBindManage + "&clientIp=" + clientIp + "&isWap=" + isWap
                        + "&returnUrl=" + backUrl;
        String returnUrl = URLEncodeUtils.encodeURL(returnstr);

        String url =
                ConfigurationUtil.getString("taobao_website_authorizeURL").trim() + "?client_id="
                        + ConfigurationUtil.getString("taobao_qt_appkey").trim() + "&redirect_uri="
                        + returnUrl + "&response_type=" + response_type + "&state=" + state
                        + "&view=" + viewType;
        return url;
    }

    /**
     * 为微信授权获取code生成请求的url
     * 
     * @param request
     * @param isComeFromBindManage
     * @param clientIp
     * @return
     */
    public static String generateAuthorizeUrlForWx(String state, String isComeFromBindManage,
            String clientIp, String returnUrl) {

        String paraStrForReturnUrl = null;

        String trueReturnUrl = null;

        // 如果returnUrl不为空并且带了参数,则将参数用一个变量封起来 20150430 maliqun add,为了传递参数方便并且回到上一级操作页面
        if (returnUrl != null && !returnUrl.isEmpty() && !"null".equals(returnUrl)) {
            int hasPara = returnUrl.indexOf("?");
            if (hasPara > 0) {
                trueReturnUrl = returnUrl.substring(0, hasPara);
                System.out.println("单纯的返回url=" + trueReturnUrl);
                log.info("BaseUtil==> 微信回到上一级操作页面域名前半部分trueReturnUrl=" + trueReturnUrl);

                paraStrForReturnUrl = returnUrl.substring(hasPara + 1, returnUrl.length());
                log.info("BaseUtil==> 微信回到上一级操作页面地址需要传入的参数paraStrForReturnUrl="
                        + paraStrForReturnUrl);
                // urlEncode 一定要记得,不然&等特殊符号传递不过去
                paraStrForReturnUrl = URLEncodeUtils.encodeURL(paraStrForReturnUrl);
                System.out.println("paraStrForReturnUrl=" + paraStrForReturnUrl);
            } else {
                trueReturnUrl = returnUrl;
            }
        }
        log.info("BaseUtil==> 微信回到上一级操作页面域名前半部分trueReturnUrl=" + trueReturnUrl);


        String url =
                ConfigurationUtil.getString("wx_authorizeURL").trim()
                        + "?appid="
                        + ConfigurationUtil.getString("wx_appid").trim()
                        + "&redirect_uri="
                        + URLEncodeUtils.encodeURL(ConfigurationUtil.getString("wx_redirect_URI")
                                .trim()
                                + "?"
                                + ThirdConstant.IS_COMEFROMBINDMANAGE
                                + "="
                                + isComeFromBindManage
                                + "&clientIp="
                                + clientIp
                                + "&returnUrl="
                                + trueReturnUrl + "&paraStrForReturnUrl=" + paraStrForReturnUrl)
                        + "&response_type=code&scope="
                        + ConfigurationUtil.getString("wx_scope_userInfo").trim() + "&state="
                        + state + "#wechat_redirect";

        return url;
    }

}
