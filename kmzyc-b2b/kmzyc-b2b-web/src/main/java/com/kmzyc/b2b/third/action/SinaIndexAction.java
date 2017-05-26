package com.kmzyc.b2b.third.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.b2b.weibo.Oauth;
import com.kmzyc.b2b.weibo.model.WeiboException;
import com.km.framework.action.BaseAction;

@Controller("sinaIndexAction")
@Scope("prototype")
public class SinaIndexAction extends BaseAction {
  /**
	 * 
	 */
  private static final long serialVersionUID = 4714661164874441473L;

  private String url;

  /**
	 * 
	 */
  // 提供用户登录，获取code
  public void sinaIndex() throws IOException, WeiboException {
    System.out.println("sinaIndex in-------------------------------");
    HttpServletResponse response = ServletActionContext.getResponse();

    HttpServletRequest request = ServletActionContext.getRequest();
    response.setContentType("text/html;charset=utf-8");
    Oauth oauth = new Oauth();
    // String url=oauth.authorize("code","110");
    // 20140414注释掉,加上我们自己的参数,重载authrize方法
    this.url =
        oauth.authorizeOverload("code", "110", request
            .getParameter(ThirdConstant.IS_COMEFROMBINDMANAGE), request.getParameter("clientIp"));
    System.out.println("url=" + url);
    response.sendRedirect(url);
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
