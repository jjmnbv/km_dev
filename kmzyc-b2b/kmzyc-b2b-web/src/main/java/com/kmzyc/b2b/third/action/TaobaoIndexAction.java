package com.kmzyc.b2b.third.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.third.util.BaseUtil;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.b2b.third.util.URLEncodeUtils;
import com.km.framework.action.BaseAction;

@Controller("taobaoIndexAction")
@Scope("prototype")
public class TaobaoIndexAction extends BaseAction {
  /**
	 * 
	 */
  private static final long serialVersionUID = 8818003728183317738L;

  /**
   * 日志记录对象
   */
  private static Logger log = LoggerFactory.getLogger(TaobaoIndexAction.class);

  // 提供用户登录，获取code
  public void goAuthorization() throws IOException {

    HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();

    String isComeFromBind = request.getParameter(ThirdConstant.IS_COMEFROMBINDMANAGE);
    String clientIp = request.getParameter("clientIp");
    String isWap = request.getParameter("isWap");
    String backUrl = request.getParameter("returnUrl");
    String requestType = "code";
    String state = "6818"; // 该参数为标识应用的请求状态,传入值与返回值保持一致,可有开发人员根据情况随机拟定

    // 暂时由state参数传递 returnUrl 所需要的参数
    String paraStrForBackUrl = request.getParameter("paraStr");
    if (paraStrForBackUrl != null && !paraStrForBackUrl.isEmpty()) {
      state = URLEncodeUtils.encodeURL(paraStrForBackUrl);
    }

    System.out.println("TaobaoIndexAction 的index方法参数如下: isComeFromBind=" + isComeFromBind
        + ",clientIp=" + clientIp + ",isWap=" + isWap + ",returnUrl=" + backUrl + ",paraStr="
        + state);
    log.info("TaobaoIndexAction 的index方法参数如下: isComeFromBind=" + isComeFromBind + ",clientIp="
        + clientIp + ",isWap=" + isWap + ",paraStr=" + state);

    String authUrl =
        BaseUtil
            .taobaoGetAuthorizeURl(requestType, state, isComeFromBind, clientIp, isWap, backUrl);
    System.out.println("授权构建的url====" + authUrl);
    log.info("TaobaoIndexAction的index()方法 请求获取code的url=" + authUrl);
    response.sendRedirect(authUrl);
  }

}
