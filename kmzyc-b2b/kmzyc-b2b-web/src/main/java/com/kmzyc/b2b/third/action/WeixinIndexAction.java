package com.kmzyc.b2b.third.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.third.util.BaseUtil;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.b2b.third.util.URLEncodeUtils;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 该类暂时仅用于wap,用于微信登录拼接让用户授权的url
 * 
 * @author Administrator
 * 
 */
@Controller("WeixinIndexAction")
@Scope("prototype")
public class WeixinIndexAction extends BaseAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(WeixinIndexAction.class);
    /*
     * 该参数标识来源,是来源wap还是来源电脑端,或者是来自直接登录还是来自绑定管理,state参数在redirect后还可以获取得到
     */
    private String comeFrom;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 是否是从绑定管理处过来,如果是,值为Y
     */
    private String isComeFromBindManage;

    /**
     * 授权过后所需要过去的链接
     */
    private String returnUrl;

    /**
     * 拼接授权url
     * 
     * @return
     * @throws IOException
     */
    public String wxIndexForWap() throws IOException {
        log.info("wxIndexForWap()各参数值如下: clientIp=" + clientIp + ", comeFrom= " + comeFrom
                + ", isComeFromBindManage= " + isComeFromBindManage + ", returnUrl=" + returnUrl);

        String redirectUrl =
                BaseUtil.generateAuthorizeUrlForWx(comeFrom, isComeFromBindManage, clientIp,
                        returnUrl);

        log.info("wxIndexForWap()请求用户授权获取code url如下=" + redirectUrl);

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        response.sendRedirect(redirectUrl);
        return null;
    }



    /**
     * 20150922 mlq add 微信扫产品码授权开始
     * 
     * @return
     * @throws IOException
     */
    public String wxIndexForWxScanProduct() throws IOException {

        // TODO 此处写号跳转组方的链接,并且为其拼接好参数,暂时未做openId和用户的关联
        String productSkuId = super.getRequest().getParameter("productSkuId");

        String returnUrl =
                ConfigurationUtil.getString("wx_goToProductCombineActionUrl") + productSkuId;
        log.info("点击组方购买授权获取用户openId后去到的链接=" + returnUrl + ",productSkuId参数为=" + productSkuId);

        super.getResponse().setContentType("text/html;charset=utf-8");
        String redirectUrl =
                redirectUrlForWxGroup(comeFrom, isComeFromBindManage, clientIp, returnUrl);
        log.info("微信登录获取openId跳转的url=" + redirectUrl);
        super.getResponse().sendRedirect(redirectUrl);
        return null;
    }



    /**
     * 暂时解决方案 20151009
     * 
     * @param state
     * @param isComeFromBindManage
     * @param clientIp
     * @param returnUrl
     * @return
     */
    protected String redirectUrlForWxGroup(String state, String isComeFromBindManage,
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
                        + URLEncodeUtils.encodeURL(ConfigurationUtil.getString(
                                "wx_redirect_url_group").trim()
                                + "?"
                                + ThirdConstant.IS_COMEFROMBINDMANAGE
                                + "="
                                + isComeFromBindManage
                                + "&clientIp="
                                + clientIp
                                + "&returnUrl="
                                + trueReturnUrl + "&paraStrForReturnUrl=" + paraStrForReturnUrl)
                        + "&response_type=code&scope=snsapi_base&state=" + state
                        + "#wechat_redirect";

        return url;
    }



    public String getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getIsComeFromBindManage() {
        return isComeFromBindManage;
    }

    public void setIsComeFromBindManage(String isComeFromBindManage) {
        this.isComeFromBindManage = isComeFromBindManage;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

}
