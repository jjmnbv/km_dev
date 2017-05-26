package com.kmzyc.b2b.third.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.third.util.BaseUtil;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.km.framework.action.BaseAction;
import com.qq.connect.QQConnectException;

@Controller("QQIndexAction")
@Scope("prototype")
public class QQIndexAction extends BaseAction {
  /**
	 * 
	 */
  private static final long serialVersionUID = 8818003728183317738L;

  // Logger log = Logger.getLogger(QQIndexAction.class);
  private static Logger log = LoggerFactory.getLogger(QQIndexAction.class);

  // 提供用户登录，获取code
  public void index() throws IOException {

    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    response.setContentType("text/html;charset=utf-8");
    try {

      String isComeFromBind = request.getParameter(ThirdConstant.IS_COMEFROMBINDMANAGE);
      String clientIp = request.getParameter("clientIp");
      String isWap = request.getParameter("isWap");
      String backUrl = request.getParameter("returnUrl");

      System.out.println("QQIndexAction 的index方法参数如下: isComeFromBind=" + isComeFromBind
          + ",clientIp=" + clientIp + ",isWap=" + isWap + ",returnUrl=" + backUrl + ",paraStr="
          + request.getParameter("paraStr"));
      log.info("QQIndexAction 的index方法参数如下: isComeFromBind=" + isComeFromBind + ",clientIp="
          + clientIp + ",isWap=" + isWap + ",paraStr=" + request.getParameter("paraStr"));

      String redirectUrl =
          BaseUtil.getAuthorizeURLForQQ(request, isComeFromBind, clientIp, isWap, backUrl);

      System.out.println("QQ请求code url=" + redirectUrl);
      log.info("QQIndexAction的index()方法 请求获取code的url=" + redirectUrl);
      response.sendRedirect(redirectUrl);
    } catch (QQConnectException e) {
      log.error(e.getMessage(),e);
    }
  }

}
